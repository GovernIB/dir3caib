package es.caib.dir3caib.persistence.model;

import org.codehaus.jackson.annotate.JsonIgnore;
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
@Table(name = "DIR_CATESTADOENTIDAD", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATESTADOENTIDAD", indexes = {
        @Index(name="DIR_CESTENT_CESTENT_FK_I", columnNames = "ESTADO")
})
public class CatEstadoEntidad implements Serializable {

	private String codigoEstadoEntidad;
	private String descripcionEstadoEntidad;
   // private CatEstadoEntidad estado;
    private String estado;

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

  /*@ManyToOne(cascade = {CascadeType.PERSIST},optional = true, fetch = FetchType.EAGER)
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CESTENT_CESTENT_FK")
  public CatEstadoEntidad getEstado() {
    return estado;
  }

  public void setEstado(CatEstadoEntidad estado) {
    this.estado = estado;
  }*/

    @Column(name = "ESTADO", length = 2)
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CatEstadoEntidad that = (CatEstadoEntidad) o;
    return codigoEstadoEntidad.equals(that.codigoEstadoEntidad) && descripcionEstadoEntidad.equals(that.descripcionEstadoEntidad) ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoEstadoEntidad, descripcionEstadoEntidad);
  }
  
  @Override 
  public String toString() {
	  String temp = "{Codigo: ";
	  if (this.getCodigoEstadoEntidad() != null)
		  temp += this.getCodigoEstadoEntidad();
	  temp += ",Descripcion: ";
	  if (this.getDescripcionEstadoEntidad() != null)
		  temp += this.getDescripcionEstadoEntidad();
	  temp += " ,Estado:";
	  if(this.getEstado() != null)
		  temp += this.getEstado();
	  temp += "}";
	  return  temp;
  }


}