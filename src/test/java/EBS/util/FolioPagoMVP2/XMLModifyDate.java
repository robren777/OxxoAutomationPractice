package EBS.util.FolioPagoMVP2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLModifyDate {

    /**
     * Modifica la fecha en los archivos XML de una carpeta.
     *
     * @param folderPath Ruta de la carpeta donde se encuentran los XML.
     * @param newDate    Nueva fecha que se desea establecer (formato: yyyy-MM-ddTHH:mm:ss).
     * @throws IOException Si ocurre un error de lectura/escritura.
     */
    public static void modifyXMLDates(String folderPath, String newDate) throws IOException {
        File folder = new File(folderPath);

        // Verificar si la carpeta existe
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("La ruta proporcionada no es una carpeta v�lida.");
        }

        // Obtener la lista de archivos XML en la carpeta
        File[] xmlFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        if (xmlFiles == null || xmlFiles.length == 0) {
            throw new IllegalArgumentException("No se encontraron archivos XML en la carpeta.");
        }

        // Expresi�n regular para encontrar la fecha en el formato deseado
        String dateRegex = "\\bFecha=\"(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})\"";
        Pattern datePattern = Pattern.compile(dateRegex);

        for (File xmlFile : xmlFiles) {
            // Leer el contenido del archivo
            String content = Files.readString(xmlFile.toPath());

            // Buscar y reemplazar la fecha
            Matcher matcher = datePattern.matcher(content);
            String modifiedContent = matcher.replaceAll("Fecha=\"" + newDate + "\"");

            // Sobrescribir el archivo con el contenido modificado
            Files.writeString(xmlFile.toPath(), modifiedContent, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Fecha modificada en el archivo: " + xmlFile.getName());
        }

        System.out.println("Modificaci�n de fechas completada.");
    }

    public static void main(String[] args) {
        try {
            // Ruta de la carpeta donde est�n los XML
        	String folderPath = "C:\\Users\\1000068932\\IDE_eclipse\\Workspaces\\Folio De Pago 2.0 Jar Nuevo\\Project\\FrameworkEBS\\Files\\folioDePago2QAWeb\\MVP2\\XML\\Prueba Volumetria";

            // Nueva fecha que deseas establecer
            String newDate = "2025-05-15T03:49:38";

            // Modificar las fechas en los XML
            modifyXMLDates(folderPath, newDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
