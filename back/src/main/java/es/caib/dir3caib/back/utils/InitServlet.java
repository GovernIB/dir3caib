package es.caib.dir3caib.back.utils;

import es.caib.dir3caib.back.jobs.JobNuevo;
import es.caib.dir3caib.persistence.utils.DataBaseUtils;
import es.caib.dir3caib.persistence.utils.Versio;
import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
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



  private Scheduler scheduler;


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

    //Ejecutar CRON de importacion
    String cronExpression = Configuracio.getCronExpression();

      if(cronExpression != null || cronExpression.length()>0){
          try {

              // Este código ejecuta el job pero da null al usar un ejb.


              JobDetail jobDetail = new JobDetail("job","group",JobNuevo.class);

              CronTrigger trigger = new CronTrigger("trigger","group");

             // trigger.setStartTime(new Date());
             // trigger.setEndTime(new Date(new Date().getTime() + 10 * 60 * 1000));
              trigger.setCronExpression(cronExpression);

              /** STEP 4 : INSTANTIATE SCHEDULER FACTORY BEAN AND SET ITS PROPERTIES **/
              SchedulerFactory sfb = new StdSchedulerFactory();
              Scheduler scheduler = sfb.getScheduler();

              scheduler.scheduleJob(jobDetail, trigger);

              scheduler.start();
          }catch(Throwable th){

          }
      }

  }
}
