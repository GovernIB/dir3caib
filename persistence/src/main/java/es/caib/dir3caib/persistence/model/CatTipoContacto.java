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
@Table(name = "DIR_CATTIPOCONTACTO", schema = "", catalog = "")
@Entity
public class CatTipoContacto implements Serializable {

	private String codigoTipoContacto;
	private String descripcionTipoContacto;

	public CatTipoContacto(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoTipoContacto
   */
  @Column(name = "CODIGOTIPOCONTACTO", nullable = false, length = 2)
  @Id
  public String getCodigoTipoContacto() {
    return codigoTipoContacto;
  }

  /**
   * @param codigoTipoContacto the codigoTipoContacto to set
   */
  public void setCodigoTipoContacto(String codigoTipoContacto) {
    this.codigoTipoContacto = codigoTipoContacto;
  }

  /**
   * @return the descripcionTipoContacto
   */
  @Column(name = "DESCRIPCIONTIPOCONTACTO", nullable = false, length = 30)
  public String getDescripcionTipoContacto() {
    return descripcionTipoContacto;
  }

  /**
   * @param descripcionTipoContacto the descripcionTipoContacto to set
   */
  public void setDescripcionTipoContacto(String descripcionTipoContacto) {
    this.descripcionTipoContacto = descripcionTipoContacto;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 17 * hash + (this.codigoTipoContacto != null ? this.codigoTipoContacto.hashCode() : 0);
    hash = 17 * hash + (this.descripcionTipoContacto != null ? this.descripcionTipoContacto.hashCode() : 0);
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
    final CatTipoContacto other = (CatTipoContacto) obj;
    if ((this.codigoTipoContacto == null) ? (other.codigoTipoContacto != null) : !this.codigoTipoContacto.equals(other.codigoTipoContacto)) {
      return false;
    }
    if ((this.descripcionTipoContacto == null) ? (other.descripcionTipoContacto != null) : !this.descripcionTipoContacto.equals(other.descripcionTipoContacto)) {
      return false;
    }
    return true;
  }
  
  

}