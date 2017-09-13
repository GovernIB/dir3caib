package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.utils.ResultadosImportacion;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.ws.dir3.catalogo.client.RespuestaWS;
import es.caib.dir3caib.ws.dir3.catalogo.client.SC21CTVolcadoCatalogos;
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
import javax.xml.ws.BindingProvider;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created 10/03/14 14:20
 * <p/>
 * Clase que permite importar el catálogo desde el directorio común. En un primer paso
 * se descargan los archivos y posteriormente se importa el contenido en la BD.
 *
 * @author mgonzalez
 * @author anadal (Cache & EJB)
 * @author anadal (Eliminar PKs multiples)
 */
@Stateless(name = "ImportadorCatalogoEJB")
@SecurityDomain("seycon")
@RunAs(Dir3caibConstantes.DIR_ADMIN)
@PermitAll
public class ImportadorCatalogoBean implements ImportadorCatalogoLocal {

    protected final Logger log = Logger.getLogger(getClass());

    // Indicamos el formato de fecha dd/MM/yyyy hh:mm:ss
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @EJB(mappedName = "dir3caib/CatAmbitoTerritorialEJB/local")
    private CatAmbitoTerritorialLocal catAmbitoTerritorialEjb;

    @EJB(mappedName = "dir3caib/CatEntidadGeograficaEJB/local")
    private CatEntidadGeograficaLocal catEntidadGeograficaEjb;

    @EJB(mappedName = "dir3caib/CatEstadoEntidadEJB/local")
    private CatEstadoEntidadLocal catEstadoEntidadEjb;

    @EJB(mappedName = "dir3caib/CatIslaEJB/local")
    private CatIslaLocal catIslaEjb;

    @EJB(mappedName = "dir3caib/CatJerarquiaOficinaEJB/local")
    private CatJerarquiaOficinaLocal catJerarquiaOficinaEjb;

    @EJB(mappedName = "dir3caib/CatMotivoExtincionEJB/local")
    private CatMotivoExtincionLocal catMotivoExtincionEjb;

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

    @EJB(mappedName = "dir3caib/ServicioEJB/local")
    private ServicioLocal servicioEjb;

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    private DescargaLocal descargaEjb;


    /**
     * Método que importa el contenido de los archivos del catálogo  descargados previamente a través
     * de los WS.
     */
    @Override
    @TransactionTimeout(value = 3600)
    public ResultadosImportacion importarCatalogo() throws Exception {


        ResultadosImportacion results = new ResultadosImportacion();
        //Lista de archivos que han sido procesados al finalizar la importación
        List<String> procesados = results.getProcesados();
        //Lista de archivos que no existen y deberian existir
        List<String> inexistentes = results.getInexistentes();

        // Obtenemos el listado de ficheros que hay dentro del directorio indicado que se
        // corresponde con la descarga hecha previamente
        Descarga descarga = descargaEjb.ultimaDescarga(Dir3caibConstantes.CATALOGO);
        File f = new File(Configuracio.getCatalogosPath(descarga.getCodigo()));
        ArrayList<String> existentes = new ArrayList<String>(Arrays.asList(f.list()));
        results.setExistentes(existentes);

        // caches
        Map<String, CatEntidadGeografica> cacheEntidadGeografica = new TreeMap<String, CatEntidadGeografica>();

        Map<Long, CatNivelAdministracion> cacheNivelAdministracion = new TreeMap<Long, CatNivelAdministracion>();

        Map<Long, CatPais> cachePais = new TreeMap<Long, CatPais>();

        Map<Long, CatComunidadAutonoma> cacheComunidadAutonoma = new TreeMap<Long, CatComunidadAutonoma>();

        Map<Long, CatProvincia> cacheProvincia = new TreeMap<Long, CatProvincia>();


        // Buscamos los posibles ficheros de catalogos que pueden existir en el directorio
        for (int i = 0; i < Dir3caibConstantes.CAT_FICHEROS.length; i++) {
            String fichero = Dir3caibConstantes.CAT_FICHEROS[i];

            String[] fila;

            CSVReader reader = null;

            try {

                reader = getCSVReader(fichero, descarga.getCodigo());

                //reader=new CSVReader(new InputStreamReader(new FileInputStream(FileSystemManager.getArchivo(Dir3caibConstantes.CATALOGOS_LOCATION_PROPERTY,fichero)), "ISO-8859-15"), '|');
                if (reader != null) {
                    // Inicio importación
                    String nombreFichero = fichero;


                    //CATENTIDADGEOGRAFICA
                    if (nombreFichero.equals(Dir3caibConstantes.CAT_ENTIDAD_GEOGRAFICA)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {
                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                String codigoEntidadGeografica = fila[0];
                                CatEntidadGeografica entidadGeografica = cacheEntidadGeografica.get(codigoEntidadGeografica);

                                if(entidadGeografica == null){
                                    entidadGeografica = catEntidadGeograficaEjb.findById(codigoEntidadGeografica);
                                }

                                if (entidadGeografica == null) { // Si es nuevo creamos el objeto a introducir
                                    entidadGeografica = new CatEntidadGeografica();
                                    entidadGeografica.setCodigoEntidadGeografica(codigoEntidadGeografica);
                                }

                                entidadGeografica.setDescripcionEntidadGeografica(fila[1]);

                                catEntidadGeograficaEjb.persist(entidadGeografica);

                                cacheEntidadGeografica.put(codigoEntidadGeografica, entidadGeografica);

                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }

                    //CATESTADOENTIDAD
                    if (Dir3caibConstantes.CAT_ESTADO_ENTIDAD.equals(nombreFichero)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {
                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                String codigoEstadoEntidad = fila[0];
                                CatEstadoEntidad estadoEntidad = catEstadoEntidadEjb.findById(codigoEstadoEntidad);

                                if (estadoEntidad == null) { // Si es nuevo creamos el objeto a introducir
                                    estadoEntidad = new CatEstadoEntidad();
                                    estadoEntidad.setCodigoEstadoEntidad(codigoEstadoEntidad);
                                }
                                estadoEntidad.setDescripcionEstadoEntidad(fila[1]);

                                catEstadoEntidadEjb.persist(estadoEntidad);

                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }


                    //CATJERARQUIAOFICINA
                    if (Dir3caibConstantes.CAT_JERARQUIA_OFICINA.equals(nombreFichero)) {
                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {

                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                String sCodigoJerarquiaOficina = fila[0].trim();
                                if (!sCodigoJerarquiaOficina.isEmpty()) {
                                    Long codigoJerarquiaOficina = new Long(sCodigoJerarquiaOficina);

                                    CatJerarquiaOficina jerarquiaOficina = catJerarquiaOficinaEjb.findById(codigoJerarquiaOficina);

                                    if (jerarquiaOficina == null) { // Si es nuevo creamos el objeto a introducir
                                        jerarquiaOficina = new CatJerarquiaOficina();
                                        jerarquiaOficina.setCodigoJerarquiaOficina(codigoJerarquiaOficina);
                                    }
                                    jerarquiaOficina.setDescripcionJerarquiaOficina(fila[1]);

                                    catJerarquiaOficinaEjb.persist(jerarquiaOficina);

                                }
                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }
                    }

                    //CATMOTIVOEXTINCION
                    if (Dir3caibConstantes.CAT_MOTIVO_EXTINCION.equals(nombreFichero)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {

                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                String codigoMotivoExtincion = fila[0];

                                CatMotivoExtincion motivoExtincion = catMotivoExtincionEjb.findById(codigoMotivoExtincion);

                                if (motivoExtincion == null) { // Si es nuevo creamos el objeto a introducir
                                    motivoExtincion = new CatMotivoExtincion();
                                    motivoExtincion.setCodigoMotivoExtincion(codigoMotivoExtincion);
                                }
                                motivoExtincion.setDescripcionMotivoExtincion(fila[1]);

                                catMotivoExtincionEjb.persist(motivoExtincion);

                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }

                    // CATNIVELADMINISTRACION
                    if (Dir3caibConstantes.CAT_NIVEL_ADMINISTRACION.equals(nombreFichero)) {
                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {
                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                String sCodigoNivelAdministracion = fila[0].trim();
                                if (!sCodigoNivelAdministracion.isEmpty()) {
                                    Long codigoNivelAdministracion = new Long(sCodigoNivelAdministracion);

                                    CatNivelAdministracion nivelAdministracion = catNivelAdministracionEjb.findById(codigoNivelAdministracion);

                                    if (nivelAdministracion == null) { // Si es nuevo creamos el objeto a introducir
                                        nivelAdministracion = new CatNivelAdministracion();
                                        nivelAdministracion.setCodigoNivelAdministracion(codigoNivelAdministracion);
                                    }
                                    nivelAdministracion.setDescripcionNivelAdministracion(fila[1]);

                                    catNivelAdministracionEjb.persist(nivelAdministracion);

                                    cacheNivelAdministracion.put(codigoNivelAdministracion, nivelAdministracion);

                                }
                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }
                    }

                    //CATPAIS
                    if (Dir3caibConstantes.CAT_PAIS.equals(nombreFichero)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {
                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                Long codigoPais = new Long(fila[0].trim());
                                CatPais pais = catPaisEjb.findById(codigoPais);

                                if (pais == null) { // Si es nuevo creamos el objeto a introducir
                                    pais = new CatPais();
                                    pais.setCodigoPais(codigoPais);
                                }
                                pais.setDescripcionPais(fila[1].trim());
                                pais.setAlfa2Pais(fila[2].trim());
                                pais.setAlfa3Pais(fila[3].trim());

                                catPaisEjb.persist(pais);

                                cachePais.put(codigoPais, pais);
                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }
                    }


                    //CATCOMUNIDADAUTONOMA
                    if (Dir3caibConstantes.CAT_COMUNIDAD_AUTONOMA.equals(nombreFichero)) {
                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {

                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                Long codigoComunidad = new Long(fila[0].trim());
                                // cargamos el nivel de Administracion correspondiente.
                                CatPais catPais;
                                catPais = cachePais.get(new Long(fila[2].trim()));
                                // Miramos si ya existe
                                CatComunidadAutonoma comunidadAutonoma = catComunidadAutonomaEjb.findById(codigoComunidad);

                                if (comunidadAutonoma == null) { // Si es nuevo creamos el objeto a introducir
                                    comunidadAutonoma = new CatComunidadAutonoma();
                                    comunidadAutonoma.setCodigoComunidad(codigoComunidad);
                                }
                                comunidadAutonoma.setPais(catPais);
                                comunidadAutonoma.setDescripcionComunidad(fila[1]);
                                comunidadAutonoma.setC_comunidad_rpc(fila[3]);
                                comunidadAutonoma.setC_codigo_dir2(new Long(fila[4]));

                                catComunidadAutonomaEjb.persist(comunidadAutonoma);


                                cacheComunidadAutonoma.put(codigoComunidad, comunidadAutonoma);
                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                        cachePais.clear();
                        cachePais = null;

                    }

                    //CATPROVINCIA
                    if (Dir3caibConstantes.CAT_PROVINCIA.equals(nombreFichero)) {


                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {

                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                Long codigoProvincia = new Long(fila[0]);
                                // cargamos el nivel de Administracion correspondiente.
                                CatComunidadAutonoma catComunidadAutonoma = cacheComunidadAutonoma.get(new Long(fila[2]));

                                // Miramos si ya existe la provincia
                                CatProvincia provincia = catProvinciaEjb.findById(codigoProvincia);

                                if (provincia == null) { // Si es nuevo creamos el objeto a introducir
                                    provincia = new CatProvincia();
                                    provincia.setCodigoProvincia(codigoProvincia);
                                }
                                provincia.setComunidadAutonoma(catComunidadAutonoma);
                                provincia.setDescripcionProvincia(fila[1]);


                                catProvinciaEjb.persist(provincia);


                                cacheProvincia.put(codigoProvincia, provincia);
                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                        cacheComunidadAutonoma.clear();
                        cacheComunidadAutonoma = null;

                    }

                    //CATISLA
                    if (Dir3caibConstantes.CAT_ISLA.equals(nombreFichero)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {
                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                Long codigoIsla = new Long(fila[0]);
                                CatIsla isla = catIslaEjb.findById(codigoIsla);

                                if (isla == null) { // Si es nuevo creamos el objeto a introducir
                                    isla = new CatIsla();
                                    isla.setCodigoIsla(codigoIsla);
                                }
                                isla.setDescripcionIsla(fila[1]);
                                CatProvincia provincia;
                                provincia = cacheProvincia.get(new Long(fila[2]));

                                isla.setProvincia(provincia);

                                catIslaEjb.persist(isla);

                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }

                    //CATTIPOCONTACTO
                    if (Dir3caibConstantes.CAT_TIPO_CONTACTO.equals(nombreFichero)) {
                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {

                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                String codigoTipoContacto = fila[0];
                                CatTipoContacto tipoContacto = catTipoContactoEjb.findById(codigoTipoContacto);

                                if (tipoContacto == null) { // Si es nuevo creamos el objeto a introducir
                                    tipoContacto = new CatTipoContacto();
                                    tipoContacto.setCodigoTipoContacto(codigoTipoContacto);
                                }
                                tipoContacto.setDescripcionTipoContacto(fila[1]);

                                catTipoContactoEjb.persist(tipoContacto);

                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }

                    //CATTIPOENTIDADPUBLICA
                    if (Dir3caibConstantes.CAT_TIPO_ENTIDAD_PUBLICA.equals(nombreFichero)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {
                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                String codigoTipoEntidadPublica = fila[0];
                                CatTipoEntidadPublica tipoEntidadPublica = catTipoEntidadPublicaEjb.findById(codigoTipoEntidadPublica);

                                if (tipoEntidadPublica == null) { // Si es nuevo creamos el objeto a introducir
                                    tipoEntidadPublica = new CatTipoEntidadPublica();
                                    tipoEntidadPublica.setCodigoTipoEntidadPublica(codigoTipoEntidadPublica);
                                }
                                tipoEntidadPublica.setDescripcionTipoEntidadPublica(fila[1]);

                                catTipoEntidadPublicaEjb.persist(tipoEntidadPublica);

                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }

                    //CATTIPOUNIDADORGANICA
                    if (Dir3caibConstantes.CAT_TIPO_UNIDAD_ORGANICA.equals(nombreFichero)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {
                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                String codigoTipoUnidadOrganica = fila[0];
                                CatTipoUnidadOrganica tipoUnidadOrganica = catTipoUnidadOrganicaEjb.findById(codigoTipoUnidadOrganica);

                                if (tipoUnidadOrganica == null) { // Si es nuevo creamos el objeto a introducir
                                    tipoUnidadOrganica = new CatTipoUnidadOrganica();
                                    tipoUnidadOrganica.setCodigoTipoUnidadOrganica(codigoTipoUnidadOrganica);
                                }
                                tipoUnidadOrganica.setDescripcionTipoUnidadOrganica(fila[1]);

                                catTipoUnidadOrganicaEjb.persist(tipoUnidadOrganica);

                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }

                    //CATTIPOVIA
                    if (Dir3caibConstantes.CAT_TIPO_VIA.equals(nombreFichero)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {
                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                // Miramos si existe ya en la BD
                                Long codigoTipoVia = new Long(fila[0]);
                                CatTipoVia tipoVia = catTipoViaEjb.findById(codigoTipoVia);
                                if (tipoVia == null) { // Si es nuevo creamos el objeto a introducir
                                    tipoVia = new CatTipoVia();
                                    tipoVia.setCodigoTipoVia(codigoTipoVia);
                                }
                                tipoVia.setDescripcionTipoVia(fila[1].toLowerCase());
                                catTipoViaEjb.persist(tipoVia);
                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }


                    // CATAMBITOTERRITORIAL
                    if (Dir3caibConstantes.CAT_AMBITO_TERRITORIAL.equals(nombreFichero)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {

                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                String codigoAmbito = fila[0];
                                // cargamos el nivel de Administracion correspondiente.
                                CatNivelAdministracion catNivelAdministracion = cacheNivelAdministracion.get(Long.parseLong(fila[2]));

                                // Miramos si ya existe el ambitoTerritorial
                                CatAmbitoTerritorial ambitoTerritorial;
                                ambitoTerritorial = catAmbitoTerritorialEjb.findByPKs(codigoAmbito, Long.parseLong(fila[2]));
                                if (ambitoTerritorial == null) { // Si es nuevo creamos el objeto a introducir
                                    ambitoTerritorial = new CatAmbitoTerritorial();
                                    ambitoTerritorial.setCodigoAmbito(codigoAmbito);
                                    ambitoTerritorial.setNivelAdministracion(catNivelAdministracion);
                                }
                                ambitoTerritorial.setDescripcionAmbito(fila[1]);
                                catAmbitoTerritorialEjb.persist(ambitoTerritorial);
                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }

                    // CAT_SERVICIO
                    if (Dir3caibConstantes.CAT_SERVICIOS.equals(nombreFichero)) {

                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {

                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                String codigoServicio = fila[0];
                                // cargamos el Servicio
                                Servicio servicio = servicioEjb.findById(Long.parseLong(codigoServicio));

                                if (servicio == null) { // Si es nuevo creamos el objeto a introducir
                                    servicio = new Servicio();
                                    servicio.setCodServicio(Long.parseLong(codigoServicio));
                                }
                                servicio.setDescServicio(fila[1]);
                                servicioEjb.persist(servicio);

                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }

                    }

                    //CATLOCALIDAD
                    if (Dir3caibConstantes.CAT_LOCALIDAD.equals(nombreFichero)) {


                        reader.readNext(); //Leemos primera fila que contiene cabeceras para descartarla
                        while ((fila = reader.readNext()) != null) {
                            // Obtenemos codigo y miramos si ya existe en la BD
                            // Creamos la clave compuesta primero.
                            try {
                                Boolean existe;
                                final Long codigoLocalidad = new Long(fila[0]);
                                // cargamos el nivel de Administracion correspondiente.

                                final Long codigoProvincia = new Long(fila[2]);

                                final String codigoEntidadGeografica = fila[3];


                                // Miramos si ya existe el ambitoTerritorial
                                CatLocalidad localidad;

                                localidad = catLocalidadEjb.findByPKs(codigoLocalidad, codigoProvincia,
                                        codigoEntidadGeografica);

                                if (localidad == null) { // Si es nuevo creamos el objeto a introducir
                                    existe = false;
                                    localidad = new CatLocalidad();
                                    localidad.setCodigoLocalidad(codigoLocalidad);

                                    CatProvincia catProvincia = cacheProvincia.get(codigoProvincia);
                                    localidad.setProvincia(catProvincia);

                                    CatEntidadGeografica catEntidadGeografica;
                                    catEntidadGeografica = cacheEntidadGeografica.get(fila[3]);
                                    localidad.setEntidadGeografica(catEntidadGeografica);
                                }else{
                                    existe = true;
                                }


                                //Controlamos que no sea null o cadena vacía, ya que en bd no puede serlo.
                                if (fila[1] == null || fila[1].length() == 0) {
                                    localidad.setDescripcionLocalidad("-");
                                } else {
                                    localidad.setDescripcionLocalidad(fila[1]);
                                }

                                if(existe){
                                    catLocalidadEjb.merge(localidad);
                                }else {
                                    catLocalidadEjb.persistReal(localidad);
                                }


                            } catch (Exception e) {
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

        }

        // Actualizamos la descarga con la fecha de importación.
        descarga.setFechaImportacion(new Date());
        descarga = descargaEjb.merge(descarga);

        results.setDescarga(descarga);

        System.gc();

        return results;
    }

    public CSVReader getCSVReader(String fichero, Long idDescarga) throws FileNotFoundException,
            UnsupportedEncodingException {
        CSVReader reader;
        log.info("");
        log.info("Fichero: " + fichero);
        log.info("------------------------------------");

        // Obtenemos el fichero del sistema de archivos
        File file = new File(Configuracio.getCatalogosPath(idDescarga), fichero);
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
     *
     * @param fechaInicio
     * @param fechaFin
     */
    @Override
    public String[] descargarCatalogoWS(Date fechaInicio, Date fechaFin) throws Exception {

        byte[] buffer = new byte[1024];
        String[] resp = new String[2];


        //Definimos el formato de la fecha para las descargas de los WS.

        // Guardaremos la fecha de la ultima descarga
        Descarga descarga = new Descarga();
        descarga.setTipo(Dir3caibConstantes.CATALOGO);


        //guardamos todas las fechas de la descarga
        if (fechaInicio != null) {
            descarga.setFechaInicio(fechaInicio);

        }
        if (fechaFin != null) {
            descarga.setFechaFin(fechaFin);

        }
        // establecemos la fecha de hoy si las fechas estan vacias.
        Date hoy = new Date();

        if (fechaInicio == null) {
            descarga.setFechaInicio(hoy);
        }
        if (fechaFin == null) {
            descarga.setFechaFin(hoy);
        }

        log.info("Inicio descarga de catalogo directorio común");

        // Guardamos la descarga porque emplearemos el identificador para el nombre del directorio y el archivo.
        descarga = descargaEjb.persist(descarga);
        try {
            // Obtenemos  usuario y rutas para los WS.
            String usuario = Configuracio.getDir3WsUser();
            String password = Configuracio.getDir3WsPassword();
            String ruta = Configuracio.getArchivosPath();
            String rutaCatalogos = Configuracio.getCatalogosPath(descarga.getCodigo());

            String endpoint = Configuracio.getCatalogoEndPoint();

            SC21CTVolcadoCatalogosService catalogoService = new SC21CTVolcadoCatalogosService(new URL(endpoint + "?wsdl"));
            SC21CTVolcadoCatalogos service = catalogoService.getSC21CTVolcadoCatalogos();
            Map<String, Object> reqContext = ((BindingProvider) service).getRequestContext();
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);


            // Invocamos al WS
            RespuestaWS respuestaCsv = service.exportar(usuario, password, "csv", "COMPLETO");
            //RespuestaWS respuestaXml = service.getSC21CTVolcadoCatalogos().exportar(usuario,password,"xml","COMPLETO")
            Base64 decoder = new Base64();

            log.info("Codigo: " + respuestaCsv.getCodigo());
            log.info("Descripcion: " + respuestaCsv.getDescripcion());

            //Montamos la respuesta del ws para controlar los errores a mostrar
            resp[0] = respuestaCsv.getCodigo();
            resp[1] = respuestaCsv.getDescripcion();

            if (!respuestaCsv.getCodigo().trim().equals(Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO)) {
                descargaEjb.remove(descarga);
                return resp;
            }

            //actualizamos el estado de la descarga.
            descarga.setEstado(respuestaCsv.getCodigo());
            descargaEjb.merge(descarga);

            // Definimos el nombre del archivo zip a descargar
            String archivoCatalogoZip = ruta + Dir3caibConstantes.CATALOGOS_ARCHIVO_ZIP + descarga.getCodigo() + ".zip";
            File file = new File(archivoCatalogoZip);

            // guardamos el nuevo fichero descargado
            FileUtils.writeByteArrayToFile(file, decoder.decode(respuestaCsv.getFichero()));


            // Se crea el directorio para el catálogo
            File dir = new File(rutaCatalogos);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    //Borramos la descarga creada previamente.
                    descargaEjb.remove(descarga);
                    log.error(" No se ha podido crear el directorio");
                }
            }

            //Descomprimir el archivo
            ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoCatalogoZip));
            ZipEntry zipEntry = zis.getNextEntry();


            while (zipEntry != null) {

                String fileName = zipEntry.getName();
                File newFile = new File(rutaCatalogos + fileName);

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

            log.info("Fin descarga de catalogo directorio comun");

            return resp;
        } catch (Exception e) {
            descargaEjb.remove(descarga);
            throw new Exception(e.getMessage());
        }

    }


    /* Tarea que en un primer paso descarga los archivos csv del catálogo y posteriormente importa el contenido
     *  en la base de datos, de esta manera realiza el proceso de sincronizacion con Madrid en un sólo
     *  proceso
     *  */
    @Override
    @TransactionTimeout(value = 3600)
    public void importarCatalogoTask() {

        try {
            //Obtenemos las fechas entre las que hay que hacer la descarga

            // obtenemos los datos de la última descarga
            Descarga ultimaDescarga = descargaEjb.ultimaDescargaSincronizada(Dir3caibConstantes.CATALOGO);
            Date fechaInicio = ultimaDescarga.getFechaFin(); // fecha de la ultima descarga

            // obtenemos la fecha de hoy
            Date fechaFin = new Date();

            // Obtiene los archivos csv via WS
            descargarCatalogoWS(fechaInicio, fechaFin);

            // importamos el catálogo a la bd.

            importarCatalogo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
