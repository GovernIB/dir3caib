package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.ws.CatEstadoEntidad;

/**
 * @author mgonzalez
 * 18/03/2022
 */
public class CatEstadoEntidadWs extends CatEstadoEntidad {
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(es.caib.dir3caib.persistence.model.CatEstadoEntidad estadoEntidad){
        super.rellenar(estadoEntidad);

        this.estado= estadoEntidad.getEstado();

    }

    public static CatEstadoEntidadWs generar(es.caib.dir3caib.persistence.model.CatEstadoEntidad estadoEntidad){
        CatEstadoEntidadWs estadoEntidadWs =  new CatEstadoEntidadWs();
        if(estadoEntidad !=null){
            estadoEntidadWs.rellenar(estadoEntidad);
        }
        return estadoEntidadWs;
    }
}
