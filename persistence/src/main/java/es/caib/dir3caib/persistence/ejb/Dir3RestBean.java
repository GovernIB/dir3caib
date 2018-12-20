package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.*;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created 1/04/14 9:50
 *
 * @author mgonzalez
 * Clase que implementa la funcionalidad de varios servicios rest que pueden ser llamados desde otras aplicaciones.
 */
@Stateless(name = "Dir3RestEJB")
public class Dir3RestBean implements Dir3RestLocal {

    protected final Logger log = Logger.getLogger(getClass());
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;


    /**
     * Obtiene las unidades(codigo-denominacion) cuya denominación coincide con la indicada.
     *
     * @param denominacion
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<ObjetoDirectorio> findUnidadesByDenominacion(String denominacion) throws Exception {

        if (!denominacion.isEmpty()) {
            Query q = em.createQuery("select unidad.codigo, unidad.denominacion from Unidad as unidad where upper(unidad.denominacion) like upper(:denominacion)");
            q.setParameter("denominacion", "%" + denominacion.toLowerCase() + "%");
            return transformarAObjetoDirectorio(q.getResultList());
        } else {
            return new ArrayList<ObjetoDirectorio>();
        }
    }

    /**
     * Obtiene las oficinas(codigo-denominacion) cuya denominación coincide con la indicada.
     *
     * @param denominacion
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<ObjetoDirectorio> findOficinasByDenominacion(String denominacion) throws Exception {

        if (!denominacion.isEmpty()) {
            Query q = em.createQuery("select oficina.codigo, oficina.denominacion from Oficina as oficina where upper(oficina.denominacion) like upper(:denominacion)");
            q.setParameter("denominacion", "%" + denominacion.toLowerCase() + "%");
            return transformarAObjetoDirectorio(q.getResultList());
        } else {
            return new ArrayList<ObjetoDirectorio>();
        }
    }

    /**
     * Método que comprueba si una unidad tiene más unidades hijas
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public Boolean tieneHijos(String codigo) throws Exception {
        Query q = em.createQuery("Select unidad.codigo from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo order by unidad.codigo");
        q.setParameter("codigo", codigo);

        List<Unidad> hijos = q.getResultList();
        return hijos.size() > 0;
    }

    /**
     * Obtiene el arbol de unidades de la unidad indicada por código.
     *
     * @param codigo
     * @param fechaActualizacion
     * @return
     * @throws Exception
     */
    //TODO REVISAR PARECE QUE NO SE EMPLEA, en REGWEB3 NO SE EMPLEA(03/10/2017)
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Unidad> obtenerArbolUnidades(String codigo, String fechaActualizacion) throws Exception {
        Query q;
        if (fechaActualizacion == null) { // Es una sincronizacion, solo traemos vigentes
            q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");
            q.setParameter("codigo", codigo);
            q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        } else {// es una actualizacion, lo traemos todo
            q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo  order by unidad.codigo");
            q.setParameter("codigo", codigo);

        }

        List<Unidad> padres = q.getResultList();
        List<Unidad> padresActualizados = new ArrayList<Unidad>();
        List<Unidad> listaCompleta;

        //log.info("Número de PADRES: " + padres.size());

        if (fechaActualizacion != null) { // Si hay fecha de actualizacion solo se envian las actualizadas
            Date fechaAct = formatoFecha.parse(fechaActualizacion);
            for (Unidad unidad : padres) {
                if (fechaAct.before(unidad.getFechaImportacion())) {
                    padresActualizados.add(unidad);
                }
            }
            listaCompleta = new ArrayList<Unidad>(padresActualizados);
        } else { // si no hay fecha, se trata de una sincronización
            listaCompleta = new ArrayList<Unidad>(padres);
        }

        for (Unidad unidad : padres) {
            if (tieneHijos(unidad.getCodigo())) {
                List<Unidad> hijos = obtenerArbolUnidades(unidad.getCodigo(), fechaActualizacion);
                listaCompleta.addAll(hijos);
            }
        }

        return listaCompleta;
    }

    /**
     * Función que obtiene los hijos vigentes de una Unidad pero como Nodo ya que solo interesa
     * el código, denominación y estado .
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    //TODO REVISAR PARECE QUE NO SE EMPLEA NI EN EL RestController, en REGWEB3 NO SE EMPLEA(03/10/2017)
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Nodo> obtenerArbolUnidades(String codigo) throws Exception {
        Query q;

        q = em.createQuery("Select unidad.codigo, unidad.denominacion, unidad.estado.descripcionEstadoEntidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");
        q.setParameter("codigo", codigo);
        q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);


        List<Nodo> padres = NodoUtils.getNodoList(q.getResultList());
        List<Nodo> listaCompleta;

        listaCompleta = new ArrayList<Nodo>(padres);

        for (Nodo unidad : padres) {
            if (tieneHijos(unidad.getCodigo())) {
                List<Nodo> hijos = obtenerArbolUnidades(unidad.getCodigo());
                listaCompleta.addAll(hijos);
            }
        }

        return listaCompleta;
    }


    /*
     * Método que devuelve las oficinas de un organismo,
     * teniendo en cuenta la fecha de la ultima actualización de regweb.
     * Se emplea para la sincronizacion y actualización con regweb
     * */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Oficina> obtenerOficinasOrganismo(String codigo, String fechaActualizacion) throws Exception {

        Query q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad='V' order by oficina.codigo");

        q.setParameter("codigo", codigo);

        List<Oficina> oficinas = q.getResultList();
        List<Oficina> oficinasCompletas;
        List<Oficina> oficinasActualizadas = new ArrayList<Oficina>();
        // Si hay fecha de actualización y es anterior a la fecha de importación se debe
        // incluir en la lista de actualizadas
        for (Oficina ofi : oficinas) {
            Hibernate.initialize(ofi.getContactos());
        }
        if (fechaActualizacion != null) {
            Date fechaAct = formatoFecha.parse(fechaActualizacion);
            for (Oficina oficina : oficinas) {
                if (fechaAct.before(oficina.getFechaImportacion())) {
                    oficinasActualizadas.add(oficina);
                }
            }
            oficinasCompletas = new ArrayList<Oficina>(oficinasActualizadas);
        } else { // Si no hay fecha de actualización se trata de una sincronización
            oficinasCompletas = new ArrayList<Oficina>(oficinas);
        }

        return oficinasCompletas;

    }

    /**
     * Método que nos dice sin una unidad tiene oficinas donde registrar.
     * Solo mira relacion funcional y organizativa. Pendiente SIR y Oficinas Virtuales.
     *
     * @param codigo de la unidad que queremos consultar
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public Boolean tieneOficinasOrganismo(String codigo) throws Exception {

        Query q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad=:vigente order by oficina.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        List<Oficina> oficinas = q.getResultList();
        if (oficinas.size() > 0) {
            return true;
        } else {
            q = em.createQuery("Select relorg from RelacionOrganizativaOfi as relorg where relorg.unidad.codigo=:codigo and relorg.estado.codigoEstadoEntidad=:vigente order by relorg.id ");

            q.setParameter("codigo", codigo);
            q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            List<RelacionOrganizativaOfi> relorg = q.getResultList();
            return relorg.size() > 0;
        }

    }

    /**
     * TODO REVISAR, parece que el parametro "conOficinas" siempre se indica a "false"
     * Búsqueda de organismos según los parámetros indicados que esten vigentes
     *
     * @param codigo                    código de la unidad
     * @param denominacion              denominación de la unidad
     * @param codigoNivelAdministracion nivel de administración de la unidad
     * @param codComunidad              comunidad autónoma a la que pertenece.
     * @param conOficinas               indica el conOficinas de la búsqueda desde regweb "OrganismoInteresado" , "OrganismoDestinatario"
     * @return List<Nodo> conjunto de datos a mostrar del organismo
     * coincide con los parámetros de búsqueda
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Nodo> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, boolean conOficinas, boolean unidadRaiz, Long provincia, String localidad, boolean vigentes) throws Exception {
        Query q;
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<String> where = new ArrayList<String>();

        StringBuilder query = new StringBuilder("Select distinct(unidad.codigo),unidad.denominacion, unidad.estado.codigoEstadoEntidad, unidad.codUnidadRaiz.codigo, unidad.codUnidadRaiz.denominacion, unidad.codUnidadSuperior.codigo, unidad.codUnidadSuperior.denominacion, unilocalidad.descripcionLocalidad  " +
                "from Unidad  as unidad left outer join unidad.catLocalidad as unilocalidad  ");

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
        if (codComunidad != null && codComunidad != -1) {
            where.add(" unidad.codAmbComunidad.codigoComunidad = :codComunidad ");
            parametros.put("codComunidad", codComunidad);
        }
        if (provincia != null && provincia != -1) {
            where.add(" unidad.codAmbProvincia.codigoProvincia = :codProvincia");
            parametros.put("codProvincia", provincia);
        }
        if (localidad != null && !localidad.equals("-1") && !localidad.isEmpty()) {
            String[] localidadsplit = localidad.split("-");
            where.add(" unidad.catLocalidad.codigoLocalidad = :localidad ");
            parametros.put("localidad", new Long(localidadsplit[0]));
            if (provincia != null && provincia != -1) {
                where.add(" unidad.catLocalidad.provincia.codigoProvincia = :provincia ");
                parametros.put("provincia", provincia);
                if (localidadsplit[1] != null && localidadsplit[1].length() > 0) {
                    where.add(" unidad.catLocalidad.entidadGeografica.codigoEntidadGeografica = :entidadGeografica ");
                    parametros.put("entidadGeografica", localidadsplit[1]);
                }
            }
        }
        //Solo se buscaran vigentes cuando lo indiquen
        if (vigentes) {
            where.add(" unidad.estado.codigoEstadoEntidad =:vigente ");
            parametros.put("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
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
            query.append("order by unidad.estado.codigoEstadoEntidad desc, unidad.denominacion asc ");
            q = em.createQuery(query.toString());

            for (Map.Entry<String, Object> param : parametros.entrySet()) {
                q.setParameter(param.getKey(), param.getValue());
            }

        } else {
            query.append("order by unidad.estado.codigoEstadoEntidad desc, unidad.denominacion asc ");
            q = em.createQuery(query.toString());
        }

        // Generamos el Nodo
        List<Nodo> unidades = NodoUtils.getNodoListExtendido(q.getResultList());

        //Si nos indican la variable conOficinas a true es que interesa devolver solo aquellos organismos
        // que tienen oficinas en las que registrar
        if (conOficinas) {
            Set<Nodo> unidadesConOficinas = new HashSet<Nodo>();
            for (Nodo unidad : unidades) {
                if (tieneOficinasOrganismo(unidad.getCodigo())) {
                    unidadesConOficinas.add(unidad);
                }
            }
            unidades = new ArrayList<Nodo>(unidadesConOficinas);
        }

        // Actualizamos las unidades obtenidas y marcamos si tienen oficinasSIR
        for (Nodo unidad2 : unidades) {
            if (obtenerOficinasSIRUnidad(unidad2.getCodigo()).size() > 0) {
                unidad2.setTieneOficinaSir(true);
            }
        }
        return unidades;

    }

    /**
     * Método que permite buscar oficinas según el conjunto de criterios indicados en los parámetros.
     *
     * @param codigo                    código de la oficina
     * @param denominacion              denominación de la oficina
     * @param codigoNivelAdministracion nivel de administración de la oficina
     * @param codComunidad              comunidad a la que pertenece la oficina
     * @return Nodo conjunto de datos a mostrar de la oficina
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Nodo> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, Long provincia, String localidad, boolean oficinasSir, boolean vigentes) throws Exception {
        Query q;
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<String> where = new ArrayList<String>();

        StringBuilder query = new StringBuilder("Select oficina.codigo, oficina.denominacion, oficina.estado.codigoEstadoEntidad, unidadRaiz.codigo, unidadRaiz.denominacion, oficina.codUoResponsable.codigo, oficina.codUoResponsable.denominacion, ofilocalidad.descripcionLocalidad from Oficina as oficina left outer join oficina.codUoResponsable.codUnidadRaiz as unidadRaiz left outer join oficina.localidad as ofilocalidad ");

        // Parametros de busqueda

        if (codigo != null && codigo.length() > 0) {
            where.add(DataBaseUtils.like("oficina.codigo ", "codigo", parametros, codigo));
        }
        if (denominacion != null && denominacion.length() > 0) {
            where.add(DataBaseUtils.like("oficina.denominacion ", "denominacion", parametros, denominacion));
        }
        if (codComunidad != null && codComunidad != -1) {
            where.add("oficina.codComunidad.codigoComunidad=:codComunidad ");
            parametros.put("codComunidad", codComunidad);
        }
        if (codigoNivelAdministracion != null && codigoNivelAdministracion != -1) {
            where.add("oficina.nivelAdministracion.codigoNivelAdministracion=:codigoNivelAdministracion ");
            parametros.put("codigoNivelAdministracion", codigoNivelAdministracion);
        }
        if (provincia != null && provincia != -1) {
            where.add("(oficina.codUoResponsable.codAmbProvincia.codigoProvincia=:codigoProvincia or oficina.localidad.provincia.codigoProvincia =:codigoProvincia) ");
            parametros.put("codigoProvincia", provincia);
        }

        if (localidad != null && !localidad.equals("-1") && !localidad.isEmpty()) {
            //Separamos el codigo de la localidad del codigo de la entidadGeografica
            String[] localidadsplit = localidad.split("-");
            where.add(" oficina.localidad.codigoLocalidad = :localidad ");
            parametros.put("localidad", new Long(localidadsplit[0]));
            if (provincia != null && provincia != -1) {
                where.add(" oficina.localidad.provincia.codigoProvincia = :provincia ");
                parametros.put("provincia", provincia);
                if (localidadsplit[1] != null && localidadsplit[1].length() > 0) {
                    where.add(" oficina.localidad.entidadGeografica.codigoEntidadGeografica = :entidadGeografica ");
                    parametros.put("entidadGeografica", localidadsplit[1]);
                }
            }
        }
        //Solo se buscaran con estado vigente cuando lo indiquen
        if (vigentes) {
            where.add(" oficina.estado.codigoEstadoEntidad =:vigente ");
            parametros.put("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        }

        // buscamos aquellas que sean oficinas sir de Recepcion
        if (oficinasSir) {
            where.add(" :SERVICIO_SIR_RECEPCION in elements(oficina.servicios) ");
            //parametros.put("SERVICIO_SIR", new Servicio(Dir3caibConstantes.SERVICIO_SIR));
            parametros.put("SERVICIO_SIR_RECEPCION", new Servicio(Dir3caibConstantes.SERVICIO_SIR_RECEPCION));

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
            query.append("order by oficina.denominacion asc");
            q = em.createQuery(query.toString());

            for (Map.Entry<String, Object> param : parametros.entrySet()) {
                q.setParameter(param.getKey(), param.getValue());
            }

        } else {
            query.append("order by oficina.denominacion asc");
            q = em.createQuery(query.toString());
        }

        return NodoUtils.getNodoListExtendido(q.getResultList());

    }

    /**
     * Devuelve la denominación de la unidad especificada por codigo
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public String unidadDenominacion(String codigo) throws Exception {

        Query q = em.createQuery("select unidad.denominacion from Unidad as unidad where unidad.codigo=:codigo").setParameter("codigo", codigo);

        List<String> unidades = q.getResultList();

        if (unidades.size() > 0) {
            return unidades.get(0);
        } else {
            return "";
        }
    }

    /**
     * Devuelve la denominación de la oficina especificada por codigo
     *
     * @param codigo
     * @return
     * @throws Exception
     */

    @Override
    @SuppressWarnings(value = "unchecked")
    public String oficinaDenominacion(String codigo) throws Exception {

        Query q = em.createQuery("select oficina.denominacion from Oficina as oficina where oficina.codigo=:codigo").setParameter("codigo", codigo);

        List<String> oficinas = q.getResultList();

        if (oficinas.size() > 0) {
            return oficinas.get(0);
        } else {
            return "";
        }
    }


    /**
     * Método que busca unidades por denominación y comunidad para utilidad en HELIUM.
     *
     * @param denominacion
     * @param codComunidad
     * @return List<Nodo> listado de objetos nodo con el código,denominación, denominación de unidad raiz y denominación de unidad superior
     * @throws Exception
     */

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Nodo> busquedaDenominacionComunidad(String denominacion, Long codComunidad) throws Exception {


        Query q;
        List<String> where = new ArrayList<String>();
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder query = new StringBuilder("Select unidad.codigo, unidad.denominacion, unidad.codUnidadRaiz.denominacion, unidad.codUnidadSuperior.denominacion from Unidad as unidad ");

        //Denominación
        if (denominacion != null && denominacion.length() > 0) {
            where.add(DataBaseUtils.like("unidad.denominacion", "denominacion", parametros, denominacion));
        }
        //Comunidad Autónoma
        if (codComunidad != null && codComunidad != -1) {
            where.add(" unidad.codAmbComunidad.codigoComunidad = :codComunidad ");
            parametros.put("codComunidad", codComunidad);
        }

        //vigentes
        where.add(" unidad.estado.codigoEstadoEntidad = :codigoEstado ");
        parametros.put("codigoEstado", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);


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

            query.append("order by unidad.denominacion asc");

            q = em.createQuery(query.toString());

            for (Map.Entry<String, Object> param : parametros.entrySet()) {
                q.setParameter(param.getKey(), param.getValue());
            }

        } else {
            query.append("order by unidad.denominacion asc");
            q = em.createQuery(query.toString());
        }

        return NodoUtils.getNodoListUnidadRaizUnidadSuperior(q.getResultList());
    }


    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Oficina> obtenerOficinasSIRUnidad(String codigoUnidad) throws Exception {

        Query q = em.createQuery("select relacionSirOfi.oficina from RelacionSirOfi as relacionSirOfi where relacionSirOfi.unidad.codigo =:codigoUnidad " +
                "and :SERVICIO_SIR_RECEPCION in elements(relacionSirOfi.oficina.servicios) " +
                "and relacionSirOfi.estado.codigoEstadoEntidad='V' ");

        q.setParameter("codigoUnidad", codigoUnidad);
        //q.setParameter("SERVICIO_SIR", new Servicio(Dir3caibConstantes.SERVICIO_SIR));
        q.setParameter("SERVICIO_SIR_RECEPCION", new Servicio(Dir3caibConstantes.SERVICIO_SIR_RECEPCION));


        return q.getResultList() != null ? q.getResultList() : new ArrayList<Oficina>();

    }

    /**
     * Método que obtiene las localidades en funcion de una provincia y de una entidad geografica
     *
     * @param codigoProvincia
     * @param codigoEntidadGeografica
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CodigoValor> getLocalidadByProvinciaEntidadGeografica(Long codigoProvincia, String codigoEntidadGeografica) throws Exception {


        Query q;

        q = em.createQuery("Select catLocalidad.codigoLocalidad, catLocalidad.descripcionLocalidad from CatLocalidad as catLocalidad "
                + " left outer join catLocalidad.provincia as provincia "
                + " left outer join catLocalidad.entidadGeografica as entidadGeografica " +
                " where provincia.codigoProvincia =:codigoProvincia and entidadGeografica.codigoEntidadGeografica=:codigoEntidadGeografica ");

        q.setParameter("codigoProvincia", codigoProvincia);
        q.setParameter("codigoEntidadGeografica", codigoEntidadGeografica);


        return transformarACodigoValor(q.getResultList());


    }

    /**
     * Obtiene todas las comunidades Autónomas
     *
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CodigoValor> getComunidadesAutonomas() throws Exception {
        Query q = em.createQuery("select ca.codigoComunidad, ca.descripcionComunidad from CatComunidadAutonoma as ca order by ca.descripcionComunidad");

        return transformarACodigoValor(q.getResultList());

    }

    /**
     * Obtiene todas las entidades geográficas
     *
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CodigoValor> getEntidadesGeograficas() throws Exception {
        Query q = em.createQuery("select eg.codigoEntidadGeografica, eg.descripcionEntidadGeografica from CatEntidadGeografica as eg");

        return transformarACodigoValor(q.getResultList());

    }


    /**
     * Obtiene todas las Provincias
     *
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CodigoValor> getProvincias() throws Exception {
        Query q = em.createQuery("select prov.codigoProvincia, prov.descripcionProvincia from CatProvincia as prov order by prov.descripcionProvincia");

        return transformarACodigoValor(q.getResultList());

    }


    /**
     * Obtiene todas las Provincias de una comunidad
     *
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CodigoValor> getProvinciasByComunidad(Long codComunidad) throws Exception {
        Query q = em.createQuery("Select prov.codigoProvincia, prov.descripcionProvincia from CatProvincia as prov " +
                "where prov.comunidadAutonoma.codigoComunidad =:codComunidad order by prov.codigoProvincia");

        q.setParameter("codComunidad", codComunidad);

        return transformarACodigoValor(q.getResultList());

    }


    /**
     * Obtiene todas los niveles de administración
     *
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CodigoValor> getNivelesAdministracion() throws Exception {
        Query q = em.createQuery("select na.codigoNivelAdministracion, na.descripcionNivelAdministracion from CatNivelAdministracion as na order by na.descripcionNivelAdministracion");

        return transformarACodigoValor(q.getResultList());

    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CodigoValor> getAmbitoTerritorialByAdministracion(Long nivelAdministracion) throws Exception {

        Query q = em.createQuery("Select catAmbitoTerritorial.codigoAmbito,catAmbitoTerritorial.descripcionAmbito  from CatAmbitoTerritorial as catAmbitoTerritorial " +
                "where catAmbitoTerritorial.nivelAdministracion.codigoNivelAdministracion = :nivelAdministracion order by catAmbitoTerritorial.codigoAmbito");

        q.setParameter("nivelAdministracion", nivelAdministracion);

        return transformarACodigoValor(q.getResultList());
    }


    /**
     * Método que transforma los resultados de una lista de Object[] en una lista de CodigoValor
     *
     * @param resultados
     * @return
     */
    private List<CodigoValor> transformarACodigoValor(List<Object[]> resultados) {
        List<CodigoValor> codigosValor = new ArrayList<CodigoValor>();
        for (Object[] obj : resultados) {
            CodigoValor codigoValor = new CodigoValor();
            codigoValor.setId(obj[0]);
            codigoValor.setDescripcion((String) obj[1]);
            codigosValor.add(codigoValor);
        }

        return codigosValor;

    }

    /**
     * Método que transforma los resultados de una lista de Object[] en una lista de ObjetoDirectorio
     *
     * @param resultados
     * @return
     */
    private List<ObjetoDirectorio> transformarAObjetoDirectorio(List<Object[]> resultados) {
        List<ObjetoDirectorio> objetoDirectorios = new ArrayList<ObjetoDirectorio>();
        for (Object[] obj : resultados) {
            ObjetoDirectorio objetoDirectorio = new ObjetoDirectorio();
            objetoDirectorio.setCodigo((String) obj[0]);
            objetoDirectorio.setDenominacion((String) obj[1]);
            objetoDirectorios.add(objetoDirectorio);
        }

        return objetoDirectorios;

    }

}
