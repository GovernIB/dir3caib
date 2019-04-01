package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoVia;
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
@Stateless(name = "CatTipoViaEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN", "DIR_WS"})
public class CatTipoViaBean extends BaseEjbJPA<CatTipoVia, Long> implements CatTipoViaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatTipoVia getReference(Long id) throws Exception {

        return em.getReference(CatTipoVia.class, id);
    }

    @Override
    public CatTipoVia findById(Long id) throws Exception {

        return em.find(CatTipoVia.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatTipoVia> getAll() throws Exception {

        return  em.createQuery("Select catTipoVia from CatTipoVia as catTipoVia order by catTipoVia.codigoTipoVia").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catTipoVia.codigoTipoVia) from CatTipoVia as catTipoVia");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatTipoVia> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catTipoVia from CatTipoVia as catTipoVia order by catTipoVia.codigoTipoVia");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatTipoVia").executeUpdate();
        
    }
}
