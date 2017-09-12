package es.caib.dir3caib.persistence.ejb;


import es.caib.dir3caib.persistence.utils.ResultadosImportacion;

import javax.ejb.Local;
import java.util.Date;

/**
 * 
 * @author anadal
 *
 */
@Local
public interface ImportadorUnidadesLocal {


  /**
   * Obtiene los ficheros de las unidades y sus relaciones a través de los WS de Madrid.
   *
   * @param fechaInicio fecha de inicio de la descarga
   * @param fechaFin    fecha fin de la descarga
   * @return listado de los nombres de los archivos CSV descargados
   * @throws Exception
   */
  public String[] descargarUnidadesWS(Date fechaInicio, Date fechaFin) throws Exception;

  /**
   *  Método que importa el contenido de los archivos de las unidades, historicos y contactos descargados previamente a través
   * de los WS.
   * @return
   * @throws Exception
   */
  public ResultadosImportacion importarUnidades() throws Exception;


  /**
   *  Tarea que en un primer paso descarga los archivos csv de las unidades y posteriormente importa el contenido en
   *  la base de datos, de esta manera realiza el proceso de sincronizacion con Madrid en un sólo
  *  proceso
  */
  public void importarUnidadesTask();

}
