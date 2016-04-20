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

 //TODO BORRAR NO SE USA
   /* public List<Unidad> getMaxResult(int maxResult) throws Exception;*/

 /**
  * Borra todas las unidades
  *
  * @throws Exception
  */
    public void deleteAll() throws Exception;

 /**
  * Borra todos los historicos de las unidades
  * @throws Exception
  */
    public void deleteHistoricosUnidad() throws Exception;

 /**
  * Obtiene una unidad que es vigente con sus historicosUO
  *
  * @param id
  * @return
  * @throws Exception
  */
 public Unidad findConHistoricosVigente(String id) throws Exception;


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
  * Método que devuelve el árbol de unidades de la unidad indicada por codigo,
  * teniendo en cuenta la fecha de la ultima actualización de regweb.
  * Se emplea para la sincronizacion y actualización con regweb
     * @param codigo
  * @param fechaActualizacion
  * @param fechaSincronizacion
     * @return
     * @throws Exception
     */
    public List<Unidad> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

 /**
  * Método que devuelve la unidad indicada por código siempre que esté vigente y tenga oficinas donde registrar.
  * A partir de ella se obtienen todos sus hijos vigentes.
  * solicitado por SISTRA
  * @param codigo código de la unidad raiz de la que partimos.
  * @return
  * @throws Exception
     */
    public List<Unidad> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception;

 /**
  * Metodo que comprueba si una unidad tiene más unidades hijas
  * @param codigo
  * @return
  * @throws Exception
     */
    public Boolean tieneHijos(String codigo) throws Exception;

 /**
  * Método que obtiene los hijos de primer nivel de una unidad en función del estado de la unidad padre
  * @param codigo identificador de la unidad padre.
  * @param estado estado de la unidad padre.
  *
  * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    public List<ObjetoBasico> hijosOB(String codigo, String estado) throws Exception;

 /**
  * Método que obtiene los hijos de primer nivel de una unidad en función del estado de la unidad padre
  * @param codigo identificador de la unidad padre.
  *
  * @return  {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    public List<Unidad> hijosPrimerNivel(String codigo) throws Exception;

 /**
  * Metodo que obtiene los hijos de primer nivel de una unidad en función del estado de la unidad padre
  * @param unidadesPadres unidadesPadres de las que obtener hijos
  * @param estado indica el estado de los hijos
  * @param hijosTotales lista con todos los hijos encontrados de manera recursiva.
  *
  * @return  {@link es.caib.dir3caib.persistence.model.Unidad}
     */
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

 /**
  * Obtiene el codigo y la denominación de una Unidad con estado vigente. Se emplea para mostrar el arbol de unidades.
  * @param id identificador de la unidad
  * @param estado estado de la unidad
  * @return  {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     * */
    public ObjetoBasico findReduceUnidad(String id, String estado) throws Exception;

 /**
  * Obtiene el codigo y la denominación de una Unidad con estado vigente. Se emplea para mostrar el arbol de unidades.
  * @param id     identificador de la unidad
  * @param estado estado de la unidad
  * @return {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    public ObjetoBasico findUnidad(String id, String estado) throws Exception;

 /**
  * Obtiene solo el código de la unidad indicada
  * @param codigo
  * @return
  * @throws Exception
     */
    public Unidad obtenerUnidad(String codigo) throws Exception;

    /**
     * Obtiene todas las Unidades de la Unidad Raiz que tienen OFicinas.
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public List<Unidad> obtenerUnidadesConOficina(String codigo) throws Exception;

  
}
