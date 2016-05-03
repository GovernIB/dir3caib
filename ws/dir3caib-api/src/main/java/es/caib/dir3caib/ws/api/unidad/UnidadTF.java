
package es.caib.dir3caib.ws.api.unidad;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for unidadTF complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="unidadTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codAmbComunidad" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codAmbProvincia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codEdpPrincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codPostal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codUnidadRaiz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codUnidadSuperior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoAmbPais" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoAmbitoTerritorial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoEstadoEntidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoTipoVia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="competencias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denominacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionLocalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esEdp" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="fechaAltaOficial" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaAnulacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaBajaOficial" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaExtincion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="historicosUO" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nivelAdministracion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nivelJerarquico" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nombreVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unidadTF", propOrder = {
    "codAmbComunidad",
    "codAmbProvincia",
        "codEdpPrincipal",
    "codPostal",
    "codUnidadRaiz",
    "codUnidadSuperior",
    "codigo",
    "codigoAmbPais",
    "codigoAmbitoTerritorial",
    "codigoEstadoEntidad",
    "codigoTipoVia",
    "competencias",
    "denominacion",
    "descripcionLocalidad",
        "esEdp",
    "fechaAltaOficial",
    "fechaAnulacion",
    "fechaBajaOficial",
    "fechaExtincion",
    "historicosUO",
    "nivelAdministracion",
    "nivelJerarquico",
    "nombreVia",
    "numVia"
})
public class UnidadTF {

    protected Long codAmbComunidad;
    protected Long codAmbProvincia;
    protected String codEdpPrincipal;
    protected String codPostal;
    protected String codUnidadRaiz;
    protected String codUnidadSuperior;
    protected String codigo;
    protected Long codigoAmbPais;
    protected String codigoAmbitoTerritorial;
    protected String codigoEstadoEntidad;
    protected Long codigoTipoVia;
    protected String competencias;
    protected String denominacion;
    protected String descripcionLocalidad;
    protected boolean esEdp;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaAltaOficial;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaAnulacion;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaBajaOficial;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaExtincion;
    @XmlElement(nillable = true)
    protected List<String> historicosUO;
    protected Long nivelAdministracion;
    protected Long nivelJerarquico;
    protected String nombreVia;
    protected String numVia;

    /**
     * Gets the value of the codAmbComunidad property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodAmbComunidad() {
        return codAmbComunidad;
    }

    /**
     * Sets the value of the codAmbComunidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodAmbComunidad(Long value) {
        this.codAmbComunidad = value;
    }

    /**
     * Gets the value of the codAmbProvincia property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodAmbProvincia() {
        return codAmbProvincia;
    }

    /**
     * Sets the value of the codAmbProvincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodAmbProvincia(Long value) {
        this.codAmbProvincia = value;
    }

    /**
     * Gets the value of the codEdpPrincipal property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCodEdpPrincipal() {
        return codEdpPrincipal;
    }

    /**
     * Sets the value of the codEdpPrincipal property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCodEdpPrincipal(String value) {
        this.codEdpPrincipal = value;
    }

    /**
     * Gets the value of the codPostal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodPostal() {
        return codPostal;
    }

    /**
     * Sets the value of the codPostal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodPostal(String value) {
        this.codPostal = value;
    }

    /**
     * Gets the value of the codUnidadRaiz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodUnidadRaiz() {
        return codUnidadRaiz;
    }

    /**
     * Sets the value of the codUnidadRaiz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodUnidadRaiz(String value) {
        this.codUnidadRaiz = value;
    }

    /**
     * Gets the value of the codUnidadSuperior property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodUnidadSuperior() {
        return codUnidadSuperior;
    }

    /**
     * Sets the value of the codUnidadSuperior property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodUnidadSuperior(String value) {
        this.codUnidadSuperior = value;
    }

    /**
     * Gets the value of the codigo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the codigoAmbPais property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoAmbPais() {
        return codigoAmbPais;
    }

    /**
     * Sets the value of the codigoAmbPais property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoAmbPais(Long value) {
        this.codigoAmbPais = value;
    }

    /**
     * Gets the value of the codigoAmbitoTerritorial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoAmbitoTerritorial() {
        return codigoAmbitoTerritorial;
    }

    /**
     * Sets the value of the codigoAmbitoTerritorial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoAmbitoTerritorial(String value) {
        this.codigoAmbitoTerritorial = value;
    }

    /**
     * Gets the value of the codigoEstadoEntidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoEstadoEntidad() {
        return codigoEstadoEntidad;
    }

    /**
     * Sets the value of the codigoEstadoEntidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoEstadoEntidad(String value) {
        this.codigoEstadoEntidad = value;
    }

    /**
     * Gets the value of the codigoTipoVia property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoTipoVia() {
        return codigoTipoVia;
    }

    /**
     * Sets the value of the codigoTipoVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoTipoVia(Long value) {
        this.codigoTipoVia = value;
    }

    /**
     * Gets the value of the competencias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompetencias() {
        return competencias;
    }

    /**
     * Sets the value of the competencias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompetencias(String value) {
        this.competencias = value;
    }

    /**
     * Gets the value of the denominacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     * Sets the value of the denominacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominacion(String value) {
        this.denominacion = value;
    }

    /**
     * Gets the value of the descripcionLocalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionLocalidad() {
        return descripcionLocalidad;
    }

    /**
     * Sets the value of the descripcionLocalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionLocalidad(String value) {
        this.descripcionLocalidad = value;
    }

    /**
     * Gets the value of the esEdp property.
     *
     */
    public boolean isEsEdp() {
        return esEdp;
    }

    /**
     * Sets the value of the esEdp property.
     */
    public void setEsEdp(boolean value) {
        this.esEdp = value;
    }

    /**
     * Gets the value of the fechaAltaOficial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Timestamp getFechaAltaOficial() {
        return fechaAltaOficial;
    }

    /**
     * Sets the value of the fechaAltaOficial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAltaOficial(Timestamp value) {
        this.fechaAltaOficial = value;
    }

    /**
     * Gets the value of the fechaAnulacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Timestamp getFechaAnulacion() {
        return fechaAnulacion;
    }

    /**
     * Sets the value of the fechaAnulacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAnulacion(Timestamp value) {
        this.fechaAnulacion = value;
    }

    /**
     * Gets the value of the fechaBajaOficial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Timestamp getFechaBajaOficial() {
        return fechaBajaOficial;
    }

    /**
     * Sets the value of the fechaBajaOficial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaBajaOficial(Timestamp value) {
        this.fechaBajaOficial = value;
    }

    /**
     * Gets the value of the fechaExtincion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Timestamp getFechaExtincion() {
        return fechaExtincion;
    }

    /**
     * Sets the value of the fechaExtincion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaExtincion(Timestamp value) {
        this.fechaExtincion = value;
    }

    /**
     * Gets the value of the historicosUO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the historicosUO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistoricosUO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getHistoricosUO() {
        if (historicosUO == null) {
            historicosUO = new ArrayList<String>();
        }
        return this.historicosUO;
    }

    /**
     * Gets the value of the nivelAdministracion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNivelAdministracion() {
        return nivelAdministracion;
    }

    /**
     * Sets the value of the nivelAdministracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNivelAdministracion(Long value) {
        this.nivelAdministracion = value;
    }

    /**
     * Gets the value of the nivelJerarquico property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNivelJerarquico() {
        return nivelJerarquico;
    }

    /**
     * Sets the value of the nivelJerarquico property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNivelJerarquico(Long value) {
        this.nivelJerarquico = value;
    }

    /**
     * Gets the value of the nombreVia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreVia() {
        return nombreVia;
    }

    /**
     * Sets the value of the nombreVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreVia(String value) {
        this.nombreVia = value;
    }

    /**
     * Gets the value of the numVia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumVia() {
        return numVia;
    }

    /**
     * Sets the value of the numVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumVia(String value) {
        this.numVia = value;
    }

}
