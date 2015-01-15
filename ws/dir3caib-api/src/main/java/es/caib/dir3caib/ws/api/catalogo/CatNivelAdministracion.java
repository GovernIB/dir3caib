
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for catNivelAdministracion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catNivelAdministracion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoNivelAdministracion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionNivelAdministracion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catNivelAdministracion", propOrder = {
    "codigoNivelAdministracion",
    "descripcionNivelAdministracion"
})
public class CatNivelAdministracion {

    protected Long codigoNivelAdministracion;
    protected String descripcionNivelAdministracion;

    /**
     * Gets the value of the codigoNivelAdministracion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoNivelAdministracion() {
        return codigoNivelAdministracion;
    }

    /**
     * Sets the value of the codigoNivelAdministracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoNivelAdministracion(Long value) {
        this.codigoNivelAdministracion = value;
    }

    /**
     * Gets the value of the descripcionNivelAdministracion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionNivelAdministracion() {
        return descripcionNivelAdministracion;
    }

    /**
     * Sets the value of the descripcionNivelAdministracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionNivelAdministracion(String value) {
        this.descripcionNivelAdministracion = value;
    }

}
