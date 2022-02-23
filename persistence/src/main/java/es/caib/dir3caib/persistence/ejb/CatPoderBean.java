package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatPoder;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;

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
 * @author jagarcia
 * Date: 02/12/21
 */
@Stateless(name = "CatPoderEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.DIR_WS})
public class CatPoderBean extends BaseEjbJPA<CatPoder, Long> implements CatPoderLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatPoder getReference(Long id) throws Exception {

        return em.getReference(CatPoder.class, id);
    }

    @Override
    public CatPoder findById(Long id) throws Exception {

        return em.find(CatPoder.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatPoder> getAll() throws Exception {

        return  em.createQuery("Select catPoder from CatPoder as catPoder order by catPoder.codigoPoder").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catPoder.codigoPoder) from CatPoder as catPoder");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatPoder> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catPoder from CatPoder as catPoder order by catPoder.codigoPoder");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatPoder").executeUpdate();
        
    }
}
