
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catComunidadAutonomaTF complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="catComunidadAutonomaTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoComunidad" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoPais" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionComunidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catComunidadAutonomaTF", propOrder = {
    "codigoComunidad",
    "codigoPais",
    "descripcionComunidad"
})
@XmlSeeAlso({
    CatComunidadAutonomaWs.class
})
public class CatComunidadAutonomaTF {

    protected Long codigoComunidad;
    protected Long codigoPais;
    protected String descripcionComunidad;

    /**
     * Obtiene el valor de la propiedad codigoComunidad.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoComunidad() {
        return codigoComunidad;
    }

    /**
     * Define el valor de la propiedad codigoComunidad.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoComunidad(Long value) {
        this.codigoComunidad = value;
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
     * Obtiene el valor de la propiedad descripcionComunidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionComunidad() {
        return descripcionComunidad;
    }

    /**
     * Define el valor de la propiedad descripcionComunidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionComunidad(String value) {
        this.descripcionComunidad = value;
    }

}
