package es.caib.dir3caib.persistence.ejb;

import java.util.List;

import es.caib.dir3caib.persistence.model.RelacionSirOfi;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal (Eliminar PKs multiples)
 * Date: 10/10/13
 */
@Local
public interface RelacionSirOfiLocal extends BaseEjb<RelacionSirOfi, Long> {
  
  public void deleteAll() throws Exception;
  
  public RelacionSirOfi findByPKs(String codigoUnidad, String codigoOficina) throws Exception;
  
  public List<String> getUnidadesOficinas() throws Exception;

}
