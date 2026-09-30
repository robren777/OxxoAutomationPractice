package WEB.AvisosYPoliticas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.GlobalVariables;
import WEB.util.AvisosYPoliticas.utilAvisosPoliticas;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FC_004_ReporteAvisosPoliticas extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private utilAvisosPoliticas util;

	@Test(dataProvider = "data-provider")
	public void ATC_FC_004_ReporteAvisosPoliticas_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: Avisos y Politicas");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");
		String buscarUser = data.get("BuscarUser");

		/**
		 * Se inicializan las instancias
		 */
		u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		aux = new AuxMethods(u, testCase);
		util = new utilAvisosPoliticas(u, testCase);
		AC = new AccessControl(u, testCase);

		/**
		 * Inicio del flujo
		 */

		/**
		 * ********** PASO 1
		 */
		aux.logP("1. Ingresar a Access Control");
		addStep("Ingresar a Access Control e ingresar User y Password correctos, dar clic en bot�n \"Iniciar sesi�n\"");
		String url = GlobalVariables.URL_PORTAL_APPS_PORTALES_PUBLICOS_QA;
		AC.loginAccessControl(url, user, pass);

		/**
		 * ********** PASO 2
		 */
		aux.logP("2. Ir a Altas de Avisos y Politicas");
		addStep("Seleccionar \"Administracion\" del menu y seleccionar la opci�n de \"Reporte de Avisos Y Politicas\"");
		util.goToPortalGestionProveedores();
		u.hardWait(1);
		util.goToReportesAvisosPoliticas();
		u.hardWait(2);

		/**
		 * ********** PASO 3
		 */

		util.switchFrame();

		aux.logP("3. Introducir nombre de usuario");
		addStep("Introducir el nombre de usuario y dar clic en Buscar");

		aux.logS("Todos");
		testCase.addTextEvidenceCurrentStep("Todos");
		util.buscarReporteAvisosPoliticas(buscarUser, "Todos");
		testCase.addScreenShotCurrentStep(u, "Todos");

		/**
		 * ********** PASO 4
		 */
		aux.logP("4. Seleccionar los diferentes estatus");
		addStep("Buscar por los diferentes estatus");

		aux.logS("Aceptado");
		testCase.addTextEvidenceCurrentStep("Aceptado");
		util.buscarReporteAvisosPoliticas(buscarUser, "Aceptado");
		assertTrue(util.validarRegistrosTabla("Aceptado", ""));
		testCase.addScreenShotCurrentStep(u, "Aceptado");

		aux.logS("Rechazado");
		testCase.addTextEvidenceCurrentStep("Rechazado");
		util.buscarReporteAvisosPoliticas(buscarUser, "Rechazado");
		assertTrue(util.validarRegistrosTabla("Rechazado", ""));
		testCase.addScreenShotCurrentStep(u, "Rechazado");

		aux.logS("Pendiente");
		testCase.addTextEvidenceCurrentStep("Pendiente");
		util.buscarReporteAvisosPoliticas(buscarUser, "Pendiente");
		assertTrue(util.validarRegistrosTabla("Pendiente", ""));
		testCase.addScreenShotCurrentStep(u, "Pendiente");

	}

	@AfterTest
	public void afterTest() {
		u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Acceso al Portal de Aplicaciones"
				+ "\n2. Usuario con acceso a Gestion de Proveedores > Administracion"
				+ "\n3. Ingresar en el dataProvider el usuario a buscar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "MTC-FC-004-Reporte de avisos y pol�ticas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FC_004_ReporteAvisosPoliticas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
