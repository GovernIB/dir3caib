package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Sincronizacion;

import javax.ejb.Local;

/**
 * @author anadal
 */
@Local
public interface ImportadorCatalogoLocal {

    /**
     * @return
     * @throws Exception
     */
    void importarCatalogo(Sincronizacion sincronizacion) throws Exception;

}
