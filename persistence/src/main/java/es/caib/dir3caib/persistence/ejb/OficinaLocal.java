/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;
import es.caib.dir3caib.persistence.utils.Paginacion;

import javax.ejb.Local;
import java.util.List;

/**
 * 
 * @author mgonzalez
 * @author anadal
 */
@Local
public interface OficinaLocal extends BaseEjb<Oficina, String> {

  public void deleteAll() throws Exception;

  public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion,
      Long codigoNivelAdministracion, Long codComunidad, Long codigoProvincia)
      throws Exception;

  public void deleteHistoricosOficina() throws Exception;

  public void deleteServiciosOficina() throws Exception;

  public Oficina findFullById(String id) throws Exception;

  public ObjetoBasico findReduceOficina(String id) throws Exception;

  public Boolean tieneHijos(String codigo) throws Exception;

  public List<ObjetoBasico> hijos(String codigo) throws Exception;

  public List<Oficina> obtenerOficinasOrganismo(String codigo, String fechaActualizacion,
      String fechaSincronizacion) throws Exception;
  /**
     *  Método que devuelve  el arbol de oficinas de una oficina padre,
     * teniendo en cuenta la fecha de la ultima actualización de regweb.
     * Se emplea para la sincronizacion y actualización con regweb
     * @param codigo código de la oficina padre
     * @param fechaActualizacion  fecha de la ultima actualización de regweb
     * @param fechaSincronizacion fecha la primera sincronización de regweb.
     * @return
     * @throws Exception
     */
  public List<Oficina> obtenerArbolOficinas(String codigo, String fechaActualizacion,
      String fechaSincronizacion) throws Exception;

  /**
     * Obtiene las oficinas SIR de una unidad que están vigentes
     * @param codigo código de la Unidad
     * @return  listado de Oficinas
     * @throws Exception
     */
  public List<Oficina> obtenerOficinasSIRUnidad(String codigo) throws Exception;

  public List<String> getAllCodigos() throws Exception;
}
