/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;

/**
 *
 * @author mgonzalez
 */
public class RelacionOrganizativaOfiPK implements Serializable {
  
  
  private Oficina oficina;
  
	private Unidad unidad;

  public RelacionOrganizativaOfiPK() {
  }

  /**
   * @return the oficina
   */
  @Id
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn (name="CODOFICINA")
  @ForeignKey(name="DIR_RELORGOFI_CATOFI_FK")
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
  @ForeignKey(name="DIR_RELORGOFI_CATUNIDAD_FK")
  public Unidad getUnidad() {
    return unidad;
  }

  /**
   * @param unidad the unidad to set
   */
  public void setUnidad(Unidad unidad) {
    this.unidad = unidad;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RelacionOrganizativaOfiPK that = (RelacionOrganizativaOfiPK) o;

    if (!oficina.equals(that.oficina)) return false;
    if (!unidad.equals(that.unidad)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = oficina.hashCode();
    result = 31 * result + unidad.hashCode();
    return result;
  }
}
