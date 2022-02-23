package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.HistoricoOfi;
import es.caib.dir3caib.persistence.model.HistoricoUO;
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
@Stateless(name = "HistoricoOfiEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.ROL_TOTHOM, Dir3caibConstantes.DIR_WS})
public class HistoricoOfiBean  extends BaseEjbJPA<HistoricoOfi, Long> implements HistoricoOfiLocal {
    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;


    @Override
    public HistoricoOfi findById(Long id) throws Exception {
        return em.find(HistoricoOfi.class,id);
    }

    @Override
    public HistoricoOfi getReference(Long id) throws Exception {
        return em.getReference(HistoricoOfi.class,id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistoricoOfi> getAll() throws Exception {
        return  em.createQuery("Select historicoOfi from HistoricoOfi as historicoOfi ").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long getTotal() throws Exception {
        Query q = em.createQuery("Select count(historicoOfi.id) from HistoricoOfi as historicoOfi");

        return (Long) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistoricoOfi> getPagination(int inicio) throws Exception {
        Query q = em.createQuery("Select historicoOfi from HistoricoOfi as historicoOfi ");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
}
