
package es.caib.dir3caib.ws.api.oficina;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para oficinaTF complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="oficinaTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codOfiResponsable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codPostal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codUoResponsable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoComunidad" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoPais" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoTipoVia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="contactos" type="{http://oficina.ws.dir3caib.caib.es/}contactoTF" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="denominacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionLocalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nivelAdministracion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nombreVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organizativasOfi" type="{http://oficina.ws.dir3caib.caib.es/}relacionOrganizativaOfiTF" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="servicios" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sirOfi" type="{http://oficina.ws.dir3caib.caib.es/}relacionSirOfiTF" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tipoOficina" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oficinaTF", propOrder = {
    "codOfiResponsable",
    "codPostal",
    "codUoResponsable",
    "codigo",
    "codigoComunidad",
    "codigoPais",
    "codigoTipoVia",
    "contactos",
    "denominacion",
    "descripcionLocalidad",
    "estado",
    "nivelAdministracion",
    "nombreVia",
    "numVia",
    "organizativasOfi",
    "servicios",
    "sirOfi",
    "tipoOficina"
})
public class OficinaTF {

    protected String codOfiResponsable;
    protected String codPostal;
    protected String codUoResponsable;
    protected String codigo;
    protected Long codigoComunidad;
    protected Long codigoPais;
    protected Long codigoTipoVia;
    @XmlElement(nillable = true)
    protected List<ContactoTF> contactos;
    protected String denominacion;
    protected String descripcionLocalidad;
    protected String estado;
    protected Long nivelAdministracion;
    protected String nombreVia;
    protected String numVia;
    @XmlElement(nillable = true)
    protected List<RelacionOrganizativaOfiTF> organizativasOfi;
    @XmlElement(nillable = true)
    protected List<Long> servicios;
    @XmlElement(nillable = true)
    protected List<RelacionSirOfiTF> sirOfi;
    protected Long tipoOficina;

    /**
     * Obtiene el valor de la propiedad codOfiResponsable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodOfiResponsable() {
        return codOfiResponsable;
    }

    /**
     * Define el valor de la propiedad codOfiResponsable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodOfiResponsable(String value) {
        this.codOfiResponsable = value;
    }

    /**
     * Obtiene el valor de la propiedad codPostal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodPostal() {
        return codPostal;
    }

    /**
     * Define el valor de la propiedad codPostal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodPostal(String value) {
        this.codPostal = value;
    }

    /**
     * Obtiene el valor de la propiedad codUoResponsable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodUoResponsable() {
        return codUoResponsable;
    }

    /**
     * Define el valor de la propiedad codUoResponsable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodUoResponsable(String value) {
        this.codUoResponsable = value;
    }

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoComunidad.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoComunidad() {
        return codigoComunidad;
    }

    /**
     * Define el valor de la propiedad codigoComunidad.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoComunidad(Long value) {
        this.codigoComunidad = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoPais.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoPais() {
        return codigoPais;
    }

    /**
     * Define el valor de la propiedad codigoPais.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoPais(Long value) {
        this.codigoPais = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoTipoVia.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoTipoVia() {
        return codigoTipoVia;
    }

    /**
     * Define el valor de la propiedad codigoTipoVia.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoTipoVia(Long value) {
        this.codigoTipoVia = value;
    }

    /**
     * Gets the value of the contactos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactoTF }
     * 
     * 
     */
    public List<ContactoTF> getContactos() {
        if (contactos == null) {
            contactos = new ArrayList<ContactoTF>();
        }
        return this.contactos;
    }

    /**
     * Obtiene el valor de la propiedad denominacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     * Define el valor de la propiedad denominacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominacion(String value) {
        this.denominacion = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionLocalidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionLocalidad() {
        return descripcionLocalidad;
    }

    /**
     * Define el valor de la propiedad descripcionLocalidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionLocalidad(String value) {
        this.descripcionLocalidad = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad nivelAdministracion.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNivelAdministracion() {
        return nivelAdministracion;
    }

    /**
     * Define el valor de la propiedad nivelAdministracion.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNivelAdministracion(Long value) {
        this.nivelAdministracion = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreVia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreVia() {
        return nombreVia;
    }

    /**
     * Define el valor de la propiedad nombreVia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreVia(String value) {
        this.nombreVia = value;
    }

    /**
     * Obtiene el valor de la propiedad numVia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumVia() {
        return numVia;
    }

    /**
     * Define el valor de la propiedad numVia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumVia(String value) {
        this.numVia = value;
    }

    /**
     * Gets the value of the organizativasOfi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organizativasOfi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganizativasOfi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelacionOrganizativaOfiTF }
     * 
     * 
     */
    public List<RelacionOrganizativaOfiTF> getOrganizativasOfi() {
        if (organizativasOfi == null) {
            organizativasOfi = new ArrayList<RelacionOrganizativaOfiTF>();
        }
        return this.organizativasOfi;
    }

    /**
     * Gets the value of the servicios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the servicios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServicios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getServicios() {
        if (servicios == null) {
            servicios = new ArrayList<Long>();
        }
        return this.servicios;
    }

    /**
     * Gets the value of the sirOfi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sirOfi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSirOfi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelacionSirOfiTF }
     * 
     * 
     */
    public List<RelacionSirOfiTF> getSirOfi() {
        if (sirOfi == null) {
            sirOfi = new ArrayList<RelacionSirOfiTF>();
        }
        return this.sirOfi;
    }

    /**
     * Obtiene el valor de la propiedad tipoOficina.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTipoOficina() {
        return tipoOficina;
    }

    /**
     * Define el valor de la propiedad tipoOficina.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTipoOficina(Long value) {
        this.tipoOficina = value;
    }

}
