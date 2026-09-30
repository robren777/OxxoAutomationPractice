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

public class ATC_FT_004_ValidarAuxiliarConfiguracionCorrectamente extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_ValidarAuxiliarConfiguracionCorrectamente_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String configuracion = data.get("carpeta1");
		String financiera = data.get("carpeta2");
		String flexfields = data.get("carpeta3");
		String clave = data.get("carpeta4");
		String nombre = data.get("nombre");
		String valor = data.get("valor");
		String descripcion = data.get("descripcion");
		String presupuestacion = data.get("presupuestacion");
		String contabilizacion = data.get("contabilizacion");
		
		

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
		addStep("Ingresar a la responsabilidad 'MXOXXO-GLXXXXX-SUPER USUARIO'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Configuración : Financiera -> Flexfields -> Clave -> Valores");
		paginaInicial.clickOnCarpeta(configuracion);
		paginaInicial.clickOnCarpeta(financiera);
		paginaInicial.clickOnCarpeta(flexfields);
		paginaInicial.clickOnCarpeta(clave);
		paginaInicial.clickValoresGLSuperUsuario();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(8);
		// PASO 7
		addStep("Seleccionar value set, Name = " + nombre + ", Clic en botón 'Find'. ");
		patternUtil.keyAlt("j");
		patternUtil.enterText(nombre);
		patternUtil.keyAlt("n");
		u.hardWait(3);
		// PASO 8
		addStep("Posicionarse en la celda de Value, Clic en F11, capturar value = " + valor + ", Ctrl F11. ");
		patternUtil.nTAB(6);
		u.hardWait(1);
		patternUtil.f11();
		patternUtil.enterText(valor);
		patternUtil.ctrlF11();
		patternUtil.nTAB(1);
		u.hardWait(3);
		String descripcionApplet = p.controlCopiar();
        System.out.println("Descripcion del data provider: " + descripcion);
        System.out.println("Descripcion del Applet: " + descripcionApplet);
        assertEquals(descripcion, descripcionApplet);
		patternUtil.nTAB(6);
		u.hardWait(5);
		String presupuestacionApplet = p.controlCopiar();
        System.out.println("Presupuestación del data provider: " + presupuestacion);
        System.out.println("Presupuestación del Applet: " + presupuestacionApplet);
        assertEquals(presupuestacion, presupuestacionApplet);
		patternUtil.nTAB(1);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Contabilización");
		String contabilizacionApplet = p.controlCopiar();
        System.out.println("Contabilización del data provider: " + contabilizacion);
        System.out.println("Contabilización del Applet: " + contabilizacionApplet);
		assertEquals(contabilizacion,contabilizacionApplet);
		testCase.addTextEvidenceCurrentStep("Contabilización del data provider: " + contabilizacion);
	    testCase.addTextEvidenceCurrentStep("Contabilización del Applet: " + contabilizacionApplet);
		patternUtil.clickAceptarParametros();
		u.hardWait(3);
		patternUtil.closeWindow();
		patternUtil.ventanaConfirmarSalir("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarAuxiliarConfiguracionCorrectamente_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el auxiliar este  configurado correctamente. Tener responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'. ";
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