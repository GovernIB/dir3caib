/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.model;

import es.caib.dir3caib.utils.Configuracio;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author mgonzalez
 */
@Table(name = "DIR_SINCRONIZACION")
@Entity
@SequenceGenerator(name = "generator", sequenceName = "DIR_SEQ_ALL", allocationSize = 1)
public class Sincronizacion implements Serializable {

    private Long codigo;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaImportacion;
    private String tipo;
    private Long estado;

    private List<String> ficherosDirectorio;
    private List<String> ficherosCatalogo;

    public Sincronizacion() {
    }

    public Sincronizacion(String tipo) {
        this.tipo = tipo;
    }

    @Column(name = "CODIGO", nullable = false, length = 3)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Column(name = "FECHAINICIO")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Column(name = "FECHAFIN")
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Column(name = "FECHAIMPORTACION")
    public Date getFechaImportacion() {
        return fechaImportacion;
    }

    public void setFechaImportacion(Date fechaImportacion) {
        this.fechaImportacion = fechaImportacion;
    }

    @Column(name = "TIPO")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Column(name = "ESTADO", length = 2)
    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    @Transient
    public List<String> getFicherosDirectorio() {
        return ficherosDirectorio;
    }

    public void setFicherosDirectorio(List<String> ficherosDirectorio) {
        this.ficherosDirectorio = ficherosDirectorio;
    }

    @Transient
    public List<String> getFicherosCatalogo() {
        return ficherosCatalogo;
    }

    public void setFicherosCatalogo(List<String> ficherosCatalogo) {
        this.ficherosCatalogo = ficherosCatalogo;
    }

    @Transient
    public void obtenerFicheros(){

        if(getTipo().equals(Dir3caibConstantes.DIRECTORIO)){
            File directorio = new File(Configuracio.getDirectorioPath(getCodigo()));

            if (directorio.exists()) {
                ficherosDirectorio = new ArrayList<String>(Arrays.asList(directorio.list()));
            }
            setFicherosDirectorio(ficherosDirectorio);


        }else if(getTipo().equals(Dir3caibConstantes.CATALOGO)){
            File catalogos = new File(Configuracio.getCatalogosPath(getCodigo()));

            if (catalogos.exists()) {
                ficherosCatalogo = new ArrayList<String>(Arrays.asList(catalogos.list()));
            }
            setFicherosCatalogo(ficherosCatalogo);
        }
    }
}
