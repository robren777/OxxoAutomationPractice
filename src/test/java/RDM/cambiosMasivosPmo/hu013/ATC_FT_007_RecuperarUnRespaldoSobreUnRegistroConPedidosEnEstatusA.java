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
 * DESCRIPCIÓN: Si una ruta con tu pedido en estatus A, y se intenta recuperar
 * debe mostrarse un error y ningún cambio debe ser aplicado
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *
 * MTC_FT_007_RecuperarUnRespaldoSobreUnRegistroConPedidosEnEstatusA
 * 
 * PREREQUISITOS:
 *
 * 1.- Edge 2.- Tener acceso a la aplicación 3.- Tener un registro con pedidos
 * en estatus A 4.- Tener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO) 5.-
 * Tener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA,
 * XXFC_PMO_ROUTE_BKP, XXFC_PMO_TIENDA_BKP
 * 
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_007_RecuperarUnRespaldoSobreUnRegistroConPedidosEnEstatusA extends BaseExecution {
	/*
	 * UTILERIAS Selenium
	 */
	SeleniumUtil u;

	/*
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ATC_FT_007_RecuperarUnRespaldoSobreUnRegistroConPedidosEnEstatusA.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_RecuperarUnRespaldoSobreUnRegistroConPedidosEnEstatusA_test(HashMap<String, String> data)
			throws Exception {
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
		c.typeInput("PR");
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("facilityInput");
		assertTrue(c.validatePattern("logonButton"), "No se muestra el boton logon");
		c.click("logonButton");
		testCase.addScreenShotCurrentStep("logonButton");
		u.hardWait(waitTime);

		// Paso 3
		String paso3 = "Dar clic al menú: \"Support Functions > Reports > XXFC-PMO_Cambios_Masivos\"";
		addStep(paso3);
		logger.info(paso3);

		assertTrue(c.validatePattern("spfun"), "No se encontro la opcion \"Support Fuctions\"");
		c.click("spfun");
		testCase.addScreenShotCurrentStep("spfun");
		assertTrue(c.validatePattern("reportsDos"), "No se encontro la opcion \"Reports\"");
		c.click("reportsDos");
		testCase.addScreenShotCurrentStep("reportsDos");
		assertTrue(c.validatePattern("cambiosMasivos"), "No se encontro la opcion \"XXFC PMO Cambios Masivos\"");
		c.click("cambiosMasivos");
		testCase.addScreenShotCurrentStep("cambiosMasivos");
		u.hardWait(waitTime);
		c.maximizar();
		u.hardWait(waitTime);

		// Paso 4
		String paso4 = "Selecciona el tipo de movimiento: \"Recuperar Datos PMO\"";
		addStep(paso4);
		logger.info(paso4);

		assertTrue(c.validatePattern("recuperarDatosPmo"), "No se encontro la opcion \"Recuperar Datos PMO\"");
		c.click("recuperarDatosPmo");
		testCase.addScreenShotCurrentStep("recuperarDatosPmo");

		// Paso 5
		String paso5 = "Ingresa una plaza en el filtro \"Plaza\"";
		addStep(paso5);
		logger.info(paso5);

		assertTrue(c.validatePattern("plazaBusqueda"), "No se encontro la opcion \"Busqueda Plaza\"");
		c.click("plazaBusqueda");
		u.hardWait(waitTime);
		testCase.addScreenShotCurrentStep("");
		assertTrue(c.validatePattern("plazaFind"), "No se encontro la opcion \"Find\"");
		c.buscarPorPlazaEnListaDeValores(plaza);
		assertTrue(c.validatePattern("plazaFindButton"), "No se encontro el boton \"Find\"");
		c.click("plazaFindButton");
		c.click("plazaBusquedaOkButton");

		assertTrue(c.validatePattern("rutaFiltro"), "No se encontro la opcion \"Ruta\"");
		c.click("rutaFiltro");
		c.pressDeleteKey(10);
		c.typeInput("rutaFiltro", ruta);
		testCase.addScreenShotCurrentStep("rutaFiltro");
		assertTrue(c.validatePattern("executeButton"), "No se encontro el boton \"execute\"");
		c.click("executeButton");
		u.hardWait(waitTime);
		testCase.addScreenShotCurrentStep("executeButton");
		SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCRD71CU, GlobalVariables.DB_USER_FCRD71CU,
				GlobalVariables.DB_PASSWORD_FCRD71CU);
		SQLResult tiendaConPedidosStatusA = executeQuery(dbConecta, cq.tiendaConPedidosStatusA(ruta));
		SQLResult pmoRouteBkp = executeQuery(dbConecta, cq.pmoRouteBkp(nomProyecto));
		testCase.addTextEvidenceCurrentStep("Busqueda de la ruta que tiene tiendas con pedidos en status A \n");
		testCase.addQueryEvidenceCurrentStep(tiendaConPedidosStatusA, false);
		dbConecta.close();

		// Paso 6
		String paso6 = "Da clic en el botón \"Recuperar Datos\"";
		addStep(paso6);
		logger.info(paso6);

		assertTrue(c.validatePattern("recuperarDatosButton"), "No se encontro el boton \"Recuperar Datos\"");
		c.click("recuperarDatosButton");
		testCase.addScreenShotCurrentStep("recuperarDatosButton");

		// Paso 7
		String paso7 = "Da clic en el botón de búsqueda";
		addStep(paso7);
		logger.info(paso7);

		assertTrue(c.validatePattern("busquedaBkpButton"), "No se encontro el boton \"Busqueda\"");
		c.click("busquedaBkpButton");
		testCase.addScreenShotCurrentStep("busquedaBkpButton");

		assertTrue(c.validatePattern("findBkpInput"), "No se encontro el campo \"Find\"");
		c.typeInput("findBkpInput", nomProyecto);
		testCase.addScreenShotCurrentStep("nomProyecto");

		assertTrue(c.validatePattern("findButton"), "No se encontro el boton \"Find\"");
		c.click("findButton");
		testCase.addScreenShotCurrentStep("findButton");
		testCase.addTextEvidenceCurrentStep("Respaldo a recuperar: " + nomProyecto);
		testCase.addQueryEvidenceCurrentStep(pmoRouteBkp, false);

		// Paso 8
		String paso8 = "Selecciona un respaldo a recuperar y da clic en el botón \"OK\"";
		addStep(paso8);
		logger.info(paso8);

		assertTrue(c.validatePattern("okButton"), "No se encontro el boton \"Ok\"");
		c.click("okButton");
		testCase.addScreenShotCurrentStep("okButton");

		assertTrue(c.validatePattern("vistaPreviaButton"), "No se encontro el boton \"Vista Previa\"");
		c.click("vistaPreviaButton");
		testCase.addScreenShotCurrentStep("vistaPreviaButton");

		// Paso 9
		String paso9 = "Da clic al botón \"Aplicar\"";
		addStep(paso9);
		logger.info(paso9);

		assertTrue(c.validatePattern("aplicarButton"), "No se encontro el boton \"Aplicar\"");
		c.click("aplicarButton");
		testCase.addScreenShotCurrentStep("aplicarButton");

		// Paso 10
		String paso10 = "Dar clic en el botón de \"Yes\"";
		addStep(paso10);
		logger.info(paso10);

		assertTrue(c.validatePattern("mensajeConfirmacionRecuperacion"), "No se muestra el mensaje de alerta");
		c.click("mensajeConfirmacionRecuperacion");
		testCase.addScreenShotCurrentStep("mensajeConfirmacionRecuperacion");

		assertTrue(c.validatePattern("mensajeYesBoton"), "No se muestra el boton \"Yes\"");
		c.click("mensajeYesBoton");
		testCase.addScreenShotCurrentStep("mensajeYesBoton");

		assertTrue(c.validatePattern("errorRecuperacionStatusA"), "No se muestra el mensaje de alerta");
		c.click("errorRecuperacionStatusA");
		testCase.addScreenShotCurrentStep("errorRecuperacionStatusA");

	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1.- Edge\n" + "2.- Tener acceso a la aplicación\n" + "3.- Tener un registro con pedidos en estatus A\n"
				+ "4.- Tener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO)\n"
				+ "5.- Tener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA,  XXFC_PMO_ROUTE_BKP, XXFC_PMO_TIENDA_BKP\n";
	}

	@Override
	public String setTestDescription() {
		return "Si una ruta con tu pedido en estatus A, y se intenta recuperar debe mostrarse un error y ningún cambio debe ser aplicado";
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
