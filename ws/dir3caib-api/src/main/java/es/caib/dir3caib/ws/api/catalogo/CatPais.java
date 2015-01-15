
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for catPais complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catPais">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alfa2Pais" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alfa3Pais" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoPais" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionPais" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catPais", propOrder = {
    "alfa2Pais",
    "alfa3Pais",
    "codigoPais",
    "descripcionPais"
})
public class CatPais {

    protected String alfa2Pais;
    protected String alfa3Pais;
    protected Long codigoPais;
    protected String descripcionPais;

    /**
     * Gets the value of the alfa2Pais property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlfa2Pais() {
        return alfa2Pais;
    }

    /**
     * Sets the value of the alfa2Pais property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlfa2Pais(String value) {
        this.alfa2Pais = value;
    }

    /**
     * Gets the value of the alfa3Pais property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlfa3Pais() {
        return alfa3Pais;
    }

    /**
     * Sets the value of the alfa3Pais property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlfa3Pais(String value) {
        this.alfa3Pais = value;
    }

    /**
     * Gets the value of the codigoPais property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoPais() {
        return codigoPais;
    }

    /**
     * Sets the value of the codigoPais property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoPais(Long value) {
        this.codigoPais = value;
    }

    /**
     * Gets the value of the descripcionPais property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionPais() {
        return descripcionPais;
    }

    /**
     * Sets the value of the descripcionPais property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionPais(String value) {
        this.descripcionPais = value;
    }

}
