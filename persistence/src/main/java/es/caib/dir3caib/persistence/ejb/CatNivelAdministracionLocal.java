package es.caib.dir3caib.persistence.ejb;

import java.util.List;

import javax.ejb.Local;

import es.caib.dir3caib.persistence.model.CatNivelAdministracion;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatNivelAdministracionLocal extends BaseEjb<CatNivelAdministracion, Long> {
  
  void deleteAll() throws Exception;
  
  List<CatNivelAdministracion> getByEstado(String estado) throws Exception;
  
}
