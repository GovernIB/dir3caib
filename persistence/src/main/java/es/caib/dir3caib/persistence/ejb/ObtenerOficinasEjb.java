package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.persistence.model.ws.OficinaTF;
import org.apache.log4j.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 12/02/14
 */
@Stateless(name = "ObtenerOficinasEJB")
@RunAs("DIR_ADMIN")  //todo añadir seguridad
public class ObtenerOficinasEjb implements ObtenerOficinasLocal {

    protected final Logger log = Logger.getLogger(getClass());
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    protected OficinaLocal oficinaEjb;

    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    protected UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;

    /**
      * Obtiene los datos de una oficina en función del codigo y la fecha de actualización.
      * Si la fecha de actualización es inferior a la de importación con Madrid se supone
      * que no ha cambiado y se envia null( CREO QUE NO SE UTILIZA..)
      * @param codigo Código de la oficina
      * @param fechaActualizacion fecha en la que se realiza la actualizacion.
      */
    /* OJO: Este método no se emplea en REGWEB, ver si se emplea en SISTRA o otra aplicación, TODO si no borrarlo */
    @Override
    public OficinaTF obtenerOficina(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception{

        Oficina oficina = oficinaEjb.findFullById(codigo);


        OficinaTF oficinaTF= null;
        if(fechaActualizacion != null){

          if(fechaActualizacion.before(oficina.getFechaImportacion())){

            // Cogemos solo las relaciones organizativas posteriores a la fecha de sincronizacion
            Set<RelacionOrganizativaOfi> todasRelaciones = new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());
            Set<RelacionOrganizativaOfi> relacionesValidas= new HashSet<RelacionOrganizativaOfi>();
            for(RelacionOrganizativaOfi relOrg: todasRelaciones){
                //TODO revisar esta condicion
              if(relOrg.getUnidad().getFechaExtincion().before(fechaSincronizacion)){
                relacionesValidas.add(relOrg);
              }
            }
            oficina.setOrganizativasOfi(null);
            oficina.setOrganizativasOfi(new ArrayList<RelacionOrganizativaOfi>(relacionesValidas));

            oficinaTF = OficinaTF.generar(oficina);
          }
        }else {
          oficinaTF = OficinaTF.generar(oficina);
        }

        return oficinaTF;
    }


    /**
      * Obtiene todas las oficinas cuyo organismo responsable es el indicado por código(son todas padres e hijas).Solo se envian aquellas
      * que han sido actualizadas.
      * @param codigo Código del organismo
      * @param fechaActualizacion fecha en la que se realiza la actualizacion.
      */
    @Override
    public List<OficinaTF> obtenerArbolOficinas(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception{

        log.info("Inicio obtener Oficinas");
        // Obtenemos todos las unidades vigentes de la unidad Raiz

        List<Unidad> unidades = new ArrayList<Unidad>();
        unidades.add(unidadEjb.obtenerUnidad(codigo));

        unidades.addAll(unidadEjb.obtenerArbol(codigo));
        log.info("Total arbol: " + unidades.size());

        List<Oficina> oficinasCompleto = new ArrayList<Oficina>();

        // Por cada Unidad, obtenemos sus Oficinas
        for (Unidad unidad : unidades) {
            List<Oficina> oficinas = oficinaEjb.obtenerOficinasOrganismo(unidad.getCodigo(), fechaActualizacion, fechaSincronizacion);
            oficinasCompleto.addAll(oficinas);
        }

        // Convertimos las Oficinas en OficinaTF
        List<OficinaTF> arbolTF = new ArrayList<OficinaTF>();
        for (Oficina oficina : oficinasCompleto) {
            arbolTF.add(OficinaTF.generar(oficina));
        }

        return arbolTF;
    }

    /**
      * Obtiene el listado de oficinas Sir de una Unidad
      *
      * @param codigoUnidad Código de la unidad
      *
      */
    @Override
    public List<OficinaTF> obtenerOficinasSIRUnidad(String codigoUnidad) throws Exception{

        List<Oficina> oficinas = oficinaEjb.obtenerOficinasSIRUnidad(codigoUnidad);



        List<OficinaTF> oficinasTF = new ArrayList<OficinaTF>();

        for (Oficina oficina : oficinas) {
            oficinasTF.add(OficinaTF.generar(oficina));
        }

        return oficinasTF;
    }

    /**
     * Método que devuelve la fecha de la última actualización de las unidades
     * @return
     * @throws Exception
     */
    @Override
    public Date obtenerFechaUltimaActualizacion() throws Exception{

        Descarga descarga =  descargaEjb.findByTipo(Dir3caibConstantes.OFICINA);

        return descarga.getFechaImportacion();
    }

}
