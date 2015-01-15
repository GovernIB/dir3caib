package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatLocalidad;
import es.caib.dir3caib.persistence.model.CatLocalidadPK;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import javax.annotation.security.RolesAllowed;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Stateless(name = "CatLocalidadEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class CatLocalidadBean extends BaseEjbJPA<CatLocalidad, CatLocalidadPK> implements CatLocalidadLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatLocalidad findById(CatLocalidadPK id) throws Exception {

        return em.find(CatLocalidad.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatLocalidad> getAll() throws Exception {

        return  em.createQuery("Select catLocalidad from CatLocalidad as catLocalidad order by catLocalidad.codigoLocalidad").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catLocalidad.codigoLocalidad) from CatLocalidad as catLocalidad");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<CatLocalidad> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catLocalidad from CatLocalidad as catLocalidad order by catLocalidad.codigoLocalidad");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatLocalidad").executeUpdate();
        
    }
}
