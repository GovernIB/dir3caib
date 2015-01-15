
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.caib.dir3caib.back.utils;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Fundació Bit
 * @author earrivi
 * Date: 04-nov-2013
 */
public class Mensaje {
    
    public static void saveMessageInfo(HttpServletRequest request, String mensaje) {
        HttpSession session = request.getSession();

        List<String> mensajes = (List<String>) session.getAttribute("infos");

        if(mensajes == null){
            mensajes = new ArrayList<String>();
            mensajes.add(mensaje);
        }else{
            mensajes.add(mensaje);
        }
        session.setAttribute("infos", mensajes);
    }

    public static void saveMessageError(HttpServletRequest request, String mensaje) {
        HttpSession session = request.getSession();
        
        List<String> mensajes = (List<String>) session.getAttribute("errors");

        if(mensajes == null){
            mensajes = new ArrayList<String>();
            mensajes.add(mensaje);
        }else{
            mensajes.add(mensaje);
        }
        session.setAttribute("errors", mensajes);
        

    }

}
