package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.ws.OficinaTF;
import es.caib.dir3caib.persistence.model.ws.v2.OficinaWs;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Created by Fundació BIT.
 * Ejb que define los métodos de oficinas que se consumen via ws posteriormente por regweb
 *
 * @author earrivi
 * Date: 12/02/14
 */
@Local
public interface ObtenerOficinasLocal {

    /**
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @param denominacionOficial
     * @return
     * @throws Exception
     */
    OficinaTF obtenerOficinaTF(String codigo, Date fechaActualizacion, Date fechaSincronizacion, boolean denominacionOficial) throws Exception;

    /**
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @return
     * @throws Exception
     */
    OficinaWs obtenerOficinaWs(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

    /**
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @param denominacionOficial
     * @return
     * @throws Exception
     */
    List<OficinaTF> obtenerArbolOficinasTF(String codigo, Date fechaActualizacion, Date fechaSincronizacion, boolean denominacionOficial) throws Exception;

    /**
     * @param codigo
     * @param fechaActualizacion
     * @param fechaSincronizacion
     * @return
     * @throws Exception
     */
    List<OficinaWs> obtenerArbolOficinasWs(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

    /**
     * @param codigo
     * @param estado
     * @return
     * @throws Exception
     */
    List<Oficina> obtenerArbolOficinasOpenData(String codigo, String estado) throws Exception;

    /**
     * @param codigoUnidad
     * @param denominacionOficial
     * @return
     * @throws Exception
     */
    List<OficinaTF> obtenerOficinasSIRUnidadTF(String codigoUnidad, boolean denominacionOficial) throws Exception;

    /**
     * @param codigoUnidad
     * @return
     * @throws Exception
     */
    List<OficinaWs> obtenerOficinasSIRUnidadWs(String codigoUnidad) throws Exception;

    /**
     * @return
     * @throws Exception
     */
    Date obtenerFechaUltimaActualizacion() throws Exception;
}
