package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ServicioUO;

import javax.ejb.Local;

@Local
public interface ServicioUOLocal extends BaseEjb<ServicioUO, Long> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;
}
