package EBS.oxxoPay;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_ValidarConfiguracionComisiones extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_006_ValidarConfiguracionComisiones_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String tabuladores = data.get("carpeta1");
		String nombre = data.get("nombre");
		String tipoItem = data.get("tipoItem");
		String id = data.get("id");
		String fechaInicio = data.get("fechaInicio");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: RST Devoluciones OXXO PAY
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contraseña.");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-COXXXXX-ADMINISTRADOR'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Tabuladores-> Servicios, Prod. y Formas Pago");
		paginaInicial.clickOnCarpeta(tabuladores);
		paginaInicial.clickRunServiciosProdFormasPago();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(8);
		// PASO 7
		addStep("Seleccionar value set, Name = " + nombre + ", Clic en botón 'Find'. ");
		patternUtil.ctrlF11();
		u.hardWait(5);
		String nombreApplet = p.controlCopiar();
		System.out.println("Nombre del data provider: " + nombre);
		System.out.println("Nombre del Applet: " + nombreApplet);
		assertEquals(nombre, nombreApplet);
		u.hardWait(3);
		// PASO 8
		addStep("Posicionarse en la celda de Tipo de Item, Clic en F11, capturar Tipo de Item = " + tipoItem + ", Ctrl F11");
		patternUtil.clickImage("tipoItem");
		u.hardWait(1);
		patternUtil.f11();
        patternUtil.enterText(tipoItem);
        patternUtil.ctrlF11();
        patternUtil.nDOWN(8);
        u.hardWait(5);
        String idApplet = p.controlCopiar();
		System.out.println("ID del data provider: " + id);
		System.out.println("ID del Applet: " + idApplet);
		assertEquals(id, idApplet);
		patternUtil.nTAB(3);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Fecha Inicio");
        String fechaInicioApplet = p.controlCopiar();
		System.out.println("Inicio del data provider: " + fechaInicio);
		System.out.println("Inicio del Applet: " + fechaInicioApplet);
		assertEquals(fechaInicio, fechaInicioApplet);
		u.hardWait(3);
		patternUtil.closeWindow();
		patternUtil.ventanaConfirmarSalir("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_ValidarConfiguracionComisiones_test";
	}

	@Override
	public String setTestDescription() {
		return "Se validará en el tabulador de comisionistas que se configure la forma de pago para devoluciones y la comisión que le corresponderá al líder de la tienda. Tener responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'. ";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'. ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
}