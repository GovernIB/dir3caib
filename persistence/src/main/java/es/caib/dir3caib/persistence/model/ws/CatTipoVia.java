package es.caib.dir3caib.persistence.model.ws;

/**
 * @author mgonzalez
 * 02/03/2022
 */
public class CatTipoVia {

    private Long codigoTipoVia;
    private String descripcionTipoVia;


    public Long getCodigoTipoVia() {
        return codigoTipoVia;
    }

    public void setCodigoTipoVia(Long codigoTipoVia) {
        this.codigoTipoVia = codigoTipoVia;
    }

    public String getDescripcionTipoVia() {
        return descripcionTipoVia;
    }

    public void setDescripcionTipoVia(String descripcionTipoVia) {
        this.descripcionTipoVia = descripcionTipoVia;
    }

    public void rellenar(es.caib.dir3caib.persistence.model.CatTipoVia catTipoVia) {

        this.setCodigoTipoVia(catTipoVia.getCodigoTipoVia());
        this.setDescripcionTipoVia(catTipoVia.getDescripcionTipoVia());

    }

    public static CatTipoVia generar(es.caib.dir3caib.persistence.model.CatTipoVia catTipoVia) {
        CatTipoVia catTipoVia1 = new CatTipoVia();
        if (catTipoVia != null) {
            catTipoVia1.rellenar(catTipoVia);
        }
        return catTipoVia1;
    }
}
