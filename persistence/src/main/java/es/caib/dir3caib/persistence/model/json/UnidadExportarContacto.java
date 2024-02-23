package es.caib.dir3caib.persistence.model.json;


public class UnidadExportarContacto {
	
	private String tipo;
	private String valor;
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	@Override
	public String toString() {
		return "UnidadExportarContacto [tipo=" + tipo + ", valor=" + valor + "]";
	}
	public UnidadExportarContacto(String tipo, String valor) {
		super();
		this.tipo = tipo;
		this.valor = valor;
	}
	public UnidadExportarContacto() {
		super();
	}
}
