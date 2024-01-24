package es.caib.dir3caib.ws.api.oficina;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 2.6.4
 * 2024-01-24T09:41:40.631+01:00
 * Generated source version: 2.6.4
 * 
 */
@WebServiceClient(name = "Dir3CaibObtenerOficinasWsService", 
                  wsdlLocation = "http://localhost:8080/dir3caib/ws/Dir3CaibObtenerOficinas?wsdl",
                  targetNamespace = "http://oficina.ws.dir3caib.caib.es/") 
public class Dir3CaibObtenerOficinasWsService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://oficina.ws.dir3caib.caib.es/", "Dir3CaibObtenerOficinasWsService");
    public final static QName Dir3CaibObtenerOficinasWs = new QName("http://oficina.ws.dir3caib.caib.es/", "Dir3CaibObtenerOficinasWs");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/dir3caib/ws/Dir3CaibObtenerOficinas?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Dir3CaibObtenerOficinasWsService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/dir3caib/ws/Dir3CaibObtenerOficinas?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public Dir3CaibObtenerOficinasWsService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Dir3CaibObtenerOficinasWsService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Dir3CaibObtenerOficinasWsService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public Dir3CaibObtenerOficinasWsService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public Dir3CaibObtenerOficinasWsService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public Dir3CaibObtenerOficinasWsService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns Dir3CaibObtenerOficinasWs
     */
    @WebEndpoint(name = "Dir3CaibObtenerOficinasWs")
    public Dir3CaibObtenerOficinasWs getDir3CaibObtenerOficinasWs() {
        return super.getPort(Dir3CaibObtenerOficinasWs, Dir3CaibObtenerOficinasWs.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Dir3CaibObtenerOficinasWs
     */
    @WebEndpoint(name = "Dir3CaibObtenerOficinasWs")
    public Dir3CaibObtenerOficinasWs getDir3CaibObtenerOficinasWs(WebServiceFeature... features) {
        return super.getPort(Dir3CaibObtenerOficinasWs, Dir3CaibObtenerOficinasWs.class, features);
    }

}
