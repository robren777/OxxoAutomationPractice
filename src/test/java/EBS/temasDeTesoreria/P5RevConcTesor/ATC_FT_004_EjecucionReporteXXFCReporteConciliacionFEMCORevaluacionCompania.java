package EBS.temasDeTesoreria.P5RevConcTesor;

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

public class ATC_FT_004_EjecucionReporteXXFCReporteConciliacionFEMCORevaluacionCompania extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_EjecucionReporteXXFCReporteConciliacionFEMCORevaluacionCompania_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String recurso = data.get("recurso");
		String compania = data.get("compania");
		String origen = data.get("origen");
		String plaza = data.get("plaza");
		String cuentaCorriente = data.get("cuentacorriente");
		String tipoReporte = data.get("tiporeporte");
		String tipoSalida = data.get("tiposalida");
		String periodo = data.get("periodo");
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
		 * PASOS DEL CASO DE PRUEBA, Matriz: temasDeTesoreria.P5RevConcTesor
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
		addStep("Ingresar a la Responsabilidad de 'MXXXXX-GLXXXXX-Consulta'.");
		testCase.addScreenShotCurrentStep(u, "'MXXXXX-GLXXXXX-Consulta'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickConsultas();
		paginaInicial.runAccount();
		p.runApplet();
		u.hardWait(3);
		// PASO 6
		addStep("Se realiza la Ejecucion del reporte: Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Única > Aceptar > XXFC - Reporte de Conciliación FEMCO (Tesoreria)");
		p.vers();
		p.clickNuevaSolicitud();
		p.buscarArchivoTab(recurso);
		u.hardWait(2);
		p.ClickConciliacionTesoreria();
		// PASO 7
		addStep("Ingresar los parametros correspondientes a la plaza que se requiere hacer la validacion");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.buscarArchivo(compania);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		p.buscarArchivo(plaza);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		p.buscarArchivoTab(origen);
		p.buscarArchivoTab(cuentaCorriente);
		p.buscarArchivoTab(tipoReporte);
		p.buscarArchivoTab(tipoSalida);
		p.buscarArchivoTab(periodo);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.ejecutar();
		p.ejecutarBotonNo();
		p.encontrar();
		p.verificarEstado("ejecutandoVerde", 15);
		p.visualizarOutput();
		u.hardWait(2);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
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
		return "ATC_FT_004_EjecucionReporteXXFCReporteConciliacionFEMCORevaluacionCompania_test";
	}

	@Override
	public String setTestDescription() {
		return "Ejecutar el Reporte XXFC - Reporte de Conciliacion FEMCO (Tesoreria), para validar que se muestre del lado de Tesoreria la transaccion de revaluacion para el periodo FEB-2021 de una plaza perteneciente a compania 00099."
				+ "Pre-requisitos: Acceso a las responsabilidades: MXXXXX-GLXXXXX-Consulta." + "Acceso a BD de instancia AVEBQA"
				+ "Haber realizado la Revaluación para la Plaza 10APO de Compañía 00099 para periodo FEB-2021.";
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
		return "Acceso a las responsabilidades: MXXXXX-GLXXXXX-Consulta." + "Acceso a BD de instancia AVEBQA"
				+ "Haber realizado la Revaluación para la Plaza 10APO de Compañía 00099 para periodo FEB-2021.";

	}

	@Override
	public void beforeTest() {

	}
}