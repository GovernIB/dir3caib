package es.caib.dir3caib.back.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Informació disponible durant el cicle de vida de l'aplicació en la Sessio HTTP.
 * 
 * Exemple d'us:
 *          JAVA:   LoginInfo loginInfo = LoginInfo.getInstance();
 *          JSTL:   ${loginInfo.usuarioAutenticado.nombreCompleto}
 *               
 * Des de qualsevol lloc: Controller o JSP.
 * 
 * 
 * @author anadal
 * 
 */
public class LoginInfo {




  final boolean roleAdmin;
  final boolean descargaUnidad;
  final boolean descargaOficina;


  final User springSecurityUser;

  final Collection<GrantedAuthority> springRoles;


  /**
   *
   * @param springSecurityUser
   * @param roleAdmin
   * @param descargaUnidad
   * @param descargaOficina
   * @param roles
   */
  public LoginInfo(User springSecurityUser,
                   boolean roleAdmin, boolean descargaUnidad, boolean descargaOficina, Collection<GrantedAuthority> roles) {
    
    this.springSecurityUser = springSecurityUser;
    this.roleAdmin = roleAdmin;
    this.descargaUnidad = descargaUnidad;
    this.descargaOficina = descargaOficina;
    this.springRoles = roles;

  }

  public boolean isRoleAdmin() {
    return roleAdmin;
  }

  public boolean isDescargaUnidad() {
    return descargaUnidad;
  }

  public boolean isDescargaOficina() {
    return descargaOficina;
  }

  public User getSpringSecurityUser() {
    return springSecurityUser;
  }

  public Collection<GrantedAuthority> getSpringRoles() {
    return springRoles;
  }

  public UsernamePasswordAuthenticationToken generateToken() {
    UsernamePasswordAuthenticationToken authToken;
    Collection<GrantedAuthority> roles = getSpringRoles();
    authToken = new UsernamePasswordAuthenticationToken(this.springSecurityUser, "",
        roles);
    authToken.setDetails(this);
    return authToken;
  }

  public static LoginInfo getInstance() throws LoginException {
    Object obj;
    try {
      obj = SecurityContextHolder.getContext().getAuthentication().getDetails();
    } catch (Exception e) {
      // TODO traduccio
      throw new LoginException("Error intentant obtenir informació de Login.", e);
    }

    if (obj == null) {
      // TODO traduccio
      throw new LoginException("La informació de Login és buida");
    }

    if (obj instanceof LoginInfo) {
      return (LoginInfo) obj;
    } else {
      // TODO traduccio
      throw new LoginException("La informació de Login no és del tipus esperat."
          + " Hauria de ser de tipus " + LoginInfo.class.getName() + " i és del tipus "
          + obj.getClass().getName());
    }
  }

}
