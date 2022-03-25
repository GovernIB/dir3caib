package es.caib.dir3caib.persistence.model.json;

import es.caib.dir3caib.persistence.model.ContactoOfi;
import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;
import es.caib.dir3caib.persistence.model.ws.ContactoTF;

/**
 * Created by mgonzalez on 06/06/2017.
 */
public class ContactoRest {

    private String tipoContacto;
    private String valorContacto;

    public ContactoRest() {
    }

    public String getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getValorContacto() {
        return valorContacto;
    }

    public void setValorContacto(String valorContacto) {
        this.valorContacto = valorContacto;
    }

    public void rellenar(ContactoOfi contactoOfi) {

        this.setTipoContacto(contactoOfi.getTipoContacto().getDescripcionTipoContacto());
        this.setValorContacto(contactoOfi.getValorContacto());

    }

    public static ContactoRest generar(ContactoOfi contactoOfi) {
        ContactoRest contactoTF = new ContactoRest();
        if (contactoOfi != null) {
            contactoTF.rellenar(contactoOfi);
        }
        return contactoTF;
    }
    
    public static ContactoRest generar(ContactoTF contactoTF) {
    	ContactoRest contactoRest = new ContactoRest();
    	if (contactoTF != null) {
    		contactoRest.rellenar(contactoTF);
    	}
    	return contactoRest;
    }


    public void rellenar(ContactoUnidadOrganica contactoUO) {

        this.setTipoContacto(contactoUO.getTipoContacto().getDescripcionTipoContacto());
        this.setValorContacto(contactoUO.getValorContacto());

    }
    
    public void rellenar(ContactoTF contactoTF) {
    	this.setTipoContacto(contactoTF.getTipoContacto());
    	this.setValorContacto(contactoTF.getValorContacto());
    }

    public static ContactoRest generar(ContactoUnidadOrganica contactoUO) {
        ContactoRest contactoTF = new ContactoRest();
        if (contactoUO != null) {
            contactoTF.rellenar(contactoUO);
        }
        return contactoTF;
    }
}
