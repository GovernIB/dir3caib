package es.caib.dir3caib.back.controller;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Fundació BIT.
 * @author earrivi
 * Date: 2/10/13
 */
@Controller
public class PrincipalController {

    protected final Logger log = Logger.getLogger(getClass());

    @RequestMapping(value = "/inicio")
    public ModelAndView principal(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("principal");
        return mav;
    }
    
    @RequestMapping(value = "/inicio/{lang}")
    public ModelAndView principal(HttpServletRequest request, 
        HttpServletResponse response, @PathVariable(value="lang") String lang) {
      
      Locale loc = new Locale(lang);
      LocaleContextHolder.setLocale(loc);
      WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, loc);
      
      return new ModelAndView(new RedirectView("/inicio", true));
    }


}
