package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.ImportadorBase;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;
import es.caib.dir3caib.ws.dir3.unidad.client.*;
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
 * Created 6/03/14 13:38
 * <p/>
 * Clase que permite importar las unidades desde el directorio común. En un primer paso
 * se descargan los archivos y posteriormente se importa el contenido en la BD.
 *
 * @author mgonzalez
 * @author anadal (Cache & EJB)
 * @author anadal (Eliminar PKs multiples)
 */
@Stateless(name = "ImportadorUnidadesEJB")
@SecurityDomain("seycon")
@RunAs(Dir3caibConstantes.DIR_ADMIN)
@PermitAll
public class ImportadorUnidadesBean extends ImportadorBase implements ImportadorUnidadesLocal {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    private UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/ContactoUOEJB/local")
    private ContactoUOLocal contactoUOEjb;

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    private DescargaLocal descargaEjb;

    @EJB(mappedName = "dir3caib/Dir3CaibEJB/local")
    private Dir3CaibLocal dir3CaibEjb;


    /**
     * Método que importa el contenido de los archivos de las unidades, historicos y contactos descargados previamente a través
     * de los WS.
     */
    @Override
    @TransactionTimeout(value = 30000)
    public ResultadosImportacion importarUnidades() throws Exception {

        log.info("");
        log.info("Inicio importación Unidades");

        System.gc();

        ResultadosImportacion results = new ResultadosImportacion();

        //Lista de archivos que han sido procesados al finalizar la importación
        List<String> procesados = new ArrayList<String>();
        //Lista de archivos que no existen y deberian existir
        List<String> inexistentes = new ArrayList<String>();

        // Inicializamos la cache para la importación de Unidades
        cacheImportadorUnidades();

        // Averiguamos si es una Carga de datos inicial o una Actualización
        boolean actualizacion = existInBBDD.size() > 0;

        // Tiempos
        long start;
        long end;
        long findbyid = 0;
        int findbyidcount = 0;
        long caches = 0;
        long merge = 0;
        long persist = 0;
        long s;

        // Obtenemos la última descarga de los ficheros de Unidades realizada
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.UNIDAD);

        // Obtenemos el listado de ficheros que hay dentro del directorio de la última descarga
        File f = new File(Configuracio.getUnidadesPath(descarga.getCodigo()));
        ArrayList<String> existentes = new ArrayList<String>(Arrays.asList(f.list()));

        // Buscamos los posibles ficheros de las unidades que pueden existir en el directorio
        for (int i = 0; i < Dir3caibConstantes.UO_FICHEROS.length; i++) {
            String fichero = Dir3caibConstantes.UO_FICHEROS[i];
            CSVReader reader = null;

            log.info("");
            log.info("Inicio fichero: " + fichero);
            log.info("------------------------------------");
            try {
                // Obtenemos el fichero del sistema de archivos
                File file = new File(Configuracio.getUnidadesPath(descarga.getCodigo()), fichero);
                FileInputStream is1 = new FileInputStream(file);
                BufferedReader is = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
                reader = new CSVReader(is, ';');

                if (reader != null) {
                    // Inicio importación
                    String[] fila; //Contiene la información de una fila del fichero que estamos tratando
                    int count = 1;
                    // Comprobamos el nombre del fichero
                    if (Dir3caibConstantes.UO_UNIDADES.equals(fichero)) { // Procesamos el fichero Unidades.csv

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        start = System.currentTimeMillis();

                        while ((fila = reader.readNext()) != null) { //mientras haya filas

                            try {
                                //Obtenemos codigo de la unidad del fichero
                                String codigoUnidad = fila[0];

                                // Si es una actualización: Eliminamos los contactos de la Unidad
                                if (actualizacion) {
                                    contactoUOEjb.deleteByUnidad(codigoUnidad);
                                }

                                Unidad unidad = null;

                                //  Comprobamos si existe ya en la BD
                                if (existInBBDD.contains(codigoUnidad)) {
                                    s = System.currentTimeMillis();
                                    unidad = unidadEjb.findById(codigoUnidad);
                                    findbyid = findbyid + (System.currentTimeMillis() - s);
                                    findbyidcount++;
                                }

                                s = System.currentTimeMillis();

                                //Si es nuevo creamos el objeto a introducir
                                boolean existeix;
                                if (unidad == null) {
                                    unidad = new Unidad();
                                    unidad.setCodigo(codigoUnidad);
                                    existeix = false;
                                } else {
                                    existeix = true;
                                }

                                // Componemos la unidad con todos los datos del csv
                                componerUnidad(unidad, fila);

                                //actualizamos la variable cache para ver cuanto tiempo ha transcurrido gestionando la unidad
                                caches = caches + (System.currentTimeMillis() - s);
                                s = System.currentTimeMillis();


                                // Guardamos o Actualizamos la Unidad
                                if (existeix) {
                                    unidad = unidadEjb.merge(unidad);
                                } else {
                                    unidad = unidadEjb.persistReal(unidad);
                                }
                                //la añadimos a la lista de los existentes en BD
                                existInBBDD.add(codigoUnidad);
                                persist = persist + (System.currentTimeMillis() - s);

                                s = System.currentTimeMillis();

                                // Asignamos la Unidad Raiz de la que depende
                                String codigoUnidadRaiz = fila[9].trim();
                                if (!codigoUnidadRaiz.isEmpty()) {
                                    Unidad unidadRaiz = null;
                                    if (existInBBDD.contains(codigoUnidadRaiz)) { //Si existe la obtenemos
                                        unidadRaiz = unidadEjb.findById(codigoUnidadRaiz);
                                    } else { // Si no la creamos y la guardamos
                                        unidadRaiz = unidadVacia();
                                        unidadRaiz.setCodigo(codigoUnidadRaiz);
                                        unidadRaiz = unidadEjb.persistReal(unidadRaiz);
                                    }
                                    //añadimos la unidad raiz a los existentes en BD
                                    existInBBDD.add(codigoUnidadRaiz);
                                    //le asignamos la unidad raiz
                                    unidad.setCodUnidadRaiz(unidadRaiz);

                                } else { //Actualizamos a sin raiz
                                    unidad.setCodUnidadRaiz(null);
                                }


                                //Asignamos la Unidad Superior de la que depende
                                String codigoUnidadSuperior = fila[7].trim();
                                if (!codigoUnidadSuperior.isEmpty()) {
                                    Unidad unidadSuperior = null;
                                    if (existInBBDD.contains(codigoUnidadSuperior)) {//Si existe la obtenemos
                                        unidadSuperior = unidadEjb.findById(codigoUnidadSuperior);
                                    } else {// Si no la creamos y la guardamos
                                        unidadSuperior = unidadVacia();
                                        unidadSuperior.setCodigo(codigoUnidadSuperior);
                                        unidadSuperior = unidadEjb.persistReal(unidadSuperior);
                                    }
                                    //añadimos la unidad superior a los existentes en BD
                                    existInBBDD.add(codigoUnidadSuperior);
                                    //le asignamos la unidad superior
                                    unidad.setCodUnidadSuperior(unidadSuperior);
                                } else {//actualizamos a sin superior
                                    unidad.setCodUnidadSuperior(null);
                                }

                                //Actualizamos la Unidad
                                unidadEjb.merge(unidad);

                                merge = merge + (System.currentTimeMillis() - s);

                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }

                            count++;
                            //cada 500 realizamos flush y clear para evitar problemas de Outofmemory, reseteamos contadores
                            if (count % 500 == 0) {
                                end = System.currentTimeMillis();
                                log.info("Procesades 500 Unidades (" + (count - 500) + " - " + count
                                        + ") en " + Utils.formatElapsedTime(end - start));

                                //  log.debug("   findbyid: " + Utils.formatElapsedTime(findbyid));
                                //  log.debug("findbyidcount: " + findbyidcount);
                                //  log.debug("   caches  : " + Utils.formatElapsedTime(caches));
                                //  log.debug("   merge   : " + Utils.formatElapsedTime(merge));
                                //  log.debug("   persist : " + Utils.formatElapsedTime(persist));

                                unidadEjb.flush();
                                unidadEjb.clear();

                                start = end;
                                findbyid = 0;
                                findbyidcount = 0;
                                caches = 0;
                                merge = 0;
                                persist = 0;

                            }

                        }

                    }

                    // Historicos
                    importarHistoricos(fichero, reader);

                    // Contactos
                    importarContactos(fichero, reader);

                    log.info("Fin importar fichero: " + fichero);
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
                        log.error("tancant fitxer", ex);
                    }
                }
            }

        }

        // Guardamos fecha Importacion y tipo de la descarga
        if (procesados.size() > 0) {
            descarga.setFechaImportacion(new Date());
            descargaEjb.merge(descarga);
        }


        //Actualizamos los resultados Importación.
        results.setDescarga(descarga);
        results.setExistentes(existentes);
        results.setProcesados(procesados);

        System.gc();

        log.info("");
        log.info("Fin importar UNIDADES");

        return results;
    }



    /**
     * Obtiene los ficheros de las unidades y sus relaciones a través de los WS de Madrid.
     * @param fechaInicio fecha de inicio de la descarga
     * @param fechaFin fecha fin de la descarga
     * @return listado de los nombres de los archivos CSV descargados
     * @throws Exception
     */
    @Override
    public String[] descargarUnidadesWS(Date fechaInicio, Date fechaFin) throws Exception {

        byte[] buffer = new byte[1024];
        String[] resp = new String[2];

        // Guardaremos la fecha de la ultima descarga
        Descarga descarga = new Descarga(Dir3caibConstantes.UNIDAD);

        //guardamos todas las fechas de la descarga
        if (fechaInicio != null) {
            descarga.setFechaInicio(fechaInicio);
        }
        if (fechaFin != null) {
            descarga.setFechaFin(fechaFin);
        }

    /* El funcionamiento de los ws de madrid no permiten que la fecha de inicio sea null si la fecha fin es distinta de null.
       Descarga incremental: Hay dos opciones, incluir solo la fecha de inicio que devolverá la información que existe
       desde la fecha indicada hasta la fecha en la que se realiza la petición y la otra opción es incluir
       fecha de inicio y fecha fin. Esta devuelve la información añadida o modificada entre esas dos fechas.*/
        if (fechaFin == null) {
            descarga.setFechaFin(new Date());
        }

        if (fechaInicio != null) {
            log.info("Intervalo fechas descarga unidades directorio común: " + formatoFecha.format(descarga.getFechaInicio()) + " - " + formatoFecha.format(descarga.getFechaFin()));
        }else{
            log.info("Descarga inicial de unidades directorio común");
        }

        // Guardamos la descarga porque emplearemos el identificador para el nombre del directorio y el archivo.
        descarga = descargaEjb.persist(descarga);

        try {
            // Obtenemos rutas y usuario para el WS
            String usuario = Configuracio.getDir3WsUser();
            String password = Configuracio.getDir3WsPassword();
            String archivosPath = Configuracio.getArchivosPath();

            String pathFicherosUnidades = Configuracio.getUnidadesPath(descarga.getCodigo());

            String endPoint = Configuracio.getUnidadEndPoint();

            SD01UNDescargaUnidadesService unidadesService = new SD01UNDescargaUnidadesService(new URL(endPoint + "?wsdl"));
            SD01UNDescargaUnidades service = unidadesService.getSD01UNDescargaUnidades();
            Map<String, Object> reqContext = ((BindingProvider) service).getRequestContext();
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);

            // Establecemos parametros de WS
            UnidadesWs parametros = new UnidadesWs();
            parametros.setUsuario(usuario);
            parametros.setClave(password);
            parametros.setFormatoFichero(FormatoFichero.CSV);
            parametros.setTipoConsulta(TipoConsultaUO.COMPLETO);
            //parametros.setUnidadesDependientes(Boolean.TRUE);

            if (fechaInicio != null) {
                parametros.setFechaInicio(formatoFecha.format(fechaInicio));
            }
            if (fechaFin != null) {
                parametros.setFechaFin(formatoFecha.format(fechaFin));
            }

            // Invocamos el WS
            RespuestaWS respuesta = service.exportar(parametros);

            Base64 decoder = new Base64();
            log.info("Respuesta WS unidades DIR3: " + respuesta.getCodigo() + " - " + respuesta.getDescripcion());

            //Montamos la respuesta del ws para controlar los errores a mostrar
            resp[0] = respuesta.getCodigo();
            resp[1] = respuesta.getDescripcion();

            //Si la respuesta ha sido incorrecta o vacia, eliminamos la descarga y devolvemos la respuesta incorrecta
            if (!respuesta.getCodigo().trim().equals(Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO) && !respuesta.getCodigo().trim().equals(Dir3caibConstantes.CODIGO_RESPUESTA_VACIO)) {
                descargaEjb.remove(descarga);
                return resp;
            }
            //actualizamos el estado de la descarga.
            descarga.setEstado(respuesta.getCodigo());
            descargaEjb.merge(descarga);

            // definimos el archivo zip a descargar
            String archivoUnidadZip = archivosPath + Dir3caibConstantes.UNIDADES_ARCHIVO_ZIP + descarga.getCodigo() + ".zip";
            File file = new File(archivoUnidadZip);

            // Guardamos el archivo descargado
            FileUtils.writeByteArrayToFile(file, decoder.decode(respuesta.getFichero()));

            // Se crea el directorio para la unidad
            File dir = new File(pathFicherosUnidades);
            if (!dir.exists()) { //Si no existe el directorio de las unidades
                if (!dir.mkdirs()) {
                    //Borramos la descarga creada previamente.
                    descargaEjb.remove(descarga);
                    log.error(" No se ha podido crear el directorio");
                }
            }

            //Descomprimir el archivo
            ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoUnidadZip));
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(pathFicherosUnidades + fileName);

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


            return resp;
        } catch (Exception e) { //si hay algun problema, eliminamos la descarga
            descargaEjb.remove(descarga);
            throw new Exception(e.getMessage());
        }

    }

    /* Tarea que en un primer paso descarga los archivos csv de las unidades y posteriormente importa el contenido en
   *  la base de datos, de esta manera realiza el proceso de sincronizacion con Madrid en un sólo
   *  proceso
   *  */
    @Override
    @TransactionTimeout(value = 18000)
    public void importarUnidadesTask() {

        try {
            //Obtenemos las fechas entre las que hay que hacer la descarga

            // obtenemos los datos de la última descarga
            Descarga ultimaDescarga = descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.UNIDAD);
            if (ultimaDescarga != null) {
                Date fechaInicio = ultimaDescarga.getFechaFin(); // fecha de la ultima descarga

                // obtenemos la fecha de hoy
                Date fechaFin = new Date();

                // Obtiene los archivos csv via WS
                String[] respuesta = descargarUnidadesWS(fechaInicio, fechaFin);
                if (Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO.equals(respuesta[0])) {
                    //importamos las unidades a la bd.
                    importarUnidades();
                    //Mensaje.saveMessageInfo(request, "Se han obtenido correctamente las unidades");
                    //return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina las Unidades existentes, realiza una descarga e importa los datos
     *
     * @return
     * @throws Exception
     */
    @Override
    @TransactionTimeout(value = 30000)
    public ResultadosImportacion restaurarUnidades() throws Exception {

        // Eliminamos las Oficinas
        dir3CaibEjb.eliminarOficinas();

        // Eliminamos las Unidades
        dir3CaibEjb.eliminarUnidades();

        // Realizamos una descarga de Unidades
        String[] respuesta = descargarUnidadesWS(null, null);

        // Si la descarga ha sido correcta, importamos las Unidades
        if(Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO.equals(respuesta[0])){

            return importarUnidades();

        }

        return null;

    }

    /**
     * Componemos la unidad a partir de los datos del csv
     *
     * @param unidad
     * @param fila
     * @throws Exception
     */
    private void componerUnidad(Unidad unidad, String[] fila) throws Exception {

        String codigoUnidad = fila[0];

        // fecha de cuando se ha importado desde Madrid
        unidad.setFechaImportacion(new Date());

        //Nivel de administracion
        String codigoNivelAdmin = fila[5].trim();
        CatNivelAdministracion nivelAdministracion = null;
        if (!codigoNivelAdmin.isEmpty()) {
            nivelAdministracion = cacheNivelAdministracion.get(new Long(codigoNivelAdmin));
        }
        unidad.setNivelAdministracion(nivelAdministracion);

        //Ámbito territorial
        String ambTerrit = fila[16].trim();
        CatAmbitoTerritorial ambitoTerritorial = null;
        if (!ambTerrit.isEmpty()) {
            CatAmbitoTerritorialPK catAmbitoTerritorialPk = new CatAmbitoTerritorialPK(ambTerrit, new Long(codigoNivelAdmin));
            ambitoTerritorial = cacheAmbitoTerritorial.get(catAmbitoTerritorialPk);
            unidad.setCodAmbitoTerritorial(ambitoTerritorial);
        }
        unidad.setCodAmbitoTerritorial(ambitoTerritorial);

        //Nivel Jerarquico
        String codigoJerarquico = fila[6].trim();
        if (!codigoJerarquico.isEmpty()) {
            unidad.setNivelJerarquico(Long.valueOf(codigoJerarquico));
        } else {
            unidad.setNivelJerarquico(null);
        }

        //Comunidad autonoma
        String codigoComunidadAutonoma = fila[19].trim();
        if (!codigoComunidadAutonoma.isEmpty()) {
            unidad.setCodAmbComunidad(cacheComunidadAutonoma.get(new Long(codigoComunidadAutonoma)));
        } else {
            unidad.setCodAmbComunidad(null);
        }

        // CodAmbElm
        String codAmbElm = fila[23].trim();
        if (!codAmbElm.isEmpty()) {
            unidad.setCodAmbElm(new Long(codAmbElm));
        } else {
            unidad.setCodAmbElm(null);
        }

        //Entidad Geografica de Ambito
        CatEntidadGeografica entidadGeografica = null;
        String codigoEntGeog = fila[17].trim();
        if (!codigoEntGeog.isEmpty()) {
            entidadGeografica = cacheEntidadGeografica.get(codigoEntGeog);
            unidad.setCodAmbEntGeografica(entidadGeografica);
        } else {
            unidad.setCodAmbEntGeografica(null);
        }

        //Isla
        String codigoIsla = fila[22].trim();
        if (!codigoIsla.isEmpty()) {
            unidad.setCodAmbIsla(cacheIsla.get(new Long(codigoIsla)));
        } else {
            unidad.setCodAmbIsla(null);
        }

        //Localidad extranjera cuando el país no es España
        unidad.setCodAmbLocExtranjera(fila[24].trim());

        //Provincia
        CatProvincia provincia = null;
        final String codigoProvincia = fila[20].trim();
        if (!codigoProvincia.isEmpty()) {
            provincia = cacheProvincia.get(new Long(codigoProvincia));
            if (provincia == null) {
                log.warn("Unidad[" + codigoUnidad + "] => Provincia amb codi " + codigoProvincia + " is NULL");
            } else {
                unidad.setCodAmbProvincia(provincia);
            }
        } else {
            unidad.setCodAmbProvincia(null);
        }

        //Localidad
        String codigoMunicipio = fila[21].trim();
        if (provincia != null && entidadGeografica != null && !codigoMunicipio.isEmpty()) {
            CatLocalidadPK catLocalidadPK = new CatLocalidadPK(new Long(codigoMunicipio), provincia, entidadGeografica);
            unidad.setCatLocalidad(cacheLocalidad.get(catLocalidadPK));
        } else {
            unidad.setCatLocalidad(null);
        }

        //Pais
        String codigoPais = fila[18].trim();
        if (!codigoPais.isEmpty()) {
            unidad.setCodAmbPais(cachePais.get(new Long(codigoPais)));
        } else {
            unidad.setCodAmbPais(null);
        }

        //Comunidad de la direccion
        String codigoComunidad = fila[40].trim();
        if (!codigoComunidad.isEmpty()) {
            unidad.setCodComunidad(cacheComunidadAutonoma.get(new Long(codigoComunidad)));
        } else {
            unidad.setCodComunidad(null);
        }

        //Unidad Entidad de Derecho Publico
        String codigoEdpPrincipal = fila[12].trim();
        if (!codigoEdpPrincipal.isEmpty()) {
            Unidad unidadEdpPrincipal;
            if (existInBBDD.contains(codigoEdpPrincipal)) {
                unidadEdpPrincipal = unidadEjb.findById(codigoEdpPrincipal);
            } else {
                unidadEdpPrincipal = null;
            }
            unidad.setCodEdpPrincipal(unidadEdpPrincipal);
        } else {
            unidad.setCodEdpPrincipal(null);
        }

        //Codigo de la unidad que proviene de su fuente
        unidad.setCodExterno(fila[31].trim());

        //Entidad Geografica
        String codigoEntGeogD = fila[43].trim();
        CatEntidadGeografica entidadGeograficaD = null;
        if (!codigoEntGeogD.isEmpty()) {
            entidadGeograficaD = cacheEntidadGeografica.get(codigoEntGeogD);
        }

        //Localidad de la direccion
        String codigoProvD = fila[41].trim();
        CatProvincia provinciaD = null;

        if (!codigoProvD.isEmpty()) {
            provinciaD = cacheProvincia.get(new Long(codigoProvD));
        }

        String codigoLocD = fila[42].trim();
        if (!codigoLocD.isEmpty() && !codigoProvD.isEmpty() && !codigoEntGeogD.isEmpty()) {
            CatLocalidadPK catLocalidadPKD = new CatLocalidadPK(new Long(codigoLocD), provinciaD, entidadGeograficaD);
            unidad.setCodLocalidad(cacheLocalidad.get(catLocalidadPKD));
        } else {
            unidad.setCodLocalidad(null);
        }

        //Pais
        String codigoPaisD = fila[39].trim();
        if (!codigoPaisD.isEmpty()) {
            unidad.setCodPais(cachePais.get(new Long(codigoPaisD)));
        } else {
            unidad.setCodPais(null);
        }

        //Codigo postal
        unidad.setCodPostal(fila[38].trim());

        //Tipo Entidad Publica
        String codigoTipoEntPubli = fila[14].trim();
        if (!codigoTipoEntPubli.isEmpty()) {
            unidad.setCodTipoEntPublica(cacheTipoEntidadPublica.get(codigoTipoEntPubli));
        } else {
            unidad.setCodTipoEntPublica(null);
        }

        //Tipo Unidad Organica
        String codigoTipUniOrg = fila[15].trim();
        if (!codigoTipUniOrg.isEmpty()) {
            unidad.setCodTipoUnidad(cacheTipoUnidadOrganica.get(codigoTipUniOrg));
        } else {
            unidad.setCodTipoUnidad(null);
        }

        //Si es Entidad de Derecho Publico
        Boolean esEdp = fila[11].equals("S");
        unidad.setEsEdp(esEdp);

        //Estado Entidad
        String codigoEstado = fila[2].trim();
        if (!codigoEstado.isEmpty()) {
            unidad.setEstado(cacheEstadoEntidad.get(codigoEstado));
        } else {
            unidad.setEstado(null);
        }

        //Fecha Alta
        String sfechaAlta = fila[27].trim();
        if (!sfechaAlta.isEmpty()) {
            unidad.setFechaAltaOficial(formatoFecha.parse(sfechaAlta));
        } else {
            unidad.setFechaAltaOficial(null);
        }

        //Fecha Anulación
        String sfechaAnulacion = fila[30].trim();
        if (!sfechaAnulacion.isEmpty()) {
            unidad.setFechaAnulacion(formatoFecha.parse(sfechaAnulacion));
        } else {
            unidad.setFechaAnulacion(null);
        }

        //Fecha Baja
        String sfechaBajaOficial = fila[28].trim();
        if (!sfechaBajaOficial.isEmpty()) {
            unidad.setFechaBajaOficial(formatoFecha.parse(sfechaBajaOficial));
        } else {
            unidad.setFechaBajaOficial(null);
        }

        //Fecha Extinción
        String sfechaExtincion = fila[29].trim();
        if (!sfechaExtincion.isEmpty()) {
            unidad.setFechaExtincion(formatoFecha.parse(sfechaExtincion));
        } else {
            unidad.setFechaExtincion(null);
        }

        //Varios
        unidad.setLocExtranjera(fila[45].trim());
        unidad.setNifcif(fila[3].trim());
        unidad.setNombreVia(fila[35].trim());
        unidad.setNumVia(fila[36].trim());
        unidad.setObservBaja(fila[33].trim());
        unidad.setObservGenerales(fila[32].trim());
        unidad.setObservaciones(fila[46].trim());
        unidad.setSiglas(fila[4].trim());
        unidad.setCompetencias(fila[25].trim());
        unidad.setComplemento(fila[37].trim());
        unidad.setDenominacion(fila[1].trim());
        unidad.setDirExtranjera(fila[44].trim());
        unidad.setDisposicionLegal(fila[26].trim());

        //Tipo Via
        String codigoTipoVia = fila[34].trim();
        if (!codigoTipoVia.isEmpty()) {
            unidad.setTipoVia(cacheTipoVia.get(new Long(codigoTipoVia)));
        } else {
            unidad.setTipoVia(null);
        }
    }

    /**
     * Importa las relaciones de historicos entre unidades. Procesa el fichero HistoricosUO.csv
     * @param nombreFichero
     * @param reader
     * @throws Exception
     */
    private void importarHistoricos(String nombreFichero, CSVReader reader) throws Exception {

        if (Dir3caibConstantes.UO_HISTORICOS_UO.equals(nombreFichero)) {

            String[] fila;
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            int count = 1;
            long start = System.currentTimeMillis();
            while ((fila = reader.readNext()) != null) {
                //Un histórico esta representado por la tupla codUnidadAnterior-codUnidadUltima
                String codigoUnidadAnterior = fila[0]; //Unidad que es sustituida
                String codigoUnidadUltima = fila[2]; //unidad que la sustituye
                Unidad unidadUltima = null;
                Unidad unidadAnterior = null;
                try {

                    //Obtenemos codigo y miramos si ya existe en la BD
                    if (!codigoUnidadUltima.isEmpty()) {
                        unidadUltima = unidadEjb.getReference(codigoUnidadUltima);
                    }
                    if (!codigoUnidadAnterior.isEmpty()) {
                        unidadAnterior = unidadEjb.findById(codigoUnidadAnterior);
                    }

                    Set<Unidad> historicosAnterior = unidadAnterior.getHistoricoUO();
                    //Si no tiene historicos, asignamos una lista vacia
                    if (historicosAnterior == null) {
                        historicosAnterior = new HashSet<Unidad>();
                        unidadAnterior.setHistoricoUO(historicosAnterior);
                    }
                    if (unidadUltima == null) {
                        //  log.info(" ======================== ");
                        //  log.info(" unidadUltima == NULL !!!!! ");
                        throw new Exception();
                    }

                    //Añadimos la unidad que le sustituye
                    historicosAnterior.add(unidadUltima);

                    // Actualizamos la Unidad con sus históricos
                    unidadEjb.merge(unidadAnterior);

                    count++;
                    // cada 500 realizamos un flush y un clear para evitar problemas de Outofmemory
                    if (count % 500 == 0) {
                        long end = System.currentTimeMillis();
                        log.info("Procesats 500 Historics (" + (count - 500) + " - " + count
                                + ") en " + Utils.formatElapsedTime(end - start));

                        unidadEjb.flush();
                        unidadEjb.clear();
                        start = end;

                    }

                } catch (Exception e) {
                    log.error(" --------------------------------------------------");
                    log.error(" codigoUnidadAnterior = " + codigoUnidadAnterior);
                    log.error(" codigoUnidadUltima = " + codigoUnidadUltima);


                    log.error(" UnidadAnterior = " + unidadAnterior);
                    log.error(" UnidadUltima = " + unidadUltima);

                    log.error("Error Important Unidad HISTORICO " + e.getMessage());
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
     * Método que importa los contactos de las unidades. Procesa el fichero ContactosUO.csv
     * (Solo vienen los que estan visibles en el momento de la descarga)
     *
     * @param nombreFichero fichero que contiene los contactos
     * @param reader        nos permite leer el archivo en cuestión
     */
    private void importarContactos(String nombreFichero, CSVReader reader) throws Exception {

        if (Dir3caibConstantes.UO_CONTACTO_UO.equals(nombreFichero)) {

            try {
                String[] fila;
                reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                int count = 1;
                long start = System.currentTimeMillis();
                while ((fila = reader.readNext()) != null) {
                    ContactoUnidadOrganica contacto = new ContactoUnidadOrganica();

                    // Asociamos unidad
                    String sUnidad = fila[0].trim();
                    if (!sUnidad.isEmpty()) {
                        Unidad unidad;
                        unidad = unidadEjb.getReference(sUnidad);
                        contacto.setUnidad(unidad);
                    }

                    // Establecemos el Tipo contacto
                    String stipoContacto = fila[1].trim();
                    if (!stipoContacto.isEmpty()) {
                        CatTipoContacto tipoContacto = cacheTipoContacto.get(stipoContacto);
                        contacto.setTipoContacto(tipoContacto);
                    }

                    // Valor contacto
                    String valorContacto = fila[2].trim();
                    contacto.setValorContacto(valorContacto);

                    // Visibilidad
                    boolean visibilidad = fila[3].trim().equals("1");
                    contacto.setVisibilidad(visibilidad);

                    // Creamos el contacto
                    contactoUOEjb.persistReal(contacto);

                    count++;
                    //cada 500 realizamos flush y clear para evitar problemas de Outofmemory
                    if (count % 500 == 0) {
                        long end = System.currentTimeMillis();
                        log.info("Procesats 500 contactes (" + (count - 500) + " - " + count
                                + ") en " + Utils.formatElapsedTime(end - start));
                        contactoUOEjb.flush();
                        contactoUOEjb.clear();
                        start = end;
                    }

                }
            } catch (Exception e) {
                log.error("Error important Contacto: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Método de utilidad para crear una unidad vacia
     * @return
     */
    private Unidad unidadVacia() {

        Unidad unidad = new Unidad();
        unidad.setDenominacion("");
        unidad.setEsEdp(Boolean.FALSE);

        return unidad;
    }

}
