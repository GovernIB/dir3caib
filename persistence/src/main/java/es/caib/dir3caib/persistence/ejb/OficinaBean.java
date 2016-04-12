/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;
import es.caib.dir3caib.persistence.utils.DataBaseUtils;
import es.caib.dir3caib.persistence.utils.Paginacion;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author mgonzalez
 * @author anadal
 */
@Stateless(name = "OficinaEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class OficinaBean extends BaseEjbJPA<Oficina, String> implements OficinaLocal {

    protected final Logger log = Logger.getLogger(getClass());
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @PersistenceContext
    private EntityManager em;  

    @Override
    public Oficina findById(String id) throws Exception {

        return em.find(Oficina.class, id);
    }
    
    
    public Oficina findFullById(String id) throws Exception {

        Oficina oficina = em.find(Oficina.class, id);
        Hibernate.initialize(oficina.getHistoricosOfi());
        Hibernate.initialize(oficina.getServicios());
        return oficina;
    }


    public ObjetoBasico findReduceOficina(String id, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.estado.descripcionEstadoEntidad from Oficina as oficina where oficina.codigo=:id and oficina.estado.descripcionEstadoEntidad =:estado");
        q.setParameter("id", id);
        q.setParameter("estado", estado);

        Object[] obj = (Object[]) q.getSingleResult();

        ObjetoBasico objetoBasico = new ObjetoBasico((String) obj[0], (String) obj[1], (String) obj[2], "", "", "");

        return objetoBasico;

    }

    public ObjetoBasico findOficina(String id, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.estado.descripcionEstadoEntidad, oficina.codUoResponsable.codUnidadRaiz.codigo, oficina.codUoResponsable.codUnidadRaiz.denominacion, oficina.codUoResponsable.codigo, oficina.codUoResponsable.denominacion  from Oficina as oficina where oficina.codigo=:id and oficina.estado.descripcionEstadoEntidad =:estado");
        q.setParameter("id", id);
        q.setParameter("estado", estado);

        Object[] obj = (Object[]) q.getSingleResult();

        ObjetoBasico objetoBasico = new ObjetoBasico((String) obj[0], (String) obj[1], (String) obj[2], obj[3] + " - " + obj[4], obj[5] + " - " + obj[6], "");

        return objetoBasico;

    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Oficina> getAll() throws Exception {
        
        return  em.createQuery("Select oficina from Oficina as oficina order by oficina.codigo").getResultList();
    }
    

    @Override
    public Long getTotal() throws Exception {
        Query q = em.createQuery("Select count(distinct oficina.codigo) from Oficina as oficina");

        return (Long) q.getSingleResult();
        
    }

    @Override
    public List<Oficina> getPagination(int inicio) throws Exception {
      
        Query q = em.createQuery("Select oficina from Oficina as oficina order by oficina.codigo");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteHistoricosOficina() throws Exception {
       
        em.createNativeQuery("delete from DIR_HISTORICOOFI").executeUpdate();
    }
     
    public void deleteServiciosOficina() throws Exception {
       
        em.createNativeQuery("delete from DIR_SERVICIOOFI").executeUpdate();
    }
    
    public void deleteAll() throws Exception {
             
        em.createQuery("delete from Oficina ").executeUpdate();
    }

    /**
     * Realiza la búsqueda de unidades en función de los criterios especificados
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
    public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, Long codigoProvincia, String codigoEstado) throws Exception {

        Query q;
        Query q2;
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<String> where = new ArrayList<String>();

        StringBuffer query = new StringBuffer("Select oficina from Oficina as oficina ");

        // Parametros de busqueda
        if(codigo!= null && codigo.length() > 0){where.add(DataBaseUtils.like("oficina.codigo", "codigo", parametros, codigo));}
        if(denominacion!= null && denominacion.length() > 0){where.add(DataBaseUtils.like("oficina.denominacion", "denominacion", parametros, denominacion));}
        if(codigoNivelAdministracion!= null && codigoNivelAdministracion != -1){where.add(" oficina.nivelAdministracion.codigoNivelAdministracion = :codigoNivelAdministracion "); parametros.put("codigoNivelAdministracion",codigoNivelAdministracion);}
        if(codComunidad!= null && codComunidad != -1){where.add(" oficina.codComunidad.codigoComunidad = :codComunidad "); parametros.put("codComunidad",codComunidad);}
        if(codigoProvincia!= null && codigoProvincia != -1){where.add(" oficina.localidad.provincia.codigoProvincia = :codigoProvincia "); parametros.put("codigoProvincia",codigoProvincia);}
        if(codigoEstado!= null &&(!"-1".equals(codigoEstado))){where.add(" oficina.estado.codigoEstadoEntidad = :codigoEstado "); parametros.put("codigoEstado",codigoEstado);}


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

        }else{
            q2 = em.createQuery(query.toString().replaceAll("Select oficina from Oficina as oficina ", "Select count(oficina.codigo) from Oficina as oficina "));
            query.append("order by oficina.codigo desc");
            q = em.createQuery(query.toString());
        }

        Paginacion paginacion = null;

        if(pageNumber != null){ // Comprobamos si es una busqueda paginada o no
            Long total = (Long)q2.getSingleResult();
            paginacion = new Paginacion(total.intValue(), pageNumber);
            int inicio = (pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION;
            q.setFirstResult(inicio);
            q.setMaxResults(RESULTADOS_PAGINACION);
        }else{
            paginacion = new Paginacion(0, 0);
        }
        paginacion.setListado(q.getResultList());

        return paginacion;

    }

    @Override
    public Boolean tieneHijos(String codigo) throws Exception{

        Query q = em.createQuery("Select oficina from Oficina as oficina where oficina.codOfiResponsable.codigo =:codigo and oficina.codigo !=:codigo order by oficina.codigo");

        q.setParameter("codigo",codigo);

        List<Oficina> hijos = q.getResultList();

        return hijos.size() > 0;
    }


    @Override
    public List<ObjetoBasico> hijos(String codigo, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.estado.descripcionEstadoEntidad from Oficina as oficina where oficina.codOfiResponsable.codigo =:codigo and oficina.codigo !=:codigo and oficina.estado.descripcionEstadoEntidad =:estado order by oficina.codigo");

        q.setParameter("codigo",codigo);
        q.setParameter("estado",estado);

        return getObjetoBasicoList(q.getResultList());
    }

    /**
     * EL QUE SE EMPLEA EN LA SINCRO CON REGWEB (EL BUENO)
     * Método que devuelve las oficinas de un organismo(son todas, padres e hijos),
     * teniendo en cuenta la fecha de la ultima actualización de regweb.
     * Se emplea para la sincronizacion y actualización con regweb
     * @param codigo código de la unidad
     * @param fechaActualizacion   fecha de la ultima actualización
     * @param fechaSincronizacion  fecha de la primera sincronización
     * @return
     * @throws Exception
     */
    @Override
    public List<Oficina> obtenerOficinasOrganismo(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception{

        // En un primer paso obtenemos las oficinas en función de si es SINCRO o ACTUALIZACION
        Query q;
        if(fechaActualizacion == null){// Es una sincronizacion, solo se mandan las vigentes
          q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad =:vigente order by oficina.codigo");
          q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        } else { //Es una actualizacion, se mandan todas las que tienen fechaactualizacion anterior a la fecha de importacion de las oficinas
            q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo " +
                    " and :fechaActualizacion < oficina.fechaImportacion " +
                    " order by oficina.codigo");
            q.setParameter("fechaActualizacion", fechaActualizacion);
        }


        q.setParameter("codigo", codigo);
        List<Oficina> oficinas = q.getResultList();
        List<Oficina> oficinasCompletas = new ArrayList<Oficina>();
        List<Oficina> oficinasActualizadas = new ArrayList<Oficina>();


        // En este segundo paso tratamos las oficinas en funcion de si es SINCRO O ACTUALIZACION
        if(fechaActualizacion==null){ // ES SINCRONIZACION
          for(Oficina oficina: oficinas){
              // Solo se envian las relaciones organizativas vigentes.
              Set<RelacionOrganizativaOfi> relaciones =new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());

              Set<RelacionOrganizativaOfi> relacionesVigentes= new HashSet<RelacionOrganizativaOfi>();
              //Metemos en la lista las relacionesVigentes
              for(RelacionOrganizativaOfi relOrg: relaciones){
                  if (relOrg.getEstado().getCodigoEstadoEntidad().equals(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE) &&
                          relOrg.getUnidad().getEstado().getCodigoEstadoEntidad().equals(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE)) {
                  relacionesVigentes.add(relOrg);
                }
              }
              oficina.setOrganizativasOfi(null);
              oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesVigentes));

              // Solo se envian las relaciones sir vigentes.
              Set<RelacionSirOfi> relacionesSir =new HashSet<RelacionSirOfi>(oficina.getSirOfi());

              Set<RelacionSirOfi> relacionesSirVigentes= new HashSet<RelacionSirOfi>();
              //Metemos en la lista las relacionesVigentes
              for(RelacionSirOfi relSir : relacionesSir){
                  if (relSir.getEstado().getCodigoEstadoEntidad().equals(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE) &&
                          relSir.getUnidad().getEstado().getCodigoEstadoEntidad().equals(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE)) {
                      relacionesSirVigentes.add(relSir);
                  }
              }
              oficina.setSirOfi(null);
              oficina.setSirOfi(new ArrayList<RelacionSirOfi>(relacionesSirVigentes));

             }
             oficinasCompletas = new ArrayList<Oficina>(oficinas);

        }else{ // ES UNA ACTUALIZACION
          // Si hay fecha de actualización y es anterior o igual a la fecha de importación se debe
          // incluir en la lista de actualizadas

          for(Oficina oficina: oficinas){

              // Miramos que la oficina no esté extinguida o anulada anterior a la fecha de sincronizacion de regweb
              if (oficinaValida(oficina, fechaSincronizacion)) {

                     // Cogemos solo las relaciones organizativas posteriores a la fecha de sincronizacion
                     Set<RelacionOrganizativaOfi> todasRelaciones = new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());
                  log.info("ORGANIZATIVAS OFI: " + todasRelaciones.size());
                     Set<RelacionOrganizativaOfi> relacionesValidas= new HashSet<RelacionOrganizativaOfi>();
                     for(RelacionOrganizativaOfi relOrg: todasRelaciones){
                         if(relacionValida(relOrg, fechaSincronizacion)){
                             relacionesValidas.add(relOrg);
                         }
                     }
                  log.info("ORGANIZATIVAS VALIDAS: " + relacionesValidas.size());
                     oficina.setOrganizativasOfi(null);
                     oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesValidas));

                     // Cogemos solo las relaciones sir posteriores a la fecha de sincronizacion
                     Set<RelacionSirOfi> todasRelacionesSir = new HashSet<RelacionSirOfi>(oficina.getSirOfi());
                     Set<RelacionSirOfi> relacionesSirValidas= new HashSet<RelacionSirOfi>();
                     for(RelacionSirOfi relSir : todasRelacionesSir){
                         if(relacionSirValida(relSir, fechaSincronizacion)){
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

        log.info("DIR3CAIB OFICINAS ENVIADAS DE " + codigo + ": " + oficinasCompletas.size());

        return oficinasCompletas;

    }


    //TODO falta mirar los servicios de las oficinas para ver si estan integradas en sir de envio o recepción o ambos
    public List<Oficina> obtenerOficinasSIRUnidad(String codigo) throws Exception {

        Query q = em.createQuery("select oficina from RelacionSirOfi as relacionSirOfi, Oficina as oficina where " +
                "relacionSirOfi.unidad.id = :codigoUnidad and relacionSirOfi.estado.codigoEstadoEntidad='V' and relacionSirOfi.oficina.id = oficina.id ");

         q.setParameter("codigoUnidad", codigo);

         return q.getResultList();

    }

    /**
     * Nos dice si la relacion organizativa es valida para enviar en la actualización de regweb.
     * Se mira que si la unidad con la que esta relacionada su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar relaciones antiguas extinguidas o anuladas
     * @param relOrg    relacion organizativa
     * @param fechaSincro  fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
      public boolean relacionValida(RelacionOrganizativaOfi relOrg, Date fechaSincro) throws Exception {
           SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
           String sSincro=new String();
           if(fechaSincro!= null) {
              sSincro = fechaFormat.format(fechaSincro);
           }
           if(relOrg.getUnidad().getFechaExtincion() != null){
                String sExtincion = fechaFormat.format(relOrg.getUnidad().getFechaExtincion());
                if(relOrg.getUnidad().getFechaExtincion().after(fechaSincro) ||  sExtincion.equals(sSincro)){
                  return true;
                }
           }else{
                if(relOrg.getUnidad().getFechaAnulacion() != null){
                  String sAnulacion = fechaFormat.format(relOrg.getUnidad().getFechaAnulacion());
                  if(relOrg.getUnidad().getFechaAnulacion().after(fechaSincro) || sAnulacion.equals(sSincro)) {
                    return true;
                  }
                }else {
                   return true;
                }
           }
           return false;
      }

    /**
     * Nos dice si la relacion sir es valida para enviar en la actualización de regweb.
     * Se mira que si la unidad con la que esta relacionada su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar relaciones antiguas extinguidas o anuladas
     * @param relSir    relacion sir
     * @param fechaSincro  fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
    public boolean relacionSirValida(RelacionSirOfi relSir, Date fechaSincro) throws Exception {
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sSincro=new String();
        if(fechaSincro!= null) {
            sSincro = fechaFormat.format(fechaSincro);
        }
        if(relSir.getUnidad().getFechaExtincion() != null){
            String sExtincion = fechaFormat.format(relSir.getUnidad().getFechaExtincion());
            if(relSir.getUnidad().getFechaExtincion().after(fechaSincro) || sExtincion.equals(sSincro)){
                return true;
            }
        }else{
            if(relSir.getUnidad().getFechaAnulacion() != null){
                String sAnulacion = fechaFormat.format(relSir.getUnidad().getFechaAnulacion());
                if(relSir.getUnidad().getFechaAnulacion().after(fechaSincro) ||  sAnulacion.equals(sSincro)) {
                    return true;
                }
            }else {
                return true;
            }
        }
        return false;
    }


     @Override
     public Boolean tieneOficinasArbol(String codigo) throws Exception {

        Query q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad=:vigente order by oficina.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        List<Oficina> oficinas =  q.getResultList();
         log.info("OFICINAS"+ oficinas.size()+ " DE "+codigo);
        if (oficinas.size() > 0) {
          return true;
        }else{
          q = em.createQuery("Select relorg from RelacionOrganizativaOfi as relorg where relorg.unidad.codigo=:codigo and relorg.estado.codigoEstadoEntidad=:vigente order by relorg.id ");

          q.setParameter("codigo", codigo);
          q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
          List<RelacionOrganizativaOfi> relorg= q.getResultList();
          if(relorg.size() > 0){
              return true;
          }else{// no tiene oficinas, miramos sus hijos
              log.info("Entramos aquí");
              Query q2 = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:estado order by unidad.codigo");

              q2.setParameter("codigo",codigo);
              q2.setParameter("estado",Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
              List<Unidad> hijos= q2.getResultList();
              log.info("HIJOS  "+ hijos.size());
              for(Unidad hijo:hijos){
                  boolean tiene= tieneOficinasArbol(hijo.getCodigo());
                  if(tiene) {return true;}
                  break;
              }
          }
        }
        return false;
     }


      
      
     @Override
     public List<String> getAllCodigos() throws Exception {
        
          Query q = em.createQuery("Select oficina.codigo from Oficina as oficina order by oficina.codigo");
         
          return q.getResultList();
     }
    
     /**
      * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
      * @param result
      * @return
      * @throws Exception
      */
      private List<ObjetoBasico> getObjetoBasicoList(List<Object[]> result) throws Exception{

          List<ObjetoBasico> oficinasReducidas = new ArrayList<ObjetoBasico>();

          for (Object[] object : result){
              ObjetoBasico objetoBasico = new ObjetoBasico((String) object[0], (String) object[1], "", "", "", "");

              oficinasReducidas.add(objetoBasico);
          }

          return  oficinasReducidas;
      }

    /**
     *
     * Se mira que si la oficina,  su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar relaciones antiguas extinguidas o anuladas
     * @param oficina  oficina
     * @param fechaSincro  fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
    public boolean oficinaValida(Oficina oficina, Date fechaSincro) throws Exception {
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sSincro=new String();
        if(fechaSincro!= null) {
            sSincro = fechaFormat.format(fechaSincro);
        }
        if(oficina.getFechaExtincion() != null){
            String sExtincion = fechaFormat.format(oficina.getFechaExtincion());
            if(oficina.getFechaExtincion().after(fechaSincro) || sExtincion.equals(sSincro)){
                return true;
            }
        }else{
            if(oficina.getFechaAnulacion() != null){
                String sAnulacion = fechaFormat.format(oficina.getFechaAnulacion());
                if(oficina.getFechaAnulacion().after(fechaSincro) || sAnulacion.equals(sSincro)) {
                    return true;
                }
            }else {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene las oficinas que dependen directamente de la unidad
     * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    @Override
    public List<ObjetoBasico> oficinasDependientes(String codigo, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion from Oficina as oficina where " +
                "oficina.codUoResponsable.codigo=:codigo and oficina.estado.descripcionEstadoEntidad=:estado " +
                "and oficina.codOfiResponsable.codigo is null order by oficina.codigo");

        q.setParameter("codigo",codigo);
        q.setParameter("estado",estado);

        return getObjetoBasicoList(q.getResultList());
    }

    /**
     * Obtiene las oficinas auxiliares de un Oficina padre.
     * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    @Override
    public List<ObjetoBasico> oficinasAuxiliares(String codigo, String estado) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion from Oficina as oficina where " +
                " oficina.codOfiResponsable.codigo=:codigo and oficina.estado.descripcionEstadoEntidad =:estado " +
                " order by oficina.codigo");

        q.setParameter("codigo",codigo);
        q.setParameter("estado",estado);
        return getObjetoBasicoList(q.getResultList());
    }
}

