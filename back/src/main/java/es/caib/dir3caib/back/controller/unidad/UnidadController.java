package es.caib.dir3caib.back.controller.unidad;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.UnidadBusquedaForm;
import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.persistence.ejb.ArbolLocal;
import es.caib.dir3caib.persistence.ejb.BaseEjbJPA;
import es.caib.dir3caib.persistence.ejb.ImportadorUnidadesLocal;
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
@RequestMapping(value = "/unidad")
public class UnidadController extends BaseController {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/ImportadorUnidadesEJB/local")
    private ImportadorUnidadesLocal importadorUnidades;

    @EJB(mappedName = "dir3caib/ArbolEJB/local")
    private ArbolLocal arbolEjb;


    // Indicamos el formato de fecha dd/MM/yyyy hh:mm:ss
    SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);


    /**
     * Listado de los {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listado() {
        return "redirect:/unidad/list";
    }


    /**
     * Carga el formulario para la busqueda de {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {

        CatEstadoEntidad vigente = catEstadoEntidadEjb.findByCodigo(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        Unidad unidad = new Unidad();
        unidad.setEstado(vigente);

        UnidadBusquedaForm unidadBusqueda = new UnidadBusquedaForm(unidad, 1, false);

        //Cargamos el conjunto de los valores posibles del formulario
        List<CatNivelAdministracion> administraciones = catNivelAdministracionEjb.getAll();
        List<CatComunidadAutonoma> comunidades = catComunidadAutonomaEjb.getAll();
        List<CatEstadoEntidad> estadosEntidad = catEstadoEntidadEjb.getAll();

        // Valores por defecto en la Búsqueda
        String administracion = Configuracio.getBusquedaAdministracion();
        String comunidadAutonoma = Configuracio.getBusquedaComunidad();

        //Establece los valores por defecto en la búsqueda
        if(administracion != null){
            unidadBusqueda.getUnidad().setNivelAdministracion(new CatNivelAdministracion(Long.valueOf(administracion.trim())));
        }

        if(comunidadAutonoma != null){
            unidadBusqueda.getUnidad().setCodComunidad(new CatComunidadAutonoma(Long.valueOf(comunidadAutonoma.trim())));
        }

        model.addAttribute("unidadBusqueda", unidadBusqueda);
        model.addAttribute("administraciones", administraciones);
        model.addAttribute("comunidades", comunidades);
        model.addAttribute("estadosEntidad", estadosEntidad);

        return "unidad/unidadList";

    }


    /**
     * Realiza la búsqueda de {@link es.caib.dir3caib.persistence.model.Unidad} según los parámetros del formulario
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ModelAndView list(@ModelAttribute UnidadBusquedaForm busqueda) throws Exception {

        ModelAndView mav = new ModelAndView("unidad/unidadList");

        Unidad unidad = busqueda.getUnidad();

        Long codNivelAdministracion = (unidad.getNivelAdministracion() != null) ? unidad.getNivelAdministracion().getCodigoNivelAdministracion() : null;
        String codAmbitoTerritorial = (unidad.getCodAmbitoTerritorial() != null) ? unidad.getCodAmbitoTerritorial().getCodigoAmbito() : null;
        Long codComunidad = (unidad.getCodComunidad() != null) ? unidad.getCodComunidad().getCodigoComunidad() : null;
        Long codAmbProvincia = (unidad.getCodAmbProvincia() != null) ? unidad.getCodAmbProvincia().getCodigoProvincia() : null;
        String codEstadoEntidad = (unidad.getEstado() != null) ? unidad.getEstado().getCodigoEstadoEntidad() : null;

        Paginacion paginacion = unidadEjb.busqueda(busqueda.getPageNumber(),
                unidad.getCodigo(),
                unidad.getDenominacion(),
                codNivelAdministracion,
                codAmbitoTerritorial,
                codComunidad,
                codAmbProvincia, busqueda.getUnidadRaiz(), codEstadoEntidad);

        busqueda.setPageNumber(1);

        mav.addObject("administraciones", catNivelAdministracionEjb.getAll());
        mav.addObject("comunidades", catComunidadAutonomaEjb.getAll());
        mav.addObject("estadosEntidad", catEstadoEntidadEjb.getAll());

        mav.addObject("paginacion", paginacion);
        mav.addObject("unidadBusqueda", busqueda);

        return mav;

    }


    /**
     * Elimina todas las Unidades de la bbdd y las sincroniza con al información actual
     */
    @RequestMapping(value = "/restaurarDirectorio", method = RequestMethod.GET)
    public String restaurarUnidades(Model model) throws Exception {

        model.addAttribute("development", Configuracio.isDevelopment());
        model.addAttribute("unidadForm", new Unidad());

        return "unidad/unidadRestaurar";
    }


    /**
     * Elimina todas las Unidades de la bbdd y las sincroniza con al información actual
     * @param unidadForm
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/restaurarDirectorio", method = RequestMethod.POST)
    public ModelAndView restaurarUnidades(@ModelAttribute Unidad unidadForm, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("/unidad/unidadImportacion");

        // Restaurar las Unidades
        ResultadosImportacion results = importadorUnidades.restaurarUnidades();

        if(results != null){

            Mensaje.saveMessageInfo(request, getMessage("unidad.importacion.ok"));
            mav.addObject("procesados", results.getProcesados());// Nombre de los ficheros procesados
            mav.addObject("ficheros", Dir3caibConstantes.UO_FICHEROS);//Nombre de los ficheros obtenidos
            mav.addObject("existentes", results.getExistentes());//Nombre de los ficheros que realmente han venido en la descarga
            mav.addObject("descarga", results.getDescarga());//Datos de la descarga

            return mav;

        }else{
            Mensaje.saveMessageError(request, getMessage("unidad.descarga.nook"));

            return new ModelAndView("redirect:/unidad/restaurarDirectorio");
        }

    }


    /**
     * Sincroniza las unidades. Obtiene las unidades, sus históricos y sus contactos a través de WS desde la última fecha de
     * sincronización e importa los datos.
     *
     * @param request
     */
    @RequestMapping(value = "/sincronizar", method = RequestMethod.GET)
    public ModelAndView sincronizarUnidades(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("/unidad/unidadImportacion");
        Descarga descarga = null;

        try {

            // Obtenemos la fecha de la ultima descarga/sincronizacion
            Descarga ultimaDescarga = descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.UNIDAD);

            // Descarga de las unidades
            if(ultimaDescarga != null){
                Date fechaFin = ultimaDescarga.getFechaFin();
                descarga = descargarUnidadesWS(request, fechaFin, new Date());
            } else {//Es una descarga inicial
                descarga = descargarUnidadesWS(request, null, null);
            }

            // Importamos los datos a la BD si la descarga ha ido bien
            if (descarga != null && descarga.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {

                long start = System.currentTimeMillis();

                // Realiza la importación de al última descarga de Unidades
                descargaEjb.actualizarEstado(descarga.getCodigo(), Dir3caibConstantes.SINCRONIZACION_EN_CURSO);
                ResultadosImportacion results = importadorUnidades.importarUnidades();

                // Importación correcta
                descargaEjb.actualizarEstado(descarga.getCodigo(), Dir3caibConstantes.SINCRONIZACION_CORRECTA);

                log.info("Unidades importadas en " + Utils.formatElapsedTime(System.currentTimeMillis() - start));

                Mensaje.saveMessageInfo(request, getMessage("unidad.importacion.ok"));

                mav.addObject("procesados", results.getProcesados());// Nombre de los ficheros procesados
                mav.addObject("ficheros", Dir3caibConstantes.UO_FICHEROS);//Nombre de los ficheros obtenidos
                mav.addObject("existentes", results.getExistentes());//Nombre de los ficheros que realmente han venido en la descarga
                mav.addObject("descarga", results.getDescarga());//Datos de la descarga

            }else{
                Mensaje.saveMessageInfo(request, getMessage("unidad.sincronizacion.vacia"));
                return new ModelAndView("redirect:/unidad/descarga/list");
            }

        } catch (Exception ex) {
            // Si ha habido un Error en la sincronización, modificamos el estado de la descarga
            if(descarga != null){
                try {
                    descargaEjb.actualizarEstado(descarga.getCodigo(), Dir3caibConstantes.SINCRONIZACION_ERRONEA);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Mensaje.saveMessageError(request, getMessage("unidad.sincronizacion.error"));
            ex.printStackTrace();
            return new ModelAndView("redirect:/unidad/descarga/list");
        }

        return mav;
    }

    /**
     * Método que obtiene el árbol de unidades de una unidad.
     *
     * @param request
     * @param idUnidad
     * @return
     */
    @RequestMapping(value = "/{idUnidad}/arbol", method = RequestMethod.GET)
    public ModelAndView mostrarArbolUnidades(HttpServletRequest request, @PathVariable String idUnidad) throws Exception {

        Long start = System.currentTimeMillis();
        ModelAndView mav = new ModelAndView("/arbolList");

        //Obtenemos los datos básicos de la unidad que nos indican(suele ser la raíz del árbol)
        Unidad unidad = unidadEjb.findByCodigoLigero(idUnidad);

        Nodo nodo = new Nodo(); //Representa el nodo raiz del árbol que se quiere mostrar.
        //Obtenemos el árbol de unidades
        arbolEjb.arbolUnidades(idUnidad, nodo, unidad.getEstado().getCodigoEstadoEntidad(), true);
        Long end = System.currentTimeMillis();

        log.info("TIEMPO CARGA ARBOLarbol: " + Utils.formatElapsedTime(end - start));

        mav.addObject("nodo", nodo);
        return mav;

    }


    /**
     * Método que se encarga de listar todas las descargas que se han realizado de las unidades
     */
    @RequestMapping(value = "/descarga/list", method = RequestMethod.GET)
    public String listadoDescargaUnidad() {

        return "redirect:/unidad/descarga/list/1";
    }

    /**
     * Listado de las descargas realizadas de las unidades
     *
     * @param pageNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/descarga/list/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView descargaUnidadList(@PathVariable Integer pageNumber) throws Exception {

        ModelAndView mav = new ModelAndView("/descargaList");

        //Obtenemos el listado paginado de las descargas
        List<Descarga> listado = descargaEjb.getPaginationByTipo(((pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION), Dir3caibConstantes.UNIDAD);
        Long total = descargaEjb.getTotalByTipo(Dir3caibConstantes.UNIDAD);

        ArrayList<String> ficheros = new ArrayList<String>();
        //Obtenemos todos los ficheros asociados a cada descarga
        if (listado != null) {
            for (Descarga descarga : listado) {
                File f = new File(Configuracio.getUnidadesPath(descarga.getCodigo()));
                if (f.exists()) {
                    ficheros = new ArrayList<String>(Arrays.asList(f.list()));
                }
                descarga.setFicheros(ficheros);
            }
        }

        Paginacion paginacion = new Paginacion(total.intValue(), pageNumber);

        mav.addObject("paginacion", paginacion);
        mav.addObject("listado", listado);
        mav.addObject("elemento", "unidad");
        mav.addObject("ficheros", ficheros);

        return mav;
    }

    /**
     * Método que se encarga de obtener los archivos de las unidades a través de WS
     *
     * @param request
     * @param fechaInicio
     * @param fechaFin
     */
    private Descarga descargarUnidadesWS(HttpServletRequest request, Date fechaInicio, Date fechaFin) throws Exception {

        try {
            //Invoca a los ws para obtener los archivos de las unidades
            Descarga descarga = importadorUnidades.descargarUnidadesWS(fechaInicio, fechaFin);

            //Mostramos los mensajes en función de la respuesta del WS de Madrid
            if(descarga != null){

                if (descarga.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {
                    Mensaje.saveMessageInfo(request, getMessage("unidad.descarga.ok"));

                }else if (descarga.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_VACIA)) { // No ha devuelto datos
                    Mensaje.saveMessageInfo(request, getMessage("unidad.sincronizacion.vacia"));
                }

                return descarga;

            }else{
                Mensaje.saveMessageError(request, getMessage("unidad.descarga.nook"));
                return null;
            }


        } catch (IOException ex) {
            Mensaje.saveMessageError(request, getMessage("unidad.descomprimir.error"));
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            Mensaje.saveMessageError(request, getMessage("unidad.descarga.nook"));
            e.printStackTrace();
            return null;
        }
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor dateEditor = new CustomDateEditor(sdf, true);

        binder.registerCustomEditor(java.util.Date.class, dateEditor);
    }



}
