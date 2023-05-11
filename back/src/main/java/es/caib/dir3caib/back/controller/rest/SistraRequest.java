package es.caib.dir3caib.back.controller.rest;

import java.util.List;

public class SistraRequest {
	
	private String idDominio;
	private List<SistraCodigoValor> filtro;
	
	public SistraRequest() {
		super();
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}

	public List<SistraCodigoValor> getFiltro() {
		return filtro;
	}

	public void setFiltro(List<SistraCodigoValor> filtro) {
		this.filtro = filtro;
	}

	@Override
	public String toString() {
		return "SistraRequest [idDominio=" + idDominio + ", filtro=" + filtro + "]";
	}
	
	

}
