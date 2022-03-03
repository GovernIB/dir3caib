package es.caib.dir3caib.persistence.model.ws;

/**
 * @author mgonzalez
 * 22/02/2022
 */
public class Servicio  {

    private Long codServicio;
    private String descServicio;

    public Long getCodServicio() {
        return codServicio;
    }

    public void setCodServicio(Long codServicio) {
        this.codServicio = codServicio;
    }

    public String getDescServicio() {
        return descServicio;
    }

    public void setDescServicio(String descServicio) {
        this.descServicio = descServicio;
    }

    public void rellenar(es.caib.dir3caib.persistence.model.CatServicio catServicio) {

        this.setCodServicio(catServicio.getCodServicio());
        this.setDescServicio(catServicio.getDescServicio());

    }

    public static Servicio generar(es.caib.dir3caib.persistence.model.CatServicio catServicio) {
        Servicio servicio = new Servicio();
        if (catServicio != null) {
            servicio.rellenar(catServicio);
        }
        return servicio;
    }
}
