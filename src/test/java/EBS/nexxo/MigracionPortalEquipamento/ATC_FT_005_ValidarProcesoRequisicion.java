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

public class ATC_FT_005_ValidarProcesoRequisicion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarProcesoRequisicion_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String recurso = data.get("recurso");

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
		addStep("En la pantalla de Encontrar Solicitudes , seleccionar la opción de Ejecutar una Nueva Solicitud");
		p.vers();
		p.ejecutarNuevaSolicitud();
		// PASO 7
		addStep("En la pantalla de 'Ejecutar esta Solicitud',en el campo de 'Nombre',buscar el Concurrente de 'XXPO: Requisition Interface for Web Methods'la 'Unidad Operativa' y Parametros y seleccionar la opcion de 'Aceptar'.");
		p.buscarArchivoTab(recurso);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.ejecutar();
		p.ejecutarBotonNo();
		// PASO 9
		addStep("Seleccionar  en el Menu de Herramientas 'Ver' y clic en 'Solicitudes',damos clic en 'Encontrar'.");
		p.encontrar();
		p.verificarEstado("ejecutandoVerde", 15);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarProcesoRequisicion_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice correctamente el Proceso de Requision."
				+ "Pre-requisitos: Contar con la Responsabilidad de 'MXOXXO-POXXXXX-Solicitudes de Inversion'.";

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
		return  "Contar con la Responsabilidad de 'MXOXXO-POXXXXX-Solicitudes de Inversion'.";
			

	}

	@Override
	public void beforeTest() {

	}
}
