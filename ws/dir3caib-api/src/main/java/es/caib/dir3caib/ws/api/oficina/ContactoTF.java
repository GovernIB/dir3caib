
package es.caib.dir3caib.ws.api.oficina;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para contactoTF complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
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
 * 
 * 
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
     * Obtiene el valor de la propiedad tipoContacto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoContacto() {
        return tipoContacto;
    }

    /**
     * Define el valor de la propiedad tipoContacto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoContacto(String value) {
        this.tipoContacto = value;
    }

    /**
     * Obtiene el valor de la propiedad valorContacto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValorContacto() {
        return valorContacto;
    }

    /**
     * Define el valor de la propiedad valorContacto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValorContacto(String value) {
        this.valorContacto = value;
    }

}
