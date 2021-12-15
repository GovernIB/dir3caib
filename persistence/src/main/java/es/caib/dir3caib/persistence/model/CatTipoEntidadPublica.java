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
@Table(name = "DIR_CATTIPOENTIDADPUBLICA", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATTIPOENTIDADPUBLICA", indexes = {
        @Index(name="DIR_CTIENPU_CESTENT_FK_I", columnNames = "ESTADO")
})
public class CatTipoEntidadPublica implements Serializable {

	private String codigoTipoEntidadPublica;
	private String descripcionTipoEntidadPublica;
    private CatEstadoEntidad estado;

	public CatTipoEntidadPublica(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoTipoEntidadPublica
   */
  @Column(name = "CODIGOTIPOENTIDADPUBLICA", nullable = false, length = 2)
  @Id
  public String getCodigoTipoEntidadPublica() {
    return codigoTipoEntidadPublica;
  }

  /**
   * @param codigoTipoEntidadPublica the codigoTipoEntidadPublica to set
   */
  public void setCodigoTipoEntidadPublica(String codigoTipoEntidadPublica) {
    this.codigoTipoEntidadPublica = codigoTipoEntidadPublica;
  }

  /**
   * @return the descripcionTipoEntidadPublica
   */
  @Column(name = "DESCRIPCIONTIPOENTIDADPUBLICA", nullable = false, length = 100)
  public String getDescripcionTipoEntidadPublica() {
    return descripcionTipoEntidadPublica;
  }

  /**
   * @param descripcionTipoEntidadPublica the descripcionTipoEntidadPublica to set
   */
  public void setDescripcionTipoEntidadPublica(String descripcionTipoEntidadPublica) {
    this.descripcionTipoEntidadPublica = descripcionTipoEntidadPublica;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CTIENPU_CESTENT_FK")
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
    CatTipoEntidadPublica that = (CatTipoEntidadPublica) o;
    return codigoTipoEntidadPublica.equals(that.codigoTipoEntidadPublica) && descripcionTipoEntidadPublica.equals(that.descripcionTipoEntidadPublica)
            && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoTipoEntidadPublica, descripcionTipoEntidadPublica, estado);
  }
}