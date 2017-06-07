package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatLocalidad;
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
@Stateless(name = "CatLocalidadEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class CatLocalidadBean extends BaseEjbJPA<CatLocalidad, Long> implements CatLocalidadLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatLocalidad getReference(Long id) throws Exception {

        return em.getReference(CatLocalidad.class, id);
    }

    @Override
    public CatLocalidad findById(Long id) throws Exception {

        return em.find(CatLocalidad.class, id);
    }
    
    
    
    public CatLocalidad findByPKs(Long codigoLocalidad, Long codigoProvincia,
         String codigoEntidadGeografica) throws Exception {
    
      
      Query q = em.createQuery("Select catLocalidad from CatLocalidad as catLocalidad "
          + "WHERE catLocalidad.codigoLocalidad = :codigoLocalidad "
          + " AND provincia.codigoProvincia = :codigoProvincia"
          + " AND entidadGeografica.codigoEntidadGeografica = :codigoEntidadGeografica ");
      
      q.setParameter("codigoLocalidad", codigoLocalidad);
      q.setParameter("codigoProvincia", codigoProvincia);
      q.setParameter("codigoEntidadGeografica", codigoEntidadGeografica);
      
      try {
        return (CatLocalidad)q.getSingleResult();
      } catch(Throwable e) {
        return null;
      }
       
    }
    
    
    

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatLocalidad> getAll() throws Exception {

        return  em.createQuery("Select catLocalidad from CatLocalidad as catLocalidad order by catLocalidad.codigoLocalidad").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catLocalidad.codigoLocalidad) from CatLocalidad as catLocalidad");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<CatLocalidad> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catLocalidad from CatLocalidad as catLocalidad order by catLocalidad.codigoLocalidad");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatLocalidad").executeUpdate();
        
    }


}
