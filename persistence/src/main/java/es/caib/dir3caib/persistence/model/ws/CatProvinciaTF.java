package es.caib.dir3caib.persistence.model.ws;

import es.caib.dir3caib.persistence.model.CatProvincia;

/**
 * Created 16/04/14 12:10
 *
 * @author mgonzalez
 */
public class CatProvinciaTF {

  private Long codigoProvincia;
  private Long codigoComunidadAutonoma;
  private String descripcionProvincia;


  public CatProvinciaTF() {
  }

  public Long getCodigoProvincia() {
    return codigoProvincia;
  }

  public void setCodigoProvincia(Long codigoProvincia) {
    this.codigoProvincia = codigoProvincia;
  }

  public Long getCodigoComunidadAutonoma() {
    return codigoComunidadAutonoma;
  }

  public void setCodigoComunidadAutonoma(Long codigoComunidadAutonoma) {
    this.codigoComunidadAutonoma = codigoComunidadAutonoma;
  }

  public String getDescripcionProvincia() {
    return descripcionProvincia;
  }

  public void setDescripcionProvincia(String descripcionProvincia) {
    this.descripcionProvincia = descripcionProvincia;
  }

  public void rellenar(CatProvincia provincia){
     this.setCodigoProvincia(provincia.getCodigoProvincia());
     this.setDescripcionProvincia(provincia.getDescripcionProvincia());

     if(provincia.getComunidadAutonoma() != null){
       this.setCodigoComunidadAutonoma(provincia.getComunidadAutonoma().getCodigoComunidad());
     }


  }


  public static CatProvinciaTF generar(CatProvincia provincia){
    CatProvinciaTF provinciaTF =  new CatProvinciaTF();
    if(provincia !=null){
       provinciaTF.rellenar(provincia);
    }
    return provinciaTF;
  }

}
