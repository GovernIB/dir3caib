package es.caib.dir3caib.persistence.ejb;

import javax.ejb.Local;

/**
 *
 * @author earrivi
 */
@Local
public interface SchedulerLocal {

    /**
     * Sincroniza el directorio
     * @throws Exception
     */
    void sincronizarDirectorio() throws Exception;

    /**
     * Purga las sincronizaciones con más de un mes de antigüedad
     * @throws Exception
     */
    void purgarSincronizaciones() throws Exception;
}
