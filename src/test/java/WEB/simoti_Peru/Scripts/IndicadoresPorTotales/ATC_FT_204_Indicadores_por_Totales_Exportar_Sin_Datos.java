package WEB.simoti_Peru.Scripts.IndicadoresPorTotales;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.simoti_Peru.om.MenuPrincipal_SimotiPeru;
import WEB.simoti_Peru.om.Simoti_Peru;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validar que se muestre el mensaje de error cuando se intenta exportar y no existen datos de la consulta.
* MTC_FT_204.- Indicadores por Totales_Exportar sin datos
* @author Herbe Cauich
* @date Noviembre 2022
*/
public class ATC_FT_204_Indicadores_por_Totales_Exportar_Sin_Datos extends BaseExecution {

	SeleniumUtil u;
	MetodosExtra me;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_204_Indicadores_por_Totales_Exportar_Sin_Datos_test(HashMap<String, String> data)
			throws Exception {
		String cp = data.get("CP");
		testCase.setTest_Description(cp);
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String mes = data.get("mes");
		String anio = data.get("anio");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
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
		
		addStep("Es necesario que no existan datos en la consulta:" + 
				"Plaza: <se muestra la Plaza del usuario>" + 
				"Mes: <junio>" + 
				"A�o: <2016>" + 
				"Dar clic en [Consultar]");
		
		it.selectMes(mes);
		u.hardWait(3);
		it.selectAnio(anio);
		//it.clickBtnConsultar();
		//it.Loading();
		//u.hardWait(2);
		
		 /**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Exportar]");
		
		it.clickBtnExportar();
		it.Loading();
		//it.MensajeSinInformacionExportar();

		

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
		return "ATC_FT_204_Indicadores_por_Totales_Exportar_Sin_Datos_test";
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
