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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

    @EJB(mappedName = "dir3caib/HistoricoUOEJB/local")
    private HistoricoUOLocal historicoUOEjb;

    @EJB(mappedName = "dir3caib/CodigoUOEJB/local")
    private CodigoUOLocal codigoUOEjb;

    @EJB(mappedName = "dir3caib/NifCifUOEJB/local")
    private NifCifUOLocal nifcifUOEjb;

    @EJB(mappedName = "dir3caib/ServicioUOEJB/local")
    private ServicioUOLocal servicioUOEjb;




    /**
     * Método que importa el contenido de los archivos de las unidades, historicos y contactos descargados previamente a través
     * de los WS.
     */
    @Override
    @TransactionTimeout(value = 30000)
    public void importarUnidades(Sincronizacion sincronizacion) throws Exception {

        log.info("");
        log.info("Inicio importacion Unidades");

        System.gc();


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

                           // try {
                                //Obtenemos codigo de la unidad del fichero
                                String codigoUnidad = fila[0];
                                Long version = Long.valueOf(fila[1]);

                                String codigoUnidadVersion= codigoUnidad+"v"+version;

                                Unidad unidad = null;

                                // Comprobamos si existe ya en la BD
                                if (unidadesExistInBBDD.contains(codigoUnidadVersion)) {

                                    // Si es una actualización: Eliminamos los contactos e historicos de la Unidad
                                    contactoUOEjb.deleteByUnidad(codigoUnidadVersion);
                                    unidadEjb.eliminarHistoricosUnidad(codigoUnidadVersion);

                                    s = System.currentTimeMillis();
                                    unidad = unidadEjb.findById(codigoUnidadVersion);
                                    findbyid = findbyid + (System.currentTimeMillis() - s);
                                    findbyidcount++;
                                }

                                s = System.currentTimeMillis();

                                // Si es nuevo creamos el objeto a introducir
                                boolean existeix;
                                if (unidad == null) {
                                    unidad = new Unidad();
                                    unidad.setCodigo(codigoUnidadVersion);
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
                                unidadesExistInBBDD.add(codigoUnidadVersion);
                                persist = persist + (System.currentTimeMillis() - s);

                                s = System.currentTimeMillis();

                                // Asignamos la Unidad Raiz de la que depende
                                String codigoUnidadRaiz = fila[14].trim();
                                Long versionUnidadRaiz= Long.valueOf(fila[15].trim());
                                String codigoUnidadRaizVersion= codigoUnidadRaiz+"v"+versionUnidadRaiz;
                                if (!codigoUnidadRaizVersion.isEmpty()) {
                                    Unidad unidadRaiz = null;
                                    if (unidadesExistInBBDD.contains(codigoUnidadRaizVersion)) { //Si existe la obtenemos
                                        unidadRaiz = unidadEjb.findById(codigoUnidadRaizVersion);
                                    } else { // Si no la creamos y la guardamos
                                        unidadRaiz = unidadVacia();
                                        unidadRaiz.setCodigo(codigoUnidadRaizVersion);
                                        unidadRaiz.setCodigoDir3(codigoUnidadRaiz);
                                        unidadRaiz.setVersion(versionUnidadRaiz);
                                        unidadRaiz = unidadEjb.persistReal(unidadRaiz);
                                    }
                                    //añadimos la unidad raiz a los existentes en BD
                                    unidadesExistInBBDD.add(codigoUnidadRaizVersion);
                                    //le asignamos la unidad raiz
                                    unidad.setCodUnidadRaiz(unidadRaiz);

                                } else { //Actualizamos a sin raiz
                                    unidad.setCodUnidadRaiz(null);
                                }


                                //Asignamos la Unidad Superior de la que depende
                                String codigoUnidadSuperior = fila[11].trim();
                                Long versionUnidadSuperior = Long.valueOf(fila[12].trim());
                                String codigoUnidadSuperiorVersion= codigoUnidadSuperior+"v"+versionUnidadSuperior;
                                if (!codigoUnidadSuperiorVersion.isEmpty()) {
                                    Unidad unidadSuperior = null;
                                    if (unidadesExistInBBDD.contains(codigoUnidadSuperiorVersion)) {//Si existe la obtenemos
                                        unidadSuperior = unidadEjb.findById(codigoUnidadSuperiorVersion);
                                    } else {// Si no la creamos y la guardamos
                                        unidadSuperior = unidadVacia();
                                        unidadSuperior.setCodigo(codigoUnidadSuperiorVersion);
                                        unidadSuperior.setCodigoDir3(codigoUnidadSuperior);
                                        unidadSuperior.setVersion(versionUnidadSuperior);
                                        unidadSuperior = unidadEjb.persistReal(unidadSuperior);
                                    }
                                    //añadimos la unidad superior a los existentes en BD
                                    unidadesExistInBBDD.add(codigoUnidadSuperiorVersion);
                                    //le asignamos la unidad superior
                                    unidad.setCodUnidadSuperior(unidadSuperior);
                                } else {//actualizamos a sin superior
                                    unidad.setCodUnidadSuperior(null);
                                }

                                // Gestionamos EdpPrincipales cuando son así mismas
                                String codigoEdpPrincipal = fila[18].trim();
                                String versionEdpPrincipal = fila[19].trim();
                                String codigoEdpPrincipalVersion = codigoEdpPrincipal+"v"+versionEdpPrincipal;

                                if (!codigoEdpPrincipalVersion.isEmpty() && codigoEdpPrincipalVersion.equals(unidad.getCodigo())) {
                                    unidad.setCodEdpPrincipal(unidad);
                                }


                                //Actualizamos la Unidad
                                unidadEjb.merge(unidad);

                                merge = merge + (System.currentTimeMillis() - s);

                           /* } catch (Exception e) {
                                log.error("Error important unitats: " + e.getMessage());
                            }*/

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

                    // CodigosUO
                    importarCodigosUO(fichero, reader);

                    //NifCifUO
                    importarNifCifUO(fichero, reader);

                    //ServiciosUO
                    importarServiciosUO(fichero, reader);

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

        unidad.setCodigoDir3(codigoUnidad);
        unidad.setVersion(Long.valueOf(fila[1]));

        // fecha de cuando se ha importado desde Madrid
        unidad.setFechaImportacion(new Date());

        //Nivel de administracion
        String codigoNivelAdmin = fila[8].trim();
        CatNivelAdministracion nivelAdministracion = null;
        if (!codigoNivelAdmin.isEmpty()) {
            nivelAdministracion = cacheNivelAdministracion.get(Long.valueOf(codigoNivelAdmin));
        }
        unidad.setNivelAdministracion(nivelAdministracion);

        //Ámbito territorial
        String ambTerrit =  fila[23].trim();
        CatAmbitoTerritorial ambitoTerritorial = null;
        if (!ambTerrit.isEmpty()) {
            CatAmbitoTerritorialPK catAmbitoTerritorialPk = new CatAmbitoTerritorialPK(ambTerrit, Long.valueOf(codigoNivelAdmin));
            ambitoTerritorial = cacheAmbitoTerritorial.get(catAmbitoTerritorialPk);
            unidad.setCodAmbitoTerritorial(ambitoTerritorial);
        }
        unidad.setCodAmbitoTerritorial(ambitoTerritorial);

        //Nivel Jerarquico
        String codigoJerarquico =fila[9].trim();
        if (!codigoJerarquico.isEmpty()) {
            unidad.setNivelJerarquico(Long.valueOf(codigoJerarquico));
        } else {
            unidad.setNivelJerarquico(null);
        }

        //Comunidad autonoma
        String codigoComunidadAutonoma = fila[26].trim();
        if (!codigoComunidadAutonoma.isEmpty()) {
            unidad.setCodAmbComunidad(cacheComunidadAutonoma.get(Long.valueOf(codigoComunidadAutonoma)));
        } else {
            unidad.setCodAmbComunidad(null);
        }

        // CodAmbElm
        String codAmbElm = fila[30].trim();
        if (!codAmbElm.isEmpty()) {
            unidad.setCodAmbElm(Long.valueOf(codAmbElm));
        } else {
            unidad.setCodAmbElm(null);
        }

        //Entidad Geografica de Ambito
        CatEntidadGeografica entidadGeografica = null;
        String codigoEntGeog = fila[24].trim();
        if (!codigoEntGeog.isEmpty()) {
            entidadGeografica = cacheEntidadGeografica.get(codigoEntGeog);
            unidad.setCodAmbEntGeografica(entidadGeografica);
        } else {
            unidad.setCodAmbEntGeografica(null);
        }

        //Isla
        String codigoIsla = fila[29].trim();
        if (!codigoIsla.isEmpty()) {
            unidad.setCodAmbIsla(cacheIsla.get(Long.valueOf(codigoIsla)));
        } else {
            unidad.setCodAmbIsla(null);
        }

        //Localidad extranjera cuando el país no es España
        unidad.setCodAmbLocExtranjera(fila[31].trim());

        //Provincia
        CatProvincia provincia = null;
        final String codigoProvincia = fila[27].trim();
        if (!codigoProvincia.isEmpty()) {
            provincia = cacheProvincia.get(Long.valueOf(codigoProvincia));
            if (provincia == null) {
                log.warn("Unidad[" + codigoUnidad + "] => Provincia amb codi " + codigoProvincia + " is NULL");
            } else {
                unidad.setCodAmbProvincia(provincia);
            }
        } else {
            unidad.setCodAmbProvincia(null);
        }

        //Localidad
        String codigoMunicipio = fila[28].trim();
        if (provincia != null && entidadGeografica != null && !codigoMunicipio.isEmpty()) {
            CatLocalidadPK catLocalidadPK = new CatLocalidadPK(Long.valueOf(codigoMunicipio), provincia, entidadGeografica);
            unidad.setCatLocalidad(cacheLocalidad.get(catLocalidadPK));
        } else {
            unidad.setCatLocalidad(null);
        }

        //Pais
        String codigoPais = fila[25].trim();
        if (!codigoPais.isEmpty()) {
            unidad.setCodAmbPais(cachePais.get(Long.valueOf(codigoPais)));
        } else {
            unidad.setCodAmbPais(null);
        }

        //Comunidad de la direccion
        String codigoComunidad = fila[48].trim();
        if (!codigoComunidad.isEmpty()) {
            unidad.setCodComunidad(cacheComunidadAutonoma.get(Long.valueOf(codigoComunidad)));
        } else {
            unidad.setCodComunidad(null);
        }

        //Unidad Entidad de Derecho Publico
        String codigoEdpPrincipal = fila[18].trim();
        String versionEdpPrincipal = !fila[19].isEmpty()?fila[19].trim():"1";
        String codigoEdpPrincipalVersion= codigoEdpPrincipal+"v"+versionEdpPrincipal;
        if (!codigoEdpPrincipal.isEmpty()) {
            Unidad unidadEdpPrincipal;
            if (unidadesExistInBBDD.contains(codigoEdpPrincipalVersion)) {
                unidadEdpPrincipal = unidadEjb.findById(codigoEdpPrincipalVersion);
            } else {
                unidadEdpPrincipal = null;
            }
            unidad.setCodEdpPrincipal(unidadEdpPrincipal);
        } else {
            unidad.setCodEdpPrincipal(null);
        }

        //Codigo de la unidad que proviene de su fuente
        unidad.setCodExterno(fila[39].trim());

        //Entidad Geografica
        String codigoEntGeogD = fila[51].trim();
        CatEntidadGeografica entidadGeograficaD = null;
        if (!codigoEntGeogD.isEmpty()) {
            entidadGeograficaD = cacheEntidadGeografica.get(codigoEntGeogD);
        }

        //Localidad de la direccion
        String codigoProvD = fila[49].trim();
        CatProvincia provinciaD = null;

        if (!codigoProvD.isEmpty()) {
            provinciaD = cacheProvincia.get(Long.valueOf(codigoProvD));
        }


        String codigoLocD = fila[50].trim();
        if (!codigoLocD.isEmpty() && !codigoProvD.isEmpty() && !codigoEntGeogD.isEmpty()) {
            CatLocalidadPK catLocalidadPKD = new CatLocalidadPK(Long.valueOf(codigoLocD), provinciaD, entidadGeograficaD);
            unidad.setCodLocalidad(cacheLocalidad.get(catLocalidadPKD));
        } else {
            unidad.setCodLocalidad(null);
        }

        //Pais
        String codigoPaisD = fila[47].trim();
        if (!codigoPaisD.isEmpty()) {
            unidad.setCodPais(cachePais.get(Long.valueOf(codigoPaisD)));
        } else {
            unidad.setCodPais(null);
        }

        //Codigo postal
        unidad.setCodPostal(fila[46].trim());

        //Tipo Entidad Publica
        String codigoTipoEntPubli = fila[21].trim();
        if (!codigoTipoEntPubli.isEmpty()) {
            unidad.setCodTipoEntPublica(cacheTipoEntidadPublica.get(codigoTipoEntPubli));
        } else {
            unidad.setCodTipoEntPublica(null);
        }

        //Tipo Unidad Organica
        String codigoTipUniOrg = fila[22].trim();
        if (!codigoTipUniOrg.isEmpty()) {
            unidad.setCodTipoUnidad(cacheTipoUnidadOrganica.get(codigoTipUniOrg));
        } else {
            unidad.setCodTipoUnidad(null);
        }

        //Si es Entidad de Derecho Publico
        Boolean esEdp = fila[17].equals("S");
        unidad.setEsEdp(esEdp);

        //Estado Entidad
        String codigoEstado = fila[5].trim();
        if (!codigoEstado.isEmpty()) {
            unidad.setEstado(cacheEstadoEntidad.get(codigoEstado));
        } else {
            unidad.setEstado(null);
        }

        //Fecha Alta
        String sfechaAlta = fila[34].trim();
        if (!sfechaAlta.isEmpty()) {
            unidad.setFechaAltaOficial(formatoFecha.parse(sfechaAlta));
        } else {
            unidad.setFechaAltaOficial(null);
        }

        //Fecha Anulación
        String sfechaAnulacion = fila[37].trim();
        if (!sfechaAnulacion.isEmpty()) {
            unidad.setFechaAnulacion(formatoFecha.parse(sfechaAnulacion));
        } else {
            unidad.setFechaAnulacion(null);
        }

        //Fecha Baja
        String sfechaBajaOficial = fila[35].trim();
        if (!sfechaBajaOficial.isEmpty()) {
            unidad.setFechaBajaOficial(formatoFecha.parse(sfechaBajaOficial));
        } else {
            unidad.setFechaBajaOficial(null);
        }

        //Fecha Extinción
        String sfechaExtincion = fila[36].trim();
        if (!sfechaExtincion.isEmpty()) {
            unidad.setFechaExtincion(formatoFecha.parse(sfechaExtincion));
        } else {
            unidad.setFechaExtincion(null);
        }

        //Fecha Ultima actualización
        String sfechaUltAc = fila[38].trim();
        if (!sfechaUltAc.isEmpty()) {
            unidad.setFechaUltimaActualizacion(formatoFecha.parse(sfechaUltAc));
        } else {
            unidad.setFechaUltimaActualizacion(null);
        }


        //Varios
        unidad.setLocExtranjera(fila[45].trim());
        unidad.setNifcif(fila[6].trim());
        unidad.setNombreVia(fila[43].trim());
        unidad.setNumVia(fila[44].trim());
        unidad.setObservBaja(fila[41].trim());
        unidad.setObservGenerales(fila[40].trim());
        unidad.setObservaciones(fila[54].trim());
        unidad.setSiglas(fila[7].trim());
        unidad.setCompetencias(fila[32].trim());
        unidad.setComplemento(fila[45].trim());
        unidad.setDenominacion(fila[2].trim());
        unidad.setDenomLenguaCooficial(fila[3].trim());
        String idioma = fila[4].trim();
        if(!idioma.isEmpty()){
            unidad.setIdiomalengua(Integer.valueOf(idioma));
        }
        unidad.setDirExtranjera(fila[52].trim());
        unidad.setDisposicionLegal(fila[33].trim());


        //Poder
        String poder = fila[10].trim();
        if(!poder.isEmpty()){
            unidad.setPoder(cachePoder.get(Long.valueOf(poder)));
        } else {
            unidad.setPoder(null);
        }


        //Tipo Via
        String codigoTipoVia = fila[42].trim();
        if (!codigoTipoVia.isEmpty()) {
            unidad.setTipoVia(cacheTipoVia.get(Long.valueOf(codigoTipoVia)));
        } else {
            unidad.setTipoVia(null);
        }

        //Si comparte nif
        Boolean comparteNif = fila[55].equals("S");
        unidad.setComparteNif(comparteNif);

        //Historicos UO y Contactos a null
        //unidad.setHistoricoUO(null);
        unidad.setHistoricosAnterior(null);
        unidad.setHistoricosUltima(null);
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

                //Un historico está representado por una tupla unidad anterior-unidad ultima y una serie de datos.
                String codigoUnidadAnterior = fila[0]; //Unidad que es sustituida
                String codigoUnidadUltima = fila[3]; //unidad que la sustituye
                Long versionAnterior = Long.valueOf(fila[1]);
                Long versionUltima = Long.valueOf(fila[4]);

                String codigoUnidadAnteriorVersion=codigoUnidadAnterior+"v"+versionAnterior;
                String codigoUnidadUltimaVersion=codigoUnidadUltima+"v"+versionUltima;

                //try {

                    if (!codigoUnidadUltimaVersion.isEmpty() && !codigoUnidadAnteriorVersion.isEmpty() && unidadesExistInBBDD.contains(codigoUnidadUltimaVersion)) { // Si no están vacios
                        Unidad unidadAnterior = unidadEjb.getReference(codigoUnidadAnteriorVersion);
                        Unidad unidadUltima = unidadEjb.getReference(codigoUnidadUltimaVersion);
                        HistoricoUO historicoUO = new HistoricoUO();
                        historicoUO.setUnidadAnterior(unidadAnterior);
                        historicoUO.setUnidadUltima(unidadUltima);
                        historicoUO.setObservacionExtincion(fila[8].trim());
                        historicoUO.setMotivoRelacion(fila[7].trim());

                        //Estado Entidad
                        String codigoEstado = fila[6].trim();
                        if (!codigoEstado.isEmpty()) {
                            historicoUO.setEstado(cacheEstadoEntidad.get(codigoEstado));
                        } else {
                            historicoUO.setEstado(null);
                        }

                        historicoUOEjb.persistReal(historicoUO);

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

                /*} catch (Exception e) {
                    log.error(" --------------------------------------------------");
                    log.error(" codigoUnidadAnterior = " + codigoUnidadAnterior);
                    log.error(" codigoUnidadUltima = " + codigoUnidadUltima);


                    log.error("Error Important Unidad HISTORICO " + e.getMessage());
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
     * Método que importa los contactos de las unidades. Procesa el fichero ContactosUO.csv
     * (Solo vienen los que estan visibles en el momento de la descarga)
     *
     * @param nombreFichero fichero que contiene los contactos
     * @param reader        nos permite leer el archivo en cuestión
     */
    private void importarContactos(String nombreFichero, CSVReader reader) throws Exception {

        if (Dir3caibConstantes.UO_CONTACTO_UO.equals(nombreFichero)) {

            //try {
                String[] fila;
                reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                int count = 1;
                long start = System.currentTimeMillis();
                while ((fila = reader.readNext()) != null) {

                    String codigoUnidad = fila[0].trim();
                    Long  versionUnidad = Long.valueOf(fila[1].trim());
                    String stipoContacto = fila[2].trim();
                    String valorContacto = fila[3].trim();
                    boolean visibilidad = fila[4].trim().equals("1");

                    String codigoUnidadVersion= codigoUnidad+"v"+versionUnidad;


                    if(!codigoUnidadVersion.isEmpty() && !stipoContacto.isEmpty() && !valorContacto.isEmpty() && visibilidad){

                        ContactoUnidadOrganica contacto = new ContactoUnidadOrganica();

                        // Asociamos unidad
                        Unidad unidad = unidadEjb.getReference(codigoUnidadVersion);
                        contacto.setUnidad(unidad);

                        //Estado Entidad
                        String codigoEstado = fila[5].trim();
                        if (!codigoEstado.isEmpty()) {
                            contacto.setEstado(cacheEstadoEntidad.get(codigoEstado));
                        } else {
                            contacto.setEstado(null);
                        }

                        // Establecemos el Tipo contacto
                        CatTipoContacto tipoContacto = cacheTipoContacto.get(stipoContacto);
                        contacto.setTipoContacto(tipoContacto);

                        // Valor contacto
                        contacto.setValorContacto(valorContacto);

                        // Visibilidad
                        contacto.setVisibilidad(visibilidad);

                        // Creamos el contacto
                        contactoUOEjb.persistReal(contacto);

                        count++;
                    }

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
           /* } catch (Exception e) {
                log.error("Error important Contacto: " + e.getMessage(), e);
            }*/
        }
    }

    /**
     * Método que importa los códigos de las unidades. Procesa el fichero CodigosUO.csv
     * Esto es una relación de códigos dir3 con códigos de fuente externa
     *
     * @param nombreFichero fichero que contiene los códigos de fuente externa
     * @param reader        nos permite leer el archivo en cuestión
     */
    private void importarCodigosUO(String nombreFichero, CSVReader reader) throws Exception{
        if (Dir3caibConstantes.UO_CODIGO_UO.equals(nombreFichero)) {

            //try {
            String[] fila;
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            int count = 1;
            long start = System.currentTimeMillis();
            while ((fila = reader.readNext()) != null) {

                String codigoUnidad = fila[0].trim();
                Long  versionUnidad = Long.valueOf(fila[1].trim());
                String codigoExterno = fila[2].trim();

                String codigoUnidadVersion= codigoUnidad+"v"+versionUnidad;



                if(!codigoUnidadVersion.isEmpty()  && unidadesExistInBBDD.contains(codigoUnidadVersion)){

                    CodigoUnidadOrganica codigoUO = new CodigoUnidadOrganica();

                    // Asociamos unidad
                    Unidad unidad = unidadEjb.getReference(codigoUnidadVersion);
                    codigoUO.setUnidad(unidad);

                    //Estado Entidad
                    String codigoEstado = fila[4].trim();
                    if (!codigoEstado.isEmpty()) {
                        codigoUO.setEstado(cacheEstadoEntidad.get(codigoEstado));
                    } else {
                        codigoUO.setEstado(null);
                    }

                    // Establecemos el Tipo contacto
                    Long stipoCodigoFuenteExterna = Long.valueOf(fila[3].trim());
                    CatTipoCodigoFuenteExterna tipoCodigoFuenteExterna = cacheTipoCodigoFuenteExterna.get(stipoCodigoFuenteExterna);
                    codigoUO.setTipoCodigo(tipoCodigoFuenteExterna);

                    // Creamos el contacto
                    codigoUOEjb.persistReal(codigoUO);

                    count++;
                }

                //cada 500 realizamos flush y clear para evitar problemas de Outofmemory
                if (count % 500 == 0) {
                    long end = System.currentTimeMillis();
                    log.info("Procesats 500 codigosuo (" + (count - 500) + " - " + count
                            + ") en " + Utils.formatElapsedTime(end - start));
                    codigoUOEjb.flush();
                    codigoUOEjb.clear();
                    start = end;
                }

            }
           /* } catch (Exception e) {
                log.error("Error important Contacto: " + e.getMessage(), e);
            }*/
        }
    }


    /**
     * Método que importa los nifcif de las unidades. Procesa el fichero NifCifUO.csv
     * Esto es una relación de códigos dir3 con Nif y Cifs
     *
     * @param nombreFichero fichero que contiene los nifcif de fuente externa
     * @param reader        nos permite leer el archivo en cuestión
     */
    private void importarNifCifUO(String nombreFichero, CSVReader reader)  throws Exception{

        if (Dir3caibConstantes.UO_NIFCIF_UO.equals(nombreFichero)) {

            //try {
            String[] fila;
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            int count = 1;
            long start = System.currentTimeMillis();
            while ((fila = reader.readNext()) != null) {

                String codigoUnidad = fila[0].trim();
                Long  versionUnidad = Long.valueOf(fila[1].trim());
                String nifUnidad = fila[2].trim();
                boolean nifPrincipal= fila[3].trim().equals("S");

                String codigoUnidadVersion= codigoUnidad+"v"+versionUnidad;

                if(!codigoUnidadVersion.isEmpty()  && unidadesExistInBBDD.contains(codigoUnidadVersion)){

                    NifCifUnidadOrganica nifCifUO = new NifCifUnidadOrganica();

                    // Asociamos unidad
                    Unidad unidad = unidadEjb.getReference(codigoUnidadVersion);
                    nifCifUO.setUnidad(unidad);

                    //Estado Entidad
                    String codigoEstado = fila[4].trim();
                    if (!codigoEstado.isEmpty()) {
                        nifCifUO.setEstado(cacheEstadoEntidad.get(codigoEstado));
                    } else {
                        nifCifUO.setEstado(null);
                    }


                    // Nif Unidad
                    nifCifUO.setCodNifCif(nifUnidad);

                    // Nif Principal
                    nifCifUO.setNifPrincipal(nifPrincipal);

                    // Creamos el contacto
                    nifcifUOEjb.persistReal(nifCifUO);

                    count++;
                }

                //cada 500 realizamos flush y clear para evitar problemas de Outofmemory
                if (count % 500 == 0) {
                    long end = System.currentTimeMillis();
                    log.info("Procesats 500 nifcifs (" + (count - 500) + " - " + count
                            + ") en " + Utils.formatElapsedTime(end - start));
                    nifcifUOEjb.flush();
                    nifcifUOEjb.clear();
                    start = end;
                }

            }
           /* } catch (Exception e) {
                log.error("Error important Contacto: " + e.getMessage(), e);
            }*/
        }
    }


    /**
     * Método que importa los servicios de las unidades. Procesa el fichero ServiciosUO.csv
     *
     * @param nombreFichero
     * @param reader
     * @throws Exception
     */
    private void importarServiciosUO(String nombreFichero, CSVReader reader) throws Exception {

        if (Dir3caibConstantes.UO_SERVICIOS_UO.equals(nombreFichero)) {

            String[] fila;
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
            int count = 1;
            long start = System.currentTimeMillis();

            while ((fila = reader.readNext()) != null) {

                String codigoUnidad = fila[0];
                Long  versionUnidad = Long.valueOf(fila[1]);
                Long codigoServicio = Long.valueOf(fila[2]);

                String codigoUnidadVersion=codigoUnidad+"v"+versionUnidad;

                //try {

                if (!codigoUnidadVersion.isEmpty()  && unidadesExistInBBDD.contains(codigoUnidadVersion)) { // Si no están vacios

                    Unidad unidad = unidadEjb.getReference(codigoUnidadVersion);


                    ServicioUO servicioUO = new ServicioUO();
                    servicioUO.setUnidad(unidad);
                    servicioUO.setServicio(cacheServicioUo.get(codigoServicio));

                    //Estado Entidad
                    String codigoEstado = fila[4].trim();
                    if (!codigoEstado.isEmpty()) {
                        servicioUO.setEstado(cacheEstadoEntidad.get(codigoEstado));
                    } else {
                        servicioUO.setEstado(null);
                    }


                    servicioUOEjb.persistReal(servicioUO);

                    count++;
                    // cada 500 realizamos un flush y un clear para evitar problemas de Outofmemory
                    if (count % 500 == 0) {
                        long end = System.currentTimeMillis();
                        log.info("Procesats 500 ServiciosUO (" + (count - 500) + " - " + count
                                + ") en " + Utils.formatElapsedTime(end - start));

                        unidadEjb.flush();
                        unidadEjb.clear();
                        start = end;

                    }

                }

                /*} catch (Exception e) {
                    log.error(" --------------------------------------------------");
                    log.error(" codigoUnidadAnterior = " + codigoUnidadAnterior);
                    log.error(" codigoUnidadUltima = " + codigoUnidadUltima);


                    log.error("Error Important Unidad HISTORICO " + e.getMessage());
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
