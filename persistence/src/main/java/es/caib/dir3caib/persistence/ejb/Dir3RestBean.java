package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.json.OficinaRest;
import es.caib.dir3caib.persistence.model.json.UnidadRest;
import es.caib.dir3caib.persistence.model.json.ContactoRest;
import es.caib.dir3caib.persistence.model.json.UnidadRestSistra;
import es.caib.dir3caib.persistence.model.ws.v2.UnidadWs;
import es.caib.dir3caib.persistence.utils.CodigoValor;
import es.caib.dir3caib.persistence.utils.DataBaseUtils;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.NodoUtils;
import es.caib.dir3caib.persistence.utils.ObjetoDirectorio;
import es.caib.dir3caib.persistence.utils.ObjetoDirectorioExtendido;
import es.caib.dir3caib.utils.Utils;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created 1/04/14 9:50
 *
 * @author mgonzalez Clase que implementa la funcionalidad de varios servicios
 *         rest que pueden ser llamados desde otras aplicaciones.
 */
@Stateless(name = "Dir3RestEJB")
@RunAs(Dir3caibConstantes.DIR_WS)
public class Dir3RestBean implements Dir3RestLocal {

	protected final Logger log = Logger.getLogger(getClass());
	protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

	@PersistenceContext(unitName = "dir3caib")
	private EntityManager em;

	@EJB(mappedName = "dir3caib/ObtenerOficinasEJB/local")
	private ObtenerOficinasLocal obtenerOficinasEjb;

	@EJB(mappedName = "dir3caib/ContactoUOEJB/local")
	private ContactoUOLocal contactoUOEjb;

	@EJB(mappedName = "dir3caib/OficinaEJB/local")
	private OficinaLocal oficinaEjb;

	@EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
	private RelacionOrganizativaOfiLocal relacionOrganizativaOfiEjb;

	@EJB(mappedName = "dir3caib/UnidadEJB/local")
	private UnidadLocal unidadEjb;

	@EJB(mappedName = "dir3caib/SincronizacionEJB/local")
	private SincronizacionLocal sincronizacionEjb;

	@EJB(mappedName = "dir3caib/ObtenerUnidadesEJB/local")
	private ObtenerUnidadesLocal obtenerUnidadesEjb;

	@EJB(mappedName = "dir3caib/CatServicioEJB/local")
	private CatServicioLocal servicioEjb;

	@EJB(mappedName = "dir3caib/ServicioOfiEJB/local")
	private ServicioOfiLocal servicioOfiEjb;

	/**
	 * Obtiene las unidades(codigo-denominacion) cuya denominación coincide con la
	 * indicada.
	 *
	 * @param denominacion
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<ObjetoDirectorio> findUnidadesByDenominacion(String denominacion) throws Exception {
		return findUnidadesByDenominacion(denominacion, false, null);
	}

	/**
	 * Obtiene las unidades(codigo-denominacion) cuya denominación coincide con la
	 * indicada. Si denominacionCooficial es true, devuelve denominacion cooficial
	 * si no es nul o en su defecto el campo denominacion
	 *
	 * @param denominacion
	 * @param denominacionCooficial
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<ObjetoDirectorio> findUnidadesByDenominacion(String denominacion, boolean denominacionCooficial,
			String estado) throws Exception {

		if (!denominacion.isEmpty()) {
			Query q = em
					.createQuery("select distinct unidad.codigoDir3, unidad.denominacion, unidad.denomLenguaCooficial "
							+ "from Unidad as unidad " + "where ( upper(unidad.denominacion) like upper(:denominacion) "
							+ "	or upper(unidad.denomLenguaCooficial) like upper(:denominacion))"
							+ "and unidad.estado.codigoEstadoEntidad = :estado");
			q.setParameter("denominacion", "%" + denominacion.toLowerCase() + "%");
			q.setParameter("estado", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
			return transformarAObjetoDirectorio(q.getResultList(), denominacionCooficial);
		} else {
			return new ArrayList<ObjetoDirectorio>();
		}
	}

	/**
	 * Obtiene las oficinas(codigo-denominacion) cuya denominación coincide con la
	 * indicada.
	 *
	 * @param denominacion
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<ObjetoDirectorio> findOficinasByDenominacion(String denominacion) throws Exception {
		return findOficinasByDenominacion(denominacion, false, null);
	}

	/**
	 * Obtiene las oficinas(codigo-denominacion) cuya denominación coincide con la
	 * indicada. Si denominacionCooficial es true, devuelve la denominacionCooficial
	 * si no es nul, en su defecto, el campo denominacion
	 *
	 * @param denominacion
	 * @param denominacionCooficial
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<ObjetoDirectorio> findOficinasByDenominacion(String denominacion, boolean denominacionCooficial,
			String estado) throws Exception {
		if (!denominacion.isEmpty()) {

			Query q = em
					.createQuery("select distinct oficina.codigo, oficina.denominacion, oficina.denomLenguaCooficial "
							+ "from Oficina as oficina "
							+ "where (upper(oficina.denominacion) like upper(:denominacion)"
							+ "or upper(oficina.denomLenguaCooficial) like upper(:denominacion))"
							+ "and oficina.estado.codigoEstadoEntidad = :estado");

			q.setParameter("denominacion", "%" + denominacion.toLowerCase() + "%");
			q.setParameter("estado", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
			return transformarAObjetoDirectorio(q.getResultList(), denominacionCooficial);

		} else {
			return new ArrayList<ObjetoDirectorio>();
		}
	}

	/**
	 * Método que comprueba si una unidad tiene más unidades hijas
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public Boolean tieneHijos(String codigo) throws Exception {
		Query q = em.createQuery(
				"Select unidad.codigo from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo order by unidad.codigo");
		q.setParameter("codigo", codigo);

		List<Unidad> hijos = q.getResultList();
		return hijos.size() > 0;
	}

	/**
	 * Obtiene el arbol de unidades de la unidad indicada por código.
	 *
	 * @param codigo
	 * @param fechaActualizacion
	 * @return
	 * @throws Exception
	 */
	// TODO REVISAR PARECE QUE NO SE EMPLEA, en REGWEB3 NO SE EMPLEA(03/10/2017)
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> obtenerArbolUnidades(String codigo, String fechaActualizacion, String estado) throws Exception {
		Query q;
		if (fechaActualizacion == null) { // Es una sincronizacion, solo traemos vigentes
			q = em.createQuery("Select unidad " + "from Unidad as unidad "
					+ "where unidad.codUnidadSuperior.codigoDir3 =:codigo " + " and unidad.codigoDir3 !=:codigo "
					+ " and unidad.estado.codigoEstadoEntidad =:vigente " + " order by unidad.codigoDir3");
			q.setParameter("codigo", codigo);
			q.setParameter("vigente", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
		} else {// es una actualizacion, lo traemos todo
			q = em.createQuery(
					"Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigoDir3 =:codigo and unidad.codigoDir3 !=:codigo  order by unidad.codigo");
			q.setParameter("codigo", codigo);

		}

		List<Unidad> padres = q.getResultList();
		List<Unidad> padresActualizados = new ArrayList<Unidad>();
		List<Unidad> listaCompleta;

		// log.info("Número de PADRES: " + padres.size());

		if (fechaActualizacion != null) { // Si hay fecha de actualizacion solo se envian las actualizadas
			Date fechaAct = formatoFecha.parse(fechaActualizacion);
			for (Unidad unidad : padres) {
				if (fechaAct.before(unidad.getFechaImportacion())) {
					padresActualizados.add(unidad);
				}
			}
			listaCompleta = new ArrayList<Unidad>(padresActualizados);
		} else { // si no hay fecha, se trata de una sincronización
			listaCompleta = new ArrayList<Unidad>(padres);
		}

		for (Unidad unidad : padres) {
			if (tieneHijos(unidad.getCodigo())) {
				List<Unidad> hijos = obtenerArbolUnidades(unidad.getCodigo(), fechaActualizacion, estado);
				listaCompleta.addAll(hijos);
			}
		}

		return listaCompleta;
	}

	/**
	 * Función que obtiene los hijos vigentes de una Unidad pero como Nodo ya que
	 * solo interesa el código, denominación y estado .
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	// TODO REVISAR PARECE QUE NO SE EMPLEA NI EN EL RestController, en REGWEB3 NO
	// SE EMPLEA(03/10/2017)
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> obtenerArbolUnidades(String codigo) throws Exception {
		return obtenerArbolUnidades(codigo, false, null);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> obtenerArbolUnidades(String codigo, boolean denominacionCooficial, String estado)
			throws Exception {
		Query q;

		q = em.createQuery(
				"Select unidad.codigo, unidad.denominacion, unidad.estado.descripcionEstadoEntidad, unidad.denomLenguaCooficial "
						+ "from Unidad as unidad " + "where unidad.codUnidadSuperior.codigo =:codigo "
						+ "and unidad.codigo !=:codigo " + "and unidad.estado.codigoEstadoEntidad =:vigente "
						+ "order by unidad.codigo");
		q.setParameter("codigo", codigo);
		q.setParameter("vigente", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		List<Nodo> padres = NodoUtils.getNodoList(q.getResultList(), denominacionCooficial);
		List<Nodo> listaCompleta;

		listaCompleta = new ArrayList<Nodo>(padres);

		for (Nodo unidad : padres) {
			if (tieneHijos(unidad.getCodigo())) {
				List<Nodo> hijos = obtenerArbolUnidades(unidad.getCodigo(), denominacionCooficial, estado);
				listaCompleta.addAll(hijos);
			}
		}

		return listaCompleta;
	}

	/*
	 * Método que devuelve las oficinas de un organismo, teniendo en cuenta la fecha
	 * de la ultima actualización de regweb. Se emplea para la sincronizacion y
	 * actualización con regweb
	 */

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Oficina> obtenerOficinasOrganismo(String codigo, String fechaActualizacion, String estado)
			throws Exception {

		Query q = em.createQuery(
				"Select oficina from Oficina as oficina where oficina.codUoResponsable.codigoDir3 =:codigo "
						+ "  and oficina.codUoResponsable.estado.codigoEstadoEntidad= :vigente"
						+ "  and oficina.estado.codigoEstadoEntidad= :estado order by oficina.codigo");

		q.setParameter("codigo", codigo);
		q.setParameter("vigente", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
		q.setParameter("estado", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		List<Oficina> oficinas = q.getResultList();
		List<Oficina> oficinasCompletas;
		List<Oficina> oficinasActualizadas = new ArrayList<Oficina>();
		// Si hay fecha de actualización y es anterior a la fecha de importación se debe
		// incluir en la lista de actualizadas
		for (Oficina ofi : oficinas) {
			Hibernate.initialize(ofi.getContactos());
		}
		if (fechaActualizacion != null) {
			Date fechaAct = formatoFecha.parse(fechaActualizacion);
			for (Oficina oficina : oficinas) {
				if (fechaAct.before(oficina.getFechaImportacion())) {
					oficinasActualizadas.add(oficina);
				}
			}
			oficinasCompletas = new ArrayList<Oficina>(oficinasActualizadas);
		} else { // Si no hay fecha de actualización se trata de una sincronización
			oficinasCompletas = new ArrayList<Oficina>(oficinas);
		}

		return oficinasCompletas;

	}

	/**
	 * Obtiene todas las {@link es.caib.dir3caib.persistence.model.Oficina} cuyo
	 * organismo responsable es el indicado por código(son todas padres e
	 * hijas).Solo se envian aquellas que han sido actualizadas controlando que la
	 * unidad del código que nos pasan se haya podido actualizar también. Esto es
	 * debido a que cuando en Madrid actualizan una unidad la tendencia es
	 * extinguirla y crear una nueva con código diferente. Esto hace que se tengan
	 * que traer las oficinas de la vieja y de la nueva.
	 *
	 * @param codigo Código del organismo
	 */
	@Override
	public List<Oficina> obtenerArbolOficinasOpenData(String codigo, String estado) throws Exception {
		return obtenerOficinasEjb.obtenerArbolOficinasOpenData(codigo, estado);
	}

	/**
	 * Obtiene las oficinas de registro de baleares
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Oficina> getOficinasBalearesOpenData(String estado) throws Exception {

		Query q = em.createQuery("Select oficina from Oficina as oficina "
				+ "where oficina.codComunidad.codigoComunidad =:comunidad and oficina.estado.codigoEstadoEntidad=:vigente "
				+ "and oficina.tipoOficina.codigoJerarquiaOficina=:tipoOficina order by oficina.localidad");

		q.setParameter("comunidad", 4L); // BALEARES
		q.setParameter("tipoOficina", 1L); // GENERAL
		q.setParameter("vigente", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE); // GENERAL

		List<Oficina> oficinas = q.getResultList();
		for (Oficina oficina : oficinas) {
			Hibernate.initialize(oficina.getContactos());
			Hibernate.initialize(oficina.getServicios());
		}

		return oficinas;
	}

	/**
	 * Método que nos dice sin una unidad tiene oficinas donde registrar. Solo mira
	 * relacion funcional y organizativa. Pendiente SIR y Oficinas Virtuales.
	 *
	 * @param codigo de la unidad que queremos consultar
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public Boolean tieneOficinasOrganismo(String codigo) throws Exception {

		Query q = em.createQuery(
				"Select oficina from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad=:vigente order by oficina.codigo");

		q.setParameter("codigo", codigo);
		q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		List<Oficina> oficinas = q.getResultList();
		if (oficinas.size() > 0) {
			return true;
		} else {
			q = em.createQuery(
					"Select relorg from RelacionOrganizativaOfi as relorg where relorg.unidad.codigo=:codigo and relorg.estado.codigoEstadoEntidad=:vigente order by relorg.id ");

			q.setParameter("codigo", codigo);
			q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
			List<RelacionOrganizativaOfi> relorg = q.getResultList();
			return relorg.size() > 0;
		}

	}

	/**
	 * TODO REVISAR, parece que el parametro "conOficinas" siempre se indica a
	 * "false" (desde regweb si se envia a false) Búsqueda de organismos según los
	 * parámetros indicados que esten vigentes
	 *
	 * @param codigo                    código de la unidad
	 * @param denominacion              denominación de la unidad
	 * @param codigoNivelAdministracion nivel de administración de la unidad
	 * @param codComunidad              comunidad autónoma a la que pertenece.
	 * @param conOficinas               indica el conOficinas de la búsqueda desde
	 *                                  regweb "OrganismoInteresado" ,
	 *                                  "OrganismoDestinatario"
	 * @return List<Nodo> conjunto de datos a mostrar del organismo coincide con los
	 *         parámetros de búsqueda
	 * @throws Exception
	 */

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion,
			Long codComunidad, boolean conOficinas, boolean unidadRaiz, Long provincia, String localidad,
			boolean vigentes) throws Exception {
		return busquedaOrganismos(codigo, denominacion, codigoNivelAdministracion, codComunidad, conOficinas,
				unidadRaiz, provincia, localidad, vigentes, false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<UnidadRestSistra> busquedaOrganismosSistra(String codigo, String denominacion, Long codigoNivelAdministracion,
			Long nivelJerarquico, Long codComunidad, boolean unidadRaiz, Long provincia, boolean vigentes) throws Exception {

		Query q;
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<String> where = new ArrayList<String>();

		// Los left outer joins son para las FK ( hay que poner todos los que se usan en
		// el select)
		StringBuilder query = new StringBuilder(
				"Select distinct(unidad.codigo), unidad.denominacion, unidad.estado.codigoEstadoEntidad, unidad.codUnidadRaiz.codigo, unidad.codUnidadRaiz.denominacion, unidad.codUnidadSuperior.codigo, "
						+ " unidad.codUnidadSuperior.denominacion, unidad.codLocalidad.descripcionLocalidad, unidad.esEdp, unidad.nivelJerarquico, unidad.nifcif, unidad.nivelAdministracion.descripcionNivelAdministracion, unidad.codTipoUnidad.descripcionTipoUnidadOrganica, "
						+ " unidad.tipoVia.descripcionTipoVia, unidad.nombreVia, unidad.numVia, unidad.complemento, unidad.codPostal, unidad.codAmbitoTerritorial.descripcionAmbito, unidad.codAmbPais.descripcionPais, unidad.codAmbComunidad.descripcionComunidad, "
						+ " unidad.codAmbProvincia.descripcionProvincia, unidad.codAmbIsla.descripcionIsla, unidad.denomLenguaCooficial, unidad.codUnidadRaiz.denomLenguaCooficial, unidad.codUnidadSuperior.denomLenguaCooficial, "
						+ " unidad.codigoDir3, unidad.codUnidadRaiz.codigoDir3, unidad.codUnidadSuperior.codigoDir3, unidad.version, unidad.nivelAdministracion.codigoNivelAdministracion, unidad.codAmbitoTerritorial.codigoAmbito, unidad.codAmbPais.codigoPais, "
						+ " unidad.codAmbProvincia.codigoProvincia, unidad.tipoVia.codigoTipoVia, unidad.estado.descripcionEstadoEntidad, unidad.codLocalidad.codigoLocalidad, unidad.codAmbComunidad.codigoComunidad, unidad.competencias "
						+ " from Unidad  as unidad left outer join unidad.codLocalidad as uniLocalidad "
						+ "                        left outer join unidad.catLocalidad as catLocalidad"
						+ "                        left outer join unidad.codTipoUnidad as tipoUnidad "
						+ "                        left outer join unidad.codAmbProvincia as provincia  "
						+ "                        left outer join unidad.codAmbIsla as isla "
						+ "                        left outer join unidad.codAmbitoTerritorial as ambTerritorial "
						+ "                        left outer join unidad.nivelAdministracion as nivelAdministracion "
						+ "                        left outer join unidad.codTipoEntPublica as codTipoEntPublica "
						+ "                        left outer join unidad.codAmbEntGeografica as codAmbEntGeografica "
						+ "                        left outer join unidad.codAmbPais as pais "
						+ "                        left outer join unidad.tipoVia as tipoVia "
						+ "                        left outer join unidad.codAmbComunidad as comunidad "
						+ "                        left outer join unidad.codComunidad as codcomunidad ");

		// Parametros de busqueda
		if (codigo != null && codigo.length() > 0) {
			where.add(DataBaseUtils.like("unidad.codigoDir3", "codigo", parametros, codigo));
		}
		if (denominacion != null && denominacion.length() > 0) {
			String condicion1 = DataBaseUtils.like("unidad.denominacion", "denominacion", parametros, denominacion);
			String condicion2 = DataBaseUtils.like("unidad.denomLenguaCooficial", "denomcooficial", parametros,
					denominacion);
			where.add(" ((" + condicion1 + ") or (" + condicion2 + ")) ");
		}
		if (codigoNivelAdministracion != null && codigoNivelAdministracion != -1) {
			where.add(" unidad.nivelAdministracion.codigoNivelAdministracion = :codigoNivelAdministracion ");
			parametros.put("codigoNivelAdministracion", codigoNivelAdministracion);
		}
		if (nivelJerarquico != null && nivelJerarquico != -1) {
			where.add(" unidad.nivelJerarquico = :nivelJerarquico ");
			parametros.put("nivelJerarquico", nivelJerarquico);
		}

		if (codComunidad != null && codComunidad != -1) {
			// al nivel administración Universidades no les aplica el ámbito territorial,
			// por tanto hay que considerar la comunidad de los datos de contacto
			if (Dir3caibConstantes.NIVEL_ADMINISTRACION_UNIVERSIDADES.equals(codigoNivelAdministracion)) {
				where.add(
						" (unidad.codAmbComunidad.codigoComunidad = :codComunidad or unidad.codComunidad.codigoComunidad = :comunidad) ");
				parametros.put("codComunidad", codComunidad);
				parametros.put("comunidad", codComunidad);

			} else {
				where.add(" unidad.codAmbComunidad.codigoComunidad = :codComunidad ");
				parametros.put("codComunidad", codComunidad);
			}

		}

		if (provincia != null && provincia != -1) {
			where.add(" unidad.codAmbProvincia.codigoProvincia = :codProvincia ");
			parametros.put("codProvincia", provincia);
		}

		// Solo se buscaran vigentes cuando lo indiquen
		if (vigentes) {
			where.add(" unidad.estado.codigoEstadoEntidad =:vigente ");
			parametros.put("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
		}
		if (unidadRaiz) {
			where.add(" unidad.codUnidadRaiz.codigo = unidad.codigo ");
		}

		// Añadimos los parametros a la query
		if (parametros.size() != 0) {
			query.append("where ");
			int count = 0;
			for (String w : where) {
				if (count != 0) {
					query.append(" and ");
				}
				query.append(w);
				count++;
			}
			query.append("order by unidad.estado.codigoEstadoEntidad desc, unidad.denominacion asc ");
			q = em.createQuery(query.toString());
			// log.info("Query => " + query.toString());

			for (Map.Entry<String, Object> param : parametros.entrySet()) {
				q.setParameter(param.getKey(), param.getValue());
				// log.info("Parameter " + param.getKey() + " => " + param.getValue());
			}

		} else {
			query.append(" order by unidad.estado.codigoEstadoEntidad desc, unidad.denominacion asc ");
			q = em.createQuery(query.toString());
		}

		List<Object[]> resultados = q.getResultList();
		List<UnidadRestSistra> lista = new ArrayList<UnidadRestSistra>(resultados.size());

		for (Object[] object : resultados) {

			String[] obj = new String[object.length];
			for (int i = 0; i < object.length; i++) {
				obj[i] = String.valueOf(object[i]);
			}

			UnidadRestSistra unidadRest = new UnidadRestSistra();
			unidadRest.setCodigoDir3(Utils.isNotEmpty(obj[26]) ? obj[26] : "");
			unidadRest.setVersion(Utils.isNotEmpty(obj[29]) ? Long.parseLong(obj[29]) : 0);
			unidadRest.setDenominacion(Utils.isNotEmpty(obj[1]) ? obj[1] : "");
			unidadRest.setDenominacionCooficial(Utils.isNotEmpty(obj[23]) ? obj[23] : "");
			unidadRest.setEstado(Utils.isNotEmpty(obj[2]) ? obj[2] : "");
			unidadRest.setNivelJerarquico(Utils.isNotEmpty(obj[9]) ? Long.parseLong(obj[9]) : 0);
			unidadRest.setNivelAdministracion(Utils.isNotEmpty(obj[30]) ? Long.parseLong(obj[30]) : 0);
			unidadRest.setNumVia(Utils.isNotEmpty(obj[15]) ? obj[15] : "");
			unidadRest.setNombreVia(Utils.isNotEmpty(obj[14]) ? obj[14] : "");
			unidadRest.setDescripcionLocalidad(Utils.isNotEmpty(obj[7]) ? obj[7] : "");
			
			String codigoDir3 = Utils.isNotEmpty(obj[0]) ? obj[0] : "";
			if (codigoDir3 != "") {
				
				List<ContactoUnidadOrganica> contactos = contactoUOEjb.getContactosByUnidad(codigoDir3);
				
				Map<String, String> contactosMap = new HashMap<String, String>();
				for (ContactoUnidadOrganica cont : contactos) {
					final String key = cont.getTipoContacto().getCodigoTipoContacto();
					if (!contactosMap.containsKey(key)) {
						contactosMap.put(key, cont.getValorContacto());
					} else {
						final String valor = contactosMap.get(key) + "," + cont.getValorContacto();
						contactosMap.put(key, valor);
					}
				}
				
				for (String key : contactosMap.keySet()) {
					switch (key) {
						case "C":
							unidadRest.setContactoCentralita(contactosMap.get(key));
							break;
						case "E":
							unidadRest.setContactoEmail(contactosMap.get(key));
							break;
						case "F":
							unidadRest.setContactoFax(contactosMap.get(key));
							break;
						case "P":
							unidadRest.setContactoCitaPrevia(contactosMap.get(key));
							break;
						case "T":
							unidadRest.setContactoTelefono(contactosMap.get(key));
							break;
						case "U":
							unidadRest.setContactoURL(contactosMap.get(key));
							break;
						default:
							unidadRest.setContactoOtro(contactosMap.get(key));
							break;
					}
				}
				
			}
			lista.add(unidadRest);
		}
		
		return lista;
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion,
			Long codComunidad, boolean conOficinas, boolean unidadRaiz, Long provincia, String localidad,
			boolean vigentes, boolean denominacionCooficial) throws Exception {
		Query q;
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<String> where = new ArrayList<String>();

		// Los left outer joins son para las FK ( hay que poner todos los que se usan en
		// el select)
		StringBuilder query = new StringBuilder(
				"Select distinct(unidad.codigo), unidad.denominacion, unidad.estado.codigoEstadoEntidad, unidad.codUnidadRaiz.codigo, unidad.codUnidadRaiz.denominacion, unidad.codUnidadSuperior.codigo, "
						+ " unidad.codUnidadSuperior.denominacion, unidad.codLocalidad.descripcionLocalidad, unidad.esEdp, unidad.nivelJerarquico, unidad.nifcif, unidad.nivelAdministracion.descripcionNivelAdministracion, unidad.codTipoUnidad.descripcionTipoUnidadOrganica, "
						+ " unidad.tipoVia.descripcionTipoVia, unidad.nombreVia, unidad.numVia, unidad.complemento, unidad.codPostal, unidad.codAmbitoTerritorial.descripcionAmbito, unidad.codAmbPais.descripcionPais, unidad.codAmbComunidad.descripcionComunidad, "
						+ " unidad.codAmbProvincia.descripcionProvincia, unidad.codAmbIsla.descripcionIsla, "
						+ " unidad.denomLenguaCooficial, unidad.codUnidadRaiz.denomLenguaCooficial, unidad.codUnidadSuperior.denomLenguaCooficial, "
						+ " unidad.codigoDir3, unidad.codUnidadRaiz.codigoDir3, unidad.codUnidadSuperior.codigoDir3  "
						+ " from Unidad  as unidad left outer join unidad.codLocalidad as uniLocalidad "
						+ "                        left outer join unidad.catLocalidad as catLocalidad"
						+ "                        left outer join unidad.codTipoUnidad as tipoUnidad"
						+ "                        left outer join unidad.codAmbProvincia as provincia  "
						+ "                        left outer join unidad.codAmbIsla as isla "
						+ "                        left outer join unidad.codAmbitoTerritorial as ambTerritorial "
						+ "                        left outer join unidad.nivelAdministracion as nivelAdministracion "
						+ "                        left outer join unidad.codTipoEntPublica as codTipoEntPublica "
						+ "                        left outer join unidad.codAmbEntGeografica as codAmbEntGeografica "
						+ "                        left outer join unidad.codAmbPais as pais "
						+ "                        left outer join unidad.tipoVia as tipoVia "
						+ "                        left outer join unidad.codAmbComunidad as  comunidad "
						+ "                        left outer join unidad.codComunidad as  codcomunidad ");

		// Parametros de busqueda
		if (codigo != null && codigo.length() > 0) {
			where.add(DataBaseUtils.like("unidad.codigoDir3", "codigo", parametros, codigo));
		}
		if (denominacion != null && denominacion.length() > 0) {
			String condicion1 = DataBaseUtils.like("unidad.denominacion", "denominacion", parametros, denominacion);
			String condicion2 = DataBaseUtils.like("unidad.denomLenguaCooficial", "denomcooficial", parametros,
					denominacion);
			where.add(" ((" + condicion1 + ") or (" + condicion2 + ")) ");
		}
		if (codigoNivelAdministracion != null && codigoNivelAdministracion != -1) {
			where.add(" unidad.nivelAdministracion.codigoNivelAdministracion = :codigoNivelAdministracion ");
			parametros.put("codigoNivelAdministracion", codigoNivelAdministracion);
		}

		if (codComunidad != null && codComunidad != -1) {
			// al nivel administración Universidades no les aplica el ámbito territorial,
			// por tanto hay que considerar la comunidad de los datos de contacto
			if (Dir3caibConstantes.NIVEL_ADMINISTRACION_UNIVERSIDADES.equals(codigoNivelAdministracion)) {
				where.add(
						" (unidad.codAmbComunidad.codigoComunidad = :codComunidad or unidad.codComunidad.codigoComunidad = :comunidad) ");
				parametros.put("codComunidad", codComunidad);
				parametros.put("comunidad", codComunidad);

			} else {
				where.add(" unidad.codAmbComunidad.codigoComunidad = :codComunidad ");
				parametros.put("codComunidad", codComunidad);
			}

		}

		if (provincia != null && provincia != -1) {
			where.add(" unidad.codAmbProvincia.codigoProvincia = :codProvincia ");
			parametros.put("codProvincia", provincia);
		}
		if (localidad != null && !localidad.equals("-1") && !localidad.isEmpty()) {
			// Se consideran los dos campos de localidad porque hemos detectado que la
			// localidad viene informada indistintamente en ambas y no sigue un criterio
			// lógico,
			String[] localidadsplit = localidad.split("-");
			where.add(
					" (unidad.catLocalidad.codigoLocalidad = :localidad or unidad.codLocalidad.codigoLocalidad = :codlocalidad) ");
			parametros.put("localidad", Long.parseLong(localidadsplit[0]));
			parametros.put("codlocalidad", Long.parseLong(localidadsplit[0]));
			if (provincia != null && provincia != -1) {
				where.add(
						" (unidad.catLocalidad.provincia.codigoProvincia = :provincia  or unidad.codLocalidad.provincia.codigoProvincia = :codprovincia) ");
				parametros.put("provincia", provincia);
				parametros.put("codprovincia", provincia);
				if (localidadsplit[1] != null && localidadsplit[1].length() > 0) {
					where.add(
							" (unidad.catLocalidad.entidadGeografica.codigoEntidadGeografica = :entidadGeografica  or unidad.codLocalidad.entidadGeografica.codigoEntidadGeografica = :codentidadGeografica) ");
					parametros.put("entidadGeografica", localidadsplit[1]);
					parametros.put("codentidadGeografica", localidadsplit[1]);
				}
			}

		}
		// Solo se buscaran vigentes cuando lo indiquen
		if (vigentes) {
			where.add(" unidad.estado.codigoEstadoEntidad =:vigente ");
			parametros.put("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
		}
		if (unidadRaiz) {
			where.add(" unidad.codUnidadRaiz.codigo = unidad.codigo ");
		}

		// where.add(" unidad.version = (select max(uu.version) from Unidad uu where
		// uu.codigoDir3 = unidad.codigoDir3) ");

		// Añadimos los parametros a la query
		if (parametros.size() != 0) {
			query.append("where ");
			int count = 0;
			for (String w : where) {
				if (count != 0) {
					query.append(" and ");
				}
				query.append(w);
				count++;
			}
			query.append("order by unidad.estado.codigoEstadoEntidad desc, unidad.denominacion asc ");
			q = em.createQuery(query.toString());

			for (Map.Entry<String, Object> param : parametros.entrySet()) {
				q.setParameter(param.getKey(), param.getValue());
			}

		} else {
			query.append("order by unidad.estado.codigoEstadoEntidad desc, unidad.denominacion asc ");
			q = em.createQuery(query.toString());
		}

		// Generamos el Nodo
		List<Nodo> unidades = NodoUtils.getNodoListOpenData(q.getResultList(), denominacionCooficial);

		// Cargamos los contactos de las unidades
		for (Nodo nodo : unidades) {
			List<ContactoUnidadOrganica> contactos = contactoUOEjb.getContactosByUnidad(nodo.getCodigo());
			List<String> contactosStr = new ArrayList<>();
			for (ContactoUnidadOrganica cont : contactos) {
				contactosStr.add(cont.getTipoContacto().getDescripcionTipoContacto() + ": " + cont.getValorContacto());
			}
			nodo.setContactos(contactosStr);
			// Dades obertes ha demanat que no es retornin de moment issue #6
			// nodo.setOficinasDependientes(oficinaEjb.oficinasDependientes(nodo.getCodigo(),
			// Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
			// nodo.setOficinasFuncionales(relacionOrganizativaOfiEjb.getOrganizativasByUnidadEstado(nodo.getCodigo(),
			// Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
		}

		// Si nos indican la variable conOficinas a true es que interesa devolver solo
		// aquellos organismos
		// que tienen oficinas en las que registrar
		if (conOficinas) {
			Set<Nodo> unidadesConOficinas = new HashSet<Nodo>();
			for (Nodo unidad : unidades) {
				if (tieneOficinasOrganismo(unidad.getCodigo())) {
					unidadesConOficinas.add(unidad);
				}
			}
			unidades = new ArrayList<Nodo>(unidadesConOficinas);
		}

		// Actualizamos las unidades obtenidas y marcamos si tienen oficinasSIR
		// y sustituimos el valor del codigo por el codigoDir3(sin version)
		for (Nodo unidad2 : unidades) {
			// if (obtenerOficinasSIRUnidad(unidad2.getCodigo()).size() > 0) {
			if (oficinaEjb.obtenerOficinasSIRUnidad(unidad2.getCodigo(), false).size() > 0) {
				unidad2.setTieneOficinaSir(true);
			}
			// Unidad, unidad raiz, unidadSuperior
			String[] codigoSeparado = unidad2.getCodigo().split(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION);
			unidad2.setCodigo(codigoSeparado[0]);
			unidad2.setSuperior(unidad2.getSuperior().substring(0,
					unidad2.getSuperior().lastIndexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION)));
			unidad2.setRaiz(unidad2.getRaiz().substring(0,
					unidad2.getRaiz().lastIndexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION)));

		}
		return unidades;

	}

	/**
	 * Método que permite buscar oficinas según el conjunto de criterios indicados
	 * en los parámetros.
	 *
	 * @param codigo                    código de la oficina
	 * @param denominacion              denominación de la oficina
	 * @param codigoNivelAdministracion nivel de administración de la oficina
	 * @param codComunidad              comunidad a la que pertenece la oficina
	 * @return Nodo conjunto de datos a mostrar de la oficina
	 * @throws Exception
	 */

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion,
			Long codComunidad, Long provincia, String localidad, boolean oficinasSir, boolean vigentes)
			throws Exception {
		return busquedaOficinas(codigo, denominacion, codigoNivelAdministracion, codComunidad, provincia, localidad,
				oficinasSir, vigentes, false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion,
			Long codComunidad, Long provincia, String localidad, boolean oficinasSir, boolean vigentes,
			boolean denominacionCooficial) throws Exception {
		Query q;
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<String> where = new ArrayList<String>();

		StringBuilder query = new StringBuilder(
				"Select oficina.codigo, oficina.denominacion, oficina.estado.codigoEstadoEntidad, "
						+ "unidadRaiz.codigo, unidadRaiz.denominacion, oficina.codUoResponsable.codigo, oficina.codUoResponsable.denominacion, "
						+ "ofilocalidad.descripcionLocalidad, "
						+ "oficina.denomLenguaCooficial, unidadRaiz.denomLenguaCooficial, oficina.codUoResponsable.denomLenguaCooficial "
						+ "from Oficina as oficina left outer join oficina.codUoResponsable.codUnidadRaiz as unidadRaiz left outer join oficina.localidad as ofilocalidad ");

		if (oficinasSir) {
			StringBuilder conOficinasSir = new StringBuilder(" left outer join oficina.servicios as servicios ");
			query.append(conOficinasSir);
		}

		// Parametros de busqueda

		if (codigo != null && codigo.length() > 0) {
			where.add(DataBaseUtils.like("oficina.codigo ", "codigo", parametros, codigo));
		}
		if (denominacion != null && denominacion.length() > 0) {
			// where.add(DataBaseUtils.like("oficina.denominacion ", "denominacion",
			// parametros, denominacion));
			String condicion1 = DataBaseUtils.like("oficina.denominacion ", "denominacion", parametros, denominacion);
			String condicion2 = DataBaseUtils.like("oficina.denomLenguaCooficial ", "denomcooficial", parametros,
					denominacion);
			where.add("((" + condicion1 + ") or (" + condicion2 + "))");
		}
		if (codComunidad != null && codComunidad != -1) {
			where.add("oficina.codComunidad.codigoComunidad=:codComunidad ");
			parametros.put("codComunidad", codComunidad);
		}
		if (codigoNivelAdministracion != null && codigoNivelAdministracion != -1) {
			where.add("oficina.nivelAdministracion.codigoNivelAdministracion=:codigoNivelAdministracion ");
			parametros.put("codigoNivelAdministracion", codigoNivelAdministracion);
		}
		if (provincia != null && provincia != -1) {
			where.add(
					"(oficina.codUoResponsable.codAmbProvincia.codigoProvincia=:codigoProvincia or oficina.localidad.provincia.codigoProvincia =:codigoProvincia) ");
			parametros.put("codigoProvincia", provincia);
		}

		if (localidad != null && !localidad.equals("-1") && !localidad.isEmpty()) {
			// Separamos el codigo de la localidad del codigo de la entidadGeografica
			String[] localidadsplit = localidad.split("-");
			where.add(" oficina.localidad.codigoLocalidad = :localidad ");
			parametros.put("localidad", Long.parseLong(localidadsplit[0]));
			if (provincia != null && provincia != -1) {
				where.add(" oficina.localidad.provincia.codigoProvincia = :provincia ");
				parametros.put("provincia", provincia);
				if (localidadsplit[1] != null && localidadsplit[1].length() > 0) {
					where.add(" oficina.localidad.entidadGeografica.codigoEntidadGeografica = :entidadGeografica ");
					parametros.put("entidadGeografica", localidadsplit[1]);
				}
			}
		}
		// Solo se buscaran con estado vigente cuando lo indiquen
		if (vigentes) {
			where.add(" oficina.estado.codigoEstadoEntidad =:vigente ");
			parametros.put("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
		}

		// buscamos aquellas que sean oficinas sir de Recepcion
		if (oficinasSir) {
			where.add(" servicios.servicio.codServicio= :SERVICIO_SIR_RECEPCION ");
			parametros.put("SERVICIO_SIR_RECEPCION", Dir3caibConstantes.SERVICIO_SIR_RECEPCION);

		}

		// Añadimos los parametros a la query
		if (parametros.size() != 0) {
			query.append("where ");
			int count = 0;
			for (String w : where) {
				if (count != 0) {
					query.append(" and ");
				}
				query.append(w);
				count++;
			}
			query.append("order by oficina.denominacion asc, oficina.denomLenguaCooficial asc");
			q = em.createQuery(query.toString());

			for (Map.Entry<String, Object> param : parametros.entrySet()) {
				q.setParameter(param.getKey(), param.getValue());
			}

		} else {
			query.append("order by oficina.denominacion asc");
			q = em.createQuery(query.toString());
		}

		List<Nodo> nodos = NodoUtils.getNodoListExtendido(q.getResultList(), denominacionCooficial);

		// correcció codigo per no tornar el código amb la versió
		for (Nodo nodo : nodos) {
			nodo.setRaiz(nodo.getRaiz().substring(0,
					nodo.getRaiz().lastIndexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION)));
			nodo.setCodigoSuperior(nodo.getCodigoSuperior().substring(0,
					nodo.getCodigoSuperior().lastIndexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION)));
		}
		return nodos;

	}

	/**
	 * Devuelve la denominación de la unidad especificada por codigo
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */

	@Override
	@SuppressWarnings(value = "unchecked")
	public String unidadDenominacion(String codigo) throws Exception {
		return unidadDenominacion(codigo, false, null);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public String unidadDenominacion(String codigo, boolean denominacionCooficial, String estado) throws Exception {

		Query q = em.createQuery("select unidad.denominacion, unidad.denomLenguaCooficial " + "from Unidad as unidad "
				+ "where unidad.codigoDir3=:codigo " + "and unidad.estado.codigoEstadoEntidad = :estado");
		q.setParameter("codigo", codigo);
		q.setParameter("estado", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		List<Object[]> unidades = q.getResultList();

		if (unidades.size() > 0) {
			Object[] obj = unidades.get(0);
			return (denominacionCooficial && Utils.isNotEmpty((String) obj[1])) ? (String) obj[1] : (String) obj[0];
		} else {
			return "";
		}
	}

	/**
	 * Devuelve el estado de la unidad especificada por codigo
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public String unidadEstado(String codigo) throws Exception {

		Query q = em.createQuery("select unidad.estado.codigoEstadoEntidad " + "from Unidad as unidad "
				+ "where unidad.codigoDir3=:codigo "
				+ "and unidad.version = (select max(uu.version) from Unidad uu where uu.codigoDir3 = unidad.codigoDir3)")
				.setParameter("codigo", codigo);

		List<String> unidades = q.getResultList();

		if (unidades.size() > 0) {
			return unidades.get(0);
		} else {
			return "";
		}
	}

	/**
	 * Devuelve la denominación de la oficina especificada por codigo
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */

	@Override
	@SuppressWarnings(value = "unchecked")
	public String oficinaDenominacion(String codigo) throws Exception {
		return oficinaDenominacion(codigo, false, null);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public String oficinaDenominacion(String codigo, boolean denominacionCooficial, String estado) throws Exception {

		Query q = em.createQuery(
				"select oficina.denominacion, oficina.denomLenguaCooficial from Oficina as oficina where oficina.codigo=:codigo and oficina.estado.codigoEstadoEntidad = :estado");

		q.setParameter("codigo", codigo);
		q.setParameter("estado", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		List<Object[]> oficinas = q.getResultList();

		if (oficinas.size() > 0) {
			Object[] obj = oficinas.get(0);
			return (denominacionCooficial && Utils.isNotEmpty((String) obj[1])) ? (String) obj[1] : (String) obj[0];
		} else {
			return "";
		}
	}

	/**
	 * Método que busca unidades por denominación y comunidad para utilidad en
	 * HELIUM.
	 *
	 * @param denominacion
	 * @param codComunidad
	 * @return List<Nodo> listado de objetos nodo con el código,denominación,
	 *         denominación de unidad raiz y denominación de unidad superior
	 * @throws Exception
	 */

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> busquedaDenominacionComunidad(String denominacion, Long codComunidad) throws Exception {
		return busquedaDenominacionComunidad(denominacion, codComunidad, false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> busquedaDenominacionComunidad(String denominacion, Long codComunidad,
			boolean denominacionCooficial) throws Exception {

		Query q;
		List<String> where = new ArrayList<String>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder(
				"Select unidad.codigo, unidad.denominacion, unidad.codUnidadRaiz.denominacion, "
						+ "unidad.codUnidadSuperior.denominacion, unidad.denomLenguaCooficial, unidad.codUnidadRaiz.denomLenguaCooficial, "
						+ "unidad.codUnidadSuperior.denomLenguaCooficial, unidad.codigoDir3 from Unidad as unidad ");

		// Denominación
		if (denominacion != null && denominacion.length() > 0) {
			// where.add(DataBaseUtils.like("unidad.denominacion", "denominacion",
			// parametros, denominacion));
			String condicion1 = DataBaseUtils.like("unidad.denominacion", "denominacion", parametros, denominacion);
			String condicion2 = DataBaseUtils.like("unidad.denomLenguaCooficial", "denomcooficial", parametros,
					denominacion);
			where.add(" ((" + condicion1 + ") or (" + condicion2 + ")) ");
		}
		// Comunidad Autónoma
		if (codComunidad != null && codComunidad != -1) {
			where.add(" unidad.codAmbComunidad.codigoComunidad = :codComunidad ");
			parametros.put("codComunidad", codComunidad);
		}

		// vigentes
		where.add(" unidad.estado.codigoEstadoEntidad = :codigoEstado ");
		parametros.put("codigoEstado", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		// Añadimos los parametros a la query
		if (parametros.size() != 0) {
			query.append("where ");
			int count = 0;
			for (String w : where) {
				if (count != 0) {
					query.append(" and ");
				}
				query.append(w);
				count++;
			}

			query.append("order by unidad.denominacion asc, unidad.denomLenguaCooficial asc");

			q = em.createQuery(query.toString());

			for (Map.Entry<String, Object> param : parametros.entrySet()) {
				q.setParameter(param.getKey(), param.getValue());
			}

		} else {
			query.append("order by unidad.denominacion asc, unidad.denomLenguaCooficial asc");
			q = em.createQuery(query.toString());
		}

		return NodoUtils.getNodoListUnidadRaizUnidadSuperior(q.getResultList(), denominacionCooficial, true);
	}

	/**
	 * Método que obtiene las localidades en funcion de una provincia y de una
	 * entidad geografica
	 *
	 * @param codigoProvincia
	 * @param codigoEntidadGeografica
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CodigoValor> getLocalidadByProvinciaEntidadGeografica(Long codigoProvincia,
			String codigoEntidadGeografica, String estado) throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado)) ? " and catLocalidad.estado.codigoEstadoEntidad = :estado "
				: "";

		Query q = em.createQuery(
				"Select catLocalidad.codigoLocalidad, catLocalidad.descripcionLocalidad from CatLocalidad as catLocalidad "
						+ " left outer join catLocalidad.provincia as provincia "
						+ " left outer join catLocalidad.entidadGeografica as entidadGeografica "
						+ " where provincia.codigoProvincia =:codigoProvincia and entidadGeografica.codigoEntidadGeografica=:codigoEntidadGeografica "
						+ filtreEstado + " order by catLocalidad.descripcionLocalidad ASC");

		q.setParameter("codigoProvincia", codigoProvincia);
		q.setParameter("codigoEntidadGeografica", codigoEntidadGeografica);

		if (filtreEstado != "")
			q.setParameter("estado", estado);

		return transformarACodigoValor(q.getResultList());

	}

	/**
	 * Obtiene todas las comunidades Autónomas por defecto que estan vigentes.
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CodigoValor> getComunidadesAutonomas(String estado) throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado)) ? "where ca.estado.codigoEstadoEntidad = :estado " : "";

		Query q = em.createQuery("select ca.codigoComunidad, ca.descripcionComunidad from CatComunidadAutonoma as ca "
				+ filtreEstado + " order by ca.descripcionComunidad");

		if (filtreEstado != "")
			q.setParameter("estado", estado);

		return transformarACodigoValor(q.getResultList());

	}

	/**
	 * Obtiene todas las entidades geográficas por defecto que están vigentes
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CodigoValor> getEntidadesGeograficas(String estado) throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado)) ? " where eg.estado.codigoEstadoEntidad = :estado " : "";

		Query q = em.createQuery(
				"select eg.codigoEntidadGeografica, eg.descripcionEntidadGeografica from CatEntidadGeografica as eg "
						+ filtreEstado + " order by eg.codigoEntidadGeografica");

		if (filtreEstado != "")
			q.setParameter("estado", estado);

		return transformarACodigoValor(q.getResultList());

	}

	/**
	 * Obtiene todas las Provincias
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CodigoValor> getProvincias(String estado) throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado)) ? " where prov.estado.codigoEstadoEntidad = :estado " : "";

		Query q = em.createQuery("select prov.codigoProvincia, prov.descripcionProvincia from CatProvincia as prov "
				+ filtreEstado + "order by prov.descripcionProvincia");

		if (filtreEstado != "")
			q.setParameter("estado", estado);

		return transformarACodigoValor(q.getResultList());

	}

	/**
	 * Obtiene todas las Provincias de una comunidad
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CodigoValor> getProvinciasByComunidad(Long codComunidad, String estado) throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado)) ? " and prov.estado.codigoEstadoEntidad = :estado " : "";

		Query q = em.createQuery("Select prov.codigoProvincia, prov.descripcionProvincia from CatProvincia as prov "
				+ "where prov.comunidadAutonoma.codigoComunidad =:codComunidad " + filtreEstado
				+ "order by prov.codigoProvincia");

		q.setParameter("codComunidad", codComunidad);

		if (filtreEstado != "") {
			q.setParameter("estado", estado);
		}

		return transformarACodigoValor(q.getResultList());

	}

	/**
	 * Obtiene todas los niveles de administración
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CodigoValor> getNivelesAdministracion(String estado) throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado)) ? " where na.estado.codigoEstadoEntidad = :estado " : "";

		Query q = em.createQuery("select na.codigoNivelAdministracion, na.descripcionNivelAdministracion "
				+ "from CatNivelAdministracion as na " + filtreEstado + "order by na.descripcionNivelAdministracion");

		if (filtreEstado != "") {
			q.setParameter("estado", estado);
		}

		return transformarACodigoValor(q.getResultList());

	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CodigoValor> getAmbitoTerritorialByAdministracion(Long nivelAdministracion, String estado)
			throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado))
				? " and catAmbitoTerritorial.estado.codigoEstadoEntidad = :estado "
				: "";

		Query q = em.createQuery("Select catAmbitoTerritorial.codigoAmbito,catAmbitoTerritorial.descripcionAmbito "
				+ "from CatAmbitoTerritorial as catAmbitoTerritorial "
				+ "where catAmbitoTerritorial.nivelAdministracion.codigoNivelAdministracion = :nivelAdministracion "
				+ filtreEstado + "order by catAmbitoTerritorial.codigoAmbito");

		q.setParameter("nivelAdministracion", nivelAdministracion);

		if (filtreEstado != "") {
			q.setParameter("estado", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
		}

		return transformarACodigoValor(q.getResultList());
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CodigoValor> getEstadosEntidad(String estado) throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado))
				? " where catEstadoEntidad.estado.codigoEstadoEntidad = :estado "
				: "";

		Query q = em
				.createQuery("Select catEstadoEntidad.codigoEstadoEntidad, catEstadoEntidad.descripcionEstadoEntidad "
						+ "from CatEstadoEntidad as catEstadoEntidad " + filtreEstado
						+ "order by catEstadoEntidad.codigoEstadoEntidad ASC");

		if (filtreEstado != "") {
			q.setParameter("estado", estado);
		}

		return transformarACodigoValor(q.getResultList());

	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CodigoValor> getTiposVia(String estado) throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado)) ? " where catTipoVia.estado.codigoEstadoEntidad = :estado "
				: "";

		Query q = em.createQuery("Select catTipoVia.codigoTipoVia, catTipoVia.descripcionTipoVia "
				+ "from CatTipoVia as catTipoVia " + filtreEstado + "order by catTipoVia.descripcionTipoVia");

		if (filtreEstado != "") {
			q.setParameter("estado", estado);
		}

		return transformarACodigoValor(q.getResultList());

	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<CatPais> getPaises(String estado) throws Exception {

		String filtreEstado = (Utils.isNotEmpty(estado)) ? " where catPais.estado.codigoEstadoEntidad = :estado " : "";

		Query q = em.createQuery(
				"Select catPais " + "from CatPais as catPais " + filtreEstado + "order by catPais.descripcionPais");

		if (filtreEstado != "") {
			q.setParameter("estado", estado);
		}

		return q.getResultList();

	}

	/**
	 * Método que transforma los resultados de una lista de Object[] en una lista de
	 * CodigoValor
	 *
	 * @param resultados
	 * @return
	 */
	private List<CodigoValor> transformarACodigoValor(List<Object[]> resultados) {
		List<CodigoValor> codigosValor = new ArrayList<CodigoValor>();
		for (Object[] obj : resultados) {
			CodigoValor codigoValor = new CodigoValor();
			codigoValor.setId(obj[0]);
			codigoValor.setDescripcion((String) obj[1]);
			codigosValor.add(codigoValor);
		}

		return codigosValor;

	}

	/**
	 * Método que transforma los resultados de una lista de Object[] en una lista de
	 * ObjetoDirectorio
	 *
	 * @param resultados
	 * @return
	 */
	private List<ObjetoDirectorio> transformarAObjetoDirectorio(List<Object[]> resultados,
			boolean denominacionCooficial) {
		List<ObjetoDirectorio> objetoDirectorios = new ArrayList<ObjetoDirectorio>();
		for (Object[] obj : resultados) {
			objetoDirectorios.add(new ObjetoDirectorio((String) obj[0],
					((denominacionCooficial && Utils.isNotEmpty((String) obj[2])) ? (String) obj[2]
							: (String) obj[1])));
		}

		return objetoDirectorios;

	}

	@Override
	public OficinaRest obtenerOficina(String codigo, Date fechaActualizacion, Date fechaSincronizacion,
			boolean denominacionOficial, String estado) throws Exception {

		Oficina oficina = oficinaEjb.findById(codigo, estado);

		if (oficina != null) {
			OficinaRest oficinaRest = null;

			List<ContactoOfi> contactosVisibles = new ArrayList<ContactoOfi>();
			for (ContactoOfi contactoOfi : oficina.getContactos()) {
				if (contactoOfi.isVisibilidad()) {
					contactosVisibles.add(contactoOfi);
				}
			}
			oficina.setContactos(contactosVisibles);
			if (fechaActualizacion != null) {

				if (fechaActualizacion.before(oficina.getFechaImportacion())) {

					// Cogemos solo las relaciones organizativas posteriores a la fecha de
					// sincronizacion
					Set<RelacionOrganizativaOfi> todasRelaciones = new HashSet<RelacionOrganizativaOfi>(
							oficina.getOrganizativasOfi());
					Set<RelacionOrganizativaOfi> relacionesValidas = new HashSet<RelacionOrganizativaOfi>();
					for (RelacionOrganizativaOfi relOrg : todasRelaciones) {
						// TODO revisar esta condicion
						if (relOrg.getUnidad().getFechaExtincion().before(fechaSincronizacion)) {
							relacionesValidas.add(relOrg);
						}
					}
					oficina.setOrganizativasOfi(null);
					oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesValidas));

					oficinaRest = OficinaRest.generar(oficina, denominacionOficial);
				}
			} else {
				oficinaRest = OficinaRest.generar(oficina, denominacionOficial);
			}

			return oficinaRest;
		}

		log.info("WS: Oficina cuyo codigoDir3 es " + codigo + " no existe o no es vigente");
		return null;

	}

	@Override
	public List<OficinaRest> obtenerArbolOficinas(String codigo, Date fechaActualizacion, Date fechaSincronizacion,
			boolean denominacionOficial) throws Exception {

		log.info("WS: Inicio obtener Oficinas");
		Long start = System.currentTimeMillis();

		List<Unidad> unidades = new ArrayList<Unidad>();
		Unidad unidad = null;

		if (fechaActualizacion != null) { // ES actualizacion, miramos si la raiz se ha actualizado
			unidad = unidadEjb.findUnidadActualizada(codigo, fechaActualizacion);
			if (unidad != null) { // Han actualizado la raiz
				// miramos que no esté extinguida o anulada antes de la primera sincro.
				if (unidadEjb.unidadValida(unidad, fechaSincronizacion)) {
					unidades.add(unidad);
					Set<HistoricoUO> historicosRaiz = unidad.getHistoricosAnterior();
					if (historicosRaiz != null) {
						for (HistoricoUO historico : historicosRaiz) {
							unidades.add(historico.getUnidadUltima());
						}
					}
				}
			}
		}

		if (unidad == null) { // O es Sincro o es actualizacion pero con la raiz sin actualizar.
			unidad = unidadEjb.findUnidadEstado(codigo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
			if (unidad != null) {
				unidades.add(unidad);
			}
		}

		List<OficinaRest> arbolRest = new ArrayList<OficinaRest>();

		if (unidad != null) {
			List<Unidad> arbol = unidadEjb.obtenerArbol(unidad.getCodigo());
			if (arbol != null)
				unidades.addAll(arbol);

			List<Oficina> oficinasCompleto = new ArrayList<Oficina>();

			// Por cada Unidad, obtenemos sus Oficinas
			for (Unidad uni : unidades) {
				List<Oficina> oficinas = oficinaEjb.obtenerOficinasOrganismo(uni.getCodigo(), fechaActualizacion,
						fechaSincronizacion);
				oficinasCompleto.addAll(oficinas);
			}

			for (Oficina oficina : oficinasCompleto) {
				arbolRest.add(OficinaRest.generar(oficina, denominacionOficial));
			}
		}
		log.info("Total arbol: " + unidades.size());

		Long end = System.currentTimeMillis();
		log.info("tiempo obtenerArbolOficinas: " + Utils.formatElapsedTime(end - start));
		return arbolRest;

	}

	@Override
	public List<OficinaRest> obtenerOficinasSIRUnidad(String codigoUnidad, boolean denominacionOficial)
			throws Exception {

		List<Oficina> oficinas = oficinaEjb.obtenerOficinasSIRUnidad(codigoUnidad, true);

		List<OficinaRest> oficinasRest = new ArrayList<OficinaRest>();

		for (Oficina oficina : oficinas) {
			oficinasRest.add(OficinaRest.generar(oficina, denominacionOficial));
		}

		return oficinasRest;

	}

	/**
	 * Obtiene todas las {@link es.caib.dir3caib.persistence.model.Oficina} SIR cuyo
	 * organismo responsable es el indicado por código(del padres e hijos).
	 *
	 * @param codigo Código del organismo
	 */
	@Override
	public List<OficinaRest> obtenerArbolOficinasSir(String codigo, boolean denominacionCooficial) throws Exception {
		List<Oficina> oficinas = oficinaEjb.obtenerOficinasSIRArbol(codigo, denominacionCooficial);

		List<OficinaRest> oficinasRest = new ArrayList<OficinaRest>();

		for (Oficina oficina : oficinas) {
			oficinasRest.add(OficinaRest.generarLigero(oficina, denominacionCooficial));
		}

		return oficinasRest;
	}

	@Override
	public Date obtenerFechaUltimaActualizacion() throws Exception {

		Sincronizacion sincronizacion = sincronizacionEjb
				.ultimaSincronizacionCompletada(Dir3caibConstantes.UNIDADES_OFICINAS);

		return sincronizacion.getFechaImportacion();

	}

	@Override
	public UnidadRest obtenerUnidad(String codigo, Date fechaActualizacion, Date fechaSincronizacion,
			boolean denominacionCooficial) throws Exception {
		return UnidadRest.toUnidadRest(
				obtenerUnidadesEjb.obtenerUnidadWs(codigo, fechaActualizacion, fechaSincronizacion), true,
				denominacionCooficial, true, true);
	}

	@Override
	public UnidadRest buscarUnidad(String codigo, boolean denominacionCooficial) throws Exception {
		return UnidadRest.toUnidadRest(obtenerUnidadesEjb.buscarUnidadWs(codigo), true, denominacionCooficial, true,
				true);
	}

	@Override
	public List<UnidadRest> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion,
			boolean denominacionCooficial, boolean mostrarHistoricos, boolean mostrarContactos) throws Exception {
		return obtenerUnidadesEjb.obtenerArbolUnidadesRest(codigo, fechaActualizacion, fechaSincronizacion,
				mostrarHistoricos, mostrarContactos);
	}

	@Override
	public List<UnidadRest> obtenerArbolUnidadesDestinatarias(String codigo, boolean denominacionCooficial)
			throws Exception {

		List<UnidadWs> llista = obtenerUnidadesEjb.obtenerArbolUnidadesDestinatariasWs(codigo);
		List<UnidadRest> resultados = new ArrayList<UnidadRest>();
		for (UnidadWs unidad : llista) {
			resultados.add(UnidadRest.toUnidadRest(unidad, true, denominacionCooficial, true, true));
		}
		return resultados;
	}

	@Override
	public List<ObjetoDirectorioExtendido> obtenerHistoricosFinalesExtendido(String codigo) throws Exception {
		return obtenerUnidadesEjb.obtenerHistoricosFinalesExtendido(codigo);
	}

	@Override
	public List<UnidadRest> obtenerHistoricosFinales(String codigo, boolean denominacionCooficial) throws Exception {

		List<UnidadWs> llista = obtenerUnidadesEjb.obtenerHistoricosFinalesWs(codigo);
		List<UnidadRest> resultados = new ArrayList<UnidadRest>();
		for (UnidadWs unidad : llista) {
			resultados.add(UnidadRest.toUnidadRest(unidad, true, denominacionCooficial, true, true));
		}
		return resultados;
	}

	@Override
	public List<UnidadRest> obtenerHistoricosFinalesSIR(String codigo, boolean denominacionCooficial) throws Exception {

		List<UnidadWs> llista = obtenerUnidadesEjb.obtenerHistoricosFinalesSIRWs(codigo);
		List<UnidadRest> resultados = new ArrayList<UnidadRest>();
		for (UnidadWs unidad : llista) {
			resultados.add(UnidadRest.toUnidadRest(unidad, true, denominacionCooficial, true, true));
		}
		return resultados;

	}

}
