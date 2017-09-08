package es.caib.dir3caib.persistence.ejb;

import org.apache.log4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by Fundacio Bit
 * @author earrivi
 * Date: 26/02/13
 */
public abstract class BaseEjbJPA<T extends Serializable, E> implements BaseEjb<T, E> {

    public final Logger log = Logger.getLogger(this.getClass());
    public static final int RESULTADOS_PAGINACION = 10;

    @PersistenceContext
    private EntityManager em;

    @RolesAllowed("DIR_ADMIN")
    public void remove(T persistentInstance) throws Exception{

        try{
            em.remove(em.merge(persistentInstance));
        }catch (Exception e){
            log.error(e);
            throw e;
        }

    }
    @RolesAllowed("DIR_ADMIN")
    public T persist(T transientInstance) throws Exception{

        try{
            return em.merge(transientInstance);
        }catch (Exception e){
            log.error(e);
            throw e;
        }

    }

    @RolesAllowed("DIR_ADMIN")
    public T merge(T instance) throws Exception{

        try{
            return em.merge(instance);
        }catch (Exception e){
            log.error(e);
            throw e;
        }

    }
    
    @RolesAllowed("DIR_ADMIN")
    public T persistReal(T transientInstance) throws Exception{

        try{
            em.persist(transientInstance);
            return transientInstance;
        }catch (Exception e){
            log.error(e);
            throw e;
        }

    }

    @RolesAllowed("DIR_ADMIN")
    public void flush() throws Exception {
        em.flush();
    }

    @RolesAllowed("DIR_ADMIN")
    public void clear() throws Exception {
        em.clear();
    }
}
