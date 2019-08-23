package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;
import es.caib.dir3caib.persistence.utils.Nodo;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 *         Date: 12/02/14
 */
@Local
public interface ObtenerUnidadesLocal {

    UnidadTF obtenerUnidad(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

    UnidadTF buscarUnidad(String codigo) throws Exception;

    List<UnidadTF> obtenerArbolUnidadesTF(String codigo, Date fechaActualizada, Date fechaSincronizacion) throws Exception;

    List<UnidadTF> obtenerArbolUnidadesDestinatarias(String codigo) throws Exception;

    Date obtenerFechaUltimaActualizacion() throws Exception;

    void montarHistoricosFinales(Unidad unidad, Nodo nodo, int nivel) throws Exception;

    void obtenerHistoricosFinales(Unidad unidad, Set<Unidad> historicosFinales) throws Exception;

}
