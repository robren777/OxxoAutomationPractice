package RDM.cambiosMasivosPmo.hu012;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import RDM.util.CambiosMasivosQueries;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * DESCRIPCIÓN: Realizar una prueba que nos permita el correcto respaldo de una plaza y todas sus rutas. Y nos permita validar la informacion en la Base de Datos
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_004_RealizarUnRespaldoDeDatosPMODeUnaPlazaYTodasSusRutasYValidarEnLaBaseDeDatos
 * 
 * PREREQUISITOS:
 * 1. Edge
 * 2.-Tener acceso a la aplicación
 * 
 * INSUMOS:
 * Perfil: Coordinador y/o Analista de Sistemas
 * Usuario:
 * Contraseña:
 * RUTA
 * PLAZA
 * 
 * @author Josué Cruz García
 * @date 03/Enero/2025
 */

public class ATC_FT_004_RealizarUnRespaldoDeDatosPMODeUnaPlazaYTodasSusRutasYValidarEnLaBaseDeDatos extends BaseExecution {

	SeleniumUtil u = new SeleniumUtil(new IETest());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_RealizarUnRespaldoDeDatosPMODeUnaPlazaYTodasSusRutasYValidarEnLaBaseDeDatos_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Variables
		 */
		String plaza = data.get("plaza");
		String ruta = data.get("ruta");
		String nombreProyecto = data.get("nombreProyecto");
		/*
		 * UTILERIAS
		 */
		SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCRD71CU, GlobalVariables.DB_USER_FCRD71CU,
				GlobalVariables.DB_PASSWORD_FCRD71CU);
		CambiosMasivosQueries cq = new CambiosMasivosQueries(testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		CambiosMasivos c = new CambiosMasivos(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol, testCase);
		// PASO 1
		addStep("Ingresar a la liga http://10.180.49.86:8071");
		System.out.println("PASO 1");
		// PASO 2
		addStep("Ingresar a RDM como Coordinador y/o Analista de Sistemas" + "Capturar:" + "- Usuario" + "- Contraseña"
				+ "Y dar clic al boton de Logon");
		System.out.println("PASO 2");
		c.logInCambiosMasivos();
		// PASO 3
		addStep("Dar clic al menu: Support Functions > Reports > XXFC-PMO_Cambios_Masivos ");
		System.out.println("PASO 3");
		c.clickPmoCambiosMasivos();
		assertTrue(c.validarPantallaInicio(), "Pantalla validada");
		// PASO 4
		addStep("Ingresa los filtros:\n" + "- Plaza\n" + "- Ruta");
		// PASO 5
		addStep("En la sección de Tipo de movimiento seleccionar la opción:\n" + "\n" + "- Respaldar Datos PMO");
		// PASO 6
		addStep("Dar clic al botón de Execute;");
		assertTrue(c.buscarPorPlazaRutaYSelecRespaldarDatos(plaza, ruta),
				"problemas en: Filtros Plaza, Ruta y Respaldar Datos");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 7
		addStep("Dar clic al boton Select All");
		// PASO 8
		addStep("Dar clic al botón Respaldar Datos");
		assertTrue(c.validarBotonRespaldarDatos(), "Problemas con los botones Seleccionar All y Respaldar Datos");
		testCase.addScreenShotCurrentStep(" ");
		assertTrue(c.clicBotonRespaldarDatos(), "Error en: Selección de registros y clic al boton de Respaldar Datos");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 9
		addStep("Capturar el campo Nombre Proyecto (PruebaBTEP602DDMMAAAA)");
		// PASO 10
		addStep("Dar clic al botón OK");
		// PASO 11
		addStep("Dar clic al botón de Yes para aplicar cambios");
		assertTrue(c.validarVentanaNombreProyecto(), "Problemas en: Ventana de capturar Nombre Proyecto");
		testCase.addScreenShotCurrentStep(" ");
		assertTrue(c.capturarNombreProyecto(nombreProyecto), "Problemas en: Captura del nombre de proyecto");
		testCase.addScreenShotCurrentStep(" ");
		assertTrue(c.validarConfirmacionRespaldo(), "Problemas al: Guardar respaldo");
		testCase.addScreenShotCurrentStep(" ");
		c.click("CambiosMasivosRespladarRealizadoYES");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 12
		addStep("Validar que se visualice el respaldo en la Base de datos de RDM100.XXFC_PMO_ROUTE_BKP y RDM100.XXFC_PMO_TIENDA_BKP");
		assertFalse(executeQuery(dbConecta, cq.query2PmoRouteBkp01(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoRouteBkp01_1(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoRouteBkp02(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoRouteBkp03(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoRouteBkp04(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoRouteBkp05(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoRouteBkp06(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoRouteBkp07(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoTiendaBkp01(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoTiendaBkp02(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoTiendaBkp03(nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.query2PmoTiendaBkp04(nombreProyecto)).isEmpty());
		cq.consultaBKP02(dbConecta, nombreProyecto, false);
		dbConecta.close();
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1.- Edge\n"
				+ "2.-Tener acceso a la aplicación"
				+ "3.- Acceso a las tablas RDM100.XXFC_PMO_ROUTE_BKP y RDM100.XXFC_PMO_TIENDA_BKP";
	}

	@Override
	public String setTestDescription() {
		return "Realizar una prueba que nos permita el correcto respaldo de una plaza y todas sus rutas. Y nos permita validar la informacion en la Base de Datos";
	}

	@Override
	public String setTestDesigner() {
		return "JosueC1@hexaware.com";
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
