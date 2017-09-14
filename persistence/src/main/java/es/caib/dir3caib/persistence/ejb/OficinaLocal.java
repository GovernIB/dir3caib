/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.Paginacion;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author mgonzalez
 * @author anadal
 */
@Local
public interface OficinaLocal extends BaseEjb<Oficina, String> {

    /**
     * Elimina todas las oficinas
     *
     * @throws Exception
     */
    public void deleteAll() throws Exception;

    /**
     * Realiza la búsqueda de oficinas en función de los criterios especificados
     * @param pageNumber numero de pagina, para la paginación
     * @param codigo código de la oficina
     * @param denominacion denominacion de la oficina
     * @param codigoNivelAdministracion codigo del nivel de administración
     * @param codComunidad codigo de la comunidad  a la que pertenece.
     * @param codigoProvincia codigo de la provincia a la que pertenece.
     * @param codigoEstado codigo de estado (vigente, anulado)
     * @return
     * @throws Exception
     */
    public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion,
                               Long codigoNivelAdministracion, Long codComunidad, Long codigoProvincia, String codigoEstado)
            throws Exception;

    /**
     * Elimina todos los registros de la tabla de históricos de oficinas
     * @throws Exception
     */
    public void deleteHistoricosOficina() throws Exception;

    /**
     * Elimina todos los servicios de oficinas de la tabla de servicios.
     * @throws Exception
     */
    public void deleteServiciosOficina() throws Exception;

    /**
     * Obtiene una oficina con sus historicos y sus servicios
     * @param id
     * @return
     * @throws Exception
     */
    public Oficina findFullById(String id) throws Exception;

    /**
     * Obtiene el código, denominación y estado de la oficina indicada
     * @param codigo
     * @return
     * @throws Exception
     */
    public Oficina findByCodigoLigero(String codigo) throws Exception;

    /**
     * Obtiene una Oficina según su código y estado
     * @param codigo
     * @param estado
     * @return
     * @throws Exception
     */
    public Oficina findById(String codigo, String estado) throws Exception;


    /**
     * Obtiene el codigo, la denominación, el estado, la tupla codigo-denominacion de la unidad raiz y la tupla codigo-denominacion de la unidad responsable
     * de la oficina. Se emplea para pintar el árbol de oficinas
     * @param id
     * @param estado
     * @return
     * @throws Exception
     */
    public Nodo findOficina(String id, String estado) throws Exception;

    /*
       Método que comprueba si una oficina tiene más oficinas hijas
    */
    public Boolean tieneHijos(String codigo) throws Exception;

    /**
     * Metodo que obtiene los hijos de primer nivel de una oficina en funcion del estado del padre.
     *
     * @param codigo identificador de la oficina padre.
     * @param estado estado de la oficina padre.
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    public List<Nodo> hijos(String codigo, String estado) throws Exception;


    /**
     * EL QUE SE EMPLEA EN LA SINCRO CON REGWEB (EL BUENO)
     * Método que devuelve las oficinas de un organismo(son todas, padres e hijos),
     * teniendo en cuenta la fecha de la ultima actualización de regweb.
     * Se emplea para la sincronizacion y actualización con regweb
     * @param codigo código de la unidad
     * @param fechaActualizacion   fecha de la ultima actualización
     * @param fechaSincronizacion  fecha de la primera sincronización
     * @return
     * @throws Exception
     */
    public List<Oficina> obtenerOficinasOrganismo(String codigo, Date fechaActualizacion,
                                                  Date fechaSincronizacion) throws Exception;

    /**
     * Obtiene el listado de oficinas Sir de una Unidad
     * para ello consulta la relacionSirOfi y además que tengan los servicios SIR y SIR_RECEPCION y que sean vigentes.
     *
     * @param codigo
     *          Código de la unidad
     *
     */
    public List<Oficina> obtenerOficinasSIRUnidad(String codigo) throws Exception;

    /**
     * Este método mira si la unidad del código especificado tiene oficinas donde registrar.
     * Para ello comprueba si es unidadResponsable de alguna oficina y después mira si tiene relacionesOrganizativas con oficinas.
     * Es además recursivo, así que lo mira hasta el último nivel del organigrama.
     * @param codigo de la unidad
     * @return
     * @throws Exception
     */
    public Boolean tieneOficinasArbol(String codigo) throws Exception;

    /**
     * Obtiene todos los código de las oficinas. Se emplea en la importación de oficinas desde Madrid.
     * @return
     * @throws Exception
     */
    public List<String> getAllCodigos() throws Exception;

    /**
     * Obtiene las oficinas que dependen directamente de la unidad, es decir cuya unidad responsable es la del codigo
     * indicado y del estado indicado por estado. Se emplea para pintar el árbol de unidades con sus oficinas
     *
     * @param codigo codigo de la unidad
     * @param estado estado de las oficinas
     *
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    public List<Nodo> oficinasDependientes(String codigo, String estado) throws Exception;

    /**
     * Obtiene las oficinas auxiliares de un Oficina padre, es decir aquellas que dependen de la oficina del código
     * especificado y del estado especificado.Se emplea para pintar el árbol de unidades con sus oficinas
     *
     * @param codigo código de la oficina
     * @param estado estado de la oficina
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    public List<Nodo> oficinasAuxiliares(String codigo, String estado) throws Exception;

    /**
     *
     * @param codigoOficina
     * @param codigoServicio
     * @throws Exception
     */
    public void crearServicioOficina(String codigoOficina, Long codigoServicio) throws Exception;

    /**
     *
     * @param codigoAnterior
     * @param codigoUltima
     * @throws Exception
     */
    public void crearHistoricoOficina(String codigoAnterior, String codigoUltima) throws Exception;

    /**
     * Comprueba la existencia de un ServicioOficina en concreto
     * @param codigoOficina
     * @param codigoServicio
     * @return
     * @throws Exception
     */
    public Boolean existeServicioOficina(String codigoOficina, Long codigoServicio) throws Exception;

    /**
     * Comprueba la existencia de un HistoricoOficina en concreto
     * @param codigoAnterior
     * @param codigoUltima
     * @return
     * @throws Exception
     */
    public Boolean existeHistoricoOficina(String codigoAnterior, String codigoUltima) throws Exception;
}
