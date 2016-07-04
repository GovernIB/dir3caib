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

    /** Método que devuelve la lista de UnidadTF( que se transfiere) a partir del código indicado y en función de
     * la fecha de actualización
     * @param codigo código de la unidad raiz
     * @param fechaActualizacion fecha en la que se realiza la actualización
     * @param fechaSincronizacion fecha en la que se realiza la primera sincronizacion por parte del sistema que se sincroniza(regweb)
     */
    @Override
    public List<UnidadTF> obtenerArbolUnidadesTF(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {
        // Unidad Raiz, la obtenemos por separado, ya que el arbol lo obtenemos a través de la unidad Superior.

        Unidad unidadRaiz = null;
        List<Unidad> arbol = new ArrayList<Unidad>(); //Lista completa de unidades a enviar a regweb3(o porque es sincro o porque se han actualizado)

        if (fechaActualizacion != null) { // ES actualizacion, miramos si la raiz se ha actualizado
            log.info("ACTUALIZACION UNIDADES");
            //Obtenemos la raiz en funcion de la fecha de actualización
            unidadRaiz = unidadEjb.findUnidadActualizada(codigo, fechaActualizacion);
            if (unidadRaiz != null) { //Han actualizado la raiz
                // miramos que no esté extinguida o anulada antes de la primera sincro.
                if (unidadValida(unidadRaiz, fechaSincronizacion)) {
                    arbol.add(unidadRaiz);
                    Set<Unidad> historicosRaiz = unidadRaiz.getHistoricoUO();
                    if (historicosRaiz != null) {
                        for (Unidad historico : historicosRaiz) {
                            arbol.add(historico);
                            arbol.addAll(unidadEjb.obtenerArbolUnidades(historico.getCodigo(), fechaActualizacion, fechaSincronizacion));
                        }
                    }
                }
            }
        }


        if (unidadRaiz == null) { // O es Sincro o es actualizacion pero con la raiz sin actualizar.
            unidadRaiz = unidadEjb.findUnidadEstado(codigo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            if (unidadRaiz != null) {
                arbol.add(unidadRaiz);
            }
        }

        arbol.addAll(unidadEjb.obtenerArbolUnidades(codigo, fechaActualizacion, fechaSincronizacion));
        log.info("Numero TOTAL de unidades a actualizar: " + arbol.size());
        List<UnidadTF> arbolTF = new ArrayList<UnidadTF>();


        for (Unidad unidad : arbol) {
            arbolTF.add(UnidadTF.generar(unidad));
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
