package es.caib.dir3caib.back.controller;

import es.caib.dir3caib.persistence.ejb.*;
import org.apache.log4j.Logger;

import javax.ejb.EJB;

/**
 * Created by Fundació BIT.
 * @author earrivi
 * Date: 2/10/13
 */
public class BaseController {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    protected UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    protected OficinaLocal oficinaEjb;

    @EJB(mappedName = "dir3caib/CatAmbitoTerritorialEJB/local")
    protected CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;

    @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
    protected CatEntidadGeograficaLocal catEntidadGeograficaEjb;

    @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
    protected CatEstadoEntidadLocal catEstadoEntidadEjb;

    @EJB(mappedName = "dir3caib/CatIslaEJB/local")
    protected CatIslaLocal catIslaEjb;

    @EJB(mappedName = "dir3caib/CatJerarquiaOficinaEJB/local")
    protected CatJerarquiaOficinaLocal catJerarquiaOficinaEjb;

    @EJB(mappedName = "dir3caib/CatMotivoExtincionEJB/local")
    protected CatMotivoExtincionLocal catMotivoExtincionEjb;

    @EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
    protected CatNivelAdministracionLocal catNivelAdministracionEjb;

    @EJB(mappedName = "dir3caib/CatPaisEJB/local")
    protected CatPaisLocal catPaisEjb;

    @EJB(mappedName = "dir3caib/CatTipoContactoEJB/local")
    protected CatTipoContactoLocal catTipoContactoEjb;

    @EJB(mappedName = "dir3caib/CatTipoEntidadPublicaEJB/local")
    protected CatTipoEntidadPublicaLocal catTipoEntidadPublicaEjb;

    @EJB(mappedName = "dir3caib/CatTipoUnidadOrganicaEJB/local")
    protected CatTipoUnidadOrganicaLocal catTipoUnidadOrganicaEjb;

    @EJB(mappedName = "dir3caib/CatTipoViaEJB/local")
    protected CatTipoViaLocal catTipoViaEjb;

    @EJB(mappedName = "dir3caib/CatComunidadAutonomaEJB/local")
    protected CatComunidadAutonomaLocal catComunidadAutonomaEjb;

    @EJB(mappedName = "dir3caib/CatProvinciaEJB/local")
    protected CatProvinciaLocal catProvinciaEjb;

    @EJB(mappedName = "dir3caib/CatLocalidadEJB/local")
    protected CatLocalidadLocal catLocalidadEjb;

    @EJB(mappedName = "dir3caib/ContactoUOEJB/local")
    protected ContactoUOLocal contactoUOEjb;

    @EJB(mappedName = "dir3caib/ContactoOfiEJB/local")
    protected ContactoOfiLocal contactoOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
    protected RelacionOrganizativaOfiLocal relOrgOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionSirOfiEJB/local")
    protected RelacionSirOfiLocal relSirOfiEjb;

    @EJB(mappedName = "dir3caib/ServicioEJB/local")
    protected ServicioLocal servicioEjb;

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;



    @EJB(mappedName = "dir3caib/Dir3RestEJB/local")
    protected Dir3RestLocal dir3RestEjb;

    
}
