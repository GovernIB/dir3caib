package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatAmbitoTerritorial;
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
 * @author anadal (Eliminar PKs multiples)
 * Date: 10/10/13
 */
@Stateless(name = "CatAmbitoTerritorialEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN","tothom"})
public class CatAmbitoTerritorialBean extends BaseEjbJPA<CatAmbitoTerritorial, Long> implements CatAmbitoTerritorialLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatAmbitoTerritorial getReference(Long id) throws Exception {

        return em.getReference(CatAmbitoTerritorial.class, id);
    }

    @Override
    public CatAmbitoTerritorial findById(Long id) throws Exception {

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
    @SuppressWarnings("unchecked")
    public List<CatAmbitoTerritorial> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catAmbitoTerritorial from CatAmbitoTerritorial as catAmbitoTerritorial order by catAmbitoTerritorial.codigoAmbito");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatAmbitoTerritorial").executeUpdate();
        
    }

    @Override
    public CatAmbitoTerritorial findByPKs(String codigoAmbito, Long codigoNivelAdministracion) throws Exception {
      Query q = em.createQuery("Select catAmbitoTerritorial from CatAmbitoTerritorial as catAmbitoTerritorial "
          + " where catAmbitoTerritorial.nivelAdministracion.codigoNivelAdministracion = :nivelAdministracion "
          + " AND catAmbitoTerritorial.codigoAmbito = :codigoAmbito "
          );

      q.setParameter("nivelAdministracion", codigoNivelAdministracion);
      q.setParameter("codigoAmbito", codigoAmbito);

      try {
        return (CatAmbitoTerritorial) q.getSingleResult();
      } catch(Throwable th) {
        return null;
      }
    }
    
}
