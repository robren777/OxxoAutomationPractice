package WEB.util.PortalEquipamiento;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PomPortalEquipamiento {
	
	SeleniumUtil u;
	private WebDriverWait wait;
	TestCase testCase;
	
	String url = "https://fcportalqa.femcom.net:8443/AuthFemcoProx/access-control";
	
	//Your connection isn't private//
	
	private By btnAdvance = By.id("details-button");
	private By linkContinue = By.id("proceed-link");

	
	/*
	 * Localizadores Login
	 ******************************************************************************************/
	private By locationUserInput = By.id("email");
	private By locationPassInput = By.id("password");
	private By locationLoginButton = By.id("login");
	//private By loadingAnimation = By.xpath("//div[@class='loading-animation']");
	
	/*
	 * Localizadores Menu
	 ******************************************************************************************/
	private By menuPortalEquipamiento = By.xpath("//img[@title='Navigation']");
	private By menuArticulosEspeciales = By.xpath("//span[normalize-space()='Articulos Especiales']");
	private By menuConfiguraAprobadores = By.xpath("//a[normalize-space()='Configuraci�n de Aprobadores']");
	
	private By pestanaCuartosFrios = By.xpath("//button[@id='nav-profile-tab']");
	
	private By planeacionCuartosFrios = By.xpath("//tbody/tr[1]/td[1]/button[1]/i[1]");
	
	/*
	 * Localizadores Portal de Aplicaciones
	 ******************************************************************************************/
	private By locationMenuInicio = By.linkText("Inicio");
	By btnPortalEquipamiento2 = By.xpath("//a[contains(text(),'Portal de equipamiento v2')]");
	
	/*
	 * Localizadores Articulos especiales  --> Configuracion de aprobadores --> Pestana Cuartos Frios --> Planeacion cuartos frios
	 ******************************************************************************************/	
	By txtNombreCF = By.xpath("//input[@type='text']");
	By txtCorreoCF = By.xpath("//input[@type='email']");
	By btnRegionesCF = By.xpath("//button[normalize-space()='Regiones']");
	
	By modalAprobRegiones = By.xpath("//div[@class='modal-content']");
	
	By tablaRegiones = By.cssSelector(".table.table-bordered");
	
	By btnAceptarModal = By.xpath("//button[normalize-space()='Guardar']");
	By btnCancelarModal = By.xpath("//button[normalize-space()='Cancelar']");
	
	By msjModalApro = By.xpath("//div[@role='dialog']");
	
	By msjBtnOkModal = By.xpath("//button[normalize-space()='OK']");
	
	By tablaAproRegion = By.xpath("//tr[@class='oxxotable-tr aprobadoresTable']//td//table");
	
	By dialogEliminar = By.xpath("//div[@role='dialog']");
	
	By dialogAsignado = By.xpath("//div[@role='dialog']/div[contains(text(),'ya han sido asignadas a otro aprobador')]");
	
	By dialogAprobado = By.xpath("//div[@role='dialog']/div[contains(text(),'Aprobador guardado correctamente')]");
	
	By btndialogContinuar = By.xpath("//button[normalize-space()='Continuar']");
		
	/*
	 * Localizador CARGANDO
	 ******************************************************************************************/
	By modalCargando = By.xpath("//div[@class='loading-animation']");
	
	By modalCargando2 = By.xpath("//div[@role='status']");

	/*
	 * Constructor
	 ******************************************************************************************/
	
	public PomPortalEquipamiento(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;

	}
	
	
	/*
	 * METODOS 
	 ******************************************************************************************/
	/**
	 * Método para obtener url de Portal de aplicaciones
	 * @return url Regresa la url del portal
	 * @author María Dueñas
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Inicia sesion en el portal de aplicaciones por AC
	 * y cicla el login hasta que sea exitoso, si al tercer intento
	 * no se logra entrar, la pagina se refresca
	 * @param user 
	 * @param ps
	 * @date 04/04/2025
	 */
	public void login (String user, String ps) {
			
		Boolean bandera = false;
    	Integer cont = 0;
    	    	
    	u.getDriver().manage().window().maximize();
    	
    	proceedToPortal();
    	
		u.waitWebElement(locationUserInput, 15, 2);
		testCase.addScreenShotCurrentStep("");
			
			//para intentar 4 veces iniciar sesion	
			do {
				cont += 1;
				if (cont < 4) {
					//wait.until(ExpectedConditions.elementToBeClickable(locationLoginButton));
					userPassIniciar(user, ps);
					cargandoMsg2();
					try {
						wait.until(ExpectedConditions.elementToBeClickable(locationMenuInicio));
						u.getDriver().findElement(locationMenuInicio);
						bandera = true;
						cargandoMsg2();
					} catch (Exception e) {
						bandera = false;
					}
				} else {
					u.getDriver().navigate().refresh();
					cont = 0;
				}
			} while (!bandera);
			u.hardWait(3);
	    	testCase.addScreenShotCurrentStep("fin");
		
	}
	
	/**
	 * Metodo para avanzar en la pagina de portal de aplicaciones
	 * @author Elberth Ruiz
	 * @date 04/04/2025
	 */
	public void proceedToPortal() {
    	
		if(u.getDriver().findElements(btnAdvance).size() != 0) {
			u.click(btnAdvance);
			u.waitWebElement(linkContinue, 20, 2);
			u.click(linkContinue);
		}
    	
    }
	
	/**
	 * Metodo para iniciar sesion en el portal de aplicaciones
	 * @author Elberth Ruiz
	 * @param user, ps
	 * @date 04/04/2025
	 */
	public void userPassIniciar(String user, String ps) {
		u.highLight(locationUserInput);
		clearInput(locationUserInput);
		u.sendKeys(locationUserInput, user);
		
		u.highLight(locationPassInput);
		clearInput(locationPassInput);
		u.sendKeys(locationPassInput, ps);
		
		u.highLight(locationLoginButton);
		testCase.addScreenShotCurrentStep("");
		u.click(locationLoginButton);	
	}
	/**
	 * Ingresar al aplicativo "Portal Equipamiento V2" desde el listado de aplicaciones
	 * @author Elberth Ruiz
	 * @return 
	 * @date 04/Abril/2024
	 * 
	 * */
	public boolean appPorEquipamientoV2 () {
		boolean estado = false;
		
		cargandoMsg2(); //para esperar que se quite el modal de carga
		try {
			u.highLight(btnPortalEquipamiento2);
			
			u.waitWebElement(btnPortalEquipamiento2, 5, 800);
		
			testCase.addScreenShotCurrentStep("");
			
			u.hardWait(2);
			u.click(btnPortalEquipamiento2);
	
			cargandoMsg();//para esperar que se quite el "CARGANDO"
					
			testCase.addScreenShotCurrentStep("");
			
			estado = true;
			
		}catch(Exception e){
			System.out.println("Fallo el metodo: " + e);
			estado = false;
		}
		return estado;
	}
	
	/**
	 * Navegar por el Menu de "Portal Equipamiento V2" hasta llegar a la opcion de Cuartos Frios 
	 * @author Elberth Ruiz
	 * @date 04/Abril/2024
	 * 
	 * */
	public void menuApp() {
		u.highLight(menuPortalEquipamiento);
		testCase.addScreenShotCurrentStep("");
		u.click(menuPortalEquipamiento);
		
		//u.hardWait(1);
		u.waitWebElement(menuArticulosEspeciales, 5, 800);
		
		u.highLight(menuArticulosEspeciales);
		testCase.addScreenShotCurrentStep("");	
		u.click(menuArticulosEspeciales);
		
		u.hardWait(1);
		
		u.scrollToElement(menuConfiguraAprobadores);
		u.highLight(menuConfiguraAprobadores);
		testCase.addScreenShotCurrentStep("");
		
		//u.hardWait(1);
		u.waitWebElement(menuConfiguraAprobadores, 5, 800);
		
		u.click(menuConfiguraAprobadores);
		
		//u.hardWait(1);
		u.waitWebElement(pestanaCuartosFrios, 5, 800);
		
		wait.until(ExpectedConditions.elementToBeClickable(pestanaCuartosFrios));
		u.highLight(pestanaCuartosFrios);
		testCase.addBoldTextEvidenceCurrentStep("");
		u.click(pestanaCuartosFrios);
		
	}
	
	/**
	 * Ingresar a la opcion de Cuartos Frios 
	 * @author Elberth Ruiz
	 * @return 
	 * @date 04/Abril/2024
	 * 
	 * */
	public boolean visorAprobadores() {
		boolean estado = false;
		
		try {
		u.waitWebElement(planeacionCuartosFrios, 5, 800);
		u.highLight(planeacionCuartosFrios);
		testCase.addScreenShotCurrentStep("");
		u.click(planeacionCuartosFrios);
		
		cargandoMsg();//para esperar que se quite el "CARGANDO"
		
		testCase.addScreenShotCurrentStep("");
		u.unhighLight(planeacionCuartosFrios);
		u.unhighLight(pestanaCuartosFrios);
		estado = true;
		}catch(Exception e) {
			System.out.println("Fallo el metodo: " + e);
			estado = false;
		}
		return estado;
	}
	
	/**
	 * En la opcion de cuartos frios se ingresa el campo user en el campo usuario y se le aplica una accion (ya sea clic al boton Regiones o clic al boton Eliminar) 
	 * @author Elberth Ruiz
	 * @param user
	 * @date 04/Abril/2024
	 * 
	 * */
	public void planeacionCuartosFrios(String user) {
		//u.hardWait(1);
		cargandoMsg();//para esperar que se quite el "CARGANDO"
		clearInput(txtNombreCF);
		u.highLight(txtNombreCF);
		u.sendKeys(txtNombreCF, user);
		u.click(txtCorreoCF);
		u.highLight(txtCorreoCF);

		cargandoMsg();//para esperar que se quite el "CARGANDO"
			
		u.unhighLight(txtNombreCF);
		u.unhighLight(txtCorreoCF);		

	}
	
	/**
	 * Metodo del boton para realizar cierta accion (ya sea clic al boton Regiones o clic al boton Eliminar) 
	 * @author Elberth Ruiz
	 * @param btnAccion
	 * @return 
	 * @date 04/Abril/2024
	 * 
	 * */
	public boolean clicBtnRegElim(String btnAccion) {
		
		if (btnAccion == null || btnAccion.isEmpty()) {
			System.out.println("El campo btnRegionesEliminar en el dataprovider esta vacio");
            return false; // El texto no puede ser nulo ni vac�o
        }
		
		By btnRegionesCF = By.xpath("//button[normalize-space()='" + btnAccion + "']");
		u.highLight(btnRegionesCF);
		testCase.addScreenShotCurrentStep("");
		u.click(btnRegionesCF);
		u.unhighLight(btnRegionesCF);
		
		if(u.getDriver().findElements(dialogEliminar).size() != 0) {
			u.highLight(btndialogContinuar);
			testCase.addScreenShotCurrentStep("");
			u.click(btndialogContinuar);
			
		}
		cargandoMsg();//para esperar que se quite el "CARGANDO"
		testCase.addScreenShotCurrentStep("");
		return true;
	}
	/**
	 * Metodo para buscar un elemento, y que de ese elemento buscar la opcion dentro de ella y seleccionarla 
	 * @author Elberth Ruiz
	 * @param op, selectElement
	 * @date 04/Abril/2024
	 * 
	 * */
	public void selectOption(String op, By selectElement) {
		
		Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
		dropdown.selectByVisibleText(op);
	}
	
	/**
	 * Metodo para limpar un campo 
	 * @author Elberth Ruiz
	 * @param inputBy
	 * @date 04/Abril/2024
	 * 
	 * */
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
	 * @date 04/Abril/2024
	 */
	public void cargandoMsg() {
		
		u.hardWait(5);
		try {
			u.hardWait(1);
			while (u.getDriver().findElement(modalCargando).isDisplayed()) {
				//u.hardWait(2);
				u.waitWebElement(modalCargando, 5, 800);
			}
		} catch (Exception e) {
			System.out.println("-> " + e);
		}
	}
	/**
	 * Revisa cada 2 segundos si el modal de Cargando sigue estando visible. Es una espera dinamica, en cuanto se quite el modal de Cargando, permite avanzar.
	 * 
	 * @author Esmeralda Garcia Torres
	 * @date 04/Abril/2024
	 */
	public void cargandoMsg2() {
		u.hardWait(5);
		try {
			u.hardWait(1);
			while (u.getDriver().findElement(modalCargando2).isDisplayed()) {
				//u.hardWait(2);
				u.waitWebElement(modalCargando2, 5, 800);
			}
		} catch (Exception e) {
			System.out.println("-> " + e);
		}
	}
	 
    /**
	 * Realiza la seleccion  de un dato dentro de una tabla. 
	 * 
	 * @author Martin Vazquez, Elberth Ruiz
	 * @param checkPlaza
     * @return 
	 * @date 01/Abril/2024
	 * 
	 * */
    public boolean seleccionarRegion(String checkPlaza) {
    	
    cargandoMsg();
    
	if (checkPlaza == null || checkPlaza.isEmpty()) {
		System.out.println("El campo regiones en el dataprovider esta vacio");
		return false;
	}
    	String[] listaOpciones = checkPlaza.split(",");
		for (int i = 0; i < listaOpciones.length; i++) {
			checkPlaza = listaOpciones[i].trim();
			
			By CheckVariable = By.xpath("//table/tbody/tr/td[contains(text(),'"+ checkPlaza + "')]/following-sibling::td[2]/input[@type='checkbox']");
			
			scrollToElement(CheckVariable);
			
			u.click(CheckVariable);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			
		}
	return true;
	
    }
    /**
	 * Recorre las regiones a seleccionar. 
	 * 
	 * @author Martin Vazquez, Elberth Ruiz
	 * @param checkPlaza
     * @return 
	 * @date 01/Abril/2024
	 * 
	 * */   
    public boolean recorrerRegiones(String checkPlaza) {
	
    cargandoMsg();
	if (checkPlaza == null || checkPlaza.isEmpty()) {
		System.out.println("El campo regiones en el dataprovider esta vacio");
		return false;
	}
    String[] listaOpciones = checkPlaza.split(",");
		for (int i = 0; i < listaOpciones.length; i++) {
			checkPlaza = listaOpciones[i].trim();
			
			By CheckVariable = By.xpath("//table/tbody/tr/td[contains(text(),'"+ checkPlaza + "')]/following-sibling::td[2]/input[@type='checkbox']");
			
			scrollToElement(CheckVariable);
			
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			
		}
	return true;
    }
    
    /**
	 * En el Modal donde se muestran las regiones da clic en GUARDAR o CANCELAR. 
	 * 
	 * @author Martin Vazquez, Elberth Ruiz
	 * @param opc
     * @return 
	 * @date 01/Abril/2024
	 * 
	 * */
    public boolean accionM(String opc) {
    	if (opc == null || opc.isEmpty()) {
    		System.out.println("El campo accionModal en el dataprovider esta vacio");
    		return false;
    	}
    	
    	By opcionM = By.xpath("//button[normalize-space()='"+ opc +"']");
    	scrollToElement(opcionM);
    	u.highLight(opcionM);
    	testCase.addScreenShotCurrentStep("");
    	u.click(opcionM);
    	testCase.addScreenShotCurrentStep("");
    	cargandoMsg();//para esperar que se quite el "CARGANDO"
    	if(u.getDriver().findElements(dialogAsignado).size() != 0) {
			
			testCase.addScreenShotCurrentStep("");
			u.click(msjBtnOkModal);
			
		}else if (u.getDriver().findElements(dialogAprobado).size() != 0) {
			testCase.addScreenShotCurrentStep("");
			u.click(msjBtnOkModal);
	    	u.getDriver().findElement(tablaAproRegion).isDisplayed();
	    	u.hardWait(1);
	    	testCase.addScreenShotCurrentStep("");
		}
    	u.hardWait(1);
    	testCase.addScreenShotCurrentStep("");
    	return true;
	}
	 
    /**
	 * Modal para imprimir en consola  
	 * 
	 * @author 
	 * @param text
	 */
	 public void log(String text) {
			String code = "+> ";
			System.out.println(code + text);
			u.waitWebElement(modalCargando2, 5, 800);
	 }
	 
    /**
	 * Modal para hacer la funcion de Scroll en pantalla e ir hasta el elemento 
	 * 
	 * @author 
	 * @param ByElement
	 */
	 public void scrollToElement(By ByElement) {
			try {
				WebElement element = u.getDriver().findElement(ByElement);
				Coordinates coordinate = ((Locatable) element).getCoordinates();
				coordinate.onPage();
				coordinate.inViewPort();
			} catch (Exception e) {
				try {
					WebElement m = u.getDriver().findElement(ByElement);
					((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", m);
				} catch (Exception e2) {
					log("No se pudo scrollear");
				}
			}

		}
	 
	 /**
	  * MÃƒÂ©todo para validar la informacion megocio y perfil que se muestran en el sistema, 
	  * coincidan con el data provider
	  * @return bandera Regresa un true o false si la informacion es correcta
	  * @author Elberth Ruiz
	  * @date 11/Abril/2024
	  */
	 public Boolean verificarCorreo(){
         
         Boolean bandera = false;        
         if(u.getDriver().findElements(btnRegionesCF).size() != 0) {
        	 bandera = true;
         }
         
         return bandera;

    }
	 
	
}
