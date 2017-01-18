package es.caib.dir3caib.back.controller;

import es.caib.dir3caib.persistence.ejb.CatComunidadAutonomaLocal;
import es.caib.dir3caib.persistence.ejb.CatEstadoEntidadLocal;
import es.caib.dir3caib.persistence.ejb.CatNivelAdministracionLocal;
import es.caib.dir3caib.persistence.ejb.DescargaLocal;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;

import javax.ejb.EJB;


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

  @EJB(mappedName = "dir3caib/DescargaEJB/local")
  protected DescargaLocal descargaEjb;



  protected final Logger log = Logger.getLogger(getClass());

  /**
   * Retorna el mensaje traducido según el idioma del usuario
   * @param key
   * @return
   */
  protected String getMessage(String key){
    return I18NUtils.tradueix(key);
  }



}
