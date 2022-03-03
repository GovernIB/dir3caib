
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catTipoServicio complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="catTipoServicio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoTipoServicio" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionTipoServicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catTipoServicio", propOrder = {
    "codigoTipoServicio",
    "descripcionTipoServicio"
})
public class CatTipoServicio {

    protected Long codigoTipoServicio;
    protected String descripcionTipoServicio;

    /**
     * Obtiene el valor de la propiedad codigoTipoServicio.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoTipoServicio() {
        return codigoTipoServicio;
    }

    /**
     * Define el valor de la propiedad codigoTipoServicio.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoTipoServicio(Long value) {
        this.codigoTipoServicio = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionTipoServicio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionTipoServicio() {
        return descripcionTipoServicio;
    }

    /**
     * Define el valor de la propiedad descripcionTipoServicio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionTipoServicio(String value) {
        this.descripcionTipoServicio = value;
    }

}
