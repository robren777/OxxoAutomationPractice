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
 * Descripcion: Verificar que al tener seleccionados previamente todos los
 * registros a modificar y volver a dar clic en el boton "Select All" se
 * deseleccionen todos los registros y se limpien los datos colocados en los
 * campos "dia de generacion pedido" y " dia de ventana de entrega"
 * 
 * MTC-FT-036 Dar clic al boton "Select All" cuando todos los registros ya estan
 * seleccionados y con datos
 * 
 * Prerequisitos: 1.- Edge 2.- Tener acceso a la aplicacion 3.- Tener datos
 * actualizados en PMO Route y PMO Tienda 4.- Tener acceso a la base de datos
 * FCRD71CU (RDM_TLAJOULCO) 5.- Tener acceso a las tablas XXFC_PMO_ROUTE,
 * XXFC_PMO_TIENDA, XXFC_PMO_CARGA_STG, XXFC_PMO_ROUTE_LOG, XXFC_PMO_TIENDA_LOG
 * 
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_036_DarClicAlBotonSelectAllCuandoTodosLosRegistrosYaEstanSeleccionadosYConDatos
		extends BaseExecution {
	/*
	 * UTILERIAS Selenium
	 */
	SeleniumUtil u;

	/*
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(
			ATC_FT_036_DarClicAlBotonSelectAllCuandoTodosLosRegistrosYaEstanSeleccionadosYConDatos.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_036_DarClicAlBotonSelectAllCuandoTodosLosRegistrosYaEstanSeleccionadosYConDatos_Test(
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
        String LUNES = data.get("LU");
		String MARTES = data.get("MA");
		String MIERCOLES = data.get("MI");
		String JUEVES = data.get("JU");
		String VIERNES = data.get("VI");
		String SABADO = data.get("SA");

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
		String paso2 = "Ingresar a \"RDM\" como \"Coordinador y/o Analista de Sistemas\" capturar usuario y contrasenia y dar clic al boton de \"Logon\"";
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
		String paso4 = "Ingresa una plaza en el filtro \"Plaza\"";
		addStep(paso4);
		logger.info(paso4);

		/*
		 * Cambiar un dia de generacion *Importante tener el respaldo de esa ruta
		 */

		c.limpiarPantalla();
		c.cambiarDiaGeneracion();
		c.buscarPlaza(plaza);
		c.buscarRuta(ruta);
		c.ejecutar();
		u.hardWait(waitTime);
		testCase.addScreenShotCurrentStep("recuperarDatosPmo");

		// Paso 5
		String paso5 = "Modificar los \"dias de generacion pedido\" y \"dia de ventana de entrega\"";
		addStep(paso5);
		logger.info(paso5);

		c.ingresarDiaGen(LUNES);
		c.ingresarDiaVent(MARTES);
		c.pressDownKey();
		c.pressShiftTab();
		c.typeInput(MIERCOLES);
		c.pressTabKey();
		c.typeInput(VIERNES);
		c.pressDownKey();
		c.pressShiftTab();
		c.typeInput(JUEVES);
		c.pressTabKey();
		c.typeInput(SABADO);

		testCase.addScreenShotCurrentStep("executeButton");

		// Paso 6
		String paso6 = "Dar clic en el boton \"Select All\"";
		addStep(paso6);
		logger.info(paso6);

		c.selectAll();
		u.hardWait(3);
		assertTrue(c.validatePattern("checkBoxSelected"), "No se encuentra la imagen");
		c.highlight("checkBoxSelected");

		// Paso 7
		String paso7 = "Dar clic en el boton \"Select All\" nuevamente";
		addStep(paso7);
		logger.info(paso7);

		c.selectAll();
		u.hardWait(3);
		assertTrue(c.validatePattern("checkBoxFirstRow"), "No se encuentra la imagen");
		c.highlight("checkBoxFirstRow");

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
		return "Verificar que al tener seleccionados previamente todos los registros a modificar "
				+ "y volver a dar clic en el boton \"Select All\" se deseleccionen todos los registros "
				+ "y se limpien los datos colocados en los campos \"dia de generacion pedido\" y \" dia de ventana de entrega\"";
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
