package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;

import javax.ejb.Local;
import java.util.Date;

/**
 * 
 * @author anadal
 * 
 */
@Local
public interface ImportadorOficinasLocal {

  /**
   * Obtiene los ficheros de las oficinas y sus relaciones a través de los WS de Madrid.
   *
   * @param fechaInicio fecha de inicio de la descarga
   * @param fechaFin    fecha fin de la descarga
   * @return listado de los nombres de los archivos CSV descargados
   * @throws Exception
   */
  public Descarga descargarOficinasWS(Date fechaInicio, Date fechaFin) throws Exception;

  /**
   * Importa en la Bd los datos que contienen los archivos descargados previamente via WS
   *
   * @param isUpdate indica si es una sincronización o es una actualización
   * @return
   * @throws Exception
   */
  public ResultadosImportacion importarOficinas(boolean isUpdate) throws Exception;

  /**
   *  Tarea que en un primer paso descarga los archivos csv de las oficinas y posteriormente importa el contenido en
   *  la base de datos, de esta manera realiza el proceso de sincronizacion con Madrid en un sólo
   *  proceso
   */
  public void importarOficinasTask();

  /**
   * Elimina las Oficinas existentes, realiza una descarga e importa los datos
   * @return
   * @throws Exception
   */
  public ResultadosImportacion restaurarOficinas() throws Exception;

}
