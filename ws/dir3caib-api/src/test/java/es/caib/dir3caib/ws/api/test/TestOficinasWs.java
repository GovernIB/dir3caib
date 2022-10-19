package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.persistence.model.RelacionSirOfi;
import es.caib.dir3caib.ws.api.oficina.*;
import es.caib.dir3caib.ws.api.unidad.Dir3CaibObtenerUnidadesWs;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mgonzalez on 06/06/2017.
 */
public class TestOficinasWs extends Dir3CaibTestUtils {


    //Descomentar cuando regeneremos la api ws ccn contactos
    @Test
    public void obtenerOficina() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            // OficinaTF oficinaTF = apiOficinas.obtenerOficina("O00015341", null, null);
           // OficinaTF oficinaTF = apiOficinas.obtenerOficina("O00020952", null, null);
            OficinaTF oficinaTF = apiOficinas.obtenerOficina("O00033964", null, null);

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
    public void testOficinaExtinguida() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            //OficinaTF oficinaTF = apiOficinas.obtenerOficina("O00023131", null, null);
            OficinaTF oficinaTF = apiOficinas.obtenerOficina("O00029844", null, null);
            if (oficinaTF != null) {
                System.out.println(oficinaTF.getDenominacion());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOficinaContactos() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            OficinaTF oficinaTF = apiOficinas.obtenerOficina("O00012829", null, null);
            if (oficinaTF != null) {
                for(ContactoTF contTF: oficinaTF.getContactos()){
                    System.out.println(contTF.getTipoContacto());
                }
                System.out.println(oficinaTF.getDenominacion());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testObtenerArbolOficinasActualizacion() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);

            //Actualizacion
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
           // Date date = dateFormat.parse("04/02/2022");
            Date date = dateFormat.parse("14/10/2016");
            long fechasincroregweb = date.getTime();

            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
           // date = dateFormat.parse("11/05/2022");
            date = dateFormat.parse("13/10/2021");
            long fechaactuaregweb = date.getTime();



            //List<OficinaTF> oficinas = apiOficinas.obtenerArbolOficinas("A04059164", new Timestamp(fechaactuaregweb), new Timestamp(fechasincroregweb));
        //    List<OficinaTF> oficinas = apiOficinas.obtenerArbolOficinas("A04032314", new Timestamp(fechaactuaregweb), new Timestamp(fechasincroregweb));
            List<OficinaTF> oficinas = apiOficinas.obtenerArbolOficinas("A04003003", new Timestamp(fechaactuaregweb), new Timestamp(fechasincroregweb));

            System.out.println(oficinas.size());
            for(OficinaTF ofi: oficinas ){
                System.out.println(ofi.getDenominacion() +"- "+ ofi.getCodigo());
                for(RelacionOrganizativaOfiTF relOfi: ofi.getOrganizativasOfi()){
                    System.out.println("OFI " + relOfi.getOficina() +" - "+ relOfi.getUnidad()+ " - " + relOfi.getEstado());
                }
                System.out.println(ofi.getOrganizativasOfi().size());

                for(RelacionSirOfiTF sir: ofi.getSirOfi()){
                    System.out.println("SIR" + sir.getOficina() +" - "+ sir.getUnidad() + " - " + sir.getEstado());
                }
                System.out.println(ofi.getSirOfi().size());

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
             List<OficinaTF> oficinas = apiOficinas.obtenerArbolOficinas("A04019281", null, null);

            System.out.println(oficinas.size());
            for(OficinaTF ofi: oficinas ){
                System.out.println(ofi.getDenominacion());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testObtenerOficinasSIRUnidad() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);

            List<OficinaTF> oficinas = apiOficinas.obtenerOficinasSIRUnidad("A04027052");
            System.out.println(oficinas.size());
            for(OficinaTF ofiTF: oficinas){
                for(ContactoTF contTF: ofiTF.getContactos()){
                    System.out.print(contTF.getTipoContacto());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void obtenerFechaUltimaActualizacion() {
        try {
            Dir3CaibObtenerOficinasWs apiOficinas = getObtenerOficinasApi(true);
            Date fecha = apiOficinas.obtenerFechaUltimaActualizacion();
            System.out.println(fecha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
