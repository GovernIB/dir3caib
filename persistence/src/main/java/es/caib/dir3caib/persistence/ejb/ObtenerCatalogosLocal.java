package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ws.*;
import es.caib.dir3caib.persistence.model.ws.v2.*;


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
    List<CatEstadoEntidadWs> obtenerCatEstadoEntidadWs() throws Exception;
    List<CatEstadoEntidadWs> obtenerCatEstadoEntidadByEstado(String estado) throws Exception;

    List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception;
    List<CatNivelAdministracionWs> obtenerCatNivelAdministracionWs() throws Exception;
    List<CatNivelAdministracionWs> obtenerCatNivelAdministracionByEstado(String estado) throws Exception;

    List<CatPais> obtenerCatPais() throws Exception;
    List<CatPaisWs> obtenerCatPaisWs() throws Exception;
    List<CatPaisWs> obtenerCatPaisByEstado(String estado) throws Exception;

    List<CatComunidadAutonomaTF> obtenerCatComunidadAutonoma() throws Exception;
    List<CatComunidadAutonomaWs> obtenerCatComunidadAutonomaWs() throws Exception;
    List<CatComunidadAutonomaWs> obtenerCatComunidadAutonomaByEstado(String estado) throws Exception;

    List<CatProvinciaTF> obtenerCatProvincia() throws Exception;
    List<CatProvinciaWs> obtenerCatProvinciaWs() throws Exception;
    List<CatProvinciaWs> obtenerCatProvinciaByEstado(String estado) throws Exception;

    List<CatLocalidadTF> obtenerCatLocalidad() throws Exception;
    List<CatLocalidadWs> obtenerCatLocalidadWs() throws Exception;
    List<CatLocalidadWs> obtenerCatLocalidadByEstado(String estado) throws Exception;

    List<CatEntidadGeograficaTF> obtenerCatEntidadGeografica() throws Exception;
    List<CatEntidadGeograficaWs> obtenerCatEntidadGeograficaWs() throws Exception;
    List<CatEntidadGeograficaWs> obtenerCatEntidadGeograficaByEstado(String estado) throws Exception;

    List<Servicio> obtenerCatServicio() throws Exception;

    List<Servicio> obtenerCatServicioUO() throws Exception;

    List<CatTipoVia> obtenerCatTipoVia() throws Exception;
    List<CatTipoViaWs> obtenerCatTipoViaWs() throws Exception;
    List<CatTipoViaWs> obtenerCatTipoViaByEstado(String estado) throws Exception;

}
