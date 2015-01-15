package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoContacto;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatTipoContactoLocal extends BaseEjb<CatTipoContacto, String> {
  
  public void deleteAll() throws Exception;
}
