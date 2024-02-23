package es.caib.dir3caib.front.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;

public class DownloadController extends HttpServlet {

	private static final long serialVersionUID = 8275864328637192048L;

	protected final Logger log = Logger.getLogger(getClass());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String nivelParam = req.getParameter("nivel");
		String comunidadParam = req.getParameter("comunidad");

		if (Utils.isNotEmpty(nivelParam) && Utils.isNotEmpty(comunidadParam)) {
			String nomFitxer = "u_" + nivelParam + "_" + comunidadParam + ".js";
			File file = new File(Configuracio.getJsonPath() + nomFitxer);
			if (file.exists() && !file.isDirectory()) {
				String data = FileUtils.readFileToString(file, "UTF-8");
				resp.setHeader("Content-Type", "text/javascript; charset=UTF-8");
				resp.getWriter().write(data);
			}else {
				resp.setHeader("Content-Type", "text/javascript; charset=UTF-8");
				resp.getWriter().write("{}");	
			}
		}
	}
}
