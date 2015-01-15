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
@Table(name = "DIR_CATPAIS", schema = "", catalog = "")
@Entity
public class CatPais implements Serializable {

	private Long codigoPais;
	private String descripcionPais;
	private String alfa3Pais;
	private String alfa2Pais;

	public CatPais(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoPais
   */
  @Column(name = "CODIGOPAIS", nullable = false, length = 3)
  @Id
  public Long getCodigoPais() {
    return codigoPais;
  }

  /**
   * @param codigoPais the codigoPais to set
   */
  public void setCodigoPais(Long codigoPais) {
    this.codigoPais = codigoPais;
  }

  /**
   * @return the descripcionPais
   */
  @Column(name = "DESCRIPCIONPAIS", nullable = false, length = 50)
  public String getDescripcionPais() {
    return descripcionPais;
  }

  /**
   * @param descripcionPais the descripcionPais to set
   */
  public void setDescripcionPais(String descripcionPais) {
    this.descripcionPais = descripcionPais;
  }

  /**
   * @return the alfa3Pais
   */
  @Column(name = "ALFA3PAIS",  length = 3)
  public String getAlfa3Pais() {
    return alfa3Pais;
  }

  /**
   * @param alfa3Pais the alfa3Pais to set
   */
  public void setAlfa3Pais(String alfa3Pais) {
    this.alfa3Pais = alfa3Pais;
  }

  /**
   * @return the alfa2Pais
   */
  @Column(name = "ALFA2PAIS", length = 2)
  public String getAlfa2Pais() {
    return alfa2Pais;
  }

  /**
   * @param alfa2Pais the alfa2Pais to set
   */
  public void setAlfa2Pais(String alfa2Pais) {
    this.alfa2Pais = alfa2Pais;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 61 * hash + (this.codigoPais != null ? this.codigoPais.hashCode() : 0);
    hash = 61 * hash + (this.descripcionPais != null ? this.descripcionPais.hashCode() : 0);
    hash = 61 * hash + (this.alfa3Pais != null ? this.alfa3Pais.hashCode() : 0);
    hash = 61 * hash + (this.alfa2Pais != null ? this.alfa2Pais.hashCode() : 0);
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
    final CatPais other = (CatPais) obj;
    if (this.codigoPais != other.codigoPais && (this.codigoPais == null || !this.codigoPais.equals(other.codigoPais))) {
      return false;
    }
    if ((this.descripcionPais == null) ? (other.descripcionPais != null) : !this.descripcionPais.equals(other.descripcionPais)) {
      return false;
    }
    if ((this.alfa3Pais == null) ? (other.alfa3Pais != null) : !this.alfa3Pais.equals(other.alfa3Pais)) {
      return false;
    }
    if ((this.alfa2Pais == null) ? (other.alfa2Pais != null) : !this.alfa2Pais.equals(other.alfa2Pais)) {
      return false;
    }
    return true;
  }
  
  

}