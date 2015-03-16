package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
import es.caib.dir3caib.utils.Constants;
import es.caib.dir3caib.ws.dir3.catalogo.client.RespuestaWS;
import es.caib.dir3caib.ws.dir3.catalogo.client.SC21CTVolcadoCatalogosService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created 10/03/14 14:20
 *
 * Clase que permite importar el catálogo desde el directorio común. En un primer paso
 * se descargan los archivos y posteriormente se importa el contenido en la BD.
 *
 *
 * @author mgonzalez
 * @author anadal (Cache & EJB)
 * @author anadal (Eliminar PKs multiples)
 * 
 */
@Stateless(name = "ImportadorCatalogoEJB")
@SecurityDomain("seycon")
@RunAs(Constants.DIR_ADMIN)
@PermitAll
public class ImportadorCatalogoBean implements ImportadorCatalogoLocal {

  protected final Logger log = Logger.getLogger(getClass());

  // Indicamos el formato de fecha dd/MM/yyyy hh:mm:ss
  protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

 @EJB(mappedName = "dir3caib/CatAmbitoTerritorialEJB/local")
  protected CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;

  @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
  protected CatEntidadGeograficaLocal catEntidadGeograficaEjb;

  @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
  protected CatEstadoEntidadLocal catEstadoEntidadEjb;

  @EJB(mappedName = "dir3caib/CatIslaEJB/local")
  protected CatIslaLocal catIslaEjb;

  @EJB(mappedName = "dir3caib/CatJerarquiaOficinaEJB/local")
  protected CatJerarquiaOficinaLocal catJerarquiaOficinaEjb;

  @EJB(mappedName = "dir3caib/CatMotivoExtincionEJB/local")
  protected CatMotivoExtincionLocal catMotivoExtincionEjb;

  @EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
  protected CatNivelAdministracionLocal catNivelAdministracionEjb;

  @EJB(mappedName = "dir3caib/CatPaisEJB/local")
  protected CatPaisLocal catPaisEjb;

  @EJB(mappedName = "dir3caib/CatTipoContactoEJB/local")
  protected CatTipoContactoLocal catTipoContactoEjb;

  @EJB(mappedName = "dir3caib/CatTipoEntidadPublicaEJB/local")
  protected CatTipoEntidadPublicaLocal catTipoEntidadPublicaEjb;

  @EJB(mappedName = "dir3caib/CatTipoUnidadOrganicaEJB/local")
  protected CatTipoUnidadOrganicaLocal catTipoUnidadOrganicaEjb;

  @EJB(mappedName = "dir3caib/CatTipoViaEJB/local")
  protected CatTipoViaLocal catTipoViaEjb;

  @EJB(mappedName = "dir3caib/CatComunidadAutonomaEJB/local")
  protected CatComunidadAutonomaLocal catComunidadAutonomaEjb;

  @EJB(mappedName = "dir3caib/CatProvinciaEJB/local")
  protected CatProvinciaLocal catProvinciaEjb;

  @EJB(mappedName = "dir3caib/CatLocalidadEJB/local")
  protected CatLocalidadLocal catLocalidadEjb;

  @EJB(mappedName = "dir3caib/DescargaEJB/local")
  protected DescargaLocal descargaEjb;

  //@EJB(mappedName = "dir3caib/ImportarEJB/local")
  //protected ImportarLocal importarEjb;


  /*
  public static void main(String[] args) {
    
    try {
      

    
    String path ="D:\\dades\\dades\\CarpetesPersonals\\Programacio\\Dir3Caib-Files\\archivos\\catalogos\\CAT_COMUNIDAD_AUTONOMA.CSV";
    FileInputStream is1 = new FileInputStream(path);
    
    BufferedReader is = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
    
    int c;
    
    while ((c = is.read()) != -1) {
      System.out.print((char)c);
    }
    
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  */
  

   /**
  * Método que importa el contenido de los archivos del catálog  descargados previamente a través
  * de los WS.
  * */
  @Override
  @TransactionTimeout(value=3600)
  public ResultadosImportacion importarCatalogo() throws Exception  {
    
     
     ResultadosImportacion results = new ResultadosImportacion();
     //Lista de archivos que han sido procesados al finalizar la importación
     List<String> procesados = results.getProcesados();
     //Lista de archivos que no existen y deberian existir
     List<String> inexistentes = results.getInexistentes();

     // Obtenemos el listado de ficheros que hay dentro del directorio indicado
     File f = FileSystemManager.getArchivosPath(Dir3caibConstantes.CATALOGOS_LOCATION_PROPERTY);
     ArrayList<String> existentes = new ArrayList<String>(Arrays.asList(f.list()));
     results.setExistentes(existentes);

     // TODO caches
     Map<String, CatEntidadGeografica> cacheEntidadGeografica  = new TreeMap<String, CatEntidadGeografica>();
     
     Map<Long, CatNivelAdministracion> cacheNivelAdministracion  = new TreeMap<Long, CatNivelAdministracion>();
     
     Map<Long, CatPais> cachePais = new TreeMap<Long,CatPais>();
     
     Map<Long,CatComunidadAutonoma> cacheComunidadAutonoma = new TreeMap<Long,CatComunidadAutonoma>();
     
     Map<Long,CatProvincia> cacheProvincia = new TreeMap<Long,CatProvincia>();


     // Buscamos los posibles ficheros de catalogos que pueden existir en el directorio
     for (int i = 0; i < Dir3caibConstantes.CAT_FICHEROS.length; i++) 
     {
         String fichero = Dir3caibConstantes.CAT_FICHEROS[i];

         String[] fila;

         CSVReader reader = null;
         
         try {
           
           reader = getCSVReader(fichero);

           //reader=new CSVReader(new InputStreamReader(new FileInputStream(FileSystemManager.getArchivo(Dir3caibConstantes.CATALOGOS_LOCATION_PROPERTY,fichero)), "ISO-8859-15"), '|');
           if(reader != null){
               // Inicio importación
               String nombreFichero = fichero;
               

               //CATENTIDADGEOGRAFICA
               
               if(nombreFichero.equals(Dir3caibConstantes.CAT_ENTIDAD_GEOGRAFICA)){

                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {
                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         String codigoEntidadGeografica = fila[0];
                         CatEntidadGeografica entidadGeografica;
                         entidadGeografica = cacheEntidadGeografica.get(codigoEntidadGeografica);

                         entidadGeografica = catEntidadGeograficaEjb.findById(codigoEntidadGeografica);

                         

                         if(entidadGeografica == null){ // Si es nuevo creamos el objeto a introducir
                           entidadGeografica = new CatEntidadGeografica();
                           entidadGeografica.setCodigoEntidadGeografica(codigoEntidadGeografica);
                         }

                         entidadGeografica.setDescripcionEntidadGeografica(fila[1]);

                         catEntidadGeograficaEjb.persist(entidadGeografica);

                         
                         cacheEntidadGeografica.put(codigoEntidadGeografica, entidadGeografica);
                         
                     } catch(Exception e) {
                         log.info(e.getMessage());
                     }
                 }

               }

               //CATESTADOENTIDAD
               if(Dir3caibConstantes.CAT_ESTADO_ENTIDAD.equals(nombreFichero)){

                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {
                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         String codigoEstadoEntidad = fila[0];
                         CatEstadoEntidad estadoEntidad;

                         estadoEntidad = catEstadoEntidadEjb.findById(codigoEstadoEntidad);


                         if(estadoEntidad == null){ // Si es nuevo creamos el objeto a introducir
                           estadoEntidad = new CatEstadoEntidad();
                           estadoEntidad.setCodigoEstadoEntidad(codigoEstadoEntidad);
                         }
                         estadoEntidad.setDescripcionEstadoEntidad(fila[1]);

                         catEstadoEntidadEjb.persist(estadoEntidad);

                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }

               }




               //CATJERARQUIAOFICINA
               if(Dir3caibConstantes.CAT_JERARQUIA_OFICINA.equals(nombreFichero)){
                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {

                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         String sCodigoJerarquiaOficina = fila[0].trim();
                         if(!sCodigoJerarquiaOficina.isEmpty()){
                           Long codigoJerarquiaOficina= new Long(sCodigoJerarquiaOficina);
                           CatJerarquiaOficina jerarquiaOficina;

                           jerarquiaOficina = catJerarquiaOficinaEjb.findById(codigoJerarquiaOficina);


                           if(jerarquiaOficina == null){ // Si es nuevo creamos el objeto a introducir
                             jerarquiaOficina = new CatJerarquiaOficina();
                             jerarquiaOficina.setCodigoJerarquiaOficina(codigoJerarquiaOficina);
                           }
                           jerarquiaOficina.setDescripcionJerarquiaOficina(fila[1]);

                           catJerarquiaOficinaEjb.persist(jerarquiaOficina);

                         }
                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }
               }

               //CATMOTIVOEXTINCION
               if(Dir3caibConstantes.CAT_MOTIVO_EXTINCION.equals(nombreFichero)){

                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {

                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         String codigoMotivoExtincion = fila[0];

                         CatMotivoExtincion motivoExtincion;

                         motivoExtincion = catMotivoExtincionEjb.findById(codigoMotivoExtincion);

                         if(motivoExtincion == null){ // Si es nuevo creamos el objeto a introducir
                           motivoExtincion = new CatMotivoExtincion();
                           motivoExtincion.setCodigoMotivoExtincion(codigoMotivoExtincion);
                         }
                         motivoExtincion.setDescripcionMotivoExtincion(fila[1]);

                         catMotivoExtincionEjb.persist(motivoExtincion);

                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }

               }

               // CATNIVELADMINISTRACION
               if(Dir3caibConstantes.CAT_NIVEL_ADMINISTRACION.equals(nombreFichero)){
                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {
                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         String sCodigoNivelAdministracion =  fila[0].trim();
                         if(!sCodigoNivelAdministracion.isEmpty()){
                         Long codigoNivelAdministracion = new Long(sCodigoNivelAdministracion);

                           CatNivelAdministracion nivelAdministracion;

                           nivelAdministracion = catNivelAdministracionEjb.findById(codigoNivelAdministracion);


                           if(nivelAdministracion == null){ // Si es nuevo creamos el objeto a introducir
                            nivelAdministracion = new CatNivelAdministracion();
                            nivelAdministracion.setCodigoNivelAdministracion(codigoNivelAdministracion);
                           }
                           nivelAdministracion.setDescripcionNivelAdministracion(fila[1]);


                           catNivelAdministracionEjb.persist(nivelAdministracion);

                           
                           cacheNivelAdministracion.put(codigoNivelAdministracion, nivelAdministracion);

                         }
                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }
               }

               //CATPAIS
               if(Dir3caibConstantes.CAT_PAIS.equals(nombreFichero)){
                 //if(numCabeceras == Dir3caibConstantes.CAT_PAIS_CABECERA){
                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {
                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         Long codigoPais = new Long(fila[0].trim());
                         CatPais pais;

                         pais = catPaisEjb.findById(codigoPais);

                         if(pais == null){ // Si es nuevo creamos el objeto a introducir
                           pais = new CatPais();
                           pais.setCodigoPais(codigoPais);
                         }
                         pais.setDescripcionPais(fila[1].trim());
                         pais.setAlfa2Pais(fila[2].trim());
                         pais.setAlfa3Pais(fila[3].trim());

                         /*if(quartz){
                            importarEjb.persistPais(pais);
                         }else */{
                            catPaisEjb.persist(pais);
                         }
                         cachePais.put(codigoPais, pais);
                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }
               }



                //CATCOMUNIDADAUTONOMA
               if(Dir3caibConstantes.CAT_COMUNIDAD_AUTONOMA.equals(nombreFichero)){
                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {

                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                       Long codigoComunidad = new Long(fila[0].trim());
                       // cargamos el nivel de Administracion correspondiente.
                       CatPais catPais;
                       catPais = cachePais.get(new Long(fila[2].trim()));
                       // Miramos si ya existe
                       CatComunidadAutonoma comunidadAutonoma;
                       comunidadAutonoma = catComunidadAutonomaEjb.findById(codigoComunidad);
                       
                       if(comunidadAutonoma == null){ // Si es nuevo creamos el objeto a introducir
                         comunidadAutonoma = new CatComunidadAutonoma();
                         comunidadAutonoma.setCodigoComunidad(codigoComunidad);
                       }
                       comunidadAutonoma.setPais(catPais);
                       comunidadAutonoma.setDescripcionComunidad(fila[1]);
                       comunidadAutonoma.setC_comunidad_rpc(fila[3]);
                       comunidadAutonoma.setC_codigo_dir2(new Long(fila[4]));

                       catComunidadAutonomaEjb.persist(comunidadAutonoma);

                      
                       cacheComunidadAutonoma.put(codigoComunidad,comunidadAutonoma);
                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }
                 
                 cachePais.clear();
                 cachePais = null;

               }

               //CATPROVINCIA
               if(Dir3caibConstantes.CAT_PROVINCIA.equals(nombreFichero)){
                 //if(numCabeceras==Dir3caibConstantes.CAT_PROVINCIA_CABECERA){

                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {

                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                       Long codigoProvincia = new Long(fila[0]);
                       // cargamos el nivel de Administracion correspondiente.
                       CatComunidadAutonoma catComunidadAutonoma ;
                       catComunidadAutonoma = cacheComunidadAutonoma.get(new Long(fila[2]));

                       // Miramos si ya existe la provincia
                       CatProvincia provincia;
                       {
                          provincia = catProvinciaEjb.findById(codigoProvincia);
                       }
                       if(provincia == null){ // Si es nuevo creamos el objeto a introducir
                         provincia = new CatProvincia();
                         provincia.setCodigoProvincia(codigoProvincia);
                       }
                       provincia.setComunidadAutonoma(catComunidadAutonoma);
                       provincia.setDescripcionProvincia(fila[1]);


                       catProvinciaEjb.persist(provincia);

                       
                       cacheProvincia.put(codigoProvincia, provincia);
                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }
                 
                 cacheComunidadAutonoma.clear();
                 cacheComunidadAutonoma = null;

               }

               //CATISLA
               if(Dir3caibConstantes.CAT_ISLA.equals(nombreFichero)){

                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {
                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         Long codigoIsla = new Long(fila[0]);
                         CatIsla isla;

                         isla = catIslaEjb.findById(codigoIsla);

                         if(isla == null){ // Si es nuevo creamos el objeto a introducir
                           isla = new CatIsla();
                           isla.setCodigoIsla(codigoIsla);
                         }
                         isla.setDescripcionIsla(fila[1]);
                         CatProvincia provincia;
                         provincia = cacheProvincia.get(new Long(fila[2]));

                         isla.setProvincia(provincia);

                         catIslaEjb.persist(isla);

                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }

               }

               //CATTIPOCONTACTO
               if(Dir3caibConstantes.CAT_TIPO_CONTACTO.equals(nombreFichero)){
                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {

                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         String codigoTipoContacto = fila[0];
                         CatTipoContacto tipoContacto;

                         tipoContacto = catTipoContactoEjb.findById(codigoTipoContacto);

                         if(tipoContacto == null){ // Si es nuevo creamos el objeto a introducir
                           tipoContacto = new CatTipoContacto();
                           tipoContacto.setCodigoTipoContacto(codigoTipoContacto);
                         }
                         tipoContacto.setDescripcionTipoContacto(fila[1]);

                         catTipoContactoEjb.persist(tipoContacto);

                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }

               }

               //CATTIPOENTIDADPUBLICA
                if(Dir3caibConstantes.CAT_TIPO_ENTIDAD_PUBLICA.equals(nombreFichero)){

                  reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                  while ((fila = reader.readNext()) != null) {
                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         String codigoTipoEntidadPublica = fila[0];
                         CatTipoEntidadPublica tipoEntidadPublica;

                         tipoEntidadPublica = catTipoEntidadPublicaEjb.findById(codigoTipoEntidadPublica);

                         if(tipoEntidadPublica == null){ // Si es nuevo creamos el objeto a introducir
                           tipoEntidadPublica = new CatTipoEntidadPublica();
                           tipoEntidadPublica.setCodigoTipoEntidadPublica(codigoTipoEntidadPublica);
                         }
                         tipoEntidadPublica.setDescripcionTipoEntidadPublica(fila[1]);

                         catTipoEntidadPublicaEjb.persist(tipoEntidadPublica);

                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                  }

               }

               //CATTIPOUNIDADORGANICA
               if(Dir3caibConstantes.CAT_TIPO_UNIDAD_ORGANICA.equals(nombreFichero)){

                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {
                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         String codigoTipoUnidadOrganica = fila[0];
                         CatTipoUnidadOrganica tipoUnidadOrganica;
                         /*if(quartz){
                            tipoUnidadOrganica = importarEjb.findTipoUnidadOrganica(codigoTipoUnidadOrganica);
                         }else */{
                            tipoUnidadOrganica = catTipoUnidadOrganicaEjb.findById(codigoTipoUnidadOrganica);
                         }
                         if(tipoUnidadOrganica == null){ // Si es nuevo creamos el objeto a introducir
                           tipoUnidadOrganica = new CatTipoUnidadOrganica();
                           tipoUnidadOrganica.setCodigoTipoUnidadOrganica(codigoTipoUnidadOrganica);
                         }
                         tipoUnidadOrganica.setDescripcionTipoUnidadOrganica(fila[1]);

                         catTipoUnidadOrganicaEjb.persist(tipoUnidadOrganica);

                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }

               }

               //CATTIPOVIA
               if(Dir3caibConstantes.CAT_TIPO_VIA.equals(nombreFichero)){

                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {
                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                         // Miramos si existe ya en la BD
                         Long codigoTipoVia = new Long(fila[0]);
                         CatTipoVia tipoVia;

                         tipoVia = catTipoViaEjb.findById(codigoTipoVia);

                         if(tipoVia == null){ // Si es nuevo creamos el objeto a introducir
                           tipoVia = new CatTipoVia();
                           tipoVia.setCodigoTipoVia(codigoTipoVia);
                         }
                         tipoVia.setDescripcionTipoVia(fila[1]);

                         catTipoViaEjb.persist(tipoVia);

                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                 }

               }


               // CATAMBITOTERRITORIAL
               if(Dir3caibConstantes.CAT_AMBITO_TERRITORIAL.equals(nombreFichero)){

                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {

                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                       String codigoAmbito = fila[0];
                       // cargamos el nivel de Administracion correspondiente.
                       CatNivelAdministracion catNivelAdministracion;
                       catNivelAdministracion = cacheNivelAdministracion.get(Long.parseLong(fila[2]));
                       //CatAmbitoTerritorialPK2 catAmbitoTerritorialPK = new CatAmbitoTerritorialPK2(codigoAmbito, Long.parseLong(fila[2]));

                       // Miramos si ya existe el ambitoTerritorial
                       CatAmbitoTerritorial ambitoTerritorial;
                       {
                          ambitoTerritorial = catAmbitoTerritorialEjb.findByPKs(codigoAmbito, Long.parseLong(fila[2]));
                       }
                       if(ambitoTerritorial == null){ // Si es nuevo creamos el objeto a introducir
                         ambitoTerritorial = new CatAmbitoTerritorial();
                         ambitoTerritorial.setCodigoAmbito(codigoAmbito);
                         ambitoTerritorial.setNivelAdministracion(catNivelAdministracion);
                       }
                       ambitoTerritorial.setDescripcionAmbito(fila[1]);

                       catAmbitoTerritorialEjb.persist(ambitoTerritorial);

                     }catch(Exception e){
                         log.info(e.getMessage());
                     }
                   }

               }


               //CATLOCALIDAD
               if(Dir3caibConstantes.CAT_LOCALIDAD.equals(nombreFichero)) {


                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {
                     // Obtenemos codigo y miramos si ya existe en la BD
                     // Creamos la clave compuesta primero.
                     try{
                       final Long codigoLocalidad = new Long(fila[0]);
                       // cargamos el nivel de Administracion correspondiente.
                       
                       final Long codigoProvincia = new Long(fila[2]);

                       final String codigoEntidadGeografica = fila[3];
                       

                       
                       //CatLocalidadPK2 catLocalidadPK = new CatLocalidadPK2(codigoLocalidad, catProvincia, catEntidadGeografica);

                       // Miramos si ya existe el ambitoTerritorial
                       CatLocalidad localidad;
                       
                       localidad = catLocalidadEjb.findByPKs(codigoLocalidad, codigoProvincia,
                              codigoEntidadGeografica);
                       
                       if(localidad == null){ // Si es nuevo creamos el objeto a introducir
                         localidad = new CatLocalidad();
                         localidad.setCodigoLocalidad(codigoLocalidad);
                         
                         CatProvincia catProvincia = cacheProvincia.get(codigoProvincia);                       
                         localidad.setProvincia(catProvincia);
                         
                         CatEntidadGeografica catEntidadGeografica;
                         catEntidadGeografica = cacheEntidadGeografica.get(fila[3]);
                         localidad.setEntidadGeografica(catEntidadGeografica);
                       }
                       
                       
                       //Controlamos que no sea null o cadena vacía, ya que en bd no puede serlo.
                       if(fila[1] == null || fila[1].length() == 0){
                         localidad.setDescripcionLocalidad("-");
                       }else{
                         localidad.setDescripcionLocalidad( fila[1]);
                       }

                       
                       catLocalidadEjb.persist(localidad);
                       
                     }catch(Exception e){
                         log.error(" Error important Localidad " + e.getMessage());
                     }

                 }
                 
                 // Ja podem borrar la cache de Entidad 
                 cacheEntidadGeografica.clear();
                 cacheEntidadGeografica = null;
                 cacheProvincia.clear();
                 cacheProvincia = null;

               }
               log.info(" Acabados procesamiento: " + nombreFichero);
               procesados.add(fichero);

           }

           reader.close();
           

        } catch (FileNotFoundException ex) {
            inexistentes.add(fichero);
            ex.printStackTrace();
        } catch (IOException io){
            io.printStackTrace();
        }catch(Exception e){
              e.printStackTrace();
        } finally {
             if(reader != null){
                 try {
                     reader.close();
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
            }
        }

     }
     
     
     // Guardamos fecha Importacion y tipo
     Descarga descarga;

     descarga = descargaEjb.findByTipo(Dir3caibConstantes.CATALOGO);
     if (descarga == null) {
       descarga = new Descarga();
       descarga.setTipo(Dir3caibConstantes.CATALOGO);
     }
     descarga.setFechaImportacion(new Date());
     descargaEjb.merge(descarga);

     results.setDescarga(descarga);
     
     System.gc();
     
     return results;
  }

  public CSVReader getCSVReader(String fichero) throws FileNotFoundException,
      UnsupportedEncodingException {
    CSVReader reader;
    log.info("");
    log.info("Fichero: " + fichero);
    log.info("------------------------------------");

    // Obtenemos el fichero del sistema de archivos
    File file = FileSystemManager.getArchivo(Dir3caibConstantes.CATALOGOS_LOCATION_PROPERTY,
        fichero);
    if (file.exists()) {
      FileInputStream is1 = new FileInputStream(file);
      BufferedReader is = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
      reader = new CSVReader(is, '|');
      return reader;
    } else {
      log.error(" Fitxer " + file.getAbsolutePath() + " no existeix.");
      return null;
    }

  }


  

  

  /**
   * Método que se encarga de obtener los archivos del catálogo a través de WS

   * @param fechaInicio
   * @param fechaFin
   */
   @Override
   public void descargarCatalogoWS(Date fechaInicio, Date fechaFin) throws Exception{

      byte[] buffer = new byte[1024];


     //Definimos el formato de la fecha para las descargas de los WS.

      // Guardaremos la fecha de la ultima descarga
      Descarga descarga = new Descarga();
      descarga.setTipo(Dir3caibConstantes.CATALOGO);


      //guardamos todas las fechas de la descarga
      if(fechaInicio!= null){
        descarga.setFechaInicio(fechaInicio);

      }
      if(fechaFin!=null){
        descarga.setFechaFin(fechaFin);

      }
      // establecemos la fecha de hoy si las fechas estan vacias.
      Date hoy = new Date();

      if(fechaInicio == null){
        descarga.setFechaInicio(hoy);
      }
      if(fechaFin == null){
        descarga.setFechaFin(hoy);
      }

      log.info("DESCARGA FECHA INICIO " + descarga.getFechaInicio());

      // Obtenemos  usuario y rutas para los WS.
      String usuario = System.getProperty(Dir3caibConstantes.DIR3WS_USUARIO_PROPERTY);
      String password = System.getProperty(Dir3caibConstantes.DIR3WS_PASSWORD_PROPERTY);
      String ruta = System.getProperty(Dir3caibConstantes.ARCHIVOS_LOCATION_PROPERTY);
      String rutaCatalogos = System.getProperty(Dir3caibConstantes.CATALOGOS_LOCATION_PROPERTY);

      SC21CTVolcadoCatalogosService service = new SC21CTVolcadoCatalogosService();

      // Invocamos al WS
      RespuestaWS respuestaCsv = service.getSC21CTVolcadoCatalogos().exportar(usuario,password,"csv","COMPLETO");
      //RespuestaWS respuestaXml = service.getSC21CTVolcadoCatalogos().exportar(usuario,password,"xml","COMPLETO")
      Base64 decoder = new Base64();

      log.info("Codigo: " + respuestaCsv.getCodigo());
      log.info("Descripcion: " + respuestaCsv.getDescripcion());

      // Realizamos copia del fichero de la ultima descarga
      File file = new File(ruta + Dir3caibConstantes.CATALOGOS_ARCHIVO_ZIP);
      log.info("Directorio :" + file.getAbsolutePath());
      log.info("Tamaño :" +respuestaCsv.getFichero().length());
      if(file.exists()){
        FileUtils.copyFile(file, new File(ruta + "old_" + Dir3caibConstantes.CATALOGOS_ARCHIVO_ZIP));
      }
      // guardamos el nuevo fichero descargado
      FileUtils.writeByteArrayToFile(file, decoder.decode(respuestaCsv.getFichero()));



      // Borramos contenido
      FileUtils.cleanDirectory(new File(rutaCatalogos));

      //Descomprimir el archivo
      ZipInputStream zis = new ZipInputStream(new FileInputStream(ruta + Dir3caibConstantes.CATALOGOS_ARCHIVO_ZIP));
      ZipEntry zipEntry = zis.getNextEntry();



      while(zipEntry != null){

         String fileName = zipEntry.getName();
         File newFile = new File(rutaCatalogos + fileName);

         log.info("Fichero descomprimido: "+ newFile.getAbsoluteFile());

         //create all non exists folders
          //else you will hit FileNotFoundException for compressed folder
          new File(newFile.getParent()).mkdirs();




          FileOutputStream fos = new FileOutputStream(newFile);

          int len;
          while ((len = zis.read(buffer)) > 0) {
              fos.write(buffer, 0, len);
          }

          fos.close();
        zipEntry = zis.getNextEntry();

      }
      zis.closeEntry();
      zis.close();


      descargaEjb.persist(descarga);


   }
   
   
   
   /* Tarea que en un primer paso descarga los archivos csv del catálogo y posteriormente importa el contenido
    *  en la base de datos, de esta manera realiza el proceso de sincronizacion con Madrid en un sólo
    *  proceso
    *  */
   @Override
   @TransactionTimeout(value=3600)
    public void importarCatalogoTask(){

        try {
             //Obtenemos las fechas entre las que hay que hacer la descarga

             // obtenemos los datos de la última descarga
             Descarga ultimaDescarga = descargaEjb.findByTipo(Dir3caibConstantes.CATALOGO);
             Date fechaInicio =ultimaDescarga.getFechaFin(); // fecha de la ultima descarga

             // obtenemos la fecha de hoy
             Date fechaFin = new Date();


             // Obtiene los archivos csv via WS
             descargarCatalogoWS(fechaInicio, fechaFin);

             // Variables necesarias para importar el catálogo en el sistema
             /*
             List<String> procesados = new ArrayList<String>();
             List<String> inexistentes = new ArrayList<String>();
             Descarga descarga = new Descarga();
             */

             // importamos el catálogo a la bd.
             importarCatalogo();
        } catch(Exception e){
          e.printStackTrace();
        }
    }
   
   



}
