package es.caib.dir3caib.persistence.ejb;

import javax.ejb.Local;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;

/**
 * 
 * @author anadal
 * 
 */
@Local
public interface ImportadorOficinasLocal {

  public ResultadosImportacion importarOficinas(boolean isUpdate) throws Exception;

  public void descargarOficinasWS(String fechaInicio, String fechaFin) throws Exception;

}
