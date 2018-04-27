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
     * Elimina toda la bbdd (Oficinas, Unidades, Catalog y Sincronizaciones)
     * @throws Exception
     */
    void eliminarCompleto() throws Exception;

    /**
     * Elimina las Oficinas y Unidades y sus datos relacionados
     * @throws Exception
     */
    void eliminarOficinasUnidades() throws Exception;

    /**
     * Elimina el Catálogo
     * @throws Exception
     */
    void eliminarCatalogo() throws Exception;

    /**
     * Elimina todas las unidades y sus datos relacionados
     * @throws Exception
     */
    void eliminarUnidades() throws Exception;

    /**
     * Elimina todas las oficinas y sus datos relacionados
     * @throws Exception
     */
    void eliminarOficinas() throws Exception;

    /**
     * Elimina las Oficinas y Unidades, realiza una descarga inicia e importa los datos
     * @throws Exception
     */
    void restaurarOficinasUnidades() throws Exception;

}
