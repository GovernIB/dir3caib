package es.caib.dir3caib.persistence.ejb;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.persistence.model.Sincronizacion;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 */
@Stateless(name = "ImportadorEJB")
@SecurityDomain("seycon")
@RunAs(Dir3caibConstantes.DIR_ADMIN)
@PermitAll
public class ImportadorBean implements ImportadorLocal {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "dir3caib/ImportadorUnidadesEJB/local")
    private ImportadorUnidadesLocal importadorUnidades;

    @EJB(mappedName = "dir3caib/ImportadorOficinasEJB/local")
    private ImportadorOficinasLocal importadorOficinas;



    /**
     * @param sincronizacion
     * @throws Exception
     */
    @Override
    @TransactionTimeout(value = 40000)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void importarUnidadesOficinas(Sincronizacion sincronizacion) throws Exception {

        // Importamos las Unidades y Oficinas
        importadorUnidades.importarUnidades(sincronizacion);
        importadorOficinas.importarOficinas(sincronizacion);

    }

}
