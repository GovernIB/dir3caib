package es.caib.dir3caib.back.controller.oficina;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.FechasForm;
import es.caib.dir3caib.back.form.OficinaBusquedaForm;
import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.persistence.ejb.*;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.Paginacion;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author mgonzalez
 * Date: 2/10/13
 */

@Controller
@RequestMapping(value = "/oficina")
public class OficinaController extends BaseController {

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    protected OficinaLocal oficinaEjb;

    @EJB(mappedName = "dir3caib/ContactoOfiEJB/local")
    protected ContactoOfiLocal contactoOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
    protected RelacionOrganizativaOfiLocal relOrgOfiEjb;

    @EJB(mappedName = "dir3caib/ServicioEJB/local")
    protected ServicioLocal servicioEjb;

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/ImportadorOficinasEJB/local")
    private ImportadorOficinasLocal importadorOficinas;

    @EJB(mappedName = "dir3caib/RelacionSirOfiEJB/local")
    protected RelacionSirOfiLocal relSirOfiEjb;

    @EJB(mappedName = "dir3caib/ArbolEJB/local")
    protected ArbolLocal arbolEjb;

    // Indicamos el formato de fecha dd/MM/yyyy hh:mm:ss
    SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    /**
     * Listado de los {@link es.caib.dir3caib.persistence.model.Oficina}
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listado() {
        return "redirect:/oficina/list";
    }


    /**
     * Carga el formulario para la busqueda de {@link es.caib.dir3caib.persistence.model.Oficina}
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {

        CatEstadoEntidad vigente = catEstadoEntidadEjb.findByCodigo(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        Oficina oficina = new Oficina();
        oficina.setEstado(vigente);

        OficinaBusquedaForm oficinaBusqueda = new OficinaBusquedaForm(oficina, 1);

        List<CatNivelAdministracion> administraciones = catNivelAdministracionEjb.getAll();
        List<CatComunidadAutonoma> comunidades = catComunidadAutonomaEjb.getAll();
        List<CatEstadoEntidad> estadosEntidad = catEstadoEntidadEjb.getAll();

        if(Configuracio.isCAIB()){
            oficinaBusqueda.getOficina().setNivelAdministracion(new CatNivelAdministracion(Dir3caibConstantes.NIVEL_ADMINISTRACION_AUTONOMICA));
            oficinaBusqueda.getOficina().setCodComunidad(new CatComunidadAutonoma(Dir3caibConstantes.CA_ILLES_BALEARS));
        }


        model.addAttribute("oficinaBusqueda", oficinaBusqueda);
        model.addAttribute("administraciones", administraciones);
        model.addAttribute("comunidades", comunidades);
        model.addAttribute("estadosEntidad", estadosEntidad);

        return "oficina/oficinaList";

    }


    /**
     * Realiza la busqueda de {@link es.caib.dir3caib.persistence.model.Oficina} según los parametros del formulario
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ModelAndView list(@ModelAttribute OficinaBusquedaForm busqueda) throws Exception {

        ModelAndView mav = new ModelAndView("oficina/oficinaList");

        Oficina oficina = busqueda.getOficina();

        Long codNivelAdministracion = (oficina.getNivelAdministracion() != null) ? oficina.getNivelAdministracion().getCodigoNivelAdministracion() : null;
        Long codComunidad = (oficina.getCodComunidad() != null) ? oficina.getCodComunidad().getCodigoComunidad() : null;
        Long codAmbProvincia = (oficina.getLocalidad() != null) ? oficina.getLocalidad().getProvincia().getCodigoProvincia() : null;
        String codEstado = (oficina.getEstado() != null) ? oficina.getEstado().getCodigoEstadoEntidad() : null;

        Paginacion paginacion = oficinaEjb.busqueda(busqueda.getPageNumber(),
                oficina.getCodigo(),
                oficina.getDenominacion(),
                codNivelAdministracion,
                codComunidad,
                codAmbProvincia, codEstado);

        busqueda.setPageNumber(1);

        mav.addObject("administraciones", catNivelAdministracionEjb.getAll());
        mav.addObject("comunidades", catComunidadAutonomaEjb.getAll());
        mav.addObject("estadosEntidad", catEstadoEntidadEjb.getAll());

        mav.addObject("paginacion", paginacion);
        mav.addObject("oficinaBusqueda", busqueda);

        return mav;

    }


    /**
     * Muestra los ficheros de oficinas que hay en el directorio
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ficheros", method = RequestMethod.GET)
    public ModelAndView ficherosList(HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("/oficina/oficinaFicheros");
        ArrayList<String> existentes = new ArrayList<String>();
        // Obtenemos el listado de ficheros que hay dentro del directorio indicado
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.OFICINA);


        if (descarga != null) {
            File f = new File(Configuracio.getOficinasPath(descarga.getCodigo()));
            if (f.exists()) {
                existentes = new ArrayList<String>(Arrays.asList(f.list()));
                // Miramos si debemos mostrar el botón de importación,
                // solo se muestra si la fecha de Inicio descarga es superior a la fechaImportacion
                Date fechaInicio = descarga.getFechaInicio();
                Date fechaImportacion = descarga.getFechaImportacion();

                if (fechaImportacion != null) {
                    if (fechaInicio != null) {
                        if (fechaInicio.after(fechaImportacion)) {
                            mav.addObject("mostrarimportacion", "mostrarImportacion");
                        }
                    }
                } else {
                    mav.addObject("mostrarimportacion", "mostrarImportacion");
                }

                //mav.addObject("descarga", descarga);
            } else {
                Mensaje.saveMessageError(request, getMessage("descarga.error.importante"));
            }
        }
        mav.addObject("descarga", descarga);
        mav.addObject("existentes", existentes);

        return mav;
    }

    /**
     * Muestra el formulario para obtener las oficinas mediante el WS de DIR3
     */
    @RequestMapping(value = "/obtener", method = RequestMethod.GET)
    public String obtenerOficinas(Model model) throws Exception {

        Descarga descarga = descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.OFICINA);
        if (descarga != null) {
            model.addAttribute("descarga", descarga);
        }
        model.addAttribute("development", Configuracio.isDevelopment());
        model.addAttribute("oficina", new FechasForm());

        return "/oficina/oficinaObtener";
    }

    /**
     * Obtiene las oficinas mediante el WS de DIR3
     */
    @RequestMapping(value = "/obtener", method = RequestMethod.POST)
    public String descargaOficinas(@ModelAttribute FechasForm fechasForm, HttpServletRequest request) throws Exception {

        if (descargarOficinasWS(request, fechasForm.getFechaInicio(), fechasForm.getFechaFin())) {
            return "redirect:/oficina/ficheros ";
        } else {
            return "redirect:/oficina/obtener";
        }
    }

    /**
     * Importa el contenido de un fichero de las Oficinas a la bbdd
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/importar", method = RequestMethod.GET)
    public ModelAndView importarOficinas(HttpServletRequest request, Boolean sincro) throws Exception {

        ModelAndView mav = new ModelAndView("/oficina/oficinaImportacion");

        long start = System.currentTimeMillis();

        ResultadosImportacion resultados;
        resultados = importadorOficinas.importarOficinas(sincro == null ? false : sincro);

        long end = System.currentTimeMillis();
        log.info("Importat oficinas en " + Utils.formatElapsedTime(end - start));

        System.gc();

        Mensaje.saveMessageInfo(request, getMessage("oficina.importacion.ok"));
        mav.addObject("procesados", resultados.getProcesados());
        mav.addObject("ficheros", Dir3caibConstantes.OFI_FICHEROS);
        mav.addObject("existentes", resultados.getExistentes());
        mav.addObject("descarga", resultados.getDescarga());

        return mav;
    }

    /**
     * Elimina los ficheros de las oficinas del sistema de archivos
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public ModelAndView eliminarOficinasCompleto(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("/oficina/oficinaFicheros");

        try {
            Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.OFICINA);
            File directorio = new File(Configuracio.getOficinasPath(descarga.getCodigo()));
            relSirOfiEjb.deleteAll();
            relOrgOfiEjb.deleteAll();
            contactoOfiEjb.deleteAll();
            oficinaEjb.deleteHistoricosOficina();
            oficinaEjb.deleteServiciosOficina();
            oficinaEjb.deleteAll();
            servicioEjb.deleteAll();
            descargaEjb.deleteAllByTipo(Dir3caibConstantes.OFICINA);

            FileUtils.cleanDirectory(directorio);
            Mensaje.saveMessageInfo(request, getMessage("oficina.borrar.ok"));
        } catch (Exception ex) {
            Mensaje.saveMessageError(request, getMessage("dir3caib.borrar.directorio.error"));
            ex.printStackTrace();
        }

        return mav;
    }


    /**
     * Sincroniza las oficinas.Obtiene las oficinas y sus relaciones a traves de WS desde la última fecha de
     * sincronización e importa los datos.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/sincronizar", method = RequestMethod.GET)
    public ModelAndView sincronizarOficinas(HttpServletRequest request) {

        try {
            //Establecemos la fecha de hoy
            Date hoy = new Date();

            //Obtenemos las ultimas descargas de Unidades y Oficinas
            Descarga ultimaDescargaUnidad = descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.UNIDAD);
            Descarga ultimaDescargaOficina = descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.OFICINA);

            // Controlamos que no se puedan sincronizar las oficinas antes que las unidades.
            // Para ello comprobamos que la fecha de importación de las unidades no sea anterior
            // a la fecha de la sincro de las oficinas (hoy)
            if (Utils.isAfterDay(hoy, ultimaDescargaUnidad.getFechaImportacion())) {
                Mensaje.saveMessageError(request, getMessage("oficina.sincronizacion.nosepuede"));
            } else {
                final Boolean sincronizacion = true;

                // Obtenemos los archivos por WS
                boolean descargaOk = descargarOficinasWS(request, ultimaDescargaOficina.getFechaFin(), hoy);
                // Importamos los datos a la BD.
                if (descargaOk) {
                    return importarOficinas(request, sincronizacion);
                }

            }

        } catch (Exception ex) {
            Mensaje.saveMessageError(request, getMessage("oficina.sincronizacion.error"));
            ex.printStackTrace();
            return new ModelAndView("redirect:/inicio");
        }
        return new ModelAndView("/oficina/oficinaImportacion");
    }

    /*
     * Método que se encarga de obtener los archivos de las oficinas a través de WS
     * @param request
     * @param fechaInicio
     * @param fechaFin
     */
    public boolean descargarOficinasWS(HttpServletRequest request, Date fechaInicio, Date fechaFin) throws Exception {

        try {
            String[] respuesta = importadorOficinas.descargarOficinasWS(fechaInicio, fechaFin);
            if (Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO.equals(respuesta[0])) {
                Mensaje.saveMessageInfo(request, getMessage("oficina.descarga.ok"));
                return true;
            } else {
                if (Dir3caibConstantes.CODIGO_RESPUESTA_VACIO.equals(respuesta[0])) {
                    Mensaje.saveMessageInfo(request, getMessage("oficina.nueva.nohay"));
                    return true;
                } else {
                    Mensaje.saveMessageError(request, getMessage("oficina.descarga.nook") + ": " + respuesta[1]);
                    return false;
                }

            }
        } catch (IOException ex) {
            Mensaje.saveMessageError(request, getMessage("oficina.descomprimir.error"));
            ex.printStackTrace();
            return false;
        } catch (Exception e) {
            Mensaje.saveMessageError(request, getMessage("oficina.descarga.nook"));
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Método que obtiene el árbol de oficinas de una oficina
     *
     * @param request
     * @param idOficina
     * @return
     */
    @RequestMapping(value = "/{idOficina}/arbol", method = RequestMethod.GET)
    public ModelAndView mostrarArbolOficinas(HttpServletRequest request, @PathVariable String idOficina) throws Exception {

        ModelAndView mav = new ModelAndView("/arbolList");
        Nodo nodo = new Nodo();
        Oficina oficina = oficinaEjb.findByCodigoLigero(idOficina);
        arbolEjb.arbolOficinas(idOficina, nodo, oficina.getEstado().getCodigoEstadoEntidad());
        mav.addObject("nodo", nodo);
        mav.addObject("oficinas", "oficinas");

        return mav;

    }


    /**
     * Método que se encarga de listar todas las descargas que se han realizado del catálogo
     */
    @RequestMapping(value = "/descarga/list", method = RequestMethod.GET)
    public String listadoDescargaOficina() {

        return "redirect:/oficina/descarga/list/1";
    }

    /**
     * Listado de tipos de asunto
     *
     * @param pageNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/descarga/list/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView descargaOficinaList(@PathVariable Integer pageNumber) throws Exception {

        ModelAndView mav = new ModelAndView("/descargaList");

        List<Descarga> listado = descargaEjb.getPaginationByTipo(((pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION), Dir3caibConstantes.OFICINA);

        Long total = descargaEjb.getTotalByTipo(Dir3caibConstantes.OFICINA);

        Paginacion paginacion = new Paginacion(total.intValue(), pageNumber);

        mav.addObject("paginacion", paginacion);
        mav.addObject("listado", listado);
        mav.addObject("elemento", "oficina");

        return mav;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor dateEditor = new CustomDateEditor(sdf, true);

        binder.registerCustomEditor(java.util.Date.class, dateEditor);
    }

}
