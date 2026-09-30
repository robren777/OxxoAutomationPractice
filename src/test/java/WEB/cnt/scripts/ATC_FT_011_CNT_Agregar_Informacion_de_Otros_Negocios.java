package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_011_CNT_Agregar_Informacion_de_Otros_Negocios extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_Agregar_Informacion_de_Otros_Negocios(HashMap<String, String> data) throws Exception {

		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		String tienda = data.get("Tienda");
		String queryTiendaCreada = "SELECT * FROM T_TIENDAS WHERE CR_SUPERIOR = '10MON' AND CR_TIENDA = '" + tienda
				+ "'";

		String queryTiendaNoSeEncuentra = "SELECT * FROM TIENDAS.XX_NOMCOM_CNT_SAP WHERE CECO = '" + tienda + "'";
		
		String queryTiendaSeEncuentra = "SELECT * FROM TIENDAS.XX_NOMCOM_CNT_SAP WHERE CECO = '" + tienda + "'";

		/*
		 * PASO 1
		 *****************************************************************************************/

		addStep("Consultar las Tiendas generadas en la tabla T_TIENDAS.");
		

		SQLResult executeQueryTiendas = executeQuery(dbFCIAS, queryTiendaCreada);
		boolean statusQuery = executeQueryTiendas.isEmpty();

		if (!statusQuery) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la informaci�n de la tabla");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla");
		}

		testCase.addQueryEvidenceCurrentStep(executeQueryTiendas);
		
		assertTrue(statusQuery, "Las Tiendas se muestran en la tabla consultada.");
		
		
		/*
		 * PASO 2
		 *****************************************************************************************/

		addStep("Validar que en la tabla XX_NOMCOM_CNT_SAP no se encuentren las Tiendas.");
	

		SQLResult executeQueryNoTiendas = executeQuery(dbFCIAS, queryTiendaNoSeEncuentra);
		boolean statusQueryNegative = executeQueryNoTiendas.isEmpty();

		if (statusQueryNegative) {
			testCase.addTextEvidenceCurrentStep("Las Tiendas no se muestran en la tabla consultada.");
		} else {
			testCase.addTextEvidenceCurrentStep("Se encontraron datos en la tabla");
		}

		testCase.addQueryEvidenceCurrentStep(executeQueryNoTiendas);
		
		assertFalse(statusQueryNegative, "Las Tiendas no se muestran en la tabla consultada.");
		
		/*
		 * PASO 3
		 *****************************************************************************************/

		addStep("Ingresar a Control-M con sus respectivas credenciales. Validar que el Job se haya ejecutado exitosamente siguiendo la ruta correspondiente para ubicar al Job.");

		u = new SeleniumUtil(new ChromeTest(), true);
		u.get(data.get("server"));

	
		JSONObject obj = new JSONObject(data.get("job"));

		testCase.addTextEvidenceCurrentStep("Job en  Control M ");
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));

		testCase.addTextEvidenceCurrentStep("Login");

		u.hardWait(40);
		u.waitForLoadPage();
		CM.logOn();

		//Buscar del job
		testCase.addTextEvidenceCurrentStep("Inicio de job ");
		ControlM control = new ControlM(u, testCase, obj);
		boolean flag = control.searchJob();
		assertFalse(!flag, "No se encontro el job ");

		//Ejecucion
		String resultado = control.executeJob();
		testCase.addTextEvidenceCurrentStep(resultado);
		System.out.println("Resultado de la ejecucion -> " + resultado);

		//Valor del output 
		testCase.addTextEvidenceCurrentStep("Output: " + control.getOutput());
		System.out.println("Valor de output :" + control.getOutput());

		//Validacion del caso
		Boolean casoPasado = false;

		if (resultado.equals("Ended OK")) {

			casoPasado = true;

			System.out.println("El caso paso correctamente");

		}
		assertFalse(!casoPasado, "El job se ejecut� correctamente");

		control.closeViewpoint();

		u.close();
		
		/*
		 * PASO 4
		 *****************************************************************************************/

		addStep("Validar que las Tiendas hayan sido insertadas en la tabla XX_NOMCOM_CNT_SAP.");

		SQLResult executeQueryTiendasJob = executeQuery(dbFCIAS, queryTiendaSeEncuentra);
		boolean statusQueryJob = executeQueryNoTiendas.isEmpty();

		if (!statusQueryJob) {
			testCase.addTextEvidenceCurrentStep("Las Tiendas se muestran en la tabla consultada.");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la tabla");
		}

		testCase.addQueryEvidenceCurrentStep(executeQueryNoTiendas);
		
		assertTrue(statusQuery, "Las Tiendas se muestran en la tabla consultada.");
	}
	
	

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validar� que en la tabla XX_NOMCOM_CNT_SAP se agreguen las Tiendas de los Negocios BARA, Oxxo Gas, Oxxo Colombia as� como las Tiendas Oxxo no sufran afectaci�n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_011_Agregar_Informacion_de_Otros_Negocios";
	}



	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}
