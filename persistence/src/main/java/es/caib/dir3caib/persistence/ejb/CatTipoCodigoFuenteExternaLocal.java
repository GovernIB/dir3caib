package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.CatTipoCodigoFuenteExterna;

public interface CatTipoCodigoFuenteExternaLocal extends BaseEjb<CatTipoCodigoFuenteExterna, String> {
	  
	  void deleteAll() throws Exception;
	}

