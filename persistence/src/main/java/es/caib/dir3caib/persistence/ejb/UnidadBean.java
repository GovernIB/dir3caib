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
     * @param estado estado de la unidad
     * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     * */
    public ObjetoBasico findReduceUnidad(String id, String estado) throws Exception {

      Query q = em.createQuery("Select unidad.codigo, unidad.denominacion, unidad.estado.descripcionEstadoEntidad from Unidad as unidad where unidad.codigo=:id and unidad.estado.descripcionEstadoEntidad =:estado");
             q.setParameter("id", id);
             q.setParameter("estado", estado);

      Object[] obj = (Object[])q.getSingleResult();
        ObjetoBasico objetoBasico = new ObjetoBasico((String) obj[0], (String) obj[1], (String) obj[2], "", "", "");

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
    public String unidadDenominacion(String codigo) throws Exception {

        Query q = em.createQuery("select unidad.denominacion from Unidad as unidad where unidad.codigo=:codigo").setParameter("codigo", codigo);

        return (String) q.getSingleResult();
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
    public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion, Long codigoNivelAdministracion, String codAmbitoTerritorial, Long codComunidad, Long codigoProvincia, Boolean unidadRaiz, String codigoEstado) throws Exception {

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
        if(codigoEstado!= null &&(!"-1".equals(codigoEstado))){where.add(" unidad.estado.codigoEstadoEntidad = :codigoEstado "); parametros.put("codigoEstado",codigoEstado);}
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

    /*
     * Metodo que comprueba si una unidad tiene más unidades hijas
     */
    @Override
    public Boolean tieneHijos(String codigo) throws Exception{

        Query q = em.createQuery("Select unidad.id from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo order by unidad.codigo");

        q.setParameter("codigo",codigo);

        List<Long> hijos = q.getResultList();

        return hijos.size() > 0;
    }

    /**
     * Método que obtiene los hijos de primer nivel de una unidad en función del estado de la unidad padre
     * @param codigo identificador de la unidad padre.
     * @param estado estado de la unidad padre.
     *
     * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    @Override
    public List<ObjetoBasico> hijosOB(String codigo, String estado) throws Exception {

        Query q = em.createQuery("Select unidad.codigo, unidad.denominacion, unidad.estado.descripcionEstadoEntidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.descripcionEstadoEntidad =:estado order by unidad.codigo");

        q.setParameter("codigo",codigo);
        q.setParameter("estado",estado);

        return getObjetoBasicoList(q.getResultList());
    }

    /**
     * Método que obtiene los hijos de primer nivel de una unidad en función del estado de la unidad padre
     * @param codigo identificador de la unidad padre.
     *
     * @return  {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    @Override
    public List<Unidad> hijosPrimerNivel(String codigo) throws Exception {

        Query q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:estado order by unidad.codigo");

        q.setParameter("codigo",codigo);
        q.setParameter("estado", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        return q.getResultList();
    }

    /**
     * Metodo que obtiene los hijos de primer nivel de una unidad en función del estado de la unidad padre
     * @param unidadesPadres unidadesPadres de las que obtener hijos
     * @param estado indica el estado de los hijos
     * @param hijosTotales lista con todos los hijos encontrados de manera recursiva.
     *
     * @return  {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    @Override
    public void arbolHijos(Set<Unidad> unidadesPadres, String estado, Set<Unidad> hijosTotales) throws Exception {
        for(Unidad unidad:unidadesPadres){
            Query q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.descripcionEstadoEntidad =:estado order by unidad.codigo");

            q.setParameter("codigo",unidad.getCodigo());
            q.setParameter("estado",estado);

            Set<Unidad> hijos=new HashSet<Unidad>(q.getResultList());
            log.info("Hijos encontrados de UNIDAD:  " + unidad.getCodigo());
            hijosTotales.addAll(hijos);

            //llamada recursiva para todos los hijos
            arbolHijos(hijos,estado,hijosTotales);
        }

    }


    /*
      * Método que devuelve el árbol de unidades de la unidad indicada por codigo,
      * teniendo en cuenta la fecha de la ultima actualización de regweb.
      * Se emplea para la sincronizacion y actualización con regweb
      * */
    @Override
    public List<Unidad> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception{

        String denominacion = unidadDenominacion(codigo);
        Query q;
        try {
            if (fechaActualizacion == null) { // Es una sincronizacion, solo traemos vigentes
                q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");
                q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            } else {// es una actualizacion, traemos las que tienen fechaactualizacion anterior a la fecha de importacion de las unidades

                q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo " +
                        "and unidad.codigo !=:codigo  " +
                        "and :fechaActualizacion < unidad.fechaImportacion " +
                        "order by unidad.codigo");
                q.setParameter("fechaActualizacion", fechaActualizacion);
            }

            q.setParameter("codigo", codigo);
            List<Unidad> hijos = q.getResultList();
            List<Unidad> padresActualizados = new ArrayList<Unidad>();
            List<Unidad> listaCompleta;

            log.info(" ");
            log.info("UNIDAD PADRE: " + codigo + " - " + denominacion);

            if (fechaActualizacion != null) { // Si hay fecha de actualizacion solo se envian las actualizadas
                for (Unidad unidad : hijos) {
                    log.info("FECHA ACTUALIZACION " + fechaActualizacion + "ANTERIOR A LA FECHA DE IMPORTACION DE LA UNIDAD ID " + unidad.getCodigo() + " FECHA IMPORT" + unidad.getFechaImportacion());
                    // Miramos que la unidad no este extinguida o anulada anterior a la fecha de sincronizacion de regweb
                    if (unidadValida(unidad, fechaSincronizacion)) {
                        padresActualizados.add(unidad);
                    }
                }
                listaCompleta = new ArrayList<Unidad>(padresActualizados);
            } else { // si no hay fecha, se trata de una sincronización
                listaCompleta = new ArrayList<Unidad>(hijos);
            }

            log.info("Numero de hijos a actualizar: " + listaCompleta.size());

            for (Unidad unidad : hijos) {

                if (tieneHijos(unidad.getCodigo())) {
                    List<Unidad> arbol = obtenerArbolUnidades(unidad.getCodigo(), fechaActualizacion, fechaSincronizacion);
                    listaCompleta.addAll(arbol);
                }

            }

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
    public List<Unidad> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception{

      Query q = em.createQuery("Select unidad from Unidad as unidad where unidad.codigo =:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");
      q.setParameter("codigo", codigo);
      q.setParameter("vigente",  Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

      Unidad unidadPadre  = (Unidad)q.getSingleResult();
      log.info("UNIDAD ENCONTRADA " + unidadPadre.getCodigo());

      List<Unidad> unidadesDestConOficinas= new ArrayList<Unidad>();

      //Miramos si la unidad que nos pasan tiene oficinas
      Boolean tiene = oficinaEjb.tieneOficinasArbol(unidadPadre.getCodigo());

      if(tiene){

          unidadesDestConOficinas.add(unidadPadre);
          Set<Unidad> padres = new HashSet<Unidad>();
          padres.add(unidadPadre);
          Set<Unidad> unidadesTotales = new HashSet<Unidad>();
          //Obtenemos de manera recursiva todos los hijos de la unidad que nos indican
          arbolHijos(padres,Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE,unidadesTotales);

          unidadesDestConOficinas.addAll(unidadesTotales);
      }

      return unidadesDestConOficinas;
    }


    /**
     *
     * Se mira que si la unidad,  su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar relaciones antiguas extinguidas o anuladas
     * @param unidad  unidad
     * @param fechaSincro  fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
      public boolean unidadValida(Unidad unidad, Date fechaSincro) throws Exception {

          SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
          String sSincro=new String();
          if(fechaSincro!= null) {
              sSincro = fechaFormat.format(fechaSincro);
          }
          if(unidad.getFechaExtincion() != null){
                String sExtincion = fechaFormat.format(unidad.getFechaExtincion());
                if(unidad.getFechaExtincion().after(fechaSincro) || sExtincion.equals(sSincro)){
                  return true;
                }
           }else{
                if(unidad.getFechaAnulacion() != null){
                  String sAnulacion = fechaFormat.format(unidad.getFechaAnulacion());
                  if(unidad.getFechaAnulacion().after(fechaSincro) || sAnulacion.equals(sSincro)) {
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
              ObjetoBasico objetoBasico = new ObjetoBasico((String) object[0], (String) object[1], (String) object[2], "", "", "");

              unidadesReducidas.add(objetoBasico);
          }

          return  unidadesReducidas;
       }
}
