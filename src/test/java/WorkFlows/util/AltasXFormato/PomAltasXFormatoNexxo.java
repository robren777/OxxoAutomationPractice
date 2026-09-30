package WorkFlows.util.AltasXFormato;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PomAltasXFormatoNexxo {
	SeleniumUtil u;
	private WebDriverWait wait;
	TestCase testCase;
	
	
	//Your connection isn't private//
	
	private By btnAdvance = By.id("details-button");
	private By linkContinue = By.id("proceed-link");

	
	//LOGIN
	
	private By locationUserInput = By.id("login:userName");
	private By locationPassInput = By.id("login:userPass");
	private By locationLoginButton = By.id("login:loginButton");
	
	//INICIO PORTAL APLICACIONES
	private By locationMenuInicio = By.id("menu_cnt");
	By btnReplenishment = By.xpath("//a[contains(text(),'Replenishment')]");
	By btnAltasXFormato = By.xpath("//a[text()='Altas por Formato']");
	
	By menuAltasXFormato = By.id("e0_0i");
	By subMenuAltasXFormato = By.id("e0_1i");
	
	By menuRepleReplenishment = By.id("e0_3i");
	By subMenuRepleValidacionReple = By.id("e0_4i");
	
	//Pantalla Altas por Formato Ver. 1.0
	By txtArticulo = By.id("txtArticuloId");
	By btnBuscar = By.xpath("//tbody/tr[1]/td[2]/input[1]");
	
	By gridResultado = By.cssSelector("#tblReples");
	
	
	//CARGANDO
	By modalCargando = By.className("blockUI");

	public PomAltasXFormatoNexxo(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;

	}

	/**
	 * Inicia sesion en sembrado mediante el portal de aplicaciones
	 * y cicla el login hasta que sea exitoso, si al tercer intento
	 * no se logra entrar, la pagina se refresca
	 * @param user
	 * @param ps
	 */
	public void login (String user, String ps) {
		
		try {
			Boolean bandera = false;
			Integer cont = 0;
			u.getDriver().manage().window().maximize();

			
			if(u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 20, 2);
				u.click(linkContinue);
			}
			
			
			u.waitWebElement(locationUserInput, 15, 2);
			testCase.addScreenShotCurrentStep("");

			do {
				cont += 1;
				if (cont < 4) {
					wait.until(ExpectedConditions.elementToBeClickable(locationLoginButton));
					u.highLight(locationUserInput);
					clearInput(locationUserInput);
					u.sendKeys(locationUserInput, user);
					u.highLight(locationPassInput);
					clearInput(locationPassInput);
					u.sendKeys(locationPassInput, ps);
					u.highLight(locationLoginButton);
					u.click(locationLoginButton);
					try {
						wait.until(ExpectedConditions.elementToBeClickable(locationMenuInicio));
						bandera = true;
					} catch (Exception e) {
						bandera = false;
					}
				} else {
					u.getDriver().navigate().refresh();
					cont = 0;
				}
			} while (!bandera);
			
			testCase.addBoldTextEvidenceCurrentStep("Seleccionar en Cadena de Suministro << Replenishment >>");
			u.highLight(btnReplenishment);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.click(btnReplenishment);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo iniciar sesion");
		}
		
	}
	
	public void selectOption(String op, By selectElement) {
		
		Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
		dropdown.selectByVisibleText(op);
	}
	

	public void clearInput(By inputBy) {
		try {
			WebElement toClear = u.getDriver().findElement(inputBy);
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			System.out.println("Fallo al limpiar el campo: " + e);
		}
	}
	
	/**
	 * Revisa cada 2 segundos si el modal de Cargando sigue estando visible. Es una espera dinamica, en cuanto se quite el modal de Cargando, permite avanzar.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void cargandoMsg() {
		try {
			u.hardWait(1);
			while (u.getDriver().findElement(modalCargando).isDisplayed()) {
				u.hardWait(2);				
			}
		} catch (Exception e) {
			System.out.println("-> " + e);
		}
	}
	 public  void cambiarVentana(int pantalla) {
	        try {
	            ArrayList<String> tabs = new ArrayList<String> (u.getDriver().getWindowHandles());
	            u.getDriver().switchTo().window(tabs.get(pantalla));
	        } catch (Exception e) {
	            
	        }
	    }
	 
	 public void buscarArticulo(String articulo){
		u.waitWebElement(menuRepleReplenishment, 60, 60);
     	u.highLight(menuRepleReplenishment);
     	u.hardWait(2);
     	u.click(menuRepleReplenishment);
     	
     	u.waitWebElement(subMenuRepleValidacionReple, 60, 60);
     	u.highLight(subMenuRepleValidacionReple);
     	u.hardWait(2);
     	u.click(subMenuRepleValidacionReple);
     	
     	u.waitWebElement(txtArticulo, 60, 60);
     	u.highLight(txtArticulo);
     	u.hardWait(2);
     	u.click(txtArticulo);
     	u.sendKeys(txtArticulo, articulo);

     	
     	u.click(btnBuscar);
     	cargandoMsg();
     	u.hardWait(1);
     	testCase.addScreenShotCurrentStep("");
     	u.hardWait(5);
	 }
	
}
