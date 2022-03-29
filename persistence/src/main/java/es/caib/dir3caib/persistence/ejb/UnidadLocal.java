package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.HistoricoUO;
import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.UnidadPK;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.Paginacion;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author mgonzalez
 * @author anadal
 */

@Local
public interface UnidadLocal extends BaseEjb<Unidad, String> {

    /**
     * Busca una unidad por su pk
     * @param codigo
     * @param version
     * @return
     * @throws Exception
     */
    Unidad findByPK(String codigo, Long version) throws Exception;

    /**
     * Obtiene la unidad con código el indicado y de mayor version
     * @param codigo
     * @return
     * @throws Exception
     */
    Unidad findByCodigoDir3UltimaVersion(String codigo) throws Exception;


    /**
     * Busca la unidad por su pk pero solo carga el identificador
     * @param codigo
     * @param version
     * @return
     * @throws Exception
     */
   // Unidad findByPKsReduced(String codigo, Long version) throws Exception;

    /**
     * Borra todas las unidades
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;

    /**
     * Borra todos los historicos de las unidades
     *
     * @throws Exception
     */
    void deleteHistoricosUnidad() throws Exception;


    /**
     * Obtiene si una unidad ha sido actualizada con fecha posterior a la fecha de actualización.
     * Para ello mira si la fecha de importación de la unidad es posterior a la fecha de actualización indicada.
     * La fecha de importación de la unidad se actualiza cada vez que se sincroniza con directorio común.
     * <p>
     * Se usa para la sincronizacion con regweb3
     *
     * @param id
     * @param fechaActualizacion
     * @return
     * @throws Exception
     */
    Unidad findUnidadActualizada(String id, Date fechaActualizacion) throws Exception;

    /**
     * Obtiene una unidad que es vigente con sus historicosUO
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    Unidad findConHistoricosVigente(String codigo) throws Exception;

    /**
     * Obtiene una unidad no vigente con sus historicosUO a partir del id
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public Unidad findConHistoricos(String codigo) throws Exception;

    /**
     * Método que busca la unidad con id indicado y estado indicado
     *
     * @param id
     * @param estado
     * @return
     * @throws Exception
     */
    Unidad findUnidadEstado(String id, String estado) throws Exception;


    /**
     * Realiza una busqueda de {@link es.caib.dir3caib.persistence.model.Unidad} según los parámetros
     * Este método se emplea en la búsqueda de organismos destinatarios de regweb3
     *
     * @param pageNumber
     * @param codigo
     * @param denominacion
     * @param codigoNivelAdministracion
     * @param codAmbitoTerritorial
     * @param codComunidad
     * @param codigoProvincia
     * @param codigoEstado
     * @param nifcif
     * @param unidadVersion
     * @return
     * @throws Exception
     */
    Paginacion busqueda(Integer pageNumber, String codigo, String denominacion, Long codigoNivelAdministracion,
                        String codAmbitoTerritorial, Long codComunidad, Long codigoProvincia, Boolean unidadRaiz, String codigoEstado, 
                        String nifcif, Long unidadVersion, Boolean denominacionCooficial) throws Exception;

    /**
     * Método recursivo que devuelve el árbol de unidades de la unidad indicada por código,
     * teniendo en cuenta la fecha de la ultima actualización de regweb para el caso en que la unidad indicada no es raiz.
     * Se emplea para la sincronizacion y actualización con regweb
     *
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @return
     * @throws Exception
     */
    List<Unidad> obtenerArbolUnidadesUnidadNoRaiz(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;


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
    List<Unidad> obtenerArbolUnidadesUnidadRaiz(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

    /**
     * Método que devuelve la unidad indicada por código siempre que esté vigente y tenga oficinas donde registrar.
     * A partir de ella se obtienen todos sus hijos vigentes.
     * solicitado por SISTRA
     *
     * @param codigo código de la unidad raiz de la que partimos.
     * @return
     * @throws Exception
     */
    List<Unidad> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception;

    List<Oficina> obtenerArbolOficinasOpenData(String codigo) throws Exception;

    /**
     * Metodo que comprueba si una unidad tiene más unidades hijas
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    Boolean tieneHijos(String codigo) throws Exception;

    /**
     * Método que obtiene los hijos de primer nivel de una unidad en función del estado de la unidad padre
     *
     * @param codigo identificador de la unidad padre.
     * @param estado estado de la unidad padre.
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    List<Nodo> hijos(String codigo, String estado) throws Exception;

    /**
     * Método que obtiene los hijos vigentes de primer nivel de una unidad
     *
     * @param codigo identificador de la unidad padre.
     * @param codigo del estado si queremos filtrar por estado
     * @return {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    List<Unidad> hijosPrimerNivel(String codigo, String estado) throws Exception;

    /**
     * Metodo que obtiene los hijos de una unidad en función del estado de la unidad padre
     *
     * @param unidadesPadres unidadesPadres de las que obtener hijos
     * @param estado         indica el estado de los hijos
     * @param hijosTotales   lista con todos los hijos encontrados de manera recursiva.
     * @return {@link es.caib.dir3caib.persistence.model.Unidad}
     */
    void arbolHijos(Set<Unidad> unidadesPadres, String estado, Set<Unidad> hijosTotales) throws Exception;

    /**
     * Obtiene una unidad por su denominación
     *
     * @param denominacion
     * @return
     * @throws Exception
     */
    List<Unidad> findByDenominacion(String denominacion) throws Exception;

    /**
     * Obtiene el arbol de una Unidad, pero solo los códigos
     * Se emplea en el método de obtenerArbolOficinas.
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    List<Unidad> obtenerArbol(String codigo) throws Exception;
    
    List<Unidad> obtenerArbol(String codigo, String estado) throws Exception;

    /**
     * Obtiene una lista de unidades páginada
     *
     * @param startItem
     * @param numberOfItems
     * @return
     * @throws Exception
     */
    List<Unidad> getPagination(int startItem, int numberOfItems) throws Exception;

    /**
     * Obtiene la Denominacion de una Unidad
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    String unidadDenominacion(String codigo) throws Exception;

    /**
     * Obtiene todos los códigos de las unidades. Se emplea para la importación de las unidades desde Madrid.
     *
     * @return
     */
    List<String> getAllCodigos();




    /**
     * Devuelve todas las unidades de la lista de ids indicados. Se emplea para montar la cache de unidades
     * en la importación de oficinas desde Madrid
     *
     * @param ids
     * @return
     * @throws Exception
     */
     List<Unidad> getListByIds(List<String> ids) throws Exception;


    /**
     * Obtiene el codigo, la denominación, el estado, la tupla codigo-denominacion de la unidad raiz y la tupla codigo-denominacion de la unidad.
     * Se emplea para mostrar el arbol de unidades.
     *
     * @param id     identificador de la unidad
     * @param estado estado de la unidad
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    Nodo findUnidad(String id, String estado) throws Exception;

    /**
     * OObtiene el código, denominación y estado de la unidad indicada
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    Unidad findByCodigoLigero(String codigo) throws Exception;
    


    /**
     * Obtiene el código de todas las Unidades hijas de la unidad raiz indicada por código
     * y que tienen una oficina que cuelga de ellas
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    List<Unidad> obtenerUnidadesConOficina(String codigo) throws Exception;

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
    boolean unidadValida(Unidad unidad, Date fechaSincro) throws Exception;

    /**
     *
     * @param codigoUnidad
     * @throws Exception
     */
    void crearUnidad(String codigoUnidad) throws Exception;

    /**
     *
     * @param codigoUnidad
     * @param codigoUnidadRaiz
     * @param codigoUnidadSuperior
     * @throws Exception
     */
    void actualizarUnidad(String codigoUnidad, String codigoUnidadRaiz, String codigoUnidadSuperior) throws Exception;

    /**
     * @param codigoAnterior
     * @param codigoUltima
     * @throws Exception
     */
    void crearHistoricoUnidad(String codigoAnterior, String codigoUltima) throws Exception;

    /**
     * @param idUnidad
     * @throws Exception
     */
    void eliminarHistoricosUnidad(String idUnidad) throws Exception;

    /**
     * Comprueba la existencia de un HistoriooUnidad en concreto
     * @param codigoAnterior
     * @param codigoUltima
     * @return
     * @throws Exception
     */
    Boolean existeHistoricoUnidad(String codigoAnterior, String codigoUltima) throws Exception;

   /**
    * Obtiene los históricos hacia atrás de una unidad.
    *
    * @param codigoUnidad
    * @return
    * @throws Exception
    */
   Set<Unidad> historicosHaciaAtras(String codigoUnidad) throws Exception;
   
   Set<Unidad> historicosHaciaAtras(String codigoUnidad, boolean denominacionCooficial) throws Exception;

   /**
    * Obtiene los sustitutos de una unidad
    *
    * @param unidad
    * @param historicosFinales
    * @throws Exception
    */
    void historicosFinales(Unidad unidad, Set<Unidad> historicosFinales) throws Exception;

    void historicosUOFinales(Unidad unidad, Set<HistoricoUO> historicosFinales) throws Exception;

    /* Obtiene las unidades del nivel indicado cuya unidad Raiz es la indicada por codigo */
    public List<Unidad> getUnidadesByNivel(long nivel, String codigo, String estado) throws Exception;

    /*
         Obtiene las unidades del nivel indicado cuya unidad superior es la indicada por codigo
     */
    public List<Unidad> getUnidadesByNivelByUnidadSuperior(long nivel, String codigo, String estado) throws Exception;

    /**
     * Obtiene una unidad con sus contactos y sus relaciones
     * @param id
     * @return
     * @throws Exception
     */
    Unidad findFullById(String id) throws Exception;

    /**
     * Obtiene una unidad con sus contactos y sus relaciones
     * @param codigo
     * @return
     * @throws Exception
     */
    Unidad findFullByIdConHistoricos(String codigo) throws Exception;

    Unidad findFullByCodigoDir3ConHistoricos(String codigo) throws Exception;

   /**
    * Este método mira si la unidad del código especificado tiene oficinas donde registrar.
    * Para ello comprueba si es unidadResponsable de alguna oficina y después mira si tiene relacionesOrganizativas con oficinas.
    * Es además recursivo, así que lo mira hasta el último nivel del organigrama.
    *
    * @param codigo de la unidad
    * @return
    * @throws Exception
    */
   Boolean tieneOficinasArbol(String codigo) throws Exception;

    /**
     *
     * @param codigoUnidad
     * @param codigoServicio
     * @throws Exception
     */
    void crearServicioUnidad(String codigoUnidad, Long codigoServicio) throws Exception;

	List<Nodo> hijos(String codigo, String estado, boolean denominacionCooficial, boolean codigoDir3) throws Exception;

	Unidad findByCodigoLigero(String codigo, boolean denominacionCooficial) throws Exception;

	String unidadDenominacion(String codigo, boolean denominacionCooficial) throws Exception;

	List<Unidad> findByDenominacion(String denominacion, boolean denominacionCooficial) throws Exception;

	Nodo findUnidad(String id, String estado, boolean denominacionCooficial) throws Exception;
	
	Nodo findUnidadByCodigoDir3(String codigoDir3, String estado, boolean denominacionCooficial) throws Exception;

	void arbolHijos(Set<Unidad> unidadesPadres, String estado, Set<Unidad> hijosTotales, boolean denominacionCooficial)
			throws Exception;

	List<Unidad> obtenerArbolUnidadesUnidadRaiz(String codigo, Date fechaActualizacion, Date fechaSincronizacion,
			boolean denominacionCooficial) throws Exception;

	List<Unidad> obtenerArbolUnidadesDestinatarias(String codigo, boolean denominacionCooficial) throws Exception;
	
	public List<Unidad> getUnidadesByNivelByUnidadSuperior(long nivel, String codigo, String estado, boolean denominacionCooficial) throws Exception;

}
