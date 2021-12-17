package es.caib.dir3caib.back.controller.rest;

import es.caib.dir3caib.persistence.ejb.ArbolLocal;
import es.caib.dir3caib.persistence.ejb.Dir3RestLocal;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.json.OficinaJson;
import es.caib.dir3caib.persistence.utils.CodigoValor;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.ObjetoDirectorio;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 25/03/14 13:32
 *
 * @author mgonzalez
 */
@Controller
@RequestMapping(value = "/rest")
public class RestController {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/Dir3RestEJB/local")
    private Dir3RestLocal dir3RestEjb;

    @EJB(mappedName = "dir3caib/ArbolEJB/local")
    private ArbolLocal arbolEjb;


    /**
     * Obtiene los {@link es.caib.dir3caib.persistence.model.Unidad} por denominacion
     */
    @RequestMapping(value = "/unidad/unidadesDenominacion", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<ObjetoDirectorio>> unidadesPorDenominacion(@RequestParam String denominacion) throws Exception {

        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        //Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<ObjetoDirectorio> resultado = dir3RestEjb.findUnidadesByDenominacion(new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"));

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<ObjetoDirectorio>>(resultado, headers, status);

    }


    /**
     * Obtiene los {@link es.caib.dir3caib.persistence.model.Oficina} por denominacion
     */
    @RequestMapping(value = "/oficina/oficinasDenominacion", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<ObjetoDirectorio>> oficinasPorDenominacion(@RequestParam String denominacion) throws Exception {

        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        //Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<ObjetoDirectorio> resultado = dir3RestEjb.findOficinasByDenominacion(new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"));

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<ObjetoDirectorio>>(resultado, headers, status);

    }


    /**
     * Obtiene el arbol de  {@link es.caib.dir3caib.persistence.model.Unidad} del código indicado
     * TODO Revisar si alguien lo emplea. REGWEB no (03/10/2017)
     */
    @RequestMapping(value = "/unidad/arbolUnidades", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<ObjetoDirectorio>> arbolUnidades(@RequestParam String codigo) throws Exception {

        List<Unidad> resultado = dir3RestEjb.obtenerArbolUnidades(codigo, null);

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<ObjetoDirectorio>>(transformarUnidadAObjetoDirectorio(resultado), headers, status);

    }


    /**
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} del organismo indicado
     * //TODO revisar si se emplea. REGWEB no (03/10/2017)
     */
    @RequestMapping(value = "/oficina/oficinasOrganismo", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<ObjetoDirectorio>> oficinasOrganismo(@RequestParam String codigo) throws Exception {


        List<Oficina> resultado = dir3RestEjb.obtenerOficinasOrganismo(codigo, null);

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<ObjetoDirectorio>>(transformarOficinaAObjetoDirectorio(resultado), headers, status);

    }


    /**
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} del organismo indicado
     *
     */
    @RequestMapping(value = "/GET/oficinas", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<OficinaJson>> getArbolOficinasOrganismoOpenData(@RequestParam String codigo) throws Exception {


        List<Oficina> resultado = dir3RestEjb.obtenerArbolOficinasOpenData(codigo);

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<OficinaJson>>(transformarAOficinaJson(resultado), headers, status);

    }

    /**
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} del organismo indicado
     */
    @RequestMapping(value = "/GET/oficinas/baleares", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<OficinaJson>> getOficinasBalearesOpenData() throws Exception {


        List<Oficina> resultado = dir3RestEjb.getOficinasBalearesOpenData();

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<OficinaJson>>(transformarAOficinaJson(resultado), headers, status);

    }

    /**
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Unidad} en función de los criterios de busqueda
     */
    /* @RequestParam(value="vigentes", required=false) boolean vigentes ----- Esto es para definir parametros opcionales*/
    @RequestMapping(value = "/busqueda/organismos", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Nodo>> busquedaOrganismos(@RequestParam String codigo, @RequestParam String denominacion, @RequestParam Long codNivelAdministracion, @RequestParam Long codComunidadAutonoma, @RequestParam boolean conOficinas, @RequestParam boolean unidadRaiz, @RequestParam Long provincia, @RequestParam String localidad, @RequestParam boolean vigentes) throws Exception {

        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        //Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<Nodo> resultado = dir3RestEjb.busquedaOrganismos(codigo, new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codNivelAdministracion, codComunidadAutonoma, conOficinas, unidadRaiz, provincia, localidad, vigentes);
        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<Nodo>>(resultado, headers, status);
    }


    /**
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} en función de los criterios de busqueda
     */
    @RequestMapping(value = "/busqueda/oficinas", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Nodo>> busquedaOficinas(@RequestParam String codigo, @RequestParam String denominacion, @RequestParam Long codNivelAdministracion, @RequestParam Long codComunidadAutonoma, @RequestParam Long provincia, @RequestParam String localidad, @RequestParam boolean oficinasSir, @RequestParam boolean vigentes) throws Exception {

        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        // Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<Nodo> resultado = dir3RestEjb.busquedaOficinas(codigo, new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codNivelAdministracion, codComunidadAutonoma, provincia, localidad, oficinasSir, vigentes);
        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<Nodo>>(resultado, headers, status);
    }

    /**
     * Obtiene la denominacion de una Unidad (Empleado por REGWEB3)
     */
    @RequestMapping(value = "/unidad/denominacion", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> unidadDenominacion(@RequestParam String codigo) throws Exception {

        String denominacion = dir3RestEjb.unidadDenominacion(codigo);
        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (!denominacion.isEmpty()) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<String>(denominacion, headers, status);

    }


    /**
     * Obtiene el estado de una Unidad (Empleado por REGWEB3)
     */
    @RequestMapping(value = "/GET/unidad/estado", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> unidadEstado(@RequestParam String codigo) throws Exception {

        String estado = dir3RestEjb.unidadEstado(codigo);
        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (!estado.isEmpty()) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<String>(estado, headers, status);

    }

    /**
     * Obtiene la denominacion de una Unidad (Empleado por REGWEB3)
     */
    @RequestMapping(value = "/oficina/denominacion", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> oficinaDenominacion(@RequestParam String codigo) throws Exception {

        String denominacion = dir3RestEjb.oficinaDenominacion(codigo);
        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (!denominacion.isEmpty()) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<String>(denominacion, headers, status);
    }

    /**
     * Obtiene el organigrama solo de organismos sin oficinas a partir del codigo especificado, pero muestra sus
     * ascendentes y sus descendientes
     * Es el que se emplea para mostrar el árbol en la búsqueda de organismos destinatarios de regweb3(Lo emplea Regweb3)
     *
     * @param codigo el código raiz del que partimos
     */
    @RequestMapping(value = "/organigrama", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<Nodo> organigrama(@RequestParam String codigo) throws Exception {

        Nodo nodo = new Nodo();
        arbolEjb.arbolUnidadesAscendentes(codigo, nodo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE, false);

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (nodo.getCodigo() != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<Nodo>(nodo, headers, status);

    }


    //
    // Métodos de catàlogo para Helium(CMAIB)
    //


    /**
     * Método que realiza la busqueda de unidades por denominación y comunidad autónoma para utilidad HELIUM
     *
     * @param denominacion denominación de la unidad
     * @param codComunidad codigo de la comunidad
     * @return List<Nodo> listado de objetos nodo con el código,denominación, denominación de unidad raiz y denominación de unidad superior
     */
    @RequestMapping(value = "/busqueda/unidades/denominacion/comunidad", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Nodo>> obtenerUnidadesDenominacionComunidad(@RequestParam String denominacion, @RequestParam Long codComunidad)
            throws Exception {
        List<Nodo> resultado = new ArrayList<Nodo>();

        if (codComunidad != null) {
            resultado = dir3RestEjb.busquedaDenominacionComunidad(new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codComunidad);
        }

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<Nodo>>(resultado, headers, status);

    }


    /**
     * Obtiene los {@link es.caib.dir3caib.persistence.model.CatAmbitoTerritorial} del nivel administracion seleccionado
     * Se emplea en unidadList.jsp
     */
    @RequestMapping(value = "/catalogo/ambitosTerritoriales", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<CodigoValor>> ambitosTerritoriales(@RequestParam Long id) throws Exception {

        List<CodigoValor> resultado = dir3RestEjb.getAmbitoTerritorialByAdministracion(id);

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
    }


    /**
     * Obtiene todas las comunidades autónomas
     * {@link es.caib.dir3caib.persistence.model.CatComunidadAutonoma}
     */
    @RequestMapping(value = "/catalogo/comunidadesAutonomas", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<CodigoValor>> comunidadesAutonomas() throws Exception {

        List<CodigoValor> resultado = dir3RestEjb.getComunidadesAutonomas();

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
    }


    /**
     * Obtiene los
     * {@link es.caib.dir3caib.persistence.model.CatEntidadGeografica}
     */
    @RequestMapping(value = "/catalogo/entidadesGeograficas", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<CodigoValor>> entidadesGeograficas() throws Exception {

        List<CodigoValor> resultado = dir3RestEjb.getEntidadesGeograficas();

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
    }


    /**
     * Obtiene los
     * {@link es.caib.dir3caib.persistence.model.CatProvincia}
     */
    @RequestMapping(value = "/catalogo/provincias", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<CodigoValor>> provincias() throws Exception {

        List<CodigoValor> resultado = dir3RestEjb.getProvincias();

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
    }


    /**
     * Obtiene los
     * {@link es.caib.dir3caib.persistence.model.CatProvincia} por comunidad autonoma
     */
    @RequestMapping(value = "/catalogo/provincias/comunidadAutonoma", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<CodigoValor>> provinciasCA(@RequestParam Long id) throws Exception {

        List<CodigoValor> resultado = dir3RestEjb.getProvinciasByComunidad(id);

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
    }


    /**
     * Obtiene los
     * {@link es.caib.dir3caib.persistence.model.CatLocalidad} por provincia y entidad geografica
     */
    @RequestMapping(value = "/catalogo/localidades/provincia/entidadGeografica", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<CodigoValor>> localidades(@RequestParam Long codigoProvincia, String codigoEntidadGeografica) throws Exception {
        List<CodigoValor> resultado = dir3RestEjb.getLocalidadByProvinciaEntidadGeografica(codigoProvincia, codigoEntidadGeografica);

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
    }


    /**
     * Obtiene los
     * {@link es.caib.dir3caib.persistence.model.CatNivelAdministracion}
     */
    @RequestMapping(value = "/catalogo/nivelesAdministracion", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<CodigoValor>> nivelesAdministracion() throws Exception {

        List<CodigoValor> resultado = dir3RestEjb.getNivelesAdministracion();

        HttpHeaders headers = addAccessControllAllowOrigin();
        //Si hay resultados fijamos el HttpStatus a OK, sino indicamos que no hay resultados.
        HttpStatus status = (resultado.size() > 0) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<List<CodigoValor>>(resultado, headers, status);
    }


    private HttpHeaders addAccessControllAllowOrigin() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return headers;
    }


    /**
     * Método que transforma los resultados de una lista de Unidad en una lista de ObjetoDirectorio
     *
     * @param resultados
     * @return
     */
    private List<ObjetoDirectorio> transformarUnidadAObjetoDirectorio(List<Unidad> resultados) {
        List<ObjetoDirectorio> objetoDirectorios = new ArrayList<ObjetoDirectorio>();
        for (Unidad unidad : resultados) {
            ObjetoDirectorio objetoDirectorio = new ObjetoDirectorio();
            objetoDirectorio.setCodigo(unidad.getCodigo());
            objetoDirectorio.setDenominacion(unidad.getDenominacion());
            objetoDirectorios.add(objetoDirectorio);
        }

        return objetoDirectorios;

    }


    /**
     * Método que transforma los resultados de una lista de Oficina en una lista de ObjetoDirectorio
     *
     * @param resultados
     * @return
     */
    private List<ObjetoDirectorio> transformarOficinaAObjetoDirectorio(List<Oficina> resultados) {
        List<ObjetoDirectorio> objetoDirectorios = new ArrayList<ObjetoDirectorio>();
        for (Oficina oficina : resultados) {
            ObjetoDirectorio objetoDirectorio = new ObjetoDirectorio();
            objetoDirectorio.setCodigo(oficina.getCodigo());
            objetoDirectorio.setDenominacion(oficina.getDenominacion());
            objetoDirectorios.add(objetoDirectorio);
        }

        return objetoDirectorios;

    }

    /*
      Método que transforma los resultados de una lista de Oficina en una lista de OficinaJson
     */
    private List<OficinaJson> transformarAOficinaJson(List<Oficina> resultados) {
        List<OficinaJson> oficinaJsons = new ArrayList<OficinaJson>();
        for (Oficina ofi : resultados) {
            OficinaJson oficinaJson = new OficinaJson();
            oficinaJson.setCodigoDir3(ofi.getCodigo());
            oficinaJson.setDenominacion(ofi.getDenominacion());
            oficinaJson.setEstado(ofi.getEstado().getDescripcionEstadoEntidad());
            oficinaJson.setNivelAdministracion(ofi.getNivelAdministracion().getDescripcionNivelAdministracion());


            oficinaJson.setTipoOficina(ofi.getTipoOficina().getDescripcionJerarquiaOficina());  //CatJerarquiaOficina
            oficinaJson.setUnidadResponsable(ofi.getDenominacion());//Unidad
            if (ofi.getCodOfiResponsable() != null) {
                oficinaJson.setOficinaResponsable(ofi.getCodOfiResponsable().getDenominacion());   //Oficina
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
                oficinaJson.setMunicipio(ofi.getLocalidad().getDescripcionLocalidad()); //LOCALIDAD
            }

            oficinaJson.setNombreVia(ofi.getNombreVia());
            oficinaJson.setNumeroVia(ofi.getNumVia());
            if (ofi.getTipoVia() != null) {
                oficinaJson.setTipoVia(ofi.getTipoVia().getDescripcionTipoVia());
            }
            oficinaJson.setCodigoPostal(ofi.getCodPostal());

            //Montamos los servicios como una lista de strings
            List<String> servicios = new ArrayList<String>();

            for (CatServicio serv : ofi.getServicios()) {
                servicios.add(serv.getDescServicio());
            }

            //Version Map
            /*Map<Integer, String> serviciosMap = new HashMap<Integer, String>() {
                {
                    put(1, " ");
                    put(2, " ");
                    put(3, " ");
                    put(4, " ");
                    put(5, " ");
                    put(6, " ");
                    put(7, " ");
                    put(8, " ");
                    put(9, " ");
                    put(10, " ");
                    put(11, " ");
                    put(12, " ");
                    put(13, " ");
                    put(14, " ");
                    put(15, " ");
                }
            };


            for (Servicio serv : ofi.getServicios()) {

                switch (serv.getCodServicio().intValue()) {
                    case Dir3caibConstantes.SERVICIO_OFI_REGISTRO: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_OFI_REGISTRO);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_OFI_REGISTRO, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_OFI_INFORMACION: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_OFI_INFORMACION);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_OFI_INFORMACION, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_OFI_TRAMITACION: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_OFI_TRAMITACION);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_OFI_TRAMITACION, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_REG_VIRTUAL: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_REG_VIRTUAL);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_REG_VIRTUAL, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_SIR: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_SIR);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_SIR, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_SIR_ENVIO: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_SIR_ENVIO);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_SIR_ENVIO, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_SIR_RECEPCION: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_SIR_RECEPCION);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_SIR_RECEPCION, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_060: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_060);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_060, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_OFI_CORREOS: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_OFI_CORREOS);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_OFI_CORREOS, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_OFI_EXTRANJERIA: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_OFI_EXTRANJERIA);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_OFI_EXTRANJERIA, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_OFI_VIOLGENERO: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_OFI_VIOLGENERO);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_OFI_VIOLGENERO, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_OFI_ACCESIBLE: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_OFI_ACCESIBLE);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_OFI_ACCESIBLE, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_CLAVE: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_CLAVE);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_CLAVE, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_REA: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_REA);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_REA, serv.getDescServicio());
                        break;
                    }
                    case Dir3caibConstantes.SERVICIO_OFI_ORD: {
                        serviciosMap.remove(Dir3caibConstantes.SERVICIO_OFI_ORD);
                        serviciosMap.put(Dir3caibConstantes.SERVICIO_OFI_ORD, serv.getDescServicio());
                        break;
                    }
                }


            }

            StringBuffer serviciosBuffer = new StringBuffer();
            for (Map.Entry<Integer, String> entry : serviciosMap.entrySet()) {
                serviciosBuffer.append(entry.getValue() + ";");
            }

            oficinaJson.setServicios(serviciosBuffer.toString());*/

            List<String> contactos = new ArrayList<String>();
            for (ContactoOfi contactoOfi : ofi.getContactos()) {
                contactos.add(contactoOfi.getTipoContacto().getDescripcionTipoContacto() + " : " + contactoOfi.getValorContacto());
            }

            oficinaJson.setServicios(servicios);
            oficinaJson.setContactos(contactos);


            oficinaJsons.add(oficinaJson);
        }

        return oficinaJsons;

    }
}
