package es.caib.dir3caib.persistence.model;
import es.caib.dir3caib.persistence.model.CatTipoContacto;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

/**
 * @version 1.1
 * @created 28-oct-2013 14:41:39
 */
@Table(name = "DIR_CONTACTOOFI", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CONTACTOOFI", indexes = {
    @Index(name="DIR_CONTOFI_CATTIPCONT_FK_I", columnNames = {"TIPOCONTACTO"}),
    @Index(name="DIR_OFICINA_CONTACTOSOFI_FK_I", columnNames = {"CODOFICINA"})
})
@Entity
public class ContactoOfi implements Serializable {
  
  private Long codContacto;
	private Oficina oficina;
	private CatTipoContacto tipoContacto;
	private String valorContacto;
	private boolean visibilidad;

	public ContactoOfi(){

	}

	public void finalize() throws Throwable {

	}
  @Column(name = "CODCONTACTO", nullable = false, length = 6)
  @Id
  public Long getCodContacto() {
    return codContacto;
  }

  public void setCodContacto(Long codContacto) {
    this.codContacto = codContacto;
  }
  
  

  /**
   * @return the codOficina
   */
  @ManyToOne
  @JoinColumn(name="CODOFICINA") 
  @ForeignKey(name="DIR_CONTACTOOFI_OFICINA_FK")
  public Oficina getOficina() {
    return oficina;
  }

  /**
   * @param codOficina the codOficina to set
   */
  public void setOficina(Oficina oficina) {
    this.oficina = oficina;
  }

  /**
   * @return the tipoContacto
   */
  @ManyToOne
  @JoinColumn(name="TIPOCONTACTO") 
  @ForeignKey(name="DIR_CONTACOFI_CATTIPCONTAC_FK")
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
  
  

}