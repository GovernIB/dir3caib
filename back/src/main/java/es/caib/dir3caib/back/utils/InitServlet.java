package es.caib.dir3caib.back.utils;

import es.caib.dir3caib.persistence.utils.Versio;
import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created 10/11/14 12:08
 *
 * @author mgonzalez
 * @author anadal
 */
@Component
public class InitServlet extends HttpServlet {

  protected final Logger log = Logger.getLogger(getClass());

  @Override
  public void init(ServletConfig config) throws ServletException {

    // Sistema de Traduccions WEB
    try {
      ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
      String[] basenames = {
              "es/caib/dir3caib/back/webapp/missatges" // /WEB-INF/classes/
              /*"logicmissatges"*/
      };
      ms.setDefaultEncoding("UTF-8");
      ms.setBasenames(basenames);
      I18NUtils.setMessageSource(ms);
    } catch (Throwable th) {
      log.error("Error inicialitzant el sistema de traduccions web: " + th.getMessage(), th);
    }

    // Mostrar Versió
    String ver = Versio.VERSIO + (Configuracio.isCAIB() ? "-caib" : "");
    try {
      log.info("Dir3Caib Version: " + ver);
    } catch (Throwable e) {
      System.out.println("Dir3Caib Version: " + ver);
    }

  }

}
