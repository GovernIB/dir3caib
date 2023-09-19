package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.NifCifUnidadOrganica;

import javax.ejb.Local;

@Local
public interface NifCifUOLocal extends BaseEjb<NifCifUnidadOrganica, Long> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;
}
