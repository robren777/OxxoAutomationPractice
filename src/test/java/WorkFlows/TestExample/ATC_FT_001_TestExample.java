package WorkFlows.TestExample;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WorkFlows.util.TestExample.utilsTestExample;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

/**
 * Requisito mandatorios para el Nombre (Java): 
	-No incluir acentos, espacios en blanco ni caracteres especiales en el nombre
	-La longitud del nombre no debe exceder de 100 caracteres
	-El nombre del caso de prueba debe ser igual que el que se usa en Test, solo que debe ser precedido por la siglas o nombre del proyecto al que pertenece.
	-Cuando se trata de la primera clase para un caso de prueba  no lleva ningún identificador de proyecto.	
		[Tipo  ejecución]_[Tipo Caso de prueba]_[Numero de Caso]_[Nombre  caso de prueba]_[Proyecto]	
	El tipo de ejecución siempre debe ser ATC para los casos de prueba automatizados.	
		Ej: ATC_FT_001_TestExample_Project   (cuando es para un proyecto)	
	ATC – Automated Test Case
	FT - Functional Test
	001 - Test case
	Nombre – TestExample
	Project – Proyecto para el que se desarrolló el caso de prueba
 *
 * La clase siempre debe estar extendida de "BaseExecution", para usar los métodos y herramientas del Framework de Oxxo
 * 
 */
public class ATC_FT_001_TestExample extends BaseExecution {
	/**
	 * Se crea una instancia de SeleniumUtil, este es el objeto con el que se manipulará el navegador.
	 */
	private SeleniumUtil u;
	private utilsTestExample utilExample;
	
	/**
	 * Con la sintaxix @Test del TestNG, se indica que el método que está a continuación es un Test.
	 * 
	 * Requisito mandatorios para el Nombre (Test): 
		-No incluir acentos, espacios en blanco ni caracteres especiales en el nombre
		-La longitud del nombre no debe exceder de 100 caracteres
		-El nombre del caso de prueba debe ser único.		
			[Tipo  ejecución]_[Tipo Caso de prueba]_[Numero de Caso]_[Nombre  caso de prueba]_Test		
		El tipo de ejecución siempre debe ser ATC para los casos de prueba automatizados.		
			Ej: ATC_FT_001_TestExample_Test		
		ATC – Automated Test Case
		FT - Functional Test
		001 - Test case
		Nombre – TestExample
	 * 
	 * Se manda como parámetro el Data Provider: este es un archivo que está alojado en Config/Input/DataProviders/TestExample/ATC_FT_001_TestExample.xlsx
	 * 
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_TestExample_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");
		
		/**
		 * Obtener datos del Data Provider
		 */
		String exampleData = data.get("data");
		
		
		/**
		 * Se abre el navegador deseado.
		 */
		u = new SeleniumUtil(new FirefoxTest(), true); // Firefox
		//u = new SeleniumUtil(new ChromeTest(),true); // Chrome
		//u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		//u = new SeleniumUtil(new IETest(), true); // IE
		
		
		/**
		 * Se inicializan los utils a utilizar en el test.
		 */
		utilExample = new utilsTestExample(u, testCase);
		
		
		/**
		 * Cada que se inicia un paso de la matriz se debe indicar en el script para que este se vea reflejado en la evidencia.
		 * 
		 * Se recomienda borrar cookies cada que se abre una nueva página para evitar errores.
		 * 
		 */
		addStep("Abrir Google");
		u.hardWait(1);
		u.getDriver().manage().deleteAllCookies();
		u.get("https://www.google.com/");
		
		
		/**
		 * Se puede agregar texto para que aparezca en la evidencia.
		 * 
		 * Cada paso de preferencia debe llevar una screenshot cómo evidencia.
		 * 
		 */
		testCase.addBoldTextEvidenceCurrentStep("Texto en Negritas");
		testCase.addTextEvidenceCurrentStep("Texto Normal");
		testCase.addScreenShotCurrentStep(u, "Google");
		
		
		addStep("Buscar Selenium");
		utilExample.searchTextPom("Selenium");
		testCase.addScreenShotCurrentStep(u, "Resultado");
		
		addStep("Ir a la página de Selenium");
		utilExample.goToSeleniumPage();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "Pagina Selenium");
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
		return "Prerequisitos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Descripcion del caso de prueba";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Nombre del automatizador";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_TestConnection";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
