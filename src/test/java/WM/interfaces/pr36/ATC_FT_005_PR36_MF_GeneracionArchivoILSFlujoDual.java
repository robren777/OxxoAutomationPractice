package WM.interfaces.pr36;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.interfaces.pr36.om.conexionesSQL;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.FTPUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/* NOMBRE DEL PROYECTO: Transformacion Multiformato
 * DESCRIPCION : Se genera un documento de tipo ILS.
 * 
 * MTC_FT_011_Generacion de ILS de plaza con las 2 carreteras activas (ambas arquitecturas)
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (ILS).
 * 	Tener Ordenado el job de la interfaz PR36
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos de tipo ILS.
 * 
 * @Author Dalia Espinoza
 * @Date 06/Diciembre/2024
 */
public class ATC_FT_005_PR36_MF_GeneracionArchivoILSFlujoDual extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_PR36_MF_GeneracionArchivoILSFlujoDual_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerías
		 *********************************************************************/
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		System.out.println(
				" --------------------------------CONEXION A BD----------------------------------------------- ");
		SQLUtil FCRMSQA = new SQLUtil(GlobalVariables.DB_HOST_RMS10_MEX_MULTIFORMATO,
				GlobalVariables.DB_USER_RMS10_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_RMS10_MEX_MULTIFORMATO);
		System.out.println(
				" --------------------------------FIN CONEXION A BD----------------------------------------------- ");
		/*
		 * Querys
		 *********************************************************************/
		System.out.println(
				" --------------------------------Inicio Querys RMS----------------------------------------------- ");
		String qryRegistrosProcesar = "select LOCATION, SUBSTR(store_name10,1,5) PLAZA from(SELECT DISTINCT TO_NUMBER(LOCATION) LOCATION\r\n"
				+ "FROM  (SELECT DISTINCT LOCATION\r\n" + "FROM repl_item_loc ril, item_loc_traits ilt, sups s\r\n"
				+ "WHERE source_wh IS NULL\r\n" + "AND ilt.item (+) = ril.item\r\n"
				+ "AND ilt.loc (+) = ril.LOCATION\r\n" + "AND ril.primary_repl_supplier = s.supplier\r\n"
				+ "AND ((ril.last_update_datetime > TO_DATE ('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') AND ril.last_update_datetime <= TO_DATE ('10/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM'))\r\n"
				+ "OR (ilt.last_update_datetime > TO_DATE ('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') AND ilt.last_update_datetime <= TO_DATE ('10/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')))\r\n"
				+ "AND ril.loc_type='S'\r\n" + "UNION ALL\r\n" + "SELECT DISTINCT LOCATION\r\n"
				+ "FROM repl_item_loc ril, item_loc_traits ilt, wh\r\n" + "WHERE ilt.item (+) = ril.item\r\n"
				+ "AND ilt.loc (+) = ril.LOCATION\r\n" + "AND wh = source_wh\r\n"
				+ "AND primary_repl_supplier IS NULL\r\n"
				+ "AND ((ril.last_update_datetime > TO_DATE ('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') AND ril.last_update_datetime <= TO_DATE ('10/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM'))\r\n"
				+ "OR (ilt.last_update_datetime > TO_DATE ('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') AND ilt.last_update_datetime <= TO_DATE ('10/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')))\r\n"
				+ "AND ril.loc_type='S'\r\n" + ")\r\n" + ") a, store b\r\n" + "where a.LOCATION=b.STORE\r\n"
				+ "and SUBSTR(store_name10,1,5) not in ('10VCZ')\r\n" + "order by PLAZA";

		String qryRegistrosProcesarLocation = "SELECT ROWNUM linenumber, LPAD (item, 16, ' ') item, primary_repl_supplier,\r\n"
				+ "DECODE (LENGTH (pct_tolerance),8, SUBSTR (pct_tolerance, 2, 8),pct_tolerance) pct_tolerance,loc_type,sup_name\r\n"
				+ "FROM (SELECT DISTINCT ril.item, primary_repl_supplier, pct_tolerance,\r\n"
				+ "'S' loc_type, dept, CLASS, subclass,\r\n" + "store_reorderable_ind, sup_name,\r\n"
				+ "SUBSTR (st.description, 1, 2) id_carrier,\r\n" + "SUBSTR (st.description, 4) alias_carrier,\r\n"
				+ "pec.tiempo_entrega, pec.periodo_revision,\r\n" + "pec.venta_unidades, pec.stock_pr,\r\n"
				+ "pec.fem_stock_prom, pec.fem_start, pec.fem_end, vp.venta_perdida\r\n" + "FROM repl_item_loc ril,\r\n"
				+ "item_loc_traits ilt,\r\n" + "sups s,\r\n" + "sup_traits st,\r\n" + "sup_traits_matrix stm,\r\n"
				+ "xxfc_parametros_envio_cent pec,\r\n" + "xxfc_venta_perdida_bi vp\r\n" + "WHERE source_wh IS NULL\r\n"
				+ "AND ilt.item(+) = ril.item\r\n" + "AND ilt.loc(+) = ril.LOCATION\r\n"
				+ "AND ril.LOCATION in (%s)\r\n" + "AND ril.primary_repl_supplier = s.supplier\r\n"
				+ "AND ril.primary_repl_supplier = stm.supplier(+)\r\n" + "AND stm.sup_trait = st.sup_trait(+)\r\n"
				+ "AND vp.item(+) = ril.item\r\n" + "AND vp.location(+) = ril.location\r\n"
				+ "AND vp.supplier(+) = ril.primary_repl_supplier\r\n"
				+ "AND (   ril.last_update_datetime > TO_DATE('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') OR ilt.last_update_datetime > TO_DATE ('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')\r\n"
				+ "OR pec.last_update_datetime > TO_DATE ('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM'))\r\n"
				+ "AND ril.primary_repl_supplier = pec.supplier(+)\r\n" + "AND pec.LOCATION(+) = ril.LOCATION\r\n"
				+ "AND pec.item(+) = ril.item\r\n" + "UNION ALL\r\n"
				+ "SELECT DISTINCT ril.item, source_wh primary_repl_supplier,\r\n"
				+ "pct_tolerance, 'W' loc_type, dept, CLASS, subclass,\r\n"
				+ "store_reorderable_ind, wh_name sup_name,\r\n" + "SUBSTR (st.description, 1, 2) id_carrier,\r\n"
				+ "SUBSTR (st.description, 4) alias_carrier,\r\n" + "pec.tiempo_entrega, pec.periodo_revision,\r\n"
				+ "pec.venta_unidades, pec.stock_pr,\r\n"
				+ "pec.fem_stock_prom, pec.fem_start, pec.fem_end, vp.venta_perdida\r\n" + "FROM repl_item_loc ril,\r\n"
				+ "item_loc_traits ilt,\r\n" + "wh,\r\n" + "sup_traits st,\r\n" + "sup_traits_matrix stm,\r\n"
				+ "xxfc_parametros_envio_cent pec,\r\n" + "xxfc_venta_perdida_bi vp\r\n"
				+ "WHERE ilt.item(+) = ril.item\r\n" + "AND ilt.loc(+) = ril.LOCATION\r\n" + "AND wh = source_wh\r\n"
				+ "AND primary_repl_supplier IS NULL\r\n" + "AND ril.LOCATION in(%s)\r\n"
				+ "AND ril.source_wh = stm.supplier(+)\r\n" + "AND stm.sup_trait = st.sup_trait(+)\r\n"
				+ "AND ril.primary_repl_supplier = pec.supplier(+)\r\n" + "AND pec.item(+) = ril.item\r\n"
				+ "AND pec.LOCATION(+) = ril.LOCATION\r\n" + "AND vp.item(+) = ril.item\r\n"
				+ "AND vp.location(+) = ril.location\r\n" + "AND vp.supplier(+) = ril.primary_repl_supplier\r\n"
				+ "AND (   ril.last_update_datetime > TO_DATE ('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') OR ilt.last_update_datetime >TO_DATE ('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')\r\n"
				+ "OR pec.last_update_datetime > TO_DATE ('05/10/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')))";

		String qryWmLogRun = "SELECT RUN_ID, INTERFACE, START_DT, END_DT, STATUS \r\n" + "FROM WMLOG.WM_LOG_RUN \r\n"
				+ "WHERE INTERFACE LIKE '%s' AND STATUS = 'S' \r\n"
				+ "AND START_DT >= TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')";

		String qryThreads = "SELECT THREAD_ID, PARENT_ID, NAME, START_DT, END_DT,STATUS \r\n"
				+ "FROM WMLOG.WM_LOG_THREAD \r\n" + "WHERE PARENT_ID = '%s' \r\n" + "AND status = 'S'";

		String qryError = "SELECT ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY, ERROR_TYPE \r\n"
				+ "FROM WMLOG.WM_LOG_ERROR \r\n" + "WHERE RUN_ID = '%s'";

		String qryOutboundDocs = "SELECT ID, DOC_NAME, DOC_TYPE, SENT_DATE, STATUS, SOURCE_ID \r\n"
				+ "FROM wmview.POS_OUTBOUND_DOCS \r\n" + "WHERE DOC_TYPE = 'ILS' \r\n" + "AND PV_CR_PLAZA = '"
				+ data.get("CR_PLAZA") + "'\r\n" + "AND SENT_DATE >= TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')";

		String qryOutDocRms = "SELECT ID, DOC_NAME, DOC_TYPE, DATE_CREATED, STATUS, SOURCE_ID \r\n"
				+ "FROM posuser.POS_OUT_DOC_RMS \r\n" + "WHERE DOC_TYPE = 'ILS' \r\n" + "AND PV_CR_PLAZA = '"
				+ data.get("CR_PLAZA") + "'\r\n" + "AND DATE_CREATED >= TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')";

		Date fechaEjecucionInicio;

		testCase.setProject_Name("Multiformato Transformación");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

		/****************************************************************************************************************************************
		 * Paso 1
		 **************************************************************************************************************************************/
		System.out.println(
				" --------------------------------Inicio Paso1----------------------------------------------- ");
		addStep("Establecer conexión a la BD FCRMSQA.");

		testCase.addTextEvidenceCurrentStep("Conexion: FCRMSQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_RMS_MEX);

		/****************************************************************************************************************************************
		 * Paso 2
		 **************************************************************************************************************************************/
		System.out.println(
				" --------------------------------Inicio Paso2----------------------------------------------- ");
		addStep("Validar que exista información pendiente de procesar.");

		System.out.println("qryRegistrosProcesar: \r\n " + qryRegistrosProcesar);
		SQLResult qryRegistrosProcesar_r = executeQuery(FCRMSQA, qryRegistrosProcesar);
		assertFalse(qryRegistrosProcesar_r.isEmpty());
		testCase.addQueryEvidenceCurrentStep(qryRegistrosProcesar_r, true);

		String locations = "";
		for (int i = 0; i < qryRegistrosProcesar_r.getRowCount(); i++) {
			locations += "'" + qryRegistrosProcesar_r.getData(i, "LOCATION") + "'";
			if (i < qryRegistrosProcesar_r.getRowCount() - 1) {
				locations += ",";
			}
		}
		System.out.println("Locations con registros a procesar: \r\n " + locations);
		/****************************************************************************************************************************************
		 * Paso 3
		 **************************************************************************************************************************************/
		System.out.println(
				" --------------------------------Inicio Paso3----------------------------------------------- ");
		addStep("Validar que haya información a procesar para el location configurado.");

		String qryRegistrosProcesarLocation_f = String.format(qryRegistrosProcesarLocation, locations, locations,
				locations);
		System.out.println("qryRegistrosProcesarLocation_f: \r\n " + qryRegistrosProcesarLocation_f);
		SQLResult qryRegistrosProcesarLocation_r = executeQuery(FCRMSQA, qryRegistrosProcesarLocation_f);
		assertFalse(qryRegistrosProcesarLocation_r.isEmpty());
		testCase.addQueryEvidenceCurrentStep(qryRegistrosProcesarLocation_r, true);
		System.out.println("Información a procesar: \r\n " + qryRegistrosProcesarLocation_r);
//Paso 4 Inicio control-m
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println(
				" --------------------------------Inicio Ctrl-M----------------------------------------------- ");
		fechaEjecucionInicio = new Date();
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		/***************************************
		 * PASO 2
		 ***************************************/
		addStep("Acceder a Control M ");
		Control_mInicio cm = new Control_mInicio(u, data.get("cm_user"), data.get("cm_ps"));
		u.get(data.get("cm_server"));
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();
		/***************************************
		 * PASO 3
		 ***************************************/
		addStep("Ejecucion del job PR36_OLD");

		JSONObject obj = new JSONObject(data.get("cm_jobs"));
		ControlM job = new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();
		assertTrue(flag);
		String resultado = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		System.out.println("Valor de output :" + job.getOutput());
		assertEquals(resultado, "Ended OK");
		u.hardWait(3);
		job.closeViewpoint();
		u.close();
		System.out
				.println(" --------------------------------Fin Ctrl-M----------------------------------------------- ");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//Fin control-m
		/****************************************************************************************************************************************
		 * Paso 5
		 **************************************************************************************************************************************/
		addStep("Establecer la conexión a la FCWML6QA");

		testCase.addTextEvidenceCurrentStep("Conexion: FCWMLQA_WMLOG");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA_WMLOG);

		/****************************************************************************************************************************************
		 * Paso 6
		 **************************************************************************************************************************************/
		addStep("Validar que se inserte el detalle de la ejecución de la interface en la tabla WM_LOG_RUN de la DB  FCWML6QA");

		String fechaEjecucionInicio_f = formatter.format(fechaEjecucionInicio);

		String qryWmLogRun_f = String.format(qryWmLogRun, "%PR36%", fechaEjecucionInicio_f);
		System.out.println("qryWmLogRun_f: \r\n " + qryWmLogRun_f);
		SQLResult qryWmLogRun_r = conexionesSQL.sqlExecute(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO_QA,
				qryWmLogRun_f, GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO_QA);
		assertFalse(qryWmLogRun_r.isEmpty());
		testCase.addQueryEvidenceCurrentStep(qryWmLogRun_r);
		String runId = qryWmLogRun_r.getData(0, "RUN_ID");
		System.out.println("WM_LOG_RUN:" + runId);

		/****************************************************************************************************************************************
		 * Paso 7
		 **************************************************************************************************************************************/
		addStep("Validar que se inserte el detalle de la ejecución de los Threads lanzados por la interface en la tabla WM_LOG_THREAD de WMLOG");

		String qryThreads_f = String.format(qryThreads, runId);
		System.out.println("qryThreads_f: \r\n " + qryThreads_f);
		SQLResult qryThreads_r = conexionesSQL.sqlExecute(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO_QA, qryThreads_f,
				GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO_QA);
		assertFalse(qryThreads_r.isEmpty());
		testCase.addQueryEvidenceCurrentStep(qryThreads_r);
		System.out.println("WM_LOG_THREAD: \r\n " + qryThreads_r);
		String threadStatus = qryWmLogRun_r.getData(0, "STATUS");
		System.out.println("STATUS:" + threadStatus);
		/****************************************************************************************************************************************
		 * Paso 8
		 **************************************************************************************************************************************/
		addStep("Realizar la siguiente consulta para verificar que no se encuentre ningún error presente en la ejecución de la interfaz  dentro de la tabla WM_LOG_ERROR");

		String qryError_f = String.format(qryError, runId);
		System.out.println("qryError_f: \r\n " + qryError_f);
		SQLResult qryError_r = conexionesSQL.sqlExecute(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO_QA, qryError_f,
				GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO_QA);
		assertTrue(qryError_r.isEmpty());
		testCase.addQueryEvidenceCurrentStep(qryError_r);
		System.out.println("WM_LOG_ERROR: \r\n " + qryError_r);
		/****************************************************************************************************************************************
		 * Paso 9
		 **************************************************************************************************************************************/
		addStep("Establecer la conexión con la BD **FCWM6QA**.");

		testCase.addTextEvidenceCurrentStep("Conexion: FCWM6QA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_WM_MEX_MULTIFORMATO_QA);

		/****************************************************************************************************************************************
		 * Paso 10
		 **************************************************************************************************************************************/
		addStep("Validar el registro del documento enviado en la tabla pos_outbound_docs de FCWM6QA.");

		String fechaEjecucionInicio_F = formatter.format(fechaEjecucionInicio);

		String qryOutboundDocs_f = String.format(qryOutboundDocs, fechaEjecucionInicio_F);
		System.out.println("qryOutboundDocs_f: \r\n " + qryOutboundDocs_f);
		SQLResult qryOutboundDocs_r = conexionesSQL.sqlExecute(GlobalVariables.DB_HOST_WM_MEX_MULTIFORMATO_QA,
				qryOutboundDocs_f, GlobalVariables.DB_USER_WM_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WM_MEX_MULTIFORMATO_QA);
		assertFalse(qryOutboundDocs_r.isEmpty());
		testCase.addQueryEvidenceCurrentStep(qryOutboundDocs_r);
		String wmDocNameILS = qryOutboundDocs_r.getData(0, "DOC_NAME");
		System.out.println("Documento ILS:" + wmDocNameILS);

		addStep("Validar el registro del documento enviado en la tabla pos_out_doc_rms de FCWM6QA.");

		String fechaEjecucionInicio_F2 = formatter.format(fechaEjecucionInicio);

		String qryOutDocRms_f = String.format(qryOutDocRms, fechaEjecucionInicio_F2);
		System.out.println("qryOutDocRms_f: \r\n " + qryOutDocRms_f);
		SQLResult qryOutDocRms_r = conexionesSQL.sqlExecute(GlobalVariables.DB_HOST_WM_MEX_MULTIFORMATO_QA,
				qryOutDocRms_f, GlobalVariables.DB_USER_WM_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WM_MEX_MULTIFORMATO_QA);
		assertFalse(qryOutDocRms_r.isEmpty());
		testCase.addQueryEvidenceCurrentStep(qryOutDocRms_r);
		String wmDocNameILSJSON = qryOutDocRms_r.getData(0, "DOC_NAME");
		System.out.println("Documento ILS:" + wmDocNameILS);
		/****************************************************************************************************************************************
		 * Paso 11
		 **************************************************************************************************************************************/
		addStep("Establecer la conexión con el servidor FTP de Buzones de tienda.");

		testCase.addTextEvidenceCurrentStep("FTP_HOST: " + data.get("FTP_HOST") + "\nFTP_PORT: " + data.get("FTP_PORT")
				+ "\nFTP_USER: " + data.get("FTP_USER"));

		FTPUtil ftp = new FTPUtil(data.get("FTP_HOST"), Integer.parseInt(data.get("FTP_PORT")), data.get("FTP_USER"),
				data.get("FTP_PASSWORD"));

		/****************************************************************************************************************************************
		 * Paso 12
		 **************************************************************************************************************************************/
		addStep("Validar que se realice el envío del archivo ILS generado por la interface en el directorio configurado para la tienda procesada.");

		// Archivo XML
		String path = "/u01/posuser/FEMSA_OXXO/POS/" + data.get("CR_PLAZA") + "/" + data.get("CR_TIENDA") + "/working"
				+ "/" + wmDocNameILS;

		if (ftp.fileExists(path)) {
			System.out.println(path + " - Existe");
			testCase.addTextEvidenceCurrentStep("Se encontro archivo en la ruta: " + path);
		} else {
			System.out.println(path + " - No xiste");
			testCase.addTextEvidenceCurrentStep("Error - no se encontró archivo: " + path);
		}

		assertTrue(ftp.fileExists(path), "No se obtiene el archivo por FTP.");
		// Archivo JSON
		String path2 = "/u01/posuser/FEMSA_OXXO/POS/" + data.get("CR_PLAZA") + "/" + data.get("CR_TIENDA") + "/working"
				+ "/" + wmDocNameILSJSON;

		if (ftp.fileExists(path2)) {
			System.out.println(path2 + " - Existe");
			testCase.addTextEvidenceCurrentStep("Se encontro archivo en la ruta: " + path2);
		} else {
			System.out.println(path2 + " - No xiste");
			testCase.addTextEvidenceCurrentStep("Error - no se encontró archivo: " + path2);
		}

		assertTrue(ftp.fileExists(path2), "No se obtiene el archivo por FTP.");

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Prueba para comprobar la funcionalidad principal de la interface FEMSA_PR36 para generar archivos ILS en formato XML y JSON";
	}

	@Override
	public String setTestDesigner() {
		return "Multiformato Transformación-OptaSmart QA";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return " *Contar con acceso a las bases de datos de FCWM6QA, FCWML6QA y de FCRMSQA.\r\n"
				+ "	*Contar con el nombre y grupo del nuevo Job de Control M para PR36 de WM10.11\r\n"
				+ "	*Contar con acceso a repositorio de buzón de la tienda.\r\n"
				+ "	*Contar con las credenciales de WM10.11 de los nuevos servers del Integration Server de QA de Back Office.\r\n";
	}
}