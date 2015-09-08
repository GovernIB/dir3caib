/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.back.form;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mgonzalez
 */
public class FechasForm {
  
    private Date fechaInicio;
    private Date fechaFin;
   

    public FechasForm() {
    }


    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicioFormateada(String formatoFecha) {
        SimpleDateFormat formato = new SimpleDateFormat(formatoFecha);
        if( fechaInicio != null){
          return formato.format(fechaInicio);
        }else {
          return new String();
        }
    
    } 

  
    public String getFechaFinFormateada(String formatoFecha) {
        SimpleDateFormat formato = new SimpleDateFormat(formatoFecha);
        if( fechaFin != null){
          return formato.format(fechaFin);
        }else {
          return new String();
        }
    }

    
    
    
  
}
