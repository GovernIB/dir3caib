package es.caib.dir3caib.back.controller.rest.swagger;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

/**
 * @author jagarcia
 */

@OpenAPIDefinition(
        info = @Info(
        		title = "API REST de Dir3Caib",
        		description = "",
        		version = "1.0.0",
        		license = @License(
        				name = "License Apache 2.0",
        				url = "http://www.apache.org/licenses/LICENSE-2.0"),
        		contact = @Contact(
        				name = "Departament de Govern Digital a la Fundació BIT",
        				email = "governdigital@fundaciobit.org",
        				url = "http://otae.fundaciobit.org")
        		),
        servers = { @Server(url = "/dir3caib") }
)
@ApplicationPath("/rest")
public class JAXRSConfiguration  {

    private static final Logger LOG = LoggerFactory.getLogger(JAXRSConfiguration.class);

    public JAXRSConfiguration() {
    }

    @PostConstruct
    private void init() {
        LOG.info("Iniciant API REST DIR3CAIB");
    }
}