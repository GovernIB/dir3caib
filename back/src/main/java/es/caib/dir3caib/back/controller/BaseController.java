package es.caib.dir3caib.back.controller;

import es.caib.dir3caib.back.utils.CodigoValor;
import es.caib.dir3caib.persistence.ejb.*;
import es.caib.dir3caib.persistence.model.CatProvincia;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ejb.EJB;
import java.util.ArrayList;
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

  @EJB(mappedName = "dir3caib/DescargaEJB/local")
  protected DescargaLocal descargaEjb;

  @EJB(mappedName = "dir3caib/CatProvinciaEJB/local")
  protected CatProvinciaLocal catProvinciaEjb;



  protected final Logger log = Logger.getLogger(getClass());

  /**
   * Retorna el mensaje traducido según el idioma del usuario
   * @param key
   * @return
   */
  protected String getMessage(String key){
    return I18NUtils.tradueix(key);
  }

  /**
   * Obtiene los {@link es.caib.dir3caib.persistence.model.CatProvincia} de la comunidad autonoma seleccionada
   */
  @RequestMapping(value = "/provincias", method = RequestMethod.GET)
  public
  @ResponseBody
  List<CodigoValor> provincias(@RequestParam Long id) throws Exception {

    List<CatProvincia> provincias = catProvinciaEjb.getByComunidadAutonoma(id);
    List<CodigoValor> codigosValor = new ArrayList<CodigoValor>();
    for (CatProvincia provincia : provincias) {
      CodigoValor codigoValor = new CodigoValor();
      codigoValor.setId(provincia.getCodigoProvincia());
      codigoValor.setDescripcion(provincia.getDescripcionProvincia());
      codigosValor.add(codigoValor);
    }
    return codigosValor;
  }


}
