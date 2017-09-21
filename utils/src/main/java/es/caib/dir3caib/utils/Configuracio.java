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


    /**
     * Obtiene el nombre del directorio donde estan guardados los archivos de la descarga de las unidades
     *
     * @param idDescarga
     * @return
     */
    public static String getUnidadesPath(Long idDescarga) {
        //EL nombre del directorio es "unidad"+idDescarga
        return getArchivosPath() + "unidad" + idDescarga + "/";
    }

    /**
     * Obtiene el nombre del directorio donde estan guardados los archivos de la descarga de las oficinas
     * @param idDescarga
     * @return
     */
    public static String getOficinasPath(Long idDescarga) {
        //EL nombre del directorio es "oficina"+idDescarga
        return getArchivosPath() + "oficina" + idDescarga + "/";
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

    public static String getHibernateDialect() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "hibernate.dialect");
    }

    public static String getCronExpression() {
        return System.getProperty(DIR3CAIB_PROPERTY_BASE + "cronExpression");
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
}
