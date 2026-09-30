package EBS.util.FolioPagoMVP2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConstruccionCSV {

	
	public boolean generarArchivoCsv(String rfc, String nombreProveedor, String serie, String folio,
			String folio_pago, String importe, String importeOxxo, String fecha, String uuid, String tipoDeComprobante,
			String uuidRelacionado, String path) {
		// Construir el nombre del archivo CSV
		String nombreArchivo = "CFD" + folio_pago + ".csv";

		// Crear la ruta completa del archivo
		Path rutaArchivo = Paths.get(path, nombreArchivo);

		try {
			// Si el archivo ya existe, se elimina para reemplazarlo
			if (Files.exists(rutaArchivo)) {
				Files.delete(rutaArchivo);
			}

			// Crear el archivo y escribir los datos
			try (FileWriter writer = new FileWriter(rutaArchivo.toFile())) {
				// Escribir encabezados
				writer.append(
						"RFC,PROVEEDOR,SERIE,FOLIO,FOLIO_PAGO_OXXO,IMPORTE,IMPORTE_OXXO,FECHA_FACTURA,VALC_UUID,TIPO_COMPROBANTE,UUIDRELACIONADO\n");

				// Escribir datos
				writer.append(rfc).append(",").append(nombreProveedor).append(",").append(serie).append(",")
						.append(folio).append(",").append(folio_pago).append(",").append(importe).append(",")
						.append(importeOxxo).append(",").append(fecha).append(",").append(uuid).append(",")
						.append(tipoDeComprobante).append(",").append(uuidRelacionado).append("\n"); // UUIDRELACIONADO
																										// puede estar
																										// vac�o

				System.out.println("Archivo CSV creado exitosamente: " + rutaArchivo.toString());
				return true;
			}
		} catch (IOException e) {
			System.err.println("Error al crear el archivo CSV: " + e.getMessage());
			return false;
		}
	}

	public String incrementar(String folio_pago) {
		// Imprime el folio que se va a modificar
		System.out.println("Folio a testear: " + folio_pago);

		// Encuentra la �ltima parte num �rica del String
		int lastUnderscoreIndex = folio_pago.lastIndexOf('_');
		if (lastUnderscoreIndex == -1) {
			throw new IllegalArgumentException("El formato del String no es v �lido.");
		}

		// Extrae la parte num �rica
		String prefix = folio_pago.substring(0, lastUnderscoreIndex + 1);
		String numberPart = folio_pago.substring(lastUnderscoreIndex + 1);

		try {
			// Convierte la parte num �rica a entero y la incrementa
			int number = Integer.parseInt(numberPart);
			number++;
			String nuevoFolio = prefix + number;

			// Imprime el folio modificado
			System.out.println("Folio modificado: " + nuevoFolio);
			return nuevoFolio;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("La parte final del String no es un n �mero v �lido.");
		}
	}

	public boolean generarArchivoCsvVariasLineas(String rfc, String nombreProveedor, String serie, String folio,
			String folio_pago, String importe, String importeOxxo, String fecha, String uuid, String tipoDeComprobante,
			String uuidRelacionado, String path) {
		// Construir el nombre del archivo CSV
		String nombreArchivo = "CFD" + folio_pago + ".csv";

		// Crear la ruta completa del archivo
		Path rutaArchivo = Paths.get(path, nombreArchivo);

		try {
			// Crear el archivo si no existe y escribir encabezados
			if (!Files.exists(rutaArchivo)) {
				try (FileWriter writer = new FileWriter(rutaArchivo.toFile())) {
					// Escribir encabezados
					writer.append(
							"RFC,PROVEEDOR,SERIE,FOLIO,FOLIO_PAGO_OXXO,IMPORTE,IMPORTE_OXXO,FECHA_FACTURA,VALC_UUID,TIPO_COMPROBANTE,UUIDRELACIONADO\n");
				}
			}

			// Agregar datos al archivo existente
			try (FileWriter writer = new FileWriter(rutaArchivo.toFile(), true)) { // Modo "append"
				// Escribir datos
				writer.append(rfc).append(",").append(nombreProveedor).append(",").append(serie).append(",")
						.append(folio).append(",").append(folio_pago).append(",").append(importe).append(",")
						.append(importeOxxo).append(",").append(fecha).append(",").append(uuid).append(",")
						.append(tipoDeComprobante).append(",").append(uuidRelacionado).append("\n"); // UUIDRELACIONADO
																										// puede estar
																										// vacï¿½o

				System.out.println("Datos agregados exitosamente al archivo CSV: " + rutaArchivo.toString());
				return true;
			}
		} catch (IOException e) {
			System.err.println("Error al agregar datos al archivo CSV: " + e.getMessage());
			return false;
		}
	}

	public boolean generarArchivoCsvVariasLineasConSaltosDeLineaAlFinalDelCSV(String rfc, String nombreProveedor, String serie, String folio, 
	        String folio_pago, String importe, String importeOxxo, String fecha, String uuid, String tipoDeComprobante, 
	        String uuidRelacionado, String path) { 
	    // Construir el nombre del archivo CSV 
	    String nombreArchivo = "CFD" + folio_pago + ".csv"; 

	    // Crear la ruta completa del archivo 
	    Path rutaArchivo = Paths.get(path, nombreArchivo); 

	    try { 
	        // Crear el archivo si no existe y escribir encabezados 
	        if (!Files.exists(rutaArchivo)) { 
	            try (FileWriter writer = new FileWriter(rutaArchivo.toFile())) { 
	                // Escribir encabezados 
	                writer.append(
	                    "RFC,PROVEEDOR,SERIE,FOLIO,FOLIO_PAGO_OXXO,IMPORTE,IMPORTE_OXXO,FECHA_FACTURA,VALC_UUID,TIPO_COMPROBANTE,UUIDRELACIONADO\n"
	                ); 
	            } 
	        } 

	        // Agregar datos al archivo existente 
	        try (FileWriter writer = new FileWriter(rutaArchivo.toFile(), true)) { // Modo "append" 
	            // Escribir datos 
	            writer.append(rfc).append(",").append(nombreProveedor).append(",").append(serie).append(",") 
	                .append(folio).append(",").append(folio_pago).append(",").append(importe).append(",") 
	                .append(importeOxxo).append(",").append(fecha).append(",").append(uuid).append(",") 
	                .append(tipoDeComprobante).append(",").append(uuidRelacionado).append("\n"); 

	            // Agregar varios saltos de lÃ­nea al final del archivo
	            writer.append("\n\n\n"); // AquÃ­ se agregan tres saltos de lÃ­nea al final del archivo
	        } 

	        System.out.println("Datos agregados exitosamente al archivo CSV: " + rutaArchivo.toString()); 
	        return true; 
	    } catch (IOException e) { 
	        System.err.println("Error al agregar datos al archivo CSV: " + e.getMessage()); 
	        return false; 
	    } 
	}

	public void limpiarFinalCsvComoString(String nombreArchivo, String pathCSV) {
	    // Obtener la ruta del proyecto actual
	    String currentProject = System.getProperty("user.dir");

	    // Construir la ruta completa del archivo
	    String srcFilePath = currentProject + File.separator + pathCSV + File.separator + nombreArchivo;

	    try {
	        // Crear la ruta completa del archivo
	        Path rutaArchivo = Paths.get(srcFilePath);

	        // Leer el contenido del archivo como un Ãºnico String
	        String contenido = Files.readString(rutaArchivo);

	        // Encontrar la Ãºltima coma en el contenido
	        int ultimaComaIndex = contenido.lastIndexOf(",");

	        if (ultimaComaIndex != -1) {
	            // Limpiar cualquier contenido despuÃ©s de la Ãºltima coma
	            contenido = contenido.substring(0, ultimaComaIndex + 1);
	        }

	        // Escribir el contenido actualizado de vuelta al archivo
	        Files.writeString(rutaArchivo, contenido);

	        System.out.println("El archivo CSV se ha limpiado correctamente.");
	    } catch (IOException e) {
	        System.err.println("Error al limpiar el archivo CSV: " + e.getMessage());
	    }
	}

}
