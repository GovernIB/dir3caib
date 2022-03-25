package es.caib.dir3caib.persistence.model.json;

import es.caib.dir3caib.persistence.model.RelacionSirOfi;
import org.apache.log4j.Logger;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 5/03/14
 */
public class RelacionSirOfiRest {

  protected final Logger log = Logger.getLogger(getClass());

    private String oficina;
    private String unidad;
    private String estado;

    public RelacionSirOfiRest() {
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(RelacionSirOfi relacionSirOfi){

        this.setEstado(relacionSirOfi.getEstado().getCodigoEstadoEntidad());
        this.setOficina(relacionSirOfi.getOficina().getCodigo());
        this.setUnidad(relacionSirOfi.getUnidad().getCodigoDir3());
    }

    public static RelacionSirOfiRest generar(RelacionSirOfi relacionSirOfi){
        RelacionSirOfiRest relacionSirOfiTF =  new RelacionSirOfiRest();
        if(relacionSirOfi!=null){
            relacionSirOfiTF.rellenar(relacionSirOfi);
        }
        return relacionSirOfiTF;
    }
}
