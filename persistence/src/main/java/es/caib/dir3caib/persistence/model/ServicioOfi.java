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
@Table(name = "DIR_SERVICIOOFI")
@org.hibernate.annotations.Table(appliesTo = "DIR_SERVICIOOFI", indexes = {
        @Index(name = "DIR_SERVOFI_CESTENT_FK_I", columnNames = "ESTADO"),
        @Index(name = "DIR_SERVOFI_OFICINA_FK_I", columnNames = "CODOFICINA")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_SERVOFI_SEQ", allocationSize=1)
public class ServicioOfi  implements Serializable {

    private Long id;
    private Oficina oficina;
    private CatServicio servicio;
    private CatEstadoEntidad estado;

    public ServicioOfi() {
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
    @JoinColumn(name = "CODOFICINA")
    @ForeignKey(name="DIR_SERVOFI_OFICINA_FK")
    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    @ManyToOne
    @JoinColumn(name = "CODSERVICIO")
    @ForeignKey(name="DIR_SERVOFI_CATSERVOFI_FK")
    public CatServicio getServicio() {
        return servicio;
    }

    public void setServicio(CatServicio servicio) {
        this.servicio = servicio;
    }

    @ManyToOne
    @JoinColumn(name="ESTADO")
    @ForeignKey(name="DIR_SERVOFI_CESTENT_FK")
    public CatEstadoEntidad getEstado() {
        return estado;
    }

    public void setEstado(CatEstadoEntidad estado) {
        this.estado = estado;
    }
}

