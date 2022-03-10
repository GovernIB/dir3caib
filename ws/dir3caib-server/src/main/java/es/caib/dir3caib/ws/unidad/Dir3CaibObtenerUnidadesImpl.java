package es.caib.dir3caib.ws.unidad;

import es.caib.dir3caib.persistence.ejb.ObtenerUnidadesLocal;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;
import es.caib.dir3caib.persistence.model.ws.v2.UnidadWs;
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

@SecurityDomain(es.caib.dir3caib.persistence.model.Dir3caibConstantes.SECURITY_DOMAIN)
@Stateless(name = Dir3CaibObtenerUnidadesImpl.NAME + "Ejb")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@RolesAllowed({Dir3caibConstantes.DIR_WS})
@WebService(name = Dir3CaibObtenerUnidadesImpl.NAME_WS, portName = Dir3CaibObtenerUnidadesImpl.NAME_WS, serviceName = Dir3CaibObtenerUnidadesImpl.NAME_WS
    + "Service", endpointInterface = "es.caib.dir3caib.ws.unidad.Dir3CaibObtenerUnidadesWs")
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
   * Método que devuelve una {@link es.caib.dir3caib.persistence.model.ws.UnidadTF} vigente  a partir del código
   * indicado y en función de la fecha de actualización
   * 
   * @param codigo
   *          código de la unidad a transferir
   * @param fechaActualizacion
   *          fecha en la que se realiza la actualización
   * @return null si la unidad no está vigente
   */
  @Override
  public UnidadTF obtenerUnidad(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception {

    return obtenerUnidadesEjb.obtenerUnidadTF(codigo, fechaActualizacion, fechaSincronizacion);
  }

  /**
   * Método que devuelve una {@link UnidadWs} vigente  a partir del código
   * indicado y en función de la fecha de actualización
   *
   * @param codigo
   *          código de la unidad a transferir
   * @param fechaActualizacion
   *          fecha en la que se realiza la actualización
   * @return null si la unidad no está vigente
   */
  @Override
  public UnidadWs obtenerUnidadV2(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception{

    UnidadWs unidadWs = obtenerUnidadesEjb.obtenerUnidadWs(codigo, fechaActualizacion, fechaSincronizacion);
    return unidadWs;

  }

  /**
   * Método que devuelve una {@link es.caib.dir3caib.persistence.model.ws.UnidadTF} a partir del código indicado
   * independientemente de su estado
   *
   * @param codigo código de la unidad a transferir
   */
  @Override
  public UnidadTF buscarUnidad(String codigo) throws Exception {

    return obtenerUnidadesEjb.buscarUnidadTF(codigo);
  }

  /**
   * Método que devuelve una {@link es.caib.dir3caib.persistence.model.ws.v2.UnidadWs} a partir del código indicado
   * independientemente de su estado
   *
   * @param codigo código de la unidad a transferir
   */
  @Override
  public UnidadWs buscarUnidadV2(String codigo) throws Exception {

    return obtenerUnidadesEjb.buscarUnidadWs(codigo);
  }

  /**
   * Método que devuelve la lista de {@link es.caib.dir3caib.persistence.model.ws.UnidadTF} a partir del
   * código indicado y las fechas indicadas
   * Si no se especifican fechas obtiene aquellas unidades que son vigentes.
   * Si se especifica la fecha de actualización obtiene las unidades que han sufrido cambios entre esa fecha y la actual.
   * La fecha de sincronización nos sirve para evitar traer unidades (extinguidas/anuladas) anteriores a esta fecha
   * 
   * @param codigo
   *          código de la unidad raiz
   * @param fechaActualizacion
   *          fecha en la que se realiza la actualización
   */
  @Override
  public List<UnidadTF> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion)
      throws Exception {

    return obtenerUnidadesEjb.obtenerArbolUnidadesTF(codigo, fechaActualizacion, fechaSincronizacion);

  }


  /**
   * Método que devuelve la lista de {@link es.caib.dir3caib.persistence.model.ws.v2.UnidadWs} a partir del
   * código indicado y las fechas indicadas
   * Si no se especifican fechas obtiene aquellas unidades que son vigentes.
   * Si se especifica la fecha de actualización obtiene las unidades que han sufrido cambios entre esa fecha y la actual.
   * La fecha de sincronización nos sirve para evitar traer unidades (extinguidas/anuladas) anteriores a esta fecha
   *
   * @param codigo
   *          código de la unidad raiz
   * @param fechaActualizacion
   *          fecha en la que se realiza la actualización
   */
  public List<UnidadWs> obtenerArbolUnidadesV2(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception{
    return obtenerUnidadesEjb.obtenerArbolUnidadesWs(codigo, fechaActualizacion, fechaSincronizacion);
  }

  /**
   * Método que devuelve la lista de {@link es.caib.dir3caib.persistence.model.ws.UnidadTF} a partir del
   * código indicado y que estan vigentes y tienen oficinas. Método que emplea la aplicación SISTRA para
   * saber donde enviar un registro telemático.
   *
   * @param codigo
   *          código de la unidad raiz
   *
   */
  @Override
  public List<UnidadTF> obtenerArbolUnidadesDestinatarias(String codigo)
      throws Exception {

    return obtenerUnidadesEjb.obtenerArbolUnidadesDestinatariasTF(codigo);
  }

  /**
   * Método que devuelve la lista de {@link es.caib.dir3caib.persistence.model.ws.v2.UnidadWs} a partir del
   * código indicado y que estan vigentes y tienen oficinas. Método que emplea la aplicación SISTRA para
   * saber donde enviar un registro telemático.
   *
   * @param codigo
   *          código de la unidad raiz
   *
   */
  @Override
  public List<UnidadWs> obtenerArbolUnidadesDestinatariasV2(String codigo)
          throws Exception {

    return obtenerUnidadesEjb.obtenerArbolUnidadesDestinatariasWs(codigo);
  }

  /**
   * Obtiene la fecha en la que se actualizaron por última vez las unidades
   * @return
   * @throws Exception
   */
  @Override
  public Date obtenerFechaUltimaActualizacion()
          throws Exception {
    return obtenerUnidadesEjb.obtenerFechaUltimaActualizacion();
  }


  /**
   * Obtiene los históricos finales de una unidad como list de {@link es.caib.dir3caib.persistence.model.ws.UnidadTF}
   *
   * @return
   * @throws Exception
   */
  @Override
  public List<UnidadTF> obtenerHistoricosFinales(String codigo) throws Exception {

    return obtenerUnidadesEjb.obtenerHistoricosFinalesTF(codigo);
  }


  /**
   * Obtiene los históricos finales de una unidad como list de {@link es.caib.dir3caib.persistence.model.ws.v2.UnidadWs}
   *
   * @return
   * @throws Exception
   */
  @Override
  public List<UnidadWs> obtenerHistoricosFinalesV2(String codigo) throws Exception {

    return obtenerUnidadesEjb.obtenerHistoricosFinalesWs(codigo);
  }

   /**
    * Obtiene los históricos finales SIR de una unidad. Es decir, aquellos que tienen oficinas SIR
    *
    * @return
    * @throws Exception
    */
   @Override
   public List<UnidadTF> obtenerHistoricosFinalesSIR(String codigo) throws Exception {

      return obtenerUnidadesEjb.obtenerHistoricosFinalesSIRTF(codigo);
   }

  /**
   * Obtiene los históricos finales SIR de una unidad. Es decir, aquellos que tienen oficinas SIR
   *
   * @return
   * @throws Exception
   */
  @Override
  public List<UnidadWs> obtenerHistoricosFinalesSIRV2(String codigo) throws Exception {

    return obtenerUnidadesEjb.obtenerHistoricosFinalesSIRWs(codigo);
  }

}
