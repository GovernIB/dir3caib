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
     * Obtiene el codigo, la denominación, el estado, la tupla codigo-denominacion de la unidad raiz y de la unidad responsable
     * de la oficina.
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
     * Obtiene las oficinas SIR de una unidad que están vigentes
     *
     * @param codigo código de la Unidad
     * @return listado de Oficinas
     * @throws Exception
     */
    //TODO falta mirar los servicios de las oficinas para ver si estan integradas en sir de envio o recepción o ambos
    public List<Oficina> obtenerOficinasSIRUnidad(String codigo) throws Exception;

    /**
     * Método que comprueba si el arbol de unidades del código indicado  tiene oficinas donde registrar
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
     * Obtiene las oficinas que dependen directamente de la unidad
     * @param codigo código de la unidad
     * @param estado
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    public List<Nodo> oficinasDependientes(String codigo, String estado) throws Exception;

    /**
     * Obtiene las oficinas auxiliares de un Oficina padre.
     * @param codigo código de la oficina
     * @param estado
     * @return {@link es.caib.dir3caib.persistence.utils.Nodo}
     */
    public List<Nodo> oficinasAuxiliares(String codigo, String estado) throws Exception;
}
