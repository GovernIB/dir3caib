package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Sincronizacion;

import javax.ejb.Local;

/**
 * @author anadal
 */
@Local
public interface ImportadorLocal {

    /**
     * @return
     * @throws Exception
     */
    void importarUnidadesOficinas(Sincronizacion sincronizacion) throws Exception;

}
