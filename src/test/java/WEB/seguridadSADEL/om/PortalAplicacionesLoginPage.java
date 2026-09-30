package WEB.seguridadSADEL.om;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utils.selenium.SeleniumUtil;
import modelo.TestCase;

public class PortalAplicacionesLoginPage {
    private final SeleniumUtil u;
    private final TestCase testCase;

    private final By usuarioInput = By.id("login:userName");
    private final By passwordInput = By.id("login:userPass");
    private final By loginButton = By.id("login:loginButton");
    //Sitio no seguro
    private final By opcionSadelNominaComisionista = By.xpath("//a[contains(text(),'SADEL NOMINA COMISIONISTA') or contains(text(),'SADEL')]");
    private final By tituloPaginaSadel = By.xpath("//img[@name='Image13']");

    public PortalAplicacionesLoginPage(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
    }
    
    /**
     * Se crea el metodo para verificar que abra la pagina del navegador y le toma una screenshot
     * @author Mario A. De León Rdz.
     * @date 04/mayo/2026
     * @return true si se accede a la URL de manera correcta, false si no fue posible.
     */
    public boolean abrirPagina(String url) {
        boolean validacion = false;
        try {
        	u.getDriver().manage().deleteAllCookies();
            u.get(url);
            testCase.addScreenShotCurrentStep(u, "pagina_ingresada");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }
    
    /**
     * Se crea el metodo para verificar que abra la pagina del navegador y le toma una screenshot
     * @author Mario A. De León Rdz.
     * @date 04/mayo/2026
     * @return true si se accede a la URL de manera correcta, false si no fue posible.
     */
    public boolean abrirPaginaIE(String url) {
        boolean validacion = false;
        try {
        	u.getDriver().manage().deleteAllCookies();
            u.get(url);
            
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
    		js.executeScript("document.getElementById('overridelink').click();");
    		
            testCase.addScreenShotCurrentStep(u, "pagina_ingresada");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }
	/**
	 * Metodo para activar la tecla Flecha Abajo segun el numero de iteraciones que se indiquen, despues de terminar las iteraciones se activa la tecla enter
	 * @iteraciones numero de veces que se va a presionar la tecla
	 * @author Mario A. De Leon Rdz.
	 */
	public void robotKeyTab(int iteraciones) {
		System.out.println("inicia robot");
		Robot robot;
		try {
			robot = new Robot();
			for(int i = iteraciones; i > 0; i--) {
				robot.keyPress(KeyEvent.VK_TAB);
				System.out.println("Press down Numero: "+ i);
				robot.keyRelease(KeyEvent.VK_TAB);
				System.out.println("Release down Numero:"+ i);
			}
			
			robot.keyPress(KeyEvent.VK_ENTER);
			System.out.println("Press enter");
			robot.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("Release enter");
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
		System.out.println("termina robot");
	}
    /**
     * Metodo para ingresar usuario y contraseña en Access Control
     * @author Mario A. De León Rdz.
     * @date 04/mayo/2026
     * @return true si se ingresan las credenciales de manera correcta, false si no fue posible.
     */
    public boolean ingresarCredenciales(String username, String password) {
        boolean validacion = false;
    	try {
    		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
    		wait.until(ExpectedConditions.elementToBeClickable(usuarioInput));
    		u.sendKeys(usuarioInput, username);
    		wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
    		u.sendKeys(passwordInput, password);
    		testCase.addScreenShotCurrentStep(u, "inicio_sesion");
    		u.click(loginButton);
    		validacion = true;
    	} catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(e.getMessage());
            e.printStackTrace();
    	}
    	return validacion;
    }
    
    /**
     * Método para ingresar a la Url de nuestro Portal.
     *
     * @author Carlos Martínez Ortega
     * @return true si se logra iniciar el portal de manera correcta.
     *         false si no se pudo ingresar al portal de manera correcta
     * @date 25/Mayo/2026
     */
    public boolean Ingresaralportal(String url) {
        boolean validacion = false;
        try {
            u.getDriver().manage().window().maximize();
            u.getDriver().manage().deleteAllCookies();
            u.get(url);

            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript("document.getElementById('overridelink').click();");

            Thread.sleep(3000);

            testCase.addScreenShotCurrentStep(u, "Se ingresa correctamente al portal de aplicaciones");
            validacion = true;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt(); // ← Restaura el flag de interrupción
            testCase.addCodeEvidenceCurrentStep(
                "Hilo interrumpido al esperar en el portal. Error: " + ie.getMessage()
            );
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(
                "No se pudo ingresar a la URL del portal de aplicaciones. Error: " + e.getMessage()
            );
            e.printStackTrace();
        }
        return validacion;
    }
    /**
     * Método para ingresar usuario y contraseña.
     *
     * @author Carlos Martínez Ortega
     * @date 22/junio/2026
     * @return true si se ingresan las credenciales de manera correcta, false si no fue posible.
     */
    public boolean login(String username, String password) {
        boolean validacion = false;
        try {
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(usuarioInput));
            u.sendKeys(usuarioInput, username);
            wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
            u.sendKeys(passwordInput, password);
            testCase.addScreenShotCurrentStep(u, "Se agregan usuario y contraseña");
            u.click(loginButton);
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para validar que se muestre la opción Sadel Nómina Comisionista.
     *
     * @author Carlos Martínez Ortega
     * @date 22/junio/2026
     * @return true si se muestra la opción SADEL NÓMINA COMISIONISTA, false si no fue posible.
     */
    public boolean validarAparezcaOpcionSadelNominaComisionista() {
        boolean validacion = false;
        try {
            u.waitWebElement(opcionSadelNominaComisionista, 25, 4);
            testCase.addScreenShotCurrentStep(u, "Se muestra correctamente la pantalla principal del portal SADEL");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para validar que se ingrese de manera correcta a la pantalla de SADEL.
     *
     * @author Carlos Martínez Ortega
     * @date 22/junio/2026
     * @return true si se ingresa correctamente al sistema SADEL, false si no fue posible.
     */
    public boolean validarIngresoSadelNominaComisionista() {
        boolean validacion = false;
        try {
            u.click(opcionSadelNominaComisionista);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPaginaSadel));

            Thread.sleep(800);

            testCase.addScreenShotCurrentStep(u, "Se ingresa a sistema SADEL correctamente y la tabla está cargada");
            validacion = true;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt(); // ← Correcto: solo cuando SÍ es una interrupción
            testCase.addCodeEvidenceCurrentStep(
                "Hilo interrumpido al esperar en SADEL. Error: " + ie.getMessage()
            );
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }
}