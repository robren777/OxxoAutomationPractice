package RDM.cambiosMasivosPmo.hu013;

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

@AzureTestInfo(areaPath = "I23084-Cambios Masivos PMO\\MVP1-Cambios Masivos PMO", iterationPath = "I23084-Cambios Masivos PMO\\Sprint MVP1\\Sprint 1.11", tags = "RLS-2", userStoryId = 35522)

/**
 * DESCRIPCIÓN: Verificar que al ingresar un "nombre proyecto" inexistente no se
 * aplique ningún cambio
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *
 * MTC_FT_012_TratarDeRecuperarUnRespaldoIncorrecto
 * 
 * PREREQUISITOS:
 *
 * 1.- Edge 2.- Tener acceso a la aplicación 3.- Tener datos actualizados en PMO
 * Route y PMO Tienda 4.- Tener acceso a la base de datos FCRD71CU
 * (RDM_TLAJOULCO) 5.- Tener acceso a las tablas XXFC_PMO_ROUTE,
 * XXFC_PMO_TIENDA, XXFC_PMO_ROUTE_BKP, XXFC_PMO_TIENDA_BKP
 * 
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_012_TratarDeRecuperarUnRespaldoIncorrecto extends BaseExecution {
	/*
	 * UTILERIAS Selenium
	 */
	SeleniumUtil u;

	/*
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ATC_FT_012_TratarDeRecuperarUnRespaldoIncorrecto.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_TratarDeRecuperarUnRespaldoIncorrecto_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I23084-Cambios Masivos PMO");

		/*
		 * Variables
		 */
		String rdmUrl = GlobalVariables.serverCambiosMasivosTlajomulco;
		String userNameCambiosMasivos = GlobalVariables.usuarioCambiosMasivos;
		String passwordCambiosMasivos = GlobalVariables.contrasenaCambiosMasivos;
		String plaza = data.get("plaza");
		String ruta = data.get("ruta");
		String nomProyecto = data.get("nomProyecto");
		int waitTime = 7;

		/*
		 * Utilerias SQL y Sikulix
		 */
		u = new SeleniumUtil(new IETest());
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
		c.clickHighlight("retekOracleForms");
		c.maximizar();
		assertTrue(c.validatePattern("userNameInput"), "No se muestra el campo de username");
		c.clickHighlight("userNameInput");
		c.typeInput(userNameCambiosMasivos);
		testCase.addScreenShotCurrentStep("userNameCambiosMasivos");
		assertTrue(c.validatePattern("passwordInput"), "No se muestra el campo de password");
		c.clickHighlight("passwordInput");
		c.typeInput(passwordCambiosMasivos);
		testCase.addScreenShotCurrentStep("passwordCambiosMasivos");
		assertTrue(c.validatePattern("facilityInput"), "No se muestra el campo de facility");
		c.clickHighlight("facilityInput");
		c.typeInput("PR");
		testCase.addScreenShotCurrentStep("facilityInputPr");
		assertTrue(c.validatePattern("logonButton"), "No se muestra el boton logon");
		c.clickHighlight("logonButton");
		testCase.addScreenShotCurrentStep("logonButton");
		u.hardWait(waitTime);

		// Paso 3
		String paso3 = "Dar clic al menú: \"Support Functions > Reports > XXFC-PMO_Cambios_Masivos\"";
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
		String paso4 = "Selecciona el tipo de movimiento: \"Recuperar Datos PMO\"";
		addStep(paso4);
		logger.info(paso4);

		assertTrue(c.validatePattern("recuperarDatosPmo"), "No se encontro la opcion \"Recuperar Datos PMO\"");
		c.clickHighlight("recuperarDatosPmo");
		testCase.addScreenShotCurrentStep("recuperarDatosPmo");

		// Paso 5
		String paso5 = "Ingresa una plaza en el filtro \"Plaza\"";
		addStep(paso5);
		logger.info(paso5);

		assertTrue(c.validatePattern("plazaBusqueda"), "No se encontro la opcion \"Busqueda Plaza\"");
		c.clickHighlight("plazaBusqueda");
		u.hardWait(waitTime);
		testCase.addScreenShotCurrentStep("");
		assertTrue(c.validatePattern("plazaFind"), "No se encontro la opcion \"Find\"");
		c.buscarPorPlazaEnListaDeValores(plaza);
		assertTrue(c.validatePattern("plazaFindButton"), "No se encontro el boton \"Find\"");
		c.clickHighlight("plazaFindButton");
		c.clickHighlight("plazaBusquedaOkButton");

		assertTrue(c.validatePattern("rutaFiltro"), "No se encontro la opcion \"Ruta\"");
		c.clickHighlight("rutaFiltro");
		c.pressDeleteKey(10);
		c.typeInput("rutaFiltro", ruta);
		testCase.addScreenShotCurrentStep("rutaFiltro");
		assertTrue(c.validatePattern("executeButton"), "No se encontro el boton \"execute\"");
		c.clickHighlight("executeButton");
		u.hardWait(waitTime);
		testCase.addScreenShotCurrentStep("executeButton");

		// Paso 6
		String paso6 = "Da clic en el botón \"Recuperar Datos\"";
		addStep(paso6);
		logger.info(paso6);

		assertTrue(c.validatePattern("recuperarDatosButton"), "No se encontro el boton \"Recuperar Datos\"");
		c.clickHighlight("recuperarDatosButton");
		testCase.addScreenShotCurrentStep("recuperarDatosButton");

		// Paso 7
		String paso7 = "Ingresa un \"nombre proyecto\" inexistente";
		addStep(paso7);
		logger.info(paso7);
		testCase.addBoldTextEvidenceCurrentStep(nomProyecto);
		assertTrue(c.validatePattern("nomProyecto"), "No se encontro el inputText \"Nombre Proyecto\"");
		c.clickHighlight("nomProyecto");
		testCase.addScreenShotCurrentStep("nomProyecto");
		c.typeInput(nomProyecto);
		c.pressTabKey();
		testCase.addScreenShotCurrentStep("nomProyecto");

		assertTrue(c.validatePattern("buscarProyectoErroneo"), "No se encontro la \"Ventana de busqueda\"");
		c.clickHighlight("buscarProyectoErroneo");
		c.click("cancelButton");

		assertTrue(c.validatePattern("invalidNomProyectoError"), "No se encontro el \"Mensaje de error\"");
		c.clickHighlight("invalidNomProyectoError");

	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "  1.- Edge\n" + "  2.- Tener acceso a la aplicación\n"
				+ "  3.- Tener datos actualizados en PMO Route y PMO Tienda\n"
				+ "  4.- Tener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO)\n"
				+ "  5.- Tener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA, XXFC_PMO_ROUTE_BKP, XXFC_PMO_TIENDA_BKP";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que al ingresar un \"nombre proyecto\" inexistente no se aplique ningún cambio";
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
