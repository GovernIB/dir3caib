package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;

import javax.ejb.Local;
import java.util.Date;
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
  Descarga ultimaDescarga(String tipo) throws Exception;

  /**
   * Obtiene el total de Descargas realizadas del tipo indicado
   * @param tipo de la descarga (Oficina || Unidad)
   * @return
   * @throws Exception
   */
  Long totalDescargas(String tipo) throws Exception;

  /**
   * Obtiene la última descarga correctamente sincronizada según el tipo indicado
   * @param tipo de la descarga (Oficina || Unidad)
   * @return
   * @throws Exception
     */
  Descarga ultimaDescargaSincronizada(String tipo) throws Exception;

  /**
   * Obtiene todas las descargas del tipo indicado
   * @param tipo
   * @return
   * @throws Exception
   */
  List<Descarga> getAllByTipo(String tipo) throws Exception;

  /**
   * Borra todas las descargas del tipo indicado
   * @param tipo
   * @throws Exception
   */
  void deleteAllByTipo(String tipo) throws Exception;

  /**
   * Actualiza el estado de una Descarga
   * @param codigo
   * @param estado
   * @throws Exception
   */
  void actualizarEstado(Long codigo, String estado) throws Exception;

  /**
   * Obtiene el total por tipo.
   * @param tipo
   * @return
   * @throws Exception
   */
  Long getTotalByTipo(String tipo) throws Exception;

  /**
   *  Calcula la paginació de les descarregues
   * @param inicio
   * @param tipo
   * @return
   * @throws Exception
   */
  List<Descarga> getPaginationByTipo(int inicio, String tipo) throws Exception;

  /**
   *
   * @param tipo
   * @param fechaInicio
   * @param fechaFin
   * @return
   * @throws Exception
   */
  Descarga descargarDirectorioWS(String tipo, Date fechaInicio, Date fechaFin) throws Exception;
}
