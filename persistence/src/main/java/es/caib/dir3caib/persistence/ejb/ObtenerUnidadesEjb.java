package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Sincronizacion;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;
import es.caib.dir3caib.utils.Utils;
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
 * @author mgonzalez
 * Date: 12/02/14
 */

/**
 * Ejb que proporciona los métodos para los ws para la sincronización/actualización con regweb
 */
@Stateless(name = "ObtenerUnidadesEJB")
@RunAs("DIR_WS")
public class ObtenerUnidadesEjb implements ObtenerUnidadesLocal {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    private UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/SincronizacionEJB/local")
    private SincronizacionLocal sincronizacionEjb;


    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    /**
     Método que devuelve una {@link es.caib.dir3caib.persistence.model.ws.UnidadTF} a partir del código
     * indicado y en función de la fecha de actualización
     *
     * @param codigo
     *          código de la unidad a transferir
     * @param fechaActualizacion
     *          fecha en la que se realiza la actualización
     * @return null si la unidad no está vigente
     */
    @Override
    public UnidadTF obtenerUnidad(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

        Unidad unidad = unidadEjb.findConHistoricosVigente(codigo);
        UnidadTF unidadTF = null;

        if (unidad != null) {
            List<ContactoUnidadOrganica> contactosVisibles = new ArrayList<ContactoUnidadOrganica>();
            for (ContactoUnidadOrganica contactoUO : unidad.getContactos()) {
                if (contactoUO.isVisibilidad()) {
                    contactosVisibles.add(contactoUO);

                }
            }
            unidad.setContactos(contactosVisibles);
            // Si hay fecha de actualización y es anterior a la fecha de importación se debe transmitir
            if (fechaActualizacion != null) {
                // Miramos si ha sido actualizada
                if (fechaActualizacion.before(unidad.getFechaImportacion()) || fechaActualizacion.equals(unidad.getFechaImportacion())) {
                    // miramos que no esté extinguida o anulada antes de la primera sincro.
                    if (unidadEjb.unidadValida(unidad, fechaSincronizacion)) {
                        unidadTF = UnidadTF.generar(unidad);
                    }
                }
            } else { // Si no hay fecha Actualización se trata de una sincronización y se debe enviar
                unidadTF = UnidadTF.generar(unidad);
            }
            return unidadTF;
        } else {
            log.info("WS: la Unidad cuyo codigoDir3 es " + codigo + " está extinguida");
            return null;
        }

    }

    /**
     * Método que devuelve una {@link es.caib.dir3caib.persistence.model.ws.UnidadTF} a partir del código indicado
     *
     * @param codigo código de la unidad a transferir
     */
    @Override
    public UnidadTF buscarUnidad(String codigo) throws Exception {

        Unidad unidad = unidadEjb.findById(codigo);

        if (unidad != null) {
            List<ContactoUnidadOrganica> contactosVisibles = new ArrayList<ContactoUnidadOrganica>();
            for (ContactoUnidadOrganica contactoUO : unidad.getContactos()) {
                if (contactoUO.isVisibilidad()) {
                    contactosVisibles.add(contactoUO);

                }
            }
            unidad.setContactos(contactosVisibles);

            return UnidadTF.generar(unidad);

        } else {
            log.info("WS: la Unidad cuyo codigo Dir3 es " + codigo + " no existe");
            return null;
        }

    }

    @Override
    /**
     * Método que devuelve la lista de {@link es.caib.dir3caib.persistence.model.ws.UnidadTF} a partir del
     * código indicado y las fechas indicadas
     * Si no se especifican fechas obtiene aquellas unidades que son vigentes.
     * Si se especifica la fecha de actualización obtiene las unidades que han sufrido cambios entre esa fecha y la actual.
     * La fecha de sincronización nos sirve para evitar traer unidades (extinguidas/anuladas) anteriores a esta fecha
     *
     *
     * Este método considera también la posibilidad de que la misma raiz también se haya extinguido.
     * Esto es debido a que cuando en Madrid actualizan una unidad la tendencia es extinguirla y crear una nueva con código diferente.
     * Esto hace que se tengan que traer las unidades de la vieja y de la nueva.
     * Los casos que se consideran son:
     * SINCRONIZACION DE UNIDAD RAIZ
     * SINCRONIZACION DE UNIDAD NO RAIZ
     * ACTUALIZACION DE  UNIDAD RAIZ CON CAMBIO DE RAIZ
     * ACTUALIZACION DE  UNIDAD RAIZ SIN CAMBIO DE RAIZ
     * ACTUALIZACION DE  UNIDAD NO RAIZ CON CAMBIO DE RAIZ
     * ACTUALIZACION DE  UNIDAD NO RAIZ SIN CAMBIO DE RAIZ
     *
     */
    public List<UnidadTF> obtenerArbolUnidadesTF(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

        log.info("WS: Inicio obtenerArbolUnidadesTF");
        Long start = System.currentTimeMillis();

        List<Unidad> arbol = new ArrayList<Unidad>(); //Lista completa de unidades a enviar a regweb3(o porque es sincro o porque se han actualizado)
        Unidad unidad = null;
        Unidad unidadRaiz = null;

        //Miramos si se ha actualizado la unidad que nos pasan
        if (fechaActualizacion != null) { // es actualizacion
            log.info("ACTUALIZACION UNIDADES");
            //Obtenemos la unidad indicada en funcion de la fecha de actualización
            // de esta manera miramos si la unidad se ha actualizado
            unidad = unidadEjb.findUnidadActualizada(codigo, fechaActualizacion);
            if (unidad != null) { //Han actualizado la unidad
                unidadRaiz = unidad.getCodUnidadRaiz(); //obtenemos la raiz de la unidad que nos pasan
                // miramos que la unidad que nos pasan no esté extinguida o anulada antes de la primera sincro.
                if (unidadEjb.unidadValida(unidad, fechaSincronizacion)) {
                    arbol.add(unidad);
                    //obtenemos sus historicos para poder obtener todos los hijos que dependan de ellos
                    // y poder actualizar el árbol correctamente.
                    Set<Unidad> historicosRaiz = unidad.getHistoricoUO();
                    if (historicosRaiz != null) {
                        for (Unidad historico : historicosRaiz) {
                            //añadimos el histórico al arbol de actualizados.
                            arbol.add(historico);
                            if (unidad.equals(unidadRaiz)) { //Miramos si la unidad extinguida es raiz
                                //obtenemos el arbol de hijos de los historicos que la sustituyen.
                                arbol.addAll(unidadEjb.obtenerArbolUnidadesUnidadRaiz(historico.getCodigo(), fechaActualizacion, fechaSincronizacion));
                            } else {
                                //obtenemos el arbol de hijos de los historicos que la sustituyen.
                                arbol.addAll(unidadEjb.obtenerArbolUnidadesUnidadNoRaiz(historico.getCodigo(), fechaActualizacion, fechaSincronizacion));
                            }
                        }
                    }
                }
            }
        }


        if (unidad == null) { // Si la unidad es null es porque o es Sincro o es actualizacion pero con la raiz sin actualizar.
            //Obtenemos la unidad indicada
            unidad = unidadEjb.findUnidadEstado(codigo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            if (unidad != null) { // Si la unidad es distinta de null, hay que determinar si es raiz o no
                unidadRaiz = unidad.getCodUnidadRaiz(); //obtenemos su unidad raiz
                if (!unidad.equals(unidadRaiz)) {//Si la unidad es distinta de la unidad raiz se añade al arbol porque luego se obtienen solo sus hijos.
                    arbol.add(unidad);
                }
            }
        }

        List<UnidadTF> arbolTF = new ArrayList<UnidadTF>();
        if (unidad != null) {
            //obtenemos el arbol de la unidad que nos han indicado para que se actualice bien
            if (unidad.equals(unidadRaiz)) { // Caso que la unidad que nos indican es unidad raiz
                log.info("CASO UNIDAD QUE NOS PASAN ES RAIZ");
                arbol.addAll(unidadEjb.obtenerArbolUnidadesUnidadRaiz(codigo, fechaActualizacion, fechaSincronizacion));
                log.info("Numero TOTAL de unidades a actualizar: " + arbol.size());

            } else { // caso de que la unidad que nos indican no es raiz
                log.info("CASO UNIDAD QUE NOS PASAN NO ES RAIZ");
                arbol.addAll(unidadEjb.obtenerArbolUnidadesUnidadNoRaiz(codigo, fechaActualizacion, fechaSincronizacion));
                log.info("Numero TOTAL de unidades a actualizar: " + arbol.size());
            }

            //Montamos la lista de unidadesTF a enviar

            for (Unidad uni : arbol) {
                arbolTF.add(UnidadTF.generar(uni));
            }

            Long end = System.currentTimeMillis();
            log.info("tiempo obtenerArbolUnidadesTF: " + Utils.formatElapsedTime(end - start));

        } else {
            log.info("WS: La unidad con codigoDir3 " + codigo + " no existe o está extinguida");

        }
        return arbolTF;
    }


    /**
     * Método que devuelve la lista de {@link es.caib.dir3caib.persistence.model.ws.UnidadTF} a partir del
     * código indicado y que estan vigentes y tienen oficinas. Método que emplea la aplicación SISTRA para
     * saber donde enviar un registro telemático.
     *
     * @param codigo
     *          código de la unidad raiz
     *
     */
    @Override
    public List<UnidadTF> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception {

        List<Unidad> arbol = unidadEjb.obtenerArbolUnidadesDestinatarias(codigo);
        List<UnidadTF> arbolTF = new ArrayList<UnidadTF>();


        for (Unidad unidad : arbol) {
            arbolTF.add(UnidadTF.generarLigero(unidad));
        }

        return arbolTF;
    }

    /**
     * Método que devuelve la fecha de la última actualización de las unidades
     *
     * @return
     * @throws Exception
     */
    @Override
    public Date obtenerFechaUltimaActualizacion() throws Exception {

        Sincronizacion sincronizacion = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.DIRECTORIO);

        return sincronizacion.getFechaImportacion();
    }

}
