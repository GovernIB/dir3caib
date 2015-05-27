package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;
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
@Stateless(name = "DescargaEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class DescargaBean extends BaseEjbJPA<Descarga, Long> implements DescargaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Descarga findById(Long id) throws Exception {

        return em.find(Descarga.class, id);
    }
    
    public Descarga findByTipo(String tipo) throws Exception {
        
        Query query = em.createQuery( "select descarga from Descarga as descarga where descarga.tipo=? order by descarga.codigo desc");
        query.setParameter(1, tipo);
        List<Descarga> descargas = query.getResultList();
        if(!descargas.isEmpty()){
          return (Descarga) query.getResultList().get(0);
        } else {
          return null;
        } 
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Descarga> getAll() throws Exception {

        return  em.createQuery("Select descarga from Descarga as descarga order by descarga.codigo").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(descarga.codigo) from Descarga as descarga");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<Descarga> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select descarga from Descarga as descarga order by descarga.codigo");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    
    public void deleteByTipo(String tipo) throws Exception {
      Descarga descarga = findByTipo(tipo);
      em.remove(descarga);
         
    }

    public void deleteAllByTipo(String tipo) throws Exception {
        Query query = em.createQuery( "delete from Descarga as descarga where descarga.tipo=? ");
        query.setParameter(1, tipo);
        query.executeUpdate();
    }
}
