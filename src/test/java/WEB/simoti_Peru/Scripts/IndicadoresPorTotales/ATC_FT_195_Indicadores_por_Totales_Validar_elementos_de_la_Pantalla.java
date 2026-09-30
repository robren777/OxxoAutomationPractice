package WEB.simoti_Peru.Scripts.IndicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Peru.om.MenuPrincipal_SimotiPeru;
import WEB.simoti_Peru.om.Simoti_Peru;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validar que se muestren todos los componentes correctos para el reporte.
* MTC_FT_195.- Indicadores por Totales_Validar elementos de la Pantalla
* @author Herbe Cauich
* @date Octubre 2022
*/
public class ATC_FT_195_Indicadores_por_Totales_Validar_elementos_de_la_Pantalla extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_195_Indicadores_por_Totales_Validar_elementos_de_la_Pantalla_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new MEdgeTest(), true);
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
		addStep("El usuario despliega el menu: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores por Totales.");
		it.MAX();
		it.clickIntegridadDatos();
		u.hardWait(3);
		it.clickIndicadoresPorTotales();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario valida el nombre de los encabezados de la tabla en la secci�n resultados se mostrar� de acuerdo a la Regla de nombrado de columnas en Tabla de resultados y archivo Excel:" + 
				"1. Si el campo tiene un solo nombre, la primera letra deber� est�r en may�sculas el resto en min�sculas, respetando acentuaci�n.\r\n" + 
				"2. Si el campo tiene dos o m�s nombres, la primera letra de cada palabra deber� est�r en may�sculas y el resto en min�sculas separados por un espacio � � , respetando acentuaci�n. Por ejemplo: �Fecha de Recibido\"." + 
				"3. Si el campo tiene por nombre un identificador, siglas o un valor abreviado todas van en may�sculas, separados por un �_�, sin acentuarse. Por ejemplo: �ENV_ID�." + 
				"Resultados:" + 
				"1. No." + 
				"2. Color" + 
				"3. D�as" );

		validacion = it.validarEncabezados();
		assertTrue(validacion, "Los encabezados no son correctos");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario valida que se muestre en la parte inferior de la secci�n de resultados, "
				+ "se deben mostrar el control del p�ginado de manera que se muestren los bot�n es "
				+ "para ir adelante, atr�s, final, inicio, n�mero de p�ginas,");
		
		validacion = it.validarBotonespaginado();
		assertTrue(validacion, "No se muestran los botones de paginado");

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
		return "ATC_FT_195_Indicadores_por_Totales_Validar_elementos_de_la_Pantalla_test";
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
