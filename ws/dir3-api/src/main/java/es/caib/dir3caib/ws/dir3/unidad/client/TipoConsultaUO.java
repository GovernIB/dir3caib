
package es.caib.dir3caib.ws.dir3.unidad.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoConsultaUO.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoConsultaUO">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="COMPLETO"/>
 *     &lt;enumeration value="UNIDADES"/>
 *     &lt;enumeration value="HISTORICOS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoConsultaUO")
@XmlEnum
public enum TipoConsultaUO {

    COMPLETO,
    UNIDADES,
    HISTORICOS;

    public String value() {
        return name();
    }

    public static TipoConsultaUO fromValue(String v) {
        return valueOf(v);
    }

}
