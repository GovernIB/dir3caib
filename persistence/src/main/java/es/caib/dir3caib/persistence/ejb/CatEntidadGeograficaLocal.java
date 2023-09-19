package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatEntidadGeografica;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface CatEntidadGeograficaLocal extends BaseEjb<CatEntidadGeografica, String> {

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
    List<CatEntidadGeografica> getByEstado(String estado) throws Exception;

}
