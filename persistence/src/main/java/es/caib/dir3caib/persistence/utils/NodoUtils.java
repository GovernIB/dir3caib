package es.caib.dir3caib.persistence.utils;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Oficina;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.utils.Utils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgonzalez on 03/06/2016.
 */
public class NodoUtils {

    protected final Logger log = Logger.getLogger(getClass());

    /**
     * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
     * se devuelve codigo y denominación
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static List<Nodo> getNodoListMinimo(List<Object[]> result, boolean denominacionCooficial) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();

        for (Object[] object : result) {
        	
        	String denominacion = (denominacionCooficial && Utils.isNotEmpty((String) object[2])) ? (String) object[2] : (String) object[1];
        	
            Nodo nodo = new Nodo((String) object[0], denominacion, "", "", "", "");

            nodos.add(nodo);
        }

        return nodos;
    }

    /**
     * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
     * se devuelve codigo, denominación y estado. 
     * Si denominacionCooficial es true, devuelve la denominacionCooficial si no es nula.
     * @param result
     * @param denominacionCooficial
     * @return
     * @throws Exception
     */
    public static List<Nodo> getNodoList(List<Object[]> result, boolean denominacionCooficial) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();

        for (Object[] object : result) {
        	String denominacion = ( denominacionCooficial && !((String) object[3]).isEmpty()) ? (String) object[3] : (String) object[1];
            Nodo nodo = new Nodo((String) object[0], denominacion, (String) object[2], "", "", "");
            nodo.setCodigoDir3((String) object[10]);

            nodos.add(nodo);
        }

        return nodos;
    }


    /**
     * Convierte los resultados de una query de unidades en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static List<Nodo> getNodoListExtendido(List<Object[]> result, boolean denominacionCooficial) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();

        for (Object[] object : result) {
            // UNIDADES
            //object[0] --> codigo
            //object[1] --> denominacion
            //object[2] --> descripcionEstado
            //object[3] --> raiz.codigo
            //object[4] --> raiz.denominacion
            //object[5] --> superior.codigo
            //object[6] --> superior.denominacion
            //object[7] --> localidad
        	//object[8] --> oficina.denomcooficial, 
        	//object[9] --> unidadRaiz.denomcooficial, 
        	//object[10] --> oficina.codUoResponsable.denomcooficial 

            String[] obj = new String[object.length];

            // copy elements from object array to string array
            for (int i = 0; i < object.length; i++)
                obj[i] = String.valueOf(object[i]);

            String denominacion = ( denominacionCooficial && Utils.isNotEmpty((String) object[8])) ? (String) object[8] : (String) object[1];
            String denominacionUnidadRaiz = ( denominacionCooficial && Utils.isNotEmpty((String) object[9])) ? (String) object[9] : (String) object[4];
            String denominacionUoResponsable = ( denominacionCooficial && Utils.isNotEmpty((String) object[10])) ? (String) object[10] : (String) object[6];

            Nodo nodo = new Nodo(obj[0], denominacion, obj[2], denominacionUnidadRaiz + " - " + obj[3], denominacionUoResponsable, obj[5], obj[7]);


            nodos.add(nodo);
        }

        return nodos;
    }

    /**
     * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
     * donde se devuelve el codigo, denominación, denominación de unidad raiz, denominación de unidad superior
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static List<Nodo> getNodoListUnidadRaizUnidadSuperior(List<Object[]> result, boolean denominacionCooficial, boolean retornoCodigoDir3) throws Exception {

    	//0 unidad.codigo, 
    	//1 unidad.denominacion
    	//2 unidad.codUnidadRaiz.denominacion, 
	    //3 unidad.codUnidadSuperior.denominacion, 
    	//4 unidad.denomLenguaCooficial
    	//5 unidad.codUnidadRaiz.denomLenguaCooficial
	    //6 unidad.codUnidadSuperior.denomLenguaCooficial
    	//7 unidad.codigoDir3
    	
        List<Nodo> nodos = new ArrayList<Nodo>();

        for (Object[] object : result) {
        	String codigo = (retornoCodigoDir3) ? (String) object[7] : (String) object[0];
        	String denominacion = (denominacionCooficial && Utils.isNotEmpty((String)object[4])) ?(String) object[4] : (String) object[1];
        	String denominacionUnidadRaiz = (denominacionCooficial && Utils.isNotEmpty((String)object[5])) ?(String) object[5] : (String) object[2];
        	String denominacionUnidadSuperior = (denominacionCooficial && Utils.isNotEmpty((String)object[6])) ?(String) object[6] : (String) object[3];		
            Nodo nodo = new Nodo(codigo, denominacion, "", denominacionUnidadRaiz, denominacionUnidadSuperior, "");
            nodo.setCodigoDir3((String) object[7]);
            nodos.add(nodo);
        }

        return nodos;
    }


    public static List<Nodo> getNodoListUnidad(List<Unidad> result, Boolean denominacionCooficial) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();
        String edpPrincipal = "";
        for (Unidad unidad : result) {
            if (unidad.getCodEdpPrincipal() != null) {
            	if (denominacionCooficial && Utils.isNotEmpty(unidad.getCodEdpPrincipal().getDenomLenguaCooficial()))
            		edpPrincipal = unidad.getCodEdpPrincipal().getCodigo() + " - " + unidad.getCodEdpPrincipal().getDenomLenguaCooficial();
            	else
            		edpPrincipal = unidad.getCodEdpPrincipal().getCodigo() + " - " + unidad.getCodEdpPrincipal().getDenominacion();
            }
            
            String denominacion = (denominacionCooficial && Utils.isNotEmpty(unidad.getDenomLenguaCooficial())) ? unidad.getDenomLenguaCooficial() : unidad.getDenominacion();
            String descCodUnidadRaiz = (denominacionCooficial && unidad.getCodUnidadRaiz()!=null && Utils.isNotEmpty(unidad.getCodUnidadRaiz().getDenomLenguaCooficial())) ? unidad.getCodUnidadRaiz().getDenomLenguaCooficial() : unidad.getCodUnidadRaiz().getDenominacion();
            String descCodUnidadSuperior = (denominacionCooficial && unidad.getCodUnidadSuperior()!=null && Utils.isNotEmpty(unidad.getCodUnidadSuperior().getDenomLenguaCooficial())) ? unidad.getCodUnidadSuperior().getDenomLenguaCooficial() : unidad.getCodUnidadSuperior().getDenominacion();

            Nodo nodo = new Nodo(unidad.getCodigo(), denominacion, 
            		unidad.getEstado().getDescripcionEstadoEntidad(), 
            		unidad.getCodUnidadRaiz().getCodigoDir3() + " " + Dir3caibConstantes.SEPARADOR_CODIGO_VERSION + unidad.getCodUnidadRaiz().getVersion() + " - " + descCodUnidadRaiz, 
            		unidad.getCodUnidadSuperior().getCodigoDir3() + " " + Dir3caibConstantes.SEPARADOR_CODIGO_VERSION + unidad.getCodUnidadSuperior().getVersion() +  " - " + descCodUnidadSuperior, 
            		"", false, unidad.isEsEdp(), edpPrincipal);
            nodo.setCodigoDir3(unidad.getCodigoDir3());
            nodo.setVersion(String.valueOf(unidad.getVersion()));
            nodo.setCodigoEstado(unidad.getEstado().getCodigoEstadoEntidad());
            nodos.add(nodo);
        }

        return nodos;
    }

    public static List<Nodo> getNodoListOficina(List<Oficina> result, Boolean denominacionCooficial) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();
        String ofiResponsable = "";
        for (Oficina oficina : result) {
            if (oficina.getCodOfiResponsable() != null) {
            	String denominacionOfiResponsable = (denominacionCooficial && Utils.isNotEmpty(oficina.getCodOfiResponsable().getDenomLenguaCooficial())) ? oficina.getCodOfiResponsable().getDenomLenguaCooficial() : oficina.getCodOfiResponsable().getDenominacion();
                ofiResponsable = oficina.getCodOfiResponsable().getCodigo() + " - " + denominacionOfiResponsable;
            }
            String denominacion = (denominacionCooficial && Utils.isNotEmpty(oficina.getDenomLenguaCooficial())) ? oficina.getDenomLenguaCooficial() : oficina.getDenominacion();
            String descCodUoResponsable = (denominacionCooficial && oficina.getCodUoResponsable()!=null && Utils.isNotEmpty(oficina.getCodUoResponsable().getDenomLenguaCooficial())) ? oficina.getCodUoResponsable().getDenomLenguaCooficial() : oficina.getCodUoResponsable().getDenominacion();
            String uoResponsable = oficina.getCodUoResponsable().getCodigoDir3() + " " + Dir3caibConstantes.SEPARADOR_CODIGO_VERSION + oficina.getCodUoResponsable().getVersion()  + " - " + descCodUoResponsable;
            Nodo nodo = new Nodo(oficina.getCodigo(), denominacion, oficina.getEstado().getDescripcionEstadoEntidad(), 
            		ofiResponsable, uoResponsable, "", oficina.getOficinaSir());
            nodo.setCodigoEstado(oficina.getEstado().getCodigoEstadoEntidad());

            nodos.add(nodo);
        }

        return nodos;
    }


    /**
     * Convierte los resultados de una query en una lista de {@link es.caib.dir3caib.persistence.utils.Nodo}
     * donde se devuelve los datos que estan comentados en el código
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static List<Nodo> getNodoListOpenData(List<Object[]> result, boolean denominacionCooficial) throws Exception {

        List<Nodo> nodos = new ArrayList<Nodo>();

        for (Object[] object : result) {
        	
        	/*
        	object[0] --> unidad.codigo
        	object[1] --> unidad.denominacion
        	object[2] --> unidad.estado.codigoEstadoEntidad
        	object[3] --> unidad.codunidadRaiz.codigo
        	object[4] --> unidad.codunidadRaiz.denominacion
        	object[5] --> unidad.codunidadSuperior.codigo
        	object[6] --> unidad.codunidadSuperior.denominacion
        	object[7] --> unidad.codLocalidad.descripcionLocalidad
        	object[8] --> unidad.esEdp
        	object[9] --> unidad.nivelJerarquico
        	object[10] --> unidad.nifcif
        	object[11] --> unidad.nivelAdministracion.descripcionNivelAdministracion
        	object[12] --> unidad.codTipounidad.descripcionTipoobject[0] --> unidadOrganica
        	object[13] --> unidad.tipoVia.descripcionTipoVia
        	object[14] --> unidad.nombreVia
        	object[15] --> unidad.numVia
        	object[16] --> unidad.complemento
        	object[17] --> unidad.codPostal
        	object[18] --> unidad.codAmbitoTerritorial.descripcionAmbito
        	object[19] --> unidad.codAmbPais.descripcionPais
        	object[20] --> unidad.codAmbComunidad.descripcionComobject[0] --> unidad
        	object[21] --> unidad.codAmbProvincia.descripcionProvincia
        	object[22] --> unidad.codAmbIsla.descripcionIsla
        	object[23] --> unidad.denomLenguaCooficial
        	object[24] --> unidad.codunidadRaiz.denomLenguaCooficial
        	object[25] --> unidad.codunidadSuperior.denomLenguaCooficial
        	object[26] --> unidad.codigoDir3
        	object[27] --> unidad.codunidadRaiz.codigoDir3
        	object[28] --> unidad.codunidadSuperior.codigoDir3
			*/
        	
            String[] obj = new String[object.length];

            // copy elements from object array to string array
            for (int i = 0; i < object.length; i++) {
                obj[i] = String.valueOf(object[i]);
            }
            
            String denominacion = ( denominacionCooficial && Utils.isNotEmpty((String) obj[23])) ? (String) obj[23] : (String) obj[1]; 
            String denominacioUnidadRaiz = ( denominacionCooficial && Utils.isNotEmpty((String) obj[24])) ? (String) obj[24] : (String) obj[4];
            String denominacionUnidadSuperior = ( denominacionCooficial && Utils.isNotEmpty((String) obj[25])) ? (String) obj[25] : (String) obj[6];
            
            Nodo nodo = new Nodo(obj[0], denominacion, obj[2], denominacioUnidadRaiz + " - " + obj[3], denominacionUnidadSuperior + " - " + obj[5], obj[7],Boolean.parseBoolean(obj[8]),Long.parseLong(obj[9]), obj[10],obj[11], obj[12],obj[13] + " " + obj[14]+ ", " + obj[15]+ ", "+ obj[16]+ ", " + obj[17],obj[18],obj[19],obj[20],obj[21],obj[22]);
            nodo.setCodigoDir3((String)obj[26]);
            nodos.add(nodo);
        }

        return nodos;
    }



}
