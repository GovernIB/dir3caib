package es.caib.dir3caib.ws.unidad;

import es.caib.dir3caib.persistence.ejb.ObtenerUnidadesLocal;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;
import es.caib.dir3caib.persistence.utils.Versio;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Constants;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.wsf.spi.annotation.TransportGuarantee;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * Created by Fundació BIT.
 * 
 * @author earrivi
 * @author anadal (seguretat) Date: 12/02/14
 */

@SecurityDomain(es.caib.dir3caib.utils.Constants.SECURITY_DOMAIN)
@Stateless(name = Dir3CaibObtenerUnidadesImpl.NAME + "Ejb")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@RolesAllowed({ Constants.DIR_ADMIN })
@WebService(name = Dir3CaibObtenerUnidadesImpl.NAME_WS, portName = Dir3CaibObtenerUnidadesImpl.NAME_WS, serviceName = Dir3CaibObtenerUnidadesImpl.NAME_WS
    + "Service", targetNamespace = "http://www.caib.es/dir3caib", endpointInterface = "es.caib.dir3caib.ws.unidad.Dir3CaibObtenerUnidadesWs")
@WebContext(contextRoot = "/dir3caib/ws", urlPattern = "/" + Dir3CaibObtenerUnidadesImpl.NAME, transportGuarantee = TransportGuarantee.NONE, secureWSDLAccess = false, authMethod = "WSBASIC")
public class Dir3CaibObtenerUnidadesImpl implements Dir3CaibObtenerUnidadesWs {

  public static final String NAME = "Dir3CaibObtenerUnidades";

  public static final String NAME_WS = NAME + "Ws";

  @EJB(mappedName = "dir3caib/ObtenerUnidadesEJB/local")
  protected ObtenerUnidadesLocal obtenerUnidadesEjb;

  @Override
  public String getVersion() {

    return Versio.VERSIO + (Configuracio.isCAIB() ? "-caib" : "");
  }

  @Override
  public String getVersionWs() {

    return "1.0";
  }

  /**
   * Método que devuelve una UnidadTF( que se transfiere) a partir del código
   * indicado y en función de la fecha de actualización
   * 
   * @param codigo
   *          código de la unidad a transferir
   * @param fechaActualizacion
   *          fecha en la que se realiza la actualización
   */
  @Override
  public UnidadTF obtenerUnidad(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

    return obtenerUnidadesEjb.obtenerUnidad(codigo, fechaActualizacion, fechaSincronizacion);
  }

  /**
   * Método que devuelve la lista de UnidadTF( que se transfiere) a partir del
   * código indicado y en función de la fecha de actualización
   * 
   * @param codigo
   *          código de la unidad raiz
   * @param fechaActualizacion
   *          fecha en la que se realiza la actualización
   */
  @Override
  public List<UnidadTF> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion)
      throws Exception {

    return obtenerUnidadesEjb.obtenerArbolUnidades(codigo, fechaActualizacion, fechaSincronizacion);
  }

}
