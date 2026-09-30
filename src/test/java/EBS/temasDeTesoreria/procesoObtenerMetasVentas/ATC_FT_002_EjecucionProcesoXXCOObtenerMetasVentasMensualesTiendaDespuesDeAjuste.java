package EBS.temasDeTesoreria.procesoObtenerMetasVentas;

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

public class ATC_FT_002_EjecucionProcesoXXCOObtenerMetasVentasMensualesTiendaDespuesDeAjuste extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_EjecucionProcesoXXCOObtenerMetasVentasMensualesTiendaDespuesDeAjuste_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String recurso = data.get("recurso");
		String recurso2 = data.get("recurso2");
		String recurso3 = data.get("recurso3");
		String plaza = data.get("plaza");
		String distrito = data.get("distrito");
		String tienda = data.get("tienda");
		String nombre = data.get("nombreperiodo");
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
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: Script de Pruebas Proceso Obtener Metas Ventas
		 */

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
		addStep("Ingresar a la responsabilidad ' MXOXXO-CO10VCZ-USUARIO-32SGR'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Arrendamientos y Documentos : Arrendamiento Principal -> Visualizar Arredamientos y Documentos");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.ClickDespachosContables();
		p.runApplet();
		u.hardWait(3);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		p.vers();
		p.clickNuevaSolicitud();
		p.buscarArchivoTab(recurso);
		testCase.addScreenShotCurrentStep("accedemos al recurso solcitado");
		u.hardWait(2);
		p.buscarArchivo(plaza);
		p.buscarArchivo(distrito);
		p.buscarArchivoTab(tienda);
		p.buscarArchivo(nombre);
		testCase.addScreenShotCurrentStep("Ingresamos los parametros solicitados");
		// PASO 7
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(3);
		p.ejecutar();
		p.ejecutarBotonNo();
		p.encontrar();
		p.verificarEstado("ejecutandoVerde",10000);
		// PASO 8
		addStep("Se realiza segundo proceso");
		p.vers();
		p.clickNuevaSolicitud();
		p.buscarArchivoTab(recurso2);
		testCase.addScreenShotCurrentStep("accedemos al recurso solcitado");
		u.hardWait(2);
		p.buscarArchivo(plaza);
		p.buscarArchivo(distrito);
		p.buscarArchivoTab(tienda);
		p.buscarArchivo(nombre);
		testCase.addScreenShotCurrentStep("Ingresamos los parametros solicitados");
		// PASO 9
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(3);
		p.ejecutar();
		p.ejecutarBotonNo();
		p.encontrar();
		p.verificarEstado("ejecutandoVerde",10000);
		testCase.addScreenShotCurrentStep("Verificamos el estado del output");
		// PASO 10
		addStep("Se realiza tercer proceso");
		p.vers();
		p.clickNuevaSolicitud();
		p.buscarArchivoTab(recurso3);
		testCase.addScreenShotCurrentStep("accedemos al recurso solcitado");
		u.hardWait(2);
		p.buscarArchivo(plaza);
		p.buscarArchivo(distrito);
		p.buscarArchivoTab(tienda);
		p.buscarArchivo(nombre);
		testCase.addScreenShotCurrentStep("Ingresamos los parametros solicitados");
		// PASO 11
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(3);
		p.ejecutar();
		p.ejecutarBotonNo();
		p.encontrar();
		p.verificarEstado("ejecutandoVerde",10000);
		testCase.addScreenShotCurrentStep("Verificamos el estado del output");
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
		return "ATC_FT_002_EjecucionProcesoXXCOObtenerMetasVentasMensualesTiendaDespuesDeAjuste_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se muestre informacion en ceros de Metas Ventas antes de la aplicacion del ajuste en QA."
				+ "Pre-requisitos: Acceso a la responsabilidad: MXOXXO-CO10VCZ-USUARIO-32SGR";
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
		return "Acceso a la responsabilidad: MXOXXO-CO10VCZ-USUARIO-32SGR";
	}

	@Override
	public void beforeTest() {
		
	}

}