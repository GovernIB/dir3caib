package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
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
@Stateless(name = "CatComunidadAutonomaEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN", "tothom", "DIR_WS"})
public class CatComunidadAutonomaBean extends BaseEjbJPA<CatComunidadAutonoma, Long> implements CatComunidadAutonomaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatComunidadAutonoma getReference(Long id) throws Exception {

        return em.getReference(CatComunidadAutonoma.class, id);
    }

    @Override
    public CatComunidadAutonoma findById(Long id) throws Exception {

        return em.find(CatComunidadAutonoma.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatComunidadAutonoma> getAll() throws Exception {

        return em.createQuery("Select catComunidadAutonoma from CatComunidadAutonoma as catComunidadAutonoma order by catComunidadAutonoma.descripcionComunidad").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catComunidadAutonoma.codigoComunidad) from CatComunidadAutonoma as catComunidadAutonoma");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatComunidadAutonoma> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catComunidadAutonoma from CatComunidadAutonoma as catComunidadAutonoma order by catComunidadAutonoma.codigoComunidad");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatComunidadAutonoma").executeUpdate();
        
    }
}
