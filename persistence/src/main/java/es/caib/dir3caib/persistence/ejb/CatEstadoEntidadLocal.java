package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatEstadoEntidad;

import java.util.List;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatEstadoEntidadLocal extends BaseEjb<CatEstadoEntidad, String> {

  CatEstadoEntidad findByCodigo(String codigo) throws Exception;
  void deleteAll() throws Exception;
  
  List<CatEstadoEntidad> getAll(String estado) throws Exception;
}
