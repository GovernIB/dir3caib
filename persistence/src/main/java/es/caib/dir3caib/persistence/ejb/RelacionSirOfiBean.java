package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.RelacionSirOfi;
import es.caib.dir3caib.persistence.model.RelacionSirOfiPK;
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
 * @author anadal
 * Date: 10/10/13
 */
@Stateless(name = "RelacionSirOfiEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class RelacionSirOfiBean extends BaseEjbJPA<RelacionSirOfi, RelacionSirOfiPK>
   implements RelacionSirOfiLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public RelacionSirOfi findById(RelacionSirOfiPK id) throws Exception {

        return em.find(RelacionSirOfi.class, id);
    }
    
    
    
    @Override
    public RelacionSirOfi findByPKs(String codigoUnidad, String codigoOficina) throws Exception {

       Query query = em.createQuery("select relacionSirOfi from RelacionSirOfi as relacionSirOfi "
          + " where relacionSirOfi.oficina.codigo = :codigoOficina AND " 
          + " relacionSirOfi.unidad.codigo = :codigoUnidad");
       query.setParameter("codigoUnidad", codigoUnidad);
       query.setParameter("codigoOficina", codigoOficina);

       try {
         return (RelacionSirOfi)query.getSingleResult();
       } catch(Throwable th) {
         return null;
       }
    }


    @Override
    public List<String> getUnidadesOficinas() throws Exception  {
      //select concat(c.firstname, ' ', c.lastname) as fullname from Contact c
      
      String str  = "Select concat(relacionSirOfi.unidad.codigo, '_', relacionSirOfi.oficina.codigo) "
        + "from RelacionSirOfi as relacionSirOfi ";
        
      Query query = em.createQuery(str);
      
      return query.getResultList();
      
    }
    

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<RelacionSirOfi> getAll() throws Exception {

        return  em.createQuery("Select relacionSirOfi from RelacionSirOfi as relacionSirOfi ").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(relacionSirOfi.id) from RelacionSirOfi as relacionSirOfi");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<RelacionSirOfi> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select relacionSirOfi from RelacionSirOfi as relacionSirOfi ");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    @Override
    public void deleteAll() throws Exception {

        em.createQuery("delete from RelacionSirOfi").executeUpdate();
        
    }
}
