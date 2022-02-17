package es.caib.dir3caib.back.form;

import es.caib.dir3caib.persistence.model.Unidad;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 20/02/14
 */
public class UnidadBusquedaForm {

    private Unidad unidad;
    private Integer pageNumber;
    private Boolean unidadRaiz;
    private Boolean denominacionCooficial = true;

    public UnidadBusquedaForm() {
    }

    public UnidadBusquedaForm(Unidad unidad, Integer pageNumber, Boolean unidadRaiz) {
        this.unidad = unidad;
        this.pageNumber = pageNumber;
        this.unidadRaiz = unidadRaiz;
    }
    
    public UnidadBusquedaForm(Unidad unidad, Integer pageNumber, Boolean unidadRaiz, Boolean denominacionCooficial) {
		this.unidad = unidad;
		this.pageNumber = pageNumber;
		this.unidadRaiz = unidadRaiz;
		this.denominacionCooficial = denominacionCooficial;
	}

	public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Boolean getUnidadRaiz() {
      return unidadRaiz;
    }

    public void setUnidadRaiz(Boolean unidadRaiz) {
      this.unidadRaiz = unidadRaiz;
    }

	public Boolean getDenominacionCooficial() {
		return denominacionCooficial;
	}

	public void setDenominacionCooficial(Boolean denominacionCooficial) {
		this.denominacionCooficial = denominacionCooficial;
	}
    
    
}
