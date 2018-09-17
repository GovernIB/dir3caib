package es.caib.dir3caib.back.controller.oficina;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.OficinaBusquedaForm;
import es.caib.dir3caib.persistence.ejb.ArbolLocal;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.Paginacion;
import es.caib.dir3caib.utils.Configuracio;
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
@RequestMapping(value = "/oficina")
public class OficinaController extends BaseController {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/ArbolEJB/local")
    private ArbolLocal arbolEjb;


    /**
     * Listado de los {@link es.caib.dir3caib.persistence.model.Oficina}
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listado() {
        return "redirect:/oficina/list";
    }


    /**
     * Carga el formulario para la búsqueda de {@link es.caib.dir3caib.persistence.model.Oficina}
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {

        CatEstadoEntidad vigente = catEstadoEntidadEjb.findByCodigo(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);

        Oficina oficina = new Oficina();
        oficina.setEstado(vigente);
        //Establecemos el estado por defecto de la oficina
        OficinaBusquedaForm oficinaBusqueda = new OficinaBusquedaForm(oficina, 1);

        //Cargamos el conjunto de los valores posibles del formulario
        List<CatNivelAdministracion> administraciones = catNivelAdministracionEjb.getAll();
        List<CatComunidadAutonoma> comunidades = catComunidadAutonomaEjb.getAll();
        List<CatEstadoEntidad> estadosEntidad = catEstadoEntidadEjb.getAll();

        // Valores por defecto en la Búsqueda
        String administracion = Configuracio.getBusquedaAdministracion();
        String comunidadAutonoma = Configuracio.getBusquedaComunidad();

        //Asignamos los valores por defecto a la búsqueda
        if(administracion != null){
            oficinaBusqueda.getOficina().setNivelAdministracion(new CatNivelAdministracion(Long.valueOf(administracion.trim())));
        }

        if(comunidadAutonoma != null){
            oficinaBusqueda.getOficina().setCodComunidad(new CatComunidadAutonoma(Long.valueOf(comunidadAutonoma.trim())));
        }

        model.addAttribute("oficinaBusqueda", oficinaBusqueda);
        model.addAttribute("administraciones", administraciones);
        model.addAttribute("comunidades", comunidades);
        model.addAttribute("estadosEntidad", estadosEntidad);

        return "oficina/oficinaList";

    }


    /**
     * Realiza la búsqueda de {@link es.caib.dir3caib.persistence.model.Oficina} según los parámetros del formulario
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ModelAndView list(@ModelAttribute OficinaBusquedaForm busqueda) throws Exception {

        ModelAndView mav = new ModelAndView("oficina/oficinaList");

        Oficina oficina = busqueda.getOficina();

        //Cogemos los valores del formulario
        Long codNivelAdministracion = (oficina.getNivelAdministracion() != null) ? oficina.getNivelAdministracion().getCodigoNivelAdministracion() : null;
        Long codComunidad = (oficina.getCodComunidad() != null) ? oficina.getCodComunidad().getCodigoComunidad() : null;
        Long codAmbProvincia = (oficina.getLocalidad() != null) ? oficina.getLocalidad().getProvincia().getCodigoProvincia() : null;
        String codEstado = (oficina.getEstado() != null) ? oficina.getEstado().getCodigoEstadoEntidad() : null;

        //Realizamos la búsqueda paginada
        Paginacion paginacion = oficinaEjb.busqueda(busqueda.getPageNumber(),
                oficina.getCodigo(),
                oficina.getDenominacion(),
                codNivelAdministracion,
                codComunidad,
                codAmbProvincia, codEstado);

        busqueda.setPageNumber(1);

        mav.addObject("administraciones", catNivelAdministracionEjb.getAll());
        mav.addObject("comunidades", catComunidadAutonomaEjb.getAll());
        mav.addObject("estadosEntidad", catEstadoEntidadEjb.getAll());

        mav.addObject("paginacion", paginacion);
        mav.addObject("oficinaBusqueda", busqueda);

        return mav;

    }


    /**
     * Método que redirige al arbol de la unidad responsable de la oficina que nos indican.
     * Sólo se muestra el suborganigrama de la oficina a partir de su unidad responsable hacia abajo.
     *
     * @param request
     * @param idOficina
     * @return
     */
    @RequestMapping(value = "/{idOficina}/arbol", method = RequestMethod.GET)
    public String mostrarArbolOficinas(HttpServletRequest request, @PathVariable String idOficina) throws Exception {

        //Obtenemos los datos básicos de la oficina que nos indican
        Oficina oficina = oficinaEjb.findByCodigoLigero(idOficina);

        //Redirigimos al arbol de la unidad padre de la oficina.
        String unidadResponsable = oficina.getCodUoResponsable().getCodigo();

        return "redirect:/unidad/" + unidadResponsable + "/arbol";


    }

}
