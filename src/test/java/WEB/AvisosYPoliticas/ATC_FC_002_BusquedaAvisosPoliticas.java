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

public class ATC_FC_002_BusquedaAvisosPoliticas extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private utilAvisosPoliticas util;

	@Test(dataProvider = "data-provider")
	public void ATC_FC_002_BusquedaAvisosPoliticas_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: Avisos y Politicas");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");
		String fechaIni = data.get("FechaInicial");
		String fechaFin = data.get("FechaFinal");

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
		addStep("Seleccionar \"Administracion\" del menu y seleccionar la opci�n de \"Alta de Avisos Y Politicas\"");
		util.goToPortalGestionProveedores();
		u.hardWait(1);
		util.goToAltaAvisosPoliticas();
		u.hardWait(2);

		/**
		 * ********** PASO 3
		 */
		util.switchFrame();
		
		aux.logP("3. Buscar por los diferentes filtros");
		addStep("Seleccionar Fecha inicial y fecha final, clic en Buscar. Buscar por los diferentes filtros");
		
		util.asignarFechas(fechaIni, fechaFin);

		aux.logP("Todos");
		testCase.addTextEvidenceCurrentStep("Todos");
		util.buscarAvisosPoliticas("Todos");
		testCase.addScreenShotCurrentStep(u, "Todos");

		aux.logP("Borrador");
		testCase.addTextEvidenceCurrentStep("Borrador");
		util.buscarAvisosPoliticas("Borrador");
		u.hardWait(1);
		testCase.addTextEvidenceCurrentStep("Validar en la tabla que solo se muestren los registros del estatus seleccionado");
		assertTrue(util.validarRegistrosTabla("Borrador", ""),"No se encontro uno de los valores en uno de los registros");
		testCase.addScreenShotCurrentStep(u, "Borrador");

		aux.logP("Expirado");
		testCase.addTextEvidenceCurrentStep("Expirado");
		util.buscarAvisosPoliticas("Expirado");
		testCase.addTextEvidenceCurrentStep("Validar en la tabla que solo se muestren los registros del estatus seleccionado");
		assertTrue(util.validarRegistrosTabla("Expirado", ""),"No se encontro uno de los valores en uno de los registros");
		testCase.addScreenShotCurrentStep(u, "Expirado");

		aux.logP("Publicado");
		testCase.addTextEvidenceCurrentStep("Publicado");
		util.buscarAvisosPoliticas("Publicado");
		testCase.addTextEvidenceCurrentStep("Validar en la tabla que solo se muestren los registros del estatus seleccionado");
		assertTrue(util.validarRegistrosTabla("Publicado", ""),"No se encontro uno de los valores en uno de los registros");
		testCase.addScreenShotCurrentStep(u, "Publicado");

		aux.logP("Pausado");
		testCase.addTextEvidenceCurrentStep("Pausado");
		util.buscarAvisosPoliticas("Pausado");
		testCase.addTextEvidenceCurrentStep("Validar en la tabla que solo se muestren los registros del estatus seleccionado");
		assertTrue(util.validarRegistrosTabla("Pausado", ""),"No se encontro uno de los valores en uno de los registros");
		testCase.addScreenShotCurrentStep(u, "Pausado");

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
				+ "\n3. Ingresar en el dataProvider las fechas de inicio y fin de la b�squeda";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "MTC-FC-002-Buscar Avisos y pol�ticas por Estatus";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FC_002_BusquedaAvisosPoliticas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
