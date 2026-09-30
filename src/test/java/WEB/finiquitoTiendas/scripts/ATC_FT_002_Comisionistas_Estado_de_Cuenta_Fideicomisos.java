package WEB.finiquitoTiendas.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.finiquitoTiendas.om.Pantalla_Fideicomisos;
import WEB.om.PortalApps;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**Desc: Validar los comisionistas activos en estado de cuenta fideicomisos
* MTC_FT_002. Comisionistas Estado de Cuenta Fideicomisos
* @author Yair Lizarraga
* @date Diciembre 2022
*/
public class ATC_FT_002_Comisionistas_Estado_de_Cuenta_Fideicomisos extends BaseExecution { 
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Comisionistas_Estado_de_Cuenta_Fideicomisos_test (HashMap<String, String> data) throws Exception {

		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = GlobalVariables.URL_PORTAL_APPS_SAR;
		String Plaza = data.get("Plaza");
		String Distrito = data.get("Distrito");
		String Comisionista = data.get("Comisionista");
		u = new SeleniumUtil(new MEdgeTest(), true);
		Pantalla_Fideicomisos pf = new Pantalla_Fideicomisos (u,testCase);
		
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
		pf.clickContainer();
	
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("En el menu superior seleccionar Finiquito y posteriormente clic en Estado Cuenta Fideicomisos");
		pf.MostrarEstadoFideicomiso();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Seleccionar los criterios de busqueda  Plaza, Distrito");
		pf.capturaCamposFideicomiso(Plaza, Distrito, Comisionista);
		
		/*
		 *                                             PASO 6
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
		return "ATC_FT_002_Comisionistas_Estado_de_Cuenta_Fideicomisos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
