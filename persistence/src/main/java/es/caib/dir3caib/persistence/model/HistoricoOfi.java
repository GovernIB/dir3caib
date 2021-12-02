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
@Table(name = "DIR_HISTORICOOFI", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_HISTORICOOFI", indexes = {
        @Index(name="DIR_HISOF_CESTENT_FK_I", columnNames = "ESTADO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_HISOFI_SEQ", allocationSize=1)
public class HistoricoOfi implements Serializable {

    private Long id;
    private Oficina oficinaAnterior;
    private Oficina oficinaUltima;
    private CatEstadoEntidad estado;
    private String motivoRelacion;



    public HistoricoOfi() {
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
    @ForeignKey(name="DIR_HISTO_OFIANT_FK")
    public Oficina getOficinaAnterior() {
        return oficinaAnterior;
    }

    public void setOficinaAnterior(Oficina oficinaAnterior) {
        this.oficinaAnterior = oficinaAnterior;
    }
    @ManyToOne
    @JoinColumn(name = "CODULTIMA")
    @ForeignKey(name="DIR_HISTO_OFIULT_FK")
    public Oficina getOficinaUltima() {
        return oficinaUltima;
    }

    public void setOficinaUltima(Oficina oficinaUltima) {
        this.oficinaUltima = oficinaUltima;
    }


    @ManyToOne
    @JoinColumn(name="ESTADO")
    @ForeignKey(name="DIR_HISOF_CESTENT_FK")
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



}
