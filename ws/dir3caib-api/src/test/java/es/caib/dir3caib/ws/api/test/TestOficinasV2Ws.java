package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.ws.api.oficina.*;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author mgonzalez
 * 11/03/2022
 */
public class TestOficinasV2Ws extends Dir3CaibTestUtils{

    //Descomentar cuando regeneremos la api ws ccn contactos
    @Test
    public void testObtenerOficina() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            //OficinaWs oficinaWs = apiOficinas.obtenerOficinaV2("O00045531", null, null);
            OficinaWs oficinaWs = apiOficinas.obtenerOficinaV2("O00013867", null, null);


            if (oficinaWs != null) {
                System.out.println(oficinaWs.getCodigo() + "\t\t"
                        + oficinaWs.getDenominacion());
                System.out.println(oficinaWs.getCodigo() + "\t\t"
                        + oficinaWs.getDenomLenguaCooficial());
                System.out.println(oficinaWs.getFuenteExterna());
                System.out.println(oficinaWs.getVersionUoResponsable());
                System.out.println("SIZE: " + oficinaWs.getContactos().size());
                for (ContactoTF contactoTF : oficinaWs.getContactos()) {
                    System.out.println(contactoTF.getTipoContacto() + "\t\t"
                            + contactoTF.getValorContacto());
                }
                for(RelacionOrganizativaOfiTF relOFi: oficinaWs.getOrganizativasOfi()){
                    System.out.println(relOFi.getUnidad() +"\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testObtenerOficinaExtinguida() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            OficinaWs oficinaWs = apiOficinas.obtenerOficinaV2("O00023131", null, null);
            if (oficinaWs != null) {
                System.out.println(oficinaWs.getDenominacion());
                System.out.println(oficinaWs.getDenomLenguaCooficial());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testObtenerArbolOficinasActualizacion() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            //Sincro
            // List<OficinaTF> oficinas = apiOficinas.obtenerArbolOficinas("A04059164", null, null);

            //Actualizacion
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse("04/02/2022");
            long fechasincroregweb = date.getTime();

            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date = dateFormat.parse("09/02/2022");
            long fechaactuaregweb = date.getTime();

            List<OficinaWs> oficinas = apiOficinas.obtenerArbolOficinasV2("A04059164", new Timestamp(fechaactuaregweb), new Timestamp(fechasincroregweb));

            System.out.println(oficinas.size());
            for(OficinaWs ofi: oficinas ){
                System.out.println(ofi.getDenominacion());
                System.out.println(ofi.getDenomLenguaCooficial());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testObtenerArbolOficinasSincronizacion() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            //Sincro
            List<OficinaWs> oficinas = apiOficinas.obtenerArbolOficinasV2("A04019281", null, null);

            System.out.println(oficinas.size());
            for(OficinaWs ofi: oficinas ){
                System.out.println(ofi.getDenominacion());
                System.out.println(ofi.getDenomLenguaCooficial());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testObtenerOficinasSIRUnidad() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);

            List<OficinaWs> oficinas = apiOficinas.obtenerOficinasSIRUnidadV2("E03139201v1");
            // List<OficinaTF> oficinas = apiOficinas.obtenerOficinasSIRUnidad("A04031681v1");
            //  List<OficinaTF> oficinas = apiOficinas.obtenerOficinasSIRUnidad("A04009905");
            System.out.println(oficinas.size());
            for(OficinaWs ofiTF: oficinas){
                System.out.print(ofiTF.getDenominacion()+ "\n") ;
                System.out.print(ofiTF.getDenomLenguaCooficial()+ "\n");
                for(ContactoTF contTF: ofiTF.getContactos()){
                    System.out.print(contTF.getTipoContacto()+ "\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
