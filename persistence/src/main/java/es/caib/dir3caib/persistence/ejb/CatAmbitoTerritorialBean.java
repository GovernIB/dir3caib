package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatAmbitoTerritorial;
import es.caib.dir3caib.persistence.model.CatAmbitoTerritorialPK;
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
@Stateless(name = "CatAmbitoTerritorialEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class CatAmbitoTerritorialBean extends BaseEjbJPA<CatAmbitoTerritorial, CatAmbitoTerritorialPK> implements CatAmbitoTerritorialLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatAmbitoTerritorial findById(CatAmbitoTerritorialPK id) throws Exception {

        return em.find(CatAmbitoTerritorial.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatAmbitoTerritorial> getAll() throws Exception {

        return  em.createQuery("Select catAmbitoTerritorial from CatAmbitoTerritorial as catAmbitoTerritorial order by catAmbitoTerritorial.codigoAmbito").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catAmbitoTerritorial.id) from CatAmbitoTerritorial as catAmbitoTerritorial");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<CatAmbitoTerritorial> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catAmbitoTerritorial from CatAmbitoTerritorial as catAmbitoTerritorial order by catAmbitoTerritorial.codigoAmbito");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    @Override
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatAmbitoTerritorial").executeUpdate();
        
    }

    public List<CatAmbitoTerritorial> getByAdministracion(Long nivelAdministracion) throws Exception {

        Query q = em.createQuery("Select catAmbitoTerritorial from CatAmbitoTerritorial as catAmbitoTerritorial " +
                "where catAmbitoTerritorial.nivelAdministracion.codigoNivelAdministracion = :nivelAdministracion order by catAmbitoTerritorial.codigoAmbito");

        q.setParameter("nivelAdministracion", nivelAdministracion);

        return q.getResultList();
    }
}
