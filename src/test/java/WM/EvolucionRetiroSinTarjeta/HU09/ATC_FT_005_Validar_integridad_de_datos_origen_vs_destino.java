package WM.EvolucionRetiroSinTarjeta.HU09;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_005_Validar_integridad_de_datos_origen_vs_destino extends BaseExecution {
	/**
	 * Integridad de datos origen y destino
	 * Desc:
	 * Validar la integridad de los datos de origen y destino
	 * @author Miguel Escamilla
	 * @date 25/01/2024
	 **/
	public static boolean compareLists(List<String[]> list1, List<String[]> list2) {
		// Verificar si las listas tienen la misma longitud
		if (list1.size() != list2.size()) {
			return false;
		}

		// Recorrer ambas listas y comparar los elementos de cada arreglo String[]
		for (int i = 0; i < list1.size(); i++) {
			String[] arr1 = list1.get(i);
			String[] arr2 = list2.get(i);

			// Verificar si los arreglos tienen la misma longitud
			if (arr1.length != arr2.length) {
				return false;
			}

			// Comparar los elementos de los arreglos
			for (int j = 0; j < arr1.length; j++) {
				if (!arr1[j].equals(arr2[j])) {
					return false;
				}
			}
		}

		// Si se llega hasta aquí, las listas son iguales
		return true;
	}
	SeleniumUtil u = null;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_Validar_integridad_de_datos_origen_vs_destino(HashMap<String, String> data) throws Exception {

		//BD FCODSQA
		SQLUtil dbFCODSQA = new SQLUtil(GlobalVariables.DB_HOST_FCODSQA, GlobalVariables.DB_USER_FCODSQA,
				GlobalVariables.DB_PASSWORD_FCODSQA);

		//BD FCWMREQA
		SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
				GlobalVariables.DB_PASSWORD_FCWMREQA);


		System.out.println(
				"***************************************** PASO 1 y 2 ************************************************");

		String queryFCWMREQA_TPE_FR_TRANSACTION_RST = "Select * from ( SELECT APPLICATION, ENTITY, FOLIO, CREATION_DATE FROM TPEREP.TPE_FR_TRANSACTION_RST  ORDER BY CREATION_DATE DESC ) WHERE ROWNUM <= 5";

		System.out.println("Consulta Cantidad de Datos: " + queryFCWMREQA_TPE_FR_TRANSACTION_RST);
		SQLResult Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryFCWMREQA_TPE_FR_TRANSACTION_RST);
		int cantidadDatosTPE_FR_TRANSACTION_RST = Execute_dbFCWMREQA_Query.getRowCount();

		System.out.println("Cantidad de datos en TPE_FR_TRANSACTION_RST: " + cantidadDatosTPE_FR_TRANSACTION_RST);
		List<String[]> datosTPE_FR_TRANSACTION_RST = Execute_dbFCWMREQA_Query.getAllData();

		boolean conexionFCWMREQA = true;
		conexionFCWMREQA = Execute_dbFCWMREQA_Query.isEmpty();

		addStep("Ingresar a la BD de FCWMREQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);

		addStep("Se ingresa a la Base de datos FCWMREQA para validar la información de la tabla TPE_FR_TRANSACTION_RST");
		testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
		assertFalse(conexionFCWMREQA, "La conexion no fue exitosa");

		System.out.println(
				"***************************************** PASO 3 y 4 ************************************************");

		String queryFCODSQA_TPE_FR_TRANSACTION = "Select * from ( SELECT APPLICATION, ENTITY, FOLIO, CREATION_DATE FROM WM.TPE_FR_TRANSACTION ORDER BY CREATION_DATE DESC ) WHERE ROWNUM <= 5";

		System.out.println("Consulta: " + queryFCODSQA_TPE_FR_TRANSACTION);
		SQLResult Execute_dbFCODSQA_Query = executeQuery(dbFCODSQA, queryFCODSQA_TPE_FR_TRANSACTION);
		int FCODSQAcantidadDatos = Execute_dbFCODSQA_Query.getRowCount();

		System.out.println("Cantidad de datos en TPE_FR_TRANSACTION antes de ejecutar el Job: " + FCODSQAcantidadDatos);
		List<String[]> datosTPE_FR_TRANSACTION = Execute_dbFCODSQA_Query.getAllData();

		boolean conexionFCODSQA = true;
		conexionFCODSQA = Execute_dbFCODSQA_Query.isEmpty();

		addStep("Ingresar a la BD de FCODSQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCODSQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCODSQA);

		addStep("Se ingresa a la Base de datos FCODSQA para validar la información de la tabla TPE_FR_TRANSACTION");
		testCase.addQueryEvidenceCurrentStep(Execute_dbFCODSQA_Query);
		assertFalse(conexionFCODSQA, "La conexion no fue exitosa");


		System.out.println(
				"***************************************** PASO 5 ************************************************");

		addStep("Ejecutar el job ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
		u.get(data.get("server"));

		//espera de carga de pagina y login
		u.waitForLoadPage();
		u.hardWait(20);
		u.waitForLoadPage();
		cm.logOn();

		//objeto json para pasar los datos para encontrar el job de control-M a utilizar
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job = new ControlM(u, testCase, obj);
		u.hardWait(15);
		boolean flag = job.searchJob();
		//validar que el job se encontro
		assertTrue(flag);
		u.hardWait(80);
		//ejecucion del job y obtencion de resultados de la ejecucion en control-M
		String resultado1 = job.executeJob();
		u.hardWait(20);
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println ("Valor de output :" +job.getOutput());

		u.hardWait(10);
		//resultado esperado para la finalizacion de ejecucion del job
		assertEquals(resultado1, "Ended OK");

		u.hardWait(3);

		// cuando hace el view, y se termina se cierra la pantalla de vista pero no el job, aqui se cierra la consulta q se hizo
		job.closeViewpoint();
		u.hardWait(15);
		addStep("Se ejecuta de manera exitosa el job ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005");

		u.close();

		System.out.println(
				"***************************************** PASO 6 y 7 ************************************************");

		System.out.println("Consulta: " + queryFCODSQA_TPE_FR_TRANSACTION);
		Execute_dbFCODSQA_Query = executeQuery(dbFCODSQA, queryFCODSQA_TPE_FR_TRANSACTION);
		int TPE_FR_TRANSACTIONcantidadDatos = Execute_dbFCODSQA_Query.getRowCount();

		System.out.println("Cantidad de datos en TPE_FR_TRANSACTION despues de ejecutar el Job: " + TPE_FR_TRANSACTIONcantidadDatos);
		List<String[]> datosTPE_FR_TRANSACTION_despuesJob = Execute_dbFCODSQA_Query.getAllData();

		conexionFCODSQA = Execute_dbFCODSQA_Query.isEmpty();

		addStep("Ingresar a la BD de FCODSQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCODSQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCODSQA);

		addStep("Se ingresa a la Base de datos FCODSQA para validar la información de la tabla TPE_FR_TRANSACTION");
		testCase.addQueryEvidenceCurrentStep(Execute_dbFCODSQA_Query);
		assertFalse(conexionFCODSQA, "La conexion no fue exitosa");

		System.out.println(
				"***************************************** PASO 8 ************************************************");
		boolean tablas = compareLists(datosTPE_FR_TRANSACTION_RST, datosTPE_FR_TRANSACTION_despuesJob);

		if (tablas)
			System.out.println( "Los datos de las tablas TPE_FR_TRANSACTION_RST y TPE_FR_TRANSACTION coinciden");
	}


	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "El ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005 debe estar instalado en QA para su ejecucion";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar integridad de datos origen vs destino";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Miguel Angel Escamilla Reyes";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "005-Validar integridad de datos origen vs destino";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
