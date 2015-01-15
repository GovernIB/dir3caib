package es.caib.dir3caib.persistence.model.ws;

import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 5/03/14
 */
public class RelacionOrganizativaOfiTF {

    private String oficina;
    private String unidad;
    private String estado;

    public RelacionOrganizativaOfiTF() {
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

    public void rellenar(RelacionOrganizativaOfi relacionOrganizativaOfi){


        this.setEstado(relacionOrganizativaOfi.getEstado().getCodigoEstadoEntidad());
        this.setOficina(relacionOrganizativaOfi.getOficina().getCodigo());
        this.setUnidad(relacionOrganizativaOfi.getUnidad().getCodigo());
    }

    public static RelacionOrganizativaOfiTF generar(RelacionOrganizativaOfi relacionOrganizativaOfi){
        RelacionOrganizativaOfiTF relacionOrganizativaOfiTF = new  RelacionOrganizativaOfiTF();
        if(relacionOrganizativaOfi!=null){
            relacionOrganizativaOfiTF.rellenar(relacionOrganizativaOfi);
        }
        return relacionOrganizativaOfiTF;
    }
}
