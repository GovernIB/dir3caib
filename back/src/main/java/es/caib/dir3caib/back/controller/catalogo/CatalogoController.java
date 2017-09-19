package es.caib.dir3caib.back.controller.catalogo;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.persistence.ejb.BaseEjbJPA;
import es.caib.dir3caib.persistence.ejb.ImportadorCatalogoLocal;
import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.utils.Paginacion;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * Date: 2/10/13
 */

@Controller
@RequestMapping(value = "/catalogo")
public class CatalogoController extends BaseController {


    @EJB(mappedName = "dir3caib/ImportadorCatalogoEJB/local")
    private ImportadorCatalogoLocal importadorCatalogo;


    /**
     * Descarga y sincroniza el catálogo
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/sincronizar", method = RequestMethod.GET)
    public ModelAndView sincronizarCatalogo(HttpServletRequest request) throws Exception {

        log.info("");
        log.info("Inicio sincronizacion CATALOGO");

        ModelAndView mav = new ModelAndView("/catalogo/catalogoImportacion");
        Descarga descarga = null;

        try {

            descarga = descargarCatalogoWS(request, null, null);

            if (descarga != null && descarga.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {

                long start = System.currentTimeMillis();
                descargaEjb.actualizarEstado(descarga.getCodigo(), Dir3caibConstantes.SINCRONIZACION_EN_CURSO);
                ResultadosImportacion results = importadorCatalogo.importarCatalogo();

                // Importación correcta
                descargaEjb.actualizarEstado(descarga.getCodigo(), Dir3caibConstantes.SINCRONIZACION_CORRECTA);

                log.info("Fin sincronizacion catalogo en " + Utils.formatElapsedTime(System.currentTimeMillis() - start));

                Mensaje.saveMessageInfo(request, getMessage("catalogo.importacion.ok"));
                mav.addObject("procesados", results.getProcesados());
                mav.addObject("ficheros", Dir3caibConstantes.CAT_FICHEROS);
                mav.addObject("existentes", results.getExistentes());
                mav.addObject("descarga", results.getDescarga());

            }else{
                Mensaje.saveMessageInfo(request, getMessage("catalogo.sincronizacion.vacio"));
                return new ModelAndView("redirect:/catalogo/descarga/list");
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

            Mensaje.saveMessageError(request, getMessage("catalogo.sincronizacion.error"));
            ex.printStackTrace();
            return new ModelAndView("redirect:/catalogo/descarga/list");
        }

        return mav;
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
     *
     * @param pageNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/descarga/list/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView descargaCatalogoList(@PathVariable Integer pageNumber) throws Exception {

        ModelAndView mav = new ModelAndView("/descargaList");

        List<Descarga> listado = descargaEjb.getPaginationByTipo(((pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION), Dir3caibConstantes.CATALOGO);

        Long total = descargaEjb.getTotalByTipo(Dir3caibConstantes.CATALOGO);

        ArrayList<String> ficheros = new ArrayList<String>();

        if (listado != null) {
            for (Descarga descarga : listado) {
                File f = new File(Configuracio.getCatalogosPath(descarga.getCodigo()));
                if (f.exists()) {
                    ficheros = new ArrayList<String>(Arrays.asList(f.list()));
                }
                descarga.setFicheros(ficheros);
            }
        }

        Paginacion paginacion = new Paginacion(total.intValue(), pageNumber);

        mav.addObject("paginacion", paginacion);
        mav.addObject("listado", listado);
        mav.addObject("elemento", "catalogo");
        mav.addObject("ficheros", ficheros);

        return mav;
    }

    /**
     * Método que se encarga de obtener los archivos del catálogo  a través de request.
     *
     * @param request
     * @param fechaInicio
     * @param fechaFin
     */
    private Descarga descargarCatalogoWS(HttpServletRequest request, Date fechaInicio, Date fechaFin) throws Exception {

        try {
            Descarga descarga = importadorCatalogo.descargarCatalogoWS(fechaInicio, fechaFin);

            //Mostramos los mensajes en función de la respuesta del WS de Madrid
            if(descarga != null){

                if (descarga.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {
                    Mensaje.saveMessageInfo(request, getMessage("catalogo.descarga.ok"));

                }else if (descarga.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_VACIA)) { // No ha devuelto datos
                    Mensaje.saveMessageInfo(request, getMessage("catalogo.nueva.nohay"));
                }

                return descarga;

            }else{
                Mensaje.saveMessageError(request, getMessage("catalogo.descarga.nook"));
                return null;
            }


        } catch (IOException ex) {
            Mensaje.saveMessageError(request, getMessage("catalogo.descomprimir.nook"));
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            Mensaje.saveMessageError(request, getMessage("catalogo.descarga.nook"));
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
