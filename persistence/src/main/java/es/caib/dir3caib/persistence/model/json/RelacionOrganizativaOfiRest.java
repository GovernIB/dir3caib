package es.caib.dir3caib.persistence.model.json;

import es.caib.dir3caib.persistence.model.RelacionOrganizativaOfi;

/**
 * Created by Fundació BIT.
 *
 * @author jagarcia Date: 10/03/2022
 */
public class RelacionOrganizativaOfiRest {

	private String oficina;
	private String unidad;
	private String estado;

	public RelacionOrganizativaOfiRest() {
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void rellenar(RelacionOrganizativaOfi relacionOrganizativaOfi) {
		this.setEstado(relacionOrganizativaOfi.getEstado().getCodigoEstadoEntidad());
		this.setOficina(relacionOrganizativaOfi.getOficina().getCodigo());
		this.setUnidad(relacionOrganizativaOfi.getUnidad().getCodigoDir3());
	}

	public static RelacionOrganizativaOfiRest generar(RelacionOrganizativaOfi relacionOrganizativaOfi) {
		RelacionOrganizativaOfiRest relacionOrganizativaOfiTF = new RelacionOrganizativaOfiRest();
		if (relacionOrganizativaOfi != null) {
			relacionOrganizativaOfiTF.rellenar(relacionOrganizativaOfi);
		}
		return relacionOrganizativaOfiTF;
	}
}
