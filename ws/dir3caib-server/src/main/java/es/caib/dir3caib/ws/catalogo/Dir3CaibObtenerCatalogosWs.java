package es.caib.dir3caib.ws.catalogo;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.ws.CatComunidadAutonomaTF;
import es.caib.dir3caib.persistence.model.ws.CatEntidadGeograficaTF;
import es.caib.dir3caib.persistence.model.ws.CatLocalidadTF;
import es.caib.dir3caib.persistence.model.ws.CatProvinciaTF;
import es.caib.dir3caib.utils.Constants;

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
  public String getVersion();

  @WebMethod
  @PermitAll
  public String getVersionWs();


  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<CatEstadoEntidad> obtenerCatEstadoEntidad() throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<CatPais> obtenerCatPais() throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<CatComunidadAutonomaTF> obtenerCatComunidadAutonoma() throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<CatProvinciaTF> obtenerCatProvincia() throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<CatLocalidadTF> obtenerCatLocalidad() throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<CatEntidadGeograficaTF> obtenerCatEntidadGeografica() throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<Servicio> obtenerCatServicio() throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<CatTipoVia> obtenerCatTipoVia() throws Exception;
}
