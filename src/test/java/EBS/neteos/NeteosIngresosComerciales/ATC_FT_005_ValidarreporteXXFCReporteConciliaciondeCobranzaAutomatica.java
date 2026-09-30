package EBS.neteos.NeteosIngresosComerciales;

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


public class ATC_FT_005_ValidarreporteXXFCReporteConciliaciondeCobranzaAutomatica extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_EjecucionXXFCDepuracionDevolucionesyCargosLogisticosCEDIS_test(HashMap<String, String> data) throws Exception {

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
		String fecha1 = data.get("fecha1");
		String fecha2 = data.get("fecha2");

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
		addStep("Ingresar en la responsabilidad MXOXXO-ARXXXXX-Cobranza");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(u, "Seleccionar Otros");
		//u.scrollToElement(carpetaOtros);
		paginaInicial.clickOnCarpeta(carpeta);
		testCase.addScreenShotCurrentStep(u,"Seleccionar aplicativo");
		paginaInicial.clickRunConcurrent();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(30);
		p.runApplet2();
		u.hardWait(70);
		testCase.addScreenShotCurrentStep(" ");
		p.BuscarResponsabilidadNeteos();
		u.hardWait(17);
		p.vers();
		u.hardWait(21);
		testCase.addScreenShotCurrentStep(" ");
		p.clickEjecutarNuevaSolicitud();
		u.hardWait(18);
		// PASO 6
		addStep("Buscar el proceso XXFC-Reporte Conciliacion de Cobranza Automatica");
		p.enterText(concurrente);
		u.hardWait(16);
		
		// PASO 7
		addStep("Ingresar los parametros.Parametros:"
				+ "Fecha desde:XX-XX-XXXX"
				+ "Fechas hasta: XX-XX-XXXX"
				+ "Nombre de Cliente: XXXXXXXXXXXXXXXXXX");
		p.nTAB(14);
		p.enterText(fecha1);
		u.hardWait(13);
		sk.screen.type(fecha2);
		u.hardWait(13);
		testCase.addScreenShotCurrentStep(" ");
		sk.screen.type(Key.ENTER);
		u.hardWait(14);
		// PASO 8
		addStep("Dar clic en el boton de Ejecutar");
		p.clickEjecutar();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(17);
		p.clickNo();
		u.hardWait(17);
		// PASO 9
		addStep("Validar el concurrente finaliza correcto.");
		p.clickEncontrar();
		u.hardWait(15);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 10
		addStep("Visualizar el output del proceso XXFC Reporte Conciliacion de Cobranza Automatica");
		p.clickVisualizarOutput();
		u.hardWait(14);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa - Revisar archivo excel de evidencia manual");
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		u.hardWait(13);
		sk.screen.type(Key.ENTER);
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarreporteXXFCReporteConciliaciondeCobranzaAutomatica_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la informacion detallada de los neteos se muestre si la nota de cargo (AR) y del folio de pago (AP) fueron compensadas con la misma tasa. Validar el reporte "
				+ "incluye las nuevas columnas de tasa de impuesto para Notas de Cargo y Folios de pago.";
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
		return "1. Haber ejecutado el MTC-FT-001.";
	}

	@Override
	public void beforeTest() {

	}
}