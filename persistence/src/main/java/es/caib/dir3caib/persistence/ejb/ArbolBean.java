package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.utils.Nodo;
import org.apache.log4j.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
     * Método que monta el arbol de nodos de la unidad indicada.
     * Se emplea en dir3caib para mostrar el arbol de una unidad
     *
     * @param idUnidad    unidad  de la que partimos.
     * @param nodo        nodo en el que se montará todo el árbol.
     * @param estado      estado de las unidades que queremos mostrar en el arbol.
     * @param conOficinas indica si se quieren incluir las oficinas en el organigrama (es para mostrar el arbol en las
     *                    búsquedas de organismo destinatario de regweb3)
     * @return Nodo (arbol)
     */
    public void arbolUnidades(String idUnidad, Nodo nodo, String estado, boolean conOficinas) throws Exception {

        Nodo unidadPadre = unidadEjb.findUnidad(idUnidad, estado);


        // Nodo que se està tratando (representa una unidad)
        nodo.setCodigo(unidadPadre.getCodigo());
        nodo.setIdPadre(unidadPadre.getCodigo());
        nodo.setDenominacion(unidadPadre.getDenominacion());
        nodo.setRaiz(unidadPadre.getRaiz());
        nodo.setSuperior(unidadPadre.getSuperior());
        nodo.setDescripcionEstado(unidadPadre.getDescripcionEstado());

        //OBTENEMOS LAS OFICINAS DEPENDIENTES DEL NODO(UNIDAD) TRATADO
        // Primero obtenemos las oficinas generales dependendientes.
        List<Nodo> oficinasDependientes;
        if (conOficinas) {
            oficinasDependientes = oficinaEjb.oficinasDependientes(unidadPadre.getCodigo(), estado);

            List<Nodo> oficinasDependientesTransf = new ArrayList<Nodo>();
            for (Nodo oficina : oficinasDependientes) {
                // Obtenemos las oficinas auxliares del nodo oficina que estamos tratando
                List<Nodo> oficinasAuxiliares = oficinaEjb.oficinasAuxiliares(oficina.getCodigo(), estado);

                List<Nodo> oficinasAuxTransformadas = oficinasAuxiliares;

                //Obtenemos las oficinas auxiliares de segundo nivel
                obtenerAuxiliares(oficinasAuxTransformadas, estado);


                // Configuramos los datos del nodo (Representa una oficina)
                Nodo nodoOficina = new Nodo();
                nodoOficina.setCodigo(oficina.getCodigo());
                nodoOficina.setDenominacion(oficina.getDenominacion());
                nodoOficina.setOficinasAuxiliares(oficinasAuxTransformadas);
                oficinasDependientesTransf.add(nodoOficina);
            }
            //Añadimos las oficinas dependientes al nodo que se està tratando.
            nodo.setOficinasDependientes(oficinasDependientesTransf);

            //Oficinas Organizativas
            List<Nodo> oficinasOrganizativas = relacionOrganizativaOfiEjb.getOrganizativasByUnidadEstado(unidadPadre.getCodigo(), estado);
            nodo.setOficinasFuncionales(oficinasOrganizativas);
        }


        List<Nodo> hijos = new ArrayList<Nodo>();
        List<Nodo> unidadesHijas = unidadEjb.hijos(idUnidad, estado);


        for (Nodo unidadHija : unidadesHijas) {
            Nodo hijo = new Nodo();
            hijo.setCodigo(unidadHija.getCodigo());
            hijo.setIdPadre(idUnidad);
            hijo.setDenominacion(unidadHija.getDenominacion());
            hijo.setSuperior(unidadHija.getSuperior());
            hijo.setRaiz(unidadHija.getRaiz());
            hijo.setDescripcionEstado(unidadHija.getDescripcionEstado());
            hijos.add(hijo);
            // llamada recursiva
            arbolUnidades(unidadHija.getCodigo(), hijo, estado, conOficinas);
        }
        nodo.setHijos(hijos);
    }



    /**
     * Método que devuelve el árbol completo(ascendentes y descendentes) de la unidad indicada.
     * Primero obtiene los descendientes llamando a arbolUnidades y después obtiene los ascendentes.
     * Es el que se enmplea para mostrar el arbol en la búsqueda de organismos destinatarios de regweb3
     *
     * @param idUnidad    unidad  de la que partimos.
     * @param nodo        nodo en el que se montará todo el árbol.
     * @param estado      estado de las unidades que queremos mostrar en el arbol.
     * @param conOficinas indica si se quieren incluir las oficinas en el organigrama (es para mostrar el arbol en las
     *                    búsquedas de organismo destinatario de regweb3)
     * @return Nodo (arbol)
     */
    public void arbolUnidadesAscendentes(String idUnidad, Nodo nodo, String estado, boolean conOficinas) throws Exception {


        Nodo nodoInicial = new Nodo(); // Nodo en el que guardaremos los datos de la unidad indicada por IdUnidad
        Nodo unidad = unidadEjb.findUnidad(idUnidad, estado); // Obtenemos la unidad que nos han indicado(solo se obtienen parte de los datos del nodo)
        String codigoSuperior = new StringTokenizer(unidad.getSuperior(), " - ").nextToken();//Obtenemos el código de la Unidad Superior
        unidad.setIdPadre(codigoSuperior); //Asignamos el identificador del padre de la unidad.


        // Creamos el nodo asociado a la unidad indicada por idUnidad
        // este nodo lo necesitaremos posteriormente para montar todo el arbol hacia arriba y hacia abajo
        nodoInicial.setCodigo(unidad.getCodigo());
        nodoInicial.setIdPadre(codigoSuperior);
        nodoInicial.setDenominacion(unidad.getDenominacion());
        nodoInicial.setRaiz(unidad.getRaiz());
        nodoInicial.setSuperior(unidad.getSuperior());
        nodoInicial.setDescripcionEstado(unidad.getDescripcionEstado());

        //Obtenemos todos los hijos hacia abajo con el metodo de arbolUnidades
        List<Nodo> hijos = new ArrayList<Nodo>();
        List<Nodo> unidadesHijas = unidadEjb.hijos(idUnidad, estado);
        //Llamada a arbolUnidades para cada uno de los hijos encontrados
        for (Nodo unidadHija : unidadesHijas) {
            Nodo hijo = new Nodo();
            hijo.setCodigo(unidadHija.getCodigo());
            hijo.setIdPadre(idUnidad);
            hijo.setDenominacion(unidadHija.getDenominacion());
            hijo.setSuperior(unidadHija.getSuperior());
            hijo.setRaiz(unidadHija.getRaiz());
            hijo.setDescripcionEstado(unidadHija.getDescripcionEstado());
            hijos.add(hijo);
            // llamada recursiva
            arbolUnidades(unidadHija.getCodigo(), hijo, unidadHija.getDescripcionEstado(), conOficinas);
        }
        nodoInicial.setHijos(hijos);

        //Empezamos el proceso ascendente, obtenemos las unidades ascendentes del nodo inicial
        Nodo nodoActual = nodoInicial;
        String codigoRaiz = new StringTokenizer(nodoActual.getRaiz(), " - ").nextToken();
        while (!nodoActual.getCodigo().equals(codigoRaiz)) {//mientras el codigo del nodo actual con el codigo de su raiz sean distintos
            Nodo nodoSuperior = new Nodo();
            codigoSuperior = new StringTokenizer(nodoActual.getSuperior(), " - ").nextToken();//Obtenemos el código de la Unidad Superior
            nodoSuperior = unidadEjb.findUnidad(codigoSuperior, estado); // Obtenemos la unidad que nos han indicado(solo se obtienen parte de los datos del nodo)
            List<Nodo> hijosS = new ArrayList<Nodo>();
            hijosS.add(nodoActual);
            nodoSuperior.setHijos(hijosS);
            nodoActual = nodoSuperior;
            codigoRaiz = new StringTokenizer(nodoActual.getRaiz(), " - ").nextToken();
        }

        //TRATAMOS RAIZ
        Nodo nodoSuperior = new Nodo();
        codigoSuperior = new StringTokenizer(nodoActual.getSuperior(), " - ").nextToken();//Obtenemos el código de la Unidad Superior
        nodoSuperior = unidadEjb.findUnidad(codigoSuperior, estado); // Obtenemos la unidad que nos han indicado(solo se obtienen parte de los datos del nodo)
        List<Nodo> hijosS = new ArrayList<Nodo>();
        hijosS.add(nodoActual);
        nodoSuperior.setHijos(hijosS);


        //Asignamos al nodo a devolver la raiz del árbol.
        nodo.setCodigo(nodoSuperior.getCodigo());
        nodo.setDenominacion(nodoSuperior.getDenominacion());
        nodo.setIdPadre(codigoSuperior);
        nodo.setRaiz(nodoSuperior.getRaiz());
        nodo.setSuperior(nodoSuperior.getSuperior());
        nodo.setDescripcionEstado(nodoSuperior.getDescripcionEstado());
        nodo.setHijos(nodoSuperior.getHijos());


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
            List<Nodo> oficinasAuxiliares = oficinaEjb.oficinasAuxiliares(oficinaAuxiliarTrans.getCodigo(), estado);
            List<Nodo> oficinasAuxTransformadas = oficinasAuxiliares;
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
        Nodo oficinaPadre = oficinaEjb.findOficina(idOficina, estado);
        nodo.setCodigo(oficinaPadre.getCodigo());
        nodo.setDenominacion(oficinaPadre.getDenominacion());
        nodo.setIdPadre(idOficina);
        nodo.setRaiz(oficinaPadre.getRaiz());
        nodo.setSuperior(oficinaPadre.getSuperior());
        nodo.setDescripcionEstado(oficinaPadre.getDescripcionEstado());

        List<Nodo> hijos = new ArrayList<Nodo>();
        List<Nodo> oficinasHijas = oficinaEjb.hijos(idOficina, estado);

        for (Nodo oficinaHija : oficinasHijas) {
            Nodo hijo = new Nodo();
            hijo.setCodigo(oficinaHija.getCodigo());
            hijo.setDenominacion(oficinaHija.getDenominacion());
            hijo.setIdPadre(idOficina);
            hijo.setRaiz(oficinaHija.getRaiz());
            hijo.setSuperior(oficinaHija.getSuperior());
            hijo.setDescripcionEstado(oficinaHija.getDescripcionEstado());
            hijos.add(hijo);
            // llamada recursiva
            arbolOficinas(oficinaHija.getCodigo(), hijo, estado);
        }
        nodo.setHijos(hijos);

    }


}




