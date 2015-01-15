
package es.caib.dir3caib.ws.dir3.unidad.client;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.caib.dir3caib.ws.dir3.unidad.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.caib.dir3caib.ws.dir3.unidad.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link UnidadesWs }
     * 
     */
    public UnidadesWs createUnidadesWs() {
        return new UnidadesWs();
    }

    /**
     * Create an instance of {@link Estados }
     * 
     */
    public Estados createEstados() {
        return new Estados();
    }

}
