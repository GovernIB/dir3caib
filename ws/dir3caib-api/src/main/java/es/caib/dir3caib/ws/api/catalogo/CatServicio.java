
package es.caib.dir3caib.ws.api.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para catServicio complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="catServicio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codServicio" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descServicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo" type="{http://catalogo.ws.dir3caib.caib.es/}catTipoServicio" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catServicio", propOrder = {
    "codServicio",
    "descServicio",
    "tipo"
})
@XmlSeeAlso({
    Servicio.class
})
public class CatServicio {

    protected Long codServicio;
    protected String descServicio;
    protected CatTipoServicio tipo;

    /**
     * Obtiene el valor de la propiedad codServicio.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodServicio() {
        return codServicio;
    }

    /**
     * Define el valor de la propiedad codServicio.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodServicio(Long value) {
        this.codServicio = value;
    }

    /**
     * Obtiene el valor de la propiedad descServicio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescServicio() {
        return descServicio;
    }

    /**
     * Define el valor de la propiedad descServicio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescServicio(String value) {
        this.descServicio = value;
    }

    /**
     * Obtiene el valor de la propiedad tipo.
     * 
     * @return
     *     possible object is
     *     {@link CatTipoServicio }
     *     
     */
    public CatTipoServicio getTipo() {
        return tipo;
    }

    /**
     * Define el valor de la propiedad tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link CatTipoServicio }
     *     
     */
    public void setTipo(CatTipoServicio value) {
        this.tipo = value;
    }

}
