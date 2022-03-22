package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.ws.CatPais;

/**
 * @author mgonzalez
 * 18/03/2022
 */
public class CatPaisWs extends CatPais {

    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(es.caib.dir3caib.persistence.model.CatPais pais){
        super.rellenar(pais);

        this.estado= pais.getEstado().getCodigoEstadoEntidad();

    }

    public static CatPaisWs generar(es.caib.dir3caib.persistence.model.CatPais pais){
        CatPaisWs paisWs =  new CatPaisWs();
        if(pais !=null){
            paisWs.rellenar(pais);
        }
        return paisWs;
    }
}
