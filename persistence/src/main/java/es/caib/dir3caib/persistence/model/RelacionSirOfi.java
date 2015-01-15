package es.caib.dir3caib.persistence.model;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

/**
 * @version 1.0
 * @created 28-oct-2013 14:41:39
 * Esta relación es la referente al intercambio de Registros (SIR). Determina que oficina da servicio a una unidad.
 */
@IdClass(es.caib.dir3caib.persistence.model.RelacionSirOfiPK.class)
@Table(name = "DIR_RELACIONSIROFI", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_RELACIONSIROFI", indexes = {
    @Index(name="DIR_RELSIROFI_CATESTENTI_FK_I", columnNames = {"ESTADO"}),
    @Index(name="DIR_UNIDAD_RELSIROFI_FK_I", columnNames = {"CODUNIDAD"}),
    @Index(name="DIR_OFICINA_RELSIROFI_FK_I", columnNames = {"CODOFICINA"})
})
@Entity
public class RelacionSirOfi implements Serializable {

	private Oficina oficina;
	private Unidad unidad;
	private CatEstadoEntidad estado;

	public RelacionSirOfi(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the oficina
   */
//  @Column(name = "CODOFICINA", nullable = false, length = 9)
  @Id
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn (name="CODOFICINA")
  @ForeignKey(name="DIR_RELSIROFI_CATOFI_FK")
  public Oficina getOficina() {
    return oficina;
  }

  /**
   * @param oficina the oficina to set
   */
  public void setOficina(Oficina oficina) {
    this.oficina = oficina;
  }

  /**
   * @return the unidad
   */

  @Id
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn (name="CODUNIDAD")
  @ForeignKey(name="DIR_RELSIROFI_CATUNIDAD_FK")
  public Unidad getUnidad() {
    return unidad;
  }

  /**
   * @param unidad the unidad to set
   */
  public void setUnidad(Unidad unidad) {
    this.unidad = unidad;
  }

  /**
   * @return the estado
   */
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_RELSIROFI_CATESTENTI_FK")
  public CatEstadoEntidad getEstado() {
    return estado;
  }

  /**
   * @param estado the estado to set
   */
  public void setEstado(CatEstadoEntidad estado) {
    this.estado = estado;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RelacionSirOfi that = (RelacionSirOfi) o;

    if (!estado.equals(that.estado)) return false;
    if (!oficina.equals(that.oficina)) return false;
    if (!unidad.equals(that.unidad)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = oficina.hashCode();
    result = 31 * result + unidad.hashCode();
    result = 31 * result + estado.hashCode();
    return result;
  }
}