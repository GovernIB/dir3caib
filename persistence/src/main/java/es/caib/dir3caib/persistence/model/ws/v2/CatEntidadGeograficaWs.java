package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.CatEntidadGeografica;
import es.caib.dir3caib.persistence.model.ws.CatEntidadGeograficaTF;

/**
 * @author mgonzalez
 * 18/03/2022
 */
public class CatEntidadGeograficaWs extends CatEntidadGeograficaTF {

    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(CatEntidadGeografica entidadGeografica){
        super.rellenar(entidadGeografica);

        this.estado= entidadGeografica.getEstado().getCodigoEstadoEntidad();

    }

    public static CatEntidadGeograficaWs generar(CatEntidadGeografica entidadGeografica){
        CatEntidadGeograficaWs entidadGeograficaWs =  new CatEntidadGeograficaWs();
        if(entidadGeografica !=null){
            entidadGeograficaWs.rellenar(entidadGeografica);
        }
        return entidadGeograficaWs;
    }
}
