package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi;
import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;
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
 * Date: 10/10/13
 */
@Stateless(name = "RelacionOrganizativaOfiEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class RelacionOrganizativaOfiBean extends BaseEjbJPA<RelacionOrganizativaOfi, Long>
    implements RelacionOrganizativaOfiLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public RelacionOrganizativaOfi findById(Long id) throws Exception {

        return em.find(RelacionOrganizativaOfi.class, id);
    }
    
    
    @Override
    public RelacionOrganizativaOfi findByPKs(String codigoUnidad, String codigoOficina) throws Exception {

       Query query = em.createQuery("Select relacionOrganizativaOfi from RelacionOrganizativaOfi as relacionOrganizativaOfi "
          + " where relacionOrganizativaOfi.oficina.codigo = :codigoOficina AND " 
          + " relacionOrganizativaOfi.unidad.codigo = :codigoUnidad");
       query.setParameter("codigoUnidad", codigoUnidad);
       query.setParameter("codigoOficina", codigoOficina);

       try {
         return (RelacionOrganizativaOfi)query.getSingleResult();
       } catch(Throwable th) {
         return null;
       }
    }
    
    @Override
    public List<String> getUnidadesOficinas() throws Exception  {
      //select concat(c.firstname, ' ', c.lastname) as fullname from Contact c
      
      String str  = "Select concat(relacionOrganizativaOfi.unidad.codigo, '_', relacionOrganizativaOfi.oficina.codigo) "
        +	"from RelacionOrganizativaOfi as relacionOrganizativaOfi ";
        
      Query query = em.createQuery(str);
      
      return query.getResultList();
      
    }
    
    
    

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<RelacionOrganizativaOfi> getAll() throws Exception {

        return  em.createQuery("Select relacionOrganizativaOfi from RelacionOrganizativaOfi as relacionOrganizativaOfi ").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(relacionOrganizativaOfi.id) from RelacionOrganizativaOfi as relacionOrganizativaOfi");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<RelacionOrganizativaOfi> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select relacionOrganizativaOfi from RelacionOrganizativaOfi as relacionOrganizativaOfi ");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }


    public List<ObjetoBasico> getOrganizativasByUnidadEstado(String codigo, String estado) throws Exception {
        Query q = em.createQuery("Select relacionOrganizativaOfi.oficina.codigo, relacionOrganizativaOfi.oficina.denominacion  from RelacionOrganizativaOfi as relacionOrganizativaOfi where " +
                "relacionOrganizativaOfi.unidad.codigo =:codigo and relacionOrganizativaOfi.estado.descripcionEstadoEntidad =:estado order by relacionOrganizativaOfi.oficina.codigo");

        q.setParameter("codigo",codigo);
        q.setParameter("estado",estado);


        return getObjetoBasicoList(q.getResultList());
    }
    @Override
    public void deleteAll() throws Exception {

        em.createQuery("delete from RelacionOrganizativaOfi").executeUpdate();

    }

    /**
     * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     *
     * @param result
     * @return
     * @throws Exception
     */
    private List<ObjetoBasico> getObjetoBasicoList(List<Object[]> result) throws Exception {

        List<ObjetoBasico> listaReducida = new ArrayList<ObjetoBasico>();

        for (Object[] object : result) {
            ObjetoBasico objetoBasico = new ObjetoBasico((String) object[0], (String) object[1], "", "", "", "");

            listaReducida.add(objetoBasico);
        }

        return listaReducida;
    }
}
