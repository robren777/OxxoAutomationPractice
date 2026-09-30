package WEB.simoti_Colombia.scripts.indicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.IndicadoresPorTotales_SimotiColombia;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_016_ExportarAExcel_Guardar  extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_ExportarAExcel_Guardar_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
//u = new SeleniumUtil(new TestBrowser() {
//			
//			@Override
//			public WebDriver initDriver(boolean arg0) {
//				// TODO Auto-generated method stub
//				System.setProperty(
//			            "webdriver.chrome.driver",
//			            "C:\\Users\\1000039886\\git\\FrameworkWeb\\Config\\Drivers\\Chrome\\Windows\\112.exe");
//			 
//			 ChromeOptions chromeOptions = new ChromeOptions();
//			 chromeOptions.addArguments("--remote-allow-origins=*");
//			 
//			        // Instantiate a ChromeDriver class.
//			        WebDriver driver = new ChromeDriver(chromeOptions);
//			 
//			        // Maximize the browser
//			        driver.manage().window().maximize();
//			        return driver;
//			}
//		}, true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		IndicadoresPorTotales_SimotiColombia it = new IndicadoresPorTotales_SimotiColombia (u, testCase);
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
	
		addStep("El usuario ingresa usuario y contraseña y da clic en el botón <Iniciar Sesión>.");
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opción de SIMOTI.");
		
		pa.clickSimotiColombia();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el menú: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores"
				+ " por Totales.");
		
		u.hardWait(1);
		mp.clickIntegridadDatos();
		u.hardWait(3);
		mp.clickIndicadoresTotales();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Consultar]");

		validacion = it.selectMes(data.get("Mes"));
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el año proporcionado");
		
		it.clickConsultarBtn();
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario da clic en la opción: [Exportar]");
		
		it.clickExportarBtn();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Guardar]");
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
				+ "- Sección de filtros:\r\n"
				+ "Se deben de mostrar los mismos filtros del reporte, con los datos con los que se realizó la consulta.\r\n"
				+ "-Se deben mostrar las columnas:\r\n"
				+ "1. No. (Tipo de dato: Number, debe ser consecutivo)\r\n"
				+ "2. Color \r\n"
				+ "3. Días (Tipo de dato: Number)\r\n"
				+ "**Validar que se muestren los dias del mes y por cada día un color representando el indicador.");
		
		assertTrue(it.validarDatosTablaExcel("Reporte_Indicadores_Totales_",""),"Los datos exportados no coinciden.");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_016_ExportarAExcel_Guardar_CEDIS";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_016_ExportarAExcel_Guardar_CEDIS_test";
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