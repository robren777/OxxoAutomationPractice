package WM.interfaces.PR63_CLOUD;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import WM.om.PR63_CLOUD;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.sql.SQLResult;

@AzureTestInfo(
		areaPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise",
		iterationPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise\\Sprint 3",
		tags = "",
		userStoryId = 71351
)

public class ATC_003_PR63_CLOUD_ValidarTRPromocionComplejaMultiUnitTiendaDigital extends BaseExecution{
	@Test
	(dataProvider = "data-provider")
	public void ATC_003_PR63_CLOUD_ValidarTRPromocionComplejaMultiUnitTiendaDigital_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-003 Validar TR Archivo Cloud Promocion Compleja MultiUnit Tienda Digital
		Desc:
		Prueba de componente para validar que las Reglas de Transformacion de PR63_CLOUD sean aplicadas,
		en este caso se revisa Promocion´Simple de TiendaDigital que debio haber procesado el job PR63_CLOUD.
		@Valeria Fernandez
		@date   2025/01/15*/
		
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		PR63_CLOUD tr = new PR63_CLOUD(testCase);
		
		utils.sql.SQLUtil dbFCDASPFR = null;
		int maxAttempts = 5;
		int retryInterval = 1000; // 1 second
		
		//Reintenta conexion hasta 5 veces de dbFCDASPFR, porque es inestable su conexion
		 for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			 try {
				 dbFCDASPFR = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO,GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_DASPFR_MEX_MULTIFORMATO);
				 System.out.println("Conexion completada en el intento " + attempt); // Perform database operations here 
				 break; // Exit the loop if connection is successful 
				 } catch (Exception e) {
					 System.out.println("Conexion intento " + attempt + " fallado: " + e.getMessage()); 
					 if (attempt < maxAttempts) {
						 try {
							 Thread.sleep(retryInterval);
							 } 
						 catch (InterruptedException ie) {
							 Thread.currentThread().interrupt();
							 } 
						} else {
							System.out.println("Todos los intentos de conexion fallaron"); 
						} 
				 } 
			 }
		
		utils.sql.SQLUtil dbAVEBQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA , GlobalVariables.DB_USER_AVEBQA ,GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLUtil dbFCWMESIT = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_fcwmesit,GlobalVariables.DB_USER_fcwmesit, GlobalVariables.DB_PASSWORD_fcwmesit);
		
		//AVEBQA
		String queryRetekCR = "SELECT * FROM XXFC_MAESTRO_DE_CRS_V WHERE RETEK_CR='" + data.get("retek_cr") + "'";
		
		//FCDASPFR
		String queryPRMHead = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status") + "' AND CLOUD_PRM_STATUS = '" + data.get("cloud_prm_status") + "' AND LOCATION='" + data.get("retek_cr") + "' AND BATCH_ID='" + data.get("batchId") + "'";
		String queryPRM_STG = "SELECT * FROM WMUSER.FEM_POS_MODS_PRM_STG WHERE STORE='" +data.get("retek_cr")+"' AND LOAD_BATCH_ID='" + data.get("load_batch_id") + "' AND TRAN_TYPE='" + data.get("tranType") + "' AND THRESHOLD_NO " + data.get("threshold_no") + " AND PROMOTION='" + data.get("promotionFolio") + "'";
		String queryDetail_STG = "SELECT * FROM WMUSER.FEM_POS_PROM_DETAIL_STG WHERE STORE='" +data.get("retek_cr")+"' AND LOAD_BATCH_ID='" + data.get("load_batch_id") + "' AND PROMOTION='" + data.get("promotionFolio") + "'";
		String queryCountPromociones = "SELECT COUNT (*) FROM wmuser.FEM_POS_MODS_PRM_STG WHERE STORE = '" + data.get("retek_cr") + "' AND load_batch_id ='" + data.get("load_batch_id") + "'";
		String tr30 = "SELECT DISTINCT BATCH_ID, LOCATION FROM WMUSER.POS_PRM_HEAD WHERE LOCATION = '" + data.get("retek_cr") + "' AND wm_prm_status = '" + data.get("wm_prm_status") + "'";
		String tr31 = "SELECT a.load_batch_id\r\n"
				+ "	, TO_CHAR(a.load_timestamp,'YYYYMMDDHH24MISS') load_timestamp\r\n"
				+ "	, a.line_id, a.update_type\r\n"
				+ "	, TO_CHAR(a.start_date,'YYYYMMDD') start_date\r\n"
				+ "	, a.tran_type, a.item, a.dept, a.class, a.subclass, a.new_price, a.new_selling_uom, a.promotion\r\n"
				+ "	, a.threshold_no, a.prom_name\r\n"
				+ "	, TO_CHAR(a.end_date,'YYYYMMDD') end_date\r\n"
				+ "	, a.manual_price_entry\r\n"
				+ "	, DECODE(a.manual_price_entry, 'M', a.prom_bin_code, 'E',  a.prom_bin_code, '') bin_code\r\n"
				+ "	, DECODE(a.manual_price_entry, 'M', a.msg_pos, 'E', a.msg_pos,'') msg_pos\r\n"
				+ "	, LPAD('1' || NVL(to_char(a.time, 'HH24MI'),'0000') || NVL(to_char(a.end_time, 'HH24MI'),'2359') || a.item_short_desc,16) promotion_time\r\n"
				+ "	, a.ref_prefix, a.prefix\r\n"
				+ "FROM WMUSER.FEM_POS_MODS_PRM_STG a, WMUSER.POS_PRM_HEAD b\r\n"
				+ "WHERE b.batch_id = '" + data.get("batchId") + "'\r\n"
				+ "AND a.load_batch_id = b.load_batch_id\r\n"
				+ "AND a.load_week = b.load_week\r\n"
				+ "AND b.LOCATION = '" + data.get("retek_cr") + "'\r\n"
				+ "AND a.STORE = b.LOCATION\r\n"
				+ "AND b.wm_prm_status = '" + data.get("wm_prm_status") + "'\r\n"
				+ "ORDER BY a.load_batch_id, load_timestamp, line_id";
		String tr32 = "SELECT COALESCE (\r\n"
				+ "        (  -- VALIDACION A NIVEL DE PLAZA, TIENDA Y DOC_TYPE\r\n"
				+ "            SELECT MIGRATED_FLAG_OUT\r\n"
				+ "            FROM POSUSER.POS_RULES_DOC_CFG\r\n"
				+ "            WHERE PV_CR_PLAZA = '" + data.get("departmentId") + "'\r\n"
				+ "              AND PV_CR_TIENDA = '" + data.get("storeId") + "'\r\n"
				+ "              AND DOC_TYPE = '" + data.get("doctype") + "' ),\r\n"
				+ "        ( -- VALIDACION A NIVEL DE PLAZA Y DOC_TYPE\r\n"
				+ "            SELECT MIGRATED_FLAG_OUT\r\n"
				+ "            FROM POSUSER.POS_RULES_DOC_CFG\r\n"
				+ "            WHERE PV_CR_PLAZA = '" + data.get("departmentId") + "'\r\n"
				+ "              AND PV_CR_TIENDA = '" + data.get("storeId") + "'\r\n"
				+ "              AND DOC_TYPE = '" + data.get("doctype") + "' ),\r\n"
				+ "        (  -- VALIDACION A NIVEL DOC_TYPE\r\n"
				+ "            SELECT MIGRATED_FLAG_OUT\r\n"
				+ "            FROM POSUSER.POS_RULES_DOC_CFG\r\n"
				+ "            WHERE PV_CR_PLAZA = '" + data.get("departmentId") + "'\r\n"
				+ "              AND PV_CR_TIENDA = '" + data.get("storeId") + "'\r\n"
				+ "              AND DOC_TYPE = '" + data.get("doctype") + "' ),\r\n"
				+ "            -- DEFAULT\r\n"
				+ "        'N'\r\n"
				+ "    )\r\n"
				+ "AS MIGRATED_FLAG_OUT\r\n"
				+ "FROM DUAL";
		String tr34 = "SELECT  a.load_batch_id, a.store, a.load_timestamp, a.prom_tran_type, a.discount_type, a.threshold_amt, a.discount_amt\r\n"
				+ "FROM WMUSER.FEM_POS_PROM_DETAIL_STG a, WMUSER.POS_PRM_HEAD b\r\n"
				+ "WHERE a.load_batch_id = b.load_batch_id\r\n"
				+ "AND a.load_week = b.load_week\r\n"
				+ "AND a.store = b.location\r\n"
				+ "AND b.wm_prm_status = '" + data.get("wm_prm_status") + "'\r\n"
				+ "AND batch_id = '" + data.get("batchId") + "'\r\n"
				+ "AND store = '" + data.get("retek_cr") + "'\r\n"
				+ "ORDER BY a.load_batch_id, a.store, load_timestamp";
		
		//MDB_ERDD_PromotionCloud
		String querySTS = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("retek_cr")+"' AND PV_DOC_TYPE='" +data.get("doctype")+"'";
		String userAzure= "dbo.";
		
		String cr_plaza = "";
		String cr_tienda = "";
		
		String useMock = data.get("useMock");
		String ejecutarE2E = data.get("ejecutarE2E");
		String azureConnectionString = data.get("azureConnectionString");
        String azureContainerName = data.get("azureContainerName");
        String filePathLocal = data.get("localDir"); //Directorio local donde vas a descargar los archivos
        String archivoTR = data.get("archivoTR");
        int index = Integer.parseInt(data.get("index")); // Para obtener el elemento segun el index (el indice comienza desde 0)
        
        boolean validaStatus = false;
		
		//***************************Paso 1**********************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas E2E_PR63_CLOUD el caso de prueba: @MTC-FT-009 Envio de promocion compleja a tienda digital cloud");
		
		if(ejecutarE2E.equals("Y")) {
			ATC_009_PR63_CLOUD_EnvioPromocionComplejaTiendaDigital atc009_e2e = new ATC_009_PR63_CLOUD_EnvioPromocionComplejaTiendaDigital();
			atc009_e2e.setTestCase(this.testCase);
			atc009_e2e.ATC_009_PR63_CLOUD_EnvioPromocionComplejaTiendaDigital_test(data);
		}
		
		//Ejecutarlo antes o el caso de prueba actual tardara mas de 30 minutos en ejecutarse
		
		testCase.addBoldTextEvidenceCurrentStep("Caso de prueba ejecutado exitosamente anteriormente");
		
		//***************************Paso 2**********************************************************
		addStep("Descargar el archivo generado de la promocion simple, debe estar en Azure Storage en la ruta:\r\n"
				+ "Ir a Resource Group -> Dar click a OXZRGERDDD02 -> Dar click en pestania Overview - Buscar y dar click a oxzsterdddap02\r\n"
				+ "Dar click en Data Storage -> Dar click a Container -> Dar click en el contenedor ct-json-mappedjsonstorepromotiondata -> Buscar archivo con nombre en tabla PRM_HEAD");
		
		String azurePRM2 = tr.downloadFileFromAzure(azureConnectionString, azureContainerName, archivoTR, filePathLocal);
        File azurePRM = new File(azurePRM2);
		
		testCase.addTextEvidenceCurrentStep("Archivo PRM API V2 descargado en ruta local: " + azurePRM);
		
		//***************************Paso 3**********************************************************
		addStep("Abrir archivo descargado en el paso anterior");
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(azurePRM);
		
		testCase.addTextEvidenceCurrentStep("Archivo descargado abierto");
		
		//***************************Paso 4**********************************************************
		addStep("Ingresar a la BD AVEBQA");
		
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_AVEBQA);
		
		//***************************Paso 5**********************************************************
		addStep("Ingresar a la BD FCDASPFR");
		
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO);
		
		//***************************Paso 6**********************************************************
		addStep("Para la TR1 confirmar que el campo deparmentId (archivo) corresponda al campo ORACLE_CR_SUPERIOR, el campo storeId (archivo) corresponda al campo ORACLE_CR y el campo LOCATION sea igual al campo RETEK_CR de la vista XXFC_MAESTRO_DE_CRS_V (BD AVEBQA)");
		
		String departmentIdArchivo = "";
		String storeIdArchivo = "";

        // Obtener el valor del campo "campo1"
        JsonNode departmentId = rootNode.get("departmentId");
        JsonNode storeId = rootNode.get("storeId");

        // Verificar si el campo existe y obtener su valor
        if (departmentId != null) {
        	departmentIdArchivo = departmentId.asText();  // Convertir el valor a texto
            System.out.println("Valor de 'departmentId': " + departmentIdArchivo);
        }
        
        if (storeId != null) {
            storeIdArchivo = storeId.asText();  // Convertir el valor a texto
            System.out.println("Valor de 'storeIdArchivo': " + storeIdArchivo);
        }
        
        validaStatus = tr.queryOracle(queryRetekCR, dbAVEBQA);
        tr.processQueryInChunks(dbAVEBQA, queryRetekCR);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
        String retek = tr.getValueCampoOracle(queryRetekCR, dbAVEBQA, "RETEK_CR");
        String oracleCR_superior = tr.getValueCampoOracle(queryRetekCR, dbAVEBQA, "ORACLE_CR_SUPERIOR");
        String oracleCR = tr.getValueCampoOracle(queryRetekCR, dbAVEBQA, "ORACLE_CR");
        
        validaStatus = tr.queryOracle(queryPRMHead, dbFCDASPFR);
        tr.processQueryInChunks(dbFCDASPFR, queryPRMHead);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
        String location = tr.getValueCampoOracle(queryPRMHead, dbFCDASPFR, "LOCATION");
		 
		 if (!departmentIdArchivo.equals(oracleCR_superior)) {
			 validaStatus = true;
		 }
		 
		 if (!storeIdArchivo.equals(oracleCR)) {
			 validaStatus = true;
		 }
		 
		 if (!location.equals(retek)) {
			 validaStatus = true;
		 }
		
		 testCase.addTextEvidenceCurrentStep("Valor de deparmentId en archivo json: " + departmentIdArchivo);
		 testCase.addTextEvidenceCurrentStep("Valor de deparmentId en archivo json: " + storeIdArchivo);
		 testCase.addTextEvidenceCurrentStep("Valor de LOCATION en PRM_HEAD y valor de RETEK_CR en XXFC_MAESTRO_DE_CRS_V son iguales: " + location + " y " + retek);
		 
		 assertFalse("La Regla de Transformacion 1 NO fue respetada.", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 1 fue respetada.");
		 
		//***************************Paso 7**********************************************************
		addStep("Para la TR2 confirmar en el archivo que el campo WRITTEN_DATE (searchDate) tenga el formato \"yyyy-MM-dd HH:mm:ss\"");
		
		JsonNode searchDate = rootNode.get("searchDate");

        // Verificar si el campo "campo1" esta presente
        if (searchDate != null) {
            String searchDateArchivo = searchDate.asText();

            // Definir el formato esperado de la fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // Intentar parsear el valor usando el formato definido, en caso de que no pueda parsear falla la ejecucion
            LocalDateTime dateTime = LocalDateTime.parse(searchDateArchivo, formatter);
            System.out.println("Fecha searchDate valida: " + dateTime);
        }
        
        testCase.addTextEvidenceCurrentStep("Fecha searchDate valida: " + searchDate);
        testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 2 fue respetada.");
		
		//***************************Paso 8**********************************************************
		addStep("Para la TR3 confirmar en el archivo que el campo CR_PLAZA (departmentId) sea igual al campo \"ORACLE_CR_SUPERIOR\" de XXFC_MAESTRO_DE_CRS_V (BD AVEBQA)\r\n"
				+ "En la tabla WMUSER.POS_PRM_HEAD (BD FCDASPFR), ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"");
		
		validaStatus = tr.queryBDAzure(querySTS, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		cr_plaza = tr.getValueCampoQuery(querySTS, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "CR_PLAZA");
		cr_tienda = tr.getValueCampoQuery(querySTS, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "CR_TIENDA");
		 
		 testCase.addTextEvidenceCurrentStep("Valor de CR_PLAZA: " + cr_plaza);
		 testCase.addTextEvidenceCurrentStep("Valor de ORACLE_CR_SUPERIOR en XXFC_MAESTRO_DE_CRS_V y valor de CR_PLAZA son iguales: " + oracleCR_superior + " y " + cr_plaza);
		 
		 assertFalse("La Regla de Transformacion 3 NO fue respetada.", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 3 fue respetada.");
		
		//***************************Paso 9**********************************************************
		addStep("Para la TR4 confirmar en el archivo que el campo CR_TIENDA (storeId) sea igual al campo \"ORACLE_CR\" de XXFC_MAESTRO_DE_CRS_V (BD AVEBQA)\r\n"
				+ "En la tabla WMUSER.POS_PRM_HEAD (BD FCDASPFR), ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"");
		 
		 System.out.println(cr_tienda);
		 if (!cr_tienda.equals(oracleCR)) {
			 validaStatus = true;
		 }
		 
		 testCase.addTextEvidenceCurrentStep("Valor de CR_TIENDA: " + cr_tienda);
		 testCase.addTextEvidenceCurrentStep("Valor de ORACLE_CR_SUPERIOR en XXFC_MAESTRO_DE_CRS_V y valor de CR_TIENDA son iguales: " + oracleCR + " y " + cr_tienda);
		 
		 assertFalse("La Regla de Transformacion 4 NO fue respetada.", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 4 fue respetada.");
		
		//***************************Paso 10**********************************************************
		addStep("Para la TR5 confirmar en el archivo que el campo NO_RECORDS (numberOfRecords) obtiene el numero de registros de articulos");
		
		String numberOfRecordsArchivo = "";
		JsonNode numberOfRecords = rootNode.get("numberOfRecords");
		if (numberOfRecords != null) {
			numberOfRecordsArchivo = numberOfRecords.asText();  // Convertir el valor a texto
            System.out.println("Valor de 'numberOfRecords': " + numberOfRecordsArchivo);
        }
		
		System.out.println(queryCountPromociones);
		//Realiza la consulta con la conexion que se especifique (BD)
		SQLResult queryCountPromociones2 = dbFCDASPFR.executeQuery(queryCountPromociones);
		
		validaStatus = queryCountPromociones2.isEmpty();
		 if (!validaStatus) {
				testCase.addQueryEvidenceCurrentStep(queryCountPromociones2);
		 }
		 assertFalse("No se muestra la informacion de la tienda.", validaStatus);
		 
		 String countRegistros = queryCountPromociones2.getData(0, 0);
		 System.out.println("COUNT (*): " + countRegistros);
		 
		 if (!countRegistros.equals(numberOfRecordsArchivo)) {
			 validaStatus = true;
		 }
		
		 testCase.addTextEvidenceCurrentStep("Valor de 'numberOfRecords': " + numberOfRecordsArchivo);
		 assertFalse("La Regla de Transformacion 5 NO fue respetada.", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 5 fue respetada.");
		 
		//***************************Paso 11**********************************************************
		addStep("Para la TR6 confirmar que el campo LINENUMBER (archivo): Tenga el  valor incremental en 1 por cada elemento de la lista x Articulo en la Promocion");
		
		JsonNode nodosArray = rootNode.path("promotions");
		// Lista para almacenar los valores encontrados de "lineNumber"
        List<Integer> lineNumberValues = new ArrayList<>();

        // Imprimir los valores encontrados
        for (JsonNode nodo : nodosArray) {
            // Verificar si el nodo tiene el campo "line"
            JsonNode lineNumber = nodo.path("lineNumber");
            if (!lineNumber.isMissingNode()) {
                // Agregar el valor de "line" a la lista
            	lineNumberValues.add(lineNumber.asInt());
            }
        }
        
        System.out.println("Valores encontrados de 'lineNumber': " + lineNumberValues);

        // Validar si los valores son consecutivos
        if (!tr.isConsecutive(lineNumberValues)) {
            validaStatus = true;
        }
        
        testCase.addTextEvidenceCurrentStep("Valor de 'lineNumber': " + lineNumberValues);
        assertFalse("La Regla de Transformacion 6 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 6 fue respetada.");
		
		//***************************Paso 12**********************************************************
		addStep("Confirmar que la promocion tenga el campo TRAN_TYPE=31 y THRESHOLD_NO IS NOT NULL con la consulta en la BD FCDASPFR:\r\n"
				+ queryPRM_STG
				+ "\r\n"
				+ "Nota: Los campos STORE y LOAD_BATCH_ID deben corresponder con la promocion que se genero en el caso de prueba: MTC-FT-006 Envio de promocion simple a tienda digital cloud-manual");
		
		if(useMock.equals("N")) {
			validaStatus = tr.queryOracle(queryPRM_STG, dbFCDASPFR);
			tr.processQueryInChunks(dbFCDASPFR, queryPRM_STG);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 }else {
			 queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			 validaStatus = tr.queryBDAzure(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 }
		 
		//***************************Paso 13**********************************************************
		addStep("Para la TR7 en el campo FOLIO (archivo) confirmar trim al campo PROMOTION (BD FCDASPFR) y rellenado con espacios a la izquierda hasta completar 10 caracteres\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 12 para obtener el valor del campo FOLIO de la promocion en la tabla FEM_POS_MODS_PRM_STG\"	Campo en archivo con formato correcto");
		
		String promotion ="";
		
		if(useMock.equals("N")) {
			promotion = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "PROMOTION");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			promotion = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "PROMOTION");
		}
		
		String folioArchivo = "";
		
		folioArchivo = tr.searchNode(rootNode, index, "folio");
		
		if (folioArchivo.length() != 10) {
            validaStatus = true;
        }
		
		if (!folioArchivo.contains(promotion)) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PROMOTION': " + promotion);
		testCase.addTextEvidenceCurrentStep("Valor de 'folio': " + folioArchivo);
		assertFalse("La Regla de Transformacion 7 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 7 fue respetada.");
		
		//***************************Paso 14**********************************************************
		addStep("Confirmar que la promocion aparezca en la tabla FEM_POS_PROM_DETAIL_STG en la BD FCDASPFR:\r\n"
				+ queryDetail_STG
				+ "\r\n"
				+ "Nota: Los campos STORE y LOAD_BATCH_ID deben corresponder con la promocion que se genero en el caso de prueba: MTC-FT-009 Envio de promocion compleja a tienda digital cloud");
		
		if(useMock.equals("N")) {
			validaStatus = tr.queryOracle(queryDetail_STG, dbFCDASPFR);
			tr.processQueryInChunks(dbFCDASPFR, queryDetail_STG);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 }else {
			 queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			 validaStatus = tr.queryBDAzure(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 }
				
		//***************************Paso 15**********************************************************
		addStep("Para la TR8 confirmar que el campo CVE_CAT (archivo) cumpla con la condicion:\r\n"
				+ "Si PROM_TRAN_TYPE=1001\r\n"
				+ "   CVE_CAT=\"2\"\r\n"
				+ "De lo contrario\r\n"
				+ "   CVE_CAT=\"1\"\r\n"
				+ "Confirmar trim al CVE_CAT y rellenado con espacio a la izquierda hasta completar 2 caracteres\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 14 para obtener el valor del campo PROM_TRAN_TYPE de la promocion en la tabla FEM_POS_PROM_DETAIL_STG");
		
		validaStatus = true;
		
		String prom_tran_type ="";
		
		if(useMock.equals("N")) {
			prom_tran_type = tr.getValueCampoOracle(queryDetail_STG, dbFCDASPFR, "PROM_TRAN_TYPE");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			prom_tran_type = tr.getValueCampoQuery(queryDetail_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "PROM_TRAN_TYPE");
		}
		
		String itemCategoryArchivo = "";

		itemCategoryArchivo = tr.searchNode(rootNode, index, "itemCategory");
		
		if (itemCategoryArchivo.length() == 2) {
            validaStatus = false;
        }
		
		if(prom_tran_type.equals("1001") && itemCategoryArchivo.equals(" 2")) {
			validaStatus = false;
		}else {
			if(itemCategoryArchivo.equals(" 1")) {
				validaStatus = false;
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PROM_TRAN_TYPE': " + prom_tran_type);
		testCase.addTextEvidenceCurrentStep("Valor de 'itemCategory': " + itemCategoryArchivo);
		assertFalse("La Regla de Transformacion 8 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 8 fue respetada.");
		
		//***************************Paso 16**********************************************************
		addStep("Para la TR9 confirmar que el campo CVE_TIP (archivo) cumpla con la condicion:\r\n"
				+ "Si PROM_TRAN_TYPE=1000\r\n"
				+ "   CVE_TIP=\"00\"\r\n"
				+ "Si PROM_TRAN_TYPE=1001\r\n"
				+ "   CVE_TIP=\"01\"\r\n"
				+ "Si PROM_TRAN_TYPE=1002\r\n"
				+ "   CVE_TIP=\"02\"\r\n"
				+ "Si PROM_TRAN_TYPE=1006\r\n"
				+ "   CVE_TIP=\"06\"\r\n"
				+ "Si PROM_TRAN_TYPE=1007\r\n"
				+ "   CVE_TIP=\"07\"\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 14 para obtener el valor del campo PROM_TRAN_TYPE de la promocion en la tabla FEM_POS_PROM_DETAIL_STG");
		
		String typeArchivo = "";

		typeArchivo = tr.searchNode(rootNode, index, "type");
		
		//validaStatus = true;
		
		if(prom_tran_type.equals("1000") && typeArchivo.equals("00")) {
			validaStatus = false;
		}else {
			if(prom_tran_type.equals("1001") && typeArchivo.equals("01")) {
				validaStatus = false;
			}else {
				if(prom_tran_type.equals("1002") && typeArchivo.equals("02")) {
					validaStatus = false;
				}else {
					if(prom_tran_type.equals("1006") && typeArchivo.equals("06")) {
						validaStatus = false;
					}else {
						if(prom_tran_type.equals("1007") && typeArchivo.equals("07")) {
							validaStatus = false;
						}
					}
				}
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PROM_TRAN_TYPE': " + prom_tran_type);
		testCase.addTextEvidenceCurrentStep("Valor de 'type': " + typeArchivo);
		assertFalse("La Regla de Transformacion 9 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 9 fue respetada.");
		
		//***************************Paso 17**********************************************************
		addStep("Para la TR10 confirmar en el archivo que el campo FECHA_IN (startDate) debe ser igual al campo FEM_POS_MODS_PRM_STG.START_DATE (BD FCDASPFR)\r\n"
				+ "\r\n"
				+ "Confirmar que tenga el formato YYYYMMDD\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 12 para obtener el valor del campo START_DATE de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		String startDateArchivo = "";
		
    	// Definir el formato esperado de la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		for (JsonNode nodo : nodosArray) {
			JsonNode startDate = nodo.path("startDate");
			
            if (!startDate.isMissingNode()) {
            	startDateArchivo = startDate.asText();
                // Intentar parsear el valor usando el formato definido, en caso de que no pueda parsear falla la ejecucion
                LocalDate date = LocalDate.parse(startDateArchivo, formatter);
                break; // Salir del ciclo despues de encontrar el primero
            }
        }
		
		String START_TIME = "";
		
		if(useMock.equals("N")) {
			START_TIME = tr.getValueCampoOracle(queryDetail_STG, dbFCDASPFR, "START_TIME");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			START_TIME = tr.getValueCampoQuery(queryDetail_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "START_TIME");
		}
		
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(START_TIME, inputFormatter);
        LocalDate date2 = dateTime.toLocalDate();
        String START_TIME2 = date2.format(formatter);
        System.out.println("START_TIME formateado : " + START_TIME2);
        
		if(startDateArchivo.equals(START_TIME2)) {
			validaStatus = false;
		}
		
		assertFalse("La Regla de Transformacion 10 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("Fecha startDate valida: " + startDateArchivo);
        testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 10 fue respetada.");
		
		//***************************Paso 18**********************************************************
		addStep("Para la TR11 confirmar en el archivo que el campo FECHA_FI (endDate) debe ser igual al campo FEM_POS_MODS_PRM_STG.END_DATE (BD FCDASPFR)\r\n"
				+ "\r\n"
				+ "Confirmar que tenga el formato YYYYMMDD\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 12 para obtener el valor del campo END_DATE de la promocion en la tabla FEM_POS_MODS_PRM_STG");

		String endDateArchivo = "";
        // Verificar si el campo "campo1" esta presente
		for (JsonNode nodo : nodosArray) {
			JsonNode endDate = nodo.path("endDate");
			
            if (!endDate.isMissingNode()) {
            	endDateArchivo = endDate.asText();
                // Intentar parsear el valor usando el formato definido, en caso de que no pueda parsear falla la ejecucion
                LocalDate date = LocalDate.parse(endDateArchivo, formatter);
                break; // Salir del ciclo despues de encontrar el primero
            }
        }
		
		String END_TIME = "";
		
		if(useMock.equals("N")) {
			END_TIME = tr.getValueCampoOracle(queryDetail_STG, dbFCDASPFR, "END_TIME");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			END_TIME = tr.getValueCampoQuery(queryDetail_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "END_TIME");
		}
		
		inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTime = LocalDateTime.parse(END_TIME, inputFormatter);
        date2 = dateTime.toLocalDate();
        String END_TIME2 = date2.format(formatter);
        System.out.println("END_TIME formateado: " + END_TIME2);
        
		if(endDateArchivo.equals(END_TIME2)) {
			validaStatus = false;
		}
        
		assertFalse("La Regla de Transformacion 11 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("Fecha endDate valida: " + endDateArchivo);
        testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 11 fue respetada.");
		
		//***************************Paso 19**********************************************************
		addStep("Para la TR12 el campo ITEM=\" \" (archivo)\r\n"
				+ "Confirmar que el campo PROM_TRAN_TYPE de FEM_POS_PROM_DETAIL_STG es 1001\r\n"
				+ "Confirmar trim al campo ITEM y rellenar con espacios a la izquierda hasta completar 16 caracteres\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 14 para obtener el valor del campo PROM_TRAN_TYPE de la promocion en la tabla FEM_POS_PROM_DETAIL_STG");
		
		String itemSkuArchivo = "";
		String item = "";
		
		itemSkuArchivo = tr.searchNode(rootNode, index, "itemSku");
		
		String itemSkuArchivo2 = itemSkuArchivo.trim();
		
		if(useMock.equals("N")) {
			item = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "ITEM");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			item = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "ITEM");
		}
		
		if (itemSkuArchivo.length() != 16) {
            validaStatus = true;
        }
		
		validaStatus = true;
		
		if(prom_tran_type.equals("1001") && itemSkuArchivo.equals("                ")) {
			validaStatus = false;
		}else if (itemSkuArchivo2.equals(item)){
			validaStatus = false;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PROM_TRAN_TYPE': " + prom_tran_type);
		testCase.addTextEvidenceCurrentStep("Valor de 'itemSku': " + itemSkuArchivo);
		assertFalse("La Regla de Transformacion 12 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 12 fue respetada.");
		
		//***************************Paso 20**********************************************************
		addStep("Para la TR13 confirmar en el archivo que el campo ITEM2 (category) sea igual al campo FEM_POS_MODS_PRM_STG.MANUAL_PRICE_ENTRY (BD FCDASPFR)\r\n"
				+ "\r\n"
				+ "Si es diferente de Null confirmar trim al ITEM2 y rellenado con espacios a la izquierda hasta completar 16 caracteres\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 12 para obtener el valor del campo ITEM2 de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		String manual_price_entry = "";
		
		if(useMock.equals("N")) {
			manual_price_entry = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "MANUAL_PRICE_ENTRY");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			manual_price_entry = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "MANUAL_PRICE_ENTRY");
		}
		
		String categoryArchivo = "";
		
		categoryArchivo = tr.searchNode(rootNode, index, "category");
		
		if(manual_price_entry == null) {
			manual_price_entry = "";
		}
		
		categoryArchivo = categoryArchivo.trim();
		manual_price_entry = manual_price_entry.trim();
		
		if (!categoryArchivo.equals(manual_price_entry)) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'MANUAL_PRICE_ENTRY': " + manual_price_entry);
		testCase.addTextEvidenceCurrentStep("Valor de 'category': " + categoryArchivo);
		assertFalse("La Regla de Transformacion 13 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 13 fue respetada.");
		
		//***************************Paso 21**********************************************************
		addStep("Para la TR14 el campo CANT_REQ=\"0\" (archivo)\r\n"
				+ "Confirmar que CANT_REQ tenga formato numerico con patron \"000.000\"");
		
		String requiredQuantityArchivo = "";
		
		requiredQuantityArchivo = tr.searchNode(rootNode, index, "requiredQuantity");
		
		String regex2 = "^[0-9]{3}\\.[0-9]{3}$";
	   // Crear un patron a partir de la expresion regular
       Pattern pattern = Pattern.compile(regex2);

       // Crear un matcher para comparar el campo1 con la expresion regular
       Matcher matcher = pattern.matcher(requiredQuantityArchivo);

       String THRESHOLD_AMT = "";
       String tranType = data.get("tranType");
		
		if(useMock.equals("N")) {
			THRESHOLD_AMT = tr.getValueCampoOracle(queryDetail_STG, dbFCDASPFR, "THRESHOLD_AMT");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			THRESHOLD_AMT = tr.getValueCampoQuery(queryDetail_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "THRESHOLD_AMT");
		}
		
		if(tranType.equals("31") && !requiredQuantityArchivo.equals(THRESHOLD_AMT) && !matcher.matches()) { //Si grouperArchivo no tiene el formato 000.000 y es igual a ref_prefix, fallara
			validaStatus=true;
		}else if (tranType.equals("32") && !requiredQuantityArchivo.equals("000.000")) {
			validaStatus=true;
		}

		testCase.addTextEvidenceCurrentStep("Valor de 'THRESHOLD_AMT': " + THRESHOLD_AMT);
		testCase.addTextEvidenceCurrentStep("Valor de 'requiredQuantity': " + requiredQuantityArchivo);
		assertFalse("La Regla de Transformacion 14 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 14 fue respetada.");
		
		//***************************Paso 22**********************************************************
		addStep("Para la TR15 el campo DESCUENTO=\"0\" (archivo)\r\n"
				+ "Confirmar que DESCUENTO tenga formato numerico con patron \"00000.000\"");
		
		String discountArchivo = "";
		String discount_amt="";

		discountArchivo = tr.searchNode(rootNode, index, "discount");
		
		// Expresion regular para verificar el formato "00000.000"
        String regex = "^[0-9]{5}\\.[0-9]{3}$";
		
		 // Crear un patron a partir de la expresion regular
        pattern = Pattern.compile(regex);

        // Crear un matcher para comparar el campo1 con la expresion regular
        matcher = pattern.matcher(discountArchivo);
        
        if(useMock.equals("N")) {
			discount_amt = tr.getValueCampoOracle(queryDetail_STG, dbFCDASPFR, "DISCOUNT_AMT");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			discount_amt = tr.getValueCampoQuery(queryDetail_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "DISCOUNT_AMT");
		}
        
        int discount_amt2=0;
        
        if (discount_amt==null) {
        	discount_amt2 = 0;
        }else {
        	discount_amt2 = Integer.parseInt(discount_amt);
        }
        
        double numeroDouble = Double.parseDouble(discountArchivo);
        int discountArchivo2 = (int) numeroDouble;

        if(discountArchivo2 != discount_amt2) {
        	validaStatus=true;
        }
        
        if(tranType.equals("31") && !matcher.matches()) { //Si grouperArchivo no tiene el formato 000.000 y es igual a ref_prefix, fallara
			validaStatus=true;
		}else if (tranType.equals("32") && !discountArchivo.equals("00000.000")) {
			validaStatus=true;
		}
        
        testCase.addTextEvidenceCurrentStep("Valor de 'discount': " + discountArchivo);
        testCase.addTextEvidenceCurrentStep("Valor de 'DISCOUNT_AMT': " + discount_amt);
        assertFalse("La Regla de Transformacion 15 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 15 fue respetada.");
		
		//***************************Paso 23**********************************************************
		addStep("Para la TR16 en el campo TIP_DESC=\" \" (archivo):");
		
		validaStatus = true;
		
		String DISCOUNT_TYPE = "";
		
		if(useMock.equals("N")) {
			DISCOUNT_TYPE = tr.getValueCampoOracle(queryDetail_STG, dbFCDASPFR, "DISCOUNT_TYPE");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			DISCOUNT_TYPE = tr.getValueCampoQuery(queryDetail_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "DISCOUNT_TYPE");
		}
		
		String discountTypeArchivo = "";
		
		discountTypeArchivo = tr.searchNode(rootNode, index, "discountType");
		
		if (DISCOUNT_TYPE.equals("0") && discountTypeArchivo.equals("P")) {
			validaStatus = false;
		}
		
		if (DISCOUNT_TYPE.equals("1") && discountTypeArchivo.equals("A")) {
			validaStatus = false;
		}
		
		if (DISCOUNT_TYPE.equals("2") && discountTypeArchivo.equals("F")) {
			validaStatus = false;
		}
		
		//if (DISCOUNT_TYPE.equals("5") && discountTypeArchivo.equals("V")) {
		if (DISCOUNT_TYPE.equals("5") && discountTypeArchivo.equals("")) {
			validaStatus = false;
		}
		
		if (prom_tran_type.equals("1006") && discountTypeArchivo.equals("")) {
			validaStatus = false;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'DISCOUNT_TYPE': " + DISCOUNT_TYPE);
		testCase.addTextEvidenceCurrentStep("Valor de 'discountType': " + discountTypeArchivo);
		assertFalse("La Regla de Transformacion 16 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 16 fue respetada.");
		
		//***************************Paso 24**********************************************************
		addStep("Para la TR17 el campo OPTATIVO (archivo) cumpla con la siguiente condicion:\r\n"
				+ "Si PROM_TRAN_TYPE=1000\r\n"
				+ "   OPTATIVO =THRESHOLD_NO (valor de 1 a 9)\r\n"
				+ "Si PROM_TRAN_TYPE=1001\r\n"
				+ "   OPTATIVO =\"1\"\r\n"
				+ "Si PROM_TRAN_TYPE=1002\r\n"
				+ "   OPTATIVO =\"1\"\r\n"
				+ "Si PROM_TRAN_TYPE=1006\r\n"
				+ "   OPTATIVO =\"1\"\r\n"
				+ "Si PROM_TRAN_TYPE=1007\r\n"
				+ "   OPTATIVO =THRESHOLD_NO (valor de 1 a 9)\r\n"
				+ "Confirmar trim al OPTATIVO y rellenar con espacios a la derecha hasta completar 1 caracter\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 14 para obtener el valor del campo PROM_TRAN_TYPE de la promocion en la tabla FEM_POS_PROM_DETAIL_STG");
		
		validaStatus = true;
		
		String optativeArchivo = "";
		
		optativeArchivo = tr.searchNode(rootNode, index, "optative");
		
		if(prom_tran_type.equals("1000") || prom_tran_type.equals("1007") && optativeArchivo.matches("[0-9]")) {
			validaStatus = false;
		}
		if(prom_tran_type.equals("1001") || prom_tran_type.equals("1002") || prom_tran_type.equals("1006") && optativeArchivo.equals("1")) {
			validaStatus = false;
		}
		
		if (optativeArchivo.length() != 1) {
            validaStatus = true;	
        }
		
		testCase.addTextEvidenceCurrentStep("Valor de 'optative': " + optativeArchivo);
		assertFalse("La Regla de Transformacion 17 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 17 fue respetada.");
		
		//***************************Paso 25**********************************************************
		addStep("Para la TR18 el campo DESCRIP1 (description) debe ser igual al campo FEM_POS_MODS_PRM_STG.PROM_NAME (BD FCDASPFR)\r\n"
				+ "Confirmar trim a DESCRIP1 y rellenado con espacios a la izquierda hasta completar 40 caracteres.\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 12 para obtener el valor del campo DESCRIP1 de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		String prom_name = "";
		
		if(useMock.equals("N")) {
			prom_name = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "PROM_NAME");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			prom_name = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "PROM_NAME");
		}
		
		String descriptionArchivo = "";
		
		descriptionArchivo = tr.searchNode(rootNode, index, "description");
		
		if (descriptionArchivo.length() != 40) {
            validaStatus = true;
        }
		
		descriptionArchivo = descriptionArchivo.trim();
		prom_name = prom_name.trim();
		
		if(!descriptionArchivo.contains(prom_name)) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PROM_NAME': " + prom_name);
		testCase.addTextEvidenceCurrentStep("Valor de 'description': " + descriptionArchivo);
		assertFalse("La Regla de Transformacion 18 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 18 fue respetada.");
		
		//***************************Paso 26**********************************************************
		addStep("Para la TR19 el campo SUBCAT (itemSubcategory) =\" \" \r\n"
				+ "Confirmar trim al SUBCAT y rellenado con espacios a la derecha hasta completar 4 caracteres");
		
		String dept = "";
		
		if(useMock.equals("N")) {
			dept = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "DEPT");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			dept = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "DEPT");
		}
		
		String itemSubcategoryArchivo = "";
		
		itemSubcategoryArchivo = tr.searchNode(rootNode, index, "itemSubcategory");
		String itemSubcategoryArchivo2 = itemSubcategoryArchivo.trim();
		
		testCase.addTextEvidenceCurrentStep("Valor de 'DEPT': " + dept);
		
		if(dept  == null) {
			dept = "";
		}
		
		validaStatus = true;
		if(prom_tran_type.equals("1001") && itemSubcategoryArchivo2.equals(dept)) {
			validaStatus = false;
		}else {
			if(itemSubcategoryArchivo.equals("")){
				validaStatus = false;
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'itemSubcategory': " + itemSubcategoryArchivo);
		assertFalse("La Regla de Transformacion 19 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 19 fue respetada.");
		
		//***************************Paso 27**********************************************************
		addStep("Para la TR20 el campo SEGMENTO (itemSegment) =\" \" \r\n"
				+ "Confirmar trim al SEGMENTO y rellenado con espacios a la derecha hasta completar 4 caracteres");
		
		String class1 = "";
		
		if(useMock.equals("N")) {
			class1 = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "CLASS");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			class1 = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "CLASS");
		}
		
		String itemSegmentArchivo = "";
		
		itemSegmentArchivo = tr.searchNode(rootNode, index, "itemSegment");
		String itemSegmentArchivo2 = itemSegmentArchivo.trim();
		
		testCase.addTextEvidenceCurrentStep("Valor de 'CLASS': " + class1);
		
		if(class1==null) {
			class1="";
		}
		
		validaStatus = true;
		if(prom_tran_type.equals("1001") && itemSegmentArchivo2.equals(class1)) {
			validaStatus = false;
		}else {
			if(itemSegmentArchivo.equals("")){
				validaStatus = false;
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PROM_TRAN_TYPE': " + prom_tran_type);
		testCase.addTextEvidenceCurrentStep("Valor de 'itemSegment': " + itemSegmentArchivo);
		assertFalse("La Regla de Transformacion 20 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 20 fue respetada.");
		
		//***************************Paso 28**********************************************************
		addStep("Para la TR21 el campo SUBSEGTO (itemSubsegment) =\" \" \r\n"
				+ "Confirmar trim al SUBSEGTO y rellenado con espacios a la derecha hasta completar 4 caracteres");
		
		String subclass1 = "";
		
		if(useMock.equals("N")) {
			subclass1 = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "SUBCLASS");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			subclass1 = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "SUBCLASS");
		}
		
		String itemSubsegmentArchivo = "";
		
		itemSubsegmentArchivo = tr.searchNode(rootNode, index, "itemSubsegment");
		String itemSubsegmentArchivo2 = itemSubsegmentArchivo.trim();
		
		testCase.addTextEvidenceCurrentStep("Valor de 'SUBCLASS': " + subclass1);
		
		if(subclass1==null) {
			subclass1="";
		}

		validaStatus = true;
		if(prom_tran_type.equals("1001") && itemSubsegmentArchivo2.equals(subclass1)) {
			validaStatus = false;
		}else {
			if(itemSubsegmentArchivo.equals("")){
				validaStatus = false;
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PROM_TRAN_TYPE': " + prom_tran_type);
		testCase.addTextEvidenceCurrentStep("Valor de 'itemSubsegment': " + itemSubsegmentArchivo);
		assertFalse("La Regla de Transformacion 21 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 21 fue respetada.");
		
		//***************************Paso 29**********************************************************
		addStep("Para la TR22 el campo ART1 (durationCode) en el archivo debe ser igual a la cadena concatenada de la tabla FEM_POS_MODS_PRM_STG (BD FCDASPFR):\r\n"
				+ "'1' + START_TIME (si es null usar '0000') + END_TIME (si es null usar '2359') + ITEM_SHORT_DESC\r\n"
				+ "Confirmar trim al ART1 y rellenado con espacios a la derecha hasta completar 16 caracteres\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor de los campos de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		String start_time = "";
		String end_time = "";
		String item_short_desc = "";
		
		if(useMock.equals("N")) {
			start_time = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "START_DATE");
			end_time = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "END_DATE");
			item_short_desc = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "ITEM_SHORT_DESC");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			start_time = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "START_DATE");
			end_time = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "END_DATE");
			item_short_desc = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "ITEM_SHORT_DESC");
		}
		
		if(useMock.equals("N")) {
			// Definir el formato de la fecha original
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		}else {
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");
		}
		
        // Parsear la fecha
        LocalDateTime start_time2 = LocalDateTime.parse(start_time, formatter);
        // Formatear la hora en el formato deseado (hhmm)
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HHmm");
        // Asignar el valor formateado a var1
        String start_time3 = start_time2.format(formatterHora);
		
        // Parsear la fecha
        LocalDateTime end_time2 = LocalDateTime.parse(end_time, formatter);
        // Asignar el valor formateado a var1
        String end_time3 = end_time2.format(formatterHora);
		
		String durationCodeArchivo = "";
		
		durationCodeArchivo = tr.searchNode(rootNode, index, "durationCode");
		
		if(start_time == "" || start_time == null) {
			start_time = "0000";
		}
		
		if(end_time == "" || end_time == null) {
			end_time = "2359";
		}
		
		String concatenacion = "1" + start_time3 + end_time3 + item_short_desc;
		
		if (durationCodeArchivo.length() != 16) {
            validaStatus = true;
        }
		
		if(!durationCodeArchivo.equals(concatenacion)) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'START_DATE': " + start_time);
		testCase.addTextEvidenceCurrentStep("Valor de 'END_DATE': " + end_time);
		testCase.addTextEvidenceCurrentStep("Valor de solo hhmm de 'START_DATE' (es lo que se pone en el archivo): " + start_time3);
		testCase.addTextEvidenceCurrentStep("Valor de solo hhmm de 'END_DATE' (es lo que se pone en el archivo): " + end_time3);
		testCase.addTextEvidenceCurrentStep("Valor de 'ITEM_SHORT_DESC': " + item_short_desc);
		testCase.addTextEvidenceCurrentStep("Valor de 'durationCode': " + durationCodeArchivo);
		assertFalse("La Regla de Transformacion 22 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 22 fue respetada.");
		
		//***************************Paso 30**********************************************************
		addStep("Para la TR23 confirmar en el archivo que el campo CANT1 (grouper) cumpla con la condicion:\r\n"
				+ "Si REF_PREFIX es NULL\r\n"
				+ "   CANT1=0\r\n"
				+ "De lo contrario\r\n"
				+ "El valor de CANT1 debe ser igual a FEM_POS_MODS_PRM_STG.REF_PREFIX (BD FCDASPFR)\r\n"
				+ "Confirmar CANT1 tenga formato numerico con patron \"000.000\"\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 12 para obtener el valor del campo CANT1 de la promocion en la tabla FEM_POS_MODS_PRM_STG");

		String ref_prefix = "";
		
		if(useMock.equals("N")) {
			ref_prefix = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "REF_PREFIX");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			ref_prefix = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "REF_PREFIX");
		}
		
		String grouperArchivo = "";
		
		grouperArchivo = tr.searchNode(rootNode, index, "grouper");
		
	   // Crear un patron a partir de la expresion regular
       pattern = Pattern.compile(regex2);

       // Crear un matcher para comparar el campo1 con la expresion regular
       matcher = pattern.matcher(grouperArchivo);
		
		if(ref_prefix == "" || ref_prefix == null && !grouperArchivo.equals("0")) {
			validaStatus=true;
		}else {
			if(!grouperArchivo.equals(ref_prefix) && !matcher.matches()) { //Si grouperArchivo no tiene el formato 000.000 y es igual a ref_prefix, fallara
				validaStatus=true;
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'REF_PREFIX': " + ref_prefix);
		testCase.addTextEvidenceCurrentStep("Valor de 'grouper': " + grouperArchivo);
		assertFalse("La Regla de Transformacion 23 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 23 fue respetada.");
		
		//***************************Paso 31**********************************************************
		addStep("Para la TR24 confirmar en el archivo que el campo ACCION (action) =\"F\"");
		
		String actionArchivo = "";
		
		actionArchivo = tr.searchNode(rootNode, index, "action");
		
		if(tranType.equals("31") && !actionArchivo.equals("A")) {
			validaStatus=true;
		}else if(tranType.equals("32") && !actionArchivo.equals("F")) {
			validaStatus=true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'action': " + actionArchivo);
		assertFalse("La Regla de Transformacion 24 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 24 fue respetada.");
		
		//***************************Paso 32**********************************************************
		addStep("Para la TR25 confirmar en el archivo que el campo BINCODE (binCode) con esta condicion:\r\n"
				+ "Si FEM_POS_MODS_PRM_STG.MANUAL_PRICE_ENTRY = ('M' o 'E')\r\n"
				+ "    BINCODE = FEM_POS_MODS_PRM_STG.PROM_BIN_CODE\r\n"
				+ "De lo contrario\r\n"
				+ "    BINCODE = \"\" (Cadena Vacia)\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 12 para obtener el valor del campo BINCODE de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		validaStatus=true;
		
		String prom_bin_code = "";
		
		if(useMock.equals("N")) {
			prom_bin_code = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "PROM_BIN_CODE");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			prom_bin_code = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "PROM_BIN_CODE");
		}
		
		String binCodeArchivo = "";
		
		binCodeArchivo = tr.searchNode(rootNode, index, "binCode");
		
		if(manual_price_entry.equals("M") || manual_price_entry.equals("E") && prom_bin_code.equals(binCodeArchivo)) {
			validaStatus=false;
		} else {
			if(binCodeArchivo.equals("")) {
				validaStatus=false;				
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'MANUAL_PRICE_ENTRY': " + manual_price_entry);
		testCase.addTextEvidenceCurrentStep("Valor de 'PROM_BIN_CODE': " + prom_bin_code);
		testCase.addTextEvidenceCurrentStep("Valor de 'binCode': " + binCodeArchivo);
		assertFalse("La Regla de Transformacion 25 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 25 fue respetada.");
		
		//***************************Paso 33**********************************************************
		addStep("Para la TR26 confirmar en el archivo el campo MSGPPAG (messagePsg) con esta condicion:\r\n"
				+ "Si FEM_POS_MODS_PRM_STG.MANUAL_PRICE_ENTRY = ('M' o 'E')\r\n"
				+ "    MSGPPAG = FEM_POS_MODS_PRM_STG.MSG_POS\r\n"
				+ "De lo contrario\r\n"
				+ "    MSGPPAG = \"\" (Cadena Vacia)\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 12 para obtener el valor del campo MSGPPAG de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		validaStatus=true;
		
		String msg_pos = "";
		
		if(useMock.equals("N")) {
			msg_pos = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "MSG_POS");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			msg_pos = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "MSG_POS");
		}
		
		String messagePsgArchivo = "";
		
		messagePsgArchivo = tr.searchNode(rootNode, index, "messagePsg");
		
		if(manual_price_entry.equals("M") || manual_price_entry.equals("E") && msg_pos.equals(messagePsgArchivo)) {
			validaStatus=false;
		} else {
			if(messagePsgArchivo.equals("")) {
				validaStatus=false;				
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'MANUAL_PRICE_ENTRY': " + manual_price_entry);
		testCase.addTextEvidenceCurrentStep("Valor de 'MSG_POS': " + msg_pos);
		testCase.addTextEvidenceCurrentStep("Valor de 'messagePsg': " + messagePsgArchivo);
		assertFalse("La Regla de Transformacion 26 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 26 fue respetada.");
		
		//***************************Paso 34**********************************************************
		addStep("Para la TR27 el campo DOC_NAME (archivo) debe tener la estructura:\r\n"
				+ "\"PRM\"<plaza><tienda><sysdate(yyMMddHHmmss)><id>\".json.gz\"\r\n"
				+ "\r\n"
				+ "Los valores se sacan de estas condiciones:\r\n"
				+ "<plaza>.- campo \"ORACLE_CR_SUPERIOR\" de XXFC_MAESTRO_DE_CRS_V se debe filtrar por el campo \"RETEK_CR\" igual al campo \"LOCATION\" de tabla WMUSER.POS_PRM_HEAD en BD FCDASPFR, ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"\r\n"
				+ "\r\n"
				+ "<tienda>.- campo \"ORACLE_CR\" de XXFC_MAESTRO_DE_CRS_V se debe filtrar por el campo \"RETEK_CR\" igual al campo \"LOCATION\" de tabla WMUSER.POS_PRM_HEAD en BD FCDASPFR, ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"\r\n"
				+ "\r\n"
				+ "<sysdate(yyMMddHHmmss)>.- Obtener fecha del sistema con formato \"yyMMddHHmmss\"\r\n"
				+ "\r\n"
				+ "<id>.- Lo genera Azure\r\n"
				+ "\r\n"
				+ "Nota: Los campos son de la BD AVEBQA");
		
		validaStatus=true;

		String docname = tr.getValueCampoQuery(querySTS, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_DOC_NAME");
		
		if(tr.containsValidDate(docname)) {
			validaStatus=false;
		}
		
		String validaArchivo = "PRM" + oracleCR_superior + oracleCR;
		
		if(docname.contains(validaArchivo)) {
			validaStatus=false;
		}
		
		if(docname.contains(".json.gz")) {
			validaStatus=false;
		}
		
		testCase.addTextEvidenceCurrentStep("Nombre de archivo: " + docname);
		assertFalse("La Regla de Transformacion 27 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 27 fue respetada.");
		
		//***************************Paso 35**********************************************************
		addStep("Para la TR28 confirmar que el campo CR_PLAZA es igual a \"ORACLE_CR_SUPERIOR\" de XXFC_MAESTRO_DE_CRS_V\r\n"
				+ "De la vista mencionada se debe filtrar por el campo \"RETEK_CR\" igual al campo \"LOCATION\" de tabla WMUSER.POS_PRM_HEAD en DAS_OPERATIVO, ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"\r\n"
				+ "\r\n"
				+ "Nota: Los campos son de la BD AVEBQA");
		
		if (!cr_plaza.equals(oracleCR_superior)) {
			 validaStatus = true;
		 }
		
		testCase.addTextEvidenceCurrentStep("Valor de CR_PLAZA: " + cr_tienda);
		 testCase.addTextEvidenceCurrentStep("Valor de ORACLE_CR_SUPERIOR en XXFC_MAESTRO_DE_CRS_V y valor de CR_PLAZA son iguales: " + oracleCR + " y " + cr_tienda);
		assertFalse("La Regla de Transformacion 28 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 28 fue respetada.");
		
		//***************************Paso 36**********************************************************
		addStep("Para la TR29 confirmar que el campo CR_TIENDA es igual a \"ORACLE_CR\" de XXFC_MAESTRO_DE_CRS_V\r\n"
				+ "De la vista mencionada se debe filtrar por el campo \"RETEK_CR\" igual al campo \"LOCATION\" de tabla WMUSER.POS_PRM_HEAD en DAS_OPERATIVO, ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"\r\n"
				+ "\r\n"
				+ "Nota: Los campos son de la BD AVEBQA");
		
		if (!cr_tienda.equals(oracleCR)) {
			 validaStatus = true;
		 }
		
		 testCase.addTextEvidenceCurrentStep("Valor de CR_TIENDA: " + cr_tienda);
		 testCase.addTextEvidenceCurrentStep("Valor de ORACLE_CR en XXFC_MAESTRO_DE_CRS_V y valor de CR_TIENDA son iguales: " + oracleCR + " y " + cr_tienda);
		assertFalse("La Regla de Transformacion 29 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 29 fue respetada.");
		
		//***************************Paso 37**********************************************************
		addStep("Para la TR30 hacer la consulta: " + tr30);
		
		validaStatus = tr.queryOracle(tr30, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, tr30);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 38**********************************************************
		addStep("Para la TR31 hacer la consulta: " + tr31);
		
		validaStatus = tr.queryOracle(tr31, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, tr31);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 39**********************************************************
		addStep("Para la TR32 hacer la consulta: " + tr32);
		
		validaStatus = tr.queryOracle(tr32, dbFCWMESIT);
		tr.processQueryInChunks(dbFCWMESIT, tr32);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 40**********************************************************
		 
		 addStep("Para la TR34 hacer la consulta: " + tr34);
		
		 validaStatus = tr.queryOracle(tr34, dbFCDASPFR);
		 tr.processQueryInChunks(dbFCDASPFR, tr34);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
	}
	
	@Override
	public String setTestFullName() {
		return "MTC-FT-003 Validar TR Archivo Cloud Promocion Compleja MultiUnit Tienda Digital";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Valida las Reglas de Tranformacion del archivo generado por PR63_CLOUD de promocion compleja MultiUnit para tienda digital";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Valeria Fernandez";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Tener acceso y poder ejecutar los casos de prueba de la matriz SIGA_Script de Pruebas E2E_PR63_CLOUD\r\n"
				+ "2. Tener acceso a las BD's AVEBQA y FCDASPFR\r\n"
				+ "3. Tener acceso a Azure Storage";
	
	}

}
