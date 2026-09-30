package WEB.AvisosYPoliticas;

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

public class ATC_FC_001_VisualizarAvisosYPoliticas extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private utilAvisosPoliticas util;

	@Test(dataProvider = "data-provider")
	public void ATC_FC_001_VisualizarAvisosYPoliticas_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: Avisos y Politicas");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");

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
		aux.logP("1. Ingresar a Access Control");
		addStep("Ingresar a Access Control e ingresar User y Password correctos, dar clic en bot�n \"Iniciar sesi�n\"");
		String url = GlobalVariables.URL_PORTAL_APPS_PORTALES_PUBLICOS_QA;
		AC.loginAccessControl(url, user, pass);

		aux.logP("2. Seleccionar Avisos y Politicas del menu");
		addStep("Seleccionar \"Avisos y Politicas\" del menu y validar que se muestra el listado "
				+ "de Avisos y Pol�ticas disponibles para el usuario");
		util.goToPortalGestionProveedores();
		u.hardWait(1);
		util.goToAvisosPoliticas();
		u.hardWait(3);
		util.getAvisosPoliticas();

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
		return "1. Acceso al Portal de Aplicaciones" + "\n2. Usuario con acceso a Gestion de Proveedores > Avisos y Politicas";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "MTC-FC-001-Aceptar Avisos y politicas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FC_001_VisualizarAvisosYPoliticas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
