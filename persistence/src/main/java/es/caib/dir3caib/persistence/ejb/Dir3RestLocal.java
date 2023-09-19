package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatPais;
import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.json.OficinaRest;
import es.caib.dir3caib.persistence.model.json.UnidadRest;
import es.caib.dir3caib.persistence.model.json.UnidadRestSistra;
import es.caib.dir3caib.persistence.utils.CodigoValor;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.ObjetoDirectorio;
import es.caib.dir3caib.persistence.utils.ObjetoDirectorioExtendido;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Created 1/04/14 9:52
 *
 * @author mgonzalez
 */
@Local
public interface Dir3RestLocal {

    /**
     *
     * @param denominacion
     * @return
     * @throws Exception
     */
    List<ObjetoDirectorio> findUnidadesByDenominacion(String denominacion) throws Exception;

    /**
     *
     * @param denominacion
     * @param denominacionCooficial
     * @param estado
     * @return
     * @throws Exception
     */
    List<ObjetoDirectorio> findUnidadesByDenominacion(String denominacion, boolean denominacionCooficial, String estado) throws Exception;

    /**
     *
     * @param denominacion
     * @return
     * @throws Exception
     */
    List<ObjetoDirectorio> findOficinasByDenominacion(String denominacion) throws Exception;

    /**
     *
     * @param denominacion
     * @param denominacionCooficial
     * @param estado
     * @return
     * @throws Exception
     */
    List<ObjetoDirectorio> findOficinasByDenominacion(String denominacion, boolean denominacionCooficial, String estado) throws Exception;

    /**
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    Boolean tieneHijos(String codigo) throws Exception;

    /**
     *
     * @param codigo
     * @param fechaActualizacion
     * @param estado
     * @return
     * @throws Exception
     */
    List<Unidad> obtenerArbolUnidades(String codigo, String fechaActualizacion, String estado) throws Exception;

    /**
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    List<Nodo> obtenerArbolUnidades(String codigo) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacionCooficial
     * @param estado
     * @return
     * @throws Exception
     */
    List<Nodo> obtenerArbolUnidades(String codigo, boolean denominacionCooficial, String estado) throws Exception;

    /**
     *
     * @param codigo
     * @param fechaActualizacion
     * @param estado
     * @return
     * @throws Exception
     */
    List<Oficina> obtenerOficinasOrganismo(String codigo, String fechaActualizacion, String estado) throws Exception;

    /**
     *
     * @param codigo
     * @param estado
     * @return
     * @throws Exception
     */
    List<Oficina> obtenerArbolOficinasOpenData(String codigo, String estado) throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<Oficina> getOficinasBalearesOpenData(String estado) throws Exception;

    /**+
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    Boolean tieneOficinasOrganismo(String codigo) throws Exception;

    /*TODO ELIMINAR los métodos que no se usan */

    /**
     *
     * @param codigo
     * @param denominacion
     * @param codigoNivelAdministracion
     * @param codComunidad
     * @param conOficinas
     * @param unidadRaiz
     * @param provincia
     * @param localidad
     * @param vigentes
     * @return
     * @throws Exception
     */
    List<Nodo> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, boolean conOficinas, boolean unidadRaiz, Long provincia, String localidad, boolean vigentes) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacion
     * @param codigoNivelAdministracion
     * @param codComunidad
     * @param provincia
     * @param localidad
     * @param oficinasSir
     * @param vigentes
     * @return
     * @throws Exception
     */
    List<Nodo> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, Long provincia, String localidad, boolean oficinasSir, boolean vigentes) throws Exception;

    /**
     * Comprueba la existencia de una Oficina vigente
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    Boolean existeOficina(String codigo) throws Exception;

    /**
     * Comprueba la existencia de una Unidad vigente
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    Boolean existeUnidad(String codigo) throws Exception;

    /**
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    String unidadDenominacion(String codigo) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacionCooficial
     * @param estado
     * @return
     * @throws Exception
     */
    String unidadDenominacion(String codigo, boolean denominacionCooficial, String estado) throws Exception;

    /**
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    String unidadEstado(String codigo) throws Exception;

    /**
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    String oficinaDenominacion(String codigo) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacionCooficial
     * @param estado
     * @return
     * @throws Exception
     */
    String oficinaDenominacion(String codigo, boolean denominacionCooficial, String estado) throws Exception;

    /**
     *
     * @param denominacion
     * @param codComunidad
     * @return
     * @throws Exception
     */
    List<Nodo> busquedaDenominacionComunidad(String denominacion, Long codComunidad) throws Exception;

    /**
     *
     * @param codigoProvincia
     * @param codigoEntidadGeografica
     * @param estado
     * @return
     * @throws Exception
     */
    List<CodigoValor> getLocalidadByProvinciaEntidadGeografica(Long codigoProvincia, String codigoEntidadGeografica, String estado) throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<CodigoValor> getComunidadesAutonomas(String estado) throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<CodigoValor> getEntidadesGeograficas(String estado) throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<CatPais> getPaises(String estado) throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<CodigoValor> getTiposVia(String estado) throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<CodigoValor> getProvincias(String estado) throws Exception;

    /**
     *
     * @param codComunidad
     * @param estado
     * @return
     * @throws Exception
     */
    List<CodigoValor> getProvinciasByComunidad(Long codComunidad, String estado) throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<CodigoValor> getNivelesAdministracion(String estado) throws Exception;

    /**
     *
     * @param nivelAdministracion
     * @param estado
     * @return
     * @throws Exception
     */
    List<CodigoValor> getAmbitoTerritorialByAdministracion(Long nivelAdministracion, String estado) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacion
     * @param codigoNivelAdministracion
     * @param codComunidad
     * @param conOficinas
     * @param unidadRaiz
     * @param provincia
     * @param localidad
     * @param vigentes
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    List<Nodo> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad,
                                  boolean conOficinas, boolean unidadRaiz, Long provincia, String localidad, boolean vigentes,
                                  boolean denominacionCooficial) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacion
     * @param codigoNivelAdministracion
     * @param codComunidad
     * @param provincia
     * @param localidad
     * @param oficinasSir
     * @param vigentes
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    List<Nodo> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad,
                                Long provincia, String localidad, boolean oficinasSir, boolean vigentes, boolean denominacionCooficial)
            throws Exception;

    /**
     *
     * @param codigo
     * @param denominacion
     * @param codigoNivelAdministracion
     * @param nivelJerarquico
     * @param codComunidad
     * @param unidadRaiz
     * @param provincia
     * @param vigentes
     * @return
     * @throws Exception
     */
    List<UnidadRestSistra> busquedaOrganismosSistra(String codigo, String denominacion, Long codigoNivelAdministracion, Long nivelJerarquico,
                                                    Long codComunidad, boolean unidadRaiz, Long provincia, boolean vigentes) throws Exception;

    /**
     *
     * @param denominacion
     * @param codComunidad
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    List<Nodo> busquedaDenominacionComunidad(String denominacion, Long codComunidad, boolean denominacionCooficial)
            throws Exception;

    /**
     *
     * @param estado
     * @return
     * @throws Exception
     */
    List<CodigoValor> getEstadosEntidad(String estado) throws Exception;

    /**
     *
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @param denominacionOficial
     * @param estado
     * @return
     * @throws Exception
     */
    OficinaRest obtenerOficina(String codigo, Date fechaActualizacion, Date fechaSincronizacion, boolean denominacionOficial, String estado) throws Exception;

    /**
     *
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @param denominacionOficial
     * @return
     * @throws Exception
     */
    List<OficinaRest> obtenerArbolOficinas(String codigo, Date fechaActualizacion, Date fechaSincronizacion, boolean denominacionOficial) throws Exception;

    /**
     *
     * @param codigoUnidad
     * @param denominacionOficial
     * @return
     * @throws Exception
     */
    List<OficinaRest> obtenerOficinasSIRUnidad(String codigoUnidad, boolean denominacionOficial) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    List<OficinaRest> obtenerArbolOficinasSir(String codigo, boolean denominacionCooficial) throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    Date obtenerFechaUltimaActualizacion() throws Exception;

    /**
     *
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    UnidadRest obtenerUnidad(String codigo, Date fechaActualizacion, Date fechaSincronizacion, boolean denominacionCooficial) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    UnidadRest buscarUnidad(String codigo, boolean denominacionCooficial) throws Exception;

    /**
     *
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @param denominacionCooficial
     * @param mostrarHistoricos
     * @param mostrarContactos
     * @return
     * @throws Exception
     */
    List<UnidadRest> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion, boolean denominacionCooficial, boolean mostrarHistoricos, boolean mostrarContactos)
            throws Exception;

    /**
     *
     * @param codigo
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    List<UnidadRest> obtenerArbolUnidadesDestinatarias(String codigo, boolean denominacionCooficial) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    List<UnidadRest> obtenerHistoricosFinales(String codigo, boolean denominacionCooficial) throws Exception;

    /**
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    List<ObjetoDirectorioExtendido> obtenerHistoricosFinalesExtendido(String codigo) throws Exception;

    /**
     *
     * @param codigo
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    List<UnidadRest> obtenerHistoricosFinalesSIR(String codigo, boolean denominacionCooficial) throws Exception;

}
