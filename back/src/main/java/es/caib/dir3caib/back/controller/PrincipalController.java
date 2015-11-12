package es.caib.dir3caib.back.controller;

import es.caib.dir3caib.persistence.ejb.DescargaLocal;
import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

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
        ModelAndView mav = new ModelAndView("principal");
        return mav;
    }
    
    @RequestMapping(value = "/inicio/{lang}")
    public ModelAndView principal(HttpServletRequest request, 
        HttpServletResponse response, @PathVariable(value="lang") String lang) throws Exception {
      
      Locale loc = new Locale(lang);
      LocaleContextHolder.setLocale(loc);
      WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, loc);
      ModelAndView mav = new ModelAndView(new RedirectView("/inicio", true));
      return mav;
    }

}
