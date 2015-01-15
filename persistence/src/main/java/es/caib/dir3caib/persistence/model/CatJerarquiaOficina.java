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
@Table(name = "DIR_CATJERARQUIAOFICINA", schema = "", catalog = "")
@Entity
public class CatJerarquiaOficina implements Serializable {

	private Long codigoJerarquiaOficina;
	private String descripcionJerarquiaOficina;

	public CatJerarquiaOficina(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoJerarquiaOficina
   */
  @Column(name = "CODIGOJERARQUIAOFICINA", nullable = false, length = 2)
  @Id
  public Long getCodigoJerarquiaOficina() {
    return codigoJerarquiaOficina;
  }

  /**
   * @param codigoJerarquiaOficina the codigoJerarquiaOficina to set
   */
  public void setCodigoJerarquiaOficina(Long codigoJerarquiaOficina) {
    this.codigoJerarquiaOficina = codigoJerarquiaOficina;
  }

  /**
   * @return the descripcionJerarquiaOficina
   */
  @Column(name = "DESCRIPCIONJERARQUIAOFICINA", nullable = false, length = 50)
  public String getDescripcionJerarquiaOficina() {
    return descripcionJerarquiaOficina;
  }

  /**
   * @param descripcionJerarquiaOficina the descripcionJerarquiaOficina to set
   */
  public void setDescripcionJerarquiaOficina(String descripcionJerarquiaOficina) {
    this.descripcionJerarquiaOficina = descripcionJerarquiaOficina;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + (this.codigoJerarquiaOficina != null ? this.codigoJerarquiaOficina.hashCode() : 0);
    hash = 37 * hash + (this.descripcionJerarquiaOficina != null ? this.descripcionJerarquiaOficina.hashCode() : 0);
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
    final CatJerarquiaOficina other = (CatJerarquiaOficina) obj;
    if (this.codigoJerarquiaOficina != other.codigoJerarquiaOficina && (this.codigoJerarquiaOficina == null || !this.codigoJerarquiaOficina.equals(other.codigoJerarquiaOficina))) {
      return false;
    }
    if ((this.descripcionJerarquiaOficina == null) ? (other.descripcionJerarquiaOficina != null) : !this.descripcionJerarquiaOficina.equals(other.descripcionJerarquiaOficina)) {
      return false;
    }
    return true;
  }
  
  
  
  

}