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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
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

    public static void envioEmailErrorSincronizacion(String tipoSincronizacion, Throwable th) throws Exception{

        String asunto = Dir3caibConstantes.ASUNTO_MAIL + tipoSincronizacion + Dir3caibConstantes.ENTORN +  InetAddress.getLocalHost().getHostName();

        InternetAddress addressFrom = new InternetAddress(Dir3caibConstantes.APLICACION_EMAIL, Dir3caibConstantes.APLICACION_NOMBRE);

        StringBuilder cuerpoMail = new StringBuilder();

        cuerpoMail.append(Dir3caibConstantes.CUERPO_MAIL);


        if(th!=null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            th.printStackTrace(pw);
            String exception = sw.getBuffer().toString();
            cuerpoMail.append(System.getProperty("line.separator"));
            cuerpoMail.append(System.getProperty("line.separator"));
            cuerpoMail.append("Exception: ").append(exception).append(System.getProperty("line.separator"));
        }

        enviaMail(asunto, cuerpoMail.toString(), addressFrom, Message.RecipientType.TO, Configuracio.getAdministradorEmail());

    }


}
