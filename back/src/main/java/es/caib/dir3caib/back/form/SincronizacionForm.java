package es.caib.dir3caib.back.form;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SincronizacionForm {

    private Date fechaLimite;



    public SincronizacionForm() {
    }



    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }



    public String getFechaLimiteFormateada(String formatoFecha) {
        SimpleDateFormat formato = new SimpleDateFormat(formatoFecha);
        if( fechaLimite != null){
            return formato.format(fechaLimite);
        }else {
            return "";
        }

    }



}
