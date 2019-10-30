package es.caib.dir3caib.back.controller;

import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.persistence.ejb.Dir3CaibLocal;
import es.caib.dir3caib.persistence.ejb.SincronizacionLocal;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Sincronizacion;
import es.caib.dir3caib.utils.Configuracio;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Fundació BIT.
 * @author earrivi
 * Date: 2/10/13
 */

@Controller
public class PrincipalController extends BaseController {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/SincronizacionEJB/local")
    private SincronizacionLocal sincronizacionEjb;

    @EJB(mappedName = "dir3caib/Dir3CaibEJB/local")
    private Dir3CaibLocal dir3CaibEjb;


    @RequestMapping(value = "/inicio")
    public ModelAndView principal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = null;

        if(request.isUserInRole(Dir3caibConstantes.DIR_ADMIN)){
            mav = new ModelAndView("principal");

            ArrayList<Sincronizacion> sincronizaciones = new ArrayList<Sincronizacion>();

            Sincronizacion catalogo = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.CATALOGO);
            Sincronizacion directorio = sincronizacionEjb.ultimaSincronizacionCompletada(Dir3caibConstantes.UNIDADES_OFICINAS);

            if(directorio != null){
                directorio.obtenerFicheros();
                sincronizaciones.add(directorio);
            }
            if(catalogo != null){
                catalogo.obtenerFicheros();
                sincronizaciones.add(catalogo);
            }

            mav.addObject("sincronizaciones", sincronizaciones);

        }else if(request.isUserInRole(Dir3caibConstantes.ROL_TOTHOM)){
            mav = new ModelAndView("redirect:/unidad/list");
        }

        return mav;
    }


    @RequestMapping(value = "/eliminarCompleto", method = RequestMethod.GET)
    public ModelAndView eliminarCompletoGet(HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("principal");

        try {
            // Eliminamos el Directorio de al bbdd
            dir3CaibEjb.eliminarCompleto();

            // Eliminamos todas las descargas realizadas
            File directorio = new File(Configuracio.getArchivosPath());
            FileUtils.cleanDirectory(directorio);

            Mensaje.saveMessageInfo(request, getMessage("dir3caib.borrar.todo.ok"));


        } catch (Exception ex) {
            Mensaje.saveMessageError(request, getMessage("dir3caib.borrar.todo.error"));
            ex.printStackTrace();
        }
        return mav;
    }

}
