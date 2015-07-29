package es.caib.dir3caib.persistence.model.utils;

/**
 * Created 10/03/15 12:17
 *
 * @author mgonzalez
 * Representa un conjunto mínimo de datos de una unidad o oficina. Los datos mínimos son
 * el código, la denominación y el estado y dos atributos más genericos para mostrar más
 * información en función de si es unidad o oficina
 */
public class ObjetoBasico {

  private String codigo;
  private String denominacion;
  private String descripcionEstado;
  private String raiz;
  private String superior;

  public ObjetoBasico() {
  }

  public ObjetoBasico(String codigo, String denominacion, String descripcionEstado, String raiz, String superior) {
    this.codigo = codigo;
    this.denominacion = denominacion;
    this.descripcionEstado = descripcionEstado;
    this.raiz = raiz;
    this.superior = superior;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
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
}
