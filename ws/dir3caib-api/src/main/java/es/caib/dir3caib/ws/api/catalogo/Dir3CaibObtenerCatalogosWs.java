package es.caib.dir3caib.ws.api.catalogo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.4
 * 2024-01-24T09:41:39.155+01:00
 * Generated source version: 2.6.4
 * 
 */
@WebService(targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", name = "Dir3CaibObtenerCatalogosWs")
@XmlSeeAlso({ObjectFactory.class})
public interface Dir3CaibObtenerCatalogosWs {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatComunidadAutonomaV2", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatComunidadAutonomaV2")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatComunidadAutonomaV2Response", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatComunidadAutonomaV2Response")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatComunidadAutonomaWs> obtenerCatComunidadAutonomaV2();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatEstadoEntidadV2", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEstadoEntidadV2")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatEstadoEntidadV2Response", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEstadoEntidadV2Response")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatEstadoEntidadWs> obtenerCatEstadoEntidadV2();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatEntidadGeograficaByEstado", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEntidadGeograficaByEstado")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatEntidadGeograficaByEstadoResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEntidadGeograficaByEstadoResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatEntidadGeograficaWs> obtenerCatEntidadGeograficaByEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatLocalidadV2", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatLocalidadV2")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatLocalidadV2Response", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatLocalidadV2Response")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatLocalidadWs> obtenerCatLocalidadV2();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatComunidadAutonoma", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatComunidadAutonoma")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatComunidadAutonomaResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatComunidadAutonomaResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatComunidadAutonomaTF> obtenerCatComunidadAutonoma();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatLocalidad", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatLocalidad")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatLocalidadResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatLocalidadResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatLocalidadTF> obtenerCatLocalidad();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatComunidadAutonomaByEstado", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatComunidadAutonomaByEstado")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatComunidadAutonomaByEstadoResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatComunidadAutonomaByEstadoResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatComunidadAutonomaWs> obtenerCatComunidadAutonomaByEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatTipoViaV2", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatTipoViaV2")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatTipoViaV2Response", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatTipoViaV2Response")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatTipoViaWs> obtenerCatTipoViaV2();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatProvinciaByEstado", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatProvinciaByEstado")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatProvinciaByEstadoResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatProvinciaByEstadoResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatProvinciaWs> obtenerCatProvinciaByEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatTipoVia", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatTipoVia")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatTipoViaResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatTipoViaResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatTipoVia> obtenerCatTipoVia();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatNivelAdministracionByEstado", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatNivelAdministracionByEstado")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatNivelAdministracionByEstadoResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatNivelAdministracionByEstadoResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatNivelAdministracionWs> obtenerCatNivelAdministracionByEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatTipoViaByEstado", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatTipoViaByEstado")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatTipoViaByEstadoResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatTipoViaByEstadoResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatTipoViaWs> obtenerCatTipoViaByEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatLocalidadByEstado", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatLocalidadByEstado")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatLocalidadByEstadoResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatLocalidadByEstadoResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatLocalidadWs> obtenerCatLocalidadByEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatProvincia", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatProvincia")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatProvinciaResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatProvinciaResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatProvinciaTF> obtenerCatProvincia();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatProvinciaV2", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatProvinciaV2")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatProvinciaV2Response", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatProvinciaV2Response")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatProvinciaWs> obtenerCatProvinciaV2();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatEntidadGeograficaV2", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEntidadGeograficaV2")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatEntidadGeograficaV2Response", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEntidadGeograficaV2Response")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatEntidadGeograficaWs> obtenerCatEntidadGeograficaV2();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatEntidadGeografica", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEntidadGeografica")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatEntidadGeograficaResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEntidadGeograficaResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatEntidadGeograficaTF> obtenerCatEntidadGeografica();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatNivelAdministracion", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatNivelAdministracion")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatNivelAdministracionResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatNivelAdministracionResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatNivelAdministracion> obtenerCatNivelAdministracion();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatIsla", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatIsla")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatIslaResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatIslaResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatIslaWs> obtenerCatIsla();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatPaisByEstado", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatPaisByEstado")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatPaisByEstadoResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatPaisByEstadoResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatPaisWs> obtenerCatPaisByEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatServicio", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatServicio")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatServicioResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatServicioResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.Servicio> obtenerCatServicio();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatNivelAdministracionV2", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatNivelAdministracionV2")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatNivelAdministracionV2Response", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatNivelAdministracionV2Response")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatNivelAdministracionWs> obtenerCatNivelAdministracionV2();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersionWs", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.GetVersionWs")
    @WebMethod
    @ResponseWrapper(localName = "getVersionWsResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.GetVersionWsResponse")
    public java.lang.String getVersionWs();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatPais", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatPais")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatPaisResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatPaisResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatPais> obtenerCatPais();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatEstadoEntidad", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEstadoEntidad")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatEstadoEntidadResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEstadoEntidadResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatEstadoEntidad> obtenerCatEstadoEntidad();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersion", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.GetVersion")
    @WebMethod
    @ResponseWrapper(localName = "getVersionResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.GetVersionResponse")
    public java.lang.String getVersion();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatPaisV2", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatPaisV2")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatPaisV2Response", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatPaisV2Response")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatPaisWs> obtenerCatPaisV2();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatServicioUO", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatServicioUO")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatServicioUOResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatServicioUOResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.Servicio> obtenerCatServicioUO();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatEstadoEntidadByEstado", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEstadoEntidadByEstado")
    @WebMethod
    @ResponseWrapper(localName = "obtenerCatEstadoEntidadByEstadoResponse", targetNamespace = "http://catalogo.ws.dir3caib.caib.es/", className = "es.caib.dir3caib.ws.api.catalogo.ObtenerCatEstadoEntidadByEstadoResponse")
    public java.util.List<es.caib.dir3caib.ws.api.catalogo.CatEstadoEntidadWs> obtenerCatEstadoEntidadByEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}
