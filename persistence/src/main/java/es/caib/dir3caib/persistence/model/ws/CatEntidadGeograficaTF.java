package es.caib.dir3caib.persistence.model.ws;

import es.caib.dir3caib.persistence.model.CatEntidadGeografica;

/**
 * Created 16/04/14 12:10
 *
 * @author mgonzalez
 */
public class CatEntidadGeograficaTF {

  private String codigoEntidadGeografica;
  private String descripcionEntidadGeografica;

  public CatEntidadGeograficaTF(){

  }


  public String getCodigoEntidadGeografica() {
    return codigoEntidadGeografica;
  }

  public void setCodigoEntidadGeografica(String codigoEntidadGeografica) {
    this.codigoEntidadGeografica = codigoEntidadGeografica;
  }

  public String getDescripcionEntidadGeografica() {
    return descripcionEntidadGeografica;
  }

  public void setDescripcionEntidadGeografica(String descripcionEntidadGeografica) {
    this.descripcionEntidadGeografica = descripcionEntidadGeografica;
  }

  public void rellenar(CatEntidadGeografica entidadGeografica){
     this.setCodigoEntidadGeografica(entidadGeografica.getCodigoEntidadGeografica());
     this.setDescripcionEntidadGeografica(entidadGeografica.getDescripcionEntidadGeografica());
  }


  public static CatEntidadGeograficaTF generar(CatEntidadGeografica entidadGeografica){
    CatEntidadGeograficaTF entidadGeograficaTF =  new CatEntidadGeograficaTF();
    if(entidadGeografica !=null){
       entidadGeograficaTF.rellenar(entidadGeografica);
    }
    return entidadGeograficaTF;
  }

}
