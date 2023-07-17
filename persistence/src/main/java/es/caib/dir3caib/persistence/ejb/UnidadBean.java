/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.DataBaseUtils;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.NodoUtils;
import es.caib.dir3caib.persistence.utils.Paginacion;
import es.caib.dir3caib.utils.Utils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
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
 * @author mgonzalez
 * @author anadal
 */
@Stateless(name = "UnidadEJB")
@SecurityDomain("seycon")
@RolesAllowed({ Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.ROL_TOTHOM, Dir3caibConstantes.DIR_WS })
public class UnidadBean extends BaseEjbJPA<Unidad, String> implements UnidadLocal {

	@EJB(mappedName = "dir3caib/OficinaEJB/local")
	private OficinaLocal oficinaEjb;

	protected final Logger log = Logger.getLogger(getClass());
	protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

	@PersistenceContext(unitName = "dir3caib")
	private EntityManager em;

	@Override
	public Unidad getReference(String id) throws Exception {

		return em.getReference(Unidad.class, id);
	}

	@Override
	public Unidad findById(String id) throws Exception {

		return em.find(Unidad.class, id);
	}

	@Override
	public Unidad findByPK(String codigo, Long version) throws Exception {
		Query q = em.createQuery("Select unidad from Unidad as unidad " + " where unidad.version = :version "
				+ " AND unidad.codigoDir3 = :codigo ");

		q.setParameter("version", version);
		q.setParameter("codigo", codigo);

		try {
			return (Unidad) q.getSingleResult();
		} catch (Throwable th) {
			return null;
		}
	}

	@Override
	public Unidad findByCodigoDir3UltimaVersion(String codigo) throws Exception {
		Query q = em.createQuery("select u from Unidad u " + "where  u.codigoDir3 =:codigo and  u.version = "
				+ " (select max(uu.version) from Unidad uu where uu.codigoDir3 = u.codigoDir3)  ");

		q.setParameter("codigo", codigo);


		try {
			Unidad unidad = (Unidad) q.getSingleResult();
			return unidad;
		} catch (Throwable th) {
			return null;
		}
	}


	/**
	 * Obtiene una unidad que es vigente con sus historicosUO
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Unidad findConHistoricosVigente(String codigo) throws Exception {
		Query q = em.createQuery(
				"select unidad from Unidad as unidad where unidad.codigoDir3=:codigo and unidad.estado.codigoEstadoEntidad=:vigente");

		q.setParameter("codigo", codigo);
		q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		Unidad unidad = null;
		List<Unidad> unidades = q.getResultList();
		if (unidades.size() > 0) {
			unidad = unidades.get(0);
		}

		if (unidad != null) {
			Hibernate.initialize(unidad.getHistoricosAnterior());
			Hibernate.initialize(unidad.getHistoricosUltima());
		}

		return unidad;
	}

	/**
	 * Obtiene una unidad que es vigente con sus historicosUO
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Unidad findConHistoricos(String codigo) throws Exception {
		Query q = em.createQuery("select unidad from Unidad as unidad where unidad.codigo=:codigo ");
		q.setParameter("codigo", codigo);

		Unidad unidad = null;
		List<Unidad> unidades = q.getResultList();
		if (unidades.size() > 0) {
			unidad = unidades.get(0);
		}

		if (unidad != null) {
			Hibernate.initialize(unidad.getHistoricosAnterior());
			Hibernate.initialize(unidad.getHistoricosUltima());
		}

		return unidad;
	}

	/**
	 * Método que busca la unidad con id indicado y estado indicado
	 *
	 * @param id
	 * @param estado
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Unidad findUnidadEstado(String id, String estado) throws Exception {
		
		String filtreEstado = (Utils.isNotEmpty(estado)) ? " and unidad.estado.codigoEstadoEntidad=:estado " : "";
		
		Query q = em.createQuery("select unidad from Unidad as unidad "
				+ "where unidad.codigoDir3=:id " + filtreEstado
				+ "and unidad.version = (select max(uu.version) from Unidad uu where uu.codigoDir3 = unidad.codigoDir3)");
		
		q.setParameter("id", id);
		
		if(filtreEstado != "")
			q.setParameter("estado", estado);

		List<Unidad> unidad = q.getResultList();

		if (unidad.size() > 0) {
			return unidad.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Obtiene el codigo, la denominación, el estado, la tupla codigo-denominacion
	 * de la unidad raiz y la tupla codigo-denominacion de la unidad. Se emplea para
	 * mostrar el arbol de unidades.
	 *
	 * @param id     identificador de la unidad
	 * @param estado estado de la unidad
	 * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
	 */

	@Override
	@SuppressWarnings("unchecked")
	public Nodo findUnidad(String id, String estado) throws Exception {
		return findUnidad(id, estado, false);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Nodo findUnidad(String id, String estado, boolean denominacionCooficial) throws Exception {

		Query q = em.createQuery("Select unidad.codigo, unidad.denominacion, unidad.estado.descripcionEstadoEntidad, "
				+ "unidad.codUnidadRaiz.codigo, unidad.codUnidadRaiz.denominacion, unidad.codUnidadSuperior.codigo, "
				+ "unidad.codUnidadSuperior.denominacion, unidad.denomLenguaCooficial, unidad.codUnidadRaiz.denomLenguaCooficial, "
				+ "unidad.codUnidadSuperior.denomLenguaCooficial, unidad.codigoDir3, unidad.version "
				+ "from Unidad as unidad where unidad.codigo=:id and unidad.estado.codigoEstadoEntidad =:estado");
		q.setParameter("id", id);
		q.setParameter("estado", estado);

		List<Object[]> unidades = q.getResultList();

		if (unidades.size() > 0) {
			Object[] obj = unidades.get(0);

			String denominacion = (denominacionCooficial && Utils.isNotEmpty((String) obj[7])) ? (String) obj[7]
					: (String) obj[1];
			String denominacionUnidadRaiz = (denominacionCooficial && Utils.isNotEmpty((String) obj[8]))
					? (String) obj[8]
					: (String) obj[4];
			String denominacionUnidadSuperior = (denominacionCooficial && Utils.isNotEmpty((String) obj[9]))
					? (String) obj[9]
					: (String) obj[6];

			Nodo nodo = new Nodo((String) obj[0], denominacion, (String) obj[2], obj[3] + " - " + denominacionUnidadRaiz,
					obj[5] + " - " + denominacionUnidadSuperior, "");
			
			nodo.setCodigoDir3((String) obj[10]);
			nodo.setVersion(String.valueOf(obj[11]));
			return nodo;
			
		} else {
			return null;
		}

	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Nodo findUnidadByCodigoDir3(String codigoDir3, String estado, boolean denominacionCooficial) throws Exception {

		Query q = em.createQuery("Select unidad.codigo, unidad.denominacion, unidad.estado.descripcionEstadoEntidad, "
				+ "unidad.codUnidadRaiz.codigo, unidad.codUnidadRaiz.denominacion, unidad.codUnidadSuperior.codigo, "
				+ "unidad.codUnidadSuperior.denominacion, unidad.denomLenguaCooficial, unidad.codUnidadRaiz.denomLenguaCooficial, "
				+ "unidad.codUnidadSuperior.denomLenguaCooficial, unidad.codigoDir3, unidad.version "
				+ "from Unidad as unidad where unidad.codigoDir3=:codigoDir3 and unidad.estado.codigoEstadoEntidad =:estado");
		q.setParameter("codigoDir3", codigoDir3);
		q.setParameter("estado", estado);

		List<Object[]> unidades = q.getResultList();

		if (unidades.size() > 0) {
			Object[] obj = unidades.get(0);

			String denominacion = (denominacionCooficial && Utils.isNotEmpty((String) obj[7])) ? (String) obj[7]
					: (String) obj[1];
			String denominacionUnidadRaiz = (denominacionCooficial && Utils.isNotEmpty((String) obj[8]))
					? (String) obj[8]
					: (String) obj[4];
			String denominacionUnidadSuperior = (denominacionCooficial && Utils.isNotEmpty((String) obj[9]))
					? (String) obj[9]
					: (String) obj[6];

			Nodo nodo = new Nodo((String) obj[0], denominacion, (String) obj[2], obj[3] + " - " + denominacionUnidadRaiz,
					obj[5] + " - " + denominacionUnidadSuperior, "");
			
			nodo.setCodigoDir3((String) obj[10]);
			nodo.setVersion(String.valueOf(obj[11]));
			return nodo;
			
		} else {
			return null;
		}

	}

	/**
	 * Obtiene si una unidad ha sido actualizada con fecha posterior a la fecha de
	 * actualización. Para ello mira si la fecha de importación de la unidad es
	 * posterior a la fecha de actualización indicada. La fecha de importación de la
	 * unidad se actualiza cada vez que se sincroniza con directorio común.
	 *
	 * Se usa para la sincronizacion con regweb3
	 *
	 * @param id
	 * @param fechaActualizacion
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Unidad findUnidadActualizada(String id, Date fechaActualizacion) throws Exception {
		Query q = em.createQuery("Select unidad from Unidad as unidad where unidad.codigoDir3 =:id "
				+ " and :fechaActualizacion < unidad.fechaImportacion "
				+ "and unidad.version = (select max(uu.version) from Unidad uu where uu.codigoDir3 = unidad.codigoDir3)");

		q.setParameter("id", id);
		q.setParameter("fechaActualizacion", fechaActualizacion);

		List<Unidad> unidades = q.getResultList();

		if (unidades.size() > 0) {
			return unidades.get(0);
		} else {
			return null;
		}

	}

	/**
	 * Obtiene el código, denominación y estado de la unidad indicada
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */

	@Override
	@SuppressWarnings("unchecked")
	public Unidad findByCodigoLigero(String codigo) throws Exception {
		return findByCodigoLigero(codigo, false);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Unidad findByCodigoLigero(String codigo, boolean denominacionCooficial) throws Exception {
		Query q = em.createQuery("select unidad.codigo, unidad.denominacion, unidad.estado.codigoEstadoEntidad, "
				+ "unidad.codUnidadRaiz.codigo, unidad.codUnidadSuperior.codigo, unidad.nivelJerarquico, unidad.denomLenguaCooficial,"
				+ "unidad.codigoDir3, unidad.version "
				+ "from Unidad as unidad where unidad.codigo=:codigo ");
		q.setParameter("codigo", codigo);

		List<Object[]> result = q.getResultList();

		if (result.size() == 1) {
			Unidad unidad = new Unidad();
			unidad.setCodigo((String) result.get(0)[0]);
			unidad.setDenominacion(
					(denominacionCooficial && Utils.isNotEmpty((String) result.get(0)[6])) ? (String) result.get(0)[6]
							: (String) result.get(0)[1]);
			unidad.setCodigoDir3((String) result.get(0)[7]);
			unidad.setVersion((Long) result.get(0)[8]);
			unidad.setEstado(new CatEstadoEntidad((String) result.get(0)[2]));
			Unidad unidadRaiz = new Unidad((String) result.get(0)[3]);
			unidad.setCodUnidadRaiz(unidadRaiz);
			Unidad unidadSuperior = new Unidad((String) result.get(0)[4]);
			unidad.setCodUnidadSuperior(unidadSuperior);
			unidad.setNivelJerarquico((Long) result.get(0)[5]);

			return unidad;
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	// TODO todas las versiones o solo las máximas?
	public List<Unidad> getAll() throws Exception {
		return em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo").getResultList();
	}

	@SuppressWarnings(value = "unchecked")
	// TODO relacionado con el getAll()
	public List<Unidad> getMaxResult(int maxResult) throws Exception {
		return em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo").setMaxResults(maxResult)
				.getResultList();
	}

	@Override
	// TODO relacionado con el getAll()
	public Long getTotal() throws Exception {
		Query q = em.createQuery("Select count(distinct unidad.codigo) from Unidad as unidad");
		return (Long) q.getSingleResult();
	}

	/**
	 * Obtiene una lista de unidades paginada desde un valor inicial y de la
	 * longitud base que establece dir3caib
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> getPagination(int inicio) throws Exception {

		Query q = em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo");
		q.setFirstResult(inicio);
		q.setMaxResults(RESULTADOS_PAGINACION);

		return q.getResultList();
	}

	/**
	 * Obtiene una lista de unidades paginada desde un valor inicial y de una
	 * longitud determinada
	 *
	 * @param startItem     valor inicial
	 * @param numberOfItems longitud de la lista
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> getPagination(int startItem, int numberOfItems) throws Exception {

		Query q = em.createQuery("Select unidad from Unidad as unidad order by unidad.codigo");
		q.setFirstResult(startItem);
		q.setMaxResults(numberOfItems);

		return q.getResultList();
	}

	/**
	 * Obtiene la Denominacion de una Unidad
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public String unidadDenominacion(String codigo) throws Exception {
		return unidadDenominacion(codigo, false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public String unidadDenominacion(String codigo, boolean denominacionCooficial) throws Exception {

		Query q = em.createQuery("select unidad.denominacion from Unidad as unidad where unidad.codigo=:codigo")
				.setParameter("codigo", codigo);

		List<String> unidades = q.getResultList();

		if (unidades.size() > 0) {
			return unidades.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Devuelve todas las unidades de la lista de ids indicados. Se emplea para
	 * montar la cache de unidades en la importación de unidades desde Madrid
	 *
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	// @Override
	// TODO REVISAR ver si nos quedamos con este método o el otro
	public List<Unidad> getListByIds(List<String> ids) throws Exception {

		Query q = em.createQuery("Select unidad.codigo from Unidad as unidad "
				+ " where unidad.codigo in (:theids) order by unidad.codigo");

		q.setParameter("theids", ids);

		List<Unidad> unidades = new ArrayList<Unidad>();
		List<?> result = q.getResultList();

		for (Object object : result) {
			unidades.add(new Unidad((String) object));
		}

		return unidades;
	}

	/**
	 * Obtiene todas las unidades de la lista de ids indicados. Se usa para la caché
	 * de unidades en la importación de oficinas y debe devolver los objetos de la
	 * bd, si no da un transient cuando se borran los contactos de una oficina.
	 *
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	// @Override
	/*
	 * public List<Unidad> getListByIds(List<String> ids) throws Exception {
	 *
	 * Query q = em.createQuery("Select unidad from Unidad as unidad " +
	 * " where concat(unidad.codigo, '-', unidad.version) in (:theids) order by unidad.codigo"
	 * );
	 *
	 * q.setParameter("theids", ids);
	 *
	 * return q.getResultList(); }
	 */

	/**
	 * Borra todos los historicos de las unidades
	 *
	 * @throws Exception
	 */
	public void deleteHistoricosUnidad() throws Exception {

		em.createNativeQuery("delete from DIR_HISTORICOUO").executeUpdate();
	}

	public void deleteAll() throws Exception {

		em.createQuery("delete from Unidad").executeUpdate();
	}

	/**
	 * Realiza una busqueda de {@link es.caib.dir3caib.persistence.model.Unidad}
	 * según los parámetros
	 *
	 * @param pageNumber
	 * @param codigo
	 * @param denominacion
	 * @param codigoNivelAdministracion
	 * @param codAmbitoTerritorial
	 * @param codComunidad
	 * @param codigoProvincia
	 * @param codigoEstado
	 * @param nifcif
	 * @param unidadVersion
	 * @param denominacionCooficial
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion, Long codigoNivelAdministracion,
			String codAmbitoTerritorial, Long codComunidad, Long codigoProvincia, Boolean unidadRaiz,
			String codigoEstado, String nifcif, Long unidadVersion, Boolean denominacionCooficial) throws Exception {

		Query q;
		Query q2;
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<String> where = new ArrayList<String>();

		StringBuilder query = new StringBuilder("Select unidad from Unidad as unidad ");

		// Parametros de busqueda
		if (!codigo.isEmpty()) {
			where.add(DataBaseUtils.like("unidad.codigo", "codigo", parametros, codigo));
		}
		if (!denominacion.isEmpty()) {

			String condicion1 = DataBaseUtils.like("unidad.denomLenguaCooficial", "denominacion1", parametros,
					denominacion);
			String condicion2 = DataBaseUtils.like("unidad.denominacion", "denominacion2", parametros, denominacion);

			// String condicion1 = "upper(unidad.denomLenguaCooficial) like
			// upper(:denominacion)";
			// String condicion2 = " upper(unidad.denominacion) like upper(:denominacion)";

			where.add("((" + condicion1 + ") or (" + condicion2 + "))");
			// parametros.put("denominacion", "%"+denominacion+"%");

		}
		if (codigoNivelAdministracion != null && codigoNivelAdministracion != -1) {
			where.add(" unidad.nivelAdministracion.codigoNivelAdministracion = :codigoNivelAdministracion ");
			parametros.put("codigoNivelAdministracion", codigoNivelAdministracion);
		}
		if (codAmbitoTerritorial != null && (!"-1".equals(codAmbitoTerritorial))) {
			where.add(" unidad.codAmbitoTerritorial.codigoAmbito = :codAmbitoTerritorial ");
			parametros.put("codAmbitoTerritorial", codAmbitoTerritorial);
		}
		if (codComunidad != null && codComunidad != -1) {
			where.add(" unidad.codAmbComunidad.codigoComunidad = :codComunidad ");
			parametros.put("codComunidad", codComunidad);
		}
		if (codigoProvincia != null && codigoProvincia != -1) {
			where.add(" unidad.codAmbProvincia.codigoProvincia = :codigoProvincia ");
			parametros.put("codigoProvincia", codigoProvincia);
		}
		if (codigoEstado != null && (!"-1".equals(codigoEstado))) {
			where.add(" unidad.estado.codigoEstadoEntidad = :codigoEstado ");
			parametros.put("codigoEstado", codigoEstado);
		}
		if (unidadRaiz) {
			where.add(" unidad.codUnidadRaiz.codigo = unidad.codigo ");
		}
		if (!nifcif.isEmpty()) {
			where.add(DataBaseUtils.like("unidad.nifcif", "nifcif", parametros, nifcif));
			// where.add(" upper(unidad.nifcif) like upper(:nifcif)");
			// parametros.put("nifcif", "%"+nifcif+"%");
		}
		if (unidadVersion != null && unidadVersion > 0) {
			where.add(" unidad.version = :unidadversion ");
			parametros.put("unidadversion", unidadVersion);
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
			q2 = em.createQuery(query.toString().replaceAll("Select unidad from Unidad as unidad ",
					"Select count(unidad.codigo) from Unidad as unidad "));
			query.append("order by unidad.denomLenguaCooficial asc, unidad.denominacion asc");
			q = em.createQuery(query.toString());

			
			for (Map.Entry<String, Object> param : parametros.entrySet()) {
				q.setParameter(param.getKey(), param.getValue());
				q2.setParameter(param.getKey(), param.getValue());
				//log.info("UnidadBean:busqueda parametro: " + param.getKey() + " => " + param.getValue());
			}
			

		} else {
			if (unidadRaiz) {
				query.append(" where unidad.codUnidadRaiz.codigo = unidad.codigo ");
			}
			q2 = em.createQuery(query.toString().replaceAll("Select unidad from Unidad as unidad ",
					"Select count(unidad.codigo) from Unidad as unidad "));
			query.append("order by unidad.denomLenguaCooficial asc, unidad.denominacion asc");
			q = em.createQuery(query.toString());
		}

		// log.info("UnidadBean:busqueda query: " + query.toString());

		Paginacion paginacion = null;

		if (pageNumber != null) { // Comprobamos si es una busqueda paginada o no
			Long total = (Long) q2.getSingleResult();
			paginacion = new Paginacion(total.intValue(), pageNumber);
			int inicio = (pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION;
			q.setFirstResult(inicio);
			q.setMaxResults(RESULTADOS_PAGINACION);
		} else {
			paginacion = new Paginacion(0, 0);
		}

		List<Nodo> nodos = NodoUtils.getNodoListUnidad(q.getResultList(), denominacionCooficial);

		for (Nodo nodo : nodos) {
			nodo.setTieneOficinaSir(oficinaEjb.tieneOficinasSIR(nodo.getCodigo()));
		}
		paginacion.setListado(new ArrayList<Object>(nodos));

		return paginacion;

	}

	/**
	 * Obtiene una unidad por su denominación
	 *
	 * @param denominacion
	 * @return
	 * @throws Exception
	 */

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> findByDenominacion(String denominacion) throws Exception {
		return findByDenominacion(denominacion, false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> findByDenominacion(String denominacion, boolean denominacionCooficial) throws Exception {

		Query q = em.createQuery(
				"select unidad from Unidad as unidad where upper(unidad.denominacion) like upper(:denominacion)");

		q.setParameter("denominacion", "%" + denominacion.toLowerCase() + "%");

		return q.getResultList();
	}

	/**
	 * Obtiene el arbol de una Unidad, pero solo los códigos Se emplea en el método
	 * de obtenerArbolOficinas.
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Unidad> obtenerArbol(String codigo) throws Exception {
		return obtenerArbol(codigo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
	}
	
	@Override
	public List<Unidad> obtenerArbol(String codigo, String estado) throws Exception {

		List<Unidad> arbol = new ArrayList<Unidad>();
		List<Unidad> hijos = hijosPrimerNivel(codigo, estado);

		arbol.addAll(hijos);

		for (Unidad hijo : hijos) {
			arbol.addAll(obtenerArbol(hijo.getCodigo(), estado));
		}

		return arbol;
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
				"Select unidad.id from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo order by unidad.codigo");

		q.setParameter("codigo", codigo);

		List<Long> hijos = q.getResultList();

		return hijos.size() > 0;
	}

	/**
	 * Método que obtiene los hijos de primer nivel de una unidad en función del
	 * estado de la unidad padre Se emplea para pintar el árbol de Unidades.
	 *
	 * @param codigo identificador de la unidad padre.
	 * @param estado estado de la unidad padre.
	 * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> hijos(String codigo, String estado) throws Exception {
		return hijos(codigo, estado, false, false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Nodo> hijos(String codigo, String estado, boolean denominacionCooficial, boolean codigoDir3) throws Exception {

		String campo = (codigoDir3) ? "codigo" : "codigoDir3";
		
		Query q = em.createQuery(
				"Select unidad.codigo, unidad.denominacion, unidad.estado.codigoEstadoEntidad,unidad.codUnidadRaiz.codigo, "
						+ "unidad.codUnidadRaiz.denominacion, unidad.codUnidadSuperior.codigo, unidad.codUnidadSuperior.denominacion, "
						+ "unidad.denomLenguaCooficial, unidad.codUnidadRaiz.denomLenguaCooficial, unidad.codUnidadSuperior.denomLenguaCooficial, unidad.codigoDir3 "
						+ "from Unidad as unidad where unidad.codUnidadSuperior."+campo+" =:codigo and unidad."+campo+" !=:codigo and unidad.estado.codigoEstadoEntidad =:estado order by unidad.codigo");

		q.setParameter("codigo", codigo);
		q.setParameter("estado", estado);

		return NodoUtils.getNodoList(q.getResultList(), denominacionCooficial);
	}

	/**
	 * Método que obtiene los codigos de los hijos vigentes de primer nivel de una
	 * unidad
	 *
	 * @param codigo identificador de la unidad padre.
	 * @return {@link es.caib.dir3caib.persistence.model.Unidad}
	 */
	@Override
	public List<Unidad> hijosPrimerNivel(String codigo, String estado) throws Exception {

		Query q = em.createQuery(
				"Select unidad.codigo from Unidad as unidad "
				+ "where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:estado order by unidad.codigo");

		q.setParameter("codigo", codigo);
		q.setParameter("estado", (Utils.isNotEmpty(estado)) ? estado : Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		List result = q.getResultList();
		List<Unidad> unidades = new ArrayList<Unidad>();

		for (Object object : result) {
			Unidad unidad = new Unidad((String) object);

			unidades.add(unidad);
		}

		return unidades;
	}

	/**
	 * Método recursivo que obtiene los hijos de las unidades indicadas en función
	 * del estado indicado y el resultado lo guarda en la variable hijosTotales Se
	 * emplea en el mètodo obtenerArbolUnidadesDestinatarias
	 *
	 * @param unidadesPadres unidadesPadres de las que obtener hijos
	 * @param estado         indica el estado de los hijos
	 * @param hijosTotales   lista con todos los hijos encontrados de manera
	 *                       recursiva.
	 *
	 * @return {@link es.caib.dir3caib.persistence.model.Unidad}
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public void arbolHijos(Set<Unidad> unidadesPadres, String estado, Set<Unidad> hijosTotales) throws Exception {
		arbolHijos(unidadesPadres, estado, hijosTotales, false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public void arbolHijos(Set<Unidad> unidadesPadres, String estado, Set<Unidad> hijosTotales,
			boolean denominacionCooficial) throws Exception {
		for (Unidad unidad : unidadesPadres) {

			Query q = em.createQuery("select unidad.codigoDir3, unidad.denominacion, unidad.codUnidadRaiz.codigoDir3, "
					+ "unidad.codUnidadSuperior.codigoDir3, unidad.esEdp, unidad.denomLenguaCooficial, unidad.codigo "
					+ "from Unidad as unidad "
					+ "where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:estado "
					+ "order by unidad.codigo");

			q.setParameter("codigo", unidad.getCodigo());
			q.setParameter("estado", estado);

			Set<Unidad> hijos = new HashSet<Unidad>();

			List<Object[]> result = q.getResultList();

			for (Object[] object : result) {
				String denominacion = (denominacionCooficial && Utils.isNotEmpty((String) object[5]))
						? (String) object[5]
						: (String) object[1];

				Unidad raiz= new Unidad();
				Unidad superior= new Unidad();
				raiz.setCodigoDir3((String) object[2]);
				superior.setCodigoDir3((String) object[3]);

				Unidad uni = new Unidad((String) object[6],(String) object[0],denominacion,raiz,superior,(Boolean) object[4]);

				if(Utils.isNotEmpty((String) object[5]))
					uni.setDenomLenguaCooficial((String) object[5]);

				hijos.add(uni);
			}

			hijosTotales.addAll(hijos);

			// llamada recursiva para todos los hijos
			arbolHijos(hijos, estado, hijosTotales, denominacionCooficial);
		}

	}

	/**
	 * Método recursivo que devuelve el árbol de unidades de la unidad indicada por
	 * código, teniendo en cuenta la fecha de la ultima actualización de regweb para
	 * el caso en que la unidad indicada no es raíz. Se emplea para la
	 * sincronizacion y actualización con regweb
	 *
	 * @param codigo
	 * @param fechaActualizacion
	 * @param fechaSincronizacion
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> obtenerArbolUnidadesUnidadNoRaiz(String codigo, Date fechaActualizacion,
			Date fechaSincronizacion) throws Exception {
		// log.info("obtenerArbolUnidades del código: " + codigo);

		// Este método es recursivo, ya que la query se basa en el organismo superior y
		// se hace necesario hacerlo
		// de forma recursiva para poder descender hasta el último nivel, ya que la
		// unidad indicada no es raiz.

		Query q;
		Query qHijos;

		if (fechaActualizacion == null) { // Es una sincronizacion, solo traemos vigentes
			q = em.createQuery(
					"Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.nivelJerarquico");
			q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
		} else {// es una actualizacion, traemos todo lo actualizado

			q = em.createQuery(
					"Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo  "
							+ "and :fechaActualizacion < unidad.fechaImportacion and unidad.version = (select max(uu.version) from Unidad uu where uu.codigoDir3 = unidad.codigoDir3) " + "order by unidad.nivelJerarquico");
			q.setParameter("fechaActualizacion", fechaActualizacion);

		}
		q.setParameter("codigo", codigo);

		// Obtenemos todos los hijos del organismo con código indicado para poder
		// obtener todos los cambios en niveles inferiores de forma recursiva
		qHijos = em.createQuery(
				"Select unidad from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo  "
						+ "order by unidad.nivelJerarquico");

		qHijos.setParameter("codigo", codigo);
		List<Unidad> todosHijos = qHijos.getResultList(); // todos los hijos de la unidad padre, para poder recorrer el
															// árbol en su totalidad

		List<Unidad> hijosActualizados = q.getResultList(); // Hijos actualizados solo por fecha de importación
		List<Unidad> hijosActualizadosValidos = new ArrayList<Unidad>(); // Hijos actualizados validos( unidadValida())
		List<Unidad> listaCompleta; // Lista completa de unidades a enviar a regweb3.

		// log.info(hijosActualizados.size() + " hijos actualizados del código : " +
		// codigo);

		if (fechaActualizacion != null) { // Si hay fecha de actualizacion solo se envian las actualizadas

			for (Unidad unidad : hijosActualizados) {
				log.debug("FECHA ACTUALIZACION " + fechaActualizacion
						+ "ANTERIOR A LA FECHA DE IMPORTACION DE LA UNIDAD ID " + unidad.getCodigo() + " FECHA IMPORT"
						+ unidad.getFechaImportacion());
				// Miramos que la unidad no este extinguida o anulada anterior a la fecha de
				// sincronizacion de regweb
				// ya que no debe ser enviada a regweb.
				if (unidadValida(unidad, fechaSincronizacion)) {
					hijosActualizadosValidos.add(unidad);
				}
			}
			listaCompleta = new ArrayList<Unidad>(hijosActualizadosValidos);
		} else { // si no hay fecha, se trata de una sincronización
			listaCompleta = new ArrayList<Unidad>(hijosActualizados);
		}

		for (Unidad unidad : todosHijos) {
			if (tieneHijos(unidad.getCodigo())) {
				List<Unidad> hijos = obtenerArbolUnidadesUnidadNoRaiz(unidad.getCodigo(), fechaActualizacion,
						fechaSincronizacion);
				listaCompleta.addAll(hijos);
			}
		}

		return listaCompleta;
	}

	/**
	 * Método que devuelve el árbol de unidades de la unidad indicada por codigo,
	 * teniendo en cuenta la fecha de la ultima actualización de regweb y se emplea
	 * para el caso que la unidad indicada es raíz. Se emplea para la sincronizacion
	 * y actualización con regweb
	 *
	 * @param codigo
	 * @param fechaActualizacion
	 * @param fechaSincronizacion
	 * @return
	 * @throws Exception
	 */
	// TODO debemos pasarle la denominacionCooficial??

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> obtenerArbolUnidadesUnidadRaiz(String codigo, Date fechaActualizacion, Date fechaSincronizacion)
			throws Exception {
		return obtenerArbolUnidadesUnidadRaiz(codigo,fechaActualizacion,fechaSincronizacion,false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> obtenerArbolUnidadesUnidadRaiz(String codigo, Date fechaActualizacion, Date fechaSincronizacion, boolean denominacionCooficial)
			throws Exception {

		String denominacion = unidadDenominacion(codigo, denominacionCooficial);
		Query q;

		try {
			if (fechaActualizacion == null) { // Es una sincronizacion
				// Obtenemos todos los organismos vigentes cuya unidad raiz es la indicada por
				// el código.
				q = em.createQuery(
						"Select unidad from Unidad as unidad where unidad.codUnidadRaiz.codigo =:codigo  and unidad.estado.codigoEstadoEntidad =:vigente order by unidad.nivelJerarquico");
				q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
			} else {
				// es una actualizacion, traemos aquellas unidades que tienen fechaactualizacion
				// anterior a la fecha de importacion de las unidades
				//TODO añadir aquí lo de max(unidad.version)
				q = em.createQuery("Select unidad from Unidad as unidad where unidad.codUnidadRaiz.codigo =:codigo "
						+ "and :fechaActualizacion < unidad.fechaImportacion and  unidad.version = (select max(uu.version) from Unidad uu where uu.codigoDir3 = unidad.codigoDir3) " + "order by unidad.nivelJerarquico");
				q.setParameter("fechaActualizacion", fechaActualizacion);
			}
			q.setParameter("codigo", codigo);

			List<Unidad> unidadesObtenidas = q.getResultList();// unidades candidatas(que devuelve la query) a ser
																// enviadas a regweb3
			List<Unidad> padresActualizados = new ArrayList<Unidad>(); // unidades que se han actualizado entre las
																		// ultimas fechas indicadas
			List<Unidad> listaCompleta; // Lista completa de unidades que se enviaran a regweb3 por cumplir todas las
										// condiciones de ser enviadas

			log.info(" ");
			log.info("UNIDAD PADRE: " + codigo + " - " + denominacion);

			if (fechaActualizacion != null) { // Si hay fecha de actualizacion solo se envian las actualizadas
				for (Unidad unidad : unidadesObtenidas) {
					log.debug("FECHA ACTUALIZACION " + fechaActualizacion
							+ "ANTERIOR A LA FECHA DE IMPORTACION DE LA UNIDAD ID " + unidad.getCodigo()
							+ " FECHA IMPORT" + unidad.getFechaImportacion());
					// Miramos que la unidad no este extinguida o anulada anterior a la fecha de
					// sincronizacion de regweb
					// ya que no debe ser enviada a regweb.
					if (unidadValida(unidad, fechaSincronizacion)) {
						padresActualizados.add(unidad);
					}
				}
				listaCompleta = new ArrayList<Unidad>(padresActualizados);
			} else { // si no hay fecha, se trata de una sincronización
				listaCompleta = new ArrayList<Unidad>(unidadesObtenidas);
			}

			log.info("Numero de hijos a actualizar: " + listaCompleta.size());

			log.info(" ");

			return listaCompleta;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Método que devuelve la unidad indicada por código siempre que esté vigente y
	 * tenga oficinas donde registrar. A partir de ella se obtienen todos sus hijos
	 * vigentes. solicitado por SISTRA
	 *
	 * @param codigo código de la unidad raiz de la que partimos.
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception {
		return obtenerArbolUnidadesDestinatarias(codigo,false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Unidad> obtenerArbolUnidadesDestinatarias(String codigo, boolean denominacionCooficial) throws Exception {

		Query q = em.createQuery(
				"Select unidad.codigoDir3, unidad.denominacion, unidad.codUnidadRaiz.codigoDir3, unidad.codUnidadSuperior.codigoDir3, unidad.esEdp, unidad.denomLenguaCooficial, unidad.codigo "
				+ "from Unidad as unidad where unidad.codigoDir3 =:codigo and unidad.estado.codigoEstadoEntidad =:vigente");
		q.setParameter("codigo", codigo);
		q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		// Transformamos a Unidades
		List<Unidad> unidades = new ArrayList<Unidad>();
		List<Object[]> result = q.getResultList();

		for (Object[] object : result) {
			String denominacion = (denominacionCooficial && Utils.isNotEmpty((String) object[5])) ? (String) object[5] : (String) object[1];
			Unidad raiz = new Unidad();
			raiz.setCodigoDir3((String) object[2]);
			Unidad superior = new Unidad();
			superior.setCodigoDir3((String) object[3]);

			Unidad nueva = new Unidad((String) object[6],(String) object[0],denominacion,raiz,superior,(Boolean) object[4]);

			if(Utils.isNotEmpty((String) object[5]))
				nueva.setDenomLenguaCooficial((String) object[5]);
			unidades.add(nueva);
		}


		Unidad unidadRaiz = null;
		// Lista con el resultado final de unidades destinatarias
		List<Unidad> unidadesDestConOficinas = new ArrayList<Unidad>();
		if (unidades.size() > 0) {
			unidadRaiz = unidades.get(0);

			// Miramos si la unidad que nos pasan tiene oficina que le registren
			Boolean tiene = tieneOficinasArbol(unidadRaiz.getCodigo());

			if (tiene) {
				unidadesDestConOficinas.add(unidadRaiz);
				Set<Unidad> unidadesRaices = new HashSet<Unidad>();
				// añadimos la unidad raiz porque tiene oficina
				unidadesRaices.add(unidadRaiz);
				// Aqui guardamos todas las unidades hijas que vamos a obtener de la unidad
				// indicada
				Set<Unidad> unidadesHijasTotales = new HashSet<Unidad>();
				// Obtenemos de manera recursiva todos los hijos de la unidad que nos indican
				arbolHijos(unidadesRaices, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE, unidadesHijasTotales);
				unidadesDestConOficinas.addAll(unidadesHijasTotales);

			}

		}

		return unidadesDestConOficinas;
	}

	/**
	 * Obtiene el código de todas las Unidades hijas de la unidad raiz indicada por
	 * código y que tienen una oficina que cuelga de ellas
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	public List<Unidad> obtenerUnidadesConOficina(String codigo) throws Exception {

		Query q = em.createQuery("Select unidad.codigo from Unidad as unidad, Oficina as oficina "
				+ "inner join oficina.codUoResponsable as unidadResponsable "
				+ "where unidadResponsable.codigo = unidad.codigo and unidad.codUnidadRaiz.codigo =:codigo and unidad.estado.codigoEstadoEntidad =:estado order by unidad.codigo");

		q.setParameter("codigo", codigo);
		q.setParameter("estado", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		List result = q.getResultList();
		List<Unidad> unidades = new ArrayList<Unidad>();

		for (Object object : result) {
			Unidad unidad = new Unidad((String) object);

			unidades.add(unidad);
		}

		return unidades;
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
	public List<Oficina> obtenerArbolOficinasOpenData(String codigo) throws Exception {

		log.info("WS: Inicio obtener Oficinas");
		// Obtenemos todos las unidades vigentes de la unidad Raiz

		Long start = System.currentTimeMillis();

		List<Unidad> unidades = new ArrayList<Unidad>();
		Unidad unidad = null;

		unidad = findUnidadEstado(codigo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
		if (unidad != null) {
			// Añadimos la unidad para que se obtengan sus oficinas
			unidades.add(unidad);
		}

		unidades.addAll(obtenerArbol(unidad.getCodigo()));
		log.info("Total arbol: " + unidades.size());

		List<Oficina> oficinasCompleto = new ArrayList<Oficina>();

		// Por cada Unidad, obtenemos sus Oficinas
		for (Unidad uni : unidades) {
			List<Oficina> oficinas = oficinaEjb.obtenerOficinasOrganismo(uni.getCodigo(), null, null);
			oficinasCompleto.addAll(oficinas);
		}

		Long end = System.currentTimeMillis();
		log.info("tiempo obtenerArbolOficinas: " + Utils.formatElapsedTime(end - start));
		return oficinasCompleto;
	}

	/**
	 * Se mira que si la unidad, su fecha de extinción y anulacion son posteriores a
	 * la fecha de la primera sincronizacion con regweb. Así evitamos enviar
	 * unidades que fueron extinguidas o anuladas antes de la primera sincronización
	 * con Madrid.
	 *
	 * @param unidad      unidad
	 * @param fechaSincro fecha de la primera sincronizacion con regweb
	 * @return
	 * @throws Exception
	 */
	public boolean unidadValida(Unidad unidad, Date fechaSincro) throws Exception {

		SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
		String sSincro = "";
		if (fechaSincro != null) {
			sSincro = fechaFormat.format(fechaSincro);
		}

		// Si tiene fecha de extinción
		if (unidad.getFechaExtincion() != null) {
			String sExtincion = fechaFormat.format(unidad.getFechaExtincion());
			// Si la fecha de extinción es posterior o igual a la fecha sincro, se debe
			// enviar a regweb
			if (unidad.getFechaExtincion().after(fechaSincro) || sExtincion.equals(sSincro)) {
				return true;
			}
		} else {
			// Si tiene fecha de anulación
			if (unidad.getFechaAnulacion() != null) {
				String sAnulacion = fechaFormat.format(unidad.getFechaAnulacion());
				// Si la fecha de anulación es posterior o igual a la fecha sincronizacion se
				// debe enviar a regweb
				if (unidad.getFechaAnulacion().after(fechaSincro) || sAnulacion.equals(sSincro)) {
					return true;
				}
			} else { // Si no tiene ni fecha de extincion ni de anulación, tambien se debe enviar a
						// regweb
				return true;
			}
		}
		return false;
	}

	/**
	 * Devuelve los Identificadores de todas las unidades existentes en base de
	 * datos
	 *
	 * @return
	 */
	@Override
	@SuppressWarnings(value = "unchecked")
	public List<String> getAllCodigos() {
		Query q = em.createQuery("Select unidad.codigo from Unidad as unidad order by unidad.codigo");

		return (List<String>) q.getResultList();
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public void crearUnidad(String codigoUnidad) throws Exception {

		Query q = em.createNativeQuery("insert into dir_unidad (codigo, esedp) values (?,?)");
		q.setParameter(1, codigoUnidad);
		q.setParameter(2, false);

		q.executeUpdate();
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public void actualizarUnidad(String codigoUnidad, String codigoUnidadRaiz, String codigoUnidadSuperior)
			throws Exception {

		Query q = em.createNativeQuery("update dir_unidad set codunidadraiz=?, codunidadsuperior=? where codigo=? ");
		q.setParameter(1, codigoUnidadRaiz);
		q.setParameter(2, codigoUnidadSuperior);
		q.setParameter(3, codigoUnidad);

		q.executeUpdate();
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public void crearHistoricoUnidad(String codigoAnterior, String codigoUltima) throws Exception {

		Query q = em.createNativeQuery("insert into dir_historicouo (codanterior, codultima) values (?,?) ");
		q.setParameter(1, codigoAnterior);
		q.setParameter(2, codigoUltima);

		q.executeUpdate();
	}

	@Override
	public void eliminarHistoricosUnidad(String idUnidad) throws Exception {

		Query q = em.createNativeQuery("delete from dir_historicouo where codultima=?");
		q.setParameter(1, idUnidad);

		q.executeUpdate();

	}


	@Override
	@SuppressWarnings(value = "unchecked")
	public Boolean existeHistoricoUnidad(String codigoAnterior, String codigoUltima) throws Exception {

		Query q = em.createNativeQuery("select * from dir_historicouo where codanterior = ? and codultima = ? ");
		q.setParameter(1, codigoAnterior);
		q.setParameter(2, codigoUltima);

		List<Object> historicos = q.getResultList();

		return historicos.size() > 0;
	}
	
	@Override
	@SuppressWarnings(value = "unchecked")
	public Set<Unidad> historicosHaciaAtras(String codigoUnidad) throws Exception {
		return historicosHaciaAtras(codigoUnidad, false);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public Set<Unidad> historicosHaciaAtras(String codigoUnidad, boolean denominacionCooficial) throws Exception {

		Query q = em.createQuery("select unidadAnterior.codigo from HistoricoUO where unidadUltima.codigo =:codigoUnidad ");

		q.setParameter("codigoUnidad", codigoUnidad);
		Set<Unidad> unidadesHistoricasAnteriores = new HashSet<Unidad>();

		List<String> historicos = q.getResultList();
		for (String historico : historicos) {
			Unidad unidad = findByCodigoLigero(historico, denominacionCooficial);
			unidadesHistoricasAnteriores.add(unidad);
		}

		return unidadesHistoricasAnteriores;
	}

	/**
	 * Función que obtiene los históricos finales vigentes de la unidad indicada
	 *
	 * @param unidad
	 * @param historicosFinales históricos finales vigentes encontrados.
	 * @throws Exception
	 */
	@Override
	public void historicosFinales(Unidad unidad, Set<Unidad> historicosFinales) throws Exception {

		Set<HistoricoUO> parciales = unidad.getHistoricosAnterior(); // Esto nos devuelve los historicos en que la
																		// unidad está en la columna anterior
		for (HistoricoUO parcial : parciales) {
			// Necesitamos cargas sus historicos finales para
			Unidad parcialUltima = findConHistoricos(parcial.getUnidadUltima().getCodigo());
			if (parcialUltima.getHistoricosAnterior().size() == 0) {
				historicosFinales.add(parcialUltima);
			} else {
				historicosFinales(parcialUltima, historicosFinales);
			}
		}

	}

	@Override
	public void historicosUOFinales(Unidad unidad, Set<HistoricoUO> historicosFinales) throws Exception {

		Set<HistoricoUO> parciales = unidad.getHistoricosAnterior(); // Esto nos devuelve los historicos en que la
		// unidad está en la columna anterior
		for (HistoricoUO parcial : parciales) {
			// Necesitamos cargas sus historicos finales para
			Unidad parcialUltima = findConHistoricos(parcial.getUnidadUltima().getCodigo());
			if (parcialUltima.getHistoricosAnterior().size() == 0) {
				historicosFinales.add(parcial);
			} else {
				historicosUOFinales(parcialUltima, historicosFinales);
			}
		}

	}

	public List<Unidad> getUnidadesByNivel(long nivel, String codigo, String estado) throws Exception{
		return getUnidadesByNivel(nivel,codigo,estado,false);
	}

	public List<Unidad> getUnidadesByNivel(long nivel, String codigo, String estado, boolean denominacionCooficial) throws Exception {

		Query q = em.createQuery(
				"Select unidad.codigo, unidad.denominacion, unidad.codUnidadRaiz.codigo, unidad.codUnidadSuperior.codigo, unidad.esEdp, unidad.denomLenguaCooficial "
				+ "from Unidad as unidad where unidad.nivelJerarquico = :nivel and unidad.codUnidadRaiz.codigo = :codigo "
				+ "and unidad.estado.codigoEstadoEntidad = :estado order by unidad.codigo");
		q.setParameter("nivel", nivel);
		q.setParameter("codigo", codigo);
		q.setParameter("estado", estado);

		List<Unidad> organismos = new ArrayList<Unidad>();
		List<Object[]> result = q.getResultList();

		for (Object[] object : result) {
			String denominacion = (denominacionCooficial && Utils.isNotEmpty((String) object[5])) ? (String) object[5] : (String) object[1];
			Unidad organismo = new Unidad((String) object[0], denominacion, (String) object[2],
					(String) object[3], (Boolean) object[4]);

			organismos.add(organismo);
		}

		return organismos;
	}

	@Override
	public List<Unidad> getUnidadesByNivelByUnidadSuperior(long nivel, String codigo, String estado) throws Exception {
		return getUnidadesByNivelByUnidadSuperior(nivel, codigo, estado, false);
	}

	@Override
	public List<Unidad> getUnidadesByNivelByUnidadSuperior(long nivel, String codigo, String estado, boolean denominacionCooficial) throws Exception {

		Query q = em.createQuery(
				"Select unidad.codigo, unidad.denominacion, unidad.codUnidadRaiz.codigo, unidad.codUnidadSuperior.codigo, unidad.esEdp, unidad.denomLenguaCooficial "
				+ "from Unidad as unidad where unidad.nivelJerarquico = :nivel and unidad.codUnidadSuperior.codigo = :codigo "
				+ "and unidad.estado.codigoEstadoEntidad = :estado order by unidad.codigo");
		q.setParameter("nivel", nivel);
		q.setParameter("codigo", codigo);
		q.setParameter("estado", estado);

		List<Unidad> organismos = new ArrayList<Unidad>();
		List<Object[]> result = q.getResultList();

		for (Object[] object : result) {
			String denominacion = (denominacionCooficial && Utils.isNotEmpty((String) object[5])) ? (String) object[5] : (String) object[1];
			Unidad organismo = new Unidad((String) object[0], denominacion, (String) object[2],
					(String) object[3], (Boolean) object[4]);

			organismos.add(organismo);
		}

		return organismos;
	}

	/**
	 * Obtiene una unidad con sus contactos y sus relaciones
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Unidad findFullById(String id) throws Exception {

		Unidad unidad = em.find(Unidad.class, id);

		if (unidad != null) {
			Hibernate.initialize(unidad.getOrganizativaOfi());
//            Hibernate.initialize(unidad.getSirOfi());
			Hibernate.initialize(unidad.getContactos());
		}

		return unidad;
	}

	/**
	 * Obtiene una unidad con sus contactos y sus relaciones
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	public Unidad findFullByIdConHistoricos(String codigo) throws Exception {

		Unidad unidad = findById(codigo);

		if (unidad != null) {
			Hibernate.initialize(unidad.getOrganizativaOfi());
//            Hibernate.initialize(unidad.getSirOfi());
            Hibernate.initialize(unidad.getContactos());
            Hibernate.initialize(unidad.getHistoricosAnterior());
            Hibernate.initialize(unidad.getHistoricosUltima());
            Hibernate.initialize(unidad.getServicios());
        }

		return unidad;
	}

	/**
	 * Obtiene una unidad con sus contactos y sus relaciones
	 *
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	public Unidad findFullByCodigoDir3ConHistoricos(String codigo) throws Exception {

		Unidad unidad = findByCodigoDir3UltimaVersion(codigo);

		if (unidad != null) {
			Hibernate.initialize(unidad.getOrganizativaOfi());
//            Hibernate.initialize(unidad.getSirOfi());
			Hibernate.initialize(unidad.getContactos());
			Hibernate.initialize(unidad.getHistoricosAnterior());
			Hibernate.initialize(unidad.getServicios());
		}

		return unidad;
	}

	/**
	 * Este método mira si la unidad del código especificado tiene oficinas donde
	 * registrar. Para ello comprueba si es unidadResponsable de alguna oficina y
	 * después mira si tiene relacionesOrganizativas con oficinas. Es además
	 * recursivo, así que lo mira hasta el último nivel del organigrama.
	 *
	 * @param codigo de la unidad
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean tieneOficinasArbol(String codigo) throws Exception {

		//log.info("tieneOficinasArbol codigo " + codigo);

		Query q = em.createQuery(
				"Select oficina.codigo from Oficina as oficina where oficina.codUoResponsable.codigo =:codigo and oficina.estado.codigoEstadoEntidad=:vigente");

		q.setParameter("codigo", codigo);
		q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		// transformamos los resultados de la query en Oficinas
		List<Oficina> oficinas = new ArrayList<Oficina>();
		List<?> result = q.getResultList();

		for (Object object : result) {
			oficinas.add(new Oficina((String) object));
		}

		//log.info(" tieneOficinasArbol OFICINAS " +oficinas.size());

		if (oficinas.size() > 0) {
			return true;
		} else {
			q = em.createQuery(
					"Select count(relorg.id) from RelacionOrganizativaOfi as relorg where relorg.unidad.codigo=:codigo and relorg.estado.codigoEstadoEntidad=:vigente");

			q.setParameter("codigo", codigo);
			q.setParameter("vigente", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
			Long relorg = (Long) q.getSingleResult();
			if (relorg > 0) {
				return true;
			} else {// no tiene oficinas, miramos sus hijos
				Query q2 = em.createQuery(
						"Select unidad.codigo from Unidad as unidad where unidad.codUnidadSuperior.codigo =:codigo and unidad.codigo !=:codigo and unidad.estado.codigoEstadoEntidad =:estado");

				q2.setParameter("codigo", codigo);
				q2.setParameter("estado", Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

				// Transformamos el resultado de la query en Unidades
				List<Unidad> hijos = new ArrayList<Unidad>();
				List<?> resulthijos = q2.getResultList();
				for (Object object : resulthijos) {
					hijos.add(new Unidad((String) object));
				}

				for (Unidad hijo : hijos) {
					//log.info( "tieneOficinasArbol " + hijo.getCodigo());
					boolean tiene = tieneOficinasArbol(hijo.getCodigo());
					if (tiene) {
						return true;
					}
					break;
				}
			}
		}
		return false;
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public void crearServicioUnidad(String codigoUnidad, Long codigoServicio) throws Exception {

		Query q = em.createNativeQuery("insert into dir_serviciouo (codunidad, codservicio) values (?,?) ");
		q.setParameter(1, codigoUnidad);
		q.setParameter(2, codigoServicio);

		q.executeUpdate();
	}

}
