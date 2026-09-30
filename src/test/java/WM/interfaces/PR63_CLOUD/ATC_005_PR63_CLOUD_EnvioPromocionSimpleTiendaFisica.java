package WM.interfaces.PR63_CLOUD;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.util.HashMap;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.sikuli.script.Key;
import org.testng.annotations.Test;

import com.azure.core.credential.TokenCredential;
import com.azure.monitor.query.models.QueryTimeInterval;

import WM.CargaPromociones.om.CargaDeArchivos;
import WM.CargaPromociones.om.pomCargaDePromociones;
import WM.interfaces.ENRED_OUT_V2.ATC_011_ENREDOUTV2_ValidarACKTiendaFisicaCloud;
import WM.interfaces.MARK_PRM.ATC_007_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenC;
import WM.om.PR63_CLOUD;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

@AzureTestInfo(
		areaPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise",
		iterationPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise\\Sprint 3",
		tags = "",
		userStoryId = 71351
)

public class ATC_005_PR63_CLOUD_EnvioPromocionSimpleTiendaFisica extends BaseExecution {
	@Test
	(dataProvider = "data-provider")

	public void ATC_005_PR63_CLOUD_EnvioPromocionSimpleTiendaFisica_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-005 Envio de promocion simple a tienda fisica cloud-manual
		Desc:
		Prueba de flujo E2E del flujo de promociones con su envio y repecion de documentos,
		se desarrollaron los siguientes componentes: job MARK_PRM, job PR63_CLOUD y API ENRED_OUT_V2
		Dependiendo de la tienda (digital cloud, fisica cloud y onpremise) el archivo se va a FTP (Filezilla) o Azure Storage.
		Este flujo prueba los componentes job MARK_PRM, job PR63_MX y API ENRED_OUT_V2 y el envio de una promocion en RMS16,
		a modo de regresion de promociones a tiendas onpremises.
		@Valeria Fernandez
		@date   2024/12/26*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		PR63_CLOUD tr = new PR63_CLOUD(testCase);
		
		utils.sql.SQLUtil dbFCDASPFR = tr.reintentarConexion();
		utils.sql.SQLUtil dbFCWM6QA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWM6QA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		utils.sql.SQLUtil dbRMSv16 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCMOMPFR_RMS,GlobalVariables.DB_USER_FCMOMPFR_RMS, GlobalVariables.DB_PASSWORD_FCMOMPFR_RMS);
		
		//dbRMSv16 y dbFCDASPFR
        String queryRMS16Store1 = "SELECT * FROM RMS.STORE WHERE STORE='" +data.get("retek_cr")+ "'";
        String queryRMS16Store2 = "SELECT * FROM RMS.STORE WHERE STORE='" +data.get("retek_cr2")+ "'";
        String queryRMS16SKU1 = "SELECT * FROM RMS.ITEM_LOC IL WHERE STATUS IN ('A')\r\n"
        		+ "AND SOURCE_METHOD = 'S'\r\n"
        		+ "AND IL.ITEM IN (SELECT ITEM FROM RMS.ITEM_MASTER WHERE STATUS = 'A' AND ORDERABLE_IND = 'Y') \r\n"
        		+ "AND IL.PRIMARY_SUPP IN (SELECT SUPPLIER FROM RMS.SUPS WHERE SUP_STATUS = 'A')\r\n"
        		+ "AND IL.PRIMARY_SUPP IN (SELECT SUPPLIER FROM RMS.ITEM_SUPPLIER WHERE ITEM = IL.ITEM AND SUPPLIER = IL.PRIMARY_SUPP)\r\n"
        		+ "AND IL.ITEM IN (SELECT ITEM FROM RMS.RPM_ITEM_LOC WHERE ITEM = IL.ITEM AND LOC = IL.LOC)\r\n"
        		+ "AND IL.LOC IN (SELECT STORE FROM RMS.STORE WHERE STORE = IL.LOC AND STORE_NAME LIKE '%VER%' AND STORE_CLOSE_DATE is NULL AND TRANSFER_ZONE NOT IN '9999')\r\n"
        		+ "AND IL.ITEM='" +data.get("sku")+ "' AND LOC='" +data.get("retek_cr")+ "'";
        String queryRMS16SKU2 = "SELECT * FROM RMS.ITEM_LOC IL WHERE STATUS IN ('A')\r\n"
        		+ "AND SOURCE_METHOD = 'S'\r\n"
        		+ "AND IL.ITEM IN (SELECT ITEM FROM RMS.ITEM_MASTER WHERE STATUS = 'A' AND ORDERABLE_IND = 'Y') \r\n"
        		+ "AND IL.PRIMARY_SUPP IN (SELECT SUPPLIER FROM RMS.SUPS WHERE SUP_STATUS = 'A')\r\n"
        		+ "AND IL.PRIMARY_SUPP IN (SELECT SUPPLIER FROM RMS.ITEM_SUPPLIER WHERE ITEM = IL.ITEM AND SUPPLIER = IL.PRIMARY_SUPP)\r\n"
        		+ "AND IL.ITEM IN (SELECT ITEM FROM RMS.RPM_ITEM_LOC WHERE ITEM = IL.ITEM AND LOC = IL.LOC)\r\n"
        		+ "AND IL.LOC IN (SELECT STORE FROM RMS.STORE WHERE STORE = IL.LOC AND STORE_NAME LIKE '%Tlc%' AND STORE_CLOSE_DATE is NULL AND TRANSFER_ZONE NOT IN '9999')\r\n"
        		+ "AND IL.ITEM='" +data.get("sku")+ "' AND LOC='" +data.get("retek_cr2")+ "'";
        String queryRMS16Item1 = "SELECT * FROM RMS.RPM_ITEM_LOC WHERE ITEM='" +data.get("sku")+ "' AND LOC='" +data.get("retek_cr")+ "'";
        String queryRMS16Item2 = "SELECT * FROM RMS.RPM_ITEM_LOC WHERE ITEM='" +data.get("sku")+ "' AND LOC='" +data.get("retek_cr2")+ "'";
        String queryRPM_Promo1 ="SELECT * FROM RMS.RPM_PROMO WHERE NAME LIKE '" +data.get("nombrePromo1")+ "'";
  		String queryRPM_Promo_Comp1 = "SELECT * FROM RMS.RPM_PROMO_COMP WHERE PROMO_ID='1'";
        String queryRPM_Promo_Dtl1 ="SELECT * FROM RMS.RPM_PROMO_DTL WHERE ATTRIBUTE_1 LIKE '" +data.get("nombrePromo1")+ "'";
        String queryRPM_Promo_Dtl_List_Grp1 = "SELECT * FROM RMS.RPM_PROMO_DTL_LIST_GRP WHERE PROMO_DTL_ID='1'";
  		String queryRPM_Promo_Dtl_Merch_Node1 = "SELECT * FROM RMS.RPM_PROMO_DTL_MERCH_NODE WHERE PROMO_DTL_ID='1'";
  		String queryRPM_Promo_Dtl_Disc_Ladder1 = "SELECT * FROM RMS.RPM_PROMO_DTL_DISC_LADDER WHERE PROMO_DTL_LIST_ID='1'";
  		String queryRPM_Promo_Dtl_List1 = "SELECT * FROM RMS.RPM_PROMO_DTL_LIST WHERE PROMO_DTL_LIST_ID='1'";
  		String queryRPM_Promo_Dtl_Zone_Location1 = "SELECT * FROM RMS.RPM_PROMO_ZONE_LOCATION WHERE PROMO_DTL_ID='1'";
        String queryRPM_Promo2 ="SELECT * FROM RMS.RPM_PROMO WHERE NAME LIKE '" +data.get("nombrePromo2")+ "'";
		String queryRPM_Promo_Comp2 = "SELECT * FROM RMS.RPM_PROMO_COMP WHERE PROMO_ID='1'";
        String queryRPM_Promo_Dtl2 ="SELECT * FROM RMS.RPM_PROMO_DTL WHERE ATTRIBUTE_1 LIKE '" +data.get("nombrePromo2")+ "'";
        String queryRPM_Promo_Dtl_List_Grp2 = "SELECT * FROM RMS.RPM_PROMO_DTL_LIST_GRP WHERE PROMO_DTL_ID='1'";
		String queryRPM_Promo_Dtl_Merch_Node2 = "SELECT * FROM RMS.RPM_PROMO_DTL_MERCH_NODE WHERE PROMO_DTL_ID='1'";
		String queryRPM_Promo_Dtl_Disc_Ladder2 = "SELECT * FROM RMS.RPM_PROMO_DTL_DISC_LADDER WHERE PROMO_DTL_LIST_ID='1'";
		String queryRPM_Promo_Dtl_List2 = "SELECT * FROM RMS.RPM_PROMO_DTL_LIST WHERE PROMO_DTL_LIST_ID='1'";
		String queryRPM_Promo_Dtl_Zone_Location2 = "SELECT * FROM RMS.RPM_PROMO_ZONE_LOCATION WHERE PROMO_DTL_ID='1'";
        //Nota: El valor de nombrePromoN debe ser el mismo que el nombre de la promo en el archivo ods, este archivo esta en la ruta "file"
		String queryDASPromo_Head1 = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE LOCATION = '" +data.get("retek_cr")+ "' AND WM_PRM_STATUS = '" +data.get("wm_prm_status_antes")+ "' AND CLOUD_PRM_STATUS " +data.get("cloud_prm_status_antes")+ " ORDER BY LOAD_DATE DESC";
		String queryDASPromo_Head2 = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE LOCATION = '" +data.get("retek_cr2")+ "' AND WM_PRM_STATUS = '" +data.get("wm_prm_status_antes")+ "' AND CLOUD_PRM_STATUS " +data.get("cloud_prm_status_antes")+ " ORDER BY LOAD_DATE DESC";
		String queryStatusPRMHeadPr63Cloud2 = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status_despues") + "' AND CLOUD_PRM_STATUS='" + data.get("cloud_prm_status_pr63") + "' AND LOCATION='" + data.get("retek_cr") + "' AND BATCH_ID='batchIdValor'";
		
        //dbFCWMESIT
		String querySimotiOutDoc1 = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='PRM' AND STATUS='" + data.get("statusAckRespuesta") + "' AND ID='" + data.get("id") + "'";
		String querySimotiOutDoc2 = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId2")+ "' AND PV_CR_TIENDA = '" +data.get("storeId2")+ "' AND DOC_TYPE='PRM' AND STATUS='" + data.get("statusAckRespuesta") + "' AND ID='" + data.get("id2") + "'";
        
		//MDB_ERDD_PromotionCloud
		String queryStatusDigitalN = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("plaza")+ "' AND STORE='" +data.get("tienda")+ "' AND DIGITAL='" +data.get("digital")+ "'";
		String queryStatus2DigitalN = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("departmentId2")+ "' AND STORE='" +data.get("storeId2")+ "' AND DIGITAL='" +data.get("digital")+ "'";
		String queryGetBatchId = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("retek_cr")+ "' AND PROCESSING_STATUS='" +data.get("status")+ "' AND PV_STATUS IS NULL AND PV_DOC_TYPE='" +data.get("doctype")+ "' ORDER BY CREATED_DATE DESC";
		String queryGetBatchId2 = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("retek_cr2")+ "' AND PROCESSING_STATUS='" +data.get("status")+ "' AND PV_STATUS IS NULL AND PV_DOC_TYPE='" +data.get("doctype")+ "' ORDER BY CREATED_DATE DESC";
		String queryGetIdPrmHdrOx = "SELECT * FROM CO_PRM_HEADP WHERE ID_DEPT='" +data.get("departmentId")+ "' AND ID_STR='" +data.get("storeId")+ "' ORDER BY DT_SCH DESC";
		String queryGetIdPrmHdrOx2 = "SELECT * FROM CO_PRM_HEADP WHERE ID_DEPT='" +data.get("departmentId2")+ "' AND ID_STR='" +data.get("storeId2")+ "' ORDER BY DT_SCH DESC";
		String queryGetFolio = "SELECT * FROM CO_PRM_HEADD WHERE ID_PRM_HDR_OX = '1'";
		String queryGetFolio2 = "SELECT * FROM CO_PRM_HEADD WHERE ID_PRM_HDR_OX = '1'";
		
        //Control M
  		By btnViewpoints = By.xpath("//span[text()='Viewpoints']");
  		
  		SikuliUtil sk = new SikuliUtil();
		
		String url = data.get("url");
		String userRms = data.get("userRms");
		String password = data.get("password");
		String file = data.get("file");
		String promotion = data.get("promotion");
		String changePromotion  = data.get("changePromotion");
		String submitted = data.get("submitted");
		String approved = data.get("approved");
		String submittedOriginal = data.get("submittedOriginal");
		String approvedOriginal = data.get("approvedOriginal");
		String submittedMod = data.get("submittedMod");
		String approvedMod = data.get("approvedMod");
		
		String API_StartCopy = data.get("API_StartCopy");
        String Path_StartCopy = data.get("Path_StartCopy");
        String fullUrl = API_StartCopy + Path_StartCopy;
        String MockAPI = data.get("MockAPI");
        int timeout_PR63_CLOUD = Integer.parseInt(data.get("timeout_PR63_CLOUD"));
        
		boolean validacion;
		boolean resultado2;
		boolean validaStatus = true;

		//***************************Paso 1*****************************************************************************
		addStep("Accesar BD RMSv16");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCMOMPFR_RMS);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCMOMPFR_RMS);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Verificar que la tienda existe en la tabla STORE con la consulta: " + queryRMS16Store1);
		 
		 validaStatus = tr.queryOracle(queryRMS16Store1, dbRMSv16);
		 tr.processQueryInChunks(dbRMSv16, queryRMS16Store1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 3*****************************************************************************
		 addStep("Verificar que la tienda existe en la tabla STORE con la consulta: " + queryRMS16Store2);
		 
		 validaStatus = tr.queryOracle(queryRMS16Store2, dbRMSv16);
		 tr.processQueryInChunks(dbRMSv16, queryRMS16Store2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 4*****************************************************************************
		addStep("Verificar que el SKU 7502271159072 existe en la tienda 50YDY plaza 10VCZ (LOC=11524) con la consulta: " + queryRMS16SKU1);
		
		validaStatus = tr.queryOracle(queryRMS16SKU1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRMS16SKU1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 5*****************************************************************************
		addStep("Verificar que el SKU 7502271159072 existe en la tienda 50YDY plaza 10VCZ (LOC=11524) con la consulta: " + queryRMS16Item1);
		
		validaStatus = tr.queryOracle(queryRMS16Item1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRMS16Item1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 6*****************************************************************************
		addStep("Verificar que el SKU 7502271159072 existe en la tienda 50B87 plaza 10TLC (LOC=41341) con la consulta: " + queryRMS16SKU2);
		
		validaStatus = tr.queryOracle(queryRMS16SKU2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRMS16SKU2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 7*****************************************************************************
		addStep("Verificar que el SKU 7502271159072 existe en la tienda 50B87 plaza 10TLC (LOC=41341) con la consulta: " + queryRMS16Item2);
		
		validaStatus = tr.queryOracle(queryRMS16Item2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRMS16Item2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 8*****************************************************************************
		addStep("Ingresar a la BD FCDASPFR");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO);
		 
		//***************************Paso 9*****************************************************************************
		addStep("Verificar que el SKU 7502271159072 existe en la tienda 50YDY plaza 10VCZ (LOC=11524) con la consulta: " + queryRMS16SKU1);
		
		validaStatus = tr.queryOracle(queryRMS16SKU1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRMS16SKU1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);

		//***************************Paso 10*****************************************************************************
		addStep("Verificar que el SKU 7502271159072 existe en la tienda 50YDY plaza 10VCZ (LOC=11524) con la consulta: " + queryRMS16Item1);
		
		validaStatus = tr.queryOracle(queryRMS16Item1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRMS16Item1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 11*****************************************************************************
		addStep("Verificar que el SKU 7502271159072 existe en la tienda 50B87 plaza 10TLC (LOC=41341) con la consulta: " + queryRMS16SKU2);
		
		validaStatus = tr.queryOracle(queryRMS16SKU2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRMS16SKU2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 12*****************************************************************************
		addStep("Verificar que el SKU 7502271159072 existe en la tienda 50B87 plaza 10TLC (LOC=41341) con la consulta: " + queryRMS16Item2);
		
		validaStatus = tr.queryOracle(queryRMS16Item2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRMS16Item2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);

		//***************************Paso 13*****************************************************************************
		 addStep("Verificar que la tienda existe en la tabla STORE con la consulta: " + queryRMS16Store2);

		 validaStatus = tr.queryOracle(queryRMS16Store2, dbRMSv16);
		 tr.processQueryInChunks(dbFCDASPFR, queryRMS16Store2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 14*****************************************************************************
		 addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
	
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
			
		//***************************Paso 15*****************************************************************************
		 addStep("Validar que la tienda del documento a enviar sea fisica, con la consulta: " + queryStatusDigitalN);

		 validaStatus = tr.queryBDAzure(queryStatusDigitalN, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 tr.getValueCampoQuery(queryStatusDigitalN, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DIGITAL");
			
		//***************************Paso 16*****************************************************************************
		addStep("Validar que la tienda del documento a enviar sea fisica, con la consulta: " + queryStatus2DigitalN);

		validaStatus = tr.queryBDAzure(queryStatus2DigitalN, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		tr.getValueCampoQuery(queryStatus2DigitalN, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DIGITAL");
			
		//***************************Paso 17*****************************************************************************
		addStep("Ingresar a RMS16 " + url);
			
	    SeleniumUtil u;
	    u = new SeleniumUtil(new ChromeTest(), true);
	    pomCargaDePromociones rms = new pomCargaDePromociones(u, testCase);
			
	    u.get(url); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		testCase.addScreenShotCurrentStep(u, "RMS16");
		rms.logginRMS16(userRms, password);
		
		//***************************Paso 18*****************************************************************************
		addStep("Llenar archivo .ods template de promocion simple descargado en precondiciones con 2 registros con los datos como estan en el ejemplo de la pestania Promo Simple de esta matriz." + file);
		System.out.println(file);
		testCase.addTextEvidenceCurrentStep("");
		
		
		//***************************Paso 19*****************************************************************************
		addStep("Ir a Tasks -> Buscar: Promotions -> Simple Promotion -> Browse");
		rms.BtnTasks();
		rms.clicRPMTask();
		testCase.addScreenShotCurrentStep(u, "Carga Promociones");
		
		//***************************Paso 20*****************************************************************************
		addStep("Seleccionar archivo de promocion creado en el paso 18");
		rms.clicFileUpload();
		rms.clicPromotion();
		CargaDeArchivos ca = new CargaDeArchivos(u, testCase, "RMS16");
		ca.selectPromotion(promotion);
		testCase.addScreenShotCurrentStep(u, "Archivo seleccionado");
		
		//***************************Paso 21*****************************************************************************
		addStep("Cargar promocion dando click a boton \"Upload\"");

		validacion = ca.cargarArchivoRms2(file);
		assertTrue(validacion, "No se pudo cargar correctamente el archivo");
		rms.clicBtnUpload();
		testCase.addScreenShotCurrentStep(u, "Archivo cargado");
		
		//***************************Paso 22*****************************************************************************
		addStep("Dar click en Review status");
		u.hardWait(3);
		String status = rms.clicReviewStatus(file);
		testCase.addScreenShotCurrentStep(u, "Review status");
		
		//***************************Paso 23*****************************************************************************
		addStep("Verificar que la promocion esta con Status \"Processing\"");
		
		if (status.equals("Processing") || status.equals("Procesando")) {
			validacion = true;
			testCase.addScreenShotCurrentStep(u, "Status: ");
		} else {
			validacion = false;
		}
		
		assertTrue(validacion, "No se muestra el status procesado correctamente. Status que muestra: " + status);
		
		//***************************Paso 24*****************************************************************************
		addStep("Verificar que la promocion de la tienda 50YDY plaza 10VCZ NO se ha replicado en RPM con las consultas: ");
		
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo1, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl1, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_List_Grp1, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Comp1, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_Merch_Node1, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_Disc_Ladder1, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_List1, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_Zone_Location1, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 25*****************************************************************************
		addStep("Verificar que la promocion de la tienda 50B87 plaza 10TLC NO se ha replicado en RPM con las consultas: ");
			
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo2, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl2, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_List_Grp2, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Comp2, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_Merch_Node2, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_Disc_Ladder2, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_List2, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracleEmpty(queryRPM_Promo_Dtl_Zone_Location2, dbRMSv16);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 26*****************************************************************************
		addStep("Solicitar ordenamiento del listado de jobs que esta en la pestania Cadena Jobs de esta matriz.");
		testCase.addBoldTextEvidenceCurrentStep("Confirmar ordenamiento de jobs antes de la ejecucion, no manda correos el Framework");
		
		//***************************Paso 27*****************************************************************************
		addStep("Ingresar a control M");
		
		//Cadena jobs
		JSONObject obj1 = new JSONObject(data.get("injectorPriceEventBatch_SP_N_16"));
		JSONObject obj2 = new JSONObject(data.get("nb_injector_custom_logic_SP_WS_16"));
		JSONObject obj3 = new JSONObject(data.get("injectorPriceEventBatch_SP_W_16"));
		JSONObject obj4 = new JSONObject(data.get("nb_rpm_payload_publish_custom"));
		JSONObject obj10 = new JSONObject(data.get("XXFC_PROMOTION_COPY_16"));
		JSONObject obj11 = new JSONObject(data.get("XXFC_PROMOTION_SEND_16"));
		JSONObject obj12 = new JSONObject(data.get("xxfc_pre_prmext_mom_16"));
		JSONObject obj13 = new JSONObject(data.get("xxfc_pmodstemp_prm_mom_16"));
		JSONObject obj14 = new JSONObject(data.get("xxfc_Prmext_mom_16"));
		JSONObject obj15 = new JSONObject(data.get("xxfc_procesa_promdnld_prm_mom_16"));
		JSONObject obj16 = new JSONObject(data.get("runPR63_CLOUD"));
		JSONObject obj17 = new JSONObject(data.get("xxfc_pre_prmupd_mom_16"));

		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));		
		testCase.addBoldTextEvidenceCurrentStep("Login");
		u.get(data.get("server"));
		u.hardWait(10);
		u.waitForLoadPage();
		CM.logOn();
		
		//***************************Paso 28*****************************************************************************
		addStep("Ejecutar el job injectorPriceEventBatch_SP_N_16");
		
		//Buscar el job
		ControlM control = new ControlM(u, testCase, obj1);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		u.hardWait(10);
		boolean flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");
		
		//Ejecucion
		String resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);
		
		//Obtener status 
		String statusEjecucion = control.getNewStatus();
		System.out.println ("Valor de output getNewStatus:" +statusEjecucion);
		
		//Obtener output
		String output = control.getOutput();
		System.out.println ("Valor de output control:" +output);
		
		testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
		
		//Validacion de la ejecucion del job
		Boolean casoPasado = false;
		
		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
			casoPasado = true;
		}
		assertTrue(casoPasado, "No termino correctamente");
		
		//***************************Paso 29*****************************************************************************
		addStep("Verificar que la promocion de la tienda 50YDY plaza 10VCZ se replico en RPM con las consultas: ");
		
		validaStatus = tr.queryOracle(queryRPM_Promo1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_List_Grp1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_List_Grp1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Comp1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Comp1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Merch_Node1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_Merch_Node1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Disc_Ladder1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_Disc_Ladder1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_List1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_List1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Zone_Location1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_Zone_Location1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		String promoDtlState = tr.getValueCampoOracle(queryRPM_Promo_Dtl1, dbRMSv16, "STATE");
		if(promoDtlState.equals("0")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("STATE: " + promoDtlState);
		 }
		 assertFalse("No se muestra el estatus esperado.", validaStatus);

		//***************************Paso 30*****************************************************************************
		addStep("Verificar que la promocion de la tienda 50B87 plaza 10TLC se replico en RPM con las consultas: ");
			
		validaStatus = tr.queryOracle(queryRPM_Promo2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_List_Grp2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_List_Grp2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Comp2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Comp2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Merch_Node2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_Merch_Node2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Disc_Ladder2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_Disc_Ladder2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_List2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_List2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Zone_Location2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo_Dtl_Zone_Location2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 31*****************************************************************************
		addStep("Revisar que la promocion en RMS16 (Review status) esta en estatus Successful");
				
		u.get(url); // Abre RMS16 de nuevo
		u.hardWait(3); // tiempo en segundos
		testCase.addTextEvidenceCurrentStep("");
		testCase.addScreenShotCurrentStep(u, "RMS16");
		rms.logginRMS16(userRms, password);
		rms.BtnTasks();
		rms.clicRPMTask();
		rms.clicFileUpload();
		rms.clicPromotion();
		status = rms.clicReviewStatus(file);
		if (status.equals("Processed Successfully") || status.equals("Procesado Exitosamente")) {
			validacion = true;
					
			testCase.addScreenShotCurrentStep(u, "Status: ");
		} else {
			validacion = false;
		}
				
		assertTrue(validacion, "No se muestra el status procesado correctamente. Status que muestra: " + status);
		
		//***************************Paso 32*****************************************************************************
		addStep("Confirmar que la promocion de la tienda 50YDY plaza 10VCZ se replico en DAS con las consultas: ");
		
		validaStatus = tr.queryOracle(queryRPM_Promo1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_List_Grp1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_List_Grp1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Comp1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Comp1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Merch_Node1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_Merch_Node1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Disc_Ladder1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_Disc_Ladder1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_List1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_List1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Zone_Location1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_Zone_Location1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 
		//***************************Paso 33*****************************************************************************
		addStep("Confirmar que la promocion de la tienda 50B87 plaza 10TLC se replico en DAS con las consultas: ");
			
		validaStatus = tr.queryOracle(queryRPM_Promo2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_List_Grp2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_List_Grp2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Comp2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Comp2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Merch_Node2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_Merch_Node2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Disc_Ladder2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_Disc_Ladder2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_List2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_List2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryRPM_Promo_Dtl_Zone_Location2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryRPM_Promo_Dtl_Zone_Location2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 
		//***************************Paso 34*****************************************************************************
		addStep("Llenar archivo .ods template cambio estatus promocion descargado en precondiciones, en el campo Promotion Id del archivo va el dato que guardaste de PROMO_DISPLAY_ID, y en Status selecciona el valor Submitted\r\n"
				+ "Revisa el ejemplo de la pestania Cambiar Promo Submitted de esta matriz");
				
		String promoDisplayId1 = tr.getValueCampoOracle(queryRPM_Promo1, dbRMSv16, "PROMO_DISPLAY_ID");
		String promoDisplayId2 = tr.getValueCampoOracle(queryRPM_Promo2, dbRMSv16, "PROMO_DISPLAY_ID");
				
		String rutaArchivoOriginal = GlobalVariables.User_Dir + submittedOriginal;
		String rutaArchivoSaliente= GlobalVariables.User_Dir + submittedMod;
				
		tr.agregarPromocionODS(rutaArchivoOriginal, rutaArchivoSaliente, promoDisplayId1, promoDisplayId2);
		
		//***************************Paso 35*****************************************************************************
		addStep("Ir a Tasks -> Buscar: Filter Promotions -> Browse");
		rms.cerrarReviewIssues();
		rms.BtnTasks();
		rms.clicFileUpload();
		rms.clicMassivePromotions();
		rms.clicFilterPromotions();
		testCase.addScreenShotCurrentStep(u, "Carga cambio status");
		
		//***************************Paso 36*****************************************************************************
		addStep("Seleccionar archivo de promocion creado en el paso 34");
		ca = new CargaDeArchivos(u, testCase, "RMS16");
		ca.selectMassiveChangePromotion(changePromotion);
		testCase.addScreenShotCurrentStep(u, "Archivo seleccionado");
		
		//***************************Paso 37*****************************************************************************
		addStep("Cargar promocion dando click a boton \"Upload\"");

		validacion = ca.cargarArchivoRms2(submitted);
		assertTrue(validacion, "No se pudo cargar correctamente el archivo");
		testCase.addScreenShotCurrentStep(u, "Archivo cargado");
		
		//***************************Paso 38*****************************************************************************
		addStep("Dar click en Review status");
		rms.clicBtnUpload2();
		status = rms.clicReviewStatus2(submitted);
		testCase.addScreenShotCurrentStep(u, "Review status");
		
		//***************************Paso 39*****************************************************************************
		addStep("Verificar que la promocion esta con Status \"Processing\"");
		
		if (status.equals("Processing") || status.equals("Procesando")) {
			validacion = true;
			testCase.addScreenShotCurrentStep(u, "Status");
		} else {
			validacion = false;
		}
		
		assertTrue(validacion, "No se muestra el status procesado correctamente. Status que muestra: " + status);
		
		//***************************Paso 40*****************************************************************************
		addStep("Ejecutar el job nb_injector_custom_logic_SP_WS_16");
		
		//Buscar el job
		control = new ControlM(u, testCase, obj2);
		u.get(data.get("server"));
		u.waitForLoadPage();
		u.hardWait(15);
		sk.screen.type(Key.ENTER);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");
		
		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);
		
		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println ("Valor de output getNewStatus:" +statusEjecucion);
		
		//Obtener output
		output = control.getOutput();
		System.out.println ("Valor de output control:" +output);
		
		testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
		
		//Validacion de la ejecucion del job
		casoPasado = false;
		
		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		casoPasado = true;
		}		
		
		assertTrue(casoPasado, "No termino correctamente");
		
		//***************************Paso 41*****************************************************************************
		addStep("Confirmar que la promocion de la tienda 50YDY plaza 10VCZ se replico en RPM con la consulta: ");
		
		promoDtlState = tr.getValueCampoOracle(queryRPM_Promo_Dtl1, dbRMSv16, "STATE");
		 if(promoDtlState.equals("2")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("STATE: " + promoDtlState);
		 }
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
		 
		//***************************Paso 42*****************************************************************************
		addStep("Confirmar que la promocion de la tienda 50B87 plaza 10TLC se replico en RPM con la consulta: ");
			
		promoDtlState = tr.getValueCampoOracle(queryRPM_Promo_Dtl2, dbRMSv16, "STATE");
		 if(promoDtlState.equals("2")) {
				validaStatus = false;
				testCase.addTextEvidenceCurrentStep("STATE: " + promoDtlState);
			}
		assertFalse("No se muestra el estatus esperado.", validaStatus);
			 
		//***************************Paso 43*****************************************************************************
		addStep("Modificar el archivo .ods template cambio estatus promocion llenado en el paso 30, en el campo Status selecciona el valor \"Approved\"\r\n"
		 		+ "Revisa el ejemplo de la pestania Cambiar Promo Approved de esta matriz");
		 
		rutaArchivoOriginal = GlobalVariables.User_Dir + approvedOriginal;
		rutaArchivoSaliente= GlobalVariables.User_Dir + approvedMod;
		 
		tr.CambioApproved(rutaArchivoOriginal, rutaArchivoSaliente, promoDisplayId1, promoDisplayId2);
			 
	    //***************************Paso 44*****************************************************************************
		addStep("Ir a Tasks -> Buscar: Filter Promotions -> Browse, En RMS16");
		
		u.get(url); // Abre RMS16 de nuevo
		u.hardWait(3); // tiempo en segundos
		testCase.addScreenShotCurrentStep(u, "RMS16");
		rms.logginRMS16(userRms, password);
		u.hardWait(3);
		rms.BtnTasks();
		rms.clicRPMTask();
		
		//***************************Paso 45*****************************************************************************
		addStep("Seleccionar archivo de promocion creado en el paso 43");
		
		rms.clicMassivePromotions();
		rms.clicFilterPromotions();
		ca = new CargaDeArchivos(u, testCase, "RMS16");
		ca.selectMassiveChangePromotion(changePromotion);
		validacion = ca.cargarArchivoRms2(approved);
		assertTrue(validacion, "No se pudo cargar correctamente el archivo");
		testCase.addScreenShotCurrentStep(u, "Archivo seleccionado");
		
		//***************************Paso 46*****************************************************************************
		addStep("Cargar promocion dando click a boton \"Upload\"");

		rms.clicBtnUpload3();
		testCase.addScreenShotCurrentStep(u, "Archivo cargado");
		
		//***************************Paso 47*****************************************************************************
		addStep("Dar click en Review status");

		status = rms.clicReviewStatus(approved);
		testCase.addScreenShotCurrentStep(u, "Review status");
		
		//***************************Paso 48*****************************************************************************
		addStep("Verificar que la promocion esta con Status \"Processing\"");
		
		if (status.equals("Processing") || status.equals("Procesando")) {
			validacion = true;
			testCase.addScreenShotCurrentStep(u, "Status: ");
		} else {
			validacion = false;
		}
		
		assertTrue(validacion, "No se muestra el status procesado correctamente. Status que muestra: " + status);
		
		//***************************Paso 49*****************************************************************************
		addStep("Ejecutar el job injectorPriceEventBatch_SP_W_16");
		
		//Buscar el job
		control = new ControlM(u, testCase, obj3);
		u.get(data.get("server"));
		u.waitForLoadPage();
		u.hardWait(15);
		sk.screen.type(Key.ENTER);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");
		
		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);
		
		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println ("Valor de output getNewStatus:" +statusEjecucion);
		
		//Obtener output
		output = control.getOutput();
		System.out.println ("Valor de output control:" +output);
		
		testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
		
		//Validacion de la ejecucion del job
		casoPasado = false;
		
		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		casoPasado = true;
		}		
		
		assertTrue(casoPasado, "No termino correctamente");
		
		//***************************Paso 50*****************************************************************************
		addStep("Confirmar que la promocion de la tienda 50YDY plaza 10VCZ se replico en RPM con la consulta: ");
		
		promoDtlState = tr.getValueCampoOracle(queryRPM_Promo_Dtl1, dbRMSv16, "STATE");
		 if(promoDtlState.equals("3")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("STATE: " + promoDtlState);
		 }
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
		 
		//***************************Paso 51*****************************************************************************
		addStep("Confirmar que la promocion de la tienda 50B87 plaza 10TLC se replico en RPM con la consulta: ");
			
		promoDtlState = tr.getValueCampoOracle(queryRPM_Promo_Dtl2, dbRMSv16, "STATE");
		 if(promoDtlState.equals("3")) {
				validaStatus = false;
				testCase.addTextEvidenceCurrentStep("STATE: " + promoDtlState);
			 }
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 52*****************************************************************************
		addStep("Realizar la consulta: " + queryRPM_Promo1);
				
		validaStatus = tr.queryOracle(queryRPM_Promo1, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 53*****************************************************************************
		addStep("Realizar la consulta: " + queryRPM_Promo2);
		
		validaStatus = tr.queryOracle(queryRPM_Promo2, dbRMSv16);
		tr.processQueryInChunks(dbRMSv16, queryRPM_Promo2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 54*****************************************************************************
		addStep("Validar que aun no exista informacion de la promocion tienda 50YDY plaza 10VCZ en la BD FCDASPFR (DAS) con la consulta: ");
		
		String queryDas1Promo1 = "SELECT * FROM EO_RMS.RPM_PRICE_EVENT_PAYLOAD ep\r\n"
				+ "INNER JOIN EO_RMS.RPM_PROMO_DTL_PAYLOAD dp on ep.PRICE_EVENT_PAYLOAD_ID = dp.PRICE_EVENT_PAYLOAD_ID\r\n"
				+ "INNER JOIN EO_RMS.RPM_PROMO_LOCATION_PAYLOAD plp on PLP.PROMO_DTL_PAYLOAD_ID = DP.PROMO_DTL_PAYLOAD_ID\r\n"
				+ "INNER JOIN rms.rpm_promo rp on RP.PROMO_ID = dp.PROMO_ID\r\n"
				+ "WHERE rp.PROMO_DISPLAY_ID ='" +promoDisplayId1+ "' and PUBLISH_STATUS = 1 and location = '" +data.get("retek_cr")+ "'";
		
		validaStatus = tr.queryOracleEmpty(queryDas1Promo1, dbFCDASPFR);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		tr.processQueryInChunks(dbFCDASPFR, queryDas1Promo1);
			
		//***************************Paso 55*****************************************************************************
		addStep("Validar que aun no exista informacion de la promocion tienda 50YDY plaza 10VCZ en la BD FCDASPFR (DAS) con la consulta: ");

		String promoCompId = tr.getValueCampoOracle(queryRPM_Promo_Comp1, dbRMSv16, "PROMO_COMP_ID");
		String queryDas2Promo1 = "SELECT * FROM eo_rms.POS_PROMO_HEAD WHERE PROMO_COMP_ID='" +promoCompId+ "'";
		
		validaStatus = tr.queryOracleEmpty(queryDas2Promo1, dbFCDASPFR);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		tr.processQueryInChunks(dbFCDASPFR, queryDas2Promo1);
		
		//***************************Paso 56*****************************************************************************
		addStep("Ejecuta los pasos 54 a 55 con los valores de la tienda 50B87 plaza 10TLC\r\n"
				+ "PROMO_ID =16117, PROMO_DISPLAY_ID =18242 y location = 41341");
		
		String queryDas1Promo2 = "SELECT * FROM EO_RMS.RPM_PRICE_EVENT_PAYLOAD ep\r\n"
				+ "INNER JOIN EO_RMS.RPM_PROMO_DTL_PAYLOAD dp on ep.PRICE_EVENT_PAYLOAD_ID = dp.PRICE_EVENT_PAYLOAD_ID\r\n"
				+ "INNER JOIN EO_RMS.RPM_PROMO_LOCATION_PAYLOAD plp on PLP.PROMO_DTL_PAYLOAD_ID = DP.PROMO_DTL_PAYLOAD_ID\r\n"
				+ "INNER JOIN rms.rpm_promo rp on RP.PROMO_ID = dp.PROMO_ID\r\n"
				+ "WHERE rp.PROMO_DISPLAY_ID ='" +promoDisplayId2+ "' and PUBLISH_STATUS = 1 and location = '" +data.get("retek_cr2")+ "'";
		
		String promoCompId2 = tr.getValueCampoOracle(queryRPM_Promo_Comp2, dbRMSv16, "PROMO_COMP_ID");
		String queryDas2Promo2 = "SELECT * FROM eo_rms.POS_PROMO_HEAD WHERE PROMO_COMP_ID='" +promoCompId2+ "'";
		
		validaStatus = tr.queryOracleEmpty(queryDas1Promo2, dbFCDASPFR);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		tr.processQueryInChunks(dbFCDASPFR, queryDas1Promo2);
		validaStatus = tr.queryOracleEmpty(queryDas2Promo2, dbFCDASPFR);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
		tr.processQueryInChunks(dbFCDASPFR, queryDas2Promo2);
		
		//***************************Paso 57*****************************************************************************
		addStep("Ejecutar job nb_rpm_payload_publish_custom.sh_PROMO_16");

		//Buscar el job
		u.click(btnViewpoints);
		u.hardWait(3);
		control = new ControlM(u, testCase, obj4);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");

		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);

		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println("Valor de output getNewStatus:" + statusEjecucion);

		//Obtener output
		output = control.getOutput();
		System.out.println("Valor de output control:" + output);

		testCase.addTextEvidenceCurrentStep("Status de ejecucion: " + statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: " + output);

		//Validacion de la ejecucion del job
		casoPasado = false;

		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		    casoPasado = true;
		}

		assertTrue(casoPasado, "No termino correctamente");
		
		//***************************Paso 58*****************************************************************************
		addStep("Ejecutar job XXFC_PROMOTION_COPY_16");

		//Buscar el job
		u.click(btnViewpoints);
		
		u.hardWait(3);
		control = new ControlM(u, testCase, obj10);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");

		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);
		
		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println("Valor de output getNewStatus:" + statusEjecucion);

		//Obtener output
		output = control.getOutput();
		System.out.println("Valor de output control:" + output);

		testCase.addTextEvidenceCurrentStep("Status de ejecucion: " + statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: " + output);

		//Validacion de la ejecucion del job
		casoPasado = false;

		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		    casoPasado = true;
		}

		assertTrue(casoPasado, "No termino correctamente");
		
		//***************************Paso 59*****************************************************************************
		addStep("Ejecutar job XXFC_PROMOTION_SEND_16");

		//Buscar el job
		u.click(btnViewpoints);
		u.hardWait(3);
		control = new ControlM(u, testCase, obj11);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");

		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);

		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println("Valor de output getNewStatus:" + statusEjecucion);

		//Obtener output
		output = control.getOutput();
		System.out.println("Valor de output control:" + output);

		testCase.addTextEvidenceCurrentStep("Status de ejecucion: " + statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: " + output);

		//Validacion de la ejecucion del job
		casoPasado = false;

		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		    casoPasado = true;
		}

		assertTrue(casoPasado, "No termino correctamente");
		
		//***************************Paso 60*****************************************************************************
		addStep("Ejecutar job xxfc_pre_prmext_mom_16");

		//Buscar el job
		u.click(btnViewpoints);
		u.hardWait(3);
		control = new ControlM(u, testCase, obj12);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");

		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);

		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println("Valor de output getNewStatus:" + statusEjecucion);

		//Obtener output
		output = control.getOutput();
		System.out.println("Valor de output control:" + output);

		testCase.addTextEvidenceCurrentStep("Status de ejecucion: " + statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: " + output);

		//Validacion de la ejecucion del job
		casoPasado = false;

		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		    casoPasado = true;
		}

		assertTrue(casoPasado, "No termino correctamente");
		
		//***************************Paso 61*****************************************************************************
		addStep("Ejecutar job xxfc_pmodstemp_prm_mom_16");

		//Buscar el job
		u.click(btnViewpoints);
		u.hardWait(3);
		control = new ControlM(u, testCase, obj13);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");

		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);

		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println("Valor de output getNewStatus:" + statusEjecucion);

		//Obtener output
		output = control.getOutput();
		System.out.println("Valor de output control:" + output);

		testCase.addTextEvidenceCurrentStep("Status de ejecucion: " + statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: " + output);

		//Validacion de la ejecucion del job
		casoPasado = false;

		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		    casoPasado = true;
		}

		assertTrue(casoPasado, "No termino correctamente");

		//***************************Paso 62*****************************************************************************
		addStep("Ejecutar job xxfc_Prmext_mom_16");

		//Buscar el job
		u.click(btnViewpoints);
		u.hardWait(3);
		control = new ControlM(u, testCase, obj14);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");

		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);

		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println("Valor de output getNewStatus:" + statusEjecucion);

		//Obtener output
		output = control.getOutput();
		System.out.println("Valor de output control:" + output);

		testCase.addTextEvidenceCurrentStep("Status de ejecucion: " + statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: " + output);

		//Validacion de la ejecucion del job
		casoPasado = false;

		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		    casoPasado = true;
		}

		assertTrue(casoPasado, "No termino correctamente");

		//***************************Paso 63*****************************************************************************
		addStep("Ejecutar job xxfc_procesa_promdnld_prm_mom_16");

		//Buscar el job
		u.click(btnViewpoints);
		u.hardWait(3);
		control = new ControlM(u, testCase, obj15);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");

		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);

		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println("Valor de output getNewStatus:" + statusEjecucion);

		//Obtener output
		output = control.getOutput();
		System.out.println("Valor de output control:" + output);

		testCase.addTextEvidenceCurrentStep("Status de ejecucion: " + statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: " + output);

		//Validacion de la ejecucion del job
		casoPasado = false;

		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		    casoPasado = true;
		}

		assertTrue(casoPasado, "No termino correctamente");
		
		//***************************Paso 64*****************************************************************************
		addStep("Verificar que la informacion de la promocion tienda 50YDY plaza 10VCZ EXISTA en la BD FCDASPFR (DAS) con la consulta: " + queryDas1Promo1);
		
		validaStatus = tr.queryOracle(queryDas1Promo1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryDas1Promo1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);

		//***************************Paso 65*****************************************************************************
		addStep("Verificar que la informacion de la promocion tienda 50YDY plaza 10VCZ EXISTA en la BD FCDASPFR (DAS) con la consulta: " + queryDas2Promo1);
		
		validaStatus = tr.queryOracle(queryDas2Promo1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryDas2Promo1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);

		//***************************Paso 66*****************************************************************************
		addStep("Ejecuta los pasos 64 a 65 con los valores de la tienda 50B87 plaza 10TLC\r\n"
				+ "PROMO_ID =16117, PROMO_DISPLAY_ID =18242 y location = 41341");

		validaStatus = tr.queryOracle(queryDas1Promo2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryDas1Promo2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryDas2Promo2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryDas2Promo2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 67*****************************************************************************
		addStep("Verificar que la informacion de la promocion tienda 50YDY plaza 10VCZ llego a la tabla POS_PRM_HEAD en DAS lista para que la tome la PR63_CLOUD: " + queryDASPromo_Head1);
		
		validaStatus = tr.queryOracle(queryDASPromo_Head1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryDASPromo_Head1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String batchIdQuery1 = tr.getValueCampoOracle(queryDASPromo_Head1, dbFCDASPFR, "BATCH_ID");
		
		//Obtiene batch id de BD azure cloud erdd
		validaStatus = tr.queryBDAzure(queryGetBatchId, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String idQuery1 = tr.getValueCampoQuery(queryGetBatchId, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "BATCH_ID");

		//***************************Paso 68*****************************************************************************
		addStep("Verificar que la informacion de la promocion tienda 50B87 plaza 10TLC llego a la tabla POS_PRM_HEAD en DAS lista para que la tome la PR63_CLOUD: " + queryDASPromo_Head2);

		validaStatus = tr.queryOracle(queryDASPromo_Head2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryDASPromo_Head2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String batchIdQuery2 = tr.getValueCampoOracle(queryDASPromo_Head2, dbFCDASPFR, "BATCH_ID");

		//***************************Paso 69*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_MARK_PRM el caso de prueba @MTC-FT-001 Marcado de tiendas MARK_PRM_CLOUD campo MIGRATED_FLAG_OUT_CLOUD en C\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10VCZ y 50YDY respectivamente");

		data.put("plaza", "*");
		data.put("tienda", "*");
		data.put("batchId", batchIdQuery1);
		data.put("batchId2", batchIdQuery2);
		data.put("batchId3", batchIdQuery1);
		
		ATC_007_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenC atc007_mark_prm = new ATC_007_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenC();
		atc007_mark_prm.setTestCase(this.testCase);
		atc007_mark_prm.ATC_007_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenC_test(data);

		data.put("plaza", "10VCZ");
		data.put("tienda", "50YDY");
		data.put("location", "11524");
		data.put("id", idQuery1);
		
		//***************************Paso 70*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_MARK_PRM el caso de prueba @MTC-FT-001 Marcado de tiendas MARK_PRM_CLOUD campo MIGRATED_FLAG_OUT_CLOUD en C\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10TLC y 50B87 respectivamente");
		
		//Se ejecuta en conjunto al caso 007 del paso anterior
		testCase.addTextEvidenceCurrentStep("Se ejecuta en conjunto al caso 007 del paso anterior");

		//***************************Paso 71*****************************************************************************
		addStep("Ejecutar job PR63_CLOUD");

		if (MockAPI.equals("Y")) {
			resultado2 = tr.runPR63_CLOUD(MockAPI, API_StartCopy, fullUrl);
			assertTrue("La API WF_PRM_StartCopy no respondió exitosamente", resultado2);
		}else {
			//Buscar el job
			control = new ControlM(u, testCase, obj16);
			testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
			u.hardWait(10);
			u.click(btnViewpoints);
			flag = control.searchJob();
			u.hardWait(10);
			assertFalse(!flag, "No se encontro el job");
			
			//Ejecucion
			resultado = control.executeJob();
			System.out.println("Resultado de la ejecucion -> " + resultado);
	
			u.hardWait(40);
			
			//Obtener status 
			statusEjecucion = control.getNewStatus();
			System.out.println ("Valor de output getNewStatus:" +statusEjecucion);
			
			//Obtener output
			output = control.getOutput();
			System.out.println ("Valor de output control:" +output);
			
			testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+statusEjecucion);
			testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
			
			//Validacion de la ejecucion del job
			casoPasado = false;
			
			if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
			casoPasado = true;
			}		
			
			assertTrue(casoPasado, "No termino correctamente");
		}
		
		Thread.sleep(timeout_PR63_CLOUD);

		OffsetDateTime endTime = OffsetDateTime.now();
	    OffsetDateTime startTime = endTime.minusMinutes(5);
	    QueryTimeInterval timeInterval = new QueryTimeInterval(startTime, endTime);

		//***************************Paso 72*****************************************************************************
		addStep("Ejecutar job xxfc_pre_prmupd_mom_16");

		//Buscar el job
		u.click(btnViewpoints);
		u.hardWait(3);
		control = new ControlM(u, testCase, obj17);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");

		//Ejecucion
		resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);

		//Obtener status 
		statusEjecucion = control.getNewStatus();
		System.out.println("Valor de output getNewStatus:" + statusEjecucion);

		//Obtener output
		output = control.getOutput();
		System.out.println("Valor de output control:" + output);

		testCase.addTextEvidenceCurrentStep("Status de ejecucion: " + statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: " + output);

		//Validacion de la ejecucion del job
		casoPasado = false;

		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")|| statusEjecucion.equals("Executing")) {
		    casoPasado = true;
		}

		assertTrue(casoPasado, "No termino correctamente");
		
		u.close();

		//***************************Paso 73*****************************************************************************
		String queryStatusPRMHeadPr63Cloud=queryStatusPRMHeadPr63Cloud2.replace("batchIdValor", batchIdQuery1);

		addStep("Verificar que se haya modificado el WM_PRM_STATUS=E en la tabla POS_PRM_HEAD de la BD FCDASPFR con la consulta: " + queryStatusPRMHeadPr63Cloud);
		
		validaStatus = tr.queryOracle(queryStatusPRMHeadPr63Cloud, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryStatusPRMHeadPr63Cloud);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String cloud_prm_status = tr.getValueCampoOracle(queryStatusPRMHeadPr63Cloud, dbFCDASPFR, "CLOUD_PRM_STATUS");

		if(cloud_prm_status.equals("C")) {
			validaStatus = false;
		}
		assertFalse("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 74*****************************************************************************
		addStep("Ingresar a Log de Errores de Azure");
		
		 String clientId = data.get("clientId");
		 String clientSecret = data.get("clientSecret");
		 String tenantId = data.get("tenantId");
		 String workspaceId = data.get("workspaceId");
		
		 TokenCredential tokenCredential = tr.loginLogsAzure(clientId, tenantId, clientSecret);

		//***************************Paso 75*****************************************************************************
		addStep("Validar que se registro la ejecucion en la tabla de log: ");

		validaStatus = tr.consultaLogsAzure(tokenCredential, workspaceId, timeInterval);
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 76*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_ENRED_OUT el caso de prueba @MTC_FT_011 Validar Api V2 ACK-Tienda fisica cloud\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10VCZ y 50YDY respectivamente");
		
		String idPrmHdrOx1 = tr.getValueCampoQuery(queryGetIdPrmHdrOx, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "ID_PRM_HDR_OX");
		queryGetFolio = queryGetFolio.replace("1", idPrmHdrOx1);
		System.out.println(queryGetFolio);
		String foPrm1 = tr.getValueCampoQuery(queryGetFolio, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "FO_PRM");
		String idPrmHdrOx2 = tr.getValueCampoQuery(queryGetIdPrmHdrOx2, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "ID_PRM_HDR_OX");
		queryGetFolio2 = queryGetFolio2.replace("1", idPrmHdrOx2);
		String foPrm2 = tr.getValueCampoQuery(queryGetFolio2, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "FO_PRM");
		data.put("id_prm_hdr_ox", idPrmHdrOx1);
		data.put("promotionFolio", foPrm1);
		
		ATC_011_ENREDOUTV2_ValidarACKTiendaFisicaCloud atc011_enredoutv2 = new ATC_011_ENREDOUTV2_ValidarACKTiendaFisicaCloud();
		atc011_enredoutv2.setTestCase(this.testCase);
		atc011_enredoutv2.ATC_011_ENREDOUTV2_ValidarACKTiendaFisicaCloud_test(data);
		
		//***************************Paso 77*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_ENRED_OUT el caso de prueba @MTC_FT_011 Validar Api V2 ACK-Tienda fisica cloud\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10TLC y 50B87 respectivamente");
		
		validaStatus = tr.queryBDAzure(queryGetBatchId2, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String idQuery2 = tr.getValueCampoQuery(queryGetBatchId2, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "BATCH_ID");
		
		//Cambiar a diferente tienda
		data.put("departmentId", "10TLC");
		data.put("storeId", "50B87");
		data.put("location", "41341");
		data.put("id", idQuery2);
		data.put("id_prm_hdr_ox", idPrmHdrOx2);
		data.put("promotionFolio", foPrm2);
		
		atc011_enredoutv2.ATC_011_ENREDOUTV2_ValidarACKTiendaFisicaCloud_test(data);
		
		//***************************Paso 78 a 84*****************************************************************************
		addStep("No se pueden automatizar pasos 78 a 84 debido a doble factor de autenticacion");
		
		//***************************Paso 85*****************************************************************************
		addStep("Accesar BD FCWM6QA");
		
		testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWM6QA_NUEVA);
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMQA_NUEVA);
		
		//***************************Paso 86*****************************************************************************
		addStep("Validar que el documento existe en la tabla ocupada por SIMOTI con la consulta: " + querySimotiOutDoc1);
		
		 //validaStatus = tr.queryBDAzure(querySimotiOutDoc1, dbFCWM6QA);
//		tr.processQueryInChunks(dbFCWM6QA, querySimotiOutDoc1);
		 testCase.addBoldTextEvidenceCurrentStep("Este paso queda fuera de alcance en este desarrollo");
		
		//***************************Paso 87*****************************************************************************
		addStep("Validar que el documento existe en la tabla ocupada por SIMOTI con la consulta: " + querySimotiOutDoc2);
			
		//validaStatus = tr.queryBDAzure(querySimotiOutDoc2, dbFCWM6QA);
//		tr.processQueryInChunks(dbFCWM6QA, querySimotiOutDoc1);
		 testCase.addBoldTextEvidenceCurrentStep("Este paso queda fuera de alcance en este desarrollo");
				
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-005 Envio de promocion simple a tienda fisica cloud-manual";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Envio de promocion simple a tienda fisica cloud, archivo de respuesta almacenado en BD Azure, interface PR63_CLOUD ejecutada manual.";
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
		return "1. Acceso a las BD's: RMSv16, FCDASPFR y FCWM6QA\r\n"
				+ "2. Acceso a la BD de Azure: MDB_ERDD_PromotionCloud\r\n"
				+ "3. Acceso al aplicativo RMS16, Control M y portal SIMOTI\r\n"
				+ "4. Contar con un SKU existente en la tienda 50YDY plaza 10VCZ (LOC=11524) en las BDs RMSv16 y FCDASPFR.\r\n"
				+ "Y con otro SKU existente en la tienda 50B87 plaza 10TLC (LOC=41341) en las mismas BDs.\r\n"
				+ "5. Tener ordenados los jobs en Control M, el listado con sus datos esta en la pestania Cadena Jobs de esta matriz. (solicitado por correo)\r\n"
				+ "6. Haber descargado el archivo .ods template de promocion simple de RMS16 (Tasks -> Buscar: Promotions -> Download Template Simple Promotion)\r\n"
				+ "7. Haber descargado el archivo .ods template cambio estatus promocion de RMS16 (Tasks -> Buscar: Filter Promotions -> Download Template)\r\n"
				+ "8. Tener acceso a las matrices de prueba de MARK_PRM y ENRED OUT V2 con sus correspondientes componentes de QA\r\n"
				+ "9. Contar con acceso a Logs de ejecucion de Azure"
				+ "10. Validar en la BD de RMSv16 en la tabla rms.PERIOD que el campo VDATE corresponda al dia actual, las promociones solo seran enviadas si tienen fecha de inicio del dia actual o 1 dia despues.";

	}
}
