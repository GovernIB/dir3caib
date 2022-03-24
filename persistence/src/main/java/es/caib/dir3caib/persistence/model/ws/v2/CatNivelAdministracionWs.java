package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.CatComunidadAutonoma;
import es.caib.dir3caib.persistence.model.ws.CatNivelAdministracion;

/**
 * @author mgonzalez
 * 18/03/2022
 */
public class CatNivelAdministracionWs extends CatNivelAdministracion {

    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(es.caib.dir3caib.persistence.model.CatNivelAdministracion nivelAdministracion){
        super.rellenar(nivelAdministracion);

        this.estado= nivelAdministracion.getEstado().getCodigoEstadoEntidad();

    }

    public static CatNivelAdministracionWs generar(es.caib.dir3caib.persistence.model.CatNivelAdministracion nivelAdministracion){
        CatNivelAdministracionWs nivelAdministracionWs =  new CatNivelAdministracionWs();
        if(nivelAdministracion !=null){
            nivelAdministracionWs.rellenar(nivelAdministracion);
        }
        return nivelAdministracionWs;
    }
}
