package RMS.RegresionVentas;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.QueriesRegresion;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_RealizarVentadeArticuloNuevo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_RealizarVentadeArticuloNuevo_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * Aqui se necesita poner la informacion de las bases de datos de WB
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		boolean ejecucionCorrectaJob = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		JobUtil job = new JobUtil(su, testCase, data);
		String resultadoEjecucion;
		
		SQLResult executeQuery;
		Boolean statusQuery = true;
		String query;
		String pid_id;
		String item;
		String pv_doc_name;
		String cr_tienda;
		String doc_type;
		String rtk_filename = null;
		String store;
		String cr_plaza = null;
		String reference_3 = null;
		String reference_9 = null;


		// Paso 1
		addStep("Realizar Venta de artículo nuevo en XPOS");
		
		// Paso 2
		addStep("Realizar Fin Día en XPOS");
		
		// Paso 3
		addStep("Se ejecuta la DS50");
						
		resultadoEjecucion = job.ejecutar("DS50");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job DS50 no se ejecuto correctamente");
		
		// Paso 4
		addStep("Se revisa información de la venta en tablas de Webmethods");
		
		//POS_SAL
		pid_id = data.get("pid_id");
		query = String.format(QueriesRegresion.POS_SAL, pid_id);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		//POS_SAL_DETL
		pid_id = data.get("pid_id");
		item = data.get("item");
		query = String.format(QueriesRegresion.POS_SAL_DETL, pid_id, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		//POS_INBOUND_DOCS
		pv_doc_name = data.get("pv_doc_name");
		query = String.format(QueriesRegresion.POS_INBOUND_DOCS, pv_doc_name);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 5
		addStep("Se ejecuta la interfaz PR24 en Control-M");
		
		resultadoEjecucion = job.ejecutar("PR24");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job PR24 no se ejecuto correctamente");
		
		
		// Paso 6
		addStep("Validar que se genere el archivo .DAT en la tabla POS_INBOUND_DOCS y que el estatus cambie de I a E.");
		
		//POS_INBOUND_DOCS
		pv_doc_name = data.get("pv_doc_name");
		query = String.format(QueriesRegresion.POS_INBOUND_DOCS, pv_doc_name);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		assertEquals(executeQuery.getData(0, "STATUS"), "E");
		
		if (executeQuery.getData(0, "TARGET_ID") == "") {
			statusQuery = false;
		}
		else {
			rtk_filename = executeQuery.getData(0, "TARGET_ID");
		}
		assertFalse(statusQuery, "No se genero el archivo .DAT");
		
		
		// Paso 7
		addStep("Validar que el archivo .DAT de ventas se encuentre en la ruta /u04/retek/rmsqa/batch/wmworking/SAL en el servidor unix de RMS.");
		
//		testCase.addTextEvidenceCurrentStep("FTP_HOST: " + data.get("FTP_HOST") +
//		                        "\nFTP_PORT: " + data.get("FTP_PORT") +
//		                        "\nFTP_USER: " + data.get("FTP_USER"));
//
//		FTPUtil ftp = new FTPUtil(data.get("FTP_HOST"), Integer.parseInt(data.get("FTP_PORT")), data.get("FTP_USER"), data.get("FTP_PASSWORD"));
//
//		String path = "/u01/posuser/FEMSA_OXXO/POS/"+data.get("CR_PLAZA")+"/"+data.get("CR_TIENDA")+"/working" + "/" +docName;
//
//		if (ftp.fileExists(path)) {
//			System.out.println(path + " - Existe");
//			testCase.addTextEvidenceCurrentStep("Se encontro archivo en la ruta: " + path);
//		} else {
//			System.out.println(path + " - No xiste");
//			testCase.addTextEvidenceCurrentStep("Error - no se encontr� archivo: " + path);
//		}
//
//		assertTrue(ftp.fileExists(path), "No se obtiene el archivo por FTP.");
		
		
		// Paso 8
		addStep("Validar que se registre información de la venta en la tabla WMUSER.RTK_INBOUND_DOCS de RMS en estatus L.");
		
		dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		//WMUSER_RTK_INBOUND_DOCS
		cr_tienda = data.get("cr_tienda");
		doc_type = data.get("doc_type");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS, cr_tienda, doc_type, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertEquals(executeQuery.getData(0, "STATUS"), "L");
		
		
		// Paso 9
		addStep("Se ejecuta la interfaz AIX_POSUPLD en Control-M");
		
		resultadoEjecucion = job.ejecutar("AIX_POSUPLD");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job AIX_POSUPLD no se ejecuto correctamente");
		
		
		// Paso 10
		addStep("Validar en la tabla WMUSER.RTK_INBOUND_DOCS de RMS que el archivo SAL cambie a estatus E.");
		
		//WMUSER_RTK_INBOUND_DOCS
		cr_tienda = data.get("cr_tienda");
		doc_type = data.get("doc_type");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS, cr_tienda, doc_type, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertEquals(executeQuery.getData(0, "STATUS"), "E");
		
		
		// Paso 11
		addStep("Validar que el archivo .DAT se haya depositado en la ruta /u04/retek/rmsqa/batch/posupld/processed en el servidor de RMS.");
		
//		testCase.addTextEvidenceCurrentStep("FTP_HOST: " + data.get("FTP_HOST") +
//		                        "\nFTP_PORT: " + data.get("FTP_PORT") +
//		                        "\nFTP_USER: " + data.get("FTP_USER"));
//
//		FTPUtil ftp = new FTPUtil(data.get("FTP_HOST"), Integer.parseInt(data.get("FTP_PORT")), data.get("FTP_USER"), data.get("FTP_PASSWORD"));
//
//		String path = "/u01/posuser/FEMSA_OXXO/POS/"+data.get("CR_PLAZA")+"/"+data.get("CR_TIENDA")+"/working" + "/" +docName;
//
//		if (ftp.fileExists(path)) {
//			System.out.println(path + " - Existe");
//			testCase.addTextEvidenceCurrentStep("Se encontro archivo en la ruta: " + path);
//		} else {
//			System.out.println(path + " - No xiste");
//			testCase.addTextEvidenceCurrentStep("Error - no se encontr� archivo: " + path);
//		}
//
//		assertTrue(ftp.fileExists(path), "No se obtiene el archivo por FTP.");
		
		
		// Paso 12
		addStep("Validar que la información de la venta del packitem se encuentre en la tabla TRAN_DATA");
		
		//TRAN_DATA
		String tran_date = ZonedDateTime.now(ZoneId.of("America/Chicago"))
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		store = data.get("store");
		
		query = String.format(QueriesRegresion.TRAN_DATA, store, item,tran_date);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 13
		addStep("Se ejecuta la interfaz STOCK_LEDGER  en Control-M");
		
		resultadoEjecucion = job.ejecutar("STOCK_LEDGER");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job STOCK_LEDGER no se ejecuto correctamente");
		
		
		// Paso 14
		addStep("Validar en la tabla FEM_FIF_STG que se haya generado el Tran Code 1 de ventas");
		
		//FEM_FIF_STG
		query = String.format(QueriesRegresion.FEM_FIF_STG, cr_plaza, store, item, tran_date, reference_3);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 15
		addStep("Se ejecutan las interfaces runRO8_MEX_VTAS.");
		
		resultadoEjecucion = job.ejecutar("runRO8_MEX_VTAS");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job runRO8_MEX_VTAS no se ejecuto correctamente");
		
		
		// Paso 16
		addStep("Validar que los campos reference_3 y reference_9 ya no se encuentren en null en la tabla FEM_FIF_STG para el Tran Code 1");
		
		//FEM_FIF_STG
		query = String.format(QueriesRegresion.FEM_FIF_STG_reference, cr_plaza, store, item, tran_date);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		if (executeQuery.getData(0, "REFERENCE_3") == "") {
			statusQuery = false;
		}
		else if(executeQuery.getData(0, "REFERENCE_9") == ""){
			statusQuery = false;
		}
		else {
			statusQuery = true;
		}
		assertFalse(statusQuery, "No se genero el archivo .DAT");
		
		
		// Paso 16
		addStep("Validar que la información de Ventas se encuentre en la tabla GL_INTERFACE de EBS para la póliza generado por la RO8.");
		
		/*
		 * Poner aqui los datos de la base de datos de EBS
		 */
		dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		//GL_INTERFACE
		query = String.format(QueriesRegresion.GL_INTERFACE, reference_3, reference_9);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(executeQuery.isEmpty(), "No existen registros");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_RealizarVentadeArticuloNuevo_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_001_RealizarVentadeArticuloNuevo";
	}

	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public void beforeTest() {
	}

}
