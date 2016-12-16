package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Servicio;
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
@Stateless(name = "ServicioEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class ServicioBean extends BaseEjbJPA<Servicio, Long> implements ServicioLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Servicio getReference(Long id) throws Exception {

        return em.getReference(Servicio.class, id);
    }

    @Override
    public Servicio findById(Long id) throws Exception {

        return em.find(Servicio.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Servicio> getAll() throws Exception {

        return  em.createQuery("Select servicio from Servicio as servicio order by servicio.codServicio").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(servicio.codServicio) from Servicio as servicio");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<Servicio> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select servicio from Servicio as servicio order by servicio.codServicio");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from Servicio").executeUpdate();
        
    }
}
