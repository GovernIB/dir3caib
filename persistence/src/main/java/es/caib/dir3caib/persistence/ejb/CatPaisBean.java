package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatPais;
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
@Stateless(name = "CatPaisEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class CatPaisBean extends BaseEjbJPA<CatPais, Long> implements CatPaisLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatPais getReference(Long id) throws Exception {

        return em.getReference(CatPais.class, id);
    }

    @Override
    public CatPais findById(Long id) throws Exception {

        return em.find(CatPais.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatPais> getAll() throws Exception {

        return  em.createQuery("Select catPais from CatPais as catPais order by catPais.codigoPais").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catPais.codigoPais) from CatPais as catPais");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatPais> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catPais from CatPais as catPais order by catPais.codigoPais");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatPais").executeUpdate();
        
    }
}
