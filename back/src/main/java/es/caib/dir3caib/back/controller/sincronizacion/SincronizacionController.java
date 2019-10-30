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
        Sincronizacion ultimaSincroDirectorio = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.UNIDADES_OFICINAS);
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

        // Obtenemos el número de descargas de Directorio y Catálogo
        Long sincronizacionesDirectorio = sincronizacionEjb.contarSincronizaciones(Dir3caibConstantes.UNIDADES_OFICINAS);
        Long sincronizacionesCatalogo = sincronizacionEjb.contarSincronizaciones(Dir3caibConstantes.CATALOGO);
        mav.addObject("sincronizacionesCatalogo", sincronizacionesCatalogo);
        mav.addObject("sincronizacionesDirectorio", sincronizacionesDirectorio);

        return mav;
    }

    /**
     * Sincroniza el Directorio
     * Obtiene todos los datos del Catálogo, las Unidades y Oficinas para importarlos.
     *
     * @param request
     */
    @RequestMapping(value = "/directorio", method = RequestMethod.GET)
    public ModelAndView sincronizarDirectorio(HttpServletRequest request) {

        try {

            long start = System.currentTimeMillis();
            List<Sincronizacion> sincros = sincronizacionEjb.sincronizarDirectorio();
            log.info("Sincronizacion del Directoro completada en " + Utils.formatElapsedTime(System.currentTimeMillis() - start));

            // Mensajes al usuario
            for (Sincronizacion sincro : sincros) {

                if(sincro != null){
                    if(sincro.getTipo().equals(Dir3caibConstantes.UNIDADES_OFICINAS)){

                        if(sincro.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_CORRECTA)){
                            Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.ok"));
                        }else if(sincro.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_VACIA)){
                            Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.vacia"));
                        }

                    }else if(sincro.getTipo().equals(Dir3caibConstantes.CATALOGO)){

                        if(sincro.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_CORRECTA)){
                            Mensaje.saveMessageInfo(request, getMessage("catalogo.sincronizacion.ok"));
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
     * Sincroniza las unidades y oficinas
     * Obtiene las Unidades y Oficinas para importarlos.
     *
     * @param request
     */
    @RequestMapping(value = "/oficinasUnidades", method = RequestMethod.GET)
    public ModelAndView sincronizaroficinasUnidades(HttpServletRequest request) {

        try {

            long start = System.currentTimeMillis();
            Sincronizacion sincronizacion = sincronizacionEjb.sincronizarUnidadesOficinas();
            log.info("Sincronizacion de las Oficinas y Unidades completada en " + Utils.formatElapsedTime(System.currentTimeMillis() - start));

            // Mensajes al usuario
            if(sincronizacion != null){

                if(sincronizacion.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_CORRECTA)){
                    Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.ok"));
                }else if(sincronizacion.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_VACIA)){
                    Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.vacia"));
                }

            }else {
                Mensaje.saveMessageError(request, getMessage("directorio.descarga.error"));
            }


        } catch (Exception ex) {
            Mensaje.saveMessageError(request, getMessage("directorio.sincronizacion.error"));
            ex.printStackTrace();
        }

        return new ModelAndView("redirect:/sincronizacion/list");
    }

    /**
     * Sincroniza el Catalogo
     * Obtiene el catalogo para importarlo.
     *
     * @param request
     */
    @RequestMapping(value = "/catalogo", method = RequestMethod.GET)
    public ModelAndView sincronizarCatalogo(HttpServletRequest request) {

        try {

            long start = System.currentTimeMillis();
            Sincronizacion sincronizacion = sincronizacionEjb.sincronizarCatalogo();
            log.info("Sincronizacion del Catalogo completado en " + Utils.formatElapsedTime(System.currentTimeMillis() - start));

            // Mensajes al usuario
            if(sincronizacion != null){

                if(sincronizacion.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_CORRECTA)){
                    Mensaje.saveMessageInfo(request, getMessage("catalogo.sincronizacion.ok"));
                }else if(sincronizacion.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_VACIA)){
                    Mensaje.saveMessageInfo(request, getMessage("catalogo.sincronizacion.vacia"));
                }else if(sincronizacion.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_ERROR_DESCARGA)){
                    Mensaje.saveMessageInfo(request, getMessage("catalogo.descarga.error"));
                }

            }else {
                Mensaje.saveMessageError(request, getMessage("catalogo.descarga.error"));
            }


        } catch (Exception ex) {
            Mensaje.saveMessageError(request, getMessage("catalogo.sincronizacion.error"));
            ex.printStackTrace();
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
            Sincronizacion ultimaSincroDirectorio = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.UNIDADES_OFICINAS);
            Sincronizacion ultimaSincroCatalogo = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.CATALOGO);

            Sincronizacion sincronizacion = sincronizacionEjb.findById(idSincronizacion);


            // Comprobamos que no se trate de la última sincronización correcta
            if (ultimaSincroDirectorio != null && ultimaSincroCatalogo != null && sincronizacion != null) {
                if (sincronizacion.getCodigo().equals(ultimaSincroDirectorio.getCodigo()) || sincronizacion.getCodigo().equals(ultimaSincroCatalogo.getCodigo())) {
                    Mensaje.saveMessageError(request, getMessage("sincronizacion.eliminar.correcta"));
                    return "redirect:/sincronizacion/list/1";
                }
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
     * Eliminar todas las {@link es.caib.dir3caib.persistence.model.Sincronizacion} excepto las últimas correctas
     */
    @RequestMapping(value = "/deleteAll")
    public String eliminarTodasSincronizacion(HttpServletRequest request) {

        try {

            // Obtenemos todas las sincronizaciones menos las últimas correctas
            Sincronizacion ultimaSincroDirectorio = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.UNIDADES_OFICINAS);
            Sincronizacion ultimaSincroCatalogo = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.CATALOGO);
            // Obtenemos todas las sincronizaciones
            List<Sincronizacion> sincronizacionesTodas = sincronizacionEjb.getAll();
            // Obtenemos el número de descargas de Directorio y Catálogo
            Long sincronizacionesDirectorio = sincronizacionEjb.contarSincronizaciones(Dir3caibConstantes.UNIDADES_OFICINAS);
            Long sincronizacionesCatalogo = sincronizacionEjb.contarSincronizaciones(Dir3caibConstantes.CATALOGO);


            if (ultimaSincroDirectorio != null && ultimaSincroCatalogo != null) {

                if (sincronizacionesCatalogo > 1 || sincronizacionesDirectorio > 1) {

                    for (Sincronizacion sincro : sincronizacionesTodas){

                        // Comprobamos que no se trate de las últimas sincronizaciones correctas
                        if(!sincro.getCodigo().equals(ultimaSincroDirectorio.getCodigo()) && !sincro.getCodigo().equals(ultimaSincroCatalogo.getCodigo())){
                            // Eliminamos la sincronización y sus ficheros asociados
                            sincronizacionEjb.eliminarSincronizacion(sincro);
                        }

                    }
                } else{
                    Mensaje.saveMessageError(request, getMessage("sincronizacion.eliminar.imposible"));
                    return "redirect:/sincronizacion/list/1";
                }
            }else{
                Mensaje.saveMessageError(request, getMessage("sincronizacion.eliminar.imposible"));
                return "redirect:/sincronizacion/list/1";
            }

            Mensaje.saveMessageInfo(request, getMessage("dir3caib.eliminar.registros"));
            return "redirect:/sincronizacion/list/1";


        } catch (Exception e) {
            Mensaje.saveMessageError(request, getMessage("sincronizacion.eliminar.error"));
            e.printStackTrace();
        }

        return "redirect:/sincronizacion/list/1";
    }


    /**
     * Elimina todas las Oficinas y Unidades de la bbdd y las sincroniza con la información actual
     */
    @RequestMapping(value = "/restaurarOficinasUnidades", method = RequestMethod.GET)
    public String restaurarOficinasUnidades(Model model) throws Exception {

        return "sincronizacion/restaurarDirectorio";
    }


    /**
     * Elimina todas las Oficinas y Unidades de la bbdd y las sincroniza con la información actual
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/restaurarOficinasUnidades", method = RequestMethod.POST)
    public ModelAndView restaurarOficinasUnidades(HttpServletRequest request) throws Exception {

        try{
            // Elimina las Oficinas y Unidades, realiza una descarga inicia e importa los datos
            dir3CaibEjb.restaurarUnidadesOficinas();

            Mensaje.saveMessageInfo(request, getMessage("directorio.sincronizacion.ok"));

        }catch (Exception e){
            e.printStackTrace();
            Mensaje.saveMessageError(request, getMessage("directorio.sincronizacion.error"));
        }

        return new ModelAndView("redirect:/sincronizacion/list");

    }

}
