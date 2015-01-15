package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ws.OficinaTF;

import javax.ejb.Local;
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

    public OficinaTF obtenerOficina(String codigo, String fechaActualizacion, String fechaSincronizacion) throws Exception;

    public List<OficinaTF> obtenerArbolOficinas(String codigo, String fechaActualizacion, String fechaSincronizacion) throws Exception;

    public List<OficinaTF> obtenerOficinasSIRUnidad(String codigoUnidad) throws Exception;
}
