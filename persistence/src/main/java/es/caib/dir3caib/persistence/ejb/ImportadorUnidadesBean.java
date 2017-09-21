package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
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
import java.util.Date;

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


    private Boolean actualizacion;

    /**
     * Método que importa el contenido de los archivos de las unidades, historicos y contactos descargados previamente a través
     * de los WS.
     */
    @Override
    @TransactionTimeout(value = 30000)
    public void importarUnidades(Sincronizacion sincronizacion) throws Exception {

        log.info("");
        log.info("Inicio importación Unidades");

        System.gc();

        // Averiguamos si es una Carga de datos inicial o una Actualización
        actualizacion = sincronizacion.getFechaInicio() != null;

        // Inicializamos la cache para la importación de Unidades
        cacheImportadorUnidades();

        // Tiempos
        long start;
        long end;
        long findbyid = 0;
        int findbyidcount = 0;
        long caches = 0;
        long merge = 0;
        long persist = 0;
        long s;

        // Buscamos los posibles ficheros de las unidades que pueden existir en el directorio
        for (int i = 0; i < Dir3caibConstantes.UO_FICHEROS.length; i++) {
            String fichero = Dir3caibConstantes.UO_FICHEROS[i];
            CSVReader reader = null;

            log.info("");
            log.info("Inicio fichero: " + fichero);
            log.info("------------------------------------");
            try {
                // Obtenemos el fichero del sistema de archivos
                File file = new File(Configuracio.getDirectorioPath(sincronizacion.getCodigo()), fichero);
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

                                Unidad unidad = null;

                                // Comprobamos si existe ya en la BD
                                if (existInBBDD.contains(codigoUnidad)) {

                                    // Si es una actualización: Eliminamos los contactos e historicos de la Unidad
                                    contactoUOEjb.deleteByUnidad(codigoUnidad);
                                    unidadEjb.eliminarHistoricosUnidad(codigoUnidad);

                                    s = System.currentTimeMillis();
                                    unidad = unidadEjb.findById(codigoUnidad);
                                    findbyid = findbyid + (System.currentTimeMillis() - s);
                                    findbyidcount++;
                                }

                                s = System.currentTimeMillis();

                                // Si es nuevo creamos el objeto a introducir
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

                    unidadEjb.flush();
                    unidadEjb.clear();

                    // Historicos
                    importarHistoricos(fichero, reader);

                    // Contactos
                    importarContactos(fichero, reader);

                    log.info("Fin importar fichero: " + fichero);
                }

                reader.close();


            } catch (FileNotFoundException ex) {

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

        System.gc();

        log.info("");
        log.info("Fin importar UNIDADES");

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

        //Historicos UO y Contactos a null
        unidad.setHistoricoUO(null);
        unidad.setContactos(null);
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

                try {

                    if (!codigoUnidadUltima.isEmpty() && !codigoUnidadAnterior.isEmpty() && existInBBDD.contains(codigoUnidadUltima)) { // Si no están vacios

                        // Creamos el HU mediante una NativeQuery muy eficiente
                        unidadEjb.crearHistoricoUnidad(codigoUnidadAnterior, codigoUnidadUltima);

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

                    }

                } catch (Exception e) {
                    log.error(" --------------------------------------------------");
                    log.error(" codigoUnidadAnterior = " + codigoUnidadAnterior);
                    log.error(" codigoUnidadUltima = " + codigoUnidadUltima);


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
                        Unidad unidad = unidadEjb.getReference(sUnidad);
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
