
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catEstadoEntidad complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
@XmlSeeAlso({
    CatEstadoEntidadWs.class
})
public class CatEstadoEntidad {

    protected String codigoEstadoEntidad;
    protected String descripcionEstadoEntidad;

    /**
     * Obtiene el valor de la propiedad codigoEstadoEntidad.
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
     * Define el valor de la propiedad codigoEstadoEntidad.
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
     * Obtiene el valor de la propiedad descripcionEstadoEntidad.
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
     * Define el valor de la propiedad descripcionEstadoEntidad.
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
