package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:39
 */
@Entity
@Table(name = "DIR_CATSERVICIO", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATSERVICIO", indexes = {
        @Index(name="DIR_CSERVIC_CTIPSERV_FK_I", columnNames = "TIPO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CSERV_SEQ", allocationSize=1)
public class Servicio implements Serializable {

  private Long codServicio;
  private String descServicio;
  private CatTipoServicio tipo;



  public Servicio(){

  }

  public Servicio(Long codServicio) {
    this.codServicio = codServicio;
  }


  public void finalize() throws Throwable {

	}

  /**
   * @return the codServicio
   */
  @Column(name = "CODSERVICIO", nullable = false, length = 6)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public Long getCodServicio() {
    return codServicio;
  }

  /**
   * @param codServicio the codServicio to set
   */
  public void setCodServicio(Long codServicio) {
    this.codServicio = codServicio;
  }

  /**
   * @return the descServicio
   */
  @Column(name = "DESCSERVICIO", nullable = false, length = 300)
  public String getDescServicio() {
    return descServicio;
  }

  /**
   * @param descServicio the descServicio to set
   */
  public void setDescServicio(String descServicio) {
    this.descServicio = descServicio;
  }

  @ManyToOne
  @JoinColumn(name="TIPO")
  @ForeignKey(name="DIR_CSERVIC_CTIPSERV_FK")
  public CatTipoServicio getTipo() {
    return tipo;
  }

  public void setTipo(CatTipoServicio tipo) {
    this.tipo = tipo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Servicio servicio = (Servicio) o;
    return codServicio.equals(servicio.codServicio) && descServicio.equals(servicio.descServicio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codServicio, descServicio);
  }



}