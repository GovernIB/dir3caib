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
@Table(name = "DIR_CATENTIDADGEOGRAFICA", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATENTIDADGEOGRAFICA", indexes = {
        @Index(name="DIR_CENTGEO_CESTENT_FK_I", columnNames = "ESTADO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CENTGEO_SEQ", allocationSize=1)
public class CatEntidadGeografica implements Serializable {

	private String codigoEntidadGeografica;
	private String descripcionEntidadGeografica;
    private CatEstadoEntidad estado;

	public CatEntidadGeografica(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoEntidadGeografica
   */
  @Column(name = "CODIGOENTIDADGEOGRAFICA", nullable = false, length = 2)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public String getCodigoEntidadGeografica() {
    return codigoEntidadGeografica;
  }

  /**
   * @param codigoEntidadGeografica the codigoEntidadGeografica to set
   */
  public void setCodigoEntidadGeografica(String codigoEntidadGeografica) {
    this.codigoEntidadGeografica = codigoEntidadGeografica;
  }

  /**
   * @return the descripcionEntidadGeografica
   */
  @Column(name = "DESCRIPCIONENTIDADGEOGRAFICA", nullable = false, length = 50)
  public String getDescripcionEntidadGeografica() {
    return descripcionEntidadGeografica;
  }

  /**
   * @param descripcionEntidadGeografica the descripcionEntidadGeografica to set
   */
  public void setDescripcionEntidadGeografica(String descripcionEntidadGeografica) {
    this.descripcionEntidadGeografica = descripcionEntidadGeografica;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CENTGEO_CESTENT_FK")
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
    CatEntidadGeografica that = (CatEntidadGeografica) o;
    return codigoEntidadGeografica.equals(that.codigoEntidadGeografica) && descripcionEntidadGeografica.equals(that.descripcionEntidadGeografica) && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoEntidadGeografica, descripcionEntidadGeografica, estado);
  }

}