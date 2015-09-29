
package es.caib.dir3caib.ws.api.oficina;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for oficinaTF complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oficinaTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codOfiResponsable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codPostal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codUoResponsable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoTipoVia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="denominacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionLocalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nivelAdministracion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nombreVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organizativasOfi" type="{http://oficina.ws.dir3caib.caib.es/}relacionOrganizativaOfiTF" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="servicios" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sirOfi" type="{http://oficina.ws.dir3caib.caib.es/}relacionSirOfiTF" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tipoOficina" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oficinaTF", propOrder = {
    "codOfiResponsable",
    "codPostal",
    "codUoResponsable",
    "codigo",
    "codigoTipoVia",
    "denominacion",
    "descripcionLocalidad",
    "estado",
    "nivelAdministracion",
    "nombreVia",
    "numVia",
    "organizativasOfi",
    "servicios",
    "sirOfi",
    "tipoOficina"
})
public class OficinaTF {

    protected String codOfiResponsable;
    protected String codPostal;
    protected String codUoResponsable;
    protected String codigo;
    protected Long codigoTipoVia;
    protected String denominacion;
    protected String descripcionLocalidad;
    protected String estado;
    protected Long nivelAdministracion;
    protected String nombreVia;
    protected String numVia;
    @XmlElement(nillable = true)
    protected List<RelacionOrganizativaOfiTF> organizativasOfi;
    @XmlElement(nillable = true)
    protected List<Long> servicios;
    @XmlElement(nillable = true)
    protected List<RelacionSirOfiTF> sirOfi;
    protected Long tipoOficina;

    /**
     * Gets the value of the codOfiResponsable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodOfiResponsable() {
        return codOfiResponsable;
    }

    /**
     * Sets the value of the codOfiResponsable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodOfiResponsable(String value) {
        this.codOfiResponsable = value;
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
     * Gets the value of the codUoResponsable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodUoResponsable() {
        return codUoResponsable;
    }

    /**
     * Sets the value of the codUoResponsable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodUoResponsable(String value) {
        this.codUoResponsable = value;
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
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
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

    /**
     * Gets the value of the organizativasOfi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organizativasOfi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganizativasOfi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelacionOrganizativaOfiTF }
     * 
     * 
     */
    public List<RelacionOrganizativaOfiTF> getOrganizativasOfi() {
        if (organizativasOfi == null) {
            organizativasOfi = new ArrayList<RelacionOrganizativaOfiTF>();
        }
        return this.organizativasOfi;
    }

    /**
     * Gets the value of the servicios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the servicios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServicios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getServicios() {
        if (servicios == null) {
            servicios = new ArrayList<Long>();
        }
        return this.servicios;
    }

    /**
     * Gets the value of the sirOfi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sirOfi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSirOfi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelacionSirOfiTF }
     * 
     * 
     */
    public List<RelacionSirOfiTF> getSirOfi() {
        if (sirOfi == null) {
            sirOfi = new ArrayList<RelacionSirOfiTF>();
        }
        return this.sirOfi;
    }

    /**
     * Gets the value of the tipoOficina property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTipoOficina() {
        return tipoOficina;
    }

    /**
     * Sets the value of the tipoOficina property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTipoOficina(Long value) {
        this.tipoOficina = value;
    }

}
