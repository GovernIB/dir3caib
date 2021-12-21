package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CodigoUnidadOrganica;
import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;
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
@Stateless(name = "CodigoUOEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN", "tothom", "DIR_WS"})
public class CodigoUOBean extends BaseEjbJPA<CodigoUnidadOrganica, Long> implements CodigoUOLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CodigoUnidadOrganica getReference(Long id) throws Exception {

        return em.getReference(CodigoUnidadOrganica.class, id);
    }

    @Override
    public CodigoUnidadOrganica findById(Long id) throws Exception {

        return em.find(CodigoUnidadOrganica.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CodigoUnidadOrganica> getAll() throws Exception {

        return  em.createQuery("Select codigoUO from CodigoUnidadOrganica as codigoUO order by codigoUO.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(codigoUO.id) from CodigoUnidadOrganica as codigoUO");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CodigoUnidadOrganica> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select codigoUO from CodigoUnidadOrganica as codigoUO order by codigoUO.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
}
