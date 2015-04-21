package es.caib.dir3caib.utils;

/**
 * 
 * @author anadal
 * 
 */
public class Configuracio implements Constants {

  public static boolean isCAIB() {
    return Boolean.getBoolean(DIR3CAIB_PROPERTY_BASE + "iscaib");
  }

  
  public static boolean isDevelopment() {
    return Boolean.getBoolean(DIR3CAIB_PROPERTY_BASE + "development");
  }

  public static boolean getArchivosPath() {
    return Boolean.getBoolean(DIR3CAIB_PROPERTY_BASE + "archivos.path");
  }

  public static String getCatalogosPath() {
    return System.getProperty(DIR3CAIB_PROPERTY_BASE + "catalogos.path");
  }

  public static String getOficinasPath() {
    return System.getProperty(DIR3CAIB_PROPERTY_BASE + "oficinas.path");
  }

  public static String getCatalogoEndPoint() {
    return System.getProperty(DIR3CAIB_PROPERTY_BASE + "catalogo.endpoint");
  }

  public static String getUnidadEndPoint() {
    return System.getProperty(DIR3CAIB_PROPERTY_BASE +  "unidad.endpoint");
  }

  public static String getOficinaEndPoint() {
    return System.getProperty(DIR3CAIB_PROPERTY_BASE + "oficina.endpoint");
  }

  public static String getDir3WsUser() {
    return System.getProperty(DIR3CAIB_PROPERTY_BASE +  "dir3ws.user");
  }

  public static String getDir3WsPassword() {
    return System.getProperty(DIR3CAIB_PROPERTY_BASE + "dir3ws.pass");
  }

  public static String getHibernateDialect() {
    return  System.getProperty(DIR3CAIB_PROPERTY_BASE + "hibernate.dialect");
  }
}
