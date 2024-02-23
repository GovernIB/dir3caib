package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.CatNivelAdministracion;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Sincronizacion;
import es.caib.dir3caib.persistence.model.json.UnidadExportar;
import es.caib.dir3caib.persistence.utils.MailUtils;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;
import es.caib.dir3caib.ws.dir3.catalogo.client.SC21CTVolcadoCatalogos;
import es.caib.dir3caib.ws.dir3.catalogo.client.SC21CTVolcadoCatalogosService;
import es.caib.dir3caib.ws.dir3.oficina.client.OficinasVersionWs;
import es.caib.dir3caib.ws.dir3.oficina.client.SD02OFDescargaOficinas;
import es.caib.dir3caib.ws.dir3.oficina.client.SD02OFDescargaOficinasService;
import es.caib.dir3caib.ws.dir3.oficina.client.TipoConsultaOF;
import es.caib.dir3caib.ws.dir3.unidad.client.SD01UNDescargaUnidades;
import es.caib.dir3caib.ws.dir3.unidad.client.SD01UNDescargaUnidadesService;
import es.caib.dir3caib.ws.dir3.unidad.client.TipoConsultaUO;
import es.caib.dir3caib.ws.dir3.unidad.client.UnidadesWsVersion;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.ws.BindingProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi Date: 10/10/13
 */
@Stateless(name = "SincronizacionEJB")
@SecurityDomain("seycon")
@RolesAllowed({ Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.DIR_WS })
public class SincronizacionBean extends BaseEjbJPA<Sincronizacion, Long> implements SincronizacionLocal {

	protected final Logger log = Logger.getLogger(getClass());

	public static final SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

	@EJB(mappedName = "dir3caib/ImportadorUnidadesEJB/local")
	private ImportadorUnidadesLocal importadorUnidades;

	@EJB(mappedName = "dir3caib/ImportadorOficinasEJB/local")
	private ImportadorOficinasLocal importadorOficinas;

	@EJB(mappedName = "dir3caib/ImportadorCatalogoEJB/local")
	private ImportadorCatalogoLocal importadorCatalogo;

	@EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
	protected CatNivelAdministracionLocal catNivelAdministracionEjb;

	@PersistenceContext(unitName = "dir3caib")
	private EntityManager em;

	@Override
	public Sincronizacion getReference(Long id) throws Exception {

		return em.getReference(Sincronizacion.class, id);
	}

	@Override
	public Sincronizacion findById(Long id) throws Exception {

		return em.find(Sincronizacion.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Sincronizacion ultimaSincronizacionByTipo(String tipo) throws Exception {

		Query query = em.createQuery(
				"select sincronizacion from Sincronizacion as sincronizacion where sincronizacion.tipo= :tipo order by sincronizacion.codigo desc");
		query.setParameter("tipo", tipo);
		List<Sincronizacion> sincronizacions = query.getResultList();
		if (!sincronizacions.isEmpty()) {
			return (Sincronizacion) query.getResultList().get(0);
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Sincronizacion ultimaSincronizacionCorrecta(String tipo) throws Exception {

		Query query = em.createQuery(
				"select sincronizacion from Sincronizacion as sincronizacion where sincronizacion.tipo = :tipo and sincronizacion.estado = :correcto "
						+ " order by sincronizacion.codigo desc");

		query.setParameter("tipo", tipo);
		query.setParameter("correcto", Dir3caibConstantes.SINCRONIZACION_CORRECTA);

		List<Sincronizacion> sincronizacions = query.getResultList();
		if (!sincronizacions.isEmpty()) {
			return (Sincronizacion) query.getResultList().get(0);
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Sincronizacion ultimaSincronizacionDirectorio() throws Exception {

		Query query = em.createQuery(
				"select sincronizacion from Sincronizacion as sincronizacion where (sincronizacion.tipo = :completa or sincronizacion.tipo = :actualizacion) "
						+ "and (sincronizacion.estado = :correcto or sincronizacion.estado = :vacia) "
						+ " order by sincronizacion.codigo desc");

		query.setParameter("actualizacion", Dir3caibConstantes.DIRECTORIO_ACTUALIZACION);
		query.setParameter("completa", Dir3caibConstantes.DIRECTORIO_COMPLETO);
		query.setParameter("correcto", Dir3caibConstantes.SINCRONIZACION_CORRECTA);
		query.setParameter("vacia", Dir3caibConstantes.SINCRONIZACION_VACIA);

		List<Sincronizacion> sincronizacions = query.getResultList();
		if (!sincronizacions.isEmpty()) {
			return (Sincronizacion) query.getResultList().get(0);
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Sincronizacion ultimaSincronizacionCatalogo() throws Exception {

		Query query = em.createQuery(
				"select sincronizacion from Sincronizacion as sincronizacion where sincronizacion.tipo = :tipo "
						+ "and (sincronizacion.estado = :correcto or sincronizacion.estado = :vacia) "
						+ " order by sincronizacion.codigo desc");

		query.setParameter("tipo", Dir3caibConstantes.CATALOGO);
		query.setParameter("correcto", Dir3caibConstantes.SINCRONIZACION_CORRECTA);
		query.setParameter("vacia", Dir3caibConstantes.SINCRONIZACION_VACIA);

		List<Sincronizacion> sincronizacions = query.getResultList();
		if (!sincronizacions.isEmpty()) {
			return (Sincronizacion) query.getResultList().get(0);
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void purgarSincronizaciones() throws Exception {

		Calendar hoy = Calendar.getInstance(); // obtiene la fecha de hoy
		hoy.add(Calendar.DATE, -30); // el -30 indica que se le restaran 10 dias

		Query q = em.createQuery(
				"select sincronizacion from Sincronizacion as sincronizacion where sincronizacion.fechaImportacion <= :fecha "
						+ "and sincronizacion.tipo = :tipo and (sincronizacion.estado = :correcto or sincronizacion.estado = :vacia) ");

		q.setParameter("correcto", Dir3caibConstantes.SINCRONIZACION_CORRECTA);
		q.setParameter("vacia", Dir3caibConstantes.SINCRONIZACION_VACIA);
		q.setParameter("tipo", Dir3caibConstantes.DIRECTORIO_ACTUALIZACION);
		q.setParameter("fecha", hoy.getTime());

		List<Sincronizacion> sincronizaciones = q.getResultList();

		for (Sincronizacion sincronizacion : sincronizaciones) {

			eliminarSincronizacion(sincronizacion);
		}
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Sincronizacion> getAll() throws Exception {

		return em
				.createQuery(
						"Select sincronizacion from Sincronizacion as sincronizacion order by sincronizacion.codigo")
				.getResultList();
	}

	@Override
	public Long getTotal() throws Exception {

		Query q = em.createQuery("Select count(sincronizacion.codigo) from Sincronizacion as sincronizacion");

		return (Long) q.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Sincronizacion> getPagination(int inicio) throws Exception {

		Query q = em.createQuery(
				"Select sincronizacion from Sincronizacion as sincronizacion order by sincronizacion.codigo desc");
		q.setFirstResult(inicio);
		q.setMaxResults(RESULTADOS_PAGINACION);

		return q.getResultList();
	}

	@Override
	public void deleteAllByTipo(String tipo) throws Exception {
		Query query = em.createQuery("delete from Sincronizacion as sincronizacion where sincronizacion.tipo=:tipo ");
		query.setParameter("tipo", tipo);
		query.executeUpdate();
	}

	@Override
	public void eliminarSincronizacionesDirectorio(Long idSincronizacion) throws Exception {

		Query q = em.createQuery("delete from Sincronizacion as s where s.tipo != :tipo and s.id != :idSincronizacion");
		q.setParameter("tipo", Dir3caibConstantes.CATALOGO);
		q.setParameter("idSincronizacion", idSincronizacion).executeUpdate();
	}

	@Override
	public void actualizarEstado(Long codigo, Long estado) throws Exception {

		Query query = em.createQuery("update Sincronizacion set estado = :estado where codigo = :codigo ");
		query.setParameter("codigo", codigo);
		query.setParameter("estado", estado);
		query.executeUpdate();
	}

	@Override
	@TransactionTimeout(value = 50000)
	public Sincronizacion descargaSincronizacionDirectorio(Date fechaInicio, Date fechaFin) throws Exception {

		log.info("----------------------------------------------------------------------------------------");
		log.info("Iniciamos la descarga incremental de las Unidades y Oficinas");

		// Nueva sincronización
		Sincronizacion sincronizacion = new Sincronizacion(Dir3caibConstantes.DIRECTORIO_ACTUALIZACION);
		sincronizacion.setFechaInicio(fechaInicio);
		sincronizacion.setFechaFin(new Date());

		// Guardamos la sincronizacion porque emplearemos el identificador para el
		// nombre del directorio y el archivo.
		sincronizacion = persist(sincronizacion);

		/*
		 * El funcionamiento de los ws de madrid no permiten que la fecha de inicio sea
		 * null si la fecha fin es distinta de null. Sincronizacion incremental: Hay dos
		 * opciones, incluir solo la fecha de inicio que devolverá la información que
		 * existe desde la fecha indicada hasta la fecha en la que se realiza la
		 * petición y la otra opción es incluir fecha de inicio y fecha fin. Esta
		 * devuelve la información añadida o modificada entre esas dos fechas.
		 */

		try {

			// Path general de la sincronización
			String sincronizacionPath = Configuracio.getSincronizacionPath(sincronizacion.getCodigo());

			// Path donde se descomprimiran los ficheros CSV descargados
			String directorioPath = sincronizacionPath + "directorio/";

			// Ficheros zip con la descarga de Unidades y Oficinas
			String unidadesZip = sincronizacionPath + Dir3caibConstantes.UNIDADES_ARCHIVO_ZIP
					+ sincronizacion.getCodigo() + ".zip";
			String oficinasZip = sincronizacionPath + Dir3caibConstantes.OFICINAS_ARCHIVO_ZIP
					+ sincronizacion.getCodigo() + ".zip";

			// Service Unidades
			SD01UNDescargaUnidades serviceUnidades = getServiceDescargaUnidades();

			// Establecemos parametros de serviceUnidades
			UnidadesWsVersion parametrosUnidades = new UnidadesWsVersion();
			parametrosUnidades.setUsuario(Configuracio.getDir3WsUser());
			parametrosUnidades.setClave(Configuracio.getDir3WsPassword());
			parametrosUnidades.setFormatoFichero(es.caib.dir3caib.ws.dir3.unidad.client.FormatoFichero.CSV);
			parametrosUnidades.setTipoConsulta(TipoConsultaUO.COMPLETO);

			// Service Oficinas
			SD02OFDescargaOficinas serviceOficinas = getServiceDescargarOficinas();

			// Establecemos parametros de serviceOficinas
			OficinasVersionWs parametrosOficinas = new OficinasVersionWs();
			parametrosOficinas.setUsuario(Configuracio.getDir3WsUser());
			parametrosOficinas.setClave(Configuracio.getDir3WsPassword());
			parametrosOficinas.setFormatoFichero(es.caib.dir3caib.ws.dir3.oficina.client.FormatoFichero.CSV);
			parametrosOficinas.setTipoConsulta(TipoConsultaOF.COMPLETO);

			// Establecemos parametros comunes
			if (fechaInicio != null) {
				parametrosUnidades.setFechaInicio(formatoFecha.format(fechaInicio));
				parametrosOficinas.setFechaInicio(formatoFecha.format(fechaInicio));
			}
			if (fechaFin != null) {
				parametrosUnidades.setFechaFin(formatoFecha.format(fechaFin));
				parametrosOficinas.setFechaFin(formatoFecha.format(fechaFin));
			}

			// Invocamos el WS de Unidades
			es.caib.dir3caib.ws.dir3.unidad.client.RespuestaWS respuestaUnidades = serviceUnidades
					.exportarV3(parametrosUnidades);
			log.info("Respuesta WS unidades DIR3: " + respuestaUnidades.getCodigo() + " - "
					+ respuestaUnidades.getDescripcion());

			String codigoUnidades = respuestaUnidades.getCodigo().trim();

			// Invocamos el WS de Oficinas
			es.caib.dir3caib.ws.dir3.oficina.client.RespuestaWS respuestaOficinas = serviceOficinas
					.exportarV4(parametrosOficinas);
			log.info("Respuesta WS oficinas DIR3: " + respuestaOficinas.getCodigo() + " - "
					+ respuestaOficinas.getDescripcion());

			String codigoOficinas = respuestaOficinas.getCodigo().trim();

			// Procesamos los Archivos zip recibidos
			if (codigoUnidades.equals(Dir3caibConstantes.CODIGO_VACIO)
					&& codigoOficinas.equals(Dir3caibConstantes.CODIGO_VACIO)) {

				// Actualizamos el estado de la Sincronizacion
				sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_VACIA);
				sincronizacion.setFechaImportacion(new Date());
				merge(sincronizacion);

			} else if (codigoUnidades.equals(Dir3caibConstantes.CODIGO_CORRECTO)
					|| codigoOficinas.equals(Dir3caibConstantes.CODIGO_CORRECTO)) {

				// Actualizamos el estado de la Sincronizacion
				sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_DESCARGADA);
				merge(sincronizacion);

				try {

					if (codigoUnidades.equals(Dir3caibConstantes.CODIGO_CORRECTO)) {
						descomprimirZip(unidadesZip, respuestaUnidades.getFichero(), directorioPath);
					}
					if (codigoOficinas.equals(Dir3caibConstantes.CODIGO_CORRECTO)) {
						descomprimirZip(oficinasZip, respuestaOficinas.getFichero(), directorioPath);
					}

				} catch (Exception e) {
					log.info(
							"Ha ocurrido un error descomprimiendo los archivos de las unidades/oficinas obtenidos del WS");
					// Borramos la sincronizacion creada previamente.
					eliminarSincronizacion(sincronizacion);
				}

			} else {
				// La sincronizacion ha ido mal, la eliminamos
				eliminarSincronizacion(sincronizacion);
				return null;
			}

			log.info("Fin de la descarga incremental de las Unidades y Oficinas");
			log.info("----------------------------------------------------------------------------------------");

			return sincronizacion;

		} catch (Exception e) { // si hay algun problema, eliminamos la sincronizacion
			eliminarSincronizacion(sincronizacion);
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	@TransactionTimeout(value = 50000)
	public Sincronizacion descargaCompletaDirectorio() throws Exception {

		log.info("Iniciamos la descarga completa de las Unidades y Oficinas");

		// Nueva sincronización
		Sincronizacion sincronizacion = new Sincronizacion(Dir3caibConstantes.DIRECTORIO_COMPLETO);
		sincronizacion.setFechaInicio(null);
		sincronizacion.setFechaFin(new Date());

		// Guardamos la sincronizacion porque emplearemos el identificador para el
		// nombre del directorio y el archivo.
		sincronizacion = persist(sincronizacion);

		/*
		 * El funcionamiento de los ws de madrid no permiten que la fecha de inicio sea
		 * null si la fecha fin es distinta de null. Sincronizacion incremental: Hay dos
		 * opciones, incluir solo la fecha de inicio que devolverá la información que
		 * existe desde la fecha indicada hasta la fecha en la que se realiza la
		 * petición y la otra opción es incluir fecha de inicio y fecha fin. Esta
		 * devuelve la información añadida o modificada entre esas dos fechas.
		 */

		try {

			// Path general de la sincronización
			String sincronizacionPath = Configuracio.getSincronizacionPath(sincronizacion.getCodigo());

			// Service Unidades
			SD01UNDescargaUnidades serviceUnidades = getServiceDescargaUnidades();

			// Establecemos parametros de serviceUnidades
			UnidadesWsVersion parametrosUnidades = new UnidadesWsVersion();
			parametrosUnidades.setUsuario(Configuracio.getDir3WsUser());
			parametrosUnidades.setClave(Configuracio.getDir3WsPassword());
			parametrosUnidades.setFormatoFichero(es.caib.dir3caib.ws.dir3.unidad.client.FormatoFichero.CSV);
			parametrosUnidades.setTipoConsulta(TipoConsultaUO.COMPLETO);

			// Service Oficinas
			SD02OFDescargaOficinas serviceOficinas = getServiceDescargarOficinas();

			// Establecemos parametros de serviceOficinas
			OficinasVersionWs parametrosOficinas = new OficinasVersionWs();
			parametrosOficinas.setUsuario(Configuracio.getDir3WsUser());
			parametrosOficinas.setClave(Configuracio.getDir3WsPassword());
			parametrosOficinas.setFormatoFichero(es.caib.dir3caib.ws.dir3.oficina.client.FormatoFichero.CSV);
			parametrosOficinas.setTipoConsulta(TipoConsultaOF.COMPLETO);

			// Obtenemos los niveles de administración vigentes
			List<CatNivelAdministracion> niveles = catNivelAdministracionEjb
					.getByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

			// Realizamos la descarga por niveles para reducir la carga de la peticióna los
			// WS
			for (CatNivelAdministracion nivel : niveles) {

				// Parámetro de Nivel para la descarga
				parametrosUnidades.setNivelAdministracion(nivel.getCodigoNivelAdministracion().intValue());
				parametrosOficinas.setNivelAdministracion(nivel.getCodigoNivelAdministracion().intValue());
				// parametrosOficinas.setEstados(estados); TODO hay un error en la definición
				// del API

				// Invocamos el WS de Unidades
				log.info("Peticion WS unidades DIR3 Nivel administracion: "
						+ nivel.getCodigoNivelAdministracion().intValue() + " - "
						+ nivel.getDescripcionNivelAdministracion());
				es.caib.dir3caib.ws.dir3.unidad.client.RespuestaWS respuestaUnidades = serviceUnidades
						.exportarV3(parametrosUnidades);
				log.info("Respuesta WS unidades DIR3 para nivel " + nivel.getDescripcionNivelAdministracion() + ": "
						+ respuestaUnidades.getCodigo() + " - " + respuestaUnidades.getDescripcion());

				String codigoUnidades = respuestaUnidades.getCodigo().trim();

				// Invocamos el WS de Oficinas
				log.info("Peticion WS oficinas DIR3 Nivel administracion: "
						+ nivel.getCodigoNivelAdministracion().intValue() + " - "
						+ nivel.getDescripcionNivelAdministracion());
				es.caib.dir3caib.ws.dir3.oficina.client.RespuestaWS respuestaOficinas = serviceOficinas
						.exportarV4(parametrosOficinas);
				log.info("Respuesta WS oficinas DIR3 para nivel " + nivel.getDescripcionNivelAdministracion() + ": "
						+ respuestaOficinas.getCodigo() + " - " + respuestaOficinas.getDescripcion());

				String codigoOficinas = respuestaOficinas.getCodigo().trim();

				// Ficheros zip con la descarga de Unidades y Oficinas
				String unidadesZip = sincronizacionPath + Dir3caibConstantes.UNIDADES_ARCHIVO_ZIP
						+ sincronizacion.getCodigo() + ".zip";
				String oficinasZip = sincronizacionPath + Dir3caibConstantes.OFICINAS_ARCHIVO_ZIP
						+ sincronizacion.getCodigo() + ".zip";

				// Directorio donde se descomprimiran los ficheros zip recibidos
				String directorioPath = sincronizacionPath + "directorio/nivel_" + nivel.getCodigoNivelAdministracion()
						+ "/";

				// Procesamos los Archivos zip recibidos
				if (codigoUnidades.equals(Dir3caibConstantes.CODIGO_VACIO)
						&& codigoOficinas.equals(Dir3caibConstantes.CODIGO_VACIO)) {

					// Actualizamos el estado de la Sincronizacion
					sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_VACIA);
					sincronizacion.setFechaImportacion(new Date());
					merge(sincronizacion);

				} else if (codigoUnidades.equals(Dir3caibConstantes.CODIGO_CORRECTO)
						|| codigoOficinas.equals(Dir3caibConstantes.CODIGO_CORRECTO)) {

					// Actualizamos el estado de la Sincronizacion
					sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_DESCARGADA);
					merge(sincronizacion);

					try {

						if (codigoUnidades.equals(Dir3caibConstantes.CODIGO_CORRECTO)) {
							descomprimirZip(unidadesZip, respuestaUnidades.getFichero(), directorioPath);
						}
						if (codigoOficinas.equals(Dir3caibConstantes.CODIGO_CORRECTO)) {
							descomprimirZip(oficinasZip, respuestaOficinas.getFichero(), directorioPath);
						}

					} catch (Exception e) {
						log.info(
								"Ha ocurrido un error descomprimiendo los archivos de las unidades/oficinas obtenidos del WS");
						// Borramos la sincronizacion creada previamente.
						eliminarSincronizacion(sincronizacion);
					}

				} else {
					// La sincronizacion ha ido mal, la eliminamos
					eliminarSincronizacion(sincronizacion);
					return null;
				}
			}

			log.info("Fin de la descarga completa de las Unidades y Oficinas");

			return sincronizacion;

		} catch (Exception e) { // si hay algun problema, eliminamos la sincronizacion
			eliminarSincronizacion(sincronizacion);
			throw new Exception(e.getMessage());
		}

	}

	@Override
	public Sincronizacion descargarCatalogoWS() throws Exception {

		log.info("Iniciamos la descarga del catalogo");

		Sincronizacion sincronizacion = new Sincronizacion(Dir3caibConstantes.CATALOGO);

		// Datos comunes para invocar el WS del Directorio Común
		String usuario = Configuracio.getDir3WsUser();
		String password = Configuracio.getDir3WsPassword();

		String codigoCatalogos = "";
		String ficheroCatalogos = "";

		// Directorios
		String sincronizacionPath = "";
		String catalogosZip = "";

		// Establecemos las fechas para la sincronizacion
		sincronizacion.setFechaInicio(new Date());
		sincronizacion.setFechaFin(new Date());

		// Guardamos la sincronizacion porque emplearemos el identificador para el
		// nombre del directorio y el archivo.
		sincronizacion = persist(sincronizacion);

		try {

			// Definimos el nombre del archivo zip a guardar y el directorio donde se
			// descomprimirá
			sincronizacionPath = Configuracio.getSincronizacionPath(sincronizacion.getCodigo());
			String catalogoPath = sincronizacionPath + "catalogos/";
			catalogosZip = sincronizacionPath + Dir3caibConstantes.CATALOGOS_ARCHIVO_ZIP + sincronizacion.getCodigo()
					+ ".zip";

			// Obtenemos el EndPoint del WS
			String endPointCatalogos = Configuracio.getCatalogoEndPoint();

			// Service
			SC21CTVolcadoCatalogos catalogoService = new SC21CTVolcadoCatalogosService(
					new URL(endPointCatalogos + "?wsdl")).getSC21CTVolcadoCatalogos();
			Map<String, Object> reqContextCatalogos = ((BindingProvider) catalogoService).getRequestContext();
			reqContextCatalogos.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointCatalogos);

			// Set timeout until a connection is established
			// reqContextCatalogos.put("javax.xml.ws.client.connectionTimeout", "50000");

			// Set timeout until the response is received
			// reqContextCatalogos.put("javax.xml.ws.client.receiveTimeout", "50000");

			// Invocamos al WS
			es.caib.dir3caib.ws.dir3.catalogo.client.RespuestaWS respuesta = catalogoService.exportarV2(usuario,
					password, "csv", "COMPLETO");

			log.info("Respuesta Ws catalogo: " + respuesta.getCodigo() + " - " + respuesta.getDescripcion());

			codigoCatalogos = respuesta.getCodigo().trim();
			ficheroCatalogos = respuesta.getFichero();

			// Procesamos los Archivoz zip recibidos
			if (codigoCatalogos.equals(Dir3caibConstantes.CODIGO_CORRECTO)) {

				// Actualizamos el estado de la Sincronizacion
				sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_DESCARGADA);
				merge(sincronizacion);

				try {

					descomprimirZip(catalogosZip, ficheroCatalogos, catalogoPath);

				} catch (Exception e) {
					log.info("Ha ocurrido un error descomprimiendo los archivos del catalogo obtenidos del WS");
					// Borramos la sincronizacion creada previamente.
					eliminarSincronizacion(sincronizacion);
				}

			} else if (codigoCatalogos.equals(Dir3caibConstantes.CODIGO_VACIO)) {

				// Actualizamos el estado de la Sincronizacion
				sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_VACIA);
				merge(sincronizacion);

			} else {
				// La descarga ha ido mal, lo informamos mediante el estado
				sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_ERROR_DESCARGA);
				merge(sincronizacion);
				MailUtils.envioEmailErrorSincronizacion(Dir3caibConstantes.SINCRONIZACION_CATALOGO, null);
				return sincronizacion;
			}

			log.info("Fin de la descarga del catalogo");

			return sincronizacion;

		} catch (Exception e) { // si hay algun problema, modificamos el estadod e la descarga
			log.info("Excepcion en la descarga del catalogo");
			e.printStackTrace();
			sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_ERROR_DESCARGA);
			merge(sincronizacion);
			MailUtils.envioEmailErrorSincronizacion(Dir3caibConstantes.SINCRONIZACION_CATALOGO, e);
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void eliminarSincronizacion(Sincronizacion sincronizacion) throws Exception {

		String sincronizacionPath = Configuracio.getSincronizacionPath(sincronizacion.getCodigo());

		try {
			// Eliminamos todas las descargas realizadas
			File directorio = new File(sincronizacionPath);
			FileUtils.cleanDirectory(directorio);
			FileUtils.deleteDirectory(directorio);
		} catch (Exception e) {
			log.info("El directorio no existe: " + sincronizacionPath);
			e.printStackTrace();
		}

		remove(sincronizacion);

	}

	/**
	 * @param sincronizacion
	 * @throws Exception
	 */
	@Override
	@TransactionTimeout(value = 40000)
	public Sincronizacion importarUnidadesOficinas(Sincronizacion sincronizacion) throws Exception {

		// Importamos las Unidades y Oficinas
		importadorUnidades.importarUnidades(sincronizacion);
		importadorOficinas.importarOficinas(sincronizacion);

		// Si el proceso ha sido correcto, actualizamos el estado
		sincronizacion.setFechaImportacion(new Date());
		sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_CORRECTA);

		return merge(sincronizacion);
	}

	/**
	 * @param sincronizacion
	 * @throws Exception
	 */
	@Override
	@TransactionTimeout(value = 3600)
	public Sincronizacion importarCatalogo(Sincronizacion sincronizacion, Boolean localidades) throws Exception {

		// Importamos el Catalogo
		importadorCatalogo.importarCatalogo(sincronizacion, localidades);

		// Si el proceso ha sido correcto, actualizamos el estado
		sincronizacion.setFechaImportacion(new Date());
		sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_CORRECTA);

		return merge(sincronizacion);
	}

	@Override
	@TransactionTimeout(value = 3600)
	public Sincronizacion sincronizarCatalogo() throws Exception {

		Sincronizacion sincroCatalogo = null;

		try {
			Sincronizacion ultimaSincro = ultimaSincronizacionCatalogo();

			// Descarga del catálogo DIR3
			sincroCatalogo = descargarCatalogoWS();

			// Importamos Catálogo
			if (sincroCatalogo != null
					&& sincroCatalogo.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {

				if (ultimaSincro != null) { // Si ya hay una sincro previa, no sincronizacimos las Localidades
					sincroCatalogo = importarCatalogo(sincroCatalogo, false);
				} else {
					sincroCatalogo = importarCatalogo(sincroCatalogo, true);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			// Si ha habido un Error en la sincronización, modificamos el estado de la
			// descarga
			if (sincroCatalogo != null
					&& sincroCatalogo.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {
				try {
					actualizarEstado(sincroCatalogo.getCodigo(), Dir3caibConstantes.SINCRONIZACION_ERRONEA);
				} catch (Exception ex2) {
					MailUtils.envioEmailErrorSincronizacion(Dir3caibConstantes.SINCRONIZACION_CATALOGO, ex2);
					ex2.printStackTrace();
				}
			}
			MailUtils.envioEmailErrorSincronizacion(Dir3caibConstantes.SINCRONIZACION_CATALOGO, e);
			throw e;
		}

		return sincroCatalogo;
	}

	@Override
	@TransactionTimeout(value = 50000)
	public List<Sincronizacion> sincronizarDirectorio() throws Exception {

		Sincronizacion sincroCatalogo = sincronizarCatalogo();
		Sincronizacion sincroDirectorio = sincronizarUnidadesOficinas();

		return Arrays.asList(sincroCatalogo, sincroDirectorio);

	}

	@Override
	@TransactionTimeout(value = 50000)
	public Sincronizacion sincronizarUnidadesOficinas() throws Exception {

		Sincronizacion sincroUnidadesOficinas = null;

		try {

			// Obtenemos la fecha de la ultima descarga/sincronizacion
			Sincronizacion ultimaSincro = ultimaSincronizacionDirectorio();

			// Descarga de directorio DIR3
			if (ultimaSincro != null) {
				sincroUnidadesOficinas = descargaSincronizacionDirectorio(ultimaSincro.getFechaFin(), new Date());
			} else {// Es una descarga inicial
				sincroUnidadesOficinas = descargaCompletaDirectorio();
			}

			// Importamos Unidades y Oficinas
			if (sincroUnidadesOficinas != null
					&& sincroUnidadesOficinas.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {
				sincroUnidadesOficinas = importarUnidadesOficinas(sincroUnidadesOficinas);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// Si ha habido un Error en la sincronización, modificamos el estado de la
			// descarga
			if (sincroUnidadesOficinas != null
					&& sincroUnidadesOficinas.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {
				try {
					actualizarEstado(sincroUnidadesOficinas.getCodigo(), Dir3caibConstantes.SINCRONIZACION_ERRONEA);

				} catch (Exception ex1) {
					MailUtils.envioEmailErrorSincronizacion(Dir3caibConstantes.SINCRONIZACION_DIRECTORIO, ex1);
					ex1.printStackTrace();
				}
			}
			MailUtils.envioEmailErrorSincronizacion(Dir3caibConstantes.SINCRONIZACION_DIRECTORIO, e);
			throw e;
		}

		return sincroUnidadesOficinas;
	}

	/**
	 * @return
	 * @throws MalformedURLException
	 */
	private SD01UNDescargaUnidades getServiceDescargaUnidades() throws MalformedURLException {

		// Obtenemos el EndPoint del WS
		String endPointUnidades = Configuracio.getUnidadEndPoint();

		SD01UNDescargaUnidades serviceUnidades = new SD01UNDescargaUnidadesService(new URL(endPointUnidades + "?wsdl"))
				.getSD01UNDescargaUnidades();
		Map<String, Object> reqContextUnidades = ((BindingProvider) serviceUnidades).getRequestContext();
		reqContextUnidades.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointUnidades);

		// Set timeout until a connection is established
		reqContextUnidades.put("javax.xml.ws.client.connectionTimeout", "600000");

		// Set timeout until the response is received
		reqContextUnidades.put("javax.xml.ws.client.receiveTimeout", "600000");

		return serviceUnidades;

	}

	/**
	 * @return
	 */
	private SD02OFDescargaOficinas getServiceDescargarOficinas() throws MalformedURLException {

		// Obtenemos el EndPoint del WS
		String endPointOficinas = Configuracio.getOficinaEndPoint();

		SD02OFDescargaOficinas serviceOficinas = new SD02OFDescargaOficinasService(new URL(endPointOficinas + "?wsdl"))
				.getSD02OFDescargaOficinas();
		Map<String, Object> reqContextOficinas = ((BindingProvider) serviceOficinas).getRequestContext();
		reqContextOficinas.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointOficinas);

		// Set timeout until a connection is established
		reqContextOficinas.put("javax.xml.ws.client.connectionTimeout", "600000");

		// Set timeout until the response is received
		reqContextOficinas.put("javax.xml.ws.client.receiveTimeout", "600000");

		return serviceOficinas;
	}

	/**
	 * Crea y descomprime el fichero recibido desde los WS de DIR3
	 *
	 * @param nombreZip
	 * @param ficheroRespuesta
	 * @param directorio
	 * @throws Exception
	 */
	private void descomprimirZip(String nombreZip, String ficheroRespuesta, String directorio) throws Exception {

		byte[] buffer = new byte[1024];
		Base64 decoder = new Base64();

		// Guardamos el archivo sincronizaciondo en un zip en la ruta indicada
		File file = new File(nombreZip);
		FileUtils.writeByteArrayToFile(file, decoder.decode(ficheroRespuesta));

		// Se crea un directorio donde descomprimiremos el zip
		File dir = new File(directorio);
		if (!dir.exists()) { // Si no existe el directorio
			if (!dir.mkdirs()) {
				log.error(" No se ha podido crear el directorio");
				throw new Exception("No se ha podido crear el directorio");
			}
		}

		// Descomprimimos el archivo en el directorio creado anteriormente
		ZipInputStream zis = new ZipInputStream(new FileInputStream(nombreZip));
		ZipEntry zipEntry = zis.getNextEntry();

		while (zipEntry != null) {
			String fileName = zipEntry.getName();
			File newFile = new File(directorio + fileName);

			log.info("Fichero descomprimido: " + newFile.getAbsoluteFile());

			// create all non exists folders
			// else you will hit FileNotFoundException for compressed folder
			new File(newFile.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(newFile);

			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Long contarSincronizaciones(String tipo) throws Exception {

		Query query = em.createQuery(
				"select count(sincronizacion.codigo) from Sincronizacion as sincronizacion where sincronizacion.tipo = :tipo");

		query.setParameter("tipo", tipo);

		return (Long) query.getSingleResult();
	}

}
