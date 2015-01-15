package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatEntidadGeografica;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatEntidadGeograficaLocal extends BaseEjb<CatEntidadGeografica, String> {
  
  public void deleteAll() throws Exception;
  
}
