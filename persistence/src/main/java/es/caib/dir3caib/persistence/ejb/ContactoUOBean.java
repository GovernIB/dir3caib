/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 *
 * @author mgonzalez
 */
@Stateless(name = "ContactoUOEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class ContactoUOBean extends BaseEjbJPA<ContactoUnidadOrganica, Long> implements ContactoUOLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public ContactoUnidadOrganica findById(Long id) throws Exception {

        return em.find(ContactoUnidadOrganica.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<ContactoUnidadOrganica> getAll() throws Exception {

        return  em.createQuery("Select contacto from ContactoUnidadOrganica as contacto order by contacto.codContacto").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(contacto.codContacto) from ContactoUnidadOrganica as contacto");

        return (Long) q.getSingleResult();
    }

    @Override
    public List<ContactoUnidadOrganica> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select contacto from ContactoUnidadOrganica as contacto order by contacto.codContacto");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }
    

    public void deleteAll() throws Exception {

        em.createQuery("delete from ContactoUnidadOrganica").executeUpdate();
        
    }
  
}
