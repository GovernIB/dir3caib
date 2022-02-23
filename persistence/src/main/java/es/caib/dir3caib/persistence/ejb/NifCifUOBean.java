package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CodigoUnidadOrganica;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.NifCifUnidadOrganica;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author mgonzalez
 * 15/12/2021
 */
@Stateless(name = "NifCifUOEJB")
@SecurityDomain("seycon")
@RolesAllowed({Dir3caibConstantes.DIR_ADMIN, Dir3caibConstantes.ROL_TOTHOM, Dir3caibConstantes.DIR_WS})
public class NifCifUOBean extends BaseEjbJPA<NifCifUnidadOrganica, Long> implements NifCifUOLocal{
    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public NifCifUnidadOrganica getReference(Long id) throws Exception {

        return em.getReference(NifCifUnidadOrganica.class, id);
    }

    @Override
    public NifCifUnidadOrganica findById(Long id) throws Exception {

        return em.find(NifCifUnidadOrganica.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<NifCifUnidadOrganica> getAll() throws Exception {

        return  em.createQuery("Select nifcifUO from NifCifUnidadOrganica as nifcifUO order by nifcifUO.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(nifcifUO.id) from NifCifUnidadOrganica as nifcifUO");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NifCifUnidadOrganica> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select nifcifUO from NifCifUnidadOrganica as nifcifUO order by nifcifUO.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
}
