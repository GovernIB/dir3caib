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
@Table(name = "DIR_CATTIPOUNIDADORGANICA", schema = "", catalog = "")
@Entity
public class CatTipoUnidadOrganica implements Serializable {

	private String codigoTipoUnidadOrganica;
	private String descripcionTipoUnidadOrganica;

	public CatTipoUnidadOrganica(){

	}

	public void finalize() throws Throwable {

	}

  
  /**
   * @return the codigoTipoUnidadOrganica
   */
  @Column(name = "CODIGOTIPOUNIDADORGANICA", nullable = false, length = 3)
  @Id
  public String getCodigoTipoUnidadOrganica() {
    return codigoTipoUnidadOrganica;
  }

  /**
   * @param codigoTipoUnidadOrganica the codigoTipoUnidadOrganica to set
   */
  public void setCodigoTipoUnidadOrganica(String codigoTipoUnidadOrganica) {
    this.codigoTipoUnidadOrganica = codigoTipoUnidadOrganica;
  }

  /**
   * @return the descripcionTipoUnidadOrganica
   */
  @Column(name = "DESCRIPCIONTIPOUNIDADORGANICA", nullable = false, length = 100)
  public String getDescripcionTipoUnidadOrganica() {
    return descripcionTipoUnidadOrganica;
  }

  /**
   * @param descripcionTipoUnidadOrganica the descripcionTipoUnidadOrganica to set
   */
  public void setDescripcionTipoUnidadOrganica(String descripcionTipoUnidadOrganica) {
    this.descripcionTipoUnidadOrganica = descripcionTipoUnidadOrganica;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 37 * hash + (this.codigoTipoUnidadOrganica != null ? this.codigoTipoUnidadOrganica.hashCode() : 0);
    hash = 37 * hash + (this.descripcionTipoUnidadOrganica != null ? this.descripcionTipoUnidadOrganica.hashCode() : 0);
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
    final CatTipoUnidadOrganica other = (CatTipoUnidadOrganica) obj;
    if ((this.codigoTipoUnidadOrganica == null) ? (other.codigoTipoUnidadOrganica != null) : !this.codigoTipoUnidadOrganica.equals(other.codigoTipoUnidadOrganica)) {
      return false;
    }
    if ((this.descripcionTipoUnidadOrganica == null) ? (other.descripcionTipoUnidadOrganica != null) : !this.descripcionTipoUnidadOrganica.equals(other.descripcionTipoUnidadOrganica)) {
      return false;
    }
    return true;
  }
  

  

  

}