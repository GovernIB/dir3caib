
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catNivelAdministracion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="catNivelAdministracion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoNivelAdministracion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionNivelAdministracion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catNivelAdministracion", propOrder = {
    "codigoNivelAdministracion",
    "descripcionNivelAdministracion"
})
public class CatNivelAdministracion {

    protected Long codigoNivelAdministracion;
    protected String descripcionNivelAdministracion;

    /**
     * Obtiene el valor de la propiedad codigoNivelAdministracion.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoNivelAdministracion() {
        return codigoNivelAdministracion;
    }

    /**
     * Define el valor de la propiedad codigoNivelAdministracion.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoNivelAdministracion(Long value) {
        this.codigoNivelAdministracion = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionNivelAdministracion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionNivelAdministracion() {
        return descripcionNivelAdministracion;
    }

    /**
     * Define el valor de la propiedad descripcionNivelAdministracion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionNivelAdministracion(String value) {
        this.descripcionNivelAdministracion = value;
    }

}
