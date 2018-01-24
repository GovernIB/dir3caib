package es.caib.dir3caib.persistence.ejb;

import javax.ejb.Local;

/**
 *
 * @author earrivi
 */
@Local
public interface SchedulerLocal {

    void sincronizarDirectorio() throws Exception;
}
