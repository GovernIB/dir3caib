package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.Unidad;

import javax.ejb.Local;
import java.util.List;

/**
 * Created 1/04/14 9:52
 *
 * @author mgonzalez
 */
@Local
public interface Dir3RestLocal  {

  public List<Unidad> findUnidadesByDenominacion(String denominacion) throws Exception;
  public List<Oficina> findOficinasByDenominacion(String denominacion) throws Exception;
  public Boolean tieneHijos(String codigo) throws Exception;
  public List<Unidad> obtenerArbolUnidades(String codigo, String fechaActualizacion) throws Exception;
  public List<Oficina> obtenerOficinasOrganismo(String codigo, String fechaActualizacion) throws Exception;
  public List<Unidad> busquedaOrganismos(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad) throws Exception;
  public List<Oficina> busquedaOficinas(String codigo, String denominacion, Long codigoNivelAdministracion, Long codComunidad) throws Exception;
  public String unidadDenominacion(String codigo) throws Exception;
  public String oficinaDenominacion(String codigo) throws Exception;
}
