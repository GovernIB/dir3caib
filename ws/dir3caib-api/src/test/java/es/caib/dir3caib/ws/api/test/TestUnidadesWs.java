package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.ws.api.unidad.Dir3CaibObtenerUnidadesWs;
import es.caib.dir3caib.ws.api.unidad.UnidadTF;
import org.junit.Test;

import java.util.List;

/**
 * Created by mgonzalez on 03/09/2015.
 */
public class TestUnidadesWs extends Dir3CaibTestUtils{


    @Test
    public void obtenerArbolUnidadesDestinatarias() {
      try{
          Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
          List<UnidadTF> destinatarias = apiUnidades.obtenerArbolUnidadesDestinatarias("U00300001");
            System.out.println("DESTINATRIAS " + destinatarias.size());
          for (UnidadTF unidadTF : destinatarias) {
            System.out.println(unidadTF.getCodigo() + "\t\t"
                    + unidadTF.getDenominacion());
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
