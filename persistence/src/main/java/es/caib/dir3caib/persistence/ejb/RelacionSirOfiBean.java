package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.RelacionSirOfi;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal (Eliminar PKs multiples)
 * Date: 10/10/13
 */
@Stateless(name = "RelacionSirOfiEJB")
@SecurityDomain("seycon")
@RolesAllowed({"DIR_ADMIN", "tothom", "DIR_WS"})
public class RelacionSirOfiBean extends BaseEjbJPA<RelacionSirOfi, Long>
   implements RelacionSirOfiLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public RelacionSirOfi getReference(Long id) throws Exception {

        return em.getReference(RelacionSirOfi.class, id);
    }

    @Override
    public RelacionSirOfi findById(Long id) throws Exception {

        return em.find(RelacionSirOfi.class, id);
    }
    
    
    
    @Override
    public RelacionSirOfi findByPKs(String codigoUnidad, Long versionUnidad,String codigoOficina) throws Exception {

       Query query = em.createQuery("select relacionSirOfi from RelacionSirOfi as relacionSirOfi "
          + " where relacionSirOfi.oficina.codigo = :codigoOficina AND " 
          + " relacionSirOfi.unidad.codigo = :codigoUnidad AND relacionSirOfi.unidad.version = :versionUnidad");
       query.setParameter("codigoUnidad", codigoUnidad);
       query.setParameter("codigoOficina", codigoOficina);
       query.setParameter("versionUnidad", versionUnidad);

       try {
         return (RelacionSirOfi)query.getSingleResult();
       } catch(Throwable th) {
         return null;
       }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<String> getUnidadesOficinas() throws Exception  {
      //select concat(c.firstname, ' ', c.lastname) as fullname from Contact c
      
      String str  = "Select concat(relacionSirOfi.unidad.codigo, '_',relacionSirOfi.unidad.version,'_', relacionSirOfi.oficina.codigo) "
        + "from RelacionSirOfi as relacionSirOfi ";
        
      Query query = em.createQuery(str);
      
      return query.getResultList();
      
    }
    

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<RelacionSirOfi> getAll() throws Exception {

        return  em.createQuery("Select relacionSirOfi from RelacionSirOfi as relacionSirOfi ").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(relacionSirOfi.id) from RelacionSirOfi as relacionSirOfi");

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RelacionSirOfi> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select relacionSirOfi from RelacionSirOfi as relacionSirOfi ");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    @Override
    public void deleteAll() throws Exception {

        em.createQuery("delete from RelacionSirOfi").executeUpdate();
        em.flush();
    }

   public List<RelacionSirOfi> relacionesSirOfiByUnidaddEstado(String codigo, String estado) throws Exception {
      Query q = em.createQuery("Select relacionSirOfi.oficina.codigo, relacionSirOfi.oficina.denominacion, " +
              "relacionSirOfi.oficina.codUoResponsable.codigo, relacionSirOfi.unidad.codigo " +
              "from RelacionSirOfi as relacionSirOfi where relacionSirOfi.unidad.codUnidadRaiz.codigo =:codigo " +
              "and relacionSirOfi.estado.codigoEstadoEntidad =:estado order by relacionSirOfi.oficina.codigo");

      q.setParameter("codigo", codigo);
      q.setParameter("estado", estado);

       List<Object[]> result = q.getResultList();
       List<RelacionSirOfi> relacionSirOfis = new ArrayList<RelacionSirOfi>();

       for (Object[] object : result) {
           RelacionSirOfi relacionOrganizativaOfi = new RelacionSirOfi((String) object[0], (String) object[1], (String) object[2], (String) object[3]);
           relacionSirOfis.add(relacionOrganizativaOfi);
       }

       return relacionSirOfis;

   }
}
