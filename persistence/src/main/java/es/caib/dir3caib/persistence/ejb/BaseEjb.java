package es.caib.dir3caib.persistence.ejb;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Fundacio Bit
 * @author earrivi
 * Date: 26/02/13
 */
public interface BaseEjb<T extends Serializable, E> {

  /**
   * Crear registro
   *
   * @param transientInstance
   * @throws Exception
   */
  public T persistReal(T transientInstance) throws Exception;
  
    /**
     * Crear registro
     *
     * @param transientInstance
     * @throws Exception
     */
    public T persist(T transientInstance) throws Exception;

    /**
     *  Actualizar registro
     * @param instance
     * @return
     * @throws Exception
     */
    public T merge(T instance) throws Exception;

    /**
     *  Eliminar registro
     * @param persistentInstance
     * @throws Exception
     */
    public void remove(T persistentInstance) throws Exception;

    /**
     *  Obtener registro por id
     * @param id
     * @return
     * @throws Exception
     */
    public T findById(E id) throws Exception;

  /**
   *  Obtener la referencia al registro por id
   * @param id
   * @return
   * @throws Exception
   */
  public T getReference(E id) throws Exception;

    /**
     *  Obtener todos los registros
     * @return
     * @throws Exception
     */
    public List<T> getAll() throws Exception;

    /**
     * Obtiene el total de registros para la paginacion
     * @return
     * @throws Exception
     */
    public Long getTotal() throws Exception;

    /**
     * Obtiene X valores comenzando en la posicion pasada por parametro
     * @param inicio
     * @return
     * @throws Exception
     */
    public List<T> getPagination(int inicio) throws Exception;

    public void flush() throws Exception;

    public void clear() throws Exception;
}
