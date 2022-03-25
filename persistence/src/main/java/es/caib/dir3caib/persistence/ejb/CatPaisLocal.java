package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatPais;

import java.util.List;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatPaisLocal extends BaseEjb<CatPais, Long> {
  
  void deleteAll() throws Exception;

  List<CatPais> getAll(String estado) throws Exception;

  List<CatPais> getByEstado(String estado) throws Exception;
}
