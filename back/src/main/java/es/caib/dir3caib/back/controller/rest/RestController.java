package es.caib.dir3caib.back.controller.rest;

import es.caib.dir3caib.back.utils.CodigoValor;
import es.caib.dir3caib.persistence.ejb.*;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.Nodo;
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
    protected Dir3RestLocal dir3RestEjb;

    @EJB(mappedName = "dir3caib/ArbolEJB/local")
    protected ArbolLocal arbolEjb;

    @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
    protected CatEstadoEntidadLocal catEstadoEntidadEjb;

    @EJB(mappedName = "dir3caib/CatProvinciaEJB/local")
    protected CatProvinciaLocal catProvinciaEjb;

    @EJB(mappedName = "dir3caib/CatAmbitoTerritorialEJB/local")
    protected CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    protected UnidadLocal unidadEjb;

    /**
     * Obtiene los {@link es.caib.dir3caib.persistence.model.Unidad} por denominacion
     */
    @RequestMapping(value = "/unidad/unidadesDenominacion", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Unidad>> unidadesPorDenominacion(@RequestParam String denominacion) throws Exception {
        log.info("dentro rest unidadPorDenominacion");
        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        //Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<Unidad> resultado = dir3RestEjb.findUnidadesByDenominacion(new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"));
        log.info(" Unidades encontradas: " + resultado.size());
        HttpHeaders headers = addAccessControllAllowOrigin();
        return new ResponseEntity<List<Unidad>>(resultado, headers, HttpStatus.OK);
    }


    /**
     * Obtiene los {@link es.caib.dir3caib.persistence.model.Oficina} por denominacion
     */
    @RequestMapping(value = "/oficina/oficinasDenominacion", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Oficina>> oficinasPorDenominacion(@RequestParam String denominacion) throws Exception {
        log.info("dentro rest oficinaPorDenominacion");
        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        //Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<Oficina> resultado = dir3RestEjb.findOficinasByDenominacion(new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"));
        log.info(" Oficinas encontradas: " + resultado.size());
        HttpHeaders headers = addAccessControllAllowOrigin();
        return new ResponseEntity<List<Oficina>>(resultado, headers, HttpStatus.OK);
    }


    /**
     * Obtiene el arbol de  {@link es.caib.dir3caib.persistence.model.Unidad} del código indicado
     */
    @RequestMapping(value = "/unidad/arbolUnidades", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Unidad>> arbolUnidades(@RequestParam String codigo) throws Exception {
        log.info("dentro rest Arbol Unidades");

        List<Unidad> unidades = dir3RestEjb.obtenerArbolUnidades(codigo, null);
        log.info("Arbol unidades encontrado " + unidades.size());
        HttpHeaders headers = addAccessControllAllowOrigin();

        return new ResponseEntity<List<Unidad>>(unidades, headers, HttpStatus.OK);

    }


    /**
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} del organismo indicado
     */
    @RequestMapping(value = "/oficina/oficinasOrganismo", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Oficina>> oficinasOrganismo(@RequestParam String codigo) throws Exception {
        log.info("dentro rest Oficinas organismo");

        List<Oficina> oficinas = dir3RestEjb.obtenerOficinasOrganismo(codigo, null);
        log.info("Arbol oficinas encontrado " + oficinas.size());
        HttpHeaders headers = addAccessControllAllowOrigin();

        return new ResponseEntity<List<Oficina>>(oficinas, headers, HttpStatus.OK);

    }

    /**
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Unidad} en función de los criterios de busqueda
     */
    @RequestMapping(value = "/busqueda/organismos", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Nodo>> busquedaOrganismos(@RequestParam String codigo, @RequestParam String denominacion, @RequestParam Long codNivelAdministracion, @RequestParam Long codComunidadAutonoma, @RequestParam boolean conOficinas, @RequestParam boolean unidadRaiz, @RequestParam Long provincia, @RequestParam String localidad) throws Exception {

        log.info("dentro rest busqueda organismos");
        //log.info("dentro rest busqueda organismos ISO lenght: " + new String(denominacion.getBytes("ISO-8859-1"), "UTF-8").length());
        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        //Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<Nodo> unidades = dir3RestEjb.busquedaOrganismos(codigo, new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codNivelAdministracion, codComunidadAutonoma, conOficinas, unidadRaiz, provincia, localidad);
        log.info("Organismos encontrados " + unidades.size());
        HttpHeaders headers = addAccessControllAllowOrigin();

        return new ResponseEntity<List<Nodo>>(unidades, headers, HttpStatus.OK);

        //Pruebas con cache rest
        //return new ResponseEntity<List<Nodo>>(unidades, headers, HttpStatus.NOT_MODIFIED);

    }


    /**
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} en función de los criterios de busqueda
     */
    @RequestMapping(value = "/busqueda/oficinas", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<Nodo>> busquedaOficinas(@RequestParam String codigo, @RequestParam String denominacion, @RequestParam Long codNivelAdministracion, @RequestParam Long codComunidadAutonoma, @RequestParam Long provincia, @RequestParam String localidad) throws Exception {
        log.info("dentro rest busqueda oficinas");
        //Transformamos el campo denominacion de ISO a UTF-8 para realizar las búsquedas en bd que estan en UTF-8.
        // Esto se hace porque el @RequestParam viene en ISO-8859-1.
        List<Nodo> oficinas = dir3RestEjb.busquedaOficinas(codigo, new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codNivelAdministracion, codComunidadAutonoma, provincia, localidad);
        log.info("Oficinas encontradas " + oficinas.size());
        HttpHeaders headers = addAccessControllAllowOrigin();

        return new ResponseEntity<List<Nodo>>(oficinas, headers, HttpStatus.OK);

    }

    /**
     * Obtiene la denominacion de una Unidad
     */
    @RequestMapping(value = "/unidad/denominacion", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> unidadDenominacion(@RequestParam String codigo) throws Exception {
        log.info("dentro rest unidad denominacion ");
        String denominacion = dir3RestEjb.unidadDenominacion(codigo);
        log.info("Unidad denominacion " + denominacion);
        HttpHeaders headers = addAccessControllAllowOrigin();

        return new ResponseEntity<String>(denominacion, headers, HttpStatus.OK);

    }

    /**
     * Obtiene la denominacion de una Unidad
     */
    @RequestMapping(value = "/oficina/denominacion", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> oficinaDenominacion(@RequestParam String codigo) throws Exception {
        log.info("dentro rest oficina denominacion ");
        String denominacion = dir3RestEjb.oficinaDenominacion(codigo);
        log.info("Oficina denominacion " + denominacion);
        HttpHeaders headers = addAccessControllAllowOrigin();

        return new ResponseEntity<String>(denominacion, headers, HttpStatus.OK);

    }

    /**
     * Obtiene el organigrama solo de organismos sin oficinas a partir del codigo especificado, pero muestra sus
     * ascendentes y sus descendientes
     * Es el que se enmplea para mostrar el arbol en la búsqueda de organismos destinatarios de regweb3
     *
     * @param codigo el código raiz del que partimos
     */
    @RequestMapping(value = "/organigrama", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<Nodo> organigrama(@RequestParam String codigo) throws Exception {
        log.info("dentro rest organigrama codigo : " + codigo);
        Nodo nodo = new Nodo();
        arbolEjb.arbolUnidadesAscendentes(codigo, nodo, Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE, false);
        log.info("NODO  REST" + nodo.getDenominacion());

        HttpHeaders headers = addAccessControllAllowOrigin();
        return new ResponseEntity<Nodo>(nodo, headers, HttpStatus.OK);

    }


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

        List<Nodo> unidades = dir3RestEjb.busquedaDenominacionComunidad(new String(denominacion.getBytes("ISO-8859-1"), "UTF-8"), codComunidad);

        log.info("Encontradas " + unidades.size());
        HttpHeaders headers = addAccessControllAllowOrigin();
        return new ResponseEntity<List<Nodo>>(unidades, headers, HttpStatus.OK);

    }

    /**
     * Obtiene los {@link es.caib.dir3caib.persistence.model.CatProvincia} de la comunidad autonoma seleccionada
     */
    @RequestMapping(value = "/provincias", method = RequestMethod.GET)
    public
    @ResponseBody
    List<CodigoValor> provincias(@RequestParam Long id) throws Exception {

        List<CatProvincia> provincias = catProvinciaEjb.getByComunidadAutonoma(id);
        List<CodigoValor> codigosValor = new ArrayList<CodigoValor>();
        for (CatProvincia provincia : provincias) {
            CodigoValor codigoValor = new CodigoValor();
            codigoValor.setId(provincia.getCodigoProvincia());
            codigoValor.setDescripcion(provincia.getDescripcionProvincia());
            codigosValor.add(codigoValor);
        }
        return codigosValor;
    }

    /**
     * Obtiene los {@link es.caib.dir3caib.persistence.model.CatAmbitoTerritorial} del nivel administracion seleccionado
     */
    @RequestMapping(value = "/ambitosTerritoriales", method = RequestMethod.GET)
    public
    @ResponseBody
    List<CodigoValor> ambitosTerritoriales(@RequestParam Long id) throws Exception {

        List<CatAmbitoTerritorial> ambitos = catAmbitoTerritorialEjb.getByAdministracion(id);
        List<CodigoValor> codigosValor = new ArrayList<CodigoValor>();
        for (CatAmbitoTerritorial ambito : ambitos) {
            CodigoValor codigoValor = new CodigoValor();
            codigoValor.setId(ambito.getCodigoAmbito());
            codigoValor.setDescripcion(ambito.getDescripcionAmbito());
            codigosValor.add(codigoValor);
        }
        return codigosValor;
    }


    private HttpHeaders addAccessControllAllowOrigin() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return headers;
    }
}
