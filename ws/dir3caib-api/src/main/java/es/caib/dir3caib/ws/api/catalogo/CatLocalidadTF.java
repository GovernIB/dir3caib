
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catLocalidadTF complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="catLocalidadTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoEntidadGeografica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoLocalidad" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoProvincia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionLocalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catLocalidadTF", propOrder = {
    "codigoEntidadGeografica",
    "codigoLocalidad",
    "codigoProvincia",
    "descripcionLocalidad"
})
public class CatLocalidadTF {

    protected String codigoEntidadGeografica;
    protected Long codigoLocalidad;
    protected Long codigoProvincia;
    protected String descripcionLocalidad;

    /**
     * Obtiene el valor de la propiedad codigoEntidadGeografica.
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
     * Define el valor de la propiedad codigoEntidadGeografica.
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
     * Obtiene el valor de la propiedad codigoLocalidad.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoLocalidad() {
        return codigoLocalidad;
    }

    /**
     * Define el valor de la propiedad codigoLocalidad.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoLocalidad(Long value) {
        this.codigoLocalidad = value;
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
     * Obtiene el valor de la propiedad descripcionLocalidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionLocalidad() {
        return descripcionLocalidad;
    }

    /**
     * Define el valor de la propiedad descripcionLocalidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionLocalidad(String value) {
        this.descripcionLocalidad = value;
    }

}
