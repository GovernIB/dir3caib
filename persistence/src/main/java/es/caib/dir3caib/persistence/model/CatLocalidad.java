package es.caib.dir3caib.persistence.model;
import es.caib.dir3caib.persistence.model.CatProvincia;
import es.caib.dir3caib.persistence.model.CatEntidadGeografica;

import java.io.Serializable;
import java.util.Objects;

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
 * @version 1.1
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATLOCALIDAD", 
  uniqueConstraints= @UniqueConstraint(columnNames={"CODIGOLOCALIDAD", "PROVINCIA", "ENTIDADGEOGRAFICA"}))
@org.hibernate.annotations.Table(appliesTo="DIR_CATLOCALIDAD", 
    indexes = {
    @Index(name="DIR_CATLOCAL_CATPROVIN_FK_I", columnNames="PROVINCIA"),
    @Index(name="DIR_CATLOCAL_CATENTGEOGR_FK_I", columnNames="ENTIDADGEOGRAFICA"),
    @Index(name="DIR_CLOCAL_CESTENT_FK_I", columnNames = "ESTADO")
 })
@SequenceGenerator(name="generator",sequenceName = "DIR_CLOCA_SEQ", allocationSize=1)
public class CatLocalidad implements Serializable {

  private Long id;
  private Long codigoLocalidad;
  private CatProvincia provincia;
  private String descripcionLocalidad;
  private CatEntidadGeografica entidadGeografica;
  private CatEstadoEntidad estado;

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

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CLOCAL_CESTENT_FK")
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
    CatLocalidad that = (CatLocalidad) o;
    return codigoLocalidad.equals(that.codigoLocalidad) && provincia.equals(that.provincia) && descripcionLocalidad.equals(that.descripcionLocalidad) && entidadGeografica.equals(that.entidadGeografica) && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoLocalidad, provincia, descripcionLocalidad, entidadGeografica, estado);
  }

}