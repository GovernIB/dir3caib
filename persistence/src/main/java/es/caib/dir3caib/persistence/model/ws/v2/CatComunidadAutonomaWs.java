package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.ws.CatComunidadAutonomaTF;

/**
 * @author mgonzalez
 * 18/03/2022
 */
public class CatComunidadAutonomaWs extends CatComunidadAutonomaTF  {

    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(CatComunidadAutonoma comunidadAutonoma){
        super.rellenar(comunidadAutonoma);

        this.estado= comunidadAutonoma.getEstado().getCodigoEstadoEntidad();

    }

    public static CatComunidadAutonomaWs generar(CatComunidadAutonoma comunidadAutonoma){
        CatComunidadAutonomaWs comunidadAutonomaWs =  new CatComunidadAutonomaWs();
        if(comunidadAutonoma !=null){
            comunidadAutonomaWs.rellenar(comunidadAutonoma);
        }
        return comunidadAutonomaWs;
    }
}
