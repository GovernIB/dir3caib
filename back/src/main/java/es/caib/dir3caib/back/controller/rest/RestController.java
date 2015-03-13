package es.caib.dir3caib.back.controller.rest;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.persistence.ejb.Dir3RestLocal;
import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.ejb.EJB;

/**
 * Created 25/03/14 13:32
 *
 * @author mgonzalez
 */
@Controller
@RequestMapping(value = "/rest")
public class RestController extends BaseController {
  
  @EJB(mappedName = "dir3caib/Dir3RestEJB/local")
  protected Dir3RestLocal dir3RestEjb;

      /**
       * Obtiene los {@link es.caib.dir3caib.persistence.model.Unidad} por denominacion
       */
      @RequestMapping(value = "/unidad/unidadesDenominacion", method = RequestMethod.GET)
      public @ResponseBody
      ResponseEntity<List<Unidad>> unidadesPorDenominacion(@RequestParam String denominacion) throws Exception {
        log.info("dentro rest unidadPorDenominacion");
        List<Unidad> resultado = dir3RestEjb.findUnidadesByDenominacion(denominacion);
        log.info( " Unidades encontradas: " + resultado.size());
        HttpHeaders headers = addAccessControllAllowOrigin();
          return new ResponseEntity<List<Unidad>>(resultado, headers, HttpStatus.OK);
      }


      /**
       * Obtiene los {@link es.caib.dir3caib.persistence.model.Oficina} por denominacion
       */
      @RequestMapping(value = "/oficina/oficinasDenominacion", method = RequestMethod.GET)
      public @ResponseBody
      ResponseEntity<List<Oficina>> oficinasPorDenominacion(@RequestParam String denominacion) throws Exception {
        log.info("dentro rest oficinaPorDenominacion");
        List<Oficina> resultado = dir3RestEjb.findOficinasByDenominacion(denominacion);
        log.info( " Oficinas encontradas: " + resultado.size());
        HttpHeaders headers = addAccessControllAllowOrigin();
        return new ResponseEntity<List<Oficina>>(resultado, headers, HttpStatus.OK);
      }


     /**
      * Obtiene los {@link es.caib.dir3caib.persistence.model.Unidad} por denominacion
      */
     @RequestMapping(value = "/unidad/arbolUnidades", method = RequestMethod.GET)
     public @ResponseBody
     ResponseEntity<List<Unidad>> arbolUnidades(@RequestParam String codigo) throws Exception {
        log.info("dentro rest Arbol Unidades");

         List<Unidad> unidades = dir3RestEjb.obtenerArbolUnidades(codigo, null);
         log.info("Arbol unidades encontrado " + unidades.size());
         HttpHeaders headers = addAccessControllAllowOrigin();

         return new ResponseEntity<List<Unidad>>(unidades, headers, HttpStatus.OK);

     }


     /**
      * Obtiene los {@link es.caib.dir3caib.persistence.model.Unidad} por denominacion
      */
     @RequestMapping(value = "/oficina/oficinasOrganismo", method = RequestMethod.GET)
     public @ResponseBody
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
     public @ResponseBody
     ResponseEntity<List<ObjetoBasico>> busquedaOrganismos(@RequestParam String codigo, @RequestParam String denominacion, @RequestParam Long codNivelAdministracion,  @RequestParam Long codComunidadAutonoma) throws Exception {
        log.info("dentro rest busqueda organismos ");
        List<ObjetoBasico> unidades = dir3RestEjb.busquedaOrganismos(codigo, denominacion, codNivelAdministracion, codComunidadAutonoma);
        log.info("Organismos encontrados " + unidades.size());
        HttpHeaders headers = addAccessControllAllowOrigin();

        return new ResponseEntity<List<ObjetoBasico>>(unidades, headers, HttpStatus.OK);

     }


     /**
     * Obtiene las {@link es.caib.dir3caib.persistence.model.Oficina} en función de los criterios de busqueda
     */
     @RequestMapping(value = "/busqueda/oficinas", method = RequestMethod.GET)
     public @ResponseBody
     ResponseEntity<List<ObjetoBasico>> busquedaOficinas(@RequestParam String codigo, @RequestParam String denominacion, @RequestParam Long codNivelAdministracion,  @RequestParam Long codComunidadAutonoma) throws Exception {
        log.info("dentro rest busqueda oficinas ");
        List<ObjetoBasico> oficinas = dir3RestEjb.busquedaOficinas(codigo, denominacion, codNivelAdministracion, codComunidadAutonoma);
        log.info("Oficinas encontradas " + oficinas.size());
        HttpHeaders headers = addAccessControllAllowOrigin();

        return new ResponseEntity<List<ObjetoBasico>>(oficinas, headers, HttpStatus.OK);

     }

     /**
     * Obtiene la denominacion de una Unidad
     */
     @RequestMapping(value = "/unidad/denominacion", method = RequestMethod.GET)
     public @ResponseBody
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
     public @ResponseBody
     ResponseEntity<String> oficinaDenominacion(@RequestParam String codigo) throws Exception {
        log.info("dentro rest oficina denominacion ");
        String denominacion = dir3RestEjb.oficinaDenominacion(codigo);
        log.info("Oficina denominacion " + denominacion);
        HttpHeaders headers = addAccessControllAllowOrigin();

        return new ResponseEntity<String>(denominacion, headers, HttpStatus.OK);

     }



     private HttpHeaders addAccessControllAllowOrigin() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return headers;
     }
}
