package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.utils.ObjetoBasico;
import es.caib.dir3caib.persistence.utils.Nodo;
import org.apache.log4j.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgonzalez on 29/03/2016.
 */
@Stateless(name = "ArbolEJB")
@RunAs("DIR_ADMIN")
public class ArbolBean implements ArbolLocal {

    protected final Logger log = Logger.getLogger(getClass());


    @EJB(mappedName = "dir3caib/UnidadEJB/local")
    protected UnidadLocal unidadEjb;

    @EJB(mappedName = "dir3caib/OficinaEJB/local")
    protected OficinaLocal oficinaEjb;

    @EJB(mappedName = "dir3caib/RelacionOrganizativaOfiEJB/local")
    protected RelacionOrganizativaOfiLocal relacionOrganizativaOfiEjb;


    /**
     * Metodo que devuelve una estructura de nodos que representan un árbol de unidades
     *
     * @param idUnidad    unidad  de la que partimos.
     * @param nodo        nodo en el que se montará todo el árbol.
     * @param estado      estado de las unidades que queremos mostrar en el arbol.
     * @param conOficinas indica si se quieren incluir las oficinas en el organigrama
     * @return Nodo (arbol)
     */
    public void arbolUnidades(String idUnidad, Nodo nodo, String estado, boolean conOficinas) throws Exception {

        ObjetoBasico unidadPadre = unidadEjb.findUnidad(idUnidad, estado);


        // Nodo que se està tratando (representa una unidad)
        nodo.setId(unidadPadre.getCodigo());
        nodo.setIdPadre(unidadPadre.getCodigo());
        nodo.setNombre(unidadPadre.getDenominacion());
        nodo.setRaiz(unidadPadre.getRaiz());
        nodo.setSuperior(unidadPadre.getSuperior());
        nodo.setEstado(unidadPadre.getDescripcionEstado());

        //OBTENEMOS LAS OFICINAS DEPENDIENTES DEL NODO(UNIDAD) TRATADO
        // Primero obtenemos las oficinas generales dependendientes.
        List<ObjetoBasico> oficinasDependientes;
        if (conOficinas) {
            oficinasDependientes = oficinaEjb.oficinasDependientes(unidadPadre.getCodigo(), estado);

            List<Nodo> oficinasDependientesTransf = new ArrayList<Nodo>();
            for (ObjetoBasico oficina : oficinasDependientes) {
                // Obtenemos las oficinas auxliares del nodo oficina que estamos tratando
                List<ObjetoBasico> oficinasAuxiliares = oficinaEjb.oficinasAuxiliares(oficina.getCodigo(), estado);
                //Transforma una lista de oficinas auxiliares en formato ObjetoBasico a Nodo
                List<Nodo> oficinasAuxTransformadas = transformarObjetoBasicoANodo(oficinasAuxiliares);

                //Obtenemos las oficinas auxiliares de segundo nivel
                obtenerAuxiliares(oficinasAuxTransformadas, estado);


                // Configuramos los datos del nodo (Representa una oficina)
                Nodo nodoOficina = new Nodo();
                nodoOficina.setId(oficina.getCodigo());
                nodoOficina.setNombre(oficina.getDenominacion());
                nodoOficina.setOficinasAuxiliares(oficinasAuxTransformadas);
                oficinasDependientesTransf.add(nodoOficina);
            }
            //Añadimos las oficinas dependientes al nodo que se està tratando.
            nodo.setOficinasDependientes(oficinasDependientesTransf);

            //Oficinas Funcionales
            List<ObjetoBasico> oficinasFuncionales = relacionOrganizativaOfiEjb.getOrganizativasByUnidadEstado(unidadPadre.getCodigo(), estado);
            nodo.setOficinasFuncionales(transformarObjetoBasicoANodo(oficinasFuncionales));
        }


        List<Nodo> hijos = new ArrayList<Nodo>();
        List<ObjetoBasico> unidadesHijas = unidadEjb.hijosOB(idUnidad, estado);


        for (ObjetoBasico unidadHija : unidadesHijas) {
            Nodo hijo = new Nodo();
            hijo.setId(unidadHija.getCodigo());
            hijo.setIdPadre(idUnidad);
            hijo.setNombre(unidadHija.getDenominacion());
            hijo.setSuperior(unidadHija.getSuperior());
            hijo.setRaiz(unidadHija.getRaiz());
            hijo.setEstado(unidadHija.getDescripcionEstado());
            hijos.add(hijo);
            // llamada recursiva
            arbolUnidades(unidadHija.getCodigo(), hijo, unidadHija.getDescripcionEstado(), conOficinas);
        }
        nodo.setHijos(hijos);
    }


    /**
     * Método que transforma una lista de {@link es.caib.dir3caib.persistence.model.utils.ObjetoBasico} en una lista de
     * {@link es.caib.dir3caib.persistence.utils.Nodo}
     *
     * @param oficinasAtransformar
     * @return lista de Nodo
     * @throws Exception
     */
    private List<Nodo> transformarObjetoBasicoANodo(List<ObjetoBasico> oficinasAtransformar) throws Exception {
        List<Nodo> nodos = new ArrayList<Nodo>();
        for (ObjetoBasico obj : oficinasAtransformar) {
            Nodo nodo = new Nodo();
            nodo.setId(obj.getCodigo());
            nodo.setNombre(obj.getDenominacion());
            nodos.add(nodo);
        }
        return nodos;
    }


    /**
     * Método que de manera recursiva obtiene las oficinas auxiliares de una lista de oficinas indicadas y con el estado indicado
     * Se emplea para pintar el árbol de las unidades y oficinas.
     *
     * @param oficinas
     * @param estado
     * @throws Exception
     */
    private void obtenerAuxiliares(List<Nodo> oficinas, String estado) throws Exception {

        for (Nodo oficinaAuxiliarTrans : oficinas) {
            // obtener sus auxiliares
            List<ObjetoBasico> oficinasAuxiliares = oficinaEjb.oficinasAuxiliares(oficinaAuxiliarTrans.getId(), estado);
            List<Nodo> oficinasAuxTransformadas = transformarObjetoBasicoANodo(oficinasAuxiliares);
            oficinaAuxiliarTrans.setOficinasAuxiliares(oficinasAuxTransformadas);
            //recursividad
            obtenerAuxiliares(oficinasAuxTransformadas, estado);
        }
    }


    /**
     * Metodo que devuelve una estructura de nodos que representan un árbol de oficinas
     *
     * @param idOficina oficina raiz de la que partimos.
     * @return Nodo (árbol)
     */
    public void arbolOficinas(String idOficina, Nodo nodo, String estado) throws Exception {

        log.info(" CODIGO DE LA OFICINA " + idOficina);
        //Oficina oficinaPadre = oficinaEjb.findById(idOficina);
        ObjetoBasico oficinaPadre = oficinaEjb.findOficina(idOficina, estado);
        nodo.setId(oficinaPadre.getCodigo());
        nodo.setNombre(oficinaPadre.getDenominacion());
        nodo.setIdPadre(idOficina);
        nodo.setRaiz(oficinaPadre.getRaiz());
        nodo.setSuperior(oficinaPadre.getSuperior());
        nodo.setEstado(oficinaPadre.getDescripcionEstado());

        List<Nodo> hijos = new ArrayList<Nodo>();
        // List<Oficina> oficinasHijas = oficinaEjb.hijos(idOficina);
        List<ObjetoBasico> oficinasHijas = oficinaEjb.hijos(idOficina, estado);

        for (ObjetoBasico oficinaHija : oficinasHijas) {
            Nodo hijo = new Nodo();
            hijo.setId(oficinaHija.getCodigo());
            hijo.setNombre(oficinaHija.getDenominacion());
            hijo.setIdPadre(idOficina);
            hijo.setRaiz(oficinaHija.getRaiz());
            hijo.setSuperior(oficinaHija.getSuperior());
            hijo.setEstado(oficinaHija.getDescripcionEstado());
            hijos.add(hijo);
            // llamada recursiva
            arbolOficinas(oficinaHija.getCodigo(), hijo, estado);
        }
        nodo.setHijos(hijos);

    }

}
