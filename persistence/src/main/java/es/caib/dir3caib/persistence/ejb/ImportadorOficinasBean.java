package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.CacheUnidadOficina;
import es.caib.dir3caib.persistence.utils.ImportadorBase;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

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

    @EJB(mappedName = "dir3caib/ContactoOfiEJB/local")
    private ContactoOfiLocal contactoOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
    private RelacionOrganizativaOfiLocal relOrgOfiEjb;

    @EJB(mappedName = "dir3caib/RelacionSirOfiEJB/local")
    private RelacionSirOfiLocal relSirOfiEjb;

    @EJB(mappedName = "dir3caib/HistoricoOfiEJB/local")
    private HistoricoOfiLocal historicoOfiEjb;

    @EJB(mappedName = "dir3caib/ServicioOfiEJB/local")
    private ServicioOfiLocal servicioOfiEjb;



    private Boolean actualizacion;

    // Cache de oficinas creadas
    private Map<String, Oficina> oficinesCache = new TreeMap<String, Oficina>();

    /**
     * Importa en la Bd los datos que contienen los archivos descargados previamente via WS
     * @param
     * @return
     * @throws Exception
     */
    @Override
    @TransactionTimeout(value = 30000)
    public void importarOficinas(Sincronizacion sincronizacion) throws Exception {

        log.info("");
        log.info("Inicio importación Oficinas");

        System.gc();

        // Averiguamos si es una Carga de datos inicial o una Actualización
        actualizacion = sincronizacion.getFechaInicio() != null;

        // Inicializamos la cache para la importación de Oficinas
        cacheImportadorOficinas(actualizacion, sincronizacion);

        // Tiempos
        long start = System.currentTimeMillis();
        long end;

        // Buscamos los posibles ficheros de oficinas que pueden existir en el directorio
        for (int i = 0; i < Dir3caibConstantes.OFI_FICHEROS.length; i++) {
            String fichero = Dir3caibConstantes.OFI_FICHEROS[i];
            CSVReader reader = null;

            log.info("");
            log.info("Inicio fichero: " + fichero);
            log.info("------------------------------------");

            try {
                // Obtenemos el fichero del sistema de archivos
                FileInputStream is1 = new FileInputStream(new File(Configuracio.getDirectorioPath(sincronizacion.getCodigo()), fichero));
                BufferedReader is = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
                reader = new CSVReader(is, ';');
                if (reader != null) {

                    // Leemos el contenido y lo guardamos en un List
                    String[] fila;
                    if (Dir3caibConstantes.OFI_OFICINAS.equals(fichero)) { //Procesamos el fichero Oficinas.csv
                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        int count = 1;
                        while ((fila = reader.readNext()) != null) {
                            //Obtenemos codigo y miramos si ya existe en la BD
                           // try {

                                String codigoOficina = fila[0];

                                Oficina oficina = null;
                                boolean existeix;


                                //  Miramos si existe ya en la BD
                                if (oficinasExistInBBDD.contains(codigoOficina)) {
                                    log.info("Entro en existe oficina "  + codigoOficina);

                                    // Eliminamos sus contactos y servicios en la actualizacion
                                    contactoOfiEjb.deleteByOficina(codigoOficina);
                                    oficinaEjb.deleteServiciosOficina(codigoOficina);
                                    oficinaEjb.eliminarHistoricosOficina(codigoOficina);

                                    oficina = oficinaEjb.findById(codigoOficina);
                                    existeix = true;
                                } else { // si no existe
                                    oficina = new Oficina();
                                    oficina.setCodigo(codigoOficina);
                                    existeix = false;
                                }

                                // Componemos el Bean de la Oficina
                                componerOficina(oficina, fila);

                                // Guardamos o actualizamos al Oficina
                                if (existeix) {
                                    oficina = oficinaEjb.merge(oficina);
                                } else {
                                    oficina = oficinaEjb.persistReal(oficina);
                                    oficinasExistInBBDD.add(codigoOficina);
                                }

                                // guardamos la oficina procesada enla cache
                                oficinesCache.put(oficina.getCodigo(), oficina);

                            //} catch (Exception e) {

                             //log.error("Error  important oficines  " + e.getMessage(), e);
                            //}
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

                    oficinaEjb.flush();
                    oficinaEjb.clear();

                    // CONTACTOS
                    importarContactos(fichero, reader);

                    //HISTORICOS OFI
                    importarHistoricos(fichero, reader);

                    // Relaciones organizativas
                    importarRelacionesOrganizativas(fichero, reader);

                    // Relaciones SIR Oficina
                    importarRelacionesSir(fichero, reader);

                    //Servicios
                    importarServicios(fichero, reader, actualizacion);

                    log.info("Fin importar fichero: " + fichero);
                }

                reader.close();

            } catch (FileNotFoundException ex) {
                log.warn("Fichero no encontrado " + fichero);
            } catch (IOException io) {
               io.printStackTrace();
                throw new IOException(io.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }

        System.gc();

        log.info("");
        log.info("Fin importar OFICINAS");
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
      //  String codigoComunidadAutonoma = fila[21].trim();
        String codigoComunidadAutonoma = fila[26].trim();
        if (!codigoComunidadAutonoma.isEmpty()) {
            oficina.setCodComunidad(cacheComunidadAutonoma.get(Long.valueOf(codigoComunidadAutonoma)));
        } else {
            oficina.setCodComunidad(null);
        }

        // Pais
        //String codigoPais = fila[20].trim();
        String codigoPais = fila[25].trim();
        if (!codigoPais.isEmpty()) {
            oficina.setCodPais(cachePais.get(Long.valueOf(codigoPais)));
        } else {
            oficina.setCodPais(null);
        }


        //Unidad organica responsable
        //String codUOResponsable = fila[5].trim();
        String codUOResponsable = fila[7].trim();
        String versionUOResponsable = fila[8].trim();
        UnidadPK unidadPKResponsable = new UnidadPK(codUOResponsable,Long.valueOf(versionUOResponsable));
        if (!codUOResponsable.isEmpty()) {
            //TODO ELIMINAR
           // oficina.setCodUoResponsable(cacheUnidad.get(codUOResponsable));
           // Unidad unidadResponsable = unidadEjb.findByPKsReduced(codUOResponsable,Long.valueOf(versionUOResponsable));
           // oficina.setCodUoResponsable(unidadResponsable);
            log.info("unidadResponsable " + codUOResponsable + " - "+versionUOResponsable );
            oficina.setCodUoResponsable(cacheUnidad.get(unidadPKResponsable));
        } else {
            oficina.setCodUoResponsable(null);
        }

        // atributos directos
       // oficina.setComplemento(fila[18].trim());
        oficina.setComplemento(fila[23].trim());
       // oficina.setDenominacion(fila[1].trim());
        oficina.setDenominacion(fila[1].trim());
        oficina.setDenomlenguacooficial(fila[2].trim());
        oficina.setIdiomalengua(!fila[3].isEmpty()?Integer.parseInt(fila[3].trim()):0);
        oficina.setFuenteExterna(fila[12].trim());
      //  oficina.setDiasSinHabiles(fila[10].trim());
        oficina.setDiasSinHabiles(fila[14].trim());
       // oficina.setDirExtranjera(fila[25].trim());
        oficina.setDirExtranjera(fila[30].trim());
        //oficina.setDireccionObservaciones(fila[27].trim());
        oficina.setDireccionObservaciones(fila[32].trim());
       // oficina.setHorarioAtencion(fila[9].trim());
        oficina.setHorarioAtencion(fila[13].trim());
       // oficina.setLocExtranjera(fila[26].trim());
        oficina.setLocExtranjera(fila[31].trim());
       // oficina.setNombreVia(fila[16].trim());
        oficina.setNombreVia(fila[21].trim());
      //  oficina.setNumVia(fila[17].trim());
        oficina.setNumVia(fila[22].trim());
      //  oficina.setCodPostal(fila[19].trim());
        oficina.setCodPostal(fila[24].trim());

        // Estado
       // String codigoEstado = fila[2].trim();
        String codigoEstado = fila[4].trim();
        if (!codigoEstado.isEmpty()) {
            oficina.setEstado(cacheEstadoEntidad.get(codigoEstado));
        } else {
            oficina.setEstado(null);
        }

        // Fecha alta
       // String sfechaAlta = fila[12].trim();
        String sfechaAlta = fila[16].trim();
        if (!sfechaAlta.isEmpty()) {
            oficina.setFechaAltaOficial(formatoFecha.parse(sfechaAlta));
        } else {
            oficina.setFechaAltaOficial(null);
        }

        // Fecha extincioón
       // String sfechaExtincion = fila[13].trim();
        String sfechaExtincion = fila[17].trim();
        if (!sfechaExtincion.isEmpty()) {
            oficina.setFechaExtincion(formatoFecha.parse(sfechaExtincion));
        } else {
            oficina.setFechaExtincion(null);
        }

        // Fecha anulación
    //    String sfechaAnulacion = fila[14].trim();
        String sfechaAnulacion = fila[18].trim();
        if (!sfechaAnulacion.isEmpty()) {
            oficina.setFechaExtincion(formatoFecha.parse(sfechaAnulacion));
        } else {
            oficina.setFechaAnulacion(null);
        }

        //Fecha Ultima actualización
        String sfechaUltAc = fila[19].trim();
        if (!sfechaUltAc.isEmpty()) {
            oficina.setFechaUltimaActualizacion(formatoFecha.parse(sfechaUltAc));
        } else {
            oficina.setFechaUltimaActualizacion(null);
        }

        //Localidad de la dirección
     //   String codigoProv = fila[22].trim();
        String codigoProv = fila[27].trim();
       // String codigoEntGeog = fila[24].trim();
        String codigoEntGeog = fila[29].trim();
        CatProvincia provincia = null;
        if (!codigoProv.isEmpty()) {
            provincia = cacheProvincia.get(Long.valueOf(codigoProv));
        }

        //Entidad Geografica
        CatEntidadGeografica entidadGeograficaD = null;
        if (!codigoEntGeog.isEmpty()) {
            entidadGeograficaD = cacheEntidadGeografica.get(codigoEntGeog);
        }

        //Localidad
     //   String codigoLocalidad = fila[23].trim();
        String codigoLocalidad = fila[28].trim();
        if (!codigoLocalidad.isEmpty() && codigoProv!=null && codigoEntGeog!=null) {
            CatLocalidadPK catLocalidadPKD = new CatLocalidadPK(Long.valueOf(codigoLocalidad), provincia, entidadGeograficaD);
            CatLocalidad localidadD = cacheLocalidad.get(catLocalidadPKD);
            oficina.setLocalidad(localidadD);
        } else {
            oficina.setLocalidad(null);
        }

        //Nivel Administración
        //String codigoNivelAdmin = fila[3].trim();
        String codigoNivelAdmin = fila[5].trim();
        if (!codigoNivelAdmin.isEmpty()) {
            oficina.setNivelAdministracion(cacheNivelAdministracion.get(Long.valueOf(codigoNivelAdmin)));
        } else {
            oficina.setNivelAdministracion(null);
        }

        // TipoOficina
        //String tipoOficina = fila[4].trim();
        String tipoOficina = fila[6].trim();
        if (!tipoOficina.isEmpty()) {
            oficina.setTipoOficina(cacheJerarquiaOficina.get(Long.valueOf(tipoOficina)));
        } else {
            oficina.setTipoOficina(null);
        }

        // Tipo Via
       // String tipoVia = fila[15].trim();
        String tipoVia = fila[20].trim();
        if (!tipoVia.isEmpty()) {
            oficina.setTipoVia(cacheTipoVia.get(Long.valueOf(tipoVia)));
        } else {
            oficina.setTipoVia(null);
        }

        // Asignamos la Oficina Responsable
      //  String codigoOfiResponsable = fila[7].trim();
        String codigoOfiResponsable = fila[10].trim();
        Oficina ofiResponsable = null;
        if (!codigoOfiResponsable.isEmpty()) {

            if (oficinasExistInBBDD.contains(codigoOfiResponsable)) { // si existe, la obtenemos
                ofiResponsable = oficinaEjb.getReference(codigoOfiResponsable);
            }
            if (ofiResponsable == null) { //Si no existe la creamos
                ofiResponsable = oficinaVacia();
                ofiResponsable.setCodigo(codigoOfiResponsable);

                ofiResponsable = oficinaEjb.persistReal(ofiResponsable);
                oficinasExistInBBDD.add(codigoOfiResponsable);

            }
            oficina.setCodOfiResponsable(ofiResponsable);
        } else { // actualizamos en el caso de que no tenga oficina responsable
            oficina.setCodOfiResponsable(null);
        }

        // Eliminamos la relacion con los servicios
        oficina.setServicios(null);

        // Eliminamos la relacion con los historicos
        //TODO ELIMINAR
       // oficina.setHistoricosOfi(null);
        oficina.setHistoricosAnterior(null);
        oficina.setHistoricosUltima(null);
    }


    /**
     * Método que importa los históricos de las oficinas. Procesa el fichero HistoricosOFI.csv
     *
     * @param nombreFichero
     * @param reader
     * @throws Exception
     */
    private void importarHistoricos(String nombreFichero, CSVReader reader) throws Exception {

        if (Dir3caibConstantes.OFI_HISTORICOS_OFI.equals(nombreFichero)) {

            String[] fila;
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            int count = 1;
            long start = System.currentTimeMillis();

            while ((fila = reader.readNext()) != null) {

                //Obtenemos los códigos
                String codigoOficinaAnterior = fila[0]; //codigo de la oficina que es sustituida
                String codigoOficinaUltima = fila[2]; // código de la oficina que sustituye

              //  try {

                    if (!codigoOficinaUltima.isEmpty() && !codigoOficinaAnterior.isEmpty() && oficinasExistInBBDD.contains(codigoOficinaUltima)) {// Si no están vacios

                        // Creamos el HO mediante una NativeQuery muy eficiente
                        Oficina oficinaAnterior = oficinaEjb.getReference(codigoOficinaAnterior);
                        Oficina oficinaUltima = oficinaEjb.getReference(codigoOficinaUltima);
                        HistoricoOfi historicoOfi = new HistoricoOfi();
                        historicoOfi.setOficinaAnterior(oficinaAnterior);
                        historicoOfi.setOficinaUltima(oficinaUltima);
                        historicoOfi.setMotivoRelacion(fila[5].trim());

                        //Estado Entidad
                        String codigoEstado = fila[4].trim();
                        if (!codigoEstado.isEmpty()) {
                            historicoOfi.setEstado(cacheEstadoEntidad.get(codigoEstado));
                        } else {
                            historicoOfi.setEstado(null);
                        }

                        //oficinaEjb.crearHistoricoOficina(codigoOficinaAnterior, codigoOficinaUltima);
                        historicoOfiEjb.persistReal(historicoOfi);

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

                    }

                /*} catch (Exception e) {
                    log.error("=======================================");
                    log.error("codigoOficinaAnterior: " + codigoOficinaAnterior);
                    log.error("codigoOficinaUltima: " + codigoOficinaUltima);
                    log.error("Error important Historicos-Oficines " + e.getMessage());
                    StackTraceElement[] stack = e.getStackTrace();
                    int maxLines = (stack.length > 4) ? 5 : stack.length;
                    for (int n = 0; n < maxLines; n++) {
                        log.error(stack[n].toString());
                    }
                }*/
            }
        }
    }

    /**
     * Método que importa los contactos de una Oficina. Procesa el fichero ContactoOFI.csv
     * (Solo vienen los que estan visibles en el momento de la descarga)
     *
     * @param nombreFichero
     * @param reader
     * @throws Exception
     */
    private void importarContactos(String nombreFichero, CSVReader reader) throws Exception {
        String[] fila;
        if (Dir3caibConstantes.OFI_CONTACTO_OFI.equals(nombreFichero)) {
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            int count = 1;
            long start = System.currentTimeMillis();
            while ((fila = reader.readNext()) != null) {
               // try {

                    String sOficina = fila[0].trim();
                    String stipoContacto = fila[1].trim();
                    String valorContacto = fila[2].trim();
                    boolean visibilidad = fila[3].trim().equals("1");

                    if(!sOficina.isEmpty() && !stipoContacto.isEmpty() && !valorContacto.isEmpty() && visibilidad){

                        ContactoOfi contacto = new ContactoOfi();

                        // Asociamos oficina
                        Oficina oficina = oficinaEjb.getReference(sOficina);
                        contacto.setOficina(oficina);

                        //Estado Entidad
                        String codigoEstado = fila[4].trim();
                        if (!codigoEstado.isEmpty()) {
                            contacto.setEstado(cacheEstadoEntidad.get(codigoEstado));
                        } else {
                            contacto.setEstado(null);
                        }

                        //Tipo contacto
                        contacto.setTipoContacto(cacheTipoContacto.get(stipoContacto));

                        //Valor contacto
                        contacto.setValorContacto(valorContacto);

                        //Visibilidad
                        contacto.setVisibilidad(visibilidad);

                        // Guardamos el Contacto
                        contactoOfiEjb.persistReal(contacto);

                        count++;
                    }

                    if (count % 500 == 0) {
                        long end = System.currentTimeMillis();
                        log.info("Procesats 500 contactes (" + (count - 500) + " - " + count
                                + ") en " + Utils.formatElapsedTime(end - start));
                        contactoOfiEjb.flush();
                        contactoOfiEjb.clear();
                        start = end;
                    }

               // } catch (Exception e) {
                   // log.error("Error important contactos: " + e.getMessage(), e);
               // }
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
                //try {
                    String codigoOficina = fila[0].trim();
                    String codigoUnidad = fila[2].trim();
                    String versionUnidad = fila[3].trim();

                    if (!codigoOficina.isEmpty() && !codigoUnidad.isEmpty()) {

                        boolean existeix;

                        RelacionOrganizativaOfi relacionOrganizativaOfi;
                        //Miramos si existe la relacionOrganizativa
                        if (cache.existsUnidadOficina(codigoUnidad, versionUnidad, codigoOficina)) {
                            long s1 = System.currentTimeMillis();
                            relacionOrganizativaOfi = relOrgOfiEjb.findByPKs(codigoUnidad, Long.valueOf(versionUnidad),codigoOficina);
                            existeix = true;
                            findby = findby + (System.currentTimeMillis() - s1);
                        } else {
                            relacionOrganizativaOfi = null;
                            existeix = false;
                        }
                        //Si no existe creamos una nueva
                        if (relacionOrganizativaOfi == null) {
                            relacionOrganizativaOfi = new RelacionOrganizativaOfi();

                            Oficina oficina = oficinaEjb.getReference(codigoOficina);

                            //Unidad unidad = unidadEjb.getReference(codigoUnidad);
                            Unidad unidad = unidadEjb.findByPKsReduced(codigoUnidad,Long.valueOf(versionUnidad));

                            relacionOrganizativaOfi.setOficina(oficina);
                            relacionOrganizativaOfi.setUnidad(unidad);
                        }

                        //Actualizamos el estado en ambos casos
                        String codigoEstado = fila[5].trim();
                        if (!codigoEstado.isEmpty()) {
                            relacionOrganizativaOfi.setEstado(catEstadoEntidadEjb.getReference(codigoEstado));
                        }

                        // Guardamos la Relación Organizativa
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

               /* } catch (Exception e) {
                    log.error("Error important relaciones organizativas " + e.getMessage(), e);
                }*/
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
                //try {
                    String codigoOficina = fila[0].trim();
                    String codigoUnidad = fila[2].trim();
                    String versionUnidad = fila[3].trim();

                    if (!codigoOficina.isEmpty() && !codigoUnidad.isEmpty()) {

                        boolean existe;
                        RelacionSirOfi relacionSirOfi = null;

                        //Miramos si existe previamente la relacionSir
                        if (cache.existsUnidadOficina(codigoUnidad, versionUnidad,codigoOficina)) {
                            long s1 = System.currentTimeMillis();
                            relacionSirOfi = relSirOfiEjb.findByPKs(codigoUnidad, Long.valueOf(versionUnidad), codigoOficina);
                            existe = true;
                            findby = findby + (System.currentTimeMillis() - s1);
                        } else {
                            existe = false;
                            relacionSirOfi = new RelacionSirOfi();
                            Oficina oficina = oficinaEjb.getReference(codigoOficina);
                            if (oficina == null) {
                                oficina = oficinaEjb.getReference(codigoOficina);
                            }

                            //Unidad unidad = unidadEjb.getReference(Long.valueOf(codigoUnidad));
                            Unidad unidad = unidadEjb.findByPKsReduced(codigoUnidad,Long.valueOf(versionUnidad));


                            relacionSirOfi.setOficina(oficina);
                            relacionSirOfi.setUnidad(unidad);
                        }
                        String codigoEstado = fila[5].trim();
                        if (!codigoEstado.isEmpty()) {
                            relacionSirOfi.setEstado(catEstadoEntidadEjb.getReference(codigoEstado));
                        }

                        // Guardamos la RelacionSirOfi
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
                /*} catch (Exception e) {
                    log.error("Error important RELACIONES_SIROFI: " + e.getMessage(), e);
                }*/
            }
        }
    }

    /**
     * Método que importa los servicios de las oficinas. Procesa el fichero ServiciosOFI.csv
     *
     * @param nombreFichero
     * @param reader
     * @param actualizacion
     * @throws Exception
     */
    //TODO sustituir el codigo por el de importarServiciosUO de ImportarUnidadesBean
    private void importarServicios(String nombreFichero, CSVReader reader, boolean actualizacion) throws Exception {


        if (Dir3caibConstantes.OFI_SERVICIOS_OFI.equals(nombreFichero)) {

            String[] fila;
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            int count = 1;
            long start = System.currentTimeMillis();

            while ((fila = reader.readNext()) != null) {

             //   try {
                    // Obtenemos los códigos
                    String codigoOficina = fila[0].trim();
                    String codigoServicio = fila[1].trim();

                    if (!codigoOficina.isEmpty() && !codigoServicio.isEmpty() && oficinasExistInBBDD.contains(codigoOficina)) { // Si no están vacios

                        Oficina oficina = oficinaEjb.getReference(codigoOficina);


                        ServicioOfi servicioOfi = new ServicioOfi();
                        servicioOfi.setOficina(oficina);
                        servicioOfi.setServicio(cacheServicioOfi.get(Long.valueOf(codigoServicio)));

                        //Estado Entidad
                        String codigoEstado = fila[3].trim();
                        if (!codigoEstado.isEmpty()) {
                            servicioOfi.setEstado(cacheEstadoEntidad.get(codigoEstado));
                        } else {
                            servicioOfi.setEstado(null);
                        }


                        servicioOfiEjb.persistReal(servicioOfi);
                       // oficinaEjb.crearServicioOficina(codigoOficina, Long.valueOf(codigoServicio));

                        count++;
                        if (count % 500 == 0) {
                            long end = System.currentTimeMillis();
                            log.info("Procesats 500 Serveis (" + (count - 500) + " - " + count
                                    + ") en " + Utils.formatElapsedTime(end - start));
                            servicioOfiEjb.flush();
                            servicioOfiEjb.clear();
                            start = end;
                        }
                    }

               /* } catch (Exception e) {
                    log.error(" Error en OFI_SERVICIOS_OFI " + e.getMessage(), e);
                }*/
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

