package es.caib.dir3caib.persistence.utils;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by mgonzalez on 11/09/2017.
 * Clase que representa un String "codigoUnidad_codigoOficina"
 */
public class CacheUnidadOficina {

    Set<String> caches = new TreeSet<String>();


    public CacheUnidadOficina(List<String> uniofi) {
        this.caches.addAll(uniofi);
    }


    /**
     * Método que devuelve true si existe el par "unidad_oficina" en la lista
     *
     * @param unidad  código de la unidad
     * @param oficina código de la oficina
     * @return
     */
    public boolean existsUnidadOficina(String unidad, String oficina) {
        return this.caches.contains(unidad + "_" + oficina);
    }


}
