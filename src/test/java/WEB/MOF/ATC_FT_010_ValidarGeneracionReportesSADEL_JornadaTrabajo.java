package WEB.MOF;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.MOF.MOF;
import modelo.BaseExecution;
import utils.selenium.MEdgeEspTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_010_ValidarGeneracionReportesSADEL_JornadaTrabajo extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private MOF util;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_ValidarGeneracionReportesSADEL_JornadaTrabajo_Test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("Framework Web: Monitor de Obligaciones Fiscales MOF");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");

		/**
		 * Se inicializan las instancias
		 */
		u = new SeleniumUtil(new MEdgeEspTest(), true); // Edge
		aux = new AuxMethods(u, testCase);
		util = new MOF(u, testCase, aux);
		AC = new AccessControl(u, testCase);

		/**
		 ***** PASO 1
		 */
		aux.logP("1. Ingresar a Access Control");
		addStep("Ingresar a Access Control e ingresar User y Password correctos, dar clic en bot�n \"Iniciar sesi�n\"");
		String url = "http://fcowlq02.femcom.net:7777/AccessControl/pages/login_form.jsf";
		AC.loginAccessControl(url, user, pass);

		/**
		 ***** PASO 2
		 */
		aux.logP("2. Ingresar al Portal \"Monitor de Oblicaciones Fiscales\"");
		addStep("Portal Monitor de Oblicaciones Fiscales");
		util.clickPortalMonitorObligacionesFiscales(1);
		assertTrue(util.IsMonitorObligacionesFiscalesActive());
		u.hardWait(1);

		/**
		 ***** PASO 3
		 */
		aux.logP("3. Seleccionar menu");
		addStep("Seleccionar el modulo \"Reportes SADEL\" y la opcion \"Jornadas de Trabajo\"");
		util.SelectMOFReportesSADEL();
		util.OpReportesSADELJornadaTrabajo();
		u.hardWait(3);

		/**
		 ***** PASO 4
		 */
		aux.logP("4. Generar Reporte");
		addStep("Seleccionar Plaza y Lider y Generar Reporte");
		util.ingresarDatosJornadaTrabajo("10GUD - Plaza Guadalajara", " ADRIANA CRUZ  LORENZO ");
		util.descargarReporteJornadasTrabajo();

		/**
		 ***** PASO 5
		 */
		aux.logP("5. Descargar Reporte");
		addStep("Descargar Reporte");
		util.DescargarExcelJornadasTrabajo();

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
		return "1. Acceso al Portal de Aplicaciones" + "\n2. Usuario con acceso a MOF > Reportes SADEL"
				+ "\n3. Ingresar en el dataProvider el usuario y la password";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Objetivo: Se validar� que se muestren correctamente el Reporte SADEL -> Jornadas de trabajo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_010_ValidarGeneracionReportesSADEL_JornadaTrabajo";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
