
package es.caib.dir3caib.ws.dir3.oficina.client;

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
 *         &lt;element name="incluidos" type="{http://impl.manager.directorio.map.es/wsExport}excluidos" minOccurs="0"/>
 *         &lt;element name="excluidos" type="{http://impl.manager.directorio.map.es/wsExport}excluidos" minOccurs="0"/>
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

    protected Excluidos incluidos;
    protected Excluidos excluidos;

    /**
     * Gets the value of the incluidos property.
     * 
     * @return
     *     possible object is
     *     {@link Excluidos }
     *     
     */
    public Excluidos getIncluidos() {
        return incluidos;
    }

    /**
     * Sets the value of the incluidos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Excluidos }
     *     
     */
    public void setIncluidos(Excluidos value) {
        this.incluidos = value;
    }

    /**
     * Gets the value of the excluidos property.
     * 
     * @return
     *     possible object is
     *     {@link Excluidos }
     *     
     */
    public Excluidos getExcluidos() {
        return excluidos;
    }

    /**
     * Sets the value of the excluidos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Excluidos }
     *     
     */
    public void setExcluidos(Excluidos value) {
        this.excluidos = value;
    }

}
