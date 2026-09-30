package RDM.cambiosMasivosPmo.hu005;

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

@AzureTestInfo(areaPath = "I23084-Cambios Masivos PMO\\MVP1-Cambios Masivos PMO", iterationPath = "I23084-Cambios Masivos PMO\\Sprint MVP1\\Sprint 1.11", tags = "RLS-2", userStoryId = 35507)
/**
 * Descripcion: Verificar que los campos "dia de llegada PT" y "dia de salida
 * PT" no se calculen cuando su valor previo sea "null"
 * 
 * MTC-FT-022 Verificar si los dias de llegada/salida PT estan en null, deben
 * mantenerse null
 * 
 * Prerequisitos: 1.- Edge 2.- Tener acceso a la aplicacion 3.- Tener datos
 * actualizados en PMO Route y PMO Tienda 4.- Tener acceso a la base de datos
 * FCRD71CU (RDM_TLAJOULCO) 5.- Tener acceso a las tablas XXFC_PMO_ROUTE,
 * XXFC_PMO_TIENDA, XXFC_PMO_CARGA_STG, XXFC_PMO_ROUTE_LOG, XXFC_PMO_TIENDA_LOG
 * 
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_022_VerificarSiLosDiasDeLlegadasalidaPTEstanEnNullaDebenMantenerseNull extends BaseExecution {
	/*
	 * UTILERIAS Selenium
	 */
	SeleniumUtil u;

	/*
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ATC_FT_022_VerificarSiLosDiasDeLlegadasalidaPTEstanEnNullaDebenMantenerseNull.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_022_VerificarSiLosDiasDeLlegadasalidaPTEstanEnNullaDebenMantenerseNull_Test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I23084-Cambios Masivos PMO");
		/*
		 * Variables
		 */
		String rdmUrl = GlobalVariables.serverCambiosMasivosTlajomulco;
		String userNameCambiosMasivos = GlobalVariables.usuarioCambiosMasivos;
		String passwordCambiosMasivos = GlobalVariables.contrasenaCambiosMasivos;
        String plaza = data.get("plaza");
		String ruta = data.get("ruta");
		// Para Oracle forms
		String DOMINGO = data.get("DO");
		String LUNES = data.get("LU");
        String MIERCOLES = data.get("MI");
		// Para queries
		String DO = data.get("1");
		String LU = data.get("2");

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
		 * Queries
		 */
		SQLResult a = executeQuery(dbConecta, cq.queryBuscarPorPlazaRutaDiaGenEnt(plaza, ruta, DOMINGO, MIERCOLES));
		logger.info(cq.queryBuscarPorPlazaRutaDiaGenEnt(plaza, ruta, DOMINGO, MIERCOLES));
		assertFalse(a.isEmpty(), "No hay datos para probar");

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
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("userNameCambiosMasivos");
		assertTrue(c.validatePattern("passwordInput"), "No se muestra el campo de password");
		c.clickHighlight("passwordInput");
		c.typeInput(passwordCambiosMasivos);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("passwordCambiosMasivos");
		assertTrue(c.validatePattern("facilityInput"), "No se muestra el campo de facility");
		c.clickHighlight("facilityInput");
		c.pressDeleteKey(2);
		c.typeInput("PR");
		u.hardWait(1);
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
		String paso5 = "Ingresa una plaza en el filtro \"Plaza\", que cuente con los campos \"dia de llegada PT\" y \"dia de salida PT\" en \"null\"";
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

		assertTrue(c.validatePattern("diaGenPedFiltro"), "No se encontro la opcion Dia Generacion");
		c.clickHighlight("diaGenPedFiltro");
		c.pressDeleteKey(3);
		c.typeInput(DO);

		assertTrue(c.validatePattern("executeButton"), "No se encontro el boton \"execute\"");
		c.clickHighlight("executeButton");
		u.hardWait(waitTime);
		testCase.addScreenShotCurrentStep("executeButton");

		// Paso 6
		String paso6 = "Se cambia el dia de generacion pedido y se da clic en el boton \"Aplicar cambios\"";
		addStep(paso6);
		logger.info(paso6);

		assertTrue(c.validatePattern("diaGenPedInputBox"), "No se encontro el campo \"diaGenPedInputBox\"");
		c.clickHighlight("diaGenPedInputBox");
		c.typeInput(LUNES);
		testCase.addScreenShotCurrentStep("diaGenPedInputBox");

		assertTrue(c.validatePattern("diaVentEntInputBox"), "No se encontro el campo \"diaVentEntInputBox\"");
		c.clickHighlight("diaVentEntInputBox");
		c.typeInput(MIERCOLES);
		testCase.addScreenShotCurrentStep("diaVentEntInputBox");
		c.pressTabKey();

		assertTrue(c.validatePattern("hScrollRight"), "No se encontro el scroll horizontal");
		c.clickHighlight("hScrollRight");
		c.click("hScrollRight", 10);
		u.hardWait(3);

		assertTrue(c.validatePattern("hScrollEnd"), "No se encontro el scroll horizontal final");
		c.highlight("hScrollEnd");

		assertTrue(c.validatePattern("columnasPT"), "No se encontraron las columnas PT");
		c.highlight("columnasPT");

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

		c.limpiarPantalla();
		c.cambiarDiaGeneracion();
		c.buscarPlaza(plaza);
		c.buscarRuta(ruta);
		c.buscarDiaGen(LU);
		c.ejecutar();
		u.hardWait(5);

		assertTrue(c.validatePattern("columnasPT"), "No se encontraron las columnas PT");
		c.highlight("columnasPT");

		testCase.addScreenShotCurrentStep("pantalla");

		// Paso 8
		String paso8 = "Verifica que los campos \"dia de llegada PT\" y \"dia de salida PT\" se conserven en \"null\"";
		addStep(paso8);
		logger.info(paso8);

		String query = "SELECT\n" + "     *\n" + " FROM\n" + "     (\n" + "         SELECT\n" + "             *\n"
				+ "         FROM\n" + "             (\n" + "                 SELECT\n" + "                     *\n"
				+ "                 FROM\n" + "                     (\n" + "                         SELECT\n"
				+ "                             tipo_distro,\n" + "                             route,\n"
				+ "                             dia_gen_ped,\n" + "                             dia_ini_entrega,\n"
				+ "                             h_ini_entrega,\n" + "                             dia_llegada_pt\n"
				+ "                         FROM\n" + "                             ("
				+ cq.queryBuscarPorPlazaRuta(plaza, ruta) + " )  WHERE dia_gen_ped ='" + LUNES + "'"
				+ " )  WHERE dia_ini_entrega ='" + MIERCOLES + "'      )\n" + " WHERE\n"
				+ "     dia_llegada_pt IS  NULL )";

		logger.info(query);
		SQLResult result = executeQuery(dbConecta, query);
		assertFalse(result.isEmpty());
		logger.info(String.valueOf(result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(result);

		// Deshacer
		c.ingresarDiaGen(DOMINGO);
		c.ingresarDiaVent(MIERCOLES);
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
				+ "5.- Tener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA, XXFC_PMO_CARGA_STG, XXFC_PMO_ROUTE_LOG, XXFC_PMO_TIENDA_LOG      \n";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que los campos \"dia de llegada PT\"  y \"dia de salida PT\" no se calculen cuando su valor previo sea \"null\"";
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
