/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi;
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

    /**
     * Obtiene el codigo y la denominación de una Oficina con estado vigente.
     * Se emplea para mostrar el árbol de oficinas.
     * @param id identificador de la oficina
     * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     * */
    public ObjetoBasico findReduceOficina(String id) throws Exception {

      Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.estado.descripcionEstadoEntidad from Oficina as oficina where oficina.codigo=:id and oficina.estado.codigoEstadoEntidad =:vigente");
             q.setParameter("id", id);
             q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

      Object[] obj = (Object[])q.getSingleResult();

      ObjetoBasico objetoBasico = new ObjetoBasico((String)obj[0],(String)obj[1],(String)obj[2]);

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


    @Override
    public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, Long codigoProvincia) throws Exception {

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

    /*
     Metodo que comprueba si una oficina tiene más oficinas hijas
     */
    @Override
    public Boolean tieneHijos(String codigo) throws Exception{

        Query q = em.createQuery("Select oficina from Oficina as oficina where oficina.codOfiResponsable.codigo =:codigo and oficina.codigo !=:codigo order by oficina.codigo");

        q.setParameter("codigo",codigo);

        List<Oficina> hijos = q.getResultList();

        return hijos.size() > 0;
    }

    /**
     * Metodo que obtiene los hijos de primer nivel de una oficina que estan vigentes
     * @param codigo identificador de la oficina padre.
     * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    @Override
    public List<ObjetoBasico> hijos(String codigo) throws Exception {

        Query q = em.createQuery("Select oficina.codigo, oficina.denominacion, oficina.estado.descripcionEstadoEntidad from Oficina as oficina where oficina.codOfiResponsable.codigo =:codigo and oficina.codigo !=:codigo and oficina.estado.codigoEstadoEntidad =:vigente order by oficina.codigo");

        q.setParameter("codigo",codigo);
        q.setParameter("vigente",Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        return getObjetoBasicoList(q.getResultList());
    }




    /**
     * Método que devuelve las oficinas de un organismo,
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

        log.info("obtenerOficinasOrganismo");
        // En un primer paso obtenemos las oficinas en función de si es SINCRO o ACTUALIZACION
        Query q;
        if(fechaActualizacion == null){// Es una sincronizacion, solo se mandan las vigentes
          log.info("SINCRONIZACION OFICINAS");
          q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad =:vigente order by oficina.codigo");
          q.setParameter("codigo",codigo);
          q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        }else{ //Es una actualizacion, se mandan todas
          log.info("ACTUALIZACION OFICINAS");
          q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo order by oficina.codigo");
          q.setParameter("codigo",codigo);
        }


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
                if(relOrg.getEstado().getCodigoEstadoEntidad().equals( Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE)){
                  relacionesVigentes.add(relOrg);
                }
              }
              oficina.setOrganizativasOfi(null);
              oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesVigentes));
             }
             oficinasCompletas = new ArrayList<Oficina>(oficinas);

        }else{ // ES UNA ACTUALIZACION
          // Si hay fecha de actualización y es anterior o igual a la fecha de importación se debe
          // incluir en la lista de actualizadas

          for(Oficina oficina: oficinas){
             if(fechaActualizacion.before(oficina.getFechaImportacion()) || fechaActualizacion.equals(oficina.getFechaImportacion())){
                // Cogemos solo las relaciones organizativas posteriores a la fecha de sincronizacion
                Set<RelacionOrganizativaOfi> todasRelaciones = new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());
                Set<RelacionOrganizativaOfi> relacionesValidas= new HashSet<RelacionOrganizativaOfi>();
                for(RelacionOrganizativaOfi relOrg: todasRelaciones){
                  if(relacionValida(relOrg, fechaSincronizacion)){
                    relacionesValidas.add(relOrg);
                  }
                }
                oficina.setOrganizativasOfi(null);
                oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesValidas));
                oficinasActualizadas.add(oficina);
             }
          }
          oficinasCompletas = new ArrayList<Oficina>(oficinasActualizadas);
        }

        return oficinasCompletas;

    }

    /**
     *  Método que devuelve  el arbol de oficinas de una oficina padre,
     * teniendo en cuenta la fecha de la ultima actualización de regweb.
     * Se emplea para la sincronizacion y actualización con regweb
     * @param codigo código de la oficina padre
     * @param fechaActualizacion  fecha de la ultima actualización de regweb
     * @param fechaSincronizacion fecha la primera sincronización de regweb.
     * @return
     * @throws Exception
     */
    @Override
     public List<Oficina> obtenerArbolOficinas(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception{

        Query q;
        if(fechaActualizacion == null){ // SINCRONIZACION solo traemos las vigentes
            log.info("SINCRONIZACION OFICINAS");
            q = em.createQuery("Select oficina from Oficina as oficina where oficina.codOfiResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad =:vigente order by oficina.codigo");
            q.setParameter("codigo",codigo);
            q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        } else { // ACTUALIZACION
            log.info("ACTUALIZACION OFICINAS");
            q = em.createQuery("Select oficina from Oficina as oficina where oficina.codOfiResponsable.codigo =:codigo  order by oficina.codigo");
            q.setParameter("codigo",codigo);
        }


        List<Oficina> padres = q.getResultList();
        List<Oficina> padresActualizados = new ArrayList<Oficina>();
        List<Oficina> listaCompleta;

        log.info("Número de OFICINAS PADRES: " + padres.size());


        //Preparación de las oficinas a enviar.
        // si hay fecha de actualización solo se envian las actualizadas
        // posterior a la fecha indicada
        if(fechaActualizacion!= null){
          //  Date fechaAct = formatoFecha.parse(fechaActualizacion);
            // Fecha de la primera sincronizacion de regweb
          //  Date fechaSincro = formatoFecha.parse(fechaSincronizacion);
            for(Oficina oficina : padres){
              if(fechaActualizacion.before(oficina.getFechaImportacion()) || fechaActualizacion.equals(oficina.getFechaImportacion())){
                // Cogemos solo las relaciones organizativas posteriores a la fecha de sincronizacion
                Set<RelacionOrganizativaOfi> todasRelaciones = new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());
                Set<RelacionOrganizativaOfi> relacionesValidas= new HashSet<RelacionOrganizativaOfi>();
                for(RelacionOrganizativaOfi relOrg: todasRelaciones){
                  if(relacionValida(relOrg, fechaSincronizacion)){
                    relacionesValidas.add(relOrg);
                  }
                }
                oficina.setOrganizativasOfi(null);
                oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesValidas));

                padresActualizados.add(oficina);
              }
            }
            listaCompleta = new ArrayList<Oficina>(padresActualizados);
        } else { // Si no hay fechaActualizacion, es una sincronización y se envian todas las vigentes y sus relaciones vigentes
              for(Oficina oficina: padres){
              // Solo se envian las relaciones organizativas vigentes.
              Set<RelacionOrganizativaOfi> relaciones =new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());

              Set<RelacionOrganizativaOfi> relacionesVigentes= new HashSet<RelacionOrganizativaOfi>();
              //Metemos en la lista las relacionesVigentes
              for(RelacionOrganizativaOfi relOrg: relaciones){
                if(relOrg.getEstado().getCodigoEstadoEntidad().equals( Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE)){
                  relacionesVigentes.add(relOrg);
                }
              }
              oficina.setOrganizativasOfi(null);
              oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesVigentes));
             }
             listaCompleta = new ArrayList<Oficina>(padres);

        }

        for (Oficina oficina: padres) {
            if(tieneHijos(oficina.getCodigo())){
                List<Oficina> hijos = obtenerArbolOficinas(oficina.getCodigo(),fechaActualizacion,fechaSincronizacion);
                log.info("Oficina " + oficina.getDenominacion() + ", tiene "+ hijos.size()+" hijos!");
                listaCompleta.addAll(hijos);
            }
        }

        return listaCompleta;
    }

    /**
     * Obtiene las oficinas SIR de una unidad que están vigentes
     * @param codigo código de la Unidad
     * @return  listado de Oficinas
     * @throws Exception
     */
    public List<Oficina> obtenerOficinasSIRUnidad(String codigo) throws Exception {

         Query q = em.createQuery("select oficina from RelacionSirOfi as relacionSirOfi, Oficina as oficina where " +
                "relacionSirOfi.unidad.id = :codigoUnidad and relacionSirOfi.estado.codigoEstadoEntidad='V' and relacionSirOfi.oficina.id = oficina.id");

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
           if(relOrg.getUnidad().getFechaExtincion() != null){
                if(relOrg.getUnidad().getFechaExtincion().after(fechaSincro) || relOrg.getUnidad().getFechaExtincion().equals(fechaSincro)){
                  return true;
                }
           }else{
                if(relOrg.getUnidad().getFechaAnulacion() != null){
                  if(relOrg.getUnidad().getFechaAnulacion().after(fechaSincro) || relOrg.getUnidad().getFechaAnulacion().equals(fechaSincro)) {
                    return true;
                  }
                }else {
                   return true;
                }
           }
           return false;
      }

      /**
      * Método que comprueba si una unidad tiene oficinas donde registrar
      * @param codigo
      * @return
      * @throws Exception
      */
      @Override
      public Boolean tieneOficinasOrganismo(String codigo) throws Exception {

        Query q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad=:vigente order by oficina.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        List<Oficina> oficinas =  q.getResultList();
        if (oficinas.size() > 0) {
          return true;
        }else{
          q = em.createQuery("Select relorg from RelacionOrganizativaOfi as relorg where relorg.unidad.codigo=:codigo and relorg.estado.codigoEstadoEntidad=:vigente order by relorg.id ");

          q.setParameter("codigo", codigo);
          q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
          List<RelacionOrganizativaOfi> relorg= q.getResultList();
          return relorg.size() > 0;
        }

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
              ObjetoBasico objetoBasico = new ObjetoBasico((String)object[0],(String)object[1],"");

              oficinasReducidas.add(objetoBasico);
          }

          return  oficinasReducidas;
       }
}

