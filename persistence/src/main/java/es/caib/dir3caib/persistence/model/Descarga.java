/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
  private String estado;
  private List<String> ficheros;

  public Descarga() {
  }

  public Descarga(String tipo) {
    this.tipo = tipo;
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

  // este campo representa los tipos de estado que devuelve dir3caib de madrid.
  @Column(name = "ESTADO", length = 2)
  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  @Transient
  public List<String> getFicheros() {
    return ficheros;
  }

  public void setFicheros(List<String> ficheros) {
    this.ficheros = ficheros;
  }
}
