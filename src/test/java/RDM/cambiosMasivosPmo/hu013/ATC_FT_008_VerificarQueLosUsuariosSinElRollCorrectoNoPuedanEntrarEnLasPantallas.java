package RDM.cambiosMasivosPmo.hu013;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import RDM.util.CambiosMasivosQueries;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

@AzureTestInfo(areaPath = "I23084-Cambios Masivos PMO\\MVP1-Cambios Masivos PMO", iterationPath = "I23084-Cambios Masivos PMO\\Sprint MVP1\\Sprint 1.11", tags = "RLS-2", userStoryId = 35522)

/**
 * DESCRIPCIÓN: Un usuario que no cuente con los roles de: SOPORTE_OS,
 * SISTEMAS_OS, ANALISTA DE SISTEMAS, COORDINADOR DE SISTEMAS
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *
 * MTC_FT_008_VerificarQueLosUsuariosSinElRollCorrectoNoPuedanEntrarEnLasPantallas
 * 
 * PREREQUISITOS:
 *
 * 1.- Edge 2.- Tener acceso a la aplicación 3.- Un usuario que no cuente con
 * los roles de: SOPORTE_OS, SISTEMAS_OS, ANALISTA DE SISTEMAS, COORDINADOR DE
 * SISTEMAS
 * 
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_008_VerificarQueLosUsuariosSinElRollCorrectoNoPuedanEntrarEnLasPantallas extends BaseExecution {
	/*
	 * UTILERIAS Selenium
	 */
	SeleniumUtil u;

	/*
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ATC_FT_008_VerificarQueLosUsuariosSinElRollCorrectoNoPuedanEntrarEnLasPantallas.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_VerificarQueLosUsuariosSinElRollCorrectoNoPuedanEntrarEnLasPantallas_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I23084-Cambios Masivos PMO");

		/*
		 * Variables
		 */
		String rdmUrl = GlobalVariables.serverCambiosMasivosTlajomulco;
		String userNameCambiosMasivos = GlobalVariables.usuarioCambiosMasivosInspectorCalidadFastFood;
		String passwordCambiosMasivos = GlobalVariables.contrasenaCambiosMasivosInspectorCalidadFastFood;
		int waitTime = 7;

		/*
		 * Utilerias SQL y Sikulix
		 */
		u = new SeleniumUtil(new IETest());
		SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCRD71CU, GlobalVariables.DB_USER_FCRD71CU,
				GlobalVariables.DB_PASSWORD_FCRD71CU);
		CambiosMasivosQueries cq = new CambiosMasivosQueries(testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
		Pattern pathimg = new Pattern();
		var ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<>();
		ArrayList<Pattern> patterns = new ArrayList<>();
		File Fol = new File(pathImg);
		CambiosMasivos c = new CambiosMasivos(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// Paso 1
		String paso1 = "Navegar a la liga @RDM_URL : " + rdmUrl;
		addStep(paso1);
		logger.info(paso1);

		testCase.addBoldTextEvidenceCurrentStep(rdmUrl);

		c.cargaPaginaRdm(rdmUrl);

		// Paso 2
		String paso2 = "Ingresar a \"RDM\" como \"Coordinador y/o Analista de Sistemas\" capturar usuario y contraseña y dar clic al botón de \"Logon\"";
		addStep(paso2);
		logger.info(paso2);

		assertTrue(c.validatePattern("retekOracleForms"), "No se muestra el aplicativo");
		c.click("retekOracleForms");
		c.maximizar();
		assertTrue(c.validatePattern("userNameInput"), "No se muestra el campo de username");
		c.click("userNameInput");
		c.typeInput(userNameCambiosMasivos);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("userNameCambiosMasivos");
		assertTrue(c.validatePattern("passwordInput"), "No se muestra el campo de password");
		c.click("passwordInput");
		c.typeInput(passwordCambiosMasivos);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("passwordCambiosMasivos");
		assertTrue(c.validatePattern("facilityInput"), "No se muestra el campo de facility");
		c.click("facilityInput");
		c.pressDeleteKey(2);
		c.typeInput("PR");
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("facilityInput");
		assertTrue(c.validatePattern("logonButton"), "No se muestra el boton logon");
		c.click("logonButton");
		testCase.addScreenShotCurrentStep("logonButton");
		u.hardWait(waitTime);

		// Paso 3
		String paso3 = "Dar clic al menú: \"Support Functions > Reports >\"";
		addStep(paso3);
		logger.info(paso3);

		assertTrue(c.validatePattern("spfun"), "No se encontro la opcion \"Support Fuctions\"");
		c.click("spfun");
		testCase.addScreenShotCurrentStep("spfun");
		assertTrue(c.validatePattern("reportsDos"), "No se encontro la opcion \"Reports\"");
		c.click("reportsDos");
		testCase.addScreenShotCurrentStep("reportsDos");
		assertTrue(c.validatePattern("cambiosMasivosDisable"),
				"No se encontro la opcion \"XXFC PMO Cambios Masivos\" o esta enable");
		c.click("cambiosMasivosDisable");
		testCase.addTextEvidenceCurrentStep("La opción 'Cambios Masivos' esta deshabilitada");
		testCase.addScreenShotCurrentStep("cambiosMasivosDisable");
		assertTrue(c.validatePattern("cargaMasivosDisable"),
				"No se encontro la opcion \"XXFC PMO Carga Masivos\" o esta enable");
		testCase.addTextEvidenceCurrentStep("La opción 'Carga Masivos' esta deshabilitada");
		c.click("cargaMasivosDisable");
		testCase.addScreenShotCurrentStep("cargaMasivosDisable");
		u.hardWait(waitTime);

		SQLResult result = executeQuery(dbConecta, cq.dmsUserAccess(userNameCambiosMasivos));
		assertFalse(result.isEmpty(), "No hay resultados");
		testCase.addQueryEvidenceCurrentStep(result);
	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1.- Edge\n" + "2.- Tener acceso a la aplicación\n"
				+ "3.- Un usuario que no cuente con los roles de: SOPORTE_OS, SISTEMAS_OS, ANALISTA DE SISTEMAS, COORDINADOR DE SISTEMAS\n";
	}

	@Override
	public String setTestDescription() {
		return "Un usuario que no cuente con los roles de: SOPORTE_OS, SISTEMAS_OS, ANALISTA DE SISTEMAS, COORDINADOR DE SISTEMAS";
	}

	@Override
	public String setTestDesigner() {
		return "JesusH4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@AfterTest
	@Override
	public void baseAfterTest() {
		super.baseAfterTest();
		u.quit();
	}

}
