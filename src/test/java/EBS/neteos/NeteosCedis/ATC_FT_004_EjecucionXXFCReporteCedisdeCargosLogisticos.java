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
import EBS.util.neteos.SikuliNeteos.MetodosSikuli;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
 * PRERREQUISITOS: haber ejecutado la interface RO3_MX
 * tener ordenes con devoluciones y cargos logisticos
 *
 * DESCRIPCION: crea un reporte con los cargos logisticos de las ordenes procesadas por la interace RO3_MX
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_004_EjecucionXXFCReporteCedisdeCargosLogisticos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_EjecucionXXFCReporteCedisdeCargosLogisticos_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		By carpetaTransacciones = By.xpath(
				"//div[normalize-space()='MXOXXO-ARXXXXX-CONSULTAS_ADMON_COM_OS']/parent::a/following-sibling::ul//div[normalize-space()='Transacciones']");
		String concurrente = data.get("concurrente");
		String origen = data.get("origen");
		String lote = data.get("lote");
		String fechai = data.get("fechai");
		String fechaf = data.get("fechaf");

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

		
		MetodosSikuli pu = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
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
		addStep("Ingresamos a la responsabilidad MXOXXO-ARXXXXX-CONSULTAS_ADMON_COM_OS");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(u, "Seleccionar Informes");
		u.scrollToElement(carpetaTransacciones);
		paginaInicial.clickOnCarpeta(carpeta);
		testCase.addScreenShotCurrentStep(u,"Seleccionar Transacctions");
		paginaInicial.clickRunTransactions();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		p.runApplet();
		u.hardWait(60);
		testCase.addScreenShotCurrentStep(" ");
		p.vers();
		u.hardWait(30);
		p.clickEjecutarNuevaSolicitud();
		u.hardWait(6);
		// PASO 6
		addStep("Ejecutamos el concurrente XXFC Reporte Cedis de Cargos Logisticos");
		testCase.addScreenShotCurrentStep(" ");
		p.enterText(concurrente);
		u.hardWait(5);
		sk.screen.type(fechai);
		sk.screen.type(fechaf);
		p.nTAB(3);
		testCase.addScreenShotCurrentStep(" ");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		
		// PASO 7
		addStep("Una vez finalizado el concurrente, importamos el output para validar su contanido");
		p.clickEjecutar();
		u.hardWait(3);
		p.clickNo();
		p.clickEncontrar();
	    u.hardWait(4);
	    sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		// PASO 8
		addStep("Revisamos que se vean reflejados los cargos logisticos correspondientes a las devoluciones procesadas y separadas previamente.");
		// PASO 9
		addStep("Verificamos que el orden de los registros se va reflejado con forme a los siguientes campos: Proveedor y por Tipo de Ingresos.");
		// PASO 10
		addStep("Verificar el campo RTV que corresponda con la informacion que proviente de los datos insertados desde ICM.");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_EjecucionXXFCReporteCedisdeCargosLogisticos_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que los cargos logisticos sean retenidos en interfase STAGE y sean informados en el reporte consolidado nacional";
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
		return "'Deben existir registros de carga logistica en las tablas XXFC_AP_INVOICES_INTERFACE, XXFC_AP_INVOICES_LINES_INTERFACE, XXFC_RA_INTERFACE_SALESCREDITS_ALL y XXFC_RA_INTERFACE_LINES_ALL";
	}

	@Override
	public void beforeTest() {

	}
}