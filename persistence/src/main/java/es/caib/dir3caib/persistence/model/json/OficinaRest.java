package es.caib.dir3caib.persistence.model.json;

import es.caib.dir3caib.persistence.model.*;
import es.caib.dir3caib.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 *         Date: 27/02/14
 */
public class OficinaRest implements Serializable {

    private String codigo;
    private String denominacion;
    private String estado;    //CatEstadoEntidad
    private Long nivelAdministracion;  //CatNivelAdministracion
    private Long tipoOficina;  //CatJerarquiaOficina
    private String codUoResponsable;      //Unidad
    private String codOfiResponsable;   //Oficina
    private List<RelacionSirOfiRest> sirOfi;
    private List<RelacionOrganizativaOfiRest> organizativasOfi;
    private Long codigoPais;
    private Long codigoComunidad;
    private String descripcionLocalidad; //LOCALIDAD
    private String nombreVia;
    private String numVia;
    private Long codigoTipoVia;
    private String codPostal;
    private List<Long> servicios;
    private List<ContactoRest> contactos;

    public OficinaRest() {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getNivelAdministracion() {
        return nivelAdministracion;
    }

    public void setNivelAdministracion(Long nivelAdministracion) {
        this.nivelAdministracion = nivelAdministracion;
    }

    public Long getTipoOficina() {
        return tipoOficina;
    }

    public void setTipoOficina(Long tipoOficina) {
        this.tipoOficina = tipoOficina;
    }

    public String getCodUoResponsable() {
        return codUoResponsable;
    }

    public void setCodUoResponsable(String codUoResponsable) {
        this.codUoResponsable = codUoResponsable;
    }

    public String getCodOfiResponsable() {
        return codOfiResponsable;
    }

    public void setCodOfiResponsable(String codOfiResponsable) {
        this.codOfiResponsable = codOfiResponsable;
    }

    public List<RelacionSirOfiRest> getSirOfi() {
        return sirOfi;
    }

    public void setSirOfi(List<RelacionSirOfiRest> sirOfi) {
        this.sirOfi = sirOfi;
    }

    public Long getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Long codigoPais) {
        this.codigoPais = codigoPais;
    }

    public Long getCodigoComunidad() {
        return codigoComunidad;
    }

    public void setCodigoComunidad(Long codigoComunidad) {
        this.codigoComunidad = codigoComunidad;
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

    public List<Long> getServicios() {
        return servicios;
    }

    public void setServicios(List<Long> servicios) {
        this.servicios = servicios;
    }

    public List<ContactoRest> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoRest> contactos) {
        this.contactos = contactos;
    }

    public void setContactosRest(List<ContactoOfi> contactos) {
        List<ContactoRest> contactoRestList = new ArrayList<ContactoRest>();
        for (ContactoOfi contactoOfi : contactos) {
            contactoRestList.add(ContactoRest.generar(contactoOfi));
        }
        this.contactos = contactoRestList;
    }
    
    /**
     * Transforma un List de {@link es.caib.dir3caib.persistence.model.RelacionSirOfi} en {@link es.caib.dir3caib.persistence.model.ws.RelacionSirOfiTF}
     * @param sirOfi
     */
    public void setSirOfiRest(List<RelacionSirOfi> sirOfi) {

        List<RelacionSirOfiRest> sirOfiRestList = new ArrayList<RelacionSirOfiRest>();
        for (RelacionSirOfi relacionSirOfi : sirOfi) {
        	sirOfiRestList.add(RelacionSirOfiRest.generar(relacionSirOfi));
        }
        this.sirOfi = sirOfiRestList;
    }

    public List<RelacionOrganizativaOfiRest> getOrganizativasOfi() {
        return organizativasOfi;
    }

    public void setOrganizativasOfi(List<RelacionOrganizativaOfiRest> organizativasOfi) {
        this.organizativasOfi = organizativasOfi;
    }

    /**
     * Transforma un List de {@link es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi} en {@link es.caib.dir3caib.persistence.model.ws.RelacionOrganizativaOfiTF}
     * @param organizativasOfi
     */
    public void setOrganizativasOfiRest(List<RelacionOrganizativaOfi> organizativasOfi) {

        List<RelacionOrganizativaOfiRest> relacionOrganizativaOfiRestList = new ArrayList<RelacionOrganizativaOfiRest>();
        for (RelacionOrganizativaOfi relacionOrganizativaOfi : organizativasOfi) {
            relacionOrganizativaOfiRestList.add(RelacionOrganizativaOfiRest.generar(relacionOrganizativaOfi));
        }
        this.organizativasOfi = relacionOrganizativaOfiRestList;
    }

    public void rellenar(Oficina oficina, boolean denominacionOficial){
        this.setCodigo(oficina.getCodigo());
        this.setDenominacion((denominacionOficial && Utils.isNotEmpty(oficina.getDenomLenguaCooficial())) ? oficina.getDenomLenguaCooficial() : oficina.getDenominacion());
        this.setEstado(oficina.getEstado().getCodigoEstadoEntidad());
        if(oficina.getCodPais()!=null) {
            this.setCodigoPais(oficina.getCodPais().getCodigoPais());
        } else {
            this.setCodigoPais(null);
        }
        if(oficina.getCodComunidad()!=null) {
            this.setCodigoComunidad(oficina.getCodComunidad().getCodigoComunidad());
        } else {
            this.setCodigoComunidad(null);
        }
        if(oficina.getLocalidad()!=null) {
            this.setDescripcionLocalidad(oficina.getLocalidad().getDescripcionLocalidad());
        } else {
            this.setDescripcionLocalidad(null);
        }
        this.setNombreVia(oficina.getNombreVia());
        this.setNumVia(oficina.getNumVia());
        if(oficina.getTipoVia()!=null) {
            this.setCodigoTipoVia(oficina.getTipoVia().getCodigoTipoVia());
        } else {
            this.setCodigoTipoVia(null);
        }
        this.setCodPostal(oficina.getCodPostal());

        if(oficina.getNivelAdministracion() != null){
            this.setNivelAdministracion(oficina.getNivelAdministracion().getCodigoNivelAdministracion());
        } else {
            this.setNivelAdministracion(null);
        }

        if(oficina.getTipoOficina() != null){
            this.setTipoOficina(oficina.getTipoOficina().getCodigoJerarquiaOficina());
        } else {
            this.setTipoOficina(null);
        }
        this.setCodUoResponsable(oficina.getCodUoResponsable().getCodigoDir3());

        if(oficina.getCodOfiResponsable() != null){
            this.setCodOfiResponsable(oficina.getCodOfiResponsable().getCodigo());
        } else {
            this.setCodOfiResponsable(null);
        }

        if(oficina.getSirOfi() != null){
        	List<RelacionSirOfi> listaRelacionSirOfi = new ArrayList<RelacionSirOfi>();
        	for (RelacionSirOfi r : oficina.getSirOfi())
        		if(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE.equals(r.getEstado().getCodigoEstadoEntidad()))
        			listaRelacionSirOfi.add(r);
            this.setSirOfiRest(listaRelacionSirOfi);
        } else {
            this.setSirOfiRest(null);
        }

        if(oficina.getOrganizativasOfi() != null){
            this.setOrganizativasOfiRest(oficina.getOrganizativasOfi());
        } else {
            this.setOrganizativasOfiRest(null);
        }

        //TODO ADAPTAR
        /*if(oficina.getServicios() != null){
            List<Long> serviciosIds= new ArrayList<Long>();
            for(CatServicio servicio: oficina.getServicios()){
                serviciosIds.add(servicio.getCodServicio());
            }
            this.setServicios(serviciosIds);
        } else {
            this.setServicios(null);
        }*/

        if (oficina.getContactos() != null) {
            this.setContactosRest(oficina.getContactos());
        } else {
            this.setContactosRest(null);
        }
    }


    public static OficinaRest generar(Oficina oficina, boolean denominacionOficial){
        OficinaRest oficinaRest = null;
        if(oficina!=null){
        	oficinaRest = new OficinaRest();
        	oficinaRest.rellenar(oficina,denominacionOficial);
        }
        return oficinaRest;
    }
}
