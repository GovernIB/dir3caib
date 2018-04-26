package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatIsla;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Stateless(name = "CatIslaEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class CatIslaBean extends BaseEjbJPA<CatIsla, Long> implements CatIslaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatIsla getReference(Long id) throws Exception {

        return em.getReference(CatIsla.class, id);
    }

    @Override
    public CatIsla findById(Long id) throws Exception {

        return em.find(CatIsla.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatIsla> getAll() throws Exception {

        return  em.createQuery("Select catIsla from CatIsla as catIsla order by catIsla.codigoIsla").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catIsla.codigoIsla) from CatIsla as catIsla");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatIsla> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catIsla from CatIsla as catIsla order by catIsla.codigoIsla");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    @Override
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatIsla").executeUpdate();
        
    }
}
