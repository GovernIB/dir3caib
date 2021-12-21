package es.caib.dir3caib.persistence.ejb;


import es.caib.dir3caib.persistence.model.CodigoUnidadOrganica;
import es.caib.dir3caib.persistence.model.ContactoUnidadOrganica;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CodigoUOLocal extends BaseEjb<CodigoUnidadOrganica, Long>{


}
