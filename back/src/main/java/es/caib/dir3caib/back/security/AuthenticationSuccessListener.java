package es.caib.dir3caib.back.security;

import es.caib.dir3caib.persistence.ejb.DescargaLocal;
import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.naming.InitialContext;
import java.util.Collection;


/**
 *
 * @author anadal
 *
 */
@Component
public class AuthenticationSuccessListener implements
    ApplicationListener<InteractiveAuthenticationSuccessEvent> {

  protected final Logger log = Logger.getLogger(getClass());
  
  
  protected DescargaLocal descargaEjb = null;
  
  

  

  @Override
  public synchronized void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {

    SecurityContext sc = SecurityContextHolder.getContext();
    Authentication au = sc.getAuthentication();

    if (au == null) {
      // TODO traduccio
      throw new LoginException("NO PUC ACCEDIR A LA INFORMACIO de AUTENTICACIO");
    }

    User user = (User) au.getPrincipal();
    
    String name = user.getUsername();
    log.info(" =================================================================");
    log.info(" ============ Login Usuari: " + name);

    // Cercam si té  ROLE_ADMIN
    Collection<GrantedAuthority> seyconAuthorities = user.getAuthorities();
    boolean containsRoleAdmin = false;
    boolean containsDescargaUnidad = false;
    boolean containsDescargaOficina = false;


    for (GrantedAuthority grantedAuthority : seyconAuthorities) {
      String rol = grantedAuthority.getAuthority();
      log.info("Rol SEYCON : " + rol);

      if (Dir3caibConstantes.ROL_ADMIN.equals(rol)) {
        containsRoleAdmin = true;
      }

    }

    if (descargaEjb == null) {
      try {
        descargaEjb = (DescargaLocal) new InitialContext()
            .lookup("dir3caib/DescargaEJB/local");
      } catch (Exception e) {
        // TODO traduccio
        throw new LoginException("No puc accedir al gestor de BBDD d´obtenció de" +
            		" descàrregues ", e);
      }
    }

    if(containsRoleAdmin){
        try {
          Descarga descargaUnidad = descargaEjb.findByTipo(Dir3caibConstantes.UNIDAD);
          Descarga descargaOficina = descargaEjb.findByTipo(Dir3caibConstantes.OFICINA);
          if(descargaUnidad != null) { containsDescargaUnidad = true;}
          if(descargaOficina != null) { containsDescargaOficina = true;}
        }catch(Exception e){
          throw new LoginException("Error obtenint les descarregues d'unitats i oficines");
        }
    }

    LoginInfo loginInfo;
    // create a new authentication token
    loginInfo = new LoginInfo(user, containsRoleAdmin, containsDescargaUnidad, containsDescargaOficina, Configuracio.isDevelopment(),seyconAuthorities);

    // and set the authentication of the current Session context
    SecurityContextHolder.getContext().setAuthentication(loginInfo.generateToken());

  }
  
  
  

  
}
