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
public interface ImportadorOficinasLocal {

  public ResultadosImportacion importarOficinas(boolean isUpdate) throws Exception;

  public void descargarOficinasWS(Date fechaInicio, Date fechaFin) throws Exception;

  public void importarOficinasTask();

}
