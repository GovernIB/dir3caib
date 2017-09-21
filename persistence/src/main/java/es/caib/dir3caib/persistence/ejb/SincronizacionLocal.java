package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Sincronizacion;

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
public interface SincronizacionLocal extends BaseEjb<Sincronizacion, Long> {
  /**
   * Obtiene la ultima sincronizacion realizada del tipo indicado
   * @param tipo de la sincronizacion (Oficina || Unidad)
   * @return
   * @throws Exception
   */
  public Sincronizacion ultimaSincronizacion(String tipo) throws Exception;

  /**
   * Obtiene el total de Sincronizacions realizadas del tipo indicado
   * @param tipo de la sincronizacion (Oficina || Unidad)
   * @return
   * @throws Exception
   */
  public Long totalSincronizacions(String tipo) throws Exception;

  /**
   * Obtiene la última sincronizacion correctamente sincronizada según el tipo indicado
   * @param tipo de la sincronizacion (Directorio || Catalogo)
   * @return
   * @throws Exception
     */
  public Sincronizacion ultimaSincronizacionCorrecta(String tipo) throws Exception;

  /**
   * Obtiene todas las sincronizacions del tipo indicado
   * @param tipo
   * @return
   * @throws Exception
   */
  public List<Sincronizacion> getAllByTipo(String tipo) throws Exception;

  /**
   * Borra todas las sincronizacions del tipo indicado
   * @param tipo
   * @throws Exception
   */
  public void deleteAllByTipo(String tipo) throws Exception;

  /**
   * Actualiza el estado de una Sincronizacion
   * @param codigo
   * @param estado
   * @throws Exception
   */
  public void actualizarEstado(Long codigo, Long estado) throws Exception;

  /**
   * Obtiene el total por tipo.
   * @param tipo
   * @return
   * @throws Exception
   */
  public Long getTotalByTipo(String tipo) throws Exception;

  /**
   * Descarga los ficheros del WS de DIR3 con los datos de las Unidades y Oficinas
   * @param tipo
   * @param fechaInicio
   * @param fechaFin
   * @return
   * @throws Exception
   */
  public Sincronizacion descargarDirectorioWS(String tipo, Date fechaInicio, Date fechaFin) throws Exception;

  /**
   * Realiza la importación del directório (Unidades y Oficinas)
   * @param sincronizacion
   * @throws Exception
   */
  public void importarDirectorio(Sincronizacion sincronizacion) throws Exception;

  /**
   * Realiza la importación del catálogo
   * @param sincronizacion
   * @throws Exception
   */
  public void importarCatalogo(Sincronizacion sincronizacion) throws Exception;

  /**
   * Tarea programada que realiza la descarga e importación del directório (Unidades y Oficinas)
   * @throws Exception
   */
  public void sincronizarDirectorioTask() throws Exception;

}
