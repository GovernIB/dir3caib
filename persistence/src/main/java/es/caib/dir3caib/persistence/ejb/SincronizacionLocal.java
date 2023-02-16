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
     *
     * @param tipo de la sincronizacion (Oficina || Unidad)
     * @return
     * @throws Exception
     */
    Sincronizacion ultimaSincronizacionByTipo(String tipo) throws Exception;

    /**
     * Obtiene la última sincronizacion correctamente sincronizada según el tipo indicado
     *
     * @param tipo de la sincronizacion (Directorio || Catalogo)
     * @return
     * @throws Exception
     */
    Sincronizacion ultimaSincronizacionCorrecta(String tipo) throws Exception;

    /**
     * Obtiene la última sincronizacion finalizada correctamente (Coorecta || Vacia)
     *
     * @param tipo de la sincronizacion (Directorio || Catalogo)
     * @return
     * @throws Exception
     */
    Sincronizacion ultimaSincronizacionCompletada(String tipo) throws Exception;


    /**
     * Purga las sincronizaciones con más de un mes de antigüedad
     * @throws Exception
     */
    void purgarSincronizaciones() throws Exception;

    /**
     * Borra todas las sincronizacions del tipo indicado
     *
     * @param tipo
     * @throws Exception
     */
    void deleteAllByTipo(String tipo) throws Exception;

    /**
     * Actualiza el estado de una Sincronizacion
     *
     * @param codigo
     * @param estado
     * @throws Exception
     */
    void actualizarEstado(Long codigo, Long estado) throws Exception;

    /**
     * Descarga los ficheros del WS de DIR3 con los datos de Unidades y Oficinas
     *
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws Exception
     */
    Sincronizacion descargarDirectorioWS(Date fechaInicio, Date fechaFin) throws Exception;

    /**
     * Descarga los ficheros del WS de DIR3 con los datos del catálogo
     *
     * @return
     * @throws Exception
     */
    Sincronizacion descargarCatalogoWS() throws Exception;

    /**
     * Elimina una sincronización y sus ficheros correspondientes
     *
     * @param sincronizacion
     * @throws Exception
     */
    void eliminarSincronizacion(Sincronizacion sincronizacion) throws Exception;

    /**
     * Realiza la importación del directório (Unidades y Oficinas)
     *
     * @param sincronizacion
     * @throws Exception
     */
    Sincronizacion importarUnidadesOficinas(Sincronizacion sincronizacion) throws Exception;

    /**
     * Realiza la importación del catálogo
     *
     * @param sincronizacion
     * @throws Exception
     */
    Sincronizacion importarCatalogo(Sincronizacion sincronizacion, Boolean localidades) throws Exception;


    /**
     * Realiza la descarga y sincronización del Catálogo, las Unidades y las Ofiinas
     *
     * @return
     * @throws Exception
     */
    List<Sincronizacion> sincronizarDirectorio() throws Exception;

  /**
   * Realiza la descarga y sincronización del Catálogo
   *
   * @return
   * @throws Exception
   */
  Sincronizacion sincronizarCatalogo() throws Exception;

    /**
     * Realiza la descarga y sincronización de las Oficinas y Unidades
     *
     * @return
     * @throws Exception
     */
    Sincronizacion sincronizarUnidadesOficinas() throws Exception;


    /**
     * Cuenta el número de sincronizaciones según el tipo
     *
     * @param tipo de la sincronizacion (Directorio || Catalogo)
     * @throws Exception
     */
    Long contarSincronizaciones(String tipo) throws Exception;

}
