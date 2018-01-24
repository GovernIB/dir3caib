package es.caib.dir3caib.ws.unidad;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;

/**
 * Created by Fundació BIT.
 * Interface que define los métodos para exportar la información de las unidades de dir3caib
 * 
 * @author earrivi
 * @author anadal (seguretat) Date: 12/02/14
 */
@WebService(
/*
 * name="ObtenerUnidades", targetNamespace = "http://www.caib.es/dir3caib"
 */)
public interface Dir3CaibObtenerUnidadesWs {

  @WebMethod
  @PermitAll
  String getVersion();

  @WebMethod
  @PermitAll
  String getVersionWs();

  @WebMethod
  @RolesAllowed({ Dir3caibConstantes.DIR_ADMIN })
  UnidadTF obtenerUnidad(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

  @WebMethod
  @RolesAllowed({ Dir3caibConstantes.DIR_ADMIN })
  List<UnidadTF> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion)
      throws Exception;

  @WebMethod
  @RolesAllowed({ Dir3caibConstantes.DIR_ADMIN })
  List<UnidadTF> obtenerArbolUnidadesDestinatarias(String codigo)
      throws Exception;

  @WebMethod
  @RolesAllowed({ Dir3caibConstantes.DIR_ADMIN })
  Date obtenerFechaUltimaActualizacion()
          throws Exception;

}
