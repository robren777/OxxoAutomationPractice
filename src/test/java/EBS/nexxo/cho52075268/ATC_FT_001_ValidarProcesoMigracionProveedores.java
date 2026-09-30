package EBS.nexxo.cho52075268;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarProcesoMigracionProveedores extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarProcesoMigracionProveedores_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String validacion = data.get("validacion");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSCargarArchivos cargarArchivos = new EBSCargarArchivos(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52075268
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'");
		testCase.addScreenShotCurrentStep(u, "RESPONSABILIDAD MXOXXO-FNDXXXXX-Usuario carga masiva");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		paginaInicial.clickcargaarchivosr();
		testCase.addScreenShotCurrentStep(u, "Seleccionar: Carga de Archivos");
		// PASO 7
		addStep("Desplegar el campo 'Proceso' para verificar No se muestre el proceso 'Migracion de Proveedores'.");
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
		cargarArchivos.clickIr();
		assertEquals(cargarArchivos.getTextNombreMigracion(), validacion);
		testCase.addScreenShotCurrentStep(u,
				"Validacion para verificar se muestre el proceso 'Migracion de Proveedores'.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarProcesoMigracionProveedores_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar Acceso para Visualizar el Proceso 'Migracion de Proveedores' dentro de la Pantalla de Carga de Archivos con Usuario configurado en Mapeos Varios. Pre-requisitos: 1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'."
				+ "2.- Usuario Asignado configurado en la tabla 'xxfc_mapeos_varios' con tipo_mapeo = 'XXFC_FUS_NEXXO' , salida1 = 'XXFC_AP_MIGRA_PROV’.";
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
		return "1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'."
				+ "2.- Usuario Asignado configurado en la tabla 'xxfc_mapeos_varios' con tipo_mapeo = 'XXFC_FUS_NEXXO' , salida1 = 'XXFC_AP_MIGRA_PROV’.";
	}

	@Override
	public void beforeTest() {

	}
}