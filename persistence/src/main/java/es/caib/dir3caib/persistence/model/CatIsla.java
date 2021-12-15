package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;


/**
 * @version 1.1
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATISLA", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATISLA", indexes = {
        @Index(name="DIR_CATISLA_CATPROV_FK_I", columnNames = "PROVINCIA"),
        @Index(name="DIR_CATISLA_CESTENT_FK_I", columnNames = "ESTADO")
})
public class CatIsla implements Serializable {

  private Long codigoIsla;
  private String descripcionIsla;
  private CatProvincia provincia;
  private CatEstadoEntidad estado;

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


  @ManyToOne(cascade= {CascadeType.PERSIST}, fetch=FetchType.LAZY)
  @JoinColumn(name="PROVINCIA")
  @ForeignKey(name="DIR_CATISLA_CATPROV_FK")
  public CatProvincia getProvincia() {
    return provincia;
  }

  public void setProvincia(CatProvincia provincia) {
    this.provincia = provincia;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CATISLA_CESTENT_FK")
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
    CatIsla catIsla = (CatIsla) o;
    return codigoIsla.equals(catIsla.codigoIsla) && descripcionIsla.equals(catIsla.descripcionIsla) && provincia.equals(catIsla.provincia) && estado.equals(catIsla.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoIsla, descripcionIsla, provincia, estado);
  }


}