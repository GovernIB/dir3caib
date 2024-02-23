package es.caib.dir3caib.front.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import es.caib.dir3caib.persistence.utils.Versio;
import es.caib.dir3caib.utils.Configuracio;

@Component
public class InitServlet extends HttpServlet {

	protected final Logger log = Logger.getLogger(getClass());

	@Override
	public void init(ServletConfig config) throws ServletException {

		// Mostrar Versió
		String ver = Versio.VERSIO + (Configuracio.isCAIB() ? "-caib" : "");
		try {
			log.info("Dir3Caib Front Version: " + ver);
		} catch (Throwable e) {
			System.out.println("Dir3Caib Front Version: " + ver);
		}

	}

}
