package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Sincronizacion;

import javax.ejb.Local;
import java.util.Date;

/**
 * @author anadal
 */
@Local
public interface ImportadorCatalogoLocal {

    /**
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws Exception
     */
    public Descarga descargarCatalogoWS(Date fechaInicio, Date fechaFin) throws Exception;

    /**
     * @return
     * @throws Exception
     */
    public void importarCatalogo(Sincronizacion sincronizacion) throws Exception;

}
