package es.caib.dir3caib.persistence.model.json;

import java.util.List;

/**
 * Created by mgonzalez on 19/12/2018.
 */
public class OficinaJson {
   private String codigo;
   private String denominacion;
   private String estado;    //CatEstadoEntidad
   private String nivelAdministracion;  //CatNivelAdministracion
   private String tipoOficina;  //CatJerarquiaOficina
   private String codUoResponsable;      //Unidad
   private String codOfiResponsable;   //Oficina
   private String horarioAtencion;
   private String diasSinHabiles;
   private String observaciones;
   private String fechaAltaOficial;
   private String fechaExtincion;
   private String fechaAnulacion;
   private String pais;
   private String comunidad;
   private String descripcionLocalidad; //LOCALIDAD
   private String nombreVia;
   private String numVia;
   private String tipoVia;
   private String codPostal;

   private List<String> servicios;
   private List<String> contactos;


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

   public String getCodUoResponsable() {
      return codUoResponsable;
   }

   public void setCodUoResponsable(String codUoResponsable) {
      this.codUoResponsable = codUoResponsable;
   }

   public String getCodOfiResponsable() {
      return codOfiResponsable;
   }

   public void setCodOfiResponsable(String codOfiResponsable) {
      this.codOfiResponsable = codOfiResponsable;
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

   public String getDescripcionLocalidad() {
      return descripcionLocalidad;
   }

   public void setDescripcionLocalidad(String descripcionLocalidad) {
      this.descripcionLocalidad = descripcionLocalidad;
   }

   public String getNombreVia() {
      return nombreVia;
   }

   public void setNombreVia(String nombreVia) {
      this.nombreVia = nombreVia;
   }

   public String getNumVia() {
      return numVia;
   }

   public void setNumVia(String numVia) {
      this.numVia = numVia;
   }

   public String getTipoVia() {
      return tipoVia;
   }

   public void setTipoVia(String tipoVia) {
      this.tipoVia = tipoVia;
   }

   public String getCodPostal() {
      return codPostal;
   }

   public void setCodPostal(String codPostal) {
      this.codPostal = codPostal;
   }

   public List<String> getServicios() {
      return servicios;
   }

   public void setServicios(List<String> servicios) {
      this.servicios = servicios;
   }

   public List<String> getContactos() {
      return contactos;
   }

   public void setContactos(List<String> contactos) {
      this.contactos = contactos;
   }
}
