package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.ws.OficinaTF;

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

    OficinaTF obtenerOficina(String codigo, Date fechaActualizacion, Date fechaSincronizacion, boolean denominacionOficial) throws Exception;

    List<OficinaTF> obtenerArbolOficinas(String codigo, Date fechaActualizacion, Date fechaSincronizacion, boolean denominacionOficial) throws Exception;

    List<Oficina> obtenerArbolOficinasOpenData(String codigo) throws Exception;

    List<OficinaTF> obtenerOficinasSIRUnidad(String codigoUnidad, boolean denominacionOficial) throws Exception;

    Date obtenerFechaUltimaActualizacion() throws Exception;
}
