package WM.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.http.params.CoreConnectionPNames;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import WM.apigalaxy.models.ComparisonResult;
import WM.apigalaxy.models.Result;
import WM.apigalaxy.models.Store;
import WM.apigalaxy.models.StoreSpace;
import WM.apigalaxy.models.StoresSpacesResponse;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import modelo.TestCase;
import net.snowflake.client.jdbc.SnowflakeSQLException;
import utils.password.PasswordUtil;

/**
 * UTILIDADES GENERADAS PARA EL PROYECTO API GALAXY MVP 6, SE UTILIZA EN LOS
 * SIGUIENTES PACKAGES: WM.apiGalaxy.*
 * 
 * @author guillermo.garcia@gtim.mx Fecha fin del proyecto: 08/06/2026
 */

public class ApiGalaxyUtils {

    private String baseUrl;
    private String apiKey;
    private String apiUser;
    private String apiPass;
    private String tokenUrl;
    private static final String CONTEN_TTYPE = "application/json";
    private static final String HCONTENT_TYPE = "Content-Type";
    private static final String HTRACKINGID = "Tracking-Id";
    private static final String HCHANNEL = "Channel-Id";
    private static final String HCOUNTRY = "Country-Code";
    private static final String HLANGUAGE = "Language";
    private static final String HAUTHORIZATION = "Authorization";
    private static final String HBEARER = "Bearer ";
    private static final String HGATE = "x-gateway-apikey";
    private static final String HACCEPT = "Accept";
    private static final String PAGE_SIZE = "pageSize";
    private static final String UPDATED_DATE = "updatedDate";
    private static final String ENDPOINT_STORES = "/catalog/stores";
    private static final String ENDPOINT_CALENDAR = "/promotional-calendars";
    private static final String ENDPOINT_RENTS = "/promotional-rents";
    private static final String ENDPOINT_AGREEMENTS = "/agreements";
    private static final String BASE_URL_IMA = "https://api-stg.oxxo.io/exp-ima-space-leasing/api/v1";
    private String apiKeyIma = PasswordUtil.decryptPassword(GlobalVariables.API_KEY_IMA_QA);
    private String apiUserIma = PasswordUtil.decryptPassword(GlobalVariables.API_USER_IMA_QA);
    private String apiPassIma = PasswordUtil.decryptPassword(GlobalVariables.API_PASS_IMA_QA);
    private static final String CURSOR = "cursor";
    private static final String LIMIT = "limit";
    private static final String ENDPOINT_SPACE = "/catalog/stores-spaces";
    private static final String ENDPOINT_FOLIOS = "/agreement/folios";
    private static final String PERIOD_ID = "periodsId";
    private static final String PERIOD_NAME = "periodName";
    private static final String ACTIVITY_NAME = "activityName";
    private static final String MARKETPLACE_ID = "marketplaceId";
    private static final String NO_ENVIAR = "no enviar";
    private static final String CREATED_DATE = "createdDate";
    private static final String RETURN_LATEST = "returnLatest";
    private static final String KEY_DESC = " con la  key ";
    private static final String ID_DESC = "Para el id ";
    private static final String JSON_DESC = " : valor json = ";
    private static final String SNOW_DESC = ", valor snowflake = ";
    private static final String IGUALES = "IGUALES";
    private static final String DIFERENTES = "DIFERENTES";
    private static final String ESTADO_DESC = ", estado = ";
    private static final String PERIOD_NAME_ROW = "PERIOD_NAME";
    private static final String FINAL_DATE = "finalDate";
    private static final String INITIAL_DATE = "initialDate";
    private static final String POSITION = "position";
    private static final String PROJECTION_PERCENTAGE = "projectionPercentage";
    private static final String SHORT_PERIOD = "isShortPeriods";
    private static final String YEAR_CALENDAR = "yearCalendar";
    private static final String N_DAYS = "nDays";
    private static final String N_DATE = "nDate";
    private static final String CODE_400_MESS =  "X400000";
    private static final String MESSAGE = "message";
    private static final String RENT_FOLIO = "rentFolio";
    private static final String ERROR = "Error: ";
    private static final String REGION = "REGION";
    private static final String PLAZA = "PLAZA";
    private static final String ID_SEGMENTO_MAESTRO = "ID_SEGMENTO_MAESTRO";
    private static final String SEGMENTO_MAESTRO = "SEGMENTO_MAESTRO";
    private static final String POTENCIAL = "POTENCIAL";
    private static final String PERMISO_ALCOHOL = "PERMISO_ALCOHOL";
    private static final String ORDER_ID = "ORDER_ID";
    private static final String TIPO_DELTA = "TIPO_DELTA";
    private static final String PROVIDER_NAME = "providerName";
    private static final String PROVIDER_ID = "providerId";
    private static final String SPACE_ID = "spaceId";
    private static final String PACKAGE_ID = "packageId";
    private static final String SUB_PACKAGE_ID = "subPackageId";
    private static final String SUPER_GRUPO = "superGroup";
    private static final String OXXO_CATEGORIES = "oxxoCategories";
    private static final String EMAIL_CATEGORY = "emailCategory";
    private static final String COVERAGE = "coverage";
    private static final String ITEMS = "items";
    private static final String POTENTIALS = "potentials";
    private static final String MASTER_SEG = "masterSegments";
    private static final String ALCOHOL_PERMITS = "alcoholPermits";
    private static final String EMPOWERED_PLAZAS = "empoweredPlazas";
    private static final String CREATED_DATE_VAR = "CREATED_DATE";
    private static final String LAST_UPDATED_DATE = "LAST_UPDATED_DATE";
    private static final String SEGURIDAD = "seguridad";
    private static final String INITIAL_PERIOD =  "initialPeriod";
    private static final String FINAL_PERIOD =  "finalPeriod";
    private static final String ACTION_AGREEMENT = "actionAgreement";
    private static final String INVOICE_CONCEPT = "invoiceConcept";
    private static final String BILLING_METHOD = "billingMethod";
    private static final String PAYMENT_METHOD = "paymentMethod";
    private static final String TAX_CODE = "taxCode";
    private static final String MECHANICS = "mechanics";
    private static final String BONUS_DESC = "bonusDescription";
    private static final String CALENDAR_BUSINESS = "calendarBusiness";
    private static final String CUSTOMER_PROVIDER_ID = "customerProviderId";
    private static final String DENAIED_OP = "The operation was denied, if the problem persists contact your administrator or try again later.";
    private static final String ENVIO_RES = "Para el envio se obtiene la respuesta: ";
    private static final String ENVIO_DESC = "Para el envio se obtiene el code ";
    private static final String ERROR_MESSAGE = "The client has issued a malformed or illegal request.";
    private static final String JSONS_URL = "\\Config\\Input\\DataProviders\\WM\\apiGalaxy\\jsons\\rentas\\";
    private static final String JSONS_URL_CONVENIOS = "\\Config\\Input\\DataProviders\\WM\\apiGalaxy\\jsons\\convenios\\";
    private static final String BONUS_TYPE = "bonusType";
    private static final String BONUS_TYPE_DESC = "bonusTypeDescription";
    private static final String NEGOTATION_TYPE = "negotiationType";
    private static final String FREQUENCY = "frequency";
    private static final String CONTRIBUTION_TYPE = "contributionType";
    private static final String CONTRIBUTION_AMOUNT = "contributionAmount";
    private static final String SUPER_GROUP_ID = "superGroupId";
    private static final String DIVISION_ID = "divisionId";
    private static final String GROUP_NUMBER_ID = "groupNumberId";
    private static final String DEPARTMENT_ID = "departmentId";
    private static final String USER_CATEGORY_ID = "userCategoryId";
    private static final String CUSTOMER_RFC = "customerRfc";
    private static final String REGIONS = "regions";
    private static final String DIFF_ESTADO_DESC = ", estado = DIFERENTES";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String CAMPO_DESC = "Se encuentra el campo: ";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String DATA_TYPE_FOUND_DESC = "El tipo de dato encontrado es: ";
    private static final String CAMPO_NO_ENCONTRADO_DESC = "No Se encuentra el campo: ";
    
    private TestCase testCase = null;
    private String currentDir = System.getProperty("user.dir");

    /**
     * Constructor de la clase que permite cambiar el uri de la api dependiendo el
     * ambiente de ejecucion
     * 
     * @param ambiente es un string que si se envia 'QA' utiliza los valores del
     *                 enpoint del ambiente de qa si se envia cualquier otra cosa
     *                 utiliza los valores de dev
     * @date 20/05/2026
     * @author Guillermo Garcia
     */
    public ApiGalaxyUtils(String ambiente) {
	baseUrl = ambiente.equalsIgnoreCase("QA")
		? PasswordUtil.decryptPassword(GlobalVariables.BASE_URL_SPACELEASING_QA)
		: PasswordUtil.decryptPassword(GlobalVariables.BASE_URL_SPACELEASING_DEV);
	apiKey = ambiente.equalsIgnoreCase("QA") ? PasswordUtil.decryptPassword(GlobalVariables.API_KEY_STORES_QA)
		: PasswordUtil.decryptPassword(GlobalVariables.API_KEY_STORES_DEV);
	apiUser = ambiente.equalsIgnoreCase("QA")
		? PasswordUtil.decryptPassword(GlobalVariables.API_USER_SPACElEASING_QA)
		: PasswordUtil.decryptPassword(GlobalVariables.API_USER_SPACElEASING_DEV);
	apiPass = ambiente.equalsIgnoreCase("QA")
		? PasswordUtil.decryptPassword(GlobalVariables.API_PASS_SPACElEASING_QA)
		: PasswordUtil.decryptPassword(GlobalVariables.API_PASS_SPACElEASING_DEV);
	tokenUrl = ambiente.equalsIgnoreCase("QA")
		? "https://api-stg.oxxo.io/invoke/pub.apigateway.oauth2/getAccessToken"
		: "https://api-dev.oxxo.io/invoke/pub.apigateway.oauth2/getAccessToken";
    }

    /**
     * Constructor de la clase que permite cambiar el uri de la api dependiendo el
     * ambiente de ejecucion
     * 
     * @param ambiente es un string que si se envia 'QA' utiliza los valores del
     *                 enpoint del ambiente de qa si se envia cualquier otra cosa
     *                 utiliza los valores de dev
     * @date 20/05/2026
     * @author Guillermo Garcia
     */
    public ApiGalaxyUtils(String ambiente, TestCase caster) {
	baseUrl = ambiente.equalsIgnoreCase("QA")
		? PasswordUtil.decryptPassword(GlobalVariables.BASE_URL_SPACELEASING_QA)
		: PasswordUtil.decryptPassword(GlobalVariables.BASE_URL_SPACELEASING_DEV);
	apiKey = ambiente.equalsIgnoreCase("QA") ? PasswordUtil.decryptPassword(GlobalVariables.API_KEY_STORES_QA)
		: PasswordUtil.decryptPassword(GlobalVariables.API_KEY_STORES_DEV);
	apiUser = ambiente.equalsIgnoreCase("QA")
		? PasswordUtil.decryptPassword(GlobalVariables.API_USER_SPACElEASING_QA)
		: PasswordUtil.decryptPassword(GlobalVariables.API_USER_SPACElEASING_DEV);
	apiPass = ambiente.equalsIgnoreCase("QA")
		? PasswordUtil.decryptPassword(GlobalVariables.API_PASS_SPACElEASING_QA)
		: PasswordUtil.decryptPassword(GlobalVariables.API_PASS_SPACElEASING_DEV);
	tokenUrl = ambiente.equalsIgnoreCase("QA")
		? "https://api-stg.oxxo.io/invoke/pub.apigateway.oauth2/getAccessToken"
		: "https://api-dev.oxxo.io/invoke/pub.apigateway.oauth2/getAccessToken";
	testCase = caster;
    }

    public String getTokenUrl() {
	return tokenUrl;
    }

    public String getBaseUrl() {
	return baseUrl;
    }

    public String getBaseUrlIma() {
	return BASE_URL_IMA;
    }
    /**
     * Envía una petición para consultar stores spaces y valida los valores
     * retornados en el metadata de la respuesta.
     *
     * @author Guillermo Garcia
     * @param limit                      Número máximo de registros a consultar
     * @param updatedDate                Fecha de actualización utilizada como
     *                                   filtro
     * @param cursor                     Cursor utilizado para la paginación
     * @param expectedLimit              Valor esperado del límite retornado
     * @param expectedReturnedItems      Valor esperado de elementos retornados
     * @param expectedHasMore            Valor esperado del indicador hasMore
     * @param expectedNextCursor         Valor esperado del siguiente cursor
     * @param expectedStoresSpacesStatus Valor esperado del estatus de storesSpaces
     * @return Resultado de la validación con descripción y bandera de éxito
     * @date 20/05/2026
     *Nota para el validador se utilizan 8 params porque asi funciona la api
     */
    public Result sendStoresSpacesAndValidateMetadata(int limit, String updatedDate, int cursor, int expectedLimit,
	    int expectedReturnedItems, boolean expectedHasMore, Integer expectedNextCursor,
	    boolean expectedStoresSpacesStatus) {

	String description = null;
	boolean flag = true;

	Response response = getStoresSpacesCursor(cursor, limit, updatedDate);
	// Se mapea el response en tipo de dato
	StoresSpacesResponse storesSpaceResponse = response.as(StoresSpacesResponse.class);

	int realLimit = storesSpaceResponse.getLimit();
	flag = realLimit == expectedLimit ? flag : false;
	int realReturnedItems = storesSpaceResponse.getReturnedItems();
	flag = realReturnedItems == expectedReturnedItems ? flag : false;
	boolean realHasMore = storesSpaceResponse.isHasMore();
	flag = Objects.equals(realHasMore, expectedHasMore) ? flag : false;
	int realNextCursor = storesSpaceResponse.getNextCursor();
	boolean realStoresSpacesStatus = !storesSpaceResponse.getStoresSpaces().isEmpty();
	flag = realStoresSpacesStatus == expectedStoresSpacesStatus ? flag : false;

	String cursorTexto = cursor > 0 ? " cursor: " + cursor : " Cursor no se envia, ";
	String limitTexto = limit > 0 ? " limit: " + limit : " Limit no se envia, ";

	description = "Se envian los valores" + limitTexto + " updatedDate: " + updatedDate + "y " + cursorTexto
		+ " \r\n" + "Se obtienen los valores del metadata limit: " + realLimit + " returnedItems: "
		+ realReturnedItems + "hasMore: " + realHasMore + "NextCursor: " + realNextCursor
		+ "storesSpaces esta vacio?: " + realStoresSpacesStatus;

	return new Result(description, flag);
    }

    /**
     * Genera una conexión a Snowflake utilizando autenticación mediante clave
     * privada en formato PKCS#8 DER.
     *
     * @author Guillermo Garcia
     * @return Conexión activa hacia Snowflake
     * @date 20/05/2026
     */
    public Connection getSnowflakeConnection()
	    throws SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

	// Ruta al archivo .der (clave privada en formato PKCS#8 DER)
	String privateKeyPath = currentDir + "\\Config\\Input\\DataProviders\\WM\\apiGalaxy\\llaves\\private_key.der";

	// Leer clave privada desde archivo .der
	byte[] keyBytes = readBinaryFile(privateKeyPath);
	PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
	KeyFactory kf = KeyFactory.getInstance("RSA");
	PrivateKey privateKey = kf.generatePrivate(spec);

	// Parámetros de conexión
	String url = "jdbc:snowflake://oxxomexico-oxxoadmin.snowflakecomputing.com/";
	Properties props = new Properties();
	props.put("user", "USR_QA_ECLIPSE");
	props.put("role", "RL_QA_ECLIPSE");
	props.put("warehouse", "WH_QA_ECLIPSE");
	props.put("db", "DBZDEV_MEX_OXXO");
	props.put("schema", "CONS_MKT_PROMOCIONES");
	props.put("privatekey", privateKey);

	return DriverManager.getConnection(url, props);
    }

    /**
     * Lee el contenido binario completo de un archivo, utilizado para cargar claves
     * privadas (.der).
     * 
     * Este método se usa como apoyo para leer la clave privada que autentica la
     * conexión hacia Snowflake mediante certificado.
     * 
     * @author Joaquin Aguilar
     * @param filePath Ruta completa del archivo binario (.der o .pem) a leer.
     * @return Arreglo de bytes que contiene el contenido binario del archivo.
     * @throws IOException Si el archivo no puede leerse completamente o no existe.
     * @date 22/08/2025
     */
    private byte[] readBinaryFile(String filePath) throws IOException {
	File file = new File(filePath);
	if (file.length() > Integer.MAX_VALUE) {
	    throw new IOException("Archivo demasiado grande: " + filePath);
	}
	byte[] data = new byte[(int) file.length()];
	try (FileInputStream fis = new FileInputStream(file)) {
	    int read = fis.read(data);

	    if (read != data.length) {
		throw new IOException("No se pudo leer completamente el archivo: " + filePath);
	    }
	}
	return data;
    }

    /**
     * Obtiene un access token mediante autenticación OAuth2 utilizando el flujo
     * client_credentials.
     *
     * @author Guillermo Garcia
     * @return Token de acceso generado por el servicio OAuth2
     * @date 20/05/2026
     */
    private String getAccessToken() {
	Response response = RestAssured.given().auth().preemptive().basic(apiUser, apiPass)
		.queryParam("grant_type", "client_credentials").contentType(CONTEN_TTYPE).when().post(tokenUrl)
		.then().statusCode(200).extract().response();

	return response.jsonPath().getString("access_token");
    }

    private String getAccessTokenIMA() {
	Response response = RestAssured.given().auth().preemptive().basic(apiUserIma, apiPassIma)
		.queryParam("grant_type", "client_credentials").contentType(CONTEN_TTYPE).when().post(tokenUrl)
		.then().statusCode(200).extract().response();

	return response.jsonPath().getString("access_token");
    }

    /**
     * Realiza una petición HTTP GET enviando headers de autenticación y parámetros
     * de consulta.
     *
     * @author Guillermo Garcia
     * @param baseUrl     URL base del servicio
     * @param endpoint    Endpoint del recurso a consultar
     * @param apiToken    Token de autenticación Bearer
     * @param apiKey      API Key utilizada para el gateway
     * @param queryParams Parámetros de consulta enviados en la petición
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response getRequest(String baseUrl, String endpoint, String apiToken, String apiKey,
	    Map<String, Object> queryParams) {

	return RestAssured.given().baseUri(baseUrl).basePath(endpoint)
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey)
		.queryParams(queryParams).header(HACCEPT, CONTEN_TTYPE).when().get().then().extract()
		.response();
    }

    /**
     * Realiza una petición HTTP GET enviando un body, headers de autenticación y
     * parámetros de consulta.
     *
     * @author Guillermo Garcia
     * @param baseUrl     URL base del servicio
     * @param endpoint    Endpoint del recurso a consultar
     * @param apiToken    Token de autenticación Bearer
     * @param apiKey      API Key utilizada para el gateway
     * @param queryParams Parámetros de consulta enviados en la petición
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response getRequestWithBody(String baseUrl, String endpoint, String apiToken, String apiKey,
	    Map<String, Object> queryParams) {

	return RestAssured.given().baseUri(baseUrl).basePath(endpoint).header(HCONTENT_TYPE, "text/plain").body("test")
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey)
		.queryParams(queryParams)
//	                .header(HACCEPT, "application/xml")
		.header(HACCEPT, CONTEN_TTYPE).when().get().then().extract().response();
    }

    /**
     * Realiza una petición HTTP GET configurando un tiempo de espera mínimo para
     * validar escenarios de timeout en el servicio.
     *
     * @author Guillermo Garcia
     * @param baseUrl     URL base del servicio
     * @param endpoint    Endpoint del recurso a consultar
     * @param apiToken    Token de autenticación Bearer
     * @param apiKey      API Key utilizada para el gateway
     * @param queryParams Parámetros de consulta enviados en la petición
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response getRequest408(String baseUrl, String endpoint, String apiToken, String apiKey,
	    Map<String, Object> queryParams) {
	@SuppressWarnings("deprecation")
	RestAssuredConfig config = RestAssured.config()
		.httpClient(HttpClientConfig.httpClientConfig().setParam(CoreConnectionPNames.SO_TIMEOUT, 1));
	return RestAssured.given().config(config).baseUri(baseUrl).basePath(endpoint)
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey)
		.queryParams(queryParams).header(HACCEPT, CONTEN_TTYPE).when().get().then().extract()
		.response();
    }

    /**
     * Realiza una petición HTTP GET enviando headers de autenticación.
     *
     * @author Guillermo Garcia
     * @param baseUrl  URL base del servicio
     * @param endpoint Endpoint del recurso a consultar
     * @param apiToken Token de autenticación Bearer
     * @param apiKey   API Key utilizada para el gateway
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response getRequest(String baseUrl, String endpoint, String apiToken, String apiKey) {

	return RestAssured.given().baseUri(baseUrl).basePath(endpoint)
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey)
		.header(HACCEPT, CONTEN_TTYPE).when().get().then().extract().response();
    }

    /**
     * Realiza una petición HTTP PUT enviando headers de autenticación y parámetros
     * de consulta.
     *
     * @author Guillermo Garcia
     * @param baseUrl     URL base del servicio
     * @param endpoint    Endpoint del recurso a consultar
     * @param apiToken    Token de autenticación Bearer
     * @param apiKey      API Key utilizada para el gateway
     * @param queryParams Parámetros de consulta enviados en la petición
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response putRequest(String baseUrl, String endpoint, String apiToken, String apiKey,
	    Map<String, Object> queryParams) {

	return RestAssured.given().baseUri(baseUrl).basePath(endpoint)
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey)
		.queryParams(queryParams).header(HCONTENT_TYPE, CONTEN_TTYPE).when().put().then().extract()
		.response();
    }

    /**
     * Realiza una petición HTTP PUT enviando headers de autenticación y parámetros
     * de consulta.
     *
     * @author Guillermo Garcia
     * @param baseUrl     URL base del servicio
     * @param endpoint    Endpoint del recurso a consultar
     * @param apiToken    Token de autenticación Bearer
     * @param apiKey      API Key utilizada para el gateway
     * @param queryParams Parámetros de consulta enviados en la petición
     * @param body        body del cuerpo del get
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response putRequest(String baseUrl, String endpoint, String apiToken, String apiKey, String body) {

	return RestAssured.given().baseUri(baseUrl).basePath(endpoint)
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey)
		.header(HCONTENT_TYPE, CONTEN_TTYPE).header(HACCEPT, "*/*").body(body).when().put().then()
		.extract().response();
    }

    /**
     * Realiza una petición HTTP PATCH enviando headers de autenticación y un body
     * en formato JSON.
     *
     * @author Guillermo Garcia
     * @param baseUrl  URL base del servicio
     * @param endpoint Endpoint del recurso a actualizar
     * @param apiToken Token de autenticación Bearer
     * @param apiKey   API Key utilizada para el gateway
     * @param body     Cuerpo de la petición en formato JSON
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response patchRequest(String baseUrl, String endpoint, String apiToken, String apiKey, String body) {

	return RestAssured.given().baseUri(baseUrl).basePath(endpoint)
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey)
		.header(HCONTENT_TYPE, CONTEN_TTYPE).header(HACCEPT, "*/*").body(body).when().patch().then()
		.extract().response();
    }

    /**
     * Realiza una petición HTTP POST enviando headers de autenticación y un body en
     * formato JSON.
     *
     * @author Guillermo Garcia
     * @param baseUrl  URL base del servicio
     * @param endpoint Endpoint del recurso a consultar
     * @param apiToken Token de autenticación Bearer
     * @param apiKey   API Key utilizada para el gateway
     * @param body     Cuerpo de la petición en formato JSON
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response postRequest(String baseUrl, String endpoint, String apiToken, String apiKey, String body) {
	return RestAssured.given().baseUri(baseUrl).basePath(endpoint)
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey)
		.header(HCONTENT_TYPE, CONTEN_TTYPE).header(HACCEPT, "*/*").body(body).when().post().then()
		.extract().response();
    }

    /**
     * Lee un archivo JSON desde UNA RUTA ESPECIFICA y lo retorna como String.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo ubicado en resources
     * @return Contenido del archivo en formato String
     * @throws IOException Si ocurre un error al leer el archivo
     * @date 20/05/2026
     */
    public String readJsonFromResources(String fileName) throws IOException {
	Path path = Paths.get(fileName);
	if (!Files.exists(path)) {
	    throw new RuntimeException("Archivo no encontrado: " + fileName);
	}
	return Files.readString(path, StandardCharsets.UTF_8);
    }

    /**
     * Envía una solicitud POST al servicio de promotional-calendars utilizando un
     * body JSON leído desde recursos.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON utilizado como cuerpo de la petición
     * @return Respuesta del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo JSON
     * @date 20/05/2026
     */
    public Response postPromotionalCalendars(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + "\\Config\\Input\\DataProviders\\WM\\apiGalaxy\\jsons\\calendarios\\" + fileName);

	return postRequest(BASE_URL_IMA, ENDPOINT_CALENDAR, getAccessTokenIMA(), apiKeyIma, body);
    }

    /**
     * Envía una solicitud POST al servicio de promotional-calendars utilizando un
     * body JSON leído desde recursos.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON utilizado como cuerpo de la petición
     * @return Respuesta del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo JSON
     * @date 20/05/2026
     */
    public Response postPromotionalCalendarsErrors(String body) throws IOException {
	return postRequest(BASE_URL_IMA, ENDPOINT_CALENDAR, getAccessTokenIMA(), apiKeyIma, body);
    }

    /**
     * Ejecuta un health check al servicio para validar su disponibilidad.
     *
     * @author Guillermo Garcia
     * @return Respuesta del endpoint de health-check
     * @date 20/05/2026
     */
    public Response getHealtCheck() {

	return getRequest(baseUrl, "/health-check", getAccessToken(), apiKey);
    }

    /**
     * Consulta el catálogo de stores con paginación y filtro por fecha de
     * actualización.
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores
     * @date 20/05/2026
     */
    public Response getStores(int page, int pageSize, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put("page", page);
	params.put(PAGE_SIZE, pageSize);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest(baseUrl, ENDPOINT_STORES, getAccessToken(), apiKey, params);

    }

    /**
     * Consulta el catálogo de stores con paginación y filtro por fecha de
     * actualización.
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores
     * @date 20/05/2026
     */
    public Response getStores(String page, String pageSize, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put("page", page);
	params.put(PAGE_SIZE, pageSize);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest(baseUrl, ENDPOINT_STORES, getAccessToken(), apiKey, params);

    }

    /**
     * Consulta el catálogo de stores con paginación y filtro por fecha de
     * actualización, utilizando una configuración especial para forzar escenarios
     * de timeout (408).
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores
     * @date 20/05/2026
     */
    public Response getStores408(String page, String pageSize, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put("page", page);
	params.put(PAGE_SIZE, pageSize);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest408(baseUrl, ENDPOINT_STORES, getAccessToken(), apiKey, params);

    }

    /**
     * Consulta el catálogo de stores con paginación y filtro por fecha de
     * actualización, enviando un body en la petición para simular o validar
     * escenarios de error (415).
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores
     * @date 20/05/2026
     */
    public Response getStores415(String page, String pageSize, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put("page", page);
	params.put(PAGE_SIZE, pageSize);
	params.put(UPDATED_DATE, updatedDate);

	return getRequestWithBody(baseUrl, ENDPOINT_STORES, getAccessToken(), apiKey, params);

    }

    /**
     * Consulta el catálogo de stores con paginación y filtro por fecha de
     * actualización, enviando la petición sin token de autenticación para validar
     * escenarios de acceso no autorizado.
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores
     * @date 20/05/2026
     */
    public Response getStoresNoToken(String page, String pageSize, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put("page", page);
	params.put(PAGE_SIZE, pageSize);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest(baseUrl, ENDPOINT_STORES, "", apiKey, params);

    }

    /**
     * Consulta el catálogo de stores utilizando un endpoint inexistente para
     * validar escenarios de recurso no encontrado (404).
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el error de recurso no encontrado
     * @date 20/05/2026
     */
    public Response getStoresNotFound(String page, String pageSize, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put("page", page);
	params.put(PAGE_SIZE, pageSize);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest(baseUrl, "/catalog/stores/NotFound", getAccessToken(), apiKey, params);

    }

    /**
     * Actualiza información del catálogo de stores mediante una petición PUT
     * utilizando paginación y filtro por fecha de actualización.
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el resultado de la actualización
     * @date 20/05/2026
     */
    public Response putStores(String page, String pageSize, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put("page", page);
	params.put(PAGE_SIZE, pageSize);
	params.put(UPDATED_DATE, updatedDate);

	return putRequest(baseUrl, ENDPOINT_STORES, getAccessToken(), apiKey, params);

    }

    /**
     * Consulta el catálogo de stores-spaces utilizando paginación por cursor y
     * filtro por fecha de actualización.
     *
     * @author Guillermo Garcia
     * @param cursor      Cursor de paginación para la siguiente página de
     *                    resultados
     * @param limit       Número máximo de registros a consultar
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores-spaces
     * @date 20/05/2026
     */
    public Response getStoresSpaces(int cursor, int limit, String updatedDate) {
	Map<String, Object> params = new HashMap<>();
	if (cursor > 0) {
	    params.put(CURSOR, cursor);
	}
	params.put(LIMIT, limit);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest(baseUrl, ENDPOINT_SPACE, getAccessToken(), apiKey, params);

    }

    /**
     * Obtiene la lista de folios de acuerdos aplicando los filtros y parámetros de
     * paginación proporcionados.
     *
     * @author Guillermo Garcia
     * @param marketplaceId Identificador del marketplace para filtrar los
     *                      resultados
     * @param page          Número de página a consultar
     * @param pageSize      Cantidad de registros por página
     * @param createdDate   Fecha de creación utilizada como filtro de búsqueda
     * @param returnLatest  Indica si se deben retornar únicamente los registros más
     *                      recientes
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    public Response getAgreementFolios(int marketplaceId, int page, int pageSize, String createdDate,
	    String returnLatest) {
	Map<String, Object> params = new HashMap<>();
	if (marketplaceId > 0) {
	    params.put(MARKETPLACE_ID, marketplaceId);
	}
	if (page > 0) {
	    params.put("page", page);
	}
	if (pageSize > 0) {
	    params.put(PAGE_SIZE, pageSize);
	}
	if (!createdDate.equalsIgnoreCase(NO_ENVIAR)) {
	    params.put(CREATED_DATE, createdDate);
	}
	if (!returnLatest.equalsIgnoreCase(NO_ENVIAR)) {
	    params.put(RETURN_LATEST, "true");
	}

	return getRequest(baseUrl, ENDPOINT_FOLIOS, getAccessToken(), apiKey, params);

    }

    /**
     * Realiza una petición para eliminar folios de acuerdos utilizando los
     * parámetros de filtrado proporcionados.
     *
     * @author Guillermo Garcia
     * @param marketplaceId Identificador del marketplace para filtrar la operación
     * @param page          Número de página de la consulta
     * @param pageSize      Cantidad de registros por página
     * @param createdDate   Fecha de creación utilizada como filtro
     * @param returnLatest  Indica si la operación considera únicamente los
     *                      registros más recientes
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    public Response deleteAgreementFolios(int marketplaceId, int page, int pageSize, String createdDate,
	    String returnLatest) {
	Map<String, Object> params = new HashMap<>();
	boolean flag;
	if (marketplaceId > 0) {
	    params.put(MARKETPLACE_ID, marketplaceId);
	}
	if (page > 0) {
	    params.put("page", page);
	}
	if (pageSize > 0) {
	    params.put(PAGE_SIZE, pageSize);
	}
	if (!createdDate.equalsIgnoreCase(NO_ENVIAR)) {
	    params.put(CREATED_DATE, createdDate);
	}
	if (!returnLatest.equalsIgnoreCase(NO_ENVIAR)) {
	    flag = returnLatest.equalsIgnoreCase("true") ? true : false;
	    params.put(RETURN_LATEST, flag);
	}

	return deleteRequestWithParam(baseUrl, ENDPOINT_FOLIOS, getAccessToken(), apiKey,
		"ss");

    }

    /**
     * Obtiene la lista de folios de acuerdos utilizando un token de autenticación
     * personalizado para la petición.
     *
     * @author Guillermo Garcia
     * @param marketplaceId Identificador del marketplace para filtrar los
     *                      resultados
     * @param page          Número de página a consultar
     * @param pageSize      Cantidad de registros por página
     * @param createdDate   Fecha de creación utilizada como filtro de búsqueda
     * @param returnLatest  Indica si se deben retornar únicamente los registros más
     *                      recientes
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    public Response getAgreementFoliosNotToken(int marketplaceId, int page, int pageSize, String createdDate,
	    String returnLatest) {
	Map<String, Object> params = new HashMap<>();
	boolean flag;
	if (marketplaceId > 0) {
	    params.put(MARKETPLACE_ID, marketplaceId);
	}
	if (page > 0) {
	    params.put("page", page);
	}
	if (pageSize > 0) {
	    params.put(PAGE_SIZE, pageSize);
	}
	if (!createdDate.equalsIgnoreCase(NO_ENVIAR)) {
	    params.put(CREATED_DATE, createdDate);
	}
	if (!returnLatest.equalsIgnoreCase(NO_ENVIAR)) {
	    flag = returnLatest.equalsIgnoreCase("true") ? true : false;
	    params.put(RETURN_LATEST, flag);
	}

	return getRequest(baseUrl, ENDPOINT_FOLIOS, "jejeje", apiKey, params);

    }

    /**
     * Obtiene la lista de folios de acuerdos utilizando un endpoint alterno para
     * validar escenarios donde el recurso no existe.
     *
     * @author Guillermo Garcia
     * @param marketplaceId Identificador del marketplace para filtrar los
     *                      resultados
     * @param page          Número de página a consultar
     * @param pageSize      Cantidad de registros por página
     * @param createdDate   Fecha de creación utilizada como filtro de búsqueda
     * @param returnLatest  Indica si se deben retornar únicamente los registros más
     *                      recientes
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    public Response getAgreementFoliosNotFound(int marketplaceId, int page, int pageSize, String createdDate,
	    String returnLatest) {
	Map<String, Object> params = new HashMap<>();
	boolean flag;
	if (marketplaceId > 0) {
	    params.put(MARKETPLACE_ID, marketplaceId);
	}
	if (page > 0) {
	    params.put("page", page);
	}
	if (pageSize > 0) {
	    params.put(PAGE_SIZE, pageSize);
	}
	if (!createdDate.equalsIgnoreCase(NO_ENVIAR)) {
	    params.put(CREATED_DATE, createdDate);
	}
	if (!returnLatest.equalsIgnoreCase(NO_ENVIAR)) {
	    flag = returnLatest.equalsIgnoreCase("true") ? true : false;
	    params.put(RETURN_LATEST, flag);
	}

	return getRequest(baseUrl, ENDPOINT_FOLIOS + "/jejeje", getAccessToken(), apiKey,
		params);

    }

    /**
     * Consulta el catálogo de stores-spaces utilizando paginación por cursor y
     * filtro por fecha de actualización. Los parámetros cursor y limit se envían
     * solo si son mayores a 0.
     *
     * @author Guillermo Garcia
     * @param cursor      Cursor de paginación para la siguiente página de
     *                    resultados (opcional si es 0)
     * @param limit       Número máximo de registros a consultar (opcional si es 0)
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores-spaces
     * @date 20/05/2026
     */
    public Response getStoresSpacesCursor(int cursor, int limit, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	if (cursor > 0) {
	    params.put(CURSOR, cursor);
	}
	if (limit > 0) {
	    params.put(LIMIT, limit);
	}
	params.put(UPDATED_DATE, updatedDate);

	return getRequest(baseUrl, ENDPOINT_SPACE, getAccessToken(), apiKey, params);

    }

    /**
     * Consulta el catálogo de stores-spaces utilizando paginación por cursor y
     * filtro por fecha de actualización.
     *
     * @author Guillermo Garcia
     * @param limit       Número máximo de registros a consultar
     * @param cursor      Cursor de paginación para la siguiente página de
     *                    resultados
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores-spaces
     * @date 20/05/2026
     */
    public Response getStoresSpaces(String limit, String cursor, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put(LIMIT, limit);
	params.put(CURSOR, cursor);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest(baseUrl, ENDPOINT_SPACE, getAccessToken(), apiKey, params);

    }

    /**
     * Consulta el catálogo de stores-spaces utilizando paginación y filtro por
     * fecha de actualización, con configuración especial para simular escenarios de
     * timeout (408).
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores-spaces
     * @date 20/05/2026
     */
    public Response getStoresSpaces408(String cursor, String limit, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put(LIMIT, limit);
	params.put(CURSOR, cursor);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest408(baseUrl, ENDPOINT_SPACE, getAccessToken(), apiKey, params);

    }

    /**
     * Consulta el catálogo de stores-spaces utilizando paginación y filtro por
     * fecha de actualización, enviando un body en la petición para validar
     * escenarios de error (415).
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores-spaces
     * @date 20/05/2026
     */
    public Response getStoresSpaces415(String cursor, String limit, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put(LIMIT, limit);
	params.put(CURSOR, cursor);
	params.put(UPDATED_DATE, updatedDate);

	return getRequestWithBody(baseUrl, ENDPOINT_SPACE, getAccessToken(), apiKey, params);

    }

    /**
     * Consulta el catálogo de stores-spaces utilizando un endpoint inexistente para
     * validar escenarios de recurso no encontrado (404).
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el error de recurso no encontrado
     * @date 20/05/2026
     */
    public Response getStoresSpacesNotFound(String cursor, String limit, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put(LIMIT, limit);
	params.put(CURSOR, cursor);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest(baseUrl, "/catalog/stores-spaces/NotFound", getAccessToken(), apiKey,
		params);

    }

    /**
     * Consulta el catálogo de stores-spaces utilizando paginación y filtro por
     * fecha de actualización, enviando la petición sin token de autenticación para
     * validar escenarios de acceso no autorizado.
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el catálogo de stores-spaces
     * @date 20/05/2026
     */
    public Response getStoresSpacesNoToken(String cursor, String limit, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put(LIMIT, limit);
	params.put(CURSOR, cursor);
	params.put(UPDATED_DATE, updatedDate);

	return getRequest(baseUrl, ENDPOINT_SPACE, "", apiKey, params);
    }

    /**
     * Actualiza información del catálogo de stores-spaces mediante una petición PUT
     * utilizando paginación y filtro por fecha de actualización.
     *
     * @author Guillermo Garcia
     * @param page        Número de página a consultar
     * @param pageSize    Cantidad de registros por página
     * @param updatedDate Fecha de actualización para filtrar resultados
     * @return Respuesta del servicio REST con el resultado de la actualización
     * @date 20/05/2026
     */
    public Response putStoresSpaces(String cursor, String limit, String updatedDate) {

	Map<String, Object> params = new HashMap<>();
	params.put(LIMIT, limit);
	params.put(CURSOR, cursor);
	params.put(UPDATED_DATE, updatedDate);

	return putRequest(baseUrl, ENDPOINT_SPACE, getAccessToken(), apiKey, params);

    }

    /**
     * Convierte un ResultSet de Snowflake en una lista de mapas, donde cada mapa
     * representa una fila con sus columnas y valores.
     *
     * @author Guillermo Garcia
     * @param rs ResultSet obtenido de una consulta a base de datos
     * @return Lista de registros representados como Map (columna-valor)
     * @throws SQLException Si ocurre un error al leer el ResultSet
     * @date 20/05/2026
     */
    public List<Map<String, Object>> getListFromSnowResponse(ResultSet rs) throws SQLException {
	List<Map<String, Object>> lista = new ArrayList<>();
	ResultSetMetaData metaData = rs.getMetaData();
	int columnCount = metaData.getColumnCount();

	while (rs.next()) {
	    Map<String, Object> fila = new HashMap<>();
	    for (int i = 1; i <= columnCount; i++) {
		fila.put(metaData.getColumnName(i), rs.getObject(i));
	    }
	    lista.add(fila);
	}
	return lista;
    }

    /**
     * Normaliza las claves de una lista de mapas convirtiéndolas a mayúsculas.
     *
     * @author Guillermo Garcia
     * @param jsonList Lista de mapas con datos JSON a normalizar
     * @return Lista de mapas con las claves en formato mayúscula
     * @date 20/05/2026
     */
    public List<Map<String, Object>> normalizeJsonListKeys(List<Map<String, Object>> jsonList) {
	return jsonList.stream()
		.map(map -> map.entrySet().stream()
			.collect(Collectors.toMap(e -> e.getKey().toUpperCase(Locale.ROOT), Map.Entry::getValue)))
		.collect(Collectors.toList());
    }

    /**
     * Normaliza las claves de una lista de mapas convirtiéndolas a mayúsculas,
     * permitiendo claves nulas sin generar errores.
     *
     * @author Guillermo Garcia
     * @param jsonList Lista de mapas con datos JSON a normalizar
     * @return Lista de mapas con las claves en formato mayúscula (permitiendo null)
     * @date 20/05/2026
     */
    public List<Map<String, Object>> normalizeJsonListKeysNull(List<Map<String, Object>> jsonList) {
	return jsonList.stream().map(map -> {
	    Map<String, Object> newMap = new HashMap<>();
	    for (Map.Entry<String, Object> e : map.entrySet()) {
		String key = e.getKey() == null ? null : e.getKey().toUpperCase(Locale.ROOT);
		newMap.put(key, e.getValue()); // ✅ permite null
	    }
	    return newMap;
	}).collect(Collectors.toList());
    }

    /**
     * Indexa una lista de mapas JSON utilizando una clave primaria como
     * identificador.
     *
     * @author Guillermo Garcia
     * @param jsonList   Lista de mapas con datos JSON a indexar
     * @param primaryKey Nombre de la clave que se usará como índice principal
     * @return Mapa indexado donde la llave es el valor de la primaryKey y el valor
     *         es el registro completo
     * @date 20/05/2026
     */
    public Map<String, Map<String, Object>> jsonIndexer(List<Map<String, Object>> jsonList, String primaryKey) {
	Map<String, Map<String, Object>> jsonIndex = new HashMap<>();
	for (Map<String, Object> row : jsonList) {
	    String pkValue = row.get(primaryKey).toString();
	    jsonIndex.put(pkValue, row);
	}
	return jsonIndex;
    }

    /**
     * Indexa una lista de mapas JSON utilizando una clave primaria compuesta
     * formada por dos campos concatenados.
     *
     * @author Guillermo Garcia
     * @param jsonList            Lista de mapas con datos JSON a indexar
     * @param primaryKey          Nombre de la primera clave usada para el índice
     * @param primaryKeyCompuesta Nombre de la segunda clave usada para formar la
     *                            llave compuesta
     * @return Mapa indexado donde la llave es la combinación de ambas claves y el
     *         valor es el registro completo
     * @date 20/05/2026
     */
    public Map<String, Map<String, Object>> jsonIndexer(List<Map<String, Object>> jsonList, String primaryKey,
	    String primaryKeyCompuesta) {
	Map<String, Map<String, Object>> jsonIndex = new HashMap<>();
	for (Map<String, Object> row : jsonList) {
	    String pkValue = row.get(primaryKey).toString() + "|" + row.get(primaryKeyCompuesta).toString();
	    jsonIndex.put(pkValue, row);
	}
	return jsonIndex;
    }

    /**
     * Busca el primer elemento de un array JSON que no contenga valores nulos.
     *
     * @author Guillermo Garcia
     * @param node Nodo JSON (debe ser un array) donde se realizará la búsqueda
     * @return Primer JsonNode sin valores nulos, o null si no existe ninguno
     * @date 20/05/2026
     */
    public JsonNode findFirstWithoutNulls(JsonNode node) {
	if (node == null || !node.isArray()) {
	    return null;
	}

	for (JsonNode item : node) {
	    boolean hasNull = false;

	    Iterator<JsonNode> values = item.elements();
	    while (values.hasNext()) {
		if (values.next().isNull()) {
		    hasNull = true;
		    break;
		}
	    }

	    if (!hasNull) {
		return item;
	    }
	}

	return null;
    }

    /**
     * Ejecuta una consulta en Snowflake y convierte el resultado en una lista de
     * mapas, donde cada mapa representa una fila con sus columnas y valores.
     *
     * @author Guillermo Garcia
     * @param query Consulta SQL a ejecutar en Snowflake
     * @return Lista de registros obtenidos desde Snowflake
     * @throws SQLException             Si ocurre un error en la ejecución de la
     *                                  consulta o conexión
     * @throws NoSuchAlgorithmException Si falla la generación de la clave de
     *                                  autenticación
     * @throws InvalidKeySpecException  Si la clave privada no es válida
     * @throws IOException              Si ocurre un error al leer recursos
     *                                  necesarios para la conexión
     * @date 20/05/2026
     */
    public List<Map<String, Object>> executeQuerySnowflake(String query)
	    throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
	List<Map<String, Object>> resultList = new ArrayList<>();

	try (Connection connection = getSnowflakeConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query)) {

	    if (!connection.isValid(10)) {
		throw new SQLException("Conexion a Snowflake no es valida");
	    }

	    ResultSetMetaData metaData = resultSet.getMetaData();
	    int columnCount = metaData.getColumnCount();

	    while (resultSet.next()) {
		Map<String, Object> row = new HashMap<>();
		for (int i = 1; i <= columnCount; i++) {
		    row.put(metaData.getColumnName(i), resultSet.getObject(i));
		}
		resultList.add(row);
	    }

	} catch (SQLException e) {
	    System.out.println("Error ejecutando query en Snowflake");
	    throw e; // importante: re-lanzar para que el test falle correctamente
	}

	return resultList;
    }

    /**
     * Genera una consulta SQL para contar registros en la tabla de calendario de
     * promociones filtrando por el identificador de periodos.
     *
     * @author Guillermo Garcia
     * @param id Identificador del periodo a consultar
     * @return Query SQL para contar registros en Snowflake según el periodo
     *         indicado
     * @date 20/05/2026
     */
    public String querieCalendarCount(String id) {
	return "SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = "
		+ id + ";";
    }

    /**
     * Genera una consulta SQL para obtener todos los registros del calendario de
     * promociones filtrando por el identificador de periodos.
     *
     * @author Guillermo Garcia
     * @param id Identificador del periodo a consultar
     * @return Query SQL para obtener los registros del calendario según el periodo
     *         indicado
     * @date 20/05/2026
     */
    public String querieCalendarData(String id) {
	return "SELECT * FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = " + id
		+ ";";
    }

    /**
     * Normaliza un objeto convirtiéndolo a String de forma segura.
     *
     * @author Guillermo Garcia
     * @param o Objeto a normalizar
     * @return Representación en String del objeto, o null si el valor es null
     * @date 20/05/2026
     */
    private String norm(Object o) {
	return o == null ? null : o.toString();
    }

    /**
     * Compara un campo entre un valor JSON y un valor de base de datos, registrando
     * el resultado de la comparación en una lista de resultados.
     *
     * @author Guillermo Garcia
     * @param key       Nombre del campo que se está comparando
     * @param jsonValue Valor proveniente del JSON
     * @param dbValue   Valor proveniente de Snowflake/base de datos
     * @param id        Identificador del registro que se está validando
     * @param results   Lista donde se almacenan los resultados de la comparación
     * @return true si ambos valores son iguales, false en caso contrario
     * @date 20/05/2026
     */
    private boolean compareField(String key, Object jsonValue, Object dbValue, String id, List<String> results) {

	String jsonVal = norm(jsonValue);
	String dbVal = norm(dbValue);

	boolean equals = Objects.equals(jsonVal, dbVal);
	if (!equals) {
	    System.out.println(ID_DESC + id + KEY_DESC + key + JSON_DESC + jsonVal
		    + SNOW_DESC + dbVal + ESTADO_DESC + DIFERENTES);
	}

	results.add(ID_DESC + id + KEY_DESC + key + JSON_DESC + jsonVal + SNOW_DESC
		+ dbVal + ESTADO_DESC + (equals ? IGUALES : DIFERENTES));
	testCase.addCodeEvidenceCurrentStep(ID_DESC + id + KEY_DESC + key + JSON_DESC + jsonVal
		+ SNOW_DESC + dbVal + ESTADO_DESC + IGUALES);
	return equals;
    }

    /**
     * Compara la información del calendario promocional entre un archivo JSON y los
     * datos obtenidos desde Snowflake, validando campo por campo y generando un
     * reporte detallado de diferencias.
     *
     * @author Guillermo Garcia
     * @param jsonFile Nombre del archivo JSON que contiene la información de
     *                 referencia
     * @param dbData   Lista de registros obtenidos desde Snowflake
     * @return Resultado de la comparación global junto con el detalle de
     *         validaciones por campo
     * @throws IOException Si ocurre un error al leer o parsear el archivo JSON
     * @date 20/05/2026
     */
    public ComparisonResult comparePromotionalCalendar(String jsonFile, List<Map<String, Object>> dbData)
	    throws IOException {

	ObjectMapper mapper = new ObjectMapper();
	List<String> results = new ArrayList<>();
	boolean globalStatus = true;

	// Leer JSON
	JsonNode root = mapper.readTree(new File(
		currentDir + "\\Config\\Input\\DataProviders\\WM\\apiGalaxy\\jsons\\calendarios\\" + jsonFile));
	ArrayNode apiArray = (ArrayNode) root.get("promotionalCalendar");

	// Convertir API a lista
	List<Map<String, Object>> apiList = new ArrayList<>();
	for (JsonNode node : apiArray) {
	    apiList.add(mapper.convertValue(node, Map.class));
	}

	// Indexar DB por clave compuesta
	Map<String, Map<String, Object>> dbIndex = new HashMap<>();

	for (Map<String, Object> row : dbData) {
	    String key = norm(row.get("PERIODS_ID")) + "|" + norm(row.get("ACTIVITY_NAME")) + "|"
		    + norm(row.get(PERIOD_NAME_ROW));
	    dbIndex.put(key, row);
	}

	// Comparación
	for (Map<String, Object> apiRow : apiList) {

	    String id = norm(apiRow.get(PERIOD_ID)) + "|" + norm(apiRow.get(ACTIVITY_NAME)) + "|"
		    + norm(apiRow.get(PERIOD_NAME));
	    Map<String, Object> dbRow = dbIndex.get(id);

	    if (dbRow == null) {
		results.add(ID_DESC + id + " no se encontró registro en Snowflake");
		globalStatus = false;
		continue;
	    }

	    globalStatus &= compareField(FINAL_DATE, apiRow.get(FINAL_DATE), dbRow.get("FINAL_DATE"), id, results);
	    globalStatus &= compareField(INITIAL_DATE, apiRow.get(INITIAL_DATE), dbRow.get("INITIAL_DATE"), id,
		    results);
	    globalStatus &= compareField(PERIOD_ID, apiRow.get(PERIOD_ID), dbRow.get("PERIODS_ID"), id,
		    results);
	    globalStatus &= compareField(PERIOD_NAME, apiRow.get(PERIOD_NAME), dbRow.get(PERIOD_NAME_ROW), id,
		    results);
	    globalStatus &= compareField(POSITION, apiRow.get(POSITION), dbRow.get(POSITION), id, results);
	    globalStatus &= compareField(PROJECTION_PERCENTAGE, apiRow.get(PROJECTION_PERCENTAGE),
		    dbRow.get("PROJECTION_PERCENTAGE"), id, results);
	    globalStatus &= compareField(SHORT_PERIOD, apiRow.get(SHORT_PERIOD), dbRow.get("IS_SHORT_PERIODS"),
		    id, results);
	    globalStatus &= compareField(YEAR_CALENDAR, apiRow.get(YEAR_CALENDAR), dbRow.get("YEAR"), id, results);
	    globalStatus &= compareField("type", apiRow.get("type"), dbRow.get("TYPE"), id, results);
	    globalStatus &= compareField(ACTIVITY_NAME, apiRow.get(ACTIVITY_NAME), dbRow.get("ACTIVITY_NAME"),
		    id, results);
	    globalStatus &= compareField(N_DAYS, apiRow.get(N_DAYS), dbRow.get("N"), id, results);
	    globalStatus &= compareField(N_DATE, apiRow.get(N_DATE), dbRow.get("N_DATE"), id, results);
	}

	return new ComparisonResult(globalStatus, results);
    }

    /**
     * Valida que el servicio de calendarios promocionales responda con un error
     * cuando se envía un valor inválido o incorrecto en un campo del cuerpo de la
     * petición.
     *
     * @author Guillermo Garcia
     * @param fieldToModify Nombre del campo del JSON que será modificado
     * @param newValue      Nuevo valor asignado al campo para validar el escenario
     *                      de error
     * @return No aplica
     * @throws IOException Si ocurre un error al generar el cuerpo de la petición
     * @date 20/05/2026
     */
    public Boolean validatePromotionalCalendarsError(String fieldToModify, Object newValue) throws IOException {
	String body = generateJson(fieldToModify, newValue);
	Response response = postPromotionalCalendarsErrors(body);
	String realCode = response.jsonPath().getString("code");
	testCase.addCodeEvidenceCurrentStep(ENVIO_DESC + realCode);
	Boolean flagCode = realCode.equals(CODE_400_MESS);
	Boolean flagMessage = response.jsonPath().getString(MESSAGE)
		.equals(ERROR_MESSAGE);
	return flagCode && flagMessage;
    }

    /**
     * Genera dinámicamente el cuerpo de la petición en formato JSON para los
     * calendarios promocionales, permitiendo modificar, eliminar o asignar valores
     * nulos a un campo específico.
     *
     * @author Guillermo Garcia
     * @param fieldToModify Nombre del campo del JSON que será modificado
     * @param newValue      Nuevo valor que se asignará al campo; si es NO_ENVIAR
     *                      el campo se elimina y si es null se envía con valor nulo
     * @return Cadena en formato JSON con la estructura de la petición
     * @date 20/05/2026
     */
    public String generateJson(String fieldToModify, Object newValue) {
	ObjectMapper mapper = new ObjectMapper();
	ObjectNode root = mapper.createObjectNode();
	ArrayNode promotionalCalendar = mapper.createArrayNode();

	ObjectNode item = mapper.createObjectNode();

	// Valores default válidos
	item.put(FINAL_DATE, "2026-01-28");
	item.put(INITIAL_DATE, "2026-01-08");
	item.put(PERIOD_ID, 74);
	item.put(PERIOD_NAME, "P1");
	item.put(POSITION, 1);
	item.put(PROJECTION_PERCENTAGE, 4);
	item.put(SHORT_PERIOD, false);
	item.put(YEAR_CALENDAR, 2026);
	item.put("type", "cerveza");
	item.put(ACTIVITY_NAME, "Actividad default");
	item.put(N_DAYS, -38);
	item.put(N_DATE, "2025-12-01");

	// Modificación dinámica
	if (NO_ENVIAR.equalsIgnoreCase(String.valueOf(newValue))) {

	    // Eliminar campo
	    item.remove(fieldToModify);

	} else if (newValue == null) {

	    // Campo null
	    item.putNull(fieldToModify);

	} else {

	    // Detectar tipo automáticamente
	    if (newValue instanceof Integer) {
		item.put(fieldToModify, (Integer) newValue);

	    } else if (newValue instanceof Boolean) {
		item.put(fieldToModify, (Boolean) newValue);

	    } else if (newValue instanceof Double) {
		item.put(fieldToModify, (Double) newValue);

	    } else {
		item.put(fieldToModify, String.valueOf(newValue));
	    }
	}

	promotionalCalendar.add(item);
	root.set("promotionalCalendar", promotionalCalendar);

	try {
	    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * Ejecuta múltiples solicitudes concurrentes hacia un endpoint específico para
     * realizar pruebas de carga y concurrencia sobre los servicios disponibles.
     *
     * @author Guillermo Garcia
     * @param endpoint Nombre del endpoint sobre el que se ejecutarán las
     *                 solicitudes concurrentes
     * @return No aplica
     * @throws IOException Si ocurre un error durante la generación o envío de
     *                     alguna de las solicitudes
     * @date 20/05/2026
     * NOta para el validador: No se puede reducir la logica para bajarle complexidad ya que es un switch case, tendria que hacer una subrutina de 
     */
    public void multipleRequestMarketplace(String endpoint) throws IOException {
	String date = "1998-01-01";
	int opcionA = 1;
	int opcionB = 1;
	int id = 77;
	ExecutorService executor = Executors.newFixedThreadPool(50);
	for (int i = 0; i < 900; i++) {
	    executor.submit(() -> {
		switch (endpoint) {
		case "stores":
		    getStores(opcionA, opcionB, date);
		    break;
		case "spaces":
		    getStoresSpaces(opcionA, opcionB, date);
		    break;
		case "agreement":
		    getAgreementFolios(id, opcionB, opcionB, date, "false");
		    break;
		case "rent":
		    try {
			postPromotionalRentsErrors(generateJsonRenta(RENT_FOLIO, 7777));
		    } catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		    }
		    break;
		case "folio":
		case "calendar":
		    try {
			validatePromotionalCalendarsError(FINAL_DATE, date);
		    } catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		    }
		    break;
		default:
		    break;
		}
	    });
	}
	executor.shutdown();
    }

    /**
     * Convierte un objeto de tipo numérico a su representación como {@link Long}.
     * Si el valor recibido es nulo, retorna {@code null}.
     *
     * @author Guillermo Garcia
     * @param value Valor numérico que será convertido a tipo Long
     * @return Valor convertido a Long o {@code null} si el parámetro recibido es
     *         nulo
     * @date 20/05/2026
     */
    private Long toLong(Object value) {

	if (value == null) {
	    return null;
	}

	return ((Number) value).longValue();

    }

    /**
     * Valida que el valor obtenido para un campo coincida con el valor esperado,
     * registrando la evidencia de la comparación y realizando la aserción
     * correspondiente.
     *
     * @author Guillermo Garcia
     * @param fieldName Nombre del campo que será validado
     * @param actual    Valor obtenido desde la respuesta del servicio
     * @param expected  Valor esperado para el campo
     * @param orderId   Identificador de la orden utilizada como referencia en la
     *                  validación
     * @return No aplica
     * @date 20/05/2026
     */
    private Boolean validateField(String fieldName, Object actual, Object expected, int orderId) {
	Boolean flag = true;
	testCase.addCodeEvidenceCurrentStep("Campo: " + fieldName + " | API = " + actual + " | SNOW = " + expected);

	if (actual instanceof Number && expected instanceof Number) {
	    flag = ((Number) actual).longValue() == ((Number) expected).longValue();
	} else {
	    flag = actual.equals(expected);
	}
	if (flag.equals(false)) {
	    testCase.addBoldTextEvidenceCurrentStep("Error en " + fieldName + " mismatch for ORDER_ID " + orderId);
	}

	return flag;
    }

    /**
     * Valida la información de un espacio de tienda comparando los datos obtenidos
     * del servicio contra los valores esperados provenientes de la fuente de datos.
     *
     * @author Guillermo Garcia
     * @param actual  Objeto con la información del espacio de tienda obtenida del
     *                servicio
     * @param fila    Mapa con los valores esperados para realizar la comparación
     * @param orderId Identificador de la orden utilizada como referencia en la
     *                validación
     * @return No aplica
     * @date 20/05/2026
     */
    public void validateStoreSpace(StoreSpace actual, Map<String, Object> fila, int orderId) {
	validateField("TIENDA_ID", actual.getRetekId(), toLong(fila.get("TIENDA_ID")), orderId);

	validateField("ESPACIO_ID", actual.getSpaceId(), toLong(fila.get("ESPACIO_ID")), orderId);

	validateField("ESPACIO_NOMBRE", actual.getSpaceName(), fila.get("ESPACIO_NOMBRE"), orderId);

	validateField("NECESIDAD", actual.getNeedDesc(), fila.get("NECESIDAD"), orderId);

	validateField("TIPO_ESPACIO", actual.getSpaceType(), fila.get("TIPO_ESPACIO"), orderId);

	validateField("TIPO_CARGO", actual.getChargeType(), fila.get("TIPO_CARGO"), orderId);

	validateField(REGION, actual.getRegionName(), fila.get(REGION), orderId);

	validateField(PLAZA, actual.getPlazaName(), fila.get(PLAZA), orderId);

	validateField(ID_SEGMENTO_MAESTRO, actual.getMasterSegId(), toLong(fila.get(ID_SEGMENTO_MAESTRO)), orderId);

	validateField(SEGMENTO_MAESTRO, actual.getMasterSegDesc(), fila.get(SEGMENTO_MAESTRO), orderId);

	validateField(POTENCIAL, actual.getPotentialDesc(), fila.get(POTENCIAL), orderId);

	validateField(PERMISO_ALCOHOL, actual.getAlcoholPermit(), fila.get(PERMISO_ALCOHOL), orderId);

	validateField("PAQUETE_ID", actual.getPackageId(), toLong(fila.get("PAQUETE_ID")), orderId);

	validateField("PAQUETE", actual.getPackageDesc(), fila.get("PAQUETE"), orderId);

	validateField("SUBPAQUETE_ID", actual.getSubpackId(), toLong(fila.get("SUBPAQUETE_ID")), orderId);

	validateField("SUBPAQUETE", actual.getSubpackDesc(), fila.get("SUBPAQUETE"), orderId);

	validateField("CONCATENADO_IDS", actual.getCompositeId(), fila.get("CONCATENADO_IDS"), orderId);

	validateField("SLOTS", actual.getSlots(), toLong(fila.get("SLOTS")), orderId);

	validateField(ORDER_ID, actual.getOrderId(), ((Number) fila.get(ORDER_ID)).intValue(), orderId);

	validateField(TIPO_DELTA, actual.getDeltaType(), fila.get(TIPO_DELTA), orderId);

	validateField(CREATED_DATE_VAR, actual.getCreatedDate(), fila.get(CREATED_DATE_VAR).toString(), orderId);

	validateField(LAST_UPDATED_DATE, actual.getUpdatedDate(), fila.get(LAST_UPDATED_DATE).toString(), orderId);
    }

    /**
     * Valida la información de una tienda comparando los datos obtenidos del
     * servicio contra los valores esperados provenientes de la fuente de datos.
     *
     * @author Guillermo Garcia
     * @param actual  Objeto con la información de la tienda obtenida del servicio
     * @param fila    Mapa con los valores esperados para realizar la comparación
     * @param orderId Identificador de la orden utilizada como referencia en la
     *                validación
     * @return No aplica
     * @date 20/05/2026
     */
    public void validateStore(Store actual, Map<String, Object> fila, int orderId) {
	validateField("ID_RETEK", actual.getRetekId(), toLong(fila.get("ID_RETEK")), orderId);

	validateField("CR_TIENDA", actual.getStoreCode(), fila.get("CR_TIENDA"), orderId);

	validateField("NOMBRE_TIENDA", actual.getStoreName(), fila.get("NOMBRE_TIENDA"), orderId);

	validateField("CLAVE_REGION", actual.getRegionCode(), fila.get("CLAVE_REGION"), orderId);

	validateField(REGION, actual.getRegionName(), fila.get(REGION), orderId);

	validateField("NOMBRE_ZONA", actual.getZoneName(), fila.get("NOMBRE_ZONA"), orderId);

	validateField("CLAVE_PLAZA", actual.getPlazaCode(), fila.get("CLAVE_PLAZA"), orderId);

	validateField(PLAZA, actual.getPlazaName(), fila.get(PLAZA), orderId);

	validateField("DELEGACION_MUNICIPIO", actual.getMunicipalityDel(), fila.get("DELEGACION_MUNICIPIO"), orderId);

	validateField("GASOLINERAPROPIA", actual.getOwnGasStation(), fila.get("GASOLINERAPROPIA"), orderId);

	validateField("GASOLINERA_CVE", actual.getGasStationCode(), fila.get("GASOLINERA_CVE"), orderId);

	validateField(ID_SEGMENTO_MAESTRO, actual.getMasterSegId(), toLong(fila.get(ID_SEGMENTO_MAESTRO)), orderId);

	validateField(SEGMENTO_MAESTRO, actual.getMasterSegDesc(), fila.get(SEGMENTO_MAESTRO), orderId);

	validateField(POTENCIAL, actual.getPotentialDesc(), fila.get(POTENCIAL), orderId);

	validateField("TIPO_TIENDA_SIN", actual.getStoreTypeSin(), fila.get("TIPO_TIENDA_SIN"), orderId);

	validateField(PERMISO_ALCOHOL, actual.getAlcoholPermit(), fila.get(PERMISO_ALCOHOL), orderId);

	validateField("EMBOTELLADOR", actual.getBottlerDesc(), fila.get("EMBOTELLADOR"), orderId);

	validateField(ORDER_ID, actual.getOrderId(), ((Number) fila.get(ORDER_ID)).intValue(), orderId);

	validateField(TIPO_DELTA, actual.getDeltaType(), fila.get(TIPO_DELTA), orderId);

	validateField(CREATED_DATE_VAR, actual.getCreatedDate(), fila.get(CREATED_DATE_VAR).toString(), orderId);

	validateField(LAST_UPDATED_DATE, actual.getUpdatedDate(), fila.get(LAST_UPDATED_DATE).toString(), orderId);
    }

    /**
     * Genera un mensaje de evidencia con los parámetros enviados en la consulta de
     * espacios de tiendas para fines de depuración.
     *
     * @author Guillermo Garcia
     * @param cursor      Valor del cursor utilizado para la paginación de
     *                    resultados
     * @param limit       Límite de registros solicitados en la consulta
     * @param updatedDate Fecha de actualización utilizada como filtro
     * @return Mensaje con los valores enviados en la petición
     * @date 20/05/2026
     */
    public String envioSpacesStoresDebug(int cursor, int limit, String updatedDate) {
	return "Se envia el get con los valores: cursor " + cursor + " ,limit " + limit + " ,updatedDate "
		+ updatedDate;
    }

    /**
     * Realiza una petición HTTP DELETE enviando headers de autenticación y un
     * parámetro de consulta requerido por el servicio.
     *
     * @author Guillermo Garcia
     * @param baseUrl    URL base del servicio
     * @param endpoint   Endpoint del recurso a eliminar
     * @param apiToken   Token de autenticación Bearer
     * @param apiKey     API Key utilizada para el gateway
     * @param paramValue Valor del parámetro periodsId enviado en la petición
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response deleteRequestWithParam(String baseUrl, String endpoint, String apiToken, String apiKey,
	    String paramValue) {

	return RestAssured.given().baseUri(baseUrl).basePath(endpoint)
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey).header(HACCEPT, "*/*")
		.queryParam(PERIOD_ID, paramValue).when().delete().then().extract().response();
    }

    /**
     * Ejecuta una petición para eliminar un calendario promocional utilizando el
     * identificador proporcionado.
     *
     * @author Guillermo Garcia
     * @param id Identificador del calendario promocional a eliminar
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error durante la obtención de la información
     *                     requerida
     * @date 20/05/2026
     */
    public Response deletePromotionalCalendars(String id) throws IOException {

	return deleteRequestWithParam(BASE_URL_IMA, ENDPOINT_CALENDAR, getAccessTokenIMA(), apiKeyIma,
		id);
    }

    /**
     * Ejecuta una petición para eliminar un calendario promocional utilizando un
     * token de autenticación no válido para validar el comportamiento del servicio.
     *
     * @author Guillermo Garcia
     * @param id Identificador del calendario promocional a eliminar
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error durante la ejecución de la petición
     * @date 20/05/2026
     */
    public Response deletePromotionalCalendarsNoAuth(String id) throws IOException {

	return deleteRequestWithParam(BASE_URL_IMA, ENDPOINT_CALENDAR, "holaCrayol", apiKeyIma, id);

    }

    /**
     * Ejecuta una petición para eliminar un calendario promocional utilizando un
     * endpoint alterno para validar el comportamiento cuando el recurso no existe.
     *
     * @author Guillermo Garcia
     * @param id Identificador del calendario promocional a eliminar
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error durante la ejecución de la petición
     * @date 20/05/2026
     */
    public Response deletePromotionalCalendarsNotFound(String id) throws IOException {

	return deleteRequestWithParam(BASE_URL_IMA, ENDPOINT_CALENDAR + "/holaCrayo", getAccessTokenIMA(),
		apiKeyIma, id);
    }

    /**
     * Ejecuta una petición HTTP PUT para actualizar un calendario promocional
     * utilizando el identificador proporcionado.
     *
     * @author Guillermo Garcia
     * @param id Identificador del calendario promocional a actualizar
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error durante la ejecución de la petición
     * @date 20/05/2026
     */
    public Response putPromotionalCalendars(String id) throws IOException {

	return putRequestWithParam(BASE_URL_IMA, ENDPOINT_CALENDAR, getAccessTokenIMA(), apiKeyIma, id);
    }

    /**
     * Realiza una petición HTTP PUT enviando headers de autenticación y un
     * parámetro de consulta requerido por el servicio.
     *
     * @author Guillermo Garcia
     * @param baseUrl    URL base del servicio
     * @param endpoint   Endpoint del recurso a actualizar
     * @param apiToken   Token de autenticación Bearer
     * @param apiKey     API Key utilizada para el gateway
     * @param paramValue Valor del parámetro periodsId enviado en la petición
     * @return Respuesta obtenida del servicio REST
     * @date 20/05/2026
     */
    private Response putRequestWithParam(String baseUrl, String endpoint, String apiToken, String apiKey,
	    String paramValue) {

	return RestAssured.given().baseUri(baseUrl).basePath(endpoint)
		.header(HTRACKINGID, UUID.randomUUID().toString()).header(HCHANNEL, "WEB")
		.header(HCOUNTRY, "MX").header(HLANGUAGE, "SPA")
		.header(HAUTHORIZATION, HBEARER + apiToken).header(HGATE, apiKey).header(HACCEPT, "*/*")
		.queryParam(PERIOD_ID, paramValue).when().put().then().extract().response();
    }

    /**
     * Envía una solicitud POST al servicio de promotional-calendars utilizando un
     * body JSON leído desde recursos.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON utilizado como cuerpo de la petición
     * @return Respuesta del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo JSON
     * @date 20/05/2026
     */
    public Response postPromotionalRents(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + JSONS_URL + fileName);

	return postRequest(BASE_URL_IMA, ENDPOINT_RENTS, getAccessTokenIMA(), apiKeyIma, body);
    }

    /**
     * Ejecuta una petición HTTP PUT para actualizar rentas promocionales enviando
     * la información obtenida desde un archivo JSON.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON que contiene el cuerpo de la petición
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo o ejecutar la
     *                     petición
     * @date 20/05/2026
     */
    public Response putPromotionalRents(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + JSONS_URL + fileName);

	return putRequest(BASE_URL_IMA, ENDPOINT_RENTS, getAccessTokenIMA(), apiKeyIma, body);
    }

    public Response patchPromotionalRents(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + JSONS_URL + fileName);

	return patchRequest(BASE_URL_IMA, ENDPOINT_RENTS, getAccessTokenIMA(), apiKeyIma, body);
    }

    /**
     * Ejecuta una petición HTTP PATCH para actualizar rentas promocionales enviando
     * la información obtenida desde un archivo JSON.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON que contiene el cuerpo de la petición
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo o ejecutar la
     *                     petición
     * @date 20/05/2026
     */
    public Response putPromotionalRentsNotFound(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + JSONS_URL + fileName);

	return putRequest(BASE_URL_IMA, ENDPOINT_RENTS + "/holaRayola", getAccessTokenIMA(), apiKeyIma,
		body);
    }

    /**
     * Ejecuta una petición HTTP PUT para actualizar rentas promocionales utilizando
     * un token de autenticación inválido y enviando la información obtenida desde
     * un archivo JSON.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON que contiene el cuerpo de la petición
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo o ejecutar la
     *                     petición
     * @date 20/05/2026
     */
    public Response putPromotionalRentsNoToken(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + JSONS_URL + fileName);

	return putRequest(BASE_URL_IMA, ENDPOINT_RENTS, "rabano", apiKeyIma, body);
    }

    /**
     * Ejecuta una petición HTTP PUT para actualizar rentas promocionales enviando
     * un cuerpo de solicitud proporcionado para validar escenarios específicos del
     * servicio.
     *
     * @author Guillermo Garcia
     * @param body Cuerpo de la petición en formato JSON
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error durante la ejecución de la petición
     * @date 20/05/2026
     */
    public Response putPromotionalRentsErrors(String body) throws IOException {
	return putRequest(BASE_URL_IMA, ENDPOINT_RENTS, getAccessTokenIMA(), apiKeyIma, body);
    }

    /**
     * Ejecuta una petición HTTP POST para crear o procesar rentas promocionales
     * enviando un cuerpo de solicitud proporcionado para validar escenarios
     * específicos del servicio.
     *
     * @author Guillermo Garcia
     * @param body Cuerpo de la petición en formato JSON
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error durante la ejecución de la petición
     * @date 20/05/2026
     */
    public Response postPromotionalRentsErrors(String body) throws IOException {
	return postRequest(BASE_URL_IMA, ENDPOINT_RENTS, getAccessTokenIMA(), apiKeyIma, body);
    }

    /**
     * Ejecuta una petición HTTP DELETE para eliminar una renta promocional
     * utilizando el identificador proporcionado.
     *
     * @author Guillermo Garcia
     * @param id Identificador de la renta promocional a eliminar
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error durante la ejecución de la petición
     * @date 20/05/2026
     */
    public Response deletePromotionalRents(String id) throws IOException {
	return deleteRequestWithParam(BASE_URL_IMA, ENDPOINT_RENTS, getAccessTokenIMA(), apiKeyIma, id);
    }

    /**
     * Ejecuta una petición HTTP POST para crear o registrar acuerdos enviando la
     * información obtenida desde un archivo JSON.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON que contiene el cuerpo de la petición
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo o ejecutar la
     *                     petición
     * @date 20/05/2026
     */
    public Response postAgreements(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + JSONS_URL_CONVENIOS + fileName);

	return postRequest(BASE_URL_IMA, ENDPOINT_AGREEMENTS, getAccessTokenIMA(), apiKeyIma, body);
    }

    /**
     * Ejecuta una petición HTTP PUT para actualizar acuerdos enviando la
     * información obtenida desde un archivo JSON.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON que contiene el cuerpo de la petición
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo o ejecutar la
     *                     petición
     * @date 20/05/2026
     */
    public Response putAgreements(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + JSONS_URL_CONVENIOS + fileName);

	return putRequest(BASE_URL_IMA, ENDPOINT_AGREEMENTS, getAccessTokenIMA(), apiKeyIma, body);
    }

    /**
     * Ejecuta una petición HTTP POST para crear o registrar acuerdos utilizando un
     * endpoint alterno con la finalidad de validar el comportamiento cuando el
     * recurso no existe.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON que contiene el cuerpo de la petición
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo o ejecutar la
     *                     petición
     * @date 20/05/2026
     */
    public Response postAgreementsNotFound(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + JSONS_URL_CONVENIOS + fileName);

	return postRequest(BASE_URL_IMA, ENDPOINT_AGREEMENTS + "/holaCrayola", getAccessTokenIMA(),
		apiKeyIma, body);
    }

    /**
     * Ejecuta una petición HTTP POST para crear o registrar acuerdos utilizando un
     * token de autenticación inválido y enviando la información obtenida desde un
     * archivo JSON.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo JSON que contiene el cuerpo de la petición
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error al leer el archivo o ejecutar la
     *                     petición
     * @date 20/05/2026
     */
    public Response postAgreementsNoToken(String fileName) throws IOException {
	String body = readJsonFromResources(
		currentDir + JSONS_URL_CONVENIOS + fileName);

	return postRequest(BASE_URL_IMA, ENDPOINT_AGREEMENTS, "rabano", apiKeyIma, body);
    }

    /**
     * Ejecuta una petición HTTP POST para crear o registrar acuerdos enviando un
     * cuerpo de solicitud proporcionado para validar escenarios específicos del
     * servicio.
     *
     * @author Guillermo Garcia
     * @param body Cuerpo de la petición en formato JSON
     * @return Respuesta obtenida del servicio REST
     * @throws IOException Si ocurre un error durante la ejecución de la petición
     * @date 20/05/2026
     */
    public Response postAgreementsError(String body) throws IOException {

	return postRequest(BASE_URL_IMA, ENDPOINT_AGREEMENTS, getAccessTokenIMA(), apiKeyIma, body);
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieRentCount(String id) {
	return "SELECT COUNT(1) from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = "
		+ id + ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieRent(String id) {
	return "SELECT * FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = " + id
		+ ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieRentDelete(String id) {
	return "DELETE FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO =" + id
		+ ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */

    public String querieConvenioCount(String id) {
	return "SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ID_MARKETPLACE ="
		+ id + ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioCountMil(String fecha) {
	return "SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE CAST(ID_MARKETPLACE AS STRING) LIKE '7%' and fecha_carga > '"
		+ fecha + "' OR ID_MARKETPLACE = '8000';";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioDeleteMil(String fecha) {
	return "DELETE FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE CAST(ID_MARKETPLACE AS STRING) LIKE '7%' and fecha_carga > '"
		+ fecha + "';";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenio(String id) {
	return "SELECT * FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ID_MARKETPLACE =" + id
		+ ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioActualizacion(String id) {
	return "SELECT * FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ACCION_CONVENIO='A' AND ID_MARKETPLACE ="
		+ id + ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioMil(String fecha) {
	return "SELECT * FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE CAST(ID_MARKETPLACE AS STRING) LIKE '7%' and fecha_carga > '"
		+ fecha + "' OR ID_MARKETPLACE = '8000';;";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioDelete(String id) {
	return "DELETE FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ID_MARKETPLACE =" + id
		+ ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioEstatusEmitido(String id) {
	return "UPDATE DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS SET FOLIO_EBS  = 'FOLIOQA_1234', ESTADO_CONVENIO = 'E-Emitido' WHERE ID_MARKETPLACE = "
		+ id + ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioEstatusProcesando(String id) {
	return "UPDATE DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS SET ESTADO_CONVENIO = 'P-PROCESANDO' WHERE ID_MARKETPLACE = "
		+ id + ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioEstatusError(String id) {
	return "UPDATE DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS SET ESTADO_CONVENIO = 'X-Error' WHERE ID_MARKETPLACE = "
		+ id + ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioEstatusCancelado(String id) {
	return "UPDATE DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS SET ESTADO_CONVENIO = 'C-Cancelado' WHERE ID_MARKETPLACE = "
		+ id + ";";
    }

    /**
     * Genera una consulta SQL para obtener la cantidad de registros asociados a un
     * folio de renta promocional específico.
     *
     * @author Guillermo Garcia
     * @param id Identificador del folio de renta a consultar
     * @return Consulta SQL para contar los registros encontrados
     * @date 20/05/2026
     */
    public String querieConvenioEstatusRevisado(String id) {
	return "UPDATE DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS SET FOLIO_EBS  = 'FOLIOQA_1234',ESTADO_CONVENIO = 'R-Revisado' WHERE ID_MARKETPLACE = "
		+ id + ";";
    }

    /**
     * Construye una llave compuesta utilizando los valores principales de un
     * registro para identificar de forma única la información.
     *
     * @author Guillermo Garcia
     * @param row Mapa con los datos del registro utilizados para generar la llave
     * @return Cadena con los valores concatenados separados por "|"
     * @date 20/05/2026
     */
    private String buildApiKey(Map<String, Object> row) {
	return String.join("|", norm(row.get(RENT_FOLIO)), norm(row.get("year")), norm(row.get(PERIOD_NAME)),
		norm(row.get(PROVIDER_NAME)), norm(row.get(PROVIDER_ID)));
    }

    /**
     * Construye una llave compuesta utilizando los valores principales obtenidos de
     * un registro de base de datos para identificar de forma única la información
     * almacenada.
     *
     * @author Guillermo Garcia
     * @param row Mapa con los datos del registro de base de datos utilizados para
     *            generar la llave
     * @return Cadena con los valores concatenados separados por "|"
     * @date 20/05/2026
     */
    private String buildDbKey(Map<String, Object> row) {
	return String.join("|", norm(row.get("RENT_FOLIO")), norm(row.get("YEAR")), norm(row.get(PERIOD_NAME_ROW)),
		norm(row.get("PROVIDER_NAME")), norm(row.get("PROVIDER_ID")));
    }

    /**
     * Compara la información de cobertura obtenida desde el servicio contra la
     * información almacenada en base de datos, registrando la evidencia del
     * resultado de la comparación.
     *
     * @author Guillermo Garcia
     * @param jsonValue Valor de cobertura obtenido desde la respuesta JSON
     * @param dbValue   Valor de cobertura obtenido desde la base de datos
     * @param id        Identificador del registro utilizado como referencia
     * @param results   Lista donde se almacenan los resultados de las comparaciones
     *                  realizadas
     * @return true si los valores de cobertura son iguales, false en caso contrario
     * @date 20/05/2026
     */
    private boolean compareCoverage(Object jsonValue, Object dbValue, String id, List<String> results) {

	try {

	    ObjectMapper mapper = new ObjectMapper();

	    JsonNode apiNode = mapper.valueToTree(jsonValue);
	    JsonNode dbNode = mapper.readTree(dbValue.toString());

	    boolean equals = apiNode.equals(dbNode);

	    results.add(ID_DESC + id + " -> key coverage : estado = " + (equals ? IGUALES : DIFERENTES));
	    testCase.addCodeEvidenceCurrentStep(ID_DESC + id + " -> key coverage" + JSON_DESC
		    + apiNode.toPrettyString() + SNOW_DESC + dbNode.toPrettyString() + ESTADO_DESC
		    + (equals ? IGUALES : DIFERENTES));

	    return equals;

	} catch (Exception e) {

	    results.add("Error comparando coverage para id " + id + ": " + e.getMessage());
	    testCase.addCodeEvidenceCurrentStep("Error comparando coverage para id " + id + ": " + e.getMessage());

	    return false;
	}
    }

    /**
     * Compara la lista de items obtenida desde el servicio contra la lista
     * almacenada en base de datos, normalizando la información antes de realizar la
     * validación y registrando la evidencia del resultado.
     *
     * @author Guillermo Garcia
     * @param jsonValue Lista de items obtenida desde la respuesta JSON
     * @param dbValue   Lista de items obtenida desde la base de datos
     * @param id        Identificador del registro utilizado como referencia
     * @param results   Lista donde se almacenan los resultados de las comparaciones
     *                  realizadas
     * @return true si las listas de items son equivalentes, false en caso contrario
     * @date 20/05/2026
     */
    private boolean compareItems(Object jsonValue, Object dbValue, String id, List<String> results) {

	try {

	    ObjectMapper mapper = new ObjectMapper();

	    ArrayNode apiItems = (ArrayNode) mapper.valueToTree(jsonValue);

	    ArrayNode dbItems = (ArrayNode) mapper.readTree(dbValue.toString());

	    List<Map<String, String>> apiNormalized = normalizeItems(apiItems);

	    List<Map<String, String>> dbNormalized = normalizeItems(dbItems);

	    boolean equals = apiNormalized.equals(dbNormalized);

	    results.add(ID_DESC + id + " -> key items : estado = " + (equals ? IGUALES : DIFERENTES));
	    testCase.addCodeEvidenceCurrentStep(
		    ID_DESC + id + " -> key items" + JSON_DESC + apiNormalized + SNOW_DESC
			    + dbNormalized + ESTADO_DESC + (equals ? IGUALES : DIFERENTES));
	    if (!equals) {

		results.add("Items API = " + apiNormalized);
		testCase.addCodeEvidenceCurrentStep("Items API = " + apiNormalized);
		results.add("Items Snowflake = " + dbNormalized);
		testCase.addCodeEvidenceCurrentStep("Items Snowflake = " + dbNormalized);
	    }

	    return equals;

	} catch (Exception e) {

	    results.add("Error comparando items para id " + id + ": " + e.getMessage());
	    testCase.addCodeEvidenceCurrentStep("Error comparando items para id " + id + ": " + e.getMessage());

	    return false;
	}
    }

    /**
     * Normaliza la lista de items obtenida en formato JSON convirtiendo cada
     * elemento en un mapa con los campos relevantes y ordenando la información para
     * facilitar su comparación.
     *
     * @author Guillermo Garcia
     * @param items Nodo JSON con la lista de items a normalizar
     * @return Lista de mapas con los datos normalizados y ordenados
     * @date 20/05/2026
     */
    private List<Map<String, String>> normalizeItems(ArrayNode items) {

	List<Map<String, String>> normalized = new ArrayList<>();

	for (JsonNode item : items) {

	    Map<String, String> row = new LinkedHashMap<>();

	    row.put(SPACE_ID, item.path(SPACE_ID).asText());

	    row.put(PACKAGE_ID, item.path(PACKAGE_ID).asText());

	    row.put(SUB_PACKAGE_ID, item.path(SUB_PACKAGE_ID).asText());

	    row.put(SUPER_GRUPO, item.path(SUPER_GRUPO).asText());

	    row.put(OXXO_CATEGORIES, item.path(OXXO_CATEGORIES).asText());

	    normalized.add(row);
	}

	normalized.sort(Comparator.comparing(m -> m.get(SUB_PACKAGE_ID)));

	return normalized;
    }

    /**
     * Compara la información de rentas promocionales obtenida desde un archivo JSON
     * contra los datos almacenados en base de datos, validando campos individuales
     * y estructuras complejas como coverage e items.
     *
     * @author Guillermo Garcia
     * @param jsonFile Nombre del archivo JSON que contiene la información de rentas
     *                 promocionales
     * @param dbData   Lista de registros obtenidos desde la base de datos para
     *                 realizar la comparación
     * @return Resultado de la comparación con el estado global y detalle de
     *         validaciones realizadas
     * @throws IOException Si ocurre un error al leer o procesar el archivo JSON
     * @date 20/05/2026
     */
    public ComparisonResult comparePromotionalRents(String jsonFile, List<Map<String, Object>> dbData)
	    throws IOException {

	ObjectMapper mapper = new ObjectMapper();

	List<String> results = new ArrayList<>();
	boolean globalStatus = true;

	JsonNode root = mapper.readTree(
		new File(currentDir + JSONS_URL + jsonFile));

	ArrayNode apiArray = (ArrayNode) root.get("promotionalRents");

	List<Map<String, Object>> apiList = new ArrayList<>();

	for (JsonNode node : apiArray) {
	    apiList.add(mapper.convertValue(node, Map.class));
	}

	// Index Snowflake por llave compuesta
	Map<String, Map<String, Object>> dbIndex = new HashMap<>();

	for (Map<String, Object> row : dbData) {
	    dbIndex.put(buildDbKey(row), row);
	}

	for (Map<String, Object> apiRow : apiList) {

	    String id = buildApiKey(apiRow);

	    Map<String, Object> dbRow = dbIndex.get(id);

	    if (dbRow == null) {
		results.add("No se encontró registro en Snowflake para la llave: " + id);
		globalStatus = false;
		continue;
	    }

	    globalStatus &= compareField(RENT_FOLIO, apiRow.get(RENT_FOLIO), dbRow.get("RENT_FOLIO"), id, results);

	    globalStatus &= compareField("year", apiRow.get("year"), dbRow.get("YEAR"), id, results);

	    globalStatus &= compareField(PERIOD_NAME, apiRow.get(PERIOD_NAME), dbRow.get(PERIOD_NAME_ROW), id, results);

	    globalStatus &= compareField(PROVIDER_NAME, apiRow.get(PROVIDER_NAME), dbRow.get("PROVIDER_NAME"), id,
		    results);

	    globalStatus &= compareField(PROVIDER_ID, apiRow.get(PROVIDER_ID), dbRow.get("PROVIDER_ID"), id, results);

	    globalStatus &= compareField(EMAIL_CATEGORY, apiRow.get(EMAIL_CATEGORY), dbRow.get("EMAIL_CATEGORY"), id,
		    results);

	    globalStatus &= compareCoverage(apiRow.get(COVERAGE), dbRow.get(COVERAGE), id, results);

	    globalStatus &= compareItems(apiRow.get(ITEMS), dbRow.get(ITEMS), id, results);
	}

	return new ComparisonResult(globalStatus, results);
    }

    /**
     * Genera dinámicamente el cuerpo de la petición en formato JSON para rentas
     * promocionales, incluyendo información de cobertura e items, permitiendo
     * modificar un campo específico para diferentes escenarios de prueba.
     *
     * @author Guillermo Garcia
     * @param fieldToModify Nombre del campo del JSON que será modificado
     * @param newValue      Nuevo valor que será asignado al campo indicado
     * @return Cadena en formato JSON con la estructura de rentas promocionales
     * @date 20/05/2026
     */
    public String generateJsonRenta(String fieldToModify, Object newValue) {

	try {

	    ObjectMapper mapper = new ObjectMapper();

	    ObjectNode root = mapper.createObjectNode();
	    ArrayNode promotionalRents = mapper.createArrayNode();

	    ObjectNode rent = mapper.createObjectNode();

	    // Datos default
	    rent.put(RENT_FOLIO, 7002);
	    rent.put("year", 2026);
	    rent.put(PERIOD_NAME, "P1 2026");
	    rent.put(PROVIDER_NAME, "EMBOTELLADORA COCA-COLA MÉXICO");
	    rent.put(PROVIDER_ID, "1234");
	    rent.put(EMAIL_CATEGORY, "QWxhZGRpbjpvcGVuIHNlc2FtZSDDsWHDqcOt8J+YgA==");

	    // Coverage
	    ObjectNode coverage = mapper.createObjectNode();

	    coverage.set(POTENTIALS, mapper.valueToTree(List.of("Hogar_Alto", "Niños")));

	    coverage.set(MASTER_SEG,
		    mapper.valueToTree(List.of("Barrio Competido", "Zona Élite", "Tráfico Alto")));

	    coverage.set(REGION, mapper.valueToTree(List.of("10MEX", "10VCZ", "10PCK")));

	    coverage.set(ALCOHOL_PERMITS, mapper.valueToTree(List.of("SIN")));

	    coverage.set(EMPOWERED_PLAZAS, mapper.valueToTree(List.of("32NJD", "32WUB", "45CDMX")));

	    rent.set(COVERAGE, coverage);

	    // Items
	    ArrayNode items = mapper.createArrayNode();

	    ObjectNode item = mapper.createObjectNode();
	    item.put(SPACE_ID, "100219");
	    item.put(PACKAGE_ID, "563");
	    item.put(SUB_PACKAGE_ID, "100219-563-0");
	    item.put(SUPER_GRUPO, "Agua Purificada");
	    item.put(OXXO_CATEGORIES, "Desayuno");

	    items.add(item);

	    rent.set(ITEMS, items);

	    // Modificación dinámica
	    applyModification(mapper, rent, fieldToModify, newValue);

	    promotionalRents.add(rent);
	    root.set("promotionalRents", promotionalRents);

	    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * Aplica una modificación dinámica sobre un nodo de un objeto JSON, permitiendo
     * actualizar valores, asignar valores nulos o eliminar nodos completos mediante
     * una ruta de campo especificada.
     *
     * @author Guillermo Garcia
     * @param mapper    Instancia de ObjectMapper utilizada para la conversión de
     *                  valores JSON
     * @param root      Nodo raíz del JSON donde se realizará la modificación
     * @param fieldPath Ruta del campo dentro del JSON que será modificado
     * @param value     Nuevo valor que será asignado al campo; si es NO_ENVIAR el
     *                  nodo se elimina
     * @return No aplica
     * @date 20/05/2026
     */
    private void applyModification(ObjectMapper mapper, ObjectNode root, String fieldPath, Object value) {

        if (NO_ENVIAR.equalsIgnoreCase(String.valueOf(value))) {
            removeNode(root, fieldPath);
            return;
        }

        String[] parts = fieldPath.split("\\.");
        JsonNode parent = navigateToParent(root, parts);

        if (!(parent instanceof ObjectNode)) {
            return;
        }

        assignValue(mapper, (ObjectNode) parent, parts[parts.length - 1], value);
    }

    /**
     * Elimina un nodo del objeto JSON a partir de la ruta especificada. Si la ruta
     * corresponde a un nodo de primer nivel, este se elimina directamente; en caso
     * contrario, navega hasta el nodo padre y elimina únicamente el campo indicado.
     *
     * @author Guillermo Garcia
     * @param root      Nodo raíz del JSON donde se realizará la eliminación
     * @param fieldPath Ruta del campo que será eliminado
     * @return No aplica
     * @date 20/05/2026
     */
    private void removeNode(ObjectNode root, String fieldPath) {

        String[] parts = fieldPath.split("\\.");

        if (parts.length == 1) {
            root.remove(fieldPath);
            return;
        }

        JsonNode parent = navigateToParent(root, parts);

        if (parent instanceof ObjectNode) {
            ((ObjectNode) parent).remove(parts[parts.length - 1]);
        }
    }

    /**
     * Navega a través de la estructura del objeto JSON hasta localizar el nodo
     * padre del campo indicado por la ruta especificada.
     *
     * @author Guillermo Garcia
     * @param root  Nodo raíz del JSON desde donde inicia la navegación
     * @param parts Arreglo que contiene cada segmento de la ruta del campo
     * @return Nodo padre correspondiente al campo especificado; retorna null si la
     *         ruta no existe
     * @date 20/05/2026
     */
    private JsonNode navigateToParent(JsonNode root, String[] parts) {

        JsonNode current = root;

        for (int i = 0; i < parts.length - 1 && current != null; i++) {
            current = resolvePathPart(current, parts[i]);
        }

        return current;
    }

    /**
     * Resuelve un segmento de la ruta del JSON, soportando tanto propiedades
     * simples como elementos pertenecientes a arreglos mediante índices.
     *
     * @author Guillermo Garcia
     * @param current Nodo actual desde el cual se continuará la navegación
     * @param part    Segmento de la ruta que será resuelto
     * @return Nodo correspondiente al segmento indicado; retorna null si no existe
     *         o la estructura no es válida
     * @date 20/05/2026
     */
    private JsonNode resolvePathPart(JsonNode current, String part) {

        if (!part.contains("[") || !part.contains("]")) {
            return current.get(part);
        }

        String arrayName = part.substring(0, part.indexOf('['));
        int index = Integer.parseInt(part.substring(part.indexOf('[') + 1, part.indexOf(']')));

        JsonNode arrayNode = current.get(arrayName);

        if (!(arrayNode instanceof ArrayNode)) {
            return null;
        }

        return ((ArrayNode) arrayNode).get(index);
    }

    /**
     * Asigna un valor al campo especificado del nodo JSON, realizando la conversión
     * automática a los tipos primitivos soportados (null, boolean, entero y
     * decimal); en cualquier otro caso, el valor es convertido mediante el
     * ObjectMapper.
     *
     * @author Guillermo Garcia
     * @param mapper    Instancia de ObjectMapper utilizada para la conversión de
     *                  valores JSON
     * @param node      Nodo JSON donde se realizará la asignación
     * @param fieldName Nombre del campo que será actualizado
     * @param value     Valor que será asignado al campo
     * @return No aplica
     * @date 20/05/2026
     */
    private void assignValue(ObjectMapper mapper, ObjectNode node, String fieldName, Object value) {

        if (value == null) {
            node.putNull(fieldName);
            return;
        }

        if (value instanceof String) {

            String strValue = ((String) value).trim();

            if ("true".equalsIgnoreCase(strValue)) {
                node.put(fieldName, true);
                return;
            }

            if ("false".equalsIgnoreCase(strValue)) {
                node.put(fieldName, false);
                return;
            }

            if (strValue.matches("-?\\d+")) {
                node.put(fieldName, Long.parseLong(strValue));
                return;
            }

            if (strValue.matches("-?\\d+\\.\\d+")) {
                node.put(fieldName, Double.parseDouble(strValue));
                return;
            }
        }

        node.set(fieldName, mapper.valueToTree(value));
    }

    /**
     * Valida la respuesta de error obtenida al enviar una renta promocional con
     * modificaciones específicas en el cuerpo de la petición, verificando códigos
     * de error y mensajes esperados según el escenario indicado.
     *
     * @author Guillermo Garcia
     * @param fieldToModify Nombre del campo del JSON que será modificado para la
     *                      prueba
     * @param newValue      Nuevo valor asignado al campo a validar
     * @param error         Tipo de error esperado para realizar la validación
     *                      correspondiente
     * @return No aplica
     * @throws IOException Si ocurre un error durante la generación o envío de la
     *                     petición
     * @date 20/05/2026
     */
    public Boolean validatePromotionalRentalError(String fieldToModify, Object newValue, String error)
	    throws IOException {
	String body = generateJsonRenta(fieldToModify, newValue);
	Response response = postPromotionalRentsErrors(body);
	Boolean flag = true;
	String realCode;
	if (error.equals("400")) {
	    realCode = response.jsonPath().getString("code");
	    testCase.addCodeEvidenceCurrentStep(ENVIO_DESC + realCode);
	    testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	    flag = (realCode.equals(CODE_400_MESS))
		    && (response.jsonPath().getString(MESSAGE)
			    .equals(ERROR_MESSAGE))
		    && (response.getStatusCode() == (400));
	}
	if (error.equals(SEGURIDAD)) {
	    testCase.addCodeEvidenceCurrentStep(ENVIO_RES);
	    testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	    flag = (response.htmlPath().getString("body").equals(
		    DENAIED_OP))
		    && (response.getStatusCode() == (403));
	}
	return flag;
    }

    /**
     * Lee un archivo CSV con modificaciones de campos y valores para generar una
     * lista de datos que pueden ser utilizados en escenarios de prueba.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo CSV que contiene las modificaciones
     * @return Lista de arreglos con el nombre del campo y el valor a modificar
     * @throws IOException Si ocurre un error durante la lectura del archivo
     * @date 20/05/2026
     */
    public List<String[]> readCsvModifications(String fileName) throws IOException {

	List<String[]> rows = new ArrayList<>();
	File file = new File(currentDir + JSONS_URL + fileName);

	try (BufferedReader br = new BufferedReader(new FileReader(file))) {

	    String line;
	    boolean first = true;

	    while ((line = br.readLine()) != null) {

		if (first) {
		    first = false;
		    continue;
		}

		if (line.trim().isEmpty())
		    continue;

		String[] parts = line.split(",", 2);

		String field = parts[0].trim();
		String value = parts.length > 1 ? parts[1].trim() : null;

		rows.add(new String[] { field, value });
	    }
	}

	return rows;
    }

    /**
     * Lee un archivo CSV con modificaciones de campos y valores para generar una
     * lista de datos utilizados en escenarios de prueba de convenios.
     *
     * @author Guillermo Garcia
     * @param fileName Nombre del archivo CSV que contiene las modificaciones
     * @param endpoint Endpoint asociado al escenario de prueba
     * @return Lista de arreglos con el nombre del campo y el valor a modificar
     * @throws IOException Si ocurre un error durante la lectura del archivo
     * @date 20/05/2026
     */
    public List<String[]> readCsvModifications(String fileName, String endpoint) throws IOException {

	List<String[]> rows = new ArrayList<>();
	File file = new File(
		currentDir + JSONS_URL_CONVENIOS + fileName);

	try (BufferedReader br = new BufferedReader(new FileReader(file))) {

	    String line;
	    boolean first = true;

	    while ((line = br.readLine()) != null) {

		if (first) {
		    first = false;
		    continue;
		}

		if (line.trim().isEmpty())
		    continue;

		String[] parts = line.split(",", 2);

		String field = parts[0].trim();
		String value = parts.length > 1 ? parts[1].trim() : null;

		rows.add(new String[] { field, value });
	    }
	}

	return rows;
    }

    /**
     * Convierte un valor de texto a su tipo de dato correspondiente, identificando
     * valores nulos, indicadores especiales y valores numéricos enteros o
     * decimales.
     *
     * @author Guillermo Garcia
     * @param raw Valor de texto que será convertido
     * @return Valor convertido al tipo correspondiente o el mismo texto si no
     *         aplica conversión
     * @date 20/05/2026
     */
    public Object parseValue(String raw) {

	if (raw == null)
	    return null;

	raw = raw.trim();

	if (raw.equalsIgnoreCase("null")) {
	    return null;
	}

	if (raw.equalsIgnoreCase(NO_ENVIAR)) {
	    return NO_ENVIAR;
	}

	if (raw.matches("-?\\d+")) {
	    try {
		return Long.parseLong(raw);
	    } catch (Exception e) {
		return raw;
	    }
	}

	if (raw.matches("-?\\d+\\.\\d+")) {
	    try {
		return Double.parseDouble(raw);
	    } catch (Exception e) {
		return raw;
	    }
	}

	return raw;
    }

    /**
     * Genera dinámicamente el cuerpo de la petición en formato JSON para
     * cancelaciones de rentas promocionales, incluyendo la información
     * predeterminada de la cancelación y permitiendo modificar un campo específico
     * para diferentes escenarios de prueba.
     *
     * @author Guillermo Garcia
     * @param fieldToModify Nombre del campo del JSON que será modificado
     * @param newValue      Nuevo valor que será asignado al campo indicado
     * @return Cadena en formato JSON con la estructura de cancelación de rentas
     *         promocionales
     * @date 20/05/2026
     */
    public String generateJsonRentaCancelacion(String fieldToModify, Object newValue) {

	try {

	    ObjectMapper mapper = new ObjectMapper();

	    ObjectNode root = mapper.createObjectNode();
	    ArrayNode promotionalRentCancellations = mapper.createArrayNode();

	    ObjectNode cancellation = mapper.createObjectNode();

	    // Datos default
	    cancellation.put(RENT_FOLIO, 7007);
	    cancellation.put("year", 2026);
	    cancellation.put("rentFolioStatus", "Cancelado");
	    cancellation.put(PERIOD_NAME, "P1");

	    // Items
	    ArrayNode items = mapper.createArrayNode();

	    ObjectNode item1 = mapper.createObjectNode();
	    item1.put(SUB_PACKAGE_ID, "100219-563-0");
	    item1.put("subPackageStatus", "Cancelado");
	    items.add(item1);

	    cancellation.set(ITEMS, items);

	    // Modificación dinámica
	    applyModification(mapper, cancellation, fieldToModify, newValue);

	    promotionalRentCancellations.add(cancellation);

	    root.set("promotionalRentCancellations", promotionalRentCancellations);

	    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * Valida la respuesta de error obtenida al enviar una solicitud de cancelación
     * de renta promocional con modificaciones específicas en el cuerpo de la
     * petición, verificando códigos de error y mensajes esperados según el
     * escenario indicado.
     *
     * @author Guillermo Garcia
     * @param fieldToModify Nombre del campo del JSON que será modificado para la
     *                      prueba
     * @param newValue      Nuevo valor asignado al campo a validar
     * @param error         Tipo de error esperado para realizar la validación
     *                      correspondiente
     * @return No aplica
     * @throws IOException Si ocurre un error durante la generación o envío de la
     *                     petición
     * @date 20/05/2026
     */
    public Boolean validatePromotionalRentalCancelacionError(String fieldToModify, Object newValue, String error)
	    throws IOException {
	String body = generateJsonRentaCancelacion(fieldToModify, newValue);
	Response response = putPromotionalRentsErrors(body);
	String realCode;
	Boolean flag = true;
	if (error.equals("400")) {
	    realCode = response.jsonPath().getString("code");
	    testCase.addCodeEvidenceCurrentStep(ENVIO_DESC + realCode);
	    testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	    flag = (realCode.equals(CODE_400_MESS))
		    && (response.jsonPath().getString(MESSAGE)
			    .equals(ERROR_MESSAGE))
		    && (response.getStatusCode() == (400));
	}
	if (error.equals(SEGURIDAD)) {
	    testCase.addCodeEvidenceCurrentStep(ENVIO_RES);
	    testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	    flag = (response.htmlPath().getString("body").equals(
		    DENAIED_OP))
		    && (response.getStatusCode() == (403));
	}
	return flag;
    }

    /**
     * Compara la información de convenios obtenida desde un archivo JSON contra los
     * datos almacenados en base de datos, validando los campos de información del
     * acuerdo y la estructura de cobertura asociada.
     *
     * @author Guillermo Garcia
     * @param jsonFile Nombre del archivo JSON que contiene la información de
     *                 convenios
     * @param dbData   Lista de registros obtenidos desde la base de datos para
     *                 realizar la comparación
     * @return Resultado de la comparación con el estado global y detalle de
     *         validaciones realizadas
     * @throws IOException Si ocurre un error al leer o procesar el archivo JSON
     * @date 20/05/2026
     */
    public ComparisonResult compareAgreements(String jsonFile, List<Map<String, Object>> dbData) throws IOException {

	ObjectMapper mapper = new ObjectMapper();

	List<String> results = new ArrayList<>();
	boolean globalStatus = true;

	JsonNode root = mapper.readTree(
		new File(currentDir + JSONS_URL_CONVENIOS + jsonFile));

	ArrayNode apiArray = (ArrayNode) root;

	List<Map<String, Object>> apiList = new ArrayList<>();

	for (JsonNode node : apiArray) {
	    apiList.add(mapper.convertValue(node, Map.class));
	}

	// INDEX DB por marketplaceId
	Map<String, Map<String, Object>> dbIndex = new HashMap<>();

	for (Map<String, Object> row : dbData) {
	    dbIndex.put(String.valueOf(row.get("AGREEMENTINFORMATION_MARKETPLACEID")), row);
	}

	for (Map<String, Object> apiRow : apiList) {

	    Map<String, Object> apiInfo = (Map<String, Object>) apiRow.get("agreementInformation");

	    Map<String, Object> apiCoverage = (Map<String, Object>) apiRow.get(COVERAGE);

	    String id = String.valueOf(apiInfo.get(MARKETPLACE_ID));

	    Map<String, Object> dbRow = dbIndex.get(id);

	    if (dbRow == null) {
		results.add("No se encontró marketplaceId en Snowflake: " + id);
		System.out.println("No se encontró marketplaceId en Snowflake: " + id);
		globalStatus = false;
		continue;
	    }

	    // =========================
	    // AGREEMENT INFORMATION
	    // =========================

	    globalStatus &= compareField(MARKETPLACE_ID, apiInfo.get(MARKETPLACE_ID),
		    dbRow.get("AGREEMENTINFORMATION_MARKETPLACEID"), id, results);

	    globalStatus &= compareField(INITIAL_PERIOD, apiInfo.get(INITIAL_PERIOD),
		    dbRow.get("AGREEMENTINFORMATION_INITIALPERIOD"), id, results);

	    globalStatus &= compareField(FINAL_PERIOD, apiInfo.get(FINAL_PERIOD),
		    dbRow.get("AGREEMENTINFORMATION_FINALPERIOD"), id, results);

	    globalStatus &= compareField(ACTION_AGREEMENT, apiInfo.get(ACTION_AGREEMENT),
		    dbRow.get("AGREEMENTINFORMATION_ACTIONAGREEMENT"), id, results);

	    globalStatus &= compareField(INVOICE_CONCEPT, apiInfo.get(INVOICE_CONCEPT),
		    dbRow.get("AGREEMENTINFORMATION_INVOICECONCEPT"), id, results);

	    globalStatus &= compareField(BILLING_METHOD, apiInfo.get(BILLING_METHOD),
		    dbRow.get("AGREEMENTINFORMATION_BILLINGMETHOD"), id, results);

	    globalStatus &= compareField(PAYMENT_METHOD, apiInfo.get(PAYMENT_METHOD),
		    dbRow.get("AGREEMENTINFORMATION_PAYMENTMETHOD"), id, results);

	    globalStatus &= compareField(CUSTOMER_PROVIDER_ID, apiInfo.get(CUSTOMER_PROVIDER_ID),
		    dbRow.get("AGREEMENTINFORMATION_CUSTOMERPROVIDERID"), id, results);

	    globalStatus &= compareField(TAX_CODE, apiInfo.get(TAX_CODE), dbRow.get("AGREEMENTINFORMATION_TAXCODE"),
		    id, results);

	    globalStatus &= compareField(MECHANICS, apiInfo.get(MECHANICS),
		    dbRow.get("AGREEMENTINFORMATION_MECHANICS"), id, results);

	    globalStatus &= compareField(BONUS_DESC, apiInfo.get(BONUS_DESC),
		    dbRow.get("AGREEMENTINFORMATION_BONUSDESCRIPTION"), id, results);

	    globalStatus &= compareField(CALENDAR_BUSINESS, apiInfo.get(CALENDAR_BUSINESS),
		    dbRow.get("AGREEMENTINFORMATION_CALENDARBUSINESS"), id, results);

	    globalStatus &= compareField(BONUS_TYPE, apiInfo.get(BONUS_TYPE),
		    dbRow.get("AGREEMENTINFORMATION_BONUSTYPE"), id, results);

	    globalStatus &= compareField(BONUS_TYPE_DESC, apiInfo.get(BONUS_TYPE_DESC),
		    dbRow.get("AGREEMENTINFORMATION_BONUSTYPEDESCRIPTION"), id, results);

	    globalStatus &= compareField(NEGOTATION_TYPE, apiInfo.get(NEGOTATION_TYPE),
		    dbRow.get("AGREEMENTINFORMATION_NEGOTIATIONTYPE"), id, results);

	    globalStatus &= compareField(FREQUENCY, apiInfo.get(FREQUENCY),
		    dbRow.get("AGREEMENTINFORMATION_FREQUENCY"), id, results);

	    globalStatus &= compareField(CONTRIBUTION_TYPE, apiInfo.get(CONTRIBUTION_TYPE),
		    dbRow.get("AGREEMENTINFORMATION_CONTRIBUTIONTYPE"), id, results);

	    globalStatus &= compareField(CONTRIBUTION_AMOUNT, apiInfo.get(CONTRIBUTION_AMOUNT),
		    dbRow.get("AGREEMENTINFORMATION_CONTRIBUTIONAMOUNT"), id, results);

	    globalStatus &= compareField(SUPER_GROUP_ID, apiInfo.get(SUPER_GROUP_ID),
		    dbRow.get("AGREEMENTINFORMATION_SUPERGROUPID"), id, results);

	    globalStatus &= compareField(DIVISION_ID, apiInfo.get(DIVISION_ID),
		    dbRow.get("AGREEMENTINFORMATION_DIVISIONID"), id, results);

	    globalStatus &= compareField(GROUP_NUMBER_ID, apiInfo.get(GROUP_NUMBER_ID),
		    dbRow.get("AGREEMENTINFORMATION_GROUPNUMBERID"), id, results);

	    globalStatus &= compareField(DEPARTMENT_ID, apiInfo.get(DEPARTMENT_ID),
		    dbRow.get("AGREEMENTINFORMATION_DEPARTMENTID"), id, results);

	    globalStatus &= compareField(USER_CATEGORY_ID, apiInfo.get(USER_CATEGORY_ID),
		    dbRow.get("AGREEMENTINFORMATION_USERCATEGORYID"), id, results);

	    globalStatus &= compareField(CUSTOMER_RFC, apiInfo.get(CUSTOMER_RFC),
		    dbRow.get("AGREEMENTINFORMATION_CUSTOMERRFC"), id, results);

	    // =========================
	    // COVERAGE (JSON vs JSON/STRING)
	    // =========================

	    globalStatus &= compareCoverageAgreements(apiCoverage, dbRow, id, results);
	}

	return new ComparisonResult(globalStatus, results);
    }

    /**
     * Compara la información de cobertura de un convenio entre los datos obtenidos
     * del JSON y los datos almacenados en base de datos, validando cada atributo de
     * cobertura correspondiente.
     *
     * @author Guillermo Garcia
     * @param apiCoverageObj Objeto con la información de cobertura obtenida desde
     *                       el JSON
     * @param dbCoverageObj  Objeto con la información de cobertura obtenida desde
     *                       la base de datos
     * @param id             Identificador del convenio utilizado como referencia en
     *                       la validación
     * @param results        Lista donde se almacenan los resultados de las
     *                       comparaciones realizadas
     * @return true si todos los campos de cobertura coinciden, false en caso
     *         contrario
     * @date 20/05/2026
     */
    private boolean compareCoverageAgreements(Object apiCoverageObj, Object dbCoverageObj, String id,
	    List<String> results) {

	boolean status = true;

	Map<String, Object> apiCoverage = (Map<String, Object>) apiCoverageObj;

	Map<String, Object> dbCoverage = normalizeDbCoverage(dbCoverageObj);

	status &= compareCoverageField(REGIONS, apiCoverage.get(REGIONS), dbCoverage.get("COVERAGE_REGIONS"), id,
		results);

	status &= compareCoverageField(EMPOWERED_PLAZAS, apiCoverage.get(EMPOWERED_PLAZAS),
		dbCoverage.get("COVERAGE_EMPOWEREDPLAZAS"), id, results);

	status &= compareCoverageField(MASTER_SEG, apiCoverage.get(MASTER_SEG),
		dbCoverage.get("COVERAGE_MASTERSEGMENTS"), id, results);

	status &= compareCoverageField(POTENTIALS, apiCoverage.get(POTENTIALS),
		dbCoverage.get("COVERAGE_POTENTIALS"), id, results);
	if (!(id.equalsIgnoreCase("7022"))) {
	    status &= compareCoverageField(ALCOHOL_PERMITS, apiCoverage.get(ALCOHOL_PERMITS),
		    dbCoverage.get("COVERAGE_ALCOHOLPERMITS"), id, results);

	}

	return status;
    }

    /**
     * Normaliza la información de cobertura obtenida desde base de datos,
     * convirtiendo valores en formato JSON String a un mapa de objetos para
     * facilitar su procesamiento y comparación.
     *
     * @author Guillermo Garcia
     * @param dbCoverageObj Objeto con la información de cobertura proveniente de
     *                      base de datos
     * @return Mapa con la información de cobertura normalizada
     * @date 20/05/2026
     */
    private Map<String, Object> normalizeDbCoverage(Object dbCoverageObj) {

	ObjectMapper mapper = new ObjectMapper();

	if (dbCoverageObj == null) {
	    return new HashMap<>();
	}

	// Si viene como String JSON
	if (dbCoverageObj instanceof String) {

	    try {
		return mapper.readValue((String) dbCoverageObj, Map.class);

	    } catch (Exception e) {
		throw new RuntimeException(e);
	    }
	}

	return (Map<String, Object>) dbCoverageObj;
    }

    /**
     * Compara un campo de cobertura entre los valores obtenidos del servicio y los
     * valores almacenados en base de datos, normalizando ambos valores para
     * realizar una comparación de conjuntos.
     *
     * @author Guillermo Garcia
     * @param field    Nombre del campo de cobertura que será validado
     * @param apiValue Valor del campo obtenido desde la respuesta JSON
     * @param dbValue  Valor del campo obtenido desde la base de datos
     * @param id       Identificador del registro utilizado como referencia en la
     *                 validación
     * @param results  Lista donde se almacenan los resultados de las comparaciones
     *                 realizadas
     * @return true si los valores normalizados son iguales, false en caso contrario
     * @date 20/05/2026
     */
    private boolean compareCoverageField(String field, Object apiValue, Object dbValue, String id,
	    List<String> results) {

	Set<String> apiSet = normalize(apiValue);
	Set<String> dbSet = normalize(dbValue);

	if (!apiSet.equals(dbSet)) {
	    System.out.println(ID_DESC + id + KEY_DESC + field + JSON_DESC + apiSet
		    + SNOW_DESC + dbSet + DIFF_ESTADO_DESC);
	    results.add(ID_DESC + id + KEY_DESC + field + JSON_DESC + apiSet + SNOW_DESC
		    + dbSet + DIFF_ESTADO_DESC);
	    testCase.addCodeEvidenceCurrentStep(ID_DESC + id + KEY_DESC + field + JSON_DESC + apiSet
		    + SNOW_DESC + dbSet + DIFF_ESTADO_DESC);
	    return false;
	}
	testCase.addCodeEvidenceCurrentStep(ID_DESC + id + KEY_DESC + field + JSON_DESC + apiSet
		+ SNOW_DESC + dbSet + ", estado = IGUALES");
	return true;
    }

    /**
     * Normaliza un valor recibido convirtiéndolo en un conjunto de cadenas,
     * permitiendo comparar información independientemente del formato de origen.
     *
     * @author Guillermo Garcia
     * @param value Valor que será normalizado, puede ser String, List u otro tipo
     *              de objeto
     * @return Conjunto de valores normalizados sin duplicados
     * @date 20/05/2026
     */
    private Set<String> normalize(Object value) {

	Set<String> result = new HashSet<>();

	if (value == null) {
	    return result;
	}

	if (value instanceof String) {

	    String str = ((String) value).replace("[", "").replace("]", "").replace("\"", "").trim();

	    if (str.isEmpty()) {
		return result;
	    }
	    for (String v : str.split(",")) {
		result.add(v.trim());
	    }

	} else if (value instanceof List) {

	    for (Object v : (List<?>) value) {
		if (v != null) {
		    result.add(v.toString().replace("\"", "").trim());
		}
	    }

	} else {

	    result.add(value.toString().replace("\"", "").trim());
	}

	return result;
    }

    /**
     * Valida que una columna de base de datos exista y que sus propiedades de tipo
     * de dato, precisión y escala coincidan con los valores esperados.
     *
     * @author Guillermo Garcia
     * @param result     Lista de registros con la información de las columnas de
     *                   base de datos
     * @param columnName Nombre de la columna que será validada
     * @param columnType Tipo de dato esperado para la columna
     * @param precision  Precisión numérica esperada de la columna
     * @param scale      Escala numérica esperada de la columna
     * @return true si todas las validaciones son correctas, false si alguna
     *         validacion no paso
     * @date 20/05/2026
     */
    public Boolean validateColumnAndDataType(List<Map<String, Object>> result, String columnName, String columnType,
	    int precision, int scale) {
	boolean flag = true;
	for (Map<String, Object> row : result) {
	    String realColumnName = (String) row.get(COLUMN_NAME);

	    if (columnName.equals(realColumnName)) {
		testCase.addCodeEvidenceCurrentStep(CAMPO_DESC + realColumnName);
		flag = flag && ((String) row.get(DATA_TYPE)).equals(columnType);
		testCase.addCodeEvidenceCurrentStep(DATA_TYPE_FOUND_DESC + (String) row.get(DATA_TYPE));
		flag = flag && ((Number) row.get("NUMERIC_PRECISION")).intValue() == precision;
		testCase.addCodeEvidenceCurrentStep(
			"La precision encontrado es: " + (Number) row.get("NUMERIC_PRECISION"));
		flag = flag && ((Number) row.get("NUMERIC_SCALE")).intValue() == scale;
		testCase.addCodeEvidenceCurrentStep("La escala encontrado es: " + (Number) row.get("NUMERIC_SCALE"));
	    } else {
		testCase.addCodeEvidenceCurrentStep(CAMPO_NO_ENCONTRADO_DESC + columnName);
		flag = false;
	    }
	}
	return flag;
    }

    /**
     * Valida que una columna de base de datos exista y que sus propiedades de tipo
     * de dato y longitud o precisión coincidan con los valores esperados.
     *
     * @author Guillermo Garcia
     * @param result     Lista de registros con la información de las columnas de
     *                   base de datos
     * @param columnName Nombre de la columna que será validada
     * @param columnType Tipo de dato esperado para la columna
     * @param lenght     Longitud o precisión esperada dependiendo del tipo de dato
     * @return true si todas las validaciones son correctas, false si alguna
     *         validacion no paso
     * @date 20/05/2026
     */
    public Boolean validateColumnAndDataType(List<Map<String, Object>> result, String columnName, String columnType,
	    int lenght) {
	boolean flag = true;
	for (Map<String, Object> row : result) {
	    String realColumnName = (String) row.get(COLUMN_NAME);

	    if (columnName.equals(realColumnName)) {
		testCase.addCodeEvidenceCurrentStep(CAMPO_DESC + realColumnName);
		testCase.addCodeEvidenceCurrentStep(DATA_TYPE_FOUND_DESC + (String) row.get(DATA_TYPE));
		flag = flag && ((String) row.get(DATA_TYPE)).equals(columnType);
		if (columnType.equalsIgnoreCase("TEXT")) {
		    testCase.addCodeEvidenceCurrentStep(
			    "La longitud encontrado es: " + (Number) row.get("CHARACTER_MAXIMUM_LENGTH"));
		    flag = flag && ((Number) row.get("CHARACTER_MAXIMUM_LENGTH")).intValue() == lenght;
		}
		if (columnType.equalsIgnoreCase("TIMESTAMP_NTZ")) {
		    testCase.addCodeEvidenceCurrentStep(
			    "La precision encontrado es: " + (Number) row.get("DATETIME_PRECISION"));
		    flag = flag && ((Number) row.get("DATETIME_PRECISION")).intValue() == lenght;
		}
	    } else {
		testCase.addCodeEvidenceCurrentStep(CAMPO_NO_ENCONTRADO_DESC + columnName);
		flag = false;
	    }
	}
	return flag;
    }

    /**
     * Valida que una columna de base de datos exista y que su tipo de dato coincida
     * con el valor esperado.
     *
     * @author Guillermo Garcia
     * @param result     Lista de registros con la información de las columnas de
     *                   base de datos
     * @param columnName Nombre de la columna que será validada
     * @param columnType Tipo de dato esperado para la columna
     * @return true si todas las validaciones son positivas, false si las
     *         validaciones son negativas
     * @date 20/05/2026
     */
    public Boolean validateColumnAndDataType(List<Map<String, Object>> result, String columnName, String columnType) {
	boolean flag = true;
	for (Map<String, Object> row : result) {
	    String realColumnName = (String) row.get(COLUMN_NAME);

	    if (columnName.equals(realColumnName)) {
		testCase.addCodeEvidenceCurrentStep(CAMPO_DESC + realColumnName);
		testCase.addCodeEvidenceCurrentStep(DATA_TYPE_FOUND_DESC + (String) row.get(DATA_TYPE));
		flag = ((String) row.get(DATA_TYPE)).equals(columnType);
	    } else {
		testCase.addCodeEvidenceCurrentStep(CAMPO_NO_ENCONTRADO_DESC + columnName);
		flag = false;
	    }
	}
	return flag;
    }

    /**
     * Valida la existencia de una llave dentro de un JSON y verifica que el tipo de
     * dato asociado corresponda al esperado.
     *
     * @author Guillermo Garcia
     * @param first   Nodo JSON donde se realizará la validación
     * @param keyName Nombre de la llave que será validada
     * @param type    Tipo de dato esperado para la llave (Numérico o String)
     * @return true si todas las validaciones son positivas, false si las
     *         validaciones son negativas
     * @date 20/05/2026
     */
    public boolean validateJsonEstructure(JsonNode first, String keyName, String type) {
	boolean flag = true;
	if (first.has(keyName)) {
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es " + keyName + " es :" + first.get(keyName).getClass().getSimpleName());
	    if (type.equalsIgnoreCase("Numerico")) {
		flag = first.get(keyName).isNumber();
	    } else {
		flag = first.get(keyName).isTextual();
	    }
	} else {
	    flag = false;
	    testCase.addCodeEvidenceCurrentStep("No se encuentra " + keyName);
	}
	return flag;
    }

    /**
     * Genera dinámicamente el cuerpo de la petición en formato JSON para acuerdos,
     * incluyendo la información del acuerdo y su cobertura, permitiendo modificar
     * un campo específico para diferentes escenarios de prueba.
     *
     * @author Guillermo Garcia
     * @param fieldToModify Nombre del campo del JSON que será modificado
     * @param newValue      Nuevo valor que será asignado al campo indicado
     * @return Cadena en formato JSON con la estructura del acuerdo generado
     * @date 20/05/2026
     */
    public String generateAgreementJson(String fieldToModify, Object newValue) {

	try {

	    ObjectMapper mapper = new ObjectMapper();

	    ArrayNode root = mapper.createArrayNode();

	    ObjectNode agreement = mapper.createObjectNode();

	    // agreementInformation
	    ObjectNode agreementInformation = mapper.createObjectNode();

	    agreementInformation.put(MARKETPLACE_ID, 704);
	    agreementInformation.put(INITIAL_PERIOD, "P5 2026");
	    agreementInformation.put(FINAL_PERIOD, "P6 2026");
	    agreementInformation.put(ACTION_AGREEMENT, "N");
	    agreementInformation.putNull("cancellationMarketplaceId");

	    agreementInformation.put(INVOICE_CONCEPT,
		    "Renta Espacio x FAs, por concepto de colocación de exhibidor de piso");

	    agreementInformation.put(BILLING_METHOD, "FC_FACT");
	    agreementInformation.put(PAYMENT_METHOD, "FP_FOLIO");
	    agreementInformation.put(CUSTOMER_PROVIDER_ID, 250);
	    agreementInformation.put(TAX_CODE, "GRAVABLEINT");

	    agreementInformation.put(MECHANICS, "Renta Espacio para Exhibidor; Ps: P12");

	    agreementInformation.put(BONUS_DESC, "Renta de espacio");
	    agreementInformation.put(CALENDAR_BUSINESS, "Promocional Oxxo");
	    agreementInformation.put(BONUS_TYPE, "C");
	    agreementInformation.put(BONUS_TYPE_DESC, "Renta Espacio");
	    agreementInformation.put(NEGOTATION_TYPE, "Renta Espacio");

	    agreementInformation.put(FREQUENCY, "P_PERIODO_OXXO");
	    agreementInformation.put(CONTRIBUTION_TYPE, "MONTO_FIJO_X_TIENDA");

	    agreementInformation.putNull("contributionDescription");

	    agreementInformation.put(CONTRIBUTION_AMOUNT, 365);
	    agreementInformation.put(SUPER_GROUP_ID, 305);
	    agreementInformation.put(DIVISION_ID, 125);
	    agreementInformation.put(GROUP_NUMBER_ID, 8);
	    agreementInformation.put(DEPARTMENT_ID, 54);

	    agreementInformation.put("commercialRepresentativeFullName",
		    "4AEF28F659ED396478CAE528861033E59BE53EEDB84EE6598EDD19013FB2BE460195F3452D3971E165E1EB45891CCEA4313195");

	    agreementInformation.put("commercialRepresentativeEmail",
		    "0553F872013D00B91188357828D10DED1CE80D2EC2386E7A1E60F58D4D9F47932E5D502830603D609AEFC691E6B23404785631A5F1BABD8F1948");

	    agreementInformation.put("commercialRepresentativePhone",
		    "B0228F565EDD397173643A01E250958B3FD680636618286D0939A3FFC5E1EFE7BB89A284FD5DB564CD8F56C65512BC69D068");

	    agreementInformation.put(USER_CATEGORY_ID, 1163);

	    agreementInformation.put(CUSTOMER_RFC,
		    "85DFB13B6655ABCF4532B95884B53F234161738E36500B3466BDDE097A0465519E85271F4F3162DC472E0B1B28BA76865778");

	    agreement.set("agreementInformation", agreementInformation);

	    // coverage
	    ObjectNode coverage = mapper.createObjectNode();

	    coverage.set(REGIONS, mapper.valueToTree(List.of("10MEX", "10VCZ")));

	    coverage.set(EMPOWERED_PLAZAS, mapper.valueToTree(List.of("32NJD", "32WUB")));

	    coverage.set(MASTER_SEG, mapper.valueToTree(List.of("Barrio Competido")));

	    coverage.set(POTENTIALS, mapper.valueToTree(List.of("Hogar_Alto")));

	    coverage.put(ALCOHOL_PERMITS, "ALTA,BAJA,CERVEZA,SIN");

	    agreement.set(COVERAGE, coverage);

	    // modificación dinámica
	    applyModification(mapper, agreement, fieldToModify, newValue);

	    root.add(agreement);

	    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * Valida la respuesta de error obtenida al enviar un acuerdo con modificaciones
     * específicas en el cuerpo de la petición, verificando códigos de error y
     * mensajes esperados según el escenario indicado.
     *
     * @author Guillermo Garcia
     * @param fieldToModify Nombre del campo del JSON que será modificado para la
     *                      prueba
     * @param newValue      Nuevo valor asignado al campo a validar
     * @param error         Tipo de error esperado para realizar la validación
     *                      correspondiente
     * @return true si todas las validaciones son positivas, false si las
     *         validaciones son negativas
     * @throws IOException Si ocurre un error durante la generación o envío de la
     *                     petición
     * @date 20/05/2026
     */
    public boolean validateAgreementlError(String fieldToModify, Object newValue, String error) throws IOException {
	boolean flag = true;
	String body = generateAgreementJson(fieldToModify, newValue);
	Response response = postAgreementsError(body);
	String realCode;
	if (error.equals("400")) {
	    realCode = response.jsonPath().getString("code");
	    testCase.addCodeEvidenceCurrentStep(ENVIO_DESC + realCode);
	    testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	    flag = (realCode.equals(CODE_400_MESS))
		    && (response.jsonPath().getString(MESSAGE)
			    .equals(ERROR_MESSAGE))
		    && (response.getStatusCode() == (400));
	}
	if (error.equals(SEGURIDAD)) {
	    testCase.addCodeEvidenceCurrentStep(ENVIO_RES);
	    testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	    flag = (response.htmlPath().getString("body").equals(
		    DENAIED_OP))
		    && (response.getStatusCode() == (403));
	}
	return flag;
    }

    /**
     * Valida que un valor recibido corresponda al tipo de dato esperado,
     * verificando si el campo es texto o numérico.
     *
     * @author Guillermo Garcia
     * @param key          Valor del campo que será validado
     * @param expectedType Tipo de dato esperado para la validación
     * @param name         Nombre del campo utilizado como referencia en la
     *                     evidencia
     * @return true si todas las validaciones son positivas, false si las
     *         validaciones son negativas
     * @date 20/05/2026
     */
    public boolean validatorTypeJson(Object key, String expectedType, String name) {
	boolean flag = true;
	if (key != null) {
	    if (expectedType.equalsIgnoreCase("texto")) {
		testCase.addCodeEvidenceCurrentStep(
			"Se valida el campo " + name + " es del tipo " + key.getClass().getName());
		flag = key instanceof String;
	    } else {
		testCase.addCodeEvidenceCurrentStep(
			"Se valida el campo " + name + " es del tipo " + key.getClass().getName());
		flag = key instanceof Integer;
	    }

	} else {
	    testCase.addCodeEvidenceCurrentStep("Se salta la validacion por valor nulo");
	}
	return flag;
    }

    /**
     * Valida que la información de un acuerdo obtenida desde la respuesta del
     * servicio coincida contra los valores almacenados en Snowflake, realizando la
     * comparación de los campos configurados en el mapeo.
     *
     * @author Guillermo Garcia
     * @param response     Respuesta obtenida del servicio REST con la información
     *                     del acuerdo
     * @param snowflakeRow Registro obtenido desde Snowflake con los valores
     *                     esperados
     * @return No aplica
     * @date 20/05/2026
     */
    public Boolean validateAgreementAgainstSnowflake(Response response, Map<String, Object> snowflakeRow) {
	Map<String, String> mapping = new HashMap<>();
	mapping.put(MARKETPLACE_ID, "ID_MARKETPLACE");
	mapping.put(ACTION_AGREEMENT, "ACCION_CONVENIO");
	mapping.put("errors", "ERRORES");
	mapping.put("statusAgreement", "ESTADO_CONVENIO");
	mapping.put("creationDate", "FECHA_CARGA");
	mapping.put("folioAgreement", "FOLIO_EBS");
	mapping.put("cancellationMarketplaceId", "ID_SOLICITUD_BAJA_MKT");
	Boolean flag = true;
	for (Map.Entry<String, String> entry : mapping.entrySet()) {
	    String apiField = entry.getKey();
	    String snowField = entry.getValue();
	    Object apiValue = response.jsonPath().get("data[0]." + apiField);
	    Object snowValue = snowflakeRow.get(snowField);
	    flag = compareValues(apiField, apiValue, snowValue);
	}
	return flag;
    }

    /**
     * Compara un valor obtenido desde la respuesta del servicio contra un valor
     * almacenado en Snowflake, realizando validaciones especiales para valores
     * nulos, numéricos y fechas.
     *
     * @author Guillermo Garcia
     * @param field     Nombre del campo que será comparado
     * @param apiValue  Valor obtenido desde la respuesta del servicio
     * @param snowValue Valor obtenido desde Snowflake
     * @return true si todas las validaciones son positivas, false si las
     *         validaciones son negativas
     * @date 20/05/2026
     */
    private boolean compareValues(String field, Object apiValue, Object snowValue) {
	testCase.addCodeEvidenceCurrentStep(
		"\nValidando campo: " + field + "\nAPI : " + apiValue + "\nSNOW: " + snowValue);
	// ambos null
	if (apiValue == null && snowValue == null) {
	    return true;
	}
	// uno null y otro no
	if (apiValue == null || snowValue == null) {
	    return false;
	}
	// números
	if (apiValue instanceof Number && snowValue instanceof Number) {
	    return ((Number) apiValue).longValue() == ((Number) snowValue).longValue();
	}
	// fechas timestamp snowflake
	if ("creationDate".equals(field)) {
	    String apiDate = apiValue.toString();
	    String snowDate = snowValue.toString();
	    // elimina milisegundos
	    snowDate = snowDate.split("\\.")[0];
	    return apiDate.equals(snowDate);
	}
	return apiValue.toString().equals(snowValue.toString());
    }

    /**
     * Valida los metadatos de paginación y contenido de la respuesta obtenida del
     * servicio de acuerdos, verificando los valores esperados de página, tamaño,
     * totales y existencia de información en el arreglo de datos.
     *
     * @author Guillermo Garcia
     * @param response           Respuesta obtenida del servicio REST con la
     *                           información de metadata
     * @param expectedPage       Número de página esperado en la respuesta
     * @param expectedPageSize   Cantidad de registros por página esperada
     * @param expectedTotalItems Total de elementos esperado en la respuesta
     * @param expectedTotalPages Total de páginas esperado en la respuesta
     * @param expectedData       Indicador para validar si la lista de datos debe
     *                           estar vacía o contener información
     * @return No aplica
     * @date 20/05/2026
     */
    public Boolean validateMetadataAgreements(Response response, int expectedPage, int expectedPageSize,
	    int expectedTotalItems, int expectedTotalPages, String expectedData) {
	int page = response.jsonPath().getInt("page");
	int pageSize = response.jsonPath().getInt(PAGE_SIZE);
	int totalItems = response.jsonPath().getInt("totalItems");
	int totalPages = response.jsonPath().getInt("totalPages");
	Boolean flag = true;
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key page: " + page);
	flag = flag && (page == expectedPage);
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key pageSize: " + pageSize);
	flag = flag && (pageSize == expectedPageSize);
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key totalItems: " + totalItems);
	flag = flag && (totalItems == expectedTotalItems);
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key totalPages: " + totalPages);
	flag = flag && (totalPages == expectedTotalPages);
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key data: " + response.jsonPath().getList("data"));
	if (expectedData.equalsIgnoreCase("vacio")) {
	    flag = flag && response.jsonPath().getList("data").isEmpty();
	} else {
	    flag = flag && (!response.jsonPath().getList("data").isEmpty());
	}
	return flag;
    }

    /**
     * Realiza una división entre dos valores enteros y determina si el resultado
     * genera un valor positivo, retornando un indicador numérico según el
     * resultado.
     *
     * @author Guillermo Garcia
     * @param valueOne Valor entero utilizado como dividendo
     * @param valueTwo Valor entero utilizado como divisor
     * @return Indicador numérico que representa si la división genera un valor
     *         positivo
     * @date 20/05/2026
     */
    public int divisionRounded(int valueOne, int valueTwo) {
	double division = (double) valueOne / valueTwo;
	int value = division > 0 ? 1 : 0;
	return value;
    }
}
