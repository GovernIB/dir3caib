package es.caib.dir3caib.back.jobs;

/*
Clase que representa una tarea(task) de Quartz.
Esta clase básicamente se encarga de realizar la importación del cátalogo,
unidades y oficinas.
 */
public class RunMeTask {


  //@Autowired
  //ImportadorCatalogoLocal importadorCatalogo;
  //@EJB(mappedName = "dir3caib/ImportadorCatalogoEJB/local")
  //private ImportadorCatalogoLocal importadorCatalogo;
  

  //@Autowired
  //ImportadorUnidades importadorUnidades;

  //@Autowired
  //ImportadorOficinas importadorOficinas;
  //@EJB(mappedName = "dir3caib/ImportadorOficinasEJB/local")
  //private ImportadorOficinasLocal importadorOficinas;


	public void printMe() {

    //importadorCatalogo.importarCatalogoTask();
    // TODO Como controlo que ha acabado lo anterior.
   // importadorUnidades.importarUnidadesTask();
    //importadorOficinas.importarOficinasTask();

		System.out.println("Spring 3 + Quartz 1.5.2 ~");
	}
}