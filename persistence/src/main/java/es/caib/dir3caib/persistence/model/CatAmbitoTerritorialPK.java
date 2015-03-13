package es.caib.dir3caib.persistence.model;
import java.io.Serializable;



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
  public int hashCode() {
    int hash = 5;
    hash = 31 * hash + (this.codigoAmbito != null ? this.codigoAmbito.hashCode() : 0);
    hash = 31 * hash + (this.nivelAdministracion != null ? this.nivelAdministracion.hashCode() : 0);
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
    final CatAmbitoTerritorialPK other = (CatAmbitoTerritorialPK) obj;
    if ((this.codigoAmbito == null) ? (other.codigoAmbito != null) : !this.codigoAmbito.equals(other.codigoAmbito)) {
      return false;
    }
    if (this.nivelAdministracion != other.nivelAdministracion && (this.nivelAdministracion == null || !this.nivelAdministracion.equals(other.nivelAdministracion))) {
      return false;
    }
    return true;
  }
  
  

}