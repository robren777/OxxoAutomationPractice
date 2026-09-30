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
 * DESCRIPCIÓN: Verificar que al recuperar un respaldo luego de que un día de
 * generación fue cambiado y si en esta ruta no cubre los 7 días de la semana,
 * se agrega ese día de generación
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *
 * MTC_FT_014_RecuperarUnRespaldoCuandoSeCambiaUnDiaDeGeneracion
 * 
 * PREREQUISITOS:
 *
 * 1.- Edge 2.- Tener acceso a la aplicación 3.- Tener un respaldo y posterior
 * modificar a eso, modificar días de generación de pedido 4.- Tener acceso a la
 * base de datos FCRD71CU (RDM_TLAJOULCO) 5.- Tener acceso a las tablas
 * XXFC_PMO_ROUTE, XXFC_PMO_TIENDA, XXFC_PMO_ROUTE_BKP, XXFC_PMO_TIENDA_BKP
 *
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_014_RecuperarUnRespaldoCuandoSeCambiaUnDiaDeGeneracion extends BaseExecution {
	/*
	 * UTILERIAS Selenium
	 */
	SeleniumUtil u;

	/*
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ATC_FT_014_RecuperarUnRespaldoCuandoSeCambiaUnDiaDeGeneracion.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_RecuperarUnRespaldoCuandoSeCambiaUnDiaDeGeneracion_test(HashMap<String, String> data)
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
		// Para Oracle forms
		String LUNES = data.get("LU");
		String SABADO = data.get("SA");
		// Para queries
		String LU = data.get("2");
		String MA = data.get("3");
		String JU = data.get("5");
		String SA = data.get("7");

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
		SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCRD71CU, GlobalVariables.DB_USER_FCRD71CU,
				GlobalVariables.DB_PASSWORD_FCRD71CU);
		CambiosMasivosQueries cq = new CambiosMasivosQueries(testCase);

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

		/*
		 * Cambiar un dia de generacion *Importante tener el respaldo de esa ruta
		 */

		c.limpiarPantalla();
		c.cambiarDiaGeneracion();
		c.buscarPlaza(plaza);
		c.buscarRuta(ruta);
		c.buscarDiaGen(MA);
		c.buscarDiaEnt(JU);
		c.ejecutar();
		u.hardWait(waitTime);
		c.ingresarDiaGen(LUNES);
		c.ingresarDiaVent(SABADO);
		c.selectAll();
		c.aplicarCambio();
		c.click("mensajeYesBoton");
		c.click("botonYesForms");
		u.hardWait(waitTime);
		c.limpiarPantalla();

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
		String paso7 = "Da clic en el botón de búsqueda";
		addStep(paso7);
		logger.info(paso7);

		assertTrue(c.validatePattern("nomProyecto"), "No se encontro el la caja de texto \"Nombre proyecto\"");
		c.clickHighlight("nomProyecto");
		c.typeInput(nomProyecto);
		c.pressTabKey();
		testCase.addScreenShotCurrentStep("nomProyecto");
		u.hardWait(3);

		// Paso 8
		String paso8 = "Selecciona un respaldo a recuperar y da clic en el botón \"OK\"";
		addStep(paso8);
		logger.info(paso8);
		// Evidencia SQL
		testCase.addBoldTextEvidenceCurrentStep("Tabla PMO ROUTE BKP");
		SQLResult pmoRouteBkp = executeQuery(dbConecta, cq.pmoRouteBkp(nomProyecto));
		assertFalse(pmoRouteBkp.isEmpty(), "No hay Resultados");
		testCase.addQueryEvidenceCurrentStep(pmoRouteBkp, false);
		cq.pmoRouteBkpQueries(dbConecta, ruta, nomProyecto, false);

		testCase.addTextEvidenceCurrentStep("Nombre del respaldo: " + nomProyecto);
		testCase.addTextEvidenceCurrentStep("Nombre de la ruta: " + ruta);
		testCase.addBoldTextEvidenceCurrentStep("Tabla PMO TIENDA BKP");
		SQLResult pmoTiendaBkp = executeQuery(dbConecta, cq.pmoTiendaBkp(nomProyecto));
		assertFalse(pmoTiendaBkp.isEmpty(), "No hay Resultados");
		cq.pmoTiendaBkpQueries(dbConecta, nomProyecto, false);
		// Continua la ejecucion en pantalla
		assertTrue(c.validatePattern("vistaPreviaButton"), "No se encontro el boton \"Vista Previa\"");
		c.clickHighlight("vistaPreviaButton");
		testCase.addScreenShotCurrentStep("vistaPreviaButton");

		// Paso 9
		String paso9 = "Da clic al botón \"Aplicar\"";
		addStep(paso9);
		logger.info(paso9);

		assertTrue(c.validatePattern("aplicarButton"), "No se encontro el boton \"Aplicar\"");
		c.clickHighlight("aplicarButton");
		testCase.addScreenShotCurrentStep("aplicarButton");

		// Paso 10
		String paso10 = "Dar clic en el botón de \"Yes\"";
		addStep(paso10);
		logger.info(paso10);

		assertTrue(c.validatePattern("mensajeConfirmacionRecuperacion"), "No se muestra el mensaje de alerta");
		c.clickHighlight("mensajeConfirmacionRecuperacion");
		testCase.addScreenShotCurrentStep("mensajeConfirmacionRecuperacion");

		assertTrue(c.validatePattern("mensajeYesBoton"), "No se muestra el boton \"Yes\"");
		c.clickHighlight("mensajeYesBoton");
		testCase.addScreenShotCurrentStep("mensajeYesBoton");

		assertTrue(c.validatePattern("mensajeRecuperacionExitosa"), "No se muestra el mensaje de alerta");
		c.clickHighlight("mensajeRecuperacionExitosa");
		testCase.addScreenShotCurrentStep("mensajeRecuperacionExitosa");

		// Paso 11
		String paso11 = "Dar clic en el botón de \"Yes\"";
		addStep(paso11);
		logger.info(paso11);

		assertTrue(c.validatePattern("botonYesForms"), "No se muestra el boton \"Yes\"");
		c.clickHighlight("botonYesForms");
		testCase.addScreenShotCurrentStep("botonYesForms");

		// Paso 12
		String paso12 = "Verifica que los datos sean restaurados";
		addStep(paso12);
		logger.info(paso12);

		SQLResult pmoTienda = executeQuery(dbConecta, cq.pmoTienda(ruta));
		assertFalse(pmoTienda.isEmpty(), "No hay Resultados");
		testCase.addTextEvidenceCurrentStep("Nombre de la ruta: " + ruta);
		testCase.addBoldTextEvidenceCurrentStep("Tabla PMO TIENDA");
		cq.pmoTiendaQueries(dbConecta, ruta, false);

		SQLResult pmoTiendaCompare = executeQuery(dbConecta, cq.pmoTiendaCompare(nomProyecto, ruta));
		assertTrue(pmoTiendaCompare.isEmpty());
		testCase.addTextEvidenceCurrentStep("Exito, todos los registros de PMO TIENDA BKP estan en PMO TIENDA");

		SQLResult pmoRoute = executeQuery(dbConecta, cq.pmoRoute(ruta));
		assertFalse(pmoRoute.isEmpty(), "No hay Resultados");
		testCase.addTextEvidenceCurrentStep("Nombre de la ruta: " + ruta);
		testCase.addBoldTextEvidenceCurrentStep("Tabla PMO Route");
		cq.pmoRouteQueries(dbConecta, ruta, false);

		SQLResult pmoRouteCompare = executeQuery(dbConecta, cq.pmoRouteCompare(nomProyecto, ruta));
		assertTrue(pmoRouteCompare.isEmpty());
		testCase.addTextEvidenceCurrentStep("Exito, todos los registros de PMO ROUTE BKP estan en PMO ROUTE");
		dbConecta.close();

		/*
		 * Borrar registros de una ruta respaldada *Importante tener el respaldo de esa
		 * ruta
		 */

		c.limpiarPantalla();
		c.eliminarDiasGen();
		c.buscarPlaza(plaza);
		c.buscarRuta(ruta);
		c.buscarDiaGen(LU);
		c.buscarDiaEnt(SA);
		c.ejecutar();
		u.hardWait(waitTime);
		c.selectAll();
		c.aplicarCambio();
		c.click("mensajeYesBoton");
		c.click("botonYesForms");
		u.hardWait(waitTime);

	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1.- Edge\n" + "2.- Tener acceso a la aplicación\n"
				+ "3.- Tener un respaldo y posterior modificar a eso, modificar días de generación de pedido\n"
				+ "4.- Tener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO)\n"
				+ "5.- Tener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA, XXFC_PMO_ROUTE_BKP, XXFC_PMO_TIENDA_BKP\n";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que al recuperar un respaldo luego de que un día de generación fue cambiado y si en esta ruta no cubre los 7 días de la semana, se agrega ese día de generación";
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
