package es.caib.dir3caib.persistence.model;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal
 * Date: 2/10/13
 */
public class Dir3caibConstantes {

     /* -------------- ROLES --------------*/
    public static final String DIR_ADMIN = "DIR_ADMIN";  // Administrador
    public static final String DIR_WS = "DIR_WS";  // Web Services
    public static final String ROL_TOTHOM = "tothom";  // tothom

    public static final String DIR3CAIB_PROPERTY_BASE = "es.caib.dir3caib.";

    public static final String SECURITY_DOMAIN = "seycon";

    // Importación Catalogo
    public static final String CATALOGOS_ARCHIVO_ZIP = "catalogos";
    public static final String CAT_AMBITO_TERRITORIAL = "CAT_AMBITO_TERRITORIAL.CSV"; 
    public static final String CAT_COMUNIDAD_AUTONOMA = "CAT_COMUNIDAD_AUTONOMA.CSV"; 
    public static final String CAT_ENTIDAD_GEOGRAFICA = "CAT_ENTIDAD_GEOGRAFICA.CSV"; 
    public static final String CAT_ESTADO_ENTIDAD = "CAT_ESTADO_ENTIDAD.CSV"; 
    public static final String CAT_ISLA = "CAT_ISLA.CSV"; 
    public static final String CAT_JERARQUIA_OFICINA = "CAT_JERARQUIA_OFICINA.CSV"; 
    public static final String CAT_LOCALIDAD = "CAT_LOCALIDAD.CSV"; 
    public static final String CAT_MOTIVO_EXTINCION = "CAT_MOTIVO_EXTINCION.CSV"; 
    public static final String CAT_NIVEL_ADMINISTRACION = "CAT_NIVEL_ADMINISTRACION.CSV"; 
    public static final String CAT_PAIS = "CAT_PAIS.CSV"; 
    public static final String CAT_PROVINCIA = "CAT_PROVINCIA.CSV"; 
    public static final String CAT_TIPO_CONTACTO = "CAT_TIPO_CONTACTO.CSV"; 
    public static final String CAT_TIPO_ENTIDAD_PUBLICA = "CAT_TIPO_ENTIDAD_PUBLICA.CSV"; 
    public static final String CAT_TIPO_UNIDAD_ORGANICA = "CAT_TIPO_UNIDAD_ORGANICA.CSV"; 
    public static final String CAT_TIPO_VIA = "CAT_TIPO_VIA.CSV"; 
    public static final String CAT_SERVICIOS = "CAT_SERVICIOS.CSV";
    public static final String[] CAT_FICHEROS = {CAT_ENTIDAD_GEOGRAFICA, CAT_ESTADO_ENTIDAD, CAT_JERARQUIA_OFICINA, CAT_MOTIVO_EXTINCION, CAT_NIVEL_ADMINISTRACION, CAT_PAIS, CAT_COMUNIDAD_AUTONOMA, CAT_PROVINCIA, CAT_ISLA, CAT_TIPO_CONTACTO, CAT_TIPO_ENTIDAD_PUBLICA, CAT_TIPO_UNIDAD_ORGANICA, CAT_TIPO_VIA, CAT_AMBITO_TERRITORIAL, CAT_SERVICIOS, CAT_LOCALIDAD};
    

    
    
    // Importación Oficinas
    public static final String OFICINAS_ARCHIVO_ZIP = "oficinas";
    public static final String OFI_CONTACTO_OFI = "ContactoOFI.csv"; 
    public static final String OFI_HISTORICOS_OFI = "HistoricosOFI.csv"; 
    public static final String OFI_OFICINAS = "Oficinas.csv"; 
    public static final String OFI_RELACIONES_ORGANIZATIVAS_OFI = "RelacionesOrganizativasOFI.csv"; 
    public static final String OFI_RELACIONES_SIROFI = "RelacionesSIROFI.csv"; 
    public static final String OFI_SERVICIOS_OFI = "ServiciosOFI.csv"; 
    public static final String[] OFI_FICHEROS = {OFI_OFICINAS,OFI_CONTACTO_OFI,OFI_HISTORICOS_OFI,OFI_RELACIONES_ORGANIZATIVAS_OFI,OFI_RELACIONES_SIROFI,OFI_SERVICIOS_OFI};
    
     // Importación UNIDADES
    public static final String UNIDADES_ARCHIVO_ZIP = "unidades";
    public static final String UO_CONTACTO_UO = "ContactoUO.csv"; 
    public static final String UO_HISTORICOS_UO = "HistoricosUO.csv"; 
    public static final String UO_UNIDADES = "Unidades.csv"; 
    public static final String[] UO_FICHEROS = {UO_UNIDADES,UO_HISTORICOS_UO,UO_CONTACTO_UO};


    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA = "dd/MM/yyyy hh:mm:ss";
    
    //Descarga tipos
    public static final String CATALOGO = "1";
    public static final String UNIDADES_OFICINAS = "2";
    public static final String UNIDAD = "unidad";
    public static final String OFICINA = "oficina";

    //Estados entidad
    public static final String ESTADO_ENTIDAD_VIGENTE= "V";
    public static final String ESTADO_ENTIDAD_EXTINGUIDO= "E";
    public static final String ESTADO_ENTIDAD_ANULADO= "A";
    public static final String ESTADO_ENTIDAD_TRANSITORIO= "T";

    public static final String DESCRIPCION_ESTADO_ENTIDAD_VIGENTE = "Vigente";
    public static final String DESCRIPCION_ESTADO_ENTIDAD_EXTINGUIDO = "Extinguido";
    public static final String DESCRIPCION_ESTADO_ENTIDAD_ANULADO = "Anulado";
    public static final String DESCRIPCION_ESTADO_ENTIDAD_TRANSITORIO = "Transitorio";

    //codigo respuesta correcto ws descarga madrid
    public static final String CODIGO_CORRECTO = "01";
    public static final String CODIGO_VACIO = "14";

    //servicios SIR
    public static final Long SERVICIO_OFI_REGISTRO = 1L;
    public static final Long SERVICIO_OFI_INFORMACION = 2L;
    public static final Long SERVICIO_OFI_TRAMITACION = 3L;
    public static final Long SERVICIO_REG_VIRTUAL = 4L;
    public static final Long SERVICIO_SIR = 5L;
    public static final Long SERVICIO_SIR_ENVIO = 6L;
    public static final Long SERVICIO_SIR_RECEPCION = 7L;
    public static final Long SERVICIO_060 = 8L;
    public static final Long SERVICIO_OFI_CORREOS = 9L;
    public static final Long SERVICIO_OFI_EXTRANJERIA = 10L;
    public static final Long SERVICIO_OFI_VIOLGENERO = 11L;
    public static final Long SERVICIO_OFI_ACCESIBLE = 12L;
    public static final Long SERVICIO_CLAVE = 13L;
    public static final Long SERVICIO_REA = 14L;
    public static final Long SERVICIO_OFI_ORD = 15L;

    // Estados Sincronización
    public static final Long SINCRONIZACION_DESCARGADA = 1L;
    public static final Long SINCRONIZACION_VACIA = 2L;
    public static final Long SINCRONIZACION_ERROR_DESCARGA = 3L;
    public static final Long SINCRONIZACION_ERRONEA = 4L;
    public static final Long SINCRONIZACION_CORRECTA = 5L;

    //Tipos de Sincronización
    public static final String SINCRONIZACION_CATALOGO = "Catàleg";
    public static final String SINCRONIZACION_DIRECTORIO = "Directori";

    // Expresión CRON por defecto para actualizar DIR3
    public static final String CRON_SINCRONIZAR_DIR3 = "0 0 1 1/1 * ? *"; // Sincronización diaria a las 01:00 am

    // Textos para envio Mail error en Sincronización
    public static final String ASUNTO_MAIL = "S'ha produit un error a la sincronització de ";
    public static final String CUERPO_MAIL = "S'ha produït un error a la sincronització. S'ha de revisar perquè si no s'actualitzen les dades diàriament pot afectar a les aplicacions connectades amb DIR3CAIB.";
    public static final String APLICACION_NOMBRE = "DIR3CAIB";
    public static final String APLICACION_EMAIL = "no_responder@dir3caib.com";


    //NIVEL ADMINISTRACION UNIVERSIDADES
    public static final Long NIVEL_ADMINISTRACION_UNIVERSIDADES=4L;

}
