package es.caib.dir3caib.persistence.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:39
 */
@Table(name = "DIR_CATSERVICIO", schema = "", catalog = "")
@Entity
public class Servicio implements Serializable {

	private Long codServicio;
	private String descServicio;
  // Borrado por problemas de rendimiento, en principio no se necesita.
 // private Set<Oficina> oficinas;

	public Servicio(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codServicio
   */
  @Column(name = "CODSERVICIO", nullable = false, length = 3)
  @Id
  public Long getCodServicio() {
    return codServicio;
  }

  /**
   * @param codServicio the codServicio to set
   */
  public void setCodServicio(Long codServicio) {
    this.codServicio = codServicio;
  }

  /**
   * @return the descServicio
   */
  @Column(name = "DESCSERVICIO", nullable = false, length = 300)
  public String getDescServicio() {
    return descServicio;
  }

  /**
   * @param descServicio the descServicio to set
   */
  public void setDescServicio(String descServicio) {
    this.descServicio = descServicio;
  }

  /**
   * @return the oficinas
   */
  //@ManyToMany(mappedBy="servicios", cascade=CascadeType.PERSIST, fetch= FetchType.EAGER)
//  public Set<Oficina> getOficinas() {
//    return oficinas;
//  }
//
//  /**
//   * @param oficinas the oficinas to set
//   */
//  public void setOficinas(Set<Oficina> oficinas) {
//    this.oficinas = oficinas;
//  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 79 * hash + (this.codServicio != null ? this.codServicio.hashCode() : 0);
    hash = 79 * hash + (this.descServicio != null ? this.descServicio.hashCode() : 0);
   // hash = 79 * hash + (this.oficinas != null ? this.oficinas.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Servicio other = (Servicio) obj;
    if (this.codServicio != other.codServicio && (this.codServicio == null || !this.codServicio.equals(other.codServicio))) {
      return false;
    }
    if ((this.descServicio == null) ? (other.descServicio != null) : !this.descServicio.equals(other.descServicio)) {
      return false;
    }
    /*if (this.oficinas != other.oficinas && (this.oficinas == null || !this.oficinas.equals(other.oficinas))) {
      return false;
    }*/
    return true;
  }

  
  

}