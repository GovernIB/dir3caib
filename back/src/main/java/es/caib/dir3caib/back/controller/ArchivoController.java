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

    @RequestMapping(value = "/{nombreArchivo}/{tipo}", method = RequestMethod.GET)
    public void  archivo(@PathVariable("nombreArchivo") String nombreArchivo, @PathVariable("tipo") String tipo, HttpServletRequest request, HttpServletResponse response)  {


        fullDownload(nombreArchivo,tipo, response);

    }

    public void fullDownload(String nombre, String tipo,  HttpServletResponse response) {

        FileInputStream input = null;
        OutputStream output = null;
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        File file = null;


        try {
            if (nombre != null) {
                Descarga descargaCat = descargaEjb.findByTipo(Dir3caibConstantes.CATALOGO);
                if(Dir3caibConstantes.CATALOGO.equals(tipo)){
                  file = new File(Configuracio.getCatalogosPath(descargaCat.getCodigo()), nombre);
                }

                Descarga descargaUnidad = descargaEjb.findByTipo(Dir3caibConstantes.UNIDAD);
                if(Dir3caibConstantes.UNIDAD.equals(tipo)){
                  file = new File(Configuracio.getUnidadesPath(descargaUnidad.getCodigo()), nombre);
                }
                Descarga descargaOficina = descargaEjb.findByTipo(Dir3caibConstantes.OFICINA);
                if(Dir3caibConstantes.OFICINA.equals(tipo)){
                  file = new File(Configuracio.getOficinasPath(descargaOficina.getCodigo()), nombre);
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