package es.caib.dir3caib.persistence.utils;

/**
 * Created by mgonzalez on 03/10/2017.
 * Clase que representa la tupla codigo-denominación de una unidad o oficina.
 * Se emplea para los métodos rest que solo quieren devolver este conjunto básico de datos.
 */
public class ObjetoDirectorio {

    private String codigo;
    private String denominacion;

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
}
