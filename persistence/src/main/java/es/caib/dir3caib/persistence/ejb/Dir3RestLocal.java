package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.utils.CodigoValor;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.ObjetoDirectorio;

import javax.ejb.Local;
import java.util.List;

/**
 * Created 1/04/14 9:52
 *
 * @author mgonzalez
 */
@Local
public interface Dir3RestLocal  {

  List<ObjetoDirectorio> findUnidadesByDenominacion(String denominacion) throws Exception;

  List<ObjetoDirectorio> findOficinasByDenominacion(String denominacion) throws Exception;
  Boolean tieneHijos(String codigo) throws Exception;
  List<Unidad> obtenerArbolUnidades(String codigo, String fechaActualizacion) throws Exception;

  List<Nodo> obtenerArbolUnidades(String codigo) throws Exception;
  List<Oficina> obtenerOficinasOrganismo(String codigo, String fechaActualizacion) throws Exception;

  List<Oficina> obtenerArbolOficinasOpenData(String codigo) throws Exception;

  List<Oficina> getOficinasBalearesOpenData() throws Exception;
  Boolean tieneOficinasOrganismo(String codigo) throws Exception;

  List<Nodo> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, boolean conOficinas, boolean unidadRaiz, Long provincia, String localidad, boolean vigentes) throws Exception;

  List<Nodo> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, Long provincia, String localidad, boolean oficinasSir, boolean vigentes) throws Exception;
  String unidadDenominacion(String codigo) throws Exception;
  String oficinaDenominacion(String codigo) throws Exception;

  List<Nodo> busquedaDenominacionComunidad(String denominacion, Long codComunidad) throws Exception;

  List<CodigoValor> getLocalidadByProvinciaEntidadGeografica(Long codigoProvincia, String codigoEntidadGeografica) throws Exception;

  List<CodigoValor> getComunidadesAutonomas() throws Exception;

  List<CodigoValor> getEntidadesGeograficas() throws Exception;

  List<CodigoValor> getProvincias() throws Exception;

  List<CodigoValor> getProvinciasByComunidad(Long codComunidad) throws Exception;

  List<CodigoValor> getNivelesAdministracion() throws Exception;

  List<CodigoValor> getAmbitoTerritorialByAdministracion(Long nivelAdministracion) throws Exception;

  List<Oficina> obtenerOficinasSIRUnidad(String codigoUnidad) throws Exception;

}
