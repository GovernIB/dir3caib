package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatLocalidad;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal (Eliminar PKs multiples)
 * Date: 10/10/13
 */
@Local
public interface CatLocalidadLocal extends BaseEjb<CatLocalidad, Long> {
  
  CatLocalidad findByPKs(Long codigoLocalidad, Long codigoProvincia,
                         String codigoEntidadGeografica) throws Exception;
  
  void deleteAll() throws Exception;

}
