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

public class ATC_FT_003_ValidarFormaPagoPYO46yDEV_OXPAY extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_ValidarFormaPagoPYO46yDEV_OXPAY_test(HashMap<String, String> data)
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
		String sistema = data.get("carpeta2");
		String tipo = data.get("tipo");
		String codigo = data.get("codigo");
		String cuenta = data.get("cuenta");
		

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
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-GLXXXXX-SUPER USUARIO'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Configuración : Sistema -> Consultas de General Ledger");
		paginaInicial.clickOnCarpeta(configuracion);
		paginaInicial.clickOnCarpeta(sistema);
		paginaInicial.clickConsultasLedgerGLSuperUsuario();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(8);
		// PASO 7
		addStep("Posicionarse en la celda de Type, Clic en F11, capturar type = 'XXFC_GL_RST_FORMA_PAGO', Ctrl F11. ");
		patternUtil.typeAndTab(tipo);
		// PASO 8 
		addStep("Posicionarse en la celda Code, Clic en F11, capturar code = 'PYIO49', Ctrl F11. ");
		patternUtil.clickImage("codigo");
		u.hardWait(1);
		patternUtil.f11();
		patternUtil.enterText(codigo);
		patternUtil.ctrlF11();
		patternUtil.nTAB(6);
		u.hardWait(3);
		patternUtil.nTAB(2);
		u.hardWait(3);
		String cuentaApplet = p.controlCopiar();
        System.out.println("Cuenta del data provider: " + cuenta);
        System.out.println("Cuenta del Applet: " + cuentaApplet);
        assertEquals(cuenta, cuentaApplet);
		patternUtil.clickAceptarParametros();
		patternUtil.closeWindow();
		patternUtil.ventanaConfirmarSalir("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarFormaPagoPYO46yDEV_OXPAY_test";
	}

	@Override
	public String setTestDescription() {
		return "En el lookup = XXFC_GL_RST_FORMA_PAGO, validar que se encuentre la forma de pago = PYO46 y id = DEV_OXPAY de servicios. Tener responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'. ";
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