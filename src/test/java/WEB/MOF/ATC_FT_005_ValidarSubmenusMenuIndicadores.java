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

public class ATC_FT_005_ValidarSubmenusMenuIndicadores extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private MOF util;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarSubmenusMenuIndicadores_Test(HashMap<String, String> data) throws Exception {
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
		// String url = GlobalVariables.URL_PORTAL_APPS_PORTALES_PUBLICOS_QA;
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
		aux.logP("3. Seleccionar Abasto del menu");
		addStep("Seleccionar el modulo \"Indicadores\" y la opcion \"Tasas IETU\"");
		util.SelectMOFIndicadores();
		util.OpIndicadoresTasasIETU();
		u.hardWait(3);

		/**
		 ***** PASO 4
		 */
		aux.logP("4. Seleccionar un registro de la tabla y Editar");
		addStep("Seleccionar un registro de la tabla y Editar");
		String nuevaTasa = aux.cadenaAleatoriaNumerica(2);
		util.seleccionarTasa();
		u.hardWait(1);
		util.ingresarTasa(nuevaTasa);
		u.hardWait(1);

		/**
		 ***** PASO 5
		 */
		aux.logP("5. Validar cambio");
		addStep("Validar cambio");
		util.validarCambioTasa(nuevaTasa);

		/**
		 ***** PASO 6
		 */
		aux.logP("6. Descargar Excel");
		addStep("Descargar Excel");
		util.descargarExcel();
		testCase.addTextEvidenceCurrentStep("Para obtener los archivos debe ir a la carpeta de descargas del equipo.");
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
		return "1. Acceso al Portal de Aplicaciones" + "\n2. Usuario con acceso a MOF > Indicadores"
				+ "\n3. Ingresar en el dataProvider el usuario y la password";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Objetivo: Se validar� que se muestren correctamente los submen�s del men� Indicadores";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_ValidarSubmenusMenuIndicadores";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
