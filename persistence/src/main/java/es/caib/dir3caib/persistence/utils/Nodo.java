package es.caib.dir3caib.persistence.utils;

import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;

import java.util.List;

/**
 * Created 28/08/14 8:31
 *
 * @author mgonzalez
 */

public class Nodo extends ObjetoBasico {

    private String idPadre;
    private List<Nodo> hijos;
    private List<Nodo> oficinasDependientes;
    private List<Nodo> oficinasDependientesAuxiliares;
    private List<Nodo> oficinasAuxiliares;
    private List<Nodo> oficinasOrganizativas; //relacionesOrganizativasOfi


    public Nodo() {
    }

    public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad) {
        super(codigo, denominacion, descripcionEstado, raiz, superior, localidad);
    }

    public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad, String idPadre, List<Nodo> hijos, List<Nodo> oficinasDependientes, List<Nodo> oficinasDependientesAuxiliares, List<Nodo> oficinasAuxiliares, List<Nodo> oficinasOrganizativas) {
        super(codigo, denominacion, descripcionEstado, raiz, superior, localidad);
        this.idPadre = idPadre;
        this.hijos = hijos;
        this.oficinasDependientes = oficinasDependientes;
        this.oficinasDependientesAuxiliares = oficinasDependientesAuxiliares;
        this.oficinasAuxiliares = oficinasAuxiliares;
        this.oficinasOrganizativas = oficinasOrganizativas;
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

    public List<Nodo> getOficinasOrganizativas() {
        return oficinasOrganizativas;
    }

    public void setOficinasOrganizativas(List<Nodo> oficinasOrganizativas) {
        this.oficinasOrganizativas = oficinasOrganizativas;
    }
}
