package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.ws.CatComunidadAutonomaTF;
import es.caib.dir3caib.persistence.model.ws.CatEntidadGeograficaTF;
import es.caib.dir3caib.persistence.model.ws.CatLocalidadTF;
import es.caib.dir3caib.persistence.model.ws.CatProvinciaTF;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 12/02/14
 */
@Local
public interface ObtenerCatalogosLocal {

    public List<CatEstadoEntidad> obtenerCatEstadoEntidad() throws Exception;

    public List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception;

    public List<CatPais> obtenerCatPais() throws Exception;

    public List<CatComunidadAutonomaTF> obtenerCatComunidadAutonoma() throws Exception;

    public List<CatProvinciaTF> obtenerCatProvincia() throws Exception;

    public List<CatLocalidadTF> obtenerCatLocalidad() throws Exception;

    public List<CatEntidadGeograficaTF> obtenerCatEntidadGeografica() throws Exception;


}
