package es.caib.dir3caib.back.security;

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


    for (GrantedAuthority grantedAuthority : seyconAuthorities) {
      String rol = grantedAuthority.getAuthority();
      log.info("Rol SEYCON : " + rol);

      if (Dir3caibConstantes.DIR_ADMIN.equals(rol)) {
        containsRoleAdmin = true;
      }

    }

    LoginInfo loginInfo;
    // create a new authentication token
    loginInfo = new LoginInfo(user, containsRoleAdmin, Configuracio.isDevelopment(),seyconAuthorities);

    // and set the authentication of the current Session context
    SecurityContextHolder.getContext().setAuthentication(loginInfo.generateToken());

  }
  
  
  

  
}
