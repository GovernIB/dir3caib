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
@Table(name = "DIR_CATTIPOVIA", schema = "", catalog = "")
@Entity
public class CatTipoVia implements Serializable {

	private Long codigoTipoVia;
	private String descripcionTipoVia;


	public CatTipoVia(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoTipoVia
   */
  @Column(name = "CODIGOTIPOVIA", nullable = false, length = 2)
  @Id
  public Long getCodigoTipoVia() {
    return codigoTipoVia;
  }

  /**
   * @param codigoTipoVia the codigoTipoVia to set
   */
  public void setCodigoTipoVia(Long codigoTipoVia) {
    this.codigoTipoVia = codigoTipoVia;
  }

  /**
   * @return the descripcionTipoVia
   */
   @Column(name = "DESCRIPCIONTIPOVIA", nullable = false, length = 50)
  public String getDescripcionTipoVia() {
    return descripcionTipoVia;
  }

  /**
   * @param descripcionTipoVia the descripcionTipoVia to set
   */
  public void setDescripcionTipoVia(String descripcionTipoVia) {
    this.descripcionTipoVia = descripcionTipoVia;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 67 * hash + (this.codigoTipoVia != null ? this.codigoTipoVia.hashCode() : 0);
    hash = 67 * hash + (this.descripcionTipoVia != null ? this.descripcionTipoVia.hashCode() : 0);
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
    final CatTipoVia other = (CatTipoVia) obj;
    if (this.codigoTipoVia != other.codigoTipoVia && (this.codigoTipoVia == null || !this.codigoTipoVia.equals(other.codigoTipoVia))) {
      return false;
    }
    if ((this.descripcionTipoVia == null) ? (other.descripcionTipoVia != null) : !this.descripcionTipoVia.equals(other.descripcionTipoVia)) {
      return false;
    }
    return true;
  }

  
  
  

}