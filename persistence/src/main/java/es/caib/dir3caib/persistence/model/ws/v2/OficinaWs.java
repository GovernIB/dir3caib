package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.ws.OficinaTF;

import java.util.Date;

/**
 * @author mgonzalez
 * 10/03/2022
 */
public class OficinaWs extends es.caib.dir3caib.persistence.model.ws.OficinaTF{

    private String denomLenguaCooficial;
    private int idiomalengua;
    private Long versionUoResponsable;
    private String fuenteExterna;
    private Date fechaUltimaActualizacion;


    public OficinaWs(){
    }

    public OficinaWs (OficinaTF oficinaTF, String denomLenguaCooficial){
        super(oficinaTF);
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

    public Long getVersionUoResponsable() {
        return versionUoResponsable;
    }

    public void setVersionUoResponsable(Long versionUoResponsable) {
        this.versionUoResponsable = versionUoResponsable;
    }

    public String getFuenteExterna() {
        return fuenteExterna;
    }

    public void setFuenteExterna(String fuenteExterna) {
        this.fuenteExterna = fuenteExterna;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public void rellenar(Oficina oficina){
        super.rellenar(oficina,false);

        this.denomLenguaCooficial=oficina.getDenomLenguaCooficial();
        this.idiomalengua=oficina.getIdiomalengua();
        this.versionUoResponsable= oficina.getCodUoResponsable().getVersion();
        this.fuenteExterna= oficina.getFuenteExterna();
        this.fechaUltimaActualizacion=oficina.getFechaUltimaActualizacion();

    }

    public static OficinaWs generar(Oficina oficina){
        OficinaWs oficinaWs = null;
        if(oficina!=null){
            oficinaWs = new OficinaWs();
            oficinaWs.rellenar(oficina);
        }
        return oficinaWs;
    }
}
