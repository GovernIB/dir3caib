package es.caib.dir3caib.persistence.model.json;

public class UnidadExportarDatosUnidad {
	
	private String codigo; 
	private String denominacion;
	private String denominacionCooficial;
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
	public String getDenominacionCooficial() {
		return denominacionCooficial;
	}
	public void setDenominacionCooficial(String denominacionCooficial) {
		this.denominacionCooficial = denominacionCooficial;
	}
	@Override
	public String toString() {
		return "UnidadExportarDatosUnidad [codigo=" + codigo + ", denominacion=" + denominacion
				+ ", denominacionCooficial=" + denominacionCooficial + "]";
	}
	public UnidadExportarDatosUnidad(String codigo, String denominacion, String denominacionCooficial) {
		super();
		this.codigo = codigo;
		this.denominacion = denominacion;
		this.denominacionCooficial = denominacionCooficial;
	}
	public UnidadExportarDatosUnidad() {
		super();
	}
	
	
	
}
