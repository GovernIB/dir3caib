package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.CacheUnidadOficina;
import es.caib.dir3caib.persistence.utils.ImportadorBase;
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
import javax.xml.ws.BindingProvider;
import java.io.*;
import java.net.URL;
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
public class ImportadorOficinasBean extends ImportadorBase implements ImportadorOficinasLocal {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    private UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    private OficinaLocal oficinaEjb;

    @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
    private CatEstadoEntidadLocal catEstadoEntidadEjb;

    @EJB(mappedName = "dir3caib/CatJerarquiaOficinaEJB/local")
    private CatJerarquiaOficinaLocal catJerarquiaOficinaEjb;

    @EJB(mappedName = "dir3caib/ContactoOfiEJB/local")
    private ContactoOfiLocal contactoOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
    private RelacionOrganizativaOfiLocal relOrgOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionSirOfiEJB/local")
    private RelacionSirOfiLocal relSirOfiEjb;

    @EJB(mappedName = "dir3caib/ServicioEJB/local")
    private ServicioLocal servicioEjb;

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;

    @EJB(mappedName = "dir3caib/Dir3CaibEJB/local")
    private Dir3CaibLocal dir3CaibEjb;


    /**
     * Importa en la Bd los datos que contienen los archivos descargados previamente via WS
     * @param isUpdate indica si es una sincronización o es una actualización
     * @return
     * @throws Exception
     */
    @Override
    @TransactionTimeout(value = 30000)
    public ResultadosImportacion importarOficinas(boolean isUpdate) throws Exception {

        log.info("");
        log.info("Inicio importación Oficinas");

        System.gc();

        ResultadosImportacion results = new ResultadosImportacion();

        // List<String> procesados = new ArrayList<String>();
        List<String> procesados = results.getProcesados();
        List<String> inexistentes = new ArrayList<String>();


        // Inicializamos la cache para la importación de Unidades
        cacheImportadorOficinas(isUpdate);

        // Tiempos
        long start = System.currentTimeMillis();
        long end;

        // Cache de oficinas creadas
        Map<String, Oficina> oficinesCache = new TreeMap<String, Oficina>();


        // Obtenemos la última descarga de los ficheros de Oficinas realizada
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.OFICINA);

        // Obtenemos el listado de ficheros que hay dentro del directorio de la última descarga
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

                                String codigoOficina = fila[0];

                                //eliminamos sus contactos en la actualizacion
                                if (isUpdate) {
                                    contactoOfiEjb.deleteByOficina(codigoOficina);
                                }

                                Oficina oficina = null;
                                boolean existeix;
                                //  Miramos si existe ya en la BD
                                if (existInBBDD.contains(codigoOficina)) {
                                    oficina = oficinaEjb.findById(codigoOficina);
                                    existeix = true;
                                } else { // si no existe
                                    oficina = new Oficina();
                                    oficina.setCodigo(codigoOficina);
                                    existeix = false;
                                }

                                componerOficina(oficina, fila);


                                if (existeix) {
                                    oficina = oficinaEjb.merge(oficina);
                                } else {
                                    oficina = oficinaEjb.persistReal(oficina);
                                    existInBBDD.add(codigoOficina);
                                }

                                // guardamos la oficina procesada enla cache
                                oficinesCache.put(oficina.getCodigo(), oficina);
                            } catch (Exception e) {
                                log.error("Error  important oficines  " + e.getMessage(), e);
                            }
                            count++;
                            //cada 500 realizamos flush y clear para evitar problemas de outofmemory
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

                                oficinaEjb.flush();
                                oficinaEjb.clear();

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
                descarga.setFechaImportacion(new Date());
                descargaEjb.merge(descarga);

            }
            results.setDescarga(descarga);

        }
        results.setExistentes(existentes);
        results.setProcesados(procesados);

        System.gc();

        return results;
    }


    /**
     * Obtiene los ficheros de las oficinas y sus relaciones a través de los WS de Madrid.
     *
     * @param fechaInicio fecha de inicio de la descarga
     * @param fechaFin    fecha fin de la descarga
     * @return listado de los nombres de los archivos CSV descargados
     * @throws Exception
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
        if (fechaFin == null) {
            descarga.setFechaFin(new Date());
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
    @Override
    public void importarOficinasTask() {

        try {
            //Obtenemos las fechas entre las que hay que hacer la descarga

            // obtenemos los datos de la última descarga
            Descarga ultimaDescarga = descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.OFICINA);
            if (ultimaDescarga != null) {
                Date fechaInicio = ultimaDescarga.getFechaFin(); // fecha de la ultima descarga

                // obtenemos la fecha de hoy
                Date fechaFin = new Date();

                // Obtiene los archivos csv via WS
                String[] respuesta = descargarOficinasWS(fechaInicio, fechaFin);
                if (Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO.equals(respuesta[0])) {
                    // importamos las oficinas a la bd.
                    importarOficinas(true);
                }
            }

        } catch (Exception e) {
            log.error("Error important Oficines: " + e.getMessage(), e);
        }
    }

    @Override
    public ResultadosImportacion restaurarOficinas() throws Exception {

        // Eliminamos las Oficinas
        dir3CaibEjb.eliminarOficinas();

        // Realizamos una descarga de Oficinas
        String[] respuesta = descargarOficinasWS(null, null);

        // Si la descarga ha sido correcta, importamos las Oficinas
        if(Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO.equals(respuesta[0])){

            return importarOficinas(false);

        }

        return null;

    }

    /**
     * Método que compone todos los datos de la oficina
     * @param oficina
     * @param fila
     * @throws Exception
     */
    private void componerOficina(Oficina oficina, String[] fila) throws Exception {
        //Fecha Importacion
        oficina.setFechaImportacion(new Date());

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

        //Entidad Geografica
        CatEntidadGeografica entidadGeograficaD = null;
        if (!codigoEntGeog.isEmpty()) {
            entidadGeograficaD = cacheEntidadGeografica.get(codigoEntGeog);
        }

        //Localidad
        String codigoLocalidad = fila[23].trim();
        if (!codigoLocalidad.isEmpty() && !codigoProv.isEmpty() && !codigoEntGeog.isEmpty()) {
            CatLocalidadPK catLocalidadPKD = new CatLocalidadPK(new Long(codigoLocalidad), provincia, entidadGeograficaD);
            CatLocalidad localidadD;
            localidadD = cacheLocalidad.get(catLocalidadPKD);
            oficina.setLocalidad(localidadD);
        } else {
            oficina.setLocalidad(null);
        }

        //Nivel Administración
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

        //Eliminamos la relacion con los servicios
        oficina.setServicios(null);

        // Asignamos la Oficina Responsable
        String codigoOfiResponsable = fila[7].trim();
        Oficina ofiResponsable = null;
        if (!codigoOfiResponsable.isEmpty()) {

            if (existInBBDD.contains(codigoOfiResponsable)) { // si existe, la obtenemos
                ofiResponsable = oficinaEjb.getReference(codigoOfiResponsable);
            }
            if (ofiResponsable == null) { //Si no existe la creamos
                ofiResponsable = oficinaVacia();
                ofiResponsable.setCodigo(codigoOfiResponsable);

                ofiResponsable = oficinaEjb.persistReal(ofiResponsable);
                existInBBDD.add(codigoOfiResponsable);

            }
            oficina.setCodOfiResponsable(ofiResponsable);
        } else { // actualizamos en el caso de que no tenga oficina responsable
            oficina.setCodOfiResponsable(null);
        }
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
            int count = 1;
            long start = System.currentTimeMillis();
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

                    count++;
                    //Cada 500 realizamos flush y clear para evitar problemas de outofmemory
                    if (count % 500 == 0) {
                        long end = System.currentTimeMillis();
                        log.info("Procesats 500 Històrics (" + (count - 500) + " - " + count
                                + ") en " + Utils.formatElapsedTime(end - start));

                        oficinaEjb.flush();
                        oficinaEjb.clear();
                        start = end;
                    }

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
            int count = 1;
            long start = System.currentTimeMillis();
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
                    count++;
                    if (count % 500 == 0) {
                        long end = System.currentTimeMillis();
                        log.info("Procesats 500 contactes (" + (count - 500) + " - " + count
                                + ") en " + Utils.formatElapsedTime(end - start));
                        contactoOfiEjb.flush();
                        contactoOfiEjb.clear();
                        start = end;
                    }

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
                            relOrgOfiEjb.merge(relacionOrganizativaOfi);
                        } else {
                            relOrgOfiEjb.persistReal(relacionOrganizativaOfi);
                        }


                        c++;
                        //Cada 100 hacemos flush y clear para evitar problemas de outofmemory
                        if (c % 100 == 0) {
                            log.info("Procesades OFI_RELACIONES_ORGANIZATIVAS_OFI (" + c + ") "
                                    + " en " + Utils.formatElapsedTime(System.currentTimeMillis() - s));

                            log.debug("           * Time FIND = " + Utils.formatElapsedTime(findby));
                            s = System.currentTimeMillis();
                            findby = 0;

                            relOrgOfiEjb.flush();
                            relOrgOfiEjb.clear();
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
                            relSirOfiEjb.merge(relacionSirOfi);
                        } else {
                            relSirOfiEjb.persistReal(relacionSirOfi);
                        }


                        c++;
                        //Cada 100 hacemos flush y clear para evitar problemas de outofmemory
                        if (c % 100 == 0) {
                            log.info("Procesades RelacionesSIROFI:  (" + c + ") "
                                    + " en " + Utils.formatElapsedTime(System.currentTimeMillis() - s));

                            log.debug("           * Time FIND = " + Utils.formatElapsedTime(findby));
                            s = System.currentTimeMillis();
                            findby = 0;

                            relSirOfiEjb.flush();
                            relSirOfiEjb.clear();
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
            int count = 1;
            long start = System.currentTimeMillis();
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

                        count++;
                        if (count % 500 == 0) {
                            long end = System.currentTimeMillis();
                            log.info("Procesats 500 Serveis (" + (count - 500) + " - " + count
                                    + ") en " + Utils.formatElapsedTime(end - start));
                            oficinaEjb.flush();
                            oficinaEjb.clear();
                            start = end;
                        }

                    }
                } catch (Exception e) {
                    log.error(" Error EnOFI_SERVICIOS_OFI " + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * @return
     */
    private Oficina oficinaVacia() {
        Oficina oficina = new Oficina();
        oficina.setDenominacion("");

        return oficina;
    }
}

