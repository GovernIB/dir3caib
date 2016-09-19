package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;
import org.apache.log4j.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 12/02/14
 */
/**
 * Ejb que proporciona los métodos para los ws para la sincronización/actualización con regweb
 */
@Stateless(name = "ObtenerUnidadesEJB")
@RunAs("DIR_ADMIN")  //todo añadir seguridad
public class ObtenerUnidadesEjb implements ObtenerUnidadesLocal {

    protected final Logger log = Logger.getLogger(getClass());
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    protected UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;

    /** Método que devuelve una UnidadTF( que se transfiere) a partir del código indicado y en función de
     *  la fecha de actualización y la de sincronizacion ( primera sincronizacion)
     *  @param codigo código de la unidad a transferir
     *  @param fechaActualizacion fecha en la que se realiza la actualización
     */
    @Override
    public UnidadTF obtenerUnidad(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception{

        Unidad unidad = unidadEjb.findConHistoricosVigente(codigo);
        UnidadTF unidadTF= null;
        // Si hay fecha de actualización y es anterior a la fecha de importación se debe transmitir
        if(fechaActualizacion != null){
          //Date fechaAct = formatoFecha.parse(fechaActualizacion);
          //Date fechaSincro = formatoFecha.parse(fechaSincronizacion);
          // Miramos si ha sido actualizada
          if(fechaActualizacion.before(unidad.getFechaImportacion()) || fechaActualizacion.equals(unidad.getFechaImportacion())){
              // miramos que no esté extinguida o anulada antes de la primera sincro.
              if(unidadValida(unidad, fechaSincronizacion)){
                 unidadTF = UnidadTF.generar(unidad);
              }
          }
        }else { // Si no hay fecha Actualización se trata de una sincronización y se debe enviar
          unidadTF = UnidadTF.generar(unidad);
        }

        return unidadTF;
    }

    /**
     * Método antiguo de sincronización/actualización de organigrama que no considera que se pueda extinguir la unidad raíz.
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @return
     * @throws Exception
     */
   /* @Override
    public List<UnidadTF> obtenerArbolUnidadesTF(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

        if (fechaActualizacion == null) {
            log.info("SINCRONIZACION UNIDADES");
        } else {
            log.info("ACTUALIZACION UNIDADES");
        }
        Unidad unidad = unidadEjb.findById(codigo);
        Unidad unidadRaiz = unidad.getCodUnidadRaiz();
        if (unidad == unidadRaiz) { // Caso que la unidad que nos indican es unidad raiz
            log.info("CASO UNIDAD QUE NOS PASAN ES RAIZ");
            List<Unidad> arbol = unidadEjb.obtenerArbolUnidadesUnidadRaiz(codigo, fechaActualizacion, fechaSincronizacion);
            log.info("Numero TOTAL de unidades a actualizar: " + arbol.size());
            List<UnidadTF> arbolTF = new ArrayList<UnidadTF>();


            for (Unidad uni : arbol) {
                arbolTF.add(UnidadTF.generar(uni));
            }

            return arbolTF;
        } else { // caso de que la unidad que nos indican no es raiz
            log.info("CASO UNIDAD QUE NOS PASAN NO ES RAIZ");
            List<Unidad> arbol = new ArrayList<Unidad>(); //Lista completa de unidades a enviar a regweb3(o porque es sincro o porque se han actualizado)

            //Añadimos la raiz cuando es sincronización
            if (fechaActualizacion == null) {
                arbol.add(unidad);
            }
            arbol.addAll(unidadEjb.obtenerArbolUnidadesUnidadNoRaiz(codigo, fechaActualizacion, fechaSincronizacion));
            log.info("Numero TOTAL de unidades a actualizar: " + arbol.size());
            List<UnidadTF> arbolTF = new ArrayList<UnidadTF>();


            for (Unidad uni : arbol) {
                arbolTF.add(UnidadTF.generar(uni));
            }

            return arbolTF;
        }

    }*/
    @Override
    /**
     * Método para la sincronización/actualización del organigrama cuyo código raiz es el que nos indican.
     * Este método considera también la posibilidad de que la misma raiz también se haya estinguido.
     *
     */
    public List<UnidadTF> obtenerArbolUnidadesTF2(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

        List<Unidad> arbol = new ArrayList<Unidad>(); //Lista completa de unidades a enviar a regweb3(o porque es sincro o porque se han actualizado)
        Unidad unidad = null;
        Unidad unidadRaiz = null;

        //Miramos si se ha actualizado la unidad que nos pasan
        if (fechaActualizacion != null) { // ES actualizacion, miramos si la raiz se ha actualizado
            log.info("ACTUALIZACION UNIDADES");
            //Obtenemos la raiz en funcion de la fecha de actualización
            unidad = unidadEjb.findUnidadActualizada(codigo, fechaActualizacion);
            if (unidad != null) { //Han actualizado la raiz
                unidadRaiz = unidad.getCodUnidadRaiz(); //obtenemos la raiz de la unidad que nos pasan
                // miramos que no esté extinguida o anulada antes de la primera sincro.
                if (unidadValida(unidad, fechaSincronizacion)) {
                    arbol.add(unidad);
                    Set<Unidad> historicosRaiz = unidad.getHistoricoUO();
                    if (historicosRaiz != null) {
                        for (Unidad historico : historicosRaiz) {
                            arbol.add(historico);
                            if (unidad == unidadRaiz) { //Miramos si la unidad extinguida es raiz
                                //obtenemos el arbol de los historicos que la sustituyen.
                                arbol.addAll(unidadEjb.obtenerArbolUnidadesUnidadRaiz(historico.getCodigo(), fechaActualizacion, fechaSincronizacion));
                            } else {
                                //obtenemos el arbol de los historicos que la sustituyen.
                                arbol.addAll(unidadEjb.obtenerArbolUnidadesUnidadNoRaiz(historico.getCodigo(), fechaActualizacion, fechaSincronizacion));
                            }
                        }
                    }
                }
            }
        }


        if (unidad == null) { // O es Sincro o es actualizacion pero con la raiz sin actualizar.
            unidad = unidadEjb.findUnidadEstado(codigo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            if (unidad != null) {
                arbol.add(unidad);
            }
        }

        //obtenemos el arbol de la unidad que nos han indicado para que se actualice todo bien.???
        if (unidad == unidadRaiz) { // Caso que la unidad que nos indican es unidad raiz
            log.info("CASO UNIDAD QUE NOS PASAN ES RAIZ");
            arbol.addAll(unidadEjb.obtenerArbolUnidadesUnidadRaiz(codigo, fechaActualizacion, fechaSincronizacion));
            log.info("Numero TOTAL de unidades a actualizar: " + arbol.size());

        } else { // caso de que la unidad que nos indican no es raiz
            log.info("CASO UNIDAD QUE NOS PASAN NO ES RAIZ");
            arbol.addAll(unidadEjb.obtenerArbolUnidadesUnidadNoRaiz(codigo, fechaActualizacion, fechaSincronizacion));
            log.info("Numero TOTAL de unidades a actualizar: " + arbol.size());
        }

        //Montamos la lista de unidadesTF a enviar
        List<UnidadTF> arbolTF = new ArrayList<UnidadTF>();

        for (Unidad uni : arbol) {
            arbolTF.add(UnidadTF.generar(uni));
        }

        return arbolTF;

    }


    /**
   * Método que devuelve el la unidad indicada si tiene oficinas y los hijos que dependen de ella.
   * @param codigo
   * @return
   * @throws Exception
   */
    @Override
    public List<UnidadTF> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception{

        List<Unidad> arbol = unidadEjb.obtenerArbolUnidadesDestinatarias(codigo);
        List<UnidadTF> arbolTF = new ArrayList<UnidadTF>();


        for (Unidad unidad : arbol) {
            arbolTF.add(UnidadTF.generar(unidad));
        }

        return arbolTF;
    }

    /**
     * Método que devuelve la fecha de la última actualización de las unidades
     * @return
     * @throws Exception
     */
    @Override
    public Date obtenerFechaUltimaActualizacion() throws Exception{

        Descarga descarga =  descargaEjb.findByTipo(Dir3caibConstantes.UNIDAD);

        return descarga.getFechaImportacion();
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
      private boolean unidadValida(Unidad unidad, Date fechaSincro) throws Exception {
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
}
