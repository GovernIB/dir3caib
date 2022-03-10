
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
    private final static QName _BuscarUnidadV2_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "buscarUnidadV2");
    private final static QName _ObtenerArbolUnidadesDestinatariasResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidadesDestinatariasResponse");
    private final static QName _ObtenerArbolUnidadesV2Response_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidadesV2Response");
    private final static QName _ObtenerUnidadResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerUnidadResponse");
    private final static QName _ObtenerUnidad_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerUnidad");
    private final static QName _ObtenerHistoricosFinales_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerHistoricosFinales");
    private final static QName _ObtenerHistoricosFinalesSIRV2_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerHistoricosFinalesSIRV2");
    private final static QName _GetVersionWs_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "getVersionWs");
    private final static QName _ObtenerHistoricosFinalesSIR_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerHistoricosFinalesSIR");
    private final static QName _ObtenerArbolUnidadesDestinatariasV2_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidadesDestinatariasV2");
    private final static QName _ObtenerHistoricosFinalesSIRV2Response_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerHistoricosFinalesSIRV2Response");
    private final static QName _ObtenerHistoricosFinalesSIRResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerHistoricosFinalesSIRResponse");
    private final static QName _BuscarUnidadResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "buscarUnidadResponse");
    private final static QName _ObtenerFechaUltimaActualizacion_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerFechaUltimaActualizacion");
    private final static QName _BuscarUnidadV2Response_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "buscarUnidadV2Response");
    private final static QName _ObtenerArbolUnidadesV2_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidadesV2");
    private final static QName _GetVersionResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "getVersionResponse");
    private final static QName _ObtenerArbolUnidades_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidades");
    private final static QName _ObtenerHistoricosFinalesV2_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerHistoricosFinalesV2");
    private final static QName _ObtenerHistoricosFinalesResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerHistoricosFinalesResponse");
    private final static QName _ObtenerArbolUnidadesResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidadesResponse");
    private final static QName _ObtenerUnidadV2_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerUnidadV2");
    private final static QName _BuscarUnidad_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "buscarUnidad");
    private final static QName _ObtenerArbolUnidadesDestinatariasV2Response_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerArbolUnidadesDestinatariasV2Response");
    private final static QName _ObtenerHistoricosFinalesV2Response_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerHistoricosFinalesV2Response");
    private final static QName _GetVersion_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "getVersion");
    private final static QName _ObtenerUnidadV2Response_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerUnidadV2Response");
    private final static QName _ObtenerFechaUltimaActualizacionResponse_QNAME = new QName("http://unidad.ws.dir3caib.caib.es/", "obtenerFechaUltimaActualizacionResponse");
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
     * Create an instance of {@link ObtenerHistoricosFinalesResponse }
     * 
     */
    public ObtenerHistoricosFinalesResponse createObtenerHistoricosFinalesResponse() {
        return new ObtenerHistoricosFinalesResponse();
    }

    /**
     * Create an instance of {@link ObtenerUnidadV2 }
     * 
     */
    public ObtenerUnidadV2 createObtenerUnidadV2() {
        return new ObtenerUnidadV2();
    }

    /**
     * Create an instance of {@link BuscarUnidadResponse }
     * 
     */
    public BuscarUnidadResponse createBuscarUnidadResponse() {
        return new BuscarUnidadResponse();
    }

    /**
     * Create an instance of {@link ObtenerFechaUltimaActualizacion }
     * 
     */
    public ObtenerFechaUltimaActualizacion createObtenerFechaUltimaActualizacion() {
        return new ObtenerFechaUltimaActualizacion();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidades }
     * 
     */
    public ObtenerArbolUnidades createObtenerArbolUnidades() {
        return new ObtenerArbolUnidades();
    }

    /**
     * Create an instance of {@link GetVersionResponse }
     * 
     */
    public GetVersionResponse createGetVersionResponse() {
        return new GetVersionResponse();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidadesV2 }
     * 
     */
    public ObtenerArbolUnidadesV2 createObtenerArbolUnidadesV2() {
        return new ObtenerArbolUnidadesV2();
    }

    /**
     * Create an instance of {@link BuscarUnidadV2Response }
     * 
     */
    public BuscarUnidadV2Response createBuscarUnidadV2Response() {
        return new BuscarUnidadV2Response();
    }

    /**
     * Create an instance of {@link ObtenerHistoricosFinalesV2 }
     * 
     */
    public ObtenerHistoricosFinalesV2 createObtenerHistoricosFinalesV2() {
        return new ObtenerHistoricosFinalesV2();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidadesDestinatariasV2Response }
     * 
     */
    public ObtenerArbolUnidadesDestinatariasV2Response createObtenerArbolUnidadesDestinatariasV2Response() {
        return new ObtenerArbolUnidadesDestinatariasV2Response();
    }

    /**
     * Create an instance of {@link ObtenerHistoricosFinalesV2Response }
     * 
     */
    public ObtenerHistoricosFinalesV2Response createObtenerHistoricosFinalesV2Response() {
        return new ObtenerHistoricosFinalesV2Response();
    }

    /**
     * Create an instance of {@link GetVersion }
     * 
     */
    public GetVersion createGetVersion() {
        return new GetVersion();
    }

    /**
     * Create an instance of {@link ObtenerUnidadV2Response }
     * 
     */
    public ObtenerUnidadV2Response createObtenerUnidadV2Response() {
        return new ObtenerUnidadV2Response();
    }

    /**
     * Create an instance of {@link GetVersionWsResponse }
     * 
     */
    public GetVersionWsResponse createGetVersionWsResponse() {
        return new GetVersionWsResponse();
    }

    /**
     * Create an instance of {@link ObtenerFechaUltimaActualizacionResponse }
     * 
     */
    public ObtenerFechaUltimaActualizacionResponse createObtenerFechaUltimaActualizacionResponse() {
        return new ObtenerFechaUltimaActualizacionResponse();
    }

    /**
     * Create an instance of {@link BuscarUnidad }
     * 
     */
    public BuscarUnidad createBuscarUnidad() {
        return new BuscarUnidad();
    }

    /**
     * Create an instance of {@link ObtenerUnidadResponse }
     * 
     */
    public ObtenerUnidadResponse createObtenerUnidadResponse() {
        return new ObtenerUnidadResponse();
    }

    /**
     * Create an instance of {@link ObtenerUnidad }
     * 
     */
    public ObtenerUnidad createObtenerUnidad() {
        return new ObtenerUnidad();
    }

    /**
     * Create an instance of {@link ObtenerHistoricosFinalesSIRV2 }
     * 
     */
    public ObtenerHistoricosFinalesSIRV2 createObtenerHistoricosFinalesSIRV2() {
        return new ObtenerHistoricosFinalesSIRV2();
    }

    /**
     * Create an instance of {@link ObtenerHistoricosFinales }
     * 
     */
    public ObtenerHistoricosFinales createObtenerHistoricosFinales() {
        return new ObtenerHistoricosFinales();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidadesDestinatarias }
     * 
     */
    public ObtenerArbolUnidadesDestinatarias createObtenerArbolUnidadesDestinatarias() {
        return new ObtenerArbolUnidadesDestinatarias();
    }

    /**
     * Create an instance of {@link BuscarUnidadV2 }
     * 
     */
    public BuscarUnidadV2 createBuscarUnidadV2() {
        return new BuscarUnidadV2();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidadesDestinatariasResponse }
     * 
     */
    public ObtenerArbolUnidadesDestinatariasResponse createObtenerArbolUnidadesDestinatariasResponse() {
        return new ObtenerArbolUnidadesDestinatariasResponse();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidadesV2Response }
     * 
     */
    public ObtenerArbolUnidadesV2Response createObtenerArbolUnidadesV2Response() {
        return new ObtenerArbolUnidadesV2Response();
    }

    /**
     * Create an instance of {@link ObtenerArbolUnidadesDestinatariasV2 }
     * 
     */
    public ObtenerArbolUnidadesDestinatariasV2 createObtenerArbolUnidadesDestinatariasV2() {
        return new ObtenerArbolUnidadesDestinatariasV2();
    }

    /**
     * Create an instance of {@link ObtenerHistoricosFinalesSIRResponse }
     * 
     */
    public ObtenerHistoricosFinalesSIRResponse createObtenerHistoricosFinalesSIRResponse() {
        return new ObtenerHistoricosFinalesSIRResponse();
    }

    /**
     * Create an instance of {@link ObtenerHistoricosFinalesSIRV2Response }
     * 
     */
    public ObtenerHistoricosFinalesSIRV2Response createObtenerHistoricosFinalesSIRV2Response() {
        return new ObtenerHistoricosFinalesSIRV2Response();
    }

    /**
     * Create an instance of {@link GetVersionWs }
     * 
     */
    public GetVersionWs createGetVersionWs() {
        return new GetVersionWs();
    }

    /**
     * Create an instance of {@link ObtenerHistoricosFinalesSIR }
     * 
     */
    public ObtenerHistoricosFinalesSIR createObtenerHistoricosFinalesSIR() {
        return new ObtenerHistoricosFinalesSIR();
    }

    /**
     * Create an instance of {@link UnidadTF }
     * 
     */
    public UnidadTF createUnidadTF() {
        return new UnidadTF();
    }

    /**
     * Create an instance of {@link ContactoTF }
     * 
     */
    public ContactoTF createContactoTF() {
        return new ContactoTF();
    }

    /**
     * Create an instance of {@link UnidadWs }
     * 
     */
    public UnidadWs createUnidadWs() {
        return new UnidadWs();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarUnidadV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "buscarUnidadV2")
    public JAXBElement<BuscarUnidadV2> createBuscarUnidadV2(BuscarUnidadV2 value) {
        return new JAXBElement<BuscarUnidadV2>(_BuscarUnidadV2_QNAME, BuscarUnidadV2 .class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolUnidadesV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerArbolUnidadesV2Response")
    public JAXBElement<ObtenerArbolUnidadesV2Response> createObtenerArbolUnidadesV2Response(ObtenerArbolUnidadesV2Response value) {
        return new JAXBElement<ObtenerArbolUnidadesV2Response>(_ObtenerArbolUnidadesV2Response_QNAME, ObtenerArbolUnidadesV2Response.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUnidad }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerUnidad")
    public JAXBElement<ObtenerUnidad> createObtenerUnidad(ObtenerUnidad value) {
        return new JAXBElement<ObtenerUnidad>(_ObtenerUnidad_QNAME, ObtenerUnidad.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerHistoricosFinales }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerHistoricosFinales")
    public JAXBElement<ObtenerHistoricosFinales> createObtenerHistoricosFinales(ObtenerHistoricosFinales value) {
        return new JAXBElement<ObtenerHistoricosFinales>(_ObtenerHistoricosFinales_QNAME, ObtenerHistoricosFinales.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerHistoricosFinalesSIRV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerHistoricosFinalesSIRV2")
    public JAXBElement<ObtenerHistoricosFinalesSIRV2> createObtenerHistoricosFinalesSIRV2(ObtenerHistoricosFinalesSIRV2 value) {
        return new JAXBElement<ObtenerHistoricosFinalesSIRV2>(_ObtenerHistoricosFinalesSIRV2_QNAME, ObtenerHistoricosFinalesSIRV2 .class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerHistoricosFinalesSIR }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerHistoricosFinalesSIR")
    public JAXBElement<ObtenerHistoricosFinalesSIR> createObtenerHistoricosFinalesSIR(ObtenerHistoricosFinalesSIR value) {
        return new JAXBElement<ObtenerHistoricosFinalesSIR>(_ObtenerHistoricosFinalesSIR_QNAME, ObtenerHistoricosFinalesSIR.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolUnidadesDestinatariasV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerArbolUnidadesDestinatariasV2")
    public JAXBElement<ObtenerArbolUnidadesDestinatariasV2> createObtenerArbolUnidadesDestinatariasV2(ObtenerArbolUnidadesDestinatariasV2 value) {
        return new JAXBElement<ObtenerArbolUnidadesDestinatariasV2>(_ObtenerArbolUnidadesDestinatariasV2_QNAME, ObtenerArbolUnidadesDestinatariasV2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerHistoricosFinalesSIRV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerHistoricosFinalesSIRV2Response")
    public JAXBElement<ObtenerHistoricosFinalesSIRV2Response> createObtenerHistoricosFinalesSIRV2Response(ObtenerHistoricosFinalesSIRV2Response value) {
        return new JAXBElement<ObtenerHistoricosFinalesSIRV2Response>(_ObtenerHistoricosFinalesSIRV2Response_QNAME, ObtenerHistoricosFinalesSIRV2Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerHistoricosFinalesSIRResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerHistoricosFinalesSIRResponse")
    public JAXBElement<ObtenerHistoricosFinalesSIRResponse> createObtenerHistoricosFinalesSIRResponse(ObtenerHistoricosFinalesSIRResponse value) {
        return new JAXBElement<ObtenerHistoricosFinalesSIRResponse>(_ObtenerHistoricosFinalesSIRResponse_QNAME, ObtenerHistoricosFinalesSIRResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarUnidadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "buscarUnidadResponse")
    public JAXBElement<BuscarUnidadResponse> createBuscarUnidadResponse(BuscarUnidadResponse value) {
        return new JAXBElement<BuscarUnidadResponse>(_BuscarUnidadResponse_QNAME, BuscarUnidadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerFechaUltimaActualizacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerFechaUltimaActualizacion")
    public JAXBElement<ObtenerFechaUltimaActualizacion> createObtenerFechaUltimaActualizacion(ObtenerFechaUltimaActualizacion value) {
        return new JAXBElement<ObtenerFechaUltimaActualizacion>(_ObtenerFechaUltimaActualizacion_QNAME, ObtenerFechaUltimaActualizacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarUnidadV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "buscarUnidadV2Response")
    public JAXBElement<BuscarUnidadV2Response> createBuscarUnidadV2Response(BuscarUnidadV2Response value) {
        return new JAXBElement<BuscarUnidadV2Response>(_BuscarUnidadV2Response_QNAME, BuscarUnidadV2Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolUnidadesV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerArbolUnidadesV2")
    public JAXBElement<ObtenerArbolUnidadesV2> createObtenerArbolUnidadesV2(ObtenerArbolUnidadesV2 value) {
        return new JAXBElement<ObtenerArbolUnidadesV2>(_ObtenerArbolUnidadesV2_QNAME, ObtenerArbolUnidadesV2 .class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolUnidades }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerArbolUnidades")
    public JAXBElement<ObtenerArbolUnidades> createObtenerArbolUnidades(ObtenerArbolUnidades value) {
        return new JAXBElement<ObtenerArbolUnidades>(_ObtenerArbolUnidades_QNAME, ObtenerArbolUnidades.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerHistoricosFinalesV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerHistoricosFinalesV2")
    public JAXBElement<ObtenerHistoricosFinalesV2> createObtenerHistoricosFinalesV2(ObtenerHistoricosFinalesV2 value) {
        return new JAXBElement<ObtenerHistoricosFinalesV2>(_ObtenerHistoricosFinalesV2_QNAME, ObtenerHistoricosFinalesV2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerHistoricosFinalesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerHistoricosFinalesResponse")
    public JAXBElement<ObtenerHistoricosFinalesResponse> createObtenerHistoricosFinalesResponse(ObtenerHistoricosFinalesResponse value) {
        return new JAXBElement<ObtenerHistoricosFinalesResponse>(_ObtenerHistoricosFinalesResponse_QNAME, ObtenerHistoricosFinalesResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUnidadV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerUnidadV2")
    public JAXBElement<ObtenerUnidadV2> createObtenerUnidadV2(ObtenerUnidadV2 value) {
        return new JAXBElement<ObtenerUnidadV2>(_ObtenerUnidadV2_QNAME, ObtenerUnidadV2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarUnidad }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "buscarUnidad")
    public JAXBElement<BuscarUnidad> createBuscarUnidad(BuscarUnidad value) {
        return new JAXBElement<BuscarUnidad>(_BuscarUnidad_QNAME, BuscarUnidad.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArbolUnidadesDestinatariasV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerArbolUnidadesDestinatariasV2Response")
    public JAXBElement<ObtenerArbolUnidadesDestinatariasV2Response> createObtenerArbolUnidadesDestinatariasV2Response(ObtenerArbolUnidadesDestinatariasV2Response value) {
        return new JAXBElement<ObtenerArbolUnidadesDestinatariasV2Response>(_ObtenerArbolUnidadesDestinatariasV2Response_QNAME, ObtenerArbolUnidadesDestinatariasV2Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerHistoricosFinalesV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerHistoricosFinalesV2Response")
    public JAXBElement<ObtenerHistoricosFinalesV2Response> createObtenerHistoricosFinalesV2Response(ObtenerHistoricosFinalesV2Response value) {
        return new JAXBElement<ObtenerHistoricosFinalesV2Response>(_ObtenerHistoricosFinalesV2Response_QNAME, ObtenerHistoricosFinalesV2Response.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUnidadV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerUnidadV2Response")
    public JAXBElement<ObtenerUnidadV2Response> createObtenerUnidadV2Response(ObtenerUnidadV2Response value) {
        return new JAXBElement<ObtenerUnidadV2Response>(_ObtenerUnidadV2Response_QNAME, ObtenerUnidadV2Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerFechaUltimaActualizacionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unidad.ws.dir3caib.caib.es/", name = "obtenerFechaUltimaActualizacionResponse")
    public JAXBElement<ObtenerFechaUltimaActualizacionResponse> createObtenerFechaUltimaActualizacionResponse(ObtenerFechaUltimaActualizacionResponse value) {
        return new JAXBElement<ObtenerFechaUltimaActualizacionResponse>(_ObtenerFechaUltimaActualizacionResponse_QNAME, ObtenerFechaUltimaActualizacionResponse.class, null, value);
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
