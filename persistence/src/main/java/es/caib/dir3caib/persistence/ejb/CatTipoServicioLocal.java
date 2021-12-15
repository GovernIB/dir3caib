package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoServicio;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author jagarcia
 * Date: 02/12/21
 */
@Local
public interface CatTipoServicioLocal extends BaseEjb<CatTipoServicio, Long> {
  
  void deleteAll() throws Exception;
}
