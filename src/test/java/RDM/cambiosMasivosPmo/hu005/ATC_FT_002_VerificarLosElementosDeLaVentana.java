package RDM.cambiosMasivosPmo.hu005;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

@AzureTestInfo(areaPath = "I23084-Cambios Masivos PMO\\MVP1-Cambios Masivos PMO", iterationPath = "I23084-Cambios Masivos PMO\\Sprint MVP1\\Sprint 1.11", tags = "RLS-2", userStoryId = 35507)
/**
 * Descripcion: Realizar una prueba exploratoria de la herramienta que nos
 * permita visualizar correctamente los elementos de la ventana de Cambios
 * Masivos PMO
 * 
 * MTC-FT-002 Verificar los elementos de la ventana
 * 
 * Prerequisitos: 1. -Edge 2.- Tener acceso a la aplicación
 * 
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_002_VerificarLosElementosDeLaVentana extends BaseExecution {
	/*
	 * UTILERIAS Selenium
	 */
	SeleniumUtil u;

	/*
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(ATC_FT_002_VerificarLosElementosDeLaVentana.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_VerificarLosElementosDeLaVentana_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I23084-Cambios Masivos PMO");
		/*
		 * Variables
		 */
		String rdmUrl = GlobalVariables.serverCambiosMasivosTlajomulco;
		String userNameCambiosMasivos = GlobalVariables.usuarioCambiosMasivos;
		String passwordCambiosMasivos = GlobalVariables.contrasenaCambiosMasivos;
		int waitTime = 7;

		/*
		 * UTILERIAS
		 */
		u = new SeleniumUtil(new IETest());
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
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
		c.clickHighlight("retekOracleForms");
		c.maximizar();
		assertTrue(c.validatePattern("userNameInput"), "No se muestra el campo de username");
		c.clickHighlight("userNameInput");
		c.typeInput(userNameCambiosMasivos);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("userNameCambiosMasivos");
		assertTrue(c.validatePattern("passwordInput"), "No se muestra el campo de password");
		c.clickHighlight("passwordInput");
		c.typeInput(passwordCambiosMasivos);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("passwordCambiosMasivos");
		assertTrue(c.validatePattern("facilityInput"), "No se muestra el campo de facility");
		c.clickHighlight("facilityInput");
		c.typeInput("PR");
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("facilityInput");
		assertTrue(c.validatePattern("logonButton"), "No se muestra el boton logon");
		c.clickHighlight("logonButton");
		testCase.addScreenShotCurrentStep("logonButton");
		u.hardWait(waitTime);

		// Paso 3
		String paso3 = "Dar clic al menu: \"Support Functions > Reports > XXFC-PMO_Cambios_Masivos\"";
		addStep(paso3);
		logger.info(paso3);

		assertTrue(c.validatePattern("spfun"), "No se encontro la opcion \"Support Fuctions\"");
		c.clickHighlight("spfun");
		testCase.addScreenShotCurrentStep("spfun");
		assertTrue(c.validatePattern("reportsDos"), "No se encontro la opcion \"Reports\"");
		c.clickHighlight("reportsDos");
		testCase.addScreenShotCurrentStep("reportsDos");
		assertTrue(c.validatePattern("cambiosMasivos"), "No se encontro la opcion \"XXFC PMO Cambios Masivos\"");
		c.clickHighlight("cambiosMasivos");
		testCase.addScreenShotCurrentStep("cambiosMasivos");
		u.hardWait(waitTime);
		c.maximizar();
		u.hardWait(waitTime);

		// Paso 4

		String paso4 = "Verificar que se muestren los botones:";
		addStep(paso4);
		logger.info(paso4);
		testCase.addTextEvidenceCurrentStep("a.	Tiendas PMO\n" + "b.\tAplicar Cambios\n" + "c.\tLog Error\n"
				+ "d.\tSelect All\n" + "\nVerificar que se muestren la sección:\n" + "\t\te. Tipo Movimiento\n"
				+ "\t\tf. Que contiene la opción, \"Cambiar Días Generación / Entrega\"");
		testCase.addTextEvidenceCurrentStep("");
		assertTrue(c.validatePattern("tiendasPmo"), "No se visualiza boton \"Tiendas PMO\"");
		c.highlight("tiendasPmo");
		assertTrue(c.validatePattern("aplicarCambios"), "No se visualiza boton \"Aplicar Cambios\"");
		c.highlight("aplicarCambios");
		assertTrue(c.validatePattern("logError"), "No se visualiza boton \"Log Error\"");
		c.highlight("logError");
		assertTrue(c.validatePattern("selectAll"), "No se visualiza boton \"Select All\"");
		c.highlight("selectAll");
		assertTrue(c.validatePattern("tipoMovimiento"), "No se visualiza la seccion \"Tipo Movimiento\"");
		c.highlight("tipoMovimiento");
		assertTrue(c.validatePattern("cambiarDiasGeneracionEntrega"), "No se visualiza la seccion \"Tipo Movimiento\"");
		c.highlight("cambiarDiasGeneracionEntrega");

	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1.-Edge\n2.-Tener acceso a la aplicación";
	}

	@Override
	public String setTestDescription() {
		return "Realizar una prueba exploratoria de la herramienta que nos permita visualizar correctamente los elementos de la ventana de Cambios Masivos PMO";
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
