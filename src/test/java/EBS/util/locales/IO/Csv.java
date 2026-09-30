package EBS.util.locales.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Csv {

	public static boolean validateData(String filePath, String columnValue, int columnNumber) {
		boolean result= false;
        //String filePath = "ruta/del/archivo.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            //int lineNumber = 0;
            line = br.readLine();
            String[] values = line.split(",");
            
            System.out.println("CONTENIDO DE LA LINEA");
            System.out.println(values[columnNumber].toString()); 
            
            if (values[columnNumber].trim().matches(columnValue)) {
                result=true;
            }
        } 
        catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return result;
    }

}
