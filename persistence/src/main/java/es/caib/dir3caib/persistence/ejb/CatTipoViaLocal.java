package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoVia;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatTipoViaLocal extends BaseEjb<CatTipoVia, Long> {

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
    List<CatTipoVia> getAll(String estado) throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<CatTipoVia> getByEstado(String estado) throws Exception;
}
