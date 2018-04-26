package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatProvincia;
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
@Stateless(name = "CatProvinciaEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN","tothom"})
public class CatProvinciaBean extends BaseEjbJPA<CatProvincia, Long> implements CatProvinciaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatProvincia getReference(Long id) throws Exception {

        return em.getReference(CatProvincia.class, id);
    }

    @Override
    public CatProvincia findById(Long id) throws Exception {

        return em.find(CatProvincia.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatProvincia> getAll() throws Exception {

        return  em.createQuery("Select catProvincia from CatProvincia as catProvincia order by catProvincia.codigoProvincia").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catProvincia.codigoProvincia) from CatProvincia as catProvincia");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatProvincia> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catProvincia from CatProvincia as catProvincia order by catProvincia.codigoProvincia");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    @Override
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatProvincia").executeUpdate();
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CatProvincia> getByComunidadAutonoma(Long idComunidadAutonoma) throws Exception {

        Query q = em.createQuery("Select catProvincia from CatProvincia as catProvincia " +
                "where catProvincia.comunidadAutonoma.codigoComunidad =:idComunidadAutonoma order by catProvincia.codigoProvincia");

        q.setParameter("idComunidadAutonoma", idComunidadAutonoma);

        return q.getResultList();
    }
}
