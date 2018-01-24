package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatJerarquiaOficina;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatJerarquiaOficinaLocal extends BaseEjb<CatJerarquiaOficina, Long> {
  
  void deleteAll() throws Exception;
}
