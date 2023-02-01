package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatEntidadGeografica;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.utils.Utils;

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
@Stateless(name = "CatEntidadGeograficaEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.ROL_TOTHOM, Dir3caibConstantes.DIR_WS})
public class CatEntidadGeograficaBean extends BaseEjbJPA<CatEntidadGeografica, String> implements CatEntidadGeograficaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public CatEntidadGeografica getReference(String id) throws Exception {

        return em.getReference(CatEntidadGeografica.class, id);
    }

    @Override
    public CatEntidadGeografica findById(String id) throws Exception {

        return em.find(CatEntidadGeografica.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatEntidadGeografica> getAll() throws Exception {

        return  em.createQuery("Select catEntidadGeografica from CatEntidadGeografica as catEntidadGeografica order by catEntidadGeografica.codigoEntidadGeografica").getResultList();
    }
    
    @Override
    @SuppressWarnings(value = "unchecked")
    public List<CatEntidadGeografica> getByEstado(String estado) throws Exception {
        Query q = em.createQuery("Select catEntidadGeografica from CatEntidadGeografica as catEntidadGeografica where catEntidadGeografica.estado.codigoEstadoEntidad=:estado order by catEntidadGeografica.codigoEntidadGeografica");
        q.setParameter("estado", estado);
    	
        return q.getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(catEntidadGeografica.codigoEntidadGeografica) from CatEntidadGeografica as catEntidadGeografica");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatEntidadGeografica> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select catEntidadGeografica from CatEntidadGeografica as catEntidadGeografica order by catEntidadGeografica.codigoEntidadGeografica");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteAll() throws Exception {

        em.createQuery("delete from CatEntidadGeografica").executeUpdate();
        
    }
}
