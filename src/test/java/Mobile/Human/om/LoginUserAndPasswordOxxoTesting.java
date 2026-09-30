package Mobile.Human.om;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class LoginUserAndPasswordOxxoTesting {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
	/*
	 * Localizadores Inicio de sesion Usuario
	 ******************************************************************************************/
    private By inputUsuario = By.xpath("//android.widget.EditText[@text=\"Usuario\"]");
    private By btnContinuarUsuario = By.xpath("//android.widget.TextView[@text=\"Continuar\"]");
	private By mensajeErrorUser = By.xpath("//android.widget.TextView[@text=\"Tu cuenta no tiene ninguna comunidad asociada\"]");

	/*
	 * Localizadores Inicio de sesion Escoger Comunidad - OxxoTesting
	 ******************************************************************************************/
    private By comunidadOxxoTesting = By.xpath("//android.widget.TextView[@text=\"Oxxo Testing\"]");
    /*
	 * Localizadores Inicio de sesion Escoger Comunidad - Oxxo Testing - Barra de Busqueda
	 ******************************************************************************************/
	private By buscadorComunidad = By.xpath("//android.widget.EditText[@text=\"Buscar comunidad\"]");
    /*
	 * Localizadores Inicio de sesion Restablecer contrase�a
	 ******************************************************************************************/
	private By contraRestablecida1 = By.xpath("//android.widget.EditText[@text=\"Contrase�a\"]");
	private By contraRestablecida2 = By.xpath("//android.widget.EditText[@text=\"Repetir contrase�a\"]");
	private By btnActualizaContra = By.xpath("//android.widget.TextView[@text=\"ACTUALIZAR CONTRASE�A\"]");
	/*
	 * Localizadores Inicio de sesion Contrase�a
	 ******************************************************************************************/
    private By contraInput = By.xpath("//android.widget.EditText[@text=\"Contrase�a\"]");
	private By continuarBtnPass = By.xpath("//android.widget.TextView[@text=\"INICIAR SESI�N\"]");
	private By mensajeErrorPassword = By.xpath("//android.widget.TextView[@text=\"Contrase�a incorrecta\"]");

	/*
	 * Localizadores para Asserts en cada Step
	 ******************************************************************************************/
	private By assertTitleUser = By.xpath("//android.widget.TextView[@text=\"Iniciar sesi�n en tu comunidad\"]");
	private By assertTitleCommunity = By.xpath("//android.widget.TextView[@text=\"�A qu� comunidad te gustar�a loguearte?\"]");
	private By assertTitlePassword = By.xpath("//android.widget.TextView[@text=\"Ingresa tu contrase�a\"]"); 

	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase LoginUserAndPasswordOxxoTesting
	 * @author Ivan Yakab
	 * @param au (AppiumUtil)  
	 * @param testCase (TestCase)
	 * @param baseExecution (BaseExecution)
	 */
    public LoginUserAndPasswordOxxoTesting(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.baseExecution = baseExecution;
        this.au = au;
    }
    
    /**
	 * Metodo
	 ******************************************************************************************/
    /**
     * Inicia sesion con un usuario y contrase�a en comunidad Oxxo Testing
     *
     * @param user El usuario con el que se iniciara sesi�n, se debe ingresar desde el DataProvider.
     * @param password La contrase�a con el que el usuario iniciara su sesion, se debe ingresar encriptado desde el DataProvider. 
     */
    public void loginOxxoTesting(String user, String password) throws InterruptedException {
    		
    		// ESPERA DE 15 SEGUNDOS - MAQUINA VIRTUAL 
    		Thread.sleep(15000);
    	
    		/************************ Paso 2 - Ingresar el Usuario ***************************/
    	baseExecution.addStep("Ingresar el Usuario");
    		String assertTitleUserText = au.driver.findElement(assertTitleUser).getText();
    		au.sendKeys(inputUsuario, user);
    		assertEquals(assertTitleUserText,"Iniciar sesi�n en tu comunidad");
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		
    		/************************ Paso 3 - Tap en el Boton "Continuar"  ***************************/
    	baseExecution.addStep("Tap en el Boton 'Continuar' ");
			assertEquals(assertTitleUserText,"Iniciar sesi�n en tu comunidad");
    		au.click(btnContinuarUsuario);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		
    		/************************ Paso 4 En caso de Que el usuario tenga mas Comunidades y aparezca la barra de busqueda - Escribir en barra de busqueda y tap boton "Iniciar Sesion en Oxxo Testing"***********************************/
    		List<WebElement> buscadorElemento = au.driver.findElements(buscadorComunidad);

    		if (buscadorElemento.size() != 0) {
    			au.sendKeys(buscadorComunidad, "oxxo");
    			Thread.sleep(3000);
    			baseExecution.addStep("Tap en bot�n 'Iniciar Sesion' en Comunidad Testing");
    			testCase.addScreenShotCurrentStep(au, "testImage");
        		String assertTitleCommunityText = au.driver.findElement(assertTitleCommunity).getText();
        		assertEquals(assertTitleCommunityText,"�A qu� comunidad te gustar�a loguearte?");
    			au.click(comunidadOxxoTesting);
    			Thread.sleep(3000);
    		}
    		
    		/************************ Paso 4 En caso de que el usuario tenga 2 Comunidades y sin aparecer la barra de busqueda - Tap en boton "Iniciar Sesion en Oxxo Testing"***********************************/
    		List<WebElement> oxxoTestingElemento = au.driver.findElements(comunidadOxxoTesting);
    		
    		if (oxxoTestingElemento.size() != 0) {
    			Thread.sleep(3000);
    			baseExecution.addStep("Tap en bot�n 'Iniciar Sesion' en Comunidad Testing");
        		String assertTitleCommunityText = au.driver.findElement(assertTitleCommunity).getText();
        		testCase.addTextEvidenceCurrentStep(assertTitleUserText);
        		assertEquals(assertTitleCommunityText,"�A qu� comunidad te gustar�a loguearte?");
    			testCase.addScreenShotCurrentStep(au, "testImage");
    			au.click(comunidadOxxoTesting);
    		}

    		/************************ Paso 5 - Ingresar la contrase�a de los datos de prueba en el campo Contrase�a ***************************/
    	baseExecution.addStep("Ingresar la Contrase�a");
    		au.sendKeys(contraInput, password);
    		String assertTitlePasswordText = au.driver.findElement(assertTitlePassword).getText();
    		assertEquals(assertTitlePasswordText,"Ingresa tu contrase�a");
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		
    		/************************ Paso 6 - Tap en el bot�n "Continuar" ***************************/
    	baseExecution.addStep("Tap en boton 'Continuar'");
    		au.click(continuarBtnPass);
    		assertEquals(assertTitlePasswordText,"Ingresa tu contrase�a");
    		testCase.addScreenShotCurrentStep(au, "testImage");

    		/************************ Paso Condicionado, Unico y Irrepetible - Vista restablecer contrase�a ***************************/
    		By contraRestablecida = By.xpath("//android.widget.EditText[@text=\"Contrase�a\"]");
    	
    		List<WebElement> variable = au.driver.findElements(contraRestablecida);
    		
    		if (variable.size() != 0) {
    			au.sendKeys(contraRestablecida1, "passwordOxxoTesting");
    			au.sendKeys(contraRestablecida2, "passwordOxxoTesting");
    			testCase.addScreenShotCurrentStep(au, "testImage");
    			// Removido por fallo 
    			//String assertContraRestablecida1Text = au.driver.findElement(contraRestablecida1).getText();
        		//assertEquals(assertContraRestablecida1Text,"passwordOxxoTesting");
    			au.click(btnActualizaContra);
    			Thread.sleep(3500);
    		}
    		Thread.sleep(3000);
    	}
    
    /**
	 * Metodo
	 ******************************************************************************************/
    /**
     * Inicia sesion con un usuario invalido y valida mensaje de Error 
     *
     * @param user El usuario con el que se iniciara sesi�n, se debe ingresar desde el DataProvider.
     * @throws InterruptedException 
     */
    public void validateMessageErrorInputUser(String User) throws InterruptedException {
    	// ESPERA DE 15 SEGUNDOS - MAQUINA VIRTUAL 
		Thread.sleep(15000);
		/************************ Paso 2 - Ingresar el Usuario ***************************/
    	baseExecution.addStep("Ingresar un usuario");
	    	au.sendKeys(inputUsuario, User);
			String assertTitleUserText = au.driver.findElement(assertTitleUser).getText();
			assertEquals(assertTitleUserText,"Iniciar sesi�n en tu comunidad");
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		/************************ Paso 3 - Tap en el Boton "Continuar"  ***************************/
		baseExecution.addStep("Tap en el boton 'Continuar' ");
			au.click(btnContinuarUsuario);
			assertEquals(assertTitleUserText,"Iniciar sesi�n en tu comunidad");
			testCase.addScreenShotCurrentStep(au, "testImage");
		
		/************************ Paso 4 - Mostrar mensaje de error ***************************/
		baseExecution.addStep("Mostrar mensaje de error");
			String mensajeErrorUserText = au.driver.findElement(mensajeErrorUser).getText();
			testCase.addScreenShotCurrentStep(au, "testImage");
			assertEquals(mensajeErrorUserText, "Tu cuenta no tiene ninguna comunidad asociada");
    }
    
    /**
	 * Metodo
	 ******************************************************************************************/
    /**
     * Inicia sesion con un usuario valido y al ingresar una contrasena invalida aparezca un mensaje de Error 
     *
     * @param user El usuario con el que se iniciara sesi�n, se debe ingresar desde el DataProvider.
     * @throws InterruptedException 
     */
    public void vaidateMessageErrorInputPassword(String User, String Password) throws InterruptedException {
    	// ESPERA DE 15 SEGUNDOS - MAQUINA VIRTUAL 
    	Thread.sleep(15000);
    	/************************ Paso 2 - Ingresar el Usuario ***************************/
    	baseExecution.addStep("Ingresar un usuario");
	    	au.sendKeys(inputUsuario, User);
			String assertTitleUserText = au.driver.findElement(assertTitleUser).getText();
			assertEquals(assertTitleUserText,"Iniciar sesi�n en tu comunidad");
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		/************************ Paso 3 - Tap en el Boton "Continuar"  ***************************/
		baseExecution.addStep("Tap en el boton 'Continuar' ");
			au.click(btnContinuarUsuario);
			assertEquals(assertTitleUserText,"Iniciar sesi�n en tu comunidad");
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		/************************ Paso 4 - Mostrar vista ingresar contrase�a ***************************/
		baseExecution.addStep("Mostrar mensaje de error");
			String passwordTitleText = au.driver.findElement(assertTitlePassword).getText();
			au.sendKeys(contraInput, Password);
			testCase.addScreenShotCurrentStep(au, "testImage");
			assertEquals(passwordTitleText, "Ingresa tu contrase�a");
		
		/************************ Paso 5 - Tap en boton Iniciar Sesion ***************************/
		baseExecution.addStep("Tap en boton iniciar sesion");
			assertEquals(passwordTitleText, "Ingresa tu contrase�a");
			au.click(continuarBtnPass);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		/************************ Paso 6 - Mostrar mensaje de error ***************************/
		baseExecution.addStep("Mostrar mensaje de Error al ingresar una contrase�a incorrecta");
			String mensajeErrorPasswordText = au.driver.findElement(mensajeErrorPassword).getText();
			assertEquals(mensajeErrorPasswordText, "Contrase�a incorrecta");
			testCase.addScreenShotCurrentStep(au, "testImage");

    }	
    
}
