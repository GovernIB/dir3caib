package es.caib.dir3caib.back.test;
import au.com.bytecode.opencsv.CSVReader;
/*import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;*/
import es.caib.dir3caib.persistence.model.*;
import org.junit.Test;


import java.io.*;


/**
 * @author mgonzalez
 * @version 1
 * 31/10/2023
 */
public class UnidadTest {

    @Test
    public void doblesComillasSueltas(){
        Unidad unidad = new Unidad();

        String directorio = "C://Users//mgonzalez.TIC//Documents//DIR3CAIB/";
        String fichero = "Unidades.csv";

        File file = new File(directorio, fichero);
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));

            CSVReader reader = new CSVReader(bufferedReader, ';');
            reader.readNext();
            String[] fila;
            while ((fila = reader.readNext()) != null) {
               System.out.println( fila[0].trim()+";"+fila[1].trim()+";"+fila[2].trim()+";"+fila[3].trim()+";"+fila[4].trim()+";"+fila[5].trim()+";"+fila[6].trim()+";"+fila[7].trim()+";"+fila[8].trim()+";"+fila[9].trim());
               System.out.println( fila[10].trim()+";"+fila[11].trim()+";"+fila[12].trim()+";"+fila[13].trim()+";"+fila[14].trim()+";"+fila[15].trim()+";"+fila[16].trim()+";"+fila[17].trim()+";"+fila[18].trim()+";"+fila[19].trim());
               System.out.println( fila[20].trim()+";"+fila[21].trim()+";"+fila[22].trim()+";"+fila[23].trim()+";"+fila[24].trim()+";"+fila[25].trim()+";"+fila[26].trim()+";"+fila[27].trim()+";"+fila[28].trim()+";"+fila[29].trim());
               System.out.println( fila[30].trim()+";"+fila[31].trim()+";"+fila[32].trim()+";"+fila[33].trim()+";"+fila[34].trim()+";"+fila[35].trim()+";"+fila[36].trim()+";"+fila[37].trim()+";"+fila[38].trim()+";"+fila[39].trim());
               System.out.println( fila[40].trim()+";"+fila[41].trim()+";"+fila[42].trim()+";"+fila[43].trim()+";"+fila[44].trim()+";"+fila[45].trim()+";"+fila[46].trim()+";"+fila[47].trim()+";"+fila[48].trim()+";"+fila[49].trim());
               System.out.println( fila[50].trim()+";"+fila[51].trim()+";"+fila[52].trim()+";"+fila[53].trim()+";"+fila[54].trim()+";"+fila[55].trim()+";"+fila[56].trim());
            }


        } catch ( Exception e ) {
            System.out.println("Error obteniendo el fichero (" + fichero + ") " + e.getMessage());

        }


    }


   /* @Test
    public void testUnidades(){
        Unidad unidad = new Unidad();

        String directorio = "C://Users//mgonzalez.TIC//Documents//DIR3CAIB/";
        String fichero = "Unidades.csv";

        File file = new File(directorio, fichero);
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .withIgnoreQuotations(false)
                    .build();

            CSVReader reader = new CSVReaderBuilder(bufferedReader)
                    .withCSVParser(parser)
                    .build();

            reader.readNext();
            String[] fila;
            while ((fila = reader.readNext()) != null) {
                String fila25 = fila[25].trim();
                if(!fila25.isEmpty()){
                    Long fila25L = Long.valueOf(fila25);
                }
                System.out.println( fila[0].trim()+";"+fila[1].trim()+";"+fila[2].trim()+";"+fila[3].trim()+";"+fila[4].trim()+";"+fila[5].trim()+";"+fila[6].trim()+";"+fila[7].trim()+";"+fila[8].trim()+";"+fila[9].trim());
                System.out.println( fila[10].trim()+";"+fila[11].trim()+";"+fila[12].trim()+";"+fila[13].trim()+";"+fila[14].trim()+";"+fila[15].trim()+";"+fila[16].trim()+";"+fila[17].trim()+";"+fila[18].trim()+";"+fila[19].trim());
                System.out.println( fila[20].trim()+";"+fila[21].trim()+";"+fila[22].trim()+";"+fila[23].trim()+";"+fila[24].trim()+";"+fila25 +";"+fila[26].trim()+";"+fila[27].trim()+";"+fila[28].trim()+";"+fila[29].trim());
                System.out.println( fila[30].trim()+";"+fila[31].trim()+";"+fila[32].trim()+";"+fila[33].trim()+";"+fila[34].trim()+";"+fila[35].trim()+";"+fila[36].trim()+";"+fila[37].trim()+";"+fila[38].trim()+";"+fila[39].trim());
                System.out.println( fila[40].trim()+";"+fila[41].trim()+";"+fila[42].trim()+";"+fila[43].trim()+";"+fila[44].trim()+";"+fila[45].trim()+";"+fila[46].trim()+";"+fila[47].trim()+";"+fila[48].trim()+";"+fila[49].trim());
                System.out.println( fila[50].trim()+";"+fila[51].trim()+";"+fila[52].trim()+";"+fila[53].trim()+";"+fila[54].trim()+";"+fila[55].trim()+";"+fila[56].trim());
            }


        } catch ( Exception e ) {

            System.out.println(e.getMessage());

        }


    }*/


    @Test
    public void testOficinas(){
        Unidad unidad = new Unidad();

        String directorio = "C://Users//mgonzalez.TIC//Documents//DIR3CAIB/";
        String fichero = "Oficinas.csv";

        File file = new File(directorio, fichero);
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));

            CSVReader reader = new CSVReader(bufferedReader, ';');

            reader.readNext();
            String[] fila;
            while ((fila = reader.readNext()) != null) {
                System.out.println( fila[0].trim()+";"+fila[1].trim()+";"+fila[2].trim()+";"+fila[3].trim()+";"+fila[4].trim()+";"+fila[5].trim()+";"+fila[6].trim()+";"+fila[7].trim()+";"+fila[8].trim()+";"+fila[9].trim());
                System.out.println( fila[10].trim()+";"+fila[11].trim()+";"+fila[12].trim()+";"+fila[13].trim()+";"+fila[14].trim()+";"+fila[15].trim()+";"+fila[16].trim()+";"+fila[17].trim()+";"+fila[18].trim()+";"+fila[19].trim());
                System.out.println( fila[20].trim()+";"+fila[21].trim()+";"+fila[22].trim()+";"+fila[23].trim()+";"+fila[24].trim()+";"+fila[25].trim()+";"+fila[26].trim()+";"+fila[27].trim()+";"+fila[28].trim()+";"+fila[29].trim());
                System.out.println( fila[30].trim()+";"+fila[31].trim()+";"+fila[32].trim()+";"+fila[33].trim());
            }


        } catch ( Exception e ) {
            System.out.println("Se ha producido un error en el reader");

        }


    }

}
