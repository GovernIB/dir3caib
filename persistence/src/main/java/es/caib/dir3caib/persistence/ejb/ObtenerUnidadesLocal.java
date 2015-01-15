package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ws.UnidadTF;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 *         Date: 12/02/14
 */
@Local
public interface ObtenerUnidadesLocal {

    public UnidadTF obtenerUnidad(String codigo, String fechaActualizacion, String fechaSincronizacion) throws Exception;

    public List<UnidadTF> obtenerArbolUnidades(String codigo,String fechaActualizada, String fechaSincronizacion) throws Exception;

}
