package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.RelacionSirOfi;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal (Eliminar PKs multiples)
 * Date: 10/10/13
 */
@Local
public interface RelacionSirOfiLocal extends BaseEjb<RelacionSirOfi, Long> {
  
  void deleteAll() throws Exception;
  
  RelacionSirOfi findByPKs(String codigoUnidad, String codigoOficina) throws Exception;
  
  List<String> getUnidadesOficinas() throws Exception;

}
