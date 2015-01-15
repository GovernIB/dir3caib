package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.utils.Paginacion;

import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author mgonzalez
 * @author anadal
 */

@Local
public interface UnidadLocal extends BaseEjb<Unidad, String> {
 
    public List<Unidad> getMaxResult(int maxResult) throws Exception;

    public void deleteAll() throws Exception;
    public void deleteHistoricosUnidad() throws Exception;

    public Unidad findFullById(String id) throws Exception;

    /**
     * Realiza una busqueda de {@link es.caib.dir3caib.persistence.model.Unidad} según los parámetros
     * @param pageNumber
     * @param codigo
     * @param denominacion
     * @param codigoNivelAdministracion
     * @param codAmbitoTerritorial
     * @param codComunidad
     * @param codigoProvincia
     * @return
     * @throws Exception
     */
    public Paginacion busqueda(Integer pageNumber,String codigo, String denominacion,Long codigoNivelAdministracion,
                               String codAmbitoTerritorial, Long codComunidad, Long codigoProvincia, Boolean unidadRaiz) throws Exception;

    /**
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public List<Unidad> obtenerArbolUnidades(String codigo, String fechaActualizacion, String fechaSincronizacion) throws Exception;

    public Boolean tieneHijos(String codigo) throws Exception;

    public List<Unidad> hijos(String codigo) throws Exception;

    public List<Unidad> findByDenominacion(String denominacion) throws Exception;
    
    public List<Unidad> getPagination(int startItem, int numberOfItems) throws Exception;

    public List<String> getAllCodigos();
    
    public List<Unidad> getListByIds(List<String> ids) throws Exception;

  
}
