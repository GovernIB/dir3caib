
package es.caib.dir3caib.ws.dir3.oficina.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoConsultaOF.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoConsultaOF">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="COMPLETO"/>
 *     &lt;enumeration value="OFICINAS"/>
 *     &lt;enumeration value="HISTORICOS"/>
 *     &lt;enumeration value="RELACIONES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoConsultaOF")
@XmlEnum
public enum TipoConsultaOF {

    COMPLETO,
    OFICINAS,
    HISTORICOS,
    RELACIONES;

    public String value() {
        return name();
    }

    public static TipoConsultaOF fromValue(String v) {
        return valueOf(v);
    }

}
