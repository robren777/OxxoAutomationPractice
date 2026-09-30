package WM.EvolucionRetiroSinTarjeta.HU09;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ATC_FT_006_Validar_informacion_de_tablas_FACT_CONSILIACION_RST_y_TPE_FR_TRANSACTION extends BaseExecution {
	/**
	 * Informacion de tablas FACT_CONSILIACION_RST y TPE_FR_TRANSACTION
	 * Desc:
	 * Validar la infromacion de tablas FACT_CONSILIACION_RST y TPE_FR_TRANSACTION
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
	public void ATC_FT_006_Validar_informacion_de_tablas_FACT_CONSILIACION_RST_y_TPE_FR_TRANSACTION(HashMap<String, String> data) throws Exception {

		//BD FCODSQA
		SQLUtil dbFCODSQA = new SQLUtil(GlobalVariables.DB_HOST_FCODSQA, GlobalVariables.DB_USER_FCODSQA,
				GlobalVariables.DB_PASSWORD_FCODSQA);


		System.out.println(
				"***************************************** PASO 1 ************************************************");

		String queryFCODSQA_FACT_CONSILIACION_RST = "SELECT * FROM ( SELECT FOLIO_REFERENCIA, DESCRIPCIÓN_EMISOR, IMPORTE_RETIRO, FOLIO_WM FROM WM.FACT_CONSILIACION_RST ORDER BY FECHA_TRANSACCIÓN DESC ) WHERE ROWNUM <= 5";

		System.out.println("Consulta: " + queryFCODSQA_FACT_CONSILIACION_RST);
		SQLResult Execute_dbFCODSQA_Query = executeQuery(dbFCODSQA, queryFCODSQA_FACT_CONSILIACION_RST);
		int cantidadDatosFACT_CONSILIACION_RST = Execute_dbFCODSQA_Query.getRowCount();

		System.out.println("Cantidad de datos en TPE_FR_TRANSACTION: " + cantidadDatosFACT_CONSILIACION_RST);
		List<String[]> datosFACT_CONSILIACION_RST = Execute_dbFCODSQA_Query.getAllData();

		boolean conexionFCODSQA = true;
		conexionFCODSQA = Execute_dbFCODSQA_Query.isEmpty();

		addStep("Ingresar a la BD de FCODSQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCODSQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCODSQA);

		addStep("Se ingresa a la Base de datos FCODSQA para validar la información de la tabla FACT_CONSILIACION_RST");
		testCase.addQueryEvidenceCurrentStep(Execute_dbFCODSQA_Query);
		assertFalse(conexionFCODSQA, "La conexion no fue exitosa");

		if (!conexionFCODSQA){
			System.out.println("Conexion con la base de datos FCODSQA se realizo de manera exitosa");
		}


		System.out.println(
				"***************************************** PASO 3 y 4 ************************************************");

		String queryFCODSQA_TPE_FR_TRANSACTION   = "SELECT * FROM (SELECT APPLICATION, ENTITY, FOLIO, CREATION_DATE FROM WM.TPE_FR_TRANSACTION ORDER BY CREATION_DATE DESC ) WHERE ROWNUM <= 5";
		System.out.println("Consulta: " + queryFCODSQA_TPE_FR_TRANSACTION);
		Execute_dbFCODSQA_Query = executeQuery(dbFCODSQA, queryFCODSQA_TPE_FR_TRANSACTION);
		int TPE_FR_TRANSACTIONcantidadDatos = Execute_dbFCODSQA_Query.getRowCount();

		System.out.println("Cantidad de datos en TPE_FR_TRANSACTION: " + TPE_FR_TRANSACTIONcantidadDatos);
		List<String[]> datosTPE_FR_TRANSACTION = Execute_dbFCODSQA_Query.getAllData();

		conexionFCODSQA = Execute_dbFCODSQA_Query.isEmpty();

		addStep("Ingresar a la BD de FCODSQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCODSQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCODSQA);

		addStep("Se ingresa a la Base de datos FCODSQA para validar la información de la tabla TPE_FR_TRANSACTION");
		testCase.addQueryEvidenceCurrentStep(Execute_dbFCODSQA_Query);
		assertFalse(conexionFCODSQA, "La conexion no fue exitosa");

		if (!conexionFCODSQA){
			System.out.println("Conexion con la base de datos FCODSQA se realizo de manera exitosa");
		}
		System.out.println(
				"***************************************** PASO 5 ************************************************");

		addStep("Ejecutar el job ODS_MAP_ODS2ODS_FACT_CONS_RST_WM_030");

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

		//ejecucion del job y obtencion de resultados de la ejecucion en control-M
		String resultado1 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println ("Valor de output :" +job.getOutput());

		u.hardWait(120);
		//resultado esperado para la finalizacion de ejecucion del job
		assertEquals(resultado1, "Ended OK");

		u.hardWait(3);

		// cuando hace el view, y se termina se cierra la pantalla de vista pero no el job, aqui se cierra la consulta q se hizo
		job.closeViewpoint();
		u.hardWait(15);
		addStep("Se ejecuta de manera exitosa el job ODS_MAP_ODS2ODS_FACT_CONS_RST_WM_030");

		u.close();

		System.out.println(
				"***************************************** PASO 6 ************************************************");

		queryFCODSQA_TPE_FR_TRANSACTION   = "SELECT * FROM ( SELECT A.FOLIO_REFERENCIA, A.DESCRIPCIÓN_EMISOR, A.IMPORTE_RETIRO, A.FOLIO_WM FROM WM.FACT_CONSILIACION_RST A INNER JOIN WM.TPE_FR_TRANSACTION B ON A.FOLIO_WM = B.FOLIO ORDER BY B.CREATION_DATE DESC ) WHERE ROWNUM <= 5";
		System.out.println("Consulta de mapeo: " + queryFCODSQA_TPE_FR_TRANSACTION);
		Execute_dbFCODSQA_Query = executeQuery(dbFCODSQA, queryFCODSQA_TPE_FR_TRANSACTION);
		testCase.addQueryEvidenceCurrentStep(Execute_dbFCODSQA_Query);
		int datosMapeados = Execute_dbFCODSQA_Query.getRowCount();

		System.out.println("Cantidad de datos mapeados: " + datosMapeados);

		System.out.println("Se imprime una muestra de datos:");
		List<String> columnNames = Execute_dbFCODSQA_Query.getColumnNames();
		Map<String, String> datos = Execute_dbFCODSQA_Query.getRowMap(1);

		columnNames.forEach( x -> { System.out.println(x + ": " + datos.get(x)); } );

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "El ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005 y ODS_MAP_ODS2ODS_FACT_CONS_RST_WM_030 deben estar instalados en QA para su ejecucion";
	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar información de tablas FACT_CONSILIACION_RST y TPE_FR_TRANSACTION";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Miguel Angel Escamilla Reyes";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "006-Validar información de tablas FACT_CONSILIACION_RST y TPE_FR_TRANSACTION";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
