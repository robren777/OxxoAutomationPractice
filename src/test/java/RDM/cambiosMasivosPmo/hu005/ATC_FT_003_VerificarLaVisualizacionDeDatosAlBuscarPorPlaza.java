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
 * Descripcion: Realizar una búsqueda por plaza y verificar los resultados
 * visualizados en pantalla contra los datos de base de datos
 * 
 * MTC-FT-003 Verificar la visualización de datos al buscar por Plaza
 * 
 * Prerequisitos: 1.- Edge 2.- Tener acceso a la aplicación 3.- Tener datos
 * actualizados en PMO Route y PMO Tienda 4.- Tener acceso a la base de datos
 * FCRD71CU (RDM_TLAJOULCO) 5.- Tener acceso a las tablas XXFC_PMO_ROUTE,
 * XXFC_PMO_TIENDA, XXFC_PMO_CARGA_STG, XXFC_PMO_ROUTE_LOG, XXFC_PMO_TIENDA_LOG
 * 
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_003_VerificarLaVisualizacionDeDatosAlBuscarPorPlaza extends BaseExecution {
	/*
	 * UTILERIAS Selenium
	 */
	SeleniumUtil u;

	/*
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ATC_FT_003_VerificarLaVisualizacionDeDatosAlBuscarPorPlaza.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_VerificarLaVisualizacionDeDatosAlBuscarPorPlaza_Test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("I23084-Cambios Masivos PMO");
		/*
		 * Variables
		 */
		String rdmUrl = GlobalVariables.serverCambiosMasivosTlajomulco;
		String userNameCambiosMasivos = GlobalVariables.usuarioCambiosMasivos;
		String passwordCambiosMasivos = GlobalVariables.contrasenaCambiosMasivos;
		int waitTime = 7;
		String servicio = data.get("servicio");
		String plaza = data.get("plaza");

		/*
		 * UTILERIAS
		 */
		SikuliUtil sk = new SikuliUtil();
		u = new SeleniumUtil(new IETest());
		String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
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
		String paso4 = "Ingresa una plaza en el filtro \"Plaza\" ";
		addStep(paso4);
		logger.info(paso4);

		assertTrue(c.validatePattern("plazaBusqueda"), "No se encontro la opcion \"Busqueda Plaza\"");
		c.clickHighlight("plazaBusqueda");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("");
		assertTrue(c.validatePattern("plazaFind"), "No se encontro la opcion \"Find\"");
		c.buscarPorPlazaEnListaDeValores(plaza);
		assertTrue(c.validatePattern("plazaFindButton"), "No se encontro el boton \"Find\"");
		c.clickHighlight("plazaFindButton");
		c.clickHighlight("plazaBusquedaOkButton");
		c.clickHighlight("executeButton");
		SQLResult queryPlazaResult = executeQuery(dbConecta, cq.queryPlaza(servicio));
		assertFalse(queryPlazaResult.isEmpty());
		testCase.addBoldTextEvidenceCurrentStep("Consulta a la base de datos y resultado");
		testCase.addQueryEvidenceCurrentStep(queryPlazaResult, false);

		// Paso 5
		String paso5 = "Verificar en base de datos que la información  visualizada sea correcta";
		addStep(paso5);
		logger.info(paso5);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("Paso5");
		SQLResult queryBuscarPorPlaza = executeQuery(dbConecta, cq.queryBuscarPorPlaza(plaza));
		assertFalse(queryBuscarPorPlaza.isEmpty());
		cq.cambiosMasivosPmoPlaza(dbConecta, plaza, false);
		dbConecta.close();

	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1.-\tEdge\n" + "2.-\tTener acceso a la aplicación \n"
				+ "3.-\tTener datos actualizados en PMO Route y PMO Tienda\n"
				+ "4.-\tTener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO)\n"
				+ "5.-\tTener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA, XXFC_PMO_CARGA_STG, XXFC_PMO_ROUTE_LOG, XXFC_PMO_TIENDA_LOG\n";
	}

	@Override
	public String setTestDescription() {
		return "Realizar una búsqueda por plaza y verificar los resultados visualizados en pantalla contra los datos de base de datos";
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
