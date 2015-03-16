/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mgonzalez
 */
@Table(name = "DIR_DESCARGA", schema = "", catalog = "")
@Entity
@SequenceGenerator(name="generator",sequenceName = "DIR_SEQ_ALL", allocationSize=1)
public class Descarga implements Serializable {
  
  private Long codigo;

  private Date fechaInicio;
  private Date fechaFin;
  private Date fechaImportacion;

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
  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  @Column(name = "FECHAFIN")  
  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }
  
  @Column(name = "FECHAIMPORTACION")
  public Date getFechaImportacion() {
    return fechaImportacion;
  }

  public void setFechaImportacion(Date fechaImportacion) {
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
