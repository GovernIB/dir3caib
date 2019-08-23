package es.caib.dir3caib.back.controller.unidad;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.form.UnidadBusquedaForm;
import es.caib.dir3caib.persistence.ejb.ObtenerUnidadesLocal;
import es.caib.dir3caib.persistence.ejb.RelacionOrganizativaOfiLocal;
import es.caib.dir3caib.persistence.ejb.RelacionSirOfiLocal;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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


    @EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
    private RelacionOrganizativaOfiLocal relacionOrganizativaOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionSirOfiEJB/local")
    private RelacionSirOfiLocal relacionSirOfiEjb;

    @EJB(mappedName = "dir3caib/ObtenerUnidadesEJB/local")
    private ObtenerUnidadesLocal obtenerUnidadesEjb;


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
     * Método que obtiene el detalle y el árbol de una unidad.
     *
     * @param request
     * @param codUnidad
     * @return
     */
    @RequestMapping(value = "/{codUnidad}/detalle", method = RequestMethod.GET)
    public ModelAndView mostrarArbolUnidades(HttpServletRequest request, @PathVariable String codUnidad) throws Exception {

        Long start = System.currentTimeMillis();
        ModelAndView mav = new ModelAndView("unidad/unidadDetalle");

        //Obtenemos los datos básicos de la unidad que nos indican
        Unidad unidad = unidadEjb.findFullById(codUnidad);
        mav.addObject("unidad", unidad);

        //Obtenemos los historicos anteriores de la unidad indicada
        Set<Unidad> unidadesHistoricasAnteriores = unidadEjb.historicosAnteriores(codUnidad);
        mav.addObject("historicosAnteriores", unidadesHistoricasAnteriores);

        //Obtenemos los históricos finales hacia delante.
        Set<Unidad> unidadesHistoricasFinales = new HashSet<Unidad>();
        obtenerUnidadesEjb.obtenerHistoricosFinales(unidad, unidadesHistoricasFinales);
        mav.addObject("historicosFinales", unidadesHistoricasFinales);

        //Obtenemos las oficinas que registran a la Unidad
        List<Oficina> oficinasRegistran = oficinaEjb.obtenerOficinasRegistran(unidad.getCodigo());
        mav.addObject("oficinasRegistran", oficinasRegistran);

        Boolean unidadExtinguida = false;

        //Entramos si es una Unidad Vigente
        if (Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE.equals(unidad.getEstado().getCodigoEstadoEntidad())) {

            //Obtenemos en diferentes listas las unidades hasta el septimo nivel
            List<Unidad> unidadesPrimerNivel = new ArrayList<Unidad>();
            List<Unidad> unidadesSegundoNivel = new ArrayList<Unidad>();
            List<Unidad> unidadesTercerNivel = new ArrayList<Unidad>();
            List<Unidad> unidadesCuartoNivel = new ArrayList<Unidad>();
            List<Unidad> unidadesQuintoNivel = new ArrayList<Unidad>();
            List<Unidad> unidadesSextoNivel = new ArrayList<Unidad>();
            List<Unidad> unidadesSeptimoNivel = new ArrayList<Unidad>();

            //Si no tiene Nivel Jerárquico 0
            if (unidad.getNivelJerarquico() != 0) {
                //Indicamos el nivel jerarquico del que partimos
                long nivelJerarquicoInicial = unidad.getNivelJerarquico();

                //añadimos la unidad a las unidades de primer nivel
                unidadesPrimerNivel.add(unidad);
                unidadesSegundoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 1, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
                if (unidadesSegundoNivel.size() != 0) {
                    for (Unidad unidad2 : unidadesSegundoNivel) {
                        unidadesTercerNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 2, unidad2.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
                    }
                    if (unidadesTercerNivel.size() != 0) {
                        for (Unidad unidad3 : unidadesTercerNivel) {
                            unidadesCuartoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 3, unidad3.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
                        }
                        if (unidadesCuartoNivel.size() != 0) {
                            for (Unidad unidad4 : unidadesCuartoNivel) {
                                unidadesQuintoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 4, unidad4.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
                            }
                            if (unidadesQuintoNivel.size() != 0) {
                                for (Unidad unidad5 : unidadesQuintoNivel) {
                                    unidadesSextoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 5, unidad5.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
                                }
                                if (unidadesSextoNivel.size() != 0) {
                                    for (Unidad unidad6 : unidadesSextoNivel) {
                                        unidadesSeptimoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior(nivelJerarquicoInicial + 6, unidad6.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                //si tiene Nivel Jerárquico = 0
                //Només carregam fins a nivell 3 per no sobrecarregar l'arbre
                unidadesPrimerNivel = unidadEjb.getUnidadesByNivelByUnidadSuperior((long) 1, unidad.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
                for (Unidad unidad1 : unidadesPrimerNivel) {
                    unidadesSegundoNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior((long) 2, unidad1.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
                }
                for (Unidad unidad2 : unidadesSegundoNivel) {
                    unidadesTercerNivel.addAll(unidadEjb.getUnidadesByNivelByUnidadSuperior((long) 3, unidad2.getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE));
                }
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

            // Obtenemos todas las oficinas principales, dependendientes, organizativas y sir de la unidad raiz.
            List<Oficina> oficinasPrincipales = new ArrayList<Oficina>();
            List<Oficina> oficinasAuxiliares = new ArrayList<Oficina>();
            List<RelacionOrganizativaOfi> relacionesOrganizativaOfi = new ArrayList<RelacionOrganizativaOfi>();
            List<RelacionSirOfi> relacionesSirOfi = new ArrayList<RelacionSirOfi>();

            // Si no es Nivel Jerárquico 0, pasamos el codigo de la raiz para que obtenga en una sola query todos los datos
            // Para los de Nivel Jerárquico = 0, no se buscan oficinas ni relaciones
            if (unidad.getNivelJerarquico() != 0) {
                oficinasPrincipales = oficinaEjb.responsableByUnidadEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
                oficinasAuxiliares = oficinaEjb.dependienteByUnidadEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
                relacionesOrganizativaOfi = relacionOrganizativaOfiEjb.getOrganizativasCompletoByUnidadEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
                relacionesSirOfi = relacionSirOfiEjb.relacionesSirOfiByUnidaddEstado(unidad.getCodUnidadRaiz().getCodigo(), Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            }

            Long end = System.currentTimeMillis();
            log.info("TIEMPO CARGA ARBOL: " + Utils.formatElapsedTime(end - start));

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
        }

        //Calculamos los históricos hasta el final, si la unidad no es vigente
        if (!Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE.equals(unidad.getEstado().getCodigoEstadoEntidad())) {
            unidadExtinguida = true;
            Nodo nodo = new Nodo();
            obtenerUnidadesEjb.montarHistoricosFinales(unidad, nodo, 1);
            mav.addObject("nodo", nodo);
        }

        mav.addObject("unidadRaiz", unidad.getCodUnidadSuperior());
        mav.addObject("unidadExtinguida", unidadExtinguida);
        return mav;

    }

}
