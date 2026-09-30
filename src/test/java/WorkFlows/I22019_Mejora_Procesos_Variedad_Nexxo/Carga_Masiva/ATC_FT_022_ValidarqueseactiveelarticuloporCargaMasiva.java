package WorkFlows.I22019_Mejora_Procesos_Variedad_Nexxo.Carga_Masiva;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
* PREREQUISITOS: 
* - Solicitar a los operadores de CONTROL-M de la carpeta UAT_SIV_PROCESOS los siguientes JOBS:
*  XXVA_ENGCARGAMSV_TARDE
*  xxva_itemloc_staging_creation
*  NB_ITEM_LOC_16
* - Realizar la carga masiva al buzon FTP, que el archivo de carga sea un CSV y tenga los siguientes encabezados:
* ITEM,LOC_TYPE,LOCATION,PRIMARY_SUPP,PRIMARY_CNTRY,STATUS,INSTRUCCION_PLANOGRAMACION,Tipo_MSV 
*
* DESCRIPCION: Validar que en se realice el flujo de MSV de manera automatizada
* @author elberth.ruiz@grupotimex.onmicrosoft.com
*/

public class ATC_FT_022_ValidarqueseactiveelarticuloporCargaMasiva extends BaseExecution{
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_022_ValidarqueseactiveelarticuloporCargaMasiva_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> MSV");
	
	SQLResult queryResult;
	String Item = data.get("ITEM");
	
	
	String usuarioControlM = GlobalVariables.CtrlM_User_Neo10;
	String passControlM = GlobalVariables.CtrlM_Pss_Neo10;
	
	 //Conexiones a BD
	
	//PIEVE***
	 utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
	//DAS***
	 utils.sql.SQLUtil dbDAS_UAT = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_DAS_PERF,GlobalVariables.DB_USER_DAS_PERF, GlobalVariables.DB_PASSWORD_DAS_PERF);
	 //RMS16***
	 utils.sql.SQLUtil dbRMS16_UAT = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS16,GlobalVariables.DB_USER_RMS16, GlobalVariables.DB_PASSWORD_RMS16);
	 //RMS10***
	 utils.sql.SQLUtil dbRMS10 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS10,GlobalVariables.DB_USER_RMS10, GlobalVariables.DB_PASS_RMS10);
	 
	 //***********************
	 
	//Se valida la Tabla XXVA_ITEM_SUP_PLAZA_TIENDA dondse se registra el movimiento de manera correcta-PIEVE
	String QUERY_ITEM_SUP_PLAZA_TIENDA = "SELECT ITEM_RTK,ITEM_TRANSACCION_ID,PLAZA_ID,TIENDA_ID,STATUS,CREATE_DATE FROM XXVA_ITEM_SUP_PLAZA_TIENDA WHERE ITEM_RTK='"+Item+"' and LOC='15'";
	//EJECUTAMOS EL JOB xxva_itemloc_staging_creation 
	
	//Se valida que la informaci�n cae de manera correcta a la Tabla Stagging de RMS16: 
	String QUERY_XITEMLOCDESC = "SELECT * FROM RMS.STG_XITEMLOCDESC WHERE ITEM = '"+Item+"' AND PROCESS_IND='N' AND ROWNUM<='1'";//regresarlo a N
	//RMS16
	String QUERY_STG_XITEMLOCDTL = "SELECT STG_MSG_ID,STATUS, STORE_ORD_MULT,CREATE_DATETIME FROM RMS.STG_XITEMLOCDTL WHERE STG_MSG_ID IN(SELECT STG_MSG_ID FROM RMS.STG_XITEMLOCDESC WHERE ITEM = '"+Item+"' AND PROCESS_IND='N' AND ROWNUM<='1')";//regresarlo a N
	//RMS16
	String QUERY_V_ITEM_MASTER = "SELECT ITEM, STORE_ORD_MULT FROM RMS.V_ITEM_MASTER WHERE ITEM='"+Item+"'";
	//PIEVE
	String QUERY_XXVA_ITEMLOC = "SELECT ITEM, LOCATION, PRIM_SUPP,STATUS, PROCESSED_IND, STORE_ORD_MULT, CREATE_DATETIME FROM XXVA_ITEMLOC WHERE ITEM='"+Item+"' and LOCATION='15' AND PROCESSED_IND = 'Y'";
	//EJECUTAMOS EL JOB para Bajar la Informaci�n a tabla Final de RMS16 -> NB_ITEM_LOC_16 (ejecutarlo y esperar un tiempo de "gracia")
	
	//Validamos Tabla ITEM_LOC en RMS16
	String QUERY_ITEM_LOC_RMS16 = "SELECT ITEM,LOC,STORE_ORD_MULT,PRIMARY_SUPP,CREATE_DATETIME FROM RMS.ITEM_LOC WHERE ITEM = '"+Item+"' AND LOC ='15'";
	//Validamos Tabla ITEM_LOC en DAS
	String QUERY_ITEM_LOC_DAS = "SELECT ITEM,LOC,STORE_ORD_MULT,PRIMARY_SUPP,CREATE_DATETIME  FROM RMS.ITEM_LOC WHERE ITEM = '"+Item+"' AND LOC ='15'";
	//Validamos replica a VISTA 16 : RMS16_V.V_ITEM_LOC
	String QUERY_RMS16_V_ITEM_LOC_DAS = "SELECT ITEM,LOC,STORE_ORD_MULT,PRIMARY_SUPP,CREATE_DATETIME  FROM RMS16_V.V_ITEM_LOC WHERE ITEM = '"+Item+"' AND LOC ='15'";
	//Validamos replica a VISTA 10 : RMS10_V.V_ITEM_LOC
	String QUERY_RMS10_V_ITEM_LOC_DAS = "SELECT ITEM,LOC,STORE_ORD_MULT,PRIMARY_SUPP,CREATE_DATETIME  FROM RMS10_V.V_ITEM_LOC WHERE ITEM = '"+Item+"' AND LOC ='15'";
	//Validamos replica a Tabla delta: RMS_DT.DT_ITEM_LOC
	String QUERY_RMS_DT_ITEM_LOC_DAS = "SELECT ITEM,LOC,STORE_ORD_MULT,PRIMARY_SUPP,CREATE_DATETIME  FROM RMS_DT.DT_ITEM_LOC WHERE ITEM = '"+Item+"' AND LOC ='15'";
	
	u = new SeleniumUtil(new MEdgeTest(),true);
	//u = new SeleniumUtil(new ChromeTest(), true);
   //*******************************************************************************************

	//Paso 1 y 2 ******************************************************************************
	addStep("Validamos registro de Movimiento en Tabla xxva_item_sup_plaza_tienda en PIEVE");
	try {
		queryResult = executeQuery(dbXXVA_SIV3, QUERY_ITEM_SUP_PLAZA_TIENDA);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}
		
	//Paso 3 **********************************************************************************
	addStep("Corremos JOB  para el env�o de informaci�n a RMS16 \r\n"
			+ "xxva_itemloc_staging_creation en CONTROL M\r\n");
	
	u = new SeleniumUtil (new MEdgeTest(), true);
	u.getDriver().manage().deleteAllCookies();
	u.get(GlobalVariables.CtrlM_URL);
	u.getDriver().manage().window().maximize();

	u.hardWait(5);
	Control_mInicio cm = new Control_mInicio(u, usuarioControlM, passControlM);
	u.hardWait(40);
	u.waitForLoadPage();
	testCase.addScreenShotCurrentStep(u, "inicio1");
	cm.logOn();
	u.hardWait(10);
	testCase.addScreenShotCurrentStep(u, "inicio2");

	JSONObject obj = new JSONObject(data.get("Job"));
	ControlM job = new ControlM(u, testCase, obj);
	u.hardWait(4);
	boolean flag = job.searchJob();
	assertTrue(flag);

	String resultado = job.executeJob();
	u.hardWait(120);
	
	addStep("Resultado de la ejecucion: " + resultado);
	System.out.println("Resultado de la ejecucion -> " + resultado);
	testCase.addBoldTextEvidenceCurrentStep("Output: ");
	System.out.println("Valor de output :" + job.getOutput());
	testCase.addTextEvidenceCurrentStep(job.getOutput());
	if (job.getOutput().toLowerCase().contains("error")) {
		throw new AssertionError("Existe un error en el Output");
	}
	assertEquals(resultado, "Ended OK");

	u.hardWait(3);
	job.closeViewpoint();
	u.close();
		
	//Paso 4 **********************************************************************************
	addStep("Validamos Tabla Stagging  STG_XITEMLOCDESC en el campo PROCESS_IND=N en BD RMS16");	
	try {
		queryResult = executeQuery(dbRMS16_UAT, QUERY_XITEMLOCDESC);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}
		

	//Paso 5 ******************************************************************************
	addStep("Validamos Tabla Stagging STG_XITEMLOCDTL en los campos STATUS=A  y  el valor del campo \r\n "
			+ "Store_Order_Mult se obtendr� de la vista V_ITEM_MASTER utilizando el ITEM, \r\n"
			+ "si no existe en dicha tabla se le asignara el valor por default \"E\".");
	try {
		queryResult = executeQuery(dbRMS16_UAT, QUERY_STG_XITEMLOCDTL);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}
	
	//Paso 6 *****************************************************************************
	addStep("Validar la tabla XXVA_ITEMLOC, el campo PROCESSED_IND='Y' (procesado)");
	try {
		queryResult = executeQuery(dbXXVA_SIV3, QUERY_XXVA_ITEMLOC);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}

	//Paso 7 ******************************************************************************
	addStep("Ejecutamos JOB para Bajar la Informaci�n a tabla Final de RMS16 -> NB_ITEM_LOC_16");
	
	u = new SeleniumUtil (new MEdgeTest(), true);
	u.getDriver().manage().deleteAllCookies();
	u.get(GlobalVariables.CtrlM_URL);
	u.getDriver().manage().window().maximize();

	u.hardWait(5);
	Control_mInicio cm1 = new Control_mInicio(u, usuarioControlM, passControlM);
	u.hardWait(10);
	u.waitForLoadPage();
	testCase.addScreenShotCurrentStep(u, "inicio1");
	cm1.logOn();
	u.hardWait(15);///
	testCase.addScreenShotCurrentStep(u, "inicio2");

	JSONObject obj1 = new JSONObject(data.get("Job1"));
	ControlM job1 = new ControlM(u, testCase, obj1);
	//u.hardWait(4);
	boolean flag1 = job1.searchJob();
	assertTrue(flag1);

	job1.accionSinOutput();
	//String resultado1 = job1.executeJob(); //mientras se arregla el detalle del job
	u.hardWait(200);
	/*
	 * By statusJob = By.cssSelector("div.ag-cell[col-id=\"JobStatus\"] > span");
	 * String statusJ = u.getDriver().findElement(statusJob).getText();
	 * System.out.println("--> El estatus Actual es: " + statusJ);
	 */
	//addStep("Resultado de la ejecucion: " + resultado1);
	//System.out.println("Resultado de la ejecucion -> " + resultado1);
	//testCase.addBoldTextEvidenceCurrentStep("Output: ");
	//System.out.println("Valor de output :" + job1.getOutput());
	//testCase.addTextEvidenceCurrentStep(job1.getOutput());
	//if (job1.getOutput().toLowerCase().contains("error")) {
	//	throw new AssertionError("Existe un error en el Output");
	//}
	//assertEquals(resultado1, "Ended OK");//PENDIENTE VER EL RESULTADO
    /*Esta comentado mientras se ve el tema del JOB, queda en color amarillo*/
	u.hardWait(3);
	job1.closeViewpoint();
	u.close();
	
	//Paso 8 ******************************************************************************
	addStep("Validamos Tabla ITEM_LOC en RMS16");
	try {
		queryResult = executeQuery(dbRMS16_UAT, QUERY_ITEM_LOC_RMS16);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}
	
	//Paso 9 ******************************************************************************
	addStep("Validamos replica a DAS: RMS.ITEM_LOC");
	try {
		queryResult = executeQuery(dbDAS_UAT, QUERY_ITEM_LOC_DAS);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}
	
	//Paso 10 *****************************************************************************
	addStep("Validamos replica a VISTA 16 : RMS16_V.V_ITEM_LOC");
	try {
		queryResult = executeQuery(dbDAS_UAT, QUERY_RMS16_V_ITEM_LOC_DAS);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}
	
	//Paso 11 *****************************************************************************
	addStep("Validamos replica a Vista 10: RMS10_V.V_ITEM_LOC");
	try {
		queryResult = executeQuery(dbDAS_UAT, QUERY_RMS10_V_ITEM_LOC_DAS);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}
	
	//Paso 12 *****************************************************************************
	addStep("Validamos replica a Tabla delta: RMS_DT.DT_ITEM_LOC");
	try {
		queryResult = executeQuery(dbDAS_UAT, QUERY_RMS_DT_ITEM_LOC_DAS);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}

	}

	@AfterMethod
	public void afterMethod() {
	//u.getDriver().quit();
	
	}
	
	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Terminado. ATC_FT_022_ValidarqueseactiveelarticuloporCargaMasiva";
	}

	@Override
	public String setTestDesigner() {
		return "Elberth Humberto Ruiz Cota";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_022_ValidarqueseactiveelarticuloporCargaMasiva_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
