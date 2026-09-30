package EBS.util.FolioPagoMVP2;

import java.io.File;

public class XMLRenamer {

    /**
     * Renombra los archivos duplicados en la carpeta para que sigan la secuencia de los originales.
     *
     * @param folderPath Ruta de la carpeta donde se encuentran los archivos XML.
     */
    public static void renameXMLFiles() {
    	
    	String folderPath = "C:\\Users\\1000068932\\IDE_eclipse\\Workspaces\\Folio De Pago 2.0\\Project\\Respaldo\\FrameworkEBS\\Files\\folioDePago2QAWeb\\MVP2\\XML\\Prueba Volumetria";
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

        int sequence = 1; // Inicia la secuencia desde el primer archivo original

        for (File xmlFile : xmlFiles) {
            // Verificar si el archivo es un duplicado
            if (xmlFile.getName().startsWith("duplicado_")) {
                // Generar el nuevo nombre basado en la secuencia
                String newFileName = "xml_femsaws" + sequence + ".xml";
                File renamedFile = new File(folderPath, newFileName);

                // Renombrar el archivo
                if (xmlFile.renameTo(renamedFile)) {
                    System.out.println("Archivo renombrado: " + xmlFile.getName() + " -> " + newFileName);
                } else {
                    System.out.println("Error al renombrar: " + xmlFile.getName());
                }

                sequence++; // Incrementar la secuencia
            }
        }

        System.out.println("Renombrado completado.");
    }

    public static void main(String[] args) {
        try {
            // Ruta de la carpeta donde están los XML
            String folderPath = "ruta/a/tu/carpeta";
            renameXMLFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
