package es.caib.dir3caib.back.controller.oficina;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.FechasForm;
import es.caib.dir3caib.back.form.OficinaBusquedaForm;
import es.caib.dir3caib.back.utils.CodigoValor;
import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.back.utils.Nodo;
import es.caib.dir3caib.persistence.ejb.ImportadorOficinasLocal;
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
@RequestMapping(value = "/oficina")
public class OficinaController extends BaseController {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/ImportadorOficinasEJB/local")
    private ImportadorOficinasLocal importadorOficinas;

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
     public String list(Model model)throws Exception {

         OficinaBusquedaForm oficinaBusqueda = new OficinaBusquedaForm(new Oficina(),1);
         model.addAttribute("oficinaBusqueda",oficinaBusqueda);

         return "oficina/oficinaList";

     }


     /**
      * Realiza la busqueda de {@link es.caib.dir3caib.persistence.model.Oficina} según los parametros del formulario
      */
     @RequestMapping(value = "/list", method = RequestMethod.POST)
     public ModelAndView list(@ModelAttribute OficinaBusquedaForm busqueda)throws Exception {

         ModelAndView mav = new ModelAndView();

         mav = new ModelAndView("oficina/oficinaList");

        Oficina oficina = busqueda.getOficina();

         Long codNivelAdministracion = (oficina.getNivelAdministracion()!=null) ? oficina.getNivelAdministracion().getCodigoNivelAdministracion() : null;
         Long codComunidad = (oficina.getCodComunidad()!=null) ? oficina.getCodComunidad().getCodigoComunidad() : null;
         Long codAmbProvincia = (oficina.getLocalidad()!=null) ? oficina.getLocalidad().getProvincia().getCodigoProvincia(): null;

         Paginacion paginacion = oficinaEjb.busqueda(busqueda.getPageNumber(),
                         oficina.getCodigo(),
                         oficina.getDenominacion(),
                         codNivelAdministracion,
                         codComunidad,
                         codAmbProvincia);

         busqueda.setPageNumber(1);

         mav.addObject("paginacion", paginacion);
         mav.addObject("oficinaBusqueda", busqueda);

         return mav;

     }



    /**
     * Muestra los ficheros de oficinas que hay en el directorio
     * @param request
     * @return 
     */
    @RequestMapping(value = "/ficheros", method = RequestMethod.GET)
     public ModelAndView ficherosList(HttpServletRequest request){
         ModelAndView mav = new ModelAndView("/oficina/oficinaFicheros");
         
         // Obtenemos el listado de ficheros que hay dentro del directorio indicado
         File f = FileSystemManager.getArchivosPath(Dir3caibConstantes.OFICINAS_LOCATION_PROPERTY);
         ArrayList<String> existentes = new ArrayList<String>(Arrays.asList(f.list()));
         
         try{
            Descarga descarga = descargaEjb.findByTipo(Dir3caibConstantes.OFICINA);
            log.info("DESCARGA OBTENIDA " + descarga.getCodigo());
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
     * Muestra el formulario para obtener las oficinas mediante el WS de DIR3
     */
    @RequestMapping(value = "/obtener", method = RequestMethod.GET)
    public String obtenerOficinas(Model model)throws Exception {
        
        model.addAttribute("oficina", new FechasForm());
        
        return "/oficina/oficinaObtener";
    }
    
    /**
     * Obtiene las oficinas mediante el WS de DIR3
     */
    @RequestMapping(value = "/obtener", method = RequestMethod.POST)
    public String descargaOficinas(@ModelAttribute FechasForm fechasForm, HttpServletRequest request)throws Exception {
        
        log.info("fechaInicio: " + fechasForm.getFechaInicioFormateada(Dir3caibConstantes.FORMATO_FECHA));
        log.info("fechaFin: " + fechasForm.getFechaFinFormateada(Dir3caibConstantes.FORMATO_FECHA));
        
          //Fechas de descarga
        String fechaInicio = fechasForm.getFechaInicioFormateada(Dir3caibConstantes.FORMATO_FECHA);
        String fechaFin = fechasForm.getFechaFinFormateada(Dir3caibConstantes.FORMATO_FECHA);
        
        
        descargarOficinasWS(request, fechaInicio, fechaFin);
        
        return "redirect:/oficina/ficheros ";

    }
    
    /**
      * Importa el contenido de un fichero de las Oficinas a la bbdd
      * @param request
      * @return 
      */
     @RequestMapping(value = "/importar", method = RequestMethod.GET)
     public ModelAndView importarOficinas(HttpServletRequest request, Boolean sincro) throws Exception {
         
         ModelAndView mav = new ModelAndView("/oficina/oficinaImportacion");

         
         long start = System.currentTimeMillis();
                 
         ResultadosImportacion resultados;
         resultados = importadorOficinas.importarOficinas(sincro == null? false : sincro);
         
         long end = System.currentTimeMillis();
         log.info("Importat oficinas en " + Utils.formatElapsedTime(end - start));
         
         System.gc();


         Mensaje.saveMessageInfo(request, "Se han importado correctamente todas las oficinas");
         mav.addObject("procesados",resultados.getProcesados());
         mav.addObject("ficheros",Dir3caibConstantes.OFI_FICHEROS);
         mav.addObject("existentes",resultados.getExistentes());
         mav.addObject("descarga" , resultados.getDescarga());
         
         return mav;
     }
     
     /**
      * Elimina los ficheros de las oficinas del sistema de archivos
      * @param request
      * @return 
      */
     @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
     public ModelAndView eliminarOficinasCompleto(HttpServletRequest request){
         ModelAndView mav = new ModelAndView("/oficina/oficinaFicheros");
         
         File directorio = FileSystemManager.getArchivosPath(Dir3caibConstantes.OFICINAS_LOCATION_PROPERTY);
         try {
             
             relSirOfiEjb.deleteAll();
             relOrgOfiEjb.deleteAll();
             contactoOfiEjb.deleteAll();
             oficinaEjb.deleteHistoricosOficina();
             oficinaEjb.deleteServiciosOficina();
             oficinaEjb.deleteAll();
             servicioEjb.deleteAll();
             descargaEjb.deleteByTipo(Dir3caibConstantes.OFICINA);
             
             FileUtils.cleanDirectory(directorio);
             Mensaje.saveMessageInfo(request, "Se han eliminado correctamente todos los ficheros de oficinas");
         } catch (Exception ex) {
             Mensaje.saveMessageError(request, "Ha ocurrido un error al intentar eliminar los archivos del directorio oficinas");
             ex.printStackTrace();
         }
         
         return mav;
     }
     
     
     
      /**
      * Sincroniza las oficinas.Obtiene las oficinas y sus relaciones a traves de WS desde la última fecha de 
      * sincronización e importa los datos.
      * @param request
      * @return 
      */
     @RequestMapping(value = "/sincronizar", method = RequestMethod.GET)
     public ModelAndView sincronizarOficinas(HttpServletRequest request){
        
        try{

          final Boolean sincronizacion= true;
          // Obtenemos la fecha de la ultima descarga/sincronizacion
          Descarga ultimaDescarga = descargaEjb.findByTipo(Dir3caibConstantes.OFICINA);
          // Establecemmos la fecha de hoy
          String hoy = formatoFecha.format(new Date());
          // Obtenemos los archivos por WS
          descargarOficinasWS(request, ultimaDescarga.getFechaFin(), hoy);
          // Importamos los datos a la BD.


          return importarOficinas(request, sincronizacion);
          
        }catch(Exception ex){
          Mensaje.saveMessageError(request, "Ha ocurrido un error al sincronizar las oficinas");
          ex.printStackTrace();
        }
        return new ModelAndView("/oficina/oficinaImportacion");        
     }

    /*
     * Método que se encarga de obtener los archivos de las oficinas a través de WS
     * @param request
     * @param fechaInicio
     * @param fechaFin
     */
    public void descargarOficinasWS(HttpServletRequest request, String fechaInicio, String fechaFin) throws Exception {

        try{
            importadorOficinas.descargarOficinasWS(fechaInicio, fechaFin);
            Mensaje.saveMessageInfo(request, "Se han obtenido correctamente las oficinas");
        }catch(IOException ex){
            Mensaje.saveMessageError(request, "Ha ocurrido un error al descomprimir las oficinas");
            ex.printStackTrace();
        }catch (Exception e){
            Mensaje.saveMessageError(request, "Ha ocurrido un error obtener las oficinas a través de WS");
            e.printStackTrace();
        }

    }


    /**
   * Método que obtiene el árbol de oficinas de una oficina
   * @param request
   * @param idOficina
   * @return
   */
    @RequestMapping(value = "/{idOficina}/arbol", method = RequestMethod.GET)
    public ModelAndView mostrarArbolOficinas(HttpServletRequest request, @PathVariable String idOficina) {

      ModelAndView mav = new ModelAndView("/arbolList");
      Nodo nodo = new Nodo();
      arbolOficinas(idOficina, nodo);
      mav.addObject("nodo", nodo);

      return mav;

    }

   /**
   * Metodo que devuelve una estructura de nodos que representan un árbol de oficinas
   * @param idOficina  oficina raiz de la que partimos.
   * @return  Nodo (árbol)
   */
    private void arbolOficinas(String idOficina, Nodo nodo){

       try {
          Oficina oficinaPadre = oficinaEjb.findById(idOficina);
          nodo.setNombre(oficinaPadre.getDenominacion());
          nodo.setIdPadre(idOficina);
          nodo.setEstado(oficinaPadre.getEstado().getCodigoEstadoEntidad());

          List<Nodo> hijos = new ArrayList<Nodo>();
          List<Oficina> oficinasHijas = oficinaEjb.hijos(idOficina);
          for(Oficina oficinaHija: oficinasHijas){
            Nodo hijo = new Nodo();
            hijo.setNombre(oficinaHija.getDenominacion());
            hijo.setIdPadre(idOficina);
            hijo.setEstado(oficinaHija.getEstado().getCodigoEstadoEntidad());
            hijos.add(hijo);
            // llamada recursiva
            arbolOficinas(oficinaHija.getCodigo(), hijo);
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
