package es.caib.dir3caib.persistence.model.ws.v2;

import es.caib.dir3caib.persistence.model.CatIsla;

/**
 * @author mgonzalez
 * 18/03/2022
 */
public class CatIslaWs  {

    private Long codigoIsla;
    private String descripcionIsla;
    private Long codigoProvincia;
    private String estado;

    public Long getCodigoIsla() {
        return codigoIsla;
    }

    public void setCodigoIsla(Long codigoIsla) {
        this.codigoIsla = codigoIsla;
    }

    public String getDescripcionIsla() {
        return descripcionIsla;
    }

    public void setDescripcionIsla(String descripcionIsla) {
        this.descripcionIsla = descripcionIsla;
    }

    public Long getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(Long codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void rellenar(CatIsla catIsla){
       this.setCodigoIsla(catIsla.getCodigoIsla());
       this.setDescripcionIsla(catIsla.getDescripcionIsla());
       this.setCodigoProvincia(catIsla.getProvincia().getCodigoProvincia());
       this.setEstado(catIsla.getEstado().getEstado());
    }

    public static CatIslaWs generar(CatIsla catIsla){
        CatIslaWs islaWs =  new CatIslaWs();
        if(catIsla !=null){
            islaWs.rellenar(catIsla);
        }
        return islaWs;
    }
}
