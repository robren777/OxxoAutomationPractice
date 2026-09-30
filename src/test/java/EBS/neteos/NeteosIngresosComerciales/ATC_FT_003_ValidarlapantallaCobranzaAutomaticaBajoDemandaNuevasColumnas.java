package EBS.neteos.NeteosIngresosComerciales;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
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
 * PRERREQUISITOS: tener acceso a EBS
 * 
 *
 * DESCRIPCION: Validar que se encuentre la columna tipo de transaccion en la tabla de cobranza
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_003_ValidarlapantallaCobranzaAutomaticaBajoDemandaNuevasColumnas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarlapantallaCobranzaAutomaticaBajoDemandaNuevasColumnas_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		By tipoTran = By.xpath("//table[@class='OraNoBorderTableContent']//th[@id='column22']");

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
		testCase.addScreenShotCurrentStep(" ");
		// PASO 6
		addStep("Ingresamos la opcion: Pantalla Principal.");
		paginaInicial.clickRunPantallaPrincipal();
		testCase.addScreenShotCurrentStep(" ");
		u.waitForLoadPage();
		// PASO 7
		addStep("Ingresar los parametros.");
		// PASO 8
		addStep("Dar clic en el boton de Ir");
		// PASO 9
		addStep("Validar Columnas nuevas");
		u.highLight(tipoTran);
		assertEquals(u.getText(tipoTran),"Tipo Transaccion");
		testCase.addScreenShotCurrentStep(" ");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa se muestran la columna para AR Tipo de Transaccion ");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarlapantallaCobranzaAutomaticaBajoDemandaNuevasColumnas_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la pantalla muestre la columna para AR Tipo de Transaccion";
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
		return "1. Contar con registros de cobranza por procesar.";
	}

	@Override
	public void beforeTest() {

	}
}