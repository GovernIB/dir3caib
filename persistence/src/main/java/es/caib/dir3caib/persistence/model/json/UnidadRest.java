package es.caib.dir3caib.persistence.model.json;

import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.HistoricoUO;
import es.caib.dir3caib.persistence.model.Unidad;
import es.caib.dir3caib.persistence.model.ws.ContactoTF;
import es.caib.dir3caib.persistence.model.ws.UnidadTF;
import es.caib.dir3caib.persistence.model.ws.v2.UnidadWs;
import es.caib.dir3caib.persistence.utils.Nodo;
import es.caib.dir3caib.utils.Utils;

import java.util.*;

/**
 * Created by Fundació BIT.
 *
 * @author jagarcia
 * Date: 12/03/22
 */
public class UnidadRest {

    private String codigo;
    private Long version;
    private String denominacion;
    private String denominacionCooficial;
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
    private String nifCif;
    private Set<String> historicosUO;
    private List<ContactoRest> contactos;


    public UnidadRest() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public Long getVersion() {
    	return version;
    }
    
    public void setVersion(Long version) {
    	this.version = version;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDenominacionCooficial() {
		return denominacionCooficial;
	}

	public void setDenominacionCooficial(String denominacionCooficial) {
		this.denominacionCooficial = denominacionCooficial;
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

    public String getNifCif() {
        return nifCif;
    }

    public void setNifCif(String nifCif) {
        this.nifCif = nifCif;
    }

    public Set<String> getHistoricosUO() {
        return historicosUO;
    }

    public void setHistoricosUO(Set<String> historicosUO) {
        this.historicosUO = historicosUO;
    }


    public List<ContactoRest> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoRest> contactos) {
        this.contactos = contactos;
    }

    public void setContactosRest(List<ContactoUnidadOrganica> contactos) {
        List<ContactoRest> contactoTFList = new ArrayList<ContactoRest>();

        for (ContactoUnidadOrganica contactoOfi : contactos) {
            ContactoRest contactoTF = ContactoRest.generar(contactoOfi);
            contactoTFList.add(contactoTF);
        }

        this.contactos = contactoTFList;
    }

    public void rellenar(Unidad unidad) {
        this.setCodigo(unidad.getCodigo());
        this.setVersion(unidad.getVersion());
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
        if (Utils.isNotEmpty(unidad.getDenomLenguaCooficial()))
        	this.setDenominacionCooficial(unidad.getDenomLenguaCooficial());
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
        this.setNifCif(unidad.getNifcif());
        // Enviamos los historicos a regweb para la gestión del organigrama
        Set<String> sHistoricosUO = new HashSet<String>();
       for (HistoricoUO historico : unidad.getHistoricosAnterior()) {
            sHistoricosUO.add(historico.getUnidadUltima().getCodigoDir3());
        }
        this.setHistoricosUO(sHistoricosUO);

        if (unidad.getContactos() != null) {
            this.setContactosRest(unidad.getContactos());
        } else {
            this.setContactosRest(null);
        }

    }

    public void rellenarLigero(Unidad unidad) {
        this.setCodigo(unidad.getCodigo());
        this.setVersion(unidad.getVersion());
        this.setDenominacion(unidad.getDenominacion());
        if (Utils.isNotEmpty(unidad.getDenomLenguaCooficial()))
        	this.setDenominacionCooficial(unidad.getDenomLenguaCooficial());
    }


    public static UnidadRest generar(Unidad unidad) {
    	UnidadRest unidadTF = null;
        if (unidad != null) {
            unidadTF = new UnidadRest();
            unidadTF.rellenar(unidad);
        }

        return unidadTF;
    }

    public static UnidadRest generarLigero(Unidad unidad) {
    	UnidadRest unidadTF = null;
        if (unidad != null) {
            unidadTF = new UnidadRest();
            unidadTF.setCodigo(unidad.getCodigo());
            unidadTF.setVersion(unidad.getVersion());
            unidadTF.setDenominacion(unidad.getDenominacion());
            if (Utils.isNotEmpty(unidad.getDenomLenguaCooficial()))
            	unidadTF.setDenominacionCooficial(unidad.getDenomLenguaCooficial());
            unidadTF.setCodUnidadRaiz(unidad.getCodUnidadRaiz().getCodigo());
            unidadTF.setCodUnidadSuperior(unidad.getCodUnidadSuperior().getCodigo());
            unidadTF.setEsEdp(unidad.isEsEdp());
        }

        return unidadTF;
    }
    
    public static UnidadRest toUnidadRest ( UnidadTF unidadTF) {
    	
    	UnidadRest unidadRest = null;
    	if (unidadTF != null) {
    		unidadRest = new UnidadRest();
    		unidadRest.setCodigo(unidadTF.getCodigo());
    		unidadRest.setDenominacion(unidadTF.getDenominacion());
    		unidadRest.setDenominacionCooficial(unidadTF.getDenominacion());
    		unidadRest.setCodigoEstadoEntidad(unidadTF.getCodigoEstadoEntidad());
    		unidadRest.setNivelJerarquico(unidadTF.getNivelJerarquico());
    		unidadRest.setCodUnidadSuperior(unidadTF.getCodUnidadSuperior());
    	    unidadRest.setCodUnidadRaiz(unidadTF.getCodUnidadRaiz());
    	    unidadRest.setEsEdp(unidadTF.isEsEdp());
    	    unidadRest.setCodEdpPrincipal((Utils.isNotEmpty(unidadTF.getCodEdpPrincipal())) ? unidadTF.getCodEdpPrincipal() : null);
    	    unidadRest.setCompetencias(unidadTF.getCompetencias());
    	    unidadRest.setFechaAltaOficial(unidadTF.getFechaAltaOficial());
    	    unidadRest.setFechaBajaOficial(unidadTF.getFechaBajaOficial());
    	    unidadRest.setFechaExtincion(unidadTF.getFechaExtincion());
    	    unidadRest.setFechaAnulacion(unidadTF.getFechaAnulacion());
    	    unidadRest.setNivelAdministracion(unidadTF.getNivelAdministracion());
    	    unidadRest.setCodigoAmbitoTerritorial((Utils.isNotEmpty(unidadTF.getCodigoAmbitoTerritorial())) ? unidadTF.getCodigoAmbitoTerritorial() : null);
    	    unidadRest.setCodigoAmbPais((unidadTF.getCodigoAmbPais() != null) ? unidadTF.getCodigoAmbPais() : null);
    	    unidadRest.setCodAmbProvincia((unidadTF.getCodAmbProvincia() != null) ? unidadTF.getCodAmbProvincia() : null);
    	    unidadRest.setCodAmbComunidad((unidadTF.getCodAmbComunidad() != null) ? unidadTF.getCodAmbComunidad() : null);
    	    unidadRest.setDescripcionLocalidad((Utils.isNotEmpty(unidadTF.getDescripcionLocalidad())) ? unidadTF.getDescripcionLocalidad() : null);
    	    unidadRest.setNombreVia(unidadTF.getNombreVia());
    	    unidadRest.setNumVia(unidadTF.getNumVia());
    	    unidadRest.setCodigoTipoVia((unidadTF.getCodigoTipoVia() != null) ? unidadTF.getCodigoTipoVia() : null);
    	    unidadRest.setCodPostal(unidadTF.getCodPostal());
    	    unidadRest.setHistoricosUO(unidadTF.getHistoricosUO());
    	    
    	    List<ContactoRest> contactosRest = new ArrayList<ContactoRest>();
    	    for (ContactoTF contacto : unidadTF.getContactos()) {
    	    	contactosRest.add(ContactoRest.generar(contacto));
    	    }
    	    unidadRest.setContactos(contactosRest);
    		
    	}
    	return unidadRest;
    }
    
    
	public static UnidadRest toUnidadRest(UnidadWs unidad, boolean codigoDir3, boolean denominacionCooficial) {

		UnidadRest unidadRest = null;
		int posSeparador = 0;

		if (unidad != null) {
			unidadRest = new UnidadRest();

			String codigo = unidad.getCodigo();
			if (codigoDir3 && codigo != null) {
				posSeparador = codigo.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION);
				codigo = (posSeparador > 0) ? codigo.substring(0, posSeparador) : codigo;
			}
			unidadRest.setCodigo(codigo);
			
			unidadRest.setVersion(unidad.getVersion());

			unidadRest.setDenominacion((denominacionCooficial && Utils.isNotEmpty(unidad.getDenomLenguaCooficial()))
					? unidad.getDenomLenguaCooficial()
					: unidad.getDenominacion());
			if (Utils.isNotEmpty(unidad.getDenomLenguaCooficial()))
				unidadRest.setDenominacionCooficial(unidad.getDenomLenguaCooficial());
			unidadRest.setCodigoEstadoEntidad(unidad.getCodigoEstadoEntidad());
			unidadRest.setNivelJerarquico(unidad.getNivelJerarquico());

			String codigoUnidadSuperior = unidad.getCodUnidadSuperior();
			if (codigoDir3 && codigoUnidadSuperior != null) {
				posSeparador = codigoUnidadSuperior.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION);
				codigoUnidadSuperior = (posSeparador > 0) ? codigoUnidadSuperior.substring(0, posSeparador)
						: codigoUnidadSuperior;
			}
			unidadRest.setCodUnidadSuperior(codigoUnidadSuperior);

			String codigoUnidadRaiz = unidad.getCodUnidadRaiz();
			if (codigoDir3 && codigoUnidadRaiz != null) {
				posSeparador = codigoUnidadRaiz.indexOf(Dir3caibConstantes.SEPARADOR_CODIGO_VERSION);
				codigoUnidadRaiz = (posSeparador > 0) ? codigoUnidadRaiz.substring(0, posSeparador) : codigoUnidadRaiz;
			}
			unidadRest.setCodUnidadRaiz(codigoUnidadRaiz);

			unidadRest.setEsEdp(unidad.isEsEdp());
			unidadRest.setCodEdpPrincipal(
					(Utils.isNotEmpty(unidad.getCodEdpPrincipal())) ? unidad.getCodEdpPrincipal() : null);
			unidadRest.setCompetencias(unidad.getCompetencias());
			unidadRest.setFechaAltaOficial(unidad.getFechaAltaOficial());
			unidadRest.setFechaBajaOficial(unidad.getFechaBajaOficial());
			unidadRest.setFechaExtincion(unidad.getFechaExtincion());
			unidadRest.setFechaAnulacion(unidad.getFechaAnulacion());
			unidadRest.setNivelAdministracion(unidad.getNivelAdministracion());
			unidadRest.setCodigoAmbitoTerritorial(
					(Utils.isNotEmpty(unidad.getCodigoAmbitoTerritorial())) ? unidad.getCodigoAmbitoTerritorial()
							: null);
			unidadRest.setCodigoAmbPais((unidad.getCodigoAmbPais() != null) ? unidad.getCodigoAmbPais() : null);
			unidadRest.setCodAmbProvincia((unidad.getCodAmbProvincia() != null) ? unidad.getCodAmbProvincia() : null);
			unidadRest.setCodAmbComunidad((unidad.getCodAmbComunidad() != null) ? unidad.getCodAmbComunidad() : null);
			unidadRest.setDescripcionLocalidad(
					(Utils.isNotEmpty(unidad.getDescripcionLocalidad())) ? unidad.getDescripcionLocalidad() : null);
			unidadRest.setNombreVia(unidad.getNombreVia());
			unidadRest.setNumVia(unidad.getNumVia());
			unidadRest.setCodigoTipoVia((unidad.getCodigoTipoVia() != null) ? unidad.getCodigoTipoVia() : null);
			unidadRest.setCodPostal(unidad.getCodPostal());
			unidadRest.setHistoricosUO(unidad.getHistoricosUO());
			unidadRest.setNifCif(unidad.getNifCif());

			List<ContactoRest> contactosRest = new ArrayList<ContactoRest>();
			if (unidad.getContactos() != null)
				for (ContactoTF contacto : unidad.getContactos()) {
					contactosRest.add(ContactoRest.generar(contacto));
				}
			unidadRest.setContactos(contactosRest);

		}
		return unidadRest;
	}
}
