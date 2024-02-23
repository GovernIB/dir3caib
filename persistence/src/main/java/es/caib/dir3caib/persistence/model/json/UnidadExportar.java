package es.caib.dir3caib.persistence.model.json;

import java.util.List;

public class UnidadExportar {
	
	private String codigo = "";
	private String version = "";
	private String denominacion = "";
	private String denominacionCooficial = "";
	private String estado = "";
	private String esEdp = "";
	private String nif = "";
	private String nivelJerarquico = "";
	private String nivelAdministracion = "";
	private String tipoUnidad = "";
	private UnidadExportarDatosUnidad unidadRaiz;
	private UnidadExportarDatosUnidad unidadSuperior;
	private UnidadExportarDireccion direccion;
	private List<UnidadExportarContacto> contactos;
	private List<UnidadExportarOficina> oficinas;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public String getDenominacionCooficial() {
		return denominacionCooficial;
	}
	public void setDenominacionCooficial(String denominacionCooficial) {
		this.denominacionCooficial = denominacionCooficial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEsEdp() {
		return esEdp;
	}
	public void setEsEdp(String esEdp) {
		this.esEdp = esEdp;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNivelJerarquico() {
		return nivelJerarquico;
	}
	public void setNivelJerarquico(String nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
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
	public UnidadExportarDatosUnidad getUnidadRaiz() {
		return unidadRaiz;
	}
	public void setUnidadRaiz(UnidadExportarDatosUnidad unidadRaiz) {
		this.unidadRaiz = unidadRaiz;
	}
	public UnidadExportarDatosUnidad getUnidadSuperior() {
		return unidadSuperior;
	}
	public void setUnidadSuperior(UnidadExportarDatosUnidad unidadSuperior) {
		this.unidadSuperior = unidadSuperior;
	}
	public UnidadExportarDireccion getDireccion() {
		return direccion;
	}
	public void setDireccion(UnidadExportarDireccion direccion) {
		this.direccion = direccion;
	}
	public List<UnidadExportarContacto> getContactos() {
		return contactos;
	}
	public void setContactos(List<UnidadExportarContacto> contactos) {
		this.contactos = contactos;
	}
	public List<UnidadExportarOficina> getOficinas() {
		return oficinas;
	}
	public void setOficinas(List<UnidadExportarOficina> oficinas) {
		this.oficinas = oficinas;
	}
	@Override
	public String toString() {
		return "UnidadExportar [codigo=" + codigo + ", version=" + version + ", denominacion=" + denominacion
				+ ", denominacionCooficial=" + denominacionCooficial + ", estado=" + estado + ", esEdp=" + esEdp
				+ ", nif=" + nif + ", nivelJerarquico=" + nivelJerarquico + ", nivelAdministracion="
				+ nivelAdministracion + ", tipoUnidad=" + tipoUnidad + ", unidadRaiz=" + unidadRaiz
				+ ", unidadSuperior=" + unidadSuperior + ", direccion=" + direccion + ", contactos=" + contactos
				+ ", oficinas=" + oficinas + "]";
	}
	public UnidadExportar(String codigo, String version, String denominacion, String denominacionCooficial,
			String estado, String esEdp, String nif, String nivelJerarquico, String nivelAdministracion,
			String tipoUnidad, UnidadExportarDatosUnidad unidadRaiz, UnidadExportarDatosUnidad unidadSuperior,
			UnidadExportarDireccion direccion, List<UnidadExportarContacto> contactos,
			List<UnidadExportarOficina> oficinas) {
		super();
		this.codigo = codigo;
		this.version = version;
		this.denominacion = denominacion;
		this.denominacionCooficial = denominacionCooficial;
		this.estado = estado;
		this.esEdp = esEdp;
		this.nif = nif;
		this.nivelJerarquico = nivelJerarquico;
		this.nivelAdministracion = nivelAdministracion;
		this.tipoUnidad = tipoUnidad;
		this.unidadRaiz = unidadRaiz;
		this.unidadSuperior = unidadSuperior;
		this.direccion = direccion;
		this.contactos = contactos;
		this.oficinas = oficinas;
	}
	public UnidadExportar(String codigo, String version, String denominacion, String denominacionCooficial,
			String estado, String esEdp, String nif, String nivelJerarquico, String nivelAdministracion,
			String tipoUnidad) {
		super();
		this.codigo = codigo;
		this.version = version;
		this.denominacion = denominacion;
		this.denominacionCooficial = denominacionCooficial;
		this.estado = estado;
		this.esEdp = esEdp;
		this.nif = nif;
		this.nivelJerarquico = nivelJerarquico;
		this.nivelAdministracion = nivelAdministracion;
		this.tipoUnidad = tipoUnidad;
	}
	public UnidadExportar() {
		super();
	}
}
