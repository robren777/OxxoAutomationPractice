package EBS.cajasInteligentes.cho52073965;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarinstalacionpantallaCargadeArchivos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarinstalacionpantallaCargadeArchivos_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String campoarchivo = data.get("carchivo");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSCargarArchivos cargarArchivos = new EBSCargarArchivos(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52073965
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-FNDXXXXX-Usuario carga masiva'");
		testCase.addScreenShotCurrentStep(u, "RESPONSABILIDAD MXOXXO-FNDXXXXX-Usuario carga masiva");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		testCase.addScreenShotCurrentStep(u, "Seleccionar: Carga de Archivos");
		assertEquals(cargarArchivos.getTipoText(), campoarchivo);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarinstalacionpantallaCargadeArchivos_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la correcta instalacion de la pantalla 'Carga de Archivos' dentro de la responsabilidad 'MXOXXO-FNDXXXXX-Usuario carga masiva' Pre-requisitos: Contar con acceso a la responsabilidad 'MXOXXO-FNDXXXXX-Usuario carga masiva'";
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
		return "Contar con acceso a la responsabilidad 'MXOXXO-FNDXXXXX-Usuario carga masiva'";
	}

	@Override
	public void beforeTest() {

	}
}