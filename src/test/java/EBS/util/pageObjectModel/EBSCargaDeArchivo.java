package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSCargaDeArchivo {
	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public EBSCargaDeArchivo(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	By btnCargar = By.xpath("//button[@id='btnSubmit']");
	@FindBy(xpath = "//button[@id='btnSubmit']")
	public WebElement btnCargarElement;

	By proceso = By.xpath("//input[@id='lovProceso']");
	@FindBy(xpath = "//input[@id='lovProceso']")
	public WebElement procesoElement;

	By buscar = By.xpath("//img[@title='Buscar: Proceso']/parent::a");
	@FindBy(xpath = "//img[@title='Buscar: Proceso']/parent::a")
	public WebElement buscarElement;

	By chooseFile = By.xpath("//input[@id='msgFileUpload_oafileUpload']");
	@FindBy(xpath = "//input[@id='msgFileUpload_oafileUpload']")
	public WebElement chooseFileElement;

	By solicitudConcurrenteAlert = By.xpath("//div[contains(text(),'Se inicio solicitud concurrente:')]");
	@FindBy(xpath = "//div[contains(text(),'Se inicio solicitud concurrente:')]")
	public WebElement solicitudConcurrenteAlertElement;

	By paginaInicial = By.xpath("//img[@title='Página Inicial']/parent::div/parent::a");
	@FindBy(xpath = "//img[@title='Página Inicial']/parent::div/parent::a")
	public WebElement paginaInicialElement;

	public boolean getNumeroConcurrente() {
		boolean flag = false;
		String numConcurrente = solicitudConcurrenteAlertElement.getText();
		if (numConcurrente.isEmpty()) {
			System.out.println("No se encontro concurrente");
		} else {
			flag = true;
			System.out.println(numConcurrente);
		}
		return flag;
	}
}
