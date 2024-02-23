package es.caib.dir3caib.persistence.model.json;


public class UnidadExportarDireccion {

	private String localidad;
	private String tipoVia;
	private String nombreVia;
	private String numVia;
	private String complemento;
	private String codigoPostal;
	
	private String pais;
    private String provincia;
    private String isla;
    private String comunidadAutonoma;
    private String ambito;
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getTipoVia() {
		return tipoVia;
	}
	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
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
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getIsla() {
		return isla;
	}
	public void setIsla(String isla) {
		this.isla = isla;
	}
	public String getComunidadAutonoma() {
		return comunidadAutonoma;
	}
	public void setComunidadAutonoma(String comunidadAutonoma) {
		this.comunidadAutonoma = comunidadAutonoma;
	}
	public String getAmbito() {
		return ambito;
	}
	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}
	
	@Override
	public String toString() {
		return "UnidadExportarDireccion [localidad=" + localidad + ", tipoVia=" + tipoVia + ", nombreVia=" + nombreVia
				+ ", numVia=" + numVia + ", complemento=" + complemento + ", codigoPostal=" + codigoPostal + ", pais="
				+ pais + ", provincia=" + provincia + ", isla=" + isla + ", comunidadAutonoma=" + comunidadAutonoma
				+ ", ambito=" + ambito + "]";
	}
	
	public UnidadExportarDireccion(String localidad, String tipoVia, String nombreVia, String numVia,
			String complemento, String codigoPostal, String pais, String provincia, String isla,
			String comunidadAutonoma, String ambito) {
		super();
		this.localidad = localidad;
		this.tipoVia = tipoVia;
		this.nombreVia = nombreVia;
		this.numVia = numVia;
		this.complemento = complemento;
		this.codigoPostal = codigoPostal;
		this.pais = pais;
		this.provincia = provincia;
		this.isla = isla;
		this.comunidadAutonoma = comunidadAutonoma;
		this.ambito = ambito;
	}
    
	public UnidadExportarDireccion() {
		super();
	}
}