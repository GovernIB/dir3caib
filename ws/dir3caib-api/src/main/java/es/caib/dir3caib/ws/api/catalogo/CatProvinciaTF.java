
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catProvinciaTF complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
@XmlSeeAlso({
    CatProvinciaWs.class
})
public class CatProvinciaTF {

    protected Long codigoComunidadAutonoma;
    protected Long codigoProvincia;
    protected String descripcionProvincia;

    /**
     * Obtiene el valor de la propiedad codigoComunidadAutonoma.
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
     * Define el valor de la propiedad codigoComunidadAutonoma.
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
     * Obtiene el valor de la propiedad codigoProvincia.
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
     * Define el valor de la propiedad codigoProvincia.
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
     * Obtiene el valor de la propiedad descripcionProvincia.
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
     * Define el valor de la propiedad descripcionProvincia.
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
