package es.caib.dir3caib.back.jobs;

import es.caib.dir3caib.persistence.ejb.ImportadorCatalogoLocal;
import es.caib.dir3caib.persistence.ejb.ImportadorOficinasLocal;
import es.caib.dir3caib.persistence.ejb.ImportadorUnidadesLocal;

import javax.ejb.EJB;

/*
Clase que representa una tarea(task) de Quartz.
Esta clase básicamente se encarga de realizar la importación del cátalogo,
unidades y oficinas.
 */
public class RunMeTask {


  @EJB(mappedName = "dir3caib/ImportadorCatalogoEJB/local")
  private ImportadorCatalogoLocal importadorCatalogo;
  @EJB(mappedName = "dir3caib/ImportadorUnidadesEJB/local")
  private ImportadorUnidadesLocal importadorUnidades;

  @EJB(mappedName = "dir3caib/ImportadorOficinasEJB/local")
  private ImportadorOficinasLocal importadorOficinas;


	public void printMe() {

    importadorCatalogo.importarCatalogoTask();

    importadorUnidades.importarUnidadesTask();
    importadorOficinas.importarOficinasTask();

		//System.out.println("Spring 3 + Quartz 1.5.2 ~");
	}
}