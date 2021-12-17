package es.caib.dir3caib.ws.catalogo;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.ws.CatComunidadAutonomaTF;
import es.caib.dir3caib.persistence.model.ws.CatEntidadGeograficaTF;
import es.caib.dir3caib.persistence.model.ws.CatLocalidadTF;
import es.caib.dir3caib.persistence.model.ws.CatProvinciaTF;

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
  List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatPais> obtenerCatPais() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatComunidadAutonomaTF> obtenerCatComunidadAutonoma() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatProvinciaTF> obtenerCatProvincia() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatLocalidadTF> obtenerCatLocalidad() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatEntidadGeograficaTF> obtenerCatEntidadGeografica() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatServicio> obtenerCatServicio() throws Exception;

  @WebMethod
  @RolesAllowed({Dir3caibConstantes.DIR_WS})
  List<CatTipoVia> obtenerCatTipoVia() throws Exception;
}
