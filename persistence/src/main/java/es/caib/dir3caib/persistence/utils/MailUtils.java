package es.caib.dir3caib.persistence.utils;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.utils.Configuracio;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Date;

/**
 * Created by mgonzalez on 09/12/2019
 */
public class MailUtils {

    /**
     * Envia un email a un Usuario
     *
     * @param asunto
     *          Asunto del Mensaje
     * @param mensajeTexto
     *          Contenido del cerpo del correo a enviar
     * @param addressFrom
     *          Indica el remitente del mensaje
     * @param type
     *          Indica el con que de que tipo es el destinatario, Copia, Copia Oculta, etc
     * @param mailPara
     *          Email al que va dirigido el mensaje

     * @throws Exception
     */
    public static void enviaMail(String asunto, String mensajeTexto, InternetAddress addressFrom, Message.RecipientType type, String mailPara) throws Exception {

        Context ctx = new InitialContext();

        Session session = (javax.mail.Session) ctx.lookup("java:/es.caib.dir3caib.mail");

        // Creamos el mensaje
        MimeMessage msg = new MimeMessage(session);

        // Indicamos el remitente
        msg.setFrom(addressFrom);

        // Indicamos el destinatario
        InternetAddress addressTo = new InternetAddress(mailPara);
        msg.setRecipient(type, addressTo);

        // Configuramos el asunto
        msg.setSubject(asunto, "UTF-8");
        msg.setSentDate(new Date());


        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(mensajeTexto, "UTF-8");


        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);


        //Añade el contenido al correo
        msg.setContent(mp);

        // Mandamos el mail
        Transport.send(msg);

    }

    public static void envioEmailErrorSincronizacion() throws Exception{

        InternetAddress addressFrom = new InternetAddress(Configuracio.getRemitenteEmail(), Configuracio.getRemitenteNombre());
        enviaMail(Dir3caibConstantes.ASUNTO_MAIL, Dir3caibConstantes.CUERPO_MAIL, addressFrom, Message.RecipientType.TO, Configuracio.getAdministradorEmail());

    }


}
