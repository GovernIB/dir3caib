package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatMotivoExtincion;
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
@Stateless(name = "CatMotivoExtincionEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class CatMotivoExtincionBean extends BaseEjbJPA<CatMotivoExtincion, String> implements CatMotivoExtincionLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatMotivoExtincion getReference(String id) throws Exception {

        return em.getReference(CatMotivoExtincion.class, id);
    }

    @Override
    public CatMotivoExtincion findById(String id) throws Exception {

        return em.find(CatMotivoExtincion.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatMotivoExtincion> getAll() throws Exception {

        return  em.createQuery("Select catMotivoExtincion from CatMotivoExtincion as catMotivoExtincion order by catMotivoExtincion.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catMotivoExtincion.id) from CatMotivoExtincion as catMotivoExtincion");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<CatMotivoExtincion> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catMotivoExtincion from CatMotivoExtincion as catMotivoExtincion order by catMotivoExtincion.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatMotivoExtincion").executeUpdate();
        
    }
}
