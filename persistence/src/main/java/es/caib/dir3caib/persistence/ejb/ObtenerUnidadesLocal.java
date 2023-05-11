package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.json.UnidadRest;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;
import es.caib.dir3caib.persistence.model.ws.v2.UnidadWs;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.persistence.utils.ObjetoDirectorioExtendido;

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

    UnidadTF obtenerUnidadTF(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

    UnidadWs obtenerUnidadWs(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

    UnidadTF buscarUnidadTF(String codigo) throws Exception;

    UnidadWs buscarUnidadWs(String codigo) throws Exception;

    List<Unidad> obtenerArbolUnidades(String codigo, Date fechaActualizacion, Date fechaSincronizacion) throws Exception;

    List<UnidadTF> obtenerArbolUnidadesTF(String codigo, Date fechaActualizada, Date fechaSincronizacion) throws Exception;

    List<UnidadWs> obtenerArbolUnidadesWs(String codigo, Date fechaActualizada, Date fechaSincronizacion) throws Exception;
    
    List<UnidadRest> obtenerArbolUnidadesRest(String codigo, Date fechaActualizada, Date fechaSincronizacion, boolean mostrarHistoricos, boolean mostrarContactos) throws Exception;

    List<UnidadTF> obtenerArbolUnidadesDestinatariasTF(String codigo) throws Exception;

    List<UnidadWs> obtenerArbolUnidadesDestinatariasWs(String codigo) throws Exception;

    Date obtenerFechaUltimaActualizacion() throws Exception;

    void montarHistoricosFinales(Unidad unidad, Nodo nodo, int nivel, String codigoEstado) throws Exception;

    List<UnidadTF> obtenerHistoricosFinalesTF(String codigo) throws Exception;

    List<UnidadWs> obtenerHistoricosFinalesWs(String codigo) throws Exception;

    List<UnidadTF> obtenerHistoricosFinalesSIRTF(String codigo) throws Exception;

    List<UnidadWs> obtenerHistoricosFinalesSIRWs(String codigo) throws Exception;

	List<ObjetoDirectorioExtendido> obtenerHistoricosFinalesExtendido(String codigo) throws Exception;

}
