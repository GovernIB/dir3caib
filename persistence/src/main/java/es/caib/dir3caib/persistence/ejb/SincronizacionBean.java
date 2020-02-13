package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Sincronizacion;
import es.caib.dir3caib.persistence.utils.MailUtils;
import es.caib.dir3caib.utils.Configuracio;
import es.caib.dir3caib.ws.dir3.catalogo.client.SC21CTVolcadoCatalogos;
import es.caib.dir3caib.ws.dir3.catalogo.client.SC21CTVolcadoCatalogosService;
import es.caib.dir3caib.ws.dir3.oficina.client.OficinasWs;
import es.caib.dir3caib.ws.dir3.oficina.client.SD02OFDescargaOficinas;
import es.caib.dir3caib.ws.dir3.oficina.client.SD02OFDescargaOficinasService;
import es.caib.dir3caib.ws.dir3.oficina.client.TipoConsultaOF;
import es.caib.dir3caib.ws.dir3.unidad.client.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.ws.BindingProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Stateless(name = "SincronizacionEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class SincronizacionBean extends BaseEjbJPA<Sincronizacion, Long> implements SincronizacionLocal{

    protected final Logger log = Logger.getLogger(getClass());

    public static final SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @EJB(mappedName = "dir3caib/ImportadorUnidadesEJB/local")
    private ImportadorUnidadesLocal importadorUnidades;

    @EJB(mappedName = "dir3caib/ImportadorOficinasEJB/local")
    private ImportadorOficinasLocal importadorOficinas;

    @EJB(mappedName = "dir3caib/ImportadorCatalogoEJB/local")
    private ImportadorCatalogoLocal importadorCatalogo;

    @PersistenceContext(unitName="dir3caib")
    private EntityManager em;

    @Override
    public Sincronizacion getReference(Long id) throws Exception {

        return em.getReference(Sincronizacion.class, id);
    }

    @Override
    public Sincronizacion findById(Long id) throws Exception {

        return em.find(Sincronizacion.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Sincronizacion ultimaSincronizacionByTipo(String tipo) throws Exception {
        
        Query query = em.createQuery( "select sincronizacion from Sincronizacion as sincronizacion where sincronizacion.tipo= :tipo order by sincronizacion.codigo desc");
        query.setParameter("tipo", tipo);
        List<Sincronizacion> sincronizacions = query.getResultList();
        if(!sincronizacions.isEmpty()){
          return (Sincronizacion) query.getResultList().get(0);
        } else {
          return null;
        } 
    }

    @Override
    @SuppressWarnings("unchecked")
    public Sincronizacion ultimaSincronizacionCorrecta(String tipo) throws Exception {

        Query query = em.createQuery( "select sincronizacion from Sincronizacion as sincronizacion where sincronizacion.tipo = :tipo and sincronizacion.estado = :correcto " +
                " order by sincronizacion.codigo desc");

        query.setParameter("tipo", tipo);
        query.setParameter("correcto", Dir3caibConstantes.SINCRONIZACION_CORRECTA);

        List<Sincronizacion> sincronizacions = query.getResultList();
        if(!sincronizacions.isEmpty()){
            return (Sincronizacion) query.getResultList().get(0);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Sincronizacion ultimaSincronizacionCompletada(String tipo) throws Exception {

        Query query = em.createQuery( "select sincronizacion from Sincronizacion as sincronizacion where sincronizacion.tipo = :tipo " +
                "and (sincronizacion.estado = :correcto or sincronizacion.estado = :vacia) " +
                " order by sincronizacion.codigo desc");

        query.setParameter("tipo", tipo);
        query.setParameter("correcto", Dir3caibConstantes.SINCRONIZACION_CORRECTA);
        query.setParameter("vacia", Dir3caibConstantes.SINCRONIZACION_VACIA);

        List<Sincronizacion> sincronizacions = query.getResultList();
        if(!sincronizacions.isEmpty()){
            return (Sincronizacion) query.getResultList().get(0);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Sincronizacion> getAll() throws Exception {

        return  em.createQuery("Select sincronizacion from Sincronizacion as sincronizacion order by sincronizacion.codigo").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(sincronizacion.codigo) from Sincronizacion as sincronizacion");

        return (Long) q.getSingleResult();
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Sincronizacion> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select sincronizacion from Sincronizacion as sincronizacion order by sincronizacion.codigo desc");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }


    @Override
    public void deleteAllByTipo(String tipo) throws Exception {
        Query query = em.createQuery( "delete from Sincronizacion as sincronizacion where sincronizacion.tipo=:tipo ");
        query.setParameter("tipo", tipo);
        query.executeUpdate();
    }

    @Override
    public void actualizarEstado(Long codigo, Long estado) throws Exception {

        Query query = em.createQuery( "update Sincronizacion set estado = :estado where codigo = :codigo ");
        query.setParameter("codigo", codigo);
        query.setParameter("estado", estado);
        query.executeUpdate();
    }

    @Override
    public Sincronizacion descargarDirectorioWS(Date fechaInicio, Date fechaFin) throws Exception{

        log.info("Iniciamos la descarga de las Unidades y Oficinas");

        Sincronizacion sincronizacion = new Sincronizacion(Dir3caibConstantes.UNIDADES_OFICINAS);

        // Datos comunes para invocar el WS del Directorio Común
        String usuario = Configuracio.getDir3WsUser();
        String password = Configuracio.getDir3WsPassword();
        String codigoUnidades = "";
        String codigoOficinas = "";
        String ficheroUnidades = "";
        String ficheroOficinas = "";

        // Directorios
        String sincronizacionPath = "";
        String unidadesZip = "";
        String oficinasZip = "";

        // Establecemos las fechas para la sincronizacion incremental o inicial
        if (fechaInicio != null) {
            sincronizacion.setFechaInicio(fechaInicio);
        }else{
            sincronizacion.setFechaInicio(null);
        }

        if (fechaFin != null) {
            sincronizacion.setFechaFin(fechaFin);
        }else{
            sincronizacion.setFechaFin(new Date());
        }

    /* El funcionamiento de los ws de madrid no permiten que la fecha de inicio sea null si la fecha fin es distinta de null.
       Sincronizacion incremental: Hay dos opciones, incluir solo la fecha de inicio que devolverá la información que existe
       desde la fecha indicada hasta la fecha en la que se realiza la petición y la otra opción es incluir
       fecha de inicio y fecha fin. Esta devuelve la información añadida o modificada entre esas dos fechas.*/


        // Guardamos la sincronizacion porque emplearemos el identificador para el nombre del directorio y el archivo.
        sincronizacion = persist(sincronizacion);

        try {

            // Definimos el nombre del archivo zip a guardar y el directorio donde se descomprimirá
            sincronizacionPath = Configuracio.getSincronizacionPath(sincronizacion.getCodigo());
            String directorioPath = sincronizacionPath +"directorio/";

            unidadesZip = sincronizacionPath + Dir3caibConstantes.UNIDADES_ARCHIVO_ZIP + sincronizacion.getCodigo() + ".zip";
            oficinasZip = sincronizacionPath + Dir3caibConstantes.OFICINAS_ARCHIVO_ZIP + sincronizacion.getCodigo() + ".zip";

            // Obtenemos el EndPoint del WS
            String endPointUnidades = Configuracio.getUnidadEndPoint();
            String endPointOficinas = Configuracio.getOficinaEndPoint();

            // Service Unidades
            SD01UNDescargaUnidades serviceUnidades = new SD01UNDescargaUnidadesService(new URL(endPointUnidades + "?wsdl")).getSD01UNDescargaUnidades();
            Map<String, Object> reqContextUnidades = ((BindingProvider) serviceUnidades).getRequestContext();
            reqContextUnidades.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointUnidades);

            // Service Oficinas
            SD02OFDescargaOficinas serviceOficinas = new SD02OFDescargaOficinasService(new URL(endPointOficinas + "?wsdl")).getSD02OFDescargaOficinas();
            Map<String, Object> reqContextOficinas = ((BindingProvider) serviceOficinas).getRequestContext();
            reqContextOficinas.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointOficinas);

            // Establecemos parametros de serviceUnidades
            UnidadesWs parametrosUnidades = new UnidadesWs();
            parametrosUnidades.setUsuario(usuario);
            parametrosUnidades.setClave(password);
            parametrosUnidades.setFormatoFichero(FormatoFichero.CSV);
            parametrosUnidades.setTipoConsulta(TipoConsultaUO.COMPLETO);

            // Establecemos parametros de serviceOficinas
            OficinasWs parametrosOficinas = new OficinasWs();
            parametrosOficinas.setUsuario(usuario);
            parametrosOficinas.setClave(password);
            parametrosOficinas.setFormatoFichero(es.caib.dir3caib.ws.dir3.oficina.client.FormatoFichero.CSV);
            parametrosOficinas.setTipoConsulta(TipoConsultaOF.COMPLETO);

            // Establecemos parametros comunes
            if (fechaInicio != null) {
                parametrosUnidades.setFechaInicio(formatoFecha.format(fechaInicio));
                parametrosOficinas.setFechaInicio(formatoFecha.format(fechaInicio));
            }
            if (fechaFin != null) {
                parametrosUnidades.setFechaFin(formatoFecha.format(fechaFin));
                parametrosOficinas.setFechaFin(formatoFecha.format(fechaFin));
            }

            // Invocamos el WS de Unidades
            RespuestaWS respuestaUnidades = serviceUnidades.exportar(parametrosUnidades);

            log.info("Respuesta WS unidades DIR3: " + respuestaUnidades.getCodigo() + " - " + respuestaUnidades.getDescripcion());

            codigoUnidades = respuestaUnidades.getCodigo().trim();
            ficheroUnidades = respuestaUnidades.getFichero();

            // Invocamos el WS de Oficinas
            es.caib.dir3caib.ws.dir3.oficina.client.RespuestaWS respuestaOficinas = serviceOficinas.exportar(parametrosOficinas);

            log.info("Respuesta WS oficinas DIR3: " + respuestaOficinas.getCodigo() + " - " + respuestaOficinas.getDescripcion());

            codigoOficinas = respuestaOficinas.getCodigo().trim();
            ficheroOficinas = respuestaOficinas.getFichero();

            // Procesamos los Archivos zip recibidos
            if(codigoUnidades.equals(Dir3caibConstantes.CODIGO_VACIO) && codigoOficinas.equals(Dir3caibConstantes.CODIGO_VACIO)){

                // Actualizamos el estado de la Sincronizacion
                sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_VACIA);
                sincronizacion.setFechaImportacion(new Date());
                merge(sincronizacion);

            }else if( (codigoUnidades.equals(Dir3caibConstantes.CODIGO_CORRECTO) || codigoUnidades.equals(Dir3caibConstantes.CODIGO_VACIO)) &&
                (codigoOficinas.equals(Dir3caibConstantes.CODIGO_CORRECTO) || codigoOficinas.equals(Dir3caibConstantes.CODIGO_VACIO))){

                // Actualizamos el estado de la Sincronizacion
                sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_DESCARGADA);
                merge(sincronizacion);

                try{

                    descomprimirZip(unidadesZip, ficheroUnidades, directorioPath);
                    descomprimirZip(oficinasZip, ficheroOficinas, directorioPath);

                }catch (Exception e){
                    log.info("Ha ocurrido un error descomprimiendo los archivos de las unidades/oficinas obtenidos del WS");
                    //Borramos la sincronizacion creada previamente.
                    remove(sincronizacion);
                }

            }else{
                // La sincronizacion ha ido mal, la eliminamos
                remove(sincronizacion);
                MailUtils.envioEmailErrorSincronizacion();
                return null;
            }

            log.info("Fin de la descarga de las Unidades y Oficinas");

            return sincronizacion;

        } catch (Exception e) { //si hay algun problema, eliminamos la sincronizacion
            MailUtils.envioEmailErrorSincronizacion();
            remove(sincronizacion);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Sincronizacion descargarCatalogoWS() throws Exception{

        log.info("Iniciamos la descarga del catalogo");

        Sincronizacion sincronizacion = new Sincronizacion(Dir3caibConstantes.CATALOGO);

        // Datos comunes para invocar el WS del Directorio Común
        String usuario = Configuracio.getDir3WsUser();
        String password = Configuracio.getDir3WsPassword();

        String codigoCatalogos = "";
        String ficheroCatalogos = "";

        // Directorios
        String sincronizacionPath = "";
        String catalogosZip = "";

        // Establecemos las fechas para la sincronizacion
        sincronizacion.setFechaInicio(new Date());
        sincronizacion.setFechaFin(new Date());

        // Guardamos la sincronizacion porque emplearemos el identificador para el nombre del directorio y el archivo.
        sincronizacion = persist(sincronizacion);

        try {

            // Definimos el nombre del archivo zip a guardar y el directorio donde se descomprimirá
            sincronizacionPath = Configuracio.getSincronizacionPath(sincronizacion.getCodigo());
            String catalogoPath = sincronizacionPath +"catalogos/";
            catalogosZip = sincronizacionPath + Dir3caibConstantes.CATALOGOS_ARCHIVO_ZIP + sincronizacion.getCodigo() + ".zip";

            // Obtenemos el EndPoint del WS
            String endPointCatalogos = Configuracio.getCatalogoEndPoint();

            // Service
            SC21CTVolcadoCatalogos catalogoService = new SC21CTVolcadoCatalogosService(new URL(endPointCatalogos + "?wsdl")).getSC21CTVolcadoCatalogos();
            Map<String, Object> reqContextCatalogos = ((BindingProvider) catalogoService).getRequestContext();
            reqContextCatalogos.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointCatalogos);

            // Invocamos al WS
            es.caib.dir3caib.ws.dir3.catalogo.client.RespuestaWS respuesta = catalogoService.exportar(usuario, password, "csv", "COMPLETO");

            log.info("Respuesta Ws catalogo: " + respuesta.getCodigo() + " - " + respuesta.getDescripcion());

            codigoCatalogos = respuesta.getCodigo().trim();
            ficheroCatalogos = respuesta.getFichero();

            // Procesamos los Archivoz zip recibidos
            if(codigoCatalogos.equals(Dir3caibConstantes.CODIGO_CORRECTO)){

                // Actualizamos el estado de la Sincronizacion
                sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_DESCARGADA);
                merge(sincronizacion);

                try{

                    descomprimirZip(catalogosZip, ficheroCatalogos, catalogoPath);

                }catch (Exception e){
                    log.info("Ha ocurrido un error descomprimiendo los archivos del catalogo obtenidos del WS");
                    //Borramos la sincronizacion creada previamente.
                    remove(sincronizacion);
                }

            }else if(codigoCatalogos.equals(Dir3caibConstantes.CODIGO_VACIO)){

                // Actualizamos el estado de la Sincronizacion
                sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_VACIA);
                merge(sincronizacion);

            }else{
                // La sincronizacion ha ido mal, lo informamos mediante el estado
                sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_ERROR_DESCARGA);
                merge(sincronizacion);
                MailUtils.envioEmailErrorSincronizacion();
                return sincronizacion;
            }

            log.info("Fin de la descarga del catalogo");

            return sincronizacion;

        } catch (Exception e) { //si hay algun problema, modificamos el estadod e la descarga
            log.info("Excepcion en la descarga del catalogo");
            MailUtils.envioEmailErrorSincronizacion();
            sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_ERROR_DESCARGA);
            merge(sincronizacion);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void eliminarSincronizacion(Sincronizacion sincronizacion) throws Exception{

        String sincronizacionPath = Configuracio.getSincronizacionPath(sincronizacion.getCodigo());

        try{
            // Eliminamos todas las descargas realizadas
            File directorio = new File(sincronizacionPath);
            FileUtils.cleanDirectory(directorio);
            FileUtils.deleteDirectory(directorio);
        }catch (Exception e){
            log.info("El directorio no existe: " + sincronizacionPath);
            e.printStackTrace();
        }

        remove(sincronizacion);

    }

    /**
     *
     * @param sincronizacion
     * @throws Exception
     */
    @Override
    @TransactionTimeout(value = 40000)
    public Sincronizacion importarUnidadesOficinas(Sincronizacion sincronizacion) throws Exception{

        // Importamos las Unidades y Oficinas
        importadorUnidades.importarUnidades(sincronizacion);
        importadorOficinas.importarOficinas(sincronizacion);

        // Si el proceso ha sido correcto, actualizamos el estado
        sincronizacion.setFechaImportacion(new Date());
        sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_CORRECTA);

        return merge(sincronizacion);
    }

    /**
     *
     * @param sincronizacion
     * @throws Exception
     */
    @Override
    @TransactionTimeout(value = 3600)
    public Sincronizacion importarCatalogo(Sincronizacion sincronizacion, Boolean localidades) throws Exception{

        // Importamos el Catalogo
        importadorCatalogo.importarCatalogo(sincronizacion, localidades);

        // Si el proceso ha sido correcto, actualizamos el estado
        sincronizacion.setFechaImportacion(new Date());
        sincronizacion.setEstado(Dir3caibConstantes.SINCRONIZACION_CORRECTA);

        return merge(sincronizacion);
    }

    @Override
    @TransactionTimeout(value = 3600)
    public Sincronizacion sincronizarCatalogo() throws Exception{

        Sincronizacion sincroCatalogo = null;

        try{
            Sincronizacion ultimaSincro = ultimaSincronizacionCompletada(Dir3caibConstantes.CATALOGO);

            // Descarga del catálogo DIR3
            sincroCatalogo = descargarCatalogoWS();

            // Importamos Catálogo
            if (sincroCatalogo != null && sincroCatalogo.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {

                if(ultimaSincro != null){ // Si ya hay una sincro previa, no sincronizacimos las Localidades
                    sincroCatalogo = importarCatalogo(sincroCatalogo, false);
                }else{
                    sincroCatalogo = importarCatalogo(sincroCatalogo, true);
                }

            }

        }catch (Exception e){

            // Si ha habido un Error en la sincronización, modificamos el estado de la descarga
            if(sincroCatalogo != null && sincroCatalogo.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)){
                try {
                    actualizarEstado(sincroCatalogo.getCodigo(), Dir3caibConstantes.SINCRONIZACION_ERRONEA);
                } catch (Exception ex2) {
                    MailUtils.envioEmailErrorSincronizacion();
                    ex2.printStackTrace();
                }
            }
            MailUtils.envioEmailErrorSincronizacion();
            e.printStackTrace();
            throw e;
        }

        return sincroCatalogo;
    }

    @Override
    @TransactionTimeout(value = 50000)
    public List<Sincronizacion> sincronizarDirectorio() throws Exception{

        Sincronizacion sincroCatalogo = sincronizarCatalogo();
        Sincronizacion sincroDirectorio = sincronizarUnidadesOficinas();

        return Arrays.asList(sincroCatalogo,sincroDirectorio);

    }

    @Override
    @TransactionTimeout(value = 50000)
    public Sincronizacion sincronizarUnidadesOficinas() throws Exception{

        Sincronizacion sincroUnidadesOficinas = null;

        try{

            // Obtenemos la fecha de la ultima descarga/sincronizacion
            Sincronizacion ultimaSincro = ultimaSincronizacionCompletada(Dir3caibConstantes.UNIDADES_OFICINAS);

            // Descarga de directorio DIR3
            if(ultimaSincro != null){
                sincroUnidadesOficinas = descargarDirectorioWS(ultimaSincro.getFechaFin(), new Date());
            } else {//Es una descarga inicial
                sincroUnidadesOficinas = descargarDirectorioWS(null, null);
            }

            // Importamos Unidades y Oficinas
            if (sincroUnidadesOficinas != null && sincroUnidadesOficinas.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)) {

                sincroUnidadesOficinas = importarUnidadesOficinas(sincroUnidadesOficinas);
            }

        }catch (Exception e){

            // Si ha habido un Error en la sincronización, modificamos el estado de la descarga
            if(sincroUnidadesOficinas != null && sincroUnidadesOficinas.getEstado().equals(Dir3caibConstantes.SINCRONIZACION_DESCARGADA)){
                try {
                    actualizarEstado(sincroUnidadesOficinas.getCodigo(), Dir3caibConstantes.SINCRONIZACION_ERRONEA);

                } catch (Exception ex1) {
                    MailUtils.envioEmailErrorSincronizacion();
                    ex1.printStackTrace();
                }
            }
            MailUtils.envioEmailErrorSincronizacion();
            e.printStackTrace();
            throw e;
        }

        return sincroUnidadesOficinas;
    }


    /**
     * Crea y descomprime el fichero recibido desde los WS de DIR3
     * @param nombreZip
     * @param ficheroRespuesta
     * @param directorio
     * @throws Exception
     */
    private void descomprimirZip(String nombreZip, String ficheroRespuesta, String directorio) throws Exception{

        byte[] buffer = new byte[1024];
        Base64 decoder = new Base64();

        // Guardamos el archivo sincronizaciondo en un zip en la ruta indicada
        File file = new File(nombreZip);
        FileUtils.writeByteArrayToFile(file, decoder.decode(ficheroRespuesta));

        // Se crea un directorio donde descomprimiremos el zip
        File dir = new File(directorio);
        if (!dir.exists()) { //Si no existe el directorio
            if (!dir.mkdirs()) {
                log.error(" No se ha podido crear el directorio");
                throw new Exception("No se ha podido crear el directorio");
            }
        }

        // Descomprimimos el archivo en el directorio creado anteriormente
        ZipInputStream zis = new ZipInputStream(new FileInputStream(nombreZip));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            String fileName = zipEntry.getName();
            File newFile = new File(directorio + fileName);

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
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long contarSincronizaciones(String tipo) throws Exception {

        Query query = em.createQuery( "select count(sincronizacion.codigo) from Sincronizacion as sincronizacion where sincronizacion.tipo = :tipo");

        query.setParameter("tipo", tipo);

        return (Long) query.getSingleResult();
    }
}
