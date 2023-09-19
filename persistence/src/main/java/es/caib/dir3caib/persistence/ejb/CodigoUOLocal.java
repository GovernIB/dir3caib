package es.caib.dir3caib.persistence.ejb;


import es.caib.dir3caib.persistence.model.CodigoUnidadOrganica;

import javax.ejb.Local;

@Local
public interface CodigoUOLocal extends BaseEjb<CodigoUnidadOrganica, Long> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;

}
