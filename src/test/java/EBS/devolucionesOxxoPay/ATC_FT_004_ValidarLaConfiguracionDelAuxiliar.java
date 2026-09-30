package EBS.devolucionesOxxoPay;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_ValidarLaConfiguracionDelAuxiliar extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarLaConfiguracionDelAuxiliar_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombrevalores = data.get("nombrevalores");
		String valor1 = data.get("valor1");

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
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Entrar  a la responsabilidad de MXOXXO-GLXXXXX-SUPER USUARIO --> Setup --> Financials  --> Flexfields --> Key --> Values");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.carpetaConfiguracionElement.click();
		System.out.println("Click Configuracion");
		paginaInicial.carpetaFinancieraElement.click();
		System.out.println("Click Financiera");
		paginaInicial.carpetaFlexFieldsElement.click();
		System.out.println("Click FlexField");
		paginaInicial.carpetaClaveElement.click();
		System.out.println("Click Clave");
		paginaInicial.runValoresElement.click();
		System.out.println("Click Applet");
		u.hardWait(5);

		// PASO 2
		addStep("Seleccionar value set, Name = 'FEMCO_GL_AUXILIAR', Clic en botón 'Find'");
		p.llenarJuegoValores(nombrevalores);

		// PASO 3
		addStep("Posicionarse en la celda de Value, Clic en F11, capturar value = 'DEVOXPAY', Ctrl F11");
		p.introducirValor(valor1);
		p.cerrarApplet();
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener responsabilidad MXOXXO-GLXXXXX-Super Usuario";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el auxiliar este  configurado correctamente."
				+ "Prerequisitos: Tener responsabilidad MXOXXO-GLXXXXX-Super Usuario";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarLaConfiguracionDelAuxiliar_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
