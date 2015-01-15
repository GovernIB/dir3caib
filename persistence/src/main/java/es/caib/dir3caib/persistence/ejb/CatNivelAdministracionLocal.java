package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatNivelAdministracion;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatNivelAdministracionLocal extends BaseEjb<CatNivelAdministracion, Long> {
  
  public void deleteAll() throws Exception;
}
