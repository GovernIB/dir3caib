package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * @version 1.1
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATTIPOVIA")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATTIPOVIA", indexes = {
        @Index(name="DIR_CTIPVIA_CESTENT_FK_I", columnNames = "ESTADO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CTIPVIA_SEQ", allocationSize=1)
public class CatTipoVia implements Serializable {

  private Long codigoTipoVia;
  private String descripcionTipoVia;
  private CatEstadoEntidad estado;


  public CatTipoVia(){

  }

  public void finalize() throws Throwable {

  }

  /**
   * @return the codigoTipoVia
   */
  @Column(name = "CODIGOTIPOVIA", nullable = false, length = 2)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public Long getCodigoTipoVia() {
    return codigoTipoVia;
  }

  /**
   * @param codigoTipoVia the codigoTipoVia to set
   */
  public void setCodigoTipoVia(Long codigoTipoVia) {
    this.codigoTipoVia = codigoTipoVia;
  }

  /**
   * @return the descripcionTipoVia
   */
   @Column(name = "DESCRIPCIONTIPOVIA", nullable = false, length = 50)
  public String getDescripcionTipoVia() {
    return descripcionTipoVia;
  }

  /**
   * @param descripcionTipoVia the descripcionTipoVia to set
   */
  public void setDescripcionTipoVia(String descripcionTipoVia) {
    this.descripcionTipoVia = descripcionTipoVia;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CTIPVIA_CESTENT_FK")
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
    CatTipoVia that = (CatTipoVia) o;
    return codigoTipoVia.equals(that.codigoTipoVia) && descripcionTipoVia.equals(that.descripcionTipoVia) && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoTipoVia, descripcionTipoVia, estado);
  }
}