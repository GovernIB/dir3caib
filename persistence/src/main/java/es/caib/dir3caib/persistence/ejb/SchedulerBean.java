package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "SchedulerEJB")
@RunAs("DIR_ADMIN")
public class SchedulerBean implements SchedulerLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/SincronizacionEJB/local")
    private SincronizacionLocal sincronizacionEjb;

    @Override
    public void sincronizarDirectorio() throws Exception {
        if (Configuracio.isSincronizar()) {
            log.info("-------------------------------------------");
            log.info("INICIO ACTUALIZACION PROGRAMADA DE UNIDADES Y OFICINAS");
            log.info("");
            try {
                sincronizacionEjb.sincronizarDirectorioTask();
                log.info("-------------------------------------------");
                log.info("FIN ACTUALIZACION PROGRAMADA DE UNIDADES Y OFICINAS");
                log.info("");

            } catch (Throwable e) {
                log.error("Error Sincronitzant ...", e);
            }
        }
    }
}