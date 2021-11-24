package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATESTADOENTIDAD", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATESTADOENTIDAD", indexes = {
        @Index(name="DIR_CESTENT_CESTENT_FK_I", columnNames = "ESTADO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CESTENT_SEQ", allocationSize=1)
public class CatEstadoEntidad implements Serializable {

	private String codigoEstadoEntidad;
	private String descripcionEstadoEntidad;
    private CatEstadoEntidad estado;

	public CatEstadoEntidad(){

	}

  public CatEstadoEntidad(String codigoEstadoEntidad) {
    this.codigoEstadoEntidad = codigoEstadoEntidad;
  }

  public void finalize() throws Throwable {

	}

  /**
   * @return the codigoEstadoEntidad
   */
  @Column(name = "CODIGOESTADOENTIDAD", nullable = false, length = 2)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public String getCodigoEstadoEntidad() {
    return codigoEstadoEntidad;
  }

  /**
   * @param codigoEstadoEntidad the codigoEstadoEntidad to set
   */
  public void setCodigoEstadoEntidad(String codigoEstadoEntidad) {
    this.codigoEstadoEntidad = codigoEstadoEntidad;
  }

  /**
   * @return the descripcionEstadoEntidad
   */
  @Column(name = "DESCRIPCIONESTADOENTIDAD", nullable = false, length = 50)
  public String getDescripcionEstadoEntidad() {
    return descripcionEstadoEntidad;
  }

  /**
   * @param descripcionEstadoEntidad the descripcionEstadoEntidad to set
   */
  public void setDescripcionEstadoEntidad(String descripcionEstadoEntidad) {
    this.descripcionEstadoEntidad = descripcionEstadoEntidad;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CESTENT_CESTENT_FK")
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
    CatEstadoEntidad that = (CatEstadoEntidad) o;
    return codigoEstadoEntidad.equals(that.codigoEstadoEntidad) && descripcionEstadoEntidad.equals(that.descripcionEstadoEntidad) && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoEstadoEntidad, descripcionEstadoEntidad, estado);
  }


}