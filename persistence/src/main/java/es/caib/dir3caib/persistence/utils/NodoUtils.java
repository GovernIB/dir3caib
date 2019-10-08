package es.caib.dir3caib.persistence.utils;

import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.Unidad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgonzalez on 03/06/2016.
 */
public class NodoUtils {


    /**
     * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
     * se devuelve codigo y denominación
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static List<Nodo> getNodoListMinimo(List<Object[]> result) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();

        for (Object[] object : result) {
            Nodo nodo = new Nodo((String) object[0], (String) object[1], "", "", "", "");

            nodos.add(nodo);
        }

        return nodos;
    }

    /**
     * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
     * se devuelve codigo, denominación y estado
     * @param result
     * @return
     * @throws Exception
     */
    public static List<Nodo> getNodoList(List<Object[]> result) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();

        for (Object[] object : result) {
            Nodo nodo = new Nodo((String) object[0], (String) object[1], (String) object[2], "", "", "");

            nodos.add(nodo);
        }

        return nodos;
    }


    /**
     * Convierte los resultados de una query de unidades en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static List<Nodo> getNodoListExtendido(List<Object[]> result) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();

        for (Object[] object : result) {
            // UNIDADES
            //object[0] --> codigo
            //object[1] --> denominacion
            //object[2] --> descripcionEstado
            //object[3] --> raiz.codigo
            //object[4] --> raiz.denominacion
            //object[5] --> superior.codigo
            //object[6] --> superior.denominacion
            //object[7] --> localidad

            String obj0 = "";
            String obj1 = "";
            String obj2 = "";
            String obj3 = "";
            String obj4 = "";
            String obj5 = "";
            String obj6 = "";
            String obj7 = "";
            if (object[0] != null) {
                obj0 = (String) object[0];
            }

            if (object[1] != null) {
                obj1 = (String) object[1];
            }

            if (object[2] != null) {
                obj2 = (String) object[2];
            }

            if (object[3] != null) {
                obj3 = (String) object[3];
            }

            if (object[4] != null) {
                obj4 = (String) object[4];
            }

            if (object[5] != null) {
                obj5 = (String) object[5];
            }

            if (object[6] != null) {
                obj6 = (String) object[6];
            }

            if (object[7] != null) {
                obj7 = (String) object[7];
            }


            Nodo nodo = new Nodo(obj0, obj1, obj2, obj4 + " - " + obj3, obj6 + " - " + obj5, obj7);


            nodos.add(nodo);
        }

        return nodos;
    }

    /**
     * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
     * donde se devuelve el codigo, denominación, denominación de unidad raiz, denominación de unidad superior
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static List<Nodo> getNodoListUnidadRaizUnidadSuperior(List<Object[]> result) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();

        for (Object[] object : result) {
            Nodo nodo = new Nodo((String) object[0], (String) object[1], "", (String) object[2], (String) object[3], "");

            nodos.add(nodo);
        }

        return nodos;
    }


    public static List<Nodo> getNodoListUnidad(List<Unidad> result) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();
        String edpPrincipal = "";
        for (Unidad unidad : result) {
            if (unidad.getCodEdpPrincipal() != null) {
                edpPrincipal = unidad.getCodEdpPrincipal().getCodigo() + " - " + unidad.getCodEdpPrincipal().getDenominacion();
            }
            Nodo nodo = new Nodo(unidad.getCodigo(), unidad.getDenominacion(), unidad.getEstado().getDescripcionEstadoEntidad(), unidad.getCodUnidadRaiz().getCodigo() + " - " + unidad.getCodUnidadRaiz().getDenominacion(), unidad.getCodUnidadSuperior().getCodigo() + " - " + unidad.getCodUnidadSuperior().getDenominacion(), "", false, unidad.isEsEdp(), edpPrincipal);

            nodos.add(nodo);
        }

        return nodos;
    }

    public static List<Nodo> getNodoListOficina(List<Oficina> result) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();
        String ofiResponsable = "";
        for (Oficina oficina : result) {
            if (oficina.getCodOfiResponsable() != null) {
                ofiResponsable = oficina.getCodOfiResponsable().getCodigo() + " - " + oficina.getCodOfiResponsable().getDenominacion();
            }
            Nodo nodo = new Nodo(oficina.getCodigo(), oficina.getDenominacion(), oficina.getEstado().getDescripcionEstadoEntidad(), ofiResponsable, oficina.getCodUoResponsable().getCodigo() + " - " + oficina.getCodUoResponsable().getDenominacion(), "", oficina.getOficinaSir());

            nodos.add(nodo);
        }

        return nodos;
    }
}
