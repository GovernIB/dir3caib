package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatJerarquiaOficina;
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
@Stateless(name = "CatJerarquiaOficinaEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.ROL_TOTHOM, Dir3caibConstantes.DIR_WS})
public class CatJerarquiaOficinaBean extends BaseEjbJPA<CatJerarquiaOficina, Long> implements CatJerarquiaOficinaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatJerarquiaOficina getReference(Long id) throws Exception {

        return em.getReference(CatJerarquiaOficina.class, id);
    }

    @Override
    public CatJerarquiaOficina findById(Long id) throws Exception {

        return em.find(CatJerarquiaOficina.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatJerarquiaOficina> getAll() throws Exception {

        return  em.createQuery("Select catJerarquiaOficina from CatJerarquiaOficina as catJerarquiaOficina order by catJerarquiaOficina.codigoJerarquiaOficina").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catJerarquiaOficina.codigoJerarquiaOficina) from CatJerarquiaOficina as catJerarquiaOficina");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatJerarquiaOficina> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catJerarquiaOficina from CatJerarquiaOficina as catJerarquiaOficina order by catJerarquiaOficina.codigoJerarquiaOficina");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatJerarquiaOficina").executeUpdate();
        
    }
}
