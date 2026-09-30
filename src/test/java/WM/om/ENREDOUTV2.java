package WM.om;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONObject;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.monitor.query.LogsQueryClient;
import com.azure.monitor.query.LogsQueryClientBuilder;
import com.azure.monitor.query.models.LogsQueryResult;
import com.azure.monitor.query.models.LogsTableRow;
import com.azure.monitor.query.models.QueryTimeInterval;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import modelo.TestCase;
import reactor.core.publisher.Mono;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;

public class ENREDOUTV2 {
	static TestCase testCase;
	Process process = null;
	String baseURLToken = "";
	ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);
    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("^[a-z]+([A-Z][a-z]*)*$");
	
	public void setBaseURL(String baseURLTokenTestdata){
		baseURLToken = baseURLTokenTestdata;
	}
	
	public ENREDOUTV2(TestCase testCase) {
		this.testCase = testCase;
	}
	
	boolean validaStatus=false;
	
	/** processQueryInChunks
	* Segmentar el resultado de la query y ejecutar las consultas segmentadas, para que en la evidencia quedé legible
	*@autor Valeria Fernandez
	*@param
	*	-db (utils.sql.SQLUtil): La base de datos donde se va a ejecutar la query
	*	-query_plano (Cadena): La query que se va a ejecutar
	*@return Vacio
	*@date 06/Junio/2025
	*/
	public void processQueryInChunks(utils.sql.SQLUtil db, String query_plano) throws Exception {
        // Ejecutamos la consulta original para obtener las columnas
        SQLResult result = db.executeQuery(query_plano);
        int chunkSize = 4;

        List<String> allColumns = result.getColumnNames();

        // Dividimos los campos en grupos de 4
        List<List<String>> chunks = splitList(allColumns, chunkSize);

        // Extraemos la cláusula FROM de la consulta original
        String fromClause = extractFromClause(query_plano);
        testCase.addTextEvidenceCurrentStep("Misma consulta pero segmentada");
        
        for (List<String> chunk : chunks) {
            String chunkQuery = "SELECT " + String.join(", ", chunk) + " " + fromClause;
            System.out.println("Ejecutando: " + chunkQuery);
            SQLResult result2 = db.executeQuery(chunkQuery);
            testCase.addQueryEvidenceCurrentStep(result2);
        }
    }

	/** splitList
	* Divide una lista de strings en sublistas más pequeñas de un tamaño determinado, en este caso 4
	*@autor Valeria Fernandez
	*@param
	*	-list (List<String>): La lista de campos del resultado de la query
	*	-size (Entero): El tamaño del segmente, en este caso es 4, ejecuta la query cada 4 campos
	*@return parts (List<List<String>>): true si la cadena NO está vacía, false si la cadena está vacía
	*@date 06/Junio/2025
	*/
    private static List<List<String>> splitList(List<String> list, int size) {
        List<List<String>> parts = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            parts.add(new ArrayList<>(list.subList(i, Math.min(i + size, list.size()))));
        }
        return parts;
    }

    /** extractFromClause
	* Modifica la query para que solo quede todo lo que está después de FROM
	*@autor Valeria Fernandez
	*@param
	*	-query (Cadena): La query que se va a ejecutar
	*@return query.substring(fromIndex) (Cadena): La query modificada, todo lo que está después de FROM
	*@date 06/Junio/2025
	*/
    private static String extractFromClause(String query) {
        String lowerQuery = query.toLowerCase();
        int fromIndex = lowerQuery.indexOf(" from ");
        if (fromIndex == -1) {
            throw new IllegalArgumentException("La consulta no contiene cláusula FROM.");
        }
        return query.substring(fromIndex);  // Incluye "from" hasta el final
    }
	
	/** queryBDAzure
	* Consulta a BD en Azure, especificar si la consulta debe regresar vacio o no
	*@autor Valeria Fernandez
	*@param
	*	-queryTable (Cadena): La query que se va a ejecutar
	*	-DB_JDBC_AZURE (Cadena): Jdbc de BD
	*	-vacia (Cadena): Cuando es "No", verifica que no esté vacío el resultado de la query,
	*			cuando es "Si" verifica que esté vacío el resultado de la query.
	*@return resultadoValido (boolean): true or false
	*@date 27/Febrero/2025
	*/
    public boolean queryBDAzure(String queryTable, String DB_JDBC_AZURE, String vacia) throws Exception {
        boolean resultadoValido;

        try {
            Connection Conexion = DriverManager.getConnection(DB_JDBC_AZURE);
            Statement stmt = Conexion.createStatement();

            System.out.println(queryTable);
            ResultSet queryTable_T = stmt.executeQuery(queryTable);

            if (vacia.equals("No")) {
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

            ResultSet queryTable2 = stmt.executeQuery(queryTable);
            int columnCount = queryTable2.getMetaData().getColumnCount();
            List<String> queryResultado = new ArrayList<>();

            StringBuilder headers = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                headers.append(queryTable2.getMetaData().getColumnName(i));
                if (i < columnCount) {
                    headers.append(", ");
                }
            }

            queryResultado.add(headers.toString());

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

            String resultadoDigital = String.join("\n", queryResultado);

            if (!validaStatus) { // Si no está vacía, agrega evidencia
                testCase.addTextEvidenceCurrentStep("Resultado del Query en texto plano (En la primera fila están los encabezados y en la segunda el resultado)");
                testCase.addTextEvidenceCurrentStep(resultadoDigital);
            }

            return resultadoValido;

        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error: " + e.getMessage());
        }
    }

	
	/** getValueCampoQuery
	* Obtiene valor de campo en BD Azure, especificar nombre del campo
	*@autor Valeria Fernandez
	*@param
	*	-queryTable (Cadena): La query que se va a ejecutar
	*	-DB_JDBC_AZURE (Cadena): Jdbc de BD
	*	-nombreCampo (Cadena): El nombre del campo que se busca obtener su valor
	*@return ValorCampo (Cadena): El valor de nombreCampo
	*@date 27/Febrero/2025
	*/
	public String getValueCampoQuery(String queryTable, String DB_JDBC_AZURE, String nombreCampo) throws Exception {
		try {
			Connection Conexion = DriverManager.getConnection(DB_JDBC_AZURE);
			Statement stmt = Conexion.createStatement();
			
			 ResultSet queryTable_T2 = stmt.executeQuery(queryTable);
			 String ValorCampo = "";
			 
			 validaStatus = queryNoVacia(queryTable_T2);
			 
			 assertFalse("La query está vacía", validaStatus);
			 
			 ResultSet queryTable3 = stmt.executeQuery(queryTable);
			 
			 if (queryTable3.next()) {  // Mover el cursor al primer registro
				 ValorCampo = queryTable3.getString(nombreCampo);  // Obtener el valor de CAMPO como String
			    System.out.println("Valor de " + nombreCampo + ": " + ValorCampo);
			}
			 
			 testCase.addTextEvidenceCurrentStep("Valor de " + nombreCampo + ": " + ValorCampo);
			 
			 return ValorCampo;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** queryNoVacia
	* Verificar que la query de BD Azure NO regrese vacio
	*@autor Valeria Fernandez
	*@param
	*	-queryTable (Cadena): La query que se va a ejecutar
	*@return vacia (booleano): true si la cadena está vacía, false si la cadena NO está vacía
	*@date 27/Febrero/2025
	*/
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
	*@autor Valeria Fernandez
	*@param
	*	-queryTable (Cadena): La query que se va a ejecutar
	*@return vacia (booleano): true si la cadena NO está vacía, false si la cadena está vacía
	*@date 27/Febrero/2025
	*/
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
	
	/** searchNode
	* Busca el valor del nodo que se indique, en el archivo json
	*@autor Valeria Fernandez
	*@param
	*	-rootNode (Nodo Json): El Nodo que creo del archivo json que se quiere leer
	*	-index (int): Posicion del nodo que se quiere leer (0, 1, 2, etc.)
	*	-nodo (Cadena): El nombre del nodo que se busca en la posición de la variable index
	*@return String (Cadena): Regresa el valor del nodo que se buscó en la posición de la variable index
	*@date 27/Febrero/2025
	*/
	public String searchNode(JsonNode rootNode, int index, String nodo) throws Exception {
		try {
			JsonNode nodosArray = rootNode.path("promotions");
			
			String folioArchivo = "";
			
			// Verificar si el array contiene el índice deseado
	        if (nodosArray.isArray() && nodosArray.size() > index) {
	            JsonNode folio = nodosArray.get(index);

	            // Obtener el valor del nodo en esa posición
	            folioArchivo = folio.get(nodo).asText();
	            System.out.println("Valor de '" + nodo + "': '" + folioArchivo + "' en posición: " + (index + 1));
	        } else {
	            System.out.println("Índice fuera de rango.");
	        }
	        
	        return folioArchivo;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** queryOracle
	* Consulta a BD en Oracle
	*@autor Valeria Fernandez
	*@param
	*	-querytable (Cadena): La query que se quiere ejecutar
	*	-db (utils.sql.SQLUtil): La conexón a la DB que se crea en el script principal
	*@return result (boolean): true or false
	*@date 27/Febrero/2025
	*/
	public boolean queryOracle(String querytable, utils.sql.SQLUtil db) throws Exception {
	    boolean result;

	    try {
	        System.out.println(querytable);
	        // Realiza la consulta con la conexión que se especifique (BD)
	        SQLResult querytable2 = db.executeQuery(querytable);

	        result = !querytable2.isEmpty(); // true si hay resultados, false si está vacío

	        if (result) {
	            testCase.addQueryEvidenceCurrentStep(querytable2);
	        }

	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }

	    return result;
	}
	
	/** queryOracleEmpty
	* Consulta a BD en Oracle, debe regresar vacio
	*@autor Valeria Fernandez
	*@param
	*	-querytable (Cadena): La query que se quiere ejecutar
	*	-db (utils.sql.SQLUtil): La conexón a la DB que se crea en el script principal
	*@return result (boolean): true or false
	*@date 27/Febrero/2025
	*/
	public boolean queryOracleEmpty(String querytable, utils.sql.SQLUtil db) throws Exception {
	    boolean result;

	    try {
	        System.out.println(querytable);
	        // Realiza la consulta con la conexión que se especifique (BD)
	        SQLResult querytable2 = db.executeQuery(querytable);

	        result = querytable2.isEmpty(); // Se espera que esté vacío

	        if (result) {
	            testCase.addQueryEvidenceCurrentStep(querytable2);
	        }

	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }

	    return result; // true si está vacío (caso esperado), false si devolvió datos
	}

	
	/** getValueCampoOracle
	* Obtiene valor de campo en BD Oracle, especificar nombre del campo
	*@autor Valeria Fernandez
	*@param
	*	-querytable (Cadena): La query que se quiere ejecutar
	*	-db (utils.sql.SQLUtil): La conexón a la DB que se crea en el script principal
	*	-campo (Cadena): El nombre del campo que se está buscando 
	*@return Vacio
	*@date 27/Febrero/2025
	*/
	public String getValueCampoOracle(String querytable, utils.sql.SQLUtil db, String campo) throws Exception {
		try {
			SQLResult querytable2 = db.executeQuery(querytable);
			String valor = querytable2.getData(0, campo);
			System.out.println(campo + ": " + valor);
			return valor;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** loginLogsAzure
	* Login al recurso de Azure para consultar logs
	*@autor Valeria Fernandez
	*@param
	*	-clientId (Cadena): clientId de recurso en Azure
	*	-tenantId (Cadena): tenantId de recurso en Azure
	*	-clientSecret (Cadena): clientSecret de recurso en Azure 
	*@return tokenCredential (TokenCredential): token de API para autenticación Azure
	*@date 27/Febrero/2025
	*/
	public TokenCredential loginLogsAzure(String clientId, String tenantId, String clientSecret) throws Exception {
		try {
			ClientSecretCredential credential = new ClientSecretCredentialBuilder()
			        .clientId(clientId)
			        .tenantId(tenantId)
			        .clientSecret(clientSecret)
			        .build();
			  testCase.addBoldTextEvidenceCurrentStep("La conexión fue exitosa");
			  
			  String[] scopes = new String[] { "https://api.loganalytics.io/.default" };
			  
			  AccessToken accessToken = getAccessToken(credential, scopes);

			  TokenCredential tokenCredential = new TokenCredential() {
		            @Override
		            public Mono<AccessToken> getToken(TokenRequestContext request) {
		                return Mono.just(accessToken);  // Retornamos un Mono<AccessToken> envolviendo el accessToken
		            }
		        };
		        
		        return tokenCredential;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** consultaLogsAzure
	* Consulta logs a recurso de Azure con un intervalo de 5 minutos
	*@autor Valeria Fernandez
	*@param
	*	-tokenCredential (TokenCredential): token de autenticación en Azure
	*	-workspaceId (Cadena): Id de workspaceId en Azure que consulta Logs Analitycs de recurso en Azure
	*	-timeInterval (QueryTimeInterval): Intervalo de tiempo en el que hace la consulta, está en 5 minutos 
	*@return seEncontraronErrores (boolean): true or false
	*@date 27/Febrero/2025
	*/
	public boolean consultaLogsAzure(TokenCredential tokenCredential, String workspaceId, QueryTimeInterval timeInterval) throws Exception {
	    boolean seEncontraronErrores = false;

	    try {
	        LogsQueryClient logsQueryClient = new LogsQueryClientBuilder()
	            .credential(tokenCredential)
	            .buildClient();

	        // Definir la consulta KQL
	        String query = "AzureDiagnostics " +
	                "| where Resource == 'OXZRGERDDQ01' " +
	                "| where Level == 'Error' or Level == 'Critical' " +
	                "| where Message contains \"failed\" " +
	                "| project TimeGenerated, Resource, Level, Message, OperationName " +
	                "| order by TimeGenerated desc";

	        // Ejecutar la consulta
	        LogsQueryResult result = logsQueryClient.queryWorkspace(workspaceId, query, timeInterval);

	        testCase.addTextEvidenceCurrentStep("Consulta a realizar en Azure Monitor: " + query);

	        List<LogsTableRow> rows = result.getTable().getRows();
	        StringBuilder resultString = new StringBuilder();

	        if (result.getTable() != null && rows.size() > 0) {
	            seEncontraronErrores = true;
	            resultString.append("TimeGenerated\tResource\tLevel\tMessage\tOperationName\n");

	            for (LogsTableRow row : rows) {
	                resultString.append(row.getColumnValue("TimeGenerated")).append("\t")
	                            .append(row.getColumnValue("Resource")).append("\t")
	                            .append(row.getColumnValue("Level")).append("\t")
	                            .append(row.getColumnValue("Message")).append("\t")
	                            .append(row.getColumnValue("OperationName")).append("\n");
	            }
	        } else {
	            seEncontraronErrores = false;
	            resultString.append("No se encontraron errores o resultados.");
	        }

	        String resultadoAzure = resultString.toString();
	        testCase.addTextEvidenceCurrentStep("Resultado de consulta en Azure Monitor: " + resultadoAzure);

	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }

	    return seEncontraronErrores;
	}

	
	/** getAccessToken
	* Obtener token de acceso para consultar recurso de Azure
	*@autor Valeria Fernandez
	*@param
	*	-credential (ClientSecretCredential): Credenciales de Azure para generar token
	*	-scopes (Arreglo de Cadena): Tiene los scopes para que se genere el token en específico
	*@return tokenRequestContext (AccessToken): Token de credenciales de Azure
	*@date 27/Febrero/2025
	*/
	private static AccessToken getAccessToken(ClientSecretCredential credential, String[] scopes) {
        // Crear el contexto de la solicitud de token con los scopes necesarios
        TokenRequestContext tokenRequestContext = new TokenRequestContext().addScopes(scopes);
        
        // Obtener el token de acceso utilizando el credential
        return credential.getToken(tokenRequestContext).block();  // Usamos block() para obtener el token de forma sincrónica
    }
	
	/** getTokenAPI
	* Obtiene el Token para autenticarse en la API
	*@autor Valeria Fernandez
	*@param
	*	-getPathToken (Cadena): Endpoint complementario para generar token de API
	*	-baseURLTokenTestdata (Cadena): Endpoint base para generar token de API
	*	-Auth (Cadena): cliendId:secretId de API, para la autenticación al generar el token
	*@return access_token (Cadena): Token de API
	*@date 27/Febrero/2025
	*/
	public String getTokenAPI(String getPathToken, String baseURLTokenTestdata, String Auth) throws Exception {
		try {
			setBaseURL(baseURLTokenTestdata);
			//Ejecuta request
			RestAssured.baseURI = baseURLToken;
			
			String auth = Auth;
			String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
			
			//Lista de headers
			HashMap<String, String> headersListToken = new HashMap<>();
			headersListToken.put("Accept", "*/*");
			headersListToken.put("Authorization", "Basic " + encodedAuth);

			String reqBodyToken = "";
			
			//Hacemos el send del request
			Response getRespuesta = api.postRequestWithHeaders(baseURLToken+getPathToken, reqBodyToken, headersListToken);			
			
			//Obtener body de respuesta
			String getBodyResponse = getRespuesta.getBody().asString();		
			
			 //Validaci�n de JSON
			JsonPath getJson = new JsonPath(getBodyResponse);
			String access_token = getJson.get("access_token");

			//Obtener el status code
			int getStatusCode = getRespuesta.getStatusCode();	

			
			System.out.println("statusCode: "+getStatusCode);
			System.out.println("body response: "+getBodyResponse);
			System.out.println("Access Token: "+access_token);
			
			return access_token;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** getTokenAPI
	* Obtiene el Token para autenticarse en la API
	*@autor Valeria Fernandez
	*@param
	*	-getPathToken (Cadena): Endpoint complementario para generar token de API
	*	-baseURLTokenTestdata (Cadena): Endpoint base para generar token de API
	*	-Auth (Cadena): cliendId:secretId de API, para la autenticación al generar el token
	*@return access_token (Cadena): Token de API
	*@date 27/Febrero/2025
	*/
	public String getTokenAPIM(String getPathToken, String baseURLTokenTestdata, String clientId, String clientSecret) throws Exception {
		try {
            String scope = "https://graph.microsoft.com/.default"; // o el scope de tu API

            // === Endpoint para obtener el token ===
            URL url = new URL(baseURLTokenTestdata+getPathToken);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            // === Cuerpo de la solicitud ===
            String body = "grant_type=client_credentials"
                        + "&client_id=" + URLEncoder.encode(clientId, "UTF-8")
                        + "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8")
                        + "&scope=" + URLEncoder.encode(scope, "UTF-8");

            // === Envía la solicitud ===
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = body.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // === Lee la respuesta ===
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
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** getHeaderList
	* Genera una lista con los headers que utilizará la API
	*@autor Valeria Fernandez
	*@param
	*	-access_token (Cadena): Token de API generado para agregarlo a encabezados de servicio (lisp, outbound, ack)
	*@return headersList (HashMap): Encabezados para agregar a servicio ENRED OUT V2 (lisp, outbound, ack)
	*@date 27/Febrero/2025
	*/
	public HashMap<String, String> getHeaderList(String access_token) throws Exception {
		try {
			
			//Lista de headers
			HashMap<String, String> headersList = new HashMap<>();
			headersList.put("Accept", "*/*");
			headersList.put("content-type", "application/json");
			headersList.put("Authorization", "Bearer "+access_token);
			
			return headersList;
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** getHeaderList
	* Genera una lista con los headers que utilizará la API
	*@autor Valeria Fernandez
	*@param
	*	-access_token (Cadena): Token de API generado para agregarlo a encabezados de servicio (lisp, outbound, ack)
	*@return headersList (HashMap): Encabezados para agregar a servicio ENRED OUT V2 (lisp, outbound, ack)
	*@date 27/Febrero/2025
	*/
	public HashMap<String, String> getHeaderListAPIM(String access_token) throws Exception {
		try {
			
			//Lista de headers
			HashMap<String, String> headersList = new HashMap<>();
			headersList.put("Accept", "*/*");
			headersList.put("content-type", "application/json");
			headersList.put("Authorization", "Bearer "+access_token);
			headersList.put("x-Gateway-APIKey", "4f4d881eb97b4716baaafb2b6302fb2e");
			headersList.put("X-Forwarded-Proto", "https");
			
			return headersList;
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** getHeaderListGtwy
	* Genera una lista con los headers que utilizará la API Gtwy
	*@autor Valeria Fernandez
	*@param
	*	-access_token (Cadena): Token de API generado para agregarlo a encabezados de servicio (lisp, outbound, ack)
	*@return headersList (HashMap): Encabezados para agregar a servicio API Gtwy(lisp, outbound, ack)
	*@date 27/Febrero/2025
	*/
	public HashMap<String, String> getHeaderListGtwy(String access_token) throws Exception {
		try {
			
			//Lista de headers
			HashMap<String, String> headersList = new HashMap<>();
			headersList.put("Accept", "*/*");
			headersList.put("content-type", "application/json");
			headersList.put("Authorization", "Bearer "+access_token);
			headersList.put("x-Gateway-APIKey", "fbc9bc94-87da-40cd-b8f2-d8cc8cde5d85");
			headersList.put("Tracking-Id", "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66");
			headersList.put("Channel-Id", "WEB");
			headersList.put("Country-Code", "MX");
			headersList.put("Language", "SPA");
			headersList.put("User-Agent", "postman/1.0.0");
			
			return headersList;
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** lispAPI
	* Consulta el servicio lisp de la API, con header, token, y body correspondiente
	*@autor Valeria Fernandez
	*@param
	*	-baseURILisp (Cadena): Endpoint base para el servicio lisp V2
	*	-getPathLisp (Cadena): Endpoint complementario para el servicio lisp V2
	*	-departmentId (Cadena): Plaza a consultar en lisp
	*	-storeId (Cadena): Tienda a consultar en lisp
	*	-source (Cadena): Fuente de consulta en lisp
	*	-doctype (Cadena): Tipo de documentos que se buscan, en este caso PRM
	*	-access_token (Cadena): El token con el que se autoriza el lisp
	*	-getExpectedCode1 (Cadena): El código de respuesta que se espera
	*	-testcaseName (Cadena): El nombre del caso de prueba que se ejecuta
	*@return name (Cadena): Nombre del documento que se consulta
	*@date 27/Febrero/2025
	*/
	public String lispAPI(String baseURILisp, String getPathLisp, String departmentId, String storeId, String source, String doctype, String access_token, String getExpectedCode1, String testcaseName) throws Exception {
		try {
			//Llamada al API LISP usando el token generado
			RestAssured.baseURI = baseURILisp;
			String reqBody = "";
			
			if(testcaseName.contains("APIM")) {
				reqBody = "{"
						+ "\"CRPlaza\":\""+departmentId+"\","
						+ "\"CRTienda\":\""+storeId+"\","
						+ "\"source\":\""+source+"\""
				+ "}";	
			}else{
			reqBody = "{"
					+ "\"departmentId\":\""+departmentId+"\","
					+ "\"storeId\":\""+storeId+"\","
					+ "\"source\":\""+source+"\""
			+ "}";
			}
			
			//Se env�a como evidencia el request a ejecutar
			testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURILisp+getPathLisp+reqBody);
			
			HashMap<String, String> headersList = null;
			
			if(testcaseName.contains("ENREDOUT")) {
				headersList = getHeaderList(access_token);
				System.out.print("ENREDOUT");
			}if (testcaseName.contains("APIM")){
				headersList = getHeaderListAPIM(access_token);
				System.out.print("APIM");
			}else {
				headersList = getHeaderListGtwy(access_token);
				System.out.print("Gtwy");
			}

			//Hacemos el send del request
			Response getRespuesta2 = api.postRequestWithHeaders(baseURILisp+getPathLisp, reqBody, headersList);
			System.out.print(getRespuesta2);
											
			//Obtener body de respuesta
			String getBodyResponse2 = getRespuesta2.getBody().asPrettyString();	
			System.out.println("Body: "+getBodyResponse2);
			
			testCase.addTextEvidenceCurrentStep("Response: \n"+ getRespuesta2+getBodyResponse2);
			
			JsonPath getJson2 = new JsonPath("");
			if(testcaseName.contains("APIM")) {
				String raw = getBodyResponse2;

				// Quitar comillas inicial y final si existen
				if (raw.startsWith("\"") && raw.endsWith("\"")) {
				    raw = raw.substring(1, raw.length() - 1);
				}

				// Reemplazar caracteres escapados
				raw = raw.replace("\\\"", "\"")
				         .replace("\\r", "")
				         .replace("\\n", "")
				         .replace("\\\\", "\\");

				// Ahora parsear con JsonPath
				getJson2 = new JsonPath(raw);
			}else {
				//Validaci�n de JSON
				getJson2 = new JsonPath(getBodyResponse2);
			}
			
			// Obtener el nombre del documento
			String name = null;
			boolean extesionDoc = true;
						
			if(testcaseName.contains("Digital") || testcaseName.contains("ValidarReglas")) {
				
			String documents = "";
			if(testcaseName.contains("APIM")) {
				documents = getJson2.getString("documents.PVDocName"); 
			}else {
				documents = getJson2.getString("documents.name");
			}
			
			// Obtener la lista de documentos LISP
			java.util.List <Map<String, Object>> documentsList = getJson2.getList("documents");
			System.out.println("documentsList: "+documentsList);
			System.out.println("------");				
			System.out.println("Documents: "+documents);
			System.out.println("-----");
			
			if(testcaseName.contains("APIM")) {
				// Iterar sobre todos los documentos en documentsList
				for (int i = 0; i < documentsList.size(); i++) {
				    // Obtener la lista de valores Names para el documento actual
				    String Names = (String) documentsList.get(i).get("PVDocName");
				    
				    // Verificar si 'PRM' está presente en la lista de Names
				    if (Names != null && Names.contains(doctype) && Names.contains("-")) {
				    	if(Names.contains(".json.gz")) {
				    		name = (String) documentsList.get(i).get("PVDocName");  // Asignar 'PRM' a la variable cuando se encuentra
				    		extesionDoc = true;
				    		break;  // Salir del ciclo ya que ya hemos encontrado el valor
				    	}else {
				    		extesionDoc = false;
				    		System.out.println("El documento no tiene la extensión correcta (.json.gz)");
				    	}
				    }
				}
				
					assertTrue(extesionDoc, "El documento no tiene la extensión correcta (.json.gz)");
					System.out.println("Name del documento: " + name);
				
				// Obtener el fileType del documento
				String fileType = null;

				// Iterar sobre todos los documentos en documentsList
				for (int i = 0; i < documentsList.size(); i++) {
				    // Obtener la lista de valores fileType para el documento actual
				    String fileTypes = (String) documentsList.get(i).get("PVDocType");
				    
				    // Verificar si 'PRM' está presente en la lista de fileTypes
				    if (fileTypes != null && fileTypes.contains(doctype)) {
				    	fileType = (String) documentsList.get(i).get("PVDocType");  // Asignar 'PRM' a la variable cuando se encuentra
				        break;  // Salir del ciclo ya que ya hemos encontrado el valor
				    }
				}
				System.out.println("fileType del documento: " + fileType);
			}else {
			
			// Iterar sobre todos los documentos en documentsList
			for (int i = 0; i < documentsList.size(); i++) {
			    // Obtener la lista de valores Names para el documento actual
			    String Names = (String) documentsList.get(i).get("name");
			    
			    // Verificar si 'PRM' está presente en la lista de Names
			    if (Names != null && Names.contains(doctype) && Names.contains("-")) {
			    	if(Names.contains(".json.gz")) {
			    		name = (String) documentsList.get(i).get("name");  // Asignar 'PRM' a la variable cuando se encuentra
			    		extesionDoc = true;
			    		break;  // Salir del ciclo ya que ya hemos encontrado el valor
			    	}else {
			    		extesionDoc = false;
			    		System.out.println("El documento no tiene la extensión correcta (.json.gz)");
			    	}
			    }
			}
			
				assertTrue(extesionDoc, "El documento no tiene la extensión correcta (.json.gz)");
				System.out.println("Name del documento: " + name);
			
			// Obtener el fileType del documento
			String fileType = null;

			// Iterar sobre todos los documentos en documentsList
			for (int i = 0; i < documentsList.size(); i++) {
			    // Obtener la lista de valores fileType para el documento actual
			    String fileTypes = (String) documentsList.get(i).get("fileType");
			    
			    // Verificar si 'PRM' está presente en la lista de fileTypes
			    if (fileTypes != null && fileTypes.contains(doctype)) {
			    	fileType = (String) documentsList.get(i).get("fileType");  // Asignar 'PRM' a la variable cuando se encuentra
			        break;  // Salir del ciclo ya que ya hemos encontrado el valor
			    }
			}
			System.out.println("fileType del documento: " + fileType);
			}
			}
			
			//VALIDACION DE CODE
			if(testcaseName.contains("ENREDOUT")) {
				int codeResp = Integer.parseInt(getJson2.getString("code"));
				int getExpectedCode = Integer.parseInt(getExpectedCode1);
				assertEquals(codeResp, getExpectedCode); //Validacion
			}if(testcaseName.contains("APIM")) {
				int codeResp = Integer.parseInt(getJson2.getString("WMCode"));
				int getExpectedCode = Integer.parseInt(getExpectedCode1);
				assertEquals(codeResp, getExpectedCode); //Validacion
			}else {
				String codeResp = getJson2.getString("code"); 
				assertEquals(codeResp, getExpectedCode1); //Validacion
			}
			
			return name;
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** lispAPI3ArchivosV2V1
	* Consulta el servicio lisp de la API, con header, token, y body correspondiente. Este método guarda los datos cuando se consulta el lisp con 3 archivos y tipos diferentes
	*@autor Valeria Fernandez
	*@param
	*	-baseURILisp (Cadena): Endpoint base para el servicio lisp V2
	*	-getPathLisp (Cadena): Endpoint complementario para el servicio lisp V2
	*	-departmentId (Cadena): Plaza a consultar en lisp
	*	-storeId (Cadena): Tienda a consultar en lisp
	*	-source (Cadena): Fuente de consulta en lisp
	*	-doctype (Cadena): Tipo de documentos que se buscan, en este caso PRM
	*	-access_token (Cadena): El token con el que se autoriza el lisp
	*	-getExpectedCode1 (Cadena): El código de respuesta que se espera
	*	-testcaseName (Cadena): El nombre del caso de prueba que se ejecuta
	*@return regresaNames (Arreglo de Cadena): Nombres de los documentos que se consultaron
	*@date 27/Febrero/2025
	*/
	public String[] lispAPI3ArchivosV2V1(String baseURILisp, String getPathLisp, String departmentId, String storeId, String source, String doctype, String access_token, String getExpectedCode1, String testcaseName) throws Exception {
		try {			
			//Llamada al API LISP usando el token generado
			RestAssured.baseURI = baseURILisp;	
			String reqBody = "{"
					+ "\"departmentId\":\""+departmentId+"\","
					+ "\"storeId\":\""+storeId+"\","
					+ "\"source\":\""+source+"\""
			+ "}";
			
			//Se env�a como evidencia el request a ejecutar
			testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURILisp+getPathLisp+reqBody);
			
			HashMap<String, String> headersList = null;
			
			if(testcaseName.contains("ENREDOUT")) {
				headersList = getHeaderList(access_token);
				System.out.print("ENREDOUT");
			}else {
				headersList = getHeaderListGtwy(access_token);
				System.out.print("Gtwy");
			}

			//Hacemos el send del request
			Response getRespuesta2 = api.postRequestWithHeaders(baseURILisp+getPathLisp, reqBody, headersList);
			System.out.print(getRespuesta2);
											
			//Obtener body de respuesta
			String getBodyResponse2 = getRespuesta2.getBody().asPrettyString();	
			System.out.println("Body: "+getBodyResponse2);
			
			testCase.addTextEvidenceCurrentStep("Response: \n"+ getRespuesta2+getBodyResponse2);
				
			//Validaci�n de JSON
			JsonPath getJson2 = new JsonPath(getBodyResponse2);
			String documents = getJson2.getString("documents.name"); 
			
			// Obtener la lista de documentos LISP
			java.util.List <Map<String, Object>> documentsList = getJson2.getList("documents");
			System.out.println("documentsList: "+documentsList);
			System.out.println("------");				
			System.out.println("Documents: "+documents);
			System.out.println("-----");
			
			String name = null;
			String name2 = null;
			String name3 = null;
			boolean extesionDoc = true;
			
			// Iterar sobre todos los documentos en documentsList
			for (int i = 0; i < documentsList.size(); i++) {
			    // Obtener la lista de valores fileType para el documento actual
			    String Names = (String) documentsList.get(i).get("name");
			    
			    // Verificar si 'PRM' está presente en la lista de fileTypes
			    if (Names != null && Names.contains(doctype) && !Names.contains("-")) {
			    	if(Names.contains(".json.gz")) {
			    		name2 = (String) documentsList.get(i).get("name");  // Asignar 'PRM' a la variable cuando se encuentra
			    		extesionDoc = true;
			    		break;  // Salir del ciclo ya que ya hemos encontrado el valor
			    	}else {
			    		extesionDoc = false;
			    		System.out.println("El documento no tiene la extensión correcta (.json.gz)");
			    	}
			    }
			}
			
			System.out.println("Name del documento: " + name2);
			testCase.addTextEvidenceCurrentStep("Name del documento: " + name2);
			
			// Iterar sobre todos los documentos en documentsList
			for (int i = 0; i < documentsList.size(); i++) {
			    // Obtener la lista de valores fileType para el documento actual
			    String Names = (String) documentsList.get(i).get("name");
			    
			    // Verificar si 'PRM' está presente en la lista de fileTypes
			    if (Names != null && Names.contains("ITM") && !Names.contains("-")) {
			    	if(Names.contains(".json.gz")) {
			    		name3 = (String) documentsList.get(i).get("name");  // Asignar 'PRM' a la variable cuando se encuentra
			    		extesionDoc = true;
			    		break;  // Salir del ciclo ya que ya hemos encontrado el valor
			    	}else {
			    		extesionDoc = false;
			    		System.out.println("El documento no tiene la extensión correcta (.json.gz)");
			    	}
			    }
			}
			
			System.out.println("Name del documento: " + name3);
			testCase.addTextEvidenceCurrentStep("Name del documento: " + name3);
			
			// Iterar sobre todos los documentos en documentsList
			for (int i = 0; i < documentsList.size(); i++) {
			    // Obtener la lista de valores fileType para el documento actual
			    String Names = (String) documentsList.get(i).get("name");
			    
			    // Verificar si 'PRM' está presente en la lista de fileTypes
			    if (Names != null && Names.contains(doctype) && Names.contains("-")) {
			    	name = (String) documentsList.get(i).get("name");  // Asignar 'PRM' a la variable cuando se encuentra
			    	if(Names.contains(".json.gz")) {
			    		extesionDoc = true;
			    		break;  // Salir del ciclo ya que ya hemos encontrado el valor
			    	}else {
			    		extesionDoc = false;
			    		System.out.println("El documento no tiene la extensión correcta (.json.gz)");
			    	}
			    }
			}
			
			System.out.println("Name del documento: " + name);
			testCase.addTextEvidenceCurrentStep("Name del documento: " + name);
			
			assertTrue(extesionDoc, "El documento no tiene la extensión correcta (.json.gz)");
			
			//VALIDACION DE CODE
			if(testcaseName.contains("ENREDOUT")) {
				int codeResp = Integer.parseInt(getJson2.getString("code"));
				int getExpectedCode = Integer.parseInt(getExpectedCode1);
				assertEquals(codeResp, getExpectedCode); //Validacion
			}else {
				String codeResp = getJson2.getString("code"); 
				assertEquals(codeResp, getExpectedCode1); //Validacion
			}
			
			String[] regresaNames = new String[3]; // Array de 3 posiciones, todos con valor null inicialmente
			regresaNames[0] = name; //PRM Cloud
			regresaNames[1] = name2; //PRM Onpremise
			regresaNames[2] = name3; //ITM Onpremise
			
			return regresaNames;
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** lispAPIV1
	* Consulta el servicio lisp de la API V1, con header, token, y body correspondiente
	*@autor Valeria Fernandez
	*@param
	*	-baseURILisp (Cadena): Endpoint base para el servicio lisp V1
	*	-getPathLisp (Cadena): Endpoint complementario para el servicio lisp V1
	*	-departmentId (Cadena): Plaza a consultar en lisp
	*	-storeId (Cadena): Tienda a consultar en lisp
	*	-source (Cadena): Fuente de consulta en lisp
	*	-doctype (Cadena): Tipo de documentos que se buscan, en este caso PRM
	*	-access_token (Cadena): El token con el que se autoriza el lisp
	*	-getExpectedCode (Entero): El código de respuesta que se espera
	*@return name (Cadena): Nombre del documento que se consulta
	*@date 27/Febrero/2025
	*/
	public String lispAPIV1(String baseURILisp, String getPathLisp, String departmentId, String storeId, String source, String doctype, String access_token, int getExpectedCode) throws Exception {
		try {
			//Llamada al API LISP usando el token generado
			RestAssured.baseURI = baseURILisp;	
			String reqBody = "{"
					+ "\"departmentId\":\""+departmentId+"\","
					+ "\"storeId\":\""+storeId+"\","
					+ "\"source\":\""+source+"\""
			+ "}";
			
			HashMap<String, String> headersList = getHeaderList(access_token);
		
			//Hacemos el send del request
			Response getRespuesta2 = api.postRequestWithHeaders(baseURILisp+getPathLisp, reqBody, headersList);
			System.out.print(getRespuesta2);	
											
			//Obtener body de respuesta
			String getBodyResponse2 = getRespuesta2.getBody().asPrettyString();	
			testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURILisp+getPathLisp+reqBody);
			System.out.println("Body: "+getBodyResponse2);
				
			//Validaci�n de JSON
			JsonPath getJson2 = new JsonPath(getBodyResponse2);
			String documents = getJson2.getString("documents.name"); 
			
			// Obtener la lista de documentos LISP
			java.util.List <Map<String, Object>> documentsList = getJson2.getList("documents");
			System.out.println("documentsList: "+documentsList);
			System.out.println("------");				
			System.out.println("Documents: "+documents);
			System.out.println("-----");
			
			testCase.addTextEvidenceCurrentStep("Response: \n"+ getRespuesta2+getBodyResponse2);
			
			// Obtener el nombre del documento
			String name = null;
			boolean extesionDoc = true;
			
			// Iterar sobre todos los documentos en documentsList
			for (int i = 0; i < documentsList.size(); i++) {
			    // Obtener la lista de valores fileType para el documento actual
			    String Names = (String) documentsList.get(i).get("name");
			    
			    // Verificar si 'PRM' está presente en la lista de fileTypes
			    if (Names != null && Names.contains(doctype)) {
			    	if(Names.contains(".json.gz")) {
			    		name = (String) documentsList.get(i).get("name");  // Asignar 'PRM' a la variable cuando se encuentra
			    		extesionDoc = true;
			    		break;  // Salir del ciclo ya que ya hemos encontrado el valor
			    	}else {
			    		extesionDoc = false;
			    		System.out.println("El documento no tiene la extensión correcta (.json.gz)");
			    	}
			    }
			}
			
			assertTrue(extesionDoc, "El documento no tiene la extensión correcta (.json.gz)");
			System.out.println("Name del documento: " + name);
			
			// Obtener el fileType del documento
			String fileType = null;

			// Iterar sobre todos los documentos en documentsList
			for (int i = 0; i < documentsList.size(); i++) {
			    // Obtener la lista de valores fileType para el documento actual
			    String fileTypes = (String) documentsList.get(i).get("fileType");
			    
			    // Verificar si 'PRM' está presente en la lista de fileTypes
			    if (fileTypes != null && fileTypes.contains(doctype)) {
			    	fileType = (String) documentsList.get(i).get("fileType");  // Asignar 'PRM' a la variable cuando se encuentra
			        break;  // Salir del ciclo ya que ya hemos encontrado el valor
			    }
			}
			System.out.println("fileType del documento: " + fileType);
			
			//VALIDACION DE CODE
			int codeResp = Integer.parseInt(getJson2.getString("code")); 
			assertEquals(codeResp, getExpectedCode); //Validacion
			
			return name;
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** outboundAPI
	* Consulta el servicio outbound de la API, con header, token, y body correspondiente
	*@autor Valeria Fernandez
	*@param
	*	-baseURIOutbound (Cadena): Endpoint base para el servicio outbound V2
	*	-getPathOutbound (Cadena): Endpoint complementario para el servicio outbound V2
	*	-name (Cadena): Nombre del documento que se va a descargar, se obtiene del servicio outbound
	*	-departmentId (Cadena): Plaza a consultar en outbound
	*	-storeId (Cadena): Tienda a consultar en outbound
	*	-source (Cadena): Fuente de consulta en outbound
	*	-doctype (Cadena): Tipo de documentos que se buscan, en este caso PRM
	*	-access_token (Cadena): El token con el que se autoriza el outbound
	*	-getExpectedCode1 (Cadena): El código de respuesta que se espera
	*	-testcaseName (Cadena): El nombre del caso de prueba que se ejecuta
	*@return eventDateOutbound (Cadena): Fecha de descarga del documento que se consulta
	*@date 27/Febrero/2025
	*/
	public String outboundAPI(String baseURIOutbound, String getPathOutbound, String name, String departmentId, String storeId, String source, String doctype, String access_token, String getExpectedCode1, String testcaseName) throws Exception {
		try {
			//Invocar la API Outbound con JSON
			RestAssured.baseURI = baseURIOutbound;	
			String reqBodyOutbound ="";
		if(testcaseName.contains("APIM")) {
			reqBodyOutbound = "{"
					+ "\"CRPlaza\":\""+departmentId+"\","
					+ "\"CRTienda\":\""+storeId+"\","
					+ "\"source\":\""+source+"\","
					+ "\"documents\":["
							+ "{"
								+ "\"PVDocName\": \""+name+"\","
								+ "\"PVDocType\": \""+doctype+"\""
							+ "}"
						+ "]"
				+ "}";
		}else {
			 reqBodyOutbound = "{"
					+ "\"departmentId\":\""+departmentId+"\","
					+ "\"storeId\":\""+storeId+"\","
					+ "\"source\":\""+source+"\","
					+ "\"documents\":["
							+ "{"
								+ "\"name\": \""+name+"\","
								+ "\"fileType\": \""+doctype+"\""
							+ "}"
						+ "]"
				+ "}";
		}
			//Se env�a como evidencia el request a ejecutar
			testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURIOutbound+getPathOutbound+reqBodyOutbound);

			HashMap<String, String> headersList = null;
			
			if(testcaseName.contains("ENREDOUT")) {
				headersList = getHeaderList(access_token);
				System.out.print("ENREDOUT");
			}if(testcaseName.contains("APIM")) {
				headersList = getHeaderListAPIM(access_token);
				System.out.print("APIM");
			}else {
				headersList = getHeaderListGtwy(access_token);
				System.out.print("Gtwy");
			}
			
			//Hacemos el send del request
			Response getRespuesta3 = api.postRequestWithHeaders(baseURIOutbound+getPathOutbound, reqBodyOutbound, headersList);
			
			//Obtener body de respuesta
			String getBodyResponse3 = getRespuesta3.getBody().asPrettyString();
				
			System.out.println("Body: "+getBodyResponse3);
			testCase.addTextEvidenceCurrentStep("Response: \n"+ getRespuesta3+getBodyResponse3);
			
			JsonPath getJson3 = new JsonPath("");
			if(testcaseName.contains("APIM")) {
				String raw = getBodyResponse3;

				// Quitar comillas inicial y final si existen
				if (raw.startsWith("\"") && raw.endsWith("\"")) {
				    raw = raw.substring(1, raw.length() - 1);
				}

				// Reemplazar caracteres escapados
				raw = raw.replace("\\\"", "\"")
				         .replace("\\r", "")
				         .replace("\\n", "")
				         .replace("\\\\", "\\");

				// Ahora parsear con JsonPath
				getJson3 = new JsonPath(raw);
			}else {
				//Validaci�n de JSON
				getJson3 = new JsonPath(getBodyResponse3);
			}
			
		    if(testcaseName.contains("APIM")) {
		    	String documentsOutbound = getJson3.getString("documents.PVDocName");
		    }else {
		    	String documentsOutbound = getJson3.getString("documents.name");
			}
		
			if(testcaseName.contains("ENREDOUT")) {
				//VALIDACION CODE
				int codeResp = Integer.parseInt(getJson3.getString("code"));
				int getExpectedCode = Integer.parseInt(getExpectedCode1);
				assertEquals(codeResp, getExpectedCode);
			}if(testcaseName.contains("APIM")) {
				int codeResp = Integer.parseInt(getJson3.getString("WMCode"));
				int getExpectedCode = Integer.parseInt(getExpectedCode1);
				assertEquals(codeResp, getExpectedCode);
			}else {
				String codeResp = getJson3.getString("code"); 
				assertEquals(codeResp, getExpectedCode1);
			}
			
			String eventDateOutbound = "";
			
			if(testcaseName.contains("Digital") || testcaseName.contains("ValidarReglas")) {
			// Obtener la lista de documentos OUTBOUND
			java.util.List <Map<String, Object>> documentsListOutbound = getJson3.getList("documents");
			System.out.println("documentsListOutbound: "+documentsListOutbound);
			testCase.addTextEvidenceCurrentStep("Lista Documentos Outbound: \n"+ getBodyResponse3);
			
			// Obtener el NOMBRE del documento OUTBOUND
			String nameOutbound = null;
			boolean extesionDoc = true;
			
			if(testcaseName.contains("APIM")) {
				// Iterar sobre todos los documentos en documentsList
				for (int i = 0; i < documentsListOutbound.size(); i++) {
				    // Obtener la lista de valores fileType para el documento actual
				    String Names = (String) documentsListOutbound.get(i).get("PVDocName");
				    
				    // Verificar si 'PRM' está presente en la lista de fileTypes
				    if (Names != null && Names.contains(doctype) && Names.contains("-")) {
				    	if(Names.contains(".json.gz")) {
				    		name = (String) documentsListOutbound.get(i).get("PVDocName");  // Asignar 'PRM' a la variable cuando se encuentra
				    		extesionDoc = true;
				    		break;  // Salir del ciclo ya que ya hemos encontrado el valor
				    	}else {
				    		extesionDoc = false;
				    		System.out.println("El documento no tiene la extensión correcta (.json.gz)");
				    	}
				    }
				}
	
				// Obtener el FILETYPE del documento OUTBOUND
				String fileTypeOutbound = (String) documentsListOutbound.get(0).get("PVDocType");
				System.out.println("fileType del documento Outbound: " + fileTypeOutbound);
				
				// Obtener el STATUS del documento OUTBOUND
				String statusOutbound = (String) documentsListOutbound.get(0).get("PVStatus");
				System.out.println("Status del documento Outbound: " + statusOutbound);
				
				// Obtener el STATUS del documento OUTBOUND
				String dataOutbound = (String) documentsListOutbound.get(0).get("PVData");
				testCase.addTextEvidenceCurrentStep("Data del documento Outbound: " + dataOutbound);
				// Decodificar la cadena Base64
		        byte[] decodedBytes = Base64.getDecoder().decode(dataOutbound);
		        // Convertir los bytes decodificados a una cadena
		        String dataOutboundDecoded = new String(decodedBytes);
				testCase.addTextEvidenceCurrentStep("Data decodificada del documento Outbound: " + dataOutboundDecoded);
			}else {
				// Iterar sobre todos los documentos en documentsList
				for (int i = 0; i < documentsListOutbound.size(); i++) {
				    // Obtener la lista de valores fileType para el documento actual
				    String Names = (String) documentsListOutbound.get(i).get("name");
				    
				    // Verificar si 'PRM' está presente en la lista de fileTypes
				    if (Names != null && Names.contains(doctype) && Names.contains("-")) {
				    	if(Names.contains(".json.gz")) {
				    		name = (String) documentsListOutbound.get(i).get("name");  // Asignar 'PRM' a la variable cuando se encuentra
				    		extesionDoc = true;
				    		break;  // Salir del ciclo ya que ya hemos encontrado el valor
				    	}else {
				    		extesionDoc = false;
				    		System.out.println("El documento no tiene la extensión correcta (.json.gz)");
				    	}
				    }
				}
	
				// Obtener el FILETYPE del documento OUTBOUND
				String fileTypeOutbound = (String) documentsListOutbound.get(0).get("fileType");
				System.out.println("fileType del documento Outbound: " + fileTypeOutbound);
				
				// Obtener el STATUS del documento OUTBOUND
				String statusOutbound = (String) documentsListOutbound.get(0).get("status");
				System.out.println("Status del documento Outbound: " + statusOutbound);
				
				// Obtener el STATUS del documento OUTBOUND
				String dataOutbound = (String) documentsListOutbound.get(0).get("data");
				testCase.addTextEvidenceCurrentStep("Data del documento Outbound: " + dataOutbound);
				// Decodificar la cadena Base64
		        byte[] decodedBytes = Base64.getDecoder().decode(dataOutbound);
		        // Convertir los bytes decodificados a una cadena
		        String dataOutboundDecoded = new String(decodedBytes);
				testCase.addTextEvidenceCurrentStep("Data decodificada del documento Outbound: " + dataOutboundDecoded);
			}

			// Setear EVENTDATE del documento OUTBOUND
	        LocalDateTime now = LocalDateTime.now(); // Obtener la fecha y hora actual
	        
			if(testcaseName.contains("ENREDOUT") || testcaseName.contains("APIM")) {
				DateTimeFormatter formatoEventDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); // Formateador con la sintaxis del eventDate
				eventDateOutbound = (String) now.format(formatoEventDate);
				System.out.print("ENREDOUT/APIM");
			}else {
				DateTimeFormatter formatoEventDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Formateador con la sintaxis del eventDate
				eventDateOutbound = (String) now.format(formatoEventDate);
				System.out.print("Gtwy");
			}

			System.out.println("eventDate del documento Outbound: " + eventDateOutbound);
			}
			
			return eventDateOutbound;
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** outboundAPIV1
	* Consulta el servicio outbound de la API V1, con header, token, y body correspondiente
	*@autor Valeria Fernandez
	*@param
	*	-baseURIOutbound (Cadena): Endpoint base para el servicio outbound V1
	*	-getPathOutbound (Cadena): Endpoint complementario para el servicio outbound V1
	*	-name (Cadena): Nombre del documento que se va a descargar, se obtiene del servicio outbound
	*	-departmentId (Cadena): Plaza a consultar en outbound
	*	-storeId (Cadena): Tienda a consultar en outbound
	*	-source (Cadena): Fuente de consulta en outbound
	*	-doctype (Cadena): Tipo de documentos que se buscan, en este caso PRM
	*	-access_token (Cadena): El token con el que se autoriza el outbound
	*	-getExpectedCode (Entero): El código de respuesta que se espera
	*@return eventDateOutbound (Cadena): Fecha de descarga del documento que se consulta
	*@date 27/Febrero/2025
	*/
	public String outboundAPIV1(String baseURIOutbound, String getPathOutbound, String name, String departmentId, String storeId, String source, String doctype, String access_token, int getExpectedCode) throws Exception {
		try {
			//Invocar la API Outbound con JSON
			RestAssured.baseURI = baseURIOutbound;	
			String reqBodyOutbound = "{"
					+ "\"departmentId\":\""+departmentId+"\","
					+ "\"storeId\":\""+storeId+"\","
					+ "\"source\":\""+source+"\","
					+ "\"documents\":["
							+ "{"
								+ "\"name\": \""+name+"\","
								+ "\"fileType\": \""+doctype+"\""
							+ "}"
						+ "]"
				+ "}";
			
			//Se env�a como evidencia el request a ejecutar
			testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURIOutbound+getPathOutbound+reqBodyOutbound);
			
			HashMap<String, String> headersList = getHeaderList(access_token);
			
			//Hacemos el send del request
			Response getRespuesta3 = api.postRequestWithHeaders(baseURIOutbound+getPathOutbound, reqBodyOutbound, headersList);
			
			//Obtener body de respuesta
			String getBodyResponse3 = getRespuesta3.getBody().asPrettyString();
				
			System.out.println("Body: "+getBodyResponse3);
			testCase.addTextEvidenceCurrentStep("Response: \n"+ getRespuesta3+getBodyResponse3);
			
			JsonPath getJson3 = new JsonPath(getBodyResponse3);
			String documentsOutbound = getJson3.getString("documents.name");
			
			//VALIDACION CODE
			int codeResp = Integer.parseInt(getJson3.getString("code")); 
			assertEquals(codeResp, getExpectedCode);
			
			// Obtener la lista de documentos OUTBOUND
			java.util.List <Map<String, Object>> documentsListOutbound = getJson3.getList("documents");
			System.out.println("documentsListOutbound: "+documentsListOutbound);
			testCase.addTextEvidenceCurrentStep("Lista Documentos Outbound: \n"+ getBodyResponse3);
			
			// Obtener el NOMBRE del documento OUTBOUND
			String nameOutbound = (String) documentsListOutbound.get(0).get("name");
			System.out.println("Name del documento Outbound: " + nameOutbound);
			
			// Obtener el FILETYPE del documento OUTBOUND
			String fileTypeOutbound = (String) documentsListOutbound.get(0).get("fileType");
			System.out.println("fileType del documento Outbound: " + fileTypeOutbound);
			
			// Obtener el STATUS del documento OUTBOUND
			String statusOutbound = (String) documentsListOutbound.get(0).get("status");
			System.out.println("Status del documento Outbound: " + statusOutbound);
			
			// Setear EVENTDATE del documento OUTBOUND
	        LocalDateTime now = LocalDateTime.now(); // Obtener la fecha y hora actual
	        DateTimeFormatter formatoEventDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Formateador con la sintaxis del eventDate
			String eventDateOutbound = (String) now.format(formatoEventDate);
			System.out.println("eventDate del documento Outbound: " + eventDateOutbound);
			
			return eventDateOutbound;
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** ackAPI
	* Consulta el servicio ack de la API para las 2 versiones, con header, token, y body correspondiente
	*@autor Valeria Fernandez
	*@param
	*	-baseURIACK (Cadena): Endpoint base para el servicio ack V2 o V1
	*	-getPathACK (Cadena): Endpoint complementario para el servicio ack V2 o V1
	*	-name (Cadena): Nombre del documento que se va a confirmar descargado, se obtiene del servicio outbound
	*	-departmentId (Cadena): Plaza a consultar en ack
	*	-storeId (Cadena): Tienda a consultar en ack
	*	-source (Cadena): Fuente de consulta en ack
	*	-eventDateOutbound (Cadena): Fecha de descarga del documento que se consulta, se obtiene del servicio outbound
	*	-doctype (Cadena): Tipo de documentos que se buscan, en este caso PRM
	*	-getPathToken (Cadena): Endpoint complementario para generar token de API
	*	-getExpectedCode1 (Cadena): El código de respuesta que se espera
	*	-testcaseName (Cadena): El nombre del caso de prueba que se ejecuta
	*	-baseURLTokenTestdata (Cadena): Endpoint base para generar token de API
	*	-Auth (Cadena): cliendId:secretId de API, para la autenticación al generar el token
	*	-statusBody (Cadena): El status que se envia en el servicio ack (P, R, M, etc.)
	*@return Vacio
	*@date 27/Febrero/2025
	*/
	public void ackAPI(String baseURIACK, String getPathACK, String name, String departmentId, String storeId, String eventDateOutbound, String doctype, String getPathToken, String getExpectedCode1, String testcaseName, String baseURLTokenTestdata, String Auth, String clientIdAPI, String secretIdAPI, String statusBody) throws Exception {
		try {
			RestAssured.baseURI = baseURIACK;
			
			String reqBodyACK = "";
			if(testcaseName.contains("APIM")) {
				reqBodyACK = "{\n" +
				        "    \"CRPlaza\":\""+departmentId+"\",\n" +
				        "    \"CRTienda\":\""+storeId+"\",\n" +
				        "    \"source\":\"POS\",\n" +
				        "    \"documents\":[\n" +
				        "       {\n" +
				        "          \"PVDocName\":\""+name+"\",\n" +
				        "          \"PVDocType\":\""+doctype+"\",\n" +
				        "          \"PVStatus\":\""+statusBody+"\",\n" +
				        "          \"PVEventDate\":\""+eventDateOutbound+"\"\n" +
				        "       }\n" +
				        "    ]\n" +
				        "}";
			}else {
				reqBodyACK = "{\n" +
			        "    \"departmentId\":\""+departmentId+"\",\n" +
			        "    \"storeId\":\""+storeId+"\",\n" +
			        "    \"source\":\"POS\",\n" +
			        "    \"documents\":[\n" +
			        "       {\n" +
			        "          \"name\":\""+name+"\",\n" +
			        "          \"fileType\":\""+doctype+"\",\n" +
			        "          \"status\":\""+statusBody+"\",\n" +
			        "          \"eventDate\":\""+eventDateOutbound+"\"\n" +
			        "       }\n" +
			        "    ]\n" +
			        "}";
			}
			
			//Se env�a como evidencia el request a ejecutar
			testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURIACK+getPathACK+reqBodyACK);
			
			String access_token = "";
			
			if(testcaseName.contains("APIM")) {
				access_token = getTokenAPIM(getPathToken, baseURLTokenTestdata, clientIdAPI, secretIdAPI);
			}else {
				access_token = getTokenAPI(getPathToken, baseURLTokenTestdata, Auth);
			}
			
			HashMap<String, String> headersList = null;
			
			if(testcaseName.contains("ENREDOUT")) {
				headersList = getHeaderList(access_token);
				System.out.print("ENREDOUT");
			}if(testcaseName.contains("APIM")) {
				headersList = getHeaderListAPIM(access_token);
				System.out.print("APIM");
			}else {
				headersList = getHeaderListGtwy(access_token);
				System.out.print("Gtwy");
			}
			
			//Hacemos el send del request
			Response getRespuestaACK = api.postRequestWithHeaders(baseURIACK+getPathACK, reqBodyACK, headersList);
			
			//Obtener body de respuesta
			String getBodyResponseACK = getRespuestaACK.getBody().asPrettyString();
			System.out.println("Body: "+getBodyResponseACK);
			
			JsonPath getJsonACK = new JsonPath("");
			if(testcaseName.contains("APIM")) {
				String raw = getBodyResponseACK;

				// Quitar comillas inicial y final si existen
				if (raw.startsWith("\"") && raw.endsWith("\"")) {
				    raw = raw.substring(1, raw.length() - 1);
				}

				// Reemplazar caracteres escapados
				raw = raw.replace("\\\"", "\"")
				         .replace("\\r", "")
				         .replace("\\n", "")
				         .replace("\\\\", "\\");

				// Ahora parsear con JsonPath
				getJsonACK = new JsonPath(raw);
			}else {
				//Validaci�n de JSON
				getJsonACK = new JsonPath(getBodyResponseACK);
			}
			
			String messageACK = "";
			String code = "";
			
			if(testcaseName.contains("APIM")) {
				messageACK = getJsonACK.getString("WMDesc");
				code = getJsonACK.getString("WMCode");
				testCase.addTextEvidenceCurrentStep("Code ACK: "+ code);
				testCase.addTextEvidenceCurrentStep("Mensaje ACK: "+ messageACK);
				testCase.addTextEvidenceCurrentStep("Response: \n" + getRespuestaACK+getBodyResponseACK);
			}else {
				messageACK = getJsonACK.getString("message");
				code = getJsonACK.getString("code");
				testCase.addTextEvidenceCurrentStep("Code ACK: "+ code);
				testCase.addTextEvidenceCurrentStep("Mensaje ACK: "+ messageACK);
				testCase.addTextEvidenceCurrentStep("Response: \n" + getRespuestaACK+getBodyResponseACK);
			}
			
			if(testcaseName.contains("ENREDOUT") || testcaseName.contains("APIM")) {
				int codeACK = Integer.parseInt(code);
				int getExpectedCode = Integer.parseInt(getExpectedCode1);
				assertEquals(codeACK, getExpectedCode);
			}else {
				assertEquals(code, getExpectedCode1);
			}
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** validateJsonRequest
	 * Convierte el json del request de la API a JsonNode
	*@autor Valeria Fernandez
	*@param
	*	-jsonString (Cadena): El valor del body que se va a enviar en el servicio (lisp, outbound, ack)
	*@return validateJsonNode (boolean): Un método que regresa true o false
	*@date 27/Febrero/2025
	*/
    public static boolean validateJsonRequest(String jsonString) throws IOException {
        // Crear un ObjectMapper para convertir el JSON en un JsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);

        // Validamos todas las claves del JSON
        return validateJsonNode(rootNode);
    }
    
    /** validateJsonNode
    * Valida el valor del nodo del archivo json sean minúsculas y formato camelCase
	*@autor Valeria Fernandez
	*@param
	*	-node (JsonNode): El valor del body que se va a enviar en el servicio (lisp, outbound, ack) convertido a JsonNode
	*@return (boolean): Regresa true o false
	*@date 27/Febrero/2025
	*/
    private static boolean validateJsonNode(JsonNode node) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

            // Iteramos sobre las claves del objeto JSON
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey();

                // Validamos que la clave esté en minúsculas
                if (!key.equals(key.toLowerCase()) && !isCamelCase(key)) {
                    System.out.println("La clave '" + key + "' no está en minúsculas ni en formato camelCase.");
                    return false;
                }

                // Validamos que la clave esté en inglés (solo letras sin caracteres no permitidos)
                if (!isValidEnglish(key)) {
                    System.out.println("La clave '" + key + "' contiene caracteres no válidos para inglés.");
                    return false;
                }

                // Si la clave tiene un valor que también es un objeto JSON, validamos recursivamente
                if (entry.getValue().isObject()) {
                    if (!validateJsonNode(entry.getValue())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /** isCamelCase
    * Verifica que el nodo del archivo json esté en formato  Camel Case
	*@autor Valeria Fernandez
	*@param
	*	-key (Cadena): El valor de nodo del archivo json que se evalua
	*@return (boolean): Regresa true o false
	*@date 27/Febrero/2025
	*/
    private static boolean isCamelCase(String key) {
        // Verificamos que la clave siga el formato camelCase
        return CAMEL_CASE_PATTERN.matcher(key).matches();
    }
    
    /** isValidEnglish
    * Valida que la clave esté en inglés (solo letras sin caracteres no permitidos)
	*@autor Valeria Fernandez
	*@param
	*	-key (Cadena): El valor de nodo del archivo json que se evalua
	*@return (boolean): Regresa true o false
	*@date 27/Febrero/2025
	*/
    private static boolean isValidEnglish(String key) {
        // Verificamos que la clave solo contenga caracteres alfabéticos (sin acentos ni caracteres especiales)
        return key.matches("^[a-zA-Z]+$");
    }
    
    /** esJsonValido
    * Valida que el nodo sea válido
	*@autor Valeria Fernandez
	*@param
	*	-requestBody (Cadena): El valor del body que se va a enviar en el servicio (lisp, outbound, ack) convertido a JsonNode
	*@return (boolean): Regresa true o false
	*@date 27/Febrero/2025
	*/
    public boolean esJsonValido(String requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Intentar convertir el cuerpo de la solicitud a un objeto
            objectMapper.readTree(requestBody);
            return true; // Si no lanza excepción, es JSON válido
        } catch (IOException e) {
            return false; // Si hay un error, no es JSON válido
        }
    }
    
    /** validateJsonNombreNodo
    * Valida que si el nodo padre tiene un nombre, el hijo no contenga este nombre
	*@autor Valeria Fernandez
	*@param
	*	-node (JsonNode): El valor del body que se va a enviar en el servicio (lisp, outbound, ack) convertido a JsonNode
	*	-parentName (Cadena): Nombre del nodo padre, con el que se compara que el nodo hijo no contenga el nombre del nodo padre
	*@return (boolean): Regresa true o false
	*@date 27/Febrero/2025
	*/
    public static boolean validateJsonNombreNodo(JsonNode node, String parentName) {
        if (node.isObject()) {
            Iterator<Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();

                // Si el nodo padre tiene un nombre, verificamos que el hijo no contenga este nombre (sin importar mayúsculas/minúsculas)
                if (parentName != null && fieldName.toLowerCase().contains(parentName.toLowerCase())) {
                    System.out.println("El nombre del nodo hijo '" + fieldName + "' contiene el nombre del nodo padre '" + parentName + "' (sin importar mayúsculas/minúsculas).");
                    return false;
                }

                // Llamada recursiva para validar los nodos hijos
                if (!validateJsonNombreNodo(field.getValue(), fieldName)) {
                    return false;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode arrayElement : node) {
                // Validar cada elemento del array
                if (!validateJsonNombreNodo(arrayElement, parentName)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    /** validateDocument
    * Valida que el nodo "name" que corresponde al nombre del archivo, tenga la extensión válida (.json.gz)
   	*@autor Valeria Fernandez
   	*@param
   	*	-document (JsonNode): El valor del nodo "name" para evaluar que tenga el formato correcto
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    private static boolean validateDocument(JsonNode document) {
        JsonNode nameNode = document.path("name");
        if (nameNode.isTextual()) {
            String name = nameNode.asText();
            // Verificar que "name" tenga una extensión válida (por ejemplo, ".json.gz")
            if (!name.matches(".*\\.[a-zA-Z0-9]+$")) {
                System.out.println("El campo 'name' en el documento debe tener una extensión válida.");
                return false;
            }
        } else {
            System.out.println("El campo 'name' en el documento debe ser un texto.");
            return false;
        }

        return true;
    }
    
    /** archivoAzureExiste
    * Valida si un archivo SI existe en el contenedor de Azure
   	*@autor Valeria Fernandez
   	*@param
   	*	-connectionString (Cadena): Cadena de conexión de Azure para buscar archivo
   	*	-containerName (Cadena): Nombre del contenedor donde se buscará el archivo
   	*	-blobName (Cadena): Nombre del archivo a buscar
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
	public static boolean archivoAzureExiste(String connectionString, String containerName, String blobName) {
        // Crear un cliente de servicio de blob con la cadena de conexión
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                                                .connectionString(connectionString)
                                                .buildClient();
        
        // Obtener el cliente del contenedor
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        
        try {
            // Obtener el cliente del blob
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            
            // Intentar obtener las propiedades del blob (si no existe, se lanzará una excepción)
            blobClient.getProperties();
            
            return true;  // Si no lanza excepción, el blob existe
        } catch (ResourceNotFoundException e) {
            // Si el archivo no se encuentra, se lanza una excepción ResourceNotFoundException
            return false;
        }
    }
	
	/** archivoAzureNoExiste
	* Valida si un archivo NO existe en el contenedor de Azure
   	*@autor Valeria Fernandez
   	*@param
   	*	-connectionString (Cadena): Cadena de conexión de Azure para buscar archivo
   	*	-containerName (Cadena): Nombre del contenedor donde se buscará el archivo
   	*	-blobName (Cadena): Nombre del archivo a buscar
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
	public static boolean archivoAzureNoExiste(String connectionString, String containerName, String blobName) {
		// Crear un cliente de servicio de blob con la cadena de conexión
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                                                .connectionString(connectionString)
                                                .buildClient();
        // Obtener el cliente del contenedor
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        // Verificar si el contenedor existe
        if (!containerClient.exists()) {
            System.out.println("El contenedor no existe.");
            return true;  // Si el contenedor no existe, asumimos que el archivo no existe
        }

        try {
            // Obtener el cliente del blob
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            // Intentar obtener las propiedades del blob (si existe, no se lanzará excepción)
            blobClient.getProperties();
            return false;  // Si no lanza excepción, el blob existe, por lo que retornamos false
        } catch (ResourceNotFoundException e) {
            // Si el archivo no se encuentra, se lanza una excepción ResourceNotFoundException
        	System.out.println("El archivo no existe" + e);
            return true;  // Si se captura la excepción, significa que el archivo no existe, retornamos true
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            System.out.println("Excepción: " + e.getMessage());
            testCase.addTextEvidenceCurrentStep("Excepción: " + e.getMessage());
            return true;  // En caso de otros errores, devolvemos false por seguridad
        }
    }
	
	/** downloadFileFromAzure
	* Descarga el archivo json del contenedor de Azure en la ruta local especificada
   	*@autor Valeria Fernandez
   	*@param
   	*	-connectionString (Cadena): Cadena de conexión de Azure para buscar archivo
   	*	-containerName (Cadena): Nombre del contenedor donde se buscará el archivo
   	*	-blobName (Cadena): Nombre del archivo a buscar
   	*	-localFilePath (Cadena): Ruta local donde se va a almacenar el archivo
   	*@return localFile (File): Ruta absoluta local del archivo descargado 
   	*@date 27/Febrero/2025
   	*/
    // Descargar un archivo desde Azure Storage
    public static String downloadFileFromAzure(String connectionString, String containerName, String blobName, String localFilePath) throws IOException {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        File localFile = new File(localFilePath + blobName);
        
        if (!localFile.exists()) {
            localFile.createNewFile();  // Crear el archivo si no existe
        }
        
        try (OutputStream outputStream = Files.newOutputStream(localFile.toPath())) {
            // Usar downloadToStream() para descargar el blob al OutputStream
            blobClient.downloadStream(outputStream);
            System.out.println("El archivo se ha descargado exitosamente: " + localFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return localFile.getAbsolutePath();
    }
    
    /** isFilePresentInFTP
    * Verifica si un archivo existe en FTP
   	*@autor Valeria Fernandez
   	*@param
   	*	-ftpHost (Cadena): Host de conexión a FTP
   	*	-ftpUser (Cadena): Usuario de conexión a FTP
   	*	-ftpPassword (Cadena): Contraseña de conexión a FTP
   	*	-filePath (Cadena): Ruta local donde se va a almacenar el archivo
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    public static boolean isFilePresentInFTP(String ftpHost, String ftpUser, String ftpPassword, String filePath) {
    	FTPClient ftpClient = new FTPClient();
        try {
            // Conectar al servidor FTP
            ftpClient.connect(ftpHost);
            ftpClient.login(ftpUser, ftpPassword);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Obtener el directorio y el nombre del archivo
            String parentDir = filePath.substring(0, filePath.lastIndexOf('/'));
            String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);

            // Cambiar al directorio donde se encuentra el archivo
            if (!ftpClient.changeWorkingDirectory(parentDir)) {
                // Si no podemos cambiar al directorio, el archivo no existe
                return false;
            }

            // Listar los archivos en el directorio
            FTPFile[] files = ftpClient.listFiles();

            // Buscar el archivo específico en el listado
            for (FTPFile file : files) {
                if (file.getName().equals(fileName)) {
                    return true; // El archivo existe
                }
            }
            return false; // El archivo no existe
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error al intentar conectar o listar los archivos
        } finally {
            try {
                ftpClient.disconnect(); // Desconectar del servidor FTP
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      }
    
    /** downloadFileFromFTP
    * Descarga el archivo json de FTP en la ruta local especificada
   	*@autor Valeria Fernandez
   	*@param
   	*	-ftpHost (Cadena): Host de conexión a FTP
   	*	-ftpUser (Cadena): Usuario de conexión a FTP
   	*	-ftpPassword (Cadena): Contraseña de conexión a FTP
   	*	-remoteFilePath (Cadena): Ruta remota de donde se va a descargar el archivo
   	*	-localFilePath (Cadena): Ruta local donde se va a almacenar el archivo
   	*@return localFile (File): Ruta absoluta local del archivo descargado
   	*@date 27/Febrero/2025
   	*/
    // Descargar un archivo desde FTP
    public static String downloadFileFromFTP(String ftpHost, String ftpUser, String ftpPassword, String remoteFilePath, String localFilePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        File localFile = new File(localFilePath);
        try (OutputStream outputStream = new FileOutputStream(localFile)) {
            ftpClient.connect(ftpHost);
            ftpClient.login(ftpUser, ftpPassword);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(remoteFilePath, outputStream);
            System.out.println("El archivo se ha descargado exitosamente: " + localFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            ftpClient.disconnect();
        }
        return localFile.getAbsolutePath();
    }

    /** compareJSONFiles
    * Compara que los archivos json tengan la misma estructura
   	*@autor Valeria Fernandez
   	*@param
   	*	-file1 (File): Archivo 1 que se compara (V1)
   	*	-file2 (File): Archivo 2 que se compara (V2)
   	*@return keys1.equals(keys2) (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    // Comparar los archivos JSON por sus etiquetas
    public static boolean compareJSONFiles(File file1, File file2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Parsear ambos archivos JSON
        JsonNode json1 = objectMapper.readTree(file1);
        JsonNode json2 = objectMapper.readTree(file2);
        
        // Obtener las claves (etiquetas) de ambos archivos
        Set<String> keys1 = getKeys(json1);
        Set<String> keys2 = getKeys(json2);
        
        // Comparar las claves
        return keys1.equals(keys2);
    }

    /** getKeys
    * Extrae las llaves (keys) del archivo json
   	*@autor Valeria Fernandez
   	*@param
   	*	-jsonNode (JsonNode): Contenido del archivo para extraer keys (nodos)
   	*@return keys (Set<String>): Las keys (nodos) ordenados en un Set de Cadena
   	*@date 27/Febrero/2025
   	*/
    // Extraer las claves (etiquetas) de un archivo JSON
    private static Set<String> getKeys(JsonNode jsonNode) {
        Iterator<String> fieldNames = jsonNode.fieldNames();
        Set<String> keys = new java.util.HashSet<>();
        while (fieldNames.hasNext()) {
            keys.add(fieldNames.next());
        }
        return keys;
    }
    
    /** decompressGzipFile
    * Descomprime el archivo gz
   	*@autor Valeria Fernandez
   	*@param
   	*	-gzippedFilePath (Cadena): Ruta local del archivo comprimido en gz
   	*@return decompressedFile (File): Ruta absoluta local del archivo descomprimido
   	*@date 27/Febrero/2025
   	*/
    public static String decompressGzipFile(String gzippedFilePath) throws IOException {
    	// Crear los objetos File para el archivo comprimido y el archivo de salida
        File gzippedFile = new File(gzippedFilePath);
        // El archivo descomprimido tendrá el mismo nombre pero sin la extensión ".gz"
        File decompressedFile = new File(gzippedFile.getParent(), gzippedFile.getName().replace(".gz", ""));

        // Abrir los streams de entrada y salida
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(gzippedFile));
             FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile)) {

            // Buffer para la lectura/escritura
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Leer y escribir el archivo descomprimido
            while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            // Retornar el path completo del archivo descomprimido
            return decompressedFile.getAbsolutePath();
        }
    }
    
    /** containsValidDate
    * Verifica que el nodo tenga un fecha válida, conforme al formato yyyymmddHHmmss
   	*@autor Valeria Fernandez
   	*@param
   	*	-str (Cadena): Fecha que se va a evaluar
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    public static boolean containsValidDate(String str) {
        // Expresión regular para buscar una cadena en el formato yyyymmddHHmmss en cualquier parte del texto
        String pattern = "(\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}\\d{2})";
        
        // Crear el objeto Pattern
        Pattern r = Pattern.compile(pattern);
        
        // Crear un objeto Matcher para buscar el patrón en la cadena
        Matcher m = r.matcher(str);
        
        // Buscar una coincidencia
        if (m.find()) {
            String dateString = m.group(1);
            
            // Intentar analizar la fecha y hora utilizando DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            try {
                // Intentar parsear la fecha
                LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                return true; // La fecha y hora es válida
            } catch (DateTimeParseException e) {
                return false; // La fecha y hora no es válida
            }
        }
        
        // Si no se encuentra un patrón válido
        return false;
    }

    /** isConsecutive
    * Valida si los número del arreglo son consecutivos, empezando en 1
   	*@autor Valeria Fernandez
   	*@param
   	*	-arr ((List<Integer>): Lista de enteros, que se evalua si son consecutivos
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    public static boolean isConsecutive(List<Integer> arr) {
        if (arr.isEmpty() || arr.get(0) != 1) {
            return false;
        }

        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) != arr.get(i - 1) + 1) {
                return false;
            }
        }

        return true;
    }
    
    /** validateJsonCamposLisp
    * Verifica que los nodos del servicio lisp sean los correctos de las TR
   	*@autor Valeria Fernandez
   	*@param
   	*	-node (JsonNode): El valor del body que se va a enviar en el servicio (lisp, outbound, ack) convertido a JsonNode
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    public static boolean validateJsonCamposLisp(JsonNode node) {
        if (node.isObject()) {
            Iterator<Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                JsonNode fieldValue = field.getValue();

                // Validaciones según el nombre del campo
                if (!validateFieldLisp(fieldName, fieldValue)) {
                    return false;
                }

                // Recursividad para validar objetos anidados
                if (!validateJsonCamposLisp(fieldValue)) {
                    return false;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode arrayElement : node) {
                // Validar cada elemento del array
                if (!validateJsonCamposLisp(arrayElement)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    /** validateFieldLisp
    * Valida que los campos del servicio lisp sean correctos conforme a su nombre, las validaciones vienen de las TR 
   	*@autor Valeria Fernandez
   	*@param
   	*	-fieldName (Cadena): El nombre del nodo en cadena, determina que condiciones se evaluan
   	*	-fieldValue (JsonNode): El valor del nodo, se evalua para confirmar tenga el valor correcto
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    private static boolean validateFieldLisp(String fieldName, JsonNode fieldValue) {
        switch (fieldName) {
            case "code":
                // Verificar que "code" sea un valor alfanumérico
                if (!fieldValue.isTextual() || !fieldValue.asText().matches("[a-zA-Z0-9]+")) {
                    System.out.println("El campo 'code' debe ser alfanumérico.");
                    return false;
                }
                break;

            case "message":
                // Verificar que "message" sea un texto
                if (!fieldValue.isTextual()) {
                    System.out.println("El campo 'message' debe ser un texto.");
                    return false;
                }
                break;

            case "maxFiles":
            	// Verificar que "maxFiles" sea cadena numérica
            	if (!fieldValue.isTextual() || !fieldValue.asText().matches("\\d{1,3}")) {
            	    System.out.println("El campo 'size' debe ser un número de hasta 3 dígitos.");
            	    return false;
            	}
                break;

            case "documents":
                // Verificar que "documents" sea un array
                if (fieldValue.isArray()) {
                    for (JsonNode document : fieldValue) {
                        // Verificar que cada documento tenga el campo "name" con extensión
                        if (!validateDocument(document)) {
                            return false;
                        }
                    }
                }
                break;

            case "size":
                // Verificar que "size" sea cadena numérica
            	if (!fieldValue.isTextual() || !fieldValue.asText().matches("\\d{1,5}")) {
            	    System.out.println("El campo 'size' debe ser un número de hasta 5 dígitos.");
            	    return false;
            	}
                break;

            default:
                // No validamos otros campos
                break;
        }

        return true;
    }
    
    /** validateJsonCamposOutbound
    * Verifica que los nodos del servicio outbound sean los correctos de las TR
   	*@autor Valeria Fernandez
   	*@param
   	*	-node (JsonNode): El valor del body que se va a enviar en el servicio (lisp, outbound, ack) convertido a JsonNode
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    public static boolean validateJsonCamposOutbound(JsonNode node) {
        if (node.isObject()) {
            Iterator<Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                JsonNode fieldValue = field.getValue();

                // Validaciones según el nombre del campo
                if (!validateFieldOutbound(fieldName, fieldValue)) {
                    return false;
                }

                // Recursividad para validar objetos anidados
                if (!validateJsonCamposOutbound(fieldValue)) {
                    return false;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode arrayElement : node) {
                // Validar cada elemento del array
                if (!validateJsonCamposOutbound(arrayElement)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    /** validateFieldOutbound
    * Valida que los campos del servicio outbound sean correctos conforme a su nombre, las validaciones vienen de las TR
   	*@autor Valeria Fernandez
   	*@param
   	*	-fieldName (Cadena): El nombre del nodo en cadena, determina que condiciones se evaluan
   	*	-fieldValue (JsonNode): El valor del nodo, se evalua para confirmar tenga el valor correcto
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    private static boolean validateFieldOutbound(String fieldName, JsonNode fieldValue) {
        switch (fieldName) {
            case "code":
                // Verificar que "code" sea un valor alfanumérico
                if (!fieldValue.isTextual() || !fieldValue.asText().matches("[a-zA-Z0-9]+")) {
                    System.out.println("El campo 'code' debe ser alfanumérico.");
                    return false;
                }
                break;

            case "message":
                // Verificar que "message" sea un texto
                if (!fieldValue.isTextual()) {
                    System.out.println("El campo 'message' debe ser un texto.");
                    return false;
                }
                break;

            case "documents":
                // Verificar que "documents" sea un array
                if (fieldValue.isArray()) {
                    for (JsonNode document : fieldValue) {
                        // Verificar que cada documento tenga el campo "name" con extensión
                        if (!validateDocument(document)) {
                            return false;
                        }
                    }
                }
                break;
            case "status":
                // Verificar que "status" sea un solo carácter
                if (!fieldValue.isTextual() || fieldValue.asText().length() != 1) {
                    System.out.println("El campo 'status' debe ser un solo carácter.");
                    return false;
                }
                break;

            default:
                // No validamos otros campos
                break;
        }

        return true;
    }
    
    /** validateJsonCamposAck
    * Verifica que los nodos del servicio ack sean los correctos de las TR
   	*@autor Valeria Fernandez
   	*@param
   	*	-node (JsonNode): El valor del body que se va a enviar en el servicio (lisp, outbound, ack) convertido a JsonNode
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    public static boolean validateJsonCamposAck(JsonNode node) {
        if (node.isObject()) {
            Iterator<Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                JsonNode fieldValue = field.getValue();

                // Validaciones según el nombre del campo
                if (!validateFieldAck(fieldName, fieldValue)) {
                    return false;
                }

                // Recursividad para validar objetos anidados
                if (!validateJsonCamposAck(fieldValue)) {
                    return false;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode arrayElement : node) {
                // Validar cada elemento del array
                if (!validateJsonCamposAck(arrayElement)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    /** validateFieldAck
    * Valida que los campos del servicio ack sean correctos conforme a su nombre, las validaciones vienen de las TR
   	*@autor Valeria Fernandez
   	*@param
   	*	-fieldName (Cadena): El nombre del nodo en cadena, determina que condiciones se evaluan
   	*	-fieldValue (JsonNode): El valor del nodo, se evalua para confirmar tenga el valor correcto
   	*@return (boolean): Regresa true o false
   	*@date 27/Febrero/2025
   	*/
    private static boolean validateFieldAck(String fieldName, JsonNode fieldValue) {
        switch (fieldName) {
            case "code":
                // Verificar que "code" sea un valor alfanumérico
                if (!fieldValue.isTextual() || !fieldValue.asText().matches("[a-zA-Z0-9]+")) {
                    System.out.println("El campo 'code' debe ser alfanumérico.");
                    return false;
                }
                break;

            case "message":
                // Verificar que "message" sea un texto
                if (!fieldValue.isTextual()) {
                    System.out.println("El campo 'message' debe ser un texto.");
                    return false;
                }
                break;

            default:
                // No validamos otros campos
                break;
        }

        return true;
    }
}
