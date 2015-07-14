package es.caib.dir3caib.persistence.model.utils;

/**
 * Created by mgonzalez on 13/07/2015.
 * Se amplia el conjunto de datos para la unidad.
 */
public class ObjetoBasicoUnidad extends ObjetoBasico {

    private String unidadRaiz;
    private String unidadSuperior;


    public ObjetoBasicoUnidad() {
    }

    public ObjetoBasicoUnidad(String codigo, String denominacion, String descripcionEstado, String unidadRaiz, String unidadSuperior) {
        super(codigo, denominacion, descripcionEstado);
        this.unidadRaiz = unidadRaiz;
        this.unidadSuperior = unidadSuperior;
    }

    public String getUnidadRaiz() {
        return unidadRaiz;
    }


    public void setUnidadRaiz(String unidadRaiz) {
        this.unidadRaiz = unidadRaiz;
    }

    public String getUnidadSuperior() {
        return unidadSuperior;
    }

    public void setUnidadSuperior(String unidadSuperior) {
        this.unidadSuperior = unidadSuperior;
    }
}
