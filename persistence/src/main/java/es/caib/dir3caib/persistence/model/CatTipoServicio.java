package es.caib.dir3caib.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @version 1.1
 * @author mgonzalez
 * 18/11/2021
 */
@Entity
@Table(name = "DIR_CATTIPOSERVICIO", schema = "", catalog = "")
@SequenceGenerator(name="generator",sequenceName = "DIR_CTIPOSER_SEQ", allocationSize=1)
public class CatTipoServicio implements Serializable {

    private Long codigoTipoServicio;
    private String descripcionTipoServicio;

    public CatTipoServicio() {
    }

    @Column(name = "CODIGOTIPOSERVICIO", nullable = false)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
    public Long getCodigoTipoServicio() {
        return codigoTipoServicio;
    }

    public void setCodigoTipoServicio(Long codigoTipoServicio) {
        this.codigoTipoServicio = codigoTipoServicio;
    }



    @Column(name = "DESCRIPCIONTIPOSERVICIO", nullable = false, length = 300)
    public String getDescripcionTipoServicio() {
        return descripcionTipoServicio;
    }

    public void setDescripcionTipoServicio(String descripcionTipoServicio) {
        this.descripcionTipoServicio = descripcionTipoServicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatTipoServicio that = (CatTipoServicio) o;
        return codigoTipoServicio.equals(that.codigoTipoServicio) && descripcionTipoServicio.equals(that.descripcionTipoServicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoTipoServicio, descripcionTipoServicio);
    }

}
