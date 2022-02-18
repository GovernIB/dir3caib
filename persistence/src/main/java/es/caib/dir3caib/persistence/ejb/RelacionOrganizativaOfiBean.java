package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.NodoUtils;
import es.caib.dir3caib.utils.Utils;

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
@RolesAllowed({"DIR_ADMIN", "tothom", "DIR_WS"})
public class RelacionOrganizativaOfiBean extends BaseEjbJPA<RelacionOrganizativaOfi, Long>
    implements RelacionOrganizativaOfiLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public RelacionOrganizativaOfi getReference(Long id) throws Exception {

        return em.getReference(RelacionOrganizativaOfi.class, id);
    }

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
    @SuppressWarnings("unchecked")
    public List<String> getUnidadesOficinas() throws Exception  {

      
      String str  = "Select concat(relacionOrganizativaOfi.unidad.codigo, '_', relacionOrganizativaOfi.oficina.codigo) "
        +	"from RelacionOrganizativaOfi as relacionOrganizativaOfi ";

       /* String str  = "Select concat(relacionOrganizativaOfi.unidad.codigo, '_', relacionOrganizativaOfi.unidad.version, '_', relacionOrganizativaOfi.oficina.codigo) "
                +	"from RelacionOrganizativaOfi as relacionOrganizativaOfi ";*/
        
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
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Nodo> getOrganizativasByUnidadEstado(String codigo, String estado) throws Exception { 
    	return getOrganizativasByUnidadEstado(codigo,estado,false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Nodo> getOrganizativasByUnidadEstado(String codigo, String estado, boolean denominacionCooficial) throws Exception {
        Query q = em.createQuery("Select relacionOrganizativaOfi.oficina.codigo, relacionOrganizativaOfi.oficina.denominacion, relacionOrganizativaOfi.oficina.denomlenguacooficial "
        		+ "from RelacionOrganizativaOfi as relacionOrganizativaOfi where relacionOrganizativaOfi.unidad.codigo =:codigo and relacionOrganizativaOfi.estado.codigoEstadoEntidad =:estado "
                + "order by relacionOrganizativaOfi.oficina.codigo");

        q.setParameter("codigo",codigo);
        q.setParameter("estado",estado);

        return NodoUtils.getNodoListMinimo(q.getResultList(), denominacionCooficial);
    }

    @Override
    public List<RelacionOrganizativaOfi> getOrganizativasCompletoByUnidadEstado(String codigo, String estado) throws Exception {
    	return getOrganizativasCompletoByUnidadEstado(codigo,estado,false);
    }
    
    @Override
    public List<RelacionOrganizativaOfi> getOrganizativasCompletoByUnidadEstado(String codigo, String estado, boolean denominacionCooficial) throws Exception {
        Query q = em.createQuery("Select relacionOrganizativaOfi.oficina.codigo, relacionOrganizativaOfi.oficina.denominacion, " +
                "relacionOrganizativaOfi.oficina.codUoResponsable.codigo, relacionOrganizativaOfi.unidad.codigo, " +
                "relacionOrganizativaOfi.unidad.codUnidadRaiz.codigo, relacionOrganizativaOfi.oficina.denomlenguacooficial " +
                "from RelacionOrganizativaOfi as relacionOrganizativaOfi where " +
        		"relacionOrganizativaOfi.unidad.codUnidadRaiz.codigo =:codigo and relacionOrganizativaOfi.estado.codigoEstadoEntidad =:estado " +
        		"order by relacionOrganizativaOfi.oficina.codigo");

        q.setParameter("codigo", codigo);
        q.setParameter("estado", estado);

        List<Object[]> result = q.getResultList();
        List<RelacionOrganizativaOfi> relacionOrganizativaOfis = new ArrayList<RelacionOrganizativaOfi>();

        for (Object[] object : result) {
        	String denominacion = (denominacionCooficial && Utils.isNotEmpty((String) object[5])) ? (String) object[5] : (String) object[1];
            RelacionOrganizativaOfi relacionOrganizativaOfi = new RelacionOrganizativaOfi((String) object[0], denominacion, (String) object[2], (String) object[3], (String) object[4]);
            relacionOrganizativaOfis.add(relacionOrganizativaOfi);
        }

        return relacionOrganizativaOfis;
    }

    @Override
    public void deleteAll() throws Exception {

        em.createQuery("delete from RelacionOrganizativaOfi").executeUpdate();
        em.flush();
    }

}
