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


  
  public String[] descargarUnidadesWS(Date fechaInicio, Date fechaFin) throws Exception;
  
  public ResultadosImportacion importarUnidades() throws Exception;

  /*
  public void importarContactos(String nombreFichero,  CSVReader reader, boolean quartz) throws Exception;
  
  public void importarHistoricos(String nombreFichero, CSVReader reader, boolean quartz);
  */
  public void importarUnidadesTask();

}
