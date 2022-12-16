
package es.caib.dir3caib.ws.api.unidad;

import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Clase Java para unidadWs complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="unidadWs">
 *   &lt;complexContent>
 *     &lt;extension base="{http://unidad.ws.dir3caib.caib.es/}unidadTF">
 *       &lt;sequence>
 *         &lt;element name="comparteNif" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="denomLenguaCooficial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaUltimaActualizacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idiomalengua" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nifCif" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="poder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unidadWs", propOrder = {
    "comparteNif",
    "denomLenguaCooficial",
    "fechaUltimaActualizacion",
    "idiomalengua",
    "nifCif",
    "poder",
    "version"
})
public class UnidadWs
    extends UnidadTF
{

    protected boolean comparteNif;
    protected String denomLenguaCooficial;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaUltimaActualizacion;
    protected int idiomalengua;
    protected String nifCif;
    protected String poder;
    protected Long version;

    /**
     * Obtiene el valor de la propiedad comparteNif.
     * 
     */
    public boolean isComparteNif() {
        return comparteNif;
    }

    /**
     * Define el valor de la propiedad comparteNif.
     * 
     */
    public void setComparteNif(boolean value) {
        this.comparteNif = value;
    }

    /**
     * Obtiene el valor de la propiedad denomLenguaCooficial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenomLenguaCooficial() {
        return denomLenguaCooficial;
    }

    /**
     * Define el valor de la propiedad denomLenguaCooficial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenomLenguaCooficial(String value) {
        this.denomLenguaCooficial = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaUltimaActualizacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Timestamp getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    /**
     * Define el valor de la propiedad fechaUltimaActualizacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaUltimaActualizacion(Timestamp value) {
        this.fechaUltimaActualizacion = value;
    }

    /**
     * Obtiene el valor de la propiedad idiomalengua.
     * 
     */
    public int getIdiomalengua() {
        return idiomalengua;
    }

    /**
     * Define el valor de la propiedad idiomalengua.
     * 
     */
    public void setIdiomalengua(int value) {
        this.idiomalengua = value;
    }

    /**
     * Obtiene el valor de la propiedad nifCif.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNifCif() {
        return nifCif;
    }

    /**
     * Define el valor de la propiedad nifCif.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNifCif(String value) {
        this.nifCif = value;
    }

    /**
     * Obtiene el valor de la propiedad poder.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoder() {
        return poder;
    }

    /**
     * Define el valor de la propiedad poder.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoder(String value) {
        this.poder = value;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVersion(Long value) {
        this.version = value;
    }

}
