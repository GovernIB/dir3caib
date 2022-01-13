package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;


/**
 * @version 1.1
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATAMBITOTERRITORIAL",
  uniqueConstraints= @UniqueConstraint(columnNames={"CODIGOAMBITO", "NIVELADMINISTRACION"}))
@org.hibernate.annotations.Table(appliesTo = "DIR_CATAMBITOTERRITORIAL", indexes = {
    @Index(name="DIR_CATAMBITOTERRITORIAL_PK_I", columnNames = {"CODIGOAMBITO", "NIVELADMINISTRACION" }),
    @Index(name="DIR_CAMBTER_CATNIVADM_FK_I", columnNames = "NIVELADMINISTRACION"),
    @Index(name="DIR_CAMBTER_CESTENT_FK_I", columnNames = "ESTADO")
 })
@SequenceGenerator(name="generator",sequenceName = "DIR_CAMBTER_SEQ", allocationSize=1)
public class CatAmbitoTerritorial implements Serializable {

  @XmlTransient
  private Long id;

  @XmlAttribute(name = "id")
  private String codigoAmbito;
  @XmlAttribute(name = "nombre")
  private String descripcionAmbito;
  @XmlTransient
  private CatNivelAdministracion nivelAdministracion;
  private CatEstadoEntidad estado;


  public CatAmbitoTerritorial(){

  }



  public void finalize() throws Throwable {

  }

  @Id
  @Column (name = "ID")
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public Long getId() {
    return id;
  }



  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the codigoAmbito
   */

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

  //@Index(name="DIR_CATAMBTERR_CATNIVADM_FK_I", columnNames="NIVELADMINISTRACION")
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn (name="NIVELADMINISTRACION")
  @ForeignKey(name="DIR_CAMBTER_CATNIVADM_FK")
  public CatNivelAdministracion getNivelAdministracion() {
    return nivelAdministracion;
  }

  /**
   * @param nivelAdministracion the nivelAdministracion to set
   */
  public void setNivelAdministracion(CatNivelAdministracion nivelAdministracion) {
    this.nivelAdministracion = nivelAdministracion;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CAMBTER_CESTENT_FK")
  public CatEstadoEntidad getEstado() {
    return estado;
  }

  public void setEstado(CatEstadoEntidad estado) {
    this.estado = estado;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CatAmbitoTerritorial that = (CatAmbitoTerritorial) o;
    return codigoAmbito.equals(that.codigoAmbito) && descripcionAmbito.equals(that.descripcionAmbito)
            && nivelAdministracion.equals(that.nivelAdministracion)
            && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoAmbito, descripcionAmbito, nivelAdministracion, estado);
  }
}