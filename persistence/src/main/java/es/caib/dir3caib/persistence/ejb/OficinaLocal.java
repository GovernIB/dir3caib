/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;
import es.caib.dir3caib.persistence.utils.Paginacion;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author mgonzalez
 * @author anadal
 */
@Local
public interface OficinaLocal extends BaseEjb<Oficina, String> {

    public void deleteAll() throws Exception;

    public Paginacion busqueda(Integer pageNumber, String codigo, String denominacion,
                               Long codigoNivelAdministracion, Long codComunidad, Long codigoProvincia, String codigoEstado)
            throws Exception;

    public void deleteHistoricosOficina() throws Exception;

    public void deleteServiciosOficina() throws Exception;

    public Oficina findFullById(String id) throws Exception;

    /**
     * Obtiene el codigo y la denominación de una Oficina con el estado indicado.
     * Se emplea para mostrar el árbol de oficinas.
     *
     * @param id     identificador de la oficina
     * @param estado de la oficina
     * @return {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */

    public ObjetoBasico findReduceOficina(String id, String estado) throws Exception;

    /*
       Método que comprueba si una oficina tiene más oficinas hijas
    */
    public Boolean tieneHijos(String codigo) throws Exception;

    /**
     * Metodo que obtiene los hijos de primer nivel de una oficina en funcion del estado del padre.
     *
     * @param codigo identificador de la oficina padre.
     * @param estado estado de la oficina padre.
     * @return {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    public List<ObjetoBasico> hijos(String codigo, String estado) throws Exception;


    /**
     * Método que devuelve las oficinas de un organismo,
     * teniendo en cuenta la fecha de la ultima actualización de regweb.
     * Se emplea para la sincronizacion y actualización con regweb
     *
     * @param codigo              código de la unidad
     * @param fechaActualizacion  fecha de la ultima actualización
     * @param fechaSincronizacion fecha de la primera sincronización
     * @return
     * @throws Exception
     */
    public List<Oficina> obtenerOficinasOrganismo(String codigo, Date fechaActualizacion,
                                                  Date fechaSincronizacion) throws Exception;

    /**
     * Método que devuelve  el arbol de oficinas de una oficina padre,
     * teniendo en cuenta la fecha de la ultima actualización de regweb.
     * Se emplea para la sincronizacion y actualización con regweb
     *
     * @param codigo              código de la oficina padre
     * @param fechaActualizacion  fecha de la ultima actualización de regweb
     * @param fechaSincronizacion fecha la primera sincronización de regweb.
     * @return
     * @throws Exception
     */
    public List<Oficina> obtenerArbolOficinas(String codigo, Date fechaActualizacion,
                                              Date fechaSincronizacion) throws Exception;

    /**
     * Obtiene las oficinas SIR de una unidad que están vigentes
     *
     * @param codigo código de la Unidad
     * @return listado de Oficinas
     * @throws Exception
     */
    public List<Oficina> obtenerOficinasSIRUnidad(String codigo) throws Exception;

    /**
     * Método que comprueba si el arbol de unidades del código indicado  tiene oficinas donde registrar
     *
     * @param codigo
     * @return
     * @throws Exception
     */
    public Boolean tieneOficinasArbol(String codigo) throws Exception;

    public List<String> getAllCodigos() throws Exception;

    /**
     * Obtiene las oficinas que dependen directamente de la unidad
     *
     * @return {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    public List<ObjetoBasico> oficinasDependientes(String codigo, String estado) throws Exception;

    /**
     * Obtiene las oficinas auxiliares de un Oficina padre.
     *
     * @return {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico}
     */
    public List<ObjetoBasico> oficinasAuxiliares(String codigo, String estado) throws Exception;
}
