package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.CatProvincia;
import es.caib.dir3caib.persistence.model.ws.CatProvinciaTF;

/**
 * @author mgonzalez
 * 18/03/2022
 */
public class CatProvinciaWs extends CatProvinciaTF {

    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(CatProvincia provincia){
        super.rellenar(provincia);

        this.estado= provincia.getEstado().getCodigoEstadoEntidad();

    }

    public static CatProvinciaWs generar(CatProvincia provincia){
        CatProvinciaWs provinciaWs =  new CatProvinciaWs();
        if(provincia !=null){
            provinciaWs.rellenar(provincia);
        }
        return provinciaWs;
    }
}
