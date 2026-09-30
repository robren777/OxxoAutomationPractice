package WM.interfaces.PR35_MX;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.FTPUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/* DESCRIPCION : Envio de recetas (PCC) hacia POS.
 * 
 * MTC_FT_PR35_MX_001-EJECUCION EXITOSA DE LA PR35_MX
 * 
 * PREREQUISITOS:
 * 	ACCESO A LA BD DE NUCLEO.
 * 	TENER ORDENADO LOS JOBS DE LA PR35_MX
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos PCC (Envio de Recetas).
 * 
 * @Author Gustavo Morales
 * @Date 09/Abril/2025
 */
public class ATC_FT_001_PR35MX_EnvioRecetasBanderaY extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_PR35MX_EnvioRecetasBanderaY_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerías pr5 para la POS_OUT_DOC_RMS
		 *********************************************************************/
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		// POSUSER/
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WM_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		// WMLOG/
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WMLOG_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A DASPRF----------------------------------------------- ");
		// DAS/
		Connection dbDas = DriverManager.getConnection(GlobalVariables.DB_HOST_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_DASPERF_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A DASPRF----------------------------------------------- ");

		testCase.setProject_Name("Transformacion Multiformato");

		String run_status = "";
		String doc_name = "";

		// ***************************************** Paso 1
		// *****************************************************

		addStep("Revisar que exista informacion en la tabla de WM_INTERFACE_CONFIG");

		String QueryDoc = "SELECT INTERFASE, ENTIDAD, OPERACION, CATEGORIA, VALOR1 \r\n"
				+ "FROM WMUSER.WM_INTERFASE_CONFIG WHERE INTERFASE = '" + data.get("interfase") + "'";

		PreparedStatement PosQuery = dbPos.prepareStatement(QueryDoc);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc);
			while (PosResult.next()) {

				String INTERFASE = PosResult.getString("INTERFASE");
				String ENTIDAD = PosResult.getString("ENTIDAD");
				String OPERACION = PosResult.getString("OPERACION");
				String CATEGORIA = PosResult.getString("CATEGORIA");
				String VALOR1 = PosResult.getString("VALOR1");

				testCase.addTextEvidenceCurrentStep(
						"--> " + "INTERFASE : " + INTERFASE + " | " + "ENTIDAD : " + ENTIDAD + " | " + "OPERACION : "
								+ OPERACION + " | " + "CATEGORIA : " + CATEGORIA + " | " + "VALOR1 : " + VALOR1);

			}

		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

		// ***************************************** Paso 2
		// *****************************************************

		addStep("Revisar que exista informacion en la tabla de WM_OXXO_ROLLOUT_CONFIG");

		String QueryDoc1 = "SELECT CR_PLAZA, CR_CEDIS, LOC_TYPE, V16, LOC_DESC\r\n"
				+ "FROM WMUSER.WM_OXXO_ROLLOUT_CONFIG WHERE CR_PLAZA = '" + data.get("plaza") + "'";

		PreparedStatement PosQuery1 = dbPos.prepareStatement(QueryDoc1);
		ResultSet PosResult1 = PosQuery1.executeQuery();

		if (PosResult1.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc1);
			while (PosResult1.next()) {

				String CR_PLAZA = PosResult1.getString("CR_PLAZA");
				String CR_CEDIS = PosResult1.getString("CR_CEDIS");
				String LOC_TYPE = PosResult1.getString("LOC_TYPE");
				String V16 = PosResult1.getString("V16");
				String LOC_DESC = PosResult1.getString("LOC_DESC");

				testCase.addTextEvidenceCurrentStep("--> " + "CR_PLAZA : " + CR_PLAZA + " | " + "CR_CEDIS : " + CR_CEDIS
						+ " | " + "LOC_TYPE : " + LOC_TYPE + " | " + "V16 : " + V16 + " | " + "LOC_DESC : " + LOC_DESC);

			}

		} else {
			assertFalse(!PosResult1.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult1.close();
		PosQuery1.close();

		// ***************************************** Paso 3
		// *****************************************************

		addStep("Revisar que exista informacion en la tabla de POS_RULES_DOC_CFG");

		String QueryDoc2 = "SELECT ID, DOC_TYPE, PV_CR_PLAZA, MIGRATED_FLAG_OUT\r\n"
				+ "FROM POS_RULES_DOC_CFG WHERE PV_CR_PLAZA = '" + data.get("plaza") + "'\r\n" + "AND DOC_TYPE = 'PCC'";

		PreparedStatement PosQuery2 = dbPos.prepareStatement(QueryDoc2);
		ResultSet PosResult2 = PosQuery2.executeQuery();

		if (PosResult2.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc2);
			while (PosResult2.next()) {

				String ID = PosResult2.getString("ID");
				String DOC_TYPE = PosResult2.getString("DOC_TYPE");
				String PV_CR_PLAZA = PosResult2.getString("PV_CR_PLAZA");
				String MIGRATED_FLAG_OUT = PosResult2.getString("MIGRATED_FLAG_OUT");

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "DOC_TYPE : " + DOC_TYPE + " | "
						+ "PV_CR_PLAZA : " + PV_CR_PLAZA + " | " + "MIGRATED_FLAG_OUT : " + MIGRATED_FLAG_OUT);

			}

		} else {
			assertFalse(!PosResult2.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult2.close();
		PosQuery2.close();

		// ***************************************** Paso 4
		// *****************************************************

		addStep("Revisar que exista informacion en la vista de DAS V_POS_RECIPE_INGREDIENT_STG");

		String QueryDas = "SELECT TEMPLATE_ID, ACTION_TYPE, BASE_EXTRACTED_IND, ITEM, STORE, STORE_NAME10, INGREDIENT_ID\r\n"
				+ "FROM EO_RMS.V_POS_RECIPE_INGREDIENT_STG WHERE STORE_NAME10 = '" + data.get("plaza/tienda") + "'";

		PreparedStatement DasQuery = dbDas.prepareStatement(QueryDas);
		ResultSet DasResult = DasQuery.executeQuery();

		if (DasResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDas);
			while (DasResult.next()) {

				String TEMPLATE_ID = DasResult.getString("TEMPLATE_ID");
				String ACTION_TYPE = DasResult.getString("ACTION_TYPE");
				String BASE_EXTRACTED_IND = DasResult.getString("BASE_EXTRACTED_IND");
				String ITEM = DasResult.getString("ITEM");
				String STORE = DasResult.getString("STORE");
				String STORE_NAME10 = DasResult.getString("STORE_NAME10");
				String INGREDIENT_ID = DasResult.getString("INGREDIENT_ID");

				testCase.addTextEvidenceCurrentStep("--> " + "TEMPLATE_ID : " + TEMPLATE_ID + " | " + "ACTION_TYPE : "
						+ ACTION_TYPE + " | " + "BASE_EXTRACTED_IND : " + BASE_EXTRACTED_IND + " | " + "ITEM : " + ITEM
						+ " | " + "STORE: " + STORE + " | " + "STORE_NAME10: " + STORE_NAME10 + " | "
						+ "INGREDIENT_ID: " + INGREDIENT_ID);

			}

		} else {
			assertFalse(!DasResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		DasResult.close();
		DasQuery.close();

		/********** Paso 5 *********/

		addStep("Ejecucion job : PR35_MX en  control-M");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

		Control_mInicio CM = new Control_mInicio(u, data.get("cm_user"), data.get("cm_ps"));
		u.get(data.get("cm_server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();

		JSONObject obj = new JSONObject(data.get("cm_jobs"));
		ControlM control = new ControlM(u, testCase, obj);
		boolean flag = control.searchJob();
		assertTrue(flag, "No se encontro el job");
		String resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		System.out.println("Valor del output:" + control.getOutput());
		assertEquals(resultado, "Wait for Event");
		u.hardWait(3);
		control.closeViewpoint();
		u.close();

		/********** Paso 6 *********/

		addStep("Revisar la corrida de la interfaz en la tabla de WM_LOG_RUN");

		String QueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR35_MX'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		PreparedStatement LogQuery = dbLog.prepareStatement(QueryIntegrationServer);
		ResultSet LogResult = LogQuery.executeQuery();

		if (LogResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryIntegrationServer);
			while (LogResult.next()) {
				// RUN_ID,START_DT,STATUS
				run_status = LogResult.getString("RUN_ID");
				String START_DT = LogResult.getString("START_DT");
				String STATUS = LogResult.getString("STATUS");

				testCase.addTextEvidenceCurrentStep(
						"--> " + " RUN_ID : " + run_status + " START_DT : " + START_DT + " STATUS : " + STATUS);
			}
		} else {
			assertFalse(!LogResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}
		LogResult.close();
		LogQuery.close();

		/********** Paso 7 *********/

		addStep("Revisar los threads levantados en la tabla WM_LOG_THREADS");

		String QueryStatusThread = " SELECT PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_status + "AND STATUS = 'S'"
				+ "AND ATT2 = '50VID'";

		PreparedStatement LogThreadQuery = dbLog.prepareStatement(QueryStatusThread);
		ResultSet LogThreadResult = LogThreadQuery.executeQuery();

		if (LogThreadResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryStatusThread);
			while (LogThreadResult.next()) {
				// PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5
				String PARENT_ID = LogThreadResult.getString("PARENT_ID");
				String NAME = LogThreadResult.getString("NAME");
				String START_DT = LogThreadResult.getString("START_DT");
				String END_DT = LogThreadResult.getString("END_DT");
				String STATUS = LogThreadResult.getString("STATUS");
				String ATT1 = LogThreadResult.getString("ATT1");
				String ATT2 = LogThreadResult.getString("ATT2");
				String ATT5 = LogThreadResult.getString("ATT5");

				testCase.addTextEvidenceCurrentStep("--> " + " PARENT_ID : " + PARENT_ID + " NAME : " + NAME
						+ " START_DT : " + START_DT + " END_DT : " + END_DT + " STATUS : " + STATUS + " ATT1 : " + ATT1
						+ " ATT2 : " + ATT2 + " ATT5 : " + ATT5);
			}
		} else {
			assertFalse(!LogThreadResult.next(),
					"--> ¡ERROR!, No existe información relacionada a Threads de la ejecución");
		}
		LogThreadResult.close();
		LogThreadQuery.close();

		/********** Paso 8 *********/

		addStep("Validar que el dato haya llegado a la tabla POS_OUT_DOC_RMS");

		String QueryPos = "SELECT ID, DOC_NAME, DOC_TYPE, PV_CR_PLAZA, PV_CR_TIENDA, STATUS, SOURCE_ID, DATE_CREATED, SOURCEAPP\r\n"
				+ "FROM POS_OUT_DOC_RMS\r\n" + "WHERE DOC_TYPE = 'PCC'\r\n" + "AND SOURCE_ID = '" + run_status + "'\r\n"
				+ "AND PV_CR_TIENDA = '50VID'";

		PreparedStatement PosQuery3 = dbPos.prepareStatement(QueryPos);
		ResultSet PosResult3 = PosQuery3.executeQuery();

		if (PosResult3.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryPos);
			while (PosResult3.next()) {

				doc_name = PosResult3.getString("DOC_NAME");
				String ID = PosResult3.getString("ID");
				String DOC_NAME = PosResult3.getString("DOC_NAME");
				String DOC_TYPE = PosResult3.getString("DOC_TYPE");
				String PV_CR_PLAZA = PosResult3.getString("PV_CR_PLAZA");
				String PV_CR_TIENDA = PosResult3.getString("PV_CR_TIENDA");
				String STATUS = PosResult3.getString("STATUS");
				String SOURCE_ID = PosResult3.getString("SOURCE_ID");
				String DATE_CREATED = PosResult3.getString("DATE_CREATED");
				String SOURCEAPP = PosResult3.getString("SOURCEAPP");

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "DOC_NAME : " + DOC_NAME + " | "
						+ "DOC_TYPE : " + DOC_TYPE + " | " + "PV_CR_PLAZA : " + PV_CR_PLAZA + " | " + "PV_CR_TIENDA: "
						+ PV_CR_TIENDA + " | " + "STATUS : " + STATUS + " | " + "SOURCE_ID: " + SOURCE_ID + " | "
						+ "DATE_CREATED: " + DATE_CREATED + " | " + "SOURCEAPP: " + SOURCEAPP);

			}

		} else {
			assertFalse(!PosResult3.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult3.close();
		PosQuery3.close();

		/**** Paso 9 ***/
		addStep("Se valida el documento en Filezilla");

		FTPUtil ftp = new FTPUtil(data.get("server"), 21, data.get("usuario"), data.get("contra")); // host, puerto,
																									// usuario,
		// contrasena
		String ruta = "/u01/posuser/FEMSA_OXXO/POS/10MON/50VID/working/" + doc_name; // Variable con el nombre del
																						// documento/
		System.out.println(ruta);

		if (ftp.fileExists(ruta)) {
			System.out.println(ruta + " - Existe");
			testCase.addTextEvidenceCurrentStep("Se encontro archivo en la ruta: " + ruta);
		} else {
			System.out.println(ruta + " - No existe");
			testCase.addTextEvidenceCurrentStep("Error - no se encontró archivo: " + ruta);
		}

		assertTrue(ftp.fileExists(ruta), "No se obtiene el archivo por FTP.");

	}

	@Override
	public String setTestFullName() {

		return null;

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub

		return "Envio de recetas (PCC) hacia POS";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "GUSTAVO MORALES";
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
		return "1- ACCESO A LA BD DE NUCLEO\r\n" + "2- TENER ORDENADO LOS JOBS DE LA PR35_MX\r\n"
				+ "3- TENER POSTMAN\r\n" + "4- ACCESO A CONTROL M"
				+ "5- EJECUTAR EL PORTAL RMSDAS(CATALOGO DE RECETAS)";
	}

}
