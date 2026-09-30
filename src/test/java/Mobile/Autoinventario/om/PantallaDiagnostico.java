package Mobile.Autoinventario.om;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class PantallaDiagnostico {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	/*
	 * Localizadores de inicio de sesión
	 * */
	private By ptaDiagnosticoNav = By.xpath("//android.widget.CheckedTextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/design_menu_item_text\" and @text=\"Pantalla de diagnóstico\"]");
	private By ptaDiagnostico = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvStatus"); 
	/*
	 * Localizadores de diagnostico
	 * */
	private By generarQR = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvDbfsGenerateWQr");
	/*
	 * Localizadores de Generar QR
	 * */
	private By QR = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/qrCodeImageView");
	private By btnRegresar = By.xpath("//android.widget.ImageButton[@content-desc=\"Navegar hacia arriba\"]");
	/*
	 * Localizadores de Generar QR
	 * */
	private By btnOpciones = By.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]");


	/* Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase PantallaDiagnostico
	 * @author Joel Rodriguez
	 * @param au (AppiumUtil): objeto de la clase
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public PantallaDiagnostico(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/** Metodo
	 ****************************************************************************************/
	/**
	 * Seleccionar una opción de la vista Pantalla de diagnostico
	 *
	 */
	public void Opciones() throws InterruptedException {
		/************************ Paso 2 - Acceder a menu opciones ***************************/
		baseExecution.addStep("Seleccionar icono opciones parte superior izquierda");
		boolean displayedbtnOpciones = au.driver.findElement(btnOpciones).isDisplayed();
		assertEquals(displayedbtnOpciones, true);
		au.click(btnOpciones);
		testCase.addScreenShotCurrentStep(au, "testImage");
		pantallaDiagnosticoNav();
	}

	/** Metodo
	 ****************************************************************************************/
	/**
	 * Seleccionar una opción de la vista Pantalla de diagnostico
	 *
	 */
	public void pantallaInicio() throws InterruptedException {
		/************************ Paso 3 - Acceder a pantalla diagnostico ***************************/
		baseExecution.addStep("Seleccionar pantalla de diagnostico");
		au.driver.hideKeyboard();
		String textPtaDiagnostico = au.driver.findElement(ptaDiagnostico).getText();
		assertEquals(textPtaDiagnostico, "Pantalla de diagnóstico");
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(ptaDiagnostico);
		genQR();
	}

	/** Metodo
	 ****************************************************************************************/
	/**
	 * Seleccionar opcion Generar QR
	 *
	 */
	public void genQR() throws InterruptedException {
		/************************ Paso 4 - Generar QR ***************************/
		baseExecution.addStep("Seleccionar boton Generar QR");
		boolean displayedGenQR = au.driver.findElement(generarQR).isDisplayed();
		assertEquals(displayedGenQR, true);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(generarQR);
		validateQR();
	}

	/** Metodo
	 ****************************************************************************************/
	/**
	 * Validar la generacion de QR exitoso
	 *
	 */
	public void validateQR() {
		/************************ Paso 5 - Validar QR generado ***************************/
		baseExecution.addStep("Validar genere el Codigo QR");
		boolean displayedQR = au.driver.findElement(QR).isDisplayed();
		assertEquals(displayedQR, true);
		testCase.addScreenShotCurrentStep(au, "testImage");

		if (displayedQR == true) {
			testCase.addTextEvidenceCurrentStep("Se genero el QR de manera correcta");
		} else {
			testCase.addTextEvidenceCurrentStep("No se genero el QR de manera correcta");
		}
	}

	/** Metodo
	 ****************************************************************************************/
	/**
	 * Boton regresar en Codigo QR generado
	 *
	 */
	public void regresar() {
		/************************ Paso 6 - Boton regresar ***************************/
		baseExecution.addStep("Seleccionar opción Regresar");
		boolean displayedRegresar = au.driver.findElement(btnRegresar).isDisplayed();
		assertEquals(displayedRegresar, true);
		au.click(btnRegresar);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}
	
	/** Metodo
	 ****************************************************************************************/
	/**
	 * Seleccionar una opción de la vista Pantalla de diagnostico
	 *
	 */
	public void pantallaDiagnosticoNav() throws InterruptedException {
		/************************ Paso 7 - Acceder a pantalla diagnostico desde opciones ***************************/
		baseExecution.addStep("Seleccionar pantalla de diagnostico");
		String textPtaDiagnosticoNav = au.driver.findElement(ptaDiagnosticoNav).getText();
		assertEquals(textPtaDiagnosticoNav, "Pantalla de diagnóstico");
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(ptaDiagnosticoNav);
		genQR();
	}
}