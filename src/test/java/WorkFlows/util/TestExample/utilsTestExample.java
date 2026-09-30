package WorkFlows.util.TestExample;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class utilsTestExample {

	private SeleniumUtil u;
	private TestCase testCase;
	private pomTestExample pom;
	private WebDriverWait wait;

	/**
	 * IDENTIFICACION DE WEB ELEMENTS
	 * 
	 * Por buenas practicas se recomienda hacer la busqueda de los elementos por
	 * medio de los siguientes selectores: - By.id - By.name - By.className -
	 * By.tagName - By.partialLinkText - By.linkText - By.cssSelector Evitar a
	 * medida que se pueda el uso de xpath ya que es mas lento y pesado de procesar.
	 * 
	 */
	By inputSearch = By.name("q");
	By buttonSearch = By.cssSelector("input[value=\"Buscar con Google\"]");
	By linkSelenium = By.partialLinkText("https://www.selenium.dev");

	/**
	 * Constructor. Recibe el SeleniumUtil y el testCase utilizado en el test.
	 * 
	 * @param u
	 * @param testCase
	 * @author Esmeralda Garcia Torres
	 */
	public utilsTestExample(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.pom = new pomTestExample(u, testCase);
	}

	/**
	 * Busca el texto indicado en la pagina de google. Usando Selenium puro.
	 * 
	 * @param text: texto a buscar.
	 * @author Esmeralda Garcia Torres
	 */
	public void searchTextSelenium(String text) {
		WebDriver driver = new FirefoxDriver();
		driver.get("https://google.com");

		WebElement inputSearchElement = driver.findElement(inputSearch);
		inputSearchElement.sendKeys(text);
		testCase.addScreenShotCurrentStep(u, "Texto ingresado");

		WebElement buttonSearchElement = driver.findElement(buttonSearch);
		buttonSearchElement.click();
		testCase.addScreenShotCurrentStep(u, "Busqueda");
	}

	/**
	 * Busca el texto indicado en la pagina de google. Usando el Framework.
	 * 
	 * @param text: texto a buscar.
	 * @author Esmeralda Garcia Torres
	 */
	public void searchTextFramework(String text) {
		u.sendKeys(inputSearch, text);
		testCase.addScreenShotCurrentStep(u, "Texto ingresado");

		u.click(buttonSearch);
		testCase.addScreenShotCurrentStep(u, "Busqueda");
	}

	/**
	 * Busca el texto indicado en la pagina de google. Usando el pom.
	 * 
	 * @param text: texto a buscar.
	 * @author Esmeralda Garcia Torres
	 */
	public void searchTextPom(String text) {
		pom.enterOption("Buscar en Gloogle", text, inputSearch);
		pom.clickOpcion("Buscar con Google", buttonSearch);
	}

	/**
	 * Dirige a la pagina de selenium
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToSeleniumPage() {
		u.hardWait(3);
		pom.clickOpcion("SeleniumPage", linkSelenium);
	}

}
