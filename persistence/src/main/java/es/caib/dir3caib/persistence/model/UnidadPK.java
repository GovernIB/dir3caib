package es.caib.dir3caib.persistence.model;
import java.io.Serializable;
import java.util.Objects;


/**
 * @version 1.1
 * @created 28-oct-2013 14:41:38
 */
public class UnidadPK implements Serializable, Comparable<UnidadPK> {


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

  @Override
  public int compareTo(UnidadPK o) {

   // System.out.println(this.getCodigo().compareTo(o.getCodigo()));
   // System.out.println(this.getVersion().compareTo(o.getVersion()));

    String thisCodigo = this.getCodigo();
    String oCodigo = o.getCodigo();
    Long thisVersion = this.getVersion();
    Long oVersion = o.getVersion();

   /* if ((thisCodigo.compareTo(oCodigo) > 0) && thisVersion>oVersion) {
      return 1;
    }
    if ((thisCodigo.compareTo(oCodigo) >= 0) && thisVersion>oVersion) {
      return 1;
    }

    if ((thisCodigo.compareTo(oCodigo)<0) && thisVersion == oVersion) {
      return -1;
    }

    if ((thisCodigo.compareTo(oCodigo)<0) && thisVersion<=oVersion) {
      return -1;
    }*/

    /*if (this.getCodigo().compareTo(o.getCodigo()) > 0  &&  this.getVersion().compareTo(o.getVersion())> 0) {
      return 1;
    }
    if (this.getCodigo().compareTo(o.getCodigo()) < 0  &&  this.getVersion().compareTo(o.getVersion())< 0) {
      return -1;
    }*/
    return this.getCodigo().compareTo(o.getCodigo());

  }

}