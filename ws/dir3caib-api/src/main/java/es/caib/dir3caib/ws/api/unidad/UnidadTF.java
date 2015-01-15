
package es.caib.dir3caib.ws.api.unidad;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="codUnidadRaiz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codUnidadSuperior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoEstadoEntidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="competencias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denominacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaAltaOficial" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaAnulacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaBajaOficial" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaExtincion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="historicosUO" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nivelAdministracion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nivelJerarquico" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
    "codUnidadRaiz",
    "codUnidadSuperior",
    "codigo",
    "codigoEstadoEntidad",
    "competencias",
    "denominacion",
    "fechaAltaOficial",
    "fechaAnulacion",
    "fechaBajaOficial",
    "fechaExtincion",
    "historicosUO",
    "nivelAdministracion",
    "nivelJerarquico"
})
public class UnidadTF {

    protected Long codAmbComunidad;
    protected Long codAmbProvincia;
    protected String codUnidadRaiz;
    protected String codUnidadSuperior;
    protected String codigo;
    protected String codigoEstadoEntidad;
    protected String competencias;
    protected String denominacion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAltaOficial;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAnulacion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaBajaOficial;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaExtincion;
    @XmlElement(nillable = true)
    protected List<String> historicosUO;
    protected Long nivelAdministracion;
    protected Long nivelJerarquico;

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
     * Gets the value of the fechaAltaOficial property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaAltaOficial() {
        return fechaAltaOficial;
    }

    /**
     * Sets the value of the fechaAltaOficial property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaAltaOficial(XMLGregorianCalendar value) {
        this.fechaAltaOficial = value;
    }

    /**
     * Gets the value of the fechaAnulacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaAnulacion() {
        return fechaAnulacion;
    }

    /**
     * Sets the value of the fechaAnulacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaAnulacion(XMLGregorianCalendar value) {
        this.fechaAnulacion = value;
    }

    /**
     * Gets the value of the fechaBajaOficial property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaBajaOficial() {
        return fechaBajaOficial;
    }

    /**
     * Sets the value of the fechaBajaOficial property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaBajaOficial(XMLGregorianCalendar value) {
        this.fechaBajaOficial = value;
    }

    /**
     * Gets the value of the fechaExtincion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaExtincion() {
        return fechaExtincion;
    }

    /**
     * Sets the value of the fechaExtincion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaExtincion(XMLGregorianCalendar value) {
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

}
