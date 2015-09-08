package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.ws.api.unidad.Dir3CaibObtenerUnidadesWs;
import es.caib.dir3caib.ws.api.unidad.Dir3CaibObtenerUnidadesWsService;
import es.caib.dir3caib.ws.api.unidad.UnidadTF;
import org.junit.Test;

import javax.xml.ws.BindingProvider;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mgonzalez on 03/09/2015.
 */
public class TestUnidadesWs {

    public static final String OBTENERUNIDADES = "Dir3CaibObtenerUnidades";

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

    @Test
    public void test() {
        main(new String[] {});
    }



    @Test
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

    public static void configAddressUserPassword(String usr, String pwd, String endpoint,
                                                 Object api) {

        Map<String, Object> reqContext = ((BindingProvider) api).getRequestContext();
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        reqContext.put(BindingProvider.USERNAME_PROPERTY, usr);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, pwd);
    }

    public static void main(String[] args) {
      try{
          Dir3CaibObtenerUnidadesWs apiUnidades = getObtenerUnidadesApi(true);
          List<UnidadTF> destinatarias = apiUnidades.obtenerArbolUnidadesDestinatarias("L01070407");
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
