/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;

import javax.ejb.Local;
import java.util.List;

/**
 * @author mgonzalez
 */
@Local
public interface ContactoUOLocal extends BaseEjb<ContactoUnidadOrganica, Long> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;

    /**
     *
     * @param idUnidad
     * @throws Exception
     */
    void deleteByUnidad(String idUnidad) throws Exception;

    /**
     *
     * @param codigoUnidad
     * @return
     * @throws Exception
     */
    List<ContactoUnidadOrganica> getContactosByUnidad(String codigoUnidad) throws Exception;

}
