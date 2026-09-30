package Mobile.POSMovil.om;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import modelo.BaseExecution;
import modelo.TestCase;
import Mobile.util.GlobalVariables;

public class Remediaciones {

	protected static String token;
	protected ObjectMapper objectMapper;

	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private BaseExecution baseExecution;

	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Remediaciones
	 * 
	 * @author Marcos Morales
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public Remediaciones(TestCase testCase, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.baseExecution = baseExecution;
	}

	/**
	 * Método - Obtiene el token a traves de una peticion POST para realizar el
	 * cambio de permisos
	 * 
	 * @author Marcos Morales
	 * @param usuario  Usuario de la aplicacion
	 * @param password Contraseña del usuario
	 * @param crTienda CR de Tienda
	 * @return Retorna el token a traves de una peticion POST para realizar el cambio de permisos
	 * @date 16/12/2025
	 */
	public String obtieneToken(String usuario, String password, String crTienda) {
		String token = "";

		// Construir el cuerpo de la petición
		String requestBody = String.format("{\"User\": \"%s\", \"Password\": \"%s\"}", usuario, password);

		try {
			// Realizar la petición POST
			Response response = RestAssured.given().baseUri("https://svcs-dev.xposcloud.com.mx").header("CR", crTienda) // Header
					// parametrizado
					.contentType(ContentType.JSON).body(requestBody).when()
					.post("/sales/qa/api/v1/Authentication/LoginUser");

			// Validar la respuesta
			response.then().statusCode(200).body("token", notNullValue());

			// Extraer el token
			token = response.jsonPath().getString("token");
			System.out.println("Token: " + token);
		} catch (Exception e) {
			System.err.println("Error en la petición: " + e.getMessage());
			throw e;
		}
		return token;
	}

	/**
	 * Método - Deshabilita o Habilita Permisos a traves de una peticion POST
	 * 
	 * @author Marcos Morales
	 * @param usuario    Usuario de la aplicacion
	 * @param password   Contraseña del usuario
	 * @param crTienda   CR de Tienda
	 * @param categoryId ID de la Catagoria
	 * @param category   Cadena de texto con el nombre de la categoria
	 * @param menuId     ID del menu
	 * @param menu       Cadena de texto con el nombre del menu
	 * @param bitActivo  1 o 0 en funcion de Habilitar/Deshabilitar
	 * @date 16/12/2025
	 */
	public void deshabilitarHabilitarPermiso(String usuario, String password, String crTienda, int categoryId,
			String category, int menuId, String menu, int bitActivo) {

		String authToken = obtieneToken(usuario, password, crTienda);

		// Validar el token
		if (authToken == null || authToken.isEmpty()) {
			throw new RuntimeException("No se obtuvo el token de autenticación");
		}

		// Construir el cuerpo de la petición
		String requestBody = String.format(
				"{\"permissions\": [{" + "\"categoryId\": %d, " + "\"category\": \"%s\", " + "\"menuId\": %d, "
						+ "\"menu\": \"%s\", " + "\"cr\": \"%s\", " + "\"bit_Activo\": %d" + "}]}",
						categoryId, category, menuId, menu, crTienda, bitActivo);

		try {
			// Realizar la petición POST con el token Bearer
			Response response = RestAssured.given().baseUri("https://svcs-qa.xposcloud.com.mx")
					.header("Authorization", "Bearer " + authToken) // Token Bearer
					.header("CR", crTienda) // Header parametrizado
					.contentType(ContentType.JSON).body(requestBody).when()
					.post("/posmovil/qa/api/v1/Configuration/SavePermissions");

			// Imprimir toda la respuesta en consola
			System.out.println("Response Body:\n" + response.getBody().asPrettyString());

			// Validación de la respuesta
			response.then().statusCode(200);
		} catch (Exception e) {
			System.err.println("Error en la petición: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Ejecuta la consulta en la base de datos de la Nube para obtener el estatus de un permiso
	 * 
	 * @author Marcos Morales
	 * @param crStore CR de la Tienda
	 * @param menuId Id del Servicio
	 * @date 16/12/2025
	 */
	public ResultSet obtieneResultSetPermiso(String crStore, int menuId) 
			throws SQLException {

		Connection connection = DriverManager.getConnection(
				GlobalVariables.urlOxxoCoudQA,
				GlobalVariables.userQANubeP, 
				GlobalVariables.passwordQANubeP
				);

		try {
			String sql = "select	 c.CATEGORY_ID\r\n"
					+ "		,c.CATEGORY\r\n"
					+ "		,m.MENU_ID\r\n"
					+ "		,m.MENU\r\n"
					+ "		,mcr.CR\r\n"
					+ "		,mcr.BIT_ACTIVO\r\n"
					+ "from XposCloudPOSMovilQA.dbo.CATEGORY c\r\n"
					+ "inner join XposCloudPOSMovilQA.dbo.MENU m on c.CATEGORY_ID = m.CATEGORY_ID\r\n"
					+ "inner join XposCloudPOSMovilQA.dbo.MENU_CR mcr on m.MENU_ID = mcr.MENU_ID\r\n"
					+ "where mcr.CR = ?\r\n"
					+ "and m.MENU_ID = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, crStore);
			statement.setInt(2, menuId);

			return statement.executeQuery();

		} catch (Exception e) {
			connection.close();
			throw e;
		}
	}

	/**
	 * Identifica si un permiso esta Activo o Inactivo en la base de datos de la Nube
	 * 
	 * @author Marcos Morales
	 * @param crStore CR de la Tienda
	 * @param menuId Id del Servicio
	 * @date 16/12/2025
	 */
	public int obtienePermisoActivoInactivo(String crStore, int menuId) throws SQLException {
		int bitActivo = 0;
		ResultSet rs = obtieneResultSetPermiso(crStore, menuId);
		if (rs.next()) {
			bitActivo = rs.getInt("BIT_ACTIVO");
		} else {
			throw new RuntimeException(
					"No se encontraron registros al ejecutar la consulta del metodo: obtieneResultSetPermiso");
		}
		return bitActivo;
	}

	/**
	 * Método - Habilita un Permiso a traves de una peticion POST, si este se encuentra Inactivo
	 * 
	 * @author Marcos Morales
	 * @param usuario    Usuario de la aplicacion
	 * @param password   Contraseña del usuario
	 * @param crTienda   CR de Tienda
	 * @param categoryId ID de la Catagoria
	 * @param category   Cadena de texto con el nombre de la categoria
	 * @param menuId     ID del menu
	 * @param menu       Cadena de texto con el nombre del menu
	 * @param bitActivo  1 o 0 en funcion de Habilitar/Deshabilitar
	 * @date 16/12/2025
	 */
	public void habilitarPermiso(String usuario, String password, String crTienda, int categoryId,
			String category, int menuId, String menu, int bitActivo) throws SQLException {
		int estatus = obtienePermisoActivoInactivo(crTienda, menuId);
		if (estatus == 0) {
			deshabilitarHabilitarPermiso(usuario, password, crTienda, categoryId,
					category, menuId, menu, bitActivo);
		} else {
			System.out.println("Permiso se encuentra ACTIVO");
		}
	}

	/**
	 * Genera la peticion POST para obtener y guardar el token
	 * 
	 * @author Marcos Morales
	 * @param base_url URL base de ambiente QA
	 * @param cr_tienda CR de la Tienda
	 * @param endpoint Endpoint de ambiente QA
	 * @date 17/12/2025
	 */
	private String obtenerYGuardarToken(String base_url, String cr_tienda, String endpoint) {
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("User", "NoUsuario");
		requestBody.put("Password", "2");

		try {
			// Realizar la petición POST
			Response response = RestAssured.given()
					.baseUri(base_url)
					.header("CR", cr_tienda)
					.contentType(ContentType.JSON)
					.body(requestBody)
					.when()
					.post(endpoint);

			// Validar la respuesta
			response.then().statusCode(200).body("token", notNullValue());

			// Extraer el token
			String newToken = response.jsonPath().getString("token");

			TokenManager.saveToken(newToken);
			return newToken;
		} catch (Exception e) {
			System.err.println("Error en la petición: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Valida si existe un token valido guardado
	 * 
	 * @author Marcos Morales
	 * @param base_url URL base de ambiente QA
	 * @param cr_tienda CR de la Tienda
	 * @param endpoint Endpoint de ambiente QA
	 * @date 17/12/2025
	 */
	public void authenticate(String base_url, String cr_tienda, String endpoint) {
		if (TokenManager.isTokenValid()) {
			token = TokenManager.getToken();
			System.out.println("✓ Usando token guardado");
		} else {
			token = obtenerYGuardarToken(base_url, cr_tienda, endpoint);
			System.out.println("✓ Token nuevo obtenido y guardado");
		}
	}

	/**
	 * Calcula el importe del monto de productos en la generacion del JSON a utilizar en el request
	 * 
	 * @author Marcos Morales
	 * @param precio Precio del producto (Valor a evaluar)
	 * @param cantidad Cantidad de productos (Valor a evaluar)
	 * @param precioOK Precio CORRECTO del producto
	 * @date 17/12/2025
	 */
	private static Object calcularMonto(Object precio, Object cantidad, Object precioOK) {
		// Si ambos son números Y cantidad es mayor a cero, calcular automáticamente
		if (precioOK instanceof Number && cantidad instanceof Number) {
			double precioNum = ((Number) precioOK).doubleValue();
			double cantidadNum = ((Number) cantidad).doubleValue();

			// Validar que cantidad sea positiva para cálculo válido
			if (cantidadNum > 0) {
				return precioNum * cantidadNum;
			} else {
				System.out.println("Cantidad no positiva (" + cantidadNum + "), usando monto por defecto");
				return precioOK;
			}
		}
		System.out.println("Valores no numéricos, usando monto por defecto: " + precioOK);
		return precioOK;
	}

	/**
	 * Crea un objeto con la estructura del JSON para realizar el request
	 * 
	 * @author Marcos Morales
	 * @param sku SKU del Producto
	 * @param descripcion Descripcion del producto
	 * @param precio Precio del producto (Valor a evaluar)
	 * @param cantidad Cantidad de productos (Valor a evaluar)
	 * @param precioOK Precio CORRECTO del producto
	 * @date 17/12/2025
	 */
	public static Map<String, Object> crearRequestBody(String sku, String descripcion, 
			Object precio, Object cantidad, Object precioOK, String cr_tienda) {
		Map<String, Object> item = new HashMap<>();
		item.put("description", descripcion);
		item.put("entryCode", "92");
		item.put("priceSold", precio);
		item.put("quantity", cantidad);
		item.put("sku", sku);

		// Calcular monto automáticamente (solo si ambos son numéricos)
		Object monto = calcularMonto(precio, cantidad, precioOK);
		String montoTotal = String.valueOf(monto);

		Map<String, Object> payment = new HashMap<>();
		payment.put("amount", monto);
		payment.put("payedAmount", monto);
		payment.put("paymentMethod", "Cash");
		payment.put("returnedAmount", 0.0);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("Items", Arrays.asList(item));
		requestBody.put("PaymentsInfo", Arrays.asList(payment));
		requestBody.put("totalAmount", montoTotal);
		requestBody.put("trxType", "Sale");
		requestBody.put("CR", cr_tienda);
		
		return requestBody;
	}

	/**
	 * Crea un objeto con la estructura del JSON para realizar el request
	 * 
	 * @author Marcos Morales
	 * @param sku SKU del Producto
	 * @param descripcion Descripcion del producto
	 * @param precio Precio del producto (Valor a evaluar)
	 * @param cantidad Cantidad de productos (Valor a evaluar)
	 * @param precioOK Precio CORRECTO del producto
	 * @param monto Monto de la operacion (Valor a evaluar)
	 * @param pago Pago de la operacion (Valor a evaluar)
	 * @param campo amount, payedAmount, amountpayedAmount, totalAmount
	 * @date 17/12/2025
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> crearRequestMontoIncorrecto(String sku
			,String descripcion
			,Number precio
			,Number cantidad
			,Number precioOK
			,Object monto
			,Object pago
			,String campo
			,String cr_tienda
			) {

		Map<String, Object> requestBody = crearRequestBody(sku, descripcion, precio, cantidad, precioOK,cr_tienda);

		Object paymentsObj = requestBody.get("PaymentsInfo");

		if (!(paymentsObj instanceof List)) {
			throw new IllegalStateException("El campo 'PaymentsInfo' no contiene una lista válida.");
		}

		List<Map<String, Object>> payments = (List<Map<String, Object>>) paymentsObj;

		if (payments.isEmpty()) {
			throw new IllegalStateException("La lista 'PaymentsInfo' está vacía.");
		}

		Map<String, Object> payment = payments.get(0);

		switch (campo) {
		case "amount":
			payment.put("amount", monto);
			break;

		case "payedAmount":
			payment.put("payedAmount", pago);
			break;

		case "amountpayedAmount":
			payment.put("amount", monto);
			payment.put("payedAmount", pago);
			break;

		case "totalAmount":
			requestBody.put("totalAmount", String.valueOf(monto));
			break;

		case "totalAmountNull":
			requestBody.put("totalAmount", monto);
			break;

		default:
			throw new IllegalArgumentException("Campo no reconocido: " + campo);
		}

		return requestBody;
	}

	/**
	 * Genera la evidencia del REQUEST
	 * 
	 * @author Marcos Morales
	 * @param base_url URL base de ambiente QA
	 * @param authToken Token de la peticion
	 * @param cr_tienda CR de la Tienda
	 * @param usuario Usuario de la peticion
	 * @param endpoint Endpoint de ambiente QA
	 * @param requestBody Objeto RequestBody
	 * @date 17/12/2025
	 */
	public String buildRequestInfo(String base_url, String authToken, String cr_tienda, String usuario, String endpoint, Map<String, Object> requestBody) {
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(requestBody);

			return "=== REQUEST ===\n" +
			"Method: POST\n" +
			"URL: " + base_url + endpoint + "\n" +
			"Headers:\n" +
			"  Authorization: Bearer " + authToken + "\n" +
			"  CR: " + cr_tienda + "\n" +
			"  username: " + usuario + "\n" +
			"  Content-Type: application/json\n" +
			"Body:\n" + prettyJson + "\n" +
			"===============";

		} catch (Exception e) {
			return "=== REQUEST ===\n" +
					"Error formateando JSON: " + e.getMessage() + "\n" +
					"Body (raw): " + requestBody.toString() + "\n" +
					"===============";
		}
	}

	/**
	 * Genera la evidencia del RESPONSE
	 * 
	 * @author Marcos Morales
	 * @param response Objeto Response
	 * @param startTime Tiempo de inicio de la peticion
	 * @date 17/12/2025
	 */
	public String buildResponseInfo(Response response, long startTime) {
		long responseTime = System.currentTimeMillis() - startTime;

		return "=== RESPONSE ===\n" 
		+ "Status: " + response.getStatusCode() + "\n" 
		+ "Response Time: " + responseTime + "ms\n"
		+ "Headers:\n"
		+ "  Content-Type: " + response.getContentType() + "\n" 
		+ "Body:\n"
		+ response.getBody().asPrettyString() + "\n"
		+ "================";
	}

	/**
	 * Genera la evidencia del REQUEST
	 * 
	 * @author Marcos Morales
	 * @param base_url URL base de ambiente QA
	 * @param authToken Token de la peticion
	 * @param cr_tienda CR de la Tienda
	 * @param usuario Usuario de la peticion
	 * @param endpoint Endpoint de ambiente QA
	 * @param requestBody Objeto RequestBody
	 * @date 17/12/2025
	 */
	private void captureRequestEvidence(String base_url, String authToken, String cr_tienda, String usuario, String endpoint,
			Map<String, Object> requestBody) {

		// Construir información para la evidencia
		String requestInfo = buildRequestInfo(base_url, authToken, cr_tienda, usuario, endpoint, requestBody);

		// Agregar la evidencia al test
		testCase.addCodeEvidenceCurrentStep(requestInfo);
	}

	/**
	 * Ejecuta un REQUEST de tipo POST
	 * 
	 * @author Marcos Morales
	 * @param base_url URL base de ambiente QA
	 * @param authToken Token de la peticion
	 * @param cr_tienda CR de la Tienda
	 * @param usuario Usuario de la peticion
	 * @param endpoint Endpoint de ambiente QA
	 * @param requestBody Objeto RequestBody
	 * @date 17/12/2025
	 */
	public Response ejecutarRequest(String base_url, String authToken, String cr_tienda, String usuario, String endpoint,
			Map<String, Object> requestBody) {

		try {
			// Armar la información del request
			RequestSpecification requestSpec = given()
					.baseUri(base_url)
					.header("Authorization", "Bearer " + authToken)
					.header("username", usuario)
					.contentType("application/json")
					.body(requestBody);

			// Ejecutar request
			Response response = requestSpec.when().post(endpoint);

			// Capturar evidencia para el PDF
			captureRequestEvidence(base_url, authToken, cr_tienda, usuario, endpoint, requestBody);

			return response;

		} catch (Exception e) {
			System.err.println("Error en la petición: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Valida si existe un Item guardado, en caso contrario obtiene uno nuevo SKU aleatorio
	 * 
	 * @author Marcos Morales
	 * @param ipPV IP de punto de venta local
	 * @throws SQLException 
	 * @date 22/12/2025
	 */
	public void obtieneSKUAleatorio(String ipPV) throws SQLException {
		if (PM.isItemValid()) {
			System.out.println("✓ Usando item guardado");
		} else {
			PM.saveItemProperties(ipPV);
			System.out.println("✓ Item nuevo obtenido y guardado");
		}
	}

	/**
	 * Método - Crea Request para body quantity = VALOR NO NUMERICO
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 23/12/2025
	 */
	public static Map<String, Object> crearRequestQuantityNoNumerico(
			String sku,
			String descripcion,
			Object precio,
			Object quantityNoNumerica,
			Object precioOK
			) {

		Map<String, Object> item = new HashMap<>();
		item.put("description", descripcion);
		item.put("entryCode", "92");
		item.put("priceSold", precio);
		item.put("quantity", quantityNoNumerica); // Valor NO numérico
		item.put("sku", sku);

		Object monto = precioOK;

		Map<String, Object> payment = new HashMap<>();
		payment.put("amount", monto);
		payment.put("payedAmount", monto);
		payment.put("paymentMethod", "Cash");
		payment.put("returnedAmount", 0.0);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("Items", Arrays.asList(item));
		requestBody.put("PaymentsInfo", Arrays.asList(payment));
		requestBody.put("totalAmount", String.valueOf(monto));
		requestBody.put("trxType", "Sale");

		return requestBody;
	}

	/**
	 *  Métdod - Valida que los campos priceSold y quantity sean iguales a 0 en el response
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 23/12/2025
	 */
	public void validarPriceSoldYQuantityEnCero(Response response) {

		Integer quantity = response.jsonPath().getInt("Items[0].quantity");
		Number priceSold = response.jsonPath().get("Items[0].priceSold");

		// Validar quantity = 0
		if (quantity == null || quantity != 0) {
			throw new AssertionError(
					"quantity debería ser 0, pero fue: " + quantity
					);
		}

		// Validar priceSold = 0
		if (priceSold == null || priceSold.doubleValue() != 0.0) {
			throw new AssertionError(
					"priceSold debería ser 0, pero fue: " + priceSold
					);
		}

		System.out.println("priceSold y quantity validados correctamente en 0");
	}

	/**
	 * Método - Valida que priceSold sea 0 y quantity sea NEGATIVA
	 *
	 * @author Cesar Guel
	 * @param response Response del endpoint
	 * @return true si cumple la validación, false si no
	 * @date 29/12/2025
	 */
	public boolean validarPriceSoldEnCeroYQuantityNegativa(Response response) {

		try {
			Integer quantity = response.jsonPath().getInt("Items[0].quantity");
			Number priceSold = response.jsonPath().get("Items[0].priceSold");

			// Validar quantity NEGATIVA
			if (quantity == null || quantity >= 0) {
				System.out.println("quantity inválida: " + quantity);
				return false;
			}

			// Validar priceSold = 0
			if (priceSold == null || priceSold.doubleValue() != 0.0) {
				System.out.println("priceSold inválido: " + priceSold);
				return false;
			}

			System.out.println("priceSold = 0 y quantity NEGATIVA validados correctamente");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Valida que priceSold sea NEGATIVO y quantity sea 0
	 *
	 * @author Cesar Guel
	 * @param response Response del endpoint
	 * @return true si cumple la validación
	 * @date 29/12/2025
	 */
	public boolean validarPriceSoldNegativoYQuantityEnCero(Response response) {

		try {
			Integer quantity = response.jsonPath().getInt("Items[0].quantity");
			Number priceSold = response.jsonPath().get("Items[0].priceSold");

			// Validar quantity = 0
			if (quantity == null || quantity != 0) {
				System.out.println("quantity inválida: " + quantity);
				return false;
			}

			// Validar priceSold NEGATIVO
			if (priceSold == null || priceSold.doubleValue() >= 0) {
				System.out.println("priceSold inválido: " + priceSold);
				return false;
			}

			System.out.println("priceSold NEGATIVO y quantity = 0 validados correctamente");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Método - Valida que PaymentsInfo.amount sea igual a 0
	 *
	 * @author Cesar Guel
	 * @param response Response del endpoint
	 * @return true si amount = 0
	 * @date 05/01/2026
	 */
	public boolean validarAmountEnCero(Response response) {

	    try {
	        Number amount = response.jsonPath().get("PaymentsInfo[0].amount");

	        if (amount == null) {
	            System.out.println("amount viene NULL");
	            return false;
	        }

	        if (amount.doubleValue() != 0.0) {
	            System.out.println("amount inválido: " + amount);
	            return false;
	        }

	        System.out.println("amount validado correctamente en 0");
	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * Método - Valida que el campo PaymentsInfo.amount sea NEGATIVO
	 *
	 * @author Cesar Guel
	 * @param response Response del endpoint
	 * @return true si amount es negativo, false si no
	 * @date 06/01/2026
	 */
	public boolean validarAmountNegativo(Response response) {

	    try {
	        Number amount = response.jsonPath()
	                .get("PaymentsInfo[0].amount");

	        if (amount == null || amount.doubleValue() >= 0) {
	            System.out.println("amount inválido: " + amount);
	            return false;
	        }

	        System.out.println("amount NEGATIVO validado correctamente: " + amount);
	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}