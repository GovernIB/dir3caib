package es.caib.dir3caib.persistence.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
@Table(name = "DIR_CATPROVINCIA", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATPROVINCIA", indexes = {
    @Index(name="DIR_CATPROV_CATCOMUNAUT_FK_I", columnNames = {"COMUNIDADAUTONOMA"})
})
@Entity
public class CatProvincia implements Serializable {

	private Long codigoProvincia;
	private CatComunidadAutonoma comunidadAutonoma;
	private String descripcionProvincia;

	public CatProvincia(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoProvincia
   */
	@Id
	@Index(name="RWE_CATPROVINCIA_PK_I")
  @Column(name = "CODIGOPROVINCIA", nullable = false, length = 2)
  public Long getCodigoProvincia() {
    return codigoProvincia;
  }

  /**
   * @param codigoProvincia the codigoProvincia to set
   */
  public void setCodigoProvincia(Long codigoProvincia) {
    this.codigoProvincia = codigoProvincia;
  }

  /**
   * @return the descripcionProvincia
   */
  @Column(name = "DESCRIPCIONPROVINCIA", nullable = false, length = 50)
  public String getDescripcionProvincia() {
    return descripcionProvincia;
  }

  /**
   * @param descripcionProvincia the descripcionProvincia to set
   */
  public void setDescripcionProvincia(String descripcionProvincia) {
    this.descripcionProvincia = descripcionProvincia;
  }

  /**
   * @return the codigoComunidad
   */
  @ManyToOne
  @JoinColumn(name="COMUNIDADAUTONOMA")
  @ForeignKey(name="DIR_CATPROVINC_CATCOMUNAUTO_FK")
  public CatComunidadAutonoma getComunidadAutonoma() {
    return comunidadAutonoma;
  }

  /**
   * @param comunidadAutonoma the codigoComunidad to set
   */
  public void setComunidadAutonoma(CatComunidadAutonoma comunidadAutonoma) {
    this.comunidadAutonoma = comunidadAutonoma;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 59 * hash + (this.codigoProvincia != null ? this.codigoProvincia.hashCode() : 0);
    hash = 59 * hash + (this.comunidadAutonoma != null ? this.comunidadAutonoma.hashCode() : 0);
    hash = 59 * hash + (this.descripcionProvincia != null ? this.descripcionProvincia.hashCode() : 0);
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
    final CatProvincia other = (CatProvincia) obj;
    if (this.codigoProvincia != other.codigoProvincia && (this.codigoProvincia == null || !this.codigoProvincia.equals(other.codigoProvincia))) {
      return false;
    }
    if (this.comunidadAutonoma != other.comunidadAutonoma && (this.comunidadAutonoma == null || !this.comunidadAutonoma.equals(other.comunidadAutonoma))) {
      return false;
    }
    if ((this.descripcionProvincia == null) ? (other.descripcionProvincia != null) : !this.descripcionProvincia.equals(other.descripcionProvincia)) {
      return false;
    }
    return true;
  }
  
  

}