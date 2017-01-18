/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatEstadoEntidad;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.utils.DataBaseUtils;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.NodoUtils;
import es.caib.dir3caib.persistence.utils.Paginacion;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author mgonzalez
 * @author anadal
 */
@Stateless(name = "UnidadEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN","tothom"})
public class UnidadBean extends BaseEjbJPA<Unidad, String> implements UnidadLocal {

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    public OficinaLocal oficinaEjb;

    protected final Logger log = Logger.getLogger(getClass());
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @PersistenceContext
    private EntityManager em;


    @Override
    public Unidad getReference(String id) throws Exception {

        return em.getReference(Unidad.class, id);
    }

    @Override
    public Unidad findById(String id) throws Exception {

        return em.find(Unidad.class, id);
    }

    /**
     * Obtiene una unidad que es vigente con sus historicosUO
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Unidad findConHistoricosVigente(String id) throws Exception {
        Query q = em.createQuery("select unidad from Unidad as unidad where unidad.codigo=:id and unidad.estado.codigoEstadoEntidad=:vigente");
        q.setParameter("id", id);
        q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);


        Unidad unidad = null;
        List<Unidad> unidades = q.getResultList();
        if (unidades.size() > 0) {
            unidad = unidades.get(0);
        }

        if (unidad != null) {
            Hibernate.initialize(unidad.getHistoricoUO());
        }

        return unidad;
    }

    /**
     * Método que busca la unidad con id indicado y estado indicado
     *
     * @param id
     * @param estado
     * @return
     * @throws Exception
     */
    public Unidad findUnidadEstado(String id, String estado) throws Exception {
        Query q = em.createQuery("select unidad from Unidad as unidad where unidad.codigo=:id and unidad.estado.codigoEstadoEntidad=:estado");
        q.setParameter("id", id);
        q.setParameter("estado", estado);

        List<Unidad> unidad = q.getResultList();

        if(unidad.size() > 0){
            return unidad.get(0);
        }else {
            return  null;
        }
    }

    /**
     * Obtiene el codigo, la denominación, el estado, la tupla codigo-denominacion de la unidad raiz y la tupla codigo-denominacion de la unidad.
     * Se emplea para mostrar el arbol de unidades.
     * @param id     identificador de la unidad
     * @param estado estado de la unidad
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    @Override
    public Nodo findUnidad(String id, String estado) throws Exception {

        Query q = em.createQuery("Select unidad.codigo, unidad.denominacion, unidad.estado.descripcionEstadoEntidad, unidad.codUnidadRaiz.codigo, unidad.codUnidadRaiz.denominacion, unidad.codUnidadSuperior.codigo, unidad.codUnidadSuperior.denominacion from Unidad as unidad where unidad.codigo=:id and unidad.estado.codigoEstadoEntidad =:estado");
        q.setParameter("id", id);
        q.setParameter("estado", estado);

        List<Object[]> unidades = q.getResultList();

        if(unidades.size() > 0){
            Object[] obj = unidades.get(0);
            return new Nodo((String) obj[0], (String) obj[1], (String) obj[2], obj[3] + " - " + obj[4], obj[5] + " - " + obj[6], "");
        }else{
            return null;
        }

    }

    /**
     * Obtiene si una unidad ha sido actualizada con fecha posterior a la fecha de actualización.
     * Para ello mira si la fecha de importación de la unidad es posterior a la fecha de actualización indicada.
     * La fecha de importación de la unidad se actualiza cada vez que se sincroniza con directorio común.
     *
     * Se usa para la sincronizacion con regweb3
     * @param id
     * @param fechaActualizacion
     * @return
     * @throws Exception
     */
    @Override
    public Unidad findUnidadActualizada(String id, Date fechaActualizacion) throws Exception {
        Query q = em.createQuery("Select unidad from Unidad as unidad where unidad.codigo =:id " +
                " and :fechaActualizacion < unidad.fechaImportacion ");

        q.setParameter("id", id);
        q.setParameter("fechaActualizacion", fechaActualizacion);

        List<Unidad> unidades = q.getResultList();
        if (unidades.size() > 0) {
            return unidades.get(0);
        } else {
            return null;
        }

    }


    /**
     * Obtiene el código, denominación y estado de la unidad indicada
     * @param codigo
     * @return
     * @throws Exception
     */
    @Override
    public Unidad findByCodigoLigero(String codigo) throws Exception {
        Query q = em.createQuery("select unidad.codigo, unidad.denominacion, unidad.estado.codigoEstadoEntidad from Unidad as unidad where unidad.codigo=:codigo ");
        q.setParameter("codigo", codigo);

        List<Object[]> result = q.getResultList();

        if (result.size() == 1) {
            Unidad unidad = new Unidad();
            unidad.setCodigo((String) result.get(0)[0]);
            unidad.setDenominacion((String) result.get(0)[1]);
            unidad.setEstado(new CatEstadoEntidad((String)result.get(0)[2]));

            return  unidad;
        }else {
            return  null;
        }
    }


    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Unidad> getAll() throws Exception {

        return em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo").getResultList();
    }

    public List<Unidad> getMaxResult(int maxResult) throws Exception {
        return em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo").setMaxResults(maxResult).getResultList();
    }

    @Override
    public Long getTotal() throws Exception {
        log.info("Entramos en getTotal");
        Query q = em.createQuery("Select count(distinct unidad.codigo) from Unidad as unidad");

        return (Long) q.getSingleResult();

    }

    /**
     * Obtiene una lista de unidades paginada desde un valor inicial y de la longitud base que establece dir3caib
     * @return
     * @throws Exception
     */
    @Override
    public List<Unidad> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    /**
     * Obtiene una lista de unidades paginada desde un valor inicial y de una longitud determinada
     * @param startItem valor inicial
     * @param numberOfItems longitud de la lista
     * @return
     * @throws Exception
     */
    @Override
    public List<Unidad> getPagination(int startItem, int numberOfItems) throws Exception {

        Query q = em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo");
        q.setFirstResult(startItem);
        q.setMaxResults(numberOfItems);

        return q.getResultList();
    }

    /**
     * Obtiene la Denominacion de una Unidad
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    @Override
    public String unidadDenominacion(String codigo) throws Exception {

        Query q = em.createQuery("select unidad.denominacion from Unidad as unidad where unidad.codigo=:codigo").setParameter("codigo", codigo);

        List<String> unidades = q.getResultList();

        if(unidades.size() > 0){
            return unidades.get(0);
        }else {
            return  null;
        }
    }

    /**
     * Devuelve todas las unidades de la lista de ids indicados. Se emplea para montar la cache de unidades
     * en la importación de unidades desde Madrid
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<Unidad> getListByIds(List<String> ids) throws Exception {


        Query q = em.createQuery("Select unidad.codigo from Unidad as unidad "
                + " where unidad.codigo in (:theids) order by unidad.codigo");

        q.setParameter("theids", ids);

        List<Unidad> unidades = new ArrayList<Unidad>();
        List<?> result = q.getResultList();

        for (Object object : result) {
            unidades.add(new Unidad((String) object));
        }

        return unidades;
    }

    /**
     * Borra todos los historicos de las unidades
     * @throws Exception
     */
    public void deleteHistoricosUnidad() throws Exception {

        em.createNativeQuery("delete from DIR_HISTORICOUO").executeUpdate();
    }


    public void deleteAll() throws Exception {

        em.createQuery("delete from Unidad").executeUpdate();
    }


    /**
     * Realiza una busqueda de {@link es.caib.dir3caib.persistence.model.Unidad} según los parámetros
     * @param pageNumber
     * @param codigo
     * @param denominacion
     * @param codigoNivelAdministracion
     * @param codAmbitoTerritorial
     * @param codComunidad
     * @param codigoProvincia
     * @param codigoEstado
     * @return
     * @throws Exception
     */
    @Override
    public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion, Long codigoNivelAdministracion, String codAmbitoTerritorial, Long codComunidad, Long codigoProvincia, Boolean unidadRaiz, String codigoEstado) throws Exception {

        Query q;
        Query q2;
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<String> where = new ArrayList<String>();

        StringBuffer query = new StringBuffer("Select unidad from Unidad as unidad ");

        // Parametros de busqueda
        if (codigo != null && codigo.length() > 0) {
            where.add(DataBaseUtils.like("unidad.codigo", "codigo", parametros, codigo));
        }
        if (denominacion != null && denominacion.length() > 0) {
            where.add(DataBaseUtils.like("unidad.denominacion", "denominacion", parametros, denominacion));
        }
        if (codigoNivelAdministracion != null && codigoNivelAdministracion != -1) {
            where.add(" unidad.nivelAdministracion.codigoNivelAdministracion = :codigoNivelAdministracion ");
            parametros.put("codigoNivelAdministracion", codigoNivelAdministracion);
        }
        if (codAmbitoTerritorial != null && (!"-1".equals(codAmbitoTerritorial))) {
            where.add(" unidad.codAmbitoTerritorial.codigoAmbito = :codAmbitoTerritorial ");
            parametros.put("codAmbitoTerritorial", codAmbitoTerritorial);
        }
        if (codComunidad != null && codComunidad != -1) {
            where.add(" unidad.codAmbComunidad.codigoComunidad = :codComunidad ");
            parametros.put("codComunidad", codComunidad);
        }
        if (codigoProvincia != null && codigoProvincia != -1) {
            where.add(" unidad.codAmbProvincia.codigoProvincia = :codigoProvincia ");
            parametros.put("codigoProvincia", codigoProvincia);
        }
        if (codigoEstado != null && (!"-1".equals(codigoEstado))) {
            where.add(" unidad.estado.codigoEstadoEntidad = :codigoEstado ");
            parametros.put("codigoEstado", codigoEstado);
        }
        if (unidadRaiz) {
            where.add(" unidad.codUnidadRaiz.codigo = unidad.codigo ");
        }

        // Añadimos los parametros a la query
        if (parametros.size() != 0) {
            query.append("where ");
            int count = 0;
            for (String w : where) {
                if (count != 0) {
                    query.append(" and ");
                }
                query.append(w);
                count++;
            }
            q2 = em.createQuery(query.toString().replaceAll("Select unidad from Unidad as unidad ", "Select count(unidad.codigo) from Unidad as unidad "));
            query.append("order by unidad.denominacion asc");
            q = em.createQuery(query.toString());

            for (Map.Entry<String, Object> param : parametros.entrySet()) {
                q.setParameter(param.getKey(), param.getValue());
                q2.setParameter(param.getKey(), param.getValue());
            }

        } else {
            if (unidadRaiz) {
                query.append(" where unidad.codUnidadRaiz.codigo = unidad.codigo ");
            }
            q2 = em.createQuery(query.toString().replaceAll("Select unidad from Unidad as unidad ", "Select count(unidad.codigo) from Unidad as unidad "));
            query.append("order by unidad.denominacion asc");
            q = em.createQuery(query.toString());
        }

        Paginacion paginacion = null;

        if (pageNumber != null) { // Comprobamos si es una busqueda paginada o no
            Long total = (Long) q2.getSingleResult();
            paginacion = new Paginacion(total.intValue(), pageNumber);
            int inicio = (pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION;
            q.setFirstResult(inicio);
            q.setMaxResults(RESULTADOS_PAGINACION);
        } else {
            paginacion = new Paginacion(0, 0);
        }
        paginacion.setListado(q.getResultList());

        return paginacion;

    }


    /**
     * Obtiene una unidad por su denominación
     *
     * @param denominacion
     * @return
     * @throws Exception
     */
    @Override
    public List<Unidad> findByDenominacion(String denominacion) throws Exception {

        Query q = em.createQuery("select unidad from Unidad as unidad where upper(unidad.denominacion) like upper(:denominacion)");

        q.setParameter("denominacion", "%" + denominacion.toLowerCase() + "%");

        return q.getResultList();
    }

    /**
     * Obtiene el arbol de una Unidad, pero solo los códigos
     * Se emplea en el método de obtenerArbolOficinas.
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    @Override
    public List<Unidad> obtenerArbol(String codigo) throws Exception {

        List<Unidad> arbol = new ArrayList<Unidad>();
        List<Unidad> hijos = hijosPrimerNivel(codigo);

        arbol.addAll(hijos);

        for (Unidad hijo : hijos) {
            arbol.addAll(obtenerArbol(hijo.getCodigo()));
        }

        return arbol;
    }


    /**
     * Método que comprueba si una unidad tiene más unidades hijas
     * @param codigo
     * @return
     * @throws Exception
     */
    @Override
    public Boolean tieneHijos(String codigo) throws Exception {

        Query q = em.createQuery("Select unidad.id from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo order by unidad.codigo");

        q.setParameter("codigo", codigo);

        List<Long> hijos = q.getResultList();

        return hijos.size() > 0;
    }


    /**
     * Método que obtiene los hijos de primer nivel de una unidad en función del estado de la unidad padre
     * Se emplea para pintar el árbol de Unidades.
     * @param codigo identificador de la unidad padre.
     * @param estado estado de la unidad padre.
     * @return  {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    @Override
    public List<Nodo> hijos(String codigo, String estado) throws Exception {

        Query q = em.createQuery("Select unidad.codigo, unidad.denominacion, unidad.estado.codigoEstadoEntidad,unidad.codUnidadRaiz.codigo, unidad.codUnidadRaiz.denominacion, unidad.codUnidadSuperior.codigo, unidad.codUnidadSuperior.denominacion from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:estado order by unidad.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("estado", estado);

        return NodoUtils.getNodoList(q.getResultList());
    }

    /**
     * Método que obtiene los codigos de los hijos vigentes de primer nivel de una unidad
     * @param codigo identificador de la unidad padre.
     * @return  {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    @Override
    public List<Unidad> hijosPrimerNivel(String codigo) throws Exception {

        Query q = em.createQuery("Select unidad.codigo from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:estado order by unidad.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("estado", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        List result = q.getResultList();
        List<Unidad> unidades = new ArrayList<Unidad>();

        for (Object object : result) {
            Unidad unidad = new Unidad((String) object);

            unidades.add(unidad);
        }

        return unidades;
    }

    /**
     * Método recursivo que obtiene los hijos de las unidades indicadas  en función del estado indicado y el resultado lo guarda en la variable hijosTotales
     * Se emplea en el mètodo obtenerArbolUnidadesDestinatarias
     *
     * @param unidadesPadres unidadesPadres de las que obtener hijos
     * @param estado indica el estado de los hijos
     * @param hijosTotales lista con todos los hijos encontrados de manera recursiva.
     *
     * @return  {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    @Override
    public void arbolHijos(Set<Unidad> unidadesPadres, String estado, Set<Unidad> hijosTotales) throws Exception {
        for (Unidad unidad : unidadesPadres) {

            Query q = em.createQuery("select unidad.codigo, unidad.denominacion from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:estado order by unidad.codigo");

            q.setParameter("codigo", unidad.getCodigo());
            q.setParameter("estado", estado);


            Set<Unidad> hijos = new HashSet<Unidad>();

            List<Object[]> result = q.getResultList();

            for (Object[] object : result) {
                hijos.add(new Unidad((String) object[0], (String) object[1]));
            }

            hijosTotales.addAll(hijos);

            //llamada recursiva para todos los hijos
            arbolHijos(hijos, estado, hijosTotales);
        }

    }

    /**
     * Método recursivo que devuelve el árbol de unidades de la unidad indicada por código,
     * teniendo en cuenta la fecha de la ultima actualización de regweb para el caso en que la unidad indicada no es raíz.
     * Se emplea para la sincronizacion y actualización con regweb
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @return
     * @throws Exception
     */
    @Override
    public List<Unidad> obtenerArbolUnidadesUnidadNoRaiz(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {
        //log.info("obtenerArbolUnidades del código: " + codigo);

        // Este método es recursivo, ya que la query se basa en el organismo superior y se hace necesario hacerlo
        // de forma recursiva  para poder descender hasta el último nivel, ya que la unidad indicada no es raiz.

        Query q;
        Query qHijos;

        if (fechaActualizacion == null) { // Es una sincronizacion, solo traemos vigentes
            q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.nivelJerarquico");
            q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        } else {// es una actualizacion, traemos todo lo actualizado

            q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo  " +
                    "and :fechaActualizacion < unidad.fechaImportacion " +
                    "order by unidad.nivelJerarquico");
            q.setParameter("fechaActualizacion", fechaActualizacion);

        }
        q.setParameter("codigo", codigo);


        //Obtenemos todos los hijos del organismo con código indicado para poder obtener todos los cambios en niveles inferiores de forma recursiva
        qHijos = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo  " +
                "order by unidad.nivelJerarquico");

        qHijos.setParameter("codigo", codigo);
        List<Unidad> todosHijos = qHijos.getResultList(); // todos los hijos de la unidad padre, para poder recorrer el árbol en su totalidad

        List<Unidad> hijosActualizados = q.getResultList(); // Hijos actualizados solo por fecha de importación
        List<Unidad> hijosActualizadosValidos = new ArrayList<Unidad>(); //Hijos actualizados validos( unidadValida())
        List<Unidad> listaCompleta; // Lista completa de unidades a enviar a regweb3.

        //log.info(hijosActualizados.size() + " hijos actualizados del código : " + codigo);

        if (fechaActualizacion != null) { // Si hay fecha de actualizacion solo se envian las actualizadas

            for (Unidad unidad : hijosActualizados) {
                log.debug("FECHA ACTUALIZACION " + fechaActualizacion + "ANTERIOR A LA FECHA DE IMPORTACION DE LA UNIDAD ID " + unidad.getCodigo() + " FECHA IMPORT" + unidad.getFechaImportacion());
                // Miramos que la unidad no este extinguida o anulada anterior a la fecha de sincronizacion de regweb
                // ya que no debe ser enviada a regweb.
                if (unidadValida(unidad, fechaSincronizacion)) {
                    hijosActualizadosValidos.add(unidad);
                }
            }
            listaCompleta = new ArrayList<Unidad>(hijosActualizadosValidos);
        } else { // si no hay fecha, se trata de una sincronización
            listaCompleta = new ArrayList<Unidad>(hijosActualizados);
        }

        for (Unidad unidad : todosHijos) {
            if (tieneHijos(unidad.getCodigo())) {
                List<Unidad> hijos = obtenerArbolUnidadesUnidadNoRaiz(unidad.getCodigo(), fechaActualizacion, fechaSincronizacion);
                listaCompleta.addAll(hijos);
            }
        }

        return listaCompleta;
    }

    /**
     * Método que devuelve el árbol de unidades de la unidad indicada por codigo,
     * teniendo en cuenta la fecha de la ultima actualización de regweb y se emplea para el caso que la unidad indicada es raíz.
     * Se emplea para la sincronizacion y actualización con regweb
     *
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @return
     * @throws Exception
     */
    @Override
    public List<Unidad> obtenerArbolUnidadesUnidadRaiz(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

        String denominacion = unidadDenominacion(codigo);
        Query q;

        try {
            if (fechaActualizacion == null) { // Es una sincronizacion
                //Obtenemos todos los organismos vigentes cuya unidad raiz es la indicada por el código.
                q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadRaiz.codigo =:codigo  and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.nivelJerarquico");
                q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            } else {
                // es una actualizacion, traemos aquellas unidades que tienen fechaactualizacion anterior a la fecha de importacion de las unidades
                q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadRaiz.codigo =:codigo " +
                        "and :fechaActualizacion < unidad.fechaImportacion " +
                        "order by unidad.nivelJerarquico");
                q.setParameter("fechaActualizacion", fechaActualizacion);
            }
            q.setParameter("codigo", codigo);


            List<Unidad> unidadesObtenidas = q.getResultList();// unidades candidatas(que devuelve la query) a ser enviadas a regweb3
            List<Unidad> padresActualizados = new ArrayList<Unidad>(); // unidades que se han actualizado entre las ultimas fechas indicadas
            List<Unidad> listaCompleta; // Lista completa de unidades que se enviaran a regweb3 por cumplir todas las condiciones de ser enviadas

            log.info(" ");
            log.info("UNIDAD PADRE: " + codigo + " - " + denominacion);

            if (fechaActualizacion != null) { // Si hay fecha de actualizacion solo se envian las actualizadas
                for (Unidad unidad : unidadesObtenidas) {
                    log.debug("FECHA ACTUALIZACION " + fechaActualizacion + "ANTERIOR A LA FECHA DE IMPORTACION DE LA UNIDAD ID " + unidad.getCodigo() + " FECHA IMPORT" + unidad.getFechaImportacion());
                    // Miramos que la unidad no este extinguida o anulada anterior a la fecha de sincronizacion de regweb
                    // ya que no debe ser enviada a regweb.
                    if (unidadValida(unidad, fechaSincronizacion)) {
                        padresActualizados.add(unidad);
                    }
                }
                listaCompleta = new ArrayList<Unidad>(padresActualizados);
            } else { // si no hay fecha, se trata de una sincronización
                listaCompleta = new ArrayList<Unidad>(unidadesObtenidas);
            }

            log.info("Numero de hijos a actualizar: " + listaCompleta.size());

            log.info(" ");

            return listaCompleta;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que devuelve la unidad indicada por código siempre que esté vigente y tenga oficinas donde registrar.
     * A partir de ella se obtienen todos sus hijos vigentes.
     * solicitado por SISTRA
     * @param codigo código de la unidad raiz de la que partimos.
     * @return
     * @throws Exception
     */
    @Override
    public List<Unidad> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception {

        Query q = em.createQuery("Select unidad.codigo, unidad.denominacion from Unidad as unidad where unidad.codigo =:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");
        q.setParameter("codigo", codigo);
        q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);


        // Transformamos a Unidades
        List<Unidad> unidades = new ArrayList<Unidad>();
        List<Object[]> result = q.getResultList();

        for (Object[] object : result) {
            unidades.add(new Unidad((String) object[0], (String) object[1]));
        }

        Unidad unidadRaiz = null;
        //Lista con el resultado final de unidades destinatarias
        List<Unidad> unidadesDestConOficinas = new ArrayList<Unidad>();
        if (unidades.size() > 0) {
            unidadRaiz = unidades.get(0);
            log.info("UNIDAD ENCONTRADA " + unidadRaiz.getCodigo());

            //Miramos si la unidad que nos pasan tiene oficina
            Boolean tiene = oficinaEjb.tieneOficinasArbol(unidadRaiz.getCodigo());

            if (tiene) {
                unidadesDestConOficinas.add(unidadRaiz);
                Set<Unidad> unidadesRaices = new HashSet<Unidad>();
                //añadimos la unidad raiz porque tiene oficina
                unidadesRaices.add(unidadRaiz);
                //Aqui guardamos todas las unidades hijas que vamos a obtener de la unidad indicada
                Set<Unidad> unidadesHijasTotales = new HashSet<Unidad>();
                //Obtenemos de manera recursiva todos los hijos de la unidad que nos indican
                arbolHijos(unidadesRaices, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE, unidadesHijasTotales);

                unidadesDestConOficinas.addAll(unidadesHijasTotales);
            }

        }

        return unidadesDestConOficinas;
    }

    /**
     * Obtiene el código de todas las Unidades hijas de la unidad raiz indicada por código
     * y que tienen una oficina que cuelga de ellas
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public List<Unidad> obtenerUnidadesConOficina(String codigo) throws Exception {

        Query q = em.createQuery("Select unidad.codigo from Unidad as unidad, Oficina as oficina " +
                "inner join oficina.codUoResponsable as unidadResponsable " +
                "where unidadResponsable.codigo = unidad.codigo and unidad.codUnidadRaiz.codigo =:codigo and unidad.estado.codigoEstadoEntidad =:estado order by unidad.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("estado", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        List result = q.getResultList();
        List<Unidad> unidades = new ArrayList<Unidad>();

        for (Object object : result) {
            Unidad unidad = new Unidad((String) object);

            unidades.add(unidad);
        }

        return unidades;
    }


    /**
     * Se mira que si la unidad,  su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar unidades que fueron extinguidas o anuladas
     * antes de la primera sincronización con Madrid.
     *
     * @param unidad      unidad
     * @param fechaSincro fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
    public boolean unidadValida(Unidad unidad, Date fechaSincro) throws Exception {

        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sSincro = new String();
        if (fechaSincro != null) {
            sSincro = fechaFormat.format(fechaSincro);
        }
        // Si tiene fecha de extinción
        if (unidad.getFechaExtincion() != null) {
            String sExtincion = fechaFormat.format(unidad.getFechaExtincion());
            // Si la fecha de extinción es posterior o igual a la fecha sincro, se debe enviar a regweb
            if (unidad.getFechaExtincion().after(fechaSincro) || sExtincion.equals(sSincro)) {
                return true;
            }
        } else {
            // Si tiene fecha de anulación
            if (unidad.getFechaAnulacion() != null) {
                String sAnulacion = fechaFormat.format(unidad.getFechaAnulacion());
                // Si la fecha de anulación es posterior o igual a la fecha sincronizacion se debe enviar a regweb
                if (unidad.getFechaAnulacion().after(fechaSincro) || sAnulacion.equals(sSincro)) {
                    return true;
                }
            } else { // Si no tiene ni fecha de extincion ni de anulación, tambien se debe enviar a regweb
                return true;
            }
        }
        return false;
    }


    /**
     * Devuelve  los Identificadores de todas las unidades existentes en base de datos
     *
     * @return
     */
    @Override
    public List<String> getAllCodigos() {
        Query q = em.createQuery("Select unidad.codigo from Unidad as unidad order by unidad.codigo");
        List<String> codigos = q.getResultList();
        return codigos;
    }
}
