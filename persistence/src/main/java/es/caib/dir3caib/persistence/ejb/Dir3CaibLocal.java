package es.caib.dir3caib.persistence.ejb;

import javax.ejb.Local;

/**
 * Created 12/09/17 9:52
 *
 * @author earrivi
 */
@Local
public interface Dir3CaibLocal {

    /**
     *
     * @throws Exception
     */
    void eliminarCompleto() throws Exception;

    /**
     *
     * @throws Exception
     */
    void eliminarDirectorio() throws Exception;

    /**
     *
     * @throws Exception
     */
    void eliminarCatalogo() throws Exception;

    /**
     *
     * @throws Exception
     */
    void eliminarUnidades() throws Exception;

    /**
     *
     * @throws Exception
     */
    void eliminarOficinas() throws Exception;

    /**
     *
     * @throws Exception
     */
    void restaurarDirectorio() throws Exception;

}
