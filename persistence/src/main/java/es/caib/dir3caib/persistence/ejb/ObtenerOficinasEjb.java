package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi;
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

    /**
      * Obtiene los datos de una oficina en función del codigo y la fecha de actualización.
      * Si la fecha de actualización es inferior a la de importación con Madrid se supone
      * que no ha cambiado y se envia null
      * @param codigo Código de la oficina
      * @param fechaActualizacion fecha en la que se realiza la actualizacion.
      */
    @Override
    public OficinaTF obtenerOficina(String codigo, String fechaActualizacion, String fechaSincronizacion) throws Exception{

        Oficina oficina = oficinaEjb.findFullById(codigo);

        OficinaTF oficinaTF= null;
        if(fechaActualizacion != null){
          Date fechaAct = formatoFecha.parse(fechaActualizacion);
          Date fechaSincro = formatoFecha.parse(fechaSincronizacion);
          if(fechaAct.before(oficina.getFechaImportacion())){
            // Cogemos solo las relaciones organizativas posteriores a la fecha de sincronizacion
            Set<RelacionOrganizativaOfi> todasRelaciones = new HashSet<RelacionOrganizativaOfi>(oficina.getOrganizativasOfi());
            Set<RelacionOrganizativaOfi> relacionesValidas= new HashSet<RelacionOrganizativaOfi>();
            for(RelacionOrganizativaOfi relOrg: todasRelaciones){
              if(relOrg.getUnidad().getFechaExtincion().before(fechaSincro)){
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
      * Obtiene el arbol de oficinas que dependen de un organismo.Solo se envian aquellas
      * que han sido actualizadas.
      * @param codigo Código del organismo
      * @param fechaActualizacion fecha en la que se realiza la actualizacion.
      */
    @Override
    public List<OficinaTF> obtenerArbolOficinas(String codigo, String fechaActualizacion, String fechaSincronizacion) throws Exception{

        List<Oficina> oficinas = oficinaEjb.obtenerOficinasOrganismo(codigo, fechaActualizacion,fechaSincronizacion);

        List<Oficina> oficinasCompleto = new ArrayList<Oficina>(oficinas);

        for (Oficina oficina : oficinas) {
            oficinasCompleto.addAll(oficinaEjb.obtenerArbolOficinas(oficina.getCodigo(),fechaActualizacion,fechaSincronizacion));
        }

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

}
