package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Constants;
import es.caib.dir3caib.utils.Utils;
import es.caib.dir3caib.ws.dir3.oficina.client.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.BindingProvider;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created 10/03/14 14:38
 *
 * @author mgonzalez
 * @author anadal (Cache & EJB)
 * @author anadal (Eliminar PKs multiples)
 * 
 */
@Stateless(name = "ImportadorOficinasEJB")
@SecurityDomain("seycon")
@RunAs(Constants.DIR_ADMIN)
@PermitAll
public class ImportadorOficinasBean  implements ImportadorOficinasLocal {
  protected final Logger log = Logger.getLogger(getClass());

  @EJB(mappedName = "dir3caib/UnidadEJB/local")
  protected UnidadLocal unidadEjb;

  @EJB(mappedName = "dir3caib/OficinaEJB/local")
  protected OficinaLocal oficinaEjb;

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


  @EJB(mappedName = "dir3caib/ContactoOfiEJB/local")
  protected ContactoOfiLocal contactoOfiEjb;

  @EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
  protected RelacionOrganizativaOfiLocal relOrgOfiEjb;

  @EJB(mappedName = "dir3caib/RelacionSirOfiEJB/local")
  protected RelacionSirOfiLocal relSirOfiEjb;

  @EJB(mappedName = "dir3caib/ServicioEJB/local")
  protected ServicioLocal servicioEjb;

  @EJB(mappedName = "dir3caib/DescargaEJB/local")
  protected DescargaLocal descargaEjb;

  //@EJB(mappedName = "dir3caib/ImportarEJB/local")
  //protected ImportarLocal importarEjb;

  SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);
  
  
  
  public class CacheUnidadOficina {
    
    Set<String> caches = new TreeSet<String>();
    
    
    public CacheUnidadOficina(List<String> uniofi) {
      this.caches.addAll(uniofi);
    }
    
    
    public boolean existsUnidadOficina(String unidad, String oficina) {
      return this.caches.contains(unidad + "_" + oficina);
    }
    
    
  }
  
  
  
  public class UnidadesCacheManager {
    
    // XYZ 
    public int countFind = 0;
    public int countCache = 0;
    public long findByTime = 0;
    
    private final UnidadLocal unidadEjb;
    
    Map<String, Unidad> cacheUnidad = new TreeMap<String,Unidad>();

    public UnidadesCacheManager(UnidadLocal unidadEjb) {
      super();
      this.unidadEjb = unidadEjb;
    }

    // * @param isupdate
    public UnidadesCacheManager(UnidadLocal unidadEjb, List<List<String>> unidadesRequeridas, int total) throws Exception {
      this(unidadEjb);

        //final int numberOfItems = 500;
        //int startItem = 1;
        List<Unidad> unidades;
        int count = 0;
        
        for(List<String> ids : unidadesRequeridas) {
          long start2 = System.currentTimeMillis();
          
          
          if ( log.isDebugEnabled()) {
            log.debug(" ids.size = " + ids.size());
          }
          unidades =  unidadEjb.getListByIds(ids);
          if ( log.isDebugEnabled()) {
            log.info(" getListByIds(ids).size = " + unidades.size());
          }

          for (Unidad ca : unidades) {        
            cacheUnidad.put(ca.getCodigo(), ca);
            count ++;
          }
          long end2 = System.currentTimeMillis();

          log.info(" Cache de Unidades " + count + " / " + total + "   -->   " + Utils.formatElapsedTime(end2 - start2));

        }

        log.info(" Cache Of Unidades. Total = " + count );

    }
    
    
    public Unidad get(String codigo) throws Exception {
      
      Unidad unidad = cacheUnidad.get(codigo);
      
      if (unidad == null) {
        long start = System.currentTimeMillis();
        unidad = this.unidadEjb.findById(codigo);
        findByTime =  findByTime + (System.currentTimeMillis() - start);
        cacheUnidad.put(codigo, unidad);
        countFind++;
      } else {        
        countCache++;
      }
      return unidad;
      
    }
    
    
  }
  
  
  
  /*
  public class UnidadesCacheManager {
    
    // XYZ 
    public int countFind = 0;
    public int countCache = 0;
    public long findByTime = 0;
    
    private final UnidadLocal unidadEjb;
    
    Map<String, Unidad> cacheUnidad = new TreeMap<String,Unidad>();

    // * @param isupdate
    public UnidadesCacheManager(UnidadLocal unidadEjb) throws Exception {
      super();
      
      this.unidadEjb = unidadEjb;


      
    }
    
    
    public Unidad get(String codigo) throws Exception {
      
      Unidad unidad = cacheUnidad.get(codigo);
      
      if (unidad == null) {
        long start = System.currentTimeMillis();
        unidad = this.unidadEjb.findById(codigo);
        findByTime =  findByTime + (System.currentTimeMillis() - start);
        cacheUnidad.put(codigo, unidad);
        countFind++;
      } else {        
        countCache++;
      }
      return unidad;
      
    }
    
    
  }
  
  */
  
  
  /*
  public class UnidadesCacheManager {
    
    private  final boolean isupdate;
    
    private final UnidadLocal unidadEjb;
    
    Map<String, Unidad> cacheUnidad = new TreeMap<String,Unidad>();

    // * @param isupdate
    public UnidadesCacheManager(UnidadLocal unidadEjb, boolean isupdate) throws Exception {
      super();
      this.isupdate = isupdate;
      this.unidadEjb = unidadEjb;

      if (!this.isupdate) {
        final int numberOfItems = 500;
        int startItem = 1;
        List<Unidad> unidades;
        int count = 0;
        final Long total = this.unidadEjb.getTotal();
        do {
          long start2 = System.currentTimeMillis();
          unidades =  unidadEjb.getPagination(startItem, numberOfItems);

          for (Unidad ca : unidades) {        
            cacheUnidad.put(ca.getCodigo(), ca);
            count ++;
          }
          long end2 = System.currentTimeMillis();

          log.info(" Cache de Unidades " + (startItem + numberOfItems - 1) + "/" + total + "   -->   " + Utils.formatElapsedTime(end2 - start2));
          startItem = startItem + numberOfItems;

        } while(unidades.size() == numberOfItems);

        log.info(" Total Count Of Unidades: " + count );
      }
      
      
    }
    
    
    public Unidad get(String codigo) throws Exception {
      if (isupdate) {
        return this.unidadEjb.findById(codigo);
      } else {
        return this.cacheUnidad.get(codigo);
      }
    }
    
    
  }
  */
  
  
  

   /**
  * Método que importa el contenido de los archivos de las oficinas y sus relaciones descargados previamente a través
  * de los WS.
  * @param isUpdate booleano que indica si la llamada es una sincronización(actualizacion)
  * */
  @Override
  @TransactionTimeout(value=13600)
  public ResultadosImportacion importarOficinas(boolean isUpdate) throws Exception {
    Date hoy = new Date();

    System.gc();

    ResultadosImportacion results = new ResultadosImportacion();
    
    List<String> procesados = results.getProcesados();
    List<String> inexistentes = results.getInexistentes();

    
    /*  CACHES */
    log.info("Inicialitzant Cache de Unidades per Importar Oficinas ...");
    
    long start = System.currentTimeMillis();
    
    UnidadesCacheManager cacheUnidad;
    
    if (isUpdate) {
      cacheUnidad = new UnidadesCacheManager(this.unidadEjb);
    } else {
      List<List<String>> unitsIds = new ArrayList<List<String>>();
      int total = getRequiredUnidades(unitsIds, 250);
      cacheUnidad = new UnidadesCacheManager(this.unidadEjb, unitsIds, total);
    }


    long end = System.currentTimeMillis();
    log.info("Inicialitzat Cache de Unidades en " + Utils.formatElapsedTime(end - start));
    
    start = System.currentTimeMillis();
    log.info("Inicialitzant Varies Caches per Importar Oficinas ...");

    
    Map<String, CatTipoContacto> cacheTipoContacto = new TreeMap<String,CatTipoContacto>();
    for (CatTipoContacto ca : catTipoContactoEjb.getAll()) {
      cacheTipoContacto.put(ca.getCodigoTipoContacto(), ca);
    }
   

    Map<Long, CatTipoVia> cacheTipoVia = new TreeMap<Long,CatTipoVia>();
    for (CatTipoVia ca : catTipoViaEjb.getAll()) {
      cacheTipoVia.put(ca.getCodigoTipoVia(), ca);
    }
    log.info(" TipoVias : " + cacheTipoVia.size());


    
    Map<Long, CatNivelAdministracion> cacheNivelAdministracion  = new TreeMap<Long, CatNivelAdministracion>();
    for (CatNivelAdministracion na : catNivelAdministracionEjb.getAll()) {
      cacheNivelAdministracion.put(na.getCodigoNivelAdministracion(), na);
    }
    
    Map<Long,CatProvincia> cacheProvincia = new TreeMap<Long,CatProvincia>();
    for (CatProvincia ca : catProvinciaEjb.getAll()) {
      cacheProvincia.put(ca.getCodigoProvincia(), ca);
    }
    log.info(" Provincia: " + cacheProvincia.size());

    Map<Long,CatComunidadAutonoma> cacheComunidadAutonoma = new TreeMap<Long,CatComunidadAutonoma>();
    for (CatComunidadAutonoma ca : catComunidadAutonomaEjb.getAll()) {
      cacheComunidadAutonoma.put(ca.getCodigoComunidad(), ca);
    }
    log.info(" Comunidad Autonoma : " + cacheComunidadAutonoma.size());

    Map<Long, CatPais> cachePais = new TreeMap<Long,CatPais>();
    for (CatPais ca : catPaisEjb.getAll()) {
      cachePais.put(ca.getCodigoPais(), ca);
    }
    log.info(" Pais : " + cachePais.size());

    
    Map<String, CatEstadoEntidad> cacheEstadoEntidad = new TreeMap<String,CatEstadoEntidad>();
    for (CatEstadoEntidad ca : catEstadoEntidadEjb.getAll()) {
      cacheEstadoEntidad.put(ca.getCodigoEstadoEntidad(), ca);
    }
    log.info(" Estado Entidad : " + cacheEstadoEntidad.size());
    
    
    Map<String, CatEntidadGeografica> cacheEntidadGeografica  = new TreeMap<String, CatEntidadGeografica>();
    for (CatEntidadGeografica ca : catEntidadGeograficaEjb.getAll()) {
      cacheEntidadGeografica.put(ca.getCodigoEntidadGeografica(), ca);
    }


    Map<CatLocalidadPK,CatLocalidad> cacheLocalidad = new HashMap<CatLocalidadPK,CatLocalidad>();
    for (CatLocalidad ca : catLocalidadEjb.getAll()) {
      CatLocalidadPK catLocalidadPK = new CatLocalidadPK(ca.getCodigoLocalidad(), ca.getProvincia(), ca.getEntidadGeografica());
      cacheLocalidad.put(catLocalidadPK, ca);
    }
    log.info(" Localidad: " + cacheLocalidad.size());
    
    
    
    end = System.currentTimeMillis();
    log.info("Inicialitzades Varies Caches per Importar Oficinas en " + Utils.formatElapsedTime(end - start));
    
    
    Set<String> existInBBDD = new TreeSet<String>();
    existInBBDD.addAll(oficinaEjb.getAllCodigos());
    
    
    Map<String,Oficina> oficinesCache = new TreeMap<String, Oficina>();
    
    


     // Obtenemos el listado de ficheros que hay dentro del directorio indicado que se
     // corresponde con la descarga hecha previamente
     Descarga descarga = descargaEjb.findByTipo(Dir3caibConstantes.OFICINA);
     File f = new File(Configuracio.getOficinasPath(descarga.getCodigo()));
     ArrayList<String> existentes = new ArrayList<String>(Arrays.asList(f.list()));
     

     // Buscamos los posibles ficheros de oficinas que pueden existir en el directorio
     for (int i = 0; i < Dir3caibConstantes.OFI_FICHEROS.length; i++) {
         String fichero = Dir3caibConstantes.OFI_FICHEROS[i];
         CSVReader reader = null;

         log.info("");
         log.info("Fichero: " + fichero);
         log.info("------------------------------------");
         try {
            // Obtenemos el fichero del sistema de archivos
           FileInputStream is1 = new FileInputStream(new File(Configuracio.getOficinasPath(descarga.getCodigo()),fichero));
           BufferedReader is = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
            reader = new CSVReader(is,';');
            if(reader != null){
                // Leemos el contenido y lo guardamos en un List
                String nombreFichero = fichero;
                String[] fila;
                if(Dir3caibConstantes.OFI_OFICINAS.equals(nombreFichero)){
                    reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                    int count = 1; 
                    while ((fila = reader.readNext()) != null) {
                       //Obtenemos codigo y miramos si ya existe en la BD
                      try{
                          //  Miramos si existe ya en la BD
                          String codigoOficina = fila[0];
                          Oficina oficina = null;
                          boolean existeix;

                          if (existInBBDD.contains(codigoOficina)) {
                            oficina = oficinaEjb.findById(codigoOficina);
                          }
                          if(oficina == null){  //Si es nuevo creamos el objeto a introducir
                            oficina = new Oficina();
                            oficina.setCodigo(codigoOficina);
                            existeix = false;
                          } else {
                            existeix = true;
                          }

                          //Fecha Importacion
                          oficina.setFechaImportacion(hoy);

                          // Comunidad Autonoma
                          String codigoComunidadAutonoma = fila[21].trim();
                          if(!codigoComunidadAutonoma.isEmpty()){
                            CatComunidadAutonoma comunidadAutonoma;
                            comunidadAutonoma = cacheComunidadAutonoma.get(new Long(codigoComunidadAutonoma));

                            oficina.setCodComunidad(comunidadAutonoma);
                          }

                          // Pais
                          String codigoPais = fila[20].trim();
                          if(!codigoPais.isEmpty()){
                            CatPais pais;
                            pais = cachePais.get(new Long(codigoPais));
                            oficina.setCodPais(pais);
                          }

                          // Codigo Postal
                          oficina.setCodPostal(fila[19].trim());

                          //Unidad organica responsable
                          String codUOResponsable = fila[5].trim();
                          if(!codUOResponsable.isEmpty()){
                            Unidad uoResponsable;
                            uoResponsable = cacheUnidad.get(codUOResponsable);
                            oficina.setCodUoResponsable(uoResponsable);
                          }

                          // atributos directos
                          oficina.setComplemento(fila[18].trim());
                          oficina.setDenominacion(fila[1].trim());
                          oficina.setDiasSinHabiles(fila[10].trim());
                          oficina.setDirExtranjera(fila[25].trim());
                          oficina.setDireccionObservaciones(fila[27].trim());
                          oficina.setHorarioAtencion(fila[9].trim());
                          oficina.setLocExtranjera(fila[26].trim());

                          // Estado
                          String codigoEstado = fila[2].trim();
                          if(!codigoEstado.isEmpty()){
                             CatEstadoEntidad estado;
                             estado = cacheEstadoEntidad.get(codigoEstado);
                             oficina.setEstado(estado);
                          }

                          //fechas
                          Date fechaAlta = null;
                          String sfechaAlta = fila[12].trim();
                          if(!sfechaAlta.isEmpty()){
                            fechaAlta = formatoFecha.parse(sfechaAlta);
                            oficina.setFechaAltaOficial(fechaAlta);
                          }


                          Date fechaExtincion = null;
                          String sfechaExtincion = fila[13].trim();
                          if(!sfechaExtincion.isEmpty()){
                            fechaExtincion = formatoFecha.parse(sfechaExtincion);
                            oficina.setFechaExtincion(fechaExtincion);
                          }

                          Date fechaAnulacion = null;
                          String sfechaAnulacion = fila[14].trim();
                          if(!sfechaAnulacion.isEmpty()){
                            fechaAnulacion = formatoFecha.parse(sfechaAnulacion);
                            oficina.setFechaExtincion(fechaAnulacion);
                          }


                          //Localidad de la dirección
                          String codigoProv = fila[22].trim();
                          String codigoEntGeog = fila[24].trim();
                          CatProvincia provincia = null;
                          if( !codigoProv.isEmpty()){
                            provincia = cacheProvincia.get(new Long(codigoProv));
                          }
                          //log.info("CodProvincia");
                          CatEntidadGeografica entidadGeograficaD = null;
                          if( !codigoEntGeog.isEmpty()){
                            entidadGeograficaD = cacheEntidadGeografica.get(codigoEntGeog);
                          }

                          String codigoLocalidad = fila[23].trim();
                          if(!codigoLocalidad.isEmpty() && !codigoProv.isEmpty() && !codigoEntGeog.isEmpty()){
                            CatLocalidadPK catLocalidadPKD = new CatLocalidadPK(new Long(codigoLocalidad), provincia, entidadGeograficaD);
                            CatLocalidad localidadD;
                            localidadD = cacheLocalidad.get(catLocalidadPKD);
                            oficina.setLocalidad(localidadD);
                          }
                          String codigoNivelAdmin = fila[3].trim();
                          CatNivelAdministracion nivelAdministracion = null;
                          if(!codigoNivelAdmin.isEmpty()){
                            nivelAdministracion = cacheNivelAdministracion.get(new Long(codigoNivelAdmin));
                            oficina.setNivelAdministracion(nivelAdministracion);
                          }

                          //tipoOficina
                          String tipoOficina = fila[4].trim();
                          CatJerarquiaOficina jerarquiaOficina = null;
                          if(!tipoOficina.isEmpty()){
                            jerarquiaOficina = catJerarquiaOficinaEjb.findById(new Long(tipoOficina));
                            oficina.setTipoOficina(jerarquiaOficina);
                          }

                          //Tipo Via
                          String tipoVia = fila[15].trim();
                          CatTipoVia catTipoVia = null;
                          if(!tipoVia.isEmpty()){
                            catTipoVia = cacheTipoVia.get(new Long(tipoVia));
                            oficina.setTipoVia(catTipoVia);
                          }

                          if (existeix) {
                            oficina = oficinaEjb.merge(oficina);
                          } else {
                            oficina = oficinaEjb.persistReal(oficina);
                            existInBBDD.add(codigoOficina);
                          }

                          //Oficina Responsable
                          String codigoOfiResponsable = fila[7].trim();
                          Oficina ofiResponsable = null;
                          if(!codigoOfiResponsable.isEmpty()){

                            if (existInBBDD.contains(codigoOfiResponsable)){
                              ofiResponsable = oficinaEjb.findById(codigoOfiResponsable);
                            }
                            if(ofiResponsable == null){
                              ofiResponsable = oficinaVacia();
                              ofiResponsable.setCodigo(codigoOfiResponsable);

                              ofiResponsable = oficinaEjb.persist(ofiResponsable);
                              existInBBDD.add(codigoOfiResponsable);

                            }
                            oficina.setCodOfiResponsable(ofiResponsable);
                          }

                          oficinaEjb.merge(oficina);

                          oficinesCache.put(oficina.getCodigo(), oficina);
                      }catch(Exception e ){
                        log.error("Error  important oficines  " + e.getMessage(), e);
                      }
                      count ++;
                      if (count % 5000 == 0) {
                        end = System.currentTimeMillis();
                        log.info("Procesades 5000 Oficinas (" + (count - 5000)+ " - " + count 
                            + ") en " + Utils.formatElapsedTime(end - start));
                        start = end;
                        
                        log.info("            Cache[CountCache]: " + cacheUnidad.countCache);
                        log.info("            Cache[CountFindBy]: " + cacheUnidad.countFind);
                        log.info("            Cache[FindByTime]: " + Utils.formatElapsedTime(cacheUnidad.findByTime));
                        
                        cacheUnidad.countCache = 0;
                        cacheUnidad.countFind = 0;
                        cacheUnidad.findByTime = 0;
                        
                      }
                    }
                }

                // CONTACTOS
               if(Dir3caibConstantes.OFI_CONTACTO_OFI.equals(nombreFichero)){
                 reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                 while ((fila = reader.readNext()) != null) {
                    try{
                      ContactoOfi contacto = new ContactoOfi();

                      // Asociamos oficina
                      String sOficina = fila[0].trim();
                      if(!sOficina.isEmpty()){
                        Oficina oficina;
                        oficina = oficinesCache.get(sOficina);
                        if (oficina == null) {
                          oficina = oficinaEjb.findById(sOficina);
                        }
                        contacto.setOficina(oficina);
                      }

                      //Tipo contacto
                      String  stipoContacto = fila[1].trim();
                      if(!stipoContacto.isEmpty()){
                        CatTipoContacto tipoContacto;
                        tipoContacto = cacheTipoContacto.get(stipoContacto);
                        contacto.setTipoContacto(tipoContacto);
                      }

                      //Valor contacto
                      String valorContacto = fila[2].trim();
                      contacto.setValorContacto(valorContacto);
                      Boolean visibilidad = fila[3].equals("S")?true:false;
                      contacto.setVisibilidad(visibilidad);
 

                      contactoOfiEjb.persistReal(contacto);

                    } catch(Exception e){
                      log.error("Error important contactos: " + e.getMessage(), e);
                    }
                 }
              }

              //HISTORICOS OFI
              if(Dir3caibConstantes.OFI_HISTORICOS_OFI.equals(nombreFichero)){

                reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                while ((fila = reader.readNext()) != null) {
                    //Obtenemos codigo y miramos si ya existe en la BD

                  String codigoOficinaAnterior = fila[0];
                  String codigoOficinaUltima = fila[2];
                  Oficina oficinaUltima = null;
                  Oficina oficinaAnterior = null;
                    try{

                      if(!codigoOficinaUltima.isEmpty()){
                        oficinaUltima = oficinesCache.get(codigoOficinaUltima);
                        if (oficinaUltima == null){
                          oficinaUltima = oficinaEjb.findById(codigoOficinaUltima);
                        }
                      }
                      if(!codigoOficinaAnterior.isEmpty()){
                        oficinaAnterior = oficinesCache.get(codigoOficinaAnterior);
                        if (oficinaAnterior == null){
                          oficinaAnterior = oficinaEjb.findById(codigoOficinaAnterior);
                        }
                      }
                      
                      if (oficinaAnterior == null) {
                        throw new Exception();
                      }

                      //Obtenemos los historicos de la oficinaAnterior y añadimos la oficinaUltima
                      Set<Oficina> historicosAnterior = oficinaAnterior.getHistoricosOfi();
                      if (historicosAnterior == null) {
                        historicosAnterior = new HashSet<Oficina>();
                        oficinaAnterior.setHistoricosOfi(historicosAnterior);
                      }
                      
                      if (oficinaUltima == null) {
                        throw new Exception();
                      }
                      historicosAnterior.add(oficinaUltima);
                      
                      oficinaEjb.persist(oficinaAnterior);

                    } catch(Exception e){
                      log.error("=======================================");
                      log.error("codigoOficinaAnterior: " + codigoOficinaAnterior);
                      log.error("oficinaAnterior: " + oficinaAnterior);
                      log.error("codigoOficinaUltima: " + codigoOficinaUltima);
                      log.error("String oficinaUltima: " + oficinaUltima);
                      log.error("Error important Historicos-Oficines " + e.getMessage());
                      StackTraceElement[] stack = e.getStackTrace();
                      int maxLines = (stack.length > 4) ? 5 : stack.length;
                      for (int n = 0; n < maxLines; n++) {
                        log.error(stack[n].toString());
                      }
                    }
                  }
              }

              // Relaciones organizativas
              if(Dir3caibConstantes.OFI_RELACIONES_ORGANIZATIVAS_OFI.equals(nombreFichero)){
                reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                long s = System.currentTimeMillis();
                long findby = 0;
                int c = 1;
                CacheUnidadOficina cache = new CacheUnidadOficina(relOrgOfiEjb.getUnidadesOficinas());
                while ((fila = reader.readNext()) != null) {
                    //Obtenemos codigo y miramos si ya existe en la BD
                    try{
                      String sOficina = fila[0].trim();
                      String sUnidad = fila[2].trim();
                      if(!sOficina.isEmpty() && !sUnidad.isEmpty()){
                        /*
                        Oficina oficina;

                        oficina = oficinesCache.get(sOficina);
                        
                        if (oficina == null) {
                          oficina = oficinaEjb.findById(sOficina);
                        }
                        
                        Unidad  unidad;
                        unidad = cacheUnidad.get(sUnidad);

                        RelacionOrganizativaOfiPK relacionOrganizativaOfiPK = new RelacionOrganizativaOfiPK();
                        relacionOrganizativaOfiPK.setOficina(oficina);
                        relacionOrganizativaOfiPK.setUnidad(unidad);
                        */

                        RelacionOrganizativaOfi relacionOrganizativaOfi;
                        if (cache.existsUnidadOficina(sUnidad, sOficina)){
                          long s1 = System.currentTimeMillis();
                          //relacionOrganizativaOfi = relOrgOfiEjb.findById(relacionOrganizativaOfiPK);
                          relacionOrganizativaOfi = relOrgOfiEjb.findByPKs(sUnidad, sOficina);
                          findby = findby + (System.currentTimeMillis() - s1);
                        } else {
                          relacionOrganizativaOfi = null;
                        }
                        if(relacionOrganizativaOfi == null){
                          relacionOrganizativaOfi = new RelacionOrganizativaOfi();
                          
                          Oficina oficina;
                          oficina = oficinesCache.get(sOficina);
                          if (oficina == null) {
                            oficina = oficinaEjb.findById(sOficina);
                          }
                          
                          Unidad  unidad;
                          unidad = cacheUnidad.get(sUnidad);
                          
                          relacionOrganizativaOfi.setOficina(oficina);
                          relacionOrganizativaOfi.setUnidad(unidad);
                        }
                        String codigoEstado = fila[4].trim();
                        CatEstadoEntidad estado = null;
                        if(!codigoEstado.isEmpty()){
                          estado = cacheEstadoEntidad.get(codigoEstado);
                          relacionOrganizativaOfi.setEstado(estado);
                        }


                        relOrgOfiEjb.persist(relacionOrganizativaOfi);

                        
                        c++;
                        if (c % 100 == 0) {
                          log.info("Procesades OFI_RELACIONES_ORGANIZATIVAS_OFI (" + c + ") "
                            + " en " + Utils.formatElapsedTime(System.currentTimeMillis() - s));
                          
                          log.info("           * Time FIND = " + Utils.formatElapsedTime(findby));
                          s = System.currentTimeMillis();
                          findby = 0;
                        }
                        
                      }

                    }catch(Exception e){
                      log.error("Error important relaciones organizativas " + e.getMessage(), e);
                    }
                  }
              }

              // Relaciones SIR Oficina
              if(Dir3caibConstantes.OFI_RELACIONES_SIROFI.equals(nombreFichero)){
                int c = 0;
                long s = System.currentTimeMillis();
                long findby = 0;
                CacheUnidadOficina cache = new CacheUnidadOficina(relSirOfiEjb.getUnidadesOficinas());
                reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                while ((fila = reader.readNext()) != null) {
                    //Obtenemos codigo y miramos si ya existe en la BD
                    try{
                      String sOficina = fila[0].trim();
                      String sUnidad = fila[2].trim();
                      if(!sOficina.isEmpty() && !sUnidad.isEmpty()){
                        /*
                        Oficina oficina;
                        oficina = oficinesCache.get(sOficina);
                        
                        if (oficina == null) {
                          oficina = oficinaEjb.findById(sOficina);
                        }
                        Unidad  unidad;
                        unidad = cacheUnidad.get(sUnidad);
                        RelacionSirOfiPK relacionSirOfiPK = new RelacionSirOfiPK();
                        relacionSirOfiPK.setOficina(oficina);
                        relacionSirOfiPK.setUnidad(unidad);
                        */

                        RelacionSirOfi relacionSirOfi = null;
                        if (cache.existsUnidadOficina(sUnidad, sOficina)){
                          long s1 = System.currentTimeMillis();
                          //relacionSirOfi = relSirOfiEjb.findById***(relacionSirOfiPK);
                          relacionSirOfi = relSirOfiEjb.findByPKs(sUnidad, sOficina);                          
                          findby = findby + (System.currentTimeMillis() - s1);
                        }
                        if(relacionSirOfi == null){
                          relacionSirOfi = new RelacionSirOfi();
                          
                          Oficina oficina;
                          oficina = oficinesCache.get(sOficina);
                          
                          if (oficina == null) {
                            oficina = oficinaEjb.findById(sOficina);
                          }
                          Unidad  unidad;
                          unidad = cacheUnidad.get(sUnidad);
                          
                          
                          relacionSirOfi.setOficina(oficina);
                          relacionSirOfi.setUnidad(unidad);
                        }
                        String codigoEstado = fila[4].trim();
                        CatEstadoEntidad estado = null;
                        if(!codigoEstado.isEmpty()){
                          estado = cacheEstadoEntidad.get(codigoEstado);
                          relacionSirOfi.setEstado(estado);
                        }

                        relSirOfiEjb.persist(relacionSirOfi);


                        c++;
                        if (c % 100 == 0) {
                          log.info("   RelacionesSIROFI:  (" + c + ") "
                            + " en " + Utils.formatElapsedTime(System.currentTimeMillis() - s));
                          
                          log.info("           * Time FIND = " + Utils.formatElapsedTime(findby));
                          s = System.currentTimeMillis();
                          findby = 0;
                        }
                        
                      }
                    } catch(Exception e){
                      log.error("Error important RELACIONES_SIROFI: " + e.getMessage(), e);
                    }
                }
              }

             //Servicios
             if(Dir3caibConstantes.OFI_SERVICIOS_OFI.equals(nombreFichero)){
               reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
               while ((fila = reader.readNext()) != null) {
                  //Obtenemos codigo y miramos si ya existe en la BD
                  try{
                    String codigoOficina = fila[0].trim();
                    String codigoServicio = fila[1].trim();
                    if(!codigoOficina.isEmpty() && !codigoServicio.isEmpty()){
                      Long codServicio = new Long(codigoServicio);

                      Oficina oficina;
                      oficina = oficinesCache.get(codigoOficina);
                      
                      if (oficina == null) {
                        oficina = oficinaEjb.findById(codigoOficina);
                      }
                      //  log.info("obtenemos oficina");

                      Servicio servicio;

                      servicio = servicioEjb.findById(codServicio);

                      //  log.info("obtenemos servicio");
                      if(servicio == null){
                        servicio = new Servicio();
                        servicio.setCodServicio(codServicio);
                        servicio.setDescServicio(fila[2].trim());
                       // log.info("Antes del persist servicio");
                        servicioEjb.persist(servicio);

                      }
                      Set<Servicio> servicios = oficina.getServicios();
                      if (servicios == null) {
                        servicios = new HashSet<Servicio>();
                        oficina.setServicios(servicios);
                      }
                      servicios.add(servicio);

                      oficinaEjb.merge(oficina);

                      // log.info("Despues del merge oficina");
                    }
                  }catch(Exception e){
                    log.error(" Error EnOFI_SERVICIOS_OFI " + e.getMessage(), e);
                  }
               }
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
        }finally{
             if(reader != null){
                 try {
                     reader.close();
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
            }
        }

        descarga = descargaEjb.findByTipo(Dir3caibConstantes.OFICINA);
        if (descarga == null) {
          descarga = new Descarga();
          descarga.setTipo(Dir3caibConstantes.OFICINA);
        }
        descarga.setFechaImportacion(hoy);
        descargaEjb.merge(descarga);

        
        results.setDescarga(descarga);

     }
     results.setExistentes(existentes);
     
     System.gc();
     
     return results;
  }

  public Oficina oficinaVacia(){
    Oficina oficina = new Oficina();
    oficina.setDenominacion(new String());

    return oficina;
  }

   /*
   * Método que se encarga de obtener los archivos de las oficinas a través de WS
   * @param request
   * @param fechaInicio
   * @param fechaFin
   */
  @Override
  public void descargarOficinasWS(Date fechaInicio, Date fechaFin) throws Exception {

      byte[] buffer = new byte[1024];


     //Definimos el formato de la fecha para las descargas de los WS.
      log.info( "Fecha Inicio " + fechaInicio);
      log.info( "Fecha Fin " + fechaFin);

      // Guardaremos la fecha de la ultima descarga
      Descarga descarga = new Descarga();
      descarga.setTipo(Dir3caibConstantes.OFICINA);

      //guardamos todas las fechas de la descarga
      if(fechaInicio!= null){
        descarga.setFechaInicio(fechaInicio);
      }
      if(fechaFin!=null){
        descarga.setFechaFin(fechaFin);
      }

      // Si las fechas estan vacias, las de descarga tenemos que fijar la fecha de hoy.
      Date hoy = new Date();
      //Si no indican fechas es una sincro desde el principio de los tiempos.
      /*if(fechaInicio == null){
        descarga.setFechaInicio(hoy);
      }*/

      if(fechaFin == null){
        descarga.setFechaFin(hoy);
      }

      descarga = descargaEjb.persist(descarga);
      log.info("DESCARGA FECHA INICIO " + descarga.getFechaInicio());

      try {

        //Obtenemos las diferentes rutas para invocar a los WS y almacenar la información obtenida
        String usuario = Configuracio.getDir3WsUser();
        String password = Configuracio.getDir3WsPassword();
        String ruta = Configuracio.getArchivosPath();

        String rutaOficinas = Configuracio.getOficinasPath(descarga.getCodigo());

        String endPoint = Configuracio.getOficinaEndPoint();

        SD02OFDescargaOficinasService oficinasService = new SD02OFDescargaOficinasService(new URL(endPoint + "?wsdl"));
        SD02OFDescargaOficinas service = oficinasService.getSD02OFDescargaOficinas();
        Map<String, Object> reqContext = ((BindingProvider) service).getRequestContext();
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);

        // Establecemos los parametros necesarios para el WS
        OficinasWs parametros = new OficinasWs();
        parametros.setUsuario(usuario);
        parametros.setClave(password);
        parametros.setFormatoFichero(FormatoFichero.CSV);
        parametros.setTipoConsulta(TipoConsultaOF.COMPLETO);

        // definimos fechas
        if (fechaInicio != null) {
          parametros.setFechaInicio(formatoFecha.format(fechaInicio));
        }
        if (fechaFin != null) {
          parametros.setFechaFin(formatoFecha.format(fechaFin));
        }

        // Invocamos el WS
        RespuestaWS respuesta = service.exportar(parametros);
        Base64 decoder = new Base64();

        log.info("Codigo: " + respuesta.getCodigo());
        log.info("Descripcion: " + respuesta.getDescripcion());

        // Realizamos una copia del archivo zip de la ultima descarga
        String archivoOficinaZip = ruta + Dir3caibConstantes.OFICINAS_ARCHIVO_ZIP + descarga.getCodigo() + ".zip";
        File file = new File(archivoOficinaZip);


        //Guardamos el zip devuelto por el WS en el directorio.
        FileUtils.writeByteArrayToFile(file, decoder.decode(respuesta.getFichero()));

        // Se crea el directorio para el catálogo
        File dir = new File(rutaOficinas);
        if (!dir.exists()) {
          if (!dir.mkdirs()) {
            //Borramos la descarga creada previamente.
            descargaEjb.deleteByTipo(Dir3caibConstantes.OFICINA);
            log.error(" No se ha podido crear el directorio");
          }
        }

        //Descomprimir el archivo
        ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoOficinaZip));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
          String fileName = zipEntry.getName();
          File newFile = new File(rutaOficinas + fileName);
          log.info("Fichero descomprimido: " + newFile.getAbsoluteFile());

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


      }catch(Exception e){
        descargaEjb.deleteByTipo(Dir3caibConstantes.OFICINA);
        throw new Exception();
      }


  }

  /* Tarea que en un primer paso descarga los archivos csv de las oficinas y posteriormente importa el contenido
   *  en la base de datos, de esta manera realiza el proceso de sincronizacion con Madrid en un sólo
   *  proceso
   *  */
  @TransactionTimeout(value=13600)
   public void importarOficinasTask(){

       try {
            //Obtenemos las fechas entre las que hay que hacer la descarga

            // obtenemos los datos de la última descarga
            Descarga ultimaDescarga = descargaEjb.findByTipo(Dir3caibConstantes.OFICINA);
            Date fechaInicio =ultimaDescarga.getFechaFin(); // fecha de la ultima descarga


            // obtenemos la fecha de hoy
            Date fechaFin = new Date();

            // Obtiene los archivos csv via WS
            descargarOficinasWS(fechaInicio, fechaFin);


            // importamos las oficinas a la bd.
            importarOficinas(true);
       } catch(Exception e){
         log.error("Error important Oficines: " + e.getMessage(),  e);
       }
   }

  
  
  private int getRequiredUnidades(List<List<String>> all, int size) throws Exception  {
    FileInputStream is1 = null;
    CSVReader reader = null;
    
    Set<String> allCodes = new HashSet<String>();
    
    
    List<String> codigosUnidad = new ArrayList<String>();
    all.add(codigosUnidad);
  
    int count = 0;
    int allCount = 0;
    try {
      Descarga descarga = descargaEjb.findByTipo(Dir3caibConstantes.OFICINA);
      File file = new File(Configuracio.getOficinasPath(descarga.getCodigo()),Dir3caibConstantes.OFI_OFICINAS);
      is1 = new FileInputStream(file);
      BufferedReader is = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
      reader = new CSVReader(is,';');
      
      
      
       // Leemos el contenido y lo guardamos en un List
       
       String[] fila;
       
       reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
       
       while ((fila = reader.readNext()) != null) {
          //Obtenemos codigo y miramos si ya existe en la BD
         
         String codUOResponsable = fila[5].trim();
         
         
         if (!allCodes.contains(codUOResponsable)) {
         
           allCount++;
           count++;
           if (count > 500) {
             codigosUnidad = new ArrayList<String>();
             all.add(codigosUnidad);
             count = 0;
           }
           
           codigosUnidad.add(codUOResponsable);
           allCodes.add(codUOResponsable);
         }
       }
       
       return allCount;
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (Exception e) {          
        } 
      }
      if (is1 != null) {
        try {
          is1.close();
        } catch (Exception e) {          
        } 
      }
    }
  }
  

}

