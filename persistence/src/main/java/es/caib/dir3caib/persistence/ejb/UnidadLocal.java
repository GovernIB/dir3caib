package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.utils.Nodo;
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
     * Obtiene si una unidad ha sido actualizada con fecha posterior a la fecha de actualización.
     * Se usa para la sincronizacion con regweb3
     *
     * @param id
     * @param fechaActualizacion
     * @return
     * @throws Exception
     */
    public Unidad findUnidadActualizada(String id, Date fechaActualizacion) throws Exception;


 /**
  * Obtiene una unidad que es vigente con sus historicosUO
  *
  * @param id
  * @return
  * @throws Exception
  */
 public Unidad findConHistoricosVigente(String id) throws Exception;

    /**
     * Método que busca la unidad con id indicado y estado indicado
     *
     * @param id
     * @param estado
     * @return
     * @throws Exception
     */
    public Unidad findUnidadEstado(String id, String estado) throws Exception;



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
  * teniendo en cuenta la fecha de la ultima actualización de regweb para el caso en que la unidad indicada no es raiz.
  * Se emplea para la sincronizacion y actualización con regweb
     * @param codigo
  * @param fechaActualizacion
  * @param fechaSincronizacion
     * @return
     * @throws Exception
     */
 public List<Unidad> obtenerArbolUnidadesUnidadNoRaiz(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;


    /**
     * Método que devuelve el árbol de unidades de la unidad indicada por codigo,
     * teniendo en cuenta la fecha de la ultima actualización de regweb y se emplea para el caso que la unidad indicada es raiz.
     * Se emplea para la sincronizacion y actualización con regweb
     *
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @return
     * @throws Exception
     */
    public List<Unidad> obtenerArbolUnidadesUnidadRaiz(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

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
  * @return  {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
 public List<Nodo> hijosOB(String codigo, String estado) throws Exception;

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

    /**
     * Obtiene una unidad por su denominación
     *
     * @param denominacion
     * @return
     * @throws Exception
     */
    public List<Unidad> findByDenominacion(String denominacion) throws Exception;

    /**
     * Obtiene el arbol de una Unidad, pero solo los códigos
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public List<Unidad> obtenerArbol(String codigo) throws Exception;

    /**
     * Obtiene una lista de unidades páginada
     * @param startItem
     * @param numberOfItems
     * @return
     * @throws Exception
     */
    public List<Unidad> getPagination(int startItem, int numberOfItems) throws Exception;

    /**
     * Obtiene la Denominacion de una Unidad
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public String unidadDenominacion(String codigo) throws Exception;

    /**
     * Obtiene todos los códigos de las unidades. Se emplea para la importación de las unidades desde Madrid.
     * @return
     */
    public List<String> getAllCodigos();


    /**
     * Devuelve todas las unidades de la lista de ids indicados. Se emplea para montar la cache de unidades
     * en la importación de unidades desde Madrid
     * @param ids
     * @return
     * @throws Exception
     */
    public List<Unidad> getListByIds(List<String> ids) throws Exception;


 /**
  * Obtiene el codigo y la denominación de una Unidad con estado vigente. Se emplea para mostrar el arbol de unidades.
  * @param id     identificador de la unidad
  * @param estado estado de la unidad
  * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
 public Nodo findUnidad(String id, String estado) throws Exception;

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

    /**
     * Se mira que si la unidad,  su fecha de extinción y anulacion son posteriores
     * a la fecha de la primera sincronizacion con regweb. Así evitamos enviar unidades que fueron extinguidas o anuladas
     * antes de la primera sincronización con Madrid.
     *
     * @param unidad      unidad
     * @param fechaSincro fecha de la primera sincronizacion con regweb
     * @return
     * @throws Exception
     */
    public boolean unidadValida(Unidad unidad, Date fechaSincro) throws Exception;

  
}
