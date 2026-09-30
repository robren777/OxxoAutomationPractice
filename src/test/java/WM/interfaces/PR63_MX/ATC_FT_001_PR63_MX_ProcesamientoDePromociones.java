package WM.interfaces.PR63_MX;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.FTPUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/* DESCRIPCION : Validar el envio del Archivo PRM.
 * 
 * -MTC_FT_PR63_MX_001 Ejecución exitosa de interface ,Procesamiento de Promocion Simple para tienda migrada bandera "Y",
 * -MTC_FT_PR63_MX_002 Ejecución exitosa de interface ,Procesamiento de Promocion Simple para tienda migrada bandera "N",
 * MTC_FT_PR63_MX_003 Ejecución exitosa de interface ,Procesamiento de Promocion Simple para tienda migrada bandera "D"
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (PRM).
 * 	Tener Ordenado el job de la interfaz PR63_MX, PR63_MX_OLD
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 	Hacer un update a la tabla MIGRATED_FLAG_OUT para cambiar la bandera a Y, N o D, dependiendo del camino a probar.
 * 
 * INSUMOS:
 * 	Documentos con promociones disponibles.
 * 
 * @Author Rafael Alarcón
 * @Date 27/Diciembre/2024
 */
public class ATC_FT_001_PR63_MX_ProcesamientoDePromociones extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_PR63_MX_ProcesamientoDePromociones_test(HashMap<String, String> data) throws Exception {

		/******************
		 * Datos de conexion
		 *********************************************************************/

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		/* POSUSER */
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WM_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER-------------------------------------------------- ");
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		/* WMLOG */
		Connection dbWmlog = DriverManager.getConnection(GlobalVariables.DB_HOST_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WMLOG_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG-------------------------------------------------- ");
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A DAS PERF----------------------------------------------- ");
		/* DAS */
		Connection dbDas = DriverManager.getConnection(GlobalVariables.DB_HOST_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_DASPERF_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A DAS PERF----------------------------------------------- ");
		testCase.setProject_Name("Transformación Multiformato");

		/****************************
		 * Variables de datos
		 *******************************/

		String batch_id = "";

		String migratedFlag = "";

		String run_id = "";

		String doc_name = "";

		String new_doc = "";

		String doc_json = "";

		String doc_json2 = "";

		/**************************** Pasos de la prueba ******************************/

		/********** Paso 1 *********/

		addStep("Validar la existencia de informacion de promociones , pendiente por procesar en la tablas de Base de Datos");

		String tdcQueryPrmHead = "SELECT ARCHIVO, LOAD_BATCH_ID, WM_PRM_STATUS FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = 'L'";

		PreparedStatement PRMQuery = dbDas.prepareStatement(tdcQueryPrmHead);
		ResultSet PRMResult = PRMQuery.executeQuery();

		if (PRMResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(tdcQueryPrmHead);
			while (PRMResult.next()) {
				batch_id += PRMResult.getString("LOAD_BATCH_ID") + ",";// Se concatenan los ID para proximas
																		// consultas/filtros.

				String archivo = PRMResult.getString("ARCHIVO");
				String loadBatchId = PRMResult.getString("LOAD_BATCH_ID");
				String wmPrmStatus = PRMResult.getString("WM_PRM_STATUS");

				testCase.addTextEvidenceCurrentStep("--> " + "ARCHIVO : " + archivo + " | " + "LOAD_BATCH_ID : "
						+ loadBatchId + " | " + "WM_PRM_STATUS : " + wmPrmStatus);
			}
			batch_id = batch_id.substring(0, batch_id.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PRMResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 2 *********/

		addStep("Validar la bandera y ejecutar interface mediante la ejecucion de un JOB en Control M");

		try {

			String tdcQueryMigratedFlag = "SELECT MIGRATED_FLAG_OUT FROM POSUSER.POS_RULES_DOC_CFG WHERE DOC_TYPE = 'PRM' AND PV_CR_PLAZA = '"
					+ data.get("plaza") + "'";

			testCase.addTextEvidenceCurrentStep(tdcQueryMigratedFlag);

			PreparedStatement FlagQuery = dbPos.prepareStatement(tdcQueryMigratedFlag);
			ResultSet FlagConfig = FlagQuery.executeQuery();

			if (FlagConfig.next()) {

				migratedFlag = FlagConfig.getString("MIGRATED_FLAG_OUT");

				testCase.addTextEvidenceCurrentStep("--> Bandera encontrada : " + migratedFlag);

				switch (migratedFlag) {

				case "Y":

					addStep("Ejecutar JOB runPR63_MX");

					SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);

					Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
					u.get(data.get("server"));
					u.hardWait(30);
					u.waitForLoadPage();
					cm.logOn();
					JSONObject obj = new JSONObject(data.get("job1"));
					ControlM job = new ControlM(u, testCase, obj);
					boolean flag = job.searchJob();
					assertTrue(flag, "Bandera si encontro el job");
					String resultado = job.executeJob();
					System.out.println("Resultado de la ejecucion -> " + resultado);
					System.out.println("Valor de output : " + job.getOutput());
					assertEquals(resultado, "Ended OK");
					u.hardWait(3);
					job.closeViewpoint();
					u.close();

					/********** Paso 3 *********/

					addStep("Revisar la corrida de la interfaz en la tabla de WM_LOG_RUN");

					String QueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS"
							+ " FROM WMLOG.WM_LOG_RUN" + " WHERE INTERFACE = 'PR63_MX'"
							+ " AND  START_DT >= TRUNC(SYSDATE)" + " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

					PreparedStatement LogQuery = dbWmlog.prepareStatement(QueryIntegrationServer);
					ResultSet LogResult = LogQuery.executeQuery();

					if (LogResult.isBeforeFirst()) {
						testCase.addTextEvidenceCurrentStep(QueryIntegrationServer);
						while (LogResult.next()) {
							// RUN_ID,START_DT,STATUS
							run_id = LogResult.getString("RUN_ID");
							String START_DT = LogResult.getString("START_DT");
							String STATUS = LogResult.getString("STATUS");

							testCase.addTextEvidenceCurrentStep(
									"--> " + " RUN_ID : " + run_id + " START_DT : " + START_DT + " STATUS : " + STATUS);
						}
					} else {
						assertFalse(!LogResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
					}
					LogResult.close();
					LogQuery.close();

					/********** Paso 4 *********/

					addStep("Revisar los threads levantados en la tabla WM_LOG_THREADS");

					String QueryStatusThread = " SELECT PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5"
							+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_id;

					PreparedStatement LogThreadQuery = dbWmlog.prepareStatement(QueryStatusThread);
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
									+ " START_DT : " + START_DT + " END_DT : " + END_DT + " STATUS : " + STATUS
									+ " ATT1 : " + ATT1 + " ATT2 : " + ATT2 + " ATT5 : " + ATT5);
						}
					} else {
						assertFalse(!LogThreadResult.next(),
								"--> ¡ERROR!, No existe información relacionada a Threads de la ejecución");
					}
					LogThreadResult.close();
					LogThreadQuery.close();

					break;

				case "N":

					addStep("Ejecutar JOB runPR63_MX_OLD");

					SeleniumUtil u2 = new SeleniumUtil(new MEdgeTest(), true);

					Control_mInicio cm2 = new Control_mInicio(u2, data.get("user"), data.get("ps"));
					u2.get(data.get("server"));
					u2.hardWait(30);
					u2.waitForLoadPage();
					cm2.logOn();
					JSONObject obj2 = new JSONObject(data.get("job2"));
					ControlM job2 = new ControlM(u2, testCase, obj2);
					boolean flag2 = job2.searchJob();
					assertTrue(flag2, "Bandera si encontro el job");
					String resultado2 = job2.executeJob();
					System.out.println("Resultado de la ejecucion -> " + resultado2);
					System.out.println("Valor de output : " + job2.getOutput());
					assertEquals(resultado2, "Ended OK");
					u2.hardWait(3);
					job2.closeViewpoint();
					u2.close();

					/********** Paso 3 *********/

					addStep("Revisar la corrida de la interfaz en la tabla de WM_LOG_RUN");

					String QueryIntegrationServer2 = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS"
							+ " FROM WMLOG.WM_LOG_RUN" + " WHERE INTERFACE = 'PR63_MX_OLD'"
							+ " AND  START_DT >= TRUNC(SYSDATE)" + " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

					PreparedStatement LogQuery2 = dbWmlog.prepareStatement(QueryIntegrationServer2);
					ResultSet LogResult2 = LogQuery2.executeQuery();

					if (LogResult2.isBeforeFirst()) {
						testCase.addTextEvidenceCurrentStep(QueryIntegrationServer2);
						while (LogResult2.next()) {
							// RUN_ID,START_DT,STATUS
							run_id = LogResult2.getString("RUN_ID");
							String START_DT = LogResult2.getString("START_DT");
							String STATUS = LogResult2.getString("STATUS");

							testCase.addTextEvidenceCurrentStep(
									"--> " + " RUN_ID : " + run_id + " START_DT : " + START_DT + " STATUS : " + STATUS);
						}
					} else {
						assertFalse(!LogResult2.next(), "--> ¡ERROR!, No existe información relacionada al documento");
					}
					LogResult2.close();
					LogQuery2.close();

					/********** Paso 4 *********/

					addStep("Revisar los threads levantados en la tabla WM_LOG_THREADS");

					String QueryStatusThread2 = " SELECT PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5"
							+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_id;

					PreparedStatement LogThreadQuery2 = dbWmlog.prepareStatement(QueryStatusThread2);
					ResultSet LogThreadResult2 = LogThreadQuery2.executeQuery();

					if (LogThreadResult2.isBeforeFirst()) {
						testCase.addTextEvidenceCurrentStep(QueryStatusThread2);
						while (LogThreadResult2.next()) {
							// PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5
							String PARENT_ID = LogThreadResult2.getString("PARENT_ID");
							String NAME = LogThreadResult2.getString("NAME");
							String START_DT = LogThreadResult2.getString("START_DT");
							String END_DT = LogThreadResult2.getString("END_DT");
							String STATUS = LogThreadResult2.getString("STATUS");
							String ATT1 = LogThreadResult2.getString("ATT1");
							String ATT2 = LogThreadResult2.getString("ATT2");
							String ATT5 = LogThreadResult2.getString("ATT5");

							testCase.addTextEvidenceCurrentStep("--> " + " PARENT_ID : " + PARENT_ID + " NAME : " + NAME
									+ " START_DT : " + START_DT + " END_DT : " + END_DT + " STATUS : " + STATUS
									+ " ATT1 : " + ATT1 + " ATT2 : " + ATT2 + " ATT5 : " + ATT5);
						}
					} else {
						assertFalse(!LogThreadResult2.next(),
								"--> ¡ERROR!, No existe información relacionada a Threads de la ejecución");
					}
					LogThreadResult2.close();
					LogThreadQuery2.close();

					break;

				case "D":

					addStep("Ejecutar JOB runPR63_MX/runPR63_MX_OLD");

					SeleniumUtil u3 = new SeleniumUtil(new MEdgeTest(), true);

					Control_mInicio cm3 = new Control_mInicio(u3, data.get("user"), data.get("ps"));
					u3.get(data.get("server"));
					u3.hardWait(30);
					u3.waitForLoadPage();
					cm3.logOn();
					JSONObject obj3 = new JSONObject(data.get("job3"));
					ControlM job3 = new ControlM(u3, testCase, obj3);
					boolean flag3 = job3.searchJob();
					assertTrue(flag3, "Bandera si encontro el job");
					String resultado3 = job3.executeJob();
					System.out.println("Resultado de la ejecucion -> " + resultado3);
					System.out.println("Valor de output : " + job3.getOutput());
					assertEquals(resultado3, "Ended OK");
					u3.hardWait(3);
					job3.closeViewpoint();
					u3.close();

					/********** Paso 3 *********/

					String QueryIntegrationServer4 = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS"
							+ " FROM WMLOG.WM_LOG_RUN" + " WHERE INTERFACE = 'PR63_MX_OLD'"
							+ " AND  START_DT >= TRUNC(SYSDATE)" + " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

					PreparedStatement LogQuery4 = dbWmlog.prepareStatement(QueryIntegrationServer4);
					ResultSet LogResult4 = LogQuery4.executeQuery();

					if (LogResult4.isBeforeFirst()) {
						testCase.addTextEvidenceCurrentStep(QueryIntegrationServer4);
						while (LogResult4.next()) {
							// RUN_ID,START_DT,STATUS
							run_id = LogResult4.getString("RUN_ID");
							String START_DT = LogResult4.getString("START_DT");
							String STATUS = LogResult4.getString("STATUS");

							testCase.addTextEvidenceCurrentStep(
									"--> " + " RUN_ID : " + run_id + " START_DT : " + START_DT + " STATUS : " + STATUS);
						}
					} else {
						assertFalse(!LogResult4.next(), "--> ¡ERROR!, No existe información relacionada al documento");
					}
					LogResult4.close();
					LogResult4.close();

					/********** Paso 4 *********/

					addStep("Revisar los threads levantados en la tabla WM_LOG_THREADS");

					String QueryStatusThread3 = " SELECT PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5"
							+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_id;

					PreparedStatement LogThreadQuery3 = dbWmlog.prepareStatement(QueryStatusThread3);
					ResultSet LogThreadResult3 = LogThreadQuery3.executeQuery();

					if (LogThreadResult3.isBeforeFirst()) {
						testCase.addTextEvidenceCurrentStep(QueryStatusThread3);
						while (LogThreadResult3.next()) {
							// PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5
							String PARENT_ID = LogThreadResult3.getString("PARENT_ID");
							String NAME = LogThreadResult3.getString("NAME");
							String START_DT = LogThreadResult3.getString("START_DT");
							String END_DT = LogThreadResult3.getString("END_DT");
							String STATUS = LogThreadResult3.getString("STATUS");
							String ATT1 = LogThreadResult3.getString("ATT1");
							String ATT2 = LogThreadResult3.getString("ATT2");
							String ATT5 = LogThreadResult3.getString("ATT5");

							testCase.addTextEvidenceCurrentStep("--> " + " PARENT_ID : " + PARENT_ID + " NAME : " + NAME
									+ " START_DT : " + START_DT + " END_DT : " + END_DT + " STATUS : " + STATUS
									+ " ATT1 : " + ATT1 + " ATT2 : " + ATT2 + " ATT5 : " + ATT5);
						}
					} else {
						assertFalse(!LogThreadResult3.next(),
								"--> ¡ERROR!, No existe información relacionada a Threads de la ejecución");
					}
					LogThreadResult3.close();
					LogThreadResult3.close();

					break;

				default:
					assertFalse(!FlagConfig.next(),
							"No se encontró información en la consulta para la tabla POS_RULES_DOC_CFG");
				}

			} else {
				assertFalse(!FlagConfig.next(),
						"-> ERROR!, No se ha encontrado un bandera válida en la tabla de configuraciones, revisar la información");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/********** Paso 5 *********/

		addStep("Validar que se actualicen los datos en la tabla: POS_PRM_HEAD. WM_PRC_STATUS = 'E', WM_TARGET_PRC = [DOC_NAME]");

		String ObtenerDoc = "SELECT LOAD_BATCH_ID, WM_TARGET_PRM, WM_PRM_STATUS FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = 'E' AND LOAD_BATCH_ID IN ("
				+ batch_id + ")";

		PreparedStatement DocPrmQuery = dbDas.prepareStatement(ObtenerDoc);
		ResultSet DocPrmResult = DocPrmQuery.executeQuery();

		if (DocPrmResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(ObtenerDoc);
			while (DocPrmResult.next()) {
				doc_name += "'" + DocPrmResult.getString("WM_TARGET_PRM") + "',";// Se concatenan los nombres del docs

				String archivo = DocPrmResult.getString("WM_TARGET_PRM");
				String loadBatchId = DocPrmResult.getString("LOAD_BATCH_ID");
				String wmPrmStatus = DocPrmResult.getString("WM_PRM_STATUS");

				testCase.addTextEvidenceCurrentStep("--> " + "WM_TARGET_PRM : " + archivo + " | " + "LOAD_BATCH_ID : "
						+ loadBatchId + " | " + "WM_PRM_STATUS : " + wmPrmStatus);
			}
			doc_name = doc_name.substring(0, doc_name.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!DocPrmResult.next(),
					"-> ERROR!, La información del documento no está actualiza, revisar de nuevo");
		}

		/********** Paso 6 *********/

		addStep("Validar que se genere el documento PRM en el Buzón correspondiente a la tienda.");

		if (migratedFlag != null) {
			switch (migratedFlag) {

			case "Y":

				new_doc = doc_name.replace("'", "");

				System.out.println(new_doc);

				FTPUtil ftp = new FTPUtil("" + data.get("ftpHost") + "", 21, "" + data.get("ftpUser") + "",
						"" + data.get("ftpPass") + ""); // host, puerto, usuario, contrasena
				String ruta = "u01/posuser/FEMSA_OXXO/POS/" + data.get("plaza") + "/" + data.get("tienda") + "/working/"
						+ new_doc; /* Variable con el nombre del documento */

				if (ftp.fileExists(ruta)) {
					testCase.addFileEvidenceCurrentStep(ruta);
					testCase.addTextEvidenceCurrentStep(
							"Se encontro el documento " + new_doc + " pendiente por procesar en el repositorio FTP");
				} else {
					assertFalse(!ftp.fileExists(ruta),
							"-> ERROR!, No se encontró el documento " + new_doc + " en el buzón FTP");
				}
				break;

			case "N":

				new_doc = doc_name.replace("'", "");

				System.out.println(new_doc);

				FTPUtil ftp2 = new FTPUtil("" + data.get("ftpHost") + "", 21, "" + data.get("ftpUser") + "",
						"" + data.get("ftpPass") + ""); // host, puerto, usuario, contrasena
				String ruta2 = "u01/posuser/FEMSA_OXXO/POS/" + data.get("plaza") + "/" + data.get("tienda")
						+ "/working/" + new_doc; /* Variable con el nombre del documento */

				if (ftp2.fileExists(ruta2)) {
					testCase.addFileEvidenceCurrentStep(ruta2);
					testCase.addTextEvidenceCurrentStep(
							"Se encontro el documento " + new_doc + " pendiente por procesar en el repositorio FTP");
				} else {
					assertFalse(!ftp2.fileExists(ruta2),
							"-> ERROR!, No se encontró el documento " + new_doc + " en el buzón FTP");
				}
				break;

			case "D":

				new_doc = doc_name.replace("'", "");

				System.out.println(new_doc);

				FTPUtil ftp3 = new FTPUtil("" + data.get("ftpHost") + "", 21, "" + data.get("ftpUser") + "",
						"" + data.get("ftpPass") + ""); // host, puerto, usuario, contrasena
				// Doc XML
				String ruta_xml = "u01/posuser/FEMSA_OXXO/POS/" + data.get("plaza") + "/" + data.get("tienda")
						+ "/working/" + new_doc; /* Variable con el nombre del documento */

				if (ftp3.fileExists(ruta_xml)) {
					testCase.addTextEvidenceCurrentStep("-> Doc .xml : /n" + ruta_xml);
					testCase.addTextEvidenceCurrentStep(
							"Se encontro el documento " + new_doc + " pendiente por procesar en el repositorio FTP");
				} else {
					assertFalse(!ftp3.fileExists(ruta_xml),
							"-> ERROR!, No se encontró el documento " + doc_name + " en el buzón FTP");
				}

				// Doc JSON

				doc_json = new_doc.replace(".xml", ".json.gz");

				String ruta_json = "u01/posuser/FEMSA_OXXO/POS/" + data.get("plaza") + "/" + data.get("tienda")
						+ "/working/" + doc_json; /* Variable con el nombre del documento */

				if (ftp3.fileExists(ruta_json)) {
					testCase.addTextEvidenceCurrentStep("-> Doc .json : /n" + ruta_json);
					testCase.addTextEvidenceCurrentStep(
							"Se encontro el documento " + doc_json + " pendiente por procesar en el repositorio FTP");
				} else {
					assertFalse(!ftp3.fileExists(ruta_json),
							"-> ERROR!, No se encontro el documento" + doc_json + "en el buzón FTP");
				}

				break;
			}
		}

		/********** Paso 7 *********/

		addStep("Validar que se inserten  los datos del documento en la tabla: POS_OUTBOUND _DOCS de la BD de WM (Central). STATUS =  'L', SOURCE_ID = [BATCH_ID], DOC_TYPE = 'PRM', DOC_NAME = [DOC_NAME.json.gz/.xml]");

		// Doc JSON
		String filename = doc_name;
		doc_json2 = filename.replace(".xml", ".json.gz");

		if (migratedFlag != null) {
			switch (migratedFlag) {

			case "Y":

				String tdcQueryDocPRM = "SELECT ID, STATUS, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA, DATE_CREATED FROM POSUSER.POS_OUT_DOC_RMS WHERE DOC_TYPE = 'PRM' AND DOC_NAME = "
						+ doc_json2;

				System.out.println(tdcQueryDocPRM);

				PreparedStatement PosQuery = dbPos.prepareStatement(tdcQueryDocPRM);
				ResultSet PosResult = PosQuery.executeQuery();

				if (PosResult.isBeforeFirst()) {
					testCase.addTextEvidenceCurrentStep(tdcQueryDocPRM);
					while (PosResult.next()) {
						// ID, POE_ID, STATUS, TARGET_TYPE, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA,
						// DATE_CREATED
						String ID = PosResult.getString("ID");
						String STATUS = PosResult.getString("STATUS");
						String DOC_NAME = PosResult.getString("DOC_NAME");
						String PV_CR_PLAZA = PosResult.getString("PV_CR_PLAZA");
						String PV_CR_TIENDA = PosResult.getString("PV_CR_TIENDA");
						String DATE_CREATED = PosResult.getString("DATE_CREATED");

						testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " STATUS : " + STATUS
								+ " DOC_NAME : " + DOC_NAME + " PV_CR_PLAZA : " + PV_CR_PLAZA + " PV_CR_TIENDA : "
								+ PV_CR_TIENDA + " DATE_CREATED : " + DATE_CREATED);
					}
				} else {
					assertFalse(!PosResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
				}
				break;

			case "N":

				String tdcQueryDocPRM2 = "SELECT ID, STATUS, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA, DATE_CREATED FROM POSUSER.POS_OUTBOUND_DOCS WHERE DOC_TYPE = 'PRM' AND DOC_NAME = "
						+ doc_name;

				System.out.println(tdcQueryDocPRM2);

				PreparedStatement PosQuery2 = dbPos.prepareStatement(tdcQueryDocPRM2);
				ResultSet PosResult2 = PosQuery2.executeQuery();

				if (PosResult2.isBeforeFirst()) {
					testCase.addTextEvidenceCurrentStep(tdcQueryDocPRM2);
					while (PosResult2.next()) {
						// ID, POE_ID, STATUS, TARGET_TYPE, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA,
						// DATE_CREATED
						String ID = PosResult2.getString("ID");
						String STATUS = PosResult2.getString("STATUS");
						String DOC_NAME = PosResult2.getString("DOC_NAME");
						String PV_CR_PLAZA = PosResult2.getString("PV_CR_PLAZA");
						String PV_CR_TIENDA = PosResult2.getString("PV_CR_TIENDA");
						String DATE_CREATED = PosResult2.getString("DATE_CREATED");

						testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " STATUS : " + STATUS
								+ " DOC_NAME : " + DOC_NAME + " PV_CR_PLAZA : " + PV_CR_PLAZA + " PV_CR_TIENDA : "
								+ PV_CR_TIENDA + " DATE_CREATED : " + DATE_CREATED);
					}
				} else {
					assertFalse(!PosResult2.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
				}
				break;

			case "D":

				String tdcQueryDocPRMD = "SELECT ID, STATUS, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA, DATE_CREATED FROM POSUSER.POS_OUT_DOC_RMS WHERE DOC_TYPE = 'PRM' "
						+ "AND DOC_NAME = " + doc_json2;

				System.out.println(tdcQueryDocPRMD);

				PreparedStatement PosQueryD = dbPos.prepareStatement(tdcQueryDocPRMD);
				ResultSet PosResultD = PosQueryD.executeQuery();

				if (PosResultD.isBeforeFirst()) {
					testCase.addTextEvidenceCurrentStep(tdcQueryDocPRMD);
					while (PosResultD.next()) {
						// ID, POE_ID, STATUS, TARGET_TYPE, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA,
						// DATE_CREATED
						String ID = PosResultD.getString("ID");
						String STATUS = PosResultD.getString("STATUS");
						String DOC_NAME = PosResultD.getString("DOC_NAME");
						String PV_CR_PLAZA = PosResultD.getString("PV_CR_PLAZA");
						String PV_CR_TIENDA = PosResultD.getString("PV_CR_TIENDA");
						String DATE_CREATED = PosResultD.getString("DATE_CREATED");

						testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " STATUS : " + STATUS
								+ " DOC_NAME : " + DOC_NAME + " PV_CR_PLAZA : " + PV_CR_PLAZA + " PV_CR_TIENDA : "
								+ PV_CR_TIENDA + " DATE_CREATED : " + DATE_CREATED);
					}
				} else {
					assertFalse(!PosResultD.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
				}

				String tdcQueryDocPRM2D = "SELECT ID, STATUS, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA, DATE_CREATED FROM POSUSER.POS_OUTBOUND_DOCS WHERE DOC_TYPE = 'PRM' AND DOC_NAME = "
						+ doc_name;

				PreparedStatement PosQuery2D = dbPos.prepareStatement(tdcQueryDocPRM2D);
				ResultSet PosResult2D = PosQuery2D.executeQuery();

				if (PosResult2D.isBeforeFirst()) {
					testCase.addTextEvidenceCurrentStep(tdcQueryDocPRM2D);
					while (PosResult2D.next()) {
						// ID, POE_ID, STATUS, TARGET_TYPE, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA,
						// DATE_CREATED
						String ID = PosResult2D.getString("ID");
						String STATUS = PosResult2D.getString("STATUS");
						String DOC_NAME = PosResult2D.getString("DOC_NAME");
						String PV_CR_PLAZA = PosResult2D.getString("PV_CR_PLAZA");
						String PV_CR_TIENDA = PosResult2D.getString("PV_CR_TIENDA");
						String DATE_CREATED = PosResult2D.getString("DATE_CREATED");

						testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " STATUS : " + STATUS
								+ " DOC_NAME : " + DOC_NAME + " PV_CR_PLAZA : " + PV_CR_PLAZA + " PV_CR_TIENDA : "
								+ PV_CR_TIENDA + " DATE_CREATED : " + DATE_CREATED);
					}
				} else {
					assertFalse(!PosResult2D.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
				}
				break;
			}
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Validar el envio del Archivo PRM";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Rafael Alarcon";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con acceso a la BD de RMS10, WMLOG y POSUSER, \n" + ""
				+ "2. Contar con documentos PRM listos para procesar, \n" + ""
				+ "3. Contar con acceso al repositorio Filezilla, \n" + ""
				+ "4. Validar que la Plaza se encuentren migrada en la tabla POS_RULES_DOC_CFG con un valor de Y,N,D en el campo MIGRATED_FLAG_OUT.";
	}

}
