package WorkFlows.Devoluciones;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Devoluciones.utilsDevoluciones;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_CargasMasivas extends BaseExecution {
	
	private SeleniumUtil u;
	private utilsDevoluciones utilDev;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_CargasMasivas_Test(HashMap<String, String> data) 
			throws Exception {
		
		testCase.setProject_Name("ATC_FT_006_Cargas Masivas");
		/**
		 * Declaraci�n de variables
		 */
		String urlPEGA = GlobalVariables.urlPEGA;
		String user = GlobalVariables.userAdminDevoluciones;
		/**
		 * Se abre el navegador (Edge)
		 */
		u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		/**
		 * Se inicializan los utils por utilizar
		 */
		utilDev = new utilsDevoluciones(u, testCase);
		/**
		 * Paso 1
		 */
		addStep("Abrir PEGA");
		u.getDriver().manage().window().maximize();
		u.getDriver().manage().deleteAllCookies();
		u.get(urlPEGA);
		testCase.addScreenShotCurrentStep(u, "Resultado");
		/**
		 * Paso 2
		 */
		addStep("Ingresa usuario correctamente");
		utilDev.loginPom(user);
		/**
		 * Paso 3
		 */
		addStep("Aplicativos registrados");
		utilDev.appsWeb();
		/**
		 * Paso 4
		 */
		addStep("Aplicativo \"Portal Devoluciones\" disponible");
		utilDev.portalDevText();
		/**
		 * Paso 5
		 */
		addStep("Abrir Portal Devoluciones");
		utilDev.portalDev();
		u.hardWait(3);
		/**
		 * Paso 6
		 * Comparativa de los elementos presentes en el aplicativo seleccionado
		 */
		addStep("Portal Devoluciones - Elementos");
		testCase.addBoldTextEvidenceCurrentStep("Encabezado:");
		testCase.addTextEvidenceCurrentStep("Version - Fecha - Rol - Usuario");
		testCase.addBoldTextEvidenceCurrentStep("Menus:");
		testCase.addTextEvidenceCurrentStep("Inicio - Reportes - Configuracion - Devoluciones masivas");
		testCase.addScreenShotCurrentStep(u, "Resultado");
		/**
		 * Paso 7
		 */
		addStep("Cargas Reple");
		utilDev.mouseHover("Cargas Reple");
		/**
		 * Paso 8
		 */
		addStep("Subir archivo");
		utilDev.subirArchivo();
		/**
		 * Paso 9
		 */
		addStep("Comprobar que se generó la información");
		u.hardWait(2);
		utilDev.waitProcessing();
		utilDev.gridFolios("Carga Reple", null);
		utilDev.waitProcessing();
		testCase.addScreenShotCurrentStep(u, "Carga");
	}

	@AfterTest
	public void afterTest() {
		u.getDriver().quit();
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Usuario administrador "
				+ "2. Acceso a PEGA "
		+ "3. Archivo excel de cargas masivas";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar el correcto funcionamiento de las Cargas Reple dentro del Portal Devoluciones";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Karim Duran";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_CargasMasivas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}