package WM.interfaces.PR36_MX;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.interfaces.PR36_MX.om.conexionesSQL;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.FTPUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/* DESCRIPCION : Prueba para comprobar la funcionalidad principal de la interface FEMSA_PR36_MX para generar archivos ILS en formato JSON de una plaza
 * 
 * MTC_FT_006_Generacion de ILS de una plaza Arq ENREDOUT
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
public class ATC_FT_003_PR36_MX_MF_GeneracionArchivoILSPlazaNEW extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_PR36_MX_MF_GeneracionArchivoILSPlazaNEW_test(HashMap<String, String> data) throws Exception {


		/*
		 * Utilerías
		 *********************************************************************/
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		/*
		 * Querys
		 *********************************************************************/
		System.out.println(
				" --------------------------------Inicio Querys DASPFR----------------------------------------------- ");
		String qryRegistrosProcesar = "SELECT SUBSTR(STORE_NAME10,1,5) AS CR_PLAZA, LOCATION, SUBSTR(STORE_NAME10,6,10)\r\n"
				+ "FROM  (SELECT DISTINCT TO_NUMBER(LOCATION) LOCATION\r\n" + "FROM (SELECT DISTINCT LOCATION\r\n"
				+ "FROM WMUSER.pos_repl_item_loc ril, WMUSER.v_item_loc_traits ilt, WMUSER.v_sups s\r\n" + "WHERE source_wh IS NULL\r\n"
				+ "AND ilt.item (+) = ril.item\r\n" + "AND ilt.loc (+) = ril.LOCATION\r\n"
				+ "AND ril.primary_repl_supplier = s.supplier\r\n"
				+ "AND ((ril.last_update_datetime > TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') AND ril.last_update_datetime <= TO_DATE ('31/01/2025 09:00:00', 'DD/MM/YYYY HH:MI:SS AM'))\r\n"
				+ "OR (ilt.last_update_datetime > TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') AND ilt.last_update_datetime <= TO_DATE ('31/01/2025 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')))\r\n"
				+ "AND ril.loc_type='S'\r\n" + "UNION ALL\r\n" + "SELECT DISTINCT LOCATION\r\n"
				+ "FROM WMUSER.pos_repl_item_loc ril, WMUSER.v_item_loc_traits ilt, WMUSER.v_wh vwh\r\n"
				+ "WHERE ilt.item (+) = ril.item\r\n" + "AND ilt.loc (+) = ril.LOCATION\r\n"
				+ "AND vwh.wh = source_wh\r\n" + "AND primary_repl_supplier IS NULL\r\n"
				+ "AND ((ril.last_update_datetime > TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') AND ril.last_update_datetime <= TO_DATE ('31/01/2025 09:00:00', 'DD/MM/YYYY HH:MI:SS AM'))\r\n"
				+ "OR (ilt.last_update_datetime > TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM') AND ilt.last_update_datetime <= TO_DATE ('31/01/2025 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')))\r\n"
				+ "AND ril.loc_type='S'\r\n" + ")\r\n" + ") a, wmuser.vm_store b\r\n" + "where a.LOCATION=b.STORE\r\n";


		String qryRegistrosProcesarLocation = "SELECT ROWNUM linenumber, LPAD (item, 16, ' ') item, primary_repl_supplier,\r\n"
				+ "DECODE (LENGTH (pct_tolerance),8, SUBSTR (pct_tolerance, 2, 8),pct_tolerance) pct_tolerance,loc_type,sup_name\r\n"
				+ "FROM (SELECT DISTINCT ril.item, primary_repl_supplier,pct_tolerance,'S' loc_type,dept,ril.CLASS,ril.subclass,store_reorderable_ind,\r\n"
				+ "sup_name,SUBSTR (st.description, 1, 2) id_carrier,SUBSTR (st.description, 4) alias_carrier,pec.DELIVERY_TIME, pec.REVISION_PERIOD,\r\n"
				+ "pec.SALE_UNITS, pec.stock_pr,pec.STOCK_PROM, pec.DATE_START_PROM, pec.DATE_END_PROM, '0' venta_perdida\r\n"
				+ "FROM WMUSER.POS_REPL_ITEM_LOC ril,WMUSER.V_ITEM_LOC_TRAITS ilt,WMUSER.V_SUPS s,WMUSER.V_SUP_TRAITS st,WMUSER.V_SUP_TRAITS_MATRIX stm,WMUSER.V_NB_PARAMETROS_ENVIO_CENT pec\r\n"
				+ "WHERE source_wh IS NULL AND ilt.item(+) = ril.item AND ilt.loc(+) = ril.LOCATION\r\n"
				+ "AND ril.LOCATION in (%s)\r\n"
				+ "AND ril.primary_repl_supplier = s.supplier AND ril.primary_repl_supplier = stm.supplier(+)\r\n"
				+ "AND stm.sup_trait = st.sup_trait(+)\r\n"
				+ "AND (ril.last_update_datetime > TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')\r\n"
				+ "OR ilt.last_update_datetime >  TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')\r\n"
				+ "OR pec.last_update_datetime > TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM'))\r\n"
				+ "AND ril.primary_repl_supplier = pec.supplier(+) AND pec.STORE(+) = ril.LOCATION AND pec.item(+) = ril.item\r\n"
				+ "UNION ALL\r\n"
				+ "SELECT DISTINCT ril.item, source_wh primary_repl_supplier,pct_tolerance, 'W' loc_type, dept, ril.CLASS, ril.subclass,store_reorderable_ind,\r\n"
				+ "wh_name sup_name,SUBSTR (st.description, 1, 2) id_carrier,SUBSTR (st.description, 4) alias_carrier,pec.DELIVERY_TIME, pec.REVISION_PERIOD,\r\n"
				+ "pec.SALE_UNITS, pec.stock_pr,pec.STOCK_PROM, pec.DATE_START_PROM, pec.DATE_END_PROM, '0' venta_perdida\r\n"
				+ "FROM WMUSER.POS_REPL_ITEM_LOC ril,WMUSER.V_ITEM_LOC_TRAITS ilt,WMUSER.V_WH vwh,WMUSER.V_SUP_TRAITS st,WMUSER.V_SUP_TRAITS_MATRIX stm,WMUSER.V_NB_PARAMETROS_ENVIO_CENT pec\r\n"
				+ "WHERE ilt.item(+) = ril.item AND ilt.loc(+) = ril.LOCATION AND vwh.WH = source_wh AND primary_repl_supplier IS NULL\r\n"
				+ "AND ril.LOCATION in (%s)\r\n"
				+ "AND ril.source_wh = stm.supplier(+)AND stm.sup_trait = st.sup_trait(+)AND ril.primary_repl_supplier = pec.supplier(+)AND pec.item(+) = ril.item\r\n"
				+ "AND pec.store(+) = ril.LOCATION\r\n"
				+ "AND (ril.last_update_datetime > TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')\r\n"
				+ "OR ilt.last_update_datetime > TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')\r\n"
				+ "OR pec.last_update_datetime > TO_DATE ('29/12/2024 09:00:00', 'DD/MM/YYYY HH:MI:SS AM')))";


		String qryWmLogRun = "SELECT RUN_ID, INTERFACE, START_DT, END_DT, STATUS \r\n" + "FROM WMLOG.WM_LOG_RUN \r\n"
				+ "WHERE INTERFACE LIKE '%s' AND STATUS = 'S' \r\n"
				+ "AND START_DT >= TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')";

		String qryThreads = "SELECT THREAD_ID, PARENT_ID, NAME, START_DT, END_DT,STATUS \r\n"
				+ "FROM WMLOG.WM_LOG_THREAD \r\n" + "WHERE PARENT_ID = '%s' \r\n" + "AND status = 'S'";

		String qryError = "SELECT ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY, ERROR_TYPE \r\n"
				+ "FROM WMLOG.WM_LOG_ERROR \r\n" + "WHERE RUN_ID = '%s'";

		String qryOutDocRms = "SELECT ID, DOC_NAME, DOC_TYPE, DATE_CREATED, STATUS, SOURCE_ID \r\n"
				+ "FROM POSUSER.POS_OUT_DOC_RMS \r\n" + "WHERE DOC_TYPE = 'ILS' \r\n" + "AND PV_CR_PLAZA = '"
				+ data.get("CR_PLAZA") + "'\r\n" + "AND SOURCE_ID = %s";

		Date fechaEjecucionInicio;

		testCase.setProject_Name("Multiformato Transformación");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

		/****************************************************************************************************************************************
		 * Paso 1
		 **************************************************************************************************************************************/
		addStep("Establecer conexión a la BD FCRMSQA.");

		testCase.addTextEvidenceCurrentStep("Conexion: FCDASPFR");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_DASPERF_NUCLEO_MULTIFORMATO_QA);

		/****************************************************************************************************************************************
		 * Paso 2
		 **************************************************************************************************************************************/
		System.out.println(
				" --------------------------------Inicio Paso2----------------------------------------------- ");
		addStep("Validar que exista información pendiente de procesar.");

		System.out.println("qryRegistrosProcesar: \r\n " + qryRegistrosProcesar);
		SQLResult qryRegistrosProcesar_r = conexionesSQL.sqlExecute(
				GlobalVariables.DB_HOST_DASPERF_NUCLEO_MULTIFORMATO_QA, qryRegistrosProcesar,
				GlobalVariables.DB_USER_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_DASPERF_NUCLEO_MULTIFORMATO_QA);
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
		SQLResult qryRegistrosProcesarLocation_r = conexionesSQL.sqlExecute(
				GlobalVariables.DB_HOST_DASPERF_NUCLEO_MULTIFORMATO_QA, qryRegistrosProcesarLocation_f,
				GlobalVariables.DB_USER_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_DASPERF_NUCLEO_MULTIFORMATO_QA);
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
		addStep("Ejecucion del job runPR36_MX_ILS");

		JSONObject obj = new JSONObject(data.get("cm_jobs"));
		ControlM job = new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();
		assertTrue(flag);
		String resultado = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		System.out.println("Valor de output :" + job.getOutput());
		assertEquals(resultado, "Ended OK");
		u.hardWait(300);
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

		String qryWmLogRun_f = String.format(qryWmLogRun, "%PR36_MX%", fechaEjecucionInicio_f);
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
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_WM_NUCLEO_MULTIFORMATO_QA);

		/****************************************************************************************************************************************
		 * Paso 10
		 **************************************************************************************************************************************/
		addStep("Validar el registro del documento enviado en la tabla pos_out_doc_rms de FCWMSIT.");

		String qryOutDocRms_f = String.format(qryOutDocRms, runId);
		System.out.println("qryOutDocRms_f: \r\n " + qryOutDocRms_f);
		SQLResult qryOutDocRms_r = conexionesSQL.sqlExecute(GlobalVariables.DB_HOST_WM_NUCLEO_MULTIFORMATO_QA,
				qryOutDocRms_f, GlobalVariables.DB_USER_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WM_NUCLEO_MULTIFORMATO_QA);
		assertFalse(qryOutDocRms_r.isEmpty());
		testCase.addQueryEvidenceCurrentStep(qryOutDocRms_r);
		String wmDocNameILS = qryOutDocRms_r.getData(0, "DOC_NAME");
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

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Prueba para comprobar la funcionalidad principal de la interface FEMSA_PR36_MX para generar archivos ILS en formato JSON de una plaza.";
	}

	@Override
	public String setTestDesigner() {
		return "Dalia Espinoza";
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
				+ "	*Contar con el nombre y grupo del nuevo Job de Control M para PR36_MX de WM10.11\r\n"
				+ "	*Contar con acceso a repositorio de buzón de la tienda.\r\n"
				+ "	*Contar con las credenciales de WM10.11 de los nuevos servers del Integration Server de QA de Back Office.\r\n";
	}
}