
package es.caib.dir3caib.ws.api.catalogo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerCatNivelAdministracionByEstadoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerCatNivelAdministracionByEstadoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://catalogo.ws.dir3caib.caib.es/}catNivelAdministracionWs" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerCatNivelAdministracionByEstadoResponse", propOrder = {
    "_return"
})
public class ObtenerCatNivelAdministracionByEstadoResponse {

    @XmlElement(name = "return")
    protected List<CatNivelAdministracionWs> _return;

    /**
     * Gets the value of the return property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the return property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CatNivelAdministracionWs }
     * 
     * 
     */
    public List<CatNivelAdministracionWs> getReturn() {
        if (_return == null) {
            _return = new ArrayList<CatNivelAdministracionWs>();
        }
        return this._return;
    }

}