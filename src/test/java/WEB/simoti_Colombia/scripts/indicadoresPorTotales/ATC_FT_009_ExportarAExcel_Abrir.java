package WEB.simoti_Colombia.scripts.indicadoresPorTotales;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.IndicadoresPorTotales_SimotiColombia;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

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
		u = new SeleniumUtil(new ChromeTest(), true);
		
		PortalApps pa = new PortalApps(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		IndicadoresPorTotales_SimotiColombia it = new IndicadoresPorTotales_SimotiColombia (u, testCase);
		Boolean validacion = false;
		
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
	
		addStep("El usuario ingresa usuario y contraseï¿½a y da clic en el botï¿½n ï¿½Iniciar Sesiï¿½nï¿½.");
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opciï¿½n de SIMOTI.");
		
		pa.clickSimotiColombia();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el menï¿½: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores"
				+ " por Totales.");
		
		mp.clickIntegridadDatos();
		u.hardWait(3);
		mp.clickIndicadoresTotales();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario selecciona los siguientes valores: " + 
				"*Mes: <mayor a mes actual>" + 
				"*Aï¿½o: <aï¿½o actual>. El sistema aplica la validaciï¿½n:" + 
				"'Estimado usuario no se permite la selecciï¿½n de una fecha posterior a la actual'");
		
		if(data.get("Plaza")!=null)
		{
			validacion = it.selectPlaza(data.get("Plaza"));
			assertTrue(validacion, "No se puede seleccionar la plaza");
		}
		
		it.selectMes(data.get("Mes"));
		
		it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el aï¿½o proporcionado");
		
		it.clickConsultarBtn();
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario da clic en la opciï¿½n: [Exportar]");
		
		it.clickExportarBtn();
		
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Abrir]");
		u.hardWait(3);
		it.selectPlaza(data.get("Plaza"));
		it.selectMes(data.get("Mes"));
		it.selectAnio(data.get("Anio"));
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario abre el documento excel que guardo y se deben mostrar de la siguiente manera>\r\n"
				+ "-En el encabezado se muestra:\r\n"
				+ "SIMOTI\r\n"
				+ "Integridad de Datos\r\n"
				+ "Reporte de Indicadores por Totales\r\n"
				+ "- SecciÃ³n de filtros:\r\n"
				+ "Se deben de mostrar los mismos filtros del reporte, con los datos con los que se realizÃ³ la consulta.\r\n"
				+ "-Se deben mostrar las columnas:\r\n"
				+ "1. No. \r\n"
				+ "2. Color \r\n"
				+ "3. DÃ­as");

		it.clickConsultarBtn();
		u.hardWait(6);
		assertTrue(it.validarDatosTablaExcel("Reporte_Indicadores_Totales_",""),"Los datos exportados no coinciden.");
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El usuario valida que las columnas que se muestran el excel sean acorde a la siguiente regla:\r\n"
				+ "Regla nombrado de columnas en Tabla de resultados y archivo Excel:\r\n"
				+ "1. Si el campo tiene un solo nombre, la primera letra deberÃ¡ estÃ¡r en mayÃºsculas el resto en minÃºsculas, respetando acentuaciÃ³n.\r\n"
				+ "2. Si el campo tiene dos o mÃ¡s nombres, la primera letra de cada palabra deberÃ¡ estÃ¡r en mayÃºsculas y el resto en minÃºsculas se parados por un espacio â€œ â€� , respetando acentuaciÃ³n. Por ejemplo: â€œFecha de RecepciÃ³nâ€�, â€œArchivo Entradaâ€�, â€œFecha Ordenâ€�.\r\n"
				+ "3. Si el campo tiene por nombre un identificador, siglas o un valor abreviado todas van en mayÃºsculas, separados por un â€œ_â€�, sin acentuarse. Por ejemplo: â€œSOURCE_IDâ€�, â€œIFEâ€�, â€œFolio WMâ€�.");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("El usuario va a la pantalla de consulta y nuevamente selecciona otros valores en los filtros, de manera que NO deberÃ¡ dar clic en Consultar. Abrir nuevamente el excel.");
		u.hardWait(3);
		it.selectPlaza(data.get("Plaza"));
		it.selectMes(data.get("Mes 2"));
		
		it.selectAnio(data.get("Anio 2"));
		assertTrue(validacion, "No se puede seleccionar el aï¿½o proporcionado");
		

		it.clickExportarBtn();
		assertFalse(it.validarDatosTablaExcel("Reporte_Indicadores_Totales_"," (1)"),"Los datos exportados coinciden.");
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("El usuario va a la pantalla de consulta y selecciona otros valores en los filtros y da clic en Consultar");
		u.hardWait(3);
		it.selectPlaza(data.get("Plaza"));
		it.selectMes(data.get("Mes 2"));
		
		it.selectAnio(data.get("Anio 2"));
		assertTrue(validacion, "No se puede seleccionar el aï¿½o proporcionado");
		it.clickConsultarBtn();
		mp.waitLoading2();
		u.hardWait(5);
		boolean valResults = it.existenResultados();
		
		assertTrue(valResults, "El sistema no muestra informacion");
		
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("El usuario da clic en la opciÃ³n: [Exportar]");

		it.clickExportarBtn();
		mp.waitLoading2();
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/

		addStep("El usuario da clic en: [Abrir]");
		
		me.abrirArchivo("Reporte_Indicadores", "Chrome");
		u.hardWait(3);
		it.selectPlaza(data.get("Plaza"));
		it.selectMes(data.get("Mes 2"));
		it.selectAnio(data.get("Anio 2"));
		it.clickConsultarBtn();
		mp.waitLoading();
		u.hardWait(5);
		String fecha = me.obtenerFechaActual("ddMMyyyy");
		//assertTrue(it.validarDatosTablaExcel("Reporte_Indicadores_Totales_"," (2)"),"Los datos exportados no coinciden.");
		assertTrue(it.validarDatosTablaExcel("Reporte_Indicadores_Totales_",""),"Los datos exportados no coinciden.");
	
	
	
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
		//u.getDriver().quit();
	}
}