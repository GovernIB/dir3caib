package es.caib.dir3caib.ws.oficina;

import es.caib.dir3caib.persistence.ejb.ObtenerOficinasLocal;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.ws.OficinaTF;
import es.caib.dir3caib.persistence.model.ws.v2.OficinaWs;
import es.caib.dir3caib.persistence.utils.Versio;
import es.caib.dir3caib.utils.Configuracio;
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

@SecurityDomain(Dir3caibConstantes.SECURITY_DOMAIN)
@Stateless(name = Dir3CaibObtenerOficinasImpl.NAME + "Ejb")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@RolesAllowed({Dir3caibConstantes.DIR_WS})
@WebService(name = Dir3CaibObtenerOficinasImpl.NAME_WS, portName = Dir3CaibObtenerOficinasImpl.NAME_WS, serviceName = Dir3CaibObtenerOficinasImpl.NAME_WS
    + "Service", endpointInterface = "es.caib.dir3caib.ws.oficina.Dir3CaibObtenerOficinasWs")
@WebContext(contextRoot = "/dir3caib/ws", urlPattern = "/" + Dir3CaibObtenerOficinasImpl.NAME, transportGuarantee = TransportGuarantee.NONE, secureWSDLAccess = false, authMethod = "WSBASIC")
public class Dir3CaibObtenerOficinasImpl /* extends CommonMethodsImpl */implements
    Dir3CaibObtenerOficinasWs {

  public static final String NAME = "Dir3CaibObtenerOficinas";

  public static final String NAME_WS = NAME + "Ws";

  @EJB(mappedName = "dir3caib/ObtenerOficinasEJB/local")
  protected ObtenerOficinasLocal obtenerOficinasEjb;

  @Override
  public String getVersion() {

    return Versio.VERSIO + (Configuracio.isCAIB() ? "-caib" : "");
  }

  @Override
  public String getVersionWs() {

    return "1.0";
  }

  /**
   * Obtiene los datos de una {@link es.caib.dir3caib.persistence.model.ws.OficinaTF}  en función del codigo y la
   * fecha de actualización. Si la fecha de actualización es inferior a la de
   * importación con Madrid se supone que no ha cambiado y se envia null
   * 
   * @param codigo
   *          Código de la oficina
   * @param fechaActualizacion
   *          fecha en la que se realiza la actualizacion.
   */
  @Override
  public OficinaTF obtenerOficina(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

    return obtenerOficinasEjb.obtenerOficinaTF(codigo, fechaActualizacion,fechaSincronizacion, false);
  }

  /**
   * Obtiene los datos de una {@link es.caib.dir3caib.persistence.model.ws.OficinaTF}  en función del codigo y la
   * fecha de actualización. Si la fecha de actualización es inferior a la de
   * importación con Madrid se supone que no ha cambiado y se envia null
   *
   * @param codigo
   *          Código de la oficina
   * @param fechaActualizacion
   *          fecha en la que se realiza la actualizacion.
   */
  @Override
  public OficinaWs obtenerOficinaV2(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

    return obtenerOficinasEjb.obtenerOficinaWs(codigo, fechaActualizacion,fechaSincronizacion);
  }

  /**
   *
   * Obtiene todas las {@link es.caib.dir3caib.persistence.model.ws.OficinaTF} cuyo organismo responsable es el indicado por código(son todas padres e hijas).Solo se envian aquellas
   * que han sido actualizadas controlando que la unidad del código que nos pasan se haya podido actualizar también.
   * Esto es debido a que cuando en Madrid actualizan una unidad la tendencia es extinguirla y crear una nueva con código diferente.
   * Esto hace que se tengan que traer las oficinas de la vieja y de la nueva.
   *
   * @param codigo             Código del organismo
   * @param fechaActualizacion fecha en la que se realiza la actualizacion.
   */
  @Override
  public List<OficinaTF> obtenerArbolOficinas(String codigo, Date fechaActualizacion, Date fechaSincronizacion)
      throws Exception {

    return obtenerOficinasEjb.obtenerArbolOficinasTF(codigo, fechaActualizacion, fechaSincronizacion, false);
  }

  /**
   *
   * Obtiene todas las {@link es.caib.dir3caib.persistence.model.ws.v2.OficinaWs} cuyo organismo responsable es el indicado por código(son todas padres e hijas).Solo se envian aquellas
   * que han sido actualizadas controlando que la unidad del código que nos pasan se haya podido actualizar también.
   * Esto es debido a que cuando en Madrid actualizan una unidad la tendencia es extinguirla y crear una nueva con código diferente.
   * Esto hace que se tengan que traer las oficinas de la vieja y de la nueva.
   *
   * @param codigo             Código del organismo
   * @param fechaActualizacion fecha en la que se realiza la actualizacion.
   */
  @Override
  public List<OficinaWs> obtenerArbolOficinasV2(String codigo, Date fechaActualizacion, Date fechaSincronizacion)
          throws Exception {

    return obtenerOficinasEjb.obtenerArbolOficinasWs(codigo, fechaActualizacion, fechaSincronizacion);
  }

  /**
   * Obtiene el listado de oficinas Sir de una Unidad
   *
   * @param codigoUnidad
   *          Código de la unidad
   *
   */
  @Override
  public List<OficinaTF> obtenerOficinasSIRUnidad(String codigoUnidad)
      throws Exception{
      return obtenerOficinasEjb.obtenerOficinasSIRUnidadTF(codigoUnidad, false);
  }

  /**
   * Obtiene el listado de oficinas Sir de una Unidad
   *
   * @param codigoUnidad
   *          Código de la unidad
   *
   */
  @Override
  public List<OficinaWs> obtenerOficinasSIRUnidadV2(String codigoUnidad)
          throws Exception{
    return obtenerOficinasEjb.obtenerOficinasSIRUnidadWs(codigoUnidad);
  }

  /**
   * Obtiene la fecha en la que se actualizaron por última vez las oficinas
   * @return
   * @throws Exception
   */
  @Override
  public Date obtenerFechaUltimaActualizacion()
          throws Exception {

    return obtenerOficinasEjb.obtenerFechaUltimaActualizacion();
  }
}
