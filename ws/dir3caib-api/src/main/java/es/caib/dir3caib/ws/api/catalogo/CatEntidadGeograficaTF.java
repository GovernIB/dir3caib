
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for catEntidadGeograficaTF complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catEntidadGeograficaTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoEntidadGeografica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionEntidadGeografica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catEntidadGeograficaTF", propOrder = {
    "codigoEntidadGeografica",
    "descripcionEntidadGeografica"
})
public class CatEntidadGeograficaTF {

    protected String codigoEntidadGeografica;
    protected String descripcionEntidadGeografica;

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
     * Gets the value of the descripcionEntidadGeografica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionEntidadGeografica() {
        return descripcionEntidadGeografica;
    }

    /**
     * Sets the value of the descripcionEntidadGeografica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionEntidadGeografica(String value) {
        this.descripcionEntidadGeografica = value;
    }

}
