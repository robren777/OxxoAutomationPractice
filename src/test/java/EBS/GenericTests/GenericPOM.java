package EBS.GenericTests;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class GenericPOM {
	SeleniumUtil u;
	TestCase testCase;

	public GenericPOM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	By tipo = By.xpath("//select[@id='choiceTipo']");
	@FindBy(xpath = "//select[@id='choiceTipo']")
	WebElement tipoElement;

	By nivel = By.xpath("//select[@id='choiceNivel']");
	@FindBy(xpath = "//select[@id='choiceNivel']")
	WebElement nivelElement;

	By tipoDeTasa = By.id("choiceTipoTasa");
	@FindBy(id = "choiceTipoTasa")
	WebElement tipoDeTasaElement;

	By tipoDePersona = By.id("choiceTipoPersona");
	@FindBy(id = "choiceTipoPersona")
	WebElement tipoDePersonaElement;

	By btnIr = By.xpath("//button[@id='btnIr']");
	@FindBy(xpath = "//select[@id='choiceTipo']")
	WebElement btnIrElement;

	By btnCrear = By.xpath("//button[@id='btnCrear']");
	@FindBy(xpath = "//button[@id='btnCrear']")
	WebElement btnCrearElement;

	// WebElement btnCrear2 = u.getDriver().findElement(btnCrear);

	public void selectTipo(String tipoS) {
		u.waitForLoadPage();
		u.highLight(tipo);
		u.click(tipo);
		Select optT = new Select(u.getDriver().findElement(tipo));
		optT.selectByVisibleText(tipoS);
	}

	public void selectNivel(String nivelOpt) {
		u.highLight(nivel);
		Select opt = new Select(u.getDriver().findElement(nivel));
		switch (nivelOpt) {
		case "Proveedor":
			opt.selectByIndex(1);
			break;
		case "Sucursal":
			opt.selectByIndex(2);
			break;
		}
	}

	public void selectTipoTasa(String tasaOpt) {
		u.highLight(tipoDeTasa);
		Select opt = new Select(u.getDriver().findElement(tipoDeTasa));
		switch (tasaOpt.toLowerCase(Locale.ROOT)) {
		case "f":
			opt.selectByIndex(1);
			break;
		case "i":
			opt.selectByIndex(2);
			break;
		case "":
			opt.selectByIndex(0);
			break;
		}
	}

	public void selectTipoPersona(String personaOpt) {
		u.highLight(tipoDePersona);
		Select opt = new Select(u.getDriver().findElement(tipoDePersona));
		switch (personaOpt.toLowerCase(Locale.ROOT)) {
		case "f":
			opt.selectByIndex(1);
			break;
		case "m":
			opt.selectByIndex(2);
			break;
		case "r":
			opt.selectByIndex(3);
			break;
		case "":
			opt.selectByIndex(0);
			break;
		}
	}
}
