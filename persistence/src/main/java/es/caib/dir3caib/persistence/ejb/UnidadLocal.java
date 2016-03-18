package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;
import es.caib.dir3caib.persistence.utils.Paginacion;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
     * @param codigoEstado
     * @return
     * @throws Exception
     */
    public Paginacion busqueda(Integer pageNumber,String codigo, String denominacion,Long codigoNivelAdministracion,
                               String codAmbitoTerritorial, Long codComunidad, Long codigoProvincia, Boolean unidadRaiz, String codigoEstado) throws Exception;

    /**
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public List<Unidad> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;
    public List<Unidad> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception;

    public Boolean tieneHijos(String codigo) throws Exception;

    public List<ObjetoBasico> hijosOB(String codigo, String estado) throws Exception;

    public List<Unidad> hijosPrimerNivel(String codigo) throws Exception;

    public void arbolHijos(Set<Unidad> unidadesPadres, String estado, Set<Unidad> hijosTotales) throws Exception;

    public List<Unidad> findByDenominacion(String denominacion) throws Exception;

    /**
     * Obtiene al arbol de una Unidad
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public List<Unidad> obtenerArbol(String codigo) throws Exception;
    
    public List<Unidad> getPagination(int startItem, int numberOfItems) throws Exception;

    /**
     * Obtiene la Denominacion de una Unidad
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public String unidadDenominacion(String codigo) throws Exception;

    public List<String> getAllCodigos();
    
    public List<Unidad> getListByIds(List<String> ids) throws Exception;

    public ObjetoBasico findReduceUnidad(String id, String estado) throws Exception;

    public Unidad obtenerUnidad(String codigo) throws Exception;

  
}
