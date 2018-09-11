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

  void deleteAll() throws Exception;

  RelacionOrganizativaOfi findByPKs(String codigoUnidad, String codigoOficina) throws Exception;

  List<String> getUnidadesOficinas() throws Exception;

  /**
   * Obtiene las relaciones organizativas de la unidad indicada y en función del estado indicado
   * @param codigo codigo de la unidad
   * @param estado estado de la relación
   * @return
   * @throws Exception
   */
  List<Nodo> getOrganizativasByUnidadEstado(String codigo, String estado) throws Exception;

  /*
   btiene las relaciones organizativas de la unidad indicada y en función del estado indicado
   * @param codigo codigo de la unidad
   * @param estado estado de la relación
   * @return
   * @throws Exception
   */
  List<RelacionOrganizativaOfi> getOrganizativasCompletoByUnidadEstado(String codigo, String estado) throws Exception;

}
