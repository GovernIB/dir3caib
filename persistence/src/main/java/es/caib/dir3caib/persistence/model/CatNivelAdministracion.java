package es.caib.dir3caib.persistence.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATNIVELADMINISTRACION", schema = "", catalog = "")
public class CatNivelAdministracion implements Serializable {

	private Long codigoNivelAdministracion;
	private String descripcionNivelAdministracion;

	public CatNivelAdministracion(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoNivelAdministracion
   */
  @Id
  @Column(name = "CODIGONIVELADMINISTRACION", nullable = false, length = 2)
  public Long getCodigoNivelAdministracion() {
    return codigoNivelAdministracion;
  }

  /**
   * @param codigoNivelAdministracion the codigoNivelAdministracion to set
   */
  public void setCodigoNivelAdministracion(Long codigoNivelAdministracion) {
    this.codigoNivelAdministracion = codigoNivelAdministracion;
  }

  /**
   * @return the descripcionNivelAdministracion
   */
  @Column(name = "DESCRIPCIONNIVELADMINISTRACION", nullable = false, length = 300)
  public String getDescripcionNivelAdministracion() {
    return descripcionNivelAdministracion;
  }

  /**
   * @param descripcionNivelAdministracion the descripcionNivelAdministracion to set
   */
  public void setDescripcionNivelAdministracion(String descripcionNivelAdministracion) {
    this.descripcionNivelAdministracion = descripcionNivelAdministracion;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + (this.codigoNivelAdministracion != null ? this.codigoNivelAdministracion.hashCode() : 0);
    hash = 29 * hash + (this.descripcionNivelAdministracion != null ? this.descripcionNivelAdministracion.hashCode() : 0);
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
    final CatNivelAdministracion other = (CatNivelAdministracion) obj;
    if (this.codigoNivelAdministracion != other.codigoNivelAdministracion && (this.codigoNivelAdministracion == null || !this.codigoNivelAdministracion.equals(other.codigoNivelAdministracion))) {
      return false;
    }
    if ((this.descripcionNivelAdministracion == null) ? (other.descripcionNivelAdministracion != null) : !this.descripcionNivelAdministracion.equals(other.descripcionNivelAdministracion)) {
      return false;
    }
    return true;
  }

 

 
  
 

}