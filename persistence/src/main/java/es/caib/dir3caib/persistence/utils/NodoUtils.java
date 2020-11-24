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

            String[] obj = new String[object.length];

            // copy elements from object array to string array
            for (int i = 0; i < object.length; i++)
                obj[i] = String.valueOf(object[i]);


            Nodo nodo = new Nodo(obj[0], obj[1], obj[2], obj[4] + " - " + obj[3], obj[6] + " - " + obj[5], obj[7]);


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



    public static List<Nodo> getNodoListOpenData(List<Object[]> result) throws Exception {

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
            //object[8] --> esEdp
            //object[9] --> nivelJerarquico
            //object[10] --> nifcif
            //object[11] --> nivelAdministracion.descripcionNivelAdministracion
            //object[12] --> descripcionTipoUnidadOrganica
            //object[13] --> descripcionTipoVia
            //object[14] --> nombreVia
            //object[15] --> numVia
            //object[16] --> complemento
            //object[17] --> codAmbitoTerritorial.descripcionAmbito
            //object[18] --> codAmbPais.descripcionPais
            //object[19] --> codAmbComunidad.descripcionComunidad
            //object[20] --> codAmbProvincia.descripcionProvincia
            //object[21] --> codAmbIsla.descripcionIsla



            String[] obj = new String[object.length];

            // copy elements from object array to string array
            for (int i = 0; i < object.length; i++) {
                obj[i] = String.valueOf(object[i]);
            }
            Nodo nodo = new Nodo(obj[0], obj[1], obj[2], obj[4] + " - " + obj[3], obj[6] + " - " + obj[5], obj[7],Boolean.parseBoolean(obj[8]),Long.parseLong(obj[9]), obj[10],obj[11], obj[12],obj[13] + " " + obj[14]+ ", " + obj[15]+ ", "+ obj[16]+ ", " + obj[17],obj[18],obj[19],obj[20],obj[21],obj[22]);


            nodos.add(nodo);
        }

        return nodos;
    }



}
