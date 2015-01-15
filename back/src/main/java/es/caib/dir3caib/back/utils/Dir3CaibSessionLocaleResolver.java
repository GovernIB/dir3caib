package es.caib.dir3caib.back.utils;


import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;


/**
 * 
 * @author anadal
 *
 */
public class Dir3CaibSessionLocaleResolver extends SessionLocaleResolver {

  protected final Logger log = Logger.getLogger(getClass());
  
  @Override
  protected Locale determineDefaultLocale(HttpServletRequest request) {
    try {
      Locale loc = null;
      Enumeration<Locale> idiomes = request.getLocales();
      while (idiomes.hasMoreElements()) {
        Locale locale = idiomes.nextElement();
        if ("ca".equals(locale.getLanguage()) || "es".equals(locale.getLanguage())) {
          loc = locale;
          break;
        }
        
      }
      if (loc == null) {
        // Default value
        loc = new Locale("ca");
      }

      LocaleContextHolder.setLocale(loc);
      try {
        this.setLocale(request, null, loc);
      } catch(Exception e) {
         WebUtils.setSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE_NAME, loc);
      }
      return loc;
    } catch(Exception e) {
      log.error(e.getMessage(), e);
      return super.determineDefaultLocale(request);  
    }
    

  }
  
  
}
