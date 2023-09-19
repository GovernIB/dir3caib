package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Sincronizacion;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Created 12/09/17 9:52
 */
@Stateless(name = "Dir3CaibEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.ROL_TOTHOM, Dir3caibConstantes.DIR_WS})
public class Dir3CaibBean implements Dir3CaibLocal {

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName = "dir3caib")
    private EntityManager em;

    @EJB
    private SincronizacionLocal sincronizacionEjb;
    @EJB
    private OficinaLocal oficinaEjb;
    @EJB
    private ContactoOfiLocal contactoOfiEjb;
    @EJB
    private RelacionOrganizativaOfiLocal relOrgOfiEjb;
    @EJB
    private CatServicioLocal servicioEjb;
    @EJB
    private RelacionSirOfiLocal relSirOfiEjb;
    @EJB
    private UnidadLocal unidadEjb;
    @EJB
    private ContactoUOLocal contactoUOEjb;
    @EJB
    private CatEstadoEntidadLocal catEstadoEntidadEjb;
    @EJB
    private CatIslaLocal catIslaEjb;
    @EJB
    private CatJerarquiaOficinaLocal catJerarquiaOficinaEjb;
    @EJB
    private CatMotivoExtincionLocal catMotivoExtincionEjb;
    @EJB
    private CatNivelAdministracionLocal catNivelAdministracionEjb;
    @EJB
    private CatPaisLocal catPaisEjb;
    @EJB
    private CatTipoContactoLocal catTipoContactoEjb;
    @EJB
    private CatTipoEntidadPublicaLocal catTipoEntidadPublicaEjb;
    @EJB
    private CatTipoUnidadOrganicaLocal catTipoUnidadOrganicaEjb;
    @EJB
    private CatTipoViaLocal catTipoViaEjb;
    @EJB
    private CatComunidadAutonomaLocal catComunidadAutonomaEjb;
    @EJB
    private CatProvinciaLocal catProvinciaEjb;
    @EJB
    private CatLocalidadLocal catLocalidadEjb;
    @EJB
    private CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;
    @EJB
    private CatEntidadGeograficaLocal catEntidadGeograficaEjb;
    @EJB
    private NifCifUOLocal nifCifEjb;
    @EJB
    private CodigoUOLocal codigoUoEjb;
    @EJB
    private ServicioUOLocal servicioUoEjb;

    @Override
    public void eliminarCompleto() throws Exception {

        eliminarOficinasUnidades();
        eliminarCatalogo();
        eliminarSincronizaciones();
    }

    @Override
    public void eliminarOficinasUnidades() throws Exception {

        eliminarOficinas();
        eliminarUnidades();
    }

    @Override
    public void eliminarCatalogo() throws Exception {

        Sincronizacion sincronizacion = sincronizacionEjb.ultimaSincronizacionByTipo(Dir3caibConstantes.CATALOGO);

        if (sincronizacion != null) {
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
            servicioEjb.deleteAll();

            em.flush();

            log.info("Eliminar Catalogo completo");
        }
    }

    @Override
    public void eliminarUnidades() throws Exception {

        contactoUOEjb.deleteAll();
        nifCifEjb.deleteAll();
        codigoUoEjb.deleteAll();
        servicioUoEjb.deleteAll();
        unidadEjb.deleteHistoricosUnidad();
        unidadEjb.deleteAll();

        em.flush();

        log.info("Eliminar Unidades completo");
    }

    @Override
    public void eliminarOficinas() throws Exception {

        relSirOfiEjb.deleteAll();
        relOrgOfiEjb.deleteAll();
        contactoOfiEjb.deleteAll();
        oficinaEjb.deleteHistoricosOficina();
        oficinaEjb.deleteServiciosOficina();
        oficinaEjb.deleteAll();

        em.flush();

        log.info("Eliminar Oficinas completo");
    }

    @Override
    public void eliminarSincronizaciones() throws Exception {

        sincronizacionEjb.deleteAllByTipo(Dir3caibConstantes.DIRECTORIO_ACTUALIZACION);
        sincronizacionEjb.deleteAllByTipo(Dir3caibConstantes.DIRECTORIO_COMPLETO);
        sincronizacionEjb.deleteAllByTipo(Dir3caibConstantes.CATALOGO);
        log.info("Eliminar Sincronizaciones completo");
    }

    @Override
    @TransactionTimeout(value = 40000)
    public void restaurarUnidadesOficinas() throws Exception {

        // Realizamos una descarga inicial completa
        Sincronizacion sincronizacion = sincronizacionEjb.descargaCompletaDirectorio();

        // Si la descarga ha ido bien, eliminamos las anteriores
        sincronizacionEjb.eliminarSincronizacionesDirectorio(sincronizacion.getCodigo());

        // Eliminamos las Unidades y Oficinas
        eliminarOficinasUnidades();

        // Si la descarga de datos es correcta, procedemos a realizar la sincronización de datos
        if (sincronizacion != null && sincronizacion.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {

            sincronizacionEjb.importarUnidadesOficinas(sincronizacion);
        }
    }
}
