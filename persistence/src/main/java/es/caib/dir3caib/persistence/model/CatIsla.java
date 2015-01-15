package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;

import java.io.Serializable;
import javax.persistence.*;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
@Table(name = "DIR_CATISLA", schema = "", catalog = "")
@Entity
public class CatIsla implements Serializable {

	private Long codigoIsla;
	private String descripcionIsla;
  private CatProvincia provincia;

	public CatIsla(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoIsla
   */
  @Column(name = "CODIGOISLA", nullable = false, length = 2)
  @Id
  public Long getCodigoIsla() {
    return codigoIsla;
  }

  /**
   * @param codigoIsla the codigoIsla to set
   */
  public void setCodigoIsla(Long codigoIsla) {
    this.codigoIsla = codigoIsla;
  }

  /**
   * @return the descripcionIsla
   */
  @Column(name = "DESCRIPCIONISLA", nullable = false, length = 50)
  public String getDescripcionIsla() {
    return descripcionIsla;
  }

  /**
   * @param descripcionIsla the descripcionIsla to set
   */
  public void setDescripcionIsla(String descripcionIsla) {
    this.descripcionIsla = descripcionIsla;
  }


  @ManyToOne
  @JoinColumn(name="PROVINCIA")
  @ForeignKey(name="DIR_CATISLA_CATPROV_FK")
  public CatProvincia getProvincia() {
    return provincia;
  }

  public void setProvincia(CatProvincia provincia) {
    this.provincia = provincia;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 59 * hash + (this.codigoIsla != null ? this.codigoIsla.hashCode() : 0);
    hash = 59 * hash + (this.descripcionIsla != null ? this.descripcionIsla.hashCode() : 0);
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
    final CatIsla other = (CatIsla) obj;
    if (this.codigoIsla != other.codigoIsla && (this.codigoIsla == null || !this.codigoIsla.equals(other.codigoIsla))) {
      return false;
    }
    if ((this.descripcionIsla == null) ? (other.descripcionIsla != null) : !this.descripcionIsla.equals(other.descripcionIsla)) {
      return false;
    }
    return true;
  }
  
  

}