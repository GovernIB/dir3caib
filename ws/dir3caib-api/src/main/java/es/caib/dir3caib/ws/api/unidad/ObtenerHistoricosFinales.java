
package es.caib.dir3caib.ws.api.unidad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerHistoricosFinales complex type.
 * <p>
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;complexType name="obtenerHistoricosFinales">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerHistoricosFinales", propOrder = {
   "arg0"
})
public class ObtenerHistoricosFinales {

   protected String arg0;

   /**
    * Obtiene el valor de la propiedad arg0.
    *
    * @return possible object is
    * {@link String }
    */
   public String getArg0() {
      return arg0;
   }

   /**
    * Define el valor de la propiedad arg0.
    *
    * @param value allowed object is
    *              {@link String }
    */
   public void setArg0(String value) {
      this.arg0 = value;
   }

}
