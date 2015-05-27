package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface DescargaLocal extends BaseEjb<Descarga, Long> {
  
  public Descarga findByTipo(String tipo) throws Exception;
  
  public void deleteByTipo(String tipo) throws Exception;

  public void deleteAllByTipo(String tipo) throws Exception;
}
