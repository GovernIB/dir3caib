
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for catProvinciaTF complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catProvinciaTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoComunidadAutonoma" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoProvincia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionProvincia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catProvinciaTF", propOrder = {
    "codigoComunidadAutonoma",
    "codigoProvincia",
    "descripcionProvincia"
})
public class CatProvinciaTF {

    protected Long codigoComunidadAutonoma;
    protected Long codigoProvincia;
    protected String descripcionProvincia;

    /**
     * Gets the value of the codigoComunidadAutonoma property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoComunidadAutonoma() {
        return codigoComunidadAutonoma;
    }

    /**
     * Sets the value of the codigoComunidadAutonoma property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoComunidadAutonoma(Long value) {
        this.codigoComunidadAutonoma = value;
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
     * Gets the value of the descripcionProvincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionProvincia() {
        return descripcionProvincia;
    }

    /**
     * Sets the value of the descripcionProvincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionProvincia(String value) {
        this.descripcionProvincia = value;
    }

}
