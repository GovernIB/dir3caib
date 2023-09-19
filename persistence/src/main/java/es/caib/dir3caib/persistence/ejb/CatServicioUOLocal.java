package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatServicioUO;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatServicioUOLocal extends BaseEjb<CatServicioUO, Long> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;
}
