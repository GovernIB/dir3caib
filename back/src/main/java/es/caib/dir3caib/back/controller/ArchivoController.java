package es.caib.dir3caib.back.controller;


import es.caib.dir3caib.persistence.ejb.DescargaLocal;
import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.FileSystemManager;
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

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;

    @RequestMapping(value = "/{nombreArchivo}/{idDescarga}", method = RequestMethod.GET)
    public void  archivo(@PathVariable("nombreArchivo") String nombreArchivo,@PathVariable("idDescarga") Long idDescarga,  HttpServletRequest request, HttpServletResponse response)  {

        fullDownload(nombreArchivo,idDescarga, response);
    }

    public void fullDownload(String nombre, Long idDescarga,  HttpServletResponse response) {

        FileInputStream input = null;
        OutputStream output = null;
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        File file = null;


        try {
            if (nombre != null) {

                Descarga descarga = descargaEjb.findById(idDescarga);

                if(Dir3caibConstantes.CATALOGO.equals(descarga.getTipo())){
                  file = new File(Configuracio.getCatalogosPath(descarga.getCodigo()), nombre);
                }

                if(Dir3caibConstantes.UNIDAD.equals(descarga.getTipo())){
                  file = new File(Configuracio.getUnidadesPath(descarga.getCodigo()), nombre);
                }

                if(Dir3caibConstantes.OFICINA.equals(descarga.getTipo())){
                  file = new File(Configuracio.getOficinasPath(descarga.getCodigo()), nombre);
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