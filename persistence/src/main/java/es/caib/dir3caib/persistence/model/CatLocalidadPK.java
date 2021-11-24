package es.caib.dir3caib.persistence.model;
import es.caib.dir3caib.persistence.model.CatProvincia;
import es.caib.dir3caib.persistence.model.CatEntidadGeografica;
import java.io.Serializable;
import java.util.Objects;

/**
 * @version 1.1
 * @created 28-oct-2013 14:41:39
 */
public class CatLocalidadPK  implements Serializable {

 
	private Long codigoLocalidad;  
	private CatProvincia provincia;
	private CatEntidadGeografica entidadGeografica;

	public CatLocalidadPK(){

	}

  public CatLocalidadPK(Long codigoLocalidad, CatProvincia provincia, CatEntidadGeografica entidadGeografica) {
    this.codigoLocalidad = codigoLocalidad;
    this.provincia = provincia;
    this.entidadGeografica = entidadGeografica;
  }
  
  
  

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoLocalidad
   */
  public Long getCodigoLocalidad() {
    return codigoLocalidad;
  }

  /**
   * @param codigoLocalidad the codigoLocalidad to set
   */
  public void setCodigoLocalidad(Long codigoLocalidad) {
    this.codigoLocalidad = codigoLocalidad;
  }

  /**
   * @return the provincia
   */
  public CatProvincia getProvincia() {
    return provincia;
  }

  /**
   * @param provincia the provincia to set
   */
  public void setProvincia(CatProvincia provincia) {
    this.provincia = provincia;
  }

  /**
   * @return the entidadGeografica
   */


  public CatEntidadGeografica getEntidadGeografica() {
    return entidadGeografica;
  }

  /**
   * @param entidadGeografica the codigoEntidadGeografica to set
   */
  public void setEntidadGeografica(CatEntidadGeografica entidadGeografica) {
    this.entidadGeografica = entidadGeografica;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CatLocalidadPK that = (CatLocalidadPK) o;
    return codigoLocalidad.equals(that.codigoLocalidad) && provincia.equals(that.provincia) && entidadGeografica.equals(that.entidadGeografica);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoLocalidad, provincia, entidadGeografica);
  }


}