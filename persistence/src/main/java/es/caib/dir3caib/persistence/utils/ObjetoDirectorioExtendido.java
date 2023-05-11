package es.caib.dir3caib.persistence.utils;

/**
 * Created by jagarcia on 20/04/2023.
 * Clase que representa la tupla codigo-denominación extendida de una unidad
 * Se emplea para los métodos rest que quieren devolver este conjunto básico de datos.
 */
public class ObjetoDirectorioExtendido extends ObjetoDirectorio {

    private Long version;
    private String denominacionCooficial;
    private String estado;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

	public ObjetoDirectorioExtendido(String codigo, Long version, String denominacion, String denominacionCooficial, String estado) {
		super(codigo, denominacion);
		this.version = version;
		this.denominacionCooficial = denominacionCooficial;
		this.estado = estado;
	}
    
    
    
}
