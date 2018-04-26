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
  Sincronizacion ultimaSincronizacionByTipo(String tipo) throws Exception;

  /**
   * Obtiene la última sincronizacion correctamente sincronizada según el tipo indicado
   * @param tipo de la sincronizacion (Directorio || Catalogo)
   * @return
   * @throws Exception
     */
  Sincronizacion ultimaSincronizacionCorrecta(String tipo) throws Exception;

  /**
   * Obtiene la última sincronizacion finalizada correctamente (Coorecta || Vacia)
   * @param tipo de la sincronizacion (Directorio || Catalogo)
   * @return
   * @throws Exception
   */
  Sincronizacion ultimaSincronizacionCompletada(String tipo) throws Exception;

  /**
   * Borra todas las sincronizacions del tipo indicado
   * @param tipo
   * @throws Exception
   */
  void deleteAllByTipo(String tipo) throws Exception;

  /**
   * Actualiza el estado de una Sincronizacion
   * @param codigo
   * @param estado
   * @throws Exception
   */
  void actualizarEstado(Long codigo, Long estado) throws Exception;

  /**
   * Descarga los ficheros del WS de DIR3 con los datos de Unidades y Oficinas
   * @param fechaInicio
   * @param fechaFin
   * @return
   * @throws Exception
   */
  Sincronizacion descargarDirectorioWS(Date fechaInicio, Date fechaFin) throws Exception;

  /**
   * Descarga los ficheros del WS de DIR3 con los datos del catálogo
   * @param fechaInicio
   * @return
   * @throws Exception
   */
  Sincronizacion descargarCatalogoWS(Date fechaInicio) throws Exception;

  /**
   * Elimina una sincronización y sus ficheros correspondientes
   * @param sincronizacion
   * @throws Exception
   */
  void eliminarSincronizacion(Sincronizacion sincronizacion) throws Exception;

  /**
   * Realiza la importación del directório (Unidades y Oficinas)
   * @param sincronizacion
   * @throws Exception
   */
  Sincronizacion importarDirectorio(Sincronizacion sincronizacion) throws Exception;

  /**
   * Realiza la importación del catálogo
   * @param sincronizacion
   * @throws Exception
   */
  Sincronizacion importarCatalogo(Sincronizacion sincronizacion) throws Exception;

  /**
   * Realiza la descarga y sincronización del Catálogo, las Unidades y las Ofiinas
   * @return
   * @throws Exception
   */
  List<Sincronizacion> sincronizarDirectorio() throws Exception;

  /**
   * Tarea programada que realiza la descarga e importación del directório (Unidades y Oficinas)
   * @throws Exception
   */
  void sincronizarDirectorioTask() throws Exception;

}
