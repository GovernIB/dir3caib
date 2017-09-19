package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
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

import javax.annotation.security.RolesAllowed;
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
@Stateless(name = "DescargaEJB")
@SecurityDomain("seycon")
@RolesAllowed("DIR_ADMIN")
public class DescargaBean extends BaseEjbJPA<Descarga, Long> implements DescargaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    public static final SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Descarga getReference(Long id) throws Exception {

        return em.getReference(Descarga.class, id);
    }

    @Override
    public Descarga findById(Long id) throws Exception {

        return em.find(Descarga.class, id);
    }

    @Override
    public Descarga ultimaDescarga(String tipo) throws Exception {
        
        Query query = em.createQuery( "select descarga from Descarga as descarga where descarga.tipo= :tipo order by descarga.codigo desc");
        query.setParameter("tipo", tipo);
        List<Descarga> descargas = query.getResultList();
        if(!descargas.isEmpty()){
          return (Descarga) query.getResultList().get(0);
        } else {
          return null;
        } 
    }

    @Override
    public Long totalDescargas(String tipo) throws Exception {

        Query q = em.createQuery("Select count(descarga.codigo) from Descarga as descarga where descarga.tipo = :tipo");
        q.setParameter("tipo", tipo);

        return (Long) q.getSingleResult();

    }

    public Descarga ultimaDescargaSincronizada(String tipo) throws Exception {

        Query query = em.createQuery( "select descarga from Descarga as descarga where descarga.tipo = :tipo and descarga.estado = :correcto " +
                " order by descarga.codigo desc");

        query.setParameter("tipo", tipo);
        query.setParameter("correcto", Dir3caibConstantes.SINCRONIZACION_CORRECTA);

        List<Descarga> descargas = query.getResultList();
        if(!descargas.isEmpty()){
            return (Descarga) query.getResultList().get(0);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Descarga> getAll() throws Exception {

        return  em.createQuery("Select descarga from Descarga as descarga order by descarga.codigo").getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Descarga> getAllByTipo(String tipo) throws Exception {

        Query query =em.createQuery("Select descarga from Descarga as descarga where descarga.tipo=:tipo order by descarga.codigo desc");
        query.setParameter("tipo", tipo);
        return query.getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(descarga.codigo) from Descarga as descarga");

        return (Long) q.getSingleResult();
    }


    public Long getTotalByTipo(String tipo) throws Exception {

        Query q = em.createQuery("Select count(descarga.codigo) from Descarga as descarga where descarga.tipo=:tipo");
        q.setParameter("tipo", tipo);

        return (Long) q.getSingleResult();
    }

    @Override
    public List<Descarga> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select descarga from Descarga as descarga order by descarga.codigo");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    @Override
    public List<Descarga> getPaginationByTipo(int inicio, String tipo) throws Exception {

        Query q = em.createQuery("Select descarga from Descarga as descarga where descarga.tipo=:tipo order by descarga.codigo desc");
        q.setParameter("tipo", tipo);
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    @Override
    public void deleteAllByTipo(String tipo) throws Exception {
        Query query = em.createQuery( "delete from Descarga as descarga where descarga.tipo=:tipo ");
        query.setParameter("tipo", tipo);
        query.executeUpdate();
    }

    @Override
    public void actualizarEstado(Long codigo, String estado) throws Exception {

        Query query = em.createQuery( "update Descarga set estado = :estado where codigo = :codigo ");
        query.setParameter("codigo", codigo);
        query.setParameter("estado", estado);
        query.executeUpdate();
    }

    @Override
    public Descarga descargarDirectorioWS(String tipo, Date fechaInicio, Date fechaFin) throws Exception{

        Descarga descarga = new Descarga(tipo);

        byte[] buffer = new byte[1024];
        Base64 decoder = new Base64();

        // Datos comunes para invocar el WS del Directorio Común
        String usuario = Configuracio.getDir3WsUser();
        String password = Configuracio.getDir3WsPassword();
        String endPoint;
        String codigoRespuesta = "";
        String ficheroRespuesta = "";

        // Directorios
        String archivosPath = Configuracio.getArchivosPath();
        String nombreZip = "";
        String directorioDescomprimidos = "";

        // Establecemos las fechas para la descarga incremental o inicial
        if (fechaInicio != null) {
            descarga.setFechaInicio(fechaInicio);
        }else{
            descarga.setFechaInicio(null);
        }

        if (fechaFin != null) {
            descarga.setFechaFin(fechaFin);
        }else{
            descarga.setFechaFin(new Date());
        }

    /* El funcionamiento de los ws de madrid no permiten que la fecha de inicio sea null si la fecha fin es distinta de null.
       Descarga incremental: Hay dos opciones, incluir solo la fecha de inicio que devolverá la información que existe
       desde la fecha indicada hasta la fecha en la que se realiza la petición y la otra opción es incluir
       fecha de inicio y fecha fin. Esta devuelve la información añadida o modificada entre esas dos fechas.*/


        // Guardamos la descarga porque emplearemos el identificador para el nombre del directorio y el archivo.
        descarga = persist(descarga);

        try {

            if(Dir3caibConstantes.UNIDAD.equals(tipo)){

                // Definimos el nombre del archivo zip a guardar y el directorio donde se descomprimirá
                nombreZip = archivosPath + Dir3caibConstantes.UNIDADES_ARCHIVO_ZIP + descarga.getCodigo() + ".zip";
                directorioDescomprimidos = Configuracio.getUnidadesPath(descarga.getCodigo());

                // Obtenemos el EndPoint del WS
                endPoint = Configuracio.getUnidadEndPoint();

                // Service
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

                if (fechaInicio != null) {
                    parametros.setFechaInicio(formatoFecha.format(fechaInicio));
                }
                if (fechaFin != null) {
                    parametros.setFechaFin(formatoFecha.format(fechaFin));
                }

                // Invocamos el WS
                RespuestaWS respuesta = service.exportar(parametros);

                log.info("Respuesta WS unidades DIR3: " + respuesta.getCodigo() + " - " + respuesta.getDescripcion());

                codigoRespuesta = respuesta.getCodigo();
                ficheroRespuesta = respuesta.getFichero();


            }else if(Dir3caibConstantes.OFICINA.equals(tipo)){

                // Definimos el nombre del archivo zip a guardar y el directorio donde se descomprimirá
                nombreZip = archivosPath + Dir3caibConstantes.OFICINAS_ARCHIVO_ZIP + descarga.getCodigo() + ".zip";
                directorioDescomprimidos = Configuracio.getOficinasPath(descarga.getCodigo());

                // Obtenemos el EndPoint del WS
                endPoint = Configuracio.getOficinaEndPoint();

                // Service
                SD02OFDescargaOficinasService oficinasService = new SD02OFDescargaOficinasService(new URL(endPoint + "?wsdl"));
                SD02OFDescargaOficinas service = oficinasService.getSD02OFDescargaOficinas();
                Map<String, Object> reqContext = ((BindingProvider) service).getRequestContext();
                reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);

                // Establecemos los parametros necesarios para el WS
                OficinasWs parametros = new OficinasWs();
                parametros.setUsuario(usuario);
                parametros.setClave(password);
                parametros.setFormatoFichero(es.caib.dir3caib.ws.dir3.oficina.client.FormatoFichero.CSV);
                parametros.setTipoConsulta(TipoConsultaOF.COMPLETO);

                // definimos fechas
                if (fechaInicio != null) {
                    parametros.setFechaInicio(formatoFecha.format(fechaInicio));
                }
                if (fechaFin != null) {
                    parametros.setFechaFin(formatoFecha.format(fechaFin));
                }

                // Invocamos el WS
                es.caib.dir3caib.ws.dir3.oficina.client.RespuestaWS respuesta = service.exportar(parametros);

                log.info("Respuesta WS oficinas DIR3: " + respuesta.getCodigo() + " - " + respuesta.getDescripcion());

                codigoRespuesta = respuesta.getCodigo();
                ficheroRespuesta = respuesta.getFichero();


            }else if(Dir3caibConstantes.CATALOGO.equals(tipo)){

                // Definimos el nombre del archivo zip a guardar y el directorio donde se descomprimirá
                nombreZip = archivosPath + Dir3caibConstantes.CATALOGOS_ARCHIVO_ZIP + descarga.getCodigo() + ".zip";
                directorioDescomprimidos = Configuracio.getCatalogosPath(descarga.getCodigo());

                // Obtenemos el EndPoint del WS
                endPoint = Configuracio.getCatalogoEndPoint();

                // Service
                SC21CTVolcadoCatalogosService catalogoService = new SC21CTVolcadoCatalogosService(new URL(endPoint + "?wsdl"));
                SC21CTVolcadoCatalogos service = catalogoService.getSC21CTVolcadoCatalogos();
                Map<String, Object> reqContext = ((BindingProvider) service).getRequestContext();
                reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);

                // Invocamos al WS
                es.caib.dir3caib.ws.dir3.catalogo.client.RespuestaWS respuesta = service.exportar(usuario, password, "csv", "COMPLETO");

                log.info("Respuesta Ws catalogo: " + respuesta.getCodigo() + " - " + respuesta.getDescripcion());

                codigoRespuesta = respuesta.getCodigo();
                ficheroRespuesta = respuesta.getFichero();

            }

            // Si la descarga ha sido Correcta
            if(codigoRespuesta.trim().equals(Dir3caibConstantes.CODIGO_RESPUESTA_CORRECTO)){

                // Actualizamos el estado de la Descarga
                descarga.setEstado(Dir3caibConstantes.SINCRONIZACION_DESCARGADA);
                merge(descarga);

                // Guardamos el archivo descargado en un zip en la ruta indicada
                File file = new File(nombreZip);
                FileUtils.writeByteArrayToFile(file, decoder.decode(ficheroRespuesta));

                // Se crea un directorio donde descomprimiremos el zip
                File dir = new File(directorioDescomprimidos);
                if (!dir.exists()) { //Si no existe el directorio
                    if (!dir.mkdirs()) {
                        //Borramos la descarga creada previamente.
                        remove(descarga);
                        log.error(" No se ha podido crear el directorio");
                        return null;
                    }
                }

                // Descomprimimos el archivo en el directorio creado anteriormente
                ZipInputStream zis = new ZipInputStream(new FileInputStream(nombreZip));
                ZipEntry zipEntry = zis.getNextEntry();

                while (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    File newFile = new File(directorioDescomprimidos + fileName);

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


            }else if(codigoRespuesta.trim().equals(Dir3caibConstantes.CODIGO_RESPUESTA_VACIO)){

                // Actualizamos el estado de la Descarga
                descarga.setEstado(Dir3caibConstantes.SINCRONIZACION_VACIA);
                merge(descarga);

            }else{

                // La descarga ha ido mal, la eliminamos
                remove(descarga);
                return null;
            }

            return descarga;

        } catch (Exception e) { //si hay algun problema, eliminamos la descarga
            remove(descarga);
            throw new Exception(e.getMessage());
        }
    }
}
