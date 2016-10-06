
/*
 * 
 */

package es.caib.dir3caib.ws.dir3.unidad.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.12-patch-04
 * Mon Sep 15 12:29:14 CEST 2014
 * Generated source version: 2.2.12-patch-04
 * 
 */


@WebServiceClient(name = "SD01UN_DescargaUnidadesService", 
                  wsdlLocation = "http://pre-dir3ws.redsara.es/directorio/services/SD01UN_DescargaUnidades?wsdl",
                  targetNamespace = "http://impl.manager.directorio.map.es") 
public class SD01UNDescargaUnidadesService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://impl.manager.directorio.map.es", "SD01UN_DescargaUnidadesService");
    public final static QName SD01UNDescargaUnidades = new QName("http://impl.manager.directorio.map.es", "SD01UN_DescargaUnidades");
    static {
        URL url = null;
        try {
            url = new URL("http://pre-dir3ws.redsara.es/directorio/services/SD01UN_DescargaUnidades?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://pre-dir3ws.redsara.es/directorio/services/SD01UN_DescargaUnidades?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public SD01UNDescargaUnidadesService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SD01UNDescargaUnidadesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SD01UNDescargaUnidadesService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns SD01UNDescargaUnidades
     */
    @WebEndpoint(name = "SD01UN_DescargaUnidades")
    public SD01UNDescargaUnidades getSD01UNDescargaUnidades() {
        return super.getPort(SD01UNDescargaUnidades, SD01UNDescargaUnidades.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SD01UNDescargaUnidades
     */
    @WebEndpoint(name = "SD01UN_DescargaUnidades")
    public SD01UNDescargaUnidades getSD01UNDescargaUnidades(WebServiceFeature... features) {
        return super.getPort(SD01UNDescargaUnidades, SD01UNDescargaUnidades.class, features);
    }

}