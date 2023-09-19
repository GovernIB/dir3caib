package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoCodigoFuenteExterna;

public interface CatTipoCodigoFuenteExternaLocal extends BaseEjb<CatTipoCodigoFuenteExterna, Long> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;
}

