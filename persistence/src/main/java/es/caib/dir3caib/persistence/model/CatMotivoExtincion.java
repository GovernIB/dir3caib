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
@Table(name = "DIR_CATMOTIVOEXTINCION", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATMOTIVOEXTINCION", indexes = {
        @Index(name="DIR_CMOTEXT_CESTENT_FK_I", columnNames = "ESTADO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CMOTEXT_SEQ", allocationSize=1)
public class CatMotivoExtincion implements Serializable {

	private String codigoMotivoExtincion;
	private String descripcionMotivoExtincion;
    private CatEstadoEntidad estado;

	public CatMotivoExtincion(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoMotivoExtincion
   */
  @Column(name = "CODIGOMOTIVOEXTINCION", nullable = false, length = 3)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public String getCodigoMotivoExtincion() {
    return codigoMotivoExtincion;
  }

  /**
   * @param codigoMotivoExtincion the codigoMotivoExtincion to set
   */
  public void setCodigoMotivoExtincion(String codigoMotivoExtincion) {
    this.codigoMotivoExtincion = codigoMotivoExtincion;
  }

  /**
   * @return the descripcionMotivoExtincion
   */
  @Column(name = "DESCRIPCIONMOTIVOEXTINCION", nullable = false, length = 400)
  public String getDescripcionMotivoExtincion() {
    return descripcionMotivoExtincion;
  }

  /**
   * @param descripcionMotivoExtincion the descripcionMotivoExtincion to set
   */
  public void setDescripcionMotivoExtincion(String descripcionMotivoExtincion) {
    this.descripcionMotivoExtincion = descripcionMotivoExtincion;
  }

    @ManyToOne
    @JoinColumn(name="ESTADO")
    @ForeignKey(name="DIR_CMOTEXT_CESTENT_FK")
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
    CatMotivoExtincion that = (CatMotivoExtincion) o;
    return codigoMotivoExtincion.equals(that.codigoMotivoExtincion) && descripcionMotivoExtincion.equals(that.descripcionMotivoExtincion) && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoMotivoExtincion, descripcionMotivoExtincion, estado);
  }
}