package es.caib.dir3caib.persistence.model.ws;

/**
 * @author mgonzalez
 * 02/03/2022
 */
public class CatNivelAdministracion {

    private Long codigoNivelAdministracion;
    private String descripcionNivelAdministracion;

    public Long getCodigoNivelAdministracion() {
        return codigoNivelAdministracion;
    }

    public void setCodigoNivelAdministracion(Long codigoNivelAdministracion) {
        this.codigoNivelAdministracion = codigoNivelAdministracion;
    }

    public String getDescripcionNivelAdministracion() {
        return descripcionNivelAdministracion;
    }

    public void setDescripcionNivelAdministracion(String descripcionNivelAdministracion) {
        this.descripcionNivelAdministracion = descripcionNivelAdministracion;
    }

    public void rellenar(es.caib.dir3caib.persistence.model.CatNivelAdministracion catNivelAdministracion) {

        this.setCodigoNivelAdministracion(catNivelAdministracion.getCodigoNivelAdministracion());
        this.setDescripcionNivelAdministracion(catNivelAdministracion.getDescripcionNivelAdministracion());

    }

    public static CatNivelAdministracion generar(es.caib.dir3caib.persistence.model.CatNivelAdministracion catNivelAdministracion) {
        CatNivelAdministracion catNivelAdministracion1 = new CatNivelAdministracion();
        if (catNivelAdministracion != null) {
            catNivelAdministracion1.rellenar(catNivelAdministracion);
        }
        return catNivelAdministracion1;
    }
}
