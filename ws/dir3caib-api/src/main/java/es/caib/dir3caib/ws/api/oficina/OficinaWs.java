
package es.caib.dir3caib.ws.api.oficina;

import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Clase Java para oficinaWs complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="oficinaWs">
 *   &lt;complexContent>
 *     &lt;extension base="{http://oficina.ws.dir3caib.caib.es/}oficinaTF">
 *       &lt;sequence>
 *         &lt;element name="denomLenguaCooficial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaUltimaActualizacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fuenteExterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idiomalengua" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="versionUoResponsable" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oficinaWs", propOrder = {
    "denomLenguaCooficial",
    "fechaUltimaActualizacion",
    "fuenteExterna",
    "idiomalengua",
    "versionUoResponsable"
})
public class OficinaWs
    extends OficinaTF
{

    protected String denomLenguaCooficial;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaUltimaActualizacion;
    protected String fuenteExterna;
    protected int idiomalengua;
    protected Long versionUoResponsable;

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
     * Obtiene el valor de la propiedad fuenteExterna.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuenteExterna() {
        return fuenteExterna;
    }

    /**
     * Define el valor de la propiedad fuenteExterna.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuenteExterna(String value) {
        this.fuenteExterna = value;
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
     * Obtiene el valor de la propiedad versionUoResponsable.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVersionUoResponsable() {
        return versionUoResponsable;
    }

    /**
     * Define el valor de la propiedad versionUoResponsable.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVersionUoResponsable(Long value) {
        this.versionUoResponsable = value;
    }

}
