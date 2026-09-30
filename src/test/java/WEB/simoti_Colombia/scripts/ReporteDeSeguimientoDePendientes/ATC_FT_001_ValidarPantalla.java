package WEB.simoti_Colombia.scripts.ReporteDeSeguimientoDePendientes;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.ReporteDeSeguimientoDePendientes_SimotiColombia;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que se muestren todos los componentes correctos para el reporte.
 * MTC-FT-001 Plaza_Reporte de Seguimiento de Pendientes_Validar Pantalla
 * @author Marichuy Due�as
 * @date Noviembre 2022*/
 public class ATC_FT_001_ValidarPantalla extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarPantalla_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = GlobalVariables.USER_PORTAL_APPS_6006020;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_6006020;
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ReporteDeSeguimientoDePendientes_SimotiColombia rsp = new ReporteDeSeguimientoDePendientes_SimotiColombia (u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
		u.get(url);
		u.hardWait(3);
		pa.proceedToPortal();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		
		pa.logginInventarioEspacios(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opci�n de SIMOTI.");
		
		pa.clickSimotiColombia();
		boolean validarMenusCedis = mp.validarMenuUsuarioCedis();	
		assertTrue(validarMenusCedis, "No se muestran los menus especificados");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: Transmisi�n de Datos y selecciona el reporte: Reporte "
				+ "Promociones");
		
		mp.clickTransmisionDatos();
		u.hardWait(3);
		mp.clickReportePromociones();
		validacion = rsp.validarPantalla();
		assertTrue(validacion, "No se muestran correctamente los elementos de la pantalla");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario valida el nombre de los encabezados de la tabla en la secci�n resultados se mostrar� de acuerdo a la Regla de nombrado de columnas en Tabla de resultados\r\n" + 
				"Si el campo tiene un solo nombre, la primera letra deber� estar en may�sculas el resto en min�sculas, respetando acentuaci�n.\r\n" + 
				"Si el campo tiene dos o m�s nombres, la primera letra de cada palabra deber� estar en may�sculas y el resto en min�sculas se parados por un espacio � � , respetando acentuaci�n. Por ejemplo: �Fecha de Recepci�n�, �Archivo Entrada�, �Fecha Orden�.\r\n" + 
				"Se deberan mostrar los siguientes campos con las siguientes caracteristicas\r\n" + 
				"No. (Tipo de Dato: Number)\r\n" + 
				"Promoci�n (Tipo de Dato: Number)\r\n" + 
				"Descripci�n (Tipo de Dato: String)\r\n" + 
				"Fecha Inicio (Tipo de dato: Date dd/mm/aaaa)\r\n" + 
				"Fecha Fin (Tipo de dato: Date dd/mm/aaaa)\r\n" + 
				"");

		validacion = rsp.validarEncabezados();
		assertTrue(validacion, "No se muestran correctamente los encabezados de la tabla secci�n de resultados");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario valida que se muestre en la parte inferior de la secci�n de resultados, "
				+ "se deben mostrar el control del p�ginado de manera que se muestren los bot�n es "
				+ "para ir adelante, atr�s, final, inicio, n�mero de p�ginas");
		
		validacion = rsp.validarBotonespaginado();
		assertTrue(validacion, "No se muestra correctamente la parte inferior de la secci�n de resultados");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar que se muestren todos los componentes correctos para el reporte.";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarPantalla_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return  "1.- Tener navegador: Internet Explorer 11.\r\n" + 
				"2.- Tener el link de la herramienta: \r\n" + 
				"3.- Contar con un usuario de la red de OXXO, as� mismo este usuario deber� est�r asociado a un perfil de acceso a Plaza.";
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}