package RDM.cierreAlmacenes;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.openqa.selenium.By;
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
* DESCRIPCI�N: Clase para ejecucion de Caso de Prueba ATC_FT_006_RMS16ValidarReasonCodesEnEnableFlagEsperado.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* -MTC_FT_006 RMS16 Validar Reason Codes En Enable Flag Esperado
* 
* PREREQUISITOS:
* - Contar con acceso a:"
				-BASE DE DATOS RMS16 FCMOMSIT
				-BASE DE DATOS EBS
				-CONTROL M
				-Todos los objetos de enable_flag en estado esperado
* 
* INSUMOS:
* -Contar con los articulos necesarios creados en RMS tabla nb_fem_fif_stg
* 
* @author Genaro Arredondo
* @date 12/Feb/2025
*/

public class ATC_FT_006_RMS16ValidarReasonCodesEnEnableFlagEsperadoTest extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_RMS16ValidarReasonCodesEnEnableFlagEsperadoTest_test(HashMap<String, String> data) throws Exception {
		
		
		/*
		 * VARIABLES
		 */
		String tran_date = data.get("tran_date");
        String controlMUser = data.get("controlMUser");
        String controlMPass = PasswordUtil.decryptPassword(data.get("controlMPass"));
        String controlMServer = data.get("controlMServer");
        String inv_adj_user_id = data.get("inv_adj_user_id");
        String accounting_date = data.get("accounting_date");
        String period_name = data.get("period_name");
        String restoreBtn = data.get("restore");
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_RMS16, GlobalVariables.DB_USER_RMS16,
                GlobalVariables.DB_PASSWORD_RMS16);
		SQLUtil dbEbs = new SQLUtil(GlobalVariables.DB_HOST_EBS, GlobalVariables.DB_USER_EBS,
                GlobalVariables.DB_PASSWORD_EBS);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
				
				// PASO 1
		
				addStep("Validar configuraci�n de Enable Flag en default");
				System.out.println("PASO 1");
				
				testCase.addBoldTextEvidenceCurrentStep("Conectado a la BD RMS: ");
				testCase.addTextEvidenceCurrentStep("Host: " + dbRms.getHost());
				testCase.addTextEvidenceCurrentStep("User: " + dbRms.getUser());
				
				String enableDef = String.format("select IF_ID, ATTRIBUTE1 TRAN_CODE, ABS(h.LOOKUP_CODE) REASON_CODE, ENABLED_FLAG \n" +
		                "                        from wmuser.wm_fnd_lookup_values h\n" +
		                "                        where lookup_type = 'REASON_CODE_TYPE'\n" +
		                "                        and attribute1 = 22\n" +
		                "                        and IF_ID = 'RO8_MX' order by lookup_code asc");
				System.out.println(enableDef);

				SQLResult executeQueryEnableDef = executeQuery(dbRms, enableDef);

				boolean statusQueryEnableDef = executeQueryEnableDef.isEmpty();
				if (!statusQueryEnableDef) {
					testCase.addQueryEvidenceCurrentStep(executeQueryEnableDef);
				}
				
				// PASO 2
				addStep("Validar en la BD los ajustes de inventario a procesar con "
						+ "tran_code 22, plaza 15CMF,reference_3 y reference_9 en NULL.");
				System.out.println("PASO 2");
				
				String refNull = String.format("select ITEM, ITEM_DESC,REFERENCE_10 R10, REFERENCE_3 R3, REFERENCE_9 R9, TRAN_DATE, TRAN_CODE, TOTAL_COST, TOTAL_RETAIL, UNITS, VAT_RATE\r\n"
		                + "from rms_ext.nb_fem_fif_stg where tran_date >='%s'\r\n"
		                + "and inv_adj_user_id = '%s'\r\n"
		                + "and reference_3 is null and reference_9 is null", tran_date, inv_adj_user_id);
				System.out.println(refNull);

				SQLResult executeQueryNull = executeQuery(dbRms, refNull);

				boolean statusQueryNull = executeQueryNull.isEmpty();
				if (!statusQueryNull) {
					testCase.addQueryEvidenceCurrentStep(executeQueryNull);
				}

				assertFalse(statusQueryNull, "No se encontraron resultados en nb_fem_fif_stg "
						+ "con tran_date = " + tran_date
						+ " y inv_adj_user_id = " + inv_adj_user_id);
				
				// PASO 3
				addStep("Ejecutar el proceso runRO8_AJUST_MEX");
				System.out.println("PASO 3");
					
				JSONObject objRO8Ajust = new JSONObject(data.get("runRO08_MX_AJUST"));	
				Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPass);
				
				u.get(controlMServer);
				u.waitForLoadPage();		
				CM.logOn(); // loguea
				u.hardWait(20);
				

				ControlM rO8Ajust = new ControlM(u, testCase, objRO8Ajust); // Clase instanciada para job runRO08_MX_AJUST
				rO8Ajust.searchJob(); // busca el job runRO08_MX_AJUST
				
				String resultadoEjecucionRO8Ajust = rO8Ajust.executeJob(); // ejecuta el job runRO08_MX_AJUST
				
				u.hardWait(20);

				assertTrue(resultadoEjecucionRO8Ajust.equals("Ended OK") || resultadoEjecucionRO8Ajust.equals("Ended NOT OK"), 
						"El job runRO8_AJUST_MEX no se ejecuto correctamente");
				u.hardWait(15);
				
				// PASO 4
				addStep("Validar en la BD los ajustes de inventario con valor correspondiente"
						+ "en el campo reference_3.");
				System.out.println("PASO 4");
				
				String ref3 = String.format("select ITEM, ITEM_DESC,REFERENCE_10 R10, REFERENCE_3 R3, REFERENCE_9 R9, TRAN_DATE, TRAN_CODE, TOTAL_COST, TOTAL_RETAIL, UNITS, \n" +
		                "VAT_RATE \r\n"
		                + "from rms_ext.nb_fem_fif_stg \r\n"
		                + "where tran_date = '%s'\r\n"
		                + "and inv_adj_user_id = '%s'\r\n", tran_date, inv_adj_user_id);
				System.out.println(ref3);

				SQLResult executeQueryref3 = executeQuery(dbRms, ref3);

				boolean statusQueryref3 = executeQueryref3.isEmpty();
				if (!statusQueryref3) {
					testCase.addQueryEvidenceCurrentStep(executeQueryref3);
				}

				assertFalse(statusQueryref3, "No se encontraron resultados en nb_fem_fif_stg "
						+ "con reference_3 con su valor correspondiente");
				
				// PASO 5
		        addStep("Ingresar a la BD EBS y validar los ajustes de inventario"
		                + " con su póliza y cuentas contables.");
		        System.out.println("PASO 5");

		        testCase.addBoldTextEvidenceCurrentStep("Conectado a la BD EBS: ");
		        testCase.addTextEvidenceCurrentStep("Host: " + dbEbs.getHost());
		        testCase.addTextEvidenceCurrentStep("User: " + dbEbs.getUser());

		        String refInterface = String.format("select h.status, h.group_id, ABS(h.ATTRIBUTE12) REASON_CODE, h.SEGMENT2, h.SEGMENT3,\r\n"
		                + " h.SEGMENT4, h.ENTERED_DR, h.ENTERED_CR, h.REFERENCE1, \r\n"
		                + "h.REFERENCE10 from gl.gl_interface h \r\n"
		                + "where accounting_date = '%s'\r\n"
		                + "and period_name = '%s'\r\n"
		                + "and user_je_source_name = 'Retek' order by h.attribute12 desc", accounting_date, period_name);
		        System.out.println(refInterface);

		        SQLResult executeQueryInterface = executeQuery(dbEbs, refInterface);

		        boolean statusQueryInterface = executeQueryInterface.isEmpty();
		        if (!statusQueryInterface) {
		            testCase.addQueryEvidenceCurrentStep(executeQueryInterface);
		        }
		        assertFalse(statusQueryInterface, "No se encontraron resultados en EBS");

		        // PASO 6
		        addStep("Ejecutar el proceso import_retek_ciclico_MEX");
		        System.out.println("PASO 6");
		        u.hardWait(20);

		        JSONObject objImpRetek = new JSONObject(data.get("import_retek_ciclico_MEX"));

		        u.get(controlMServer);
		        u.waitForLoadPage();
		        u.hardWait(60);
		        u.click(By.xpath(restoreBtn));
		        u.waitForLoadPage();

		        ControlM ImpRetek = new ControlM(u, testCase, objImpRetek); // Clase instanciada para job import_retek_ciclico_MEX
		        ImpRetek.searchJob(); // busca el job import_retek_ciclico_MEX

		        String resultadoEjecucionImpRetek = ImpRetek.executeJob(); // ejecuta el job import_retek_ciclico_MEX

		        assertTrue(resultadoEjecucionImpRetek.equals("Wait for Event") || resultadoEjecucionImpRetek.equals("Ended NOT OK"),
		                "El job ciclo_ro8_new_MEX no se ejecuto correctamente");


		        // PASO 7
		        addStep("Ingresar a la BD EBS y validar que la información llegue a asientos");
		        System.out.println("PASO 7");

		        String ebs = String.format("select h.EXTERNAL_REFERENCE, h.RUNNING_TOTAL_DR, h.RUNNING_TOTAL_CR, h.NAME, h.DESCRIPTION \r\n" +
		                "from GL.gl_je_headers h, apps.gl_je_sources s\n" +
		                "where h.PERIOD_NAME = '%s' and h.default_effective_date = '%s'\r\n" +
		                "and s.JE_SOURCE_NAME = h.je_source and s.user_je_source_name = 'Retek'\r\n" +
		                "order by h.last_update_date desc", period_name, accounting_date);
		        System.out.println(ebs);

		        SQLResult executeQueryebs = executeQuery(dbEbs, ebs);

		        boolean statusQueryebs = executeQueryebs.isEmpty();
		        if (!statusQueryebs) {
		            testCase.addQueryEvidenceCurrentStep(executeQueryebs);
		        }
		        assertFalse(statusQueryebs, "No se encontraron resultados en EBS");
				
	}
	
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "Contar con acceso a:\\r\\n\"\r\n"
				+ "				+ \"BASE DE DATOS RMS16 FCMOMSIT\\r\\n\"\r\n"
				+ "				+ \"BASE DE DATOS EBS\\r\\n\"\r\n"
				+ "				+ \"CONTROL M\\r\\n\"\r\n"
				+ "				+ \"\\r\\n\"\r\n"
				+ "				+ \"Contar con los objetos necesarios creados en R08\\r\\n\"\r\n"
				+ "				+ \"Todos los objetos de enable_flag en estado esperado";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al seleccionar los reason code registrado con"
				+ " enable_flag con valor esperado en R08, generando"
				+ " el reporte satisfactoriamente";	
	}

	@Override
	public String setTestDesigner() {
		return "genaroa@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_RMS16ValidarReasonCodesEnEnableFlagEsperadoTest_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
