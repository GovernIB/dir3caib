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
@Table(name = "DIR_CATENTIDADGEOGRAFICA", schema = "", catalog = "")
@Entity
public class CatEntidadGeografica implements Serializable {

	private String codigoEntidadGeografica;
	private String descripcionEntidadGeografica;

	public CatEntidadGeografica(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoEntidadGeografica
   */
  @Column(name = "CODIGOENTIDADGEOGRAFICA", nullable = false, length = 2)
  @Id
  public String getCodigoEntidadGeografica() {
    return codigoEntidadGeografica;
  }

  /**
   * @param codigoEntidadGeografica the codigoEntidadGeografica to set
   */
  public void setCodigoEntidadGeografica(String codigoEntidadGeografica) {
    this.codigoEntidadGeografica = codigoEntidadGeografica;
  }

  /**
   * @return the descripcionEntidadGeografica
   */
  @Column(name = "DESCRIPCIONENTIDADGEOGRAFICA", nullable = false, length = 50)
  public String getDescripcionEntidadGeografica() {
    return descripcionEntidadGeografica;
  }

  /**
   * @param descripcionEntidadGeografica the descripcionEntidadGeografica to set
   */
  public void setDescripcionEntidadGeografica(String descripcionEntidadGeografica) {
    this.descripcionEntidadGeografica = descripcionEntidadGeografica;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 17 * hash + (this.codigoEntidadGeografica != null ? this.codigoEntidadGeografica.hashCode() : 0);
    hash = 17 * hash + (this.descripcionEntidadGeografica != null ? this.descripcionEntidadGeografica.hashCode() : 0);
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
    final CatEntidadGeografica other = (CatEntidadGeografica) obj;
    if (this.codigoEntidadGeografica != other.codigoEntidadGeografica && (this.codigoEntidadGeografica == null || !this.codigoEntidadGeografica.equals(other.codigoEntidadGeografica))) {
      return false;
    }
    if ((this.descripcionEntidadGeografica == null) ? (other.descripcionEntidadGeografica != null) : !this.descripcionEntidadGeografica.equals(other.descripcionEntidadGeografica)) {
      return false;
    }
    return true;
  }
  
  

}