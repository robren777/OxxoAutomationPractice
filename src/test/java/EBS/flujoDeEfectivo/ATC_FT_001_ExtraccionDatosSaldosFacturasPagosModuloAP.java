package EBS.flujoDeEfectivo;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ExtraccionDatosSaldosFacturasPagosModuloAP extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ExtraccionDatosSaldosFacturasPagosModuloAP_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String campo = data.get("campo");
		String palabraRegistro1 = data.get("palabraRegistro1");
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
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a la siguiente liga: http://fcrebsqa.femcom.net:8011/");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		// PASO 2
		addStep("Ingresar a la siguiente ruta: MXOXXO-APXXXXX-Super Usuario Consulta/Otros/Requests/Run/Solicitud única");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.carpetaOtrosAPSUElement.click();
		paginaInicial.carpetaRequestAPSUElement.click();
		paginaInicial.runrequests();
		p.inicio();
		// PASO 3
		addStep("Capturar el nombre del proceso: XXFC - Extraccion datos Flujo de Efectivo");
		p.ingresarCampo(campo);
		// PASO 4
		addStep("Dar click en ejecutar");
		p.ejecutar();
		p.aceptarPrecaucion();
		p.otraSolicitud();
		p.vers();
		p.encontrar();
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		patternUtil.keyAlt("z"); // Visualizar el Registro
		patternUtil.appletMaximize();
		assertEquals(p.getTextDelNavegador("registronavegador", palabraRegistro1), palabraRegistro1);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		sk.screen.wait(ImgPatter.get("cerraractivo"), 15).click();
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();
		// p.cerrarTodoN();
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ExtraccionDatosSaldosFacturasPagosModuloAP_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el concurrente realice la extraccion de los datos de facturas, Pagos, Saldos y Montos de los proveedores en el mes actual."
				+ "Prerequisitos: Debe de haber en AP: Facturas, Pagos y Saldos para el mes actual";
	}

	@Override
	public String setTestDesigner() {
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Debe de haber en AP: Facturas, Pagos y Saldos para el mes actual";
	}

	@Override
	public void beforeTest() {

	}
}
