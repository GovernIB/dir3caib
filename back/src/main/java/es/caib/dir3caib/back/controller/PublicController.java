package es.caib.dir3caib.back.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.caib.dir3caib.persistence.utils.Versio;

@Controller
public class PublicController {
	
	protected final Logger log = Logger.getLogger(getClass());

	@RequestMapping(value = "/public/versio")
	public void versio(HttpServletResponse response)
			throws Exception {
		response.getWriter().write(Versio.VERSIO);
		response.getWriter().flush();
		response.getWriter().close();

	}

}
