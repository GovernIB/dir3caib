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
@Table(name = "DIR_CATTIPOENTIDADPUBLICA", schema = "", catalog = "")
@Entity
public class CatTipoEntidadPublica implements Serializable {

	private String codigoTipoEntidadPublica;
	private String descripcionTipoEntidadPublica;

	public CatTipoEntidadPublica(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoTipoEntidadPublica
   */
  @Column(name = "CODIGOTIPOENTIDADPUBLICA", nullable = false, length = 2)
  @Id
  public String getCodigoTipoEntidadPublica() {
    return codigoTipoEntidadPublica;
  }

  /**
   * @param codigoTipoEntidadPublica the codigoTipoEntidadPublica to set
   */
  public void setCodigoTipoEntidadPublica(String codigoTipoEntidadPublica) {
    this.codigoTipoEntidadPublica = codigoTipoEntidadPublica;
  }

  /**
   * @return the descripcionTipoEntidadPublica
   */
  @Column(name = "DESCRIPCIONTIPOENTIDADPUBLICA", nullable = false, length = 100)
  public String getDescripcionTipoEntidadPublica() {
    return descripcionTipoEntidadPublica;
  }

  /**
   * @param descripcionTipoEntidadPublica the descripcionTipoEntidadPublica to set
   */
  public void setDescripcionTipoEntidadPublica(String descripcionTipoEntidadPublica) {
    this.descripcionTipoEntidadPublica = descripcionTipoEntidadPublica;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 61 * hash + (this.codigoTipoEntidadPublica != null ? this.codigoTipoEntidadPublica.hashCode() : 0);
    hash = 61 * hash + (this.descripcionTipoEntidadPublica != null ? this.descripcionTipoEntidadPublica.hashCode() : 0);
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
    final CatTipoEntidadPublica other = (CatTipoEntidadPublica) obj;
    if ((this.codigoTipoEntidadPublica == null) ? (other.codigoTipoEntidadPublica != null) : !this.codigoTipoEntidadPublica.equals(other.codigoTipoEntidadPublica)) {
      return false;
    }
    if ((this.descripcionTipoEntidadPublica == null) ? (other.descripcionTipoEntidadPublica != null) : !this.descripcionTipoEntidadPublica.equals(other.descripcionTipoEntidadPublica)) {
      return false;
    }
    return true;
  }
  
  

}