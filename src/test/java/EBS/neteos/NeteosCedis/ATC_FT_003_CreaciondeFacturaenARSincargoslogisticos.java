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
 * tener ordenes que se hayan ido por AR
 *
 * DESCRIPCION: crea facturas AR donde no se aprecian los cargos logisticos
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_003_CreaciondeFacturaenARSincargoslogisticos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_CreaciondeFacturaenARSincargoslogisticos_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		By carpetacontrolar = By.xpath(
				"//div[normalize-space()='MXOXXO-ARXXXXX-Super Usuario']/parent::a/following-sibling::ul//div[normalize-space()='Controlar']");
		String concurrente = data.get("concurrente");
		String origen = data.get("origen");
		String fecha = data.get("fecha");

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
		addStep("Ingresamos a la responsabilidad MXOXXO-ARXXXXX-Super Usuario");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(u, "Seleccionar Carpeta Controlar");
		u.scrollToElement(carpetacontrolar);
		paginaInicial.clickOnCarpeta(carpeta);
		testCase.addScreenShotCurrentStep(u,"Seleccionar Concurrente");
		paginaInicial.clickRunConcurrente();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		p.runApplet();
		u.hardWait(50);
		testCase.addScreenShotCurrentStep(" ");
		p.clickAceptarParametros();
		u.hardWait(6);
		// PASO 6
		addStep("Ejecutamos el concurrente Programa de Importacion de Factura Automatica.");
		testCase.addScreenShotCurrentStep(" ");		
		// PASO 7
		addStep("Seleccionar los parametros: Origen de transaccion: OXXO_15CMF_NCAIMP Fecha por defecto: Fecha contable actual");
		p.clickEjecutarNuevaSolicitud();
		u.hardWait(4);
		p.ingresarCamposNeteos3(concurrente,origen,fecha);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(6);
		// PASO 8
		addStep("Damos click en ejecutar.");
		p.clickEjecutar();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		p.clickNo();
		u.hardWait(7);
		// PASO 9
		addStep("Visualizar el log del concurrente y validar la creacion de las facturas creadas en el modulo de AR");
		p.clickEncontrar();
		p.clickVisualizarOutput();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		// PASO 10
		addStep("Validar que no se reflejen las lineas de cargos logisticos en la factura en AR.");
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_CreaciondeFacturaenARSincargoslogisticos_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que las devoluciones se vean reflejadas en el modulo de AR sin el registro de cargos logisticos";
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
		return "'Validar que el proveedor de ejemplo 'FERRERO DE MEXICO SA DE CV' se encuentre marcado con un 'No' en la pantalla de Excepciones de Proveedores"
				+ "Validar que existan datos de importacion en las tablas RA_INTERFACE_SALESCREDITS_ALL y RA_INTERFACE_LINES_ALL de la devolucion de la filial mencionada";
	}

	@Override
	public void beforeTest() {

	}
}