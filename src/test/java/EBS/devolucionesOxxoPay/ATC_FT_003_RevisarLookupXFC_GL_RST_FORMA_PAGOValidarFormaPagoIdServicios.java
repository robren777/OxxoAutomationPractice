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

public class ATC_FT_003_RevisarLookupXFC_GL_RST_FORMA_PAGOValidarFormaPagoIdServicios extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_RevisarLookupXFC_GL_RST_FORMA_PAGOValidarFormaPagoIdServicios_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String tipo = data.get("tipo");
		String codigo = data.get("codigo");
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
		paginaInicial.carpetaSistemaElement.click();
		paginaInicial.runConsultasGeneralLedgerElement.click();

		// PASO 2
		addStep("Posicionarse en la celda de Type, Clic en F11, capturar type = 'XXFC_GL_RST_FORMA_PAGO', Ctrl F11");
		p.tipoConsultaGL(tipo, codigo);
		p.cerrarApplet();

		// PASO 3
		addStep("Posicionarse en la celda Code, Clic en F11, capturar code = 'PYIO49', Ctrl F11");
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
		return "Validar que las cuentas contables y la cuenta auxiliar esten configuradas."
				+ "Prerequisitos: Tener responsabilidad MXOXXO-GLXXXXX-Super Usuario";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_RevisarLookupXFC_GL_RST_FORMA_PAGOValidarFormaPagoIdServicios_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
