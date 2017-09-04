package es.caib.dir3caib.persistence.model.ws;

import es.caib.dir3caib.persistence.model.ContactoOfi;
import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;

/**
 * Created by mgonzalez on 06/06/2017.
 */
public class ContactoTF {

    private String tipoContacto;
    private String valorContacto;

    public ContactoTF() {
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

    public static ContactoTF generar(ContactoOfi contactoOfi) {
        ContactoTF contactoTF = new ContactoTF();
        if (contactoOfi != null) {
            contactoTF.rellenar(contactoOfi);
        }
        return contactoTF;
    }


    public void rellenar(ContactoUnidadOrganica contactoUO) {

        this.setTipoContacto(contactoUO.getTipoContacto().getDescripcionTipoContacto());
        this.setValorContacto(contactoUO.getValorContacto());

    }

    public static ContactoTF generar(ContactoUnidadOrganica contactoUO) {
        ContactoTF contactoTF = new ContactoTF();
        if (contactoUO != null) {
            contactoTF.rellenar(contactoUO);
        }
        return contactoTF;
    }
}
