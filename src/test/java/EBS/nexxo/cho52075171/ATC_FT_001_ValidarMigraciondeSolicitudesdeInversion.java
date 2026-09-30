package EBS.nexxo.cho52075171;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarMigraciondeSolicitudesdeInversion extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidarMigraciondeSolicitudesdeInversion_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */

		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String campo = data.get("campo");

		/*
		 * UTILERIAS
		 */

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52075171
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.scroll();
		testCase.addScreenShotCurrentStep(u,
				"Ingresamos en la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'");
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		paginaInicial.clickcargaarchivosr();
		testCase.addScreenShotCurrentStep(u, "Seleccion de carga de archivos");
		// PASO 7
		addStep("Desplegar el campo Proceso y verificar exista el proceso 'Migracion de Solicitudes de Inversion'");

		u.sendKeys(paginaInicial.cajadebusqueda(), campo);
		paginaInicial.clicklupabusqueda();
		// creamos una instancia de la pantalla primigenia
		String originalWindow = u.getDriver().getWindowHandle();
		// Check we don't have other windows open already
		assert u.getDriver().getWindowHandles().size() == 1;
		// esperamos
		u.hardWait(3);
		// encontramos las ventanas hijas
		for (String windowHandle : u.getDriver().getWindowHandles()) {
			if (!originalWindow.contentEquals(windowHandle)) {
				u.getDriver().switchTo().window(windowHandle);
				break;
			}
		}
		// esperamos a que termine de cargar
		WebElement frame = u.getDriver().findElement(paginaInicial.newframe());
		u.waitForLoadPage();
		u.getDriver().switchTo().frame(frame);
		testCase.addTextEvidenceCurrentStep("Validated: " + u.getText(paginaInicial.tabla()));
		assertEquals(u.getText(paginaInicial.tabla()), campo);
		testCase.addScreenShotCurrentStep(u, "Validacion exitosa");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarMigraciondeSolicitudesdeInversion_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar instalacion del proceso 'Migracion de Solicitudes de Inversion' dentro de la pantalla 'Carga de Archivos' en responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'. Pre-requisistos: Acceso a la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'.";
	}

	@Override
	public void beforeTest() {

	}
}
