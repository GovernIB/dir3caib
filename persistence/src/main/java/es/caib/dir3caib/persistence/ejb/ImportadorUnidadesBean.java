package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
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
public class ImportadorUnidadesBean implements ImportadorUnidadesLocal {
    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    protected UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/CatAmbitoTerritorialEJB/local")
    protected CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;

    @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
    protected CatEntidadGeograficaLocal catEntidadGeograficaEjb;

    @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
    protected CatEstadoEntidadLocal catEstadoEntidadEjb;

    @EJB(mappedName = "dir3caib/CatIslaEJB/local")
    protected CatIslaLocal catIslaEjb;

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

    @EJB(mappedName = "dir3caib/ContactoUOEJB/local")
    protected ContactoUOLocal contactoUOEjb;

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;

    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    /**
     * Método que importa el contenido de los archivos de las unidades descargados previamente a través
     * de los WS.
     * Añadido batch processing, con el entity Manager y el batchSize para mejorar el rendimiento.
     */
    @Override
    @TransactionTimeout(value = 54000)
    public ResultadosImportacion importarUnidades() throws Exception {

        log.info("");
        log.info("Inicio importación Unidades");
        int batchSize = 30;

        ResultadosImportacion results = new ResultadosImportacion();

        //Lista de archivos que han sido procesados al finalizar la importación
        List<String> procesados = results.getProcesados();
        //Lista de archivos que no existen y deberian existir
        List<String> inexistentes = results.getInexistentes();


    /*  CACHES */

        long start = System.currentTimeMillis();

        Map<Long, CatTipoVia> cacheTipoVia = new TreeMap<Long, CatTipoVia>();
        for (CatTipoVia ca : catTipoViaEjb.getAll()) {
            cacheTipoVia.put(ca.getCodigoTipoVia(), ca);
        }
        log.debug(" TipoVias : " + cacheTipoVia.size());


        Map<String, CatEstadoEntidad> cacheEstadoEntidad = new TreeMap<String, CatEstadoEntidad>();
        for (CatEstadoEntidad ca : catEstadoEntidadEjb.getAll()) {
            cacheEstadoEntidad.put(ca.getCodigoEstadoEntidad(), ca);
        }
        log.debug(" Estado Entidad : " + cacheEstadoEntidad.size());

        Map<String, CatTipoUnidadOrganica> cacheTipoUnidadOrganica = new TreeMap<String, CatTipoUnidadOrganica>();
        for (CatTipoUnidadOrganica ca : catTipoUnidadOrganicaEjb.getAll()) {
            cacheTipoUnidadOrganica.put(ca.getCodigoTipoUnidadOrganica(), ca);
        }
        log.debug(" TipoUnidadOrganica : " + cacheTipoUnidadOrganica.size());

        Map<String, CatTipoEntidadPublica> cacheTipoEntidadPublica = new TreeMap<String, CatTipoEntidadPublica>();
        for (CatTipoEntidadPublica ca : catTipoEntidadPublicaEjb.getAll()) {
            cacheTipoEntidadPublica.put(ca.getCodigoTipoEntidadPublica(), ca);
        }

        Map<Long, CatPais> cachePais = new TreeMap<Long, CatPais>();
        for (CatPais ca : catPaisEjb.getAll()) {
            cachePais.put(ca.getCodigoPais(), ca);
        }
        log.debug(" Pais : " + cachePais.size());

        Map<CatLocalidadPK, CatLocalidad> cacheLocalidad = new HashMap<CatLocalidadPK, CatLocalidad>();
        for (CatLocalidad ca : catLocalidadEjb.getAll()) {
            CatLocalidadPK catLocalidadPK = new CatLocalidadPK(ca.getCodigoLocalidad(), ca.getProvincia(), ca.getEntidadGeografica());
            cacheLocalidad.put(catLocalidadPK, ca);
        }
        log.debug(" Localidad: " + cacheLocalidad.size());


        Map<Long, CatProvincia> cacheProvincia = new TreeMap<Long, CatProvincia>();
        for (CatProvincia ca : catProvinciaEjb.getAll()) {
            cacheProvincia.put(ca.getCodigoProvincia(), ca);
        }
        log.debug(" Provincia: " + cacheProvincia.size());

        Map<Long, CatIsla> cacheIsla = new TreeMap<Long, CatIsla>();
        for (CatIsla ca : catIslaEjb.getAll()) {
            cacheIsla.put(ca.getCodigoIsla(), ca);
        }
        log.debug(" Islas : " + cacheIsla.size());


        Map<String, CatEntidadGeografica> cacheEntidadGeografica = new TreeMap<String, CatEntidadGeografica>();
        for (CatEntidadGeografica ca : catEntidadGeograficaEjb.getAll()) {
            cacheEntidadGeografica.put(ca.getCodigoEntidadGeografica(), ca);
        }
        log.debug(" Entidad Geografica : " + cacheEntidadGeografica.size());

        Map<Long, CatComunidadAutonoma> cacheComunidadAutonoma = new TreeMap<Long, CatComunidadAutonoma>();
        for (CatComunidadAutonoma ca : catComunidadAutonomaEjb.getAll()) {
            cacheComunidadAutonoma.put(ca.getCodigoComunidad(), ca);
        }
        log.debug(" Comunidad Autonoma : " + cacheComunidadAutonoma.size());


        Map<CatAmbitoTerritorialPK, CatAmbitoTerritorial> cacheAmbitoTerritorial = new HashMap<CatAmbitoTerritorialPK, CatAmbitoTerritorial>();
        for (CatAmbitoTerritorial at : catAmbitoTerritorialEjb.getAll()) {
            CatAmbitoTerritorialPK catAmbitoTerritorialPk = new CatAmbitoTerritorialPK(at.getCodigoAmbito(), at.getNivelAdministracion().getCodigoNivelAdministracion());
            cacheAmbitoTerritorial.put(catAmbitoTerritorialPk, at);
        }
        log.debug(" Ambito Territorial : " + cacheAmbitoTerritorial.size());

        Map<Long, CatNivelAdministracion> cacheNivelAdministracion = new TreeMap<Long, CatNivelAdministracion>();
        for (CatNivelAdministracion na : catNivelAdministracionEjb.getAll()) {
            cacheNivelAdministracion.put(na.getCodigoNivelAdministracion(), na);
        }
        log.debug(" Nivel Administracion : " + cacheNivelAdministracion.size());

        long end = System.currentTimeMillis();
        log.debug("Inicialitzades Caches de Importar Unidades en " + Utils.formatElapsedTime(end - start));


        start = end;

        Set<String> existInBBDD = new TreeSet<String>();
        existInBBDD.addAll(unidadEjb.getAllCodigos());


        end = System.currentTimeMillis();
        log.debug("Inicialitzada Cache Unidades existents en " + Utils.formatElapsedTime(end - start));


        Date hoy = new Date();

        // XYZ
        long findbyid = 0;
        int findbyidcount = 0;
        long caches = 0;
        long merge = 0;
        long persist = 0;
        long s;

        // Obtenemos el listado de ficheros que hay dentro del directorio indicado que se
        // corresponde con la descarga hecha previamente
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.UNIDAD);
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
                    String nombreFichero = fichero;
                    String[] fila;
                    int count = 1;
                    // Comprobamos el nombre del fichero
                    if (Dir3caibConstantes.UO_UNIDADES.equals(nombreFichero)) { // Procesamos el fichero Unidades.csv

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        start = System.currentTimeMillis();

                        while ((fila = reader.readNext()) != null) {

                            //Obtenemos codigo y miramos si ya existe en la BD
                            try {
                                //  Miramos si existe ya en la BD
                                String codigoUnidad = fila[0];
                                boolean existeix;

                                Unidad unidad = null;
                                if (existInBBDD.contains(codigoUnidad)) {
                                    s = System.currentTimeMillis();
                                    unidad = unidadEjb.findById(codigoUnidad);
                                    findbyid = findbyid + (System.currentTimeMillis() - s);
                                    findbyidcount++;
                                }


                                s = System.currentTimeMillis();
                                if (unidad == null) {  //Si es nuevo creamos el objeto a introducir
                                    unidad = new Unidad();
                                    unidad.setCodigo(codigoUnidad);
                                    existeix = false;
                                } else {
                                    existeix = true;
                                }

                                // fecha de cuando se ha importado desde Madrid
                                unidad.setFechaImportacion(hoy);

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
                                //log.info("Amb Provincia");

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
                                //log.info("Edp Principal");

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

                                //varios datos
                                unidad.setCompetencias(fila[25].trim());
                                unidad.setComplemento(fila[37].trim());
                                unidad.setDenominacion(fila[1].trim());
                                unidad.setDirExtranjera(fila[44].trim());
                                unidad.setDisposicionLegal(fila[26].trim());

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

                                //Tipo Via
                                String codigoTipoVia = fila[34].trim();
                                if (!codigoTipoVia.isEmpty()) {
                                    unidad.setTipoVia(cacheTipoVia.get(new Long(codigoTipoVia)));
                                } else {
                                    unidad.setTipoVia(null);
                                }

                                caches = caches + (System.currentTimeMillis() - s);
                                s = System.currentTimeMillis();


                                // Guardamos o Actualizamos la Unidad
                                if (existeix) {
                                    unidad = em.merge(unidad);
                                    contactoUOEjb.deleteByUnidad(unidad.getCodigo());
                                } else {
                                    em.persist(unidad);
                                }
                                existInBBDD.add(codigoUnidad);
                                persist = persist + (System.currentTimeMillis() - s);

                                s = System.currentTimeMillis();

                                // Unidad Raiz
                                String codigoUnidadRaiz = fila[9].trim();
                                if (!codigoUnidadRaiz.isEmpty()) {
                                    Unidad unidadRaiz = null;
                                    if (existInBBDD.contains(codigoUnidadRaiz)) {
                                        unidadRaiz = unidadEjb.findById(codigoUnidadRaiz);
                                    }else{
                                        unidadRaiz = unidadVacia();
                                        unidadRaiz.setCodigo(codigoUnidadRaiz);
                                        unidadRaiz = unidadEjb.persistReal(unidadRaiz);
                                    }

                                    existInBBDD.add(codigoUnidadRaiz);

                                    unidad.setCodUnidadRaiz(unidadRaiz);

                                } else {
                                    unidad.setCodUnidadRaiz(null);
                                }


                                //Unidad Superior
                                String codigoUnidadSuperior = fila[7].trim();
                                if (!codigoUnidadSuperior.isEmpty()) {
                                    Unidad unidadSuperior = null;
                                    if (existInBBDD.contains(codigoUnidadSuperior)) {
                                        unidadSuperior = unidadEjb.findById(codigoUnidadSuperior);
                                    }else{
                                        unidadSuperior = unidadVacia();
                                        unidadSuperior.setCodigo(codigoUnidadSuperior);
                                        unidadSuperior = unidadEjb.persistReal(unidadSuperior);
                                    }


                                    existInBBDD.add(codigoUnidadSuperior);

                                    unidad.setCodUnidadSuperior(unidadSuperior);
                                } else {
                                    unidad.setCodUnidadSuperior(null);
                                }

                                //Actualizamos la Unidad
                                em.merge(unidad);
                                //Borramos los contactos de la unidad
                                if (count % batchSize == 0) {
                                    em.flush();
                                    em.clear();
                                }

                                merge = merge + (System.currentTimeMillis() - s);

                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }

                            count++;

                            if (count % 500 == 0) {
                                end = System.currentTimeMillis();
                                log.info("Procesades 500 Unidades (" + (count - 500) + " - " + count
                                        + ") en " + Utils.formatElapsedTime(end - start));

                                //  log.debug("   findbyid: " + Utils.formatElapsedTime(findbyid));
                                //  log.debug("findbyidcount: " + findbyidcount);
                                //  log.debug("   caches  : " + Utils.formatElapsedTime(caches));
                                //  log.debug("   merge   : " + Utils.formatElapsedTime(merge));
                                //  log.debug("   persist : " + Utils.formatElapsedTime(persist));



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
                    importarHistoricos(nombreFichero, reader);

                    // Contactos
                    importarContactos(nombreFichero, reader);

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
                        log.error("tancant fitxer", ex);
                    }
                }
            }

        }

        // Guardamos fecha Importacion y tipo
        if (procesados.size() > 0) {
            descarga.setFechaImportacion(hoy);
            descargaEjb.merge(descarga);
        }


        results.setDescarga(descarga);
        results.setExistentes(existentes);

        System.gc();

        return results;
    }

    /*
        Importa las relaciones de historicos entre unidades. Procesa el fichero HistoricosUO.csv
     */
    private void importarHistoricos(String nombreFichero, CSVReader reader) throws Exception {

        if (Dir3caibConstantes.UO_HISTORICOS_UO.equals(nombreFichero)) {

            String[] fila;
            reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla

            while ((fila = reader.readNext()) != null) {
                String codigoUnidadAnterior = fila[0];
                String codigoUnidadUltima = fila[2];
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
                    if (historicosAnterior == null) {
                        historicosAnterior = new HashSet<Unidad>();
                        unidadAnterior.setHistoricoUO(historicosAnterior);
                    }
                    if (unidadUltima == null) {
                        //  log.info(" ======================== ");
                        //  log.info(" unidadUltima == NULL !!!!! ");
                        throw new Exception();
                    }

                    historicosAnterior.add(unidadUltima);

                    unidadEjb.merge(unidadAnterior);

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
     *
     * @param nombreFichero fichero que contiene los contactos
     * @param reader        nos permite leer el archivo en cuestión
     */
    private void importarContactos(String nombreFichero, CSVReader reader) throws Exception {

        if (Dir3caibConstantes.UO_CONTACTO_UO.equals(nombreFichero)) {

            Map<String, CatTipoContacto> cacheCatTipoContacto = new TreeMap<String, CatTipoContacto>();
            for (CatTipoContacto na : catTipoContactoEjb.getAll()) {
                cacheCatTipoContacto.put(na.getCodigoTipoContacto(), na);
            }


            try {
                String[] fila;
                reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                while ((fila = reader.readNext()) != null) {
                    ContactoUnidadOrganica contacto = new ContactoUnidadOrganica();

                    // Asociamos unidad
                    String sUnidad = fila[0].trim();
                    if (!sUnidad.isEmpty()) {
                        Unidad unidad;
                        unidad = unidadEjb.getReference(sUnidad);
                        contacto.setUnidad(unidad);
                    }

                    //Tipo contacto
                    String stipoContacto = fila[1].trim();
                    if (!stipoContacto.isEmpty()) {
                        CatTipoContacto tipoContacto;
                        tipoContacto = cacheCatTipoContacto.get(stipoContacto);
                        contacto.setTipoContacto(tipoContacto);
                    }

                    //Valor contacto
                    String valorContacto = fila[2].trim();
                    contacto.setValorContacto(valorContacto);

                    boolean visibilidad = fila[3].trim().equals("1");
                    contacto.setVisibilidad(visibilidad);

                    contactoUOEjb.persistReal(contacto);

                }
            } catch (Exception e) {
                log.error("Error important Contacto: " + e.getMessage(), e);
            }
        }
    }

    public Unidad unidadVacia() {

        Unidad unidad = new Unidad();
        unidad.setDenominacion(new String());
        unidad.setEsEdp(new Boolean(false));

        return unidad;
    }

    /**
     * Método que se encarga de obtener los archivos de las unidades a través de WS
     *
     * @param fechaInicio
     * @param fechaFin
     */
    public String[] descargarUnidadesWS(Date fechaInicio, Date fechaFin) throws Exception {

        byte[] buffer = new byte[1024];
        String[] resp = new String[2];

        // Guardaremos la fecha de la ultima descarga
        Descarga descarga = new Descarga();
        descarga.setTipo(Dir3caibConstantes.UNIDAD);

        //guardamos todas las fechas de la descarga
        if (fechaInicio != null) {
            descarga.setFechaInicio(fechaInicio);
        }
        if (fechaFin != null) {
            descarga.setFechaFin(fechaFin);
        }

        Date hoy = new Date();
    /* El funcionamiento de los ws de madrid no permiten que la fecha de inicio sea null si la fecha fin es distinta de null.
       Descarga incremental: Hay dos opciones, incluir solo la fecha de inicio que devolverá la información que existe
       desde la fecha indicada hasta la fecha en la que se realiza la petición y la otra opción es incluir
       fecha de inicio y fecha fin. Esta devuelve la información añadida o modificada entre esas dos fechas.*/
        if (fechaFin == null) {
            descarga.setFechaFin(hoy);
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
            String ruta = Configuracio.getArchivosPath();

            String rutaUnidades = Configuracio.getUnidadesPath(descarga.getCodigo());

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

            if (!respuesta.getCodigo().trim().equals(Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO) && !respuesta.getCodigo().trim().equals(Dir3caibConstantes.CODIGO_RESPUESTA_VACIO)) {
                descargaEjb.remove(descarga);
                return resp;
            }
            //actualizamos el estado de la descarga.
            descarga.setEstado(respuesta.getCodigo());
            descargaEjb.merge(descarga);

            // definimos el archivo zip a descargar
            String archivoUnidadZip = ruta + Dir3caibConstantes.UNIDADES_ARCHIVO_ZIP + descarga.getCodigo() + ".zip";
            File file = new File(archivoUnidadZip);

            // Guardamos el archivo descargado
            FileUtils.writeByteArrayToFile(file, decoder.decode(respuesta.getFichero()));

            // Se crea el directorio para la unidad
            File dir = new File(rutaUnidades);
            if (!dir.exists()) {
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
                File newFile = new File(rutaUnidades + fileName);

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
        } catch (Exception e) {
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


            // importamos las unidades a la bd.
            //importarUnidades();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
