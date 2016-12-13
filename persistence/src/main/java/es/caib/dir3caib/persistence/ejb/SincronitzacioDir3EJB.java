package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;
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
        log.info("Primera sincronitzacio DIR3 sera " + nextExecution);
      } else {
        log.info("Sincronització DIR3 desactivada");
      }
      
    } catch (ParseException e) {
      log.error("Error creant sincronitzador: " + e.getMessage(), e);
    }
  }
  
  @Override
  public void clearTimers() {
    removeTimer(NAME_TIMER);
  }
  

  @Timeout
  @TransactionTimeout(value = 18000)
  public void timeOutHandler(Timer timer){
    try {
      long timeRemaining = timer.getTimeRemaining();
      
      timer.cancel();
      
      removeTimer(NAME_TIMER);
   
      nextExecution();

      // Si han passat més de 30segons de l'hora pravista d'execució
      // llavors no l'executam. 
      if (timeRemaining > -30000) {
        sincronitzar();
      } else {
        log.warn("Timer programat per "
          + new Date(System.currentTimeMillis() + timeRemaining)
          + " no s'executara.");
      }
      
    } catch (Throwable e) {
      log.error("Error sincronitzant: " +e.getMessage(), e);
    }

  }

  protected Date nextExecution() throws ParseException {
    String cronExpression = Configuracio.getCronExpression();

    if(cronExpression == null) { // Si la propiedad no está definida, se obtiene una por defecto.
      cronExpression = Dir3caibConstantes.CRON_SINCRONIZAR_DIR3;
    }

      Date currTime = new Date();
      CronTriggerImpl tr = new CronTriggerImpl();
      tr.setCronExpression(cronExpression);
      Date nextFireAt = tr.getFireTimeAfter(currTime);

      TimerService timerService = context.getTimerService();

      Timer timer2 = timerService.createTimer(nextFireAt, NAME_TIMER /* timerConfig */);
      
      if (log.isDebugEnabled()) {
        log.debug("Reference time: " + currTime);
        log.debug("Next fire after reference time: " + nextFireAt);
        log.debug("timeoutHandler : " + timer2.getInfo());
      }
      return nextFireAt;

  }
  
  
  protected void removeTimer(String name) {
    TimerService timerService = context.getTimerService();
    for (Object obj : timerService.getTimers()) {
            javax.ejb.Timer timer = (javax.ejb.Timer) obj;
            String scheduled = (String) timer.getInfo();
            //System.out.println("-> Timer Found : " + scheduled);
            if (scheduled.equals(name)) {
                    log.info("Removing old timer : " + scheduled + "(" + timer.getNextTimeout() + ")");
                    timer.cancel();
            }
    }
}

  @TransactionTimeout(value = 18000)
  public void sincronitzar()  {
    
    log.info("Entra dins el metode sincronitzar() de SincronitzacioDir3EJB");
   
    try {
      
      importadorUnidades.importarUnidadesTask();
      importadorOficinas.importarOficinasTask();
      
    } catch (Throwable e) {
      log.error("Error Sincronitzant ...", e);
    }

  }

}
