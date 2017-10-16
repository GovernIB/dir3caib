package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Sincronizacion;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;

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

    @EJB private SincronizacionLocal sincronizacionEjb;
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
    public void eliminarCompleto() throws Exception{

        eliminarOficinas();
        eliminarUnidades();
        eliminarCatalogo();
    }

    @Override
    public void eliminarDirectorio() throws Exception{

        eliminarOficinas();
        eliminarUnidades();
        sincronizacionEjb.deleteAllByTipo(Dir3caibConstantes.DIRECTORIO);
    }

    @Override
    public void eliminarCatalogo() throws Exception {

        Sincronizacion sincronizacion = sincronizacionEjb.ultimaSincronizacion(Dir3caibConstantes.CATALOGO);

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
            sincronizacionEjb.deleteAllByTipo(Dir3caibConstantes.CATALOGO);
        }

        log.info("Eliminar Catalogo completo");
    }

    @Override
    public void eliminarUnidades() throws Exception {

        Sincronizacion sincronizacion = sincronizacionEjb.ultimaSincronizacion(Dir3caibConstantes.UNIDAD);

        if (sincronizacion != null) {
            // Contactos
            contactoUOEjb.deleteAll();
            //Unidades
            unidadEjb.deleteHistoricosUnidad();
            unidadEjb.deleteAll();
        }

        log.info("Eliminar Unidades completo");
    }

    @Override
    public void eliminarOficinas() throws Exception {

        Sincronizacion sincronizacion = sincronizacionEjb.ultimaSincronizacion(Dir3caibConstantes.OFICINA);

        if (sincronizacion != null) {
            relSirOfiEjb.deleteAll();
            relOrgOfiEjb.deleteAll();
            contactoOfiEjb.deleteAll();
            oficinaEjb.deleteHistoricosOficina();
            oficinaEjb.deleteServiciosOficina();
            oficinaEjb.deleteAll();
        }

        log.info("Eliminar Oficinas completo");
    }

    @Override
    @TransactionTimeout(value = 40000)
    public void restaurarDirectorio() throws Exception{

        // Eliminamos las Unidades y Oficinas
        eliminarDirectorio();

        // Realizamos una descarga inicial
        Sincronizacion sincronizacion = sincronizacionEjb.descargarDirectorioWS(Dir3caibConstantes.DIRECTORIO, null, null);

        // Si la descarga de datos es correcta, procedemos a realizar la sincronización de datos
        if (sincronizacion != null && sincronizacion.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {

            sincronizacionEjb.importarDirectorio(sincronizacion);
        }
    }
}
