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
     * @throws Exception
     */
    public void eliminarDirectorio() throws Exception;

    /**
     * @throws Exception
     */
    public void eliminarCatalogo() throws Exception;

    /**
     * @throws Exception
     */
    public void eliminarUnidades() throws Exception;

    /**
     * @throws Exception
     */
    public void eliminarOficinas() throws Exception;

}
