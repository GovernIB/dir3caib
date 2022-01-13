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
@Table(name = "DIR_CATTIPOCONTACTO", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATTIPOCONTACTO", indexes = {
        @Index(name="DIR_CTIPCON_CESTENT_FK_I", columnNames = "ESTADO")
})
public class CatTipoContacto implements Serializable {

	private String codigoTipoContacto;
	private String descripcionTipoContacto;
    private CatEstadoEntidad estado;

	public CatTipoContacto(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoTipoContacto
   */
  @Column(name = "CODIGOTIPOCONTACTO", nullable = false, length = 2)
  @Id
  public String getCodigoTipoContacto() {
    return codigoTipoContacto;
  }

  /**
   * @param codigoTipoContacto the codigoTipoContacto to set
   */
  public void setCodigoTipoContacto(String codigoTipoContacto) {
    this.codigoTipoContacto = codigoTipoContacto;
  }

  /**
   * @return the descripcionTipoContacto
   */
  @Column(name = "DESCRIPCIONTIPOCONTACTO", nullable = false, length = 30)
  public String getDescripcionTipoContacto() {
    return descripcionTipoContacto;
  }

  /**
   * @param descripcionTipoContacto the descripcionTipoContacto to set
   */
  public void setDescripcionTipoContacto(String descripcionTipoContacto) {
    this.descripcionTipoContacto = descripcionTipoContacto;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CTIPCON_CESTENT_FK")
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
    CatTipoContacto that = (CatTipoContacto) o;
    return codigoTipoContacto.equals(that.codigoTipoContacto) && descripcionTipoContacto.equals(that.descripcionTipoContacto) && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoTipoContacto, descripcionTipoContacto, estado);
  }

}