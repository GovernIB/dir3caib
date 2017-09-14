package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;
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
@Stateless(name = "DescargaEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class DescargaBean extends BaseEjbJPA<Descarga, Long> implements DescargaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Descarga getReference(Long id) throws Exception {

        return em.getReference(Descarga.class, id);
    }

    @Override
    public Descarga findById(Long id) throws Exception {

        return em.find(Descarga.class, id);
    }

    @Override
    public Descarga ultimaDescarga(String tipo) throws Exception {
        
        Query query = em.createQuery( "select descarga from Descarga as descarga where descarga.tipo= :tipo order by descarga.codigo desc");
        query.setParameter("tipo", tipo);
        List<Descarga> descargas = query.getResultList();
        if(!descargas.isEmpty()){
          return (Descarga) query.getResultList().get(0);
        } else {
          return null;
        } 
    }

    @Override
    public Long totalDescargas(String tipo) throws Exception {

        Query q = em.createQuery("Select count(descarga.codigo) from Descarga as descarga where descarga.tipo = :tipo");
        q.setParameter("tipo", tipo);

        return (Long) q.getSingleResult();

    }

    public Descarga ultimaDescargaSincronizada(String tipo) throws Exception {

        Query query = em.createQuery( "select descarga from Descarga as descarga where descarga.tipo= :tipo and descarga.estado= :correcto and " +
                "descarga.fechaImportacion != null order by descarga.codigo desc");

        query.setParameter("tipo", tipo);
        query.setParameter("correcto", Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO);

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
    @SuppressWarnings(value = "unchecked")
    public List<Descarga> getAllByTipo(String tipo) throws Exception {

        Query query =em.createQuery("Select descarga from Descarga as descarga where descarga.tipo=? order by descarga.codigo desc");
        query.setParameter(1, tipo);
        return query.getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(descarga.codigo) from Descarga as descarga");

        return (Long) q.getSingleResult();
    }


    public Long getTotalByTipo(String tipo) throws Exception {

        Query q = em.createQuery("Select count(descarga.codigo) from Descarga as descarga where descarga.tipo=?");
        q.setParameter(1, tipo);

        return (Long) q.getSingleResult();
    }

    @Override
    public List<Descarga> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select descarga from Descarga as descarga order by descarga.codigo");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }


    public List<Descarga> getPaginationByTipo(int inicio, String tipo) throws Exception {

        Query q = em.createQuery("Select descarga from Descarga as descarga where descarga.tipo=? order by descarga.codigo desc");
        q.setParameter(1, tipo);
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    public void deleteAllByTipo(String tipo) throws Exception {
        Query query = em.createQuery( "delete from Descarga as descarga where descarga.tipo=? ");
        query.setParameter(1, tipo);
        query.executeUpdate();
    }
}
