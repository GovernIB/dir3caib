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

    List<CatEstadoEntidad> obtenerCatEstadoEntidad() throws Exception;

    List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception;

    List<CatPais> obtenerCatPais() throws Exception;

    List<CatComunidadAutonomaTF> obtenerCatComunidadAutonoma() throws Exception;

    List<CatProvinciaTF> obtenerCatProvincia() throws Exception;

    List<CatLocalidadTF> obtenerCatLocalidad() throws Exception;

    List<CatEntidadGeograficaTF> obtenerCatEntidadGeografica() throws Exception;

    List<CatServicio> obtenerCatServicio() throws Exception;

    List<CatTipoVia> obtenerCatTipoVia() throws Exception;

}
