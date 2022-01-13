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
@Table(name = "DIR_CODIGOUO")
@org.hibernate.annotations.Table(appliesTo = "DIR_CODIGOUO", indexes = {
        @Index(name="DIR_CODUO_CESTENT_FK_I", columnNames = "ESTADO"),
        @Index(name="DIR_CODUO_CFUEEXT_FK_I", columnNames = "TIPOCODIGO"),
        @Index(name="DIR_CODUO_UNIDAD_FK_I", columnNames = "CODUNIDAD")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CODUO_SEQ", allocationSize=1)
public class CodigoUnidadOrganica  implements Serializable {

    private Long codUnidadOrganica;
    private Unidad unidad;
    private String codigoExterno;
    private CatTipoCodigoFuenteExterna tipoCodigo;
    private CatEstadoEntidad estado;

    public CodigoUnidadOrganica() {
    }

    @Column(name = "CODUNIDADORGANICA", nullable = false)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
    public Long getCodUnidadOrganica() {
        return codUnidadOrganica;
    }

    public void setCodUnidadOrganica(Long codUnidadOrganica) {
        this.codUnidadOrganica = codUnidadOrganica;
    }

    @ManyToOne
    @JoinColumn(name="CODUNIDAD")
    @ForeignKey(name="DIR_CODUO_UNIDAD_FK")
    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    @Column(name = "CODIGOEXTERNO")
    public String getCodigoExterno() {
        return codigoExterno;
    }

    public void setCodigoExterno(String codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    @ManyToOne
    @JoinColumn(name="TIPOCODIGO")
    @ForeignKey(name="DIR_CODUO_CFUEEXT_FK")
    public CatTipoCodigoFuenteExterna getTipoCodigo() {
        return tipoCodigo;
    }

    public void setTipoCodigo(CatTipoCodigoFuenteExterna tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    @ManyToOne
    @JoinColumn(name="ESTADO")
    @ForeignKey(name="DIR_CODUO_CESTENT_FK")
    public CatEstadoEntidad getEstado() {
        return estado;
    }

    public void setEstado(CatEstadoEntidad estado) {
        this.estado = estado;
    }


}
