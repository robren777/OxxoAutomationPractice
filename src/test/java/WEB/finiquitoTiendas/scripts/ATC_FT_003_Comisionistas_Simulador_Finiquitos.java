package WEB.finiquitoTiendas.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.finiquitoTiendas.om.Pantalla_Simulador;
import WEB.om.PortalApps;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**Desc: Validar los comisionistas activos en Simulador de Finiquitos
* MTC_FT_003. Comisionistas Simulador Finiquitos
* @author Yair Lizarraga
* @date Diciembre 2022
*/
public class ATC_FT_003_Comisionistas_Simulador_Finiquitos extends BaseExecution { 
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Comisionistas_Simulador_Finiquitos_test (HashMap<String, String> data) throws Exception {

		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = GlobalVariables.URL_PORTAL_APPS_SAR;
		String Plaza = data.get("Plaza");
		String Distrito = data.get("Distrito");
		String Comisionista = data.get("Comisionista");
		String Tienda = data.get("Tienda");
		u = new SeleniumUtil(new MEdgeTest(), true);
		Pantalla_Simulador ps = new Pantalla_Simulador (u,testCase);
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al portal de aplicaciones "+url+" "); 
		
	    PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Capturar un usuario y contrase�a validos	"); 
		
		pa.proceedToPortal(); 
		pa.cicloLogin(user, password);
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Seleccionar en el listado de aplicaciones Finiquito en Tienda"); 
		ps.clickContainer();
	
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("En el menu superior seleccionar Finiquito y posteriormente clic en Simulador de Finiquito");
		ps.MostrarSimulador();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("En la pantalla Simulador de Comisionistas Mercantil clic en bot�n Siguiente");
		ps.clicBtnSimulador();
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("En la pantalla de Instrucciones Simulador clic en bot�n Siguiente");
		ps.clicBtnInstruccionesSimulador();
		
		/*
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Seleccionar los criterios de busqueda");
		ps.capturaCamposSimuladorFiniquitos(Plaza, Distrito, Comisionista, Tienda);
		
		/*
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Validar en BD que los comisionistas desplegados en pantalla concuerden con la BD");
		utils.sql.SQLUtil dbFCIAS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		String query;
		query =  "SELECT NOMBRE_COMISIONISTA FROM XXCO_FEP.XXCO_FEP_COMISIONES WHERE DESC_DISTRITO = '10GUD Guadalajara'";	
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
				if (!StatusQuery) {
					testCase.addTextEvidenceCurrentStep("Existen los comisionistas en la BD");
				} else {	
					assertTrue(false, "No se encontraron comisionistas");
	}
		
        	
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
		return null;
	}

	@Override
	public String setTestDesigner() {
		return"AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Comisionistas_Simulador_Finiquitos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
