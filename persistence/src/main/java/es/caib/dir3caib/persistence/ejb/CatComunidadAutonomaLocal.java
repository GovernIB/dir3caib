package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatComunidadAutonomaLocal extends BaseEjb<CatComunidadAutonoma, Long> {
  
  public void deleteAll() throws Exception;
}
