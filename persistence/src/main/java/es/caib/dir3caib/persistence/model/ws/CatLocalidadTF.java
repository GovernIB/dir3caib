package es.caib.dir3caib.persistence.model.ws;

import es.caib.dir3caib.persistence.model.CatLocalidad;

/**
 * Created 16/04/14 12:10
 *
 * @author mgonzalez
 */
public class CatLocalidadTF {

  private Long codigoLocalidad;
  private Long codigoProvincia;
  private String descripcionLocalidad;
  private String codigoEntidadGeografica;

  public CatLocalidadTF() {
  }

  public Long getCodigoLocalidad() {
    return codigoLocalidad;
  }

  public void setCodigoLocalidad(Long codigoLocalidad) {
    this.codigoLocalidad = codigoLocalidad;
  }

  public Long getCodigoProvincia() {
    return codigoProvincia;
  }

  public void setCodigoProvincia(Long codigoProvincia) {
    this.codigoProvincia = codigoProvincia;
  }

  public String getDescripcionLocalidad() {
    return descripcionLocalidad;
  }

  public void setDescripcionLocalidad(String descripcionLocalidad) {
    this.descripcionLocalidad = descripcionLocalidad;
  }

  public String getCodigoEntidadGeografica() {
    return codigoEntidadGeografica;
  }

  public void setCodigoEntidadGeografica(String codigoEntidadGeografica) {
    this.codigoEntidadGeografica = codigoEntidadGeografica;
  }

  public void rellenar(CatLocalidad localidad){
    this.setCodigoLocalidad(localidad.getCodigoLocalidad());
    this.setDescripcionLocalidad(localidad.getDescripcionLocalidad());

    if(localidad.getProvincia() != null){
       this.setCodigoProvincia(localidad.getProvincia().getCodigoProvincia());
    }

    if(localidad.getEntidadGeografica() != null){
       this.setCodigoEntidadGeografica(localidad.getEntidadGeografica().getCodigoEntidadGeografica());
    }


  }


  public static CatLocalidadTF generar(CatLocalidad localidad){
    CatLocalidadTF localidadTF =  new CatLocalidadTF();
    if(localidad !=null){
       localidadTF.rellenar(localidad);
    }
    return localidadTF;
  }

}
