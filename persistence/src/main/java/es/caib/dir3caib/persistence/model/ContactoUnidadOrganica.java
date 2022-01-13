package es.caib.dir3caib.persistence.model;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @version 1.1
 * @created 28-oct-2013 14:41:39
 */
@Entity
@Table(name = "DIR_CONTACTOUO", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CONTACTOUO", indexes = {
        @Index(name="DIR_CONTUO_CATTIPOCONT_FK_I", columnNames = {"TIPOCONTACTO"}),
        @Index(name="DIR_CONTUO_UNIDAD_FK_I", columnNames = {"CODUNIDAD"}),
        @Index(name="DIR_CONTUO_CESTENT_FK_I", columnNames = {"ESTADO"}),

})
@SequenceGenerator(name="generator",sequenceName = "DIR_CONTUO_SEQ", allocationSize=1)
public class ContactoUnidadOrganica implements Serializable {

  private Long codContacto;
  private CatTipoContacto tipoContacto;
  private Unidad unidad;
  private String valorContacto;
  private boolean visibilidad;
  private CatEstadoEntidad estado;

  public ContactoUnidadOrganica(){

  }

  public ContactoUnidadOrganica(CatTipoContacto tipoContacto, String valorContacto) {
    this.tipoContacto = tipoContacto;
    this.valorContacto = valorContacto;
  }

  public void finalize() throws Throwable {

  }

  @Column(name = "CODCONTACTO", nullable = false, length = 6)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public Long getCodContacto() {
    return codContacto;
  }

  public void setCodContacto(Long codContacto) {
    this.codContacto = codContacto;
  }

  /**
   * @return the tipoContacto
   */
  @ManyToOne
  @JoinColumn(name="TIPOCONTACTO")
  @ForeignKey(name="DIR_CONTUO_CATTIPOCONT_FK")
  public CatTipoContacto getTipoContacto() {
    return tipoContacto;
  }

  /**
   * @param tipoContacto the tipoContacto to set
   */
  public void setTipoContacto(CatTipoContacto tipoContacto) {
    this.tipoContacto = tipoContacto;
  }

  /**
   * @return the unidad
   */
  @ManyToOne
  @JoinColumn(name="CODUNIDAD")
  @ForeignKey(name="DIR_CONTUO_UNIDAD_FK")
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
   * @return the valorContacto
   */
  @Column(name = "VALORCONTACTO", nullable = false, length = 100)
  public String getValorContacto() {
    return valorContacto;
  }

  /**
   * @param valorContacto the valorContacto to set
   */
  public void setValorContacto(String valorContacto) {
    this.valorContacto = valorContacto;
  }

  /**
   * @return the visibilidad
   */
  @Column(name = "VISIBILIDAD",  length = 1)
  public boolean isVisibilidad() {
    return visibilidad;
  }

  /**
   * @param visibilidad the visibilidad to set
   */
  public void setVisibilidad(boolean visibilidad) {
    this.visibilidad = visibilidad;
  }


  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CONTUO_CESTENT_FK")
  public CatEstadoEntidad getEstado() {
    return estado;
  }

  public void setEstado(CatEstadoEntidad estado) {
    this.estado = estado;
  }
}

