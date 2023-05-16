package es.caib.dir3caib.persistence.model.json;

public class UnidadRestExtendido extends UnidadRest {
	
    private String descripcionEstadoEntidad = "";
    private String descripcionNivelJerarquico = "";
    private String denominacionUnidadSuperior = "";
    private String denominacionCooficialUnidadSuperior = "";
    private String denominacionUnidadRaiz = "";
    private String denominacionCooficialUnidadRaiz = "";
    private String codigoLocalidad = "";
    private String descripcionNivelAdministracion = "";
    private String descripcionTipoVia = "";
    private String descripcionAmbitoTerritorial = "";
    private String descripcionAmbPais = "";
    private String descripcionAmbProvincia = "";
    private String descripcionAmbComunidad = "";
    private String codigoDir3 = "";
    private String codigoDir3UnidadSuperior = "";
    private String codigoDir3UnidadRaiz = "";
    
    // Es creen aquests tipus de contactos en format String perquè Sistra no pot interpretar les llistes
    private String contactoEmail = "";
    private String contactoCentralita = "";
    private String contactoFax = "";
    private String contactoOtro = "";
    private String contactoURL = "";
    private String contactoTelefono = "";
    private String contactoCitaPrevia = "";
    
    private Boolean tieneOficinaSIR;
    
	public String getDescripcionEstadoEntidad() {
		return descripcionEstadoEntidad;
	}
	public void setDescripcionEstadoEntidad(String descripcionEstadoEntidad) {
		this.descripcionEstadoEntidad = descripcionEstadoEntidad;
	}
	public String getDescripcionNivelJerarquico() {
		return descripcionNivelJerarquico;
	}
	public void setDescripcionNivelJerarquico(String descripcionNivelJerarquico) {
		this.descripcionNivelJerarquico = descripcionNivelJerarquico;
	}
	public String getDenominacionUnidadSuperior() {
		return denominacionUnidadSuperior;
	}
	public void setDenominacionUnidadSuperior(String denominacionUnidadSuperior) {
		this.denominacionUnidadSuperior = denominacionUnidadSuperior;
	}
	public String getDenominacionCooficialUnidadSuperior() {
		return denominacionCooficialUnidadSuperior;
	}
	public void setDenominacionCooficialUnidadSuperior(String denominacionCooficialUnidadSuperior) {
		this.denominacionCooficialUnidadSuperior = denominacionCooficialUnidadSuperior;
	}
	public String getDenominacionUnidadRaiz() {
		return denominacionUnidadRaiz;
	}
	public void setDenominacionUnidadRaiz(String denominacionUnidadRaiz) {
		this.denominacionUnidadRaiz = denominacionUnidadRaiz;
	}
	public String getDenominacionCooficialUnidadRaiz() {
		return denominacionCooficialUnidadRaiz;
	}
	public void setDenominacionCooficialUnidadRaiz(String denominacionCooficialUnidadRaiz) {
		this.denominacionCooficialUnidadRaiz = denominacionCooficialUnidadRaiz;
	}
	public String getCodigoLocalidad() {
		return codigoLocalidad;
	}
	public void setCodigoLocalidad(String codigoLocalidad) {
		this.codigoLocalidad = codigoLocalidad;
	}
	public String getDescripcionNivelAdministracion() {
		return descripcionNivelAdministracion;
	}
	public void setDescripcionNivelAdministracion(String descripcionNivelAdministracion) {
		this.descripcionNivelAdministracion = descripcionNivelAdministracion;
	}
	public String getDescripcionTipoVia() {
		return descripcionTipoVia;
	}
	public void setDescripcionTipoVia(String descripcionTipoVia) {
		this.descripcionTipoVia = descripcionTipoVia;
	}
	public String getDescripcionAmbitoTerritorial() {
		return descripcionAmbitoTerritorial;
	}
	public void setDescripcionAmbitoTerritorial(String descripcionAmbitoTerritorial) {
		this.descripcionAmbitoTerritorial = descripcionAmbitoTerritorial;
	}
	public String getDescripcionAmbPais() {
		return descripcionAmbPais;
	}
	public void setDescripcionAmbPais(String descripcionAmbPais) {
		this.descripcionAmbPais = descripcionAmbPais;
	}
	public String getDescripcionAmbProvincia() {
		return descripcionAmbProvincia;
	}
	public void setDescripcionAmbProvincia(String descripcionAmbProvincia) {
		this.descripcionAmbProvincia = descripcionAmbProvincia;
	}
	public String getDescripcionAmbComunidad() {
		return descripcionAmbComunidad;
	}
	public void setDescripcionAmbComunidad(String descripcionAmbComunidad) {
		this.descripcionAmbComunidad = descripcionAmbComunidad;
	}
	public String getCodigoDir3() {
		return codigoDir3;
	}
	public void setCodigoDir3(String codigoDir3) {
		this.codigoDir3 = codigoDir3;
	}
	public String getCodigoDir3UnidadSuperior() {
		return codigoDir3UnidadSuperior;
	}
	public void setCodigoDir3UnidadSuperior(String codigoDir3UnidadSuperior) {
		this.codigoDir3UnidadSuperior = codigoDir3UnidadSuperior;
	}
	public String getCodigoDir3UnidadRaiz() {
		return codigoDir3UnidadRaiz;
	}
	public void setCodigoDir3UnidadRaiz(String codigoDir3UnidadRaiz) {
		this.codigoDir3UnidadRaiz = codigoDir3UnidadRaiz;
	}
	public Boolean getTieneOficinaSIR() {
		return tieneOficinaSIR;
	}
	public void setTieneOficinaSIR(Boolean tieneOficinaSIR) {
		this.tieneOficinaSIR = tieneOficinaSIR;
	}
	public String getContactoEmail() {
		return contactoEmail;
	}
	public void setContactoEmail(String contactoEmail) {
		this.contactoEmail = contactoEmail;
	}
	public String getContactoCentralita() {
		return contactoCentralita;
	}
	public void setContactoCentralita(String contactoCentralita) {
		this.contactoCentralita = contactoCentralita;
	}
	public String getContactoFax() {
		return contactoFax;
	}
	public void setContactoFax(String contactoFax) {
		this.contactoFax = contactoFax;
	}
	public String getContactoOtro() {
		return contactoOtro;
	}
	public void setContactoOtro(String contactoOtro) {
		this.contactoOtro = contactoOtro;
	}
	public String getContactoURL() {
		return contactoURL;
	}
	public void setContactoURL(String contactoURL) {
		this.contactoURL = contactoURL;
	}
	public String getContactoTelefono() {
		return contactoTelefono;
	}
	public void setContactoTelefono(String contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}
	public String getContactoCitaPrevia() {
		return contactoCitaPrevia;
	}
	public void setContactoCitaPrevia(String contactoCitaPrevia) {
		this.contactoCitaPrevia = contactoCitaPrevia;
	}
	public UnidadRestExtendido() {
		super();
	} 
    
}
