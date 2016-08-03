package es.caib.dir3caib.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


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

  public Servicio(Long codServicio) {
    this.codServicio = codServicio;
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Servicio servicio = (Servicio) o;

    return codServicio != null ? codServicio.equals(servicio.codServicio) : servicio.codServicio == null;

  }

  @Override
  public int hashCode() {
    return codServicio != null ? codServicio.hashCode() : 0;
  }


}