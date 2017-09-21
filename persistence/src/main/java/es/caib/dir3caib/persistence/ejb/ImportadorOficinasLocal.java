package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Sincronizacion;

import javax.ejb.Local;

/**
 * 
 * @author anadal
 * 
 */
@Local
public interface ImportadorOficinasLocal {

  /**
   * Importa en la Bd los datos que contienen los archivos descargados previamente via WS
   *
   * @param
   * @return
   * @throws Exception
   */
  public void importarOficinas(Sincronizacion sincronizacion) throws Exception;

}
