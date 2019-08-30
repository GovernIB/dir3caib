package es.caib.dir3caib.ws.api.unidad;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.4
 * 2019-08-30T12:15:13.971+02:00
 * Generated source version: 2.6.4
 * 
 */
@WebService(targetNamespace = "http://unidad.ws.dir3caib.caib.es/", name = "Dir3CaibObtenerUnidadesWs")
@XmlSeeAlso({ObjectFactory.class})
public interface Dir3CaibObtenerUnidadesWs {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerUnidad", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerUnidad")
    @WebMethod
    @ResponseWrapper(localName = "obtenerUnidadResponse", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerUnidadResponse")
    public es.caib.dir3caib.ws.api.unidad.UnidadTF obtenerUnidad(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.sql.Timestamp arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.sql.Timestamp arg2
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "buscarUnidad", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.BuscarUnidad")
    @WebMethod
    @ResponseWrapper(localName = "buscarUnidadResponse", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.BuscarUnidadResponse")
    public es.caib.dir3caib.ws.api.unidad.UnidadTF buscarUnidad(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerArbolUnidades", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerArbolUnidades")
    @WebMethod
    @ResponseWrapper(localName = "obtenerArbolUnidadesResponse", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerArbolUnidadesResponse")
    public java.util.List<es.caib.dir3caib.ws.api.unidad.UnidadTF> obtenerArbolUnidades(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.sql.Timestamp arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.sql.Timestamp arg2
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersionWs", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.GetVersionWs")
    @WebMethod
    @ResponseWrapper(localName = "getVersionWsResponse", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.GetVersionWsResponse")
    public java.lang.String getVersionWs();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersion", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.GetVersion")
    @WebMethod
    @ResponseWrapper(localName = "getVersionResponse", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.GetVersionResponse")
    public java.lang.String getVersion();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerFechaUltimaActualizacion", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerFechaUltimaActualizacion")
    @WebMethod
    @ResponseWrapper(localName = "obtenerFechaUltimaActualizacionResponse", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerFechaUltimaActualizacionResponse")
    public java.sql.Timestamp obtenerFechaUltimaActualizacion();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerHistoricosFinales", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerHistoricosFinales")
    @WebMethod
    @ResponseWrapper(localName = "obtenerHistoricosFinalesResponse", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerHistoricosFinalesResponse")
    public java.util.List<es.caib.dir3caib.ws.api.unidad.UnidadTF> obtenerHistoricosFinales(
       @WebParam(name = "arg0", targetNamespace = "")
          java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerArbolUnidadesDestinatarias", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerArbolUnidadesDestinatarias")
    @WebMethod
    @ResponseWrapper(localName = "obtenerArbolUnidadesDestinatariasResponse", targetNamespace = "http://unidad.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.unidad.ObtenerArbolUnidadesDestinatariasResponse")
    public java.util.List<es.caib.dir3caib.ws.api.unidad.UnidadTF> obtenerArbolUnidadesDestinatarias(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}
