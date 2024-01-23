package es.caib.dir3caib.persistence.ejb;


import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.ws.CatEstadoEntidad;
import es.caib.dir3caib.persistence.model.ws.CatNivelAdministracion;
import es.caib.dir3caib.persistence.model.ws.CatPais;
import es.caib.dir3caib.persistence.model.ws.CatTipoVia;
import es.caib.dir3caib.persistence.model.ws.*;
import es.caib.dir3caib.persistence.model.ws.v2.*;
import org.apache.log4j.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 12/02/14
 */
@Stateless(name = "ObtenerCatalogosEJB")
@RunAs(Dir3caibConstantes.DIR_WS)  //todo añadir seguridad
public class ObtenerCatalogosEjb implements ObtenerCatalogosLocal {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
    private CatEstadoEntidadLocal catEstadoEntidadEjb;

    @EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
    private CatNivelAdministracionLocal catNivelAdministracionEjb;

    @EJB(mappedName = "dir3caib/CatPaisEJB/local")
    private CatPaisLocal catPaisEjb;

    @EJB(mappedName = "dir3caib/CatComunidadAutonomaEJB/local")
    private CatComunidadAutonomaLocal catComunidadAutonomaEjb;

    @EJB(mappedName = "dir3caib/CatProvinciaEJB/local")
    private CatProvinciaLocal catProvinciaEjb;

    @EJB(mappedName = "dir3caib/CatIslaEJB/local")
    private CatIslaLocal catIslaEjb;

    @EJB(mappedName = "dir3caib/CatLocalidadEJB/local")
    private CatLocalidadLocal catLocalidadEjb;

    @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
    private CatEntidadGeograficaLocal catEntidadGeograficaEjb;

    @EJB(mappedName = "dir3caib/CatServicioEJB/local")
    private CatServicioLocal servicioEjb;

    @EJB(mappedName = "dir3caib/CatServicioUOEJB/local")
    private CatServicioUOLocal servicioUOEjb;

    @EJB(mappedName = "dir3caib/CatTipoViaEJB/local")
    private CatTipoViaLocal catTipoViaEjb;


    /**
     * Obtiene todos los estados en los que puede estar una entidad(organismo).
     * @return
     * @throws Exception
     */
    @Override
    public List<CatEstadoEntidad> obtenerCatEstadoEntidad() throws Exception {
        List<es.caib.dir3caib.persistence.model.CatEstadoEntidad> estadosEntidad= catEstadoEntidadEjb.getAll();

        List<CatEstadoEntidad>  estadosEntidadWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatEstadoEntidad estado:estadosEntidad){
            estadosEntidadWs.add(CatEstadoEntidad.generar(estado));
        }
        return estadosEntidadWs;
        // return catEstadoEntidadEjb.getAll();
    }

    /**
     * Obtiene todos los estados en los que puede estar una entidad(organismo).
     * @return
     * @throws Exception
     */
    @Override
    public List<CatEstadoEntidadWs>  obtenerCatEstadoEntidadWs() throws Exception {
        List<es.caib.dir3caib.persistence.model.CatEstadoEntidad> estadosEntidad= catEstadoEntidadEjb.getAll();

        List<CatEstadoEntidadWs>  estadosEntidadWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatEstadoEntidad estadoEntidad:estadosEntidad){
            estadosEntidadWs.add(CatEstadoEntidadWs.generar(estadoEntidad));
        }
        return estadosEntidadWs;

    }

    /**
     * Obtiene todos los estados en los que puede estar una entidad(organismo) del estado indicado
     * @return
     * @throws Exception
     */
    @Override
    public List<CatEstadoEntidadWs> obtenerCatEstadoEntidadByEstado(String estado) throws Exception {
        List<es.caib.dir3caib.persistence.model.CatEstadoEntidad> estadosEntidad= catEstadoEntidadEjb.getByEstado(estado);

        List<CatEstadoEntidadWs>  estadosEntidadWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatEstadoEntidad estadoEntidad:estadosEntidad){
            estadosEntidadWs.add(CatEstadoEntidadWs.generar(estadoEntidad));
        }
        return estadosEntidadWs;

    }



    /**
     * Obtiene todos los niveles de administración
     * @return
     * @throws Exception
     */
    @Override
    public List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception {
        List<es.caib.dir3caib.persistence.model.CatNivelAdministracion> nivelAdministracions= catNivelAdministracionEjb.getAll();

        List<CatNivelAdministracion>  nivelAdminWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatNivelAdministracion nivel:nivelAdministracions){
            nivelAdminWs.add(CatNivelAdministracion.generar(nivel));
        }
        return nivelAdminWs;

    }

    /**
     * Obtiene todos los niveles de administración
     * @return
     * @throws Exception
     */
    @Override
    public List<CatNivelAdministracionWs> obtenerCatNivelAdministracionWs() throws Exception {
        List<es.caib.dir3caib.persistence.model.CatNivelAdministracion> nivelAdministracions= catNivelAdministracionEjb.getAll();

        List<CatNivelAdministracionWs>  nivelAdminWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatNivelAdministracion nivel:nivelAdministracions){
            nivelAdminWs.add(CatNivelAdministracionWs.generar(nivel));
        }
        return nivelAdminWs;

    }

    /**
     * Obtiene todos los niveles de administración del estado indicado
     * @return
     * @throws Exception
     */
    @Override
    public List<CatNivelAdministracionWs> obtenerCatNivelAdministracionByEstado(String estado) throws Exception {
        List<es.caib.dir3caib.persistence.model.CatNivelAdministracion> nivelAdministracions= catNivelAdministracionEjb.getByEstado(estado);

        List<CatNivelAdministracionWs>  nivelAdminWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatNivelAdministracion nivel:nivelAdministracions){
            nivelAdminWs.add(CatNivelAdministracionWs.generar(nivel));
        }
        return nivelAdminWs;

    }

    /**
     * Obtiene todos los paises del catálogo
     * @return
     * @throws Exception
     */
    @Override
    public List<CatPais> obtenerCatPais() throws Exception {
        List<es.caib.dir3caib.persistence.model.CatPais> paises= catPaisEjb.getAll();

        List<CatPais>  paisesWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatPais pais:paises){
            paisesWs.add(CatPais.generar(pais));
        }
        return paisesWs;

    }

    /**
     * Obtiene todos los paises del catálogo
     * @return
     * @throws Exception
     */
    @Override
    public List<CatPaisWs> obtenerCatPaisWs() throws Exception {
        List<es.caib.dir3caib.persistence.model.CatPais> paises= catPaisEjb.getAll();

        List<CatPaisWs>  paisesWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatPais pais:paises){
            paisesWs.add(CatPaisWs.generar(pais));
        }
        return paisesWs;

    }

    /**
     * Obtiene todos los paises del catálogo del estado indicado
     * @return
     * @throws Exception
     */
    @Override
    public List<CatPaisWs> obtenerCatPaisByEstado(String estado) throws Exception {
        List<es.caib.dir3caib.persistence.model.CatPais> paises= catPaisEjb.getByEstado(estado);

        List<CatPaisWs>  paisesWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatPais pais:paises){
            paisesWs.add(CatPaisWs.generar(pais));
        }
        return paisesWs;

    }

    /**
     * Obtiene todas las comunidades autónomas
     * @return
     * @throws Exception
     */
    @Override
    public List<CatComunidadAutonomaTF> obtenerCatComunidadAutonoma() throws Exception {
        List<CatComunidadAutonoma>  comunidadesAutonomas = catComunidadAutonomaEjb.getAll();
        List<CatComunidadAutonomaTF> comunidadesAutonomasTF = new ArrayList<CatComunidadAutonomaTF>();

        for( CatComunidadAutonoma comunidad : comunidadesAutonomas){
            comunidadesAutonomasTF.add(CatComunidadAutonomaTF.generar(comunidad));
        }

        return comunidadesAutonomasTF;
    }


    /**
     * Obtiene todas las comunidades autónomas
     * @return
     * @throws Exception
     */
    @Override
    public List<CatComunidadAutonomaWs> obtenerCatComunidadAutonomaWs() throws Exception {
        List<CatComunidadAutonoma>  comunidadesAutonomas = catComunidadAutonomaEjb.getAll();
        List<CatComunidadAutonomaWs> comunidadesAutonomasWs = new ArrayList<CatComunidadAutonomaWs>();

        for( CatComunidadAutonoma comunidad : comunidadesAutonomas){
            comunidadesAutonomasWs.add(CatComunidadAutonomaWs.generar(comunidad));
        }

        return comunidadesAutonomasWs;
    }

    /**
     * Obtiene todas las comunidades autónomas del estado indicado
     * @return
     * @throws Exception
     */
    @Override
    public List<CatComunidadAutonomaWs> obtenerCatComunidadAutonomaByEstado(String estado) throws Exception {
        List<CatComunidadAutonoma>  comunidadesAutonomas = catComunidadAutonomaEjb.getByEstado(estado);
        List<CatComunidadAutonomaWs> comunidadesAutonomasWs = new ArrayList<CatComunidadAutonomaWs>();

        for( CatComunidadAutonoma comunidad : comunidadesAutonomas){
            comunidadesAutonomasWs.add(CatComunidadAutonomaWs.generar(comunidad));
        }

        return comunidadesAutonomasWs;
    }

    /**
     * Obtiene todas las provincias
     * @return
     * @throws Exception
     */
    @Override
    public List<CatProvinciaTF> obtenerCatProvincia() throws Exception {
        List<CatProvincia>  provincias = catProvinciaEjb.getAll();
        List<CatProvinciaTF> provinciasWs = new ArrayList<CatProvinciaTF>();

        for( CatProvincia provincia : provincias){
            provinciasWs.add(CatProvinciaTF.generar(provincia));
        }

        return provinciasWs;

    }

    /**
     * Obtiene todas las provincias
     * @return
     * @throws Exception
     */
    @Override
    public List<CatProvinciaWs> obtenerCatProvinciaWs() throws Exception {
        List<CatProvincia>  provincias = catProvinciaEjb.getAll();
        List<CatProvinciaWs> provinciasWs = new ArrayList<CatProvinciaWs>();

        for( CatProvincia provincia : provincias){
            provinciasWs.add(CatProvinciaWs.generar(provincia));
        }

        return provinciasWs;

    }

    /**
     * Obtiene todas las Islas
     * @return
     * @throws Exception
     */
    @Override
    public List<CatIslaWs> obtenerCatIslaWs() throws Exception{
        List<CatIsla> islas = catIslaEjb.getAll();
        List<CatIslaWs> catIslasWs = new ArrayList<>();
        for(CatIsla catIsla: islas){
            catIslasWs.add(CatIslaWs.generar(catIsla));
        }

        return catIslasWs;
    }

    /**
     * Obtiene todas las provincias del estado indicado
     * @return
     * @throws Exception
     */
    @Override
    public List<CatProvinciaWs> obtenerCatProvinciaByEstado(String estado) throws Exception {
        List<CatProvincia>  provincias = catProvinciaEjb.getByEstado(estado);
        List<CatProvinciaWs> provinciasWs = new ArrayList<CatProvinciaWs>();

        for( CatProvincia provincia : provincias){
            provinciasWs.add(CatProvinciaWs.generar(provincia));
        }

        return provinciasWs;

    }

    /**
     * Obtiene todas las localidades
     * @return
     * @throws Exception
     */
    @Override
    public List<CatLocalidadTF> obtenerCatLocalidad() throws Exception {
        List<CatLocalidad>  localidades = catLocalidadEjb.getAll();
        log.info("Localidades obtenidas de la bbdd dir3caib:" + localidades.size());
        List<CatLocalidadTF> localidadesTF = new ArrayList<CatLocalidadTF>();

        for( CatLocalidad localidad : localidades){
            localidadesTF.add(CatLocalidadTF.generar(localidad));
        }
        log.info("Localidades procesadas para WS:" + localidadesTF.size());
        return localidadesTF;

    }

    /**
     * Obtiene todas las localidades
     * @return
     * @throws Exception
     */
    @Override
    public List<CatLocalidadWs> obtenerCatLocalidadWs() throws Exception {
        List<CatLocalidad>  localidades = catLocalidadEjb.getAll();
        log.info("Localidades obtenidas de la bbdd dir3caib:" + localidades.size());
        List<CatLocalidadWs> localidadesWs = new ArrayList<CatLocalidadWs>();

        for( CatLocalidad localidad : localidades){
            localidadesWs.add(CatLocalidadWs.generar(localidad));
        }
        log.info("Localidades procesadas para WS:" + localidadesWs.size());
        return localidadesWs;

    }

    /**
     * Obtiene todas las localidades del estado indicado
     * @return
     * @throws Exception
     */
    @Override
    public List<CatLocalidadWs> obtenerCatLocalidadByEstado(String estado) throws Exception {
        List<CatLocalidad>  localidades = catLocalidadEjb.getByEstado(estado);
        List<CatLocalidadWs> localidadesWs = new ArrayList<CatLocalidadWs>();

        for( CatLocalidad localidad : localidades){
            localidadesWs.add(CatLocalidadWs.generar(localidad));
        }
        log.info("Localidades procesadas para WS:" + localidadesWs.size());
        return localidadesWs;

    }


    /**
     * Obtiene todas las entidades geográficas.
     * @return
     * @throws Exception
     */
    @Override
    public List<CatEntidadGeograficaTF> obtenerCatEntidadGeografica() throws Exception {
        List<CatEntidadGeografica>  entidadesGeograficas = catEntidadGeograficaEjb.getAll();
        List<CatEntidadGeograficaTF> entidadesGeograficasTF = new ArrayList<CatEntidadGeograficaTF>();

        for( CatEntidadGeografica entidadGeografica : entidadesGeograficas){
            entidadesGeograficasTF.add(CatEntidadGeograficaTF.generar(entidadGeografica));
        }

        return entidadesGeograficasTF;

    }

    /**
     * Obtiene todas las entidades geográficas.
     * @return
     * @throws Exception
     */
    @Override
    public List<CatEntidadGeograficaWs> obtenerCatEntidadGeograficaWs() throws Exception {
        List<CatEntidadGeografica>  entidadesGeograficas = catEntidadGeograficaEjb.getAll();
        List<CatEntidadGeograficaWs> entidadesGeograficasWs = new ArrayList<CatEntidadGeograficaWs>();

        for( CatEntidadGeografica entidadGeografica : entidadesGeograficas){
            entidadesGeograficasWs.add(CatEntidadGeograficaWs.generar(entidadGeografica));
        }

        return entidadesGeograficasWs;

    }


    /**
     * Obtiene todas las entidades geográficas.
     * @return
     * @throws Exception
     */
    @Override
    public List<CatEntidadGeograficaWs> obtenerCatEntidadGeograficaByEstado(String estado) throws Exception {
        List<CatEntidadGeografica>  entidadesGeograficas = catEntidadGeograficaEjb.getByEstado(estado);
        List<CatEntidadGeograficaWs> entidadesGeograficasWs = new ArrayList<CatEntidadGeograficaWs>();

        for( CatEntidadGeografica entidadGeografica : entidadesGeograficas){
            entidadesGeograficasWs.add(CatEntidadGeograficaWs.generar(entidadGeografica));
        }

        return entidadesGeograficasWs;

    }


    /**
     * Obtiene todos los Servicios
     * @return
     * @throws Exception
     */
    @Override
    public List<Servicio> obtenerCatServicio() throws Exception{

        List<Servicio> servicios = new ArrayList<>();

        for(CatServicio catServicio: servicioEjb.getAll()){
            servicios.add( Servicio.generar(catServicio));
        }

        return servicios;
    }


    /**
     * Obtiene todos los Servicios
     * @return
     * @throws Exception
     */
    @Override
    public List<Servicio> obtenerCatServicioUO() throws Exception{

        List<Servicio> servicios = new ArrayList<>();

        for(CatServicioUO catServicioUO: servicioUOEjb.getAll()){
            servicios.add( Servicio.generar(catServicioUO));
        }

        return servicios;
    }

    /**
     * Obtiene todos los Tipo Via
     * @return
     * @throws Exception
     */
    @Override
    public List<CatTipoVia> obtenerCatTipoVia() throws Exception{
        List<es.caib.dir3caib.persistence.model.CatTipoVia> tipoVias= catTipoViaEjb.getAll();

        List<CatTipoVia>  tipoViasWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatTipoVia tipoVia:tipoVias){
            tipoViasWs.add(CatTipoVia.generar(tipoVia));
        }
        return tipoViasWs;
    }

    /**
     * Obtiene todos los Tipo Via
     * @return
     * @throws Exception
     */
    @Override
    public List<CatTipoViaWs> obtenerCatTipoViaWs() throws Exception{
        List<es.caib.dir3caib.persistence.model.CatTipoVia> tipoVias= catTipoViaEjb.getAll();

        List<CatTipoViaWs>  tipoViasWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatTipoVia tipoVia:tipoVias){
            tipoViasWs.add(CatTipoViaWs.generar(tipoVia));
        }
        return tipoViasWs;
    }

    /**
     * Obtiene todos los Tipo Via
     * @return
     * @throws Exception
     */
    @Override
    public List<CatTipoViaWs> obtenerCatTipoViaByEstado(String estado) throws Exception{
        List<es.caib.dir3caib.persistence.model.CatTipoVia> tipoVias= catTipoViaEjb.getByEstado(estado);

        List<CatTipoViaWs>  tipoViasWs = new ArrayList<>();
        for(es.caib.dir3caib.persistence.model.CatTipoVia tipoVia:tipoVias){
            tipoViasWs.add(CatTipoViaWs.generar(tipoVia));
        }
        return tipoViasWs;
    }


}
