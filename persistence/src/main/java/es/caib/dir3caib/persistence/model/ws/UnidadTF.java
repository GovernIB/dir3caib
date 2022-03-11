package es.caib.dir3caib.persistence.model.ws;

import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;
import es.caib.dir3caib.persistence.model.HistoricoUO;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.ws.v2.UnidadWs;
import es.caib.dir3caib.utils.Utils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 12/02/14
 */
public class UnidadTF implements Serializable {

    private String codigo;
    private String denominacion;
    private String codigoEstadoEntidad;
    private Long nivelJerarquico;
    private String codUnidadSuperior;
    private String codUnidadRaiz;
    private boolean esEdp;
    private String codEdpPrincipal;
    private String competencias;
    private Date fechaAltaOficial;
    private Date fechaBajaOficial;
    private Date fechaExtincion;
    private Date fechaAnulacion;
    private Long nivelAdministracion;
    private String codigoAmbitoTerritorial;
    private Long codigoAmbPais;
    private Long codAmbProvincia;
    private Long codAmbComunidad;
    private String descripcionLocalidad;
    private String nombreVia;
    private String numVia;
    private Long codigoTipoVia;
    private String codPostal;
    private Set<String> historicosUO;
    private List<ContactoTF> contactos;


    public UnidadTF() {
    }

    public UnidadTF(UnidadTF unidadTF) {
        codigo = unidadTF.codigo;
        denominacion = unidadTF.denominacion;
        codigoEstadoEntidad = unidadTF.codigoEstadoEntidad;
        nivelJerarquico = unidadTF.nivelJerarquico;
        codUnidadSuperior = unidadTF.codUnidadSuperior;
        codUnidadRaiz = unidadTF.codUnidadRaiz;
        esEdp = unidadTF.esEdp;
        codEdpPrincipal = unidadTF.codEdpPrincipal;
        competencias = unidadTF.competencias;
        fechaAltaOficial = unidadTF.fechaAltaOficial;
        fechaBajaOficial = unidadTF.fechaBajaOficial;
        fechaExtincion = unidadTF.fechaExtincion;
        fechaAnulacion = unidadTF.fechaAnulacion;
        nivelAdministracion = unidadTF.nivelAdministracion;
        codigoAmbitoTerritorial = unidadTF.codigoAmbitoTerritorial;
        codigoAmbPais = unidadTF.codigoAmbPais;
        codAmbProvincia = unidadTF.codAmbProvincia;
        codAmbComunidad = unidadTF.codAmbComunidad;
        descripcionLocalidad = unidadTF.descripcionLocalidad;
        nombreVia = unidadTF.nombreVia;
        numVia = unidadTF.numVia;
        codigoTipoVia = unidadTF.codigoTipoVia;
        codPostal = unidadTF.codPostal;
        historicosUO = unidadTF.historicosUO;
        contactos = unidadTF.contactos;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getCodigoEstadoEntidad() {
        return codigoEstadoEntidad;
    }

    public void setCodigoEstadoEntidad(String codigoEstadoEntidad) {
        this.codigoEstadoEntidad = codigoEstadoEntidad;
    }

    public Long getNivelJerarquico() {
        return nivelJerarquico;
    }

    public void setNivelJerarquico(Long nivelJerarquico) {
        this.nivelJerarquico = nivelJerarquico;
    }

    public String getCodUnidadSuperior() {
        return codUnidadSuperior;
    }

    public void setCodUnidadSuperior(String codUnidadSuperior) {
        this.codUnidadSuperior = codUnidadSuperior;
    }

    public String getCodUnidadRaiz() {
        return codUnidadRaiz;
    }

    public void setCodUnidadRaiz(String codUnidadRaiz) {
        this.codUnidadRaiz = codUnidadRaiz;
    }

    public boolean isEsEdp() {
        return esEdp;
    }

    public void setEsEdp(boolean esEdp) {
        this.esEdp = esEdp;
    }

    public String getCodEdpPrincipal() {
        return codEdpPrincipal;
    }

    public void setCodEdpPrincipal(String codEdpPrincipal) {
        this.codEdpPrincipal = codEdpPrincipal;
    }

    public String getCompetencias() {
        return competencias;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }

    public Date getFechaAltaOficial() {
        return fechaAltaOficial;
    }

    public void setFechaAltaOficial(Date fechaAltaOficial) {
        this.fechaAltaOficial = fechaAltaOficial;
    }

    public Date getFechaBajaOficial() {
        return fechaBajaOficial;
    }

    public void setFechaBajaOficial(Date fechaBajaOficial) {
        this.fechaBajaOficial = fechaBajaOficial;
    }

    public Date getFechaExtincion() {
        return fechaExtincion;
    }

    public void setFechaExtincion(Date fechaExtincion) {
        this.fechaExtincion = fechaExtincion;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public Long getNivelAdministracion() {
        return nivelAdministracion;
    }

    public void setNivelAdministracion(Long nivelAdministracion) {
        this.nivelAdministracion = nivelAdministracion;
    }

    public String getCodigoAmbitoTerritorial() {
        return codigoAmbitoTerritorial;
    }

    public void setCodigoAmbitoTerritorial(String codigoAmbitoTerritorial) {
        this.codigoAmbitoTerritorial = codigoAmbitoTerritorial;
    }

    public Long getCodigoAmbPais() {
        return codigoAmbPais;
    }

    public void setCodigoAmbPais(Long codigoAmbPais) {
        this.codigoAmbPais = codigoAmbPais;
    }

    public Long getCodAmbProvincia() {
        return codAmbProvincia;
    }

    public void setCodAmbProvincia(Long codAmbProvincia) {
        this.codAmbProvincia = codAmbProvincia;
    }

    public Long getCodAmbComunidad() {
        return codAmbComunidad;
    }

    public void setCodAmbComunidad(Long codAmbComunidad) {
        this.codAmbComunidad = codAmbComunidad;
    }

    public String getDescripcionLocalidad() {
        return descripcionLocalidad;
    }

    public void setDescripcionLocalidad(String descripcionLocalidad) {
        this.descripcionLocalidad = descripcionLocalidad;
    }

    public String getNombreVia() {
        return nombreVia;
    }

    public void setNombreVia(String nombreVia) {
        this.nombreVia = nombreVia;
    }

    public String getNumVia() {
        return numVia;
    }

    public void setNumVia(String numVia) {
        this.numVia = numVia;
    }

    public Long getCodigoTipoVia() {
        return codigoTipoVia;
    }

    public void setCodigoTipoVia(Long codigoTipoVia) {
        this.codigoTipoVia = codigoTipoVia;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public Set<String> getHistoricosUO() {
        return historicosUO;
    }

    public void setHistoricosUO(Set<String> historicosUO) {
        this.historicosUO = historicosUO;
    }


    public List<ContactoTF> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoTF> contactos) {
        this.contactos = contactos;
    }

    public void setContactosTF(List<ContactoUnidadOrganica> contactos) {
        List<ContactoTF> contactoTFList = new ArrayList<ContactoTF>();

        for (ContactoUnidadOrganica contactoOfi : contactos) {
            ContactoTF contactoTF = ContactoTF.generar(contactoOfi);
            contactoTFList.add(contactoTF);
        }

        this.contactos = contactoTFList;
    }

    public void rellenar(Unidad unidad,boolean denominacionOficial) {
        this.setCodigo(unidad.getCodigo());
        this.setCodUnidadRaiz(unidad.getCodUnidadRaiz().getCodigo());
        this.setCodUnidadSuperior(unidad.getCodUnidadSuperior().getCodigo());
        this.setCodigoEstadoEntidad(unidad.getEstado().getCodigoEstadoEntidad());
        this.setEsEdp(unidad.isEsEdp());
        if (unidad.getCodEdpPrincipal() != null) {
            this.setCodEdpPrincipal(unidad.getCodEdpPrincipal().getCodigo());
        } else {
            this.setCodEdpPrincipal(null);
        }
        this.setCompetencias(unidad.getCompetencias());
        this.setDenominacion(unidad.getDenominacion());
        this.setDenominacion((denominacionOficial && Utils.isNotEmpty(unidad.getDenomLenguaCooficial())) ? unidad.getDenomLenguaCooficial() : unidad.getDenominacion());
        this.setFechaAltaOficial(unidad.getFechaAltaOficial());
        this.setFechaAnulacion(unidad.getFechaAnulacion());
        this.setFechaBajaOficial(unidad.getFechaBajaOficial());
        this.setFechaExtincion(unidad.getFechaExtincion());
        this.setNivelJerarquico(unidad.getNivelJerarquico());
        this.setNivelAdministracion(unidad.getNivelAdministracion().getCodigoNivelAdministracion());
        if (unidad.getCodAmbitoTerritorial() != null) {
            this.setCodigoAmbitoTerritorial(unidad.getCodAmbitoTerritorial().getCodigoAmbito());
        } else {
            this.setCodigoAmbitoTerritorial(null);
        }
        if (unidad.getCodAmbPais() != null) {
            this.setCodigoAmbPais(unidad.getCodAmbPais().getCodigoPais());
        } else {
            this.setCodigoAmbPais(null);
        }
        if (unidad.getCodAmbProvincia() != null) {
            this.setCodAmbProvincia(unidad.getCodAmbProvincia().getCodigoProvincia());
        } else {
            this.setCodAmbProvincia(null);
        }
        if (unidad.getCodAmbComunidad() != null) {
            this.setCodAmbComunidad(unidad.getCodAmbComunidad().getCodigoComunidad());
        } else {
            this.setCodAmbComunidad(null);
        }
        if (unidad.getCodLocalidad() != null) {
            this.setDescripcionLocalidad(unidad.getCodLocalidad().getDescripcionLocalidad());
        } else {
            this.setDescripcionLocalidad(null);
        }
        this.setNombreVia(unidad.getNombreVia());
        this.setNumVia(unidad.getNumVia());
        if (unidad.getTipoVia() != null) {
            this.setCodigoTipoVia(unidad.getTipoVia().getCodigoTipoVia());
        } else {
            this.setCodigoTipoVia(null);
        }
        this.setCodPostal(unidad.getCodPostal());
        // Enviamos los historicos a regweb para la gestión del organigrama
        Set<String> sHistoricosUO = new HashSet<String>();
       for (HistoricoUO historico : unidad.getHistoricosAnterior()) {
            sHistoricosUO.add(historico.getUnidadUltima().getCodigoDir3());
        }
        this.setHistoricosUO(sHistoricosUO);

        if (unidad.getContactos() != null) {
            this.setContactosTF(unidad.getContactos());
        } else {
            this.setContactosTF(null);
        }

    }

   /* public void rellenarLigero(Unidad unidad) {
        this.setCodigo(unidad.getCodigo());
        this.setDenominacion(unidad.getDenominacion());
    }*/


    public static UnidadTF generar(Unidad unidad,boolean denominacionOficial) {
        UnidadTF unidadTF = null;
        if (unidad != null) {
            unidadTF = new UnidadTF();
            unidadTF.rellenar(unidad, denominacionOficial);
        }

        return unidadTF;
    }

    public static UnidadTF generarLigero(Unidad unidad,boolean denominacionOficial) {
        UnidadTF unidadTF = null;
        if (unidad != null) {
            unidadTF = new UnidadTF();
            unidadTF.setCodigo(unidad.getCodigo());
            unidadTF.setDenominacion((denominacionOficial && Utils.isNotEmpty(unidad.getDenomLenguaCooficial())) ? unidad.getDenomLenguaCooficial() : unidad.getDenominacion());
            unidadTF.setCodUnidadRaiz(unidad.getCodUnidadRaiz().getCodigo());
            unidadTF.setCodUnidadSuperior(unidad.getCodUnidadSuperior().getCodigo());
            unidadTF.setEsEdp(unidad.isEsEdp());
        }

        return unidadTF;
    }
}
