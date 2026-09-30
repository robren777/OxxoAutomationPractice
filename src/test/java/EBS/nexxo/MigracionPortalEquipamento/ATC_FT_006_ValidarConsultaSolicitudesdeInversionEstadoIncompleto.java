package EBS.nexxo.MigracionPortalEquipamento;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_ValidarConsultaSolicitudesdeInversionEstadoIncompleto extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ValidarConsultaSolicitudesdeInversionEstadoIncompleto_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
        String unidadOperativa = data.get("unidadoperativa");
        String preparador = data.get("preparador");
		/*
		 * UTILERIAS
		 */

		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: MigracionPortalEquipamento
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
		addStep("Ingresar a la Responsabilidad de 'MXOXXO-POXXXXX-Solicitudes de Inversion'.");
		testCase.addScreenShotCurrentStep(u, "'MXOXXO-POXXXXX-Solicitudes de Inversion'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.ClickrunConsultaSolicitud();
		p.runApplet();
		// PASO 6
		addStep("Del menu que se presenta seleccionar la opcion de Solicitudes de Inversion--> Consulta de Solicitudes de Inversion");
		// PASO 7
		addStep("En la pantalla de Encontrar Solicitudes Internas agregar los datos en los siguientes campos: Unidad Operativa - CADENA_COMERCIAL ,Preparador - INTERFACE WO2 , Estado INCOMPLETO y seleccionar el boton de Encontrar");
		p.buscarArchivoTab(unidadOperativa);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		p.buscarArchivoTab(preparador);
		p.clickPestanaEstado();
		p.clickTipoEstado();
		sk.screen.keyDown(Key.UP);
		sk.screen.keyUp(Key.UP);
		sk.screen.keyDown(Key.UP);
		sk.screen.keyUp(Key.UP);
		sk.screen.keyDown(Key.UP);
		sk.screen.keyUp(Key.UP);
		sk.screen.keyDown(Key.UP);
		sk.screen.keyUp(Key.UP);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.encontrarBotonLargo();
		// PASO 8
		addStep("En la pantalla de Resumen de Cabeceras de Solicitud Interna dar clic en el boton de Abrir");
		// PASO 9
		addStep("En la pantalla de Resumen de Cabeceras de Solicitud Interna dar clic en el boton de Lineas");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_ValidarConsultaSolicitudesdeInversionEstadoIncompleto_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice correctamente el Proceso de Requision."
				+ "Pre-requisitos: Contar con la Responsabilidad de 'MXOXXO-POXXXXX-Solicitudes de Inversion'."
				+ "NOTA : Caso construido debido a falta de recurso en la parte de unidad operativa";

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
		return  "Contar con la Responsabilidad de 'MXOXXO-POXXXXX-Solicitudes de Inversion'."
				+ "NOTA : Caso construido debido a falta de recurso en la parte de unidad operativa";

	}

	@Override
	public void beforeTest() {

	}
}