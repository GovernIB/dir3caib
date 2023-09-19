package es.caib.dir3caib.persistence.model;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal
 * Date: 2/10/13
 */
public interface Dir3caibConstantes {

    /* -------------- ROLES --------------*/
    String DIR_ADMIN = "DIR_ADMIN";  // Administrador
    String DIR_WS = "DIR_WS";  // Web Services
    String ROL_TOTHOM = "tothom";  // tothom

    String DIR3CAIB_PROPERTY_BASE = "es.caib.dir3caib.";

    String SECURITY_DOMAIN = "seycon";

    String SEPARADOR_CODIGO_VERSION = "v";

    // Importación Catalogo
    String CATALOGOS_ARCHIVO_ZIP = "catalogos";
    String CAT_AMBITO_TERRITORIAL = "CAT_AMBITO_TERRITORIAL.CSV";
    String CAT_COMUNIDAD_AUTONOMA = "CAT_COMUNIDAD_AUTONOMA.CSV";
    String CAT_ENTIDAD_GEOGRAFICA = "CAT_ENTIDAD_GEOGRAFICA.CSV";
    String CAT_ESTADO_ENTIDAD = "CAT_ESTADO_ENTIDAD.CSV";
    String CAT_ISLA = "CAT_ISLA.CSV";
    String CAT_JERARQUIA_OFICINA = "CAT_JERARQUIA_OFICINA.CSV";
    String CAT_LOCALIDAD = "CAT_LOCALIDAD.CSV";
    String CAT_MOTIVO_EXTINCION = "CAT_MOTIVO_EXTINCION.CSV";
    String CAT_NIVEL_ADMINISTRACION = "CAT_NIVEL_ADMINISTRACION.CSV";
    String CAT_PAIS = "CAT_PAIS.CSV";
    String CAT_PROVINCIA = "CAT_PROVINCIA.CSV";
    String CAT_TIPO_CONTACTO = "CAT_TIPO_CONTACTO.CSV";
    String CAT_TIPO_ENTIDAD_PUBLICA = "CAT_TIPO_ENTIDAD_PUBLICA.CSV";
    String CAT_TIPO_UNIDAD_ORGANICA = "CAT_TIPO_UNIDAD_ORGANICA.CSV";
    String CAT_TIPO_VIA = "CAT_TIPO_VIA.CSV";
    String CAT_TIPO_SERVICIO = "CAT_TIPO_SERVICIOS.CSV";
    String CAT_TIPO_PODER = "CAT_PODER.CSV";
    String CAT_TIPO_UNIDAD_NO_ORGANICA = "CAT_TIPO_UNIDAD_NO_ORGANICA.CSV";
    String CAT_TIPO_CODIGO_FUENTE = "CAT_TIPO_CODIGO_FUENTE_EXT.CSV";
    String CAT_SERVICIOS = "CAT_SERVICIOS.CSV";
    String CAT_SERVICIOS_UO = "CAT_SERVICIOS_UO.CSV";
    String[] CAT_FICHEROS = {
            CAT_ESTADO_ENTIDAD,
            CAT_ENTIDAD_GEOGRAFICA,
            CAT_JERARQUIA_OFICINA,
            CAT_MOTIVO_EXTINCION,
            CAT_NIVEL_ADMINISTRACION,
            CAT_PAIS,
            CAT_COMUNIDAD_AUTONOMA,
            CAT_PROVINCIA,
            CAT_ISLA,
            CAT_TIPO_CONTACTO,
            CAT_TIPO_ENTIDAD_PUBLICA,
            CAT_TIPO_UNIDAD_ORGANICA,
            CAT_TIPO_VIA,
            CAT_AMBITO_TERRITORIAL,
            CAT_TIPO_SERVICIO,
            CAT_TIPO_PODER,
            CAT_TIPO_CODIGO_FUENTE,
            CAT_TIPO_UNIDAD_NO_ORGANICA,
            CAT_SERVICIOS,
            CAT_SERVICIOS_UO,
            CAT_LOCALIDAD,
    };


    // Importación Oficinas
    String OFICINAS_ARCHIVO_ZIP = "oficinas";
    String OFI_CONTACTO_OFI = "ContactoOFI.csv";
    String OFI_HISTORICOS_OFI = "HistoricosOFI.csv";
    String OFI_OFICINAS = "Oficinas.csv";
    String OFI_RELACIONES_ORGANIZATIVAS_OFI = "RelacionesOrganizativasOFI.csv";
    String OFI_RELACIONES_SIROFI = "RelacionesSIROFI.csv";
    String OFI_SERVICIOS_OFI = "ServiciosOFI.csv";
    String[] OFI_FICHEROS = {OFI_OFICINAS, OFI_CONTACTO_OFI, OFI_HISTORICOS_OFI, OFI_RELACIONES_ORGANIZATIVAS_OFI, OFI_RELACIONES_SIROFI, OFI_SERVICIOS_OFI};

    // Importación UNIDADES
    String UNIDADES_ARCHIVO_ZIP = "unidades";
    String UO_CONTACTO_UO = "ContactoUO.csv";
    String UO_HISTORICOS_UO = "HistoricosUO.csv";
    String UO_UNIDADES = "Unidades.csv";
    String UO_CODIGO_UO = "CodigosUO.csv";
    String UO_NIFCIF_UO = "NifCifUO.csv";
    String UO_SERVICIOS_UO = "ServiciosUO.csv";
    String[] UO_FICHEROS = {UO_UNIDADES, UO_HISTORICOS_UO, UO_CONTACTO_UO, UO_CODIGO_UO, UO_NIFCIF_UO, UO_SERVICIOS_UO};


    String FORMATO_FECHA = "dd/MM/yyyy";
    String FORMATO_FECHA_HORA = "dd/MM/yyyy hh:mm:ss";

    // Tipos Sincronización
    String CATALOGO = "1";
    String DIRECTORIO_ACTUALIZACION = "2";
    String DIRECTORIO_COMPLETO = "3";

    //Estados entidad
    String ESTADO_ENTIDAD_VIGENTE = "V";
    String ESTADO_ENTIDAD_EXTINGUIDO = "E";
    String ESTADO_ENTIDAD_ANULADO = "A";
    String ESTADO_ENTIDAD_TRANSITORIO = "T";

    String DESCRIPCION_ESTADO_ENTIDAD_VIGENTE = "Vigente";
    String DESCRIPCION_ESTADO_ENTIDAD_EXTINGUIDO = "Extinguido";
    String DESCRIPCION_ESTADO_ENTIDAD_ANULADO = "Anulado";
    String DESCRIPCION_ESTADO_ENTIDAD_TRANSITORIO = "Transitorio";

    //codigo respuesta correcto ws descarga madrid
    String CODIGO_CORRECTO = "01";
    String CODIGO_VACIO = "14";

    //servicios SIR
    Long SERVICIO_OFI_REGISTRO = 1L;
    Long SERVICIO_OFI_INFORMACION = 2L;
    Long SERVICIO_OFI_TRAMITACION = 3L;
    Long SERVICIO_REG_VIRTUAL = 4L;
    Long SERVICIO_SIR = 5L;
    Long SERVICIO_SIR_ENVIO = 6L;
    Long SERVICIO_SIR_RECEPCION = 7L;
    Long SERVICIO_060 = 8L;
    Long SERVICIO_OFI_CORREOS = 9L;
    Long SERVICIO_OFI_EXTRANJERIA = 10L;
    Long SERVICIO_OFI_VIOLGENERO = 11L;
    Long SERVICIO_OFI_ACCESIBLE = 12L;
    Long SERVICIO_CLAVE = 13L;
    Long SERVICIO_REA = 14L;
    Long SERVICIO_OFI_ORD = 15L;

    // Estados Sincronización
    Long SINCRONIZACION_DESCARGADA = 1L;
    Long SINCRONIZACION_VACIA = 2L;
    Long SINCRONIZACION_ERROR_DESCARGA = 3L;
    Long SINCRONIZACION_ERRONEA = 4L;
    Long SINCRONIZACION_CORRECTA = 5L;

    //Tipos de Sincronización
    String SINCRONIZACION_CATALOGO = "Catàleg";
    String SINCRONIZACION_DIRECTORIO = "Directori";

    // Expresión CRON por defecto para actualizar DIR3
    String CRON_SINCRONIZAR_DIR3 = "0 0 1 1/1 * ? *"; // Sincronización diaria a las 01:00 am

    // Textos para envio Mail error en Sincronización
    String ASUNTO_MAIL = "S'ha produit un error a la sincronització de ";
    String ENTORN = " a l'entorn  ";
    String CUERPO_MAIL = "S'ha produït un error a la sincronització. S'ha de revisar perquè si no s'actualitzen les dades diàriament pot afectar a les aplicacions connectades amb DIR3CAIB.";
    String APLICACION_NOMBRE = "DIR3CAIB";
    String APLICACION_EMAIL = "noreply@caib.es";


    //NIVEL ADMINISTRACION UNIVERSIDADES
    Long NIVEL_ADMINISTRACION_UNIVERSIDADES = 4L;


    //IDIOMAS DIR3
    Long CASTELLANO = 1L;
    Long CATALA = 2L;
    Long EUSKERA = 3L;
    Long GALEGO = 4L;
    Long VALENCIA = 5L;

    String TIPO_CONTACTO_EMAIL = "E";
    String TIPO_CONTACTO_FAX = "F";
    String TIPO_CONTACTO_URL = "U";
    String TIPO_CONTACTO_TELEFONO = "T";

}
