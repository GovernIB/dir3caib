
package es.caib.dir3caib.ws.api.unidad;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.caib.dir3caib.ws.api.unidad package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ObtenerArbolUnidadesDestinatarias_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidadesDestinatarias");
    private final static QName _GetVersionResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "getVersionResponse");
    private final static QName _GetVersionWs_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "getVersionWs");
    private final static QName _ObtenerArbolUnidades_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidades");
    private final static QName _ObtenerArbolUnidadesDestinatariasResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidadesDestinatariasResponse");
    private final static QName _ObtenerUnidadResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerUnidadResponse");
    private final static QName _GetVersion_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "getVersion");
    private final static QName _ObtenerUnidad_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerUnidad");
    private final static QName _ObtenerArbolUnidadesResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidadesResponse");
    private final static QName _GetVersionWsResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "getVersionWsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.caib.dir3caib.ws.api.unidad
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidadesResponse }
     * 
     */
    public ObtenerArbolUnidadesResponse createObtenerArbolUnidadesResponse() {
        return new ObtenerArbolUnidadesResponse();
    }

    /**
     * Create an instance of {@link GetVersionResponse }
     * 
     */
    public GetVersionResponse createGetVersionResponse() {
        return new GetVersionResponse();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidadesDestinatariasResponse }
     * 
     */
    public ObtenerArbolUnidadesDestinatariasResponse createObtenerArbolUnidadesDestinatariasResponse() {
        return new ObtenerArbolUnidadesDestinatariasResponse();
    }

    /**
     * Create an instance of {@link UnidadTF }
     * 
     */
    public UnidadTF createUnidadTF() {
        return new UnidadTF();
    }

    /**
     * Create an instance of {@link GetVersionWs }
     * 
     */
    public GetVersionWs createGetVersionWs() {
        return new GetVersionWs();
    }

    /**
     * Create an instance of {@link GetVersionWsResponse }
     * 
     */
    public GetVersionWsResponse createGetVersionWsResponse() {
        return new GetVersionWsResponse();
    }

    /**
     * Create an instance of {@link ObtenerUnidad }
     * 
     */
    public ObtenerUnidad createObtenerUnidad() {
        return new ObtenerUnidad();
    }

    /**
     * Create an instance of {@link ObtenerUnidadResponse }
     * 
     */
    public ObtenerUnidadResponse createObtenerUnidadResponse() {
        return new ObtenerUnidadResponse();
    }

    /**
     * Create an instance of {@link GetVersion }
     * 
     */
    public GetVersion createGetVersion() {
        return new GetVersion();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidades }
     * 
     */
    public ObtenerArbolUnidades createObtenerArbolUnidades() {
        return new ObtenerArbolUnidades();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidadesDestinatarias }
     * 
     */
    public ObtenerArbolUnidadesDestinatarias createObtenerArbolUnidadesDestinatarias() {
        return new ObtenerArbolUnidadesDestinatarias();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolUnidadesDestinatarias }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerArbolUnidadesDestinatarias")
    public JAXBElement<ObtenerArbolUnidadesDestinatarias> createObtenerArbolUnidadesDestinatarias(ObtenerArbolUnidadesDestinatarias value) {
        return new JAXBElement<ObtenerArbolUnidadesDestinatarias>(_ObtenerArbolUnidadesDestinatarias_QNAME, ObtenerArbolUnidadesDestinatarias.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "getVersionResponse")
    public JAXBElement<GetVersionResponse> createGetVersionResponse(GetVersionResponse value) {
        return new JAXBElement<GetVersionResponse>(_GetVersionResponse_QNAME, GetVersionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersionWs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "getVersionWs")
    public JAXBElement<GetVersionWs> createGetVersionWs(GetVersionWs value) {
        return new JAXBElement<GetVersionWs>(_GetVersionWs_QNAME, GetVersionWs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolUnidades }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerArbolUnidades")
    public JAXBElement<ObtenerArbolUnidades> createObtenerArbolUnidades(ObtenerArbolUnidades value) {
        return new JAXBElement<ObtenerArbolUnidades>(_ObtenerArbolUnidades_QNAME, ObtenerArbolUnidades.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolUnidadesDestinatariasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerArbolUnidadesDestinatariasResponse")
    public JAXBElement<ObtenerArbolUnidadesDestinatariasResponse> createObtenerArbolUnidadesDestinatariasResponse(ObtenerArbolUnidadesDestinatariasResponse value) {
        return new JAXBElement<ObtenerArbolUnidadesDestinatariasResponse>(_ObtenerArbolUnidadesDestinatariasResponse_QNAME, ObtenerArbolUnidadesDestinatariasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUnidadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerUnidadResponse")
    public JAXBElement<ObtenerUnidadResponse> createObtenerUnidadResponse(ObtenerUnidadResponse value) {
        return new JAXBElement<ObtenerUnidadResponse>(_ObtenerUnidadResponse_QNAME, ObtenerUnidadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "getVersion")
    public JAXBElement<GetVersion> createGetVersion(GetVersion value) {
        return new JAXBElement<GetVersion>(_GetVersion_QNAME, GetVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUnidad }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerUnidad")
    public JAXBElement<ObtenerUnidad> createObtenerUnidad(ObtenerUnidad value) {
        return new JAXBElement<ObtenerUnidad>(_ObtenerUnidad_QNAME, ObtenerUnidad.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolUnidadesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerArbolUnidadesResponse")
    public JAXBElement<ObtenerArbolUnidadesResponse> createObtenerArbolUnidadesResponse(ObtenerArbolUnidadesResponse value) {
        return new JAXBElement<ObtenerArbolUnidadesResponse>(_ObtenerArbolUnidadesResponse_QNAME, ObtenerArbolUnidadesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersionWsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "getVersionWsResponse")
    public JAXBElement<GetVersionWsResponse> createGetVersionWsResponse(GetVersionWsResponse value) {
        return new JAXBElement<GetVersionWsResponse>(_GetVersionWsResponse_QNAME, GetVersionWsResponse.class, null, value);
    }

}
