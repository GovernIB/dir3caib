package es.caib.dir3caib.back.controller.descarga;

import es.caib.dir3caib.back.controller.BaseController;
import es.caib.dir3caib.back.utils.Mensaje;
import es.caib.dir3caib.persistence.ejb.DescargaLocal;
import es.caib.dir3caib.persistence.model.Descarga;
import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author mgonzalez
 * Date: 28/09/16
 */

@Controller
@RequestMapping(value = "/descarga")
public class DescargaController extends BaseController {

    @EJB(mappedName = "dir3caib/DescargaEJB/local")
    protected DescargaLocal descargaEjb;

    protected final Logger log = Logger.getLogger(getClass());


    // Indicamos el formato de fecha dd/MM/yyyy hh:mm:ss
    SimpleDateFormat formatoFecha = new SimpleDateFormat(Dir3caibConstantes.FORMATO_FECHA);


    /**
     * Eliminar un {@link es.caib.dir3caib.persistence.model.Descarga}
     */
    @RequestMapping(value = "/{idDescarga}/delete")
    public String eliminarDescarga(@PathVariable Long idDescarga, HttpServletRequest request) {

        try {

            Descarga descarga = descargaEjb.findById(idDescarga);
            descargaEjb.remove(descarga);

            Mensaje.saveMessageInfo(request, getMessage("dir3caib.eliminar.registro"));

        } catch (Exception e) {
            Mensaje.saveMessageError(request, getMessage("dir3caib.relaciones.registro"));
            e.printStackTrace();
        }

        return "redirect:/inicio";
    }

}
