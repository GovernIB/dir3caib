/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;

/**
 *
 * @author mgonzalez
 */
public interface ContactoUOLocal extends BaseEjb<ContactoUnidadOrganica, Long>{
  
   void deleteAll() throws Exception;

    void deleteByUnidad(String idUnidad) throws Exception;
  
  
}
