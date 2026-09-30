package Mobile.Autoinventario.om;

import static org.testng.Assert.assertEquals;
import java.time.Duration;
import org.openqa.selenium.By;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class Inicio {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	/*
	 /*
     * Localizadores Vista Inicio
     ******************************************************************************************/
    private By labelInvCiclico = By.id("com.femsa.oxxo.oxxoauditor:id/lblCyclicalInventory");
    
 
	/* Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Proveedores Directos
	 * @author Alejandro Valdez

	 /*
	 * Localizadores Vista Inicio
	 ******************************************************************************************/
	private By labelAutoInv = By.id("com.femsa.oxxo.oxxoauditor:id/lblRtaAutoInventories");
	private By labelWelcome = By.id("com.femsa.oxxo.oxxoauditor:id/lblWelcome");


	/* Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Inicio
	 * @author Joel Rodriguez
	 * @param au (AppiumUtil): objeto de la clase
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */

	public Inicio(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}
  
	/**
     * Metodo
     ****************************************************************************************/
    /**
     * Seleccionar una opci�n de la vista Inicio
     *
     */
    public void botonInvCiclico() throws InterruptedException {
      baseExecution.addStep("Tap en el boton 'Inventario Cíclico'");
      boolean displayedlabelOpcionInventarioCiclico = au.driver.findElement(labelInvCiclico).isDisplayed();
      assertEquals(displayedlabelOpcionInventarioCiclico, true);
      au.click(labelInvCiclico);
      //testCase.addScreenShotCurrentStep(au, "testImage");
    }

	/**
	 * Metodo
	 ****************************************************************************************/
	/**
	 * Mostrar pantalla Inicio
	 *
	 */
	public void validateInicio() throws InterruptedException {
		baseExecution.addStep("Menu Inicio de Oxxo Auditor");
		boolean displayedLabelWelcome = au.driver.findElement(labelWelcome).isDisplayed();
		assertEquals(displayedLabelWelcome, true);
		testCase.addScreenShotCurrentStep(au, "testImage");    
		//     au.scrollToElement(labelAutoInv);      au.click(labelAutoInv);
	}

	/**
	 * Metodo
	 ****************************************************************************************/
	/**
	 * Validar opciones Inventario Ciclico y Autoinventario Inteligente
	 *
	 */
	public void validateContenido() throws InterruptedException{
		baseExecution.addStep("Validar Inventario Ciclico y Autoinventario Inteligente");
		boolean displayedLabelIC = au.driver.findElement(labelInvCiclico).isDisplayed();
		assertEquals(displayedLabelIC, true);
		au.scrollToElement(labelAutoInv);
		boolean displayedLabelAI = au.driver.findElement(labelAutoInv).isDisplayed();
		assertEquals(displayedLabelAI, true);
		if (displayedLabelAI == true) {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			testCase.addScreenShotCurrentStep(au,"testImage" );
			testCase.addTextEvidenceCurrentStep("Se visualizan opciones Inventario Ciclico y Autoinventario Inteligente");
		}else {
			testCase.addTextEvidenceCurrentStep("No se visualiza la opcion de Autoinventario Inteligente");
		}
	}
}
