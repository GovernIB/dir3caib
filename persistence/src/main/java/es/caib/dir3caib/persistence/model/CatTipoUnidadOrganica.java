package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATTIPOUNIDADORGANICA", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATTIPOUNIDADORGANICA", indexes = {
        @Index(name="DIR_CTIUNOR_CESTENT_FK_I", columnNames = "ESTADO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CTUNIORG_SEQ", allocationSize=1)
public class CatTipoUnidadOrganica implements Serializable {

  private String codigoTipoUnidadOrganica;
  private String descripcionTipoUnidadOrganica;
  private CatEstadoEntidad estado;

  public CatTipoUnidadOrganica(){

  }

  public void finalize() throws Throwable {

  }

  
  /**
   * @return the codigoTipoUnidadOrganica
   */
  @Column(name = "CODIGOTIPOUNIDADORGANICA", nullable = false, length = 3)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public String getCodigoTipoUnidadOrganica() {
    return codigoTipoUnidadOrganica;
  }

  /**
   * @param codigoTipoUnidadOrganica the codigoTipoUnidadOrganica to set
   */
  public void setCodigoTipoUnidadOrganica(String codigoTipoUnidadOrganica) {
    this.codigoTipoUnidadOrganica = codigoTipoUnidadOrganica;
  }

  /**
   * @return the descripcionTipoUnidadOrganica
   */
  @Column(name = "DESCRIPCIONTIPOUNIDADORGANICA", nullable = false, length = 100)
  public String getDescripcionTipoUnidadOrganica() {
    return descripcionTipoUnidadOrganica;
  }

  /**
   * @param descripcionTipoUnidadOrganica the descripcionTipoUnidadOrganica to set
   */
  public void setDescripcionTipoUnidadOrganica(String descripcionTipoUnidadOrganica) {
    this.descripcionTipoUnidadOrganica = descripcionTipoUnidadOrganica;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CTIUNOR_CESTENT_FK")
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
    CatTipoUnidadOrganica that = (CatTipoUnidadOrganica) o;
    return codigoTipoUnidadOrganica.equals(that.codigoTipoUnidadOrganica) && descripcionTipoUnidadOrganica.equals(that.descripcionTipoUnidadOrganica)
            && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoTipoUnidadOrganica, descripcionTipoUnidadOrganica, estado);
  }
}