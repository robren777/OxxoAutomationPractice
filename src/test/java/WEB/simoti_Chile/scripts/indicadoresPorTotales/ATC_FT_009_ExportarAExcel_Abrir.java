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
//import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc:Validar que se genere y exporte excel
* MTC_FT_203.- Indicadores por Totales_Exportar a excel_abrir
* @author Marichuy Due�as/Pablo Pozos
* @date Noviembre 2022
*/
public class ATC_FT_009_ExportarAExcel_Abrir extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_ExportarAExcel_Abrir_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		//u = new SeleniumUtil(new ChromeTest(), true);
		u = new SeleniumUtil(new MEdgeTest(), true); // IETest para usar internet explorer
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
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza selecci�n de valores en los filtros: " + 
				"Plaza: <se muestra la Plaza del usuario> " + 
				"Mes: <seleccionar valor> " + 
				"A�o: <Seleccionar valor> " + 
				"Dar clic en [Consultar]");

		validacion = it.selectMes(data.get("Mes"));
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
		
		it.clickConsultarBtn();
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario da clic en la opci�n: [Exportar]");
		
		it.clickExportarBtn();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Abrir]");
		u.hardWait(5);
		it.abrirArchivoExcel("Reporte_","Edge");


		/**
		 *                                             PASO 8
		 ******************************************************************************************/

		addStep("El usuario abre el documento excel que guardo y se deben mostrar de la siguiente manera>\n" +
				"-En el encabezado se muestra:\n" +
				"SIMOTI\n" +
				"Integridad de Datos\n" +
				"Reporte de Indicadores por Totales\n" +
				"- Secci�n de filtros:\n" +
				"Se deben de mostrar los mismos filtros del reporte, con los datos con los que se realiz� la consulta.\n" +
				"-Se deben mostrar las columnas:\n" +
				"1. No. \n" +
				"2. Color \n" +
				"3. D�as\n");
		u.hardWait(5);
		assertTrue(it.validarDatoss("Reporte_Indicadores_Totales_",""),"Los datos exportados no coinciden.");


		/**
		 *                                             PASO 9
		 ******************************************************************************************/

		addStep("El usuario valida que las columnas que se muestran el excel sean acorde a la siguiente regla:\n" +
				"Regla nombrado de columnas en Tabla de resultados y archivo Excel:\n" +
				"1. Si el campo tiene un solo nombre, la primera letra deber� est�r en may�sculas el resto en min�sculas, respetando acentuaci�n.\n" +
				"2. Si el campo tiene dos o m�s nombres, la primera letra de cada palabra deber� est�r en may�sculas y el resto en min�sculas se parados por un espacio � � , respetando acentuaci�n. Por ejemplo: �Fecha de Recepci�n�, �Archivo Entrada�, �Fecha Orden�.\n" +
				"3. Si el campo tiene por nombre un identificador, siglas o un valor abreviado todas van en may�sculas, separados por un �_�, sin acentuarse. Por ejemplo: �SOURCE_ID�, �IFE�, �Folio WM�.\n");

		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("El usuario va a la pantalla de consulta y nuevamente selecciona otros valores en los filtros, de manera que NO deber� dar clic en Consultar. Abrir nuevamente el excel.\n");
		u.getDriver().navigate().refresh();
		//u.hardWait(3);

		it.selectMes(data.get("Mes 2"));

		it.selectAnio(data.get("Anio 2"));
		assertTrue(validacion, "No se puede seleccionar el ano proporcionado");

		it.abrirArchivoExcel("Reporte_Indicadores","Edge");


		/**
		 *                                             PASO 11
		 ******************************************************************************************/

		addStep("El usuario va a la pantalla de consulta y selecciona otros valores en los filtros y da clic en Consultar\n");
		u.hardWait(3);
		it.selectMes(data.get("Mes 2"));

		it.selectAnio(data.get("Anio 2"));
		assertTrue(validacion, "No se puede seleccionar el ano proporcionado");
		it.clickConsultarBtn();

		/**
		 *                                             PASO 12
		 ******************************************************************************************/

		addStep("El usuario da clic en Exportar.\n");
		it.clickExportarBtn();

		/**
		 *                                             PASO 13
		 ******************************************************************************************/

		addStep("El usuario da clic en [Abrir]");
		it.abrirArchivoExcel("Reporte_Indicadores","Edge");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_009_ExportarAExcel_Abrir";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_ExportarAExcel_Abrir_test";
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