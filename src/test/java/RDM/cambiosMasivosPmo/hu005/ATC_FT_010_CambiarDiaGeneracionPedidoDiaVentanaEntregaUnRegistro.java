package RDM.cambiosMasivosPmo.hu005;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
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

@AzureTestInfo(areaPath = "I23084-Cambios Masivos PMO\\MVP1-Cambios Masivos PMO", iterationPath = "I23084-Cambios Masivos PMO\\Sprint MVP1\\Sprint 1.11", tags = "RLS-2", userStoryId = 35507)
/**
 * Descripcion: Realizar el cambio de un dia de ventana de entrega y dia de
 * generacion de pedido para unicamente un registro
 * 
 * MTC-FT-010 Cambiar el dia de generacion de pedido y el dia de ventana de
 * entrega para un registro
 * 
 * 
 * Prerequisitos: 1.- Edge 2.- Tener acceso a la aplicacion 3.- Tener datos
 * actualizados en PMO Route y PMO Tienda 4.- Tener acceso a la base de datos
 * FCRD71CU (RDM_TLAJOULCO) 5.- Tener acceso a las tablas XXFC_PMO_ROUTE,
 * XXFC_PMO_TIENDA, XXFC_PMO_CARGA_STG, XXFC_PMO_ROUTE_LOG, XXFC_PMO_TIENDA_LOG
 * 
 * 
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_010_CambiarDiaGeneracionPedidoDiaVentanaEntregaUnRegistro extends BaseExecution {

	/*
	 * UTILERIAS Selenium
	 */
	SeleniumUtil u;

	/*
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ATC_FT_010_CambiarDiaGeneracionPedidoDiaVentanaEntregaUnRegistro.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_CambiarDiaGeneracionPedidoDiaVentanaEntregaUnRegistro_test(HashMap<String, String> data)
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
		int waitTime = 7;
		// Para Oracle forms
		String DOMINGO = data.get("DO");
		String MIERCOLES = data.get("MI");
		String JUEVES = data.get("JU");
		String SABADO = data.get("SA");
		// Para queries
		String MI = data.get("4");

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
		 * Queries
		 */
		SQLResult dbData = executeQuery(dbConecta, cq.queryBuscarPorPlazaRuta(plaza, ruta));

		SQLResult getQueryTipoDistroPlazaRuta = executeQuery(dbConecta, cq.getQueryTipoDistroPlazaRuta(plaza, ruta));

		SQLResult getQueryFinEntregaPlazaRuta = executeQuery(dbConecta, cq.getQueryFinEntregaPlazaRuta(plaza, ruta));

		SQLResult getQueryFinProdPlazaRuta = executeQuery(dbConecta, cq.getQueryFinProdPlazaRuta(plaza, ruta));

		SQLResult getQueryFinVerificacionPlazaRuta = executeQuery(dbConecta,
				cq.getQueryFinVerificacionPlazaRuta(plaza, ruta));

		SQLResult getQuerySalidaPlazaRuta = executeQuery(dbConecta, cq.getQuerySalidaPlazaRuta(plaza, ruta));

		SQLResult getQueryLlegadaPTPlazaRuta = executeQuery(dbConecta, cq.getQueryLlegadaPTPlazaRuta(plaza, ruta));

		/*
		 * Verificar datos antes de entrar al aplicativo
		 */
		assertFalse(dbData.isEmpty());
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
		String paso2 = "Ingresar a \"RDM\" como \"Coordinador y/o Analista de Sistemas\" capturar usuario y contrasenia y dar clic al boton de \"Logon\"";
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
		c.typeInput("PR", 2);
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
		String paso4 = "Selecciona el tipo de movimiento: \"Cambiar Dias Generacion/Entrega\"";
		addStep(paso4);
		logger.info(paso4);

		assertTrue(c.validatePattern("cambiarDiasGeneracionEntrega"),
				"No se encontro el tipo de movimiento \"Cambiar Dias Generacion/Entrega\"");
		c.clickHighlight("cambiarDiasGeneracionEntrega");
		testCase.addScreenShotCurrentStep("cambiarDiasGeneracionEntrega");

		// Paso 5
		String paso5 = "Ingresa una plaza en el filtro \"Plaza\"";
		addStep(paso5);
		logger.info(paso5);

		assertTrue(c.validatePattern("plaza"), "No se encontro el filtro Plaza");
		c.clickHighlight("plaza");
		c.typeInput(plaza);
		testCase.addScreenShotCurrentStep("");

		assertTrue(c.validatePattern("rutaFiltro"), "No se encontro la opcion \"Ruta\"");
		c.clickHighlight("rutaFiltro");
		c.pressDeleteKey(3);
		c.typeInput(ruta);
		testCase.addScreenShotCurrentStep("rutaFiltro");
		assertTrue(c.validatePattern("executeButton"), "No se encontro el boton \"execute\"");
		c.clickHighlight("executeButton");
		u.hardWait(waitTime);
		testCase.addScreenShotCurrentStep("executeButton");
		cq.cambiosMasivosPmoPlazaPlazaRuta(dbConecta, plaza, ruta, false);

		// Paso 6
		String paso6 = "Se cambia el dia de generacion pedido y el dia de ventana de entrega y se da clic en el boton \"Aplicar cambios\"";
		addStep(paso6);
		logger.info(paso6);

		assertTrue(c.validatePattern("diaGenPedInputBox"), "No se encontro el campo \"diaGenPedInputBox\"");
		c.clickHighlight("diaGenPedInputBox");
		c.typeInput(MIERCOLES);
		testCase.addScreenShotCurrentStep("diaGenPedInputBox");

		assertTrue(c.validatePattern("diaVentEntInputBox"), "No se encontro el campo \"diaVentEntInputBox\"");
		c.clickHighlight("diaVentEntInputBox");
		c.typeInput(SABADO);
		testCase.addScreenShotCurrentStep("diaVentEntInputBox");

		assertTrue(c.validatePattern("selectAll"), "No se encontro el boton \"Select All\"");
		c.clickHighlight("selectAll");

		assertTrue(c.validatePattern("aplicarCambios"), "No se encontro el boton \"Aplicar Cambios\"");
		c.clickHighlight("aplicarCambios");

		testCase.addScreenShotCurrentStep("aplicarCambios");

		assertTrue(c.validatePattern("cambiarDiasMensaje"), "No se encontro el mensaje de confirmacion");
		testCase.addScreenShotCurrentStep("cambiarDiasMensaje");

		assertTrue(c.validatePattern("mensajeYesBoton"), "No se encontro el boton Yes");
		testCase.addScreenShotCurrentStep("Alerta");

		c.clickHighlight("mensajeYesBoton");
		testCase.addScreenShotCurrentStep("mensajeYesBoton");

		u.hardWait(3);

		assertTrue(c.validatePattern("mensajeConfirmacionDeCambios"),
				"No se mostro el mensaje de confirmacion de cambios");
		c.clickHighlight("mensajeConfirmacionDeCambios");
		testCase.addScreenShotCurrentStep("mensajeConfirmacionDeCambios");

		assertTrue(c.validatePattern("botonYesForms"), "No se encontro el boton \"Yes\"");
		c.clickHighlight("botonYesForms");

		u.hardWait(waitTime);

		testCase.addScreenShotCurrentStep("botonYesForms");

		// Paso 7
		String paso7 = "Verifica que los cambios se muestren en pantalla";
		addStep(paso7);
		logger.info(paso7);

		assertTrue(c.validatePattern("clearButton"), "No se encontro el boton \"clearButton\"");
		c.clickHighlight("clearButton");
		u.hardWait(1);
		assertTrue(c.validatePattern("clearButton"), "No se encontro el boton \"clearButton\"");
		c.clickHighlight("clearButton");

		assertTrue(c.validatePattern("cambiarDiasGeneracionEntrega"),
				"No se encontro el tipo de movimiento \"Cambiar Dias Generacion/Entrega\"");
		c.clickHighlight("cambiarDiasGeneracionEntrega");

		assertTrue(c.validatePattern("plaza"), "No se encontro el filtro Plaza");
		c.clickHighlight("plaza");
		c.typeInput(plaza);
		testCase.addScreenShotCurrentStep("");

		assertTrue(c.validatePattern("rutaFiltro"), "No se encontro la opcion \"Ruta\"");
		c.clickHighlight("rutaFiltro");
		c.pressDeleteKey(3);
		c.typeInput(ruta);
		testCase.addScreenShotCurrentStep("rutaFiltro");

		assertTrue(c.validatePattern("executeButton"), "No se encontro el boton \"execute\"");
		c.clickHighlight("executeButton");
		u.hardWait(waitTime);
		testCase.addScreenShotCurrentStep("executeButton");

		cq.cambiosMasivosPmoPlazaPlazaRuta(dbConecta, plaza, ruta, false);

		// Paso 8
		String paso8 = "Verifica en base de datos que los cambios se ejecutaran correctamente";
		addStep(paso8);
		logger.info(paso8);

		String diaGenA = dbData.getData(0, 2);
		logger.info("Dia Gen antes del cambio: " + diaGenA);
		String diaGenB = dbData.getData(0, 3);
		logger.info("Dia Ent antes del cambio: " + diaGenB);

		SQLResult dbDataAfterChange = executeQuery(dbConecta, cq.queryBuscarPorPlazaRuta(plaza, ruta));

		// Columnas inicio
		SQLResult getQueryTipoDistroPlazaRutaAfterChange = executeQuery(dbConecta,
				cq.getQueryTipoDistroPlazaRuta(plaza, ruta));

		SQLResult getQueryFinEntregaPlazaRutaAfterChange = executeQuery(dbConecta,
				cq.getQueryFinEntregaPlazaRuta(plaza, ruta));

		SQLResult getQueryFinProdPlazaRutaAfterChange = executeQuery(dbConecta,
				cq.getQueryFinProdPlazaRuta(plaza, ruta));

		SQLResult getQueryFinVerificacionPlazaRutaAfterChange = executeQuery(dbConecta,
				cq.getQueryFinVerificacionPlazaRuta(plaza, ruta));

		SQLResult getQuerySalidaPlazaRutaAfterChange = executeQuery(dbConecta, cq.getQuerySalidaPlazaRuta(plaza, ruta));

		SQLResult getQueryLlegadaPTPlazaRutaAfterChange = executeQuery(dbConecta,
				cq.getQueryLlegadaPTPlazaRuta(plaza, ruta));
		// Columnas fin

		assertFalse(dbDataAfterChange.isEmpty());

		String diaGenC = dbDataAfterChange.getData(0, 2);
		logger.info("Dia Gen antes del cambio: " + diaGenC);
		String diaGenD = dbDataAfterChange.getData(0, 3);
		logger.info("Dia Ent antes del cambio: " + diaGenD);

		assertNotEquals(diaGenA, diaGenC, "Los dias de generacion no cambiaron");
		assertNotEquals(diaGenB, diaGenD, "Los dias de entrega no cambiaron");

		testCase.addBoldTextEvidenceCurrentStep("Consulta en base de datos antes del cambio");
		testCase.addQueryEvidenceCurrentStep(getQueryTipoDistroPlazaRuta, false);
		testCase.addQueryEvidenceCurrentStep(getQueryFinEntregaPlazaRuta, false);
		testCase.addQueryEvidenceCurrentStep(getQueryFinProdPlazaRuta, false);
		testCase.addQueryEvidenceCurrentStep(getQueryFinVerificacionPlazaRuta, false);
		testCase.addQueryEvidenceCurrentStep(getQuerySalidaPlazaRuta, false);
		testCase.addQueryEvidenceCurrentStep(getQueryLlegadaPTPlazaRuta, false);

		testCase.addBoldTextEvidenceCurrentStep("Consulta en base de datos despues del cambio");
		testCase.addQueryEvidenceCurrentStep(getQueryTipoDistroPlazaRutaAfterChange, false);
		testCase.addQueryEvidenceCurrentStep(getQueryFinEntregaPlazaRutaAfterChange, false);
		testCase.addQueryEvidenceCurrentStep(getQueryFinProdPlazaRutaAfterChange, false);
		testCase.addQueryEvidenceCurrentStep(getQueryFinVerificacionPlazaRutaAfterChange, false);
		testCase.addQueryEvidenceCurrentStep(getQuerySalidaPlazaRutaAfterChange, false);
		testCase.addQueryEvidenceCurrentStep(getQueryLlegadaPTPlazaRutaAfterChange, false);

		dbConecta.close();

		// Deshacer el cambio de dia para poder ejecutar nuevamente
		c.limpiarPantalla();
		c.cambiarDiaGeneracion();
		c.buscarPlaza(plaza);
		c.buscarRuta(ruta);
		c.buscarDiaGen(MI);
		c.ejecutar();
		u.hardWait(5);
		c.ingresarDiaGen(DOMINGO);
		c.ingresarDiaVent(JUEVES);
		c.selectAll();
		c.aplicarCambios();
		c.confirmarCambios();
		c.cambiosAplicados();
		u.hardWait(5);
	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1.- Edge\n" + "2.- Tener acceso a la aplicacion\n"
				+ "3.- Tener datos actualizados en PMO Route y PMO Tienda\n"
				+ "4.- Tener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO)\n"
				+ "5.- Tener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA, XXFC_PMO_CARGA_STG, XXFC_PMO_ROUTE_LOG, XXFC_PMO_TIENDA_LOG                                         \n";
	}

	@Override
	public String setTestDescription() {
		return "Realizar el cambio de un dia de ventana de entrega y dia de generacion de pedido para unicamente un registro";
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
