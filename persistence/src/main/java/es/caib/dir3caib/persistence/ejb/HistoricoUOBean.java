package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.HistoricoUO;
import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi;
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
 * 14/12/2021
 */

@Stateless(name = "HistoricoUOEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN", "tothom", "DIR_WS"})
public class HistoricoUOBean extends BaseEjbJPA<HistoricoUO, Long> implements HistoricoUOLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;


    @Override
    public HistoricoUO findById(Long id) throws Exception {
        return em.find(HistoricoUO.class,id);
    }

    @Override
    public HistoricoUO getReference(Long id) throws Exception {
        return em.getReference(HistoricoUO.class,id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistoricoUO> getAll() throws Exception {
         return  em.createQuery("Select historicoUO from HistoricoUO as historicoUO ").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long getTotal() throws Exception {
        Query q = em.createQuery("Select count(historicoUO.id) from HistoricoUO as historicoUO");

        return (Long) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistoricoUO> getPagination(int inicio) throws Exception {
        Query q = em.createQuery("Select historicoUO from HistoricoUO as historicoUO ");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
}
