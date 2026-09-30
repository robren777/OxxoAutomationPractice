package WEB.Facturacion;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WEB.util.AuxMethods;
import WEB.util.Facturacion.Facturacion;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
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
public class ATC_FT_007_AyudadelPortal extends BaseExecution {
	/**
	 * Se crea una instancia de SeleniumUtil, este es el objeto con el que se
	 * manipulara el navegador.
	 */
	private SeleniumUtil u;
	private Facturacion FAC;
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
	public void ATC_FT_007_AyudadelPortal_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: FACTURACION");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");//Comercio.14 passw

		/**
		 * Se abre el navegador deseado.
		 */
		//u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		// u = new SeleniumUtil(new FirefoxTest(), true); // Firefox
		 u = new SeleniumUtil(new ChromeTest(),true); // Chrome
		// u = new SeleniumUtil(new IETest(), true); // IE

		/**
		 * Se inicializan los clases a utilizar en el test.
		 */
		FAC = new Facturacion(u, testCase);
		aux = new AuxMethods(u, testCase);

		/**
		 * Inicio del flujo
		 */
		// **********************************************
		aux.logP("1. Ingresar a Facturacion");
		addStep("Ingresar a Facturacion");
		FAC.loginFacturacion(user, pass);
		// **********************************************
		aux.logP("2. Ingresar a la Seccion de Ayuda");
		addStep("Ingresar a la Seccion de Ayuda");
		FAC.clickAyuda();
		// **********************************************
		aux.logP("3. Ingresar a la Seccion de Edicion de Configuracion");
		addStep("Ingresar a la Seccion de Edicion de la Configuracion");
		FAC.clickEdicion();
		// **********************************************
		aux.logP("4. Ingresar a la Seccion de Facturacion");
		addStep("Ingresar a la Seccion de Facturacion");
		FAC.clickFact();
		// **********************************************
		aux.logP("5. Ingresar a la Seccion de Facturacion con registro");
		addStep("Ingresar a la Seccion de Facturacion con registro");
		FAC.clickFactCR();
		// **********************************************
		aux.logP("6. Ingresar a la Seccion de Facturacion sin registro");
		addStep("Ingresar a la Seccion de Facturacion sin registro");
		FAC.clickFactSR();
		// ***********************************************
		aux.logP("7. Ingresar a la Seccion de Facturacion XML");
		addStep("Ingresar a la Seccion de Facturacion XML");
		FAC.clickFactXML();
		// **********************************************
		aux.logP("8. Ingresar a la Seccion de Inicio de sesion");
		addStep("Ingresar a la Seccion de Inicio de sesion");
		FAC.clickInic();
		// **********************************************
		aux.logP("9. Ingresar a la Seccion de Registro");
		addStep("Ingresar a la Seccion de Registro");
		FAC.clickReg();
		// **********************************************
		aux.logP("10. Ingresar a la Seccion de Reimpresion de factura");
		addStep("Ingresar a la Seccion de Reimpresion de factura");
		FAC.clickReimp();
		// **********************************************
		aux.logP("11. Ingresar a la Seccion de Descarga guia de ayuda");
		addStep("Ingresar a la Seccion de Descarga guia de ayuda");
		FAC.clickDescar();
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
		return "1. Acceso al Portal de Facturacion" + "\n2. Usuario Interno de Facturacion";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Contar con un usuario Interno con acceso al portal de facturacion para validar "
				+ "que se tenga acceso correcto al portal de facturacion";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "David Perez Couto";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_AyudadelPortal";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
