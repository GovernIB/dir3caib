package es.caib.dir3caib.back.controller.sincronizacion;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.persistence.ejb.BaseEjbJPA;
import es.caib.dir3caib.persistence.ejb.Dir3CaibLocal;
import es.caib.dir3caib.persistence.ejb.SincronizacionLocal;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Sincronizacion;
import es.caib.dir3caib.persistence.utils.Paginacion;
import es.caib.dir3caib.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author mgonzalez
 * Date: 2/10/13
 */

@Controller
@RequestMapping(value = "/sincronizacion")
public class SincronizacionController extends BaseController {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/SincronizacionEJB/local")
    private SincronizacionLocal sincronizacionEjb;

    @EJB(mappedName = "dir3caib/Dir3CaibEJB/local")
    private Dir3CaibLocal dir3CaibEjb;


    /**
     * Método que se encarga de listar todas las descargas que se han realizado de las unidades
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listadoDescargaUnidad() {

        return "redirect:/sincronizacion/list/1";
    }

    /**
     * Listado de las descargas realizadas de las unidades
     *
     * @param pageNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView descargaUnidadList(@PathVariable Integer pageNumber) throws Exception {

        ModelAndView mav = new ModelAndView("/sincronizacion/sincronizacionList");

        // Obtenemos descarga correcta de Directorio y Catálogo
        Sincronizacion ultimaSincroDirectorio = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.DIRECTORIO);
        Sincronizacion ultimaSincroCatalogo = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.CATALOGO);

        //Obtenemos el listado paginado de las sincronizaciones
        List<Sincronizacion> listado = sincronizacionEjb.getPagination(((pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION));
        Long total = sincronizacionEjb.getTotal();

        //Obtenemos todos los ficheros asociados a cada descarga
        if (listado != null) {
            for (Sincronizacion sincronizacion : listado) {

                sincronizacion.obtenerFicheros();

            }
        }

        Paginacion paginacion = new Paginacion(total.intValue(), pageNumber);

        mav.addObject("paginacion", paginacion);
        mav.addObject("listado", listado);
        mav.addObject("ultimaSincroDirectorio", ultimaSincroDirectorio);
        mav.addObject("ultimaSincroCatalogo", ultimaSincroCatalogo);

        return mav;
    }

    /**
     * Sincroniza el Directorio
     * Obtiene todos los datos de las Unidades y Oficinas para importarlos.
     *
     * @param request
     */
    @RequestMapping(value = "/sincronizar", method = RequestMethod.GET)
    public ModelAndView sincronizarDirectorio(HttpServletRequest request) {

        try {

            long start = System.currentTimeMillis();
            List<Sincronizacion> sincros = sincronizacionEjb.sincronizarDirectorio();
            log.info("Sincronizacion del Directoro completada en " + Utils.formatElapsedTime(System.currentTimeMillis() - start));

            // Mensajes al usuario
            for (Sincronizacion sincro : sincros) {

                if(sincro != null){
                    if(sincro.getTipo().equals(Dir3caibConstantes.DIRECTORIO)){

                        if(sincro.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_CORRECTA)){
                            Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.ok"));
                        }else if(sincro.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_VACIA)){
                            Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.vacia"));
                        }

                    }else if(sincro.getTipo().equals(Dir3caibConstantes.CATALOGO)){

                        if(sincro.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_CORRECTA)){
                            Mensaje.saveMessageInfo(request, getMessage("catalogo.sincronizacion.ok"));
                        }else if(sincro.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_VACIA)){
                            Mensaje.saveMessageInfo(request, getMessage("catalogo.sincronizacion.vacia"));
                        }

                    }
                }else {

                    Mensaje.saveMessageError(request, getMessage("directorio.descarga.error"));
                }

            }

        } catch (Exception ex) {
            Mensaje.saveMessageError(request, getMessage("directorio.sincronizacion.error"));
            ex.printStackTrace();
        }

        return new ModelAndView("redirect:/sincronizacion/list");
    }

    /**
     * Sincroniza el Catálofo
     * Obtiene todos los datos del Catálogo para importarlos.
     *
     * @param request
     */
    @RequestMapping(value = "/catalogo", method = RequestMethod.GET)
    public ModelAndView sincronizarCatalogo(HttpServletRequest request) {

        Sincronizacion sincronizacion = null;

        try {

            sincronizacion = sincronizacionEjb.descargarCatalogoWS( null);

            //Mostramos los mensajes en función de la respuesta del WS de Madrid
            if(sincronizacion != null){

                // Si la descarga de datos es correcta, procedemos a realizar la sincronización de datos
                if (sincronizacion.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {

                    long start = System.currentTimeMillis();

                    sincronizacionEjb.importarCatalogo(sincronizacion);

                    log.info("Sincronizacion del catalogo completada en " + Utils.formatElapsedTime(System.currentTimeMillis() - start));

                    Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.ok"));

                }else if (sincronizacion.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_VACIA)) { // No ha devuelto datos
                    Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.vacia"));
                }

            }else{
                Mensaje.saveMessageError(request, getMessage("directorio.descarga.error"));
                return new ModelAndView("redirect:/sincronizacion/list");

            }

        } catch (Exception ex) {
            // Si ha habido un Error en la sincronización, modificamos el estado de la descarga
            if(sincronizacion != null){
                try {
                    sincronizacionEjb.actualizarEstado(sincronizacion.getCodigo(), Dir3caibConstantes.SINCRONIZACION_ERRONEA);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Mensaje.saveMessageError(request, getMessage("directorio.sincronizacion.error"));
            ex.printStackTrace();

            return new ModelAndView("redirect:/sincronizacion/list");
        }

        return new ModelAndView("redirect:/sincronizacion/list");
    }

    /**
     * Eliminar una {@link es.caib.dir3caib.persistence.model.Sincronizacion}
     */
    @RequestMapping(value = "/{idSincronizacion}/delete")
    public String eliminarSincronizacion(@PathVariable Long idSincronizacion, HttpServletRequest request) {

        try {

            // Obtenemos la última sincronizacion correcta
            Sincronizacion ultimaSincroDirectorio = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.DIRECTORIO);
            Sincronizacion ultimaSincroCatalogo = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.CATALOGO);

            Sincronizacion sincronizacion = sincronizacionEjb.findById(idSincronizacion);

            // Comprobamos que no se trate de la última sincronización correcta
            if(sincronizacion.getCodigo().equals(ultimaSincroDirectorio.getCodigo()) || sincronizacion.getCodigo().equals(ultimaSincroCatalogo.getCodigo())){
                Mensaje.saveMessageError(request, getMessage("sincronizacion.eliminar.correcta"));
                return "redirect:/sincronizacion/list/1";
            }

            // Eliminamos la sincronización y sus ficheros asociados
            sincronizacionEjb.eliminarSincronizacion(sincronizacion);

            Mensaje.saveMessageInfo(request, getMessage("dir3caib.eliminar.registro"));

        } catch (Exception e) {
            Mensaje.saveMessageError(request, getMessage("sincronizacion.eliminar.error"));
            e.printStackTrace();
        }

        return "redirect:/sincronizacion/list/1";
    }


    /**
     * Elimina el Directório de la bbdd y las sincroniza con al información actual
     */
    @RequestMapping(value = "/restaurarDirectorio", method = RequestMethod.GET)
    public String restaurarDirectorio(Model model) throws Exception {

        return "sincronizacion/restaurarDirectorio";
    }


    /**
     * Elimina todas las Unidades de la bbdd y las sincroniza con al información actual
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/restaurarDirectorio", method = RequestMethod.POST)
    public ModelAndView restaurarDirectorio(HttpServletRequest request) throws Exception {

        try{
            // Restaurar el Directório
            dir3CaibEjb.restaurarDirectorio();

            Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.ok"));

        }catch (Exception e){
            e.printStackTrace();
            Mensaje.saveMessageError(request, getMessage("directorio.sincronizacion.error"));
        }

        return new ModelAndView("redirect:/sincronizacion/list");

    }

}
