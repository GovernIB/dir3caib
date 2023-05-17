package es.caib.dir3caib.back.controller.rest.swagger;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import es.caib.dir3caib.back.controller.rest.SistraResponse;
import es.caib.dir3caib.persistence.model.json.OficinaRest;
import es.caib.dir3caib.persistence.model.json.UnidadRest;
import es.caib.dir3caib.persistence.model.json.UnidadRestExtendido;
import es.caib.dir3caib.persistence.utils.ObjetoDirectorioExtendido;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

/**
 * Aquesta classe nomès s'utilitza per generar la documentació de Swagger a
 * partir de les anotacions de OpenAPI. Per això els mètodes no tenen
 * implementació.
 *
 * Quan es modifica la firma de qualque mètode del fitxer RestController s'ha de
 * tenir en compte aquí.
 *
 * @author jagarcia
 *
 */

@OpenAPIDefinition(info = @Info(title = "API REST de Dir3Caib", description = "", version = "1.0.0", license = @License(name = "License Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"), contact = @Contact(name = "Departament de Govern Digital a la Fundació BIT", email = "governdigital@fundaciobit.org", url = "http://otae.fundaciobit.org")), servers = {
		@Server(url = "/dir3caib/rest/") })
@SecurityScheme(type = SecuritySchemeType.HTTP, securitySchemeName = "BasicAuth", scheme = "basic")
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class RestResource {

	@Operation(operationId = "unidadesPorDenominacion", summary = "Servei per cercar unitats que contenguin la denominació indicada")

	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de ObjetoDirectorio en format JSON", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidad/unidadesDenominacion")
	public Response unidadesPorDenominacion(

			@Parameter(description = "denominació de l'organisme que cercam", required = true, example = "salut", schema = @Schema(implementation = String.class)) @QueryParam("denominacion") String denominacion,

			@Parameter(description = "booleà per indicar que ens retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = Boolean.class)) @DefaultValue("true") @QueryParam("cooficial") boolean cooficial,

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna els resultats amb estat vigent (V)", required = false, example = "V", schema = @Schema(implementation = String.class)) @DefaultValue("") @QueryParam("estado") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	@Operation(operationId = "oficinasPorDenominacion", summary = "Servei per cercar les oficines que contenguin la denominació indicada")

	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de ObjetoDirectorio en format JSON. ", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/oficina/oficinasDenominacion")
	public Response oficinasPorDenominacion(

			@Parameter(description = "denominació de l'oficina que cercam", required = true, example = "salut", schema = @Schema(implementation = String.class)) @QueryParam("denominacion") String denominacion,

			@Parameter(description = "booleà per indicar que ens retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("cooficial") @DefaultValue("true") boolean cooficial,

			@Parameter(description = "filtre dels resultats per codi d'estat. Si no s'informa, ens retorna els resultats amb estat vigent (V)", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.ok().status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */

	@Operation(operationId = "arbolUnidades", summary = "Servei que obté l'arbre d'organismes/unitats del codi indicat")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de ObjetoDirectorio en format JSON", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidad/arbolUnidades")
	public Response arbolUnidades(

			@Parameter(description = "codi de l'organisme arrel del que volem obtenir el seu arbre ", required = true, example = "E04096003", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "booleà per indicar que ens retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("cooficial") @DefaultValue("true") boolean cooficial,

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna els resultats amb estat vigent (V)", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.ok().status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */

	@Operation(operationId = "oficinasOrganismo", summary = "Servei que obté les oficines que depenen de l'organisme del codi indicat")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de ObjetoDirectorio en format JSON ", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/oficina/oficinasOrganismo")
	public Response oficinasOrganismo(

			@Parameter(description = "codi de l'organisme del que volem obtenir les seves oficines", required = true, example = "E00145001", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "booleà per indicar que ens retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("cooficial") @DefaultValue("true") boolean cooficial,

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna els resultats amb estat vigent (V)", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.ok().status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */

	@Operation(operationId = "busquedaOrganismos", summary = "Servei que permet fer una cerca d'organismes dins dir3 en funció dels paràmetres indicats. S'empra per cercar organismes externs destinataris quan es fa un registre d'entrada/sortida")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes de tipus Nodo en format JSON", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/busqueda/organismos")
	public Response busquedaOrganismos(

			@Parameter(description = "codi de l'organisme que cercam", required = false, example = "A04059084", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "denominació de l'organisme que cercam", required = false, example = "fundació", schema = @Schema(implementation = String.class)) @QueryParam("denominacion") String denominacion,

			@Parameter(description = "codi del nivell d'administració dels organismes que cercam", required = false, example = "2", schema = @Schema(implementation = Long.class)) @QueryParam("codNivelAdministracion") Long codNivelAdministracion,

			@Parameter(description = "codi de la comunitat autònoma per la qual cercam", required = false, example = "4", schema = @Schema(implementation = Long.class)) @QueryParam("codComunidadAutonoma") Long codComunidadAutonoma,

			@Parameter(description = "indica si volem que només es tornin els organismes que tenguin oficines", required = false, example = "false", schema = @Schema(implementation = boolean.class)) @QueryParam("conOficinas") boolean conOficinas,

			@Parameter(description = "indica si només volem que es retornin els organismes arrels", required = false, example = "false", schema = @Schema(implementation = boolean.class)) @QueryParam("unidadRaiz") boolean unidadRaiz,

			@Parameter(description = "indica el codi de província dels organismes que cercam", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("provincia") Long provincia,

			@Parameter(description = "indica el codi de localitat dels organismes que cercam", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("localidad") String localidad,

			@Parameter(description = "indica si només volem aquells organismes que són vigents", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("vigentes") @DefaultValue("true") boolean vigentes,

			@Parameter(description = "indica si volem que es retornin les denominacions cooficials si existeixen. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("cooficial") @DefaultValue("true") boolean cooficial

	) {

		return Response.ok().status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */

	@Operation(operationId = "busquedaOficinas", summary = "Servei per fer una cerca d'oficines origen dins dir3 en funció dels paràmetres indicats. S'empra per cercar oficines d'origen externes quan es fa un registre d'entrada/sortida")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes Nodo en format json", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/busqueda/oficinas")
	public Response busquedaOficinas(

			@Parameter(description = "codi de  l'oficina que cercam", required = false, example = "O00001641", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "denominació de l'oficina que cercam", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("denominacion") String denominacion,

			@Parameter(description = "codi del nivell d'administració de les oficines que cercam", required = false, example = "3", schema = @Schema(implementation = Long.class)) @QueryParam("codNivelAdministracion") Long codNivelAdministracion,

			@Parameter(description = "codi de la comunitat autònoma per la qual cercam", required = false, example = "4", schema = @Schema(implementation = Long.class)) @QueryParam("codComunidadAutonoma") Long codComunidadAutonoma,

			@Parameter(description = "indica el codi de la provincia de les oficines que cercam", required = false, example = "7", schema = @Schema(implementation = Long.class)) @QueryParam("provincia") Long provincia,

			@Parameter(description = "indica el codi de la localitat de les oficines que cercam", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("localidad") String localidad,

			@Parameter(description = "indica si només volem aquelles oficines que estan integrades en SIR", required = false, example = "false", schema = @Schema(implementation = boolean.class)) @QueryParam("oficinasSir") boolean oficinasSir,

			@Parameter(description = "indica si només volem aquelles oficines que són vigents", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("vigentes") boolean vigentes,

			@Parameter(description = "indica si volem que es retornin les denominacions cooficials si existeixen. Per defecte, no es retornen.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("cooficial") @DefaultValue("false") boolean cooficial

	) {

		return Response.ok().status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */

	@Operation(operationId = "unidadDenominacion", summary = "Servei per obtenir la denominació d'una unitat a partir del seu codi Dir3")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Cadena de text amb la denominació de la unitat", content = @Content(mediaType = MediaType.TEXT_PLAIN, schema = @Schema(implementation = String.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidad/denominacion")
	public Response unidadDenominacion(

			@Parameter(description = "codi de  la unitat que cercam", required = true, example = "L01502973", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("cooficial") @DefaultValue("true") boolean cooficial,

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna els resultats amb estat vigent (V)", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.ok().status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */

	@Operation(operationId = "oficinaDenominacion", summary = "Servei per obtenir la denominació d'una oficina a partir del seu codi Dir3")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Cadena de text amb la denominació de la oficina", content = @Content(mediaType = MediaType.TEXT_PLAIN, schema = @Schema(implementation = String.class))) })

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/oficina/denominacion")
	public Response oficinaDenominacion(

			@Parameter(description = "codi de  la oficina que cercam", required = true, example = "O00016615", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("cooficial") @DefaultValue("true") boolean cooficial,

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna els resultats amb estat vigent (V)", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */
	@Operation(operationId = "organigrama", summary = "Servei per obté l'arbre d'organismes/unitats del codi indicat pero la estructura que monta es un conjunt d'informació reduida.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes Nodo amb un subconjunt d'informació de l'organisme.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/organigrama")
	public Response organigrama(

			@Parameter(description = "codi de l'organisme arrel del que volem obtenir el seu arbre", required = true, example = "A04031575", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "booleà per indicar que ens retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("cooficial") @DefaultValue("true") boolean cooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */

	@Operation(operationId = "unidadEstado", summary = "Servei per obtenir el codi de l'estat d'una unitat determinada")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de unitats orgàniques ", content = @Content(mediaType = MediaType.TEXT_PLAIN, schema = @Schema(implementation = String.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/GET/unidad/estado")
	public Response unidadEstado(

			@Parameter(description = "codi de la unitat que es vol consultar el seu estat", required = true, example = "L01502973", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */

	@Operation(operationId = "obtenerUnidadesDenominacionComunidad", summary = "Servei per permet cercar unitats per denominació i comunitat autònoma")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes Nodo en format JSON", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/busqueda/unidades/denominacion/comunidad")
	public Response obtenerUnidadesDenominacionComunidad(
			@Parameter(description = "denominació de l'organisme que cercam", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("denominacion") String denominacion,

			@Parameter(description = "codi de la comunitat autònoma a la que volem cercar", required = true, example = "7", schema = @Schema(implementation = Long.class)) @QueryParam("codComunidad") Long codComunidad,

			@Parameter(description = "booleà per indicar que ens retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("cooficial") @DefaultValue("true") boolean cooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */

	@Operation(operationId = "getArbolOficinasOrganismoOpenData", summary = "Servei que permet obtenir totes les oficines del organisme indicat per paràmetre")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes OficinaJson en format JSON", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/GET/oficinas")
	public Response getArbolOficinasOrganismoOpenData(

			@Parameter(description = "codi de la unitat que volem obtenir les seves oficines", required = true, example = "L01502973", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna els resultats amb estat vigent (V)", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("V") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */
	@Operation(operationId = "getOficinasBalearesOpenData", summary = "Servei que permet obtenir totes les oficines, vigents si no s’indica un altre estat, de Balears.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes OficinaJson", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/GET/oficinas/baleares")
	public Response getOficinasBalearesOpenData(

			@Parameter(description = "filtre per codi d'estat. Si no s'informa, ens retorna els resultats amb estat vigent (V)", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("V") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */
	@Operation(operationId = "ambitosTerritoriales", summary = "Servei que permet obtenir els àmbits territorials del nivell d'administració indicat ")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <CodigoValor> que representa els àmbits territorials del catàleg", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/ambitosTerritoriales")
	public Response ambitosTerritoriales(

			@Parameter(description = "identificador del nivell d'administració", required = true, example = "2", schema = @Schema(implementation = Long.class)) @QueryParam("id") Long id,

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	/*
	 * 
	 */
	@Operation(operationId = "comunidadesAutonomas", summary = "Servei que permet obtenir totes les comunitats autònomes")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <CodigoValor> que representa les comunitats autonòmes del catàleg", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/comunidadesAutonomas")
	public Response comunidadesAutonomas(

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */
	@Operation(operationId = "entidadesGeograficas", summary = "Servei per permet obtenir totes les entitats geogràfiques")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <CodigoValor> que representa les entitats geogràfiques del catàleg ", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/entidadesGeograficas")
	public Response entidadesGeograficas(

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	/*
	 * 
	 */
	@Operation(operationId = "provincias", summary = "Servei per permet obtenir totes les provincies")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <CodigoValor> que representa les províncies del catàleg", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/provincias")
	public Response provincias(

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 *
	 */
	@Operation(operationId = "provinciasCA", summary = "Servei per permet obtenir totes les provincies de la comunitat autònoma indicada per paràmetre.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <CodigoValor> que representa les províncies del catàleg", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/provincias/comunidadAutonoma")
	public Response provinciasCA(

			@Parameter(description = "codi de la comunitat autònoma", required = true, example = "7", schema = @Schema(implementation = Long.class)) @QueryParam("id") Long id,

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */
	@Operation(operationId = "provinciasCA", summary = "Servei per permet obtenir totes les provincies de la comunitat autònoma indicada per paràmetre.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <CodigoValor> que representa les províncies del catàleg", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/localidades/provincia/entidadGeografica")
	public Response localidades(

			@Parameter(description = "codi de la provincia", required = true, example = "7", schema = @Schema(implementation = Long.class)) @QueryParam("codigoProvincia") Long codigoProvincia,

			@Parameter(description = "codi de l'entitat geogràfica", required = true, example = "01", schema = @Schema(implementation = String.class)) @QueryParam("codigoEntidadGeografica") String codigoEntidadGeografica,

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */
	@Operation(operationId = "nivelesAdministracion", summary = "Servei per permet obtenir els nivells d'administració del catàleg")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <CodigoValor> que representa els nivells d'administració del catàleg", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/nivelesAdministracion")
	public Response nivelesAdministracion(

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */
	@Operation(operationId = "estadosEntidad", summary = "Servei que permet obtenir tots els estats que poden tenir els registres de dir3. Es recorda que un estat també pot tenir un estat associat.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <CodigoValor> que representa els estats del catàleg", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/estados")
	public Response estadosEntidad(

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */
	@Operation(operationId = "tiposVia", summary = "Servei que permet obtenir tots els tipus de via del catàleg.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <CodigoValor> que representa els tipus de via del catàleg", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/tiposvia")
	public Response tiposVia(

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * 
	 */
	@Operation(operationId = "paises", summary = "Servei que permet obtenir tots els països.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista de <PaisJson> que representa els països del catàleg", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/catalogo/paises")
	public Response paises(

			@Parameter(description = "filtre per codi d'estat dels resultats. Si no s'informa, ens retorna tots els resultats.", required = false, example = "V", schema = @Schema(implementation = String.class)) @QueryParam("estado") @DefaultValue("") String estado

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * Retorna l'arbre d'oficines de la unitat indicada pel codi
	 */

	@Operation(operationId = "obtenerArbolOficinas", summary = "Servei que ens retorna l'arbre d'oficines de la unitat indicada pel codi, partint de la darrera actualització i la data de la sincronització que s'indica per paràmetre. Si la data d'actualització és null es considera una sincronització i retorna tot l'arbre d'oficines vigents amb la unitat.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes OficinaRest que representa l'arbre en format de llista", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })

	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/oficinas/obtenerArbolOficinas")
	public Response obtenerArbolOficinas(

			@Parameter(description = "codi de la unitat", required = true, example = "L01502973", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "data de la darrera actualització. Format: yyyy-MM-dd", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("fechaActualizacion") String fechaActualizacionString,

			@Parameter(description = "data en la que es va fer la primera sincronització. Format: yyyy-MM-dd", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("fechaSincronizacion") String fechaSincronizacionString,

			@Parameter(description = "booleà que indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("true") boolean denominacionCooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * Retorna l'arbre d'oficines de la unitat indicada pel codi
	 */

	@Operation(operationId = "obtenerOficinasSIRUnidad", summary = "Servei que ens retorna el llistat d'oficines integrades en SIR de la unitat indicada pel codi.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes OficinaRest que representa l'arbre en format de llista", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/oficinas/obtenerOficinasSIRUnidad")
	public Response obtenerOficinasSIRUnidad(

			@Parameter(description = "codi de la unitat", required = true, example = "E03139201", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "booleà que indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("true") boolean denominacionCooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * Retorna l'arbre d'oficines sir de tot l'arbre de la unitat indicada pel codi
	 */
	@Operation(operationId = "obtenerArbolOficinasSir", summary = "Servei que permet obtenir totes les oficines sir del organisme indicat per paràmetre i dels seus organismes fills")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes OficinaRest en format JSON", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/oficinas/obtenerArbolOficinasSir")
	public Response obtenerArbolOficinasSir(

			@Parameter(description = "codi de la unitat que volem obtenir les seves oficines", required = true, example = "L01502973", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,
			@Parameter(description = "Indica si volem obtenir la denominació cooficial. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("true") boolean denominacionCooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * Obté les dades d'una oficina en funció del codi i la data d'actualització i
	 * la data de la primera sincronització. Si la data d'actualització és posterior
	 * a la d'importació se suposa que ha canviat i s'envia.
	 */

	@Operation(operationId = "obtenerOficina", summary = "Servei que ens retorna les dades d'una oficina en funció del codi, la data d'actualització i la de sincronització")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Un objecte OficinaRest amb les dades d'una oficina", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = OficinaRest.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/oficinas/obtenerOficina")
	public Response obtenerOficina(

			@Parameter(description = "codi de la unitat", required = true, example = "O00031074", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "data de la darrera actualització. Format: yyyy-MM-dd", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("fechaActualizacion") String fechaActualizacionString,

			@Parameter(description = "data en la que es va fer la primera sincronització. Format: yyyy-MM-dd", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("fechaSincronizacion") String fechaSincronizacionString,

			@Parameter(description = "booleà que indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("true") boolean denominacionCooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * Retorna l 'arbre d'unitats de la unitat indicada pel codi, partint de la data
	 * de la darrera actualització i la data de la sincronització
	 */

	@Operation(operationId = "obtenerArbolUnidades", summary = "Servei que ens retorna l 'arbre d'unitats de la unitat indicada pel codi, partint de la data de la darrera actualització i la data de la sincronització. "
			+ "Si no s'indica data d'actualització es considera que és una sincronització i et retorna tot l'arbre d'unitats vigents. Si s'indica data d'actualització, "
			+ "retorna les unitats que han estat actualitzades dins el període de temps indicat tenint en compte també la data de la darrera importació des de dir3 Madrid.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes UnidadRest que representa l'arbre en format de llista.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidades/obtenerArbolUnidades")
	public Response obtenerArbolUnidades(

			@Parameter(description = "codi de la unitat arrel", required = true, example = "E03139201", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "data de la darrera actualització. Format: yyyy-MM-dd", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("fechaActualizacion") String fechaActualizacionString,

			@Parameter(description = "data en la que es va fer la primera sincronització. Format: yyyy-MM-dd", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("fechaSincronizacion") String fechaSincronizacionString,

			@Parameter(description = "booleà que indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("true") boolean denominacionCooficial,

			@Parameter(description = "booleà que indica si volem que es retorni el històric de les unitats. Per defecte està a 'true' i ens retornarà la llista si existeix.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("historicos") @DefaultValue("true") boolean mostrarHistoricos,

			@Parameter(description = "booleà que indica si volem que es retornin els contactes de la unitat ( direcció, telèfon, email,...). Per defecte està a 'true' i ens retornarà la llista si existeix.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("contactos") @DefaultValue("true") boolean mostrarContactos

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * Retorna l 'arbre d'unitats de la unitat indicada pel codi, partint de la data
	 * de la darrera actualització i la data de la sincronització
	 */

	@Operation(operationId = "obtenerArbolUnidadesDestinatarias", summary = "Mètode que retorna una llista d’unitats que tenen oficines i que per tant són possibles destinatàries. S’empra per les aplicacions per saber a on dirigir la seva tramitació.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Llista d'objectes UnidadRest que representa l'arbre en format de llista.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidades/obtenerArbolUnidadesDestinatarias")
	public Response obtenerArbolUnidadesDestinatarias(

			@Parameter(description = "codi de la unitat arrel", required = true, example = "A04031575", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "booleà que indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("true") boolean denominacionCooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * Retorna la informació d'una unitat vigent en funció de la data
	 * d'actualització i sincronització.
	 */

	@Operation(operationId = "obtenerUnidad", summary = "Mètode que retorna un objecte de tipus UnidadRest vigent a partir del codi indicat  en funció de la data d'actualització i la de sincronització "
			+ "(indica quan es va fer la primera sincronització). Si la data d'actualització és posterior a la d'importació amb dir3 de Madrid es "
			+ "suposa que  ha canviat i s' envia. ")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "un objecte de tipus UnidadRest que representa amb la informació de la unitat.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UnidadRest.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidades/obtenerUnidad")
	public Response obtenerUnidad(

			@Parameter(description = "codi de la unitat", required = true, example = "E03139201", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "data de la darrera actualització. Format: yyyy-MM-dd", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("fechaActualizacion") String fechaActualizacionString,

			@Parameter(description = "data en la que es va fer la primera sincronització. Format: yyyy-MM-dd", required = false, example = "", schema = @Schema(implementation = String.class)) @QueryParam("fechaSincronizacion") String fechaSincronizacionString,

			@Parameter(description = "booleà que indica si volem que es retorni la denominació cooficial si existeix", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("false") boolean denominacionCooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * Retorna la informació d'una unitat a partir del codi indicat
	 */

	@Operation(operationId = "buscarUnidad", summary = "Mètode que retorna un objecte de tipus UnidadRest amb la informació de la unitat a partir del codi indicat")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "un objecte de tipus UnidadRest que representa amb la informació de la unitat.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UnidadRest.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidades/buscarUnidad")
	public Response buscarUnidad(

			@Parameter(description = "codi de la unitat", required = true, example = "E03139201", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "booleà que indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("true") boolean denominacionCooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	@Operation(operationId = "historico", summary = "Mètode que retorna les unitats que substitueixen a la unitat amb el codi indicat. Aquest codi serà d’una unitat extingida.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Una llista d'objectes de tipus ObjetoDirectorioExtendido.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UnidadRest.class))) })
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidades/historico")
	public Response historicoUnidad(
			@Parameter(description = "codi de la unitat extingida", required = true, example = "E04984901", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo

	) throws Exception {

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	/*
	 * Retorna la informació d'una unitat a partir del codi indicat
	 */

	@Operation(operationId = "obtenerHistoricosFinales", summary = "Mètode que retorna les unitats que substitueixen a la unitat amb el codi indicat. Aquest codi serà d’una unitat extingida.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Una llista d'objectes de tipus UnidadRest.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UnidadRest.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidades/obtenerHistoricosFinales")
	public Response obtenerHistoricosFinales(

			@Parameter(description = "codi de la unitat extingida", required = true, example = "E04984901", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "booleà que indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("true") boolean denominacionCooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	/*
	 * Retorna la informació d'una unitat a partir del codi indicat
	 */

	@Operation(operationId = "obtenerHistoricosFinalesSIR", summary = "Mètode que retorna les unitats que tenen alguna oficina SIR que substitueixen a la unitat del codi indicat. Aquest codi serà d’una unitat extingida. "
			+ "Regweb3 empra aquest mètode per a la gestió de enviaments SIR d’unitats extingides.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Sense contingut", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "200", description = "Una llista d'objectes de tipus UnidadRest", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UnidadRest.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unidades/obtenerHistoricosFinalesSIR")
	public Response obtenerHistoricosFinalesSIR(

			@Parameter(description = "codi de la unitat extingida", required = true, example = "E04984901", schema = @Schema(implementation = String.class)) @QueryParam("codigo") String codigo,

			@Parameter(description = "booleà que indica si volem que es retorni la denominació cooficial si existeix. Per defecte està a 'true' i ens retornarà la denominació cooficial. Si es vol la denominació oficial o les dues denominacions s'ha d'enviar el valor 'false'.", required = false, example = "true", schema = @Schema(implementation = boolean.class)) @QueryParam("denominacionCooficial") @DefaultValue("true") boolean denominacionCooficial

	) {

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	/*
	 * Retorna totes les unitats que compleixen amb els criteris de cerca que
	 * s'envien per POST a la part de "filtro" de l'objecte SistraRequest.
	 */

	@Operation(operationId = "sistraObtenerUnidades", summary = "Mètode que retorna les unitats en funció dels criteris indicats al camp filtro del JSON d'entrada.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Una llista d'objectes de tipus UnidadRestExtendido", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SistraResponse.class))) })
	@SecurityRequirement(name = "BasicAuth")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/sistra/unidades")
	public Response sistraObtenerUnidades(
			@RequestBody(description = "JSON amb els criteris de cerca d'unitats en format JSON: {\"idDominio\":\"SISTRA\", \"filtro\":[{\"codigo\":\"codigodir3\",\"valor\":\"\"},{\"codigo\":\"denominacion\",\"valor\":\"\"},{\"codigo\":\"nivelAdministracion\",\"valor\":\"2\"},{\"codigo\":\"comunidadAutonoma\",\"valor\":\"4\"},{\"codigo\":\"provincia\",\"valor\":\"\"},{\"codigo\":\"conOficinas\",\"valor\":\"false\"},{\"codigo\":\"unidadRaiz\",\"valor\":\"false\"},{\"codigo\":\"vigentes\",\"valor\":\"true\"}]}", required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))) String peticion) {
		return Response.status(Response.Status.NO_CONTENT).build();
	}

}
