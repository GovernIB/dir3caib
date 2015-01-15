package es.caib.dir3caib.back.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


/* Clase que implementa un Job de Quartz.
* Es la que luego se indica en el archivo spring-quartz.xml para que se ejecute como Job.
*
* */
public class RunMeJob extends QuartzJobBean {
	private RunMeTask runMeTask;

	public void setRunMeTask(RunMeTask runMeTask) {
		this.runMeTask = runMeTask;
	}

	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		runMeTask.printMe();

	}
}