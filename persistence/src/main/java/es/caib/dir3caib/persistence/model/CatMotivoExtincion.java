package es.caib.dir3caib.persistence.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
@Table(name = "DIR_CATMOTIVOEXTINCION", schema = "", catalog = "")
@Entity
public class CatMotivoExtincion implements Serializable {

	private String codigoMotivoExtincion;
	private String descripcionMotivoExtincion;

	public CatMotivoExtincion(){

	}

	public void finalize() throws Throwable {

	}

  /**
   * @return the codigoMotivoExtincion
   */
  @Column(name = "CODIGOMOTIVOEXTINCION", nullable = false, length = 3)
  @Id
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
  
  

}