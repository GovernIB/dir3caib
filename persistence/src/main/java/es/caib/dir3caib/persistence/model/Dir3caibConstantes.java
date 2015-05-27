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
    public static final String ROL_ADMIN = "DIR_ADMIN";  // Administrador

    
    // Importación Catalogo
    //public static final String CATALOGOS_LOCATION_PROPERTY = "es.caib.dir3caib.catalogos.path";
    public static final String CATALOGOS_ARCHIVO_ZIP = "catalogosCSV";
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
    public static final String[] CAT_FICHEROS = {CAT_ENTIDAD_GEOGRAFICA, CAT_ESTADO_ENTIDAD, CAT_JERARQUIA_OFICINA, CAT_MOTIVO_EXTINCION, CAT_NIVEL_ADMINISTRACION, CAT_PAIS, CAT_COMUNIDAD_AUTONOMA, CAT_PROVINCIA, CAT_ISLA, CAT_TIPO_CONTACTO, CAT_TIPO_ENTIDAD_PUBLICA, CAT_TIPO_UNIDAD_ORGANICA, CAT_TIPO_VIA, CAT_AMBITO_TERRITORIAL, CAT_LOCALIDAD};
    
    
    // Cabeceras Catalogo
    
    public static final int CAT_AMBITO_TERRITORIAL_CABECERA   = 3; 
    public static final int CAT_COMUNIDAD_AUTONOMA_CABECERA   = 5 ; 
    public static final int CAT_ENTIDAD_GEOGRAFICA_CABECERA   = 2; 
    public static final int CAT_ESTADO_ENTIDAD_CABECERA       = 2; 
    public static final int CAT_ISLA_CABECERA                 = 3;
    public static final int CAT_JERARQUIA_OFICINA_CABECERA    = 2; 
    public static final int CAT_LOCALIDAD_CABECERA            = 5;
    public static final int CAT_MOTIVO_EXTINCION_CABECERA     = 2; 
    public static final int CAT_NIVEL_ADMINISTRACION_CABECERA = 2; 
    public static final int CAT_PAIS_CABECERA                 = 4; 
    public static final int CAT_PROVINCIA_CABECERA            = 3; 
    public static final int CAT_TIPO_CONTACTO_CABECERA        = 2; 
    public static final int CAT_TIPO_ENTIDAD_PUBLICA_CABECERA = 2; 
    public static final int CAT_TIPO_UNIDAD_ORGANICA_CABECERA = 2; 
    public static final int CAT_TIPO_VIA_CABECERA             = 3; 
    
    
    // Importación Oficinas
    //public static final String OFICINAS_LOCATION_PROPERTY = "es.caib.dir3caib.oficinas.path";
    public static final String OFICINAS_ARCHIVO_ZIP = "oficinasCSV";
    public static final String OFI_CONTACTO_OFI = "ContactoOFI.csv"; 
    public static final String OFI_HISTORICOS_OFI = "HistoricosOFI.csv"; 
    public static final String OFI_OFICINAS = "Oficinas.csv"; 
    public static final String OFI_RELACIONES_ORGANIZATIVAS_OFI = "RelacionesOrganizativasOFI.csv"; 
    public static final String OFI_RELACIONES_SIROFI = "RelacionesSIROFI.csv"; 
    public static final String OFI_SERVICIOS_OFI = "ServiciosOFI.csv"; 
    public static final String[] OFI_FICHEROS = {OFI_OFICINAS,OFI_CONTACTO_OFI,OFI_HISTORICOS_OFI,OFI_RELACIONES_ORGANIZATIVAS_OFI,OFI_RELACIONES_SIROFI,OFI_SERVICIOS_OFI};
    
     // Importación UNIDADES
    //public static final String UNIDADES_LOCATION_PROPERTY = "es.caib.dir3caib.unidades.path";
    public static final String UNIDADES_ARCHIVO_ZIP = "unidadesCSV";
    public static final String UO_CONTACTO_UO = "ContactoUO.csv"; 
    public static final String UO_HISTORICOS_UO = "HistoricosUO.csv"; 
    public static final String UO_UNIDADES = "Unidades.csv"; 
    public static final String[] UO_FICHEROS = {UO_UNIDADES,UO_HISTORICOS_UO,UO_CONTACTO_UO}; 
    
    public static final int UNIDADES_CABECERA = 47;
    public static final int UO_HISTORICOS_CABECERA = 4;
    public static final int UO_CONTACTO_CABECERA = 4;
    
    public static final int OFICINAS_CABECERA = 28;
    public static final int OFI_CONTACTO_CABECERA = 4;
    public static final int OFI_HISTORICOS_CABECERA = 4;
    public static final int OFI_RELACIONES_ORGANIZATIVAS_CABECERA = 5;
    public static final int OFI_RELACIONES_SIROFI_CABECERA = 5;
    public static final int OFI_SERVICIOS_CABECERA = 3 ;
    
    
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA = "dd/MM/yyyy hh:mm:ss";
    
    //Descarga tipos
    public static final String UNIDAD = "unidad";
    public static final String OFICINA = "oficina";
    public static final String CATALOGO = "catalogo";

    //Estados entidad
    public static final String ESTADO_ENTIDAD_VIGENTE= "V";
    public static final String ESTADO_ENTIDAD_EXTINGUIDO= "E";
    public static final String ESTADO_ENTIDAD_ANULADO= "A";
    public static final String ESTADO_ENTIDAD_TRANSITORIO= "T";

}
