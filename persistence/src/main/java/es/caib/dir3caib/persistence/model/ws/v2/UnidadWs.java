package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;
import es.caib.dir3caib.utils.Configuracio;

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
    private String nifCif;
    private Long codAmbIsla;

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

    public String getNifCif() {
        return nifCif;
    }

    public void setNifCif(String nifCif) {
        this.nifCif = nifCif;
    }

    public Long getCodAmbIsla() {
        return codAmbIsla;
    }

    public void setCodAmbIsla(Long codAmbIsla) {
        this.codAmbIsla = codAmbIsla;
    }

    public void rellenar(Unidad unidad) {
        super.rellenar(unidad, false);
        //Fijamos la denominación independientemente de lo que haya hecho el rellenar de la clase padre
        this.setDenominacion(unidad.getDenominacion());
        // Fijamos la denominación en lengua cooficial si no está vacia, si no fijamos la denominación
        if(unidad.getDenomLenguaCooficial() != null) {
            this.setDenomLenguaCooficial(unidad.getDenomLenguaCooficial());
        }else{
            this.setDenomLenguaCooficial(unidad.getDenominacion());
        }
        this.setIdiomalengua(unidad.getIdiomalengua());
        this.setVersion(unidad.getVersion());
        this.setPoder(unidad.getPoder().getDescripcionPoder());
        this.setComparteNif(unidad.isComparteNif());
        this.setFechaUltimaActualizacion(unidad.getFechaUltimaActualizacion());
        this.setNifCif(unidad.getNifcif());
        if(unidad.getCodAmbIsla() != null){
            this.setCodAmbIsla(unidad.getCodAmbIsla().getCodigoIsla());
        }
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
            unidadWs.setCodigo(unidad.getCodigoDir3());
            unidadWs.setDenominacion(unidad.getDenominacion());
            unidadWs.setDenomLenguaCooficial(unidad.getDenomLenguaCooficial());
            unidadWs.setCodUnidadRaiz(unidad.getCodUnidadRaiz().getCodigoDir3());
            unidadWs.setCodUnidadSuperior(unidad.getCodUnidadSuperior().getCodigoDir3());
            unidadWs.setEsEdp(unidad.isEsEdp());
        }

        return unidadWs;
    }


}
