package WEB.portal_Remesas.scripts;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.portal_Remesas.om.Portal_Remesas;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Remesas_Envios_de_Dinero_Reporte_de_Montos_Excel extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Remesas_Envios_de_Dinero_Reporte_de_Montos_Excel_test(HashMap<String, String> data) {
		
		/*
		 * Variables
		 ******************************************************************************************/
		u = new SeleniumUtil(new MEdgeTest(), true);
//		u = new SeleniumUtil(new TestBrowser() {
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
		Portal_Remesas portal = new Portal_Remesas(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password")); 
		String URL = data.get("Url");
		String fechaInicio = data.get("fecha_inicio");
		String fechaFin = data.get("fecha_fin");
		
		
//		// Paso 1
//		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
//		u = new SeleniumUtil(new MEdgeTest(), true);
//		u.get(URL);
//		Login pa = new Login(u, testCase);		
//		Portal_Remesas portal = new Portal_Remesas(u, testCase);
//		testCase.addScreenShotCurrentStep("Imagen de login");
//		MetodosExtra me = new MetodosExtra(u, testCase);
//		
//		// Paso 2
//		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
//		u.hardWait(5);
//		pa.logginPlatform(user, password);
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("El usuario ingresa a la aplicaci�n Access Control V3"); 
		
		u.getDriver().manage().window().maximize();
	    u.get(URL);
		u.hardWait(3); 
		pa.proceedToPortal();
		u.hardWait(2); 
		testCase.addScreenShotCurrentStep(u, "ingresarLiga");
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�");
		pa.cicloLogin(user, password);
		
		// Paso 3
		addStep("Ingresar a la pesta�a de Env�os de Dinero, Reportes, Montos");
		portal.seleccionaPortalRemesas();
		
		// Paso 4
		portal.EnviosDinero_Reportes_Montos_notSubmenu();
		
		// Paso 5
		addStep("Ingresar Fecha de inicio, Ingresar Fecha Fin, No mayor a 30 d�as, Selecconar una Zona OXXO, Seleccionar CR Plaza, Seleccionar CR Tienda, Seleccionar un Proveedor");
		portal.llenarReportePortalMontos(fechaInicio,fechaFin);
		//portal.llenarReportePortalMontos(fechaini,fechafin,zonaOxxo,CrPlaza,CrTienda,Proveedor);
		
		// Paso 6
		addStep("Seleccionar la opci�n En Excel");
		portal.generaReporte("excel");
		u.waitForLoadPage();
		u.hardWait(10);
		
		// paso 7
		addStep("Presionar Generar Reporte");
		assertTrue(me.abrirArchivo("reporteMontos","Edge"));//Se cambió para que no tome el primer parametro y muestre el único archivo descargado
		
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
		return "Validar generación de excel de Envíos de Dinero Reporte de Montos";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}