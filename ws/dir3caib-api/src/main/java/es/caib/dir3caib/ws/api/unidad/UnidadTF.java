
package es.caib.dir3caib.ws.api.unidad;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para unidadTF complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="unidadTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codAmbComunidad" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codAmbProvincia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codEdpPrincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codPostal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codUnidadRaiz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codUnidadSuperior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoAmbPais" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoAmbitoTerritorial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoEstadoEntidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoTipoVia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="competencias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactos" type="{http://unidad.ws.dir3caib.caib.es/}contactoTF" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="denominacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionLocalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esEdp" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="fechaAltaOficial" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaAnulacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaBajaOficial" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaExtincion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="historicosUO" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nivelAdministracion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nivelJerarquico" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nombreVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unidadTF", propOrder = {
    "codAmbComunidad",
    "codAmbProvincia",
    "codEdpPrincipal",
    "codPostal",
    "codUnidadRaiz",
    "codUnidadSuperior",
    "codigo",
    "codigoAmbPais",
    "codigoAmbitoTerritorial",
    "codigoEstadoEntidad",
    "codigoTipoVia",
    "competencias",
    "contactos",
    "denominacion",
    "descripcionLocalidad",
    "esEdp",
    "fechaAltaOficial",
    "fechaAnulacion",
    "fechaBajaOficial",
    "fechaExtincion",
    "historicosUO",
    "nivelAdministracion",
    "nivelJerarquico",
    "nombreVia",
    "numVia"
})
@XmlSeeAlso({
    UnidadWs.class
})
public class UnidadTF {

    protected Long codAmbComunidad;
    protected Long codAmbProvincia;
    protected String codEdpPrincipal;
    protected String codPostal;
    protected String codUnidadRaiz;
    protected String codUnidadSuperior;
    protected String codigo;
    protected Long codigoAmbPais;
    protected String codigoAmbitoTerritorial;
    protected String codigoEstadoEntidad;
    protected Long codigoTipoVia;
    protected String competencias;
    @XmlElement(nillable = true)
    protected List<ContactoTF> contactos;
    protected String denominacion;
    protected String descripcionLocalidad;
    protected boolean esEdp;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaAltaOficial;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaAnulacion;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaBajaOficial;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Timestamp fechaExtincion;
    @XmlElement(nillable = true)
    protected List<String> historicosUO;
    protected Long nivelAdministracion;
    protected Long nivelJerarquico;
    protected String nombreVia;
    protected String numVia;

    /**
     * Obtiene el valor de la propiedad codAmbComunidad.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodAmbComunidad() {
        return codAmbComunidad;
    }

    /**
     * Define el valor de la propiedad codAmbComunidad.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodAmbComunidad(Long value) {
        this.codAmbComunidad = value;
    }

    /**
     * Obtiene el valor de la propiedad codAmbProvincia.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodAmbProvincia() {
        return codAmbProvincia;
    }

    /**
     * Define el valor de la propiedad codAmbProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodAmbProvincia(Long value) {
        this.codAmbProvincia = value;
    }

    /**
     * Obtiene el valor de la propiedad codEdpPrincipal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEdpPrincipal() {
        return codEdpPrincipal;
    }

    /**
     * Define el valor de la propiedad codEdpPrincipal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEdpPrincipal(String value) {
        this.codEdpPrincipal = value;
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
     * Obtiene el valor de la propiedad codUnidadRaiz.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodUnidadRaiz() {
        return codUnidadRaiz;
    }

    /**
     * Define el valor de la propiedad codUnidadRaiz.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodUnidadRaiz(String value) {
        this.codUnidadRaiz = value;
    }

    /**
     * Obtiene el valor de la propiedad codUnidadSuperior.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodUnidadSuperior() {
        return codUnidadSuperior;
    }

    /**
     * Define el valor de la propiedad codUnidadSuperior.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodUnidadSuperior(String value) {
        this.codUnidadSuperior = value;
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
     * Obtiene el valor de la propiedad codigoAmbPais.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoAmbPais() {
        return codigoAmbPais;
    }

    /**
     * Define el valor de la propiedad codigoAmbPais.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoAmbPais(Long value) {
        this.codigoAmbPais = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoAmbitoTerritorial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoAmbitoTerritorial() {
        return codigoAmbitoTerritorial;
    }

    /**
     * Define el valor de la propiedad codigoAmbitoTerritorial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoAmbitoTerritorial(String value) {
        this.codigoAmbitoTerritorial = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoEstadoEntidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoEstadoEntidad() {
        return codigoEstadoEntidad;
    }

    /**
     * Define el valor de la propiedad codigoEstadoEntidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoEstadoEntidad(String value) {
        this.codigoEstadoEntidad = value;
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
     * Obtiene el valor de la propiedad competencias.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompetencias() {
        return competencias;
    }

    /**
     * Define el valor de la propiedad competencias.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompetencias(String value) {
        this.competencias = value;
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
     * Obtiene el valor de la propiedad esEdp.
     * 
     */
    public boolean isEsEdp() {
        return esEdp;
    }

    /**
     * Define el valor de la propiedad esEdp.
     * 
     */
    public void setEsEdp(boolean value) {
        this.esEdp = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaAltaOficial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Timestamp getFechaAltaOficial() {
        return fechaAltaOficial;
    }

    /**
     * Define el valor de la propiedad fechaAltaOficial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAltaOficial(Timestamp value) {
        this.fechaAltaOficial = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaAnulacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Timestamp getFechaAnulacion() {
        return fechaAnulacion;
    }

    /**
     * Define el valor de la propiedad fechaAnulacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAnulacion(Timestamp value) {
        this.fechaAnulacion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaBajaOficial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Timestamp getFechaBajaOficial() {
        return fechaBajaOficial;
    }

    /**
     * Define el valor de la propiedad fechaBajaOficial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaBajaOficial(Timestamp value) {
        this.fechaBajaOficial = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaExtincion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Timestamp getFechaExtincion() {
        return fechaExtincion;
    }

    /**
     * Define el valor de la propiedad fechaExtincion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaExtincion(Timestamp value) {
        this.fechaExtincion = value;
    }

    /**
     * Gets the value of the historicosUO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the historicosUO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistoricosUO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getHistoricosUO() {
        if (historicosUO == null) {
            historicosUO = new ArrayList<String>();
        }
        return this.historicosUO;
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
     * Obtiene el valor de la propiedad nivelJerarquico.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNivelJerarquico() {
        return nivelJerarquico;
    }

    /**
     * Define el valor de la propiedad nivelJerarquico.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNivelJerarquico(Long value) {
        this.nivelJerarquico = value;
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

}
