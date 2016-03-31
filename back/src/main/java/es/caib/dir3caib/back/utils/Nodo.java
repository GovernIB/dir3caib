package es.caib.dir3caib.back.utils;

import java.util.List;

/**
 * Created 28/08/14 8:31
 *
 * @author mgonzalez
 */

public class Nodo {

  private String id;
  private String idPadre;
  private String nombre;
  private String estado;
  private List<Nodo> hijos;
  private List<Nodo> oficinasDependientes;
  private List<Nodo> oficinasDependientesAuxiliares;
  private List<Nodo> oficinasAuxiliares;
  private List<Nodo> oficinasFuncionales; //relacionesOrganizativasOfi


  public Nodo() {
  }

  public Nodo(String nombre, String estado){
    this.nombre=nombre;
    this.estado= estado;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIdPadre() {
    return idPadre;
  }

  public void setIdPadre(String idPadre) {
    this.idPadre = idPadre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<Nodo> getHijos() {
    return hijos;
  }

  public void setHijos(List<Nodo> hijos) {
    this.hijos = hijos;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public List<Nodo> getOficinasDependientes() {
    return oficinasDependientes;
  }

  public void setOficinasDependientes(List<Nodo> oficinasDependientes) {
    this.oficinasDependientes = oficinasDependientes;
  }

  public List<Nodo> getOficinasDependientesAuxiliares() {
    return oficinasDependientesAuxiliares;
  }

  public void setOficinasDependientesAuxiliares(List<Nodo> oficinasDependientesAuxiliares) {
    this.oficinasDependientesAuxiliares = oficinasDependientesAuxiliares;
  }

  public List<Nodo> getOficinasAuxiliares() {
    return oficinasAuxiliares;
  }

  public void setOficinasAuxiliares(List<Nodo> oficinasAuxiliares) {
    this.oficinasAuxiliares = oficinasAuxiliares;
  }

  public List<Nodo> getOficinasFuncionales() {
    return oficinasFuncionales;
  }

  public void setOficinasFuncionales(List<Nodo> oficinasFuncionales) {
    this.oficinasFuncionales = oficinasFuncionales;
  }
}
