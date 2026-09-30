package WEB.PreguntasF;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WEB.util.AuxMethods;
import WEB.util.PreguntasF.Preguntas;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * Requisito mandatorios para el Nombre (Java): -No incluir acentos, espacios en
 * blanco ni caracteres especiales en el nombre -La longitud del nombre no debe
 * exceder de 100 caracteres -El nombre del caso de prueba debe ser igual que el
 * que se usa en Test, solo que debe ser precedido por la siglas o nombre del
 * proyecto al que pertenece. -Cuando se trata de la primera clase para un caso
 * de prueba no lleva ningun identificador de proyecto. [Tipo ejecucion]_[Tipo
 * Caso de prueba]_[Numero de Caso]_[Nombre caso de prueba]_[Proyecto] El tipo
 * de ejecucion siempre debe ser ATC para los casos de prueba automatizados. Ej:
 * ATC_FT_001_TestExample_Project (cuando es para un proyecto) ATC - Automated
 * Test Case FT - Functional Test 001 - Test case Nombre - TestExample Project -
 * Proyecto para el que se desarrollo el caso de prueba
 *
 * La clase siempre debe estar extendida de "BaseExecution", para usar los
 * metodos y herramientas del Framework de Oxxo
 * 
 */
public class ATC_FT_002_EditarAdministradordetemas extends BaseExecution {
	/**
	 * Se crea una instancia de SeleniumUtil, este es el objeto con el que se
	 * manipulara el navegador.
	 */
	private SeleniumUtil u;
	private Preguntas PREG;
	private AuxMethods aux;

	/**
	 * Con la sintaxix @Test del TestNG, se indica que el metodo que esta a
	 * continuacion es un Test.
	 * 
	 * Requisito mandatorios para el Nombre (Test): -No incluir acentos, espacios en
	 * blanco ni caracteres especiales en el nombre -La longitud del nombre no debe
	 * exceder de 100 caracteres -El nombre del caso de prueba debe ser unico. [Tipo
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
	public void ATC_FT_002_EditarAdministradordetemas_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: PREGUNTAS");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass"); // Comercio.45

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
		PREG = new Preguntas(u, testCase);
		aux = new AuxMethods(u, testCase);

		/**
		 * Inicio del flujo
		 */
		aux.logP("1. Ingresar a Access Control");
		addStep("Ingresar a Access Control");
		PREG.loginAccessControl(user, pass);
		// **********************************************

		aux.logP("2. Ingresar a Preguntas Frecuentes");
		addStep("Ingresar a Preguntas Frecuentes");
		PREG.clickPregFrec();
		// **********************************************

		aux.logP("3. Ingresar a Menu Administrador de Temas");
		addStep("Ingresar a Menu Administrador de Temas");
		PREG.clickMenuAdminTemas();

		// **********************************************
		aux.logP("4. Editar tema");
		addStep("Editar tema");
		PREG.editarTema();
		// **********************************************

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
		return "1. Acceso al Portal de Aplicaciones" + "\n2. Usuario Access Control";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Editar administrador de temas   ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "David Perez Couto";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_EditarAdministradordetemas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
