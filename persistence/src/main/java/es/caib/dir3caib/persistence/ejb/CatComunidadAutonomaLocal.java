package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatComunidadAutonomaLocal extends BaseEjb<CatComunidadAutonoma, Long> {

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
    List<CatComunidadAutonoma> getByEstado(String estado) throws Exception;
}
