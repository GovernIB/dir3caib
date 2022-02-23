package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoServicio;
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
 * @author jagarcia
 * Date: 02/12/21
 */
@Stateless(name = "CatTipoServicioEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.DIR_WS})
public class CatTipoServicioBean extends BaseEjbJPA<CatTipoServicio, Long> implements CatTipoServicioLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatTipoServicio getReference(Long id) throws Exception {

        return em.getReference(CatTipoServicio.class, id);
    }

    @Override
    public CatTipoServicio findById(Long id) throws Exception {

        return em.find(CatTipoServicio.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatTipoServicio> getAll() throws Exception {

        return  em.createQuery("Select catTipoServicio from CatTipoServicio as catTipoServicio order by catTipoServicio.codigoTipoServicio").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catTipoServicio.codigoTipoServicio) from CatTipoServicio as catTipoServicio");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatTipoServicio> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catTipoServicio from CatTipoServicio as catTipoServicio order by catTipoServicio.codigoTipoServicio");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatTipoServicio").executeUpdate();
        
    }
}
