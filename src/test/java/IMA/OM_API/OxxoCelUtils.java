package IMA.OM_API;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.UUID;

import modelo.TestCase;


public class OxxoCelUtils {

	protected static TestCase testCase;

	public OxxoCelUtils(TestCase testCase) {
	    OxxoCelUtils.testCase = testCase;
	}

	
	private static final String DATE_TIME_PATTERN = "yyyyMMddHHmmss";
	
	/**
	 * Método para generar un TransactionID unico
	 * 
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @return Retorna el transactionId generado
	 * @date 28/04/2025
	 */
	public static String generarTransactionId() {
		String prefijo = "10NLA503JS01";
		String sufijo = "-0008-OXXOCELTEST";
		// timestamp tipo yyyyMMddHHmmss
		String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN ));
		// número random extra
		int random = 1000 + java.util.concurrent.ThreadLocalRandom.current().nextInt(9000);
		return prefijo + fecha + random + sufijo;
	}
	
	/**
	 * Método para formatear fecha-hora
	 * 
	 * @author Jazmin Jimenez Torres
	 * @throws Exception
	 * @return Retorna string de fecha
	 * @date 15/05/2026
	 * @param formato [1-3] tipo de formato a devolver
	 */
	public static String formatFechaHora(Integer formato) throws Exception {
		try {
			String valorFormateado = "";
			String G_SendDateTime = "";
			if (G_SendDateTime.trim().equals("")) {
				G_SendDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
			}
			String fecha = G_SendDateTime;
			// Definir formato de entrada
			DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN );
			// Parsear la cadena a LocalDateTime
			LocalDateTime dateTimeN = LocalDateTime.parse(fecha, formatoEntrada);
			// Restar 1 hora por diferencia en zona horaria
			LocalDateTime dateTimeMenosUnaHora = dateTimeN.minusHours(1);
			// Volver a formatear al mismo patrón
			String fechafinal = dateTimeMenosUnaHora.format(formatoEntrada);
			LocalDateTime dateTimeF = LocalDateTime.parse(fechafinal, formatoEntrada);
			// Formatos de salida
			DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
			DateTimeFormatter formatoCreatedAt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			// Variables con los tres formatos
			if (formato == 1) {
				valorFormateado = dateTimeF.format(formatoFecha);
			}
			if (formato == 2) {
				valorFormateado = dateTimeF.format(formatoHora);
			}
			if (formato == 3) {
				valorFormateado = dateTimeF.format(formatoCreatedAt);
			}
			return valorFormateado;
		} catch (DateTimeParseException  e) {
			  String error = String.format(
				        "Error en formatFechaHora -> %s: %s | Formato: %s",
				        e.getClass().getSimpleName(),
				        e.getMessage(),
				        formato
				    );
			  testCase.addCodeEvidenceCurrentStep(error);
			  throw new RuntimeException(error, e);
		}
	}
	
	/**
	 * Método para generar un TransactionID 409I
	 * @param N/A
	 * @author Mario Garza
	 * @return Retorna el transactionId generado
	 * @date 28/04/2025
	 */
	public static String generarTransactionId409I() {
		String prefijo = "409";
		String sufijo = "I";
		// número random extra
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
		return prefijo + sufijo + random;
	}

	/**
	 * Método para generar un TransactionID 503 unico
	 * 
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @return Retorna el transactionId generado
	 * @date 18/05/2025
	 */
	public static String generarTransactionId500() {
		String prefijo = "503";
		String sufijo = "T";
		// número random extra
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
		return prefijo + sufijo + random;
	}
	
	/**
	 * Método para generar un TransactionID 201
	 * @param N/A
	 * @author Mario Garza
	 * @return Retorna el transactionId generado
	 * @date 28/04/2025
	 */
	public static String generarTransactionId201() {
		String prefijo = "201";
		String sufijo = "A";
		// número random extra
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
		return prefijo + sufijo + random;
	}
	
	/**
	 * Método para generar un TransactionID 401
	 * @param N/A
	 * @author Mario Garza
	 * @return Retorna el transactionId generado
	 * @date 28/04/2025
	 */
	public static String generarTransactionId401() {
		String prefijo = "401";
		String sufijo = "U";
		// número random extra
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
		return prefijo + sufijo + random;
	}
	
	/**
	 * Método para generar un TransactionID 404
	 * @param 
	 * @author Mario Garza
	 * @return Retorna el transactionId generado
	 * @date 28/04/2025
	 */
	public static String generarTransactionId404() {
		String prefijo = "404";
		String sufijo = "A";
		// número random extra
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
		return prefijo + sufijo + random;
	}
	
	/**
	 * Método para generar un TransactionID 409
	 * @param N/A
	 * @author Mario Garza
	 * @return Retorna el transactionId generado
	 * @date 28/04/2025
	 */
	public static String generarTransactionId409() {
		String prefijo = "409";
		String sufijo = "A";
		// número random extra
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
		return prefijo + sufijo + random;
	}
	
	/**
	 * Método para generar un TransactionID 201I
	 * @param N/A
	 * @author Mario Garza
	 * @return Retorna el transactionId generado
	 * @date 28/04/2025
	 */
	public static String generarTransactionId201I() {
		String prefijo = "201";
		String sufijo = "I";
		// número random extra
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
		return prefijo + sufijo + random;
	}
	
	/**
	 * Método para calcular diferencia de tiempos
	 * 
	 * @author Jazmin Jimenez Torres
	 * @throws Exception
	 * @return Retorna string de fecha
	 * @date 15/05/2026
	 * @param fecha1, fecha2
	 */
	public static String calculaTiempoTotal(String fecha1, String fecha2) {

	    try {

	        DateTimeFormatter formatoEntrada =
	                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        fecha1 = fecha1.substring(0, 19);
	        fecha2 = fecha2.substring(0, 19);

	        LocalDateTime fechaInicial = LocalDateTime.parse(fecha1, formatoEntrada);
	        LocalDateTime fechaFinal = LocalDateTime.parse(fecha2, formatoEntrada);

	        Duration duration = Duration.between(fechaInicial, fechaFinal);

	        long minutos = duration.toMinutes();
	        long segundos = duration.getSeconds() % 60;

	        return minutos + "min " + segundos + "sec";

	    } catch (DateTimeParseException | StringIndexOutOfBoundsException e) {

	        String error = String.format(
	            "Error en calculo TiempoTotal -> %s: %s | Fecha1: %s | Fecha2: %s",
	            e.getClass().getSimpleName(),
	            e.getMessage(),
	            fecha1,
	            fecha2
	        );

	        testCase.addCodeEvidenceCurrentStep(error);
	        throw new RuntimeException(error, e);
	    }
	}
	
	// Enum dentro de la clase
	public enum CodeBDEstatus {
		I("INIT"), P("PROCESSING"), S("SUCCESS"), F("FAILED"), R("RETRY"), T("COMPLETED"), A("ACCEPTED"), J("REJECTED"),
		C("CANCELLED"), E("ERROR"), D("DUPLICATED"), U("UNAUTHORIZED"), B("BREAKER");

		private final String descripcion;

		CodeBDEstatus(String descripcion) {
			this.descripcion = descripcion;
		}
	
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método para buscar la descripcion del status
	 * 
	 * @author Jazmin Jimenez Torres
	 * @throws Exception
	 * @return string con la descripcion
	 * @date 15/05/2026
	 * @param la letra que identifica al estatus en la BD
	 * 
	 */
	public static String buscaDescripEstatus(String status) throws Exception {
		try {
			return CodeBDEstatus.valueOf(status).getDescripcion();
		} catch (IllegalArgumentException e) {
			return "Código no encontrado";
		}
	}
	}
	
}
