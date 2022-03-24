
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catEntidadGeograficaTF complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="catEntidadGeograficaTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoEntidadGeografica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionEntidadGeografica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catEntidadGeograficaTF", propOrder = {
    "codigoEntidadGeografica",
    "descripcionEntidadGeografica"
})
@XmlSeeAlso({
    CatEntidadGeograficaWs.class
})
public class CatEntidadGeograficaTF {

    protected String codigoEntidadGeografica;
    protected String descripcionEntidadGeografica;

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
     * Obtiene el valor de la propiedad descripcionEntidadGeografica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionEntidadGeografica() {
        return descripcionEntidadGeografica;
    }

    /**
     * Define el valor de la propiedad descripcionEntidadGeografica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionEntidadGeografica(String value) {
        this.descripcionEntidadGeografica = value;
    }

}
