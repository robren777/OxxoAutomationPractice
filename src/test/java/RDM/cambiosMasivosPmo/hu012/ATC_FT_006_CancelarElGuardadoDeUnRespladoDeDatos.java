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
 * DESCRIPCIÓN: Realizar una prueba que nos permita corroborar que no se guarde el Respaldo de Datos al dar Cancelar o Exit a la ventana Informacion para Respaldo. Y validar que no se haya guardado en la Base de Datos
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_006_CancelarElGuardadoDeUnRespladoDeDatos
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

public class ATC_FT_006_CancelarElGuardadoDeUnRespladoDeDatos extends BaseExecution {

	SeleniumUtil u = new SeleniumUtil(new IETest());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_CancelarElGuardadoDeUnRespladoDeDatos_test(HashMap<String, String> data)
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
		assertTrue(c.buscarPorPlazaRutaYSelecRespaldarDatos(plaza, ruta),"problemas en: Filtros Plaza, Ruta y Respaldar Datos");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 7
		addStep("Dar clic al boton Select All");
		assertTrue(c.validatePattern("CambiosMasivosSelectAll"),"Problemas con el botón Seleccionar All");
		testCase.addScreenShotCurrentStep(" ");
		c.click("CambiosMasivosSelectAll");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 8
		addStep("Dar clic al botón Respaldar Datos");
		assertTrue(c.validatePattern("CambiosMasivosRespaldarDatos"), "Problemas con el botón Respaldar Datos");
		testCase.addScreenShotCurrentStep(" ");
		c.click("CambiosMasivosRespaldarDatos");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 9
		addStep("Capturar el campo Nombre Proyecto (AUT_TEST06_10GUD_GDL101_DDMMAAAA)");
		assertTrue(c.validatePattern("CambiosMasivosRespladarNombreProyectoVentana"), "Problemas, no visualiza ventana para capturar nombre del proyecto");
		testCase.addScreenShotCurrentStep(" ");
		assertTrue(c.validatePattern("CambiosMasivosRespladarNombreProyecto"), "Problemas, no se puede capturar nombre del proyecto");
		testCase.addScreenShotCurrentStep(" ");
		c.typeInput("CambiosMasivosRespladarNombreProyecto", nombreProyecto);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 10
		addStep("Dar clic al botón EXIT");
		assertTrue(c.validatePattern("CambiosMasivosRespladarNombreProyectoEXIT"), "Problemas, no visualiza el botón OK");
		testCase.addScreenShotCurrentStep(" ");
		c.click("CambiosMasivosRespladarNombreProyectoEXIT");
		testCase.addScreenShotCurrentStep("CambiosMasivosRespladarNombreProyectoEXIT");
		// PASO 11
		addStep("Validar que NO se visualice el respaldo en la Base de datos de RDM100.XXFC_PMO_ROUTE_BKP y RDM100.XXFC_PMO_TIENDA_BKP");
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp01(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp02(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp03(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp04(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp05(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp06(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp07(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp08(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp09(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp10(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp11(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp12(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp13(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoRouteBkp14(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoTiendaBkp01(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoTiendaBkp02(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoTiendaBkp03(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoTiendaBkp04(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoTiendaBkp05(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoTiendaBkp06(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoTiendaBkp07(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoTiendaBkp08(ruta, nombreProyecto)).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryPmoTiendaBkp09(ruta, nombreProyecto)).isEmpty());
		cq.consultaBKP(dbConecta, ruta, nombreProyecto, false);
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
		return "Realizar una prueba que nos permita corroborar que no se guarde el Respaldo de Datos al dar Cancelar o Exit a la ventana Informacion para Respaldo. Y validar que no se haya guardado en la Base de Datos";
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
