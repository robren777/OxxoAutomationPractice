package WEB.portal_Remesas.scripts;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.portal_Remesas.om.Portal_Remesas;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_Remesas_Envios_de_Dinero_Reporte_de_Montos_Pantalla extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Remesas_Envios_de_Dinero_Reporte_de_Montos_Pantalla_test(HashMap<String, String> data) {
		
//		/*
//		 * Variables
//		 ******************************************************************************************/
//		String user = data.get("User");
//		String password = data.get("Password");
//		String URL = data.get("Url");
//		
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
		 * Variables
		 ******************************************************************************************/
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		Portal_Remesas portal = new Portal_Remesas(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password")); 
		String URL = data.get("Url");
		String fechaInicio = data.get("fecha_inicio");
		String fechaFin = data.get("fecha_fin");
		
		
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
		addStep("Seleccionar la opci�n En Pantalla");
		u.takeScreenshot("opcion");
		
		// paso 7
		addStep("Presionar Generar Reporte");
		portal.generaReporte("pantalla");
		//Se valida que almenos haya un registro en la tabla
		u.waitForLoadPage();
		u.hardWait(10);
		u.highLight(By.xpath("//tr[1]/td[contains(@class, 'estiloCabecera')][2]"));
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
		return "Validar mostrar en pantalla Reporte de Envíos de Dinero Reporte de Montos";
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