package es.caib.dir3caib.persistence.model.json;

import java.util.List;

/**
 * Created by mgonzalez on 19/12/2018.
 */
public class OficinaJson {
   private String codigoDir3;
   private String denominacion;
   private String estado;    //CatEstadoEntidad
   private String nivelAdministracion;  //CatNivelAdministracion
   private String tipoOficina;  //CatJerarquiaOficina
   private String unidadResponsable;      //Unidad
   private String oficinaResponsable;   //Oficina
   private String horarioAtencion;
   private String diasSinHabiles;
   private String observaciones;
   private String fechaAltaOficial;
   private String fechaExtincion;
   private String fechaAnulacion;
   private String pais;
   private String comunidad;
   private String municipio; //LOCALIDAD
   private String nombreVia;
   private String numeroVia;
   private String tipoVia;
   private String codigoPostal;

   //private List<String> servicios;
   private String servicios;
   private List<String> contactos;

   public String getCodigoDir3() {
      return codigoDir3;
   }

   public void setCodigoDir3(String codigoDir3) {
      this.codigoDir3 = codigoDir3;
   }

   public String getDenominacion() {
      return denominacion;
   }

   public void setDenominacion(String denominacion) {
      this.denominacion = denominacion;
   }

   public String getEstado() {
      return estado;
   }

   public void setEstado(String estado) {
      this.estado = estado;
   }

   public String getNivelAdministracion() {
      return nivelAdministracion;
   }

   public void setNivelAdministracion(String nivelAdministracion) {
      this.nivelAdministracion = nivelAdministracion;
   }

   public String getTipoOficina() {
      return tipoOficina;
   }

   public void setTipoOficina(String tipoOficina) {
      this.tipoOficina = tipoOficina;
   }

   public String getUnidadResponsable() {
      return unidadResponsable;
   }

   public void setUnidadResponsable(String unidadResponsable) {
      this.unidadResponsable = unidadResponsable;
   }

   public String getOficinaResponsable() {
      return oficinaResponsable;
   }

   public void setOficinaResponsable(String oficinaResponsable) {
      this.oficinaResponsable = oficinaResponsable;
   }

   public String getHorarioAtencion() {
      return horarioAtencion;
   }

   public void setHorarioAtencion(String horarioAtencion) {
      this.horarioAtencion = horarioAtencion;
   }

   public String getDiasSinHabiles() {
      return diasSinHabiles;
   }

   public void setDiasSinHabiles(String diasSinHabiles) {
      this.diasSinHabiles = diasSinHabiles;
   }

   public String getObservaciones() {
      return observaciones;
   }

   public void setObservaciones(String observaciones) {
      this.observaciones = observaciones;
   }

   public String getFechaAltaOficial() {
      return fechaAltaOficial;
   }

   public void setFechaAltaOficial(String fechaAltaOficial) {
      this.fechaAltaOficial = fechaAltaOficial;
   }

   public String getFechaExtincion() {
      return fechaExtincion;
   }

   public void setFechaExtincion(String fechaExtincion) {
      this.fechaExtincion = fechaExtincion;
   }

   public String getFechaAnulacion() {
      return fechaAnulacion;
   }

   public void setFechaAnulacion(String fechaAnulacion) {
      this.fechaAnulacion = fechaAnulacion;
   }

   public String getPais() {
      return pais;
   }

   public void setPais(String pais) {
      this.pais = pais;
   }

   public String getComunidad() {
      return comunidad;
   }

   public void setComunidad(String comunidad) {
      this.comunidad = comunidad;
   }

   public String getMunicipio() {
      return municipio;
   }

   public void setMunicipio(String municipio) {
      this.municipio = municipio;
   }

   public String getNombreVia() {
      return nombreVia;
   }

   public void setNombreVia(String nombreVia) {
      this.nombreVia = nombreVia;
   }

   public String getNumeroVia() {
      return numeroVia;
   }

   public void setNumeroVia(String numeroVia) {
      this.numeroVia = numeroVia;
   }

   public String getTipoVia() {
      return tipoVia;
   }

   public void setTipoVia(String tipoVia) {
      this.tipoVia = tipoVia;
   }

   public String getCodigoPostal() {
      return codigoPostal;
   }

   public void setCodigoPostal(String codigoPostal) {
      this.codigoPostal = codigoPostal;
   }

   public String getServicios() {
      return servicios;
   }

   public void setServicios(String servicios) {
      this.servicios = servicios;
   }

   public List<String> getContactos() {
      return contactos;
   }

   public void setContactos(List<String> contactos) {
      this.contactos = contactos;
   }
}
