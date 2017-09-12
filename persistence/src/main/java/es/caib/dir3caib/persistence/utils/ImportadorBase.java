package es.caib.dir3caib.persistence.utils;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.ejb.*;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.utils.Utils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Clase base que agrupa funcionalidades comunes para los Importadores
 * Se definen las diferentes caches para optimizar las consultas a la bd en los procesos
 * de importación de unidades y oficinas.
 */
public class ImportadorBase {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    private UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    private OficinaLocal oficinaEjb;

    @EJB(mappedName = "dir3caib/CatAmbitoTerritorialEJB/local")
    private CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;

    @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
    private CatEntidadGeograficaLocal catEntidadGeograficaEjb;

    @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
    private CatEstadoEntidadLocal catEstadoEntidadEjb;

    @EJB(mappedName = "dir3caib/CatIslaEJB/local")
    private CatIslaLocal catIslaEjb;

    @EJB(mappedName = "dir3caib/CatNivelAdministracionEJB/local")
    private CatNivelAdministracionLocal catNivelAdministracionEjb;

    @EJB(mappedName = "dir3caib/CatPaisEJB/local")
    private CatPaisLocal catPaisEjb;

    @EJB(mappedName = "dir3caib/CatTipoContactoEJB/local")
    private CatTipoContactoLocal catTipoContactoEjb;

    @EJB(mappedName = "dir3caib/CatTipoEntidadPublicaEJB/local")
    private CatTipoEntidadPublicaLocal catTipoEntidadPublicaEjb;

    @EJB(mappedName = "dir3caib/CatTipoUnidadOrganicaEJB/local")
    private CatTipoUnidadOrganicaLocal catTipoUnidadOrganicaEjb;

    @EJB(mappedName = "dir3caib/CatTipoViaEJB/local")
    private CatTipoViaLocal catTipoViaEjb;

    @EJB(mappedName = "dir3caib/CatComunidadAutonomaEJB/local")
    private CatComunidadAutonomaLocal catComunidadAutonomaEjb;

    @EJB(mappedName = "dir3caib/CatProvinciaEJB/local")
    private CatProvinciaLocal catProvinciaEjb;

    @EJB(mappedName = "dir3caib/CatLocalidadEJB/local")
    private CatLocalidadLocal catLocalidadEjb;

    public static final SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    //Caches
    public Map<Long, CatTipoVia> cacheTipoVia = new TreeMap<Long, CatTipoVia>();
    public Map<String, CatEstadoEntidad> cacheEstadoEntidad = new TreeMap<String, CatEstadoEntidad>();
    public Map<String, CatTipoUnidadOrganica> cacheTipoUnidadOrganica = new TreeMap<String, CatTipoUnidadOrganica>();
    public Map<String, CatTipoEntidadPublica> cacheTipoEntidadPublica = new TreeMap<String, CatTipoEntidadPublica>();
    public Map<Long, CatPais> cachePais = new TreeMap<Long, CatPais>();
    public Map<CatLocalidadPK, CatLocalidad> cacheLocalidad = new HashMap<CatLocalidadPK, CatLocalidad>();
    public Map<Long, CatProvincia> cacheProvincia = new TreeMap<Long, CatProvincia>();
    public Map<Long, CatIsla> cacheIsla = new TreeMap<Long, CatIsla>();
    public Map<String, CatEntidadGeografica> cacheEntidadGeografica = new TreeMap<String, CatEntidadGeografica>();
    public Map<Long, CatComunidadAutonoma> cacheComunidadAutonoma = new TreeMap<Long, CatComunidadAutonoma>();
    public Map<CatAmbitoTerritorialPK, CatAmbitoTerritorial> cacheAmbitoTerritorial = new HashMap<CatAmbitoTerritorialPK, CatAmbitoTerritorial>();
    public Map<Long, CatNivelAdministracion> cacheNivelAdministracion = new TreeMap<Long, CatNivelAdministracion>();
    public Map<String, CatTipoContacto> cacheTipoContacto = new TreeMap<String, CatTipoContacto>();
    public Set<String> existInBBDD = new TreeSet<String>();
    public UnidadesCacheManager cacheUnidad;


    /**
     * Inicializamos los caches necesarios para importar las Unidades
     * @throws Exception
     */
    public void cacheImportadorUnidades() throws Exception {


        long start = System.currentTimeMillis();

        // Tipo Via
        for (CatTipoVia ca : catTipoViaEjb.getAll()) {
            cacheTipoVia.put(ca.getCodigoTipoVia(), ca);
        }
        log.debug(" TipoVias : " + cacheTipoVia.size());

        // CatEstadoEntidad
        for (CatEstadoEntidad ca : catEstadoEntidadEjb.getAll()) {
            cacheEstadoEntidad.put(ca.getCodigoEstadoEntidad(), ca);
        }
        log.debug(" Estado Entidad : " + cacheEstadoEntidad.size());

        // CatTipoUnidadOrganica
        for (CatTipoUnidadOrganica ca : catTipoUnidadOrganicaEjb.getAll()) {
            cacheTipoUnidadOrganica.put(ca.getCodigoTipoUnidadOrganica(), ca);
        }
        log.debug(" TipoUnidadOrganica : " + cacheTipoUnidadOrganica.size());

        // CatTipoEntidadPublica
        for (CatTipoEntidadPublica ca : catTipoEntidadPublicaEjb.getAll()) {
            cacheTipoEntidadPublica.put(ca.getCodigoTipoEntidadPublica(), ca);
        }

        // CatPais
        for (CatPais ca : catPaisEjb.getAll()) {
            cachePais.put(ca.getCodigoPais(), ca);
        }
        log.debug(" Pais : " + cachePais.size());

        // CatLocalidad
        for (CatLocalidad ca : catLocalidadEjb.getAll()) {
            CatLocalidadPK catLocalidadPK = new CatLocalidadPK(ca.getCodigoLocalidad(), ca.getProvincia(), ca.getEntidadGeografica());
            cacheLocalidad.put(catLocalidadPK, ca);
        }
        log.debug(" Localidad: " + cacheLocalidad.size());

        // CatProvincia
        for (CatProvincia ca : catProvinciaEjb.getAll()) {
            cacheProvincia.put(ca.getCodigoProvincia(), ca);
        }
        log.debug(" Provincia: " + cacheProvincia.size());

        // CatIsla
        for (CatIsla ca : catIslaEjb.getAll()) {
            cacheIsla.put(ca.getCodigoIsla(), ca);
        }
        log.debug(" Islas : " + cacheIsla.size());

        // CatEntidadGeografica
        for (CatEntidadGeografica ca : catEntidadGeograficaEjb.getAll()) {
            cacheEntidadGeografica.put(ca.getCodigoEntidadGeografica(), ca);
        }
        log.debug(" Entidad Geografica : " + cacheEntidadGeografica.size());

        // CatComunidadAutonoma
        for (CatComunidadAutonoma ca : catComunidadAutonomaEjb.getAll()) {
            cacheComunidadAutonoma.put(ca.getCodigoComunidad(), ca);
        }
        log.debug(" Comunidad Autonoma : " + cacheComunidadAutonoma.size());

        // CatAmbitoTerritorial
        for (CatAmbitoTerritorial at : catAmbitoTerritorialEjb.getAll()) {
            CatAmbitoTerritorialPK catAmbitoTerritorialPk = new CatAmbitoTerritorialPK(at.getCodigoAmbito(), at.getNivelAdministracion().getCodigoNivelAdministracion());
            cacheAmbitoTerritorial.put(catAmbitoTerritorialPk, at);
        }
        log.debug(" Ambito Territorial : " + cacheAmbitoTerritorial.size());

        // CatNivelAdministracion
        for (CatNivelAdministracion na : catNivelAdministracionEjb.getAll()) {
            cacheNivelAdministracion.put(na.getCodigoNivelAdministracion(), na);
        }
        log.debug(" Nivel Administracion : " + cacheNivelAdministracion.size());

        // CatTipoContacto
        for (CatTipoContacto ca : catTipoContactoEjb.getAll()) {
            cacheTipoContacto.put(ca.getCodigoTipoContacto(), ca);
        }
        log.debug(" TipoContacto : " + cacheTipoContacto.size());


        long end = System.currentTimeMillis();
        log.debug("Inicialitzades Caches de Importar Unidades en " + Utils.formatElapsedTime(end - start));

        start = end;

        // Obtenemos todos los códigos de las Unidades que existen en bbdd
        existInBBDD.addAll(unidadEjb.getAllCodigos());

        end = System.currentTimeMillis();
        log.debug("Inicialitzada Cache Unidades existents en " + Utils.formatElapsedTime(end - start));
    }


    /**
     * Inicializamos los caches necesarios para importar las Oficinas
     * @param isUpdate indica que es una actualización de datos( ya existen los datos en la BD)
     * @throws Exception
     */
    public void cacheImportadorOficinas(boolean isUpdate) throws Exception {

        long start = System.currentTimeMillis();

        if (isUpdate) { // cache unidades vacia
            cacheUnidad = new UnidadesCacheManager(this.unidadEjb);
        } else { // Si es creación/sincronización solo inicializamos las requeridas(unidades Responsables de las oficinas que se van a sincronizar)
            List<List<String>> unitsIds = new ArrayList<List<String>>();
            int total = getRequiredUnidades(unitsIds);
            cacheUnidad = new UnidadesCacheManager(this.unidadEjb, unitsIds, total);
        }

        long end = System.currentTimeMillis();
        log.debug("Inicialitzat Cache de Unidades en " + Utils.formatElapsedTime(end - start));


        log.debug("Inicialitzant Varies Caches per Importar Oficinas ...");
        start = System.currentTimeMillis();

        // CatTipoContacto
        for (CatTipoContacto ca : catTipoContactoEjb.getAll()) {
            cacheTipoContacto.put(ca.getCodigoTipoContacto(), ca);
        }
        log.debug(" TipoContacto : " + cacheTipoContacto.size());

        // CatTipoVia
        for (CatTipoVia ca : catTipoViaEjb.getAll()) {
            cacheTipoVia.put(ca.getCodigoTipoVia(), ca);
        }
        log.debug(" TipoVias : " + cacheTipoVia.size());

        // CatNivelAdministracion
        for (CatNivelAdministracion na : catNivelAdministracionEjb.getAll()) {
            cacheNivelAdministracion.put(na.getCodigoNivelAdministracion(), na);
        }
        log.debug(" NivelAdministracion : " + cacheNivelAdministracion.size());

        // CatProvincia
        for (CatProvincia ca : catProvinciaEjb.getAll()) {
            cacheProvincia.put(ca.getCodigoProvincia(), ca);
        }
        log.debug(" Provincia: " + cacheProvincia.size());

        // CatComunidadAutonoma
        for (CatComunidadAutonoma ca : catComunidadAutonomaEjb.getAll()) {
            cacheComunidadAutonoma.put(ca.getCodigoComunidad(), ca);
        }
        log.debug(" Comunidad Autonoma : " + cacheComunidadAutonoma.size());

        // CatPais
        for (CatPais ca : catPaisEjb.getAll()) {
            cachePais.put(ca.getCodigoPais(), ca);
        }
        log.debug(" Pais : " + cachePais.size());

        // CatEstadoEntidad
        for (CatEstadoEntidad ca : catEstadoEntidadEjb.getAll()) {
            cacheEstadoEntidad.put(ca.getCodigoEstadoEntidad(), ca);
        }
        log.debug(" Estado Entidad : " + cacheEstadoEntidad.size());

        // CatEntidadGeografica
        for (CatEntidadGeografica ca : catEntidadGeograficaEjb.getAll()) {
            cacheEntidadGeografica.put(ca.getCodigoEntidadGeografica(), ca);
        }
        log.debug(" EntidadGeografica : " + cacheEntidadGeografica.size());

        // CatLocalidad
        for (CatLocalidad ca : catLocalidadEjb.getAll()) {
            CatLocalidadPK catLocalidadPK = new CatLocalidadPK(ca.getCodigoLocalidad(), ca.getProvincia(), ca.getEntidadGeografica());
            cacheLocalidad.put(catLocalidadPK, ca);
        }
        log.debug(" Localidad: " + cacheLocalidad.size());


        end = System.currentTimeMillis();
        log.info("Inicialitzades Varies Caches per Importar Oficinas en " + Utils.formatElapsedTime(end - start));

        // Obtenemos todos los códigos de las Oficinas que existen en bbdd
        existInBBDD.addAll(oficinaEjb.getAllCodigos());

    }

    /**
     * Esta función obtiene los códigos de todas las unidades responsables de las oficinas que
     * han venido en el fichero "OFICINAS.CSV" que son las que se deben importar. Estas unidades responsables es lo que llamamos unidadesRequeridas.
     * @param all donde guardamos los códigos de las unidades requeridas finales
     * @return
     * @throws Exception
     */
    private int getRequiredUnidades(List<List<String>> all) throws Exception {
        FileInputStream is1 = null;
        CSVReader reader = null;

        // Conjunto donde guardamos todos los códigos de las unidades Responsables para procesarlas
        Set<String> allCodes = new HashSet<String>();

        List<String> codigosUnidad = new ArrayList<String>();
        all.add(codigosUnidad);

        int count = 0;
        int allCount = 0;
        try {
            //Obtenemos la ultima descarga de oficinas
            Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.OFICINA);
            File file = new File(Configuracio.getOficinasPath(descarga.getCodigo()), Dir3caibConstantes.OFI_OFICINAS);
            is1 = new FileInputStream(file);
            BufferedReader is = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
            reader = new CSVReader(is, ';');


            //Recorremos cada una de las filas del fichero para ir obteniendo la unidadResponsable de cada oficina
            String[] fila;
            reader.readNext();
            while ((fila = reader.readNext()) != null) {

                String codUOResponsable = fila[5].trim();

                // Si no la contiene la añadimos a la lista
                if (!allCodes.contains(codUOResponsable)) {
                    allCount++;
                    count++;
                    //Traspasamos a la lista final de 500 en 500
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

    /**
     *
     */
   /* public class CacheUnidadOficina {

        Set<String> caches = new TreeSet<String>();


        public CacheUnidadOficina(List<String> uniofi) {
            this.caches.addAll(uniofi);
        }


        public boolean existsUnidadOficina(String unidad, String oficina) {
            return this.caches.contains(unidad + "_" + oficina);
        }


    }*/

    /**
     *
     */
    /*public class UnidadesCacheManager {


        private final UnidadLocal unidadEjb;
        public int countFind = 0;
        public int countCache = 0;
        public long findByTime = 0;
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

        *//**
         *
         * @param codigo
         * @return
         * @throws Exception
     *//*
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


    }*/

}
