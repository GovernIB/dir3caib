package es.caib.dir3caib.persistence.model.ws;

import es.caib.dir3caib.persistence.model.RelacionSirOfi;
import org.apache.log4j.Logger;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 5/03/14
 */
public class RelacionSirOfiTF {

  protected final Logger log = Logger.getLogger(getClass());

    private String oficina;
    private String unidad;
    private String estado;

    public RelacionSirOfiTF() {
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
        this.setUnidad(relacionSirOfi.getUnidad().getCodigo());
    }

    public static RelacionSirOfiTF generar(RelacionSirOfi relacionSirOfi){
        RelacionSirOfiTF relacionSirOfiTF =  new RelacionSirOfiTF();
        if(relacionSirOfi!=null){
            relacionSirOfiTF.rellenar(relacionSirOfi);
        }
        return relacionSirOfiTF;
    }
}
