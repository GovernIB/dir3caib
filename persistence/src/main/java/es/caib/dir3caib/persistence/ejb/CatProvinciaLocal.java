package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatProvincia;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatProvinciaLocal extends BaseEjb<CatProvincia, Long> {

    /**
     * @param idComunidadAutonoma
     * @return
     * @throws Exception
     */
    List<CatProvincia> getByComunidadAutonoma(Long idComunidadAutonoma) throws Exception;

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;

    /**
     * @param estado
     * @return
     * @throws Exception
     */
    List<CatProvincia> getAll(String estado) throws Exception;

    /**
     * @param estado
     * @return
     * @throws Exception
     */
    List<CatProvincia> getByEstado(String estado) throws Exception;
}
