package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.utils.Nodo;

import javax.ejb.Local;

/**
 * Created by mgonzalez on 29/03/2016.
 */
@Local
public interface ArbolLocal {

    /**
     * Metodo que devuelve una estructura de nodos que representan un árbol de unidades
     *
     * @param idUnidad    unidad raiz de la que partimos.
     * @param nodo        nodo en el que se montará todo el árbol.
     * @param estado      estado de las unidades que queremos mostrar en el arbol.
     * @param conOficinas indica si se quieren incluir las oficinas en el organigrama
     * @return Nodo (arbol)
     */
    public void arbolUnidades(String idUnidad, Nodo nodo, String estado, boolean conOficinas) throws Exception;

    public void arbolOficinas(String idOficina, Nodo nodo, String estado) throws Exception;
}
