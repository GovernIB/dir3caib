package es.caib.dir3caib.back.controller.rest;

import es.caib.dir3caib.persistence.ejb.ArbolLocal;
import es.caib.dir3caib.persistence.ejb.Dir3RestLocal;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.Unidad;
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
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Unidad} en función de los criterios de busqueda
     */
    @RequestMapping(value = "/busqueda/organismos", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Nodo>> busquedaOrganismos(@RequestParam String codigo, @RequestParam String denominacion, @RequestParam Long codNivelAdministracion, @RequestParam Long codComunidadAutonoma, @RequestParam boolean conOficinas, @RequestParam boolean unidadRaiz, @RequestParam Long provincia, @RequestParam String localidad) throws Exception {

        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        //Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<Nodo> resultado = dir3RestEjb.busquedaOrganismos(codigo, new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codNivelAdministracion, codComunidadAutonoma, conOficinas, unidadRaiz, provincia, localidad);
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
    ResponseEntity<List<Nodo>> busquedaOficinas(@RequestParam String codigo, @RequestParam String denominacion, @RequestParam Long codNivelAdministracion, @RequestParam Long codComunidadAutonoma, @RequestParam Long provincia, @RequestParam String localidad, @RequestParam boolean oficinasSir) throws Exception {

        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        // Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<Nodo> resultado = dir3RestEjb.busquedaOficinas(codigo, new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codNivelAdministracion, codComunidadAutonoma, provincia, localidad, oficinasSir);
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
}
