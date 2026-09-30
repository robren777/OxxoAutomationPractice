package WM.om;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.monitor.query.LogsQueryClient;
import com.azure.monitor.query.LogsQueryClientBuilder;
import com.azure.monitor.query.models.LogsQueryResult;
import com.azure.monitor.query.models.LogsTableRow;
import com.azure.monitor.query.models.QueryTimeInterval;
import com.azure.resourcemanager.datafactory.DataFactoryManager;
import com.azure.resourcemanager.datafactory.models.RunFilterParameters;
import com.azure.resourcemanager.datafactory.models.RunQueryFilter;
import com.azure.resourcemanager.datafactory.models.RunQueryFilterOperand;
import com.azure.resourcemanager.datafactory.models.RunQueryFilterOperator;
import com.azure.resourcemanager.datafactory.models.TriggerResource;
import com.azure.resourcemanager.datafactory.models.TriggerRun;
import com.azure.resourcemanager.datafactory.models.TriggerRunsQueryResponse;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import WM.util.GlobalVariables;
import io.restassured.RestAssured;
import modelo.TestCase;
import reactor.core.publisher.Mono;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class PR63_CLOUD {
	static TestCase testCase;
	Process process = null;
	String baseURLToken = "";
	ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);
    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("^[a-z]+([A-Z][a-z]*)*$");
	
	public void setBaseURL(String baseURLTokenTestdata){
		baseURLToken = baseURLTokenTestdata;
	}
	
	public PR63_CLOUD(TestCase testCase) {
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
	
	
	/** getTypeCampoQuery
	* Obtiene el tipo de dato del campo indicado en BD Azure, especificar nombre del campo
	*@autor Valeria Fernandez
	*@param
	*	-table (Cadena): La tabla en la que se busca el field (campo)
	*	-field (Cadena): Campo de BD del que se quiere saber el dataType
	*	-DB_JDBC_AZURE (Cadena): Jdbc de BD
	*	-dataType (Cadena): Tipo de dato del campo
	*@return esTipoCorrecto (boolean): true or false
	*@date 27/Febrero/2025
	*/
	public boolean getTypeCampoQuery(String table, String field, String DB_JDBC_AZURE, String dataType) throws Exception {
	    try {
	        Connection Conexion = DriverManager.getConnection(DB_JDBC_AZURE);
	        DatabaseMetaData meta = Conexion.getMetaData();
	        ResultSet rs = meta.getColumns(null, "dbo", table, field);

	        boolean esTipoCorrecto = false;

	        if (rs.next()) {
	            String tipoSQL = rs.getString("TYPE_NAME").toUpperCase(); // Ejemplo: DECIMAL, INT
	            System.out.println("Tipo SQL esperado: " + dataType);
	            testCase.addTextEvidenceCurrentStep("Tipo SQL esperado: " + dataType);
	            System.out.println("Columna: " + field + ", Tipo en BD: " + tipoSQL);
	            testCase.addTextEvidenceCurrentStep("Columna: " + field + ", Tipo en BD: " + tipoSQL);

	            esTipoCorrecto = tipoSQL.equals(dataType.toUpperCase());
	        } else {
	            System.out.println("No se encontró la columna " + field + " en la tabla " + table);
	            testCase.addTextEvidenceCurrentStep("No se encontró la columna " + field + " en la tabla " + table);
	            esTipoCorrecto = false; // o true, según tu lógica
	        }

	        rs.close();
	        return esTipoCorrecto;

	    } catch (Exception e) {
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
    
    /** agregarPromocionODS
    * Genera un nuevo archivo ods (Submmited) agregando los id de las promociones, este es que se sube a rms16 y sube las promociones 
   	*@autor Valeria Fernandez
   	*@param
   	*	-inputFilePath (Cadena): Ruta del archivo original que se modifica en la función
   	*	-outputFilePath (Cadena): Ruta del archivo resultante después de la modificación
   	*	-promotionId1 (Cadena): Id de la promoción 1 (se agrega al archivo resultante)
   	*	-promotionId2 (Cadena): Id de la promoción 2 (se agrega al archivo resultante)
   	*@return Vacío
   	*@date 24/Marzo/2025
   	*/
    public static void agregarPromocionODS(String inputFilePath, String outputFilePath, String promotionId1, String promotionId2) {
        try {
            // Cargar el archivo ODS existente
        	OdfSpreadsheetDocument  document = OdfSpreadsheetDocument.loadDocument(inputFilePath);
        	OdfTable table = document.getTableByName("Change_promo_data"); // Use getTableByIndex() for OdfSpreadsheetDocument

            OdfTableCell cell = table.getCellByPosition(0,1);//A2
            cell.setStringValue(promotionId1);
            
            OdfTableCell cell2 = table.getCellByPosition(0,2);//A3
            cell2.setStringValue(promotionId2);

            // Guardar el archivo con los cambios
            document.save(outputFilePath);
            System.out.println("Archivo actualizado exitosamente: " + outputFilePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al modificar el archivo ODS.");
        }
    }
    
    /** CambioApproved
    * Genera un nuevo archivo ods (Approved) agregando los id de las promociones, este es que se sube a rms16 y aprueba las promociones
   	*@autor Valeria Fernandez
   	*@param
   	*	-inputFilePath (Cadena): Ruta del archivo original que se modifica en la función
   	*	-outputFilePath (Cadena): Ruta del archivo resultante después de la modificación
   	*	-promotionId1 (Cadena): Id de la promoción 1 (se agrega al archivo resultante)
   	*	-promotionId2 (Cadena): Id de la promoción 2 (se agrega al archivo resultante)
   	*@return Vacío
   	*@date 24/Marzo/2025
   	*/
    public static void CambioApproved(String inputFilePath, String outputFilePath, String promotionId1, String promotionId2) {
        try {
            // Cargar el archivo ODS existente
        	OdfSpreadsheetDocument  document = OdfSpreadsheetDocument.loadDocument(inputFilePath);
        	OdfTable table = document.getTableByName("Change_promo_data"); // Use getTableByIndex() for OdfSpreadsheetDocument

        	OdfTableCell cell = table.getCellByPosition(0,1);//A2
            cell.setStringValue(promotionId1);
            
            OdfTableCell cell2 = table.getCellByPosition(0,2);//A3
            cell2.setStringValue(promotionId2);

            // Guardar el archivo con los cambios
            document.save(outputFilePath);
            System.out.println("Archivo actualizado exitosamente: " + outputFilePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al modificar el archivo ODS.");
        }
    }
    
    /** runPR63_CLOUD
    * Ejecuta el proceso de runPromotionCloud, dependiendo del valor de MockAPI (Y/N) ejecuta el workflow de Azure o el job de Control M
   	*@autor Valeria Fernandez
   	*@param
   	*	-MockAPI (Cadena): Tiene el valor de Y o N, lo agrega al body del request
   	*	-API_StartCopy (Cadena): Endpoint base de la API
   	*	-fullUrl (Cadena): Full Endpoint de la API que llama al workflow
   	*@return statusCode == 200 (boolean): Si no es =200 regresa false
   	*@date 24/Marzo/2025
   	*/
    public static boolean runPR63_CLOUD(String MockAPI, String API_StartCopy, String fullUrl) {
        try {
            RestAssured.baseURI = API_StartCopy;

            String reqBody = "{"
                    + "\"UseMock\":\"N\""
                    + "}";

            testCase.addTextEvidenceCurrentStep("API Request de workflow WF_PRM_StartCopy: " + fullUrl + "\r\n" + reqBody);
            System.out.print(fullUrl + reqBody + "\r\n");

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(reqBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            String responseBody = response.body();

            System.out.println("Código de estado: " + statusCode);
            System.out.println("Cuerpo de la respuesta: " + responseBody);

            testCase.addTextEvidenceCurrentStep("API Response de workflow WF_PRM_StartCopy: " + responseBody);

            return statusCode == 200;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al ejecutar el job/workflow");
            return false;
        }
    }

    
    /** reintentarConexion
    * Reintenta la conexión de la Bd hasta 5 veces
	*@autor Valeria Fernandez
	*@param Vacío
	*@return dbFCDASPFR (SQLUtil): Es la conexión a la BD
	*@date 24/Marzo/2025
	*/
    public static SQLUtil reintentarConexion() {
		utils.sql.SQLUtil dbFCDASPFR = null;
		int maxAttempts = 5;
		int retryInterval = 1000; // 1 second
		
    	//Reintenta conexión hasta 5 veces de dbFCDASPFR, porque es inestable su conexión
		 for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			 try {
				 dbFCDASPFR = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO,GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_DASPFR_MEX_MULTIFORMATO);
				 System.out.println("Conexión completada en el intento " + attempt); // Perform database operations here 
				 break; // Exit the loop if connection is successful 
				 } catch (Exception e) {
					 System.out.println("Conexión intento " + attempt + " fallado: " + e.getMessage()); 
					 if (attempt < maxAttempts) {
						 try {
							 Thread.sleep(retryInterval);
							 } 
						 catch (InterruptedException ie) {
							 Thread.currentThread().interrupt();
							 } 
						} else {
							System.out.println("Todos los intentos de conexión fallaron"); 
						} 
				 } 
			 }
		 return dbFCDASPFR;
    }
    
    /** loginDataFactoryAzure
	* Login al recurso de Azure para consultar Data Factory
	*@autor Valeria Fernandez
	*@param
	*	-clientId (Cadena): clientId de recurso en Azure
	*	-tenantId (Cadena): tenantId de recurso en Azure
	*	-clientSecret (Cadena): clientSecret de recurso en Azure 
	*@return tokenCredential (TokenCredential): token de API para autenticación Azure
	*@date 27/Mayo/2025
	*/
	public TokenCredential loginDataFactoryAzure(String clientId, String tenantId, String clientSecret) throws Exception {
		try {
			 // Autenticación básica
	        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
	                .clientId(clientId)
	                .tenantId(tenantId)
	                .clientSecret(clientSecret)
	                .build();

	        System.out.println("✅ La conexión fue exitosa");
	        testCase.addTextEvidenceCurrentStep("La conexión a Data Factory fue exitosa");

	        // Scope requerido para Azure Resource Manager
	        String[] scopes = new String[]{"https://management.azure.com/.default"};

	        // Obtener el AccessToken
	        AccessToken accessToken = getAccessToken(credential, scopes);

	        // Crear TokenCredential manual desde AccessToken
	        TokenCredential tokenCredential = new TokenCredential() {
	            @Override
	            public Mono<AccessToken> getToken(TokenRequestContext request) {
	                return Mono.just(accessToken);
	            }
	        };
		        
		    return tokenCredential;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/** consultaTriggerDFAzure
	* Consulta última ejecución en Data Factory de trigger específicado
	*@autor Valeria Fernandez
	*@param
	*	-tokenCredential (TokenCredential): token de autenticación en Azure
	*	-workspaceId (Cadena): Id de workspaceId en Azure que consulta Logs Analitycs de recurso en Azure
	*	-tenantId (Cadena): tenantId de recurso en Azure
	*	-resourceGroup (Cadena): Nombre de recurso en Azure
	*	-dataFactoryName (Cadena): Nombre de Data Factory en Azure 
	*	-triggerName (Cadena): Nombre del trigger en Data Factory Azure
	*@return dentroDe24Horas (boolean): true o false
	*@date 27/Mayo/2025
	*/
	public boolean consultaTriggerDFAzure(TokenCredential tokenCredential, String subscriptionId, String tenantId, String resourceGroup, String dataFactoryName, String triggerName) throws Exception {
	    try {
	        AzureProfile profile = new AzureProfile(tenantId, subscriptionId, AzureEnvironment.AZURE);

	        DataFactoryManager dfManager = DataFactoryManager.authenticate(tokenCredential, profile);

	        TriggerResource trigger = dfManager.triggers().get(resourceGroup, dataFactoryName, triggerName);
	        System.out.println("Trigger: " + trigger.name());

	        RunFilterParameters filterParams = new RunFilterParameters()
	            .withLastUpdatedAfter(OffsetDateTime.now().minusDays(7))
	            .withLastUpdatedBefore(OffsetDateTime.now())
	            .withFilters(List.of(
	                new RunQueryFilter()
	                    .withOperand(RunQueryFilterOperand.TRIGGER_NAME)
	                    .withOperator(RunQueryFilterOperator.EQUALS)
	                    .withValues(List.of(triggerName))
	            ));

	        TriggerRunsQueryResponse response = dfManager.triggerRuns().queryByFactory(resourceGroup, dataFactoryName, filterParams);
	        List<TriggerRun> triggerRuns = response.value();

	        if (triggerRuns == null || triggerRuns.isEmpty()) {
	            System.out.println("No se encontraron ejecuciones recientes del trigger.");
	            return false;
	        }

	        TriggerRun latestRun = triggerRuns.stream()
	            .max(Comparator.comparing(TriggerRun::triggerRunTimestamp))
	            .orElse(null);

	        if (latestRun != null) {
	            OffsetDateTime timestamp = latestRun.triggerRunTimestamp();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            String timestampStr = timestamp.format(formatter);

	            OffsetDateTime now = OffsetDateTime.now();
	            OffsetDateTime hace24Horas = now.minusHours(24);

	            System.out.println("Última ejecución:");
	            System.out.println(" - Estado: " + latestRun.status());
	            System.out.println(" - TimeStamp: " + timestampStr);
	            testCase.addTextEvidenceCurrentStep("Última ejecución:");
	            testCase.addTextEvidenceCurrentStep("Estado: " + latestRun.status());
	            testCase.addTextEvidenceCurrentStep("TimeStamp: " + timestampStr);

	            boolean dentroDe24Horas = timestamp.isAfter(hace24Horas);

	            if (dentroDe24Horas) {
	                System.out.println("La última ejecución fue dentro de las últimas 24 horas");
	            } else {
	                System.out.println("La última ejecución NO fue dentro de las últimas 24 horas");
	            }

	            return dentroDe24Horas;
	        } else {
	            System.out.println("No se encontraron ejecuciones recientes del trigger.");
	            return false;
	        }

	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
}
