package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatPais;
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
  
  List<ObjetoDirectorio> findUnidadesByDenominacion(String denominacion, boolean denominacionCooficial, String estado) throws Exception;

  List<ObjetoDirectorio> findOficinasByDenominacion(String denominacion) throws Exception;
  
  List<ObjetoDirectorio> findOficinasByDenominacion(String denominacion, boolean denominacionCooficial, String estado) throws Exception;
  
  Boolean tieneHijos(String codigo) throws Exception;
  
  List<Unidad> obtenerArbolUnidades(String codigo, String fechaActualizacion, String estado) throws Exception;

  List<Nodo> obtenerArbolUnidades(String codigo) throws Exception;
  
  List<Nodo> obtenerArbolUnidades(String codigo, boolean denominacionCooficial, String estado) throws Exception;
  
  List<Oficina> obtenerOficinasOrganismo(String codigo, String fechaActualizacion, String estado) throws Exception;

  List<Oficina> obtenerArbolOficinasOpenData(String codigo) throws Exception;
  
  List<Oficina> getOficinasBalearesOpenData() throws Exception;
  
  Boolean tieneOficinasOrganismo(String codigo) throws Exception;

  List<Nodo> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, boolean conOficinas, boolean unidadRaiz, Long provincia, String localidad, boolean vigentes) throws Exception;

  List<Nodo> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, Long provincia, String localidad, boolean oficinasSir, boolean vigentes) throws Exception;
  
  String unidadDenominacion(String codigo) throws Exception;
  
  String unidadDenominacion(String codigo, boolean denominacionCooficial, String estado) throws Exception;

  String unidadEstado(String codigo) throws Exception;
  
  String oficinaDenominacion(String codigo) throws Exception;
  
  String oficinaDenominacion(String codigo, boolean denominacionCooficial, String estado) throws Exception;

  List<Nodo> busquedaDenominacionComunidad(String denominacion, Long codComunidad) throws Exception;

  List<CodigoValor> getLocalidadByProvinciaEntidadGeografica(Long codigoProvincia, String codigoEntidadGeografica, String estado) throws Exception;

  List<CodigoValor> getComunidadesAutonomas(String estado) throws Exception;

  List<CodigoValor> getEntidadesGeograficas(String estado) throws Exception;
  
  List<CatPais> getPaises(String estado) throws Exception;
  
  List<CodigoValor> getTiposVia(String estado) throws Exception;

  List<CodigoValor> getProvincias(String estado) throws Exception;

  List<CodigoValor> getProvinciasByComunidad(Long codComunidad, String estado) throws Exception;

  List<CodigoValor> getNivelesAdministracion(String estado) throws Exception;

  List<CodigoValor> getAmbitoTerritorialByAdministracion(Long nivelAdministracion, String estado) throws Exception;

  List<Oficina> obtenerOficinasSIRUnidad(String codigoUnidad) throws Exception;

  List<Nodo> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad,
		boolean conOficinas, boolean unidadRaiz, Long provincia, String localidad, boolean vigentes,
		boolean denominacionCooficial) throws Exception;

  List<Nodo> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad,
		Long provincia, String localidad, boolean oficinasSir, boolean vigentes, boolean denominacionCooficial)
		throws Exception;

  List<Nodo> busquedaDenominacionComunidad(String denominacion, Long codComunidad, boolean denominacionCooficial)
		throws Exception;
  
  List<CodigoValor> getEstadosEntidad(String estado) throws Exception;

}
