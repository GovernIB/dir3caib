package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ServicioOfi;
import es.caib.dir3caib.persistence.model.ServicioUO;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author mgonzalez
 * 17/12/2021
 */
@Stateless(name = "ServicioOfiEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN", "tothom", "DIR_WS"})
public class ServicioOfiBean extends BaseEjbJPA<ServicioOfi, Long> implements ServicioOfiLocal{
    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public ServicioOfi findById(Long id) throws Exception {
        return em.find(ServicioOfi.class,id);
    }

    @Override
    public ServicioOfi getReference(Long id) throws Exception {
        return em.getReference(ServicioOfi.class,id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ServicioOfi> getAll() throws Exception {
        return  em.createQuery("Select servicioOfi from ServicioOfi as servicioOfi ").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long getTotal() throws Exception {
        Query q = em.createQuery("Select count(servicioOfi.id) from ServicioOfi as servicioOfi");

        return (Long) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ServicioOfi> getPagination(int inicio) throws Exception {
        Query q = em.createQuery("Select servicioOfi from ServicioOfi as servicioOfi ");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
}
