package es.caib.dir3caib.persistence.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;


/**
 * @version 1.1
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATPROVINCIA", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATPROVINCIA", indexes = {
    @Index(name="DIR_CATPROV_CATCOMUNAUT_FK_I", columnNames = {"COMUNIDADAUTONOMA"}),
    @Index(name="DIR_CPROVIN_CESTENT_FK_I", columnNames = "ESTADO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CPROV_SEQ", allocationSize=1)
public class CatProvincia implements Serializable {

  private Long codigoProvincia;
  private CatComunidadAutonoma comunidadAutonoma;
  private String descripcionProvincia;
  private CatEstadoEntidad estado;

  public CatProvincia(){

  }

  public void finalize() throws Throwable {

  }

  /**
   * @return the codigoProvincia
   */
  @Id
  @Index(name="DIR_CATPROVINCIA_PK_I")
  @Column(name = "CODIGOPROVINCIA", nullable = false, length = 2)
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
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

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CPROVIN_CESTENT_FK")
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
    CatProvincia that = (CatProvincia) o;
    return codigoProvincia.equals(that.codigoProvincia) && comunidadAutonoma.equals(that.comunidadAutonoma)
            && descripcionProvincia.equals(that.descripcionProvincia) && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoProvincia, comunidadAutonoma, descripcionProvincia, estado);
  }

}