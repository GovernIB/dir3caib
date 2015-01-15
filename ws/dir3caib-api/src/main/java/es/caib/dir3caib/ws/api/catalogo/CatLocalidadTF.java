
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for catLocalidadTF complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catLocalidadTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoEntidadGeografica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoLocalidad" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoProvincia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionLocalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catLocalidadTF", propOrder = {
    "codigoEntidadGeografica",
    "codigoLocalidad",
    "codigoProvincia",
    "descripcionLocalidad"
})
public class CatLocalidadTF {

    protected String codigoEntidadGeografica;
    protected Long codigoLocalidad;
    protected Long codigoProvincia;
    protected String descripcionLocalidad;

    /**
     * Gets the value of the codigoEntidadGeografica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoEntidadGeografica() {
        return codigoEntidadGeografica;
    }

    /**
     * Sets the value of the codigoEntidadGeografica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoEntidadGeografica(String value) {
        this.codigoEntidadGeografica = value;
    }

    /**
     * Gets the value of the codigoLocalidad property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoLocalidad() {
        return codigoLocalidad;
    }

    /**
     * Sets the value of the codigoLocalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoLocalidad(Long value) {
        this.codigoLocalidad = value;
    }

    /**
     * Gets the value of the codigoProvincia property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoProvincia() {
        return codigoProvincia;
    }

    /**
     * Sets the value of the codigoProvincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoProvincia(Long value) {
        this.codigoProvincia = value;
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

}
