package es.caib.dir3caib.persistence.model.ws;


/**
 * @author mgonzalez
 * 02/03/2022
 */
public class CatEstadoEntidad  {

    private String codigoEstadoEntidad;
    private String descripcionEstadoEntidad;


    public String getCodigoEstadoEntidad() {
        return codigoEstadoEntidad;
    }

    public void setCodigoEstadoEntidad(String codigoEstadoEntidad) {
        this.codigoEstadoEntidad = codigoEstadoEntidad;
    }

    public String getDescripcionEstadoEntidad() {
        return descripcionEstadoEntidad;
    }

    public void setDescripcionEstadoEntidad(String descripcionEstadoEntidad) {
        this.descripcionEstadoEntidad = descripcionEstadoEntidad;
    }


    public void rellenar(es.caib.dir3caib.persistence.model.CatEstadoEntidad catEstadoEntidad) {

        this.setCodigoEstadoEntidad(catEstadoEntidad.getCodigoEstadoEntidad());
        this.setDescripcionEstadoEntidad(catEstadoEntidad.getDescripcionEstadoEntidad());

    }

    public static CatEstadoEntidad generar(es.caib.dir3caib.persistence.model.CatEstadoEntidad catEstadoEntidad) {
        CatEstadoEntidad catEstadoEntidad1 = new CatEstadoEntidad();
        if (catEstadoEntidad != null) {
            catEstadoEntidad1.rellenar(catEstadoEntidad);
        }
        return catEstadoEntidad1;
    }
}
