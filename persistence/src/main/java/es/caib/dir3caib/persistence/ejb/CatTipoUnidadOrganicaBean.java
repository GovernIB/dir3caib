package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoUnidadOrganica;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import javax.annotation.security.RolesAllowed;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Stateless(name = "CatTipoUnidadOrganicaEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class CatTipoUnidadOrganicaBean extends BaseEjbJPA<CatTipoUnidadOrganica, String> implements CatTipoUnidadOrganicaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatTipoUnidadOrganica findById(String id) throws Exception {

        return em.find(CatTipoUnidadOrganica.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatTipoUnidadOrganica> getAll() throws Exception {

        return  em.createQuery("Select catTipoUnidadOrganica from CatTipoUnidadOrganica as catTipoUnidadOrganica order by catTipoUnidadOrganica.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catTipoUnidadOrganica.id) from CatTipoUnidadOrganica as catTipoUnidadOrganica");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<CatTipoUnidadOrganica> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catTipoUnidadOrganica from CatTipoUnidadOrganica as catTipoUnidadOrganica order by catTipoUnidadOrganica.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatTipoUnidadOrganica").executeUpdate();
        
    }
}
