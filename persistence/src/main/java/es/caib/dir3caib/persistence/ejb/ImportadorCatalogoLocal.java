package es.caib.dir3caib.persistence.ejb;

import javax.ejb.Local;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;

/**
 * 
 * @author anadal
 * 
 */
@Local
public interface ImportadorCatalogoLocal {

  public void descargarCatalogoWS(String fechaInicio, String fechaFin)
      throws Exception;

  public void importarCatalogoTask();

  public ResultadosImportacion importarCatalogo() throws Exception;

}
