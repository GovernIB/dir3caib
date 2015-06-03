
package es.caib.dir3caib.ws.api.catalogo;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter2
    extends XmlAdapter<String, Date>
{


    public Date unmarshal(String value) {
        return (org.fundaciobit.genapp.common.ws.WsUtilDateAdapter.parseDate(value));
    }

    public String marshal(Date value) {
        return (org.fundaciobit.genapp.common.ws.WsUtilDateAdapter.printDate(value));
    }

}
