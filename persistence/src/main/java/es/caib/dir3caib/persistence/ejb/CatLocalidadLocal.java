package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatLocalidad;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal (Eliminar PKs multiples)
 * Date: 10/10/13
 */
@Local
public interface CatLocalidadLocal extends BaseEjb<CatLocalidad, Long> {

    /**
     * @param codigoLocalidad
     * @param codigoProvincia
     * @param codigoEntidadGeografica
     * @return
     * @throws Exception
     */
    CatLocalidad findByPKs(Long codigoLocalidad, Long codigoProvincia, String codigoEntidadGeografica) throws Exception;

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
    List<CatLocalidad> getAll(String estado) throws Exception;

    /**
     * @param estado
     * @return
     * @throws Exception
     */
    List<CatLocalidad> getByEstado(String estado) throws Exception;

}
