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
@Table(name = "DIR_CATNIVELADMINISTRACION")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATNIVELADMINISTRACION", indexes = {
        @Index(name="DIR_CNIVADM_CESTENT_FK_I", columnNames = "ESTADO")
})
public class CatNivelAdministracion implements Serializable {

  private Long codigoNivelAdministracion;
  private String descripcionNivelAdministracion;
  private CatEstadoEntidad estado;

  public CatNivelAdministracion(){

  }

  public CatNivelAdministracion(Long codigoNivelAdministracion) {
    this.codigoNivelAdministracion = codigoNivelAdministracion;
  }

  public void finalize() throws Throwable {

	}

  /**
   * @return the codigoNivelAdministracion
   */
  @Id
  @Column(name = "CODIGONIVELADMINISTRACION", nullable = false, length = 2)
  public Long getCodigoNivelAdministracion() {
    return codigoNivelAdministracion;
  }

  /**
   * @param codigoNivelAdministracion the codigoNivelAdministracion to set
   */
  public void setCodigoNivelAdministracion(Long codigoNivelAdministracion) {
    this.codigoNivelAdministracion = codigoNivelAdministracion;
  }

  /**
   * @return the descripcionNivelAdministracion
   */
  @Column(name = "DESCRIPCIONNIVELADMINISTRACION", nullable = false, length = 300)
  public String getDescripcionNivelAdministracion() {
    return descripcionNivelAdministracion;
  }

  /**
   * @param descripcionNivelAdministracion the descripcionNivelAdministracion to set
   */
  public void setDescripcionNivelAdministracion(String descripcionNivelAdministracion) {
    this.descripcionNivelAdministracion = descripcionNivelAdministracion;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CNIVADM_CESTENT_FK")
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
    CatNivelAdministracion that = (CatNivelAdministracion) o;
    return codigoNivelAdministracion.equals(that.codigoNivelAdministracion) && descripcionNivelAdministracion.equals(that.descripcionNivelAdministracion) && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoNivelAdministracion, descripcionNivelAdministracion, estado);
  }

}