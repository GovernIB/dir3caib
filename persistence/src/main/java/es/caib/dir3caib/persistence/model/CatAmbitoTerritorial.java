package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
@Entity
@IdClass(es.caib.dir3caib.persistence.model.CatAmbitoTerritorialPK.class)
@Table(name = "DIR_CATAMBITOTERRITORIAL")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATAMBITOTERRITORIAL", indexes = {
    @Index(name="DIR_CATAMBITOTERRITORIAL_PK_I", columnNames = {"CODIGOAMBITO", "NIVELADMINISTRACION" })
    ,@Index(name="DIR_CATAMBTERR_CATNIVADM_FK_I", columnNames = "NIVELADMINISTRACION")
 })
public class CatAmbitoTerritorial implements Serializable {

    @XmlAttribute(name = "id")
    private String codigoAmbito;
    @XmlAttribute(name = "nombre")
    private String descripcionAmbito;
    @XmlTransient
    private CatNivelAdministracion nivelAdministracion;
  

	public CatAmbitoTerritorial(){

	}
  
  

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoAmbito
   */
  @Id
  @Column(name = "CODIGOAMBITO", nullable = false, length = 2)
  public String getCodigoAmbito() {
    return codigoAmbito;
  }

  /**
   * @param codigoAmbito the codigoAmbito to set
   */
  public void setCodigoAmbito(String codigoAmbito) {
    this.codigoAmbito = codigoAmbito;
  }

  /**
   * @return the descripcionAmbito
   */
  @Column(name = "DESCRIPCIONAMBITO", nullable = false, length = 30)
  public String getDescripcionAmbito() {
    return descripcionAmbito;
  }

  /**
   * @param descripcionAmbito the descripcionAmbito to set
   */
  public void setDescripcionAmbito(String descripcionAmbito) {
    this.descripcionAmbito = descripcionAmbito;
  }

  /**
   * @return the nivelAdministracion
   */
  @Id
  //@Index(name="DIR_CATAMBTERR_CATNIVADM_FK_I", columnNames="NIVELADMINISTRACION")
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn (name="NIVELADMINISTRACION")
  @ForeignKey(name="DIR_CATAMBTERR_CATNIVADM_FK")
  public CatNivelAdministracion getNivelAdministracion() {
    return nivelAdministracion;
  }

  /**
   * @param nivelAdministracion the nivelAdministracion to set
   */
  public void setNivelAdministracion(CatNivelAdministracion nivelAdministracion) {
    this.nivelAdministracion = nivelAdministracion;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 83 * hash + (this.codigoAmbito != null ? this.codigoAmbito.hashCode() : 0);
    hash = 83 * hash + (this.descripcionAmbito != null ? this.descripcionAmbito.hashCode() : 0);
    hash = 83 * hash + (this.nivelAdministracion != null ? this.nivelAdministracion.hashCode() : 0);
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
    final CatAmbitoTerritorial other = (CatAmbitoTerritorial) obj;
    if ((this.codigoAmbito == null) ? (other.codigoAmbito != null) : !this.codigoAmbito.equals(other.codigoAmbito)) {
      return false;
    }
    if ((this.descripcionAmbito == null) ? (other.descripcionAmbito != null) : !this.descripcionAmbito.equals(other.descripcionAmbito)) {
      return false;
    }
    if (this.nivelAdministracion != other.nivelAdministracion && (this.nivelAdministracion == null || !this.nivelAdministracion.equals(other.nivelAdministracion))) {
      return false;
    }
    return true;
  }

 

  

}