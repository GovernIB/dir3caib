package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatAmbitoTerritorial;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal (Eliminar PKs multiples)
 * Date: 10/10/13
 */
@Local
public interface CatAmbitoTerritorialLocal extends BaseEjb<CatAmbitoTerritorial, Long> {
  
  void deleteAll() throws Exception;

  
  CatAmbitoTerritorial findByPKs(String codigoAmbito, Long codigoNivelAdministracion) throws Exception;
}
