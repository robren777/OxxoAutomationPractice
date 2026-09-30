package EBS.util.FolioPagoMVP2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class XMLDuplicator {

    /**
     * Copia y pega archivos XML en una carpeta hasta alcanzar un total de 5000 archivos.
     *
     * @param folderPath Ruta de la carpeta donde se encuentran los XML originales.
     * @throws IOException Si ocurre un error de lectura/escritura.
     */
    public static void duplicateXMLFiles(String folderPath) throws IOException {
        File folder = new File(folderPath);

        // Verificar si la carpeta existe
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("La ruta proporcionada no es una carpeta válida.");
        }

        // Obtener la lista de archivos XML en la carpeta
        File[] xmlFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        if (xmlFiles == null || xmlFiles.length == 0) {
            throw new IllegalArgumentException("No se encontraron archivos XML en la carpeta.");
        }

        int currentCount = xmlFiles.length;

        // Copiar archivos hasta alcanzar un total de 5000
        while (currentCount < 5000) {
            for (File xmlFile : xmlFiles) {
                if (currentCount >= 5000) {
                    break;
                }

                // Generar un nuevo nombre para el archivo duplicado
                String newFileName = "duplicado_" + currentCount + "_" + xmlFile.getName();
                File newFile = new File(folderPath, newFileName);

                // Copiar el archivo
                Files.copy(xmlFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                currentCount++;
            }
        }

        System.out.println("Duplicación completada. Total de archivos: " + currentCount);
    }

    public static void main(String[] args) {
        try {
            // Ruta de la carpeta donde están los XML originales
            String folderPath = "C:\\Users\\1000068932\\IDE_eclipse\\Workspaces\\Folio De Pago 2.0\\Project\\Respaldo\\FrameworkEBS\\Files\\folioDePago2QAWeb\\MVP2\\XML\\Prueba Volumetria";
            duplicateXMLFiles(folderPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
