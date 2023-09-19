package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatPoder;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author jagarcia
 * Date: 02/12/21
 */
@Local
public interface CatPoderLocal extends BaseEjb<CatPoder, Long> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;
}
