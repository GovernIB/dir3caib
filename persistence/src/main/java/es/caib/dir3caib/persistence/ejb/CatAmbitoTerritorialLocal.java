package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatAmbitoTerritorial;
import es.caib.dir3caib.persistence.model.CatAmbitoTerritorialPK;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatAmbitoTerritorialLocal extends BaseEjb<CatAmbitoTerritorial, CatAmbitoTerritorialPK> {
  
  public void deleteAll() throws Exception;

    public List<CatAmbitoTerritorial> getByAdministracion(Long nivelAdministracion) throws Exception;
}
