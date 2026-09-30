package WEB.simoti_Chile.scripts.indicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Chile.om.IndicadoresPorTotales_SimotiChile;
import WEB.simoti_Chile.om.MenuPrincipal_SimotiChile;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validar que se muestren todos los componentes correctos para el reporte.
* MTC_FT_001. Validar Elementos de la Pantalla
* @author Marichuy Due�as
* @date Octubre 2022
*/
public class ATC_FT_001_Validar_elementos_de_la_Pantalla extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validar_elementos_de_la_Pantalla_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiChile mp = new MenuPrincipal_SimotiChile(u, testCase);
		IndicadoresPorTotales_SimotiChile it = new IndicadoresPorTotales_SimotiChile (u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opci�n de SIMOTI.");
		
		pa.clickSimotiChile();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores"
				+ " por Totales.");
		
		mp.clickIntegridadDatos();
		u.hardWait(3);
		mp.clickIndicadoresTotales();
		validacion = it.validarPantalla();
		assertTrue(validacion, "No se muestran correctamente los elemntos de la pantalla");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario valida el nombre de los encabezados de la tabla en la secci�n resultados se mostrar� de acuerdo a la Regla de nombrado de columnas en Tabla de resultados y archivo Excel:\r\n" + 
				"1. Si el campo tiene un solo nombre, la primera letra deber� est�r en may�sculas el resto en min�sculas, respetando acentuaci�n.\r\n" + 
				"2. Si el campo tiene dos o m�s nombres, la primera letra de cada palabra deber� est�r en may�sculas y el resto en min�sculas separados por un espacio � � , respetando acentuaci�n. Por ejemplo: �Fecha de Recibido\".\r\n" + 
				"3. Si el campo tiene por nombre un identificador, siglas o un valor abreviado todas van en may�sculas, separados por un �_�, sin acentuarse. Por ejemplo: �ENV_ID�.\r\n" + 
				"Resultados:\r\n" + 
				"1. No.\r\n" + 
				"2. Color\r\n" + 
				"3. D�as\r\n" );

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
		return "Construido. ATC_FT_001_Validar_elementos_de_la_Pantalla";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validar_elementos_de_la_Pantalla_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
