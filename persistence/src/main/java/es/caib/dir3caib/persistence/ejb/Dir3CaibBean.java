package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Created 12/09/17 9:52
 */
@Stateless(name = "Dir3CaibEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN","tothom"})
public class Dir3CaibBean implements Dir3CaibLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @EJB private DescargaLocal descargaEjb;
    @EJB private OficinaLocal oficinaEjb;
    @EJB private ContactoOfiLocal contactoOfiEjb;
    @EJB private RelacionOrganizativaOfiLocal relOrgOfiEjb;
    @EJB private ServicioLocal servicioEjb;
    @EJB private RelacionSirOfiLocal relSirOfiEjb;
    @EJB private UnidadLocal unidadEjb;
    @EJB private ContactoUOLocal contactoUOEjb;
    @EJB private CatEstadoEntidadLocal catEstadoEntidadEjb;
    @EJB private CatIslaLocal catIslaEjb;
    @EJB private CatJerarquiaOficinaLocal catJerarquiaOficinaEjb;
    @EJB private CatMotivoExtincionLocal catMotivoExtincionEjb;
    @EJB private CatNivelAdministracionLocal catNivelAdministracionEjb;
    @EJB private CatPaisLocal catPaisEjb;
    @EJB private CatTipoContactoLocal catTipoContactoEjb;
    @EJB private CatTipoEntidadPublicaLocal catTipoEntidadPublicaEjb;
    @EJB private CatTipoUnidadOrganicaLocal catTipoUnidadOrganicaEjb;
    @EJB private CatTipoViaLocal catTipoViaEjb;
    @EJB private CatComunidadAutonomaLocal catComunidadAutonomaEjb;
    @EJB private CatProvinciaLocal catProvinciaEjb;
    @EJB private CatLocalidadLocal catLocalidadEjb;
    @EJB private CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;
    @EJB private CatEntidadGeograficaLocal catEntidadGeograficaEjb;

    @Override
    public void eliminarDirectorio() throws Exception{

        eliminarOficinas();
        eliminarUnidades();
        eliminarCatalogo();

    }

    @Override
    public void eliminarCatalogo() throws Exception {

        log.info("Eliminar Catalogo completo");
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.CATALOGO);

        if (descarga != null) {
            catLocalidadEjb.deleteAll();
            catIslaEjb.deleteAll();
            catProvinciaEjb.deleteAll();
            catComunidadAutonomaEjb.deleteAll();
            catAmbitoTerritorialEjb.deleteAll();
            catTipoViaEjb.deleteAll();
            catTipoUnidadOrganicaEjb.deleteAll();
            catTipoEntidadPublicaEjb.deleteAll();
            catTipoContactoEjb.deleteAll();
            catPaisEjb.deleteAll();
            catNivelAdministracionEjb.deleteAll();
            catMotivoExtincionEjb.deleteAll();
            catJerarquiaOficinaEjb.deleteAll();
            catEstadoEntidadEjb.deleteAll();
            catEntidadGeograficaEjb.deleteAll();
            descargaEjb.deleteAllByTipo(Dir3caibConstantes.CATALOGO);
        }
    }

    @Override
    public void eliminarUnidades() throws Exception {

        log.info("Eliminar Unidades completo");
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.UNIDAD);

        if (descarga != null) {
            // Contactos
            contactoUOEjb.deleteAll();
            //Unidades
            unidadEjb.deleteHistoricosUnidad();
            unidadEjb.deleteAll();
            descargaEjb.deleteAllByTipo(Dir3caibConstantes.UNIDAD);
        }
    }

    @Override
    public void eliminarOficinas() throws Exception {

        log.info("Eliminar Oficinas completo");
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.OFICINA);

        if (descarga != null) {
            relSirOfiEjb.deleteAll();
            relOrgOfiEjb.deleteAll();
            contactoOfiEjb.deleteAll();
            oficinaEjb.deleteHistoricosOficina();
            oficinaEjb.deleteServiciosOficina();
            oficinaEjb.deleteAll();
            servicioEjb.deleteAll();
            descargaEjb.deleteAllByTipo(Dir3caibConstantes.OFICINA);
        }
    }

}
