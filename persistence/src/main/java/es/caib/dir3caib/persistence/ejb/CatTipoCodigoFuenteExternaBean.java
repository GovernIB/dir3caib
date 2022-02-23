package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoCodigoFuenteExterna;
import es.caib.dir3caib.persistence.model.CatTipoContacto;
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
@Stateless(name = "CatTipoCodigoFuenteExternaEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.DIR_WS})
public class CatTipoCodigoFuenteExternaBean extends BaseEjbJPA<CatTipoCodigoFuenteExterna, Long> implements CatTipoCodigoFuenteExternaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatTipoCodigoFuenteExterna getReference(Long id) throws Exception {

        return em.getReference(CatTipoCodigoFuenteExterna.class, id);
    }

    @Override
    public CatTipoCodigoFuenteExterna findById(Long id) throws Exception {

        return em.find(CatTipoCodigoFuenteExterna.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatTipoCodigoFuenteExterna> getAll() throws Exception {

        return  em.createQuery("Select catTipoCodigoFuenteExterna from CatTipoCodigoFuenteExterna as catTipoCodigoFuenteExterna order by catTipoCodigoFuenteExterna.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catTipoCodigoFuenteExterna.id) from CatTipoCodigoFuenteExterna as catTipoCodigoFuenteExterna");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatTipoCodigoFuenteExterna> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catTipoCodigoFuenteExterna from CatTipoCodigoFuenteExterna as catTipoCodigoFuenteExterna order by catTipoCodigoFuenteExterna.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatTipoCodigoFuenteExterna").executeUpdate();
        
    }
}
