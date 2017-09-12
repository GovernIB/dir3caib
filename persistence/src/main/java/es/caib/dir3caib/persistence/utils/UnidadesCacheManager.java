package es.caib.dir3caib.persistence.utils;

/**
 * Created by mgonzalez on 11/09/2017.
 */

import es.caib.dir3caib.persistence.ejb.UnidadLocal;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.utils.Utils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class UnidadesCacheManager {

    protected final Logger log = Logger.getLogger(getClass());

    private final UnidadLocal unidadEjb;
    public int countFind = 0;
    public int countCache = 0;
    public long findByTime = 0;
    Map<String, Unidad> cacheUnidad = new TreeMap<String, Unidad>();

    public UnidadesCacheManager(UnidadLocal unidadEjb) {
        super();
        this.unidadEjb = unidadEjb;
    }

    /**
     * Constructor que monta la cache de unidades con los identificadores de las unidadesRequeridas que se le pasan por parámetro
     *
     * @param unidadEjb
     * @param unidadesRequeridas unidades responsables de las oficinas que se van a importar
     * @param total
     * @throws Exception
     */
    public UnidadesCacheManager(UnidadLocal unidadEjb, List<List<String>> unidadesRequeridas, int total) throws Exception {
        this(unidadEjb);


        List<Unidad> unidades;
        int count = 0;

        for (List<String> ids : unidadesRequeridas) {
            long start2 = System.currentTimeMillis();


            if (log.isDebugEnabled()) {
                log.debug(" ids.size = " + ids.size());
            }
            //Obtiene la lista de unidades a partir de los identificadores
            unidades = unidadEjb.getListByIds(ids);
            if (log.isDebugEnabled()) {
                log.info(" getListByIds(ids).size = " + unidades.size());
            }

            // Monta la cache de unidad con las unidades obtenidas.
            for (Unidad ca : unidades) {
                cacheUnidad.put(ca.getCodigo(), ca);
                count++;
            }
            long end2 = System.currentTimeMillis();

            log.info(" Cache de Unidades " + count + " / " + total + "   -->   " + Utils.formatElapsedTime(end2 - start2));

        }

        log.info(" Cache Of Unidades. Total = " + count);

    }

    /**
     * Método que obtiene una unidad de la lista de las unidades en cache, si no está en cache, va a bd a buscarla
     *
     * @param codigo código de la unidad.
     * @return
     * @throws Exception
     */
    public Unidad get(String codigo) throws Exception {

        Unidad unidad = cacheUnidad.get(codigo);

        if (unidad == null) { // Si no està en cache
            //Calculamos que se tarda en cogerla de BD
            long start = System.currentTimeMillis();
            unidad = this.unidadEjb.getReference(codigo);
            findByTime = findByTime + (System.currentTimeMillis() - start);
            cacheUnidad.put(codigo, unidad);
            countFind++;
        } else {
            countCache++;
        }
        return unidad;

    }


}
