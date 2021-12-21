package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CodigoUnidadOrganica;
import es.caib.dir3caib.persistence.model.NifCifUnidadOrganica;

import javax.ejb.Local;

@Local
public interface NifCifUOLocal extends BaseEjb<NifCifUnidadOrganica, Long> {
}
