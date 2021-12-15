package es.caib.dir3caib.persistence.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @version 1.1
 * @created 28-oct-2013 14:41:38
 */
@Entity
@Table(name = "DIR_CATCOMUNIDADAUTONOMA", schema = "", catalog = "")
@org.hibernate.annotations.Table(appliesTo = "DIR_CATCOMUNIDADAUTONOMA", indexes = {
    @Index(name="DIR_CATCOMUNAUT_CATPAIS_FK_I", columnNames = {"PAIS"}),
    @Index(name="DIR_CCOMAUT_CESTENT_FK_I", columnNames = "ESTADO")
})
public class CatComunidadAutonoma  implements Serializable {

	private Long codigoComunidad;
	private String descripcionComunidad;
	private CatPais pais;
	private String c_comunidad_rpc;
	private Long c_codigo_dir2;
    private CatEstadoEntidad estado;

	public CatComunidadAutonoma(){

	}

  public CatComunidadAutonoma(Long codigoComunidad) {
    this.codigoComunidad = codigoComunidad;
  }

  public void finalize() throws Throwable {

	}

  /**
   * @return the codigoComunidad
   */
  @Column(name = "CODIGOCOMUNIDAD", nullable = false, length = 2)
  @Id
  public Long getCodigoComunidad() {
    return codigoComunidad;
  }

  /**
   * @param codigoComunidad the codigoComunidad to set
   */
  public void setCodigoComunidad(Long codigoComunidad) {
    this.codigoComunidad = codigoComunidad;
  }

  /**
   * @return the descripcionComunidad
   */
  @Column(name = "DESCRIPCIONCOMUNIDAD", nullable = false, length = 50)
  public String getDescripcionComunidad() {
    return descripcionComunidad;
  }

  /**
   * @param descripcionComunidad the descripcionComunidad to set
   */
  public void setDescripcionComunidad(String descripcionComunidad) {
    this.descripcionComunidad = descripcionComunidad;
  }

  /**
   * @return the codigoPais
   */
  @ManyToOne(cascade= {CascadeType.PERSIST}, fetch = FetchType.LAZY)
  @JoinColumn(name="PAIS")
  @ForeignKey(name="DIR_CATCOMUNAUT_CATPAIS_FK")
  public CatPais getPais() {
    return pais;
  }

  /**
   * @param pais the codigoPais to set
   */
  public void setPais(CatPais pais) {
    this.pais = pais;
  }

  /**
   * @return the c_comunidad_rpc
   */
  @Column(name = "C_COMUNIDAD_RPC", length = 2)
  public String getC_comunidad_rpc() {
    return c_comunidad_rpc;
  }

  /**
   * @param c_comunidad_rpc the c_comunidad_rpc to set
   */
  public void setC_comunidad_rpc(String c_comunidad_rpc) {
    this.c_comunidad_rpc = c_comunidad_rpc;
  }

  /**
   * @return the c_codigo_dir2
   */
  @Column(name = "C_CODIGO_DIR2", length = 2)
  public Long getC_codigo_dir2() {
    return c_codigo_dir2;
  }

  /**
   * @param c_codigo_dir2 the c_codigo_dir2 to set
   */
  public void setC_codigo_dir2(Long c_codigo_dir2) {
    this.c_codigo_dir2 = c_codigo_dir2;
  }


  @ManyToOne
  @JoinColumn(name="ESTADO")
  @ForeignKey(name="DIR_CCOMAUT_CESTENT_FK")
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
    CatComunidadAutonoma that = (CatComunidadAutonoma) o;
    return codigoComunidad.equals(that.codigoComunidad) && descripcionComunidad.equals(that.descripcionComunidad)
            && pais.equals(that.pais) && c_comunidad_rpc.equals(that.c_comunidad_rpc) && c_codigo_dir2.equals(that.c_codigo_dir2)
            && estado.equals(that.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoComunidad, descripcionComunidad, pais, c_comunidad_rpc, c_codigo_dir2, estado);
  }

}