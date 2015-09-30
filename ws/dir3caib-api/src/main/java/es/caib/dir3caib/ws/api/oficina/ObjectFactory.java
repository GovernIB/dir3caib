
package es.caib.dir3caib.ws.api.oficina;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.caib.dir3caib.ws.api.oficina package. 
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

    private final static QName _ObtenerOficinasSIRUnidad_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "obtenerOficinasSIRUnidad");
    private final static QName _GetVersionResponse_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "getVersionResponse");
    private final static QName _GetVersionWs_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "getVersionWs");
    private final static QName _ObtenerArbolOficinas_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "obtenerArbolOficinas");
    private final static QName _ObtenerArbolOficinasResponse_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "obtenerArbolOficinasResponse");
    private final static QName _GetVersionWsResponse_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "getVersionWsResponse");
    private final static QName _ObtenerOficinaResponse_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "obtenerOficinaResponse");
    private final static QName _ObtenerOficinasSIRUnidadResponse_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "obtenerOficinasSIRUnidadResponse");
    private final static QName _GetVersion_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "getVersion");
    private final static QName _ObtenerOficina_QNAME = new QName("http://oficina.ws.dir3caib.caib.es/", "obtenerOficina");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.caib.dir3caib.ws.api.oficina
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetVersionWs }
     * 
     */
    public GetVersionWs createGetVersionWs() {
        return new GetVersionWs();
    }

    /**
     * Create an instance of {@link ObtenerOficinasSIRUnidadResponse }
     * 
     */
    public ObtenerOficinasSIRUnidadResponse createObtenerOficinasSIRUnidadResponse() {
        return new ObtenerOficinasSIRUnidadResponse();
    }

    /**
     * Create an instance of {@link ObtenerArbolOficinas }
     * 
     */
    public ObtenerArbolOficinas createObtenerArbolOficinas() {
        return new ObtenerArbolOficinas();
    }

    /**
     * Create an instance of {@link OficinaTF }
     * 
     */
    public OficinaTF createOficinaTF() {
        return new OficinaTF();
    }

    /**
     * Create an instance of {@link ObtenerArbolOficinasResponse }
     * 
     */
    public ObtenerArbolOficinasResponse createObtenerArbolOficinasResponse() {
        return new ObtenerArbolOficinasResponse();
    }

    /**
     * Create an instance of {@link GetVersion }
     * 
     */
    public GetVersion createGetVersion() {
        return new GetVersion();
    }

    /**
     * Create an instance of {@link GetVersionWsResponse }
     * 
     */
    public GetVersionWsResponse createGetVersionWsResponse() {
        return new GetVersionWsResponse();
    }

    /**
     * Create an instance of {@link ObtenerOficinaResponse }
     * 
     */
    public ObtenerOficinaResponse createObtenerOficinaResponse() {
        return new ObtenerOficinaResponse();
    }

    /**
     * Create an instance of {@link ObtenerOficinasSIRUnidad }
     * 
     */
    public ObtenerOficinasSIRUnidad createObtenerOficinasSIRUnidad() {
        return new ObtenerOficinasSIRUnidad();
    }

    /**
     * Create an instance of {@link ObtenerOficina }
     * 
     */
    public ObtenerOficina createObtenerOficina() {
        return new ObtenerOficina();
    }

    /**
     * Create an instance of {@link GetVersionResponse }
     * 
     */
    public GetVersionResponse createGetVersionResponse() {
        return new GetVersionResponse();
    }

    /**
     * Create an instance of {@link RelacionOrganizativaOfiTF }
     * 
     */
    public RelacionOrganizativaOfiTF createRelacionOrganizativaOfiTF() {
        return new RelacionOrganizativaOfiTF();
    }

    /**
     * Create an instance of {@link RelacionSirOfiTF }
     * 
     */
    public RelacionSirOfiTF createRelacionSirOfiTF() {
        return new RelacionSirOfiTF();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerOficinasSIRUnidad }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "obtenerOficinasSIRUnidad")
    public JAXBElement<ObtenerOficinasSIRUnidad> createObtenerOficinasSIRUnidad(ObtenerOficinasSIRUnidad value) {
        return new JAXBElement<ObtenerOficinasSIRUnidad>(_ObtenerOficinasSIRUnidad_QNAME, ObtenerOficinasSIRUnidad.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "getVersionResponse")
    public JAXBElement<GetVersionResponse> createGetVersionResponse(GetVersionResponse value) {
        return new JAXBElement<GetVersionResponse>(_GetVersionResponse_QNAME, GetVersionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersionWs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "getVersionWs")
    public JAXBElement<GetVersionWs> createGetVersionWs(GetVersionWs value) {
        return new JAXBElement<GetVersionWs>(_GetVersionWs_QNAME, GetVersionWs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolOficinas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "obtenerArbolOficinas")
    public JAXBElement<ObtenerArbolOficinas> createObtenerArbolOficinas(ObtenerArbolOficinas value) {
        return new JAXBElement<ObtenerArbolOficinas>(_ObtenerArbolOficinas_QNAME, ObtenerArbolOficinas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolOficinasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "obtenerArbolOficinasResponse")
    public JAXBElement<ObtenerArbolOficinasResponse> createObtenerArbolOficinasResponse(ObtenerArbolOficinasResponse value) {
        return new JAXBElement<ObtenerArbolOficinasResponse>(_ObtenerArbolOficinasResponse_QNAME, ObtenerArbolOficinasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersionWsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "getVersionWsResponse")
    public JAXBElement<GetVersionWsResponse> createGetVersionWsResponse(GetVersionWsResponse value) {
        return new JAXBElement<GetVersionWsResponse>(_GetVersionWsResponse_QNAME, GetVersionWsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerOficinaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "obtenerOficinaResponse")
    public JAXBElement<ObtenerOficinaResponse> createObtenerOficinaResponse(ObtenerOficinaResponse value) {
        return new JAXBElement<ObtenerOficinaResponse>(_ObtenerOficinaResponse_QNAME, ObtenerOficinaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerOficinasSIRUnidadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "obtenerOficinasSIRUnidadResponse")
    public JAXBElement<ObtenerOficinasSIRUnidadResponse> createObtenerOficinasSIRUnidadResponse(ObtenerOficinasSIRUnidadResponse value) {
        return new JAXBElement<ObtenerOficinasSIRUnidadResponse>(_ObtenerOficinasSIRUnidadResponse_QNAME, ObtenerOficinasSIRUnidadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "getVersion")
    public JAXBElement<GetVersion> createGetVersion(GetVersion value) {
        return new JAXBElement<GetVersion>(_GetVersion_QNAME, GetVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerOficina }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oficina.ws.dir3caib.caib.es/", name = "obtenerOficina")
    public JAXBElement<ObtenerOficina> createObtenerOficina(ObtenerOficina value) {
        return new JAXBElement<ObtenerOficina>(_ObtenerOficina_QNAME, ObtenerOficina.class, null, value);
    }

}
