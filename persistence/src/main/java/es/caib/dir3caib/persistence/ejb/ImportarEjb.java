package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created 8/09/14 10:13
 *  Ejb que se encarga de invocar los métodos necesarios para la importación del catálogo,
 *  de las unidades y de las oficinas vía task de quartz. Se crea este ejb que es como una
 *  capa superior a los que ya existen y que necesitan autenticación.
 *
 * @author mgonzalez
 */
@Stateless(name = "ImportarEJB")
@RunAs("DIR_ADMIN")
public class ImportarEjb implements ImportarLocal {

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

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;


    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    protected UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    protected OficinaLocal oficinaEjb;

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


    /**
    * persiste un elemento descarga
    * @return
    * @throws Exception
    */
     @Override
     public void persistDescarga(Descarga descarga) throws Exception {
       descargaEjb.persist(descarga);
     }

     /**
    * actualiza un elemento descarga
    * @return
    * @throws Exception
    */
     @Override
     public void mergeDescarga(Descarga descarga) throws Exception {
       descargaEjb.merge(descarga);
     }

      /**
    * obtiene un elemento descarga
    * @return
    * @throws Exception
    */
     @Override
     public Descarga findDescargaByTipo(String tipo) throws Exception {
       return descargaEjb.findByTipo(tipo);
     }

    /**
    * persiste un elemento ambitoTerritorial
    * @return
    * @throws Exception
    */
     @Override
     public void persistAmbitoTerritorial(CatAmbitoTerritorial ambitoTerritorial) throws Exception {
       catAmbitoTerritorialEjb.persist(ambitoTerritorial);
     }

     /**
    * obtiene un elemento ambito territorial
    * @return
    * @throws Exception
    */
     @Override
     public CatAmbitoTerritorial findAmbitoTerritorial(CatAmbitoTerritorialPK idAmbito) throws Exception {
       return catAmbitoTerritorialEjb.findById(idAmbito);
     }

     /**
    * persiste un elemento entidad geografica
    * @return
    * @throws Exception
    */
     @Override
     public void persistEntidadGeografica(CatEntidadGeografica entidadGeografica) throws Exception {
       catEntidadGeograficaEjb.persist(entidadGeografica);
     }

     /**
    * obtiene un elemento entidad geografica
    * @return
    * @throws Exception
    */
     @Override
     public CatEntidadGeografica findEntidadGeografica(String idEntidad) throws Exception {
       return catEntidadGeograficaEjb.findById(idEntidad);
     }

     /**
    * persiste un elemento estado entidad
    * @return
    * @throws Exception
    */
     @Override
     public void persistEstadoEntidad(CatEstadoEntidad estadoEntidad) throws Exception {
       catEstadoEntidadEjb.persist(estadoEntidad);
     }

     /**
    * obtiene un elemento estado entidad
    * @return
    * @throws Exception
    */
     @Override
     public CatEstadoEntidad findEstadoEntidad(String estado) throws Exception {
       return catEstadoEntidadEjb.findById(estado);
     }

     /**
    * persiste un elemento jerarquia oficina
    * @return
    * @throws Exception
    */
     @Override
     public void persistJerarquiaOficina(CatJerarquiaOficina jerarquiaOficina) throws Exception {
       catJerarquiaOficinaEjb.persist(jerarquiaOficina);
     }

     /**
    * obtiene un elemento jerarquia oficina
    * @return
    * @throws Exception
    */
     @Override
     public CatJerarquiaOficina findJerarquiaOficina(Long idJerarquia) throws Exception {
       return catJerarquiaOficinaEjb.findById(idJerarquia);
     }

     /**
    * persiste un elemento motivo extinción
    * @return
    * @throws Exception
    */
     @Override
     public void persistMotivoExtincion(CatMotivoExtincion motivoExtincion) throws Exception {
       catMotivoExtincionEjb.persist(motivoExtincion);
     }

     /**
    * obtiene un elemento motivo extincion
    * @return
    * @throws Exception
    */
     @Override
     public CatMotivoExtincion findMotivoExtincion(String idMotivoExtincion) throws Exception {
       return catMotivoExtincionEjb.findById(idMotivoExtincion);
     }

     /**
    * persiste un elemento nivel administracion
    * @return
    * @throws Exception
    */
     @Override
     public void persistNivelAdministracion(CatNivelAdministracion nivelAdministracion) throws Exception {
       catNivelAdministracionEjb.persist(nivelAdministracion);
     }

     /**
    * obtiene un elemento nivel administracion
    * @return
    * @throws Exception
    */
     @Override
     public CatNivelAdministracion findNivelAdministracion(Long idNivelAdministracion) throws Exception {
       return catNivelAdministracionEjb.findById(idNivelAdministracion);
     }

     /**
    * persiste un elemento pais
    * @return
    * @throws Exception
    */
     @Override
     public void persistPais(CatPais pais) throws Exception {
       catPaisEjb.persist(pais);
     }

     /**
    * obtiene un elemento pais
    * @return
    * @throws Exception
    */
     @Override
     public CatPais findPais(Long idPais) throws Exception {
       return catPaisEjb.findById(idPais);
     }

     /**
    * persiste un elemento comunidad autonoma
    * @return
    * @throws Exception
    */
     @Override
     public void persistComunidadAutonoma(CatComunidadAutonoma comunidadAutonoma) throws Exception {
       catComunidadAutonomaEjb.persist(comunidadAutonoma);
     }

     /**
    * obtiene un elemento comunidad autónoma
    * @return
    * @throws Exception
    */
     @Override
     public CatComunidadAutonoma findComunidadAutonoma(Long idComunidad) throws Exception {
       return catComunidadAutonomaEjb.findById(idComunidad);
     }

     /**
    * persiste un elemento provincia
    * @return
    * @throws Exception
    */
     @Override
     public void persistProvincia(CatProvincia provincia) throws Exception {
       catProvinciaEjb.persist(provincia);
     }

     /**
    * obtiene un elemento provincia
    * @return
    * @throws Exception
    */
     @Override
     public CatProvincia findProvincia(Long idProvincia) throws Exception {
       return catProvinciaEjb.findById(idProvincia);
     }

     /**
    * persiste un elemento isla
    * @return
    * @throws Exception
    */
     @Override
     public void persistIsla(CatIsla isla) throws Exception {
       catIslaEjb.persist(isla);
     }

     /**
    * obtiene un elemento isla
    * @return
    * @throws Exception
    */
     @Override
     public CatIsla findIsla(Long idIsla) throws Exception {
       return catIslaEjb.findById(idIsla);
     }

     /**
    * persiste un elemento tipo contacto
    * @return
    * @throws Exception
    */
     @Override
     public void persistTipoContacto(CatTipoContacto tipoContacto) throws Exception {
       catTipoContactoEjb.persist(tipoContacto);
     }

     /**
    * obtiene un elemento tipo contacto
    * @return
    * @throws Exception
    */
     @Override
     public CatTipoContacto findTipoContacto(String idTipoContacto) throws Exception {
       return catTipoContactoEjb.findById(idTipoContacto);
     }

     /**
    * persiste un elemento tipo entidad publica
    * @return
    * @throws Exception
    */
     @Override
     public void persistTipoEntidadPublica(CatTipoEntidadPublica tipoEntidadPublica) throws Exception {
       catTipoEntidadPublicaEjb.persist(tipoEntidadPublica);
     }

     /**
    * obtiene un elemento tipo entidad publica
    * @return
    * @throws Exception
    */
     @Override
     public CatTipoEntidadPublica findTipoEntidadPublica(String idTipoEntidadPublica) throws Exception {
       return catTipoEntidadPublicaEjb.findById(idTipoEntidadPublica);
     }

     /**
    * persiste un elemento tipo unidad organica
    * @return
    * @throws Exception
    */
     @Override
     public void persistTipoUnidadOrganica(CatTipoUnidadOrganica tipoUnidadOrganica) throws Exception {
       catTipoUnidadOrganicaEjb.persist(tipoUnidadOrganica);
     }

     /**
    * obtiene un elemento tipo Unidad Organica
    * @return
    * @throws Exception
    */
     @Override
     public CatTipoUnidadOrganica findTipoUnidadOrganica(String idTipoUnidadOrganica) throws Exception {
       return catTipoUnidadOrganicaEjb.findById(idTipoUnidadOrganica);
     }

     /**
    * persiste un elemento tipo vía
    * @return
    * @throws Exception
    */
     @Override
     public void persistTipoVia(CatTipoVia tipoVia) throws Exception {
      catTipoViaEjb.persist(tipoVia);
     }

     /**
    * obtiene un elemento tipo vía
    * @return
    * @throws Exception
    */
     @Override
     public CatTipoVia findTipoVia(Long idTipoVia) throws Exception {
       return catTipoViaEjb.findById(idTipoVia);
     }

     /**
    * persiste un elemento localidad
    * @return
    * @throws Exception
    */
     @Override
     public void persistLocalidad(CatLocalidad localidad) throws Exception {
      catLocalidadEjb.persist(localidad);
     }

     /**
    * obtiene un elemento localidad
    * @return
    * @throws Exception
    */
     @Override
     public CatLocalidad findLocalidad(CatLocalidadPK idLocalidad) throws Exception {
       return catLocalidadEjb.findById(idLocalidad);
     }

     /**
    * persiste un elemento unidad
    * @return
    * @throws Exception
    */
     @Override
     public Unidad persistUnidad(Unidad unidad) throws Exception {
      return unidadEjb.persist(unidad);
     }

     /**
    * obtiene un elemento unidad
    * @return
    * @throws Exception
    */
     @Override
     public Unidad findUnidad(String idUnidad) throws Exception {
       return unidadEjb.findById(idUnidad);
     }

     /**
     * actualiza un elemento unidad
     * @return
     * @throws Exception
     */
     @Override
     public Unidad mergeUnidad(Unidad unidad) throws Exception {
       return unidadEjb.merge(unidad);
     }

    /**
    * persiste un elemento oficina
    * @return
    * @throws Exception
    */
     @Override
     public Oficina persistOficina(Oficina oficina) throws Exception {
      return oficinaEjb.persist(oficina);
     }

     /**
    * obtiene un elemento oficina
    * @return
    * @throws Exception
    */
     @Override
     public Oficina findOficina(String idOficina) throws Exception {
       return oficinaEjb.findById(idOficina);
     }

     /**
     * actualiza un elemento unidad
     * @return
     * @throws Exception
     */
     @Override
     public Oficina mergeOficina(Oficina oficina) throws Exception {
      return oficinaEjb.merge(oficina);
     }

     /**
     * persiste un elemento contacto unidad organica
     * @return
     * @throws Exception
     */
      @Override
      public void persistContactoUO(ContactoUnidadOrganica contactoUO) throws Exception {
         contactoUOEjb.persist(contactoUO);
      }

       /**
      * obtiene un elemento contacto unidad organica
      * @return
      * @throws Exception
      */
      @Override
      public ContactoUnidadOrganica findContactoUO(Long idContactoUO) throws Exception {
         return contactoUOEjb.findById(idContactoUO);
      }

      /**
     * persiste un elemento contacto oficina
     * @return
     * @throws Exception
     */
      @Override
      public void persistContactoOfi(ContactoOfi contactoOfi) throws Exception {
         contactoOfiEjb.persist(contactoOfi);
      }

       /**
      * obtiene un elemento contacto oficina
      * @return
      * @throws Exception
      */
      @Override
      public ContactoOfi findContactoOfi(Long idContactoOfi) throws Exception {
         return contactoOfiEjb.findById(idContactoOfi);
      }

      /**
     * persiste un elemento relacion organizativa ofi
     * @return
     * @throws Exception
     */
      @Override
      public void persistRelacionOrgOfi(RelacionOrganizativaOfi relOrganizativaOfi) throws Exception {
         relOrgOfiEjb.persist(relOrganizativaOfi);
      }

       /**
      * obtiene un elemento relacion organizativa ofi
      * @return
      * @throws Exception
      */
      @Override
      public RelacionOrganizativaOfi findRelOrganizativaOfi(String codigoUnidad, String codigoOficina) throws Exception {
         return relOrgOfiEjb.findByPKs(codigoUnidad, codigoOficina);
      }


       /**
      * persiste un elemento relacion sir ofi
      * @return
      * @throws Exception
      */
      @Override
      public void persistRelacionSirOfi(RelacionSirOfi relacionSirOfi) throws Exception {
         relSirOfiEjb.persist(relacionSirOfi);
      }

       /**
      * obtiene un elemento relacion sir ofi
      * @return
      * @throws Exception
      */
      @Override
      public RelacionSirOfi findRelSirOfi(RelacionSirOfiPK idRelSirOfi) throws Exception {
         return relSirOfiEjb.findById(idRelSirOfi);
      }

      /**
      * persiste un elemento servicio
      * @return
      * @throws Exception
      */
      @Override
      public void persistServicio(Servicio servicio) throws Exception {
         servicioEjb.persist(servicio);
      }

       /**
      * obtiene un elemento relacion sir ofi
      * @return
      * @throws Exception
      */
      @Override
      public Servicio findServicio(Long idServicio) throws Exception {
         return servicioEjb.findById(idServicio);
      }
}
