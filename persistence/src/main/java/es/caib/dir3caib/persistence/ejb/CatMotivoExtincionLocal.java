package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatMotivoExtincion;

import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatMotivoExtincionLocal extends BaseEjb<CatMotivoExtincion, String> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;
}
