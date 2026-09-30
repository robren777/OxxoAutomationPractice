package WEB.AccessControl;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.GlobalVariables;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_002_AccesoPortalAplicacionesUsuarioExterno extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_AccesoPortalAplicacionesUsuarioExterno_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: Access Control");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");
		String newPass = data.get("NewPass");

		/**
		 * Se inicializan las instancias
		 */
		u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		AC = new AccessControl(u, testCase);
		aux = new AuxMethods(u, testCase);

		/**
		 * Inicio del flujo
		 */
		aux.logP("1. Ingresar a Access Control");
		addStep("Ingresar a Access Control e ingresar User y Password correctos, dar clic en bot�n \"Iniciar sesi�n\"");
		String urlP = GlobalVariables.URL_PORTAL_APPS_PORTALES_PUBLICOS_QA;
		AC.loginAccessControl(urlP, user, pass);

		aux.logP("2. Validar en Tabla");
		addStep("Validar en la tabla XXAC_USERS la informaci�n correcta del usuario ingresado");
		String hostDB = GlobalVariables.DB_HOST_FCIASQA_QAVIEW;
		String userDB = GlobalVariables.DB_USER_FCIASQA_QAVIEW;
		String passDB = GlobalVariables.DB_PASSWORD_FCIASQA_QAVIEW;
		String queryDB = "select ID_USER, USER_FULL_NAME, LAST_UPDATE_DATE, USER_TYPE from xxac.XXAC_USERs where ID_USER = \'" + user + "\'";
		
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(hostDB, userDB, passDB);
			String query_ItemStatus = queryDB;
			aux.logS(query_ItemStatus);

			queryResult = executeQuery(dbXXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			assertNotNull(queryResult.isEmpty(), "No se encontraro el usuario");

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			testCase.addTextEvidenceCurrentStep("Fallo al ejecutar el query");
			assertTrue(false);
		}

		aux.logP("3. Cambio de Contrasenia");
		addStep("Validar Cambio de Contrasenia");
		u.getDriver().manage().deleteAllCookies();
		String url = GlobalVariables.URL_PORTAL_APPS;
		testCase.addTextEvidenceCurrentStep(url);
		u.get(url);
		AC.cambiarContrasenia(user, pass, newPass);
		
		AC.loginAccessControl(urlP, user, newPass);
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
		return "1. Acceso al Portal de Aplicaciones" + "\n2. Usuario Externo de Access Control"
				+ "\n3. Acceso a la BD 10.184.32.194:1535/FCIASQA.FEMCOM.NET | user: QAVIEW"
				+ "\n4. Ingresar en el dataProvider la nueva constrase�a a asignar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Contar con un usuario Interno con acceso al portal de aplicaciones para validar "
				+ "que se tenga acceso correcto al portal de aplicaciones";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_AccesoPortalAplicacionesUsuarioExterno";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
