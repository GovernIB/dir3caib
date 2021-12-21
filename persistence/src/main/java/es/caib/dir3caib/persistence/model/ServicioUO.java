package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author mgonzalez
 * 15/12/2021
 */

@Entity
@Table(name = "DIR_SERVICIOUO")
@org.hibernate.annotations.Table(appliesTo = "DIR_SERVICIOUO", indexes = {
        @Index(name = "DIR_SERVUO_CESTENT_FK_I", columnNames = "ESTADO"),
        @Index(name = "DIR_SERVUO_UNIDAD_FK_I", columnNames = "CODUNIDAD")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_SERVUO_SEQ", allocationSize=1)
public class ServicioUO  implements Serializable {

    private Long id;
    private Unidad unidad;
    private CatServicioUO servicio;
    private CatEstadoEntidad estado;

    public ServicioUO() {
    }

    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "CODUNIDAD")
    @ForeignKey(name="DIR_SERVUO_UNIDAD_FK")
    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    @ManyToOne
    @JoinColumn(name = "CODSERVICIO")
    @ForeignKey(name="DIR_SERVUO_CATSERVUO_FK")
    public CatServicioUO getServicio() {
        return servicio;
    }

    public void setServicio(CatServicioUO servicio) {
        this.servicio = servicio;
    }

    @ManyToOne
    @JoinColumn(name="ESTADO")
    @ForeignKey(name="DIR_SERVUO_CESTENT_FK")
    public CatEstadoEntidad getEstado() {
        return estado;
    }

    public void setEstado(CatEstadoEntidad estado) {
        this.estado = estado;
    }
}
