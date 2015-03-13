package es.caib.dir3caib.persistence.model;
import es.caib.dir3caib.persistence.model.CatProvincia;
import es.caib.dir3caib.persistence.model.CatEntidadGeografica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */

@Table(name = "DIR_CATLOCALIDAD", 
  uniqueConstraints= @UniqueConstraint(columnNames={"CODIGOLOCALIDAD", "PROVINCIA", "ENTIDADGEOGRAFICA"}))
@org.hibernate.annotations.Table(appliesTo="DIR_CATLOCALIDAD", 
    indexes = {
    @Index(name="DIR_CATLOCAL_CATPROVIN_FK_I", columnNames="PROVINCIA"),
    @Index(name="DIR_CATLOCAL_CATENTGEOGR_FK_I", columnNames="ENTIDADGEOGRAFICA")
 })
@Entity
@SequenceGenerator(name="generator",sequenceName = "DIR_SEQ_ALL", allocationSize=1)
public class CatLocalidad implements Serializable {

  private Long id;
	private Long codigoLocalidad;
	private CatProvincia provincia;
	private String descripcionLocalidad;
	private CatEntidadGeografica entidadGeografica;

	public CatLocalidad(){

	}


	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  /**
   * @return the codigoLocalidad
   */

  @Column(name = "CODIGOLOCALIDAD", nullable = false, length = 4)  
  public Long getCodigoLocalidad() {
    return codigoLocalidad;
  }

  /**
   * @param codigoLocalidad the codigoLocalidad to set
   */
  public void setCodigoLocalidad(Long codigoLocalidad) {
    this.codigoLocalidad = codigoLocalidad;
  }

  /**
   * @return the provincia
   */

  @ManyToOne
  @JoinColumn (name="PROVINCIA")
  @ForeignKey(name="DIR_CATLOCAL_CATPROVIN_FK")
  public CatProvincia getProvincia() {
    return provincia;
  }

  /**
   * @param provincia the provincia to set
   */
  public void setProvincia(CatProvincia provincia) {
    this.provincia = provincia;
  }

  /**
   * @return the descripcionLocalidad
   */
  @Column(name = "DESCRIPCIONLOCALIDAD", length = 50)
  public String getDescripcionLocalidad() {
    return descripcionLocalidad;
  }

  /**
   * @param descripcionLocalidad the descripcionLocalidad to set
   */
  public void setDescripcionLocalidad(String descripcionLocalidad) {
    this.descripcionLocalidad = descripcionLocalidad;
  }

  /**
   * @return the entidadGeografica
   */
  @ManyToOne
  @JoinColumn (name="ENTIDADGEOGRAFICA")
  @ForeignKey (name="DIR_CATLOCAL_CATENTGEOGR_FK")
  public CatEntidadGeografica getEntidadGeografica() {
    return entidadGeografica;
  }

  /**
   * @param entidadGeografica the entidadGeografica to set
   */
  public void setEntidadGeografica(CatEntidadGeografica entidadGeografica) {
    this.entidadGeografica = entidadGeografica;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 67 * hash + (this.codigoLocalidad != null ? this.codigoLocalidad.hashCode() : 0);
    hash = 67 * hash + (this.provincia != null ? this.provincia.hashCode() : 0);
    hash = 67 * hash + (this.descripcionLocalidad != null ? this.descripcionLocalidad.hashCode() : 0);
    hash = 67 * hash + (this.entidadGeografica != null ? this.entidadGeografica.hashCode() : 0);
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
    final CatLocalidad other = (CatLocalidad) obj;
    if (this.codigoLocalidad != other.codigoLocalidad && (this.codigoLocalidad == null || !this.codigoLocalidad.equals(other.codigoLocalidad))) {
      return false;
    }
    if (this.provincia != other.provincia && (this.provincia == null || !this.provincia.equals(other.provincia))) {
      return false;
    }
    if ((this.descripcionLocalidad == null) ? (other.descripcionLocalidad != null) : !this.descripcionLocalidad.equals(other.descripcionLocalidad)) {
      return false;
    }
    if (this.entidadGeografica != other.entidadGeografica && (this.entidadGeografica == null || !this.entidadGeografica.equals(other.entidadGeografica))) {
      return false;
    }
    return true;
  }
  
  

}