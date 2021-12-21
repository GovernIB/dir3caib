package es.caib.dir3caib.persistence.model;
import java.io.Serializable;
import java.util.Objects;


/**
 * @version 1.1
 * @created 28-oct-2013 14:41:38
 */
public class UnidadPK implements Serializable {


	public String codigo;

	public Long version;

	public UnidadPK(){

	}

  public UnidadPK(String codigo, Long version){
    this.codigo = codigo;
    this.version = version;
	}

  /**
   * @return the codigo
   */
  public String getCodigo() {
    return codigo;
  }

  /**
   * @param codigo the codigo to set
   */
  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  /**
   * @return the version
   */
  public Long getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(Long version) {
    this.version = version;
  }

	public void finalize() throws Throwable {

	}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UnidadPK that = (UnidadPK) o;
    return codigo.equals(that.codigo) && version.equals(that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo, version);
  }


}