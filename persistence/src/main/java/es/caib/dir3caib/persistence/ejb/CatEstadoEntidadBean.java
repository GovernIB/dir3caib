package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatEstadoEntidad;
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
@Stateless(name = "CatEstadoEntidadEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN","tothom"})
public class CatEstadoEntidadBean extends BaseEjbJPA<CatEstadoEntidad, String> implements CatEstadoEntidadLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatEstadoEntidad getReference(String id) throws Exception {

        return em.getReference(CatEstadoEntidad.class, id);
    }

    @Override
    public CatEstadoEntidad findById(String id) throws Exception {

        return em.find(CatEstadoEntidad.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatEstadoEntidad> getAll() throws Exception {

        return  em.createQuery("Select catEstadoEntidad from CatEstadoEntidad as catEstadoEntidad order by catEstadoEntidad.codigoEstadoEntidad").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catEstadoEntidad.codigoEstadoEntidad) from CatEstadoEntidad as catEstadoEntidad");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<CatEstadoEntidad> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catEstadoEntidad from CatEstadoEntidad as catEstadoEntidad order by catEstadoEntidad.codigoEstadoEntidad");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatEstadoEntidad").executeUpdate();
        
    }

    @Override
    public CatEstadoEntidad findByCodigo(String codigo) throws Exception {
        Query q = em.createQuery("Select catEstadoEntidad from CatEstadoEntidad as catEstadoEntidad where catEstadoEntidad.codigoEstadoEntidad = :codigo");

        q.setParameter("codigo",codigo);

        List<CatEstadoEntidad> catEstadoEntidad = q.getResultList();
        if(catEstadoEntidad.size() == 1){
            return catEstadoEntidad.get(0);
        }else{
            return  null;
        }

    }
}
