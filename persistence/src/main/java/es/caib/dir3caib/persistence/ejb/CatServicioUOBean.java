package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatServicioUO;
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
@Stateless(name = "ServicioUOEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN", "DIR_WS"})
public class CatServicioUOBean extends BaseEjbJPA<CatServicioUO, Long> implements CatServicioUOLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatServicioUO getReference(Long id) throws Exception {

        return em.getReference(CatServicioUO.class, id);
    }

    @Override
    public CatServicioUO findById(Long id) throws Exception {

        return em.find(CatServicioUO.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatServicioUO> getAll() throws Exception {

        return  em.createQuery("Select servicio from ServicioUO as servicio order by servicio.codServicio").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(servicio.codServicio) from ServicioUO as servicio");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatServicioUO> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select servicio from ServicioUO as servicio order by servicio.codServicio");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from ServicioUO").executeUpdate();
        
    }
}
