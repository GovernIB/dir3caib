package es.caib.dir3caib.persistence.ejb;


import es.caib.dir3caib.persistence.model.Sincronizacion;

import javax.ejb.Local;

/**
 * @author anadal
 */
@Local
public interface ImportadorUnidadesLocal {


    /**
     * Método que importa el contenido de los archivos de las unidades, historicos y contactos descargados previamente a través
     * de los WS.
     *
     * @return
     * @throws Exception
     */
    void importarUnidades(Sincronizacion sincronizacion) throws Exception;


}
