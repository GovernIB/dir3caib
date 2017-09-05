package es.caib.dir3caib.back.controller;

import es.caib.dir3caib.persistence.ejb.*;
import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.utils.Configuracio;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;

import javax.ejb.EJB;
import java.io.File;


/**
 * Created by Fundació BIT.
 * @author earrivi
 * @author anadal
 * Date: 2/10/13
 */
public class BaseController {

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    public DescargaLocal descargaEjb;

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    public OficinaLocal oficinaEjb;

    @EJB(mappedName = "dir3caib/ContactoOfiEJB/local")
    public ContactoOfiLocal contactoOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
    public RelacionOrganizativaOfiLocal relOrgOfiEjb;

    @EJB(mappedName = "dir3caib/ServicioEJB/local")
    public ServicioLocal servicioEjb;

    @EJB(mappedName = "dir3caib/RelacionSirOfiEJB/local")
    public RelacionSirOfiLocal relSirOfiEjb;

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    public UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/ContactoUOEJB/local")
    public ContactoUOLocal contactoUOEjb;

    @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
    public CatEstadoEntidadLocal catEstadoEntidadEjb;

    @EJB(mappedName = "dir3caib/CatIslaEJB/local")
    public CatIslaLocal catIslaEjb;

    @EJB(mappedName = "dir3caib/CatJerarquiaOficinaEJB/local")
    public CatJerarquiaOficinaLocal catJerarquiaOficinaEjb;

    @EJB(mappedName = "dir3caib/CatMotivoExtincionEJB/local")
    public CatMotivoExtincionLocal catMotivoExtincionEjb;

    @EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
    public CatNivelAdministracionLocal catNivelAdministracionEjb;

    @EJB(mappedName = "dir3caib/CatPaisEJB/local")
    public CatPaisLocal catPaisEjb;

    @EJB(mappedName = "dir3caib/CatTipoContactoEJB/local")
    public CatTipoContactoLocal catTipoContactoEjb;

    @EJB(mappedName = "dir3caib/CatTipoEntidadPublicaEJB/local")
    public CatTipoEntidadPublicaLocal catTipoEntidadPublicaEjb;

    @EJB(mappedName = "dir3caib/CatTipoUnidadOrganicaEJB/local")
    public CatTipoUnidadOrganicaLocal catTipoUnidadOrganicaEjb;

    @EJB(mappedName = "dir3caib/CatTipoViaEJB/local")
    public CatTipoViaLocal catTipoViaEjb;

    @EJB(mappedName = "dir3caib/CatComunidadAutonomaEJB/local")
    public CatComunidadAutonomaLocal catComunidadAutonomaEjb;

    @EJB(mappedName = "dir3caib/CatProvinciaEJB/local")
    public CatProvinciaLocal catProvinciaEjb;

    @EJB(mappedName = "dir3caib/CatLocalidadEJB/local")
    public CatLocalidadLocal catLocalidadEjb;

    @EJB(mappedName = "dir3caib/CatAmbitoTerritorialEJB/local")
    public CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;

    @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
    public CatEntidadGeograficaLocal catEntidadGeograficaEjb;


    protected final Logger log = Logger.getLogger(getClass());

  /**
   * Retorna el mensaje traducido según el idioma del usuario
   * @param key
   * @return
   */
  protected String getMessage(String key){
    return I18NUtils.tradueix(key);
  }


    /**
     * Método que nos permite borrar el contenido de la bd via web.
     * Está oculto, es una funcionalidad para nosotros los desarrolladores
     *
     * @throws Exception
     */
    public void eliminarTodoCompleto() throws Exception {

        eliminarOficinasCompleto();
        eliminarUnidadesCompleto();
        eliminarCatalogoCompleto();
    }


    /**
     * Método que borra todas las oficinas y sus relaciones
     *
     * @throws Exception
     */
    public void eliminarOficinasCompleto() throws Exception {
        log.info("Eliminar Oficinas completo");
        //Eliminamos oficinas
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.OFICINA);
        if (descarga != null) {
            File directorio = new File(Configuracio.getOficinasPath(descarga.getCodigo()));
            FileUtils.cleanDirectory(directorio);

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

    /**
     * Método que permite borrar todas las unidades y sus relaciones
     *
     * @throws Exception
     */
    public void eliminarUnidadesCompleto() throws Exception {
        //Eliminamos unidades
        log.info("Eliminar Unidades completo");
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.UNIDAD);
        if (descarga != null) {
            File directorio = new File(Configuracio.getUnidadesPath(descarga.getCodigo()));
            FileUtils.cleanDirectory(directorio);
            // Contactos
            contactoUOEjb.deleteAll();
            //Unidades
            unidadEjb.deleteHistoricosUnidad();
            unidadEjb.deleteAll();
            descargaEjb.deleteAllByTipo(Dir3caibConstantes.UNIDAD);
        }

    }

    /**
     * Método que permite borrar todos los datos del catálogo.
     *
     * @throws Exception
     */
    public void eliminarCatalogoCompleto() throws Exception {

        log.info("Eliminar Catalogo completo");
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.CATALOGO);
        if (descarga != null) {
            File directorio = new File(Configuracio.getCatalogosPath(descarga.getCodigo()));
            FileUtils.cleanDirectory(directorio);
            // Eliminamos catálogo
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




}
