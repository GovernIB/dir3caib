package es.caib.dir3caib.persistence.model;
import java.io.Serializable;
import java.util.Objects;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
public class CatAmbitoTerritorialPK implements Serializable {

 
	public String codigoAmbito;
  
	public Long nivelAdministracion;

	public CatAmbitoTerritorialPK(){

	}
  
  public CatAmbitoTerritorialPK(String codigoAmbito, Long nivelAdministracion){
    this.codigoAmbito = codigoAmbito;
    this.nivelAdministracion = nivelAdministracion;
	}

  /**
   * @return the codigoAmbito
   */
  public String getCodigoAmbito() {
    return codigoAmbito;
  }

  /**
   * @param codigoAmbito the codigoAmbito to set
   */
  public void setCodigoAmbito(String codigoAmbito) {
    this.codigoAmbito = codigoAmbito;
  }

  /**
   * @return the nivelAdministracion
   */
  public Long getNivelAdministracion() {
    return nivelAdministracion;
  }

  /**
   * @param nivelAdministracion the nivelAdministracion to set
   */
  public void setNivelAdministracion(Long nivelAdministracion) {
    this.nivelAdministracion = nivelAdministracion;
  }

	public void finalize() throws Throwable {

	}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CatAmbitoTerritorialPK that = (CatAmbitoTerritorialPK) o;
    return codigoAmbito.equals(that.codigoAmbito) && nivelAdministracion.equals(that.nivelAdministracion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoAmbito, nivelAdministracion);
  }


}