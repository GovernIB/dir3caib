package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.utils.Utils;
import es.caib.dir3caib.ws.api.unidad.ContactoTF;
import es.caib.dir3caib.ws.api.unidad.Dir3CaibObtenerUnidadesWs;
import es.caib.dir3caib.ws.api.unidad.UnidadTF;
import es.caib.dir3caib.ws.api.unidad.UnidadWs;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by mgonzalez on 03/09/2015.
 */
public class TestUnidadesWs extends Dir3CaibTestUtils{


   @Test
    public void obtenerArbolUnidadesDestinatarias() {
      try{
          Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
        // for (int i = 0; i < 20; i++) {
            Long start = System.currentTimeMillis();
            List<UnidadTF> destinatarias = apiUnidades.obtenerArbolUnidadesDestinatarias("A04019281");
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA UNIDADESDESTINATARIAS: " + Utils.formatElapsedTime(end - start));
            System.out.println("DESTINATRIAS " + destinatarias.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnidadExtinguida() {
        try {
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
            //UnidadTF unidad = apiUnidades.obtenerUnidad("A04009905", null, null);
            //UnidadTF unidad = apiUnidades.obtenerUnidad("A04059164", null, null);
            UnidadTF unidad = apiUnidades.obtenerUnidad("A04019281", null, null);
            if (unidad != null) {
                System.out.println(unidad.getDenominacion());
            }else{
                System.out.println("La unidad está extinguida");
            }

            List<UnidadTF> unidades = apiUnidades.obtenerArbolUnidades("A04009905", null, null);
            System.out.println(unidades.size());
            List<UnidadTF> unidades2 = apiUnidades.obtenerArbolUnidadesDestinatarias("A04009905");
            System.out.println(unidades2.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnidadConHistoricos() {
        try {
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
            UnidadTF unidad = apiUnidades.obtenerUnidad("A04059164", null, null);
            if (unidad != null) {
                System.out.println(unidad.getDenominacion());
                System.out.println(unidad.getCodigo());
                System.out.println(unidad.getHistoricosUO());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   @Test
   public void obtenerArbolUnidadesActualizacion() {
      try {
         Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
         Long start = System.currentTimeMillis();
         Timestamp.valueOf("2018-12-11 12:38:19.398");

         //Actualizacion
         List<UnidadTF> destinatarias = apiUnidades.obtenerArbolUnidades("A04059164", Timestamp.valueOf("2022-02-09 12:38:19.398"), Timestamp.valueOf("2022-02-04 12:38:19.398"));

         Long end = System.currentTimeMillis();

         System.out.println("TIEMPO CARGA ARBOL: " + Utils.formatElapsedTime(end - start));
         System.out.println("ARBOL " + destinatarias.size());
         for (UnidadTF unidadTF : destinatarias) {
            System.out.println(unidadTF.getCodigo() + "\t\t"
               + unidadTF.getDenominacion());
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }



    @Test
    public void obtenerArbolUnidadesSincronizacion() {
        try {
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
            Long start = System.currentTimeMillis();
            List<UnidadTF> destinatarias = apiUnidades.obtenerArbolUnidades("A04059164", null, null);

            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA ARBOL: " + Utils.formatElapsedTime(end - start));
            System.out.println("ARBOL " + destinatarias.size());
            for (UnidadTF unidadTF : destinatarias) {
                System.out.println(unidadTF.getCodigo() + "\t\t"
                        + unidadTF.getDenominacion());
            }
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   @Test
   public void testBuscarUnidad() {
      try {
         Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
         UnidadTF unidad = apiUnidades.buscarUnidad("A01007082");
         if (unidad != null) {
            System.out.println(unidad.getDenominacion());
            for (String historicoTF : unidad.getHistoricosUO()) {
               System.out.println(historicoTF);
            }


         }


      } catch (Exception e) {
         e.printStackTrace();
      }
   }

    @Test
    public void obtenerFechaUltimaActualizacion() {
        try {
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
            Date fecha = apiUnidades.obtenerFechaUltimaActualizacion();
            System.out.println(fecha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void obtenerHistoricosFinales() {
        try{
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);

            Long start = System.currentTimeMillis();
            List<UnidadTF> unidadTFList = apiUnidades.obtenerHistoricosFinales("A04008301");
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA UNIDAD: " + Utils.formatElapsedTime(end - start));
            System.out.println("Unidades Historicos Finales: " + unidadTFList.size());
            for(UnidadTF unidadTF:unidadTFList){
                System.out.println(unidadTF.getCodigo() + " - "+ unidadTF.getDenominacion());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obtenerHistoricosFinalesSIR() {
        try{
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);

            Long start = System.currentTimeMillis();
            List<UnidadTF> unidadTFList = apiUnidades.obtenerHistoricosFinalesSIR("A04008301");
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA UNIDAD: " + Utils.formatElapsedTime(end - start));
            System.out.println("Unidades Historicos Finales: " + unidadTFList.size());
            for(UnidadTF unidadTF:unidadTFList){
                System.out.println(unidadTF.getCodigo() + " - "+ unidadTF.getDenominacion());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
