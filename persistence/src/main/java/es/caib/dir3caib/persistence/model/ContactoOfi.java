package es.caib.dir3caib.persistence.model;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

/**
 * @version 1.1
 * @created 28-oct-2013 14:41:39
 */
@Table(name = "DIR_CONTACTOOFI", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CONTACTOOFI", indexes = {
        @Index(name="DIR_CONOFI_CATTIPCONT_FK_I", columnNames = {"TIPOCONTACTO"}),
        @Index(name="DIR_CONOFI_OFICINA_FK_I", columnNames = {"CODOFICINA"}),
        @Index(name="DIR_CONOFI_CESTENT_FK_I", columnNames = {"ESTADO"})
})
@Entity
@SequenceGenerator(name="generator",sequenceName = "DIR_CONOF_SEQ", allocationSize=1)
public class ContactoOfi implements Serializable {

  private Long codContacto;
  private Oficina oficina;
  private CatTipoContacto tipoContacto;
  private String valorContacto;
  private boolean visibilidad;
  private CatEstadoEntidad estado;

  public ContactoOfi(){

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
   * @return the oficina
   */
  @ManyToOne
  @JoinColumn(name="CODOFICINA")
  @ForeignKey(name="DIR_CONOFI_OFICINA_FK")
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
   * @return the tipoContacto
   */
  @ManyToOne
  @JoinColumn(name="TIPOCONTACTO")
  @ForeignKey(name="DIR_CONOFI_CATTIPCONT_FK")
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
  @ForeignKey(name="DIR_CONOFI_CESTENT_FK")
  public CatEstadoEntidad getEstado() {
    return estado;
  }

  public void setEstado(CatEstadoEntidad estado) {
    this.estado = estado;
  }



}