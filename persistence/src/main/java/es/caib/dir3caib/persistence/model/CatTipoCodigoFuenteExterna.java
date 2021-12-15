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
@Table(name = "DIR_CATTIPOCODFUENTEEXTERNA", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATTIPOCODFUENTEEXTERNA", indexes = {
        @Index(name="DIR_CFUEEXT_CESTENT_FK_I", columnNames = "ESTADO")
})
public class CatTipoCodigoFuenteExterna implements Serializable {

    private Long codigoTipoCodigoFuenteExterna;
    private String descripcionTipoCodigoFuenteExterna;
    private CatEstadoEntidad estado;


    public CatTipoCodigoFuenteExterna() {
    }

    @Column(name = "CODIGOTIPCODFUENTEEXTERNA", nullable = false)
    @Id
    public Long getCodigoTipoCodigoFuenteExterna() {
        return codigoTipoCodigoFuenteExterna;
    }

    public void setCodigoTipoCodigoFuenteExterna(Long codigoTipoCodigoFuenteExterna) {
        this.codigoTipoCodigoFuenteExterna = codigoTipoCodigoFuenteExterna;
    }

    @Column(name = "DESCRIPCIONTIPCODFUENTEEXTERNA", nullable = false, length = 30)
    public String getDescripcionTipoCodigoFuenteExterna() {
        return descripcionTipoCodigoFuenteExterna;
    }

    public void setDescripcionTipoCodigoFuenteExterna(String descripcionTipoCodigoFuenteExterna) {
        this.descripcionTipoCodigoFuenteExterna = descripcionTipoCodigoFuenteExterna;
    }

    @ManyToOne
    @JoinColumn(name="ESTADO")
    @ForeignKey(name="DIR_CFUEEXT_CESTENT_FK")
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
        CatTipoCodigoFuenteExterna that = (CatTipoCodigoFuenteExterna) o;
        return codigoTipoCodigoFuenteExterna.equals(that.codigoTipoCodigoFuenteExterna) && descripcionTipoCodigoFuenteExterna.equals(that.descripcionTipoCodigoFuenteExterna) && estado.equals(that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoTipoCodigoFuenteExterna, descripcionTipoCodigoFuenteExterna, estado);
    }


}
