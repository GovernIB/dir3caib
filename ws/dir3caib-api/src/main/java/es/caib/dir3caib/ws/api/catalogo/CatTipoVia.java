
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for catTipoVia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catTipoVia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoTipoVia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionTipoVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catTipoVia", propOrder = {
    "codigoTipoVia",
    "descripcionTipoVia"
})
public class CatTipoVia {

    protected Long codigoTipoVia;
    protected String descripcionTipoVia;

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
     * Gets the value of the descripcionTipoVia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionTipoVia() {
        return descripcionTipoVia;
    }

    /**
     * Sets the value of the descripcionTipoVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionTipoVia(String value) {
        this.descripcionTipoVia = value;
    }

}
