package es.caib.dir3caib.back.utils;

import es.caib.dir3caib.persistence.utils.DataBaseUtils;
import es.caib.dir3caib.persistence.utils.Versio;
import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created 10/11/14 12:08
 *
 * @author mgonzalez
 */
@Component
public class InitServlet extends HttpServlet {

  protected final Logger log = Logger.getLogger(getClass());


  @Override
  public void init(ServletConfig config) throws ServletException {



    // Inicialitzar Like de BBDD
    try {
      String dialect = Configuracio.getHibernateDialect();
      if (dialect.indexOf("Oracle") != -1) {
        log.info("Setting Oracle Like Manager.");
        DataBaseUtils.setLikeManager(new DataBaseUtils.OracleLike());
      } else {

        if (dialect.indexOf("PostgreSQL") != -1) {
          log.info("Setting PostgreSQL Like Manager.");
          DataBaseUtils.setLikeManager(new DataBaseUtils.PostgreSQLLike());
        } else {
          log.info("Setting Default Like Manager.");
          DataBaseUtils.setLikeManager(new DataBaseUtils.DefaultLike());
        }
      }
    } catch(Throwable th) {
      log.error("Error desconegut establint LikeManager " + th.getMessage(), th);
    }

     // Mostrar Versió
    String ver = Versio.VERSIO + (Configuracio.isCAIB()?"-caib" : "");
    try {
      log.info("Dir3Caib Version: " + ver);
    } catch (Throwable e) {
      System.out.println("Dir3Caib Version: " + ver);
    }

  }
}
