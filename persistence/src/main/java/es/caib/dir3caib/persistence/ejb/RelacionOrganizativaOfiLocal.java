package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi;
import es.caib.dir3caib.persistence.utils.Nodo;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 10/10/13
 */
@Local
public interface RelacionOrganizativaOfiLocal extends BaseEjb<RelacionOrganizativaOfi, Long> {

    /**
     * Elimina el contenido de la tabla
     *
     * @throws Exception
     */
    void deleteAll() throws Exception;

    /**
     *
     * @param codigoUnidad
     * @param codigoOficina
     * @return
     * @throws Exception
     */
    RelacionOrganizativaOfi findByPKs(String codigoUnidad, String codigoOficina) throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    List<String> getUnidadesOficinas() throws Exception;

    /**
     * Obtiene las relaciones organizativas de la unidad indicada y en función del estado indicado
     *
     * @param codigo codigo de la unidad
     * @param estado estado de la relación
     * @return
     * @throws Exception
     */
    List<Nodo> getOrganizativasByUnidadEstado(String codigo, String estado) throws Exception;

    /**
     * Obtiene las relaciones organizativas de la unidad indicada y en función del estado indicado
     *
     * @param codigo codigo de la unidad
     * @param estado estado de la relación
     * @return
     * @throws Exception
     */
    List<RelacionOrganizativaOfi> getOrganizativasCompletoByUnidadEstado(String codigo, String estado) throws Exception;

    /**
     *
     * @param codigo
     * @param estado
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    List<RelacionOrganizativaOfi> getOrganizativasCompletoByUnidadEstado(String codigo, String estado,
                                                                         boolean denominacionCooficial) throws Exception;

    /**
     *
     * @param codigo
     * @param estado
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    List<Nodo> getOrganizativasByUnidadEstado(String codigo, String estado, boolean denominacionCooficial) throws Exception;

}
