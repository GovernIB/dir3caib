/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;

import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author mgonzalez
 */
@Local
public interface ContactoUOLocal extends BaseEjb<ContactoUnidadOrganica, Long>{
  
   void deleteAll() throws Exception;

    void deleteByUnidad(String idUnidad) throws Exception;

   List<ContactoUnidadOrganica> getContactosByUnidad(String codigoUnidad) throws Exception;
  
  
}
