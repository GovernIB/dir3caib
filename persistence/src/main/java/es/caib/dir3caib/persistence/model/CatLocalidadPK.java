package es.caib.dir3caib.persistence.model;
import es.caib.dir3caib.persistence.model.CatProvincia;
import es.caib.dir3caib.persistence.model.CatEntidadGeografica;
import java.io.Serializable;

/**
 * @version 1.0
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
  public int hashCode() {
    int hash = 3;
    hash = 67 * hash + (this.codigoLocalidad != null ? this.codigoLocalidad.hashCode() : 0);
    hash = 67 * hash + (this.provincia != null ? this.provincia.hashCode() : 0);
    hash = 67 * hash + (this.entidadGeografica != null ? this.entidadGeografica.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CatLocalidadPK other = (CatLocalidadPK) obj;
    if (this.codigoLocalidad != other.codigoLocalidad && (this.codigoLocalidad == null || !this.codigoLocalidad.equals(other.codigoLocalidad))) {
      return false;
    }
    if (this.provincia != other.provincia && (this.provincia == null || !this.provincia.equals(other.provincia))) {
      return false;
    }
    if (this.entidadGeografica != other.entidadGeografica && (this.entidadGeografica == null || !this.entidadGeografica.equals(other.entidadGeografica))) {
      return false;
    }
    return true;
  }

}