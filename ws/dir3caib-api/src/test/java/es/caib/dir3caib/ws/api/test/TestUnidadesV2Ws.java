package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.utils.Utils;
import es.caib.dir3caib.ws.api.oficina.Dir3CaibObtenerOficinasWs;
import es.caib.dir3caib.ws.api.oficina.OficinaTF;
import es.caib.dir3caib.ws.api.oficina.OficinaWs;
import es.caib.dir3caib.ws.api.unidad.ContactoTF;
import es.caib.dir3caib.ws.api.unidad.Dir3CaibObtenerUnidadesWs;
import es.caib.dir3caib.ws.api.unidad.UnidadTF;
import es.caib.dir3caib.ws.api.unidad.UnidadWs;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author mgonzalez
 * 03/03/2022
 */
public class TestUnidadesV2Ws extends Dir3CaibTestUtils{


    @Test
    public void obtenerUnidadV2() {
        try{
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);

            Long start = System.currentTimeMillis();
            UnidadWs unidadWs = apiUnidades.obtenerUnidadV2("A04003003", null, null);
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA UNIDAD: " + Utils.formatElapsedTime(end - start));
            System.out.println("DESTINATRIAS " + unidadWs.getDenominacion());
            System.out.println("DESTINATRIAS " + unidadWs.getDenomLenguaCooficial());
            System.out.println("NIFCIF " + unidadWs.getNifCif());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obtenerArbolUnidadesDestinatariasWs() {
        try{
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
           // for (int i = 0; i < 20; i++) {
                Long start = System.currentTimeMillis();
                //List<UnidadWs> destinatarias = apiUnidades.obtenerArbolUnidadesDestinatariasV2("A04019281");
                List<UnidadWs> destinatarias = apiUnidades.obtenerArbolUnidadesDestinatariasV2("A04003003");
                Long end = System.currentTimeMillis();

                System.out.println("TIEMPO CARGA UNIDADESDESTINATARIAS: " + Utils.formatElapsedTime(end - start));
                System.out.println("DESTINATRIAS " + destinatarias.size());
                System.out.println("UNidad " + ((UnidadWs)destinatarias.get(0)).getDenominacion());
                System.out.println("UNidad " + ((UnidadWs)destinatarias.get(0)).getDenomLenguaCooficial());
           // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnidadExtinguida() {
        try {
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
            UnidadWs unidad = apiUnidades.obtenerUnidadV2("A04008301", null, null);
            if (unidad != null) {
                System.out.println(unidad.getDenominacion());
            }else{
                System.out.println("La unidad está extinguida");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarUnidadV2() {
        try{
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);

            Long start = System.currentTimeMillis();
            UnidadWs unidadWs = apiUnidades.buscarUnidadV2("L01070051");
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA UNIDAD: " + Utils.formatElapsedTime(end - start));
            System.out.println("Unidad  " + unidadWs.getDenominacion());
            System.out.println("Unidad denomcooficial " + unidadWs.getDenomLenguaCooficial());
            if(!unidadWs.getContactos().isEmpty()){
                for(ContactoTF contactoTF: unidadWs.getContactos()){
                    System.out.println("Contacto " + contactoTF.getTipoContacto() + " - " + contactoTF.getValorContacto());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void obtenerHistoricosFinales() {
        try{
            Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);

            Long start = System.currentTimeMillis();
            List<UnidadWs> unidadesWs = apiUnidades.obtenerHistoricosFinalesV2("A04008301");
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA UNIDAD: " + Utils.formatElapsedTime(end - start));
            System.out.println("Unidades Historicos Finales: " + unidadesWs.size());
            for(UnidadWs unidadWs:unidadesWs){
                System.out.println(unidadWs.getCodigo() + " - "+ unidadWs.getDenominacion());
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
            List<UnidadWs> unidadesWs = apiUnidades.obtenerHistoricosFinalesSIRV2("A04031681");
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA UNIDAD: " + Utils.formatElapsedTime(end - start));
            System.out.println("Unidades Historicos Finales: " + unidadesWs.size());
            for(UnidadWs unidadWs:unidadesWs){
                System.out.println(unidadWs.getCodigo() + " - "+ unidadWs.getDenominacion());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testObtenerOficinasSIRUnidad() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);

            List<OficinaWs> oficinas = apiOficinas.obtenerOficinasSIRUnidadV2("A04027052");
            System.out.println(oficinas.size());
            for(OficinaWs ofiWs: oficinas){
                for(es.caib.dir3caib.ws.api.oficina.ContactoTF contTF: ofiWs.getContactos()){
                    System.out.print(contTF.getTipoContacto());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
