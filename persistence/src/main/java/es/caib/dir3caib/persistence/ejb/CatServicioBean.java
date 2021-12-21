package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatServicio;
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
@Stateless(name = "CatServicioEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN", "DIR_WS"})
public class CatServicioBean extends BaseEjbJPA<CatServicio, Long> implements CatServicioLocal {

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatServicio getReference(Long id) throws Exception {

        return em.getReference(CatServicio.class, id);
    }

    @Override
    public CatServicio findById(Long id) throws Exception {

        return em.find(CatServicio.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatServicio> getAll() throws Exception {

        return  em.createQuery("Select servicio from Servicio as servicio order by servicio.codServicio").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(servicio.codServicio) from Servicio as servicio");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatServicio> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select servicio from Servicio as servicio order by servicio.codServicio");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from Servicio").executeUpdate();
        
    }
}
