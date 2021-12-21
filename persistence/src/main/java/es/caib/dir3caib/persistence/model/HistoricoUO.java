package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author mgonzalez
 * 25/11/2021
 */
@Entity
@Table(name = "DIR_HISTORICOUO", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_HISTORICOUO", indexes = {
        @Index(name="DIR_HISTOUO_CESTENT_FK_I", columnNames = "ESTADO"),
        @Index(name = "DIR_HISTUO_UNIDAD_FK_I", columnNames = "CODUNIDAD"),
        @Index(name = "DIR_HISTUO_OFICINA_FK_I", columnNames = "CODOFICINA")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_HISTUO_SEQ", allocationSize=1)
public class HistoricoUO implements Serializable {

    private Long id;
    private Unidad unidadAnterior;
    private Unidad unidadUltima;
    private CatEstadoEntidad estado;
    private String motivoRelacion;
    private String observacionExtincion;



    public HistoricoUO() {
    }

    public HistoricoUO(Unidad unidadAnterior, Unidad unidadUltima, CatEstadoEntidad estado, String motivoRelacion, String observacionExtincion) {
        this.unidadAnterior = unidadAnterior;
        this.unidadUltima = unidadUltima;
        this.estado = estado;
        this.motivoRelacion = motivoRelacion;
        this.observacionExtincion = observacionExtincion;
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
    @JoinColumn(name = "CODANTERIOR")
    @ForeignKey(name="DIR_HISTO_UNIANT_FK")
    public Unidad getUnidadAnterior() {
        return unidadAnterior;
    }

    public void setUnidadAnterior(Unidad unidadAnterior) {
        this.unidadAnterior = unidadAnterior;
    }

    @ManyToOne
    @JoinColumn(name = "CODULTIMA")
    @ForeignKey(name="DIR_HISTO_UNIULT_FK")
    public Unidad getUnidadUltima() {
        return unidadUltima;
    }

    public void setUnidadUltima(Unidad unidadUltima) {
        this.unidadUltima = unidadUltima;
    }

    @ManyToOne
    @JoinColumn(name="ESTADO")
    @ForeignKey(name="DIR_HISTO_CESTENT_FK")
    public CatEstadoEntidad getEstado() {
        return estado;
    }

    public void setEstado(CatEstadoEntidad estado) {
        this.estado = estado;
    }
    @Column(name = "MOTIVORELACION")
    public String getMotivoRelacion() {
        return motivoRelacion;
    }

    public void setMotivoRelacion(String motivoRelacion) {
        this.motivoRelacion = motivoRelacion;
    }

    @Column(name = "OBSERVEXTINCION")
    public String getObservacionExtincion() {
        return observacionExtincion;
    }

    public void setObservacionExtincion(String observacionExtincion) {
        this.observacionExtincion = observacionExtincion;
    }


}
