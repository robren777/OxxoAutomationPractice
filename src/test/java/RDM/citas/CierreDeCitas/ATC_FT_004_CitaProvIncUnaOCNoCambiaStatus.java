package RDM.citas.CierreDeCitas;

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

public class ATC_FT_004_CitaProvIncUnaOCNoCambiaStatus extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_CitaProvIncUnaOCNoCambiaStatus_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String poNumber = /*data.get("poNumber")*/"43906875";
		//String apptNumber = data.get("apptNumber");
		String apptNumber = "94329";
		String controlMUser = data.get("controlMUser");
		String controlMPass = PasswordUtil.decryptPassword(data.get("controlMPass"));
		String controlMServer = data.get("controlMServer");

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_BARA_QA1, GlobalVariables.DB_USERVIEW_BARA_QA1, GlobalVariables.DB_PASSWORD_BARA_QA1);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
	
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar a la BD de RDM");
		System.out.println("PASO 1");
		
		testCase.addBoldTextEvidenceCurrentStep("Conectado a la BD RDM: ");
		testCase.addTextEvidenceCurrentStep("Host: " + dbRdm.getHost());
		testCase.addTextEvidenceCurrentStep("User: " + dbRdm.getUser());
		
		// PASO 2
		addStep("Validar en la BD que la orden de compra se encuentre en estatus \"A\" (Aprobada).");
		System.out.println("PASO 2");
		
		String po = String.format("SELECT FACILITY_ID, PO_NBR, VENDOR_NBR, STATUS "
				+ "FROM PO WHERE PO_NBR = %s AND STATUS = 'A'", poNumber);
		System.out.println(po);

		SQLResult executeQuery = executeQuery(dbRdm, po);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron resultados en PO con PO_NBR = " + poNumber
				+ " y STATUS = 'A'");
		
		// PASO 3
		// VALIDAR QUE EXISTA RELACION CITA Y PO
		addStep("Validar en la BD que la cita a proveedor se encuentre ligada a solo una "
				+ "orden de compra");
		System.out.println("PASO 3");
		
		String appt = String.format("SELECT APPT_NBR, PO_NBR, ITEM_ID FROM APPT_DETAIL"
				+ " WHERE APPT_NBR = %s", apptNumber);
		System.out.println(appt);

		executeQuery = executeQuery(dbRdm, appt);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron citas en APPT_DETAIL con PO_NBR = " + poNumber);
		
		// VALIDA QUE LA CITA SOLO TENGA UN PO LIGADO
		System.out.println("PASO SOLO UN PO LIGADO");
		
		String poLigado = String.format("SELECT UNIQUE(PO_NBR) FROM APPT_DETAIL"
				+ " WHERE APPT_NBR = %s", apptNumber);
		System.out.println(poLigado);

		executeQuery = executeQuery(dbRdm, poLigado);

		statusQuery = executeQuery.isEmpty();
		
		assertTrue(executeQuery.getRowCount() == 1, "La cita: " + apptNumber + " tiene mas de una PO ligada.");
			
		// PRE JOB 
		System.out.println("PASO PREJOB");	
		
		String apptStatus = String.format("SELECT APPT_STATUS FROM APPOINTMENT "
					+ "WHERE APPT_NBR = %s", apptNumber);
		System.out.println(apptStatus);
			
		executeQuery = executeQuery(dbRdm, apptStatus);
			
		String statusAppt = "";
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			statusAppt = executeQuery.getData(0, "APPT_STATUS");
		}

		assertFalse(statusQuery, "No se encontraron resultados en APPOINTMENT con APPT_NBR = " + apptNumber);
		
		// PASO 4
		addStep("Ejecutar el proceso xxfc_close_appt_pkg");
		System.out.println("PASO 4");
		
		JSONObject obj = new JSONObject(data.get("xxfc_close_appt.sh_MTY"));
		Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);		
		u.hardWait(60);
		CM.logOn(); // loguea

		ControlM control = new ControlM(u, testCase, obj); // si es uno por uno, debes instanciar esta clase

		control.searchJob(); // busca el job
		String resultadoEjecucion = control.executeJob(); // ejecuta el job
		
		assertTrue(resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition"), 
				"El job xxfc_close_appt_pkg no se ejecuto correctamente");
		
		u.close();
		
		// PASO 5
		addStep("Validar en la BD que el estatus de la cita a proveedor no cambie el estatus.");
		System.out.println("PASO 5");	
		
		apptStatus = String.format("SELECT APPT_NBR, CREATION_TS, APPT_STATUS FROM APPOINTMENT "
					+ "WHERE APPT_NBR = %s AND APPT_STATUS = '%s'", apptNumber, statusAppt);
		System.out.println(apptStatus);
			
		executeQuery = executeQuery(dbRdm, apptStatus);
			
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron resultados en APPOINTMENT con APPT_NBR = " + apptNumber
				+ " y STATUS = " + statusAppt);
		
		// PASO 6
		addStep("Validar en la BD que las ordenes de compra se encuentren en estatus \"A\" (Aprobada)");
		System.out.println("PASO 6");
		
		System.out.println(po);

		executeQuery = executeQuery(dbRdm, po);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron resultados en PO con PO_NBR = " + poNumber
				+ " y STATUS = 'A'");
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1) Habilitacion del job xxfc_close_appt.sh_MTY"
				+ "2) Tener una cita vigente"
				+ "3) Agregar solo una orden de compra a la cita";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar que al ejecutar el proceso xxfc_close_appt_pkg, "
				+ "la cita a proveedor que no se recibio completa no se cierre si la "
				+ "fecha de las ordenes de compra aun no tienen vencimiento, "
				+ "la orden de compra no cambie de estatus A (Aprobada)";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_CitaProvIncUnaOCNoCambiaStatus_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
