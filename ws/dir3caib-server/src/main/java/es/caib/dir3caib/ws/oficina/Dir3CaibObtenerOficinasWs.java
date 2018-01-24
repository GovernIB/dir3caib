package es.caib.dir3caib.ws.oficina;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.ws.OficinaTF;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;

/**
 * Created by Fundació BIT.
 * Interface que define los métodos para exportar la información de las oficinas de dir3caib
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
  String getVersion();

  @WebMethod
  @PermitAll
  String getVersionWs();

  @WebMethod
  @RolesAllowed({ Dir3caibConstantes.DIR_ADMIN })
  OficinaTF obtenerOficina(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

  @WebMethod
  @RolesAllowed({ Dir3caibConstantes.DIR_ADMIN })
  List<OficinaTF> obtenerArbolOficinas(String codigo, Date fechaActualizacion, Date fechaSincronizacion)
      throws Exception;

  @WebMethod
  @RolesAllowed({ Dir3caibConstantes.DIR_ADMIN })
  List<OficinaTF> obtenerOficinasSIRUnidad(String codigoUnidad)
      throws Exception;

  @WebMethod
  @RolesAllowed({ Dir3caibConstantes.DIR_ADMIN })
  Date obtenerFechaUltimaActualizacion()
          throws Exception;
}
