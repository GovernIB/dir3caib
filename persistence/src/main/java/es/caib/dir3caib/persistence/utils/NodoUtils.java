package es.caib.dir3caib.persistence.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgonzalez on 03/06/2016.
 */
public class NodoUtils {


    /**
     * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
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
     *
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
            //object[2] --> raiz.codigo
            //object[3] --> raiz.denominacion
            //object[4] --> superior.codigo
            //object[5] --> superior.denominacion
            //object[6] --> localidad
            String obj0 = "";
            String obj1 = "";
            String obj2 = "";
            String obj3 = "";
            String obj4 = "";
            String obj5 = "";
            String obj6 = "";
            if (object[0] != null) {
                obj0 = (String) object[0];
            }
            ;
            if (object[1] != null) {
                obj1 = (String) object[1];
            }
            ;
            if (object[2] != null) {
                obj2 = (String) object[2];
            }
            ;
            if (object[3] != null) {
                obj3 = (String) object[3];
            }
            ;
            if (object[4] != null) {
                obj4 = (String) object[4];
            }
            ;
            if (object[5] != null) {
                obj5 = (String) object[5];
            }
            ;
            if (object[6] != null) {
                obj6 = (String) object[6];
            }
            ;

            Nodo nodo = new Nodo(obj0, obj1, "", obj3 + " - " + obj2, obj5 + " - " + obj4, obj6);


            nodos.add(nodo);
        }

        return nodos;
    }
}
