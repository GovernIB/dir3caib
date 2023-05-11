package es.caib.dir3caib.back.controller.rest;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class SistraResponse {
	
	private Boolean error; 
	private String codigoError;
	private String descripcionError;
	private String codigoRetorno;
	
	@JsonRawValue
	private String datos;
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	public String getCodigoRetorno() {
		return codigoRetorno;
	}
	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}
	
	@Override
	public String toString() {
		return "SistraResponse [error=" + error + ", codigoError=" + codigoError + ", descripcionError="
				+ descripcionError + ", codigoRetorno=" + codigoRetorno + ", datos=" + datos + "]";
	}
	
	public SistraResponse() {
		super();
	} 

}
