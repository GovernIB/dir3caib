package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.utils.Utils;
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
         for (int i = 0; i < 20; i++) {
            Long start = System.currentTimeMillis();
            List<UnidadTF> destinatarias = apiUnidades.obtenerArbolUnidadesDestinatarias("A04003003");
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA UNIDADESDESTINATARIAS: " + Utils.formatElapsedTime(end - start));
            System.out.println("DESTINATRIAS " + destinatarias.size());
            /* for (UnidadTF unidadTF : destinatarias) {
                System.out.println(unidadTF.getCodigo() + "\t\t"
                   + unidadTF.getDenominacion());
             }*/
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Descomentar cuando regeneremos la api ws ccn contactos
   /* public void obtenerOficina() {
        try{
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
            UnidadTF unidadTF = apiUnidades.obtenerUnidad("E00120903", null, null);

            System.out.println(unidadTF.getCodigo() + "\t\t"
                    + unidadTF.getDenominacion()  );
            System.out.println("SIZE: "+unidadTF.getContactos().size());
            for(ContactoTF contactoTF :unidadTF.getContactos()){
                System.out.println(contactoTF.getTipoContacto() + "\t\t"
                        + contactoTF.getValorContacto()  );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void testUnidadExtinguida() {
        try {
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
            UnidadTF unidad = apiUnidades.obtenerUnidad("A04009905", null, null);
            if (unidad != null) {
                System.out.println(unidad.getDenominacion());
            }

            List<UnidadTF> unidades = apiUnidades.obtenerArbolUnidades("A04009905", null, null);
            System.out.println(unidades.size());
            List<UnidadTF> unidades2 = apiUnidades.obtenerArbolUnidadesDestinatarias("A04009905");
            System.out.println(unidades2.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
