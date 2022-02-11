package es.caib.dir3caib.persistence.model.utils;

/**
 * Created 10/03/15 12:17
 *
 * @author mgonzalez
 * Representa un conjunto de datos reducidos de uan estructura en árbol de una unidad o oficina. Los datos son
 * el código, la denominación y el estado y dos atributos más genericos para mostrar más
 * información en función de si es unidad o oficina. Esta clase se emplea para mostrar estructuras en árbol,
 * tanto de unidad como oficina en los métodos rest y para mostrar la información en jsp de dir3caib.
 */
public class ObjetoBasico {

  private String codigo;
  private String codigoDir3;
  private String version; 
  private String denominacion;
  private String descripcionEstado;
  private String raiz;
  private String superior; // denominación
  private String codigoSuperior; // código
  private String localidad;
   private String edpPrincipal;

  public ObjetoBasico() {
  }

  public ObjetoBasico(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad) {
	this.codigo = codigo;
    this.denominacion = denominacion;
    this.descripcionEstado = descripcionEstado;
    this.raiz = raiz;
    this.superior = superior;
    this.localidad = localidad;
    
    if (codigo.indexOf("v") > 0) {
		String[] codigoSeparado = codigo.split("v");
		this.codigoDir3 = codigoSeparado[0];
	    this.version = codigoSeparado[1];
	}
  }

   public ObjetoBasico(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad, String edpPrincipal) {
      this.codigo = codigo;
      this.denominacion = denominacion;
      this.descripcionEstado = descripcionEstado;
      this.raiz = raiz;
      this.superior = superior;
      this.localidad = localidad;
      this.edpPrincipal = edpPrincipal;
      
      if (codigo.indexOf("v") > 0) {
			String[] codigoSeparado = codigo.split("v");
			this.codigoDir3 = codigoSeparado[0];
		    this.version = codigoSeparado[1];
	  }
   }


  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigoDir3() {
	return codigoDir3;
  }

  public void setCodigoDir3(String codigoDir3) {
	this.codigoDir3 = codigoDir3;
  }

  public String getVersion() {
	return version;
  }

  public void setVersion(String version) {
	this.version = version;
  }

  public String getDenominacion() {
    return denominacion;
  }

  public void setDenominacion(String denominacion) {
    this.denominacion = denominacion;
  }

  public String getDescripcionEstado() {
    return descripcionEstado;
  }

  public void setDescripcionEstado(String descripcionEstado) {
    this.descripcionEstado = descripcionEstado;
  }

  public String getRaiz() {
    return raiz;
  }

  public void setRaiz(String raiz) {
    this.raiz = raiz;
  }

  public String getSuperior() {
    return superior;
  }

  public void setSuperior(String superior) {
    this.superior = superior;
  }

    public String getCodigoSuperior() {
        return codigoSuperior;
    }

    public void setCodigoSuperior(String codigoSuperior) {
        this.codigoSuperior = codigoSuperior;
    }

  public String getLocalidad() {
    return localidad;
  }

  public void setLocalidad(String localidad) {
    this.localidad = localidad;
  }

   public String getEdpPrincipal() {
      return edpPrincipal;
   }

   public void setEdpPrincipal(String edpPrincipal) {
      this.edpPrincipal = edpPrincipal;
   }
}
