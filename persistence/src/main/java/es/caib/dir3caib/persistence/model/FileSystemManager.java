package es.caib.dir3caib.persistence.model;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 2/10/13
 */
public class FileSystemManager {

    protected final Logger log = Logger.getLogger(getClass());

    /**
     * Obtiene la ruta donde se almacenarán los archivos
     * @return
     */
    public static File getArchivosPath(String path) {
        return new File(System.getProperty(path));
    }
    
    /**
     * Obtiene el fichero existente en el sistema de archivos
     * @return
     */
    public static File getArchivo(String path, String nombre) {
        File newFile = new File(getArchivosPath(path), String.valueOf(nombre));
        return newFile;
    }
    
    /**
     * Copia un input en un ouput
     * @param input
     * @param output
     * @throws IOException
     */
    public static void copy(InputStream input, OutputStream output) throws IOException {
        
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }
}
