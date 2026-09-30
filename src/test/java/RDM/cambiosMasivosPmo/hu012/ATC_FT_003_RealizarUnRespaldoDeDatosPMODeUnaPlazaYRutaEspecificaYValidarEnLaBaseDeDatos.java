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
 * DESCRIPCIÓN: Realizar una prueba que nos permita el correcto respaldo de una plaza y ruta especifica. Y nos permita validar la informacion en la Base de Datos
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_003_RealizarUnRespaldoDeDatosPMODeUnaPlazaYRutaEspecificaYValidarEnLaBaseDeDatos
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

public class ATC_FT_003_RealizarUnRespaldoDeDatosPMODeUnaPlazaYRutaEspecificaYValidarEnLaBaseDeDatos
		extends BaseExecution {

	SeleniumUtil u = new SeleniumUtil(new IETest());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_RealizarUnRespaldoDeDatosPMODeUnaPlazaYRutaEspecificaYValidarEnLaBaseDeDatos_test(
			HashMap<String, String> data) throws Exception {

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
		return "Realizar una prueba que nos permita el correcto respaldo de una plaza y ruta especifica. Y nos permita validar la informacion en la Base de Datos";
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
