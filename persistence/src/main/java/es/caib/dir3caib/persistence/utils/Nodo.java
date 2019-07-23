package es.caib.dir3caib.persistence.utils;

import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;

import java.util.List;

/**
 * Se utiliza para almacenar información reducida de la estructura en árbol de Unidades u Oficinas.
 * Se utiliza para retornar información via JSON de peticiones REST.
 * Created 28/08/14 8:31
 *
 * @author mgonzalez
 */

public class Nodo extends ObjetoBasico {

    private String idPadre;
    private List<Nodo> hijos;
    private List<Nodo> oficinasDependientes;
    private List<Nodo> oficinasAuxiliares;
    private List<Nodo> oficinasFuncionales; //relacionesOrganizativasOfi
   private List<Nodo> historicos; // guarda los históricos del nodo
    private boolean tieneOficinaSir = false;
    private boolean esEdp = false;
   private int nivel;


    public Nodo() {
    }

    public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad) {
        super(codigo, denominacion, descripcionEstado, raiz, superior, localidad);
    }

    public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad, boolean tieneOficinaSir) {
        super(codigo, denominacion, descripcionEstado, raiz, superior, localidad);
        this.tieneOficinaSir = tieneOficinaSir;
    }

    public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad, boolean tieneOficinaSir, boolean esEdp, String edpPrincipal) {
        super(codigo, denominacion, descripcionEstado, raiz, superior, localidad, edpPrincipal);
        this.tieneOficinaSir = tieneOficinaSir;
        this.esEdp = esEdp;
    }




    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }



    public List<Nodo> getHijos() {
        return hijos;
    }

    public void setHijos(List<Nodo> hijos) {
        this.hijos = hijos;
    }



    public List<Nodo> getOficinasDependientes() {
        return oficinasDependientes;
    }

    public void setOficinasDependientes(List<Nodo> oficinasDependientes) {
        this.oficinasDependientes = oficinasDependientes;
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

   public List<Nodo> getHistoricos() {
      return historicos;
   }

   public void setHistoricos(List<Nodo> historicos) {
      this.historicos = historicos;
   }

    public boolean getTieneOficinaSir() {
        return tieneOficinaSir;
    }

    public void setTieneOficinaSir(boolean tieneOficinaSir) {
        this.tieneOficinaSir = tieneOficinaSir;
    }

    public boolean isEsEdp() {
        return esEdp;
    }

    public void setEsEdp(boolean esEdp) {
        this.esEdp = esEdp;
    }

   public int getNivel() {
      return nivel;
   }

   public void setNivel(int nivel) {
      this.nivel = nivel;
   }
}
