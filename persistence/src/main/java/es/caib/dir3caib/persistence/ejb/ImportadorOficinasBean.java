package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
import es.caib.dir3caib.utils.Configuracio;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.BindingProvider;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created 10/03/14 14:38
 * <p/>
 * Clase que permite importar las oficinas desde el directorio común. En un primer paso
 * se descargan los archivos y posteriormente se importa el contenido en la BD.
 *
 * @author mgonzalez
 * @author anadal (Cache & EJB)
 * @author anadal (Eliminar PKs multiples)
 */
@Stateless(name = "ImportadorOficinasEJB")
@SecurityDomain("seycon")
@RunAs(Dir3caibConstantes.DIR_ADMIN)
@PermitAll
public class ImportadorOficinasBean implements ImportadorOficinasLocal {
    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    protected UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    protected OficinaLocal oficinaEjb;

    @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
    protected CatEntidadGeograficaLocal catEntidadGeograficaEjb;

    @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
    protected CatEstadoEntidadLocal catEstadoEntidadEjb;

    @EJB(mappedName = "dir3caib/CatJerarquiaOficinaEJB/local")
    protected CatJerarquiaOficinaLocal catJerarquiaOficinaEjb;

    @EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
    protected CatNivelAdministracionLocal catNivelAdministracionEjb;

    @EJB(mappedName = "dir3caib/CatPaisEJB/local")
    protected CatPaisLocal catPaisEjb;

    @EJB(mappedName = "dir3caib/CatTipoContactoEJB/local")
    protected CatTipoContactoLocal catTipoContactoEjb;

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

    @PersistenceContext
    private EntityManager em;

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


        public int countFind = 0;
        public int countCache = 0;
        public long findByTime = 0;

        private final UnidadLocal unidadEjb;

        Map<String, Unidad> cacheUnidad = new TreeMap<String, Unidad>();

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

            for (List<String> ids : unidadesRequeridas) {
                long start2 = System.currentTimeMillis();


                if (log.isDebugEnabled()) {
                    log.debug(" ids.size = " + ids.size());
                }
                unidades = unidadEjb.getListByIds(ids);
                if (log.isDebugEnabled()) {
                    log.info(" getListByIds(ids).size = " + unidades.size());
                }

                for (Unidad ca : unidades) {
                    cacheUnidad.put(ca.getCodigo(), ca);
                    count++;
                }
                long end2 = System.currentTimeMillis();

                log.info(" Cache de Unidades " + count + " / " + total + "   -->   " + Utils.formatElapsedTime(end2 - start2));

            }

            log.info(" Cache Of Unidades. Total = " + count);

        }


        public Unidad get(String codigo) throws Exception {

            Unidad unidad = cacheUnidad.get(codigo);

            if (unidad == null) {
                long start = System.currentTimeMillis();
                unidad = this.unidadEjb.getReference(codigo);
                findByTime = findByTime + (System.currentTimeMillis() - start);
                cacheUnidad.put(codigo, unidad);
                countFind++;
            } else {
                countCache++;
            }
            return unidad;

        }


    }


    /**
     * Método que importa el contenido de los archivos de las oficinas y sus relaciones descargados previamente a través
     * de los WS.
     * Añadido batch processing, con el entity Manager y el batchSize para mejorar el rendimiento.
     * @param isUpdate booleano que indica si la llamada es una sincronización(actualizacion)
     */
    @Override
    @TransactionTimeout(value = 54000)
    public ResultadosImportacion importarOficinas(boolean isUpdate) throws Exception {

        log.info("");
        log.info("Inicio importación Oficinas");

        int batchSize = 30;
        Date hoy = new Date();

        System.gc();

        ResultadosImportacion results = new ResultadosImportacion();

        List<String> procesados = results.getProcesados();
        List<String> inexistentes = results.getInexistentes();

    
    /*  CACHES */

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
        log.debug("Inicialitzat Cache de Unidades en " + Utils.formatElapsedTime(end - start));

        start = System.currentTimeMillis();
        log.debug("Inicialitzant Varies Caches per Importar Oficinas ...");


        Map<String, CatTipoContacto> cacheTipoContacto = new TreeMap<String, CatTipoContacto>();
        for (CatTipoContacto ca : catTipoContactoEjb.getAll()) {
            cacheTipoContacto.put(ca.getCodigoTipoContacto(), ca);
        }
        log.debug(" TipoContacto : " + cacheTipoContacto.size());

        Map<Long, CatTipoVia> cacheTipoVia = new TreeMap<Long, CatTipoVia>();
        for (CatTipoVia ca : catTipoViaEjb.getAll()) {
            cacheTipoVia.put(ca.getCodigoTipoVia(), ca);
        }
        log.debug(" TipoVias : " + cacheTipoVia.size());


        Map<Long, CatNivelAdministracion> cacheNivelAdministracion = new TreeMap<Long, CatNivelAdministracion>();
        for (CatNivelAdministracion na : catNivelAdministracionEjb.getAll()) {
            cacheNivelAdministracion.put(na.getCodigoNivelAdministracion(), na);
        }
        log.debug(" NivelAdministracion : " + cacheNivelAdministracion.size());

        Map<Long, CatProvincia> cacheProvincia = new TreeMap<Long, CatProvincia>();
        for (CatProvincia ca : catProvinciaEjb.getAll()) {
            cacheProvincia.put(ca.getCodigoProvincia(), ca);
        }
        log.debug(" Provincia: " + cacheProvincia.size());

        Map<Long, CatComunidadAutonoma> cacheComunidadAutonoma = new TreeMap<Long, CatComunidadAutonoma>();
        for (CatComunidadAutonoma ca : catComunidadAutonomaEjb.getAll()) {
            cacheComunidadAutonoma.put(ca.getCodigoComunidad(), ca);
        }
        log.debug(" Comunidad Autonoma : " + cacheComunidadAutonoma.size());

        Map<Long, CatPais> cachePais = new TreeMap<Long, CatPais>();
        for (CatPais ca : catPaisEjb.getAll()) {
            cachePais.put(ca.getCodigoPais(), ca);
        }
        log.debug(" Pais : " + cachePais.size());


        Map<String, CatEstadoEntidad> cacheEstadoEntidad = new TreeMap<String, CatEstadoEntidad>();
        for (CatEstadoEntidad ca : catEstadoEntidadEjb.getAll()) {
            cacheEstadoEntidad.put(ca.getCodigoEstadoEntidad(), ca);
        }
        log.debug(" Estado Entidad : " + cacheEstadoEntidad.size());


        Map<String, CatEntidadGeografica> cacheEntidadGeografica = new TreeMap<String, CatEntidadGeografica>();
        for (CatEntidadGeografica ca : catEntidadGeograficaEjb.getAll()) {
            cacheEntidadGeografica.put(ca.getCodigoEntidadGeografica(), ca);
        }
        log.debug(" EntidadGeografica : " + cacheEntidadGeografica.size());

        Map<CatLocalidadPK, CatLocalidad> cacheLocalidad = new HashMap<CatLocalidadPK, CatLocalidad>();
        for (CatLocalidad ca : catLocalidadEjb.getAll()) {
            CatLocalidadPK catLocalidadPK = new CatLocalidadPK(ca.getCodigoLocalidad(), ca.getProvincia(), ca.getEntidadGeografica());
            cacheLocalidad.put(catLocalidadPK, ca);
        }
        log.debug(" Localidad: " + cacheLocalidad.size());


        end = System.currentTimeMillis();
        log.info("Inicialitzades Varies Caches per Importar Oficinas en " + Utils.formatElapsedTime(end - start));


        Set<String> existInBBDD = new TreeSet<String>();
        existInBBDD.addAll(oficinaEjb.getAllCodigos());


        Map<String, Oficina> oficinesCache = new TreeMap<String, Oficina>();


        // Obtenemos el listado de ficheros que hay dentro del directorio indicado que se
        // corresponde con la descarga hecha previamente
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.OFICINA);
        File f = new File(Configuracio.getOficinasPath(descarga.getCodigo()));
        ArrayList<String> existentes = new ArrayList<String>(Arrays.asList(f.list()));


        // Buscamos los posibles ficheros de oficinas que pueden existir en el directorio
        for (int i = 0; i < Dir3caibConstantes.OFI_FICHEROS.length; i++) {
            String fichero = Dir3caibConstantes.OFI_FICHEROS[i];
            CSVReader reader = null;

            log.info("");
            log.info("Inicio fichero: " + fichero);
            log.info("------------------------------------");


            try {
                // Obtenemos el fichero del sistema de archivos
                FileInputStream is1 = new FileInputStream(new File(Configuracio.getOficinasPath(descarga.getCodigo()), fichero));
                BufferedReader is = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
                reader = new CSVReader(is, ';');
                if (reader != null) {
                    // Leemos el contenido y lo guardamos en un List
                    String nombreFichero = fichero;
                    String[] fila;
                    if (Dir3caibConstantes.OFI_OFICINAS.equals(nombreFichero)) { //Procesamos el fichero Oficinas.csv
                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        int count = 1;
                        while ((fila = reader.readNext()) != null) {
                            //Obtenemos codigo y miramos si ya existe en la BD
                            try {
                                //  Miramos si existe ya en la BD
                                String codigoOficina = fila[0];
                                Oficina oficina = null;
                                boolean existeix;

                                if (existInBBDD.contains(codigoOficina)) {
                                    oficina = oficinaEjb.findById(codigoOficina);
                                    existeix = true;
                                }else{
                                    oficina = new Oficina();
                                    oficina.setCodigo(codigoOficina);
                                    existeix = false;
                                }

                                //Fecha Importacion
                                oficina.setFechaImportacion(hoy);

                                // Comunidad Autonoma
                                String codigoComunidadAutonoma = fila[21].trim();
                                if (!codigoComunidadAutonoma.isEmpty()) {
                                    oficina.setCodComunidad(cacheComunidadAutonoma.get(new Long(codigoComunidadAutonoma)));
                                } else {
                                    oficina.setCodComunidad(null);
                                }

                                // Pais
                                String codigoPais = fila[20].trim();
                                if (!codigoPais.isEmpty()) {
                                    oficina.setCodPais(cachePais.get(new Long(codigoPais)));
                                } else {
                                    oficina.setCodPais(null);
                                }

                                // Codigo Postal
                                oficina.setCodPostal(fila[19].trim());

                                //Unidad organica responsable
                                String codUOResponsable = fila[5].trim();
                                if (!codUOResponsable.isEmpty()) {
                                    oficina.setCodUoResponsable(cacheUnidad.get(codUOResponsable));
                                } else {
                                    oficina.setCodUoResponsable(null);
                                }

                                // atributos directos
                                oficina.setComplemento(fila[18].trim());
                                oficina.setDenominacion(fila[1].trim());
                                oficina.setDiasSinHabiles(fila[10].trim());
                                oficina.setDirExtranjera(fila[25].trim());
                                oficina.setDireccionObservaciones(fila[27].trim());
                                oficina.setHorarioAtencion(fila[9].trim());
                                oficina.setLocExtranjera(fila[26].trim());
                                oficina.setNombreVia(fila[16].trim());
                                oficina.setNumVia(fila[17].trim());
                                oficina.setCodPostal(fila[19].trim());

                                // Estado
                                String codigoEstado = fila[2].trim();
                                if (!codigoEstado.isEmpty()) {
                                    oficina.setEstado(cacheEstadoEntidad.get(codigoEstado));
                                } else {
                                    oficina.setEstado(null);
                                }

                                //fechas
                                String sfechaAlta = fila[12].trim();
                                if (!sfechaAlta.isEmpty()) {

                                    oficina.setFechaAltaOficial(formatoFecha.parse(sfechaAlta));
                                } else {
                                    oficina.setFechaAltaOficial(null);
                                }

                                String sfechaExtincion = fila[13].trim();
                                if (!sfechaExtincion.isEmpty()) {
                                    oficina.setFechaExtincion(formatoFecha.parse(sfechaExtincion));
                                } else {
                                    oficina.setFechaExtincion(null);
                                }

                                String sfechaAnulacion = fila[14].trim();
                                if (!sfechaAnulacion.isEmpty()) {
                                    oficina.setFechaExtincion(formatoFecha.parse(sfechaAnulacion));
                                } else {
                                    oficina.setFechaAnulacion(null);
                                }


                                //Localidad de la dirección
                                String codigoProv = fila[22].trim();
                                String codigoEntGeog = fila[24].trim();
                                CatProvincia provincia = null;
                                if (!codigoProv.isEmpty()) {
                                    provincia = cacheProvincia.get(new Long(codigoProv));
                                }

                                CatEntidadGeografica entidadGeograficaD = null;
                                if (!codigoEntGeog.isEmpty()) {
                                    entidadGeograficaD = cacheEntidadGeografica.get(codigoEntGeog);
                                }

                                String codigoLocalidad = fila[23].trim();
                                if (!codigoLocalidad.isEmpty() && !codigoProv.isEmpty() && !codigoEntGeog.isEmpty()) {
                                    CatLocalidadPK catLocalidadPKD = new CatLocalidadPK(new Long(codigoLocalidad), provincia, entidadGeograficaD);
                                    CatLocalidad localidadD;
                                    localidadD = cacheLocalidad.get(catLocalidadPKD);
                                    oficina.setLocalidad(localidadD);
                                } else {
                                    oficina.setLocalidad(null);
                                }

                                String codigoNivelAdmin = fila[3].trim();
                                if (!codigoNivelAdmin.isEmpty()) {
                                    oficina.setNivelAdministracion(cacheNivelAdministracion.get(new Long(codigoNivelAdmin)));
                                } else {
                                    oficina.setNivelAdministracion(null);
                                }

                                //tipoOficina
                                String tipoOficina = fila[4].trim();
                                if (!tipoOficina.isEmpty()) {
                                    oficina.setTipoOficina(catJerarquiaOficinaEjb.getReference(new Long(tipoOficina)));
                                } else {
                                    oficina.setTipoOficina(null);
                                }

                                //Tipo Via
                                String tipoVia = fila[15].trim();
                                if (!tipoVia.isEmpty()) {
                                    oficina.setTipoVia(cacheTipoVia.get(new Long(tipoVia)));
                                } else {
                                    oficina.setTipoVia(null);
                                }

                                //Pruebas marilen
                                oficina.setServicios(null);

                                //Oficina Responsable
                                String codigoOfiResponsable = fila[7].trim();
                                Oficina ofiResponsable = null;
                                if (!codigoOfiResponsable.isEmpty()) {

                                    if (existInBBDD.contains(codigoOfiResponsable)) {
                                        ofiResponsable = oficinaEjb.getReference(codigoOfiResponsable);
                                    }
                                    if (ofiResponsable == null) {
                                        ofiResponsable = oficinaVacia();
                                        ofiResponsable.setCodigo(codigoOfiResponsable);

                                        ofiResponsable = oficinaEjb.persistReal(ofiResponsable);
                                        existInBBDD.add(codigoOfiResponsable);

                                    }
                                    oficina.setCodOfiResponsable(ofiResponsable);
                                } else { // no viene ningun código de oficina responsable, pero puede tener de antes que hay que anular
                                    oficina.setCodOfiResponsable(null);
                                }

                                if (existeix) {
                                    contactoOfiEjb.deleteByOficina(oficina.getCodigo());
                                    oficina = em.merge(oficina);

                                } else {
                                    em.persist(oficina);
                                    existInBBDD.add(codigoOficina);
                                }

                                // Cada batchSize hacemos flush y clear, esto permite que el proceso sea más rapido y libere memoria.
                                if (count % batchSize == 0) {
                                    em.flush();
                                    em.clear();
                                }

                                oficinesCache.put(oficina.getCodigo(), oficina);
                            } catch (Exception e) {
                                log.error("Error  important oficines  " + e.getMessage(), e);
                            }
                            count++;
                            if (count % 5000 == 0) {
                                end = System.currentTimeMillis();
                                log.info("Procesades 5000 Oficinas (" + (count - 5000) + " - " + count
                                        + ") en " + Utils.formatElapsedTime(end - start));
                                start = end;

                                log.debug("            Cache[CountCache]: " + cacheUnidad.countCache);
                                log.debug("            Cache[CountFindBy]: " + cacheUnidad.countFind);
                                log.debug("            Cache[FindByTime]: " + Utils.formatElapsedTime(cacheUnidad.findByTime));

                                cacheUnidad.countCache = 0;
                                cacheUnidad.countFind = 0;
                                cacheUnidad.findByTime = 0;

                            }
                        }
                    }


                    // CONTACTOS
                    importarContactos(nombreFichero, reader, cacheTipoContacto);

                    //HISTORICOS OFI
                    importarHistoricos(nombreFichero, reader, oficinesCache);

                    // Relaciones organizativas
                    importarRelacionesOrganizativas(nombreFichero, reader);

                    // Relaciones SIR Oficina
                    importarRelacionesSir(nombreFichero, reader);

                    //Servicios
                    importarServicios(nombreFichero, reader);

                    log.info("Fin importar fichero: " + nombreFichero);

                    procesados.add(fichero);
                }
                reader.close();


            } catch (FileNotFoundException ex) {
                inexistentes.add(fichero);
                log.warn("Fichero no encontrado " + fichero);
            } catch (IOException io) {
                io.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if (procesados.size() > 0) {
                descarga.setFechaImportacion(hoy);
                descargaEjb.merge(descarga);

            }
            results.setDescarga(descarga);

        }
        results.setExistentes(existentes);

        System.gc();

        return results;
    }

    public Oficina oficinaVacia() {
        Oficina oficina = new Oficina();
        oficina.setDenominacion(new String());

        return oficina;
    }

    /**
     * Método que importa los históricos de las oficinas. Procesa el fichero HistoricosOFI.csv
     *
     * @param nombreFichero
     * @param reader
     * @param oficinesCache cache de las oficinas para un procesado más óptimo
     * @throws Exception
     */
    private void importarHistoricos(String nombreFichero, CSVReader reader, Map<String, Oficina> oficinesCache) throws Exception {
        String[] fila;
        if (Dir3caibConstantes.OFI_HISTORICOS_OFI.equals(nombreFichero)) {

            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            while ((fila = reader.readNext()) != null) {
                //Obtenemos el histórico
                String codigoOficinaAnterior = fila[0]; //codigo de la oficina que es sustituida
                String codigoOficinaUltima = fila[2]; // código de la oficina que sustituye
                Oficina oficinaUltima = null;
                Oficina oficinaAnterior = null;
                try {
                    // si existe la oficina la cogemos de cache, si no de la bd
                    if (!codigoOficinaUltima.isEmpty()) {
                        oficinaUltima = oficinesCache.get(codigoOficinaUltima);
                        if (oficinaUltima == null) {
                            oficinaUltima = oficinaEjb.getReference(codigoOficinaUltima);
                        }
                    }
                    // si existe la oficina la cogemos de cache, si no de la bd
                    if (!codigoOficinaAnterior.isEmpty()) {
                        oficinaAnterior = oficinesCache.get(codigoOficinaAnterior);
                        if (oficinaAnterior == null) {
                            oficinaAnterior = oficinaEjb.findById(codigoOficinaAnterior);
                        }
                    }

                    //si no existe la oficina que es sustituida hay un error.
                    if (oficinaAnterior == null) {
                        throw new Exception();
                    }

                    //Obtenemos los historicos de la oficinaAnterior y añadimos la oficinaUltima
                    Set<Oficina> historicosAnterior = oficinaAnterior.getHistoricosOfi();
                    if (historicosAnterior == null) {
                        historicosAnterior = new HashSet<Oficina>();
                        oficinaAnterior.setHistoricosOfi(historicosAnterior);
                    }

                    //si no existe la oficina que sustituye hay un error.
                    if (oficinaUltima == null) {
                        throw new Exception();
                    }
                    historicosAnterior.add(oficinaUltima);

                    oficinaEjb.merge(oficinaAnterior);

                } catch (Exception e) {
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
    }

    /**
     * Método que importa los contactos de una Oficina. Procesa el fichero ContactoOFI.csv
     * (Solo vienen los que estan visibles en el momento de la descarga)
     *
     * @param nombreFichero
     * @param reader
     * @param cacheTipoContacto
     * @throws Exception
     */
    private void importarContactos(String nombreFichero, CSVReader reader, Map<String, CatTipoContacto> cacheTipoContacto) throws Exception {
        String[] fila;
        if (Dir3caibConstantes.OFI_CONTACTO_OFI.equals(nombreFichero)) {
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            while ((fila = reader.readNext()) != null) {
                try {
                    ContactoOfi contacto = new ContactoOfi();

                    // Asociamos oficina
                    String sOficina = fila[0].trim();
                    if (!sOficina.isEmpty()) {
                        Oficina oficina = oficinaEjb.getReference(sOficina);
                        contacto.setOficina(oficina);
                    } else {
                        contacto.setOficina(null);
                    }

                    //Tipo contacto
                    String stipoContacto = fila[1].trim();
                    if (!stipoContacto.isEmpty()) {
                        contacto.setTipoContacto(cacheTipoContacto.get(stipoContacto));
                    } else {
                        contacto.setTipoContacto(null);
                    }

                    //Valor contacto
                    String valorContacto = fila[2].trim();
                    contacto.setValorContacto(valorContacto);
                    boolean visibilidad = fila[3].trim().equals("1");
                    contacto.setVisibilidad(visibilidad);


                    contactoOfiEjb.persistReal(contacto);

                } catch (Exception e) {
                    log.error("Error important contactos: " + e.getMessage(), e);
                }
            }
        }

    }

    /**
     * Método que importa las relaciones Organizativas de las oficinas. Procesa el fichero RelacionesOrganizativasOFI.csv
     *
     * @param nombreFichero
     * @param reader
     * @throws Exception
     */
    private void importarRelacionesOrganizativas(String nombreFichero, CSVReader reader) throws Exception {
        // Relaciones organizativas
        if (Dir3caibConstantes.OFI_RELACIONES_ORGANIZATIVAS_OFI.equals(nombreFichero)) {
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            long s = System.currentTimeMillis();
            long findby = 0;
            int c = 1;
            String[] fila;
            int batchSize = 30;
            //Se monta una cache con los codigos (Unidad-Oficina)
            CacheUnidadOficina cache = new CacheUnidadOficina(relOrgOfiEjb.getUnidadesOficinas());
            while ((fila = reader.readNext()) != null) {
                //Obtenemos codigo y miramos si ya existe en la BD
                try {
                    String sOficina = fila[0].trim();
                    String sUnidad = fila[2].trim();
                    if (!sOficina.isEmpty() && !sUnidad.isEmpty()) {

                        boolean existeix;

                        RelacionOrganizativaOfi relacionOrganizativaOfi;
                        //Miramos si existe la relacionOrganizativa
                        if (cache.existsUnidadOficina(sUnidad, sOficina)) {
                            long s1 = System.currentTimeMillis();
                            relacionOrganizativaOfi = relOrgOfiEjb.findByPKs(sUnidad, sOficina);
                            existeix = true;
                            findby = findby + (System.currentTimeMillis() - s1);
                        } else {
                            relacionOrganizativaOfi = null;
                            existeix = false;
                        }
                        //Si no existe creamos una nueva
                        if (relacionOrganizativaOfi == null) {
                            relacionOrganizativaOfi = new RelacionOrganizativaOfi();

                            Oficina oficina = oficinaEjb.getReference(sOficina);

                            Unidad unidad = unidadEjb.getReference(sUnidad);

                            relacionOrganizativaOfi.setOficina(oficina);
                            relacionOrganizativaOfi.setUnidad(unidad);
                        }
                        //Actualizamo el estado en ambos casos
                        String codigoEstado = fila[4].trim();
                        if (!codigoEstado.isEmpty()) {
                            relacionOrganizativaOfi.setEstado(catEstadoEntidadEjb.getReference(codigoEstado));
                        }


                        if (existeix) {
                            em.merge(relacionOrganizativaOfi);
                        } else {
                            em.persist(relacionOrganizativaOfi);
                        }

                        // Cada batchSize hacemos flush y clear, esto permite que el proceso sea más rapido y libere memoria.
                        if (c % batchSize == 0) {
                            em.flush();
                            em.clear();
                        }

                        c++;
                        if (c % 100 == 0) {
                            log.info("Procesades OFI_RELACIONES_ORGANIZATIVAS_OFI (" + c + ") "
                                    + " en " + Utils.formatElapsedTime(System.currentTimeMillis() - s));

                            log.debug("           * Time FIND = " + Utils.formatElapsedTime(findby));
                            s = System.currentTimeMillis();
                            findby = 0;
                        }

                    }

                } catch (Exception e) {
                    log.error("Error important relaciones organizativas " + e.getMessage(), e);
                }
            }
        }

    }

    /**
     * Método que importa las relaciones Sir entre oficinas. Procesa el fichero RelacionesSIROFI.csv
     *
     * @param nombreFichero
     * @param reader
     * @throws Exception
     */
    private void importarRelacionesSir(String nombreFichero, CSVReader reader) throws Exception {

        if (Dir3caibConstantes.OFI_RELACIONES_SIROFI.equals(nombreFichero)) {
            int c = 0;
            long s = System.currentTimeMillis();
            long findby = 0;
            CacheUnidadOficina cache = new CacheUnidadOficina(relSirOfiEjb.getUnidadesOficinas());
            String[] fila;
            int batchSize = 30;
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            while ((fila = reader.readNext()) != null) {
                //Obtenemos codigo y miramos si ya existe en la BD
                try {
                    String sOficina = fila[0].trim();
                    String sUnidad = fila[2].trim();
                    if (!sOficina.isEmpty() && !sUnidad.isEmpty()) {

                        boolean existe;
                        RelacionSirOfi relacionSirOfi = null;
                        //Miramos si existe previamente la relacionSir
                        if (cache.existsUnidadOficina(sUnidad, sOficina)) {
                            long s1 = System.currentTimeMillis();
                            relacionSirOfi = relSirOfiEjb.findByPKs(sUnidad, sOficina);
                            existe = true;
                            findby = findby + (System.currentTimeMillis() - s1);
                        } else {
                            existe = false;
                            relacionSirOfi = new RelacionSirOfi();
                            Oficina oficina = oficinaEjb.getReference(sOficina);
                            if (oficina == null) {
                                oficina = oficinaEjb.getReference(sOficina);
                            }

                            Unidad unidad = unidadEjb.getReference(sUnidad);

                            relacionSirOfi.setOficina(oficina);
                            relacionSirOfi.setUnidad(unidad);
                        }
                        String codigoEstado = fila[4].trim();
                        if (!codigoEstado.isEmpty()) {
                            relacionSirOfi.setEstado(catEstadoEntidadEjb.getReference(codigoEstado));
                        }

                        if (existe) {
                            em.merge(relacionSirOfi);
                        } else {
                            em.persist(relacionSirOfi);
                        }
                        if (c % batchSize == 0) {
                            em.flush();
                            em.clear();
                        }

                        c++;
                        if (c % 100 == 0) {
                            log.info("Procesades RelacionesSIROFI:  (" + c + ") "
                                    + " en " + Utils.formatElapsedTime(System.currentTimeMillis() - s));

                            log.debug("           * Time FIND = " + Utils.formatElapsedTime(findby));
                            s = System.currentTimeMillis();
                            findby = 0;
                        }

                    }
                } catch (Exception e) {
                    log.error("Error important RELACIONES_SIROFI: " + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Método que importa los servicios de las oficinas. Procesa el fichero ServiciosOFI.csv
     *
     * @param nombreFichero
     * @param reader
     * @throws Exception
     */
    private void importarServicios(String nombreFichero, CSVReader reader) throws Exception {
        String[] fila;
        if (Dir3caibConstantes.OFI_SERVICIOS_OFI.equals(nombreFichero)) {
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            while ((fila = reader.readNext()) != null) {
                //Obtenemos codigo y miramos si ya existe en la BD
                try {
                    String codigoOficina = fila[0].trim();
                    String codigoServicio = fila[1].trim();
                    if (!codigoOficina.isEmpty() && !codigoServicio.isEmpty()) {
                        Long codServicio = new Long(codigoServicio);

                        Oficina oficina = oficinaEjb.getReference(codigoOficina);

                        if (oficina == null) {
                            oficina = oficinaEjb.findById(codigoOficina);
                        }

                        Servicio servicio = servicioEjb.findById(codServicio);


                        if (servicio == null) {
                            servicio = new Servicio();
                            servicio.setCodServicio(codServicio);
                            servicio.setDescServicio(fila[2].trim());

                            servicio = servicioEjb.persistReal(servicio);

                        }
                        Set<Servicio> servicios = oficina.getServicios();
                        if (servicios == null) {
                            servicios = new HashSet<Servicio>();
                        }

                        servicios.add(servicio);
                        oficina.setServicios(servicios);

                        oficinaEjb.merge(oficina);

                    }
                } catch (Exception e) {
                    log.error(" Error EnOFI_SERVICIOS_OFI " + e.getMessage(), e);
                }
            }
        }
    }

    /*
    * Método que se encarga de obtener los archivos de las oficinas a través de WS
    * @param request
    * @param fechaInicio
    * @param fechaFin
    */
    @Override
    public String[] descargarOficinasWS(Date fechaInicio, Date fechaFin) throws Exception {

        byte[] buffer = new byte[1024];
        String[] resp = new String[2];

        // Guardaremos la fecha de la ultima descarga
        Descarga descarga = new Descarga();
        descarga.setTipo(Dir3caibConstantes.OFICINA);

        //guardamos todas las fechas de la descarga
        if (fechaInicio != null) {
            descarga.setFechaInicio(fechaInicio);
        }
        if (fechaFin != null) {
            descarga.setFechaFin(fechaFin);
        }

        // Si las fechas estan vacias, las de descarga tenemos que fijar la fecha de hoy.
        Date hoy = new Date();

        if (fechaFin == null) {
            descarga.setFechaFin(hoy);
        }

        if (fechaInicio != null) {
            log.info("Intervalo fechas descarga oficinas directorio común: " + formatoFecha.format(descarga.getFechaInicio()) + " - " + formatoFecha.format(descarga.getFechaFin()));
        }else{
            log.info("Descarga inicial de oficinas directorio común");
        }

        descarga = descargaEjb.persist(descarga);

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

            log.info("Respuesta WS oficinas DIR3: " + respuesta.getCodigo() + " - " + respuesta.getDescripcion());

            //Montamos la respuesta del ws para controlar los errores a mostrar
            resp[0] = respuesta.getCodigo();
            resp[1] = respuesta.getDescripcion();

            if (!respuesta.getCodigo().trim().equals(Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO) && !respuesta.getCodigo().trim().equals(Dir3caibConstantes.CODIGO_RESPUESTA_VACIO)) {
                descargaEjb.remove(descarga);
                return resp;
            }

            //actualizamos el estado de la descarga.
            descarga.setEstado(respuesta.getCodigo());
            descargaEjb.merge(descarga);


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
                    descargaEjb.remove(descarga);
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

            log.info("Fin descarga de oficinas directorio común");
            return resp;
        } catch (Exception e) {
            descargaEjb.remove(descarga);
            throw new Exception(e.getMessage());
        }


    }

    /* Tarea que en un primer paso descarga los archivos csv de las oficinas y posteriormente importa el contenido
     *  en la base de datos, de esta manera realiza el proceso de sincronizacion con Madrid en un sólo
     *  proceso
     *  */
    @TransactionTimeout(value = 18000)
    public void importarOficinasTask() {

        try {
            //Obtenemos las fechas entre las que hay que hacer la descarga

            // obtenemos los datos de la última descarga
            Descarga ultimaDescarga = descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.OFICINA);
            Date fechaInicio = ultimaDescarga.getFechaFin(); // fecha de la ultima descarga

            // obtenemos la fecha de hoy
            Date fechaFin = new Date();

            // Obtiene los archivos csv via WS
            String[] respuesta = descargarOficinasWS(fechaInicio, fechaFin);
            if (Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO.equals(respuesta[0])) {
                // importamos las oficinas a la bd.
                importarOficinas(true);
            }


            // importamos las oficinas a la bd.
            //importarOficinas(true);
        } catch (Exception e) {
            log.error("Error important Oficines: " + e.getMessage(), e);
        }
    }


    private int getRequiredUnidades(List<List<String>> all, int size) throws Exception {
        FileInputStream is1 = null;
        CSVReader reader = null;

        Set<String> allCodes = new HashSet<String>();


        List<String> codigosUnidad = new ArrayList<String>();
        all.add(codigosUnidad);

        int count = 0;
        int allCount = 0;
        try {
            Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.OFICINA);
            File file = new File(Configuracio.getOficinasPath(descarga.getCodigo()), Dir3caibConstantes.OFI_OFICINAS);
            is1 = new FileInputStream(file);
            BufferedReader is = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
            reader = new CSVReader(is, ';');


            // Leemos el contenido y lo guardamos en un List

            String[] fila;

            reader.readNext();

            while ((fila = reader.readNext()) != null) {

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

