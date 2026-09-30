package RDM.cierreAlmacenes;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* DESCRIPCI�N: Clase para ejecucion de Caso de Prueba ATC_FT_002_ValidarReasonCodesTodosEnEnableFlagN.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* -MTC_FT_002 Validar Reason Codes En Enable Flag N
* 
* PREREQUISITOS:
* - Contar con acceso a:"
				-BASE DE DATOS RMS
				-BASE DE DATOS EBS
				-CONTROL M
				-Todos los objetos de enable_flag en estado N
* 
* INSUMOS:
* -Contar con los articulos necesarios creados en RMS tabla fem_fif_stg
* 
* @author Genaro Arredondo
* @date 07/Feb/2025
*/
 class ATC_FT_002_ValidarReasonCodesEnEnableFlagNTest extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarReasonCodesEnEnableFlagNTest_test(HashMap<String, String> data) throws Exception {
		
		
		/*
		 * VARIABLES
		 */
		String tran_date = data.get("tran_date");
		String inv_adj_user_id = data.get("inv_adj_user_id");
		String controlMUser = data.get("controlMUser");
		String controlMPass = PasswordUtil.decryptPassword(data.get("controlMPass"));
		String controlMServer = data.get("controlMServer");
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_RMS, GlobalVariables.DB_USER_RMS,
				GlobalVariables.DB_PASSWORD_RMS);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
				
				// PASO 1
				addStep("Validar configuraci�n de Enable Flag en N");
				System.out.println("PASO 1");
				
				testCase.addBoldTextEvidenceCurrentStep("Conectado a la BD RMS: ");
				testCase.addTextEvidenceCurrentStep("Host: " + dbRms.getHost());
				testCase.addTextEvidenceCurrentStep("User: " + dbRms.getUser());
				
				String enableN = String.format("select IF_ID, ATTRIBUTE1 TRAN_CODE, ABS(h.LOOKUP_CODE) REASON_CODE, ENABLED_FLAG \r\n"
						+ "from wmuser.wm_fnd_lookup_values h\r\n"
						+ "where lookup_type = 'REASON_CODE_TYPE'\r\n"
						+ "and attribute1 = 22\r\n"
						+ "and lookup_code between -200 and -93\r\n"
						+ " and ENABLED_FLAG = 'N' and IF_ID = 'RO8_MEX' order by lookup_code asc");
				System.out.println(enableN);

				SQLResult executeQueryEnableN = executeQuery(dbRms, enableN);

				boolean statusQueryEnableN = executeQueryEnableN.isEmpty();
				if (!statusQueryEnableN) {
					testCase.addQueryEvidenceCurrentStep(executeQueryEnableN);
				}
				
				// PASO 2
				addStep("Validar en la BD los ajustes de inventario a procesar "
						+ "con reference_3 y reference_9 en NULL.");
				System.out.println("PASO 2");
				
				String refNull = String.format("select ITEM, ITEM_DESC,REFERENCE_10 R10, REFERENCE_3 R3, REFERENCE_9 R9, TRAN_DATE,\r\n"
						+ "TRAN_CODE, TOTAL_COST,\r\n"
						+ "TOTAL_RETAIL, UNITS, VAT_RATE\r\n"
						+ "from fem_fif_stg where tran_date >='%s'\r\n"
						+ "and inv_adj_user_id = '%s'\r\n"
						+ "and reference_3 is null and reference_9 is null", tran_date, inv_adj_user_id);
				System.out.println(refNull);

				SQLResult executeQueryNull = executeQuery(dbRms, refNull);

				boolean statusQueryNull = executeQueryNull.isEmpty();
				if (!statusQueryNull) {
					testCase.addQueryEvidenceCurrentStep(executeQueryNull);
				}

				assertFalse(statusQueryNull, "No se encontraron resultados en Fem_fif_stg "
						+ "con tran_date = " + tran_date
						+ " y inv_adj_user_id = " + inv_adj_user_id);
				
				// PASO 3
				addStep("Ejecutar el proceso runRO8_AJUST_MEX");
				System.out.println("PASO 3");
					
				JSONObject objRO8Ajust = new JSONObject(data.get("runRO8_AJUST_MEX"));	
				Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPass);
				
				u.get(controlMServer);
				u.waitForLoadPage();		
				CM.logOn(); // loguea
				u.hardWait(15);
				

				ControlM rO8Ajust = new ControlM(u, testCase, objRO8Ajust); // Clase instanciada para job runRO8_AJUST_MEX
				rO8Ajust.searchJob(); // busca el job runRO8_AJUST_MEX
				
				String resultadoEjecucionRO8Ajust = rO8Ajust.executeJob(); // ejecuta el job runRO8_AJUST_MEX

				assertTrue(resultadoEjecucionRO8Ajust.equals("Wait for Event") || resultadoEjecucionRO8Ajust.equals("Ended NOT OK"), 
						"El job runRO8_AJUST_MEX no se ejecuto correctamente");
				
				// PASO 4
				addStep("Validar en la BD los ajustes de inventario con codigo -1029"
						+ "en el campo reference_3.");
				System.out.println("PASO 4");
				
				String refCodigo = String.format("select ITEM, ITEM_DESC,REFERENCE_10 R10, REFERENCE_3 R3, REFERENCE_9 R9, TRAN_DATE,\r\n"
						+ "TRAN_CODE, TOTAL_COST,\r\n"
						+ "TOTAL_RETAIL, UNITS, VAT_RATE\r\n"
						+ "from fem_fif_stg where tran_date >='%s'\r\n"
						+ "and inv_adj_user_id = '%s'\r\n"
						+ "and reference_3 = -1029", tran_date, inv_adj_user_id);
				System.out.println(refCodigo);

				SQLResult executeQueryCodigo = executeQuery(dbRms, refCodigo);

				boolean statusQueryCodigo = executeQueryCodigo.isEmpty();
				if (!statusQueryCodigo) {
					testCase.addQueryEvidenceCurrentStep(executeQueryCodigo);
				}

				assertFalse(statusQueryCodigo, "No se encontraron resultados en Fem_fif_stg "
						+ "con reference_3 = -1029");
				
				
				
	}
	
	
	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "Contar con acceso a:\r\n"
				+ "BASE DE DATOS RMS\r\n"
				+ "BASE DE DATOS EBS\r\n"
				+ "CONTROL M\r\n"
				+ "\r\n"
				+ "Contar con los objetos necesarios creados en R08\r\n"
				+ "Todos los objetos de enable_flag en estado N";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al seleccionar los reason code registrado con"
				+ " enable_flag con valor N en R08, generando"
				+ " codigos de error -1029 en las polizas";	
	}

	@Override
	public String setTestDesigner() {
		return "genaroa@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarReasonCodesEnEnableFlagNTest_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
