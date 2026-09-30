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

/**
 * Requisito mandatorios para el Nombre (Java): -No incluir acentos, espacios en
 * blanco ni caracteres especiales en el nombre -La longitud del nombre no debe
 * exceder de 100 caracteres -El nombre del caso de prueba debe ser igual que el
 * que se usa en Test, solo que debe ser precedido por la siglas o nombre del
 * proyecto al que pertenece. -Cuando se trata de la primera clase para un caso
 * de prueba no lleva ningun identificador de proyecto. 
 * 
 * [Tipo ejecucion]_[TipoCaso de prueba]_[Numero de Caso]_[Nombre caso de prueba]_[Proyecto] 
 * 
 * El tipo de ejecucion siempre debe ser ATC para los casos de prueba automatizados. Ej:
 * ATC_FT_001_TestExample_Project (cuando es para un proyecto) 
 * ATC 
 * - AutomatedTest Case FT - Functional Test 001 
 * - Test case Nombre 
 * - TestExample Project 
 * - Proyecto para el que se desarrollo el caso de prueba
 *
 * La clase siempre debe estar extendida de "BaseExecution", para usar los
 * metodos y herramientas del Framework de Oxxo
 * 
 */
public class ATC_FT_001_AccesoPortalAplicacionesUsuarioInterno extends BaseExecution {
	/**
	 * Se crea una instancia de SeleniumUtil, este es el objeto con el que se
	 * manipulara el navegador.
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;

	/**
	 * Con la sintaxix @Test del TestNG, se indica que el metodo que esta a
	 * continuacion es un Test.
	 * 
	 * Requisito mandatorios para el Nombre (Test): -No incluir acentos, espacios en
	 * blanco ni caracteres especiales en el nombre -La longitud del nombre no debe
	 * exceder de 100 caracteres -El nombre del caso de prueba debe ser unico. 
	 * [Tipo
	 * ejecucion]_[Tipo Caso de prueba]_[Numero de Caso]_[Nombre caso de
	 * prueba]_Test El tipo de ejecucion siempre debe ser ATC para los casos de
	 * prueba automatizados. Ej: ATC_FT_001_TestExample_Test ATC - Automated Test
	 * Case FT - Functional Test 001 - Test case Nombre - TestExample
	 * 
	 * Se manda como parametro el Data Provider: este es un archivo que esta alojado
	 * en Config/Input/DataProviders/TestExample/ATC_FT_001_TestExample.xlsx
	 * 
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_AccesoPortalAplicacionesUsuarioInterno_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: Access Control");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");

		/**
		 * Se abre el navegador deseado.
		 */
		u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		// u = new SeleniumUtil(new FirefoxTest(), true); // Firefox
		// u = new SeleniumUtil(new ChromeTest(),true); // Chrome
		// u = new SeleniumUtil(new IETest(), true); // IE

		/**
		 * Se inicializan los clases a utilizar en el test.
		 */
		AC = new AccessControl(u, testCase);
		aux = new AuxMethods(u, testCase);

		/**
		 * Inicio del flujo
		 */
		aux.logP("1. Ingresar a Access Control");
		addStep("Ingresar a Access Control");
		AC.loginAccessControl(user, pass);

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
			// throw new AssertionError("Fallo al ejecutar el query");
		}

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
				+ "\n2. Usuario Interno de Access Control"
				+ "\n3. Acceso a la BD 10.184.32.194:1535/FCIASQA.FEMCOM.NET | user: QAVIEW";
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
		return "ATC_FT_001_AccesoPortalAplicacionesUsuarioInterno";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
