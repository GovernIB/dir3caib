package es.caib.dir3caib.back.controller.oficina;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.OficinaBusquedaForm;
import es.caib.dir3caib.persistence.ejb.ArbolLocal;
import es.caib.dir3caib.persistence.ejb.RelacionOrganizativaOfiLocal;
import es.caib.dir3caib.persistence.ejb.RelacionSirOfiLocal;
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
import java.util.ArrayList;
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

    @EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
    private RelacionOrganizativaOfiLocal relacionOrganizativaOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionSirOfiEJB/local")
    private RelacionSirOfiLocal relacionSirOfiEjb;


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
        if (administracion != null) {
            oficinaBusqueda.getOficina().setNivelAdministracion(new CatNivelAdministracion(Long.valueOf(administracion.trim())));
        }

        if (comunidadAutonoma != null) {
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
           codAmbProvincia, codEstado, busqueda.getDenominacionCooficial());

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
    
    
    /**
     * Método que obtiene el detalle y el árbol de una oficina con el campo de DenominacionCooficial activo
     *
     * @param request
     * @param idOficina
     * @return
     */
    @RequestMapping(value = "/{idOficina}/detall", method = RequestMethod.GET)
    public ModelAndView detall(HttpServletRequest request, @PathVariable String idOficina) throws Exception {
    	
    	Long start = System.currentTimeMillis();
        ModelAndView mav = new ModelAndView("oficina/oficinaDetalle");

        // OBTENEMOS LOS DATOS PROPIOS DE LA OFICINA
        // Obtenemos la oficina
        Oficina oficina = oficinaEjb.findFullById(idOficina);
        mav.addObject("oficina", oficina);


        // OBTENEMOS EL ÁRBOL DE LA OFICINA
        //Obtenemos los datos de la unidad
        Unidad unidad = unidadEjb.findFullById(oficina.getCodUoResponsable().getCodigo());

        //Obtenemos en diferentes listas las unidades hasta el septimo nivel
        List<Unidad> unidadesPrimerNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesSegundoNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesTercerNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesCuartoNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesQuintoNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesSextoNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesSeptimoNivel = new ArrayList<Unidad>();

        //Si no es raiz
        if (unidad.getCodUnidadRaiz() != null && !unidad.getCodUnidadRaiz().getCodigo().equals(unidad.getCodigo())) {
            //Como no es raiz, indicamos el nivel jerarquico del que partimos
            long nivelJerarquicoInicial = unidad.getNivelJerarquico();

            //añadimos la unidad a las unidades de primer nivel
            unidadesPrimerNivel.add(unidad);
            unidadesSegundoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 1, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            for (Unidad unidad2 : unidadesSegundoNivel) {
                unidadesTercerNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 2, unidad2.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
            for (Unidad unidad3 : unidadesTercerNivel) {
                unidadesCuartoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 3, unidad3.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
            for (Unidad unidad4 : unidadesCuartoNivel) {
                unidadesQuintoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 4, unidad4.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
            for (Unidad unidad5 : unidadesQuintoNivel) {
                unidadesSextoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 5, unidad5.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
            for (Unidad unidad6 : unidadesSextoNivel) {
                unidadesSeptimoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 6, unidad6.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
        } else {
            //si es raiz, partimos del nivel jerarquico 1
            unidadesPrimerNivel = unidadEjb.getUnidadesByNivel((long) 1, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesSegundoNivel = unidadEjb.getUnidadesByNivel((long) 2, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesTercerNivel = unidadEjb.getUnidadesByNivel((long) 3, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesCuartoNivel = unidadEjb.getUnidadesByNivel((long) 4, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesQuintoNivel = unidadEjb.getUnidadesByNivel((long) 5, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesSextoNivel = unidadEjb.getUnidadesByNivel((long) 6, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesSeptimoNivel = unidadEjb.getUnidadesByNivel((long) 7, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        }


        // Subimos los niveles de los unidades para empezar desde la raiz
        if (unidadesPrimerNivel.size() == 0) {
            if (unidadesSegundoNivel.size() == 0) {
                if (unidadesTercerNivel.size() == 0) {
                    if (unidadesCuartoNivel.size() == 0) {
                        if (unidadesQuintoNivel.size() == 0) {
                            if (unidadesSextoNivel.size() == 0) {
                                if (unidadesSeptimoNivel.size() > 0) {
                                    unidadesPrimerNivel.addAll(unidadesSeptimoNivel);
                                    unidadesSeptimoNivel.clear();
                                }
                            } else {
                                unidadesPrimerNivel.addAll(unidadesSextoNivel);
                                unidadesSegundoNivel.addAll(unidadesSeptimoNivel);
                                unidadesSextoNivel.clear();
                                unidadesSeptimoNivel.clear();
                            }
                        } else {
                            unidadesPrimerNivel.addAll(unidadesQuintoNivel);
                            unidadesSegundoNivel.addAll(unidadesSextoNivel);
                            unidadesTercerNivel.addAll(unidadesSeptimoNivel);
                            unidadesQuintoNivel.clear();
                            unidadesSextoNivel.clear();
                            unidadesSeptimoNivel.clear();
                        }
                    } else {
                        unidadesPrimerNivel.addAll(unidadesCuartoNivel);
                        unidadesSegundoNivel.addAll(unidadesQuintoNivel);
                        unidadesTercerNivel.addAll(unidadesSextoNivel);
                        unidadesCuartoNivel.addAll(unidadesSeptimoNivel);
                        unidadesQuintoNivel.clear();
                        unidadesSextoNivel.clear();
                        unidadesSeptimoNivel.clear();
                    }
                } else {
                    unidadesPrimerNivel.addAll(unidadesTercerNivel);
                    unidadesSegundoNivel.addAll(unidadesCuartoNivel);
                    unidadesTercerNivel.addAll(unidadesQuintoNivel);
                    unidadesCuartoNivel.addAll(unidadesSextoNivel);
                    unidadesQuintoNivel.addAll(unidadesSeptimoNivel);
                    unidadesSextoNivel.clear();
                    unidadesSeptimoNivel.clear();
                }
            } else {
                unidadesPrimerNivel.addAll(unidadesSegundoNivel);
                unidadesSegundoNivel.addAll(unidadesTercerNivel);
                unidadesTercerNivel.addAll(unidadesCuartoNivel);
                unidadesCuartoNivel.addAll(unidadesQuintoNivel);
                unidadesQuintoNivel.addAll(unidadesSextoNivel);
                unidadesSextoNivel.addAll(unidadesSeptimoNivel);
                unidadesSeptimoNivel.clear();
            }
        }

        //Obtenemos todas las oficinas principales, dependendientes, organizativas y sir de la unidad raiz.
        List<Oficina> oficinasPrincipales;
        List<Oficina> oficinasAuxiliares;
        List<RelacionOrganizativaOfi> relacionesOrganizativaOfi;
        List<RelacionSirOfi> relacionesSirOfi;
        //Si no es raiz, pasamos el codigo de la raiz para que obtenga en una sola query todas las oficinas de la raiz
        if (unidad.getCodUnidadRaiz() != null && !unidad.getCodUnidadRaiz().getCodigo().equals(unidad.getCodigo())) {
            oficinasPrincipales = oficinaEjb.responsableByUnidadEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            oficinasAuxiliares = oficinaEjb.dependienteByUnidadEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            relacionesOrganizativaOfi = relacionOrganizativaOfiEjb.getOrganizativasCompletoByUnidadEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            relacionesSirOfi = relacionSirOfiEjb.relacionesSirOfiByUnidaddEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        } else { //Si es raiz, devuelve todas las oficinas en una sola query.
            oficinasPrincipales = oficinaEjb.responsableByUnidadEstado(unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            oficinasAuxiliares = oficinaEjb.dependienteByUnidadEstado(unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            relacionesOrganizativaOfi = relacionOrganizativaOfiEjb.getOrganizativasCompletoByUnidadEstado(unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            relacionesSirOfi = relacionSirOfiEjb.relacionesSirOfiByUnidaddEstado(unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        }

        Long end = System.currentTimeMillis();

        //log.info("TIEMPO CARGA ARBOL: " + Utils.formatElapsedTime(end - start));

        //mav.addObject("nodo", nodo);
        mav.addObject("unidadesPrimerNivel", unidadesPrimerNivel);
        mav.addObject("unidadesSegundoNivel", unidadesSegundoNivel);
        mav.addObject("unidadesTercerNivel", unidadesTercerNivel);
        mav.addObject("unidadesCuartoNivel", unidadesCuartoNivel);
        mav.addObject("unidadesQuintoNivel", unidadesQuintoNivel);
        mav.addObject("unidadesSextoNivel", unidadesSextoNivel);
        mav.addObject("unidadesSeptimoNivel", unidadesSeptimoNivel);
        mav.addObject("oficinasPrincipales", oficinasPrincipales);
        mav.addObject("oficinasAuxiliares", oficinasAuxiliares);
        mav.addObject("relacionesOrganizativaOfi", relacionesOrganizativaOfi);
        mav.addObject("relacionesSirOfi", relacionesSirOfi);
        mav.addObject("unidadRaiz", unidad.getCodUnidadSuperior());
        return mav;
    	
    }


    /**
     * Método que obtiene el detalle y el árbol de una oficina.
     *
     * @param request
     * @param idOficina
     * @return
     */
    @RequestMapping(value = "/{idOficina}/detalle", method = RequestMethod.GET)
    public ModelAndView detalle(HttpServletRequest request, @PathVariable String idOficina) throws Exception {

        Long start = System.currentTimeMillis();
        ModelAndView mav = new ModelAndView("oficina/oficinaDetalle");

        // OBTENEMOS LOS DATOS PROPIOS DE LA OFICINA
        // Obtenemos la oficina
        Oficina oficina = oficinaEjb.findFullById(idOficina);
        mav.addObject("oficina", oficina);


        // OBTENEMOS EL ÁRBOL DE LA OFICINA
        //Obtenemos los datos de la unidad
        Unidad unidad = unidadEjb.findFullById(oficina.getCodUoResponsable().getCodigo());

        //Obtenemos en diferentes listas las unidades hasta el septimo nivel
        List<Unidad> unidadesPrimerNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesSegundoNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesTercerNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesCuartoNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesQuintoNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesSextoNivel = new ArrayList<Unidad>();
        List<Unidad> unidadesSeptimoNivel = new ArrayList<Unidad>();

        //Si no es raiz
        if (unidad.getCodUnidadRaiz() != null && !unidad.getCodUnidadRaiz().getCodigo().equals(unidad.getCodigo())) {
            //Como no es raiz, indicamos el nivel jerarquico del que partimos
            long nivelJerarquicoInicial = unidad.getNivelJerarquico();

            //añadimos la unidad a las unidades de primer nivel
            unidadesPrimerNivel.add(unidad);
            unidadesSegundoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 1, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            for (Unidad unidad2 : unidadesSegundoNivel) {
                unidadesTercerNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 2, unidad2.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
            for (Unidad unidad3 : unidadesTercerNivel) {
                unidadesCuartoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 3, unidad3.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
            for (Unidad unidad4 : unidadesCuartoNivel) {
                unidadesQuintoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 4, unidad4.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
            for (Unidad unidad5 : unidadesQuintoNivel) {
                unidadesSextoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 5, unidad5.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
            for (Unidad unidad6 : unidadesSextoNivel) {
                unidadesSeptimoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 6, unidad6.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
            }
        } else {
            //si es raiz, partimos del nivel jerarquico 1
            unidadesPrimerNivel = unidadEjb.getUnidadesByNivel((long) 1, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesSegundoNivel = unidadEjb.getUnidadesByNivel((long) 2, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesTercerNivel = unidadEjb.getUnidadesByNivel((long) 3, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesCuartoNivel = unidadEjb.getUnidadesByNivel((long) 4, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesQuintoNivel = unidadEjb.getUnidadesByNivel((long) 5, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesSextoNivel = unidadEjb.getUnidadesByNivel((long) 6, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            unidadesSeptimoNivel = unidadEjb.getUnidadesByNivel((long) 7, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        }


        // Subimos los niveles de los unidades para empezar desde la raiz
        if (unidadesPrimerNivel.size() == 0) {
            if (unidadesSegundoNivel.size() == 0) {
                if (unidadesTercerNivel.size() == 0) {
                    if (unidadesCuartoNivel.size() == 0) {
                        if (unidadesQuintoNivel.size() == 0) {
                            if (unidadesSextoNivel.size() == 0) {
                                if (unidadesSeptimoNivel.size() > 0) {
                                    unidadesPrimerNivel.addAll(unidadesSeptimoNivel);
                                    unidadesSeptimoNivel.clear();
                                }
                            } else {
                                unidadesPrimerNivel.addAll(unidadesSextoNivel);
                                unidadesSegundoNivel.addAll(unidadesSeptimoNivel);
                                unidadesSextoNivel.clear();
                                unidadesSeptimoNivel.clear();
                            }
                        } else {
                            unidadesPrimerNivel.addAll(unidadesQuintoNivel);
                            unidadesSegundoNivel.addAll(unidadesSextoNivel);
                            unidadesTercerNivel.addAll(unidadesSeptimoNivel);
                            unidadesQuintoNivel.clear();
                            unidadesSextoNivel.clear();
                            unidadesSeptimoNivel.clear();
                        }
                    } else {
                        unidadesPrimerNivel.addAll(unidadesCuartoNivel);
                        unidadesSegundoNivel.addAll(unidadesQuintoNivel);
                        unidadesTercerNivel.addAll(unidadesSextoNivel);
                        unidadesCuartoNivel.addAll(unidadesSeptimoNivel);
                        unidadesQuintoNivel.clear();
                        unidadesSextoNivel.clear();
                        unidadesSeptimoNivel.clear();
                    }
                } else {
                    unidadesPrimerNivel.addAll(unidadesTercerNivel);
                    unidadesSegundoNivel.addAll(unidadesCuartoNivel);
                    unidadesTercerNivel.addAll(unidadesQuintoNivel);
                    unidadesCuartoNivel.addAll(unidadesSextoNivel);
                    unidadesQuintoNivel.addAll(unidadesSeptimoNivel);
                    unidadesSextoNivel.clear();
                    unidadesSeptimoNivel.clear();
                }
            } else {
                unidadesPrimerNivel.addAll(unidadesSegundoNivel);
                unidadesSegundoNivel.addAll(unidadesTercerNivel);
                unidadesTercerNivel.addAll(unidadesCuartoNivel);
                unidadesCuartoNivel.addAll(unidadesQuintoNivel);
                unidadesQuintoNivel.addAll(unidadesSextoNivel);
                unidadesSextoNivel.addAll(unidadesSeptimoNivel);
                unidadesSeptimoNivel.clear();
            }
        }

        //Obtenemos todas las oficinas principales, dependendientes, organizativas y sir de la unidad raiz.
        List<Oficina> oficinasPrincipales;
        List<Oficina> oficinasAuxiliares;
        List<RelacionOrganizativaOfi> relacionesOrganizativaOfi;
        List<RelacionSirOfi> relacionesSirOfi;
        //Si no es raiz, pasamos el codigo de la raiz para que obtenga en una sola query todas las oficinas de la raiz
        if (unidad.getCodUnidadRaiz() != null && !unidad.getCodUnidadRaiz().getCodigo().equals(unidad.getCodigo())) {
            oficinasPrincipales = oficinaEjb.responsableByUnidadEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            oficinasAuxiliares = oficinaEjb.dependienteByUnidadEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            relacionesOrganizativaOfi = relacionOrganizativaOfiEjb.getOrganizativasCompletoByUnidadEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            relacionesSirOfi = relacionSirOfiEjb.relacionesSirOfiByUnidaddEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        } else { //Si es raiz, devuelve todas las oficinas en una sola query.
            oficinasPrincipales = oficinaEjb.responsableByUnidadEstado(unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            oficinasAuxiliares = oficinaEjb.dependienteByUnidadEstado(unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            relacionesOrganizativaOfi = relacionOrganizativaOfiEjb.getOrganizativasCompletoByUnidadEstado(unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            relacionesSirOfi = relacionSirOfiEjb.relacionesSirOfiByUnidaddEstado(unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
        }

        Long end = System.currentTimeMillis();

        //log.info("TIEMPO CARGA ARBOL: " + Utils.formatElapsedTime(end - start));

        //mav.addObject("nodo", nodo);
        mav.addObject("unidadesPrimerNivel", unidadesPrimerNivel);
        mav.addObject("unidadesSegundoNivel", unidadesSegundoNivel);
        mav.addObject("unidadesTercerNivel", unidadesTercerNivel);
        mav.addObject("unidadesCuartoNivel", unidadesCuartoNivel);
        mav.addObject("unidadesQuintoNivel", unidadesQuintoNivel);
        mav.addObject("unidadesSextoNivel", unidadesSextoNivel);
        mav.addObject("unidadesSeptimoNivel", unidadesSeptimoNivel);
        mav.addObject("oficinasPrincipales", oficinasPrincipales);
        mav.addObject("oficinasAuxiliares", oficinasAuxiliares);
        mav.addObject("relacionesOrganizativaOfi", relacionesOrganizativaOfi);
        mav.addObject("relacionesSirOfi", relacionesSirOfi);
        mav.addObject("unidadRaiz", unidad.getCodUnidadSuperior());
        return mav;

    }

}
