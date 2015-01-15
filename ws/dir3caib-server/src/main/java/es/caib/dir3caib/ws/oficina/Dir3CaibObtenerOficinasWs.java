package es.caib.dir3caib.ws.oficina;

import es.caib.dir3caib.persistence.model.ws.OficinaTF;
import es.caib.dir3caib.utils.Constants;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Fundació BIT.
 * 
 * @author earrivi
 * @author anadal (seguretat) Date: 12/02/14
 */
@WebService(
/*
 * name="ObtenerOficinas", targetNamespace = "http://www.caib.es/dir3caib"
 */)
public interface Dir3CaibObtenerOficinasWs /* extends CommonMethods */{

  @WebMethod
  @PermitAll
  public String getVersion();

  @WebMethod
  @PermitAll
  public String getVersionWs();

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public OficinaTF obtenerOficina(String codigo, String fechaActualizacion, String fechaSincronizacion) throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<OficinaTF> obtenerArbolOficinas(String codigo, String fechaActualizacion, String fechaSincronizacion)
      throws Exception;

  @WebMethod
  @RolesAllowed({ Constants.DIR_ADMIN })
  public List<OficinaTF> obtenerOficinasSIRUnidad(String codigoUnidad)
      throws Exception;
}
