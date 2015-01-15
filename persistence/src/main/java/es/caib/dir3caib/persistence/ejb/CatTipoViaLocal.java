package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoVia;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatTipoViaLocal extends BaseEjb<CatTipoVia, Long> {
  
  public void deleteAll() throws Exception;
}
