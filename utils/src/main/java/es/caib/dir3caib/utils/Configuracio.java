package es.caib.dir3caib.utils;

/**
 * @author anadal
 */
public class Configuracio {

    public static final String DIR3CAIB_PROPERTY_BASE = "es.caib.dir3caib.";

    public static String getDefaultLanguage() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "defaultlanguage");
    }

    public static boolean isCAIB() {
        return Boolean.getBoolean(DIR3CAIB_PROPERTY_BASE + "iscaib");
    }


    public static boolean isDevelopment() {
        return Boolean.getBoolean(DIR3CAIB_PROPERTY_BASE + "development");
    }

    public static String getArchivosPath() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "archivos.path");
    }

    /**
     *
     * @param idSincronizacion
     * @return
     */
    public static String getSincronizacionPath(Long idSincronizacion) {
        return getArchivosPath() + "sincronizacion" + idSincronizacion + "/";
    }

    /**
     *
     * @param idSincronizacion
     * @return
     */
    public static String getCatalogosPath(Long idSincronizacion) {
        return getSincronizacionPath(idSincronizacion) + "catalogos/";
    }

    /**
     *
     * @param idSincronizacion
     * @return
     */
    public static String getDirectorioPath(Long idSincronizacion) {
        return getSincronizacionPath(idSincronizacion) + "directorio/";
    }



    public static String getCatalogoEndPoint() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "catalogo.endpoint");
    }

    public static String getUnidadEndPoint() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "unidad.endpoint");
    }

    public static String getOficinaEndPoint() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "oficina.endpoint");
    }

    public static String getDir3WsUser() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "dir3ws.user");
    }

    public static String getDir3WsPassword() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "dir3ws.pass");
    }


    public static boolean showTimeStamp() {
        return Boolean.getBoolean(DIR3CAIB_PROPERTY_BASE + "showtimestamp");
    }

    public static String getBusquedaAdministracion() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "busqueda.administracion");
    }

    public static String getBusquedaComunidad() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "busqueda.comunidad");
    }

    public static boolean isSincronizar() {
        return Boolean.getBoolean(DIR3CAIB_PROPERTY_BASE + "sincronizar");
    }

    public static String getRemitenteEmail() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "remitente.email");
    }

    public static String getRemitenteNombre() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "remitente.nombre");
    }

    public static String getAdministradorEmail() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "administrador.email");
    }

    public static boolean isDenominacionCooficial() {
        return Boolean.getBoolean(DIR3CAIB_PROPERTY_BASE + "denominacioncooficial");
    }

}
