package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.CatLocalidad;
import es.caib.dir3caib.persistence.model.ws.CatLocalidadTF;

/**
 * @author mgonzalez
 * 18/03/2022
 */
public class CatLocalidadWs extends CatLocalidadTF {

    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(CatLocalidad localidad){
        super.rellenar(localidad);

        this.estado= localidad.getEstado().getCodigoEstadoEntidad();

    }

    public static CatLocalidadWs generar(CatLocalidad localidad){
        CatLocalidadWs localidadWs =  new CatLocalidadWs();
        if(localidad !=null){
            localidadWs.rellenar(localidad);
        }
        return localidadWs;
    }
}
