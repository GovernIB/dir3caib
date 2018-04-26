package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatNivelAdministracion;
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
@Stateless(name = "CatNivelAdministracionEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN","tothom"})
public class CatNivelAdministracionBean extends BaseEjbJPA<CatNivelAdministracion, Long> implements CatNivelAdministracionLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatNivelAdministracion getReference(Long id) throws Exception {

        return em.getReference(CatNivelAdministracion.class, id);
    }

    @Override
    public CatNivelAdministracion findById(Long id) throws Exception {

        return em.find(CatNivelAdministracion.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatNivelAdministracion> getAll() throws Exception {

        return  em.createQuery("Select catNivelAdministracion from CatNivelAdministracion as catNivelAdministracion order by catNivelAdministracion.codigoNivelAdministracion").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catNivelAdministracion.codigoNivelAdministracion) from CatNivelAdministracion as catNivelAdministracion");

        return (Long) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CatNivelAdministracion> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catNivelAdministracion from CatNivelAdministracion as catNivelAdministracion order by catNivelAdministracion.codigoNivelAdministracion");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatNivelAdministracion").executeUpdate();
        
    }
}
