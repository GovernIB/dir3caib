/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ContactoOfi;

import javax.ejb.Local;

/**
 * @author mgonzalez
 */
@Local
public interface ContactoOfiLocal extends BaseEjb<ContactoOfi, Long> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;

    /**
     *
     * @param idOficina
     * @throws Exception
     */
    void deleteByOficina(String idOficina) throws Exception;
}
