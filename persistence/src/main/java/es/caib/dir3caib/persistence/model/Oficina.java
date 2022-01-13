package es.caib.dir3caib.persistence.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
/**
 * @version 1.1
 * @created 28-oct-2013 14:41:39
 */
@Entity
@Table(name = "DIR_OFICINA")
@org.hibernate.annotations.Table(appliesTo = "DIR_OFICINA", indexes = {
    @Index(name="DIR_OFICINA_CATTIPOVIA_FK_I", columnNames = {"TIPOVIA"}),
    @Index(name="DIR_OFICINA_CATPAIS_FK_I", columnNames = {"CODPAIS"}),
    @Index(name="DIR_OFICINA_OFICINA_FK_I", columnNames = {"CODOFIRESPONSABLE"}),
    @Index(name="DIR_OFICINA_CATJERAROFI_FK_I", columnNames = {"TIPOOFICINA"}),
    @Index(name="DIR_OFICINA_CATCOMUNIAUT_FK_I", columnNames = {"CODCOMUNIDAD"}),
    @Index(name="DIR_OFICINA_CATNIVELADMIN_FK_I", columnNames = {"NIVELADMINISTRACION"}),
    @Index(name="DIR_OFICINA_CATESTENT_FK_I", columnNames = {"ESTADO"}),
    @Index(name="DIR_OFICINA_CATLOCAL_FK_I", columnNames = {"LOCALIDADID"}),
    @Index(name="DIR_OFICINA_UNIDAD_FK_I", columnNames = {"CODUORESPONSABLE"}),
    @Index(name="DIR_OFICINA_CFUEXT_FK_I", columnNames = {"FUENTEEXTERNA"})
})
public class Oficina implements Serializable {

	private String codigo;
	private String denominacion;
    private String denomlenguacooficial;
    private int idiomalengua;
	private CatEstadoEntidad estado;
	private CatNivelAdministracion nivelAdministracion;
	private CatJerarquiaOficina tipoOficina;
	private Unidad codUoResponsable;
	private Oficina codOfiResponsable;
   // private CatTipoCodigoFuenteExterna fuenteExterna; //TODO PEDIR A MADRID, no cuadra con el manual de atributos.
    private String fuenteExterna;
	private String horarioAtencion;
	private String diasSinHabiles;
	private String observaciones;
	private Date fechaAltaOficial;
	private Date fechaExtincion;
	private Date fechaAnulacion;
    private Date fechaImportacion;
    private Date fechaUltimaActualizacion;
    private CatTipoVia tipoVia;
    private String nombreVia;
    private String numVia;
	private String complemento;
	private String codPostal;
	private CatPais codPais;
	private CatComunidadAutonoma codComunidad;
	private CatLocalidad localidad;
	private String dirExtranjera;
	private String locExtranjera;
	private String direccionObservaciones;
	private List<RelacionSirOfi> sirOfi;
	private List<RelacionOrganizativaOfi> organizativasOfi;
	private Set<ServicioOfi> servicios;
    private List<ContactoOfi> contactos;
    private Set<Oficina> historicosOfi;

    private Set<HistoricoOfi> historicosAnterior;

    private Set<HistoricoOfi> historicosUltima;

	public Oficina(){

	}

  public Oficina(String codigo) {
    this.codigo = codigo;
  }

  public Oficina(String codigo, String denominacion, String codUoResponsable, String codOfiResponsable) {

    this.codigo = codigo;
    this.denominacion = denominacion;
    this.codUoResponsable = new Unidad(codUoResponsable);
    this.codOfiResponsable = new Oficina(codOfiResponsable);
  }

  public Oficina(String codigo, String denominacion, String codUoResponsable, String codOfiResponsable, Set<ServicioOfi> servicios) {
    this.codigo = codigo;
    this.denominacion = denominacion;
    this.codUoResponsable = new Unidad(codUoResponsable);
    this.codOfiResponsable = new Oficina(codOfiResponsable);
    this.servicios = servicios;
  }

  public void finalize() throws Throwable {

	}

  /**
   * @return the codigo
   */
  @Column(name = "CODIGO", nullable = false, length = 9)
  @Id
  public String getCodigo() {
    return codigo;
  }

  /**
   * @param codigo the codigo to set
   */
  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  /**
   * @return the denominacion
   */
  @Column(name = "DENOMINACION", length = 300)
  public String getDenominacion() {
    return denominacion;
  }

  /**
   * @param denominacion the denominacion to set
   */
  public void setDenominacion(String denominacion) {
    this.denominacion = denominacion;
  }

  @Column(name = "DENOMCOOFICIAL", length = 300)
  public String getDenomlenguacooficial() {
    return denomlenguacooficial;
  }

  public void setDenomlenguacooficial(String denomlenguacooficial) {
    this.denomlenguacooficial = denomlenguacooficial;
  }

  @Column(name = "IDIOMALENGUA")
  @JsonIgnore
  public int getIdiomalengua() {
    return idiomalengua;
  }

  public void setIdiomalengua(int idiomalengua) {
    this.idiomalengua = idiomalengua;
  }

  /**
   * @return the estado
   */
  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_OFICINA_CATESTENT_FK")
  @JsonIgnore
  public CatEstadoEntidad getEstado() {
    return estado;
  }

  /**
   * @param estado the estado to set
   */
  public void setEstado(CatEstadoEntidad estado) {
    this.estado = estado;
  }

  /**
   * @return the nivelAdministracion
   */
  @ManyToOne
  @JoinColumn(name="NIVELADMINISTRACION")
  @ForeignKey(name="DIR_OFICINA_CATNIVELADMIN_FK")
  @JsonIgnore
  public CatNivelAdministracion getNivelAdministracion() {
    return nivelAdministracion;
  }

  /**
   * @param nivelAdministracion the nivelAdministracion to set
   */
  public void setNivelAdministracion(CatNivelAdministracion nivelAdministracion) {
    this.nivelAdministracion = nivelAdministracion;
  }

  /**
   * @return the tipoOficina
   */
  @ManyToOne
  @JoinColumn(name="TIPOOFICINA")
  @ForeignKey(name="DIR_OFICINA_CATJERAROFI_FK")
  @JsonIgnore
  public CatJerarquiaOficina getTipoOficina() {
    return tipoOficina;
  }

  /**
   * @param tipoOficina the tipoOficina to set
   */
  public void setTipoOficina(CatJerarquiaOficina tipoOficina) {
    this.tipoOficina = tipoOficina;
  }

  /**
   * @return the codUoResponsable
   */
  @ManyToOne
  @JoinColumn(name="CODUORESPONSABLE")   
  @ForeignKey(name="DIR_OFICINA_UNIDAD_FK")
  @JsonIgnore
  public Unidad getCodUoResponsable() {
    return codUoResponsable;
  }

  /**
   * @param codUoResponsable the codUoResponsable to set
   */
  public void setCodUoResponsable(Unidad codUoResponsable) {
    this.codUoResponsable = codUoResponsable;
  }

  /**
   * @return the codOfiResponsable
   */
  @ManyToOne
  @JoinColumn(name="CODOFIRESPONSABLE")   
  @ForeignKey(name="DIR_OFICINA_OFICINA_FK")
  @JsonIgnore
  public Oficina getCodOfiResponsable() {
    return codOfiResponsable;
  }

  /**
   * @param codOfiResponsable the codOfiResponsable to set
   */
  public void setCodOfiResponsable(Oficina codOfiResponsable) {
    this.codOfiResponsable = codOfiResponsable;
  }

  @Column(name = "FUENTEEXTERNA" ,length=40)
  public String getFuenteExterna() {
    return fuenteExterna;
  }

  public void setFuenteExterna(String fuenteExterna) {
    this.fuenteExterna = fuenteExterna;
  }


  //Revisar con Madrid
  /*@ManyToOne()
  @JoinColumn(name="FUENTEEXTERNA")
  @ForeignKey(name="DIR_OFICINA_CFUEXT_FK")
  @JsonIgnore
  public CatTipoCodigoFuenteExterna getFuenteExterna() {
    return fuenteExterna;
  }

  public void setFuenteExterna(CatTipoCodigoFuenteExterna fuenteExterna) {
    this.fuenteExterna = fuenteExterna;
  }*/

  /**
   * @return the horarioAtencion
   */
  @Column(name = "HORARIOANTENCION", length = 400)
  @JsonIgnore
  public String getHorarioAtencion() {
    return horarioAtencion;
  }

  /**
   * @param horarioAtencion the horarioAtencion to set
   */
  public void setHorarioAtencion(String horarioAtencion) {
    this.horarioAtencion = horarioAtencion;
  }

  /**
   * @return the diasSinHabiles
   */
  @Column(name = "DIASSINHABILES", length = 400)
  @JsonIgnore
  public String getDiasSinHabiles() {
    return diasSinHabiles;
  }

  /**
   * @param diasSinHabiles the diasSinHabiles to set
   */
  public void setDiasSinHabiles(String diasSinHabiles) {
    this.diasSinHabiles = diasSinHabiles;
  }

  /**
   * @return the observaciones
   */
  @Column(name = "OBSERVACIONES",  length = 400)
  @JsonIgnore
  public String getObservaciones() {
    return observaciones;
  }

  /**
   * @param observaciones the observaciones to set
   */
  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  /**
   * @return the fechaAltaOficial
   */
  @Column(name = "FECHAALTAOFICIAL")
  //@Temporal(TemporalType.DATE)
  @JsonIgnore
  public Date getFechaAltaOficial() {
    return fechaAltaOficial;
  }

  /**
   * @param fechaAltaOficial the fechaAltaOficial to set
   */
  public void setFechaAltaOficial(Date fechaAltaOficial) {
    this.fechaAltaOficial = fechaAltaOficial;
  }

  /**
   * @return the fechaExtincion
   */
  @Column(name = "FECHAEXTINCION")
 // @Temporal(TemporalType.DATE)
  @JsonIgnore
  public Date getFechaExtincion() {
    return fechaExtincion;
  }

  /**
   * @param fechaExtincion the fechaExtincion to set
   */
  public void setFechaExtincion(Date fechaExtincion) {
    this.fechaExtincion = fechaExtincion;
  }

  /**
   * @return the fechaAnulacion
   */
  @Column(name = "FECHAANULACION")
 // @Temporal(TemporalType.DATE)
  @JsonIgnore
  public Date getFechaAnulacion() {
    return fechaAnulacion;
  }

  /**
   * @param fechaAnulacion the fechaAnulacion to set
   */
  public void setFechaAnulacion(Date fechaAnulacion) {
    this.fechaAnulacion = fechaAnulacion;
  }

  /**
    * @return the fechaAnulacion
    */
   @Column(name = "FECHAIMPORTACION")
   //@Temporal(TemporalType.DATE)
   @JsonIgnore
   public Date getFechaImportacion() {
     return fechaImportacion;
   }

   /**
    * @param fechaImportacion the fechaImportacion to set
    */
   public void setFechaImportacion(Date fechaImportacion) {
     this.fechaImportacion = fechaImportacion;
   }

  @Column(name = "FECHULTACTUALI")
  @JsonIgnore
  public Date getFechaUltimaActualizacion() {
    return fechaUltimaActualizacion;
  }

  public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
    this.fechaUltimaActualizacion = fechaUltimaActualizacion;
  }


  /**
   * @return the tipoVia
   */
  @ManyToOne
  @JoinColumn(name="TIPOVIA")
  @ForeignKey(name="DIR_OFICINA_CATTIPOVIA_FK")
  @JsonIgnore
  public CatTipoVia getTipoVia() {
    return tipoVia;
  }

  /**
   * @param tipoVia the tipoVia to set
   */
  public void setTipoVia(CatTipoVia tipoVia) {
    this.tipoVia = tipoVia;
  }

  /**
   * @return the nombreVia
   */
  @Column(name = "NOMBREVIA", length = 300)
  @JsonIgnore
  public String getNombreVia() {
    return nombreVia;
  }

  /**
   * @param nombreVia the nombreVia to set
   */
  public void setNombreVia(String nombreVia) {
    this.nombreVia = nombreVia;
  }

  /**
   * @return the numVia
   */
  @Column(name = "NUMVIA", length = 20)
  @JsonIgnore
  public String getNumVia() {
    return numVia;
  }

  /**
   * @param numVia the numVia to set
   */
  public void setNumVia(String numVia) {
    this.numVia = numVia;
  }

  /**
   * @return the complemento
   */
  @Column(name = "COMPLEMENTO",  length = 300)
  @JsonIgnore
  public String getComplemento() {
    return complemento;
  }

  /**
   * @param complemento the complemento to set
   */
  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }

  /**
   * @return the codPostal
   */
  @Column(name = "CODPOSTAL", length = 14)
  @JsonIgnore
  public String getCodPostal() {
    return codPostal;
  }

  /**
   * @param codPostal the codPostal to set
   */
  public void setCodPostal(String codPostal) {
    this.codPostal = codPostal;
  }

  /**
   * @return the codPais
   */
  @ManyToOne
  @JoinColumn(name="CODPAIS")  
  @ForeignKey(name="DIR_OFICINA_CATPAIS_FK")
  @JsonIgnore
  public CatPais getCodPais() {
    return codPais;
  }

  /**
   * @param codPais the codPais to set
   */
  public void setCodPais(CatPais codPais) {
    this.codPais = codPais;
  }

  /**
   * @return the codComunidad
   */
  @ManyToOne
  @JoinColumn(name="CODCOMUNIDAD") 
  @ForeignKey(name="DIR_OFICINA_CATCOMUNIAUT_FK")
  @JsonIgnore
  public CatComunidadAutonoma getCodComunidad() {
    return codComunidad;
  }

  /**
   * @param codComunidad the codComunidad to set
   */
  public void setCodComunidad(CatComunidadAutonoma codComunidad) {
    this.codComunidad = codComunidad;
  }

  /**
   * @return the codLocalidad
   */
  @ManyToOne
  @JoinColumn(name="LOCALIDADID")
  @ForeignKey(name="DIR_OFICINA_CATLOCAL_FK")
  @JsonIgnore
  public CatLocalidad getLocalidad() {
    return localidad;
  }

  /**
   * @param localidad the codLocalidad to set
   */
  public void setLocalidad(CatLocalidad localidad) {
    this.localidad = localidad;
  }

  /**
   * @return the dirExtranjera
   */
  @Column(name = "DIREXTRANJERA", length = 200)
  @JsonIgnore
  public String getDirExtranjera() {
    return dirExtranjera;
  }

  /**
   * @param dirExtranjera the dirExtranjera to set
   */
  public void setDirExtranjera(String dirExtranjera) {
    this.dirExtranjera = dirExtranjera;
  }

  /**
   * @return the locExtranjera
   */
  @Column(name = "LOCEXTRANJERA", length = 40)
  @JsonIgnore
  public String getLocExtranjera() {
    return locExtranjera;
  }

  /**
   * @param locExtranjera the locExtranjera to set
   */
  public void setLocExtranjera(String locExtranjera) {
    this.locExtranjera = locExtranjera;
  }

  /**
   * @return the direccionObservaciones
   */
  @Column(name = "DIRECCIONOBSERVACIONES", length = 400)
  @JsonIgnore
  public String getDireccionObservaciones() {
    return direccionObservaciones;
  }

  /**
   * @param direccionObservaciones the direccionObservaciones to set
   */
  public void setDireccionObservaciones(String direccionObservaciones) {
    this.direccionObservaciones = direccionObservaciones;
  }

  /**
   * @return the sirOfi
   */
  @OneToMany(mappedBy="oficina")
  //@ForeignKey(name="DIR_OFICINA_RELSIROFI_FK")
  @JsonIgnore
  public List<RelacionSirOfi> getSirOfi() {
    return sirOfi;
  }

  /**
   * @param sirOfi the sirOfi to set
   */
  public void setSirOfi(List<RelacionSirOfi> sirOfi) {
    this.sirOfi = sirOfi;
  }

  /**
   * @return the organizativasOfi
   */

  @OneToMany(mappedBy = "oficina" )
  @JsonIgnore
  public List<RelacionOrganizativaOfi> getOrganizativasOfi() {
    return organizativasOfi;
  }

  /**
   * @param organizativasOfi the organizativasOfi to set
   */
  public void setOrganizativasOfi(List<RelacionOrganizativaOfi> organizativasOfi) {
    this.organizativasOfi = organizativasOfi;
  }

  /**
   * @return the servicios
   */
  /*@ManyToMany(cascade=CascadeType.PERSIST, fetch= FetchType.EAGER)
  @JoinTable(name="DIR_SERVICIOOFI", 
        joinColumns=
            @JoinColumn(name="CODOFICINA"),
        inverseJoinColumns=
            @JoinColumn(name="CODSERVICIO"))
  @ForeignKey(name="DIR_SER_OFI_FK", inverseName = "DIR_OFI_SERV_FK")
  @JsonIgnore
  public Set<CatServicio> getServicios() {
    return servicios;
  }

  *//**
   * @param servicios the servicios to set
   *//*
  public void setServicios(Set<CatServicio> servicios) {
    this.servicios = servicios;
  }*/

  @OneToMany(mappedBy = "oficina")
  @JsonIgnore
  public Set<ServicioOfi> getServicios() {
    return servicios;
  }

  public void setServicios(Set<ServicioOfi> servicios) {
    this.servicios = servicios;
  }

  /**
   * @return the contactos
   */
  @OneToMany (mappedBy = "oficina")
  @ForeignKey(name="DIR_OFICINA_CONTACTOSOFI_FK")
  @JsonIgnore
  public List<ContactoOfi> getContactos() {
    return contactos;
  }

  /**
   * @param contactos the contactos to set
   */
  public void setContactos(List<ContactoOfi> contactos) {
    this.contactos = contactos;
  }

  /**
   * @return the historicosOfi
   */
  //TODO ELIMINAR
  /*@ManyToMany(cascade=CascadeType.PERSIST, fetch= FetchType.EAGER)
  @JoinTable(name="DIR_HISTORICOOFI",
               joinColumns=@JoinColumn(name="CODANTERIOR"),
               inverseJoinColumns=@JoinColumn(name="CODULTIMA"))
  @ForeignKey(name="DIR_OFI_OFI_HISTANTE_FK", inverseName = "DIR_OFI_OFI_HISTULTI_FK")
  @JsonIgnore
  public Set<Oficina> getHistoricosOfi() {
    return historicosOfi;
  }

  *//**
   * @param historicosOfi the historicosOfi to set
   *//*
  public void setHistoricosOfi(Set<Oficina> historicosOfi) {
    this.historicosOfi = historicosOfi;
  }*/

  @OneToMany(mappedBy = "oficinaAnterior")
  @JsonIgnore
  public Set<HistoricoOfi> getHistoricosAnterior() {
    return historicosAnterior;
  }

  public void setHistoricosAnterior(Set<HistoricoOfi> historicosAnterior) {
    this.historicosAnterior = historicosAnterior;
  }

  @OneToMany(mappedBy = "oficinaUltima")
  @JsonIgnore
  public Set<HistoricoOfi> getHistoricosUltima() {
    return historicosUltima;
  }

  public void setHistoricosUltima(Set<HistoricoOfi> historicosUltima) {
    this.historicosUltima = historicosUltima;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Oficina oficina = (Oficina) o;
    return codigo.equals(oficina.codigo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo);
  }

 /* @Transient
  public Boolean getOficinaSir() {

    for (CatServicio servicio : servicios) {
      if (servicio.getCodServicio().equals(Dir3caibConstantes.SERVICIO_SIR) ||
         servicio.getCodServicio().equals(Dir3caibConstantes.SERVICIO_SIR_ENVIO) ||
         servicio.getCodServicio().equals(Dir3caibConstantes.SERVICIO_SIR_RECEPCION)) {
        return true;
      }
    }
    return false;
  }*/

  @Transient
  public Boolean getOficinaSir() {

    for (ServicioOfi servicio : servicios) {
      if (servicio.getServicio().getCodServicio().equals(Dir3caibConstantes.SERVICIO_SIR) ||
              servicio.getServicio().getCodServicio().equals(Dir3caibConstantes.SERVICIO_SIR_ENVIO) ||
              servicio.getServicio().getCodServicio().equals(Dir3caibConstantes.SERVICIO_SIR_RECEPCION)) {
        return true;
      }
    }
    return false;
  }

  /*@Transient
  public Boolean getOficinaInformacion() {

    for (CatServicio servicio : servicios) {
      if (servicio.getCodServicio().equals(Dir3caibConstantes.SERVICIO_OFI_INFORMACION)) {
        return true;
      }
    }
    return false;
  }*/

  @Transient
  public Boolean getOficinaInformacion() {

    for (ServicioOfi servicio : servicios) {
      if (servicio.getServicio().getCodServicio().equals(Dir3caibConstantes.SERVICIO_OFI_INFORMACION)) {
        return true;
      }
    }
    return false;
  }




}