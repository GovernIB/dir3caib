package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.HistoricoUO;
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
 * 16/12/2021
 */
@Stateless(name = "ServicioUOEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.ROL_TOTHOM, Dir3caibConstantes.DIR_WS})
public class ServicioUOBean extends BaseEjbJPA<ServicioUO, Long> implements ServicioUOLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public ServicioUO findById(Long id) throws Exception {
        return em.find(ServicioUO.class,id);
    }

    @Override
    public ServicioUO getReference(Long id) throws Exception {
        return em.getReference(ServicioUO.class,id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ServicioUO> getAll() throws Exception {
        return  em.createQuery("Select servicioUO from ServicioUO as servicioUO ").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long getTotal() throws Exception {
        Query q = em.createQuery("Select count(servicioUO.id) from ServicioUO as servicioUO");

        return (Long) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ServicioUO> getPagination(int inicio) throws Exception {
        Query q = em.createQuery("Select servicioUO from ServicioUO as servicioUO ");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
}
