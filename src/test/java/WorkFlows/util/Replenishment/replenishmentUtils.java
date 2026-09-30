package WorkFlows.util.Replenishment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import WorkFlows.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class replenishmentUtils {
	//Variables
	String globalUrlRepleOXXO = GlobalVariables.urlRepleOXXO;
	String globalUserRedOXXO = GlobalVariables.userRedRepleOXXO;
	String globalPassRedOXXO = GlobalVariables.passRedRepleOXXO;
	//Establece la opción a seleccionar de CEDIS, PLAZAS y GRID
	private int numCedis = 2;
	private int numPlaza = 0;
	private int numGrid = 4;

	//Utilerias
	private SeleniumUtil u;
	private TestCase testCase;
	private WebDriverWait wait;
	
	//Web elements conexión no segura
	By btnAvanzado = By.id("details-button");
	By urlContinuar = By.id("proceed-link");
	
	//Web elements Replenishment Login
	By userReple = By.id("login:userName");
	By passReple = By.id("login:userPass");
	By loginReple = By.id("login:loginButton");
	
	//Web elements Replenishment
	By linkReference = By.xpath("//a[contains(text(),'Portal ILM')]");
	By linkReple = By.xpath("//a[contains(text(),'Replenishment')]");
	
	//Web elements menu desplegable "Validacion Replenishment"
	By desplegableReple = By.id("e0_3i");
	By validacionReple = By.id("e0_4o");
	
	//Web elements pantalla "Validacion Replenishment"
	By optionCedis = By.id("cmbCedisId");
	By optionPlazas = By.id("cmbPlazaId");
	By optionGrid = By.xpath("//input[@type=\"checkbox\"]");
	By btnBuscar = By.xpath("//input[@value=\"Buscar\"]");
	By btnCambio = By.xpath("//input[@value=\"Cambio\"]");
	By btnTerminarValidacion = By.xpath("//input[@value=\"Terminar Validación\"]");
	
	//Web elements ventana modificar
	By inputMaxStock = By.xpath("//input[@name=\"idTxtMaxStock\"]");
	By inputDemoStock = By.xpath("//input[@name=\"idTxtDemoStock\"]");
	By btnModificar = By.xpath("//input[@value=\"Modificar\"]");
	
	//Constructor -> Recibe el SeleniumUtil utilizado en el test
	public replenishmentUtils(SeleniumUtil u, TestCase testCase ) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	/*----Aplicaciones FT_WF_Replenishment_01_Acceso y ATC_FT_002_Validacion_Replenishment----*/
	
	//Paso 1. Acceso al portal
	public void applyAccesaPortalReplenishment() {
		accesaPortalReplenishment(globalUrlRepleOXXO, btnAvanzado, urlContinuar);
		testCase.addScreenShotCurrentStep("Ingresar a la url del portal de aplicaciones en EDGE");
	}
	
	//Paso 2. Login Replenishment
	public void applyCredentialsReple() {
		credentialsReple(userReple, passReple, globalUserRedOXXO, globalPassRedOXXO);
		testCase.addScreenShotCurrentStep("Ingresar el usuario y contraseña");
		u.unhighLight(userReple);
		u.unhighLight(passReple);
	}
	
	//Paso 3. Dar click en ingresar -- Checar la llamada de vuelta a paso 2
	public void applyValidationLogin() {
		u.highLight(loginReple);
		testCase.addScreenShotCurrentStep("Dar Click en Ingresar");
		validationLogin(loginReple, linkReple, userReple, passReple, globalUserRedOXXO, globalPassRedOXXO);
	}
	
	//Paso 4. Seleccionar el LINK Replenishment
	public void applySelectLink() {
		scrollToElement(linkReple);
		u.highLight(linkReple);
		testCase.addScreenShotCurrentStep("Seleccionar el LINK Replenishment");
		clickElement("Replenishment", linkReple);
	}
	
	//Paso 5. Seleccionar la opción Validación Replenishment
	public void applySelectValidationReple() {
		selectValidationReple("Replenishment", desplegableReple, validacionReple);
		testCase.addScreenShotCurrentStep("Seleccionar la opción Validación Replenishment");
		u.click(validacionReple);
		u.hardWait(2);
	}
	/*------------------------ATC_FT_002_Validacion_Replenishment------------------------*/
	
	//Paso 6. Seleccionar un CEDIS
	public void applySelectCEDIS(){
		u.highLight(optionCedis);
		selectElement(optionCedis, numCedis);
		testCase.addScreenShotCurrentStep("Seleccionar uno o varios CEDIS");
		u.unhighLight(optionCedis);
	}
	
	//Paso 7. Seleccionar una plazas
	public void applySelectSquare(){
		u.highLight(optionPlazas);
		selectElement(optionPlazas, numPlaza);
		testCase.addScreenShotCurrentStep("Seleccionar una o varias plazas");
		u.unhighLight(optionPlazas);
	}
	
	//Paso 8. Click al campo buscar
	public void applyClickSearchButton(){
		u.highLight(btnBuscar);
		testCase.addScreenShotCurrentStep("Dar Click al campo buscar");
		u.click(btnBuscar);
		u.unhighLight(btnBuscar);
	}
	
	//Paso 9. Seleccionar un registro en el Grid
	public void applySelectRecordGrid(){
		selectCheckbox(optionGrid, numGrid);
		u.highLight(optionGrid);
		testCase.addScreenShotCurrentStep("Seleccionar un registro en el Grid");
		u.unhighLight(optionGrid);
	}
	
	//Paso 10. Click al boton Cambio
	public void applyClickChangeButton(){
		scrollToElement(btnCambio);
		u.highLight(btnCambio);
		testCase.addScreenShotCurrentStep("Dar Click al boton Cambio");
		u.click(btnCambio);
		u.unhighLight(btnCambio);
	}
	
	//Paso 11. Capturar info de max stock y demo stock
	public void applyMaxDemoStock(){
		switchToAnotherWindow(2,"cambiar", false);
		captureStock(inputMaxStock, inputDemoStock, "10", "8");
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("Capturar info de max stock y demo stock");
		u.unhighLight(inputMaxStock);
		u.unhighLight(inputDemoStock);
	}
	
	//Paso 12. Click en modificar
	public void applyClickModify(){
		u.highLight(btnModificar);
		testCase.addScreenShotCurrentStep("Click en modificar");
		u.click(btnModificar);
		u.unhighLight(btnModificar);
		acceptAlert();
	}
	
	//Paso 13. Click en boton terminar validacion
	public void applyClickFinish(){
		switchToAnotherWindow(1,"cambiar", false);
		scrollToElement(btnTerminarValidacion);
		u.highLight(btnTerminarValidacion);
		testCase.addScreenShotCurrentStep("Click en boton terminar validacion");
		u.click(btnTerminarValidacion);
		u.unhighLight(btnTerminarValidacion);
		acceptAlert();
	}
	
	//Paso 14. Validar la informacion de actualizacion en la tabla
	public void applyValidateInformation(){
		statusBarcode(optionGrid, numGrid);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("Validar la informacion de actualizacion en la tabla");
	}
	
	/*----------------------------------Metodos----------------------------------*/
	
	/**
	 * @desc Ingresa al portal de aplicaciones saltanto las advertencias 
	 * de seguridad y maximiza la pantalla
	 * 
	 * @param url -> Enlace a la url del portal
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	
	public void accesaPortalReplenishment(String url, By startElement, By destinationElement) {
		try {
			u.getDriver().manage().deleteAllCookies();
			u.getDriver().manage().window().maximize();
			u.get(url);
			wait.until(ExpectedConditions.elementToBeClickable(startElement));
			clickElement("Avanzado", startElement);
			wait.until(ExpectedConditions.elementToBeClickable(destinationElement));
			u.highLight(destinationElement);
			u.mouseOverAndClick(destinationElement);
		} catch (Exception e) {
			System.out.println(" -> " + e);
		}
	}
	
	/**
	 * @desc Ingresar el usuario, contraseña y los resalta.
	 * 
	 * @param user -> usuario
	 * @param pass -> contraseña usuario
	 * @param gUser -> usuario con perfil de red dentro de las variables globales
	 * @param gPass -> contraseña usuario con perfil de red dentro de las variables globales
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	
	public void credentialsReple(By user, By pass, String gUser, String gPass) {
		Boolean aux;
		
		do {
			try {
				aux = false;
				wait.until(ExpectedConditions.elementToBeClickable(userReple));
				u.clear(user);
				u.highLight(user);
				u.sendKeys(user, gUser);
				
				u.clear(pass);
				u.highLight(pass);
				u.sendKeys(pass, gPass);
			} catch (Exception e) {
				aux = true;
				System.out.println(" -> " + e);
				throw new AssertionError("Error al ingresar las credenciales");
			}
		} while (aux);
	}
	
	/**
	 * @desc Da click en iniciar sesión, si el sistema no permite el acceso
     * intenta dos veces y luego recarga la pagina y vuelve a intentar el inicio de sesion 
	 * 
	 * @param startElement       -> elemento que tenemos en la vista actual
	 * @param destinationElement -> elemento que espero obtener en la siguiente vista
	 * @param user -> usuario
	 * @param pass -> contraseña usuario
	 * @param gUser -> usuario con perfil de red dentro de las variables globales
	 * @param gPass -> contraseña usuario con perfil de red dentro de las variables globales
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	
	public void validationLogin(By startElement, By destinationElement, By user, By pass, String gUser, String gPass) {
		Boolean bandera = false;
		Integer cont = 0;
		
		do {
			cont += 1;
			if(cont == 1) {
				clickElement("Iniciar sesión", startElement);
				try {
					u.getDriver().findElement(destinationElement);
					bandera = true;
				}catch (Exception e) {
					bandera = false;
				}
			}
			else if (cont > 1 && cont < 4) {
				credentialsReple(user, pass, gUser, gPass);
				clickElement("Iniciar sesión", startElement);
				try {
					u.getDriver().findElement(destinationElement);
					bandera = true;
				} catch (Exception e) {
					bandera = false;
				}
			}else {
				u.getDriver().navigate().refresh();
				credentialsReple(user, pass, gUser, gPass);
				cont = 0;
			}
		}while(!bandera);
	}
	
	/**
	 * << Metodo reutilizado >>
	 * @desc Espera a que el elemento se muestre, lo resalta y le da click.
	 * 
	 * @param opcion    -> Nombre del boton
	 * @param elementBy -> By Element
	 * 
	 * @author Esmeralda Garcia Torres 
	 */
	
	public void clickElement(String opcion, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			u.unhighLight(elementBy);
			u.click(elementBy);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en: " + opcion);
		}
	}
	
	/**
	 * << Metodo reutilizado >>
	 * @desc Localiza el elemento, obtiene sus coordenadas y hace scroll hacia hasta que se muestre en pantalla.
	 * 
	 * @param opcion    -> Nombre del elemento
	 * @param elementBy -> By Element
	 * 
	 * @author Esmeralda Garcia Torres 
	 */
	
	public void scrollToElement(By elementBy) {
        try {
        	wait.until(ExpectedConditions.elementToBeClickable(elementBy));
            WebElement element = u.getDriver().findElement(elementBy);
            Coordinates coordinate = ((Locatable) element).getCoordinates();
            coordinate.onPage();
            coordinate.inViewPort();
        } catch (Exception e) {
            try {
                WebElement m = u.getDriver().findElement(elementBy);
                ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", m);
            } catch (Exception e2) {
                System.out.println(" -> No se pudo scrollear: " + e);
            }
        }
        
    }
	
	/**
	 * @desc Se posiciona sobre un menu desplegable y lo despliega.
	 * 
	 * @param opcion          -> Texto visible 
	 * @param elementDropdown -> Elemento del menu
	 * @param elementBy       -> Elemento del menu despegable
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	
	public void selectValidationReple(String opcion, By elementDropdown, By elementBy) {
		try {
			//Se posiciona sobre en un elemento del menu desplegable
			u.mouseOver(elementDropdown);
			u.highLight(elementDropdown);
			u.unhighLight(elementDropdown);
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			//Se posiciona sobre uno de los elementos desplegaos del menu y da click
			u.mouseOver(elementBy);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al seleccionar: " + opcion);
		}
    }
	
	/**
	 * @desc Selecciona un elemento de una lista
	 * 
	 * @param elementBy -> Elemento a seleccionar
	 * @param selection -> Opción a seleccionar
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	public void selectElement(By elementBy, Integer selection){
		try {
			if(selection < 22) {
				wait.until(ExpectedConditions.elementToBeClickable(elementBy));
				createSelect(elementBy, selection);
			}else {
				System.out.println("El elemento ingresado no existe");
				u.getDriver().quit();
			}
		}catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al seleccionar el elemento");
		}
		
		
	}
	
	/**
	 * @desc Crea la seleccion de un elemento por index
	 * 
	 * @param elementeBy -> Parametro a la lista de elementos
	 * @param selection -> Selecciona un elemento del index
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	public void createSelect(By elementBy, Integer selection){
		try {
			Select option = new Select(u.getDriver().findElement(elementBy));
			u.highLight(elementBy);
			option.selectByIndex(selection);
			u.unhighLight(elementBy);
		}catch (Exception e) {
			System.out.println("No se pudo seleccionar ->" + e);
		}
	}
	
	/**
	 * @desc Selecciona un elemento del checkbox
	 * 
	 * @param elementBy -> Parametro a la lista de elementos
	 * @param selection -> Hace click a un checkbox del grid
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	public void selectCheckbox(By elementBy, Integer selection){
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.scrollToElement(By.xpath("(//input[@type=\"checkbox\"])["+selection+"]"));
			u.click(By.xpath("(//input[@type=\"checkbox\"])["+selection+"]"));
		}catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo seleccionar el checkbox");
		}
	}
	
	/**
	 * << Metodo reutilizado >>
	 * @desc Se cambia de ventana o cierra una.
	 * 
	 * @param numWindow -> Numero de ventana
	 * @param opcion    -> cambiar o cerrar
	 * @param maximizar -> true al cambiar, false al cerrar
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void switchToAnotherWindow(int numWindow, String opcion, Boolean maximizar) {
		try {
			List<String> windowlist = null;
			Set<String> windows = u.getDriver().getWindowHandles();
			windowlist = new ArrayList<String>(windows);
			String currentWindow = u.getDriver().getWindowHandle();
			System.out.println("VENTANAS - " + windowlist.size());
			System.out.println("->" + windowlist);
			System.out.println("-> ACTUAL: " + currentWindow);

			if (!currentWindow.equalsIgnoreCase(windowlist.get(numWindow - 1))) {
				u.getDriver().switchTo().window(windowlist.get(numWindow - 1));
				System.out.println("-> CAMBIAR: " + u.getDriver().getWindowHandle());
			}

			if (maximizar)
				u.getDriver().manage().window().maximize();

			if (opcion.equalsIgnoreCase("cerrar")) {
				System.out.println("-> CERRAR: " + u.getDriver().getWindowHandle());
				u.getDriver().close();
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al " + opcion + " la ventana");
		}
	}
	
	/**
	 * @desc Selecciona un elemento del checkbox
	 * 
	 * @param elementBy -> Parametro a la lista de elementos
	 * @param selection -> Selecciona un elemento del grid
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	public void captureStock(By maxStock, By demoStock, String max, String demo){
		try {
			wait.until(ExpectedConditions.elementToBeClickable(maxStock));
			u.clear(maxStock);
			u.highLight(maxStock);
			u.sendKeys(maxStock, max);
			
			u.clear(demoStock);
			u.highLight(demoStock);
			u.sendKeys(demoStock, demo);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al ingresar las credenciales");
		}
	}
	
	/**
	 * @desc Selecciona un elemento del checkbox
	 * 
	 * @param elementBy -> Parametro a la lista de elementos
	 * @param selection -> Selecciona un elemento del grid
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	public void acceptAlert(){
		try {
			Alert popup = u.getDriver().switchTo().alert();
			popup.accept();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al ingresar las credenciales");
		}
	}
	
	/**
	 * @desc Selecciona un elemento del checkbox
	 * 
	 * @param elementBy -> Parametro a la lista de elementos
	 * @param selection -> Selecciona un elemento del grid
	 * 
	 * @author Ian Alexander Ruiz González
	 */
	public void statusBarcode(By elementBy, Integer selection){
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			WebElement status = u.getDriver().findElement(By.tagName("title"));
			String title = status.getText();
			
			if(title == "TERMINADO") {
				System.out.println("El estatus del producto se cambio con exito");
			}
		}catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("El estatus del producto no cambio");
		}
	}
}
