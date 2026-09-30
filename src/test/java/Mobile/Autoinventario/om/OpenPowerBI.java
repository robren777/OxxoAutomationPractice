package Mobile.Autoinventario.om;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import modelo.BaseExecution;
import modelo.TestCase;


public class OpenPowerBI {
	/*
	/* Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Conexion
	 * @author Joel Rodriguez
	 * @param au (AppiumUtil): objeto de la clase
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	/**
	 * Metodo
	 ****************************************************************************************/
	/**
	 * Conexion
	 * @return 
	 *
	 */
	
    public static void abrirPowerBi() {
        // Ruta del ejecutable de Power BI Desktop
        String powerBIPath = "C:\\Program Files\\Microsoft Power BI Desktop\\bin\\PBIDesktop.exe";
        // Ruta del archivo .pbix que deseas abrir
        String pbixFilePath = "C:\\Users\\GTIM\\Downloads\\SXPREINVENTARIO_META.pbix";
        
        try {
            // Ejecutar Power BI Desktop con el archivo .pbix
            Process process = Runtime.getRuntime().exec(new String[]{pbixFilePath});
            System.out.println("Power BI Desktop se está abriendo con el archivo especificado...");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo abrir Power BI Desktop con el archivo especificado.");
        }
    }
}
