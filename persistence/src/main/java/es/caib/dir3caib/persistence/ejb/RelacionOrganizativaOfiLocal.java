package es.caib.dir3caib.persistence.ejb;

import java.util.List;

import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi;
import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfiPK;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface RelacionOrganizativaOfiLocal extends BaseEjb<RelacionOrganizativaOfi, RelacionOrganizativaOfiPK> {

  public void deleteAll() throws Exception;

  public RelacionOrganizativaOfi findByPKs(String codigoUnidad, String codigoOficina) throws Exception;

  public List<String> getUnidadesOficinas() throws Exception;

}
