package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.ws.CatTipoVia;

/**
 * @author mgonzalez
 * 18/03/2022
 */
public class CatTipoViaWs extends CatTipoVia {

    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(es.caib.dir3caib.persistence.model.CatTipoVia tipoVia){
        super.rellenar(tipoVia);

        this.estado= tipoVia.getEstado().getCodigoEstadoEntidad();

    }

    public static CatTipoViaWs generar(es.caib.dir3caib.persistence.model.CatTipoVia tipoVia){
        CatTipoViaWs tipoViaWs =  new CatTipoViaWs();
        if(tipoVia !=null){
            tipoViaWs.rellenar(tipoVia);
        }
        return tipoViaWs;
    }
}
