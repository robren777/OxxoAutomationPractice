package EBS.neteos.NeteosCedis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
 * PRERREQUISITOS: haber ejecutado la interface RO3_MX
 * tener ordenes con proveedores de tipo FL1000 que se hayan ido por AP
 *
 * DESCRIPCION: crea notas de credito de tipo AP
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_002_CreacionNotadeCreditoCuentaFL1000 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_CreacionNotadeCreditoCuentaFL1000_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		By carpetaOtros = By.xpath(
				"//div[normalize-space()='MXOXXO-APXXXXX-Super Usuario']/parent::a/following-sibling::ul//div[normalize-space()='Otros']");
		String concurrente = data.get("concurrente");
		String origen = data.get("origen");
		String lote = data.get("lote");
		String fecha = data.get("fecha");
		String unidad = data.get("unidad");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);

		PatternUtil p = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA
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
		addStep("Ingresamos a la responsabilidad MXOXXXO-APXXXXX-Super Usuario Consulta");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(u, "Seleccionar Otros");
		u.scrollToElement(carpetaOtros);
		paginaInicial.clickOnCarpeta(carpeta);
		testCase.addScreenShotCurrentStep(u,"Seleccionar aplicativo");
		paginaInicial.clickRunConcurrent();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		p.runApplet();
		u.hardWait(50);
		testCase.addScreenShotCurrentStep(" ");
		p.clickEjecutarNuevaSolicitud();
		u.hardWait(6);
		// PASO 6
		addStep("Ejecutamos el concurrente XXFC-Importacion de Interface Abierta de Cuentas a Pagar");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 7
		addStep(" Seleccionar los parametros: Origen de transaccion: OXXO_15PBK_NCAIMP Fecha por defecto: Fecha contable actual");
		p.ingresarCamposNeteos1(unidad,concurrente,origen,lote,fecha);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 8
		addStep("Damos click en ejecutar");
		u.hardWait(5);
		p.clickEjecutar();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(7);
		p.clickNo();
		u.hardWait(7);
		// PASO 9
		addStep("Visualizar el log del concurrente y validar la creacion de las notas de credito creadas en el modulo de AP.");
		// PASO 10
		addStep("Validar que no se reflejen las lineas de cargos logisticos en la nota de credito en AP.");
		p.clickEncontrar();
		p.clickVisualizarOutput();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(" ");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		// PASO 11
		addStep("Validar que la cuenta de distribucion de la devolucion de producto contenga la cuenta con segm5nto FL1000.");
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_CreacionNotadeCreditoCuentaFL1000_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la linea de devolucion de mercancía se vea reflejada en el modulo de AP como una nota de credito sin el registro de cargos logisticos";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "'Validar que la filial de ejemplo 'JUGOS DEL VALLE SA PI DE CV' se encuentre marcado con un 'Si' en la pantalla de Excepciones de Proveedores."
				+ "Validar que existan datos de importacion en las tablas AP_INTERFACE y AP_INTERFACE_LINES de la devolucion de la filial mencionada.";
	}

	@Override
	public void beforeTest() {

	}
}