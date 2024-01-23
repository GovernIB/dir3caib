
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catIslaWs complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="catIslaWs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoIsla" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoProvincia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionIsla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catIslaWs", propOrder = {
    "codigoIsla",
    "codigoProvincia",
    "descripcionIsla",
    "estado"
})
public class CatIslaWs {

    protected Long codigoIsla;
    protected Long codigoProvincia;
    protected String descripcionIsla;
    protected String estado;

    /**
     * Obtiene el valor de la propiedad codigoIsla.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoIsla() {
        return codigoIsla;
    }

    /**
     * Define el valor de la propiedad codigoIsla.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoIsla(Long value) {
        this.codigoIsla = value;
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
     * Obtiene el valor de la propiedad descripcionIsla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionIsla() {
        return descripcionIsla;
    }

    /**
     * Define el valor de la propiedad descripcionIsla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionIsla(String value) {
        this.descripcionIsla = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

}
