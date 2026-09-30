package Mobile.Autoinventario.om;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class loginOAD {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	
	/*
	 * Localizadores de inicio de sesión
	 * */
	private By user = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etUserName\"]");
	private By password = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etPassword\"]");
	private By btnLogin = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnLogin\"]");
	private By ptaDiagnostico = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvStatus"); 

	/*
	 * Localizadores menu principal
	 * */
	private By opcionGente = By.xpath("//android.widget.TextView[@text=\"Gente\"]");


	/* Constructor
	 ******************************************************************************************/
	/**

	 * Constructor de la clase LoginOAD(Oxxo al dia)
	 * @author Joel Rodriguez
	 * @param au (AppiumUtil): objeto de la clase
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public loginOAD(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Login con user y password
	 *
	 * @param nombreDeUsuario El usuario con el que se iniciara sesion
	 * @param contraDeUsuario El password con el que inicar� sesion
	 *
	 *  Pre requisito: 
	 *  1. Se debe completar la primer ventana donde se ingresa la ip y el puerto hasta llegar al Login
	 *  2. No se debe eliminar el cache ni los datos de la app
	 */
	public void login(String nombreDeUsuario, String contraDeUsuario) throws InterruptedException {
		baseExecution.addStep("Ingresar usuario y contraseña");
		au.driver.hideKeyboard();
		au.sendKeys(user, nombreDeUsuario);
		au.sendKeys(password, contraDeUsuario);
		au.driver.pressKey(new KeyEvent(AndroidKey.BACK));
		String textPantallaDiagnostico = au.driver.findElement(ptaDiagnostico).getText();
		assertEquals(textPantallaDiagnostico, "Pantalla de diagnóstico");
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(btnLogin);
		validarLogin();
	}
	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar que el post login fue correcto
	 *
	 */
	public void validarLogin() {
		/************************  Paso 2 - Validar post Login / Ventana Home ***************************/
		baseExecution.addStep("Se muestra pantalla de Menú Principal");
		boolean displayedOpcionGente = au.driver.findElement(opcionGente).isDisplayed();
		assertEquals(displayedOpcionGente, true);
		testCase.addScreenShotCurrentStep(au, "testImage");

		if (displayedOpcionGente == true) {
			testCase.addTextEvidenceCurrentStep("Se valida login de credenciales");
		} else {
			testCase.addTextEvidenceCurrentStep("Se tuvo problemas con el acceso");
		}
	}
}