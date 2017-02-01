package es.caib.dir3caib.back.controller.catalogo;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.FechasForm;
import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.persistence.ejb.*;
import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.utils.Paginacion;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;
import org.apache.commons.io.FileUtils;
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
 * @author earrivi
 * Date: 2/10/13
 */

@Controller
@RequestMapping(value = "/catalogo")
public class CatalogoController extends BaseController{
  
  @EJB(mappedName = "dir3caib/CatAmbitoTerritorialEJB/local")
  protected CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;
  
  @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
  protected CatEntidadGeograficaLocal catEntidadGeograficaEjb;

  @EJB(mappedName = "dir3caib/ImportadorCatalogoEJB/local")
  private ImportadorCatalogoLocal importadorCatalogo;
  
  @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
  protected CatEstadoEntidadLocal catEstadoEntidadEjb;
  
  @EJB(mappedName = "dir3caib/CatIslaEJB/local")
  protected CatIslaLocal catIslaEjb;
  
  @EJB(mappedName = "dir3caib/CatJerarquiaOficinaEJB/local")
  protected CatJerarquiaOficinaLocal catJerarquiaOficinaEjb;
  
  @EJB(mappedName = "dir3caib/CatMotivoExtincionEJB/local")
  protected CatMotivoExtincionLocal catMotivoExtincionEjb;
  
  @EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
  protected CatNivelAdministracionLocal catNivelAdministracionEjb;
  
  @EJB(mappedName = "dir3caib/CatPaisEJB/local")
  protected CatPaisLocal catPaisEjb;
  
  @EJB(mappedName = "dir3caib/CatTipoContactoEJB/local")
  protected CatTipoContactoLocal catTipoContactoEjb;
  
  @EJB(mappedName = "dir3caib/CatTipoEntidadPublicaEJB/local")
  protected CatTipoEntidadPublicaLocal catTipoEntidadPublicaEjb;
  
  @EJB(mappedName = "dir3caib/CatTipoUnidadOrganicaEJB/local")
  protected CatTipoUnidadOrganicaLocal catTipoUnidadOrganicaEjb;
  
  @EJB(mappedName = "dir3caib/CatTipoViaEJB/local")
  protected CatTipoViaLocal catTipoViaEjb;
  
  @EJB(mappedName = "dir3caib/CatComunidadAutonomaEJB/local")
  protected CatComunidadAutonomaLocal catComunidadAutonomaEjb;
  
  @EJB(mappedName = "dir3caib/CatProvinciaEJB/local")
  protected CatProvinciaLocal catProvinciaEjb;
  
  @EJB(mappedName = "dir3caib/CatLocalidadEJB/local")
  protected CatLocalidadLocal catLocalidadEjb;
  
  @EJB(mappedName = "dir3caib/DescargaEJB/local")
  protected DescargaLocal descargaEjb;
  
  // Indicamos el formato de fecha dd/MM/yyyy hh:mm:ss
  SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);
  
    /**
     * Muestra los ficheros de catalogos que hay en el directorio
     * @param request
     * @return 
     */
    @RequestMapping(value = "/ficheros", method = RequestMethod.GET)
     public ModelAndView ficherosList(HttpServletRequest request)  throws Exception {
        ModelAndView mav = new ModelAndView("/catalogo/catalogoFicheros");

        // Obtenemos el listado de ficheros que hay dentro del directorio indicado
        ArrayList<String> existentes = new ArrayList<String>();
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.CATALOGO);
        if (descarga != null) {
            File f = new File(Configuracio.getCatalogosPath(descarga.getCodigo()));
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
     * Muestra el formulario para obtener los catalogos mediante el WS de DIR3
     */
    @RequestMapping(value = "/obtener", method = RequestMethod.GET)
    public String obtenerCatalogos(Model model)throws Exception {

        Descarga descarga = descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.CATALOGO);
        if(descarga != null){
          model.addAttribute("descarga", descarga);
        }
        model.addAttribute("catalogo", new FechasForm());
        
        return "/catalogo/catalogoObtener";
    }
            
    

    /**
     * Obtiene los catalogos mediante el WS de DIR3
     */
    @RequestMapping(value = "/obtener", method = RequestMethod.POST)
    public String obtenerCatalogos(@ModelAttribute FechasForm fechasForm, HttpServletRequest request)throws Exception {

        
      /*  log.info("fechaInicio: " + fechasForm.getFechaInicioFormateada(Dir3caibConstantes.FORMATO_FECHA));
        log.info("fechaFin: " + fechasForm.getFechaFinFormateada(Dir3caibConstantes.FORMATO_FECHA));
        
          //Fechas de descarga
        String fechaInicio = fechasForm.getFechaInicioFormateada(Dir3caibConstantes.FORMATO_FECHA);
        String fechaFin = fechasForm.getFechaFinFormateada(Dir3caibConstantes.FORMATO_FECHA);*/


        
        descargarCatalogoWS(request, fechasForm.getFechaInicio(), fechasForm.getFechaFin());

        /*log.info("Codigo: " + respuestaXml.getCodigo());
        log.info("Descripcion: " + respuestaXml.getDescripcion());
        FileUtils.writeByteArrayToFile(new File(ruta + "catalogoXml.zip"), decoder.decode(respuestaXml.getFichero()));*/
         
        return "redirect:/catalogo/ficheros ";

    }
    
    
    /**
      * Importa el contenido de un fichero de Catalogo a la bbdd
      * @param request
      * @return 
      */
     @RequestMapping(value = "/importar", method = RequestMethod.GET)
     public ModelAndView importarCatalogo(HttpServletRequest request) throws Exception {
         
         ModelAndView mav = new ModelAndView("/catalogo/catalogoImportacion");


         boolean descargaOk = descargarCatalogoWS(request, null, null);

         long start = System.currentTimeMillis();
         if (descargaOk) {
             ResultadosImportacion results = importadorCatalogo.importarCatalogo();

             long end = System.currentTimeMillis();
             log.info("Importat cataleg en " + Utils.formatElapsedTime(end - start));

             Mensaje.saveMessageInfo(request, getMessage("catalogo.importacion.ok"));
             mav.addObject("procesados", results.getProcesados());
             mav.addObject("ficheros", Dir3caibConstantes.CAT_FICHEROS);
             mav.addObject("existentes", results.getExistentes());
             mav.addObject("descarga", results.getDescarga());

         }
         return mav;
     }
     
     
     
     /**
      * Elimina los ficheros del catalogo del sistema de archivos
      * @param request
      * @return 
      */
     @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
     public ModelAndView eliminarCatalogoCompleto(HttpServletRequest request){
         ModelAndView mav = new ModelAndView("/catalogo/catalogoFicheros");


         
         try {
             Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.CATALOGO);
             File directorio = new File(Configuracio.getCatalogosPath(descarga.getCodigo()));
             FileUtils.cleanDirectory(directorio);
             
             // Borrado del catalago de la BD
             
             catLocalidadEjb.deleteAll();
             catIslaEjb.deleteAll();
             catProvinciaEjb.deleteAll();
             catComunidadAutonomaEjb.deleteAll();
             catAmbitoTerritorialEjb.deleteAll();
             catTipoViaEjb.deleteAll();
             catTipoUnidadOrganicaEjb.deleteAll();
             catTipoEntidadPublicaEjb.deleteAll();
             catTipoContactoEjb.deleteAll();
             catPaisEjb.deleteAll();
             catNivelAdministracionEjb.deleteAll();
             catMotivoExtincionEjb.deleteAll();
             catJerarquiaOficinaEjb.deleteAll();
             //catIslaEjb.deleteAll();
             catEstadoEntidadEjb.deleteAll();
             catEntidadGeograficaEjb.deleteAll(); 
             descargaEjb.deleteAllByTipo(Dir3caibConstantes.CATALOGO);
             
             
             Mensaje.saveMessageInfo(request, getMessage("catalogo.borrar.ok"));
         } catch (IOException ex) {
             Mensaje.saveMessageError(request, getMessage("catalogo.borrar.nook") + Configuracio.getArchivosPath());
             ex.printStackTrace();
         } catch ( Exception e) {
               Mensaje.saveMessageError(request, getMessage("catalogo.borrar.bd.nook"));
               e.printStackTrace();
         }
         
         return mav;
     }
     
     
     /**
     * Método que se encarga de obtener los archivos del catálogo  a través de request.
     * @param request
     * @param fechaInicio
     * @param fechaFin
     */      
    public boolean descargarCatalogoWS(HttpServletRequest request, Date fechaInicio, Date fechaFin ) throws Exception{

         try{
             String[] respuesta= importadorCatalogo.descargarCatalogoWS(fechaInicio, fechaFin);
             if(Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO.equals(respuesta[0])){
                 Mensaje.saveMessageInfo(request, getMessage("catalogo.descarga.ok"));
                 return true;
             }else{
                 Mensaje.saveMessageError(request, getMessage("catalogo.descarga.nook") +": "+respuesta[1]);
                 return false;
             }

        }catch(IOException ex){
            Mensaje.saveMessageError(request, getMessage("catalogo.descomprimir.nook"));
            ex.printStackTrace();
            return false;
        }catch(Exception e){
            Mensaje.saveMessageError(request,  getMessage("catalogo.descarga.nook"));
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Método que se encarga de listar todas las descargas que se han realizado del catálogo
     */
    @RequestMapping(value = "/descarga/list", method = RequestMethod.GET)
    public String listadoDescargaCatalogo() {

        return "redirect:/catalogo/descarga/list/1";
    }

    /**
     * Listado de tipos de asunto
     * @param pageNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/descarga/list/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView descargaCatalogoList(@PathVariable Integer pageNumber)throws Exception {

        ModelAndView mav = new ModelAndView("/descargaList");



        List<Descarga> listado = descargaEjb.getPaginationByTipo(((pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION), Dir3caibConstantes.CATALOGO );
        log.info("LISTADO: " + listado.size());

        Long total = descargaEjb.getTotalByTipo(Dir3caibConstantes.CATALOGO);

        Paginacion paginacion = new Paginacion(total.intValue(), pageNumber);

        mav.addObject("paginacion", paginacion);
        mav.addObject("listado", listado);
        mav.addObject("elemento", "catalogo");

        return mav;
    }


     
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor dateEditor = new CustomDateEditor(sdf, true);

        binder.registerCustomEditor(java.util.Date.class, dateEditor);
    }

}
