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
    private Long nivel; //Nivel Jerarquico

    //Campos añadadidos para Dades obertes CAIB
    private String cif;
    private String nivelAdministracion;
    private String tipoUnidad;
    private String direccion;
    private List<String> contactos; //tel, url, email
    private String ambitoTerritorial;
    private String ambitoPais;
    private String ambitoComunAutonoma;
    private String ambitoProvincia;
    private String ambitoIsla;


    public Nodo() {
    }

    public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad) {
        super(codigo, denominacion, descripcionEstado, raiz, superior, localidad);
    }

    public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String codigoSuperior, String localidad) {
        super(codigo, denominacion, descripcionEstado, raiz, superior, localidad);
        super.setCodigoSuperior(codigoSuperior);
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


   public Nodo(String codigo, String denominacion, String descripcionEstado, String raiz, String superior, String localidad, Boolean esEdp,Long nivel,String cif, String nivelAdministracion, String tipoUnidad, String direccion, String ambitoTerritorial, String ambitoPais, String ambitoComunAutonoma, String ambitoProvincia, String ambitoIsla) {
      super(codigo, denominacion, descripcionEstado, raiz, superior, localidad);
      this.esEdp= esEdp;
      this.nivel= nivel;
      this.cif = cif;
      this.nivelAdministracion = nivelAdministracion;
      this.tipoUnidad = tipoUnidad;
      this.direccion = direccion;
      this.ambitoTerritorial = ambitoTerritorial;
      this.ambitoPais = ambitoPais;
      this.ambitoComunAutonoma = ambitoComunAutonoma;
      this.ambitoProvincia = ambitoProvincia;
      this.ambitoIsla = ambitoIsla;
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

   public Long getNivel() {
      return nivel;
   }

   public void setNivel(Long nivel) {
      this.nivel = nivel;
   }

   public String getCif() {
      return cif;
   }

   public void setCif(String cif) {
      this.cif = cif;
   }

   public String getNivelAdministracion() {
      return nivelAdministracion;
   }

   public void setNivelAdministracion(String nivelAdministracion) {
      this.nivelAdministracion = nivelAdministracion;
   }

   public String getTipoUnidad() {
      return tipoUnidad;
   }

   public void setTipoUnidad(String tipoUnidad) {
      this.tipoUnidad = tipoUnidad;
   }

   public String getDireccion() {
      return direccion;
   }

   public void setDireccion(String direccion) {
      this.direccion = direccion;
   }

   public List<String> getContactos() {
      return contactos;
   }

   public void setContactos(List<String> contactos) {
      this.contactos = contactos;
   }

   public String getAmbitoTerritorial() {
      return ambitoTerritorial;
   }

   public void setAmbitoTerritorial(String ambitoTerritorial) {
      this.ambitoTerritorial = ambitoTerritorial;
   }

   public String getAmbitoPais() {
      return ambitoPais;
   }

   public void setAmbitoPais(String ambitoPais) {
      this.ambitoPais = ambitoPais;
   }

   public String getAmbitoComunAutonoma() {
      return ambitoComunAutonoma;
   }

   public void setAmbitoComunAutonoma(String ambitoComunAutonoma) {
      this.ambitoComunAutonoma = ambitoComunAutonoma;
   }

   public String getAmbitoProvincia() {
      return ambitoProvincia;
   }

   public void setAmbitoProvincia(String ambitoProvincia) {
      this.ambitoProvincia = ambitoProvincia;
   }

   public String getAmbitoIsla() {
      return ambitoIsla;
   }

   public void setAmbitoIsla(String ambitoIsla) {
      this.ambitoIsla = ambitoIsla;
   }
}
