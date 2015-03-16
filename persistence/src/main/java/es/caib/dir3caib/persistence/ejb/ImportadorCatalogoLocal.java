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
public interface ImportadorCatalogoLocal {

  public void descargarCatalogoWS(Date fechaInicio, Date fechaFin)
      throws Exception;

  public void importarCatalogoTask();

  public ResultadosImportacion importarCatalogo() throws Exception;

}
