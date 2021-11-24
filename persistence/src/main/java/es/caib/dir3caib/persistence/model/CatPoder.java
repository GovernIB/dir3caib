package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @version 1.1
 * @author mgonzalez
 * 18/11/2021
 */
@Entity
@Table(name = "DIR_CATPODER", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATPODER", indexes = {
        @Index(name="DIR_CPODER_CESTENT_FK_I", columnNames = "ESTADO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CPODER_SEQ", allocationSize=1)
public class CatPoder implements Serializable {
    private Long codigoPoder;
    private String descripcionPoder;
    private CatEstadoEntidad estado;

    public CatPoder() {
    }

    @Column(name = "CODIGOPODER", nullable = false)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
    public Long getCodigoPoder() {
        return codigoPoder;
    }

    public void setCodigoPoder(Long codigoPoder) {
        this.codigoPoder = codigoPoder;
    }

    @Column(name = "DESCRIPCIONPODER", nullable = false, length = 50)
    public String getDescripcionPoder() {
        return descripcionPoder;
    }

    public void setDescripcionPoder(String descripcionPoder) {
        this.descripcionPoder = descripcionPoder;
    }

    @ManyToOne
    @JoinColumn(name="ESTADO")
    @ForeignKey(name="DIR_CPODER_CESTENT_FK")
    public CatEstadoEntidad getEstado() {
        return estado;
    }

    public void setEstado(CatEstadoEntidad estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatPoder catPoder = (CatPoder) o;
        return codigoPoder.equals(catPoder.codigoPoder) && descripcionPoder.equals(catPoder.descripcionPoder) && estado.equals(catPoder.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoPoder, descripcionPoder, estado);
    }

}
