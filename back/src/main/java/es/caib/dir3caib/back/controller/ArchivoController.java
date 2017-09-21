package es.caib.dir3caib.back.controller;


import es.caib.dir3caib.persistence.ejb.SincronizacionLocal;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.FileSystemManager;
import es.caib.dir3caib.persistence.model.Sincronizacion;
import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * Created 9/07/14 15:52
 *
 * @author mgonzalez
 */
@Controller
@RequestMapping(value = "/archivo")
public class ArchivoController extends BaseController{

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/SincronizacionEJB/local")
    private SincronizacionLocal sincronizacionEjb;


    @RequestMapping(value = "/{nombreArchivo}/{idSincronizacion}", method = RequestMethod.GET)
    public void  archivo(@PathVariable("nombreArchivo") String nombreArchivo,@PathVariable("idSincronizacion") Long idSincronizacion,  HttpServletRequest request, HttpServletResponse response)  {

        fullDownload(nombreArchivo,idSincronizacion, response);
    }

    public void fullDownload(String nombre, Long idSincronizacion,  HttpServletResponse response) {

        FileInputStream input = null;
        OutputStream output = null;
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        File file = null;


        try {
            if (nombre != null) {

                Sincronizacion sincronizacion = sincronizacionEjb.findById(idSincronizacion);

                if(Dir3caibConstantes.CATALOGO.equals(sincronizacion.getTipo())){
                  file = new File(Configuracio.getCatalogosPath(sincronizacion.getCodigo()), nombre);
                }

                if(Dir3caibConstantes.DIRECTORIO.equals(sincronizacion.getTipo())){
                  file = new File(Configuracio.getDirectorioPath(sincronizacion.getCodigo()), nombre);
                }

                String contentType = mimeTypesMap.getContentType(file);

                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
                response.setContentLength((int) file.length());

                output = response.getOutputStream();
                input = new FileInputStream(file);

                FileSystemManager.copy(input, output);

                input.close();
                output.close();
            }

        } catch (NumberFormatException e) {
            log.info(e);
        }  catch (Exception e) {
            e.printStackTrace();
        }


    }
}