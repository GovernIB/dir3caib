package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.ws.api.catalogo.CatComunidadAutonomaTF;
import es.caib.dir3caib.ws.api.catalogo.Dir3CaibObtenerCatalogosWsService;
import es.caib.dir3caib.ws.api.catalogo.Dir3CaibObtenerCatalogosWs;
import org.junit.Test;

import javax.xml.ws.BindingProvider;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author anadal
 * 
 */
public class TestWs {

  public static final String OBTENERCATALOGOS = "Dir3CaibObtenerCatalogos";

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

  public static Dir3CaibObtenerCatalogosWs getObtenerCatalogosApi(boolean setUsrPwd)
      throws Exception {
    final String endpoint = getEndPoint(OBTENERCATALOGOS);

    System.out.println("endPoint: " + endpoint);

    // URL wsdlLocation =
    // Dir3CaibObtenerCatalogosWsService.class.getResource("/wsdl/Dir3CaibObtenerCatalogos.wsdl");
    URL wsdlLocation = new URL(endpoint + "?wsdl");
    System.out.println("WSDL: " + wsdlLocation);

    Dir3CaibObtenerCatalogosWsService service = new Dir3CaibObtenerCatalogosWsService(
        wsdlLocation);

    Dir3CaibObtenerCatalogosWs api = service.getDir3CaibObtenerCatalogosWs();

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

    try {

      Dir3CaibObtenerCatalogosWs api = getObtenerCatalogosApi(true);

      System.out.println("Versio: " + api.getVersion());

      List<CatComunidadAutonomaTF> list = api.obtenerCatComunidadAutonoma();

      for (CatComunidadAutonomaTF catComunidadAutonomaTF : list) {
        System.out.println(catComunidadAutonomaTF.getCodigoComunidad() + "\t\t"
            + catComunidadAutonomaTF.getDescripcionComunidad());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}