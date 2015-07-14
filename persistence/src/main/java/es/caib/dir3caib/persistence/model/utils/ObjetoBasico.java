package es.caib.dir3caib.persistence.model.utils;

/**
 * Created 10/03/15 12:17
 *
 * @author mgonzalez
 * Representa un conjunto mínimo de datos de una unidad o oficina. Los datos mínimos son
 * el código, la denominación y el estado.
 */
public class ObjetoBasico {

  private String codigo;
  private String denominacion;
  private String descripcionEstado;

  public ObjetoBasico() {
  }

  public ObjetoBasico(String codigo, String denominacion, String descripcionEstado) {
    this.codigo = codigo;
    this.denominacion = denominacion;
    this.descripcionEstado = descripcionEstado;
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
}
