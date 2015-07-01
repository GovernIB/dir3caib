package es.caib.dir3caib.back.controller;

import es.caib.dir3caib.persistence.ejb.CatComunidadAutonomaLocal;
import es.caib.dir3caib.persistence.ejb.CatEstadoEntidadLocal;
import es.caib.dir3caib.persistence.ejb.CatNivelAdministracionLocal;
import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.CatEstadoEntidad;
import es.caib.dir3caib.persistence.model.CatNivelAdministracion;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.ejb.EJB;
import java.util.List;


/**
 * Created by Fundació BIT.
 * @author earrivi
 * @author anadal
 * Date: 2/10/13
 */
public class BaseController {

  @EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
  protected CatNivelAdministracionLocal catNivelAdministracionEjb;

  @EJB(mappedName = "dir3caib/CatComunidadAutonomaEJB/local")
  protected CatComunidadAutonomaLocal catComunidadAutonomaEjb;

  @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
  protected CatEstadoEntidadLocal catEstadoEntidadEjb;

  @ModelAttribute("administraciones")
  public List<CatNivelAdministracion> administraciones() throws Exception {
    return catNivelAdministracionEjb.getAll();
  }

  @ModelAttribute("comunidades")
  public List<CatComunidadAutonoma> comunidades() throws Exception {
    return catComunidadAutonomaEjb.getAll();
  }

  @ModelAttribute("estadosEntidad")
  public List<CatEstadoEntidad> estadosEntidad() throws Exception {
    return catEstadoEntidadEjb.getAll();
  }

  protected final Logger log = Logger.getLogger(getClass());

}
