package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.utils.Nodo;

import javax.ejb.Local;

/**
 * Created by mgonzalez on 29/03/2016.
 */
@Local
public interface ArbolLocal {

    /**
     * Método que monta el arbol de nodos de la unidad indicada.
     * Se emplea en dir3caib para mostrar el arbol de una unidad
     *
     * @param idUnidad    unidad raiz de la que partimos.
     * @param nodo        nodo en el que se montará todo el árbol.
     * @param estado      estado de las unidades que queremos mostrar en el arbol.
     * @param conOficinas indica si se quieren incluir las oficinas en el organigrama
     */
    void arbolUnidades(String idUnidad, Nodo nodo, String estado, boolean conOficinas) throws Exception;

    /**
     * Metodo que devuelve una estructura de nodos que representan un árbol de oficinas
     *
     * @param idOficina oficina raiz de la que partimos.
     */
    void arbolOficinas(String idOficina, Nodo nodo, String estado) throws Exception;

    /**
     * Método que devuelve el árbol completo(ascendentes y descendentes) de la unidad indicada.
     * Primero obtiene los descendientes llamando a arbolUnidades y después obtiene los ascendentes.
     * Es el que se enmplea para mostrar el arbol en la búsqueda de organismos destinatarios de regweb3
     *
     * @param idUnidad    unidad  de la que partimos.
     * @param nodo        nodo en el que se montará todo el árbol.
     * @param estado      estado de las unidades que queremos mostrar en el arbol.
     * @param conOficinas indica si se quieren incluir las oficinas en el organigrama (es para mostrar el arbol en las
     *                    búsquedas de organismo destinatario de regweb3)
     */
    void arbolUnidadesAscendentes(String idUnidad, Nodo nodo, String estado, boolean conOficinas) throws Exception;
}
