package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.ws.api.oficina.Dir3CaibObtenerOficinasWs;
import es.caib.dir3caib.ws.api.oficina.Dir3CaibObtenerOficinasWsService;
import es.caib.dir3caib.ws.api.unidad.Dir3CaibObtenerUnidadesWs;
import es.caib.dir3caib.ws.api.unidad.Dir3CaibObtenerUnidadesWsService;

import javax.xml.ws.BindingProvider;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Created by earrivi on 14/10/2015.
 */
public class Dir3CaibTestUtils{

    public static final String OBTENERUNIDADES = "Dir3CaibObtenerUnidades";
    public static final String OBTENEROFICINAS = "Dir3CaibObtenerOficinas";

    private static Properties testProperties = new Properties();

    static {
        // Propietats del Servidor
        try {
            System.out.println(new File(".").getAbsolutePath());
            testProperties.load(new FileInputStream("test.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getEndPoint(String api) {
        return testProperties.getProperty("test_host") + api;
    }

    public static String getTestAppUserName() {
        return testProperties.getProperty("test_usr");
    }

    public static String getTestAppPassword() {

        return testProperties.getProperty("test_pwd");
    }

    public static void configAddressUserPassword(String usr, String pwd, String endpoint,
                                                 Object api) {

        Map<String, Object> reqContext = ((BindingProvider) api).getRequestContext();
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        reqContext.put(BindingProvider.USERNAME_PROPERTY, usr);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, pwd);
    }


    public static Dir3CaibObtenerUnidadesWs getObtenerUnidadesApi(boolean setUsrPwd)
            throws Exception {
        final String endpoint = getEndPoint(OBTENERUNIDADES);

        System.out.println("endPoint: " + endpoint);


        URL wsdlLocation = new URL(endpoint + "?wsdl");
        System.out.println("WSDL: " + wsdlLocation);

        Dir3CaibObtenerUnidadesWsService service = new Dir3CaibObtenerUnidadesWsService(
                wsdlLocation);

        Dir3CaibObtenerUnidadesWs api = service.getDir3CaibObtenerUnidadesWs();

        if (setUsrPwd) {
            configAddressUserPassword(getTestAppUserName(), getTestAppPassword(), endpoint, api);
        }

        return api;
    }


    public static Dir3CaibObtenerOficinasWs getObtenerOficinasApi(boolean setUsrPwd)
            throws Exception {
        final String endpoint = getEndPoint(OBTENEROFICINAS);

        System.out.println("endPoint: " + endpoint);


        URL wsdlLocation = new URL(endpoint + "?wsdl");
        System.out.println("WSDL: " + wsdlLocation);

        Dir3CaibObtenerOficinasWsService service = new Dir3CaibObtenerOficinasWsService(
                wsdlLocation);

        Dir3CaibObtenerOficinasWs api = service.getDir3CaibObtenerOficinasWs();

        if (setUsrPwd) {
            configAddressUserPassword(getTestAppUserName(), getTestAppPassword(), endpoint, api);
        }

        return api;
    }
}
