package es.caib.dir3caib.persistence.model.ws;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import org.apache.log4j.Logger;

/**
 * Created 16/04/14 12:10
 *
 * @author mgonzalez
 */
public class CatComunidadAutonomaTF {

  protected final Logger log = Logger.getLogger(getClass());

  private Long codigoComunidad;
 	private String descripcionComunidad;
 	private Long codigoPais;


  public CatComunidadAutonomaTF() {
  }

  public Long getCodigoComunidad() {
    return codigoComunidad;
  }

  public void setCodigoComunidad(Long codigoComunidad) {
    this.codigoComunidad = codigoComunidad;
  }

  public String getDescripcionComunidad() {
    return descripcionComunidad;
  }

  public void setDescripcionComunidad(String descripcionComunidad) {
    this.descripcionComunidad = descripcionComunidad;
  }

  public Long getCodigoPais() {
    return codigoPais;
  }

  public void setCodigoPais(Long codigoPais) {
    this.codigoPais = codigoPais;
  }


  public void rellenar(CatComunidadAutonoma comunidadAutonoma){
     this.setCodigoComunidad(comunidadAutonoma.getCodigoComunidad());
     this.setDescripcionComunidad(comunidadAutonoma.getDescripcionComunidad());

     if(comunidadAutonoma.getPais() != null){
       this.setCodigoPais(comunidadAutonoma.getPais().getCodigoPais());
     }


  }


  public static CatComunidadAutonomaTF generar(CatComunidadAutonoma comunidadAutonoma){
    CatComunidadAutonomaTF comunidadAutonomaTF =  new CatComunidadAutonomaTF();
    if(comunidadAutonoma !=null){
       comunidadAutonomaTF.rellenar(comunidadAutonoma);
    }
    return comunidadAutonomaTF;
  }

}
