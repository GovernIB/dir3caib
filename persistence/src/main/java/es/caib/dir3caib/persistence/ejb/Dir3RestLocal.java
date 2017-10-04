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

  public List<ObjetoDirectorio> findUnidadesByDenominacion(String denominacion) throws Exception;

  public List<ObjetoDirectorio> findOficinasByDenominacion(String denominacion) throws Exception;
  public Boolean tieneHijos(String codigo) throws Exception;
  public List<Unidad> obtenerArbolUnidades(String codigo, String fechaActualizacion) throws Exception;

  public List<Nodo> obtenerArbolUnidades(String codigo) throws Exception;
  public List<Oficina> obtenerOficinasOrganismo(String codigo, String fechaActualizacion) throws Exception;
  public Boolean tieneOficinasOrganismo(String codigo) throws Exception;

  public List<Nodo> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, boolean conOficinas, boolean unidadRaiz, Long provincia, String localidad) throws Exception;

  public List<Nodo> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad, Long provincia, String localidad, boolean oficinasSir) throws Exception;
  public String unidadDenominacion(String codigo) throws Exception;
  public String oficinaDenominacion(String codigo) throws Exception;

  public List<Nodo> busquedaDenominacionComunidad(String denominacion, Long codComunidad) throws Exception;

    public List<CodigoValor> getLocalidadByProvinciaEntidadGeografica(Long codigoProvincia, String codigoEntidadGeografica) throws Exception;

    public List<CodigoValor> getComunidadesAutonomas() throws Exception;

    public List<CodigoValor> getEntidadesGeograficas() throws Exception;

    public List<CodigoValor> getProvincias() throws Exception;

    public List<CodigoValor> getProvinciasByComunidad(Long codComunidad) throws Exception;

    public List<CodigoValor> getNivelesAdministracion() throws Exception;

  public List<CodigoValor> getAmbitoTerritorialByAdministracion(Long nivelAdministracion) throws Exception;

}
