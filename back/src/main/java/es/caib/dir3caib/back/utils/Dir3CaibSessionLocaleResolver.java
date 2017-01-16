package es.caib.dir3caib.back.utils;


import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


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

      String idioma = Configuracio.getDefaultLanguage();

      if (idioma == null) {
        // Default value
        loc = new Locale("ca");
      }else{
        loc = new Locale(idioma);
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
