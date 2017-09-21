package es.caib.dir3caib.back.controller.unidad;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.UnidadBusquedaForm;
import es.caib.dir3caib.persistence.ejb.ArbolLocal;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.Paginacion;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author mgonzalez
 * Date: 2/10/13
 */

@Controller
@RequestMapping(value = "/unidad")
public class UnidadController extends BaseController {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/ArbolEJB/local")
    private ArbolLocal arbolEjb;


    /**
     * Listado de los {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listado() {
        return "redirect:/unidad/list";
    }


    /**
     * Carga el formulario para la busqueda de {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {

        CatEstadoEntidad vigente = catEstadoEntidadEjb.findByCodigo(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        Unidad unidad = new Unidad();
        unidad.setEstado(vigente);

        UnidadBusquedaForm unidadBusqueda = new UnidadBusquedaForm(unidad, 1, false);

        //Cargamos el conjunto de los valores posibles del formulario
        List<CatNivelAdministracion> administraciones = catNivelAdministracionEjb.getAll();
        List<CatComunidadAutonoma> comunidades = catComunidadAutonomaEjb.getAll();
        List<CatEstadoEntidad> estadosEntidad = catEstadoEntidadEjb.getAll();

        // Valores por defecto en la Búsqueda
        String administracion = Configuracio.getBusquedaAdministracion();
        String comunidadAutonoma = Configuracio.getBusquedaComunidad();

        //Establece los valores por defecto en la búsqueda
        if(administracion != null){
            unidadBusqueda.getUnidad().setNivelAdministracion(new CatNivelAdministracion(Long.valueOf(administracion.trim())));
        }

        if(comunidadAutonoma != null){
            unidadBusqueda.getUnidad().setCodComunidad(new CatComunidadAutonoma(Long.valueOf(comunidadAutonoma.trim())));
        }

        model.addAttribute("unidadBusqueda", unidadBusqueda);
        model.addAttribute("administraciones", administraciones);
        model.addAttribute("comunidades", comunidades);
        model.addAttribute("estadosEntidad", estadosEntidad);

        return "unidad/unidadList";

    }


    /**
     * Realiza la búsqueda de {@link es.caib.dir3caib.persistence.model.Unidad} según los parámetros del formulario
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ModelAndView list(@ModelAttribute UnidadBusquedaForm busqueda) throws Exception {

        ModelAndView mav = new ModelAndView("unidad/unidadList");

        Unidad unidad = busqueda.getUnidad();

        Long codNivelAdministracion = (unidad.getNivelAdministracion() != null) ? unidad.getNivelAdministracion().getCodigoNivelAdministracion() : null;
        String codAmbitoTerritorial = (unidad.getCodAmbitoTerritorial() != null) ? unidad.getCodAmbitoTerritorial().getCodigoAmbito() : null;
        Long codComunidad = (unidad.getCodComunidad() != null) ? unidad.getCodComunidad().getCodigoComunidad() : null;
        Long codAmbProvincia = (unidad.getCodAmbProvincia() != null) ? unidad.getCodAmbProvincia().getCodigoProvincia() : null;
        String codEstadoEntidad = (unidad.getEstado() != null) ? unidad.getEstado().getCodigoEstadoEntidad() : null;

        Paginacion paginacion = unidadEjb.busqueda(busqueda.getPageNumber(),
                unidad.getCodigo(),
                unidad.getDenominacion(),
                codNivelAdministracion,
                codAmbitoTerritorial,
                codComunidad,
                codAmbProvincia, busqueda.getUnidadRaiz(), codEstadoEntidad);

        busqueda.setPageNumber(1);

        mav.addObject("administraciones", catNivelAdministracionEjb.getAll());
        mav.addObject("comunidades", catComunidadAutonomaEjb.getAll());
        mav.addObject("estadosEntidad", catEstadoEntidadEjb.getAll());

        mav.addObject("paginacion", paginacion);
        mav.addObject("unidadBusqueda", busqueda);

        return mav;

    }

    /**
     * Método que obtiene el árbol de unidades de una unidad.
     *
     * @param request
     * @param idUnidad
     * @return
     */
    @RequestMapping(value = "/{idUnidad}/arbol", method = RequestMethod.GET)
    public ModelAndView mostrarArbolUnidades(HttpServletRequest request, @PathVariable String idUnidad) throws Exception {

        Long start = System.currentTimeMillis();
        ModelAndView mav = new ModelAndView("/arbolList");

        //Obtenemos los datos básicos de la unidad que nos indican(suele ser la raíz del árbol)
        Unidad unidad = unidadEjb.findByCodigoLigero(idUnidad);

        Nodo nodo = new Nodo(); //Representa el nodo raiz del árbol que se quiere mostrar.
        //Obtenemos el árbol de unidades
        arbolEjb.arbolUnidades(idUnidad, nodo, unidad.getEstado().getCodigoEstadoEntidad(), true);
        Long end = System.currentTimeMillis();

        log.info("TIEMPO CARGA ARBOLarbol: " + Utils.formatElapsedTime(end - start));

        mav.addObject("nodo", nodo);
        return mav;

    }

}
