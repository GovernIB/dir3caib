package es.caib.dir3caib.back.controller.unidad;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.FechasForm;
import es.caib.dir3caib.back.form.UnidadBusquedaForm;
import es.caib.dir3caib.back.utils.CodigoValor;
import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.back.utils.Nodo;
import es.caib.dir3caib.persistence.ejb.ImportadorUnidadesLocal;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.Paginacion;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
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
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Fundació BIT.
 * @author earrivi
 * Date: 2/10/13
 */

@Controller
@RequestMapping(value = "/unidad")
public class UnidadController extends BaseController{

    protected final Logger log = Logger.getLogger(getClass());
    
    @EJB(mappedName = "dir3caib/ImportadorUnidadesEJB/local")
    private ImportadorUnidadesLocal importadorUnidades;

    
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
    public String list(Model model)throws Exception {

        UnidadBusquedaForm unidadBusqueda = new UnidadBusquedaForm(new Unidad(),1, false);
        model.addAttribute("unidadBusqueda",unidadBusqueda);

        return "unidad/unidadList";

    }


    /**
     * Realiza la busqueda de {@link es.caib.dir3caib.persistence.model.Unidad} según los parametros del formulario
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ModelAndView list(@ModelAttribute UnidadBusquedaForm busqueda)throws Exception {

        ModelAndView mav = new ModelAndView();

        mav = new ModelAndView("unidad/unidadList");

        Unidad unidad = busqueda.getUnidad();

        Long codNivelAdministracion = (unidad.getNivelAdministracion()!=null) ? unidad.getNivelAdministracion().getCodigoNivelAdministracion() : null;
        String codAmbitoTerritorial = (unidad.getCodAmbitoTerritorial()!=null) ? unidad.getCodAmbitoTerritorial().getCodigoAmbito() : null;
        Long codComunidad = (unidad.getCodComunidad()!=null) ? unidad.getCodComunidad().getCodigoComunidad() : null;
        Long codAmbProvincia = (unidad.getCodAmbProvincia()!=null) ? unidad.getCodAmbProvincia().getCodigoProvincia(): null;

        Paginacion paginacion = unidadEjb.busqueda(busqueda.getPageNumber(),
                        unidad.getCodigo(),
                        unidad.getDenominacion(),
                        codNivelAdministracion,
                        codAmbitoTerritorial,
                        codComunidad,
                        codAmbProvincia, busqueda.getUnidadRaiz());

        busqueda.setPageNumber(1);

        mav.addObject("paginacion", paginacion);
        mav.addObject("unidadBusqueda", busqueda);

        return mav;

    }

    
    /**
     * Muestra los ficheros de unidades que hay en el directorio
     * @param request
     * @return 
     */
    @RequestMapping(value = "/ficheros", method = RequestMethod.GET)
     public ModelAndView ficherosList(HttpServletRequest request){

         ModelAndView mav = new ModelAndView("/unidad/unidadFicheros");
         
         // Obtenemos el listado de ficheros que hay dentro del directorio indicado
         File f = FileSystemManager.getArchivosPath(Dir3caibConstantes.UNIDADES_LOCATION_PROPERTY);
         ArrayList<String> existentes = new ArrayList<String>(Arrays.asList(f.list()));
         
         // Mostramos la fecha de la ultima descarga
         try{
            Descarga descarga = descargaEjb.findByTipo(Dir3caibConstantes.UNIDAD);
            if(descarga != null){              
              // Miramos si debemos mostrar el botón de importación, 
              // solo se muestra si la fecha de Inicio descarga es superior a la fechaImportacion
              String fechaInicio = descarga.getFechaInicio();
              String fechaImportacion = descarga.getFechaImportacion();

              //formateamos fechas para compararlas
              Date fInicio = formatoFecha.parse(fechaInicio);  
              if(fechaImportacion != null){
                Date fImportacion = formatoFecha.parse(fechaImportacion);  
                if(fInicio.after(fImportacion)){
                  mav.addObject("mostrarimportacion", "mostrarImportacion");
                }      
              }else {
                mav.addObject("mostrarimportacion", "mostrarImportacion");
              }             
              mav.addObject("descarga", descarga);
            }
         }catch(Exception e){
           e.printStackTrace();
         }
         
         mav.addObject("existentes", existentes);
         
         return mav;
     }
    
    /**
     * Muestra el formulario para obtener los unidades mediante el WS de DIR3
     */
    @RequestMapping(value = "/obtener", method = RequestMethod.GET)
    public String obtenerUnidades(Model model)throws Exception {
        
        model.addAttribute("unidad", new FechasForm());
        
        return "/unidad/unidadObtener";
    }
    

    /**
     * Obtiene las unidades mediante el WS de DIR3
     */
    @RequestMapping(value = "/obtener", method = RequestMethod.POST)
    public String descargaUnidades(@ModelAttribute FechasForm fechasForm, HttpServletRequest request)throws Exception {
        
        log.info("fechaInicio: " + fechasForm.getFechaInicioFormateada(Dir3caibConstantes.FORMATO_FECHA));
        log.info("fechaFin: " + fechasForm.getFechaFinFormateada(Dir3caibConstantes.FORMATO_FECHA));
        
          //Fechas de descarga
        String fechaInicio = fechasForm.getFechaInicioFormateada(Dir3caibConstantes.FORMATO_FECHA);
        String fechaFin = fechasForm.getFechaFinFormateada(Dir3caibConstantes.FORMATO_FECHA);
        
        descargarUnidadesWS(request, fechaInicio, fechaFin);
        
        // TODO: Añadir objeto descarga al request (modelAndView)
         
        return "redirect:/unidad/ficheros ";

    }
    
    /**
      * Importa el contenido de un fichero de las Unidades a la bbdd
      * @param request
      * @return 
      */
     @RequestMapping(value = "/importar", method = RequestMethod.GET)
     public ModelAndView importarUnidades(HttpServletRequest request) throws Exception {
         
         ModelAndView mav = new ModelAndView("/unidad/unidadImportacion");
         
         //Date hoy = new Date();
         
         long start = System.currentTimeMillis();
         
         ResultadosImportacion results = importadorUnidades.importarUnidades();
         
         long end = System.currentTimeMillis();
         log.info("Importat unidades en " + Utils.formatElapsedTime(end - start));

         Mensaje.saveMessageInfo(request, "Se han importado correctamente todas las unidades");
         mav.addObject("procesados",results.getProcesados());
         mav.addObject("ficheros",Dir3caibConstantes.UO_FICHEROS);
         mav.addObject("existentes", results.getExistentes());
         mav.addObject("descarga" , results.getDescarga());
         
         return mav;
     }
     
     /**
      * Elimina los ficheros de las unidades del sistema de archivos
      * @param request
      * @return 
      */
     @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
     public ModelAndView eliminarUnidadesCompleto(HttpServletRequest request){
         ModelAndView mav = new ModelAndView("/unidad/unidadFicheros");
         
         File directorio = FileSystemManager.getArchivosPath(Dir3caibConstantes.UNIDADES_LOCATION_PROPERTY);
         
         try {     
           // Contactos 
           contactoUOEjb.deleteAll();
           //Unidades 
           unidadEjb.deleteHistoricosUnidad();
           unidadEjb.deleteAll();
           descargaEjb.deleteByTipo(Dir3caibConstantes.UNIDAD);           
         
           FileUtils.cleanDirectory(directorio);
           Mensaje.saveMessageInfo(request, "Se han eliminado correctamente todos los ficheros de unidades");
         } catch (Exception ex) {
             Mensaje.saveMessageError(request, "Ha ocurrido un error al intentar eliminar los archivos del directorio unidades");
             ex.printStackTrace();
         } 
         
         return mav;
     }
     
     
      /**
      * Sincroniza las unidades.Obtiene las unidades y sus relaciones a traves de WS desde la última fecha de 
      * sincronización e importa los datos.
      * @param request
      * 
      */
     @RequestMapping(value = "/sincronizar", method = RequestMethod.GET)
     public ModelAndView sincronizarUnidades(HttpServletRequest request){
        
        try{
          // Obtenemos la fecha de la ultima descarga/sincronizacion
          Descarga ultimaDescarga = descargaEjb.findByTipo(Dir3caibConstantes.UNIDAD);
          // Establecemmos la fecha de hoy
          String hoy = formatoFecha.format(new Date());
          // Obtenemos los archivos por WS
          descargarUnidadesWS(request, ultimaDescarga.getFechaFin(), hoy);
          // Importamos los datos a la BD.
          return importarUnidades(request);
 
        }catch(Exception ex){
          Mensaje.saveMessageError(request, "Ha ocurrido un error al sincronizar las unidades");
          ex.printStackTrace();
        }
        Mensaje.saveMessageInfo(request, "Se han sincronizado todos los ficheros de unidades");
        return new ModelAndView("/unidad/unidadImportacion");
     }
     
     /**
     * Método que se encarga de obtener los archivos de las unidades a través de WS
     * @param request
     * @param fechaInicio
     * @param fechaFin
     */     
    public void descargarUnidadesWS(HttpServletRequest request, String fechaInicio, String fechaFin) throws Exception {
        try{
            importadorUnidades.descargarUnidadesWS(fechaInicio, fechaFin);
            Mensaje.saveMessageInfo(request, "Se han obtenido correctamente las unidades");
        }catch(IOException ex){
            Mensaje.saveMessageError(request, "Ha ocurrido un error al descomprimir las unidades");
            ex.printStackTrace(); 
        }catch (Exception e){
            Mensaje.saveMessageError(request, "Ha ocurrido un error al descargar las unidades a través de WS");
            e.printStackTrace();
        }
    }

   /**
   * Método que obtiene el árbol de unidades de una unidad.
   * @param request
   * @param idUnidad
   * @return
   */
    @RequestMapping(value = "/{idUnidad}/arbol", method = RequestMethod.GET)
    public ModelAndView mostrarArbolUnidades(HttpServletRequest request, @PathVariable String idUnidad) {

      ModelAndView mav = new ModelAndView("/arbolList");
      Nodo nodo = new Nodo();
      arbolUnidades(idUnidad, nodo);
      mav.addObject("nodo", nodo);

      return mav;

    }

  /**
   * Metodo que devuelve una estructura de nodos que representan un árbol de unidades
   * @param idUnidad  unidad raiz de la que partimos.
   * @return  Nodo (arbol)
   */
   private void arbolUnidades(String idUnidad, Nodo nodo){

       try {
          Unidad unidadPadre = unidadEjb.findById(idUnidad);
          nodo.setNombre(unidadPadre.getDenominacion());
          nodo.setIdPadre(idUnidad);
          nodo.setEstado(unidadPadre.getEstado().getCodigoEstadoEntidad());

          List<Nodo> hijos = new ArrayList<Nodo>();
          List<Unidad> unidadesHijas = unidadEjb.hijos(idUnidad);
          for(Unidad unidadHija: unidadesHijas){
            Nodo hijo = new Nodo();
            hijo.setNombre(unidadHija.getDenominacion());
            hijo.setIdPadre(idUnidad);
            hijo.setEstado(unidadHija.getEstado().getCodigoEstadoEntidad());
            hijos.add(hijo);
            // llamada recursiva
            arbolUnidades( unidadHija.getCodigo(), hijo);
          }
          nodo.setHijos(hijos);
       }catch(Exception e){
           e.printStackTrace();
       }
    }

    @ModelAttribute("administraciones")
    public List<CatNivelAdministracion> administraciones() throws Exception {
        return catNivelAdministracionEjb.getAll();
    }

    @ModelAttribute("comunidades")
    public List<CatComunidadAutonoma> comunidades() throws Exception {
        return catComunidadAutonomaEjb.getAll();
    }

    /**
     * Obtiene los {@link es.caib.dir3caib.persistence.model.CatAmbitoTerritorial} del nivel administracion seleccionado
     */
    @RequestMapping(value = "/ambitosTerritoriales", method = RequestMethod.GET)
    public @ResponseBody
    List<CodigoValor> ambitosTerritoriales(@RequestParam Long id) throws Exception {

        List<CatAmbitoTerritorial> ambitos = catAmbitoTerritorialEjb.getByAdministracion(id);
        List<CodigoValor> codigosValor= new ArrayList<CodigoValor>();
        for(CatAmbitoTerritorial ambito :ambitos){
           CodigoValor codigoValor = new CodigoValor();
           codigoValor.setId(ambito.getCodigoAmbito());
           codigoValor.setDescripcion(ambito.getDescripcionAmbito());
           codigosValor.add(codigoValor);
        }
        return codigosValor;
    }

    /**
     * Obtiene los {@link es.caib.dir3caib.persistence.model.CatProvincia} de la comunidad autonoma seleccionada
     */
    @RequestMapping(value = "/provincias", method = RequestMethod.GET)
    public @ResponseBody
    List<CodigoValor> provincias(@RequestParam Long id) throws Exception {

      List<CatProvincia> provincias = catProvinciaEjb.getByComunidadAutonoma(id);
      List<CodigoValor> codigosValor= new ArrayList<CodigoValor>();
      for(CatProvincia provincia :provincias){
         CodigoValor codigoValor = new CodigoValor();
         codigoValor.setId(provincia.getCodigoProvincia());
         codigoValor.setDescripcion(provincia.getDescripcionProvincia());
         codigosValor.add(codigoValor);
      }
      return codigosValor;
    }

     
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor dateEditor = new CustomDateEditor(sdf, true);

        binder.registerCustomEditor(java.util.Date.class, dateEditor);
    }

}
