package es.caib.dir3caib.back.controller.rest;

import es.caib.dir3caib.persistence.ejb.ArbolLocal;
import es.caib.dir3caib.persistence.ejb.Dir3RestLocal;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.json.OficinaJson;
import es.caib.dir3caib.persistence.model.json.OficinaRest;
import es.caib.dir3caib.persistence.model.json.PaisJson;
import es.caib.dir3caib.persistence.model.json.UnidadRest;
import es.caib.dir3caib.persistence.utils.CodigoValor;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.ObjetoDirectorio;
import es.caib.dir3caib.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import es.caib.dir3caib.back.controller.rest.RestUtils;
import es.caib.dir3caib.back.security.LoginInfo;

/**
 * Created 25/03/14 13:32
 * 
 * @author mgonzalez
 * @author jagarcia
 *
 *         Si s'afegeix o es modifica la firma d'un mètode REST s'ha
 *         d'actualitzar la corresponent firma al fitxer RestResource de dins el
 *         package swagger per tal d'actualitzar la documentació si s'utilitza
 *         SwaggerUI.
 *
 */
@Controller
@RequestMapping(value = "/rest")
public class RestController extends RestUtils {

	protected final Logger log = Logger.getLogger(getClass());

	@EJB(mappedName = "dir3caib/Dir3RestEJB/local")
	private Dir3RestLocal dir3RestEjb;

	@EJB(mappedName = "dir3caib/ArbolEJB/local")
	private ArbolLocal arbolEjb;

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.Unidad} por
	 * denominacion
	 */
	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/unidad/unidadesDenominacion", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ObjetoDirectorio>> unidadesPorDenominacion(
			@RequestParam String denominacion,
			@RequestParam(required = false, defaultValue = "false") boolean cooficial,
			@RequestParam(required = false, defaultValue = "") String estado) throws Exception {

		// Transformamos el campo denominacion de ISO a UTF-8 para realizar las
		// búsquedas en bd que estan en UTF-8.
		// Esto se hace porque el @RequestParam viene en ISO-8859-1.
		List<ObjetoDirectorio> resultado = dir3RestEjb.findUnidadesByDenominacion(
				new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), cooficial, estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<ObjetoDirectorio>>(resultado, headers, status);

	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.Oficina} por
	 * denominacion
	 */
	@RequestMapping(value = "/oficina/oficinasDenominacion", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ObjetoDirectorio>> oficinasPorDenominacion(
			@RequestParam String denominacion,
			@RequestParam(required = false, defaultValue = "false") boolean cooficial,
			@RequestParam(required = false, defaultValue = "") String estado) throws Exception {

		// Transformamos el campo denominacion de ISO a UTF-8 para realizar las
		// búsquedas en bd que estan en UTF-8.
		// Esto se hace porque el @RequestParam viene en ISO-8859-1.
		List<ObjetoDirectorio> resultado = dir3RestEjb.findOficinasByDenominacion(
				new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), cooficial, estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<ObjetoDirectorio>>(resultado, headers, status);

	}

	/**
	 * Obtiene el arbol de {@link es.caib.dir3caib.persistence.model.Unidad} del
	 * código indicado TODO Revisar si alguien lo emplea. REGWEB no (03/10/2017)
	 */
	@RequestMapping(value = "/unidad/arbolUnidades", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ObjetoDirectorio>> arbolUnidades(@RequestParam String codigo,
			@RequestParam(required = false, defaultValue = "false") boolean cooficial,
			@RequestParam(required = false, defaultValue = "") String estado) throws Exception {

		List<Unidad> resultado = dir3RestEjb.obtenerArbolUnidades(codigo, null, estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<ObjetoDirectorio>>(transformarUnidadAObjetoDirectorio(resultado, cooficial),
				headers, status);

	}

	/**
	 * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} del organismo
	 * indicado //TODO revisar si se emplea. REGWEB no (03/10/2017)
	 */
	@RequestMapping(value = "/oficina/oficinasOrganismo", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ObjetoDirectorio>> oficinasOrganismo(@RequestParam String codigo,
			@RequestParam(required = false, defaultValue = "false") boolean cooficial,
			@RequestParam(required = false, defaultValue = "") String estado) throws Exception {

		List<Oficina> resultado = dir3RestEjb.obtenerOficinasOrganismo(codigo, null, estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<ObjetoDirectorio>>(transformarOficinaAObjetoDirectorio(resultado, cooficial),
				headers, status); // TODO revisar denominacionCooficial

	}

	/**
	 * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} del organismo
	 * indicado
	 *
	 */
	@RequestMapping(value = "/GET/oficinas", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<OficinaJson>> getArbolOficinasOrganismoOpenData(
			@RequestParam String codigo, @RequestParam(defaultValue = "V") String estado) throws Exception {

		List<Oficina> resultado = dir3RestEjb.obtenerArbolOficinasOpenData(codigo, estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<OficinaJson>>(transformarAOficinaJson(resultado), headers, status);

	}

	/**
	 * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} del organismo
	 * indicado
	 */
	@RequestMapping(value = "/GET/oficinas/baleares", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<OficinaJson>> getOficinasBalearesOpenData(
			@RequestParam(defaultValue = "V") String estado) throws Exception {

		List<Oficina> resultado = dir3RestEjb.getOficinasBalearesOpenData(estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<OficinaJson>>(transformarAOficinaJson(resultado), headers, status);

	}

	/**
	 * Obtiene las {@link es.caib.dir3caib.persistence.model.Unidad} en función de
	 * los criterios de busqueda
	 */
	/*
	 * @RequestParam(value="vigentes", required=false) boolean vigentes ----- Esto
	 * es para definir parametros opcionales
	 */
	@RequestMapping(value = "/busqueda/organismos", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Nodo>> busquedaOrganismos(@RequestParam(required = false) String codigo,
			@RequestParam(required = false) String denominacion,
			@RequestParam(required = false) Long codNivelAdministracion,
			@RequestParam(required = false) Long codComunidadAutonoma,
			@RequestParam(required = false, defaultValue = "false") boolean conOficinas,
			@RequestParam(required = false) boolean unidadRaiz, @RequestParam(required = false) String provincia,
			@RequestParam(required = false) String localidad,
			@RequestParam(defaultValue = "true", required = false) boolean vigentes,
			@RequestParam(defaultValue = "true", required = false) boolean cooficial) throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();

		/*
		 * if (Utils.isEmpty(denominacion) && Utils.isEmpty(codigo)){ return new
		 * ResponseEntity<List<Nodo>>(null, headers, HttpStatus.BAD_REQUEST); }
		 */

		// Transformamos el campo denominacion de ISO a UTF-8 para realizar las
		// búsquedas en bd que estan en UTF-8.
		// Esto se hace porque el @RequestParam viene en ISO-8859-1.

		String deno = (Utils.isNotEmpty(denominacion)) ? new String(denominacion.getBytes("ISO-8859-1"), "UTF-8") : "";

		List<Nodo> resultado = dir3RestEjb.busquedaOrganismos(codigo, deno, codNivelAdministracion,
				codComunidadAutonoma, conOficinas, unidadRaiz,
				(Utils.isNumeric(provincia)) ? Long.parseLong(provincia) : null, localidad, vigentes, cooficial);

		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<Nodo>>(resultado, headers, status);
	}

	/**
	 * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} en función de
	 * los criterios de busqueda
	 */
	@RequestMapping(value = "/busqueda/oficinas", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Nodo>> busquedaOficinas(
			@RequestParam(required = false, defaultValue = "") String codigo,
			@RequestParam(required = false, defaultValue = "") String denominacion,
			@RequestParam(required = false, defaultValue = "-1") Long codNivelAdministracion,
			@RequestParam(required = false, defaultValue = "") Long codComunidadAutonoma,
			@RequestParam(required = false, defaultValue = "-1") Long provincia,
			@RequestParam(required = false, defaultValue = "") String localidad,
			@RequestParam(required = false, defaultValue = "false") boolean oficinasSir,
			@RequestParam(required = false, defaultValue = "false") boolean vigentes,
			@RequestParam(required = false, defaultValue = "true") boolean cooficial) throws Exception {

		// Transformamos el campo denominacion de ISO a UTF-8 para realizar las
		// búsquedas en bd que estan en UTF-8.
		// Esto se hace porque el @RequestParam viene en ISO-8859-1.

		List<Nodo> resultado = dir3RestEjb.busquedaOficinas(codigo,
				new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codNivelAdministracion, codComunidadAutonoma,
				provincia, localidad, oficinasSir, vigentes, cooficial);
		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<Nodo>>(resultado, headers, status);
	}

	/**
	 * Obtiene la denominacion de una Unidad (Empleado por REGWEB3)
	 */
	@RequestMapping(value = "/unidad/denominacion", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> unidadDenominacion(@RequestParam String codigo,
			@RequestParam(defaultValue = "false") boolean cooficial, @RequestParam(defaultValue = "") String estado)
			throws Exception {

		String denominacion = dir3RestEjb.unidadDenominacion(codigo, cooficial, estado);
		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (!denominacion.isEmpty()) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<String>(denominacion, headers, status);

	}

	/**
	 * Obtiene el estado de una Unidad (Empleado por REGWEB3)
	 */
	@RequestMapping(value = "/GET/unidad/estado", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> unidadEstado(@RequestParam String codigo) throws Exception {

		String estado = dir3RestEjb.unidadEstado(codigo);
		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (!estado.isEmpty()) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<String>(estado, headers, status);

	}

	/**
	 * Obtiene la denominacion de una Unidad (Empleado por REGWEB3)
	 */
	@RequestMapping(value = "/oficina/denominacion", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> oficinaDenominacion(@RequestParam String codigo,
			@RequestParam(defaultValue = "false") boolean cooficial, @RequestParam(defaultValue = "") String estado)
			throws Exception {

		String denominacion = dir3RestEjb.oficinaDenominacion(codigo, cooficial, estado);
		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (!denominacion.isEmpty()) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<String>(denominacion, headers, status);
	}

	/**
	 * Obtiene el organigrama solo de organismos sin oficinas a partir del codigo
	 * especificado, pero muestra sus ascendentes y sus descendientes Es el que se
	 * emplea para mostrar el árbol en la búsqueda de organismos destinatarios de
	 * regweb3(Lo emplea Regweb3)
	 *
	 * @param codigo el código raiz del que partimos TODO pendiente denominacion.
	 *               Cambiar select para codigodir3 en vez de codigo interno
	 */
	@RequestMapping(value = "/organigrama", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Nodo> organigrama(@RequestParam String codigo,
			@RequestParam(defaultValue = "false") boolean cooficial) throws Exception {

		Nodo nodo = new Nodo();
		arbolEjb.arbolUnidadesAscendentes(codigo, nodo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE, false, cooficial);

		nodo = revisarOrganigrama(nodo);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (nodo.getCodigo() != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<Nodo>(nodo, headers, status);

	}

	private Nodo revisarOrganigrama(Nodo arbol) throws Exception {

		for (Nodo n : arbol.getHijos()) {
			revisarOrganigrama(n);
		}

		String codigo = arbol.getCodigo();
		int posCodigo = (codigo != null) ? codigo.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION) : -1;
		arbol.setCodigo((posCodigo > 0) ? codigo.substring(0, posCodigo) : codigo);

		String codigoIdPadre = arbol.getIdPadre();
		int posIdPadre = (codigoIdPadre != null) ? codigoIdPadre.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION)
				: -1;
		arbol.setIdPadre((posIdPadre > 0) ? codigoIdPadre.substring(0, posIdPadre) : codigoIdPadre);

		String raiz = arbol.getRaiz();
		int raizPosBlanco = raiz.indexOf(" ");
		int raizPosSeparador = raiz.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION);
		String raizCodigo = (raizPosSeparador > 0) ? raiz.substring(0, raizPosSeparador) : "";
		String raizNombre = (raizPosBlanco > 0) ? raiz.substring(raizPosBlanco) : raiz;
		arbol.setRaiz(raizCodigo + raizNombre);

		String superior = arbol.getSuperior();
		int superiorBlanco = superior.indexOf(" ");
		int superiorCodigoSeparador = superior.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION);
		String superiorCodigo = (superiorCodigoSeparador > 0) ? superior.substring(0, superiorCodigoSeparador) : "";
		String superiorNombre = (superiorBlanco > 0) ? superior.substring(superiorBlanco) : superior;
		arbol.setSuperior(superiorCodigo + superiorNombre);

		return arbol;

	}

	//
	// Métodos de catàlogo para Helium(CMAIB)
	//

	/**
	 * Método que realiza la busqueda de unidades por denominación y comunidad
	 * autónoma para utilidad HELIUM
	 *
	 * @param denominacion denominación de la unidad
	 * @param codComunidad codigo de la comunidad
	 * @return List<Nodo> listado de objetos nodo con el código,denominación,
	 *         denominación de unidad raiz y denominación de unidad superior
	 */
	@RequestMapping(value = "/busqueda/unidades/denominacion/comunidad", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Nodo>> obtenerUnidadesDenominacionComunidad(
			@RequestParam(required = false, defaultValue = "") String denominacion, @RequestParam Long codComunidad,
			@RequestParam(defaultValue = "false") boolean cooficial) throws Exception {
		List<Nodo> resultado = new ArrayList<Nodo>();

		if (codComunidad != null) {
			resultado = dir3RestEjb.busquedaDenominacionComunidad(
					new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codComunidad, cooficial);
		}

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<Nodo>>(resultado, headers, status);

	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.CatAmbitoTerritorial}
	 * del nivel administracion seleccionado Se emplea en unidadList.jsp
	 */
	@RequestMapping(value = "/catalogo/ambitosTerritoriales", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<CodigoValor>> ambitosTerritoriales(@RequestParam Long id,
			@RequestParam(defaultValue = "") String estado) throws Exception {

		List<CodigoValor> resultado = dir3RestEjb.getAmbitoTerritorialByAdministracion(id, estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
	}

	/**
	 * Obtiene todas las comunidades autónomas
	 * {@link es.caib.dir3caib.persistence.model.CatComunidadAutonoma}
	 */
	@RequestMapping(value = "/catalogo/comunidadesAutonomas", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<CodigoValor>> comunidadesAutonomas(
			@RequestParam(defaultValue = "") String estado) throws Exception {

		List<CodigoValor> resultado = dir3RestEjb.getComunidadesAutonomas(estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.CatEntidadGeografica}
	 */
	@RequestMapping(value = "/catalogo/entidadesGeograficas", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<CodigoValor>> entidadesGeograficas(
			@RequestParam(defaultValue = "") String estado) throws Exception {

		List<CodigoValor> resultado = dir3RestEjb.getEntidadesGeograficas(estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.CatProvincia}
	 */
	@RequestMapping(value = "/catalogo/provincias", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<CodigoValor>> provincias(@RequestParam(defaultValue = "") String estado)
			throws Exception {

		List<CodigoValor> resultado = dir3RestEjb.getProvincias(estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.CatProvincia} por
	 * comunidad autonoma
	 */
	@RequestMapping(value = "/catalogo/provincias/comunidadAutonoma", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<CodigoValor>> provinciasCA(@RequestParam Long id,
			@RequestParam(defaultValue = "") String estado) throws Exception {

		List<CodigoValor> resultado = dir3RestEjb.getProvinciasByComunidad(id, estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.CatLocalidad} por
	 * provincia y entidad geografica
	 */
	@RequestMapping(value = "/catalogo/localidades/provincia/entidadGeografica", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<CodigoValor>> localidades(@RequestParam Long codigoProvincia,
			String codigoEntidadGeografica, @RequestParam(defaultValue = "") String estado) throws Exception {
		
		List<CodigoValor> resultado = dir3RestEjb.getLocalidadByProvinciaEntidadGeografica(codigoProvincia,
				codigoEntidadGeografica, estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.CatNivelAdministracion}
	 */
	@RequestMapping(value = "/catalogo/nivelesAdministracion", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<CodigoValor>> nivelesAdministracion(
			@RequestParam(defaultValue = "") String estado) throws Exception {

		List<CodigoValor> resultado = dir3RestEjb.getNivelesAdministracion(estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		// Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay
		// resultados.
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.CatEstadoEntidad}
	 */
	@RequestMapping(value = "/catalogo/estados", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<CodigoValor>> estadosEntidad(
			@RequestParam(defaultValue = "") String estado) throws Exception {

		List<CodigoValor> resultado = dir3RestEjb.getEstadosEntidad(estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.CatTipoVia}
	 */
	@RequestMapping(value = "/catalogo/tiposvia", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<CodigoValor>> tiposVia(@RequestParam(defaultValue = "") String estado)
			throws Exception {

		List<CodigoValor> resultado = dir3RestEjb.getTiposVia(estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
	}

	/**
	 * Obtiene los {@link es.caib.dir3caib.persistence.model.CatPais}
	 */
	@RequestMapping(value = "/catalogo/paises", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<PaisJson>> paises(@RequestParam(defaultValue = "") String estado)
			throws Exception {

		List<CatPais> resultado = dir3RestEjb.getPaises(estado);

		HttpHeaders headers = addAccessControllAllowOrigin();
		HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<PaisJson>>(tranformarAPaisJson(resultado), headers, status);
	}

	/*
	 *
	 * Date Format yyyy-MM-dd — for example, "2000-10-31".
	 */
	@RolesAllowed({ Dir3caibConstantes.DIR_ADMIN })
	@RequestMapping(value = "/oficinas/obtenerOficina", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<OficinaRest> obtenerOficina(HttpServletRequest request,
			@RequestParam String codigo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaActualizacion,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaSincronizacion,
			@RequestParam(required = false, defaultValue = "false") boolean denominacionCooficial) throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();

		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<OficinaRest>(null, headers, HttpStatus.UNAUTHORIZED);
		}

		OficinaRest resultado = dir3RestEjb.obtenerOficina(codigo, fechaActualizacion, fechaSincronizacion,
				denominacionCooficial, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

		HttpStatus status = (resultado != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<OficinaRest>(resultado, headers, status);

	}

	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/oficinas/obtenerArbolOficinas", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<OficinaRest>> obtenerArbolOficinas(HttpServletRequest request,
			@RequestParam String codigo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaActualizacion,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaSincronizacion,
			@RequestParam(required = false, defaultValue = "false") boolean denominacionCooficial) throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();
		
		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<List<OficinaRest>>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		
		List<OficinaRest> resultados = dir3RestEjb.obtenerArbolOficinas(codigo, fechaActualizacion, fechaSincronizacion,
				denominacionCooficial);

		HttpStatus status = (resultados.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<OficinaRest>>(resultados, headers, status);

	}

	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/oficinas/obtenerOficinasSIRUnidad", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<OficinaRest>> obtenerOficinasSIRUnidad( HttpServletRequest request,
			@RequestParam String codigo,
			@RequestParam(defaultValue = "false") boolean denominacionCooficial) throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();
		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<List<OficinaRest>>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		
		List<OficinaRest> resultados = dir3RestEjb.obtenerOficinasSIRUnidad(codigo, denominacionCooficial);
		
		HttpStatus status = (resultados.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<OficinaRest>>(resultados, headers, status);

	}

	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/sincronizacion/fechaUltimaActualizacion", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Date> obtenerFechaUltimaActualizacion(HttpServletRequest request) throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();
		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<Date>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		
		Date resultado = dir3RestEjb.obtenerFechaUltimaActualizacion();

		
		HttpStatus status = (resultado != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<Date>(resultado, headers, status);

	}

	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/unidades/obtenerUnidad", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<UnidadRest> obtenerUnidad( HttpServletRequest request,
			@RequestParam String codigo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaActualizacion,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaSincronizacion,
			@RequestParam(required = false, defaultValue = "false") boolean denominacionCooficial) throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();
		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<UnidadRest>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		
		UnidadRest resultado = dir3RestEjb.obtenerUnidad(codigo, fechaActualizacion, fechaSincronizacion,
				denominacionCooficial);

		HttpStatus status = (resultado != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<UnidadRest>(resultado, headers, status);

	}

	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/unidades/buscarUnidad", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<UnidadRest> obtenerUnidad( HttpServletRequest request,
			@RequestParam String codigo,
			@RequestParam(defaultValue = "false") boolean denominacionCooficial) throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();
		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<UnidadRest>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		
		UnidadRest resultado = dir3RestEjb.buscarUnidad(codigo, denominacionCooficial);

		HttpStatus status = (resultado != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<UnidadRest>(resultado, headers, status);

	}

	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/unidades/obtenerArbolUnidades", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<UnidadRest>> obtenerArbolUnidades( HttpServletRequest request,
			@RequestParam String codigo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaActualizacion,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaSincronizacion,
			@RequestParam(required = false, defaultValue = "false") boolean denominacionCooficial) throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();
		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<List<UnidadRest>>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		
		List<UnidadRest> resultados = dir3RestEjb.obtenerArbolUnidades(codigo, fechaActualizacion, fechaSincronizacion,
				denominacionCooficial);

		HttpStatus status = (resultados.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<UnidadRest>>(resultados, headers, status);

	}

	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/unidades/obtenerArbolUnidadesDestinatarias", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<UnidadRest>> obtenerArbolUnidadesDestinatarias( HttpServletRequest request,
			@RequestParam String codigo,
			@RequestParam(defaultValue = "false") boolean denominacionCooficial) throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();
		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<List<UnidadRest>>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		
		List<UnidadRest> resultados = dir3RestEjb.obtenerArbolUnidadesDestinatarias(codigo, denominacionCooficial);
		
		HttpStatus status = (resultados.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<UnidadRest>>(resultados, headers, status);

	}

	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/unidades/obtenerHistoricosFinales", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<UnidadRest>> obtenerHistoricosFinales( HttpServletRequest request,
			@RequestParam String codigo, @RequestParam(defaultValue = "false") boolean denominacionCooficial) 
					throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();
		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<List<UnidadRest>>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		
		List<UnidadRest> resultados = dir3RestEjb.obtenerHistoricosFinales(codigo, denominacionCooficial);

		HttpStatus status = (resultados.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<UnidadRest>>(resultados, headers, status);

	}

	@RolesAllowed({ Dir3caibConstantes.DIR_WS })
	@RequestMapping(value = "/unidades/obtenerHistoricosFinalesSIR", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<UnidadRest>> obtenerHistoricosFinalesSIR( HttpServletRequest request,
			@RequestParam String codigo, @RequestParam(defaultValue = "false") boolean denominacionCooficial) 
					throws Exception {

		HttpHeaders headers = addAccessControllAllowOrigin();
		String error = autenticateUsrApp(request, Arrays.asList(Dir3caibConstantes.DIR_WS));
		if (error != null) {
			return new ResponseEntity<List<UnidadRest>>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		
		List<UnidadRest> resultados = dir3RestEjb.obtenerHistoricosFinalesSIR(codigo, denominacionCooficial);

		HttpStatus status = (resultados.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<UnidadRest>>(resultados, headers, status);

	}

	public HttpHeaders addAccessControllAllowOrigin() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		return headers;
	}

	/**
	 * Método que transforma los resultados de una lista de Unidad en una lista de
	 * ObjetoDirectorio TODO debe devolver el código interno o el codigoDir3?
	 *
	 * @param resultados
	 * @return
	 */
	private List<ObjetoDirectorio> transformarUnidadAObjetoDirectorio(List<Unidad> resultados,
			boolean denominacionCooficial) {
		List<ObjetoDirectorio> objetoDirectorios = new ArrayList<ObjetoDirectorio>();
		for (Unidad unidad : resultados) {
			ObjetoDirectorio objetoDirectorio = new ObjetoDirectorio();
			objetoDirectorio.setCodigo(unidad.getCodigoDir3());
			objetoDirectorio
					.setDenominacion((denominacionCooficial && Utils.isNotEmpty(unidad.getDenomLenguaCooficial()))
							? unidad.getDenomLenguaCooficial()
							: unidad.getDenominacion());
			objetoDirectorios.add(objetoDirectorio);
		}

		return objetoDirectorios;

	}

	/**
	 * Método que transforma los resultados de una lista de Oficina en una lista de
	 * ObjetoDirectorio
	 *
	 * @param resultados
	 * @return
	 */
	private List<ObjetoDirectorio> transformarOficinaAObjetoDirectorio(List<Oficina> resultados,
			boolean denominacionCooficial) {
		List<ObjetoDirectorio> objetoDirectorios = new ArrayList<ObjetoDirectorio>();
		for (Oficina oficina : resultados) {
			ObjetoDirectorio objetoDirectorio = new ObjetoDirectorio();
			objetoDirectorio.setCodigo(oficina.getCodigo());
			objetoDirectorio
					.setDenominacion((denominacionCooficial && Utils.isNotEmpty(oficina.getDenomLenguaCooficial()))
							? oficina.getDenomLenguaCooficial()
							: oficina.getDenominacion());
			objetoDirectorios.add(objetoDirectorio);
		}

		return objetoDirectorios;

	}

	/*
	 * Método que transforma los resultados de una lista de Paises en una lista de
	 * PaisesJson
	 */
	private List<PaisJson> tranformarAPaisJson(List<CatPais> resultados) {

		List<PaisJson> paisesJson = new ArrayList<PaisJson>();
		for (CatPais pais : resultados) {
			PaisJson paisJson = new PaisJson();
			paisJson.setCodigoPais(String.valueOf(pais.getCodigoPais()));
			paisJson.setDescripcionPais(pais.getDescripcionPais());
			paisJson.setAlfa2Pais(pais.getAlfa2Pais());
			paisJson.setAlfa3Pais(pais.getAlfa3Pais());
			paisJson.setEstado(pais.getEstado().getCodigoEstadoEntidad());
			paisesJson.add(paisJson);
		}
		return paisesJson;
	}

	/*
	 * Método que transforma los resultados de una lista de Oficina en una lista de
	 * OficinaJson
	 */
	private List<OficinaJson> transformarAOficinaJson(List<Oficina> resultados) {
		List<OficinaJson> oficinaJsons = new ArrayList<OficinaJson>();
		for (Oficina ofi : resultados) {
			OficinaJson oficinaJson = new OficinaJson();
			oficinaJson.setCodigoDir3(ofi.getCodigo());
			oficinaJson.setDenominacion(ofi.getDenominacion());
			oficinaJson.setDenominacionCooficial(ofi.getDenomLenguaCooficial());
			oficinaJson.setEstado(ofi.getEstado().getDescripcionEstadoEntidad());
			oficinaJson.setNivelAdministracion(ofi.getNivelAdministracion().getDescripcionNivelAdministracion());

			oficinaJson.setTipoOficina(ofi.getTipoOficina().getDescripcionJerarquiaOficina()); // CatJerarquiaOficina
			oficinaJson.setUnidadResponsable(ofi.getCodUoResponsable().getDenominacion());// Unidad
			if (ofi.getCodOfiResponsable() != null) {
				oficinaJson.setOficinaResponsable(ofi.getCodOfiResponsable().getDenominacion()); // Oficina
			}
			oficinaJson.setHorarioAtencion(ofi.getHorarioAtencion());
			oficinaJson.setDiasSinHabiles(ofi.getDiasSinHabiles());
			oficinaJson.setObservaciones(ofi.getObservaciones());
			if (ofi.getFechaAltaOficial() != null) {
				oficinaJson.setFechaAltaOficial(ofi.getFechaAltaOficial().toString());
			}
			if (ofi.getFechaAnulacion() != null) {
				oficinaJson.setFechaAnulacion(ofi.getFechaAnulacion().toString());
			}
			if (ofi.getFechaExtincion() != null) {
				oficinaJson.setFechaExtincion(ofi.getFechaExtincion().toString());
			}
			if (ofi.getCodPais() != null) {
				oficinaJson.setPais(ofi.getCodPais().getDescripcionPais());
			}
			if (ofi.getCodComunidad() != null) {
				oficinaJson.setComunidad(ofi.getCodComunidad().getDescripcionComunidad());
			}
			if (ofi.getLocalidad() != null) {
				oficinaJson.setMunicipio(ofi.getLocalidad().getDescripcionLocalidad()); // LOCALIDAD
			}

			oficinaJson.setNombreVia(ofi.getNombreVia());
			oficinaJson.setNumeroVia(ofi.getNumVia());
			if (ofi.getTipoVia() != null) {
				oficinaJson.setTipoVia(ofi.getTipoVia().getDescripcionTipoVia());
			}
			oficinaJson.setCodigoPostal(ofi.getCodPostal());

			// Montamos los servicios como una lista de strings
			List<String> servicios = new ArrayList<String>();

			// Servicios
			for (ServicioOfi serv : ofi.getServicios()) {
				servicios.add(serv.getServicio().getDescServicio());
			}

			List<String> contactos = new ArrayList<String>();
			for (ContactoOfi contactoOfi : ofi.getContactos()) {
				contactos.add(contactoOfi.getTipoContacto().getDescripcionTipoContacto() + " : "
						+ contactoOfi.getValorContacto());
			}

			oficinaJson.setServicios(servicios);
			oficinaJson.setContactos(contactos);

			oficinaJsons.add(oficinaJson);
		}

		return oficinaJsons;

	}
}
