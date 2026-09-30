package EBS.cajasInteligentes.cho52070996;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_CONSULTAS.SelectorTituloCargadeArchivos;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Validarlainstalaciondepantallacarga extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validarlainstalaciondepantallacarga_test(HashMap<String, String> data) throws Exception {

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
		SelectorTituloCargadeArchivos carga = new SelectorTituloCargadeArchivos(u, testCase);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52070996
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
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		assertEquals(carga.getText(), campo);
		testCase.addScreenShotCurrentStep(u, "ValidarInformacion");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validarlainstalaciondepantallacarga_test";
	}

	@Override
	public String setTestDescription() {
		return "Validacion de plantilla proveedores Tipo de proveedor 'Mercancia(F)-Frontera'. Pre-requisitos: 1.- Tener asignada la responsabilidad 'MXOXXO-APXXXXX-CONSULTAS'."
				+ "2.- Haber creado proveedor desde PANP(Matriz de Casos de Prueba CHO 52068368 PANP P1.xlsx).";
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
		return "1.- Tener asignada la responsabilidad 'MXOXXO-APXXXXX-CONSULTAS'."
				+ "2.- Haber creado proveedor desde PANP(Matriz de Casos de Prueba CHO 52068368 PANP P1.xlsx).";
	}

	@Override
	public void beforeTest() {

	}
}
