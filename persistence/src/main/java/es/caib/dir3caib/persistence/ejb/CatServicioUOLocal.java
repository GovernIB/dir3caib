package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatServicioUO;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatServicioUOLocal extends BaseEjb<CatServicioUO, Long> {
  
  void deleteAll() throws Exception;
}
