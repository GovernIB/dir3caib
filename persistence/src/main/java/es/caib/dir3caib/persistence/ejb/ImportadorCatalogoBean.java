package es.caib.dir3caib.persistence.ejb;

import au.com.bytecode.opencsv.CSVReader;
import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.utils.Configuracio;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;

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


    /**
     * Método que importa el contenido de los archivos del catálogo  descargados previamente a través
     * de los WS.
     */
    @Override
    @TransactionTimeout(value = 3600)
    public void importarCatalogo(Sincronizacion sincronizacion, Boolean localidades) throws Exception {

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

                reader = getCSVReader(fichero, sincronizacion.getCodigo());

                if (reader != null) {
                    
                    // Inicio importación
               
                    //CATENTIDADGEOGRAFICA
                    if (fichero.equals(Dir3caibConstantes.CAT_ENTIDAD_GEOGRAFICA)) {

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
                    if (Dir3caibConstantes.CAT_ESTADO_ENTIDAD.equals(fichero)) {

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
                    if (Dir3caibConstantes.CAT_JERARQUIA_OFICINA.equals(fichero)) {
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
                    if (Dir3caibConstantes.CAT_MOTIVO_EXTINCION.equals(fichero)) {

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
                    if (Dir3caibConstantes.CAT_NIVEL_ADMINISTRACION.equals(fichero)) {
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
                    if (Dir3caibConstantes.CAT_PAIS.equals(fichero)) {

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
                    if (Dir3caibConstantes.CAT_COMUNIDAD_AUTONOMA.equals(fichero)) {
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
                    if (Dir3caibConstantes.CAT_PROVINCIA.equals(fichero)) {


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
                    if (Dir3caibConstantes.CAT_ISLA.equals(fichero)) {

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
                    if (Dir3caibConstantes.CAT_TIPO_CONTACTO.equals(fichero)) {
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
                    if (Dir3caibConstantes.CAT_TIPO_ENTIDAD_PUBLICA.equals(fichero)) {

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
                    if (Dir3caibConstantes.CAT_TIPO_UNIDAD_ORGANICA.equals(fichero)) {

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
                    if (Dir3caibConstantes.CAT_TIPO_VIA.equals(fichero)) {

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
                    if (Dir3caibConstantes.CAT_AMBITO_TERRITORIAL.equals(fichero)) {

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
                    if (Dir3caibConstantes.CAT_SERVICIOS.equals(fichero)) {

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
                    if (Dir3caibConstantes.CAT_LOCALIDAD.equals(fichero) && localidades) {

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
                    log.info(" Acabados procesamiento: " + fichero);
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
                        ex.printStackTrace();
                    }
                }
            }

        }

        System.gc();

    }

    /**
     * 
     * @param fichero
     * @param idSincronizacion
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private CSVReader getCSVReader(String fichero, Long idSincronizacion) throws FileNotFoundException,
            UnsupportedEncodingException {
        CSVReader reader;
        log.info("");
        log.info("Fichero: " + fichero);
        log.info("------------------------------------");

        // Obtenemos el fichero del sistema de archivos
        File file = new File(Configuracio.getCatalogosPath(idSincronizacion), fichero);
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

}
