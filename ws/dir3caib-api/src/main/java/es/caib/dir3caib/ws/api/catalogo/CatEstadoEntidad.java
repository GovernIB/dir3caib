
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for catEstadoEntidad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catEstadoEntidad">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoEstadoEntidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionEstadoEntidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catEstadoEntidad", propOrder = {
    "codigoEstadoEntidad",
    "descripcionEstadoEntidad"
})
public class CatEstadoEntidad {

    protected String codigoEstadoEntidad;
    protected String descripcionEstadoEntidad;

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
     * Gets the value of the descripcionEstadoEntidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionEstadoEntidad() {
        return descripcionEstadoEntidad;
    }

    /**
     * Sets the value of the descripcionEstadoEntidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionEstadoEntidad(String value) {
        this.descripcionEstadoEntidad = value;
    }

}
