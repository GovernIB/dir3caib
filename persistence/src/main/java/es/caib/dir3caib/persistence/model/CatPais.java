package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * @version 1.0
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATPAIS")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATPAIS", indexes = {
        @Index(name="DIR_CPAIS_CESTENT_FK_I", columnNames = "ESTADO")
})
@SequenceGenerator(name="generator",sequenceName = "DIR_CPAIS_SEQ", allocationSize=1)
public class CatPais implements Serializable {

  private Long codigoPais;
  private String descripcionPais;
  private String alfa3Pais;
  private String alfa2Pais;
  private CatEstadoEntidad estado;

  public CatPais(){

  }

  public void finalize() throws Throwable {

  }

  /**
   * @return the codigoPais
   */
  @Column(name = "CODIGOPAIS", nullable = false, length = 3)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "generator")
  public Long getCodigoPais() {
    return codigoPais;
  }

  /**
   * @param codigoPais the codigoPais to set
   */
  public void setCodigoPais(Long codigoPais) {
    this.codigoPais = codigoPais;
  }

  /**
   * @return the descripcionPais
   */
  @Column(name = "DESCRIPCIONPAIS", nullable = false, length = 100)
  public String getDescripcionPais() {
    return descripcionPais;
  }

  /**
   * @param descripcionPais the descripcionPais to set
   */
  public void setDescripcionPais(String descripcionPais) {
    this.descripcionPais = descripcionPais;
  }

  /**
   * @return the alfa3Pais
   */
  @Column(name = "ALFA3PAIS",  length = 3)
  public String getAlfa3Pais() {
    return alfa3Pais;
  }

  /**
   * @param alfa3Pais the alfa3Pais to set
   */
  public void setAlfa3Pais(String alfa3Pais) {
    this.alfa3Pais = alfa3Pais;
  }

  /**
   * @return the alfa2Pais
   */
  @Column(name = "ALFA2PAIS", length = 2)
  public String getAlfa2Pais() {
    return alfa2Pais;
  }

  /**
   * @param alfa2Pais the alfa2Pais to set
   */
  public void setAlfa2Pais(String alfa2Pais) {
    this.alfa2Pais = alfa2Pais;
  }

  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CPAIS_CESTENT_FK")
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
    CatPais catPais = (CatPais) o;
    return codigoPais.equals(catPais.codigoPais) && descripcionPais.equals(catPais.descripcionPais) && alfa3Pais.equals(catPais.alfa3Pais) && alfa2Pais.equals(catPais.alfa2Pais) && estado.equals(catPais.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoPais, descripcionPais, alfa3Pais, alfa2Pais, estado);
  }

}