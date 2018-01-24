package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoContacto;
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
@Stateless(name = "CatTipoContactoEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class CatTipoContactoBean extends BaseEjbJPA<CatTipoContacto, String> implements CatTipoContactoLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatTipoContacto getReference(String id) throws Exception {

        return em.getReference(CatTipoContacto.class, id);
    }

    @Override
    public CatTipoContacto findById(String id) throws Exception {

        return em.find(CatTipoContacto.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatTipoContacto> getAll() throws Exception {

        return  em.createQuery("Select catTipoContacto from CatTipoContacto as catTipoContacto order by catTipoContacto.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catTipoContacto.id) from CatTipoContacto as catTipoContacto");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatTipoContacto> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catTipoContacto from CatTipoContacto as catTipoContacto order by catTipoContacto.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatTipoContacto").executeUpdate();
        
    }
}
