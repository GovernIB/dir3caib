package es.caib.dir3caib.persistence.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


/**
 * @version 1.1
 * @created 28-oct-2013 14:41:39
 */
@Entity
@Table(name = "DIR_UNIDAD" ,
        uniqueConstraints= @UniqueConstraint(columnNames={"CODIGO", "VERSION"}))
@org.hibernate.annotations.Table(appliesTo = "DIR_UNIDAD", indexes = {
    @Index(name="DIR_UNIDAD_CATPROVINCIA_FK_I", columnNames = {"CODAMBPROVINCIA"}),
    @Index(name="DIR_UNIDAD_UNIDADRAIZ_FK_I", columnNames = {"CODUNIDADRAIZ"}),
    @Index(name="DIR_UNIDAD_CATAMBCOMAUTO_FK_I", columnNames = {"CODAMBCOMUNIDAD"}),
    @Index(name="DIR_UNIDAD_CATCOMUNIAUTO_FK_I", columnNames = {"CODCOMUNIDAD"}),
    @Index(name="DIR_UNIDAD_UNIDADSUPERIOR_FK_I", columnNames = {"CODUNIDADSUPERIOR"}),
    @Index(name="DIR_UNIDAD_CATTIPOVIA_FK_I", columnNames = {"TIPOVIA"}),
    @Index(name="DIR_UNIDAD_CATPAIS_FK_I", columnNames = {"CODPAIS"}),
    @Index(name="DIR_UNIDAD_CATENTGEOGRAF_FK_I", columnNames = {"CODAMBENTGEOGRAFICA"}),
    @Index(name="DIR_UNIDAD_CATAMBPAIS_FK_I", columnNames = {"CODAMBPAIS"}),
    @Index(name="DIR_UNIDAD_CATTIPENTPUBL_FK_I", columnNames = {"CODTIPOENTPUBLICA"}),
    @Index(name="DIR_UNIDAD_MUNICIPIO_FK_I", columnNames = {"CODAMBLOCALIDADID"}),
    @Index(name="DIR_UNIDAD_CATISLA_FK_I", columnNames = {"CODAMBISLA"}),
    @Index(name="DIR_UNIDAD_CATTIPUNIORG_FK_I", columnNames = {"CODTIPOUNIDAD"}),
    @Index(name="DIR_UNIDAD_CATNIVELADMIN_FK_I", columnNames = {"NIVELADMINISTRACION"}),
    @Index(name="DIR_UNIDAD_CATESTENTIDAD_FK_I", columnNames = {"ESTADO"}),
    @Index(name="DIR_UNIDAD_CATAMBITTERR_FK_I", columnNames = {"CODAMBITOTERRITORIALID"}),
    @Index(name="DIR_UNIDAD_CATLOCAL_FK_I", columnNames = {"CODLOCALIDADID"}),
    @Index(name="DIR_UNIDAD_UNIDADEDPPRINC_FK_I", columnNames = {"CODEDPPRINCIPAL"}),
})
@SequenceGenerator(name="generator",sequenceName = "DIR_UNI_SEQ", allocationSize=1)
public class Unidad implements Serializable {

  private String codigo; // contiene el string codigo+"v"+version
  private Long version;
  private String codigoDir3;
  private String denominacion;
  private String denomLenguaCooficial;
  private int idiomalengua;  //idioma de la lengua cooficial
  private CatEstadoEntidad estado;
  private String nifcif;
  private String siglas;
  private CatNivelAdministracion nivelAdministracion;
  private Long nivelJerarquico;
  private CatPoder poder;
  private Unidad codUnidadSuperior;
  private Unidad codUnidadRaiz;
  private boolean esEdp;
  private Unidad codEdpPrincipal;
  private CatTipoEntidadPublica codTipoEntPublica;
  private CatTipoUnidadOrganica codTipoUnidad;
  private CatAmbitoTerritorial codAmbitoTerritorial;
  private CatEntidadGeografica codAmbEntGeografica;
  private CatPais codAmbPais;
  private CatComunidadAutonoma codAmbComunidad;
  private CatProvincia codAmbProvincia;
  private CatLocalidad catLocalidad;
  private CatIsla codAmbIsla;
  private Long codAmbElm;
  private String codAmbLocExtranjera;
  private String competencias;
  private String disposicionLegal;
  private Date fechaAltaOficial;
  private Date fechaBajaOficial;
  private Date fechaExtincion;
  private Date fechaAnulacion;
  private Date fechaUltimaActualizacion;
  private Date fechaImportacion;
  private String codExterno;
  private String observGenerales;
  private String observBaja;
  private CatTipoVia tipoVia;
  private String nombreVia;
  private String numVia;
  private String complemento;
  private String codPostal;
  private CatPais codPais;
  private CatComunidadAutonoma codComunidad;
  private CatLocalidad codLocalidad;
  private String dirExtranjera;
  private String locExtranjera;
  private String observaciones;
  private boolean comparteNif;
  private List<ContactoUnidadOrganica> contactos;
  private List<RelacionOrganizativaOfi> organizativaOfi;
  private List<RelacionSirOfi> sirOfi;
  private Set<ServicioUO> servicios;

  // TODO ELIMINAR
//  private Set<Unidad> historicoUO;

  private Set<HistoricoUO> historicosAnterior;

  private Set<HistoricoUO> historicosUltima;

  private Set<CodigoUnidadOrganica> codigosExternos;
  private Set<NifCifUnidadOrganica> nifcifUo;


  public Unidad(){
    //TODO ELIMINAR
    //this.historicoUO = new HashSet<Unidad>();
    this.historicosAnterior = new HashSet<>();
    this.historicosUltima = new HashSet<>();
  }



  public Unidad(String codigo, String denominacion) {
    this.codigo = codigo;
    this.denominacion = denominacion;
  }

  public Unidad(String codigo, String denominacion, String codUnidadRaiz) {
    this.codigo = codigo;
    this.denominacion = denominacion;
    this.codUnidadRaiz = new Unidad(codUnidadRaiz);
  }

  public Unidad(String codigo, String denominacion, Unidad codUnidadRaiz, Unidad codUnidadSuperior, boolean esEdp) {
    this.codigo = codigo;
    this.denominacion = denominacion;
    this.codUnidadSuperior = codUnidadSuperior;
    this.codUnidadRaiz = codUnidadRaiz;
    this.esEdp = esEdp;
  }

   public Unidad(String codigo, String denominacion, String codUnidadRaiz, String codUnidadSuperior, boolean esEdp) {
      this.codigo = codigo;
      this.denominacion = denominacion;
      this.codUnidadSuperior = new Unidad(codUnidadSuperior);
      this.codUnidadRaiz = new Unidad(codUnidadRaiz);
      this.esEdp = esEdp;
   }

  public Unidad(String codigo) {
    this.codigo = codigo;
  }


  @Index (name="DIR_UNIDAD_PK_I")
  @Column(name = "CODIGO", nullable = false, length = 20)
  @Id
  public String getCodigo() {
    return codigo;
  }


  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  @Column(name = "VERSION", nullable = false)
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Column(name = "CODIGODIR3", nullable = false, length = 9)
  public String getCodigoDir3() {
    return codigoDir3;
  }

  public void setCodigoDir3(String codigoDir3) {
    this.codigoDir3 = codigoDir3;
  }

  @Column(name = "DENOMINACION", length = 300)
  public String getDenominacion() {
    return denominacion;
  }


  public void setDenominacion(String denominacion) {
    this.denominacion = denominacion;
  }

  @Column(name = "DENOMCOOFICIAL", length = 300)
  public String getDenomLenguaCooficial() {
    return denomLenguaCooficial;
  }

  public void setDenomLenguaCooficial(String denomLenguaCooficial) {
    this.denomLenguaCooficial = denomLenguaCooficial;
  }

  @Column(name = "IDIOMALENGUA")
  @JsonIgnore
  public int getIdiomalengua() {
    return idiomalengua;
  }

  public void setIdiomalengua(int idiomalengua) {
    this.idiomalengua = idiomalengua;
  }

  @ManyToOne()
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_UNIDAD_CATESTENTIDAD_FK")
  @JsonIgnore
  public CatEstadoEntidad getEstado() {
    return estado;
  }


  public void setEstado(CatEstadoEntidad estado) {
    this.estado = estado;
  }


  @Column(name = "NIFCIF", length = 9)
  @JsonIgnore
  public String getNifcif() {
    return nifcif;
  }


  public void setNifcif(String nifcif) {
    this.nifcif = nifcif;
  }


  @Column(name = "SIGLAS",  length = 10)
  @JsonIgnore
  public String getSiglas() {
    return siglas;
  }


  public void setSiglas(String siglas) {
    this.siglas = siglas;
  }


  @ManyToOne()
  @JoinColumn(name="NIVELADMINISTRACION" ,referencedColumnName = "CODIGONIVELADMINISTRACION")
  @ForeignKey(name="DIR_UNIDAD_CATNIVELADMIN_FK")
  @JsonIgnore
  public CatNivelAdministracion getNivelAdministracion() {
    return nivelAdministracion;
  }


  public void setNivelAdministracion(CatNivelAdministracion nivelAdministracion) {
    this.nivelAdministracion = nivelAdministracion;
  }


  @Column(name = "NIVELJERARQUICO", length = 6)
  @JsonIgnore
  public Long getNivelJerarquico() {
    return nivelJerarquico;
  }


  public void setNivelJerarquico(Long nivelJerarquico) {
    this.nivelJerarquico = nivelJerarquico;
  }

  @ManyToOne
  @JoinColumn(name="PODER" ,referencedColumnName = "CODIGOPODER")
  @ForeignKey(name="DIR_UNIDAD_CATPODER_FK")
  @JsonIgnore
  public CatPoder getPoder() {
    return poder;
  }

  public void setPoder(CatPoder poder) {
    this.poder = poder;
  }

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name="CODUNIDADSUPERIOR")
  @ForeignKey(name="DIR_UNIDAD_UNIDADSUPERIOR_FK")
  @JsonIgnore
  public Unidad getCodUnidadSuperior() {
    return codUnidadSuperior;
  }


  public void setCodUnidadSuperior(Unidad codUnidadSuperior) {
    this.codUnidadSuperior = codUnidadSuperior;
  }



  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name="CODUNIDADRAIZ")
  @ForeignKey(name="DIR_UNIDAD_UNIDADRAIZ_FK")
  @JsonIgnore
  public Unidad getCodUnidadRaiz() {
    return codUnidadRaiz;
  }


  public void setCodUnidadRaiz(Unidad codUnidadRaiz) {
    this.codUnidadRaiz = codUnidadRaiz;
  }


  @Column(name = "ESEDP", length = 1)
  @JsonIgnore
  public boolean isEsEdp() {
    return esEdp;
  }


  public void setEsEdp(boolean esEdp) {
    this.esEdp = esEdp;
  }


  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn(name="CODEDPPRINCIPAL")
  @ForeignKey(name="DIR_UNIDAD_UNIDADEDPPRINC_FK")
  @JsonIgnore
  public Unidad getCodEdpPrincipal() {
    return codEdpPrincipal;
  }


  public void setCodEdpPrincipal(Unidad codEdpPrincipal) {
    this.codEdpPrincipal = codEdpPrincipal;
  }


  @ManyToOne()
  @JoinColumn(name="CODTIPOENTPUBLICA")  
  @ForeignKey(name="DIR_UNIDAD_CATTIPENTPUBL_FK")
  @JsonIgnore
  public CatTipoEntidadPublica getCodTipoEntPublica() {
    return codTipoEntPublica;
  }


  public void setCodTipoEntPublica(CatTipoEntidadPublica codTipoEntPublica) {
    this.codTipoEntPublica = codTipoEntPublica;
  }


  @ManyToOne()
  @JoinColumn(name="CODTIPOUNIDAD") 
  @ForeignKey(name="DIR_UNIDAD_CATTIPUNIORG_FK_I")
  @JsonIgnore
  public CatTipoUnidadOrganica getCodTipoUnidad() {
    return codTipoUnidad;
  }


  public void setCodTipoUnidad(CatTipoUnidadOrganica codTipoUnidad) {
    this.codTipoUnidad = codTipoUnidad;
  }


  @ManyToOne()
  @JoinColumn(name="CODAMBITOTERRITORIALID")
  @ForeignKey(name="DIR_UNIDAD_CATAMBITTERR_FK")
  @JsonIgnore
  public CatAmbitoTerritorial getCodAmbitoTerritorial() {
    return codAmbitoTerritorial;
  }


  public void setCodAmbitoTerritorial(CatAmbitoTerritorial codAmbitoTerritorial) {
    this.codAmbitoTerritorial = codAmbitoTerritorial;
  }


  @ManyToOne
  @JoinColumn(name="CODAMBENTGEOGRAFICA", referencedColumnName = "CODIGOENTIDADGEOGRAFICA")
  @ForeignKey(name="DIR_UNIDAD_CATENTGEOGRAF_FK")
  @JsonIgnore
  public CatEntidadGeografica getCodAmbEntGeografica() {
    return codAmbEntGeografica;
  }


  public void setCodAmbEntGeografica(CatEntidadGeografica codAmbEntGeografica) {
    this.codAmbEntGeografica = codAmbEntGeografica;
  }


  @ManyToOne()
  @JoinColumn(name="CODAMBPAIS" , referencedColumnName = "CODIGOPAIS")
  @ForeignKey(name="DIR_UNIDAD_CATAMBPAIS_FK")
  @JsonIgnore
  public CatPais getCodAmbPais() {
    return codAmbPais;
  }


  public void setCodAmbPais(CatPais codAmbPais) {
    this.codAmbPais = codAmbPais;
  }


  @ManyToOne()
  @JoinColumn(name="CODAMBCOMUNIDAD", referencedColumnName = "CODIGOCOMUNIDAD")
  @ForeignKey(name="DIR_UNIDAD_CATAMBCOMAUTO_FK")
  @JsonIgnore
  public CatComunidadAutonoma getCodAmbComunidad() {
    return codAmbComunidad;
  }

  public void setCodAmbComunidad(CatComunidadAutonoma codAmbComunidad) {
    this.codAmbComunidad = codAmbComunidad;
  }


  @ManyToOne()
  @JoinColumn(name="CODAMBPROVINCIA")
  @ForeignKey(name="DIR_UNIDAD_CATPROVINCIA_FK")
  @JsonIgnore
  public CatProvincia getCodAmbProvincia() {
    return codAmbProvincia;
  }

  public void setCodAmbProvincia(CatProvincia codAmbProvincia) {
    this.codAmbProvincia = codAmbProvincia;
  }


  @ManyToOne()
  @JoinColumn(name="CODAMBLOCALIDADID")
  @ForeignKey(name="DIR_UNIDAD_MUNICIPIO_FK")
  @JsonIgnore
  public CatLocalidad getCatLocalidad() {
    return catLocalidad;
  }


  public void setCatLocalidad(CatLocalidad catLocalidad) {
    this.catLocalidad = catLocalidad;
  }


  @ManyToOne()
  @JoinColumn(name="CODAMBISLA") 
  @ForeignKey(name="DIR_UNIDAD_CATISLA_FK")
  @JsonIgnore
  public CatIsla getCodAmbIsla() {
    return codAmbIsla;
  }


  public void setCodAmbIsla(CatIsla codAmbIsla) {
    this.codAmbIsla = codAmbIsla;
  }
  
  
  @Column(name = "CODAMBELM",  length = 4)
  @JsonIgnore
  public Long getCodAmbElm() {
    return codAmbElm;
  }


  public void setCodAmbElm(Long codAmbElm) {
    this.codAmbElm = codAmbElm;
  }

 
 

  @Column(name = "CODAMBLOCEXTRANJERA", length = 50)
  @JsonIgnore
  public String getCodAmbLocExtranjera() {
    return codAmbLocExtranjera;
  }


  public void setCodAmbLocExtranjera(String codAmbLocExtranjera) {
    this.codAmbLocExtranjera = codAmbLocExtranjera;
  }

  /**
   * @return the competencias
   */
  @Column(name = "COMPETENCIAS", length = 400)
  @JsonIgnore
  public String getCompetencias() {
    return competencias;
  }


  public void setCompetencias(String competencias) {
    this.competencias = competencias;
  }


  @Column(name = "DISPOSICIONLEGAL", length = 400)
  @JsonIgnore
  public String getDisposicionLegal() {
    return disposicionLegal;
  }


  public void setDisposicionLegal(String disposicionLegal) {
    this.disposicionLegal = disposicionLegal;
  }


  @Column(name = "FECHAALTAOFICIAL")
  @JsonIgnore
 // @Temporal(TemporalType.DATE)
  public Date getFechaAltaOficial() {
    return fechaAltaOficial;
  }


  public void setFechaAltaOficial(Date fechaAltaOficial) {
    this.fechaAltaOficial = fechaAltaOficial;
  }


  @Column(name = "FECHABAJAOFICIAL")
  @JsonIgnore
 // @Temporal(TemporalType.DATE)
  public Date getFechaBajaOficial() {
    return fechaBajaOficial;
  }


  public void setFechaBajaOficial(Date fechaBajaOficial) {
    this.fechaBajaOficial = fechaBajaOficial;
  }

  @Column(name = "FECHAEXTINCION")
//  @Temporal(TemporalType.DATE)
  @JsonIgnore
  public Date getFechaExtincion() {
    return fechaExtincion;
  }


  public void setFechaExtincion(Date fechaExtincion) {
    this.fechaExtincion = fechaExtincion;
  }


  @Column(name = "FECHAANULACION")
 // @Temporal(TemporalType.DATE)
  @JsonIgnore
  public Date getFechaAnulacion() {
    return fechaAnulacion;
  }


  public void setFechaAnulacion(Date fechaAnulacion) {
    this.fechaAnulacion = fechaAnulacion;
  }

  @Column(name = "FECHAIMPORTACION")
 // @Temporal(TemporalType.DATE)
  @JsonIgnore
  public Date getFechaImportacion() {
    return fechaImportacion;
  }


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

  @Column(name = "CODEXTERNO", length= 40)
  @JsonIgnore
  public String getCodExterno() {
    return codExterno;
  }


  public void setCodExterno(String codExterno) {
    this.codExterno = codExterno;
  }


  @Column(name = "OBSERVGENERALES", length= 400)
  @JsonIgnore
  public String getObservGenerales() {
    return observGenerales;
  }


  public void setObservGenerales(String observGenerales) {
    this.observGenerales = observGenerales;
  }


  @Column(name = "OBSERVBAJA", length= 400)
  @JsonIgnore
  public String getObservBaja() {
    return observBaja;
  }


  public void setObservBaja(String observBaja) {
    this.observBaja = observBaja;
  }


  @ManyToOne()
  @JoinColumn(name="TIPOVIA") 
  @ForeignKey(name="DIR_UNIDAD_CATTIPOVIA_FK")
  @JsonIgnore
  public CatTipoVia getTipoVia() {
    return tipoVia;
  }


  public void setTipoVia(CatTipoVia tipoVia) {
    this.tipoVia = tipoVia;
  }


  @Column(name = "NOMBREVIA", length= 300)
  @JsonIgnore
  public String getNombreVia() {
    return nombreVia;
  }


  public void setNombreVia(String nombreVia) {
    this.nombreVia = nombreVia;
  }


  @Column(name = "NUMVIA", length= 20)
  @JsonIgnore
  public String getNumVia() {
    return numVia;
  }


  public void setNumVia(String numVia) {
    this.numVia = numVia;
  }


  @Column(name = "COMPLEMENTO", length = 300)
  @JsonIgnore
  public String getComplemento() {
    return complemento;
  }


  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }


  @Column(name = "CODPOSTAL", length = 14)
  @JsonIgnore
  public String getCodPostal() {
    return codPostal;
  }


  public void setCodPostal(String codPostal) {
    this.codPostal = codPostal;
  }


  @ManyToOne()
  @JoinColumn(name="CODPAIS")  
  @ForeignKey(name="DIR_UNIDAD_CATPAIS_FK")
  @JsonIgnore
  public CatPais getCodPais() {
    return codPais;
  }


  public void setCodPais(CatPais codPais) {
    this.codPais = codPais;
  }


  @ManyToOne()
  @JoinColumn(name="CODCOMUNIDAD") 
  @ForeignKey(name="DIR_UNIDAD_CATCOMUNIAUTO_FK")
  @JsonIgnore
  public CatComunidadAutonoma getCodComunidad() {
    return codComunidad;
  }


  public void setCodComunidad(CatComunidadAutonoma codComunidad) {
    this.codComunidad = codComunidad;
  }


 @ManyToOne()
 @JoinColumn(name="CODLOCALIDADID")
 @ForeignKey(name="DIR_UNIDAD_CATLOCAL_FK")
 @JsonIgnore
  public CatLocalidad getCodLocalidad() {
    return codLocalidad;
  }

  public void setCodLocalidad(CatLocalidad codLocalidad) {
    this.codLocalidad = codLocalidad;
  }



  @Column(name = "DIREXTRANJERA", length = 200)
  @JsonIgnore
  public String getDirExtranjera() {
    return dirExtranjera;
  }

  public void setDirExtranjera(String dirExtranjera) {
    this.dirExtranjera = dirExtranjera;
  }


  @Column(name = "LOCEXTRANJERA", length = 120)
  @JsonIgnore
  public String getLocExtranjera() {
    return locExtranjera;
  }


  public void setLocExtranjera(String locExtranjera) {
    this.locExtranjera = locExtranjera;
  }


  @Column(name = "OBSERVACIONES", length = 400)
  @JsonIgnore
  public String getObservaciones() {
    return observaciones;
  }


  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  @Column(name = "COMPARTENIF", length = 1)
  @JsonIgnore
  public boolean isComparteNif() {
    return comparteNif;
  }

  public void setComparteNif(boolean compartenif) {
    this.comparteNif = compartenif;
  }

  @OneToMany (mappedBy = "unidad", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
  @ForeignKey(name="DIR_UNIDAD_CONTACTOSUO_FK")
  @JsonIgnore
  public List<ContactoUnidadOrganica> getContactos() {
    return contactos;
  }


  public void setContactos(List<ContactoUnidadOrganica> contactos) {
    this.contactos = contactos;
  }


  @OneToMany(mappedBy="unidad", cascade=CascadeType.ALL)
  @JsonIgnore
  public List<RelacionOrganizativaOfi> getOrganizativaOfi() {
    return organizativaOfi;
  }


  public void setOrganizativaOfi(List<RelacionOrganizativaOfi> organizativaOfi) {
    this.organizativaOfi = organizativaOfi;
  }


  @OneToMany(mappedBy="unidad", cascade=CascadeType.ALL)
  @JsonIgnore
  public List<RelacionSirOfi> getSirOfi() {
    return sirOfi;
  }


  public void setSirOfi(List<RelacionSirOfi> sirOfi) {
    this.sirOfi = sirOfi;
  }


  /**
   * @return the servicios
   */
 /* @ManyToMany(cascade=CascadeType.PERSIST, fetch= FetchType.EAGER)
  @JoinTable(name="DIR_SERVICIOUO",
          joinColumns=
          @JoinColumn(name="CODUNIDAD"),
          inverseJoinColumns=
          @JoinColumn(name="CODSERVICIO"))
  @ForeignKey(name="DIR_SER_UNI_FK", inverseName = "DIR_UNI_SERV_FK")
  @JsonIgnore
  public Set<Servicio> getServicios() {
    return servicios;
  }*/

  @OneToMany(mappedBy = "unidad")
  @JsonIgnore
  public Set<ServicioUO> getServicios() {
    return servicios;
  }

  public void setServicios(Set<ServicioUO> servicios) {
    this.servicios = servicios;
  }



  @OneToMany(mappedBy = "unidadAnterior")
  @JsonIgnore
  public Set<HistoricoUO> getHistoricosAnterior() {
    return historicosAnterior;
  }

  public void setHistoricosAnterior(Set<HistoricoUO> historicosAnterior) {
    this.historicosAnterior = historicosAnterior;
  }

  @OneToMany(mappedBy = "unidadUltima")
  @JsonIgnore
  public Set<HistoricoUO> getHistoricosUltima() {
    return historicosUltima;
  }

  public void setHistoricosUltima(Set<HistoricoUO> historicosUltima) {
    this.historicosUltima = historicosUltima;
  }


  @OneToMany (mappedBy = "unidad", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
  @ForeignKey(name="DIR_UNIDAD_CODUO_FK")
  @JsonIgnore
  public Set<CodigoUnidadOrganica> getCodigosExternos() {
    return codigosExternos;
  }

  public void setCodigosExternos(Set<CodigoUnidadOrganica> codigosExternos) {
    this.codigosExternos = codigosExternos;
  }

  @OneToMany (mappedBy = "unidad", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
  @ForeignKey(name="DIR_UNIDAD_NIFCIF_FK")
  @JsonIgnore
  public Set<NifCifUnidadOrganica> getNifcifUo() {
    return nifcifUo;
  }

  public void setNifcifUo(Set<NifCifUnidadOrganica> nifcifUo) {
    this.nifcifUo = nifcifUo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Unidad unidad = (Unidad) o;
    return Objects.equals(codigo, unidad.codigo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo);
  }
}