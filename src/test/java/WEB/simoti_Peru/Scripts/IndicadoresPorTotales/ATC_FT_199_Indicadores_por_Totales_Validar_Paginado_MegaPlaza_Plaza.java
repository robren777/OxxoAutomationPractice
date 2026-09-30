package WEB.simoti_Peru.Scripts.IndicadoresPorTotales;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Peru.om.MenuPrincipal_SimotiPeru;
import WEB.simoti_Peru.om.Simoti_Peru;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_199_Indicadores_por_Totales_Validar_Paginado_MegaPlaza_Plaza extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_199_Indicadores_por_Totales_Validar_Paginado_MegaPlaza_Plaza_Test(HashMap<String, String> data)
			throws Exception {
		String cp = data.get("CP");
		testCase.setTest_Description(cp);
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String mes = data.get("mes");
		String anio = data.get("anio");
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiPeru mp = new MenuPrincipal_SimotiPeru(u, testCase);
		Simoti_Peru it = new Simoti_Peru (u, testCase);
		Boolean validacion;
		

		 /**
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al portal de aplicaciones e Ingresar el usuario, password y dar clic en entrar"); // viene //
																											// excel
		u.get(url); 
		u.hardWait(3); // tiempo en segundos
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal(); // es para lo de la pagina no segura
		
		 /**
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.cicloLogin(user, password);
		
		 /**
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.clickPortalSIMOTI_PERU();
		 /**
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("El usuario despliega el menú: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores por Totales.");
		it.MAX();
		it.clickIntegridadDatos();
		u.hardWait(3);
		it.clickIndicadoresPorTotales();
		
		 /**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza selecci�n de valores en los filtros:" + 
				"Plaza: <se muestra la Plaza del usuario>" + 
				"Mes: <seleccionar valor>" + 
				"A�o: <Seleccionar valor>" + 
				"Dar clic en [Consultar]");
		
		//it.seleccionarFiltros(selMes, selAnio);
		it.selectMes(mes);
		it.selectAnio(anio);
		u.hardWait(2);
		it.clickBtnConsultar();
		
		
		 /**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario selecciona la siguiente p�gina de resultados, ir hacia atr�s, adelante de las p�ginas." + 
				"Debe cumplir con lo siguiente:" + 
				"1. p�ginado con 10 registros por p�gina." + 
				"2. Numeraci�n de p�ginas y p�gina donde se encuentra ubicado el usuario." + 
				"3. Total de registros encontrados en la consulta del reporte. ");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_199_Indicadores_por_Totales_Validar_Paginado_MegaPlaza_Plaza";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}

}
