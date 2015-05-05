package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;
import es.caib.dir3caib.persistence.utils.DataBaseUtils;
import org.apache.log4j.Logger;

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
 */
@Stateless(name = "Dir3RestEJB")
public class Dir3RestBean implements Dir3RestLocal {

  protected final Logger log = Logger.getLogger(getClass());
  protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

  @PersistenceContext
  private EntityManager em;


  @Override
  public List<Unidad> findUnidadesByDenominacion(String denominacion) throws Exception {

    Query q = em.createQuery("select unidad from Unidad as unidad where upper(unidad.denominacion) like upper(:denominacion)");

    q.setParameter("denominacion", "%"+denominacion.toLowerCase()+"%");

    return q.getResultList();
  }

  @Override
  public List<Oficina> findOficinasByDenominacion(String denominacion) throws Exception {

      Query q = em.createQuery("select oficina from Oficina as oficina where upper(oficina.denominacion) like upper(:denominacion)");

      q.setParameter("denominacion", "%"+denominacion.toLowerCase()+"%");

      return q.getResultList();
    }

  /*
     * Metodo que comprueba si una unidad tiene más unidades hijas
     */
    @Override
    public Boolean tieneHijos(String codigo) throws Exception{

        Query q = em.createQuery("Select unidad.codigo from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo order by unidad.codigo");

        q.setParameter("codigo",codigo);

        List<Unidad> hijos = q.getResultList();

        return hijos.size() > 0;
    }


  @Override
  public List<Unidad> obtenerArbolUnidades(String codigo, String fechaActualizacion) throws Exception{
      Query q;
      if(fechaActualizacion == null){ // Es una sincronizacion, solo traemos vigentes
        q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");
        q.setParameter("codigo",codigo);
        q.setParameter("vigente",Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
      } else {// es una actualizacion, lo traemos todo
        q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo  order by unidad.codigo");
        q.setParameter("codigo",codigo);

      }

      List<Unidad> padres = q.getResultList();
      List<Unidad> padresActualizados = new ArrayList<Unidad>();
      List<Unidad> listaCompleta;

      //log.info("Número de PADRES: " + padres.size());

      if(fechaActualizacion!= null){ // Si hay fecha de actualizacion solo se envian las actualizadas
         Date fechaAct = formatoFecha.parse(fechaActualizacion);
         for(Unidad unidad: padres){
           if(fechaAct.before(unidad.getFechaImportacion())){
             padresActualizados.add(unidad);
           }
         }
         listaCompleta = new ArrayList<Unidad>(padresActualizados);
      } else { // si no hay fecha, se trata de una sincronización
         listaCompleta = new ArrayList<Unidad>(padres);
      }

      for (Unidad unidad : padres) {
          if(tieneHijos(unidad.getCodigo())){
              List<Unidad> hijos = obtenerArbolUnidades(unidad.getCodigo(),fechaActualizacion);
              log.info("Unidad " + unidad.getDenominacion() + ", tiene "+ hijos.size()+" hijos!");
              listaCompleta.addAll(hijos);
          }
      }

      return listaCompleta;
  }

  /**
   * Función que obtiene los hijos vigentes de una Unidad pero como ObjetoBasico ya que solo interesa
   * el código y la denominación.
   * @param codigo
   * @return
   * @throws Exception
   */
  @Override
  public List<ObjetoBasico> obtenerArbolUnidades(String codigo) throws Exception{
      Query q;

      q = em.createQuery("Select unidad.codigo, unidad.denominacion from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.codigo");
      q.setParameter("codigo",codigo);
      q.setParameter("vigente",Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);


      List<ObjetoBasico> padres = getObjetoBasicoList(q.getResultList());
      List<ObjetoBasico> listaCompleta;




      listaCompleta = new ArrayList<ObjetoBasico>(padres);


      for (ObjetoBasico unidad : padres) {
          if(tieneHijos(unidad.getCodigo())){
              List<ObjetoBasico> hijos = obtenerArbolUnidades(unidad.getCodigo());
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
    public List<Oficina> obtenerOficinasOrganismo(String codigo, String fechaActualizacion) throws Exception{

        Query q = em.createQuery("Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad='V' order by oficina.codigo");

        q.setParameter("codigo",codigo);

        List<Oficina> oficinas = q.getResultList();
        List<Oficina> oficinasCompletas = new ArrayList<Oficina>();
        List<Oficina> oficinasActualizadas = new ArrayList<Oficina>();
        // Si hay fecha de actualización y es anterior a la fecha de importación se debe
        // incluir en la lista de actualizadas
        if(fechaActualizacion!=null){
          Date fechaAct = formatoFecha.parse(fechaActualizacion);
          for(Oficina oficina: oficinas){
             if(fechaAct.before(oficina.getFechaImportacion())){
              oficinasActualizadas.add(oficina);
             }
          }
          oficinasCompletas = new ArrayList<Oficina>(oficinasActualizadas);
        }else{ // Si no hay fecha de actualización se trata de una sincronización
           oficinasCompletas = new ArrayList<Oficina>(oficinas);
        }

        return oficinasCompletas;

    }

  /**
   * Búsqueda de organismos según los parámetros indicados que esten vigentes y que tengan
   * al menos una oficina asociada
   * @param codigo  código de la unidad
   * @param denominacion  denominación de la unidad
   * @param codigoNivelAdministracion  nivel de administración de la unidad
   * @param codComunidad  comunidad autónoma a la que pertenece.
   * @return   List<ObjetoBasico> devuelve un listado con el codigo y la denominación que
   * coincide con los parámetros de búsqueda
   * @throws Exception
   */
     public List<ObjetoBasico> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad) throws Exception {
       Query q;
       Map<String, Object> parametros = new HashMap<String, Object>();
       List<String> where = new ArrayList<String>();
       StringBuffer query = new StringBuffer("Select distinct(unidad.codigo), unidad.denominacion from Unidad as unidad, Oficina as ofi, RelacionOrganizativaOfi as relorg  ");

       // Parametros de busqueda

       if(codigo!= null && codigo.length() > 0){where.add(DataBaseUtils.like("unidad.codigo","codigo",parametros,codigo));}
       if(denominacion!= null && denominacion.length() > 0){where.add(DataBaseUtils.like("unidad.denominacion", "denominacion", parametros, denominacion));}
       if(codigoNivelAdministracion!= null && codigoNivelAdministracion != -1){where.add(" unidad.nivelAdministracion.codigoNivelAdministracion = :codigoNivelAdministracion "); parametros.put("codigoNivelAdministracion",codigoNivelAdministracion);}
       if(codComunidad!= null && codComunidad != -1){where.add(" unidad.codAmbComunidad.codigoComunidad = :codComunidad "); parametros.put("codComunidad",codComunidad);}
       where.add(" unidad.codigo = ofi.codUoResponsable.codigo and ofi.codUoResponsable.codigo is not null and unidad.codigo = relorg.unidad.codigo and relorg.estado.codigoEstadoEntidad =:vigente"); parametros.put("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
       where.add(" unidad.estado.codigoEstadoEntidad =:vigente ");parametros.put("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);



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

       }else{
           query.append("order by unidad.denominacion asc");
           q = em.createQuery(query.toString());
       }


       return getObjetoBasicoList(q.getResultList());

     }


     public List<ObjetoBasico> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad) throws Exception {
         Query q;
         Map<String, Object> parametros = new HashMap<String, Object>();
         List<String> where = new ArrayList<String>();

         StringBuffer query = new StringBuffer("Select oficina.codigo, oficina.denominacion from Oficina as oficina ");

         // Parametros de busqueda

         if(codigo!= null && codigo.length() > 0){where.add(DataBaseUtils.like("oficina.codigo","codigo",parametros,codigo));}
         if(denominacion!= null && denominacion.length() > 0){where.add(DataBaseUtils.like("oficina.denominacion", "denominacion", parametros, denominacion));}
         if(codigoNivelAdministracion!= null && codigoNivelAdministracion != -1){where.add(" oficina.nivelAdministracion.codigoNivelAdministracion = :codigoNivelAdministracion "); parametros.put("codigoNivelAdministracion",codigoNivelAdministracion);}
         if(codComunidad!= null && codComunidad != -1){where.add(" oficina.codComunidad.codigoComunidad = :codComunidad "); parametros.put("codComunidad",codComunidad);}


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

         }else{
             query.append("order by oficina.denominacion asc");
             q = em.createQuery(query.toString());
         }


         return getObjetoBasicoList(q.getResultList());

       }

       @Override
       public String unidadDenominacion(String codigo) throws Exception {

         Query q = em.createQuery("select unidad.denominacion from Unidad as unidad where unidad.codigo=:codigo").setParameter("codigo", codigo);

         return (String)q.getSingleResult();
       }

       @Override
       public String oficinaDenominacion(String codigo) throws Exception {

         Query q = em.createQuery("select oficina.denominacion from Oficina as oficina where oficina.codigo=:codigo").setParameter("codigo", codigo);

         return (String)q.getSingleResult();
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
