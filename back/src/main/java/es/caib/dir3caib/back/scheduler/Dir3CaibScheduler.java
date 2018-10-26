package es.caib.dir3caib.back.scheduler;

import es.caib.dir3caib.persistence.ejb.SchedulerLocal;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.ejb.EJB;

@Service
public class Dir3CaibScheduler {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/SchedulerEJB/local")
    private SchedulerLocal schedulerEjb;


    // @Scheduled(cron = "0 0 4 * * *") // Cada día a las 04:00h
    public void reintentarEnvioSir(){

        try {
            schedulerEjb.sincronizarDirectorio();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
