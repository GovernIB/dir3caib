package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.CatNivelAdministracion;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.json.UnidadExportar;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "SchedulerEJB")
@RunAs("DIR_ADMIN")
public class SchedulerBean implements SchedulerLocal {

	protected final Logger log = Logger.getLogger(getClass());

	@EJB(mappedName = "dir3caib/SincronizacionEJB/local")
	private SincronizacionLocal sincronizacionEjb;

	@EJB(mappedName = "dir3caib/Dir3RestEJB/local")
	private Dir3RestLocal dir3RestEjb;

	@EJB(mappedName = "dir3caib/CatComunidadAutonomaEJB/local")
	private CatComunidadAutonomaLocal comunidadAutonomaEjb;

	@EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
	protected CatNivelAdministracionLocal catNivelAdministracionEjb;

	@Override
	@TransactionTimeout(value = 50000)
	public void sincronizarDirectorio() throws Exception {
		if (Configuracio.isSincronizar()) {
			log.info("-------------------------------------------");
			log.info("INICIO ACTUALIZACION PROGRAMADA DE UNIDADES Y OFICINAS");
			log.info("");
			try {
				sincronizacionEjb.sincronizarUnidadesOficinas();
				log.info("-------------------------------------------");
				log.info("FIN ACTUALIZACION PROGRAMADA DE UNIDADES Y OFICINAS");
				log.info("");

			} catch (Throwable e) {
				log.error("Error Sincronitzant ...", e);
			}
		}
	}

	@Override
	public void purgarSincronizaciones() throws Exception {
		log.info("------------- Purgando SINCRONIZACIONES -------------");
		try {
			sincronizacionEjb.purgarSincronizaciones();
		} catch (Exception e) {
			log.info("Error purgando integraciones");
		}

	}

	@Override
	@TransactionTimeout(value = 10000)
	public void generarJsonUnidadesVigentes() throws Exception {
		log.info("------------ Inicio Generación ficheros JSON unidades -------------");
		try { 

			long start = System.currentTimeMillis();

			System.gc();

			List<CatComunidadAutonoma> comunidades = comunidadAutonomaEjb
					.getByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

			List<CatNivelAdministracion> niveles = catNivelAdministracionEjb
					.getByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

			for (CatNivelAdministracion nivel : niveles) {
				for (CatComunidadAutonoma comunidad : comunidades) {

					long inicio = System.currentTimeMillis();

					List<UnidadExportar> resultados = dir3RestEjb.exportarUnidades(comunidad.getCodigoComunidad(),
							nivel.getCodigoNivelAdministracion());
					log.info("Nivel: " + nivel.getCodigoNivelAdministracion() + " - " + "Comunidad: "
							+ comunidad.getDescripcionComunidad() + "(" + comunidad.getCodigoComunidad() + ") => "
							+ resultados.size());

					ObjectMapper objectMapper = new ObjectMapper();
					String json = "unitats = " + objectMapper.writeValueAsString(resultados);

					String filePath = Configuracio.getJsonPath() + "/u_" + nivel.getCodigoNivelAdministracion() + "_"
							+ comunidad.getCodigoComunidad() + ".js";
					
					File f = new File(filePath); 
					FileUtils.writeStringToFile(f, json, "UTF-8");

					log.info("Saved: " + filePath + " in "
							+ Utils.formatElapsedTime(System.currentTimeMillis() - inicio));
				}

				// Generamos los ficheros para las unidades sin comunidad autonoma
				long inicio = System.currentTimeMillis();
				List<UnidadExportar> resultados = dir3RestEjb.exportarUnidades(null,
						nivel.getCodigoNivelAdministracion());
				log.info("Comunidad: NULL - Nivel: " + nivel.getCodigoNivelAdministracion() + " => "
						+ resultados.size());

				ObjectMapper objectMapper = new ObjectMapper();
				String json = "unitats = " + objectMapper.writeValueAsString(resultados);

				String filePath = Configuracio.getJsonPath() + "/u_" + nivel.getCodigoNivelAdministracion() + "_0.js";
				
				File f = new File(filePath); 
				FileUtils.writeStringToFile(f, json, "UTF-8");

				log.info("Saved: " + filePath + " in " + Utils.formatElapsedTime(System.currentTimeMillis() - inicio));

			}

			System.gc();

			log.info("Tiempo de ejecución: " + Utils.formatElapsedTime(System.currentTimeMillis() - start));

		} catch (Exception e) {
			log.info("Error generando los ficheros DATA ");
		}
		log.info("-------------------------------------------");
		log.info("Fin Generación ficheros Data unidades");
	}
}
