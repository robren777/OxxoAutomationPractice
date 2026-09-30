package WM.om;

import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.TestCase;
import utils.ApiMethodsUtil;
import WM.util.GlobalVariables;
import org.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class OrdersInbound {

	static TestCase testCase;
	String baseURLToken = "";
	ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);
	public OrdersInbound (TestCase testCase) {
			this.testCase = testCase;
		}

	//public final String DB_JDBC_POSTGRES_TERRA_OXXO_RSPN = "jdbc:postgresql://172.26.53.100:5432/terra_oxxo_rspn?sslmode=require"
            //+ "&user=sergio.robles"
            //+ "&password=)0[b48N2L1kEr?o"; encriptada: A7F7B22039AB6A532CCDC4D336082FA7
            /*+ "password="+passwordUtil.decryptPass("7219F56A65188FF0A0A2984EBDADFA55");*/
	
	public final String DB_JDBC_POSTGRES_TERRA_OXXO_RSPN = GlobalVariables.DB_JDBC_TERRA_OXXO_RSPN
            + "&user="+GlobalVariables.DB_USER_TERRA_OXXO_RSPN
            + "&password="+GlobalVariables.DB_PASSWORD_TERRA_OXXO_RSPN;
	
	public final String DB_JDBC_TABLEAU_BDAppExt = GlobalVariables.DB_JDBC_TABLEAU_BDAppExt
            + "user="+GlobalVariables.DB_USER_TABLEAU_BDAppExt+";"
            + "password="+GlobalVariables.DB_PASSWORD_TABLEAU_BDAppExt;

	
	/** getAccessToken
	* Obtiene el Token para autenticarse en la API order-response y despatch-advice
	*@autor Sergio Robles Ramos
	*@param
	*	-getPathToken (Cadena): Endpoint complementario para generar token de API
	*	-baseURLTokenTestdata (Cadena): Endpoint base para generar token de API
	*	-Auth (Cadena): cliendId:secretId de API, para la autenticación al generar el token
	*@return access_token (Cadena): Token de API
	*@date 25/Noviembre/2025
	*/	
	public String getAccessToken(String getPathToken, String baseURLTokenTestdata,
            String clientId, String clientSecret) throws Exception {
		try {
	
		String scope = "read"; // scope de tu API	
		URL url = new URL(baseURLTokenTestdata + getPathToken);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();		
		conn.setRequestMethod("POST");		
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");		
		conn.setDoOutput(true);
		
		// === Cuerpo de la solicitud ===		
		String body = "grant_type=client_credentials"		
		 + "&client_id=" + URLEncoder.encode(clientId, "UTF-8")		
		 + "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8")		
		 + "&scope=" + URLEncoder.encode(scope, "UTF-8");
		
		// === Enviar la solicitud ===	
		try (OutputStream os = conn.getOutputStream()) {		
		byte[] input = body.getBytes(StandardCharsets.UTF_8);		
		os.write(input, 0, input.length);		
		}
	
		// === VALIDACIÓN DEL CÓDIGO DE RESPUESTA ===		
		int statusCode = conn.getResponseCode();		
		if (statusCode != 200) {		
		System.out.println("Código HTTP recibido: " + statusCode);		
		return "error al obtener token";		
		}
		
		// === Leer la respuesta solo si es 200 ===		
		BufferedReader br = new BufferedReader(		
		new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)		
		);
		
		StringBuilder response = new StringBuilder();		
		String responseLine;		
		while ((responseLine = br.readLine()) != null) {		
		response.append(responseLine.trim());		
		}
		
		// === Parsear JSON y obtener access_token ===	
		JSONObject json = new JSONObject(response.toString());	
		String accessToken = json.getString("access_token");	
		System.out.println("Access Token: " + accessToken);		
		return accessToken;
		} catch (Exception e) {		
		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());	
		throw new Exception("Error: " + e.getMessage());	
		}	
		}

	/** order_response
	* solicita el servicio orden response con la API, 
	*@autor Sergio Robles Ramos
	*@param
	*	-String baseURL_order_response (Cadena): URL base de la API
	*	-getPath_order_response (Cadena): La ruta de la API
	*	-messageNumber (Cadena): Número del mensaje
	*	-purchaseDate (Cadena): Fecha de la compra
	*	-messageFormat (Cadena): Formato del mensaje
	*	-purchaseOrder (Cadena): Orden de compra
	*	-receiptDeadline (Cadena): Fecha limite del recibo
	*	-requesterSupplier (Cadena): Proveedor solicitado
	*	-receiverStore (Cadena): Tienda que recibirá la orden
	*	-poStatus (Cadena): Estatus de la orden
	*	-access_token (Cadena): Token de acceso
	*	-APIKey (Cadena): Autenticación de usuario para poder usar la API
	*	-details (Cadena): Detalles
	*	-transshipmentCode (Cadena): Código de envío para punto de trasbordo
	*	-transshipmentDescription (Cadena): Descripción del envío a punto de trasbordo
	*	-positionCount (Cadena): Cantidad de articulos que contiene la orden de compra
	*@return codeResp (Cadena): Código de respuesta de la API
	*@date 27/Noviembre/2025
	*/	

	public String orderResponse (String baseURL_order_response, String getPath_order_response, String messageNumber, String purchaseDate, String messageFormat, String purchaseOrder, String receiptDeadline, String positionCount, String requesterSupplier, String receiverStore, String poStatus, String access_token, String APIKey, String details, String transshipmentCode, String transshipmentDescription) throws Exception {
		try {
			String reqBody = "";
			//Llamada al API order_response usando el token generado
			RestAssured.baseURI = baseURL_order_response;
			
			// Variables para de formato de fecha
			DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        DateTimeFormatter formatoHora  = DateTimeFormatter.ofPattern("HH:mm:ss");
	 
	        // Variables con fecha y hora actual
	        String fechaHoy = LocalDate.now().format(formatoFecha);
	        String horaHoy  = LocalTime.now().format(formatoHora);
	        if (messageFormat.equals("W") && !transshipmentCode.equals("") && !transshipmentDescription.equals("")) {
	        	reqBody = "{\r\n"
						+ "    \"header\": {\r\n"
						+ "        \"transactionType\": \"ORDRSP\",\r\n"
						+ "        \"versionNumber\": \"D96A\",\r\n"
						+ "        \"emissionDate\": \""+fechaHoy+"\",\r\n"
						+ "        \"emissionTime\": \""+horaHoy+"\",\r\n"
						+ "        \"messageNumber\": "+messageNumber+",\r\n"
						+ "        \"purchaseDate\": \""+purchaseDate+"\",\r\n"
						+ "        \"messageFormat\": \""+messageFormat+"\",\r\n"
						+ "        \"purchaseOrder\": "+purchaseOrder+",\r\n"
						+ "        \"receiptDeadline\": \""+receiptDeadline+"\",\r\n"
						+ "        \"positionCount\": "+positionCount+",\r\n"
						+ "        \"requesterSupplier\": "+requesterSupplier+",\r\n"
						+ "        \"receiverStore\": "+receiverStore+",\r\n"
						+ "        \"transshipmentCode\": "+transshipmentCode+",\r\n"
						+ "        \"transshipmentDescription\": \""+transshipmentDescription+"\",\r\n"
						+ "        \"poStatus\": \""+poStatus+"\"\r\n"
						+ "    },"+details;
	        }else {
			reqBody = "{\r\n"
					+ "    \"header\": {\r\n"
					+ "        \"transactionType\": \"ORDRSP\",\r\n"
					+ "        \"versionNumber\": \"D96A\",\r\n"
					+ "        \"emissionDate\": \""+fechaHoy+"\",\r\n"
					+ "        \"emissionTime\": \""+horaHoy+"\",\r\n"
					+ "        \"messageNumber\": "+messageNumber+",\r\n"
					+ "        \"purchaseDate\": \""+purchaseDate+"\",\r\n"
					+ "        \"messageFormat\": \""+messageFormat+"\",\r\n"
					+ "        \"purchaseOrder\": "+purchaseOrder+",\r\n"
					+ "        \"receiptDeadline\": \""+receiptDeadline+"\",\r\n"
					+ "        \"positionCount\": "+positionCount+",\r\n"
					+ "        \"requesterSupplier\": "+requesterSupplier+",\r\n"
					+ "        \"receiverStore\": "+receiverStore+",\r\n"
					+ "        \"poStatus\": \""+poStatus+"\"\r\n"
					+ "    },"+details;
	        }
			System.out.println(reqBody);
	        
			//Lista de headers
			HashMap<String, String> headersList = new HashMap<>();
			headersList.put("Tracking-Id", "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66");
			headersList.put("Channel-Id", "TERRA");
			headersList.put("Country-Code", "MX");
			headersList.put("Language", "SPA");
			headersList.put("User-Agent", "terraApp/1.0 ordrsp");
			headersList.put("x-Gateway-APIKey",APIKey);
			headersList.put("Content-Type", "application/json");
			headersList.put("Accept", "*/*");
			headersList.put("Authorization", "Bearer "+access_token);
			
			//Hacemos el send del request
			Response getRespuesta2 = api.postRequestWithHeaders(baseURL_order_response+getPath_order_response, reqBody, headersList);
			System.out.print(getRespuesta2);	

			//Obtener body de respuesta
			String getBodyResponse2 = getRespuesta2.getBody().asPrettyString();	
			testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURL_order_response+getPath_order_response+reqBody);
			System.out.println("Body: "+getBodyResponse2);

			//Validacion de JSON
			JsonPath getJson2 = new JsonPath(getBodyResponse2);
			testCase.addTextEvidenceCurrentStep("Response: \n"+ getRespuesta2+getBodyResponse2);

			//VALIDACION DE CODE
			String codeResp = getJson2.getString("code"); 
			return codeResp;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());

		}

	}

	/** queryBDPostgresql
	* Consulta a BD en PostgreSQL, especificar si la consulta debe regresar vacio o no
	*@autor Sergio Robles
	*@param
	*	-queryTable (Cadena): La query que se va a ejecutar
	*	-DB_JDBC_AZURE (Cadena): Jdbc de BD
	*	-vacia (Cadena): Cuando es "No", verifica que no esté vacío el resultado de la query,
	*			cuando es "Si" verifica que esté vacío el resultado de la query.
	*@return resultadoValido (boolean): true or false
	*@date 26/Noviembre/2025
	*/
	//Realiza la conexion a la BD de Postgres y ejecuta la query y tambien en un parametro se especifica si la consulta debe devolver valores o no.
	
	public boolean queryBDPostgresql (String queryTable, String DB_JDBC_POSTGRES, String vacia) throws Exception {
	    boolean resultadoValido;
	    boolean validaStatus;
	 
	    try {
	        // Cargar el driver de PostgreSQL
	        Class.forName("org.postgresql.Driver");
	 
	        // Conectar a PostgreSQL usando JDBC completo (usuario y pass dentro del URL)
	        Connection Conexion = DriverManager.getConnection(DB_JDBC_POSTGRES);
	        Statement stmt = Conexion.createStatement();
	 
	        // Imprimir query en consola
	        System.out.println("Ejecutando query: " + queryTable);
	 
	        // Ejecutar la query principal
	        ResultSet queryTable_T = stmt.executeQuery(queryTable);
	 
	        // Validación de si la query debe estar vacía o no
	        if ("No".equals(vacia)) {
	            validaStatus = queryNoVacia(queryTable_T); // true si NO cumple
	            resultadoValido = !validaStatus;
	 
	            if (!resultadoValido) {
	                testCase.addTextEvidenceCurrentStep("Fallo: La query está vacía y no debería.");
	                throw new Exception("Fallo: La query está vacía y no debería.");
	            }
	        } else {
	            validaStatus = queryVacia(queryTable_T); // true si NO cumple
	            resultadoValido = !validaStatus;
	 
	            if (!resultadoValido) {
	                testCase.addTextEvidenceCurrentStep("Fallo: La query NO está vacía y debería estarlo.");
	                throw new Exception("Fallo: La query NO está vacía y debería estarlo.");
	            }
	        }
	 
	        // Ejecutar nuevamente la query para recolectar resultados
	        ResultSet queryTable2 = stmt.executeQuery(queryTable);
	        int columnCount = queryTable2.getMetaData().getColumnCount();
	        List<String> queryResultado = new ArrayList<>();
	 
	        // Obtener encabezados
	        StringBuilder headers = new StringBuilder();
	        for (int i = 1; i <= columnCount; i++) {
	            headers.append(queryTable2.getMetaData().getColumnName(i));
	            if (i < columnCount) {
	                headers.append(", ");
	            }
	        }
	        queryResultado.add(headers.toString());
	 
	        // Obtener filas de resultados
	        while (queryTable2.next()) {
	            StringBuilder row = new StringBuilder();
	            for (int i = 1; i <= columnCount; i++) {
	                row.append(queryTable2.getString(i));
	                if (i < columnCount) {
	                    row.append(", ");
	                }
	            }
	            queryResultado.add(row.toString());
	        }
	 
	        // Unir resultados en texto plano
	        String resultadoDigital = String.join("\n", queryResultado);
	 
	        // Agregar evidencia si corresponde
	        if (!validaStatus) {
	            testCase.addTextEvidenceCurrentStep(
	                "Resultado del Query en texto plano (primera fila encabezados, segunda fila resultado)"
	            );
	            testCase.addTextEvidenceCurrentStep(resultadoDigital);
	        }	 
	        // Cerrar recursos
	        queryTable2.close();
	        queryTable_T.close();
	        stmt.close();
	        Conexion.close();
	 
	        return resultadoValido;
	 
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage(), e);
	    }
	}	
	
	/** queryNoVacia
	* Verificar que la query de BD PostgreSQL NO regrese vacio
	*@autor Sergio Robles
	*@param
	*	-queryTable (Cadena): La query que se va a ejecutar
	*@return vacia (booleano): true si la cadena está vacía, false si la cadena NO está vacía
	*@date 26/Noviembre/2025
	*/
	
	//Metodo que utiliza queryBDAzure para validar que no este vacia la respuesta obtenido del select ejecutado.
	public boolean queryNoVacia(ResultSet queryTable_T) throws Exception {
		try {
			boolean vacia = true; // Asumimos que está vacía por defecto
			// Suponiendo que tienes un ResultSet llamado 'queryTable_T' que contiene los resultados de una consulta SQL
			if (queryTable_T.next()) {
			    vacia = false; // Si hay al menos un resultado, no está vacía
			}
			return vacia;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
 
	/** queryVacia
	* Verificar que la query de BD Azure SI regrese vacio
	*@autor Sergio Robles
	*@param
	*	-queryTable (Cadena): La query que se va a ejecutar
	*@return vacia (booleano): true si la cadena NO está vacía, false si la cadena está vacía
	*@date 26/Noviembre/2025
	*/
	//Metodo que utuliza queryBDAzure para validar que si este vacia la respuesta obtenido del select ejecutado.	
	public boolean queryVacia(ResultSet queryTable_T) throws Exception {
		try {
			boolean vacia = false; // Asumimos que está vacía por defecto 
			// Suponiendo que tienes un ResultSet llamado 'queryTable_T' que contiene los resultados de una consulta SQL
			if (queryTable_T.next()) {
			    vacia = true; // Si hay al menos un resultado, no está vacía
			}
			return vacia;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}

	}
	
	
	/** queryBDSQLServer
	* Consulta a BD en SQLServer, especificar si la consulta debe regresar vacio o no
	*@autor Sergio Robles Ramos
	*@param
	*	-queryTable (Cadena): La query que se va a ejecutar
	*	-DB_JDBC_SQLSERVER (Cadena): Jdbc de BD
	*	-vacia (Cadena): Cuando es "No", verifica que no esté vacío el resultado de la query,
	*			cuando es "Si" verifica que esté vacío el resultado de la query.
	*@return resultadoValido (boolean): true or false
	*@date 09/Diciembre/2025
	*/
	
	public boolean queryBDSQLServer(String queryTable, String DB_JDBC_SQLSERVER, String vacia) throws Exception {
	    boolean resultadoValido;
	    boolean validaStatus;
	 
	    try {
	        // Cargar el driver de PostgreSQL
	       // Class.forName("org.postgresql.Driver");
	 
	        // Conectar a PostgreSQL usando JDBC completo (usuario y pass dentro del URL)
	        Connection Conexion = DriverManager.getConnection(DB_JDBC_SQLSERVER);
	        Statement stmt = Conexion.createStatement();
	 
	        // Imprimir query en consola
	        System.out.println("Ejecutando query: " + queryTable);
	 
	        // Ejecutar la query principal
	        ResultSet queryTable_T = stmt.executeQuery(queryTable);
	 
	        // Validación de si la query debe estar vacía o no
	        if ("No".equals(vacia)) {
	            validaStatus = queryNoVacia(queryTable_T); // true si NO cumple
	            resultadoValido = !validaStatus;
	 
	            if (!resultadoValido) {
	                testCase.addTextEvidenceCurrentStep("Fallo: La query está vacía y no debería.");
	                throw new Exception("Fallo: La query está vacía y no debería.");
	            }
	        } else {
	            validaStatus = queryVacia(queryTable_T); // true si NO cumple
	            resultadoValido = !validaStatus;
	 
	            if (!resultadoValido) {
	                testCase.addTextEvidenceCurrentStep("Fallo: La query NO está vacía y debería estarlo.");
	                throw new Exception("Fallo: La query NO está vacía y debería estarlo.");
	            }
	        }
	 
	        // Ejecutar nuevamente la query para recolectar resultados
	        ResultSet queryTable2 = stmt.executeQuery(queryTable);
	        int columnCount = queryTable2.getMetaData().getColumnCount();
	        List<String> queryResultado = new ArrayList<>();
	 
	        // Obtener encabezados
	        StringBuilder headers = new StringBuilder();
	        for (int i = 1; i <= columnCount; i++) {
	            headers.append(queryTable2.getMetaData().getColumnName(i));
	            if (i < columnCount) {
	                headers.append(", ");
	            }
	        }
	        queryResultado.add(headers.toString());
	 
	        // Obtener filas de resultados
	        while (queryTable2.next()) {
	            StringBuilder row = new StringBuilder();
	            for (int i = 1; i <= columnCount; i++) {
	                row.append(queryTable2.getString(i));
	                if (i < columnCount) {
	                    row.append(", ");
	                }
	            }
	            queryResultado.add(row.toString());
	        }
	 
	        // Unir resultados en texto plano
	        String resultadoDigital = String.join("\n", queryResultado);
	 
	        // Agregar evidencia si corresponde
	        if (!validaStatus) {
	            testCase.addTextEvidenceCurrentStep(
	                "Resultado del Query en texto plano (primera fila encabezados, segunda fila resultado)"
	            );
	            testCase.addTextEvidenceCurrentStep(resultadoDigital);
	        }
	 
	        // Cerrar recursos
	        queryTable2.close();
	        queryTable_T.close();
	        stmt.close();
	        Conexion.close();
	 
	        return resultadoValido;
	 
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage(), e);
	    }
	}
	
	/** DespatchAdvice
	 * solicita el servicio DespatchAdvice con la API,
	 *@autor Sergio Robles Ramos
	 *@param
	 *  -String baseURL_despatch_advice (Cadena): URL base de la API
	 *  -String getPath_despatch_advice (Cadena): Ruta del servicio DespatchAdvice
	 *  -String messageNumber (Cadena): Número del mensaje
	 *  -String purchaseOrder (Cadena): Orden de compra
	 *  -String deliveryNumber (Cadena): Número de entrega
	 *  -String messageFormat (Cadena): Formato del mensaje
	 *  -String appointmentNumber (Cadena): Número de cita para la entrega
	 *  -String region (Cadena): Región asociada a la entrega
	 *  -String hub (Cadena): Centro logístico o HUB
	 *  -String routeNumber (Cadena): Número de ruta de distribución
	 *  -String deliveryDate (Cadena): Identificador UUID
	 *  -String bill (Cadena): Número de factura o referencia de facturación
	 *  -String access_token (Cadena): Token de acceso para autenticación
	 *  -String APIKey (Cadena): Clave de API para consumo del servicio
	 *  -String details (Cadena): Detalle de los productos incluidos en el envío
	 *
	 *@return codeResp (Cadena): Código de respuesta de la API
	 *@date 17/Diciembre/2025
	 */
	
	public String despatchAdvice (String baseURL_despatch_advice, String getPath_despatch_advice, String messageNumber, String purchaseOrder, String deliveryNumber, String messageFormat, String appointmentNumber, String region, String hub, String routeNumber, String deliveryDate, String uuid, String bill, String access_token, String APIKey, String details ) throws Exception {
		try {
			String reqBody = "";
			//Llamada al API order_response usando el token generado
			RestAssured.baseURI = baseURL_despatch_advice;
			
			// Variables para de formato de fecha
			DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        DateTimeFormatter formatoHora  = DateTimeFormatter.ofPattern("HH:mm:ss");
	 
	        // Variables con fecha y hora actual
	        String fechaHoy = LocalDate.now().format(formatoFecha);
	        String horaHoy  = LocalTime.now().format(formatoHora);
	        
			reqBody = "{\r\n"
					+ "    \"header\": {\r\n"
					+ "        \"transactionType\": \"DESADV\",\r\n"
					+ "        \"versionNumber\": \"D96A\",\r\n"
					+ "        \"messageNumber\": "+messageNumber+",\r\n"
					+ "        \"transmissionDate\": \""+fechaHoy+"\",\r\n"
					+ "        \"transmissionTime\": \""+horaHoy+"\",\r\n"
					+ "        \"purchaseOrder\": "+purchaseOrder+",\r\n"
					+ "        \"deliveryNumber\": "+deliveryNumber+",\r\n"
					+ "        \"messageFormat\": \""+messageFormat+"\",\r\n"
					+ "        \"appointmentNumber\": "+appointmentNumber+",\r\n"
					+ "        \"region\": \""+region+"\",\r\n"
					+ "        \"hub\": \""+hub+"\",\r\n"
					+ "        \"routeNumber\": \""+routeNumber+"\",\r\n"	
					+ "        \"deliveryDate\": \""+deliveryDate+"\",\r\n"	
					+ "        \"uuid\": \""+uuid+"\",\r\n"
					+ "        \"bill\": \""+bill+"\"\r\n"
					+ "    },"+details;
			System.out.println(reqBody);
	        
			//Lista de headers
			HashMap<String, String> headersList = new HashMap<>();
			headersList.put("Tracking-Id", "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66");
			headersList.put("Channel-Id", "TERRA");
			headersList.put("Country-Code", "MX");
			headersList.put("Language", "SPA");
			headersList.put("User-Agent", "terraApp/1.0 desadv");
			headersList.put("x-Gateway-APIKey",APIKey);
			headersList.put("Content-Type", "application/json");
			headersList.put("Accept", "*/*");
			headersList.put("Authorization", "Bearer "+access_token);
			
			//Hacemos el send del request
			Response getRespuesta2 = api.postRequestWithHeaders(baseURL_despatch_advice+getPath_despatch_advice, reqBody, headersList);
			System.out.print(getRespuesta2);	

			//Obtener body de respuesta
			String getBodyResponse2 = getRespuesta2.getBody().asPrettyString();	
			testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURL_despatch_advice+getPath_despatch_advice+reqBody);
			System.out.println("Body: "+getBodyResponse2);

			//Validacion de JSON
			JsonPath getJson2 = new JsonPath(getBodyResponse2);
			testCase.addTextEvidenceCurrentStep("Response: \n"+ getRespuesta2+getBodyResponse2);

			//VALIDACION DE CODE
			String codeResp = getJson2.getString("code"); 
			return codeResp;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());

		}

	}

	
}
