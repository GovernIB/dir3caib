package es.caib.dir3caib.back.controller.oficina;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.OficinaBusquedaForm;
import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.persistence.ejb.ArbolLocal;
import es.caib.dir3caib.persistence.ejb.BaseEjbJPA;
import es.caib.dir3caib.persistence.ejb.ImportadorOficinasLocal;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.Paginacion;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;
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

    protected final Logger log = Logger.getLogger(getClass());



    @EJB(mappedName = "dir3caib/ImportadorOficinasEJB/local")
    private ImportadorOficinasLocal importadorOficinas;


    @EJB(mappedName = "dir3caib/ArbolEJB/local")
    private ArbolLocal arbolEjb;

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
     * Carga el formulario para la búsqueda de {@link es.caib.dir3caib.persistence.model.Oficina}
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {

        CatEstadoEntidad vigente = catEstadoEntidadEjb.findByCodigo(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        Oficina oficina = new Oficina();
        oficina.setEstado(vigente);
        //Establecemos el estado por defecto de la oficina
        OficinaBusquedaForm oficinaBusqueda = new OficinaBusquedaForm(oficina, 1);

        //Cargamos el conjunto de los valores posibles del formulario
        List<CatNivelAdministracion> administraciones = catNivelAdministracionEjb.getAll();
        List<CatComunidadAutonoma> comunidades = catComunidadAutonomaEjb.getAll();
        List<CatEstadoEntidad> estadosEntidad = catEstadoEntidadEjb.getAll();

        // Valores por defecto en la Búsqueda
        String administracion = Configuracio.getBusquedaAdministracion();
        String comunidadAutonoma = Configuracio.getBusquedaComunidad();

        //Asignamos los valores por defecto a la búsqueda
        if(administracion != null){
            oficinaBusqueda.getOficina().setNivelAdministracion(new CatNivelAdministracion(Long.valueOf(administracion.trim())));
        }

        if(comunidadAutonoma != null){
            oficinaBusqueda.getOficina().setCodComunidad(new CatComunidadAutonoma(Long.valueOf(comunidadAutonoma.trim())));
        }

        model.addAttribute("oficinaBusqueda", oficinaBusqueda);
        model.addAttribute("administraciones", administraciones);
        model.addAttribute("comunidades", comunidades);
        model.addAttribute("estadosEntidad", estadosEntidad);

        return "oficina/oficinaList";

    }


    /**
     * Realiza la búsqueda de {@link es.caib.dir3caib.persistence.model.Oficina} según los parámetros del formulario
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ModelAndView list(@ModelAttribute OficinaBusquedaForm busqueda) throws Exception {

        ModelAndView mav = new ModelAndView("oficina/oficinaList");

        Oficina oficina = busqueda.getOficina();

        //Cogemos los valores del formulario
        Long codNivelAdministracion = (oficina.getNivelAdministracion() != null) ? oficina.getNivelAdministracion().getCodigoNivelAdministracion() : null;
        Long codComunidad = (oficina.getCodComunidad() != null) ? oficina.getCodComunidad().getCodigoComunidad() : null;
        Long codAmbProvincia = (oficina.getLocalidad() != null) ? oficina.getLocalidad().getProvincia().getCodigoProvincia() : null;
        String codEstado = (oficina.getEstado() != null) ? oficina.getEstado().getCodigoEstadoEntidad() : null;

        //Realizamos la búsqueda paginada
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
     * Muestra los ficheros de oficinas que hay en el directorio de la última descarga
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ficheros", method = RequestMethod.GET)
    public ModelAndView ficherosList(HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("/oficina/oficinaFicheros");
        ArrayList<String> ficheros = new ArrayList<String>();
        // Obtenemos la última descarga
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.OFICINA);

        if (descarga != null) {
            File f = new File(Configuracio.getOficinasPath(descarga.getCodigo()));
            if (f.exists()) { // Si hay ficheros, se muestran
                ficheros = new ArrayList<String>(Arrays.asList(f.list()));
            } else { // Si no, mensaje de error
                Mensaje.saveMessageError(request, getMessage("descarga.error.importante"));
            }
        }
        mav.addObject("descarga", descarga);
        mav.addObject("ficheros", ficheros);

        return mav;
    }

    /**
     * Muestra el formulario para obtener las oficinas mediante el WS de DIR3
     */
    @RequestMapping(value = "restaurarDirectorio", method = RequestMethod.GET)
    public String restaurarOficinas(Model model) throws Exception {

        model.addAttribute("development", Configuracio.isDevelopment());
        model.addAttribute("oficinaForm", new Oficina());

        return "oficina/oficinaRestaurar";
    }

    /**
     * Obtiene las oficinas mediante el WS de DIR3
     */
    @RequestMapping(value = "/restaurarDirectorio", method = RequestMethod.POST)
    public ModelAndView restaurarOficinas(@ModelAttribute Oficina oficinaForm, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("/oficina/oficinaImportacion");

        // Restaurar las Oficinas
        ResultadosImportacion results = importadorOficinas.restaurarOficinas();

        if(results != null){

            Mensaje.saveMessageInfo(request, getMessage("oficina.importacion.ok"));
            mav.addObject("procesados", results.getProcesados());// Nombre de los ficheros procesados
            mav.addObject("ficheros", Dir3caibConstantes.OFI_FICHEROS);//Nombre de los ficheros obtenidos
            mav.addObject("existentes", results.getExistentes());//Nombre de los ficheros que realmente han venido en la descarga
            mav.addObject("descarga", results.getDescarga());//Datos de la descarga

            return mav;

        }else{
            Mensaje.saveMessageError(request, getMessage("oficina.descarga.nook"));

            return new ModelAndView("redirect:/oficina/restaurarDirectorio");
        }
    }

    /**
     * Importa  todas  las oficinas, históricos, contactos, relaciones organizativas, relaciones SIR, servicios a la BD.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/importar", method = RequestMethod.GET)
    public ModelAndView importarOficinas(HttpServletRequest request, Boolean sincro) throws Exception {

        ModelAndView mav = new ModelAndView("/oficina/oficinaImportacion");

        long start = System.currentTimeMillis();

        ResultadosImportacion resultados;
        //Importamos a la BD todos los datos obtenidos de las oficinas y sus relaciones, se indica  si es una sincro o una actualización
        resultados = importadorOficinas.importarOficinas(sincro == null ? false : sincro);

        long end = System.currentTimeMillis();
        log.info("Importat oficinas en " + Utils.formatElapsedTime(end - start));

        Mensaje.saveMessageInfo(request, getMessage("oficina.importacion.ok"));
        mav.addObject("procesados", resultados.getProcesados()); // Nombre de los ficheros procesados
        mav.addObject("ficheros", Dir3caibConstantes.OFI_FICHEROS); //Nombre de los ficheros obtenidos
        mav.addObject("existentes", resultados.getExistentes()); //Nombre de los ficheros que realmente han venido en la descarga
        mav.addObject("descarga", resultados.getDescarga()); //Datos de la descarga

        return mav;
    }


    /**
     * Sincroniza las oficinas. Obtiene las oficinas y sus relaciones a traves de WS desde la última fecha de
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
            if (ultimaDescargaUnidad == null || Utils.isAfterDay(hoy, ultimaDescargaUnidad.getFechaImportacion())) {
                Mensaje.saveMessageError(request, getMessage("oficina.sincronizacion.nosepuede"));
            } else {
                final Boolean sincronizacion = true;
                boolean descargaOk = false;
                Date fechaFin = null;
                if (ultimaDescargaOficina != null) { // Si hay una descarga oficina sincronizada
                    fechaFin = ultimaDescargaOficina.getFechaFin(); //establecemos la fecha de inicio de la descarga como la fecha Fin de la última descarga
                    // Obtenemos los archivos por WS
                    descargaOk = descargarOficinasWS(request, fechaFin, hoy);
                } else {// es una descarga inicial(sincro)
                    // Obtenemos los archivos por WS
                    descargaOk = descargarOficinasWS(request, null, null);
                }

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
            //Invoca a los ws para obtener los archivos de las unidades
            String[] respuesta = importadorOficinas.descargarOficinasWS(fechaInicio, fechaFin);
            //Mostramos los mensajes en función de la respuesta del WS de Madrid
            if (Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO.equals(respuesta[0])) {
                Mensaje.saveMessageInfo(request, getMessage("oficina.descarga.ok"));
                return true;
            } else {
                if (Dir3caibConstantes.CODIGO_RESPUESTA_VACIO.equals(respuesta[0])) { //No ha devuelto datos
                    Mensaje.saveMessageInfo(request, getMessage("oficina.nueva.nohay"));
                    return true;
                } else { //Ha habido un error en la descarga
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
        //Obtenemos los datos básicos de la oficina que nos indican(suele ser la raíz del árbol)
        Oficina oficina = oficinaEjb.findByCodigoLigero(idOficina);
        //Obtenemos el árbol de oficinas
        arbolEjb.arbolOficinas(idOficina, nodo, oficina.getEstado().getCodigoEstadoEntidad());
        mav.addObject("nodo", nodo);
        mav.addObject("oficinas", "oficinas");

        return mav;

    }


    /**
     * Método que se encarga de listar todas las descargas que se han realizado de las oficinas
     */
    @RequestMapping(value = "/descarga/list", method = RequestMethod.GET)
    public String listadoDescargaOficina() {

        return "redirect:/oficina/descarga/list/1";
    }

    /**
     * Listado de las descargas de las oficinas
     *
     * @param pageNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/descarga/list/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView descargaOficinaList(@PathVariable Integer pageNumber) throws Exception {

        ModelAndView mav = new ModelAndView("/descargaList");

        //Obtenemos el listado paginado de las descargas
        List<Descarga> listado = descargaEjb.getPaginationByTipo(((pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION), Dir3caibConstantes.OFICINA);

        Long total = descargaEjb.getTotalByTipo(Dir3caibConstantes.OFICINA);

        ArrayList<String> ficheros = new ArrayList<String>();

        //Obtenemos todos los ficheros asociados a cada descarga
        if (listado != null) {
            for (Descarga descarga : listado) {
                File f = new File(Configuracio.getOficinasPath(descarga.getCodigo()));
                if (f.exists()) {
                    ficheros = new ArrayList<String>(Arrays.asList(f.list()));
                }
                descarga.setFicheros(ficheros);
            }
        }

        Paginacion paginacion = new Paginacion(total.intValue(), pageNumber);

        mav.addObject("paginacion", paginacion);
        mav.addObject("listado", listado);
        mav.addObject("elemento", "oficina");
        mav.addObject("ficheros", ficheros);

        return mav;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor dateEditor = new CustomDateEditor(sdf, true);

        binder.registerCustomEditor(java.util.Date.class, dateEditor);
    }

}
