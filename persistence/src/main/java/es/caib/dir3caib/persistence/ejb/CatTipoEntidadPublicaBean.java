package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoEntidadPublica;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;

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
@Stateless(name = "CatTipoEntidadPublicaEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.ROL_TOTHOM, Dir3caibConstantes.DIR_WS})
public class CatTipoEntidadPublicaBean extends BaseEjbJPA<CatTipoEntidadPublica, String> implements CatTipoEntidadPublicaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatTipoEntidadPublica getReference(String id) throws Exception {

        return em.getReference(CatTipoEntidadPublica.class, id);
    }

    @Override
    public CatTipoEntidadPublica findById(String id) throws Exception {

        return em.find(CatTipoEntidadPublica.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatTipoEntidadPublica> getAll() throws Exception {

        return  em.createQuery("Select catTipoEntidadPublica from CatTipoEntidadPublica as catTipoEntidadPublica order by catTipoEntidadPublica.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catTipoEntidadPublica.id) from CatTipoEntidadPublica as catTipoEntidadPublica");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatTipoEntidadPublica> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catTipoEntidadPublica from CatTipoEntidadPublica as catTipoEntidadPublica order by catTipoEntidadPublica.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatTipoEntidadPublica").executeUpdate();
        
    }
}
