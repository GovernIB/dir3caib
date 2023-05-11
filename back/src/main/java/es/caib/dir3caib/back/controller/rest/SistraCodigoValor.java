package es.caib.dir3caib.back.controller.rest;

public class SistraCodigoValor {
	
	private String codigo;
	private String valor;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public SistraCodigoValor(String codigo, String valor) {
		super();
		this.codigo = codigo;
		this.valor = valor;
	}
	
	public SistraCodigoValor() {
		super();
	}
	
	@Override
	public String toString() {
		return "SistraCodigoValor [codigo=" + codigo + ", valor=" + valor + "]";
	}
	
	
	

}
