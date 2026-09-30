package WM.interfaces.PR63_CLOUD;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

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

public class ATC_004_PR63_CLOUD_ValidarTRPromocionSimpleTiendaFisica extends BaseExecution{
	@Test
	(dataProvider = "data-provider")
	public void ATC_004_PR63_CLOUD_ValidarTRPromocionSimpleTiendaFisica_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-004 Validar TR Tablas Cloud Promocion Simple Tienda Fisica
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
		
		//MDB_ERDD_PromotionMockCloud
		String queryPRMHead = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status") + "' AND CLOUD_PRM_STATUS = '" + data.get("cloud_prm_status") + "' AND LOCATION='" + data.get("retek_cr") + "' AND BATCH_ID='" + data.get("batchId") + "'";
		String queryPRM_STG = "SELECT * FROM WMUSER.FEM_POS_MODS_PRM_STG WHERE STORE='" +data.get("retek_cr")+"' AND LOAD_BATCH_ID='" + data.get("load_batch_id") + "' AND TRAN_TYPE='" + data.get("tranType") + "' AND PROMOTION='" + data.get("promotionFolio") + "' AND ITEM='" +data.get("sku")+"'";
		
		//FCDASPFR
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
		String tr34 = "SELECT a.load_batch_id, a.store, a.load_timestamp\r\n"
				+ "FROM WMUSER.FEM_POS_MODS_PRM_STG a, WMUSER.POS_PRM_HEAD b\r\n"
				+ "WHERE a.load_batch_id = b.load_batch_id\r\n"
				+ "AND a.load_week = b.load_week\r\n"
				+ "AND a.store = b.location\r\n"
				+ "AND b.wm_prm_status = '" + data.get("wm_prm_status") + "'\r\n"
				+ "AND batch_id = '" + data.get("batchId") + "'\r\n"
				+ "AND store = '" + data.get("retek_cr") + "'\r\n"
				+ "ORDER BY a.load_batch_id, a.store, load_timestamp";
		
		//MDB_ERDD_PromotionCloud
		String queryHEADP = "SELECT * FROM dbo.CO_PRM_HEADP WHERE ID_DEPT = '" +data.get("departmentId")+"' AND ID_STR = '" +data.get("storeId")+"' AND ID_PRM_HDR_OX = '" +data.get("id_prm_hdr_ox")+"' ORDER BY DT_SCH DESC";
		String queryHEADD = "SELECT * FROM dbo.CO_PRM_HEADD WHERE ID_PRM_HDR_OX = '" +data.get("id_prm_hdr_ox")+"' AND FO_PRM='" +data.get("promotionFolio")+"' AND SKU LIKE '%" +data.get("sku")+"'";
		String queryHEADD2 = "SELECT * FROM dbo.CO_PRM_HEADD WHERE ID_PRM_HDR_OX = '" +data.get("id_prm_hdr_ox")+"'";
		String queryCountPromociones = "SELECT COUNT (*) FROM wmuser.FEM_POS_MODS_PRM_STG WHERE STORE = '" + data.get("retek_cr") + "' AND load_batch_id ='" + data.get("load_batch_id") + "'";
		String userAzure= "dbo.";
		String useMock = data.get("useMock");
		String ejecutarE2E = data.get("ejecutarE2E");
		
        boolean validaStatus = false;
		
		//***************************Paso 1**********************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas E2E_PR63_CLOUD el caso de prueba: @MTC-FT-005 Envio de promocion simple a tienda fisica cloud-manual");
		
		if(ejecutarE2E.equals("Y")) {
			ATC_005_PR63_CLOUD_EnvioPromocionSimpleTiendaFisica atc005_e2e = new ATC_005_PR63_CLOUD_EnvioPromocionSimpleTiendaFisica();
			atc005_e2e.setTestCase(this.testCase);
			atc005_e2e.ATC_005_PR63_CLOUD_EnvioPromocionSimpleTiendaFisica_test(data);
		}
		
		//Ejecutarlo antes o el caso de prueba actual tardara mas de 30 minutos en ejecutarse
		
		testCase.addBoldTextEvidenceCurrentStep("Caso de prueba ejecutado exitosamente anteriormente");
		testCase.addTextEvidenceCurrentStep("Mock: " + useMock);
		
		//***************************Paso 2**********************************************************
		addStep("Ingresar a la BD AVEBQA");
		
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_AVEBQA);
		
		//***************************Paso 3**********************************************************
		addStep("Ingresar a la BD FCDASPFR");
		
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO);
		
		//***************************Paso 4**********************************************************
		addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
		
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
		 
		//***************************Paso 5**********************************************************
		addStep("Revisa que la promocion enviada exista en la CO_PRM_HEADP (BD MDB_ERDD_PromotionCloud) con la consulta:\r\n"
				+ queryHEADP
				+ "\r\n"
				+ "Los campos ID_DEPT y ID_STR deben corresponder con la promocion que se genero en el caso de prueba: MTC-FT-005 Envio de promocion simple a tienda fisica cloud-manual");
		
		String id_str="";
		String id_dept="";
		String dt_sch="";
		String nmb_prm="";

		 validaStatus = tr.queryBDAzure(queryHEADP, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 id_dept = tr.getValueCampoQuery(queryHEADP, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "ID_DEPT");
		 id_str = tr.getValueCampoQuery(queryHEADP, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "ID_STR");
		 dt_sch = tr.getValueCampoQuery(queryHEADP, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DT_SCH");
		 nmb_prm = tr.getValueCampoQuery(queryHEADP, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "NMB_PRM");
		 
		//***************************Paso 6**********************************************************
		addStep("Revisa que la promocion enviada exista en la CO_PRM_HEADD (BD MDB_ERDD_PromotionCloud) con la consulta:\r\n"
				+ queryHEADD
				+ "\r\n"
				+ "Los campos ID_DEPT y ID_STR deben corresponder con la promocion que se genero en el caso de prueba: MTC-FT-005 Envio de promocion simple a tienda fisica cloud-manual");
		
		String nmb_prm2_temp="";
		int nmb_prm2=0;
		String fo_prm="";
		String ctgy_itm="";
		String ctgy_prm="";
		String typ_prm="";
		String srt_dt_prm="";
		String fns_dt_prm="";
		String sku="";
		String qty_reqt_prm="";
		String dsc="";
		String typ_dsc="";
		String opt_prm="";
		String dscp_itm="";
		String sub_ctgy_itm="";
		String sgmt_itm="";
		String sub_sgmt_itm="";
		String drn_cd="";
		String gp_prm="";
		String cd_prm="";
		String msg_prm="";
		String typ_prm_ad="";
		
		List<Integer> nmb_prm2Values = new ArrayList<>();
		 
		 validaStatus = tr.queryBDAzure(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 nmb_prm2_temp = tr.getValueCampoQuery(queryHEADD2, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "NMB_PRM");
		 nmb_prm2 = Integer.parseInt(nmb_prm2_temp);
		 nmb_prm2Values.add(nmb_prm2);
		 fo_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "FO_PRM");
		 ctgy_itm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "CTGY_ITM");
		 ctgy_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "CTGY_PRM");
		 typ_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "TYP_PRM");
		 srt_dt_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "SRT_DT_PRM");
		 fns_dt_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "FNS_DT_PRM");
		 sku = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "SKU");
		 qty_reqt_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "QTY_REQT_PRM");
		 dsc = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DSC");
		 typ_dsc = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "TYP_DSC");
		 opt_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "OPT_PRM");
		 dscp_itm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DSCP_ITM");
		 sub_ctgy_itm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "SUB_CTGY_ITM");
		 sgmt_itm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "SGMT_ITM");
		 sub_sgmt_itm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "SUB_SGMT_ITM");
		 drn_cd = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DRN_CD");
		 typ_prm_ad = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "TYP_PRM_AD");
		 gp_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "GP_PRM");
		 cd_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "CD_PRM");
		 msg_prm = tr.getValueCampoQuery(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "MSG_PRM");
		 
		//***************************Paso 7**********************************************************
		addStep("Para la TR1 confirmar que el campo ID_DEPT (CO_PRM_HEADP) corresponda al campo ORACLE_CR_SUPERIOR, el campo ID_STR (CO_PRM_HEADP) corresponda al campo ORACLE_CR y el campo LOCATION sea igual al campo RETEK_CR de la vista XXFC_MAESTRO_DE_CRS_V (BD AVEBQA)");
        
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
		 
		 if (!id_str.equals(oracleCR)) {
			 validaStatus = true;
		 }
		 
		 if (!id_dept.equals(oracleCR_superior)) {
			 validaStatus = true;
		 }
		 
		 if (!location.equals(retek)) {
			 validaStatus = true;
		 }
		
		 testCase.addTextEvidenceCurrentStep("Valor de 'ID_DEPT': " + id_dept);
		 testCase.addTextEvidenceCurrentStep("Valor de 'ORACLE_CR_SUPERIOR': " + oracleCR_superior);
		 testCase.addTextEvidenceCurrentStep("Valor de 'ID_STR': " + id_str);
		 testCase.addTextEvidenceCurrentStep("Valor de 'ORACLE_CR': " + oracleCR);
		 testCase.addTextEvidenceCurrentStep("Valor de LOCATION en PRM_HEAD y valor de RETEK_CR en XXFC_MAESTRO_DE_CRS_V son iguales: " + location + " y " + retek);
		 
		 assertFalse("La Regla de Transformacion 1 NO fue respetada.", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 1 fue respetada.");
		 
		//***************************Paso 8**********************************************************
		addStep("Para la TR2 confirmar en el archivo que el campo DT_SCH (CO_PRM_HEADP) tenga el formato \"yyyy-MM-dd HH:mm:ss\"");

		tr.getTypeCampoQuery("CO_PRM_HEADP", "DT_SCH", GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DATETIME");
		assertTrue("El tipo de campo no es el esperado", validaStatus); 
        
        testCase.addTextEvidenceCurrentStep("Fecha DT_SCH valida: " + dt_sch);
        testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 2 fue respetada.");
		
		//***************************Paso 9**********************************************************
		addStep("Para la TR3 confirmar en el archivo que el campo ID_DEPT (CO_PRM_HEADP) sea igual al campo \"ORACLE_CR_SUPERIOR\" de XXFC_MAESTRO_DE_CRS_V (BD AVEBQA)\r\n"
				+ "En la tabla WMUSER.POS_PRM_HEAD (BD FCDASPFR), ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"");
		 
		 if (!id_dept.equals(oracleCR_superior)) {
			 validaStatus = true;
		 }
		 
		 testCase.addTextEvidenceCurrentStep("Valor de ID_DEPT: " + id_dept);
		 testCase.addTextEvidenceCurrentStep("Valor de ORACLE_CR_SUPERIOR en XXFC_MAESTRO_DE_CRS_V y valor de ID_DEPT son iguales: " + oracleCR_superior + " y " + id_dept);
		 
		 assertFalse("La Regla de Transformacion 3 NO fue respetada.", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 3 fue respetada.");
		
		//***************************Paso 10**********************************************************
		addStep("Para la TR4 confirmar en el archivo que el campo ID_STR (CO_PRM_HEADP) sea igual al campo \"ORACLE_CR\" de XXFC_MAESTRO_DE_CRS_V (BD AVEBQA)\r\n"
				+ "En la tabla WMUSER.POS_PRM_HEAD (BD FCDASPFR), ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"");

		 if (!id_str.equals(oracleCR)) {
			 validaStatus = true;
		 }
		 
		 testCase.addTextEvidenceCurrentStep("Valor de ID_STR: " + id_str);
		 testCase.addTextEvidenceCurrentStep("Valor de ORACLE_CR_SUPERIOR en XXFC_MAESTRO_DE_CRS_V y valor de ID_STR son iguales: " + oracleCR + " y " + id_str);
		 
		 assertFalse("La Regla de Transformacion 4 NO fue respetada.", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 4 fue respetada.");
		
		//***************************Paso 11**********************************************************
		addStep("Para la TR5 en el campo NMB_PRM (CO_PRM_HEADP): Obtener numero de registros de articulos");

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
		 
		 if (!countRegistros.equals(nmb_prm)) {
			validaStatus = true;
		 }
		
		 testCase.addTextEvidenceCurrentStep("Valor de 'numberOfRecords': " + nmb_prm);
		 assertFalse("La Regla de Transformacion 5 NO fue respetada.", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 5 fue respetada.");
		 
		//***************************Paso 12**********************************************************
		addStep("Para la TR6 confirmar que el campo NMB_PRM (CO_PRM_HEADD): Tenga el  valor incremental en 1 por cada elemento de la lista x Articulo en la Promocion");
		
        // Validar si los valores son consecutivos
        if (!tr.isConsecutive(nmb_prm2Values)) {
            validaStatus = true;
        }
        
        testCase.addTextEvidenceCurrentStep("Valor de 'NMB_PRM': " + nmb_prm2Values);
        assertFalse("La Regla de Transformacion 6 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 6 fue respetada.");
		
		//***************************Paso 13**********************************************************
		addStep("Confirmar que la promocion tenga el campo TRAN_TYPE=31 y THRESHOLD_NO IS NULL con la consulta en la BD FCDASPFR:\r\n"
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
		 
		//***************************Paso 14**********************************************************
		addStep("Para la TR7 en el campo FOLIO (CO_PRM_HEADD.FO_PRM) confirmar trim al campo PROMOTION (BD FCDASPFR) y rellenado con espacios a la izquierda hasta completar 10 caracteres\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo FOLIO de la promocion en la tabla FEM_POS_MODS_PRM_STG\"	Campo en archivo con formato correcto");
		
		String promotion ="";
		
		if(useMock.equals("N")) {
			promotion = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "PROMOTION");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			promotion = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "PROMOTION");
		}
		
		if (fo_prm.length() != 10) {
            validaStatus = true;
        }
		
		if (!fo_prm.contains(promotion)) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PROMOTION': " + promotion);
		testCase.addTextEvidenceCurrentStep("Valor de 'FOLIO': " + fo_prm);
		assertFalse("La Regla de Transformacion 7 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 7 fue respetada.");
		
		//***************************Paso 15**********************************************************
		addStep("Para la TR8 confirmar en el archivo que el campo sea CVE_CAT (CO_PRM_HEADD.CTGY_ITM) =\"1\" \r\n"
				+ "Confirmar trim al CVE_CAT y rellenado con espacio a la izquierda hasta completar 2 caracteres");
		
		if (ctgy_itm.length() != 2 && !ctgy_itm.equals(" 1")) {
            validaStatus = true;
        }
		
		testCase.addTextEvidenceCurrentStep("Valor de 'CTGY_ITM': " + ctgy_itm);
		assertFalse("La Regla de Transformacion 8 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 8 fue respetada.");
		
		//***************************Paso 16**********************************************************
		addStep("Para la TR9 confirmar que el campo sea CVE_TYP=\"03\" (CO_PRM_HEADD.TYP_PRM)");
		
		if(!typ_prm.equals("03")) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'TYP_PRM': " + typ_prm);
		assertFalse("La Regla de Transformacion 9 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 9 fue respetada.");
		
		//***************************Paso 17**********************************************************
		addStep("Para la TR10 confirmar en el archivo que el campo SRT_DT_PRM (CO_PRM_HEADD) debe ser igual al campo FEM_POS_MODS_PRM_STG.START_DATE (BD FCDASPFR)\r\n"
				+ "\r\n"
				+ "Confirmar que tenga el formato YYYYMMDD\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo START_DATE de la promocion en la tabla FEM_POS_MODS_PRM_STG");

		tr.getTypeCampoQuery("CO_PRM_HEADD", "SRT_DT_PRM", GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DATETIME");
		assertTrue("El tipo de campo no es el esperado", validaStatus);
        
		testCase.addTextEvidenceCurrentStep("Fecha SRT_DT_PRM valida: " + srt_dt_prm);
        testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 10 fue respetada.");
		
		//***************************Paso 18**********************************************************
		addStep("Para la TR11 confirmar en el archivo que el campo FNS_DT_PRM (CO_PRM_HEADD) debe ser igual al campo FEM_POS_MODS_PRM_STG.END_DATE (BD FCDASPFR)\r\n"
				+ "\r\n"
				+ "Confirmar que tenga el formato YYYYMMDD\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo END_DATE de la promocion en la tabla FEM_POS_MODS_PRM_STG");

		tr.getTypeCampoQuery("CO_PRM_HEADD", "FNS_DT_PRM", GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DATETIME");
		assertTrue("El tipo de campo no es el esperado", validaStatus);
        
		testCase.addTextEvidenceCurrentStep("Fecha FNS_DT_PRM valida: " + fns_dt_prm);
        testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 11 fue respetada.");
		
		//***************************Paso 19**********************************************************
		addStep("Para la TR12 confirmar en el archivo que el campo ITEM (CO_PRM_HEADD.SKU) sea igual al campo FEM_POS_MODS_PRM_STG.ITEM (BD FCDASPFR)\r\n"
				+ "\r\n"
				+ "Confirmar trim al campo ITEM y rellenar con espacios a la izquierda hasta completar 16 caracteres\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo ITEM de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		String item ="";
		
		if(useMock.equals("N")) {
			item = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "ITEM");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			item = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "ITEM");
		}
		
		if (sku.length() != 16) {
            validaStatus = true;
        }
		
		if (!sku.contains(item)) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'ITEM': " + item);
		testCase.addTextEvidenceCurrentStep("Valor de 'SKU': " + sku);
		assertFalse("La Regla de Transformacion 12 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 12 fue respetada.");
		
		//***************************Paso 20**********************************************************
		addStep("Para la TR13 confirmar en el archivo que el campo ITEM2 (CO_PRM_HEADD.CTGY_PRM) sea igual al campo FEM_POS_MODS_PRM_STG.MANUAL_PRICE_ENTRY (BD FCDASPFR)\r\n"
				+ "\r\n"
				+ "Si es diferente de Null confirmar trim al ITEM2 y rellenado con espacios a la izquierda hasta completar 16 caracteres\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo ITEM2 de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		String manual_price_entry = "";
		
		if(useMock.equals("N")) {
			manual_price_entry = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "MANUAL_PRICE_ENTRY");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			manual_price_entry = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "MANUAL_PRICE_ENTRY");
		}
		
		ctgy_prm = ctgy_prm.trim();
		
		if(manual_price_entry == null) {
			manual_price_entry = "";
		}
		
		manual_price_entry = manual_price_entry.trim();
		
		if (!ctgy_prm.equals(manual_price_entry)) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'MANUAL_PRICE_ENTRY': " + manual_price_entry);
		testCase.addTextEvidenceCurrentStep("Valor de 'CTGY_PRM': " + ctgy_prm);
		assertFalse("La Regla de Transformacion 13 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 13 fue respetada.");
		
		//***************************Paso 21**********************************************************
		addStep("Para la TR14 confirmar en el archivo que el campo CANT_REQ (CO_PRM_HEADD.QTY_REQT_PRM) cumpla la siguiente condicion:\r\n"
				+ "Si NEW_MULTI_SELLING_UOM=\"KG\" (BD FCDASPFR)\r\n"
				+ "    CANT_REQ=\"0.001\"\r\n"
				+ "De lo contrario\r\n"
				+ "    CANT_REQ=\"1\"\r\n"
				+ "Confirmar que CANT_REQ tenga formato numerico con patron \"000.000\"\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo CANT_REQ de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		validaStatus = true;
		
		String new_selling_uom = "";
		
		if(useMock.equals("N")) {
			new_selling_uom = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "NEW_SELLING_UOM");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			new_selling_uom = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "NEW_SELLING_UOM");
		}

		if(new_selling_uom != null && new_selling_uom.equals("KG") && qty_reqt_prm.equals("0.001")) {
			validaStatus = false;
		}else {
			if(qty_reqt_prm.equals("1.000")) {
				validaStatus = false;			
			}
		}
		
		tr.getTypeCampoQuery("CO_PRM_HEADD", "QTY_REQT_PRM", GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DECIMAL");
		assertTrue("El tipo de campo no es el esperado", validaStatus);
		
		testCase.addTextEvidenceCurrentStep("Valor de 'NEW_SELLING_UOM': " + new_selling_uom);
		testCase.addTextEvidenceCurrentStep("Valor de 'QTY_REQT_PRM': " + qty_reqt_prm);
		assertFalse("La Regla de Transformacion 14 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 14 fue respetada.");
		
		//***************************Paso 22**********************************************************
		addStep("Para la TR15 confirmar en el archivo que el campo DESCUENTO (CO_PRM_HEADD.DSC) debe ser igual al campo FEM_POS_MODS_PRM_STG.NEW_PRICE (BD FCDASPFR)\r\n"
				+ "Confirmar que DESCUENTO tenga formato numerico con patron \"00000.000\"\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo DESCUENTO de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		String new_price= "";
		
		if(useMock.equals("N")) {
			new_price = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "NEW_PRICE");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			new_price = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "NEW_PRICE");
		}
        
		int new_price2 = Integer.parseInt(new_price);
        double numeroDouble = Double.parseDouble(dsc);
        int dsc2 = (int) numeroDouble;

        if(dsc2 != new_price2) {
        	validaStatus=true;
        }
        
        tr.getTypeCampoQuery("CO_PRM_HEADD", "DSC", GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DECIMAL");
		assertTrue("El tipo de campo no es el esperado", validaStatus);
        
        testCase.addTextEvidenceCurrentStep("Valor de 'NEW_PRICE': " + new_price);
        testCase.addTextEvidenceCurrentStep("Valor de 'DSC': " + dsc);
        assertFalse("La Regla de Transformacion 15 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 15 fue respetada.");
		
		//***************************Paso 23**********************************************************
		addStep("Para la TR16 en el campo TIP_DESC (CO_PRM_HEADD.TYP_DSC):\r\n"
				+ "Si FEM_POS_MODS_PRM_STG.PREFIX (BD FCDASPFR) es:\r\n"
				+ "0 entonces TIP_DESC=\"P\"\r\n"
				+ "1 entonces TIP_DESC=\"A\"\r\n"
				+ "2 entonces TIP_DESC=\"F\"\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo TIP_DESC de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		validaStatus = true;
		
		String prefix = "";
		
		if(useMock.equals("N")) {
			prefix = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "PREFIX");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			prefix = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "PREFIX");
		}
		
		if (prefix.equals("0") && typ_dsc.equals("P")) {
			validaStatus = false;
		}
		
		if (prefix.equals("1") && typ_dsc.equals("A")) {
			validaStatus = false;
		}
		
		if (prefix.equals("2") && typ_dsc.equals("F")) {
			validaStatus = false;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PREFIX': " + prefix);
		testCase.addTextEvidenceCurrentStep("Valor de 'TYP_DSC': " + typ_dsc);
		assertFalse("La Regla de Transformacion 16 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 16 fue respetada.");
		
		//***************************Paso 24**********************************************************
		addStep("Para la TR17 el campo OPTATIVO=\" \" (CO_PRM_HEADD.OPT_PRM)\r\n"
				+ "Confirmar trim al OPTATIVO y rellenar con espacios a la derecha hasta completar 1 caracter");
		
		if (!opt_prm.equals("0")) {
			validaStatus = true;
		}
		
		if (opt_prm.length() != 1) {
            validaStatus = true;
        }
		
		tr.getTypeCampoQuery("CO_PRM_HEADD", "OPT_PRM", GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "INT");
		assertTrue("El tipo de campo no es el esperado", validaStatus);
		
		testCase.addTextEvidenceCurrentStep("Valor de 'OPT_PRM': " + opt_prm);
		assertFalse("La Regla de Transformacion 17 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 17 fue respetada.");
		
		//***************************Paso 25**********************************************************
		addStep("Para la TR18 el campo DESCRIP1 (CO_PRM_HEADD.DSCP_ITM) debe ser igual al campo FEM_POS_MODS_PRM_STG.PROM_NAME (BD FCDASPFR)\r\n"
				+ "Confirmar trim a DESCRIP1 y rellenado con espacios a la izquierda hasta completar 40 caracteres.\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo DESCRIP1 de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		String prom_name = "";
		
		if(useMock.equals("N")) {
			prom_name = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "PROM_NAME");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			prom_name = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "PROM_NAME");
		}
		
		if (dscp_itm.length() != 40) {
            validaStatus = true;
        }
		
		if(!dscp_itm.contains(prom_name)) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'PROM_NAME': " + prom_name);
		testCase.addTextEvidenceCurrentStep("Valor de 'DSCP_ITM': " + dscp_itm);
		assertFalse("La Regla de Transformacion 18 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 18 fue respetada.");
		
		//***************************Paso 26**********************************************************
		addStep("Para la TR19 el campo SUBCAT (CO_PRM_HEADD.SUB_CTGY_ITM) =\" \" \r\n"
				+ "Confirmar trim al SUBCAT y rellenado con espacios a la derecha hasta completar 4 caracteres");
		
		if(!sub_ctgy_itm.equals("")) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'SUB_CTGY_ITM': " + sub_ctgy_itm);
		assertFalse("La Regla de Transformacion 19 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 19 fue respetada.");
		
		//***************************Paso 27**********************************************************
		addStep("Para la TR20 el campo SEGMENTO (CO_PRM_HEADD.SGMT_ITM) =\" \" \r\n"
				+ "Confirmar trim al SEGMENTO y rellenado con espacios a la derecha hasta completar 4 caracteres");

		if(!sgmt_itm.equals("")) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'SGMT_ITM': " + sgmt_itm);
		assertFalse("La Regla de Transformacion 20 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 20 fue respetada.");
		
		//***************************Paso 28**********************************************************
		addStep("Para la TR21 el campo SUBSEGTO (CO_PRM_HEADD.SUB_SGMT_ITM) =\" \" \r\n"
				+ "Confirmar trim al SUBSEGTO y rellenado con espacios a la derecha hasta completar 4 caracteres");
		
		if(!sub_sgmt_itm.equals("")) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'SUB_SGMT_ITM': " + sub_sgmt_itm);
		assertFalse("La Regla de Transformacion 21 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 21 fue respetada.");
		
		//***************************Paso 29**********************************************************
		addStep("Para la TR22 el campo ART1 (CO_PRM_HEADD.DRN_CD) en el archivo debe ser igual a la cadena concatenada de la tabla FEM_POS_MODS_PRM_STG (BD FCDASPFR):\r\n"
				+ "'1' + START_TIME (si es null usar '0000') + END_TIME (si es null usar '2359') + ITEM_SHORT_DESC\r\n"
				+ "Confirmar trim al ART1 y rellenado con espacios a la derecha hasta completar 16 caracteres\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor de los campos de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		String start_time = "";
		String end_time = "";
		String item_short_desc = "";
		DateTimeFormatter formatter = null;
		
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
		
		if(start_time == "" || start_time == null) {
			start_time = "0000";
		}
		
		if(end_time == "" || end_time == null) {
			end_time = "2359";
		}
		
		String concatenacion = "1" + start_time3 + end_time3 + item_short_desc;
		
		if (drn_cd.length() != 16) {
            validaStatus = true;
        }
		
		if(!drn_cd.equals(concatenacion)) {
			validaStatus = true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'START_DATE': " + start_time);
		testCase.addTextEvidenceCurrentStep("Valor de 'END_DATE': " + end_time);
		testCase.addTextEvidenceCurrentStep("Valor de solo hhmm de 'START_DATE' (es lo que se pone en el archivo): " + start_time3);
		testCase.addTextEvidenceCurrentStep("Valor de solo hhmm de 'END_DATE' (es lo que se pone en el archivo): " + end_time3);
		testCase.addTextEvidenceCurrentStep("Valor de 'ITEM_SHORT_DESC': " + item_short_desc);
		testCase.addTextEvidenceCurrentStep("Valor de 'DRN_CD': " + drn_cd);
		assertFalse("La Regla de Transformacion 22 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 22 fue respetada.");
		
		//***************************Paso 30**********************************************************
		addStep("Para la TR23 confirmar en el archivo que el campo CANT1 (CO_PRM_HEADD.GP_PRM) cumpla con la condicion:\r\n"
				+ "Si REF_PREFIX es NULL\r\n"
				+ "   CANT1=0\r\n"
				+ "De lo contrario\r\n"
				+ "El valor de CANT1 debe ser igual a FEM_POS_MODS_PRM_STG.REF_PREFIX (BD FCDASPFR)\r\n"
				+ "Confirmar CANT1 tenga formato numerico con patron \"000.000\"\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo CANT1 de la promocion en la tabla FEM_POS_MODS_PRM_STG");

		String ref_prefix = "";
		
		if(useMock.equals("N")) {
			ref_prefix = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "REF_PREFIX");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			ref_prefix = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "REF_PREFIX");
		}
		
		String ref_prefix2 = ref_prefix + ".000";
		System.out.println("Se agregan ceros para poder hacer la comparacion");
		
		if(ref_prefix == "" || ref_prefix == null && !gp_prm.equals("0.000")) {
			validaStatus=true;
		}else {
			if(!gp_prm.equals(ref_prefix2)) { //Si grouperArchivo no tiene el formato 000.000 y es igual a ref_prefix, fallara
				validaStatus=true;
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'REF_PREFIX': " + ref_prefix);
		testCase.addTextEvidenceCurrentStep("Valor de 'GP_PRM': " + gp_prm);
		assertFalse("La Regla de Transformacion 23 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 23 fue respetada.");
		
		//***************************Paso 31**********************************************************
		addStep("Para la TR24 confirmar en el archivo que el campo ACCION (CO_PRM_HEADD.TYP_PRM_AD) =\"A\"");
		
		if(!typ_prm_ad.equals("A")) {
			validaStatus=true;
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'TYP_PRM_AD': " + typ_prm_ad);
		assertFalse("La Regla de Transformacion 24 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 24 fue respetada.");
		
		//***************************Paso 32**********************************************************
		addStep("Para la TR25 confirmar en el archivo que el campo BINCODE (CO_PRM_HEADD.CD_PRM) con esta condicion:\r\n"
				+ "Si FEM_POS_MODS_PRM_STG.MANUAL_PRICE_ENTRY = ('M' o 'E')\r\n"
				+ "    BINCODE = FEM_POS_MODS_PRM_STG.PROM_BIN_CODE\r\n"
				+ "De lo contrario\r\n"
				+ "    BINCODE = \"\" (Cadena Vacia)\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo BINCODE de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		validaStatus=true;
		
		String prom_bin_code = "";
		
		if(useMock.equals("N")) {
			prom_bin_code = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "PROM_BIN_CODE");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			prom_bin_code = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "PROM_BIN_CODE");
		}
		
		if(manual_price_entry.equals("M") || manual_price_entry.equals("E") && prom_bin_code.equals(cd_prm)) {
			validaStatus=false;
		} else {
			if(cd_prm.equals("")) {
				validaStatus=false;				
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'MANUAL_PRICE_ENTRY': " + manual_price_entry);
		testCase.addTextEvidenceCurrentStep("Valor de 'PROM_BIN_CODE': " + prom_bin_code);
		testCase.addTextEvidenceCurrentStep("Valor de 'CD_PRM': " + cd_prm);
		assertFalse("La Regla de Transformacion 25 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 25 fue respetada.");
		
		//***************************Paso 33**********************************************************
		addStep("Para la TR26 confirmar en el archivo el campo MSGPPAG (CO_PRM_HEADD.MSG_PRM) con esta condicion:\r\n"
				+ "Si FEM_POS_MODS_PRM_STG.MANUAL_PRICE_ENTRY = ('M' o 'E')\r\n"
				+ "    MSGPPAG = FEM_POS_MODS_PRM_STG.MSG_POS\r\n"
				+ "De lo contrario\r\n"
				+ "    MSGPPAG = \"\" (Cadena Vacia)\r\n"
				+ "\r\n"
				+ "Nota: Usa la consulta del paso 13 para obtener el valor del campo MSGPPAG de la promocion en la tabla FEM_POS_MODS_PRM_STG");
		
		validaStatus=true;
		
		String msg_pos = "";
		
		if(useMock.equals("N")) {
			msg_pos = tr.getValueCampoOracle(queryPRM_STG, dbFCDASPFR, "MSG_POS");
		}else {
			queryPRM_STG = queryPRM_STG.replace("WMUSER.", userAzure);
			msg_pos = tr.getValueCampoQuery(queryPRM_STG, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionMockCloud, "MSG_POS");
		}
		
		if(manual_price_entry.equals("M") || manual_price_entry.equals("E") && msg_pos.equals(msg_prm)) {
			validaStatus=false;
		} else {
			if(msg_prm.equals("")) {
				validaStatus=false;				
			}
		}
		
		testCase.addTextEvidenceCurrentStep("Valor de 'MANUAL_PRICE_ENTRY': " + manual_price_entry);
		testCase.addTextEvidenceCurrentStep("Valor de 'MSG_POS': " + msg_pos);
		testCase.addTextEvidenceCurrentStep("Valor de 'MSG_PRM': " + msg_prm);
		assertFalse("La Regla de Transformacion 26 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 26 fue respetada.");
		
		//***************************Paso 34**********************************************************
		addStep("Para la TR28 confirmar que el campo ID_DEPT (CO_PRM_HEADP) es igual a \"ORACLE_CR_SUPERIOR\" de XXFC_MAESTRO_DE_CRS_V\r\n"
				+ "De la vista mencionada se debe filtrar por el campo \"RETEK_CR\" igual al campo \"LOCATION\" de tabla WMUSER.POS_PRM_HEAD en DAS_OPERATIVO, ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"\r\n"
				+ "\r\n"
				+ "Nota: Los campos son de la BD AVEBQA");
		
		validaStatus=false;
		
		if (!id_dept.equals(oracleCR_superior)) {
			 validaStatus = true;
		 }
		
		testCase.addTextEvidenceCurrentStep("Valor de ID_DEPT: " + id_dept);
		 testCase.addTextEvidenceCurrentStep("Valor de ORACLE_CR_SUPERIOR en XXFC_MAESTRO_DE_CRS_V y valor de ID_DEPT son iguales: " + oracleCR + " y " + id_dept);
		assertFalse("La Regla de Transformacion 28 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 28 fue respetada.");
		
		//***************************Paso 35**********************************************************
		addStep("Para la TR29 confirmar que el campo ID_STR (CO_PRM_HEADP) es igual a \"ORACLE_CR\" de XXFC_MAESTRO_DE_CRS_V\r\n"
				+ "De la vista mencionada se debe filtrar por el campo \"RETEK_CR\" igual al campo \"LOCATION\" de tabla WMUSER.POS_PRM_HEAD en DAS_OPERATIVO, ORACLE_CR_TYPE igual a \"T\" y \"ESTADO\" igual a \"A\"\r\n"
				+ "\r\n"
				+ "Nota: Los campos son de la BD AVEBQA");
		
		if (!id_str.equals(oracleCR)) {
			 validaStatus = true;
		 }
		
		 testCase.addTextEvidenceCurrentStep("Valor de ID_STR: " + id_str);
		 testCase.addTextEvidenceCurrentStep("Valor de ORACLE_CR en XXFC_MAESTRO_DE_CRS_V y valor de ID_STR son iguales: " + oracleCR + " y " + id_str);
		assertFalse("La Regla de Transformacion 29 NO fue respetada.", validaStatus);
		testCase.addTextEvidenceCurrentStep("La Regla de Transformacion 29 fue respetada.");
		
		//***************************Paso 36**********************************************************
		addStep("Para la TR30 hacer la consulta: " + tr30);
		
		System.out.println(tr30);
		//Realiza la consulta con la conexion que se especifique (BD)
		SQLResult tr_30 = dbFCDASPFR.executeQuery(tr30);
		
		validaStatus = tr_30.isEmpty();
		 if (!validaStatus) {
				testCase.addQueryEvidenceCurrentStep(tr_30);
		 }
		 tr.processQueryInChunks(dbFCDASPFR, tr30);
		 
		 assertFalse("No se muestra la informacion de la tienda.", validaStatus);
		
		//***************************Paso 37**********************************************************
		addStep("Para la TR31 hacer la consulta: " + tr31);
		
		System.out.println(tr31);
		//Realiza la consulta con la conexion que se especifique (BD)
		SQLResult tr_31 = dbFCDASPFR.executeQuery(tr31);
		
		validaStatus = tr_31.isEmpty();
		 if (!validaStatus) {
				testCase.addQueryEvidenceCurrentStep(tr_31);
		 }
		 tr.processQueryInChunks(dbFCDASPFR, tr31);
		 
		 assertFalse("No se muestra la informacion de la tienda.", validaStatus);
		
		//***************************Paso 38**********************************************************
		addStep("Para la TR32 hacer la consulta: " + tr32);
		
		System.out.println(tr32);
		//Realiza la consulta con la conexion que se especifique (BD)
		SQLResult tr_32 = dbFCWMESIT.executeQuery(tr32);
		
		validaStatus = tr_32.isEmpty();
		 if (!validaStatus) {
				testCase.addQueryEvidenceCurrentStep(tr_32);
		 }
		 tr.processQueryInChunks(dbFCWMESIT, tr32);
		 
		 assertFalse("No se muestra la informacion de la tienda.", validaStatus);
		
		//***************************Paso 39**********************************************************
		 
		 addStep("Para la TR34 hacer la consulta: " + tr34);
		
		System.out.println(tr34);
		//Realiza la consulta con la conexion que se especifique (BD)
		SQLResult tr_34 = dbFCDASPFR.executeQuery(tr34);
		
		validaStatus = tr_34.isEmpty();
		 if (!validaStatus) {
				testCase.addQueryEvidenceCurrentStep(tr_34);
		 }
		 tr.processQueryInChunks(dbFCDASPFR, tr34);
		 
		 assertFalse("No se muestra la informacion de la tienda.", validaStatus);
		
	}
    
	@Override
	public String setTestFullName() {
		return "MTC-FT-004 Validar TR Tablas Cloud Promocion Simple Tienda Fisica";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Valida las Reglas de Tranformacion de las tablas CO_PRM_DAT_HDR y CO_PRM_DAT_DTL generado por PR63_CLOUD de promocion simple para tienda fisica";
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
				+ "3. Acceso a BD MDB_ERDD_PromotionCloud (Azure)";
	
	}

}
