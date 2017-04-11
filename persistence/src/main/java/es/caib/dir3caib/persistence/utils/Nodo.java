package es.caib.dir3caib.persistence.utils;

import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;

import java.util.List;

/**
 * Se utiliza para almacenar información de la estructura de Unidades u Oficinas.
 * Se utiliza para retornar información via JSON de peticiones REST y crear el Arbol de organismos.
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
    private boolean tieneOficinaSir = false;


    public Nodo() {
    }

    public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad) {
        super(codigo, denominacion, descripcionEstado, raiz, superior, localidad);
    }

    public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad, boolean tieneOficinaSir) {
        super(codigo, denominacion, descripcionEstado, raiz, superior, localidad);
        this.tieneOficinaSir = tieneOficinaSir;
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

    public boolean getTieneOficinaSir() {
        return tieneOficinaSir;
    }

    public void setTieneOficinaSir(boolean tieneOficinaSir) {
        this.tieneOficinaSir = tieneOficinaSir;
    }
}
