
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catPais complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
@XmlSeeAlso({
    CatPaisWs.class
})
public class CatPais {

    protected String alfa2Pais;
    protected String alfa3Pais;
    protected Long codigoPais;
    protected String descripcionPais;

    /**
     * Obtiene el valor de la propiedad alfa2Pais.
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
     * Define el valor de la propiedad alfa2Pais.
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
     * Obtiene el valor de la propiedad alfa3Pais.
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
     * Define el valor de la propiedad alfa3Pais.
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
     * Obtiene el valor de la propiedad codigoPais.
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
     * Define el valor de la propiedad codigoPais.
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
     * Obtiene el valor de la propiedad descripcionPais.
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
     * Define el valor de la propiedad descripcionPais.
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
