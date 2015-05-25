/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;
import es.caib.dir3caib.persistence.utils.DataBaseUtils;
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
 *
 * @author mgonzalez
 * @author anadal
 */
@Stateless(name = "UnidadEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class UnidadBean extends BaseEjbJPA<Unidad, String> implements UnidadLocal {

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    public OficinaLocal oficinaEjb;

    protected final Logger log = Logger.getLogger(getClass());
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @PersistenceContext
    private EntityManager em;  

    @Override
    public Unidad findById(String id) throws Exception {

        return em.find(Unidad.class, id);
    }
    
    public Unidad findFullById(String id) throws Exception {
     Query q = em.createQuery("select unidad from Unidad as unidad where unidad.codigo=:id and unidad.estado.codigoEstadoEntidad=:vigente");
      q.setParameter("id", id);
      q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);;
      Unidad unidad = (Unidad)q.getSingleResult();
      Hibernate.initialize(unidad.getHistoricoUO());
      return unidad;
    }

    /**
     * Obtiene el codigo y la denominación de una Unidad con estado vigente. Se emplea para mostrar el arbol de unidades.
     * @param id identificador de la unidad
     * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     * */
    public ObjetoBasico findReduceUnidad(String id) throws Exception {

      Query q = em.createQuery("Select unidad.codigo, unidad.denominacion, unidad.estado.descripcionEstadoEntidad from Unidad as unidad where unidad.codigo=:id and unidad.estado.codigoEstadoEntidad =:vigente");
             q.setParameter("id", id);
             q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

      Object[] obj = (Object[])q.getSingleResult();

      ObjetoBasico objetoBasico = new ObjetoBasico((String)obj[0],(String)obj[1],(String)obj[2]);

      return objetoBasico;

    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Unidad> getAll() throws Exception {
        
        return  em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo").getResultList();
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

    @Override
    public List<Unidad> getPagination(int inicio) throws Exception {
      
        Query q = em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    @Override
    public List<Unidad> getPagination(int startItem, int numberOfItems) throws Exception {
      
        Query q = em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo");
        q.setFirstResult(startItem);
        q.setMaxResults(numberOfItems);

        return q.getResultList();
    }
    
    
    @Override
    public List<Unidad> getListByIds(List<String> ids) throws Exception {
      
      
      
        Query q = em.createQuery("Select unidad from Unidad as unidad "
          + " where unidad.codigo in (:theids) order by unidad.codigo");
        
        q.setParameter("theids", ids);

        return q.getResultList();
    }
    
    
    
     public void deleteHistoricosUnidad() throws Exception {
       
        em.createNativeQuery("delete from DIR_HISTORICOUO").executeUpdate();
    }
    
    
    public void deleteAll() throws Exception {
          
        em.createQuery("delete from Unidad").executeUpdate();                                
    }


    @Override
    public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion, Long codigoNivelAdministracion, String codAmbitoTerritorial, Long codComunidad, Long codigoProvincia, Boolean unidadRaiz) throws Exception {

        Query q;
        Query q2;
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<String> where = new ArrayList<String>();

        StringBuffer query = new StringBuffer("Select unidad from Unidad as unidad ");

        // Parametros de busqueda
        if(codigo!= null && codigo.length() > 0){where.add(DataBaseUtils.like("unidad.codigo", "codigo", parametros, codigo));}
        if(denominacion!= null && denominacion.length() > 0){where.add(DataBaseUtils.like("unidad.denominacion", "denominacion", parametros, denominacion));}
        if(codigoNivelAdministracion!= null && codigoNivelAdministracion != -1){where.add(" unidad.nivelAdministracion.codigoNivelAdministracion = :codigoNivelAdministracion "); parametros.put("codigoNivelAdministracion",codigoNivelAdministracion);}
        if(codAmbitoTerritorial!= null && (!"-1".equals(codAmbitoTerritorial))){where.add(" unidad.codAmbitoTerritorial.codigoAmbito = :codAmbitoTerritorial "); parametros.put("codAmbitoTerritorial",codAmbitoTerritorial);}
        if(codComunidad!= null && codComunidad != -1){where.add(" unidad.codAmbComunidad.codigoComunidad = :codComunidad "); parametros.put("codComunidad",codComunidad);}
        if(codigoProvincia!= null && codigoProvincia != -1){where.add(" unidad.codAmbProvincia.codigoProvincia = :codigoProvincia "); parametros.put("codigoProvincia",codigoProvincia);}
        if(unidadRaiz){where.add(" unidad.codUnidadRaiz.codigo = unidad.codigo ");}

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
            query.append("order by unidad.codigo desc");
            q = em.createQuery(query.toString());

            for (Map.Entry<String, Object> param : parametros.entrySet()) {
                q.setParameter(param.getKey(), param.getValue());
                q2.setParameter(param.getKey(), param.getValue());
            }

        }else{
            if(unidadRaiz){query.append(" where unidad.codUnidadRaiz.codigo = unidad.codigo ");}
            q2 = em.createQuery(query.toString().replaceAll("Select unidad from Unidad as unidad ", "Select count(unidad.codigo) from Unidad as unidad "));
            query.append("order by unidad.codigo desc");
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
    public List<Unidad> findByDenominacion(String denominacion) throws Exception {

      Query q = em.createQuery("select unidad from Unidad as unidad where upper(unidad.denominacion) like upper(:denominacion)");

      q.setParameter("denominacion", "%"+denominacion.toLowerCase()+"%");

      return q.getResultList();
    }

    /*
     * Metodo que comprueba si una unidad tiene más unidades hijas
     */
    @Override
    public Boolean tieneHijos(String codigo) throws Exception{

        Query q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo order by unidad.codigo");

        q.setParameter("codigo",codigo);

        List<Unidad> hijos = q.getResultList();

        return hijos.size() > 0;
    }

    /**
     * Metodo que obtiene los hijos de primer nivel de una unidad que estan vigentes
     * @param codigo identificador de la unidad padre.
     * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    @Override
    public List<ObjetoBasico> hijos(String codigo) throws Exception {

        Query q = em.createQuery("Select unidad.codigo, unidad.denominacion, unidad.estado.descripcionEstadoEntidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");

        q.setParameter("codigo",codigo);
        q.setParameter("vigente",Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        return getObjetoBasicoList(q.getResultList());
    }

    /*
      * Método que devuelve el árbol de unidades de la unidad indicada por codigo,
      * teniendo en cuenta la fecha de la ultima actualización de regweb.
      * Se emplea para la sincronizacion y actualización con regweb
      * */
    @Override
    public List<Unidad> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception{
        log.info("CODIGO QUE NOS PASAN " + codigo);
        Query q;
        if(fechaActualizacion == null){ // Es una sincronizacion, solo traemos vigentes
          log.info("SINCRONIZACION UNIDADES");
          q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");
          q.setParameter("codigo",codigo);
          q.setParameter("vigente",Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        } else {// es una actualizacion, lo traemos todo
          log.info("ACTUALIZACION UNIDADES");
          q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo  order by unidad.codigo");
          q.setParameter("codigo",codigo);

        }


        List<Unidad> padres = q.getResultList();
        List<Unidad> padresActualizados = new ArrayList<Unidad>();
        List<Unidad> listaCompleta;

        log.info("Número de PADRES UNIDADES: " + padres.size());

        if(fechaActualizacion!= null){ // Si hay fecha de actualizacion solo se envian las actualizadas
          // Date fechaAct = formatoFecha.parse(fechaActualizacion);
          // Date fechaSincro = formatoFecha.parse(fechaSincronizacion);

           for(Unidad unidad: padres){
             if(fechaActualizacion.before(unidad.getFechaImportacion()) || fechaActualizacion.equals(unidad.getFechaImportacion())){
                  // Miramos que la unidad no este extinguida o anulada anterior a la fecha de sincronizacion de regweb
                  if(unidadValida(unidad,fechaSincronizacion)){
                    padresActualizados.add(unidad);
                  }
             }
           }
           listaCompleta = new ArrayList<Unidad>(padresActualizados);
        } else { // si no hay fecha, se trata de una sincronización
           listaCompleta = new ArrayList<Unidad>(padres);
        }

        for (Unidad unidad : padres) {
            if(tieneHijos(unidad.getCodigo())){
                List<Unidad> hijos = obtenerArbolUnidades(unidad.getCodigo(),fechaActualizacion, fechaSincronizacion);
                log.info("Unidad " + unidad.getDenominacion() + ", tiene "+ hijos.size()+" hijos!");
                listaCompleta.addAll(hijos);
            }
        }

        return listaCompleta;
    }

  /**
   * Método que devuelve el árbol de unidades de la unidad indicada por codigo,
   * que esté vigente y que sean destinatarios( es decir que tengan oficinas donde registrar)
   * solicitado por SISTRA
   * @param codigo código de la unidad raiz de la que partimos.
   * @return
   * @throws Exception
   */
    @Override
    public List<Unidad> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception{

      Query q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");
      q.setParameter("codigo", codigo);
      q.setParameter("vigente",  Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

      List<Unidad> unidadesDestinatariasPadres  = q.getResultList();
      List<Unidad> unidadesDestConOficinas= new ArrayList<Unidad>();

      for(Unidad unidad: unidadesDestinatariasPadres){
        Boolean tiene = oficinaEjb.tieneOficinasOrganismo(unidad.getCodigo());
        if(tiene){
          unidadesDestConOficinas.add(unidad);
        }
        if(tieneHijos(unidad.getCodigo())){
          List<Unidad> hijos = obtenerArbolUnidadesDestinatarias(unidad.getCodigo());
          unidadesDestConOficinas.addAll(hijos);
        }
      }

      return unidadesDestConOficinas;
    }


    /**
     *
     * Se mira que si la unidad,  su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar relaciones antiguas extinguidas o anuladas
     * @param unidad    relacion organizativa
     * @param fechaSincro  fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
      public boolean unidadValida(Unidad unidad, Date fechaSincro) throws Exception {
           if(unidad.getFechaExtincion() != null){
                if(unidad.getFechaExtincion().after(fechaSincro) || unidad.getFechaExtincion().equals(fechaSincro)){
                  return true;
                }
           }else{
                if(unidad.getFechaAnulacion() != null){
                  if(unidad.getFechaAnulacion().after(fechaSincro) || unidad.getFechaAnulacion().equals(fechaSincro)) {
                    return true;
                  }
                }else {
                   return true;
                }
           }
           return false;
      }
      
      
      
      /**
       * Devuelve  los Identificadores de todas las unidades existentes en base de datos 
       * @return
       */
      @Override
      public List<String> getAllCodigos() {
        Query q = em.createQuery("Select unidad.codigo from Unidad as unidad order by unidad.codigo");
        List<String> codigos = q.getResultList();
        return codigos;
      }
      
      /**
       * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
       * @param result
       * @return
       * @throws Exception
       */
       private List<ObjetoBasico> getObjetoBasicoList(List<Object[]> result) throws Exception{

          List<ObjetoBasico> unidadesReducidas = new ArrayList<ObjetoBasico>();

          for (Object[] object : result){
              ObjetoBasico objetoBasico = new ObjetoBasico((String)object[0],(String)object[1],"");

              unidadesReducidas.add(objetoBasico);
          }

          return  unidadesReducidas;
       }
}
