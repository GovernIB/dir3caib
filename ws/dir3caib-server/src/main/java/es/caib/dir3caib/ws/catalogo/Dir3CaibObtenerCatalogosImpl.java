package es.caib.dir3caib.ws.catalogo;

import es.caib.dir3caib.persistence.ejb.ObtenerCatalogosLocal;
import es.caib.dir3caib.persistence.model.CatEstadoEntidad;
import es.caib.dir3caib.persistence.model.CatNivelAdministracion;
import es.caib.dir3caib.persistence.model.CatPais;
import es.caib.dir3caib.persistence.model.Servicio;
import es.caib.dir3caib.persistence.model.ws.CatComunidadAutonomaTF;
import es.caib.dir3caib.persistence.model.ws.CatEntidadGeograficaTF;
import es.caib.dir3caib.persistence.model.ws.CatLocalidadTF;
import es.caib.dir3caib.persistence.model.ws.CatProvinciaTF;
import es.caib.dir3caib.persistence.utils.Versio;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Constants;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.wsf.spi.annotation.TransportGuarantee;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal (seguretat)
 *  Date: 12/02/14
 */


@SecurityDomain(es.caib.dir3caib.utils.Constants.SECURITY_DOMAIN)
@Stateless(name = Dir3CaibObtenerCatalogosImpl.NAME + "Ejb")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@RolesAllowed({ Constants.DIR_ADMIN })
@WebService(name = Dir3CaibObtenerCatalogosImpl.NAME_WS, portName = Dir3CaibObtenerCatalogosImpl.NAME_WS,
    serviceName = Dir3CaibObtenerCatalogosImpl.NAME_WS + "Service",
    endpointInterface = "es.caib.dir3caib.ws.catalogo.Dir3CaibObtenerCatalogosWs"
   )
@WebContext(contextRoot = "/dir3caib/ws", urlPattern = "/" + Dir3CaibObtenerCatalogosImpl.NAME,
    transportGuarantee = TransportGuarantee.NONE, secureWSDLAccess = false,
    authMethod = "WSBASIC")
public class Dir3CaibObtenerCatalogosImpl implements Dir3CaibObtenerCatalogosWs {
  
  public static final String NAME = "Dir3CaibObtenerCatalogos";

  public static final String NAME_WS = NAME + "Ws";
  
  @Override
  public String getVersion() {
    
    return Versio.VERSIO + (Configuracio.isCAIB()?"-caib": "");
  }

  @Override
  public String getVersionWs() {

    return "1.0";
  }


    @EJB(mappedName = "dir3caib/ObtenerCatalogosEJB/local")
    protected ObtenerCatalogosLocal obtenerCatalogosEjb;

    /**
     * Obtiene todos los estados en los que puede estar una entidad(organismo).
     * @return
     * @throws Exception
     */
    @Override
    @WebMethod
    @RolesAllowed({ Constants.DIR_ADMIN })
    public List<CatEstadoEntidad> obtenerCatEstadoEntidad() throws Exception {
        return obtenerCatalogosEjb.obtenerCatEstadoEntidad();
    }

    /**
    * Obtiene todos los niveles de administración
    * @return
    * @throws Exception
    */
    @Override
    @WebMethod
    @RolesAllowed({ Constants.DIR_ADMIN })
    public List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception{
        return  obtenerCatalogosEjb.obtenerCatNivelAdministracion();
    }

    /**
    * Obtiene todos los paises del catálogo
    * @return
    * @throws Exception
    */
    @Override
    @WebMethod
    @RolesAllowed({ Constants.DIR_ADMIN })
    public List<CatPais> obtenerCatPais() throws Exception{
        return  obtenerCatalogosEjb.obtenerCatPais();
    }

    /**
     * Obtiene todas las comunidades autónomas
     * @return
     * @throws Exception
     */
    @Override
    @WebMethod
    @RolesAllowed({ Constants.DIR_ADMIN })
    public List<CatComunidadAutonomaTF> obtenerCatComunidadAutonoma() throws Exception{
        return  obtenerCatalogosEjb.obtenerCatComunidadAutonoma();
    }

    /**
    * Obtiene todas las provincias
    * @return
    * @throws Exception
    */
    @Override
    @WebMethod
    @RolesAllowed({ Constants.DIR_ADMIN })
    public List<CatProvinciaTF> obtenerCatProvincia() throws Exception{
        return  obtenerCatalogosEjb.obtenerCatProvincia();
    }

    /**
    * Obtiene todas las localidades
    * @return
    * @throws Exception
    */
    @Override
    @WebMethod
    @RolesAllowed({ Constants.DIR_ADMIN })
    public List<CatLocalidadTF> obtenerCatLocalidad() throws Exception{
        return  obtenerCatalogosEjb.obtenerCatLocalidad();
    }

   /**
   * Obtiene todas las entidades geográficas.
   * @return
   * @throws Exception
   */
    @Override
    @WebMethod
    @RolesAllowed({ Constants.DIR_ADMIN })
    public List<CatEntidadGeograficaTF> obtenerCatEntidadGeografica() throws Exception{
         return  obtenerCatalogosEjb.obtenerCatEntidadGeografica();
    }

    /**
     * Obtiene todas los servicios.
     * @return
     * @throws Exception
     */
    @Override
    @WebMethod
    @RolesAllowed({ Constants.DIR_ADMIN })
    public List<Servicio> obtenerCatServicio() throws Exception{
        return  obtenerCatalogosEjb.obtenerCatServicio();
    }
}
