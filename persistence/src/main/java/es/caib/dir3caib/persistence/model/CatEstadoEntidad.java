package es.caib.dir3caib.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
@Table(name = "DIR_CATESTADOENTIDAD", schema = "", catalog = "")
@Entity
public class CatEstadoEntidad implements Serializable {

	private String codigoEstadoEntidad;
	private String descripcionEstadoEntidad;

	public CatEstadoEntidad(){

	}

  public CatEstadoEntidad(String codigoEstadoEntidad) {
    this.codigoEstadoEntidad = codigoEstadoEntidad;
  }

  public void finalize() throws Throwable {

	}

  /**
   * @return the codigoEstadoEntidad
   */
  @Column(name = "CODIGOESTADOENTIDAD", nullable = false, length = 2)
  @Id
  public String getCodigoEstadoEntidad() {
    return codigoEstadoEntidad;
  }

  /**
   * @param codigoEstadoEntidad the codigoEstadoEntidad to set
   */
  public void setCodigoEstadoEntidad(String codigoEstadoEntidad) {
    this.codigoEstadoEntidad = codigoEstadoEntidad;
  }

  /**
   * @return the descripcionEstadoEntidad
   */
  @Column(name = "DESCRIPCIONESTADOENTIDAD", nullable = false, length = 50)
  public String getDescripcionEstadoEntidad() {
    return descripcionEstadoEntidad;
  }

  /**
   * @param descripcionEstadoEntidad the descripcionEstadoEntidad to set
   */
  public void setDescripcionEstadoEntidad(String descripcionEstadoEntidad) {
    this.descripcionEstadoEntidad = descripcionEstadoEntidad;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 89 * hash + (this.codigoEstadoEntidad != null ? this.codigoEstadoEntidad.hashCode() : 0);
    hash = 89 * hash + (this.descripcionEstadoEntidad != null ? this.descripcionEstadoEntidad.hashCode() : 0);
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
    final CatEstadoEntidad other = (CatEstadoEntidad) obj;
    if ((this.codigoEstadoEntidad == null) ? (other.codigoEstadoEntidad != null) : !this.codigoEstadoEntidad.equals(other.codigoEstadoEntidad)) {
      return false;
    }
    if ((this.descripcionEstadoEntidad == null) ? (other.descripcionEstadoEntidad != null) : !this.descripcionEstadoEntidad.equals(other.descripcionEstadoEntidad)) {
      return false;
    }
    return true;
  }

}