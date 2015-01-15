
package es.caib.dir3caib.ws.dir3.oficina.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for formatoFichero.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="formatoFichero">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="XML"/>
 *     &lt;enumeration value="CSV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "formatoFichero")
@XmlEnum
public enum FormatoFichero {

    XML,
    CSV;

    public String value() {
        return name();
    }

    public static FormatoFichero fromValue(String v) {
        return valueOf(v);
    }

}
