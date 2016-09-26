package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface DescargaLocal extends BaseEjb<Descarga, Long> {
  /**
   * Obtiene la ultima descarga realizada del tipo indicado
   * @param tipo de la descarga (Oficina || Unidad)
   * @return
   * @throws Exception
   */
  public Descarga ultimaDescarga(String tipo) throws Exception;

  /**
   * Obtiene al última descarga correctamente sincronizada según el tipo indicado
   * @param tipo de la descarga (Oficina || Unidad)
   * @return
   * @throws Exception
     */
  public Descarga ultimaDescargaSincronizada(String tipo) throws Exception;

  /**
   * Obtiene todas las descargas del tipo indicado
   * @param tipo
   * @return
   * @throws Exception
   */
  public List<Descarga> getAllByTipo(String tipo) throws Exception;

  /**
   * Borra todas las descargas del tipo indicado
   * @param tipo
   * @throws Exception
   */
  public void deleteAllByTipo(String tipo) throws Exception;

  /**
   * Obtiene el total por tipo.
   * @param tipo
   * @return
   * @throws Exception
   */
  public Long getTotalByTipo(String tipo) throws Exception;

  /**
   *  Calcula la paginació de les descarregues
   * @param inicio
   * @param tipo
   * @return
   * @throws Exception
   */
  public List<Descarga> getPaginationByTipo(int inicio, String tipo) throws Exception;
}
