package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;

import java.util.Date;

/**
 * @author mgonzalez
 * 03/03/2022
 */
public class UnidadWs extends es.caib.dir3caib.persistence.model.ws.UnidadTF {

    private String denomLenguaCooficial;
    private int idiomalengua;
    private Long version;
    private String poder;
    private Date fechaUltimaActualizacion;
    private boolean comparteNif;

    public UnidadWs(){
    }

    public UnidadWs (UnidadTF unidadTF, String denomLenguaCooficial){
        super(unidadTF);
        this.denomLenguaCooficial= denomLenguaCooficial;
    }



    public String getDenomLenguaCooficial() {
        return denomLenguaCooficial;
    }

    public void setDenomLenguaCooficial(String denomLenguaCooficial) {
        this.denomLenguaCooficial = denomLenguaCooficial;
    }

    public int getIdiomalengua() {
        return idiomalengua;
    }

    public void setIdiomalengua(int idiomalengua) {
        this.idiomalengua = idiomalengua;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getPoder() {
        return poder;
    }

    public void setPoder(String poder) {
        this.poder = poder;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public boolean getComparteNif() {
        return comparteNif;
    }

    public void setComparteNif(boolean comparteNif) {
        this.comparteNif = comparteNif;
    }

    public void rellenar(Unidad unidad) {
        super.rellenar(unidad,false);
        this.setDenomLenguaCooficial(unidad.getDenomLenguaCooficial());
        this.setIdiomalengua(unidad.getIdiomalengua());
        this.setVersion(unidad.getVersion());
        this.setPoder(unidad.getPoder().getDescripcionPoder());
        this.setComparteNif(unidad.isComparteNif());
        this.setFechaUltimaActualizacion(unidad.getFechaUltimaActualizacion());
    }

    public static UnidadWs generar(Unidad unidad) {
        UnidadWs unidadWs = null;
        if (unidad != null) {
            unidadWs = new UnidadWs();
            unidadWs.rellenar(unidad);
        }

        return unidadWs;
    }

    public static UnidadWs generarLigero(Unidad unidad) {
        UnidadWs unidadWs = null;
        if (unidad != null) {
            unidadWs = new UnidadWs();
            unidadWs.setCodigo(unidad.getCodigo());
            unidadWs.setDenominacion(unidad.getDenominacion());
            unidadWs.setDenomLenguaCooficial(unidad.getDenomLenguaCooficial());
            unidadWs.setCodUnidadRaiz(unidad.getCodUnidadRaiz().getCodigo());
            unidadWs.setCodUnidadSuperior(unidad.getCodUnidadSuperior().getCodigo());
            unidadWs.setEsEdp(unidad.isEsEdp());
        }

        return unidadWs;
    }


}
