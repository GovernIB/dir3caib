/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author mgonzalez
 */
@Table(name = "DIR_DESCARGA", schema = "", catalog = "")
@Entity
@SequenceGenerator(name="generator",sequenceName = "DIR_SEQ_ALL", allocationSize=1)
public class Descarga implements Serializable {
  
  private Long codigo;
  private String fechaInicio;
  private String fechaFin;
  private String fechaImportacion;  
  private String tipo;

  public Descarga() {
  }
  
  @Column(name = "CODIGO", nullable = false, length = 3)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }
  
  @Column(name = "FECHAINICIO")  
  public String getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(String fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  @Column(name = "FECHAFIN")  
  public String getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(String fechaFin) {
    this.fechaFin = fechaFin;
  }
  
  @Column(name = "FECHAIMPORTACION")
  public String getFechaImportacion() {
    return fechaImportacion;
  }

  public void setFechaImportacion(String fechaImportacion) {
    this.fechaImportacion = fechaImportacion;
  }
  
  @Column(name = "TIPO")
  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

}
