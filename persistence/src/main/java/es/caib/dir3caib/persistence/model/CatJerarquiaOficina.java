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
@Table(name = "DIR_CATJERARQUIAOFICINA", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATJERARQUIAOFICINA", indexes = {
        @Index(name="DIR_CJEROFI_CESTENT_FK_I", columnNames = "ESTADO")
})
public class CatJerarquiaOficina implements Serializable {

	private Long codigoJerarquiaOficina;
	private String descripcionJerarquiaOficina;
    private CatEstadoEntidad estado;

	public CatJerarquiaOficina(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoJerarquiaOficina
   */
  @Column(name = "CODIGOJERARQUIAOFICINA", nullable = false, length = 2)
  @Id
  public Long getCodigoJerarquiaOficina() {
    return codigoJerarquiaOficina;
  }

  /**
   * @param codigoJerarquiaOficina the codigoJerarquiaOficina to set
   */
  public void setCodigoJerarquiaOficina(Long codigoJerarquiaOficina) {
    this.codigoJerarquiaOficina = codigoJerarquiaOficina;
  }

  /**
   * @return the descripcionJerarquiaOficina
   */
  @Column(name = "DESCRIPCIONJERARQUIAOFICINA", nullable = false, length = 50)
  public String getDescripcionJerarquiaOficina() {
    return descripcionJerarquiaOficina;
  }

  /**
   * @param descripcionJerarquiaOficina the descripcionJerarquiaOficina to set
   */
  public void setDescripcionJerarquiaOficina(String descripcionJerarquiaOficina) {
    this.descripcionJerarquiaOficina = descripcionJerarquiaOficina;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CJEROFI_CESTENT_FK")
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
    CatJerarquiaOficina that = (CatJerarquiaOficina) o;
    return codigoJerarquiaOficina.equals(that.codigoJerarquiaOficina) && descripcionJerarquiaOficina.equals(that.descripcionJerarquiaOficina) && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoJerarquiaOficina, descripcionJerarquiaOficina, estado);
  }

}