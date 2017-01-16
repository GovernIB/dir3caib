package es.caib.dir3caib.back.controller;

import es.caib.dir3caib.persistence.ejb.DescargaLocal;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Fundació BIT.
 * @author earrivi
 * Date: 2/10/13
 */

@Controller
public class PrincipalController extends BaseController {


    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;

    @RequestMapping(value = "/inicio")
    public ModelAndView principal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = null;

        if(request.isUserInRole(Dir3caibConstantes.DIR_ADMIN)){
            mav = new ModelAndView("principal");

            mav.addObject("ultimaDescargaCatalogo",descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.CATALOGO));
            mav.addObject("ultimaDescargaUnidad",descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.UNIDAD));
            mav.addObject("ultimaDescargaOficina",descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.OFICINA));

        }else if(request.isUserInRole(Dir3caibConstantes.ROL_TOTHOM)){
            mav = new ModelAndView("redirect:/unidad/list");
        }

        return mav;
    }




}
