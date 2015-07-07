package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.quartz.impl.triggers.CronTriggerImpl;

import javax.annotation.Resource;
import javax.annotation.security.RunAs;
import javax.ejb.*;
import java.text.ParseException;
import java.util.Date;

/**
 * 
 * 
 * @author anadal
 *
 */
@Stateless(name = "SincronitzacioDir3EJB")
@SecurityDomain("seycon")
@RunAs("DIR_ADMIN")
public class SincronitzacioDir3EJB  implements SincronitzacioDir3Local {
  
  @EJB(mappedName = "dir3caib/CatIslaEJB/local")
  protected CatIslaLocal catIslaEjb;
  @EJB(mappedName = "dir3caib/ImportadorCatalogoEJB/local")
  private ImportadorCatalogoLocal importadorCatalogo;
  @EJB(mappedName = "dir3caib/ImportadorUnidadesEJB/local")
  private ImportadorUnidadesLocal importadorUnidades;

  @EJB(mappedName = "dir3caib/ImportadorOficinasEJB/local")
  private ImportadorOficinasLocal importadorOficinas;
  
  protected final Logger log = Logger.getLogger(getClass());
  
  
  private static final String NAME_TIMER = "SincDir3Timer";
  
  @Resource
  private SessionContext context;

  @Override
  public void createTimer() {

    try {
      Date nextExecution = nextExecution();

      if (nextExecution != null) {
        log.info("Primera sincronització DIR3 sera " + nextExecution);
      } else {
        log.info("Sincronització DIR3 desactivada");
      }
      
    } catch (ParseException e) {
      log.error("Error creant sincronitzador: " + e.getMessage(), e);
    }
  }

  @Timeout
  public void timeOutHandler(Timer timer){
    try {
      
      timer.cancel();
      
      removeTimer(NAME_TIMER);
   
      nextExecution();

      sincronitzar();
      
    } catch (Exception e) {
      log.error("Error sincronitzant: " +e.getMessage(), e);
    }

  }

  protected Date nextExecution() throws ParseException {
    String cronExpression = Configuracio.getCronExpression();

    if(cronExpression != null && cronExpression.length() != 0) {
    
      Date currTime = new Date();
      CronTriggerImpl tr = new CronTriggerImpl();
      tr.setCronExpression(cronExpression);
      Date nextFireAt = tr.getFireTimeAfter(currTime);
      
      
      TimerService timerService = context.getTimerService();
      Timer timer2 = timerService.createTimer(nextFireAt, NAME_TIMER);
      if (log.isDebugEnabled()) {
        log.debug("Reference time: " + currTime);
        log.debug("Next fire after reference time: " + nextFireAt);
        log.debug("timeoutHandler : " + timer2.getInfo());
      }
      return nextFireAt;
    }
    return null;
  }
  
  
  protected void removeTimer(String name) {
    TimerService timerService = context.getTimerService();
    for (Object obj : timerService.getTimers()) {
            javax.ejb.Timer timer = (javax.ejb.Timer) obj;
            String scheduled = (String) timer.getInfo();
            //System.out.println("-> Timer Found : " + scheduled);
            if (scheduled.equals(name)) {
                    //System.out.println("-> Removing old timer : " + scheduled);
                    timer.cancel();
            }
    }
}
  


  public void sincronitzar()  {
    // TODO Auto-generated method stub
    System.out.println("Executa sincornitzar System.out.println()");
    log.info("Executa sincronitzar log.info();");
    
    try {

      importadorUnidades.importarUnidadesTask();
      importadorOficinas.importarOficinasTask();
      
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}
