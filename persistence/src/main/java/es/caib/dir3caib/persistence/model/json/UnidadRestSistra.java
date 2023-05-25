package es.caib.dir3caib.persistence.model.json;

/**
 * Created by Fundació BIT.
 *
 * @author jagarcia
 * Date: 24/05/23
 */
public class UnidadRestSistra {

    private String codigoDir3;
    private Long version;
    private String denominacion;
    private String denominacionCooficial;
    private String descripcionLocalidad;
    private String nombreVia;
    private String numVia;
    
    private String estado;
    private Long nivelJerarquico;
    private Long nivelAdministracion;
    
    private String contactoEmail = "";
    private String contactoCentralita = "";
    private String contactoFax = "";
    private String contactoOtro = "";
    private String contactoURL = "";
    private String contactoTelefono = "";
    private String contactoCitaPrevia = "";
    
    public UnidadRestSistra() {
    }

	public String getCodigoDir3() {
		return codigoDir3;
	}

	public void setCodigoDir3(String codigoDir3) {
		this.codigoDir3 = codigoDir3;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getNivelJerarquico() {
		return nivelJerarquico;
	}

	public void setNivelJerarquico(Long nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
	}

	public Long getNivelAdministracion() {
		return nivelAdministracion;
	}

	public void setNivelAdministracion(Long nivelAdministracion) {
		this.nivelAdministracion = nivelAdministracion;
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

    
    /*
	public static UnidadRestSistra toUnidadRest(UnidadWs unidad,  boolean mostrarContactos) {

		UnidadRestSistra unidadRest = null;
		int posSeparador = 0;

		if (unidad != null) {
			unidadRest = new UnidadRestSistra();

			String codigo = unidad.getCodigo();
			if (codigoDir3 && codigo != null) {
				posSeparador = codigo.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION);
				codigo = (posSeparador > 0) ? codigo.substring(0, posSeparador) : codigo;
			}

			String codigoUnidadSuperior = unidad.getCodUnidadSuperior();
			if (codigoDir3 && codigoUnidadSuperior != null) {
				posSeparador = codigoUnidadSuperior.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION);
				codigoUnidadSuperior = (posSeparador > 0) ? codigoUnidadSuperior.substring(0, posSeparador)
						: codigoUnidadSuperior;
			}

			String codigoUnidadRaiz = unidad.getCodUnidadRaiz();
			if (codigoDir3 && codigoUnidadRaiz != null) {
				posSeparador = codigoUnidadRaiz.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION);
				codigoUnidadRaiz = (posSeparador > 0) ? codigoUnidadRaiz.substring(0, posSeparador) : codigoUnidadRaiz;
			}


		}
		return unidadRest;
	}
	*/
}
