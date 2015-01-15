package es.caib.dir3caib.persistence.ejb;


import javax.ejb.Local;


import es.caib.dir3caib.persistence.utils.ResultadosImportacion;

/**
 * 
 * @author anadal
 *
 */
@Local
public interface ImportadorUnidadesLocal {


  
  public void descargarUnidadesWS(String fechaInicio, String fechaFin) throws Exception;
  
  public ResultadosImportacion importarUnidades() throws Exception;

  /*
  public void importarContactos(String nombreFichero,  CSVReader reader, boolean quartz) throws Exception;
  
  public void importarHistoricos(String nombreFichero, CSVReader reader, boolean quartz);
  */
  public void importarUnidadesTask();

}
