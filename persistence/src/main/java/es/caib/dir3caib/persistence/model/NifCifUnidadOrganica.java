package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @version 1.1
 * @author mgonzalez
 * 24/11/2021
 */

@Entity
@Table(name = "DIR_NIFCIFUO")
@org.hibernate.annotations.Table(appliesTo = "DIR_NIFCIFUO", indexes = {
        @Index(name="DIR_NIFCIF_CESTENT_FK_I", columnNames = "ESTADO"),
        @Index(name="DIR_NIFCIF_UNIDAD_FK_I", columnNames = "CODUNIDAD")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_NIFCIF_SEQ", allocationSize=1)
public class NifCifUnidadOrganica implements Serializable {

    private Long id;
    private String codNifCif;
    private Unidad unidad;
    private boolean nifPrincipal;
    private CatEstadoEntidad estado;


    public NifCifUnidadOrganica() {
    }

    @Id
    @Column (name = "ID")
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column (name = "CODNIFCIF")
    public String getCodNifCif() {
        return codNifCif;
    }

    public void setCodNifCif(String codNifCif) {
        this.codNifCif = codNifCif;
    }

    /**
     * @return the unidad
     */
    @ManyToOne
    @JoinColumn(name="CODUNIDAD")
    @ForeignKey(name="DIR_NIFCIF_UNIDAD_FK")
    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    @Column(name = "NIFPRINCIPAL", length = 1)
    public boolean isNifPrincipal() {
        return nifPrincipal;
    }

    public void setNifPrincipal(boolean nifPrincipal) {
        this.nifPrincipal = nifPrincipal;
    }

    @ManyToOne
    @JoinColumn(name="ESTADO")
    @ForeignKey(name="DIR_NIFCIF_CESTENT_FK")
    public CatEstadoEntidad getEstado() {
        return estado;
    }

    public void setEstado(CatEstadoEntidad estado) {
        this.estado = estado;
    }
}
