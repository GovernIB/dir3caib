
package es.caib.dir3caib.ws.dir3.unidad.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for servicios complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="servicios">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="incluidos" type="{http://impl.manager.directorio.map.es/wsExport}estados" minOccurs="0"/>
 *         &lt;element name="excluidos" type="{http://impl.manager.directorio.map.es/wsExport}estados" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "servicios", propOrder = {
    "incluidos",
    "excluidos"
})
public class Servicios {

    protected Estados incluidos;
    protected Estados excluidos;

    /**
     * Gets the value of the incluidos property.
     * 
     * @return
     *     possible object is
     *     {@link Estados }
     *     
     */
    public Estados getIncluidos() {
        return incluidos;
    }

    /**
     * Sets the value of the incluidos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Estados }
     *     
     */
    public void setIncluidos(Estados value) {
        this.incluidos = value;
    }

    /**
     * Gets the value of the excluidos property.
     * 
     * @return
     *     possible object is
     *     {@link Estados }
     *     
     */
    public Estados getExcluidos() {
        return excluidos;
    }

    /**
     * Sets the value of the excluidos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Estados }
     *     
     */
    public void setExcluidos(Estados value) {
        this.excluidos = value;
    }

}
