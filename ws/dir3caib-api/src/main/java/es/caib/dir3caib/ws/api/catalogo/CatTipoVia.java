
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catTipoVia complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad codigoTipoVia.
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
     * Define el valor de la propiedad codigoTipoVia.
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
     * Obtiene el valor de la propiedad descripcionTipoVia.
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
     * Define el valor de la propiedad descripcionTipoVia.
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
