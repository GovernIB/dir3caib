package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.ws.CatComunidadAutonomaTF;
import es.caib.dir3caib.persistence.model.ws.CatEntidadGeograficaTF;
import es.caib.dir3caib.persistence.model.ws.CatLocalidadTF;
import es.caib.dir3caib.persistence.model.ws.CatProvinciaTF;
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
@RunAs("DIR_WS")  //todo añadir seguridad
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

    @EJB(mappedName = "dir3caib/CatLocalidadEJB/local")
    private CatLocalidadLocal catLocalidadEjb;

    @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
    private CatEntidadGeograficaLocal catEntidadGeograficaEjb;

    @EJB(mappedName = "dir3caib/ServicioEJB/local")
    private CatServicioLocal servicioEjb;

    @EJB(mappedName = "dir3caib/CatTipoViaEJB/local")
    private CatTipoViaLocal catTipoViaEjb;


  /**
   * Obtiene todos los estados en los que puede estar una entidad(organismo).
   * @return
   * @throws Exception
   */
    @Override
    public List<CatEstadoEntidad> obtenerCatEstadoEntidad() throws Exception {
        return catEstadoEntidadEjb.getAll();
    }

   /**
   * Obtiene todos los niveles de administración
   * @return
   * @throws Exception
   */
    @Override
    public List<CatNivelAdministracion> obtenerCatNivelAdministracion() throws Exception {
        return catNivelAdministracionEjb.getAll();
    }

    /**
     * Obtiene todos los paises del catálogo
     * @return
     * @throws Exception
     */
    @Override
    public List<CatPais> obtenerCatPais() throws Exception {
        return catPaisEjb.getAll();
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
      * Obtiene todas las provincias
      * @return
      * @throws Exception
      */
    @Override
    public List<CatProvinciaTF> obtenerCatProvincia() throws Exception {
          List<CatProvincia>  provincias = catProvinciaEjb.getAll();
          List<CatProvinciaTF> provinciasTF = new ArrayList<CatProvinciaTF>();

          for( CatProvincia provincia : provincias){
            provinciasTF.add(CatProvinciaTF.generar(provincia));
          }

          return provinciasTF;

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
     * Obtiene todos los Servicios
     * @return
     * @throws Exception
     */
    @Override
    public List<CatServicio> obtenerCatServicio() throws Exception{
        return servicioEjb.getAll();
    }

    /**
     * Obtiene todos los Tipo Via
     * @return
     * @throws Exception
     */
    @Override
    public List<CatTipoVia> obtenerCatTipoVia() throws Exception{
        return catTipoViaEjb.getAll();
    }
}
