
package es.caib.dir3caib.ws.api.unidad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for contactoTF complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="contactoTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoContacto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorContacto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contactoTF", propOrder = {
        "tipoContacto",
        "valorContacto"
})
public class ContactoTF {

    protected String tipoContacto;
    protected String valorContacto;

    /**
     * Gets the value of the tipoContacto property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTipoContacto() {
        return tipoContacto;
    }

    /**
     * Sets the value of the tipoContacto property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTipoContacto(String value) {
        this.tipoContacto = value;
    }

    /**
     * Gets the value of the valorContacto property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getValorContacto() {
        return valorContacto;
    }

    /**
     * Sets the value of the valorContacto property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValorContacto(String value) {
        this.valorContacto = value;
    }

}
