package es.caib.dir3caib.ws.catalogo;

import es.caib.dir3caib.persistence.model.ws.*;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.ws.v2.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Fundació BIT.
 * 
 * @author earrivi Date: 12/02/14
 */

@WebService(
/*
 * name="ObtenerCatalogos", targetNamespace = "http://www.caib.es/dir3caib"
 */
)
public interface Dir3CaibObtenerCatalogosWs {

  @WebMethod
  @PermitAll
  String getVersion();

  @WebMethod
  @PermitAll
  String getVersionWs();


  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatEstadoEntidad> obtenerCatEstadoEntidad() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatEstadoEntidadWs> obtenerCatEstadoEntidadV2() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatEstadoEntidadWs> obtenerCatEstadoEntidadByEstado(String estado) throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception;
  
  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatNivelAdministracionWs> obtenerCatNivelAdministracionV2() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatNivelAdministracionWs> obtenerCatNivelAdministracionByEstado(String estado) throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatPais> obtenerCatPais() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatPaisWs> obtenerCatPaisV2() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatPaisWs> obtenerCatPaisByEstado(String estado) throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatComunidadAutonomaTF> obtenerCatComunidadAutonoma() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatComunidadAutonomaWs> obtenerCatComunidadAutonomaV2() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatComunidadAutonomaWs> obtenerCatComunidadAutonomaByEstado(String estado) throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatProvinciaTF> obtenerCatProvincia() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatProvinciaWs> obtenerCatProvinciaV2() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatProvinciaWs> obtenerCatProvinciaByEstado(String estado) throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatLocalidadTF> obtenerCatLocalidad() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatLocalidadWs> obtenerCatLocalidadV2() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatLocalidadWs> obtenerCatLocalidadByEstado(String estado) throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatEntidadGeograficaTF> obtenerCatEntidadGeografica() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatEntidadGeograficaWs> obtenerCatEntidadGeograficaV2() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatEntidadGeograficaWs> obtenerCatEntidadGeograficaByEstado(String estado) throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<Servicio> obtenerCatServicio() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<Servicio> obtenerCatServicioUO() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatTipoVia> obtenerCatTipoVia() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatTipoViaWs> obtenerCatTipoViaV2() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatTipoViaWs> obtenerCatTipoViaByEstado(String estado) throws Exception;
}
