package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;

/**
 * Created 8/09/14 10:13
 *
 * @author mgonzalez
 */

public interface ImportarLocal {



    public void persistDescarga(Descarga descarga) throws Exception;
    public void mergeDescarga(Descarga descarga) throws Exception;
    public Descarga findDescargaByTipo(String tipo) throws Exception;
    public void persistAmbitoTerritorial(CatAmbitoTerritorial ambitoTerritorial) throws Exception;
    public CatAmbitoTerritorial findAmbitoTerritorial(CatAmbitoTerritorialPK idAmbito) throws Exception;
    public void persistEntidadGeografica(CatEntidadGeografica entidadGeografica) throws Exception;
    public CatEntidadGeografica findEntidadGeografica(String idEntidad) throws Exception;
    public void persistEstadoEntidad(CatEstadoEntidad estadoEntidad) throws Exception;
    public CatEstadoEntidad findEstadoEntidad(String estado) throws Exception;
    public void persistJerarquiaOficina(CatJerarquiaOficina jerarquiaOficina) throws Exception;
    public CatJerarquiaOficina findJerarquiaOficina(Long idJerarquia) throws Exception;
    public void persistMotivoExtincion(CatMotivoExtincion motivoExtincion) throws Exception;
    public CatMotivoExtincion findMotivoExtincion(String idMotivoExtincion) throws Exception;
    public void persistNivelAdministracion(CatNivelAdministracion nivelAdministracion) throws Exception;
    public CatNivelAdministracion findNivelAdministracion(Long idNivelAdministracion) throws Exception;
    public void persistPais(CatPais pais) throws Exception;
    public CatPais findPais(Long idPais) throws Exception;
    public void persistComunidadAutonoma(CatComunidadAutonoma comunidadAutonoma) throws Exception;
    public CatComunidadAutonoma findComunidadAutonoma(Long idComunidad) throws Exception;
    public void persistProvincia(CatProvincia provincia) throws Exception;
    public CatProvincia findProvincia(Long idProvincia) throws Exception;
    public void persistIsla(CatIsla isla) throws Exception;
    public CatIsla findIsla(Long idIsla) throws Exception;
    public void persistTipoContacto(CatTipoContacto tipoContacto) throws Exception;
    public CatTipoContacto findTipoContacto(String idTipoContacto) throws Exception;
    public void persistTipoEntidadPublica(CatTipoEntidadPublica tipoEntidadPublica) throws Exception;
    public CatTipoEntidadPublica findTipoEntidadPublica(String idTipoEntidadPublica) throws Exception;
    public void persistTipoUnidadOrganica(CatTipoUnidadOrganica tipoUnidadOrganica) throws Exception;
    public CatTipoUnidadOrganica findTipoUnidadOrganica(String idTipoUnidadOrganica) throws Exception;
    public void persistTipoVia(CatTipoVia tipoVia) throws Exception;
    public CatTipoVia findTipoVia(Long idTipoVia) throws Exception;
    public void persistLocalidad(CatLocalidad localidad) throws Exception;
    public CatLocalidad findLocalidad(CatLocalidadPK idLocalidad) throws Exception;
    public Unidad persistUnidad(Unidad unidad) throws Exception;
    public Unidad findUnidad(String idUnidad) throws Exception;
    public Unidad mergeUnidad(Unidad unidad) throws Exception;
    public Oficina persistOficina(Oficina oficina) throws Exception;
    public Oficina findOficina(String idOficina) throws Exception;
    public Oficina mergeOficina(Oficina oficina) throws Exception;
    public void persistContactoUO(ContactoUnidadOrganica contactoUO) throws Exception;
    public ContactoUnidadOrganica findContactoUO(Long idContactoUO) throws Exception;
    public void persistContactoOfi(ContactoOfi contactoOfi) throws Exception;
    public ContactoOfi findContactoOfi(Long idContactoOfi) throws Exception;
    public void persistRelacionOrgOfi(RelacionOrganizativaOfi relOrganizativaOfi) throws Exception;
    public RelacionOrganizativaOfi findRelOrganizativaOfi(String codigoUnidad, String codigoOficina) throws Exception;
    public void persistRelacionSirOfi(RelacionSirOfi relacionSirOfi) throws Exception;
    public RelacionSirOfi findRelSirOfi(RelacionSirOfiPK idRelSirOfi) throws Exception;
    public void persistServicio(Servicio servicio) throws Exception;
    public Servicio findServicio(Long idServicio) throws Exception;


}
