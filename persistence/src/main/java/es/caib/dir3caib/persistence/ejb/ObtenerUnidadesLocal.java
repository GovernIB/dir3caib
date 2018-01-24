package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.ws.UnidadTF;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 *         Date: 12/02/14
 */
@Local
public interface ObtenerUnidadesLocal {

    UnidadTF obtenerUnidad(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

    List<UnidadTF> obtenerArbolUnidadesTF(String codigo, Date fechaActualizada, Date fechaSincronizacion) throws Exception;

    List<UnidadTF> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception;

    Date obtenerFechaUltimaActualizacion() throws Exception;

}
