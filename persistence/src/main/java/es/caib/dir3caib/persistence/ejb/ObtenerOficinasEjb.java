package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.ws.OficinaTF;
import es.caib.dir3caib.utils.Utils;
import org.apache.log4j.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author mgonzalez
 * Date: 12/02/14
 */
@Stateless(name = "ObtenerOficinasEJB")
@RunAs("DIR_ADMIN")  //todo añadir seguridad
public class ObtenerOficinasEjb implements ObtenerOficinasLocal {

    protected final Logger log = Logger.getLogger(getClass());
    
    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    private OficinaLocal oficinaEjb;

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    private UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/SincronizacionEJB/local")
    private SincronizacionLocal sincronizacionEjb;
    
    
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    /**
     * Obtiene los datos de una {@link es.caib.dir3caib.persistence.model.ws.OficinaTF}  en función del codigo y la
     * fecha de actualización. Si la fecha de actualización es inferior a la de
     * importación con Madrid se supone que no ha cambiado y se envia null
     *
     * @param codigo
     *          Código de la oficina
     * @param fechaActualizacion
     *          fecha en la que se realiza la actualizacion.
     */
    @Override
    public OficinaTF obtenerOficina(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

        Oficina oficina = oficinaEjb.findById(codigo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        if (oficina != null) {
            OficinaTF oficinaTF = null;

            List<ContactoOfi> contactosVisibles = new ArrayList<ContactoOfi>();
            for (ContactoOfi contactoOfi : oficina.getContactos()) {
                if (contactoOfi.isVisibilidad()) {
                    contactosVisibles.add(contactoOfi);
                }
            }
            oficina.setContactos(contactosVisibles);
            if (fechaActualizacion != null) {

                if (fechaActualizacion.before(oficina.getFechaImportacion())) {

                    // Cogemos solo las relaciones organizativas posteriores a la fecha de sincronizacion
                    Set<RelacionOrganizativaOfi> todasRelaciones = new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());
                    Set<RelacionOrganizativaOfi> relacionesValidas = new HashSet<RelacionOrganizativaOfi>();
                    for (RelacionOrganizativaOfi relOrg : todasRelaciones) {
                        //TODO revisar esta condicion
                        if (relOrg.getUnidad().getFechaExtincion().before(fechaSincronizacion)) {
                            relacionesValidas.add(relOrg);
                        }
                    }
                    oficina.setOrganizativasOfi(null);
                    oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesValidas));

                    oficinaTF = OficinaTF.generar(oficina);
                }
            } else {
                oficinaTF = OficinaTF.generar(oficina);
            }

            return oficinaTF;
        } else {
            log.info("WS: Oficina cuyo codigoDir3 es " + codigo + " no existe");
            return null;
        }


    }


    /**
     *
     * Obtiene todas las {@link es.caib.dir3caib.persistence.model.ws.OficinaTF} cuyo organismo responsable es el indicado por código(son todas padres e hijas).Solo se envian aquellas
     * que han sido actualizadas controlando que la unidad del código que nos pasan se haya podido actualizar también.
     * Esto es debido a que cuando en Madrid actualizan una unidad la tendencia es extinguirla y crear una nueva con código diferente.
     * Esto hace que se tengan que traer las oficinas de la vieja y de la nueva.
     *
     * @param codigo             Código del organismo
     * @param fechaActualizacion fecha en la que se realiza la actualizacion.
     */
    @Override
    public List<OficinaTF> obtenerArbolOficinas(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

        log.info("WS: Inicio obtener Oficinas");
        // Obtenemos todos las unidades vigentes de la unidad Raiz

        Long start = System.currentTimeMillis();

        List<Unidad> unidades = new ArrayList<Unidad>();
        Unidad unidad = null;
        //unidades.add(unidadEjb.obtenerUnidad(codigo)); // Añadimos la raiz
        if (fechaActualizacion != null) { // ES actualizacion, miramos si la raiz se ha actualizado
            log.info("ACTUALIZACION OFICINAS");
            //Obtenemos la raiz en funcion de la fecha de actualización
            unidad = unidadEjb.findUnidadActualizada(codigo, fechaActualizacion);
            if (unidad != null) { //Han actualizado la raiz
                // miramos que no esté extinguida o anulada antes de la primera sincro.
                if (unidadEjb.unidadValida(unidad, fechaSincronizacion)) {
                    unidades.add(unidad);
                    Set<Unidad> historicosRaiz = unidad.getHistoricoUO();
                    if (historicosRaiz != null) {
                        for (Unidad historico : historicosRaiz) {
                            unidades.add(historico);
                        }
                    }
                }
            }
        }


        if (unidad == null) { // O es Sincro o es actualizacion pero con la raiz sin actualizar.
            unidad = unidadEjb.findUnidadEstado(codigo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            if (unidad != null) {
                //Añadimos la unidad para que se obtengan sus oficinas
                unidades.add(unidad);
            }
        }

        unidades.addAll(unidadEjb.obtenerArbol(codigo));
        log.info("Total arbol: " + unidades.size());

        List<Oficina> oficinasCompleto = new ArrayList<Oficina>();

        // Por cada Unidad, obtenemos sus Oficinas
        for (Unidad uni : unidades) {
            List<Oficina> oficinas = oficinaEjb.obtenerOficinasOrganismo(uni.getCodigo(), fechaActualizacion, fechaSincronizacion);
            oficinasCompleto.addAll(oficinas);
        }

        // Convertimos las Oficinas en OficinaTF
        List<OficinaTF> arbolTF = new ArrayList<OficinaTF>();
        for (Oficina oficina : oficinasCompleto) {
            arbolTF.add(OficinaTF.generar(oficina));
        }

        Long end = System.currentTimeMillis();
        log.info("tiempo obtenerArbolOficinas: " + Utils.formatElapsedTime(end - start));
        return arbolTF;
    }

    /**
     * Obtiene el listado de oficinas Sir de una Unidad
     *
     * @param codigoUnidad Código de la unidad
     */
    @Override
    public List<OficinaTF> obtenerOficinasSIRUnidad(String codigoUnidad) throws Exception {

        List<Oficina> oficinas = oficinaEjb.obtenerOficinasSIRUnidad(codigoUnidad);

        List<OficinaTF> oficinasTF = new ArrayList<OficinaTF>();

        for (Oficina oficina : oficinas) {
            oficinasTF.add(OficinaTF.generar(oficina));
        }

        return oficinasTF;
    }

    /**
     * Método que devuelve la fecha de la última actualización de las unidades
     *
     * @return
     * @throws Exception
     */
    @Override
    public Date obtenerFechaUltimaActualizacion() throws Exception {

        Sincronizacion sincronizacion = sincronizacionEjb.ultimaSincronizacionCorrecta(Dir3caibConstantes.DIRECTORIO);

        return sincronizacion.getFechaImportacion();
    }

}
