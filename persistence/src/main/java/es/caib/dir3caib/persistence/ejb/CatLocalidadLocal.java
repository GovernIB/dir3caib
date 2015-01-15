package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatLocalidad;
import es.caib.dir3caib.persistence.model.CatLocalidadPK;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatLocalidadLocal extends BaseEjb<CatLocalidad, CatLocalidadPK> {
  
  public void deleteAll() throws Exception;
}
