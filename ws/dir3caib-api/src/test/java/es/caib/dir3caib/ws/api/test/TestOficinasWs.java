package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.ws.api.oficina.ContactoTF;
import es.caib.dir3caib.ws.api.oficina.Dir3CaibObtenerOficinasWs;
import es.caib.dir3caib.ws.api.oficina.OficinaTF;
import org.junit.Test;

import java.util.List;

/**
 * Created by mgonzalez on 06/06/2017.
 */
public class TestOficinasWs extends Dir3CaibTestUtils {


    //Descomentar cuando regeneremos la api ws ccn contactos
    //@Test
    public void obtenerOficina() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            // OficinaTF oficinaTF = apiOficinas.obtenerOficina("O00015341", null, null);
            OficinaTF oficinaTF = apiOficinas.obtenerOficina("O00023131", null, null);

            if (oficinaTF != null) {
                System.out.println(oficinaTF.getCodigo() + "\t\t"
                        + oficinaTF.getDenominacion());
                System.out.println("SIZE: " + oficinaTF.getContactos().size());
                for (ContactoTF contactoTF : oficinaTF.getContactos()) {
                    System.out.println(contactoTF.getTipoContacto() + "\t\t"
                            + contactoTF.getValorContacto());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testOficinaUnidadExtinguida() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            OficinaTF oficinaTF = apiOficinas.obtenerOficina("O00023131", null, null);
            if (oficinaTF != null) {
                System.out.println(oficinaTF.getDenominacion());
            }

            List<OficinaTF> oficinas = apiOficinas.obtenerArbolOficinas("O00023131", null, null);
            System.out.println(oficinas.size());
            List<OficinaTF> oficinas2 = apiOficinas.obtenerOficinasSIRUnidad("A04009905");
            System.out.println(oficinas2.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
