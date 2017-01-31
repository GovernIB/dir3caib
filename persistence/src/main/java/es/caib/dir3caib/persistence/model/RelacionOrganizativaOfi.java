package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @version 1.0
 * @created 28-oct-2013 14:41:39
 * Esta relación representa la dependencia funcional entre unidades y oficinas
 */
@Table(name = "DIR_RELACIONORGANIZATIVAOFI",
        uniqueConstraints = @UniqueConstraint(columnNames = {"CODOFICINA", "CODUNIDAD"}))
@org.hibernate.annotations.Table(appliesTo = "DIR_RELACIONORGANIZATIVAOFI", indexes = {
        @Index(name = "DIR_RELORGANOFI_CATESTENT_FK_I", columnNames = {"ESTADO"}),
        @Index(name = "DIR_UNIDAD_RELORGOFI_FK_I", columnNames = {"CODUNIDAD"}),
        @Index(name = "DIR_OFICINA_RELORGOFI_FK_I", columnNames = {"CODOFICINA"})
})
@Entity
@SequenceGenerator(name = "generator", sequenceName = "DIR_SEQ_ALL", allocationSize = 1)
public class RelacionOrganizativaOfi implements Serializable {

    private Long id;
    private Oficina oficina;
    private Unidad unidad;
    private CatEstadoEntidad estado;

    public RelacionOrganizativaOfi() {

    }

    public void finalize() throws Throwable {

    }

    /**
     * @return the oficina
     */

    @ManyToOne(cascade = CascadeType.PERSIST,optional = false)
    @JoinColumn(name = "CODOFICINA")
    @ForeignKey(name = "DIR_RELORGOFI_CATOFI_FK")
    public Oficina getOficina() {
        return oficina;
    }

    /**
     * @param oficina the codOficina to set
     */
    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    /**
     * @return the unidad
     */

    @ManyToOne(optional = false)
    @JoinColumn(name = "CODUNIDAD")
    @ForeignKey(name = "DIR_RELORGOFI_CATUNIDAD_FK")
    public Unidad getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the estado
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ESTADO")
    @ForeignKey(name = "DIR_RELORGANOFI_CATESTENT_FK")
    public CatEstadoEntidad getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(CatEstadoEntidad estado) {
        this.estado = estado;
    }


    @Id
    @JoinColumn(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelacionOrganizativaOfi that = (RelacionOrganizativaOfi) o;

        if (!estado.equals(that.estado)) return false;
        if (!oficina.equals(that.oficina)) return false;
        if (!unidad.equals(that.unidad)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oficina.hashCode();
        result = 31 * result + unidad.hashCode();
        result = 31 * result + estado.hashCode();
        return result;
    }
}