/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mgonzalez
 * @author anadal
 */
@Stateless(name = "OficinaEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN", "tothom", "DIR_WS"})
public class OficinaBean extends BaseEjbJPA<Oficina, String> implements OficinaLocal {

    protected final Logger log = Logger.getLogger(getClass());
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @EJB(mappedName = "dir3caib/CatServicioEJB/local")
    private CatServicioLocal servicioEjb;

    @Override
    public Oficina getReference(String id) throws Exception {

        return em.getReference(Oficina.class, id);
    }

    @Override
    public Oficina findById(String id) throws Exception {

        return em.find(Oficina.class, id);
    }

    /**
     * Obtiene una oficina con sus contactos y sus servicios
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Oficina findFullById(String id) throws Exception {

        Oficina oficina = em.find(Oficina.class, id);

        if (oficina != null) {
//            Hibernate.initialize(oficina.getHistoricosOfi());
            Hibernate.initialize(oficina.getServicios());
            Hibernate.initialize(oficina.getContactos());
        }

        return oficina;
    }

    /**
     * Obtiene el código, denominación y estado de la oficina indicada
     * @param codigo
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public Oficina findByCodigoLigero(String codigo) throws Exception {
        Query q = em.createQuery("select oficina.codigo, oficina.denominacion, oficina.estado.codigoEstadoEntidad, oficina.codUoResponsable.codigo from Oficina as oficina where oficina.codigo=:codigo ");
        q.setParameter("codigo", codigo);

        List<Object[]> result = q.getResultList();

        if (result.size() == 1) {
            Oficina oficina = new Oficina();
            oficina.setCodigo((String) result.get(0)[0]);
            oficina.setDenominacion((String) result.get(0)[1]);
            oficina.setEstado(new CatEstadoEntidad((String)result.get(0)[2]));
            Unidad unidadResponsable = new Unidad((String) result.get(0)[3]);
            oficina.setCodUoResponsable(unidadResponsable);

            return  oficina;
        }else {
            return  null;
        }
    }

    /**
     * Obtiene una Oficina según su código y estado
     * @param codigo
     * @param estado
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public Oficina findById(String codigo, String estado) throws Exception{

        Query q = em.createQuery("Select oficina from Oficina as oficina where oficina.codigo=:codigo and oficina.estado.codigoEstadoEntidad =:estado");
        q.setParameter("codigo", codigo);
        q.setParameter("estado", estado);

        List<Oficina> oficinas = q.getResultList();

        if(oficinas.size() > 0){
            return oficinas.get(0);
        }else{
            return null;
        }

    }

    /**
     * Obtiene el codigo, la denominación, el estado, la tupla codigo-denominacion de la unidad raiz y la tupla codigo-denominacion de la oficina
     * @param id
     * @param estado
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public Nodo findOficina(String id, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.estado.descripcionEstadoEntidad, oficina.codUoResponsable.codUnidadRaiz.codigo, oficina.codUoResponsable.codUnidadRaiz.denominacion, oficina.codUoResponsable.codigo, oficina.codUoResponsable.denominacion  from Oficina as oficina where oficina.codigo=:id and oficina.estado.codigoEstadoEntidad =:estado");
        q.setParameter("id", id);
        q.setParameter("estado", estado);

        List<Object[]> oficinas = q.getResultList();

        if (oficinas.size() > 0) {
            Object[] obj = oficinas.get(0);
            return new Nodo((String) obj[0], (String) obj[1], (String) obj[2], obj[3] + " - " + obj[4], obj[5] + " - " + obj[6], "");
        } else {
            return null;
        }

    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Oficina> getAll() throws Exception {

        return em.createQuery("Select oficina from Oficina as oficina order by oficina.codigo").getResultList();
    }


    @Override
    public Long getTotal() throws Exception {
        Query q = em.createQuery("Select count(distinct oficina.codigo) from Oficina as oficina");

        return (Long) q.getSingleResult();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Oficina> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select oficina from Oficina as oficina order by oficina.codigo");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    /**
     * Elimina todos los registros de la tabla de históricos de oficinas
     * @throws Exception
     */
    public void deleteHistoricosOficina() throws Exception {

        em.createNativeQuery("delete from DIR_HISTORICOOFI").executeUpdate();
    }

    /**
     * Elimina todos los servicios de oficinas de la tabla de servicios.
     * @throws Exception
     */
    @Override
    public void deleteServiciosOficina() throws Exception {

        em.createNativeQuery("delete from DIR_SERVICIOOFI").executeUpdate();
    }

    @Override
    public void deleteServiciosOficina(String idOficina) throws Exception {

        Query q = em.createNativeQuery("delete from DIR_SERVICIOOFI where codoficina=?");
        q.setParameter(1, idOficina);

        q.executeUpdate();

    }

    @Override
    public void deleteAll() throws Exception {

        log.info("Oficinas eliminadas: " + em.createQuery("delete from Oficina ").executeUpdate());
    }


    /**
     * Realiza la búsqueda de oficinas en función de los criterios especificados
     * @param pageNumber numero de pagina, para la paginación
     * @param codigo código de la oficina
     * @param denominacion denominacion de la oficina
     * @param codigoNivelAdministracion codigo del nivel de administración
     * @param codComunidad codigo de la comunidad  a la que pertenece.
     * @param codigoProvincia codigo de la provincia a la que pertenece.
     * @param codigoEstado codigo de estado (vigente, anulado)
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, Long codigoProvincia, String codigoEstado) throws Exception {

        Query q;
        Query q2;
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<String> where = new ArrayList<String>();

        StringBuilder query = new StringBuilder("Select oficina from Oficina as oficina ");

        // Parametros de busqueda
        if (codigo != null && codigo.length() > 0) {
            where.add(DataBaseUtils.like("oficina.codigo", "codigo", parametros, codigo));
        }
        if (denominacion != null && denominacion.length() > 0) {
            where.add(DataBaseUtils.like("oficina.denominacion", "denominacion", parametros, denominacion));
        }
        if (codigoNivelAdministracion != null && codigoNivelAdministracion != -1) {
            where.add(" oficina.nivelAdministracion.codigoNivelAdministracion = :codigoNivelAdministracion ");
            parametros.put("codigoNivelAdministracion", codigoNivelAdministracion);
        }
        if (codComunidad != null && codComunidad != -1) {
            where.add(" oficina.codComunidad.codigoComunidad = :codComunidad ");
            parametros.put("codComunidad", codComunidad);
        }
        if (codigoProvincia != null && codigoProvincia != -1) {
            where.add(" oficina.localidad.provincia.codigoProvincia = :codigoProvincia ");
            parametros.put("codigoProvincia", codigoProvincia);
        }
        if (codigoEstado != null && (!"-1".equals(codigoEstado))) {
            where.add(" oficina.estado.codigoEstadoEntidad = :codigoEstado ");
            parametros.put("codigoEstado", codigoEstado);
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
            q2 = em.createQuery(query.toString().replaceAll("Select oficina from Oficina as oficina ", "Select count(oficina.codigo) from Oficina as oficina "));
            query.append("order by oficina.codigo desc");
            q = em.createQuery(query.toString());

            for (Map.Entry<String, Object> param : parametros.entrySet()) {
                q.setParameter(param.getKey(), param.getValue());
                q2.setParameter(param.getKey(), param.getValue());
            }

        } else {
            q2 = em.createQuery(query.toString().replaceAll("Select oficina from Oficina as oficina ", "Select count(oficina.codigo) from Oficina as oficina "));
            query.append("order by oficina.codigo desc");
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

        List<Nodo> nodos = NodoUtils.getNodoListOficina(q.getResultList());


        paginacion.setListado(new ArrayList<Object>(nodos));

        return paginacion;

    }

    /**
     * Método que comprueba si una oficina tiene más oficinas hijas
     *
     * @param codigo código de la oficina
     */
    @Override
    @SuppressWarnings("unchecked")
    public Boolean tieneHijos(String codigo) throws Exception {

        Query q = em.createQuery("Select oficina from Oficina as oficina where oficina.codOfiResponsable.codigo =:codigo and oficina.codigo !=:codigo order by oficina.codigo");

        q.setParameter("codigo", codigo);

        List<Oficina> hijos = q.getResultList();

        return hijos.size() > 0;
    }

    /**
     * Metodo que obtiene los hijos de primer nivel de una oficina en funcion del estado del padre.
     * Se emplea para pintar el árbol de oficinas
     *
     * @param codigo identificador de la oficina padre.
     * @param estado estado de la oficina padre.
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Nodo> hijos(String codigo, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.estado.descripcionEstadoEntidad from Oficina as oficina where oficina.codOfiResponsable.codigo =:codigo and oficina.codigo !=:codigo and oficina.estado.codigoEstadoEntidad =:estado order by oficina.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("estado", estado);

        return NodoUtils.getNodoList(q.getResultList());
    }

    /**
     * EL QUE SE EMPLEA EN LA SINCRO CON REGWEB (EL BUENO)
     * Método que devuelve las oficinas de un organismo(son todas, padres e hijos),
     * teniendo en cuenta la fecha de la ultima actualización de regweb.
     * Se emplea para la sincronizacion y actualización con regweb
     *
     * @param codigo              código de la unidad
     * @param fechaActualizacion  fecha de la ultima actualización
     * @param fechaSincronizacion fecha de la primera sincronización
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Oficina> obtenerOficinasOrganismo(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

        // En un primer paso obtenemos las oficinas en función de si es SINCRO o ACTUALIZACION
        // Obtenemos aquellas oficinas que tienen una dependencia orgánica  con el órganismo(codUoResponsable) (la que paga)
        Query q;
        if (fechaActualizacion == null) {// Es una sincronizacion, solo se mandan las vigentes
            q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad =:vigente order by oficina.codigo");
            q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        } else { //Es una actualizacion, se mandan todas las que tienen fechaactualizacion anterior a la fecha de importacion de las oficinas
            q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo " +
                    " and :fechaActualizacion < oficina.fechaImportacion " +
                    " order by oficina.codigo");
            q.setParameter("fechaActualizacion", fechaActualizacion);
        }


        q.setParameter("codigo", codigo);
        List<Oficina> oficinas = q.getResultList(); // oficinas candidatas a ser enviadas a regweb
        List<Oficina> oficinasCompletas = new ArrayList<Oficina>();
        List<Oficina> oficinasActualizadas = new ArrayList<Oficina>();


        // En este segundo paso tratamos las oficinas en funcion de si es SINCRO O ACTUALIZACION
        if (fechaActualizacion == null) { // ES SINCRONIZACION
            for (Oficina oficina : oficinas) {
                // RelacionOrganizativaOfi es lo que nosotros llamamos dependencia FUNCIONAL
                Set<RelacionOrganizativaOfi> relaciones = new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());

                Set<RelacionOrganizativaOfi> relacionesVigentes = new HashSet<RelacionOrganizativaOfi>();
                //Metemos en la lista las relaciones cuyo estado es vigente y el estado de la unidad con la que esta relacionada
                // tambien es vigente. En el caso de la sincro solo nos interesa que la relación sea vigente y la unidad
                // con la que está relacionada también.
                for (RelacionOrganizativaOfi relOrg : relaciones) {
                    if (relOrg.getEstado().getCodigoEstadoEntidad().equals(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE) &&
                            relOrg.getUnidad().getEstado().getCodigoEstadoEntidad().equals(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE)) {
                        relacionesVigentes.add(relOrg);
                    }
                }
                // Asignamos las relaciones vigentes encontradas para ser enviadas.
                oficina.setOrganizativasOfi(null);
                oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesVigentes));

                // Solo se envian las relaciones sir vigentes.
                Set<RelacionSirOfi> relacionesSir = new HashSet<RelacionSirOfi>(oficina.getSirOfi());

                Set<RelacionSirOfi> relacionesSirVigentes = new HashSet<RelacionSirOfi>();
                //Metemos en la lista las relacionesVigentes cuyo estado es vigente y el estado de la unidad con la que esta relacionada
                // tambien es vigente.
                for (RelacionSirOfi relSir : relacionesSir) {
                    if (relSir.getEstado().getCodigoEstadoEntidad().equals(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE) &&
                            relSir.getUnidad().getEstado().getCodigoEstadoEntidad().equals(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE)) {
                        relacionesSirVigentes.add(relSir);
                    }
                }
                oficina.setSirOfi(null);
                oficina.setSirOfi(new ArrayList<RelacionSirOfi>(relacionesSirVigentes));

            }
            oficinasCompletas = new ArrayList<Oficina>(oficinas);

        } else { // ES UNA ACTUALIZACION

            for (Oficina oficina : oficinas) {

                // Miramos que la oficina no esté extinguida o anulada anterior a la fecha de sincronizacion de regweb
                if (oficinaValida(oficina, fechaSincronizacion)) {

                    // Cogemos solo las relaciones organizativas que no han sido anuladas o extinguidas anterior a la fecha de sincronizacion
                    Set<RelacionOrganizativaOfi> todasRelaciones = new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());

                    Set<RelacionOrganizativaOfi> relacionesValidas = new HashSet<RelacionOrganizativaOfi>();
                    for (RelacionOrganizativaOfi relOrg : todasRelaciones) {
                        //En el caso de la actualización además hay que asegurarse que no se traen relaciones extinguidas
                        // o anuladas anterior a la fecha de sincronización.
                        if (relacionValida(relOrg, fechaSincronizacion)) {
                            relacionesValidas.add(relOrg);
                        }
                    }

                    oficina.setOrganizativasOfi(null);
                    oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesValidas));

                    // Cogemos solo las relaciones sir que no han sido anuladas o extinguidas anterior a la fecha de sincronizacion
                    Set<RelacionSirOfi> todasRelacionesSir = new HashSet<RelacionSirOfi>(oficina.getSirOfi());
                    Set<RelacionSirOfi> relacionesSirValidas = new HashSet<RelacionSirOfi>();
                    for (RelacionSirOfi relSir : todasRelacionesSir) {
                        //En el caso de la actualización además hay que asegurarse que no se traen relaciones extinguidas
                        // o anuladas anterior a la fecha de sincronización.
                        if (relacionSirValida(relSir, fechaSincronizacion)) {
                            relacionesSirValidas.add(relSir);
                        }
                    }
                    oficina.setSirOfi(null);
                    oficina.setSirOfi(new ArrayList<RelacionSirOfi>(relacionesSirValidas));

                    oficinasActualizadas.add(oficina);
                }
            }
            oficinasCompletas = new ArrayList<Oficina>(oficinasActualizadas);
        }


        return oficinasCompletas;

    }

    /**
     * Obtiene el listado de oficinas Sir de una Unidad
     * para ello consulta la relacionSirOfi y además que tengan los servicios SIR y SIR_RECEPCION y que sean vigentes.
     *
     * @param codigoUnidad
     *          Código de la unidad
     *
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Oficina> obtenerOficinasSIRUnidad(String codigoUnidad) throws Exception {

        Query q = em.createQuery("select relacionSirOfi.oficina from RelacionSirOfi as relacionSirOfi where relacionSirOfi.unidad.codigo =:codigoUnidad " +
                "and :SERVICIO_SIR_RECEPCION in elements(relacionSirOfi.oficina.servicios) " +
                "and relacionSirOfi.estado.codigoEstadoEntidad='V' ");

        q.setParameter("codigoUnidad", codigoUnidad);
        //q.setParameter("SERVICIO_SIR", new Servicio(Dir3caibConstantes.SERVICIO_SIR));
        q.setParameter("SERVICIO_SIR_RECEPCION", new CatServicio(Dir3caibConstantes.SERVICIO_SIR_RECEPCION));


        return q.getResultList();

    }


    @Override
    @SuppressWarnings(value = "unchecked")
    //TODO OPTIMIZAR, es muy lento
    public Boolean tieneOficinasSIR(String codigoUnidad) throws Exception {

        Query q = em.createQuery("select count(relacionSirOfi.oficina.id) from RelacionSirOfi as relacionSirOfi where relacionSirOfi.unidad.codigo =:codigoUnidad " +
           "and (:SERVICIO_SIR in elements(relacionSirOfi.oficina.servicios) or :SERVICIO_SIR_ENVIO in elements(relacionSirOfi.oficina.servicios) or :SERVICIO_SIR_RECEPCION in elements(relacionSirOfi.oficina.servicios)) " +
           "and relacionSirOfi.estado.codigoEstadoEntidad='V' ");

        q.setParameter("codigoUnidad", codigoUnidad);
        q.setParameter("SERVICIO_SIR", new CatServicio(Dir3caibConstantes.SERVICIO_SIR));
        q.setParameter("SERVICIO_SIR_ENVIO", new CatServicio(Dir3caibConstantes.SERVICIO_SIR_ENVIO));
        q.setParameter("SERVICIO_SIR_RECEPCION", new CatServicio(Dir3caibConstantes.SERVICIO_SIR_RECEPCION));

        Long count = (Long) q.getSingleResult();
        return count > 0;

    }

    /**
     * Nos dice si la relacion organizativa es valida para enviar en la actualización de regweb.
     * Se mira que si la unidad con la que esta relacionada su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar relaciones antiguas extinguidas o anuladas
     * anteriores a la fecha de sincornización
     *
     * @param relOrg      relacion organizativa
     * @param fechaSincro fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
    private boolean relacionValida(RelacionOrganizativaOfi relOrg, Date fechaSincro) throws Exception {
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sSincro = "";
        if (fechaSincro != null) {
            sSincro = fechaFormat.format(fechaSincro);
        }
        // Si tiene fecha de extinción
        if (relOrg.getUnidad().getFechaExtincion() != null) {
            String sExtincion = fechaFormat.format(relOrg.getUnidad().getFechaExtincion());
            // Si la fecha de extinción es posterior o igual a la fecha sincro, se debe enviar a regweb
            if (relOrg.getUnidad().getFechaExtincion().after(fechaSincro) || sExtincion.equals(sSincro)) {
                return true;
            }
        } else {
            // Si tiene fecha de anulación
            if (relOrg.getUnidad().getFechaAnulacion() != null) {
                String sAnulacion = fechaFormat.format(relOrg.getUnidad().getFechaAnulacion());
                // Si la fecha de anulación es posterior o igual a la fecha sincronizacion se debe enviar a regweb
                if (relOrg.getUnidad().getFechaAnulacion().after(fechaSincro) || sAnulacion.equals(sSincro)) {
                    return true;
                }
            } else { // Si no tiene ni fecha de extincion ni de anulación, tambien se debe enviar a regweb
                return true;
            }
        }
        return false;
    }

    /**
     * Nos dice si la relacion sir es valida para enviar en la actualización de regweb.
     * Se mira que si la unidad con la que esta relacionada su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar relaciones antiguas extinguidas o anuladas
     * anteriores a la fecha de sincronización
     *
     * @param relSir      relacion sir
     * @param fechaSincro fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
    private boolean relacionSirValida(RelacionSirOfi relSir, Date fechaSincro) throws Exception {
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sSincro = "";
        if (fechaSincro != null) {
            sSincro = fechaFormat.format(fechaSincro);
        }
        // Si tiene fecha de extinción
        if (relSir.getUnidad().getFechaExtincion() != null) {
            String sExtincion = fechaFormat.format(relSir.getUnidad().getFechaExtincion());
            // Si la fecha de extinción es posterior o igual a la fecha sincro, se debe enviar a regweb
            if (relSir.getUnidad().getFechaExtincion().after(fechaSincro) || sExtincion.equals(sSincro)) {
                return true;
            }
        } else {
            // Si tiene fecha de anulación
            if (relSir.getUnidad().getFechaAnulacion() != null) {
                String sAnulacion = fechaFormat.format(relSir.getUnidad().getFechaAnulacion());
                // Si la fecha de anulación es posterior o igual a la fecha sincronizacion se debe enviar a regweb
                if (relSir.getUnidad().getFechaAnulacion().after(fechaSincro) || sAnulacion.equals(sSincro)) {
                    return true;
                }
            } else {// Si no tiene ni fecha de extincion ni de anulación, tambien se debe enviar a regweb
                return true;
            }
        }
        return false;
    }

    /**
     * Determina si una oficina es SIR
     *
     * @param codigoOficina
     * @return
     * @throws Exception
     */
    /*private boolean esOficinaSir(String codigoOficina) throws Exception {

        *//*Query q = em.createQuery("select relacionSirOfi.oficina from RelacionSirOfi as relacionSirOfi where relacionSirOfi.oficina.codigo =:codigoOficina " +
           "and :SERVICIO_SIR_RECEPCION in elements(relacionSirOfi.oficina.servicios) " +
           "and relacionSirOfi.estado.codigoEstadoEntidad='V' ");*//*

        Query q = em.createQuery("select oficina.id from Oficina as oficina where oficina.codigo =:codigoOficina " +
                "and :SIR in elements(oficina.servicios)  " +
                "and :SIR_RECEPCION in elements(oficina.servicios)  " +
                "and :SIR_ENVIO in elements(oficina.servicios)  " +
                "and estado.codigoEstadoEntidad='V' ");

        q.setParameter("codigoOficina", codigoOficina);
        q.setParameter("SIR", servicioEjb.findById(Dir3caibConstantes.SERVICIO_SIR));
        q.setParameter("SIR_RECEPCION", servicioEjb.findById(Dir3caibConstantes.SERVICIO_SIR_RECEPCION));
        q.setParameter("SIR_ENVIO", new servicioEjb.findById(Dir3caibConstantes.SERVICIO_SIR_ENVIO));;


        return q.getResultList() != null && q.getResultList().size() > 0;

    }*/
    @Override
    public Boolean isSIRCompleto(String codigoOficina) throws Exception {

        Query q = em.createQuery("select oficina.id from Oficina as oficina where oficina.codigo =:codigoOficina " +
           "and :SIR in elements(oficina.servicios)  " +
           "and :SIR_RECEPCION in elements(oficina.servicios)  " +
           "and :SIR_ENVIO in elements(oficina.servicios)  " +
           "and estado.codigoEstadoEntidad='V' ");

        q.setParameter("codigoOficina", codigoOficina);
        q.setParameter("SIR", servicioEjb.findById(Dir3caibConstantes.SERVICIO_SIR));
        q.setParameter("SIR_RECEPCION", servicioEjb.findById(Dir3caibConstantes.SERVICIO_SIR_RECEPCION));
        q.setParameter("SIR_ENVIO", servicioEjb.findById(Dir3caibConstantes.SERVICIO_SIR_ENVIO));
        ;


        return q.getResultList() != null && q.getResultList().size() > 0;
    }

    /**
     * Método que obtiene todos los códigos de las oficinas que hay en dir3caib.
     * @return
     * @throws Exception
     */
    @Override
    public List<String> getAllCodigos() throws Exception {

        Query q = em.createQuery("Select oficina.codigo from Oficina as oficina order by oficina.codigo");

        return q.getResultList();
    }


    /**
     * Método que mira si la oficina,  su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar relaciones antiguas extinguidas o anuladas
     * anterior a la fecha de sincronización con regweb3.
     *
     * @param oficina     oficina
     * @param fechaSincro fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
    private boolean oficinaValida(Oficina oficina, Date fechaSincro) throws Exception {
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sSincro = "";
        if (fechaSincro != null) {
            sSincro = fechaFormat.format(fechaSincro);
        }
        if (oficina.getFechaExtincion() != null) {
            String sExtincion = fechaFormat.format(oficina.getFechaExtincion());
            if (oficina.getFechaExtincion().after(fechaSincro) || sExtincion.equals(sSincro)) {
                return true;
            }
        } else {
            if (oficina.getFechaAnulacion() != null) {
                String sAnulacion = fechaFormat.format(oficina.getFechaAnulacion());
                if (oficina.getFechaAnulacion().after(fechaSincro) || sAnulacion.equals(sSincro)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene las oficinas que dependen directamente de la unidad, es decir cuya unidad responsable es la del codigo
     * indicado y del estado indicado por estado. Se emplea para pintar el árbol de unidades con sus oficinas
     *
     * @param codigo codigo de la unidad
     * @param estado estado de las oficinas
     *
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Nodo> oficinasDependientes(String codigo, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.estado.descripcionEstadoEntidad from Oficina as oficina where " +
                "oficina.codUoResponsable.codigo=:codigo and oficina.estado.codigoEstadoEntidad=:estado " +
                "and oficina.codOfiResponsable.codigo is null order by oficina.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("estado", estado);

        return NodoUtils.getNodoList(q.getResultList());
    }

    /**
     * Obtiene las oficinas auxiliares de un Oficina padre, es decir aquellas que dependen de la oficina del código
     * especificado y del estado especificado.Se emplea para pintar el árbol de unidades con sus oficinas
     *
     * @param codigo código de la oficina
     * @param estado estado de la oficina
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Nodo> oficinasAuxiliares(String codigo, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.estado.descripcionEstadoEntidad from Oficina as oficina where " +
                " oficina.codOfiResponsable.codigo=:codigo and oficina.estado.codigoEstadoEntidad =:estado " +
                " order by oficina.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("estado", estado);
        return NodoUtils.getNodoList(q.getResultList());
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public void crearServicioOficina(String codigoOficina, Long codigoServicio) throws Exception{

        Query q = em.createNativeQuery("insert into dir_servicioofi (codoficina, codservicio) values (?,?) ");
        q.setParameter(1, codigoOficina);
        q.setParameter(2, codigoServicio);

        q.executeUpdate();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public void crearHistoricoOficina(String codigoAnterior, String codigoUltima) throws Exception{

        Query q = em.createNativeQuery("insert into dir_historicoofi (codanterior, codultima) values (?,?) ");
        q.setParameter(1, codigoAnterior);
        q.setParameter(2, codigoUltima);

        q.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean existeServicioOficina(String codigoOficina, Long codigoServicio) throws Exception {

        Query q = em.createNativeQuery("select * from dir_servicioofi where codoficina = ? and codservicio = ? ");
        q.setParameter(1, codigoOficina);
        q.setParameter(2, codigoServicio);

        List<Object> servicios = q.getResultList();

        return servicios.size() > 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean existeHistoricoOficina(String codigoAnterior, String codigoUltima) throws Exception {

        Query q = em.createNativeQuery("select * from dir_historicoofi where codigoAnterior = ? and codigoUltima = ? ");
        q.setParameter(1, codigoAnterior);
        q.setParameter(2, codigoUltima);

        List<Object> historicos = q.getResultList();

        return historicos.size() > 0;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public void eliminarHistoricosOficina(String idOficina) throws Exception {

        Query q =  em.createNativeQuery("delete from dir_historicoofi where codultima=?");
        q.setParameter(1, idOficina);

        q.executeUpdate();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Oficina> responsableByUnidadEstado(String codigoUnidadResponsable, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.codUoResponsable.codigo from Oficina as oficina where " +
           " oficina.codOfiResponsable is null and oficina.codUoResponsable.codUnidadRaiz.codigo =:codigoUnidadResponsable and oficina.estado.codigoEstadoEntidad =:estado " +
           " order by oficina.codigo");

        q.setParameter("codigoUnidadResponsable", codigoUnidadResponsable);
        q.setParameter("estado", estado);

        List<Object[]> result = q.getResultList();
        List<Oficina> oficinas = new ArrayList<Oficina>();

        for (Object[] object : result) {

            // Query q2 = em.createNativeQuery("select codservicio from DIR_SERVICIOOFI where codoficina=?");
            Query q2 = em.createQuery("select servicioOfi.servicio from ServicioOfi as servicioOfi where servicioOfi.oficina.codigo=:codOficina");
            // q2.setParameter(1, object[0]);
            q2.setParameter("codOficina", object[0]);

            List<Object> result2 = q2.getResultList();

            Set<ServicioOfi> servicios = new HashSet<ServicioOfi>();
            for (Object obj : result2) {
                //ServicioOfi servicioOfi = new ServicioOfi((Long) obj);
                ServicioOfi servicioOfi = new ServicioOfi((CatServicio) obj);
                servicios.add(servicioOfi);
            }

            Oficina oficina = new Oficina((String) object[0], (String) object[1], (String) object[2],null, servicios);

            oficinas.add(oficina);
        }



        return oficinas;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Oficina> dependienteByUnidadEstado(String codigoUnidadResponsable, String estado) throws Exception {

        Query q = em.createQuery("Select  oficina.codigo, oficina.denominacion, oficina.codUoResponsable.codigo, oficina.codOfiResponsable.codigo from Oficina as oficina   where " +
                "oficina.codUoResponsable.codUnidadRaiz.codigo =:codigoUnidadResponsable and oficina.estado.codigoEstadoEntidad =:estado and " +
                "oficina.codOfiResponsable is not null order by oficina.codigo");



        q.setParameter("codigoUnidadResponsable", codigoUnidadResponsable);
        q.setParameter("estado", estado);

        List<Object[]> result = q.getResultList();
        List<Oficina> oficinas = new ArrayList<Oficina>();

        for (Object[] object : result) {

           // Query q2 = em.createNativeQuery("select codservicio from DIR_SERVICIOOFI where codoficina=?");
            Query q2 = em.createQuery("select servicioOfi.servicio from ServicioOfi as servicioOfi where servicioOfi.oficina.codigo=:codOficina");
           // q2.setParameter(1, object[0]);
            q2.setParameter("codOficina", object[0]);

            List<Object> result2 = q2.getResultList();

            Set<ServicioOfi> servicios = new HashSet<ServicioOfi>();
            for (Object obj : result2) {
                //ServicioOfi servicioOfi = new ServicioOfi((Long) obj);
                ServicioOfi servicioOfi = new ServicioOfi((CatServicio) obj);
                servicios.add(servicioOfi);
            }


            Oficina oficina = new Oficina((String) object[0], (String) object[1], (String) object[2], (String) object[3], null);


            oficinas.add(oficina);
        }


        return oficinas;

    }

    /**
     * Obtiene las Oficinas que registran a una Unidad
     *
     * @param codigoUnidad
     * @return
     * @throws Exception
     */
    public List<Oficina> obtenerOficinasRegistran(String codigoUnidad) throws Exception {

        Query q = em.createQuery("Select oficina from Oficina as oficina " +
                "where oficina.codUoResponsable.codigo =:codigoUnidad and oficina.estado.codigoEstadoEntidad = 'V' and " +
                "oficina.codOfiResponsable is null order by oficina.codigo");

        q.setParameter("codigoUnidad", codigoUnidad);

        return q.getResultList();
    }
}

