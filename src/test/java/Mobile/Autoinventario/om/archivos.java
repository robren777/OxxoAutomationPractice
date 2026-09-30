package Mobile.Autoinventario.om;

import static org.testng.Assert.assertEquals;

//import java.time.Duration;

import org.openqa.selenium.By;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class archivos {
	 //Variables Globales
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	
	/*
	 * Localizadores Boton Archivos
	 ******************************************************************************************/
	private By archivos = By.xpath("//android.widget.TextView[@content-desc=\"Archivos\"]");
	private By labelDescargas = By.xpath("(//android.widget.TextView[@text=\"Descargas\"])[2]");
	/*
		 * Localizadores ejecutable APK OxxoAuditor
	 ******************************************************************************************/
	private By apkOxxoAuditor = By.xpath("//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"app-debugOxxoAuditor_VC6_1.0.6_.apk\"]");
	/*
	 * Localizadores pop up continuar y btn continuar descarga
	 ******************************************************************************************/
	private By popUpContinuarDescarga = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
	private By btnContinuarDescarga = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
	/*
	/*
	 * Localizadores pop up instalar Oxxo Auditor y btn instalar
	 ******************************************************************************************/
	private By popUpInstalar = By.xpath("/hierarchy/android.widget.FrameLayout");
	private By btnInstalar = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
	/*
	 * Localizadores pop up abrir Oxxo Auditor y btn listo
	 ******************************************************************************************/
	private By popUpAbrirOxxoAuditor = By.xpath("/hierarchy/android.widget.FrameLayout");
	/*
	 */
	/*
	 * Localizadores aplicacion instalada
	 ******************************************************************************************/
	private By appInstalada = By.xpath("//android.widget.TextView[@content-desc=\"OXXO Auditor\"]");
	/*
	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Autoinventarios inteligentes
	 * @author Rebeca Martinez
	 * @param au (AppiumUtil): objeto de la clase
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public archivos (TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}
	
	//Metodo para tap en menu archivos
	public void tapArchivos() throws InterruptedException {
		baseExecution.addStep("Tap en menu archivos");
		boolean displayedarchivos = au.driver.findElement(archivos).isDisplayed();
		assertEquals(displayedarchivos, true);
    	au.click(archivos);
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	validaCarpetaDescargas();
    	}
	
	//Metodo para validar haber entrado a descargas de hh
	public void validaCarpetaDescargas() throws InterruptedException {
		baseExecution.addStep("Valida estar en Descargas");
		boolean textDescargas = au.driver.findElement(labelDescargas).isDisplayed();
		assertEquals(textDescargas, true);
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	if (textDescargas == true) {
            testCase.addTextEvidenceCurrentStep("Se valida carpeta Descargas");
          } else {
            testCase.addTextEvidenceCurrentStep("No se encuentra carpeta 'Descargas'");
          }
    	tapApk();
    	}
	
	//Metodo para tap en ejecutable de APK Oxxo Auditor
	public void tapApk() throws InterruptedException {
		baseExecution.addStep("Tap en el ejecutable Oxxo Auditor");
		boolean displayedapkOxxoAuditor = au.driver.findElement(apkOxxoAuditor).isDisplayed();
		assertEquals(displayedapkOxxoAuditor, true);
    	au.click(apkOxxoAuditor);
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	validaPopUpContinuar();
    	}
	
	//Metodo para validar pop up continuar descarga
	public void validaPopUpContinuar() throws InterruptedException {
		baseExecution.addStep("Valida popup continuar");
		boolean displayedpopUpContinuarDescarga = au.driver.findElement(popUpContinuarDescarga).isDisplayed();
		assertEquals(displayedpopUpContinuarDescarga, true);
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	if (displayedpopUpContinuarDescarga == true) {
            testCase.addTextEvidenceCurrentStep("Se valida pop up continuar descarga");
          } else {
            testCase.addTextEvidenceCurrentStep("No se encuentra pop up continuar descarga'");
          }
    	tapContinuar();
    	}
	
	//Metodo para tap en btn continuar instalacion de apk oxxo auditor
	public void tapContinuar() throws InterruptedException {
		baseExecution.addStep("Tap en continuar instalacion apk oxxo auditor");
		boolean displayedbtnContinuarDescarga = au.driver.findElement(btnContinuarDescarga).isDisplayed();
		assertEquals(displayedbtnContinuarDescarga, true);
    	au.click(btnContinuarDescarga);
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	validaPopUpInstalar();
    	}
	
	//Metodo para validar PopUp Instalar
	public void validaPopUpInstalar() throws InterruptedException {
		baseExecution.addStep("Valida popup instalar");
		boolean displayedpopUpInstalar = au.driver.findElement(popUpInstalar).isDisplayed();
		assertEquals(displayedpopUpInstalar, true);
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	if (displayedpopUpInstalar == true) {
            testCase.addTextEvidenceCurrentStep("Se valida pop up instalar");
          } else {
            testCase.addTextEvidenceCurrentStep("No se encuentra pop up instalar'");
          }
    	tapInstalar();
    	}
	
	//Metodo para tap en btn instalar
	public void tapInstalar() throws InterruptedException {
		baseExecution.addStep("Tap en instalar");
		boolean displayedbtnInstalar = au.driver.findElement(btnInstalar).isDisplayed();
		assertEquals(displayedbtnInstalar, true);
    	au.click(btnInstalar);
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	validaPopUpAbrirOxxoAuditor();
    	}
	
	//Metodo para validar PopUp Abrir Oxxo Auditor Se instalo app
	public void validaPopUpAbrirOxxoAuditor() throws InterruptedException {
		baseExecution.addStep("Valida popup AbrirOxxoAuditor");
    	boolean displayedpopUpAbrirOxxoAuditor = au.driver.findElement(popUpAbrirOxxoAuditor).isDisplayed();
		assertEquals(displayedpopUpAbrirOxxoAuditor, true);
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	if (displayedpopUpAbrirOxxoAuditor == true) {
            testCase.addTextEvidenceCurrentStep("Se valida pop up Se instalo la app OXXO AUDITOR");
          } else {
            testCase.addTextEvidenceCurrentStep("No se encuentra'");
          }
    	au.driver.pressKey(new KeyEvent(AndroidKey.HOME));
    	validaInstalacion();
    	}
	
	//Metodo para validar instalacion de aplicacion
	public void validaInstalacion() throws InterruptedException {
		baseExecution.addStep("Valida instalacion de aplicacion");
    	boolean displayedappInstalada = au.driver.findElement(appInstalada).isDisplayed();
		assertEquals(displayedappInstalada, true);
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	if (displayedappInstalada == true) {
            testCase.addTextEvidenceCurrentStep("Se valida instalacion");
          } else {
            testCase.addTextEvidenceCurrentStep("No se encuentra app'");
          }
    	}
}
