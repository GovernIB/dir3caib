package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoEntidadPublica;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatTipoEntidadPublicaLocal extends BaseEjb<CatTipoEntidadPublica, String> {
  
  public void deleteAll() throws Exception;
}
