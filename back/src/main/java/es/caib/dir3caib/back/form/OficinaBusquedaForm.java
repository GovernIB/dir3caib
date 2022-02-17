package es.caib.dir3caib.back.form;

import es.caib.dir3caib.persistence.model.Oficina;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 20/02/14
 */
public class OficinaBusquedaForm {

    private Oficina oficina;
    private Integer pageNumber;
    private Boolean denominacionCooficial = true;

    public OficinaBusquedaForm() {
    }

    public OficinaBusquedaForm(Oficina oficina, Integer pageNumber) {
        this.oficina = oficina;
        this.pageNumber = pageNumber;
    }

    public OficinaBusquedaForm(Oficina oficina, Integer pageNumber, Boolean denominacionCooficial) {
		this.oficina = oficina;
		this.pageNumber = pageNumber;
		this.denominacionCooficial = denominacionCooficial;
	}

	public Oficina getOficina() {
      return oficina;
    }

    public void setOficina(Oficina oficina) {
      this.oficina = oficina;
    }

    public Integer getPageNumber() {
          return pageNumber;
      }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

	public Boolean getDenominacionCooficial() {
		return denominacionCooficial;
	}

	public void setDenominacionCooficial(Boolean denominacionCooficial) {
		this.denominacionCooficial = denominacionCooficial;
	}

}
