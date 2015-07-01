package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatEstadoEntidad;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatEstadoEntidadLocal extends BaseEjb<CatEstadoEntidad, String> {

  public CatEstadoEntidad findByCodigo(String codigo) throws Exception;
  public void deleteAll() throws Exception;
}
