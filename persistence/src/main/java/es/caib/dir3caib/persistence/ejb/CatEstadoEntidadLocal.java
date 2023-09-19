package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatEstadoEntidad;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatEstadoEntidadLocal extends BaseEjb<CatEstadoEntidad, String> {

    CatEstadoEntidad findByCodigo(String codigo) throws Exception;

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<CatEstadoEntidad> getByEstado(String estado) throws Exception;
}
