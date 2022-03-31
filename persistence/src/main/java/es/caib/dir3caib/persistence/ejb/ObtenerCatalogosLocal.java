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
    List<CatEstadoEntidadWs> obtenerCatEstadoEntidadWs(String estado) throws Exception;

    List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception;
    List<CatNivelAdministracionWs> obtenerCatNivelAdministracionWs(String estado) throws Exception;

    List<CatPais> obtenerCatPais() throws Exception;
    List<CatPaisWs> obtenerCatPaisWs(String estado) throws Exception;


    List<CatComunidadAutonomaTF> obtenerCatComunidadAutonoma() throws Exception;
    List<CatComunidadAutonomaWs> obtenerCatComunidadAutonomaWs(String estado) throws Exception;


    List<CatProvinciaTF> obtenerCatProvincia() throws Exception;
    List<CatProvinciaWs> obtenerCatProvinciaWs(String estado) throws Exception;

    List<CatLocalidadTF> obtenerCatLocalidad() throws Exception;
    List<CatLocalidadWs> obtenerCatLocalidadWs(String estado) throws Exception ;

    List<CatEntidadGeograficaTF> obtenerCatEntidadGeografica() throws Exception;
    List<CatEntidadGeograficaWs> obtenerCatEntidadGeograficaWs(String estado) throws Exception;


    List<Servicio> obtenerCatServicio() throws Exception;

    List<Servicio> obtenerCatServicioUO() throws Exception;

    List<CatTipoVia> obtenerCatTipoVia() throws Exception;
    List<CatTipoViaWs> obtenerCatTipoViaWs(String estado) throws Exception;


}
